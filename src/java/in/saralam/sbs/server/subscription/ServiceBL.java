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
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

//import sun.jdbc.rowset.CachedRowSet;

import in.saralam.sbs.server.subscription.event.ChangePlanEvent;
import com.sapienter.jbilling.common.CommonConstants;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.device.UserDeviceBL;
import com.sapienter.jbilling.server.device.UserDeviceWS;
import com.sapienter.jbilling.server.device.db.DeviceDAS;
import com.sapienter.jbilling.server.device.db.DeviceDTO;
import com.sapienter.jbilling.server.device.db.UserDeviceDAS;
import com.sapienter.jbilling.server.device.db.UserDeviceDTO;
import com.sapienter.jbilling.server.item.ItemBL;
import com.sapienter.jbilling.server.item.ItemDecimalsException;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.item.db.ItemDAS;
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
import com.sapienter.jbilling.server.system.event.EventManager;
import com.sapienter.jbilling.server.util.audit.EventLogger;
import in.saralam.sbs.server.subscription.event.NewStatusEvent;
import in.saralam.sbs.server.subscription.db.ServiceStatusDAS;
//import com.sapienter.jbilling.server.service.db.ServiceBillingTypeDAS;
import in.saralam.sbs.server.subscription.db.ServiceAliasDAS;
import in.saralam.sbs.server.subscription.db.ServiceSiteDAS;
import in.saralam.sbs.server.subscription.db.ServiceAliasDTO;
import in.saralam.sbs.server.subscription.db.ServiceSiteDTO;
import in.saralam.sbs.server.subscription.db.ServiceDAS;
import in.saralam.sbs.server.subscription.db.ServiceDTO;
import in.saralam.sbs.server.subscription.db.ServiceStatusDTO;
import in.saralam.sbs.server.subscription.db.ServiceFeatureDTO;
import in.saralam.sbs.server.subscription.db.ServiceFeatureInfoDTO;
import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.system.event.EventManager;
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
import com.sapienter.jbilling.server.order.OrderBL;
import com.sapienter.jbilling.server.order.OrderLineBL;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderDAS;
import com.sapienter.jbilling.server.order.db.OrderStatusDAS;
import com.sapienter.jbilling.server.order.db.OrderStatusDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.order.db.OrderPeriodDTO;
import com.sapienter.jbilling.server.process.db.PeriodUnitDTO;

import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.util.PreferenceBL;
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;
import com.sapienter.jbilling.server.util.audit.EventLogger;
import com.sapienter.jbilling.server.util.db.CurrencyDAS;

/*import dk.tdc.spweb.ComtalkTDCApi;
import datatypes.serviceproviderweb.tdc.dk.SuspendSubscriberReplyInfo;
import datatypes.serviceproviderweb.tdc.dk.SuspendSubscriberRequestInfo;
import datatypes.serviceproviderweb.tdc.dk.StatusMessage;
import interfaces.sessions.serviceproviderweb.tdc.dk.*;
import datatypes.serviceproviderweb.tdc.dk.*;*/

//import in.saralam.sbs.server.deferredAction.DeferredActionSessionBean;
//import in.saralam.sbs.server.deferredAction.action.PricePlanTransititon;
//import in.saralam.sbs.server.deferredAction.action.IDeferredAction;
import in.saralam.sbs.server.deferredAction.IDeferredActionSessionBean;
import com.sapienter.jbilling.server.user.EntityBL;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import org.hibernate.ScrollableResults;
import com.sapienter.jbilling.server.device.db.DeviceDAS;
import com.sapienter.jbilling.server.device.DeviceBL;
//import in.saralam.sbs.server.billing.task.SBSBillingProcessFilterTask;
//import dk.comtalk.billing.server.notification.ComtalkNotificationBL;
import java.math.BigDecimal;
import java.util.ArrayList;
//import sun.jdbc.rowset.CachedRowSet;
import in.saralam.sbs.server.deferredAction.action.CancelSubscription;
import in.saralam.sbs.server.deferredAction.action.ChangeSubscription;

