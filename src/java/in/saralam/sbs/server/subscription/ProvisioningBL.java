package in.saralam.sbs.server.subscription;

import com.sapienter.jbilling.common.CommonConstants;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.item.ItemBL;
import com.sapienter.jbilling.server.item.ItemDecimalsException;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.item.db.ItemDAS;
import com.sapienter.jbilling.server.item.tasks.IItemPurchaseManager;
import com.sapienter.jbilling.server.invoice.InvoiceBL;
import com.sapienter.jbilling.server.list.ResultList;
//import com.sapienter.jbilling.server.mediation.Record;
import com.sapienter.jbilling.server.notification.INotificationSessionBean;
import com.sapienter.jbilling.server.notification.MessageDTO;
import com.sapienter.jbilling.server.notification.NotificationBL;
import com.sapienter.jbilling.server.notification.NotificationNotFoundException;
/*import com.sapienter.jbilling.server.order.db.OrderBillingTypeDAS;
import com.sapienter.jbilling.server.order.db.OrderDAS;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDAS;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.order.db.OrderLineTypeDAS;
import com.sapienter.jbilling.server.order.db.OrderLineTypeDTO;
import com.sapienter.jbilling.server.order.db.OrderPeriodDAS;
import com.sapienter.jbilling.server.order.db.OrderPeriodDTO;
import com.sapienter.jbilling.server.order.db.OrderProcessDTO;
import com.sapienter.jbilling.server.order.db.OrderStatusDAS;*/
import com.sapienter.jbilling.server.order.event.NewActiveUntilEvent;
import com.sapienter.jbilling.server.order.event.NewOrderEvent;
import com.sapienter.jbilling.server.order.event.NewQuantityEvent;
import com.sapienter.jbilling.server.order.event.NewStatusEvent;
import com.sapienter.jbilling.server.order.event.OrderDeletedEvent;
import com.sapienter.jbilling.server.order.event.PeriodCancelledEvent;
import com.sapienter.jbilling.server.pluggableTask.OrderProcessingTask;
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
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.util.PreferenceBL;
import com.sapienter.jbilling.server.util.audit.EventLogger;
import com.sapienter.jbilling.server.util.db.CurrencyDAS;
import com.sapienter.jbilling.server.util.db.AbstractGenericStatus;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import javax.sql.rowset.CachedRowSet;

import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import in.saralam.sbs.server.subscription.db.ProvisioningTagDTO;
import in.saralam.sbs.server.subscription.db.ProvisioningTagMapDTO;
import in.saralam.sbs.server.subscription.db.ProvisioningTagMapDAS;
import in.saralam.sbs.server.subscription.db.ProvisioningTagDAS;
import in.saralam.sbs.server.subscription.ProvisioningWS;

/*--IO packages--*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.List;
import org.xml.sax.SAXException;
import com.sapienter.jbilling.server.pluggableTask.admin.ParameterDescription;
import com.sapienter.jbilling.server.item.PricingField;
//import com.sapienter.jbilling.server.mediation.Format;
//import com.sapienter.jbilling.server.mediation.FormatField;
//import com.sapienter.jbilling.server.mediation.Record;
import com.sapienter.jbilling.server.item.db.ItemDTO;

import javax.naming.NamingException;
import javax.sql.DataSource;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Emil
 */
public class ProvisioningBL extends ResultList
         {
    //private String directory;
	
	
    private ProvisioningTagDTO provisioningTagDTO = null;
    private ProvisioningTagDAS provisioningTagDAS = null;
    private static final Logger LOG = Logger.getLogger(ProvisioningBL.class);
    private EventLogger eLogger = null;
   

    public ProvisioningBL(Integer tagID) {
        init();
        set(tagID);
    }

    public ProvisioningBL() {
        init();
    }

    public ProvisioningBL(ProvisioningTagDTO provisioningTagDTO) {
        init();
        this.provisioningTagDTO = provisioningTagDTO;
    }

    private void init() {
        eLogger = EventLogger.getInstance();
        provisioningTagDAS = new ProvisioningTagDAS();
    }

    public ProvisioningTagDTO getEntity() {
        return provisioningTagDTO;
    }


    public void set(Integer id) {
    	provisioningTagDTO = provisioningTagDAS.find(id);
    }

    public void setForUpdate(Integer id) {
    	provisioningTagDTO = provisioningTagDAS.findForUpdate(id);
    }

    public void set(ProvisioningTagDTO newDevice) {
    	provisioningTagDTO = newDevice;
    }

    public ProvisioningTagDTO getDTO() {
        return provisioningTagDTO;
    }
	
	public Integer create(String tagCode, Integer tagLevel, Integer tagParent){
	
	  ProvisioningTagDTO provTagDTO = new ProvisioningTagDTO(tagCode, tagLevel, tagParent);
	  provTagDTO.setDeleted(0);
	  provTagDTO = new ProvisioningTagDAS().save(provTagDTO);
	  LOG.debug("Provisioning tag is cerated " +provTagDTO.getId());
	  return provTagDTO.getId();
	}

    public void delete(Integer executorId) {
        // the event is needed before the deletion

    }
	
    public void update(Integer executorId, ProvisioningTagDTO dto) {

    } 
	
	public ProvisioningWS getProvisioningWS(Integer languageId)
	{
		ProvisioningWS retValue = new ProvisioningWS(provisioningTagDTO.getId(), provisioningTagDTO.getCode());
		return retValue;
	}    
    
    public void setStatus(Integer executorId, Integer statusId) {

    }
	
	public void createProvMapTag(List<ProvisioningTagDTO> provTagsList, ItemDTO item){
	
	  
	  for(ProvisioningTagDTO provTag : provTagsList){
	    ProvisioningTagMapDTO provTagMap = new ProvisioningTagMapDTO();
		ProvisioningTagDTO parent = new ProvisioningTagDAS().find(provTag.getParentId());
		
		//ProvisioningTagDTO 
		provTagMap.setItemId(item);
		provTagMap.setProvisioningTag(provTag);
		provTagMap.setParent(parent);
		provTagMap.setLevel(provTag.getLevel());
		provTagMap.setDeleted(new Integer("0"));
		LOG.debug("ProvTagMap details are set ");
		ProvisioningTagMapDTO createdOne = new ProvisioningTagMapDAS().save(provTagMap);
		LOG.debug("ProvTagMap is created " +createdOne.getId());
	    
	  }
	
	}
	
	public void deleteProvTag(ProvisioningTagDTO provTag){
	  
	  provTag.setDeleted(new Integer("1"));
	  new ProvisioningTagDAS().save(provTag);
	  LOG.debug("provTag updated with deleted 1");
	
	}
     
}

