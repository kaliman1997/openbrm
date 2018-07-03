package dk.comtalk.billing.server.customer.balance;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.sapienter.jbilling.common.CommonConstants;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.item.ItemBL;
import com.sapienter.jbilling.server.item.ItemDecimalsException;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.item.db.ItemDAS;
import com.sapienter.jbilling.server.item.tasks.IItemPurchaseManager;
import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.mediation.Record;
import com.sapienter.jbilling.server.notification.INotificationSessionBean;
import com.sapienter.jbilling.server.notification.MessageDTO;
import com.sapienter.jbilling.server.notification.NotificationBL;
import com.sapienter.jbilling.server.notification.NotificationNotFoundException;

import dk.comtalk.billing.server.customer.balance.db.BalanceDTO;
import dk.comtalk.billing.server.customer.balance.db.BalanceDAS;

import com.sapienter.jbilling.server.pluggableTask.TaskException;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskManager;
import com.sapienter.jbilling.server.process.ConfigurationBL;
import com.sapienter.jbilling.server.process.db.PeriodUnitDAS;
import com.sapienter.jbilling.server.provisioning.db.ProvisioningStatusDAS;
import com.sapienter.jbilling.server.provisioning.event.SubscriptionActiveEvent;
import com.sapienter.jbilling.server.system.event.EventManager;
import com.sapienter.jbilling.server.user.ContactBL;
import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.db.CompanyDAS;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import com.sapienter.jbilling.server.user.db.UserDAS;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.util.PreferenceBL;
import com.sapienter.jbilling.server.util.audit.EventLogger;
import com.sapienter.jbilling.server.util.db.CurrencyDAS;
import java.math.BigDecimal;
import java.util.ArrayList;

public class BalanceBL extends ResultList
        implements BalanceSQL {

    private BalanceDTO balance = null;
    private BalanceDAS balanceDas = null;
    private static final Logger LOG = Logger.getLogger(BalanceBL.class);
    private EventLogger eLogger = null;

    public BalanceBL(Integer orderId) {
        init();
        set(orderId);
    }

    public BalanceBL() {
        init();
    }

    public BalanceBL(BalanceDTO balance) {
        init();
        this.balance = balance;
    }

    private void init() {
        eLogger = EventLogger.getInstance();
        balanceDas = new BalanceDAS();
    }

    public BalanceDTO getEntity() {
        return balance;
    }

  

    public void set(Integer id) {
        balance = balanceDas.find(id);
    }

    public void setForUpdate(Integer id) {
        balance = balanceDas.findForUpdate(id);
    }

    public void set(BalanceDTO newBalance) {
        balance = newBalance;
    }



    public BalanceDTO getDTO() {
        return balance;
    }
   
    public void delete(Integer executorId) {
        // the event is needed before the deletion
        balance.setDeleted(1);

        eLogger.audit(executorId, balance.getBaseUserByUserId().getId(),
                Constants.TABLE_PUCHASE_ORDER, balance.getId(),
                EventLogger.MODULE_ORDER_MAINTENANCE,
                EventLogger.ROW_DELETED, null,
                null, null);


    }
    public Integer create(Integer entityId, BalanceDTO balanceDto) throws SessionInternalError {
        try {
                  
            
            balance = balanceDas.save(balanceDto);
          
            //check if balance is created with activeSince<= now (or null)
            Date now = new Date();
            Date activeSince = balance.getActiveSince();
            if (activeSince == null || activeSince.before(now)) {
                // generate SubscriptionActiveEvent for balance
                //SubscriptionActiveEvent newEvent = new SubscriptionActiveEvent(entityId, balance);
                //EventManager.process(newEvent);
                LOG.debug("BalanceBL.create(): generated SubscriptionActiveEvent for balance: " + balance.getId());
            }

            // add a log row for convenience
            //eLogger.auditBySystem(entityId, balance.getBaseUserByUserId().getId(),
                    //Constants.TABLE_USER_BALANCE, balance.getId(),
                    //EventLogger.MODULE_ORDER_MAINTENANCE, EventLogger.ROW_CREATED, null, null, null);

            //EventManager.process(new NewOrderEvent(entityId, balance));
        } catch (Exception e) {
            throw new SessionInternalError("Create exception creating balance entity bean", BalanceBL.class, e);
        }

        return balance.getId();
    }
    public void update(Integer executorId, BalanceDTO dto) {
        // update first the balance own fields
        if (!Util.equal(balance.getActiveUntil(), dto.getActiveUntil())) {
            audit(executorId, balance.getActiveUntil());
            balance.setActiveUntil(dto.getActiveUntil());
        }
        if (!Util.equal(balance.getActiveSince(), dto.getActiveSince())) {
            audit(executorId, balance.getActiveSince());
            balance.setActiveSince(dto.getActiveSince());
        }
		if (dto.getBalance() != null) {
            balance.setBalance(balance.getBalance().add(dto.getBalance()));
        }
               
        balance = balanceDas.save(balance);

            
        
        if (executorId != null) {
            eLogger.audit(executorId, balance.getBaseUserByUserId().getId(),
                    Constants.TABLE_PUCHASE_ORDER, balance.getId(),
                    EventLogger.MODULE_ORDER_MAINTENANCE,
                    EventLogger.ROW_UPDATED, null,
                    null, null);
        } else {
            eLogger.auditBySystem(balance.getBaseUserByUserId().getCompany().getId(),
                    balance.getBaseUserByUserId().getId(),
                    Constants.TABLE_PUCHASE_ORDER,
                    balance.getId(),
                    EventLogger.MODULE_ORDER_MAINTENANCE,
                    EventLogger.ROW_UPDATED, null,
                    null, null);
        }

    }

   public List<BalanceDTO> getValidUserBalances(Integer userId, Integer orderId, Integer orderLineId) {

     Date today = new Date();
     List<BalanceDTO> balances = balanceDas.findValidByUser(userId);
     return balances;

  }

  public List<BalanceDTO> getValidUserBalances(Integer userId, Integer orderLineId) {

     Date today = new Date();
     List<BalanceDTO> balances = balanceDas.getUserBalancesByUserAndOrderLine(userId, orderLineId,  today, today);
     return balances;

  }



   public UserBalanceWS getWS(Integer languageId) {
        UserBalanceWS retValue = new UserBalanceWS();
        return retValue;
   }
    


    private void audit(Integer executorId, Date date) {
        eLogger.audit(executorId, balance.getBaseUserByUserId().getId(),
                Constants.TABLE_PUCHASE_ORDER, balance.getId(),
                EventLogger.MODULE_ORDER_MAINTENANCE,
                EventLogger.ROW_UPDATED, null,
                null, date);
    }

    private void audit(Integer executorId, Integer in) {
        eLogger.audit(executorId, balance.getBaseUserByUserId().getId(),
                Constants.TABLE_PUCHASE_ORDER, balance.getId(),
                EventLogger.MODULE_ORDER_MAINTENANCE,
                EventLogger.ROW_UPDATED, in,
                null, null);
    }

  
}

