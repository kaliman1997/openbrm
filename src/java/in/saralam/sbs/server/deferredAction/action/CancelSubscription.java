package in.saralam.sbs.server.deferredAction.action;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.log4j.Logger;
import com.sapienter.jbilling.server.order.db.OrderDAS;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.OrderBL;
//import com.sapienter.jbilling.server.device.UserDeviceBL;
//import com.sapienter.jbilling.server.device.UserDeviceWS;
//import com.sapienter.jbilling.server.device.db.DeviceDAS;
//import com.sapienter.jbilling.server.device.db.DeviceDTO;
//import com.sapienter.jbilling.server.device.db.UserDeviceDAS;
//import com.sapienter.jbilling.server.device.db.UserDeviceDTO;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import in.saralam.sbs.server.subscription.db.ServiceDAS;
import in.saralam.sbs.server.subscription.db.ServiceDTO;
import com.sapienter.jbilling.server.util.Constants;
import in.saralam.sbs.server.subscription.ServiceFeatureBL;
import in.saralam.sbs.server.subscription.SubscriptionConstants;
import in.saralam.sbs.server.subscription.ServiceBL;
import com.sapienter.jbilling.server.util.Constants;
import in.saralam.sbs.server.subscription.db.ServiceStatusDAS;
import com.sapienter.jbilling.server.user.db.UserDTO;


public class CancelSubscription implements IDeferredAction {
    
 /*private Integer orderId;
 private  Integer fromItemId;
 private Integer toItemId;*/
 private static final Logger LOG = Logger.getLogger(CancelSubscription.class);
 //private Integer executorId;
 private Date requestDate;
 private  Date  scheduleDate;
 private Integer serviceId;
 private Integer entityId;
 private UserDTO  user;
CancelSubscription priceObj;
  public CancelSubscription(){
  }
 public  CancelSubscription(CancelSubscription  priceObj){
	 this.priceObj=priceObj;
 }
 
 /*
 public void  setExecutorId(Integer executorId){
	 this.executorId=executorId;
 }
  public Integer getToExecutorId(){
	  return executorId;
  }*/
  public void setRequestDate(Date requestDate){
	 this.requestDate=requestDate;
 }
   public Date getRequestDate(){
	  return requestDate;
  }
 public void setScheduleDate(Date scheduleDate){
	 this.scheduleDate=scheduleDate;
 }
  public Date getScheduleDate(){
	  return scheduleDate;
  }
 public void setServiceId(Integer  serviceId){
	 this.serviceId=serviceId;
 }
 public Integer getServiceId(){
	  return serviceId;
  }

  public void setEntityId(Integer entityId){
	 this.entityId=entityId;
 }
 public Integer getEntityId(){
	  return entityId;
  }
   public void setBaseUser(UserDTO  user){
	 this.user=user;
 }
 public UserDTO getBaseUser(){
	  return user;
  }


  @Override
  public void execute() {
	
   	

    try {
		   boolean  rep;
           ServiceDTO service  = new ServiceDAS().find(serviceId);
		   OrderDTO orderDto=service.getOrderDTO();
		   LOG.debug("OrderDTO id"+orderDto.getId());
		   OrderLineDTO orderLineDto =service.getOrderLineDTO();
            LOG.debug("Orderline id"+orderLineDto.getId());
		   //String subscriberId= Integer.toString(serviceId);
		     LOG.debug(" user ID"+user.getId());
           //UserDeviceDTO userDeviceDto = new UserDeviceDAS().find(user.getId());
		  //UserDeviceDTO userDeviceDto =  new UserDeviceDAS().getUserDeviceByUserAndOrderAndOrderLine(user.getId(),orderDto.getId(),orderLineDto.getId());

          // String msisdn= userDeviceDto.getTelephoneNumber();
		    //LOG.debug(" msisdn"+ msisdn);
          /* ComtalkTDCApi tdcApi = new ComtalkTDCApi();
    	   LOG.debug("ComtalkTDCApi object is created");
    	  try{	 
    		 rep = tdcApi.cancelSubscriber( subscriberId, scheduleDate, false,msisdn);
    		 LOG.error("CancelSubscriber method is called:"+rep);
    	     if(rep){
          //  for making  subscription to inactive
		  LOG.debug("Canceling subscription to inactive");
       // service.setServiceStatus(new ServiceStatusDAS().find(
                    Constants.SERVICE_STATUS_INACTIVE));
          // new ServiceDAS().makePersistent(service);
		   ServiceBL serviceBl = new ServiceBL();
		    serviceBl.updateStatus(service,Constants.SERVICE_STATUS_INACTIVE);
		    LOG.debug("Canceled subscription to inactive");
       //  for deleting the   subscription 
        // LOG.debug("Canceling subscription");
    	//ServiceDTO serviceDTO = new ServiceDAS().find(serviceId);
    	//serviceDTO.setDeleted(1);
       // LOG.debug("Canceled subscription");
            }
		  }
    	  catch(Exception e){
    	    LOG.debug("Exception calling CancelSubscriber");
    	    e.printStackTrace();
    	  }
		  
    	}*/
	       ServiceBL serviceBl = new ServiceBL();
		    serviceBl.updateStatus(service,Constants.SERVICE_STATUS_INACTIVE);
		    LOG.debug("Canceled subscription to inactive");
	}
       catch (Exception e) {
         LOG.error("Exception occured while cancelling the Plan " + e);
         e.printStackTrace();
      }

 }


}