public class ServiceBL extends ResultList
        implements ServiceSQL {

    private ServiceDTO service = null;
    private ServiceDAS serviceDas = null;
	private ServiceAliasDTO servicealias = null;
    private ServiceAliasDAS servicealiasDas = null;
    private ServiceSiteDTO serviceSite = null;
    private ServiceSiteDAS serviceSiteDas = null;
	
    private static final Logger LOG = Logger.getLogger(ServiceBL.class);
    private EventLogger eLogger = null;
    public WebServicesSessionSpringBean WSSB = null;
    //public UserDeviceBL UBL = null;
public Integer userId;
    
    public ServiceBL(Integer serviceId) {
        init();
        set(serviceId);
    }

    public ServiceBL() {
        init();
    }

    public ServiceBL(ServiceDTO service) {
        init();
        this.service = service;
    }

    private void init() {
        eLogger = EventLogger.getInstance();
        serviceDas = new ServiceDAS();
    }

    public ServiceDTO getEntity() {
        return service;
    }

    public void set(Integer id) {
        service = serviceDas.find(id);
    }

    public void setForUpdate(Integer id) {
        service = serviceDas.findForUpdate(id);
    }
    
	public void set(ServiceDTO newService) {
        service = newService;
    }

    public ServiceWS getWS(Integer languageId) {
         ServiceWS retValue = new ServiceWS(service.getId(), service.getName(), service.getCreateDate(), service.getServiceStatus(),
				service.getOrderDTO(),
				service.getOrderLineDTO(),
				service.getDeleted(),
                		service.getBaseUserByUserId().getId());


        return retValue;
    }

    public ServiceDTO getDTO() {
        return service;
    }

    public void setUserId(Integer userId) {
	this.userId = userId;
    }

    public Integer getUserId() {
	return this.userId;
    }
    
    
    public Integer create(UserDTO user, OrderDTO order, OrderLineDTO orderLine){

		ServiceStatusDTO serviceStatus = null;
    	
    	Integer retValue = 0;
    	  if(user.equals(null) || order.equals(null)){
    	  LOG.debug("can't create service");
    	  return retValue;
    	  }
    	  try {

    		  OrderDTO orderDTO = new OrderDAS().find(order.getId());
    		  Integer entityId = user.getCompany().getId();
    		  LOG.debug("In create method");
    		  ServiceDTO serviceDTO = new ServiceDTO();

			  LOG.debug("creating service for provisionable item");
    		  serviceDTO.setBaseUserByUserId(user);
    	      serviceDTO.setLogin(user.getUserName());
    	      serviceDTO.setPassword(user.getPassword() == null ? new String("123qwe") : user.getPassword());
    	      serviceDTO.setCreateDate(order.getCreateDate());
    	      serviceDTO.setOrderDTO(orderDTO);
    	      serviceDTO.setOrderLineDTO(orderLine);
    	      serviceDTO.setName(orderLine.getItem().getDescription());
    	      serviceDTO.setServiceType(orderLine.getItem().getDescription());
    	      serviceDTO.setDeleted(0); //hard coded
    	    	//ServiceStatusDTO serviceStatus = new ServiceStatusDAS().find(Constants.SERVICE_STATUS_INACTIVE);

  			  Set<ItemTypeDTO> itemType = orderLine.getItem().getItemTypes();
  			  Iterator<ItemTypeDTO> itemIt = itemType.iterator();
  			  for (Iterator<ItemTypeDTO> iti = itemType.iterator(); iti.hasNext(); ) {
  		
  			    ItemTypeDTO itemTypeDTO = (ItemTypeDTO)iti.next();
			    String itemTypeDesc = itemTypeDTO.getDescription();
			    LOG.debug("item category is:"+itemTypeDesc);
			    if(itemTypeDesc.equals("SMS") || itemTypeDesc.equals("GPRS") || itemTypeDesc.equals("VOICE") ){
			    LOG.debug("service is of type SMS, GPRS, VOICE");
			    serviceDTO.setServiceType("SMS,VOICE,GPRS");
			    }
  	          }
			serviceStatus = new ServiceStatusDAS().find(Constants.SERVICE_STATUS_INACTIVE);
			 Date activeSince = new Date(order.getActiveSince().getYear(), order.getActiveSince().getMonth(),order.getActiveSince().getDate());
  			  Date today = new Date(new Date().getYear(), new Date().getMonth(), new Date().getDate());
  			 
  			OrderStatusDTO orderStatus = new OrderStatusDAS().find(Constants.ORDER_STATUS_ACTIVE);
  			if(order.getOrderStatus().getDescription().equalsIgnoreCase("Active")){
  				
  				if(activeSince.equals(today) || today.after(activeSince)){
  				
  				serviceStatus = new ServiceStatusDAS().find(Constants.SERVICE_STATUS_ACTIVE);
  			  }
			}	
    	      if(serviceStatus==null || serviceStatus.equals(null)){
    	        LOG.debug("serviceStatus is null");
    	      }
    	      LOG.debug("ServiceStatus is to be set");
    	      serviceDTO.setServiceStatus(serviceStatus);
    	    			//service.setServiceStatus(Constants.SERVICE_STATUS_INACTIVE);
    	      LOG.debug("service is created");
    	      service = new ServiceDAS().save(serviceDTO);
    	      LOG.debug("service details are saved");

    	      retValue= service.getId();
    	 }

    	 catch (Exception e) {
    		LOG.error("Create exception creating service entity bean " + ServiceBL.class + e);
    	 }
    	return retValue;
    	
    } 
	
	public Integer addAlias(Integer serviceId, String aliasname){
	
	LOG.debug("In  Alias serviceBL class");
    	Integer retValue = 0;
    	ServiceAliasDTO serviceAlias=null;
		
    	  if(serviceId.equals(null)){
    	  LOG.debug("can't create service");
    	  return retValue;
    	   }
		 ServiceDTO serviceDto = new ServiceDAS().find(serviceId);
		 Date today = new Date();
		 ServiceAliasDTO servicealiasdto = new ServiceAliasDTO();
		 servicealiasdto.setServiceId(serviceDto); 
		 servicealiasdto.setAliasName(aliasname); 
 		 servicealiasdto.setCreatedDate(today);
		 servicealiasdto.setLastUpdatedDate(today);
		 ServiceAliasDAS sadas =new ServiceAliasDAS();
		serviceAlias = sadas.save(servicealiasdto);
		 LOG.debug("ServiceAlias is created..." + serviceAlias);
		return serviceAlias.getId();
		
        }		
    
    	
	 public void deleteAlias(Integer aliasId){
	 LOG.debug("In  ServiceBL deleteAlias m/d id is " + aliasId);
 ServiceAliasDAS das = new  ServiceAliasDAS();
 ServiceAliasDTO dto = das.find(aliasId);
        dto.setDeleted(1);
       
   }
	
 	public List<ServiceAliasDTO> getServiceAliasbyService(Integer serviceId){ 
            
      		ServiceAliasDAS serviceAliasDas = new ServiceAliasDAS();
      		List<ServiceAliasDTO> serviceAliasDtoList = serviceAliasDas.findServiceAliasbyService(serviceId);
      		return serviceAliasDtoList;
   	}	

   
	//service Site address methods
	public Integer addSite(Integer serviceId, String siteAddr){

		LOG.debug("Adding new Site to Service : " + serviceId);
        	Integer retValue = 0;
        	ServiceSiteDTO serviceSite=null;

          	if(serviceId.equals(null)){
          		return retValue;
           	}
                ServiceDTO serviceDto = new ServiceDAS().find(serviceId);

                ServiceSiteDTO serviceSiteDto = new ServiceSiteDTO();
                serviceSiteDto.setServiceId(serviceDto);
                serviceSiteDto.setSiteAddr(siteAddr);

                ServiceSiteDAS sadas =new ServiceSiteDAS();
                serviceSite = sadas.save(serviceSiteDto);
                return serviceSite.getId();

        }

	public void deleteSite(Integer siteId){
 		ServiceSiteDAS das = new  ServiceSiteDAS();
 		ServiceSiteDTO dto = das.find(siteId);
        	dto.setDeleted(1);
   	}

        public List<ServiceSiteDTO> getServiceSitebyService(Integer serviceId){

      		ServiceSiteDAS servicieSiteDas = new ServiceSiteDAS();
      		List<ServiceSiteDTO> serviceSiteDtoList = servicieSiteDas.findServiceSitebyService(serviceId);
      		return serviceSiteDtoList;
   	}

	//device methods
	
	 public Integer addDevice(Integer serviceId, Integer deviceId){


                if(serviceId.equals(null)){
			LOG.debug("Invalid Service Id " + serviceId);
                        return null;
                }
		UserDeviceBL ubl = new UserDeviceBL();
                LOG.debug("Adding new Device " + deviceId + " for " + userId + " to subscription " + serviceId);

                return ubl.createUserDeviceByServiceId(serviceId, deviceId);

        }

	public void deleteDevice(Integer userDeviceId){
                UserDeviceDAS das = new  UserDeviceDAS();
                UserDeviceDTO dto = das.find(userDeviceId);
                dto.setDeleted(1);
        }

        public List<UserDeviceDTO> getUserDeviceByService(Integer serviceId){

                UserDeviceBL userDeviceBl = new UserDeviceBL();
                List<UserDeviceDTO> userDeviceList = userDeviceBl.getUserDeviceByServiceId(serviceId);
                return userDeviceList;
        }
	
	public void activateService(Integer serviceId) {
	
	//update service status as well.
         // service = new ServiceDAS().findByOrderLine(userDevice.getOrderLineId().getId());
		 ServiceDTO service = new ServiceDAS().find(serviceId);
          ServiceBL serviceBL = new ServiceBL();
          serviceBL.updateStatus(service, Constants.SERVICE_STATUS_ACTIVE);

	  //Update corresponding Order status to ORDER_STATUS_ACTIVE
	      LOG.debug("In ServiceBL activateService m/d serviceId " +serviceId);
	      
          OrderDTO order = service.getOrderDTO();
          OrderBL orderBl  = new OrderBL();
          orderBl.set(order);
          orderBl.setStatus(order.getBaseUserByCreatedBy().getId(), Constants.ORDER_STATUS_ACTIVE);
	}
	public void inactivateService(Integer serviceId) {
	
	//update service status as well.
         // service = new ServiceDAS().findByOrderLine(userDevice.getOrderLineId().getId());
		 LOG.debug("In ServiceBL inactivateService m/d serviceId " +serviceId);
		 ServiceDTO service = new ServiceDAS().find(serviceId);
          ServiceBL serviceBL = new ServiceBL();
          serviceBL.updateStatus(service, Constants.SERVICE_STATUS_INACTIVE);

	  //Update corresponding Order status to ORDER_STATUS_ACTIVE
	      	      
          OrderDTO order = service.getOrderDTO();
          OrderBL orderBl  = new OrderBL();
          orderBl.set(order);
          orderBl.setStatus(order.getBaseUserByCreatedBy().getId(), Constants.ORDER_STATUS_SUSPENDED);
	}
	
    public void updateStatus(ServiceDTO dto, Integer newStatus) {
    	
    	LOG.debug("in updatestatus method");
        // update first the service own fields
    	
    	if(dto.getStatusId().equals(newStatus)){
    		LOG.debug("Status are same");
    		return ;
    	}
    	LOG.debug("satus values aren't same");
    	ServiceStatusDTO serviceStatus = new ServiceStatusDAS().find(newStatus);
    	LOG.debug("serviceStatus returned");
    	dto.setServiceStatus(serviceStatus);
    	new ServiceDAS().save(dto);   
    	LOG.debug("sevice's status is updated");

    }
    
    public void cancelService(Integer serviceId){
    	LOG.debug("Canceling subscription");
    	ServiceDTO serviceDTO = new ServiceDAS().find(serviceId);
    	serviceDTO.setDeleted(1);
    	
    }
    
  
    public ServiceDTO getDTO(ServiceWS other) {
        ServiceDTO retValue = new ServiceDTO();
        retValue.setId(other.getId());
        retValue.setBaseUserByUserId(new UserDAS().find(other.getUserId()));
        retValue.setServiceStatus(new ServiceStatusDAS().find(other.getStatusId()));
        retValue.setCreateDate(other.getCreateDate());
        retValue.setDeleted(other.getDeleted());

        return retValue;
    }
    
    
    /** public RemoveSuspensionReplyInfo activateService(String subscriberId, String suspensionType){

	RemoveSuspensionReplyInfo reply = null;
        ServiceDTO service = null;
        UserDeviceBL userDeviceBl = new UserDeviceBL();
        final int SuccessfulCompletion = 0;
        try{

          ComtalkTDCApi tdcApi = new ComtalkTDCApi();
          //reply = tdcApi.removeSuspension(subscriberId, suspensionType, true);

        } catch(Exception e){
            LOG.debug("Exception calling remobve suspension method");
            e.printStackTrace();
        }

        StatusMessage status = reply.getStatusMessage();

        if (status.getReturnCode() == SuccessfulCompletion) {

          UserDeviceDTO userDevice = userDeviceBl.getEntityByExtId1(subscriberId);
          userDeviceBl.updateUserDeviceStatus(userDevice, Constants.USER_DEVICE_STATUS_ACTIVE);
          //update service status as well.
          service = new ServiceDAS().findByOrderLine(userDevice.getOrderLineId().getId());
          ServiceBL serviceBL = new ServiceBL();
          serviceBL.updateStatus(service, Constants.SERVICE_STATUS_ACTIVE);

	  //Update corresponding Order status to ORDER_STATUS_ACTIVE
          OrderDTO order = service.getOrderDTO();
          OrderBL orderBl  = new OrderBL();
          orderBl.set(order);
          orderBl.setStatus(order.getBaseUserByCreatedBy().getId(), Constants.ORDER_STATUS_ACTIVE);

        } else {

        }
      return reply;


     }
    
    public SuspendSubscriberReplyInfo inactivateService(String subscriberId, String suspensionType){

        SuspendSubscriberReplyInfo reply = null;
        ServiceDTO service = null;
	UserDeviceBL userDeviceBl = new UserDeviceBL();
	final int SuccessfulCompletion = 0;
        try{

          ComtalkTDCApi tdcApi = new ComtalkTDCApi();
		  
// Uncomment when really needs to communicate with TDC		  
		  
          //reply = tdcApi.suspendSubscriber(subscriberId, suspensionType, true);

        } catch(Exception e){
            LOG.debug("Exception calling inactivae method");
            e.printStackTrace();
        }

	StatusMessage status = reply.getStatusMessage();

        if (status.getReturnCode() == SuccessfulCompletion) {
	  UserDeviceDTO userDevice = userDeviceBl.getEntityByExtId1(subscriberId);
	  userDeviceBl.updateUserDeviceStatus(userDevice, Constants.USER_DEVICE_STATUS_INACTIVE);
	  //update service status as well.
	  service = new ServiceDAS().findByOrderLine(userDevice.getOrderLineId().getId());
	  ServiceBL serviceBL = new ServiceBL();
          serviceBL.updateStatus(service, Constants.SERVICE_STATUS_INACTIVE);

	  //Update corresponding Order status to ORDER_STATUS_SUSPENDED
	  OrderDTO order = service.getOrderDTO();
	  OrderBL orderBl  = new OrderBL();
	  orderBl.set(order);
	  orderBl.setStatus(order.getBaseUserByCreatedBy().getId(), Constants.ORDER_STATUS_SUSPENDED);

	
        } else {

        }
      return reply;
    }

    public boolean changeICC(String preICC, String newICC){

    	LOG.debug("In changeICC method of serviceBL");
    	boolean rep = true;
    	
    	try{
            ComtalkTDCApi tdcApi = new ComtalkTDCApi();
	    SubscriberInfo subscriber = tdcApi.searchSubscriberByICC(preICC, true);
	    
	    if(subscriber == null) {
	      LOG.debug("changeICC: subscriber not found "  );
	      return false;
	    }
	    String subscriberId = subscriber.getSubscriberId();
            
// Uncomment when really needs to communicate with TDC			
			
		//rep = tdcApi.changeICC(subscriberId, newICC, false); 
    	}
    	catch(Exception e){
    	    LOG.debug("Exception calling changeICC method");
      	    e.printStackTrace();
	    return false;
    	}
        return rep;
    }*/
    
     
    
    public boolean cancelSubscription(String subscriberId, Date reqDate){
    	boolean rep = true;
    	ServiceDTO service = new ServiceDAS().find(Integer.valueOf(subscriberId));
	UserDTO user = new UserDAS().find(service.getUserId());
	//updating  active until of the order
       ItemDTO fromItem = getPlanById(Integer.valueOf(subscriberId));
	Integer entityId = fromItem.getEntity().getId();
	  try{
             OrderDTO currentOrder = service.getOrderDTO();
	      UserBL userBl = new UserBL(currentOrder.getUserId());
             OrderBL bl = new OrderBL(currentOrder);
             LOG.debug(" calling  the update  active until");
	      EntityBL  entitybl= new   EntityBL();
	      Integer executorId =  entitybl.getRootUser(entityId);
	      bl.updateActiveUntil(executorId, reqDate, currentOrder);
	      LOG.debug(" called  the update  active until");
	      Integer languageId =userBl.getEntity().getLanguageIdField();
             //ComtalkNotificationBL comtalkNotificationBl= new ComtalkNotificationBL();
             OrderLineDTO newLine = (OrderLineDTO) currentOrder.getLine(fromItem.getId());
             String planName = newLine.getDescription();
	        if(service.getServiceStatus().getId()==Constants.SERVICE_STATUS_INACTIVE){
		    LOG.warn("This Subscription  " +subscriberId + " is already inactive" );
		    return true;
	     }
    	
	     IDeferredActionSessionBean local = (IDeferredActionSessionBean) Context.getBean(Context.Name.DEFERRED_ACTION_SESSION);
            CancelSubscription cancelPlan = new CancelSubscription();
	     LOG.debug(" req date"+reqDate);
            cancelPlan.setRequestDate(new Date());
            cancelPlan.setScheduleDate(reqDate);
            cancelPlan.setServiceId(Integer.valueOf(subscriberId));
            cancelPlan.setEntityId(entityId);
            cancelPlan.setBaseUser(user);
              if(reqDate.equals(new Date()) || reqDate.before(new Date()) ){
                 cancelPlan.execute();
            } 
               else {
      
                    local.create(cancelPlan,reqDate,entityId,user);
	  }
		
		 /*LOG.debug("calling email");
            boolean b = comtalkNotificationBl.sendPlanCancelEmail(entityId, currentOrder.getUserId(), languageId,
					planName,reqDate);
				LOG.debug("called email"+planName);*/
		 }
		  catch(Exception e){
    	    LOG.debug("Exception calling CancelSubscriber");
    	    e.printStackTrace();
    	  }
      return rep;
    }
        	 
    /* public UpdateSubscriberReplyInfo updateSubscriberAddService(String IMISDN, List<ServiceFeatureDTO> features ){
    	    
        UpdateSubscriberReplyInfo rep = null;
    	ComtalkTDCApi tdcApi = new ComtalkTDCApi();
	final int SuccessfulCompletion = 0;

    	try{	 
    	  
// Uncomment when really needs to communicate with TDC		  
		  
		  //rep = tdcApi.updateSubscriberAddService(IMISDN, features, false);
        } catch(Exception e){
    	  LOG.debug("Exception calling createsubscriber");
    	  e.printStackTrace();
        }
	
	StatusMessage status = rep.getStatusMessage();

        if (status.getReturnCode() == SuccessfulCompletion) {
          ServiceFeatureBL serviceFBL = new ServiceFeatureBL();
          serviceFBL.updateStatus(features, SubscriptionConstants.SERVICE_FEATURE_STATUS_PROVISIONED);

        } else {

        }

     	return rep;
     }
 
     public UpdateSubscriberReplyInfo updateSubscriberRemoveService(String MSISDN, List<ServiceFeatureDTO> features ){

        UpdateSubscriberReplyInfo rep = null;
	final int SuccessfulCompletion = 0;
        ComtalkTDCApi tdcApi = new ComtalkTDCApi();

        try{

// Uncomment when really needs to communicate with TDC		
		
          //rep = tdcApi.updateSubscriberRemoveService(MSISDN, features, true);

        } catch(Exception e){

          LOG.debug("Exception calling createsubscriber");
          e.printStackTrace();

        }

	StatusMessage status = rep.getStatusMessage();

        if (status.getReturnCode() == SuccessfulCompletion) {
          ServiceFeatureBL serviceFBL = new ServiceFeatureBL();
          serviceFBL.updateStatus(features, SubscriptionConstants.SERVICE_FEATURE_STATUS_DEPROVISIONED);

        } else {

        }

        return rep;
     }*/
	
     public ItemDTO getPlanById( Integer serviceId) {
       return new ServiceDAS().getPlanById(serviceId);
     }

     public void changeSubscriptionPlan(Integer subscriptionId, Integer fromItemId, Integer toItemId, Date changeDate) {
	 
	    ServiceDTO service  = new ServiceDAS().find(subscriptionId);
	    UserDTO user = new UserDAS().find(service.getUserId());
	    ItemDTO fromItem = getPlanById(subscriptionId);
	    Integer entityId = fromItem.getEntity().getId();
	     
		 ChangePlanEvent event = new ChangePlanEvent(entityId, user);
        EventManager.process(event);
		 
        OrderDTO currentOrder = service.getOrderDTO();
	    UserBL userBl = new UserBL(currentOrder.getUserId());
        OrderBL bl = new OrderBL(currentOrder);
        LOG.debug(" calling  the update  active until");
	    EntityBL  entitybl= new   EntityBL();
	    Integer executorId =  entitybl.getRootUser(entityId);
        
		IDeferredActionSessionBean local = (IDeferredActionSessionBean) Context.getBean(Context.Name.DEFERRED_ACTION_SESSION);
            ChangeSubscription changePlan = new ChangeSubscription();
	        LOG.debug(" changeDate date"+changeDate);
            changePlan.setRequestDate(new Date());
            changePlan.setScheduleDate(changeDate);
            changePlan.setServiceId(subscriptionId);
            changePlan.setEntityId(entityId);
            changePlan.setBaseUser(user);
		   	changePlan.setExecutorId(executorId);
			changePlan.setFromItemId(fromItemId);
			changePlan.setToItemId(toItemId);
		
		
   
	        if(changeDate.equals(new Date()) || changeDate.before(new Date()) ){
                changePlan.execute();
	     
            } 
                else {      
                    local.create(changePlan,changeDate,entityId,user);
	             }
       
       /*OrderDTO order = new OrderDTO();
       Integer orderId = null;
       ServiceDTO service  = new ServiceDAS().find(subscriptionId);
       ItemDTO fromPlan = new ItemDAS().find(fromItemId);
       ItemDTO toPlan = new ItemDAS().find(toItemId);
       ItemDTO taxItem = null;
       OrderDTO currentOrder = service.getOrderDTO();
       LOG.debug(" order id"+currentOrder.getId());
       UserBL user = new UserBL(currentOrder.getUserId());
       String TDCSubscriberId = null;
       
       Date activeUntil = currentOrder.getActiveUntil();

       if(activeUntil == null) {
         activeUntil = currentOrder.getNextBillableDay(); 
       }

       if(activeUntil == null) {

         OrderPeriodDTO orderPeriod = currentOrder.getOrderPeriod();
         PeriodUnitDTO unit = orderPeriod.getPeriodUnit();
         Integer unitNum = unit.getId();
	 Integer value = orderPeriod.getValue();
    	 Date activeSince = currentOrder.getActiveSince();

         Calendar c = Calendar.getInstance();
         c.setTime(activeSince);

         switch (unitNum) {
           case 1:
               c.add(Calendar.MONTH, value);
               activeUntil = c.getTime();
           
             break;

           case 2:

            c.add(Calendar.WEEK_OF_MONTH, value);
            activeUntil = c.getTime();

            break;

           case 3:

            c.add(Calendar.DATE, value);
            activeUntil = c.getTime();
            break;

           case 4:

            c.add(Calendar.YEAR, value);
            activeUntil = c.getTime();

            break;

           default:
	    break;
	}       
      }
	 
       OrderLineDTO line = (OrderLineDTO) currentOrder.getLine(fromItemId); 
       String oldPlanName = line.getDescription();
       String notes= "Plan Change From "+ oldPlanName;
       OrderBL orderBl = new OrderBL();
       //create a new order
       OrderDTO newOrder = new OrderDTO();
       newOrder.setActiveSince(activeUntil);
       newOrder.setCreateDate(new Date());
       newOrder.setOrderPeriod(currentOrder.getOrderPeriod());
       newOrder.setOrderBillingType(currentOrder.getOrderBillingType());
       newOrder.setBaseUserByUserId(currentOrder.getBaseUserByUserId());
       newOrder.setCurrency(currentOrder.getCurrency());
	
       Date cycleStart = currentOrder.getCycleStarts();
       if(cycleStart != null) {
         Calendar ctemp= Calendar.getInstance();
         ctemp.setTime(cycleStart);
         Integer dayOfMonth = ctemp.get(Calendar.DAY_OF_MONTH);
         Calendar ca = Calendar.getInstance();
         ca.setTime(activeUntil);
         ca.set(Calendar.DAY_OF_MONTH, dayOfMonth);
         cycleStart  = ca.getTime();

         LOG.debug("active until date is "+ activeUntil);
       }

       newOrder.setCycleStarts(cycleStart);
       newOrder.setDueDateUnitId(currentOrder.getDueDateUnitId());
       newOrder.setDueDateValue(currentOrder.getDueDateValue());
       newOrder.setBaseUserByCreatedBy(currentOrder.getBaseUserByCreatedBy());
       newOrder.setNotes(notes);
       Integer executorId = new EntityBL().getRootUser((user.getEntity().getEntity().getId()));
       new OrderLineBL().addItem(newOrder, toItemId, line.getQuantity());
       for(OrderLineDTO orderLine :  currentOrder.getLines()) {

	 if (orderLine.getOrderLineType().getId() == Constants.ORDER_LINE_TYPE_TAX) {

	   LOG.debug("this order line is of type Tax"+orderLine.getId());

           taxItem = orderLine.getItem();
           //taxTotal = getTotalForTax(order.getLines(), taxItem.getExcludedTypes());
	   break;

         } 
       }

       if(taxItem != null) {
    	 BigDecimal balance = BigDecimal.ZERO;
         for (OrderLineDTO orderLine : newOrder.getLines()){
               balance = balance.add(orderLine.getAmount());
         }
         balance =  balance.multiply(taxItem.getPercentage().divide(new BigDecimal(100)));
           
         new OrderLineBL().addItem(newOrder, taxItem.getId(), 1);
         newOrder.getLine(taxItem.getId()).setAmount(balance);
       }


       try {
         orderId = orderBl.create(user.getEntity().getEntity().getId(),executorId, newOrder);
	 OrderBL bl = new OrderBL(currentOrder);

         bl.updateActiveUntil(user.getEntity().getId(), activeUntil, newOrder);

       } catch (Exception e) {

	 LOG.error("Exception occured while Change Plan " + e);
	 e.printStackTrace();
	 return;

       }

       LOG.debug("active until date is "+activeUntil);
       Calendar cal = Calendar.getInstance();
       cal.setTime(activeUntil);  
       cal.set(Calendar.HOUR_OF_DAY, 0);  
       cal.set(Calendar.MINUTE, 0);  
       cal.set(Calendar.SECOND, 0);  
       cal.set(Calendar.MILLISECOND, 0); 
       Date  activeUntilAfter = cal.getTime();

       IDeferredActionSessionBean local = (IDeferredActionSessionBean) Context.getBean(Context.Name.DEFERRED_ACTION_SESSION);

       PricePlanTransititon pricePlan = new PricePlanTransititon();

       Integer entityId=user.getEntity().getEntity().getId();
       pricePlan.setOrderId(orderId);

       pricePlan.setFromItemId(fromItemId);
       pricePlan.setToItemId(toItemId);
       pricePlan.setExecutorId(executorId);
       pricePlan.setRequestDate(new Date());
       pricePlan.setScheduleDate(activeUntilAfter);
       pricePlan.setBaseUser(service.getBaseUserByUserId());
       pricePlan.setServiceId(subscriptionId);
       pricePlan.setEntityId(entityId);
       //IDeferredAction iDefredAction= pricePlan ;
       local.create(pricePlan);
	     
       Integer languageId = user.getEntity().getLanguageIdField();
      // ComtalkNotificationBL comtalkNotificationBl= new ComtalkNotificationBL();
       
       OrderDTO newOrderDTO = new OrderDAS().find(orderId);
       OrderLineDTO newLine = (OrderLineDTO) newOrderDTO.getLine(toItemId);
       String newPlanName = newLine.getDescription();
       BigDecimal newCharge = newLine.getAmount();
	   
	   // Creating ServiceAlias for Newly created Subscription with old AliasName
	   ServiceAliasDTO serviceAliasDto = new ServiceAliasDAS().findByService(service.getId());
	    if(serviceAliasDto == null) {
		LOG.debug("This Service "+ service.getId() + " has No ServiceAlias "); 
		}  else { 
            	String oldaliasName = serviceAliasDto.getAliasName();
	             LOG.debug("OrderId in ChagePlan" + orderId);
				 ServiceDTO sDto = new ServiceDAS().findByOrder(orderId);
				 Integer newServiceId = sDto.getId();
				 Integer newServiceAliasId = addAlias(newServiceId, oldaliasName);
			     serviceAliasDto.setDeleted(1);
			     LOG.debug("NewServiceAliasId "+ newServiceAliasId +" created for this Service "+ newServiceId + " oldServiceAlias " + oldaliasName );
			 }
	   
      /* try{
         boolean b = comtalkNotificationBl.sendPlanChangeEmail(entityId, currentOrder.getUserId(), languageId,
					oldPlanName, newPlanName, activeUntil, newCharge);
       } catch (Exception e) {
	 LOG.debug("email exception...");
	 e.printStackTrace();
       }*/

       
       //provision to TDC
      /*UserDeviceBL userDeviceBl = new UserDeviceBL();
      UserDeviceWS userDevice = userDeviceBl.getUserDeviceByOrderAndLine(service.getOrderDTO().getId(), service.getOrderLineDTO().getId());
         LOG.debug(" userdevice"+userDevice);
      UpdateSubscriberReplyInfo rep = null;
      final int SuccessfulCompletion = 0;
     // ComtalkTDCApi tdcApi = new ComtalkTDCApi();

      /*try{

        rep = tdcApi.updateSubscriberChangePlan(userDevice.getTelephoneNumber(), service, true);

      } catch(Exception e){

        LOG.debug("Exception calling createsubscriber");
        e.printStackTrace();

      }

      StatusMessage status = rep.getStatusMessage();

      if (status.getReturnCode() == SuccessfulCompletion) {

        updateStatus(service, Constants.SERVICE_STATUS_ACTIVE);
        new ServiceFeatureBL().updateAllServiceFeatureStatus(service.getId(), SubscriptionConstants.SERVICE_FEATURE_STATUS_PROVISIONED);
        TDCSubscriberId = rep.getSubscriberId();

      } else {
	return;

      }

    
	  //updateDevice.setExtId1(TDCSubscriberId);
          new UserDeviceDAS().makePersistent(updateDevice);
        }
      } catch (Exception e) {
         LOG.error("Exception occured while Change Plan:Assign device " + e);
         e.printStackTrace();
      }*/

 //  } */

	   }    

			public void activateServices() {
		LOG.debug("activate servicesssss.............");
		Date todayDate = new Date();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(todayDate);
		LOG.debug("today date string is "+date);
		List<ServiceDTO> serviceList = new ServiceDAS().findByActiveServices(Constants.SERVICE_STATUS_INACTIVE);
		for(ServiceDTO service : serviceList){
			String AS = new SimpleDateFormat("yyyy-MM-dd").format(service.getOrderDTO().getActiveSince());
			LOG.debug("AS date string is "+AS);
			if(date.equalsIgnoreCase(AS)){
				LOG.debug("activated service is "+service.getId());
				new ServiceBL().activateService(service.getId());
				LOG.debug("service activated...");
			}
		}
	}
	
	public void inActivateServices() {
		LOG.debug("in activate servicesssss.............");
		try{
		List<ServiceDTO> serviceList = new ServiceDAS().findToDeactivateServices();
		LOG.debug("inactive services are "+serviceList);
		for(ServiceDTO service : serviceList){
			LOG.debug("order status is "+service.getOrderDTO().getOrderStatus().getDescription());
			LOG.debug("service is "+service);
			new ServiceBL().updateStatus(service, Constants.SERVICE_STATUS_INACTIVE);
			LOG.debug("service status deactivated");
		}
		}catch (Exception e) {
			LOG.warn("error deactivating finished services "+e);
		}
	}

	public List<ServiceWS> getAllSubscriptions(){
		if(this.userId == null ) {
			return null;
		}

		List<ServiceDTO> serviceDtoList = new ServiceDAS().findSubscriptionsByUser(this.userId);
		if(serviceDtoList == null || serviceDtoList.size() == 0) {
			return null;
		}
		List<ServiceWS> subsList = new ArrayList<ServiceWS>();
		for(ServiceDTO serviceDto : serviceDtoList) {
			ServiceWS sub = new ServiceWS(serviceDto);
			subsList.add(sub);
		}
		
		return subsList;
		
	}
}

