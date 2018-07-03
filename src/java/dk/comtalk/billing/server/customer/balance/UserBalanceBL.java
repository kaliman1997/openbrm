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
import com.sapienter.jbilling.server.device.db.UserDeviceDAS;
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

public class UserBalanceBL extends ResultList
        implements BalanceSQL {

    private static final Logger LOG = Logger.getLogger(UserBalanceBL.class);

    private Integer userId;
    private BigDecimal currentBalance;
    private Integer currencyId;
    private List<BalanceDTO> userBalances = null;
    private BalanceDAS balanceDas = null;
    private EventLogger eLogger = null;
    private Date activeSince = null;
    private Date activeUntil = null;
    private String telephone = null;
    private Integer orderId;


    public UserBalanceBL(Integer userId, Integer currencyId) {
	this.balanceDas = new BalanceDAS();
	this.userId = userId;
	this.currencyId = currencyId;
	LOG.debug("Initializing UserBalance object for user " +  userId + " resource " + currencyId);
        init();
    }
	
    public UserBalanceBL(Integer userId, Integer currencyId, Date start, Date end, String telephone) {
        this.balanceDas = new BalanceDAS();
        this.userId = userId;
        this.currencyId = currencyId;
	this.activeSince = start;
	this.activeUntil = end;
	this.telephone = telephone;
        LOG.debug("Initializing UserBalance object for user " +  userId + " resource " + currencyId);
        init();
    }
    
    public UserBalanceBL(Integer uId, Integer rId,
			String aNumber) {
    	
    	this.balanceDas = new BalanceDAS();
    	this.userId = uId;
    	this.currencyId = rId;
    	this.telephone = aNumber;
    	LOG.debug("Initializing UserBalance object for user " +  userId + " resource " + currencyId +" telephone  "+telephone);
            init();
		
	}


    public UserBalanceBL(List<BalanceDTO> balances) {
	this.balanceDas = new BalanceDAS();
        this.userBalances = balances;
    }

    public UserBalanceBL(Integer userId) {
        this.balanceDas = new BalanceDAS();
        init();
    }


    private void init() {
        eLogger = EventLogger.getInstance();
	if(this.userId != null &&  this.currencyId != null) {
		if(this.activeSince != null && this.activeUntil != null) {
		  LOG.debug( "getting balance for period " + activeSince +  " to " + activeUntil );
		  this.userBalances = balanceDas.findValidByUserAndCurrency(userId, currencyId, activeSince, activeUntil);
		} else {
		  this.userBalances = balanceDas.findValidByUserAndCurrency(userId, currencyId);
		}
	} else if(this.userId != null && this.currencyId == null) {
		this.userBalances = balanceDas.findValidByUser(userId);
	}
	LOG.debug( this.userBalances.size() + " user balance object(s) found " );
	currentBalance = getCurrentBalance();
    }

    public BigDecimal getCurrentBalance(Date startDate, Date endDate) {
	LOG.debug("executing getCurrentBalance with startDate and endDate");
	currentBalance = new BigDecimal("0");
	userBalances = balanceDas.findValidByUserAndCurrency(userId, currencyId, startDate, endDate);
	if(userBalances == null || userBalances.size() <= 0 ) {
	  LOG.debug("No balance  of resource : " + currencyId + " for given user "  + userId);
	  return BigDecimal.ZERO;
	}
	LOG.debug("Got userBalances with dates:"+userBalances.size());

	UserDeviceDAS das = new UserDeviceDAS();
	 try{
	    	orderId = das.findByTelephoneNumber(telephone).getOrderId().getId();
			LOG.debug("Got order id for the telphone number:"+telephone+" is "+orderId);
	     }catch (Exception e) {
				LOG.error("telephone number not found");
	}	
	
	String notesToCompare = "Usage Order for " +telephone;
	
	for(BalanceDTO userBalance : userBalances) {
			String orderNotes = userBalance.getOrderDTO().getNotes().trim();
			LOG.debug("userbal order notes"+orderNotes+" comparing with noteToCopare"+notesToCompare);
			LOG.debug("comparing OrderIds user bal orderId"+userBalance.getOrderDTO().getId()+" with "+orderId);
			if(userBalance.getOrderDTO().getId() == orderId || orderNotes.equalsIgnoreCase(notesToCompare.trim())){  
				LOG.debug("Adding Balance " + userBalance.getBalance());
                currentBalance = currentBalance.add(userBalance.getBalance());
    		}
	}
	return currentBalance;
    }

     public BigDecimal getCurrentBalance() {

	currentBalance = new BigDecimal("0");
        if(userBalances == null || userBalances.size() <= 0 ) {
          LOG.debug("No balance  of resource : " + currencyId + " for given user "  + userId);
          return BigDecimal.ZERO;
        }
        UserDeviceDAS das = new UserDeviceDAS();
    	 try{
	    	orderId = das.findByTelephoneNumber(telephone).getOrderId().getId();
			}catch (Exception e) {
				LOG.error("telephone number not found");
	}    
		String notesToCompare = "Usage Order for " +telephone;
		String orderNotes = "";
    	for(BalanceDTO userBalance : userBalances) {
    		try{
			 orderNotes = userBalance.getOrderDTO().getNotes().trim();
			LOG.debug("userbal order notes in try catch "+orderNotes+" comparing with noteToCopare "+notesToCompare);
    		}catch (Exception e) {
    			LOG.error("order notes not found");
    		}
			if(userBalance.getOrderDTO().getId() == orderId || orderNotes.equalsIgnoreCase(notesToCompare.trim())){  
				LOG.debug("Adding Balance " + userBalance.getBalance());
                currentBalance = currentBalance.add(userBalance.getBalance());
    		}
    	}
	LOG.debug(" current balance  of resource : " + currencyId + " for given user "  + userId + " is " + currentBalance);

        return currentBalance;
    }


    public BigDecimal getValidBalanceByPeriod(Integer userId, Integer currencyId, Date startDate, Date endDate) {

	return getCurrentBalance(startDate, endDate);

    }
	

    public BalanceDTO  getEarliestExpiringBalance(Integer orderLineId, Integer currencyId ) {

	Date earliestActiveUntil = null;
	Date activeUntil = null;
	BalanceDTO retValue = null;

        for(BalanceDTO balance : userBalances) {

	  if(balance.getOrderLineDTO() == null) continue;
	  LOG.debug("comparing UserBalances orderLine with given orderLineId: "+balance.getCurrency().getId()+ " =? "+currencyId);
	  if( balance.getCurrency().getId() == currencyId && balance.getBalance().compareTo(BigDecimal.ZERO) == 1) {

	    activeUntil = balance.getActiveUntil();
	    if(earliestActiveUntil == null) {
	      earliestActiveUntil = activeUntil;
	      retValue = balance;
	    } else if (activeUntil != null && earliestActiveUntil.after(activeUntil) && balance.getBalance().compareTo(BigDecimal.ZERO) == 1) {
	      earliestActiveUntil = activeUntil;
	      retValue = balance; 
	    }
	  }

	}
	LOG.debug("earliest active until is="+retValue.getActiveUntil());
	return retValue;

    }

    public List<BalanceDTO> getUsageByOrderId(Integer orderId) {

       LOG.debug("executing getUsageByOrderId  with orderId: "+orderId);
       Date earliestActiveUntil = null;
       Date activeUntil = null;
       List<BalanceDTO> retValue = new ArrayList<BalanceDTO>();;

       for(BalanceDTO balance : userBalances) {

          if(balance.getOrderDTO() == null) continue;
          if(balance.getOrderDTO().getId() != orderId) continue;
	  if(balance.getBalance().compareTo(BigDecimal.ZERO) == 1) continue;

	  retValue.add(balance);
       }

       return retValue;



    }




}

