package in.saralam.sbs.server.subscription;

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
import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

//import sun.jdbc.rowset.CachedRowSet;

import com.sapienter.jbilling.common.CommonConstants;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.item.ItemBL;
import com.sapienter.jbilling.server.item.ItemDecimalsException;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.item.db.ItemDAS;
import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.item.db.ItemTypeDTO;
import com.sapienter.jbilling.server.item.tasks.IItemPurchaseManager;
import com.sapienter.jbilling.server.list.ResultList;
//import com.sapienter.jbilling.server.mediation.Record;
import com.sapienter.jbilling.server.notification.INotificationSessionBean;
import com.sapienter.jbilling.server.notification.MessageDTO;
import com.sapienter.jbilling.server.notification.NotificationBL;
import com.sapienter.jbilling.server.notification.NotificationNotFoundException;
import com.sapienter.jbilling.server.order.db.OrderDAS;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
//import com.sapienter.jbilling.server.system.event.EventManager;
import com.sapienter.jbilling.server.util.audit.EventLogger;
import in.saralam.sbs.server.subscription.event.NewStatusEvent;
import in.saralam.sbs.server.subscription.db.ServiceStatusDAS;
import in.saralam.sbs.server.subscription.db.ProvisioningTagMapDTO;
import in.saralam.sbs.server.subscription.db.ProvisioningTagMapDAS;
import in.saralam.sbs.server.subscription.db.ProvisioningTagMapInfoDTO;
import in.saralam.sbs.server.subscription.db.ProvisioningTagMapInfoDAS;
import in.saralam.sbs.server.subscription.db.ProvisioningTagDTO;
//import com.sapienter.jbilling.server.service.db.ServiceBillingTypeDAS;
import in.saralam.sbs.server.subscription.db.ServiceDAS;
import in.saralam.sbs.server.subscription.db.ServiceDTO;
import in.saralam.sbs.server.subscription.db.ServiceFeatureDAS;
import in.saralam.sbs.server.subscription.db.ServiceFeatureDTO;
import in.saralam.sbs.server.subscription.db.ServiceFeatureStatusDTO;
import in.saralam.sbs.server.subscription.db.ServiceFeatureStatusDAS;
import in.saralam.sbs.server.subscription.db.ServiceFeatureInfoDTO;
import in.saralam.sbs.server.subscription.db.ServiceFeatureInfoDAS;
import in.saralam.sbs.server.subscription.db.ServiceStatusDTO;
//import in.saralam.sbs.subscription.system.event.EventManager;
import com.sapienter.jbilling.server.util.Constants;

import com.sapienter.jbilling.server.pluggableTask.TaskException;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskManager;
import com.sapienter.jbilling.server.process.ConfigurationBL;
import com.sapienter.jbilling.server.process.db.PeriodUnitDAS;
//import com.sapienter.jbilling.server.provisioning.d.ProvisioningStatusDAS;
//import com.sapienter.jbilling.server.provisioning.eventb.SubscriptionActiveEvent;

import com.sapienter.jbilling.server.user.ContactBL;
import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.db.CompanyDAS;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import com.sapienter.jbilling.server.user.db.UserDAS;
import com.sapienter.jbilling.server.user.db.UserDTO;

import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.util.PreferenceBL;
import com.sapienter.jbilling.server.util.audit.EventLogger;
import com.sapienter.jbilling.server.util.db.CurrencyDAS;
import java.math.BigDecimal;
import java.util.ArrayList;
//import sun.jdbc.rowset.CachedRowSet;

public class ServiceFeatureBL extends ResultList
        implements ServiceSQL {

    private ServiceFeatureDTO serviceFeature = null;
    private ServiceFeatureDAS serviceFeatureDas = null;
    private static final Logger LOG = Logger.getLogger(ServiceFeatureBL.class);
    private EventLogger eLogger = null;
    private OrderLineDTO orderLine = null;

    public ServiceFeatureBL(Integer serviceFeatureId) {
        init();
        set(serviceFeatureId);
    }

    public ServiceFeatureBL() {
        init();
    }

    public ServiceFeatureBL(ServiceFeatureDTO serviceFeature) {
        init();
        this.serviceFeature = serviceFeature;
    }

    private void init() {
        eLogger = EventLogger.getInstance();
        serviceFeatureDas = new ServiceFeatureDAS();
    }

    public ServiceFeatureDTO getEntity() {
        return serviceFeature;
    }

    public void set(Integer id) {
    	serviceFeature = serviceFeatureDas.find(id);
    }

    public void setForUpdate(Integer id) {
    	serviceFeature = serviceFeatureDas.findForUpdate(id);
    }

    public void set(ServiceFeatureDTO newService) {
    	serviceFeature = newService;
    }



    public ServiceFeatureDTO getDTO() {
        return serviceFeature;
    }
    
    
   public Integer create(Integer serviceId){

    	LOG.debug("In serviceFeatureBL class");
    	Integer retValue = 0;
    	ServiceFeatureDTO serviceFeatureDTO=null;
    	  if(serviceId.equals(null)){
    	  LOG.debug("can't create service");
    	  return retValue;
    	   }
    	  try {
    		 ServiceFeatureDTO serviceFeatureDto =  new  ServiceFeatureDTO();
                 ServiceDTO serviceDto = new ServiceDAS().find(serviceId);
		 ServiceFeatureStatusDTO  status = new ServiceFeatureStatusDAS().find(SubscriptionConstants.SERVICE_FEATURE_STATUS_PENDING);
    		 orderLine =serviceDto.getOrderLineDTO();
    		 ItemDTO item = orderLine.getItem();
		 LOG.debug("creating service for provisionable item id :"+item.getId());
                 ItemDTO itemDto=  new ItemDTO(item.getId());	
	         //List<ProvisioningTagMapDTO> provTagMaps = new ProvisioningTagMapDAS().findByItemId(item.getId());
	         List<ProvisioningTagMapDTO> provTagMaps = new ProvisioningTagMapDAS().getPrimaryByItemId(item.getId());
		 LOG.debug("ProvisioningTagMapDAS returned lists sizes are:" + provTagMaps.size());
		 for(Iterator<ProvisioningTagMapDTO> proIt = provTagMaps.iterator(); proIt.hasNext();){

		   LOG.debug("Looping ProvisioningTagMapDTO");
		   ProvisioningTagMapDTO proTagMap =(ProvisioningTagMapDTO)proIt.next();
		   serviceFeatureDto.setProvisioningTagMap(proTagMap);
		   serviceFeatureDto.setServiceId(serviceDto);
		   serviceFeatureDto.setLevel(proTagMap.getLevel());
 		   serviceFeatureDto.setDeleted(0);
 		   serviceFeatureDto.setServiceFeatureStatus(status);
		   serviceFeature = new ServiceFeatureDAS().save(serviceFeatureDto);
		   List<ProvisioningTagMapInfoDTO> provisioningTagMapInfos  = new ProvisioningTagMapInfoDAS().findByProvisioningTagMapId(proTagMap.getId());
		   for(Iterator<ProvisioningTagMapInfoDTO> proTagMapInfoIt = provisioningTagMapInfos.iterator(); proTagMapInfoIt.hasNext();){
		     ProvisioningTagMapInfoDTO proTagMapInfo = (ProvisioningTagMapInfoDTO)proTagMapInfoIt.next();
		     ServiceFeatureInfoDTO serviceFeatureInfoDto =  new  ServiceFeatureInfoDTO();
                     serviceFeatureInfoDto.setServiceFeature(serviceFeature);
                     serviceFeatureInfoDto.setParameter(proTagMapInfo.getParameter());
                     ServiceFeatureInfoDTO serviceFeatureInfo = new ServiceFeatureInfoDAS().save(serviceFeatureInfoDto);
		   }

		   processChildren(serviceDto, serviceFeature, proTagMap);
						  
		 }
                   retValue= serviceFeature.getId();
    	      }

    	 catch (Exception e) {
    		LOG.error("Create exception creating service entity bean " + ServiceFeatureBL.class + e);
    	 }
    	return retValue;
    	
    }  

    public void processChildren(ServiceDTO service, ServiceFeatureDTO parent, ProvisioningTagMapDTO provTagMap) {

	

	try {
          ServiceFeatureDTO serviceFeatureDto =  new  ServiceFeatureDTO();
	  ServiceFeatureStatusDTO  status = new ServiceFeatureStatusDAS().find(SubscriptionConstants.SERVICE_FEATURE_STATUS_PENDING);

          Set<ProvisioningTagMapDTO> provTagMaps = provTagMap.getChildren();
          LOG.debug("ProvisioningTagMapDAS returned lists sizes are:" + provTagMaps.size());
          for(Iterator<ProvisioningTagMapDTO> proIt = provTagMaps.iterator(); proIt.hasNext();){

                   LOG.debug("Looping ProvisioningTagMapDTO");
                   ProvisioningTagMapDTO proTagMap =(ProvisioningTagMapDTO)proIt.next();
                   serviceFeatureDto.setProvisioningTagMap(proTagMap);
                   serviceFeatureDto.setServiceId(service);
                   serviceFeatureDto.setParent(parent);
                   serviceFeatureDto.setLevel(proTagMap.getLevel());
                   serviceFeatureDto.setDeleted(0);
		   serviceFeatureDto.setServiceFeatureStatus(status);
                   serviceFeature = new ServiceFeatureDAS().save(serviceFeatureDto);
                   List<ProvisioningTagMapInfoDTO> provisioningTagMapInfos  = proTagMap.getInfos();
                   for(Iterator<ProvisioningTagMapInfoDTO> proTagMapInfoIt = provisioningTagMapInfos.iterator(); proTagMapInfoIt.hasNext();){
                     ProvisioningTagMapInfoDTO proTagMapInfo = (ProvisioningTagMapInfoDTO)proTagMapInfoIt.next();
                     ServiceFeatureInfoDTO serviceFeatureInfoDto =  new  ServiceFeatureInfoDTO();
                     serviceFeatureInfoDto.setServiceFeature(serviceFeature);
                     serviceFeatureInfoDto.setParameter(proTagMapInfo.getParameter());
                     ServiceFeatureInfoDTO serviceFeatureInfo = new ServiceFeatureInfoDAS().save(serviceFeatureInfoDto);
                   }

                   processChildren(service, serviceFeature, proTagMap);

	 	}
        } catch (Exception e) {
                LOG.error("Create exception creating service entity bean " + ServiceFeatureBL.class + e);
        }
    }

    public List<ServiceFeatureDTO> getServiceFeaturesbyService(Integer serviceId){ 
            
      ServiceFeatureDAS serviceFeatureDas = new ServiceFeatureDAS();
      List<ServiceFeatureDTO> serviceFeatureDtoList = serviceFeatureDas.findServiceFeaturesbyService(serviceId);
      return serviceFeatureDtoList;
   }	

   public void updateAllServiceFeatureStatus(Integer serviceId, Integer newStatus) {
	
	ServiceFeatureDAS serviceFeatureDas = new ServiceFeatureDAS();
	ServiceFeatureStatusDTO  status = new ServiceFeatureStatusDAS().find(newStatus);
	List<ServiceFeatureDTO> features = getServiceFeaturesbyService(serviceId);
 	for (ServiceFeatureDTO feature : features) {
	  feature.setServiceFeatureStatus(status);	
	  serviceFeatureDas.save(feature);
	}
	
   }

   public void updateStatus(List<ServiceFeatureDTO> features, Integer newStatus) {

        ServiceFeatureStatusDTO  status = new ServiceFeatureStatusDAS().find(newStatus);
        for (ServiceFeatureDTO feature : features) {
          feature.setServiceFeatureStatus(status);
          serviceFeatureDas.save(feature);
        }

   }

}
