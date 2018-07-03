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
import com.sapienter.jbilling.server.device.UserDeviceBL;
import com.sapienter.jbilling.server.device.UserDeviceWS;
//import com.sapienter.jbilling.server.device.db.DeviceDAS;
//import com.sapienter.jbilling.server.device.db.DeviceDTO;
import com.sapienter.jbilling.server.device.db.UserDeviceDAS;
import com.sapienter.jbilling.server.device.db.UserDeviceDTO;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import in.saralam.sbs.server.subscription.db.ServiceDAS;
import in.saralam.sbs.server.subscription.db.ServiceDTO;
import com.sapienter.jbilling.server.util.Constants;
import in.saralam.sbs.server.subscription.ServiceFeatureBL;
import in.saralam.sbs.server.subscription.SubscriptionConstants;
import in.saralam.sbs.server.subscription.ServiceBL;
import com.sapienter.jbilling.server.user.db.UserDTO;
public class PricePlanTransititon implements IDeferredAction {
    
 private Integer orderId;
 private  Integer fromItemId;
 private Integer toItemId;
 private static final Logger LOG = Logger.getLogger(PricePlanTransititon.class);
 private Integer executorId;
 private Date requestDate;
 private  Date  scheduleDate;
 private Integer serviceId;
 private Integer entityId;
 private UserDTO  user;
 PricePlanTransititon priceObj;
  public  PricePlanTransititon(){
  }
 public  PricePlanTransititon(PricePlanTransititon priceObj){
	 this.priceObj=priceObj;
 }
 public void setOrderId(Integer orderId){
	 this.orderId=orderId;
 }
  public Integer getOrderId(){
	  return orderId;
  }
 public void setFromItemId(Integer fromItemId){
	 this.fromItemId=fromItemId;
 }
  public Integer getFromItemId(){
	  return fromItemId;
  }
 public void setToItemId(Integer toItemId){
	 this.toItemId=toItemId;
 }
  public Integer getToItemId(){
	  return toItemId;
  }
 public void  setExecutorId(Integer executorId){
	 this.executorId=executorId;
 }
  public Integer getToExecutorId(){
	  return executorId;
  }
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
	
    // userdevice
    ServiceDTO service  = new ServiceDAS().find(serviceId);
    UserDeviceBL userDeviceBl = new UserDeviceBL();
    UserDeviceWS userDevice = userDeviceBl.getUserDeviceByOrderAndLine(service.getOrderDTO().getId(), service.getOrderLineDTO().getId());
    LOG.debug(" userdevice first"+userDevice);	

    try {
        OrderDTO newOrderDTO = new OrderDAS().find(orderId);
        OrderLineDTO newLine = (OrderLineDTO) newOrderDTO.getLine(toItemId);
        if(userDevice != null && newLine != null) {
	  UserDeviceDAS userDeviceDas = new UserDeviceDAS();
	  UserDeviceDTO updateDevice = userDeviceDas.find(userDevice.getId());
          updateDevice.setOrderId(newOrderDTO);
          updateDevice.setOrderLineId(newLine);
	  //updateDevice.setExtId1(TDCSubscriberId);
          new UserDeviceDAS().makePersistent(updateDevice);

    	}
	if(newLine!=null){
	   ServiceDTO newService = new ServiceDAS().findByOrderLine(newLine.getId());
	   ServiceBL serviceBl= new ServiceBL();
	   //TO-DO call TDC Api to for real provisioning
           serviceBl.updateStatus(newService, Constants.SERVICE_STATUS_ACTIVE);
	   new ServiceFeatureBL().updateAllServiceFeatureStatus(newService.getId(), SubscriptionConstants.SERVICE_FEATURE_STATUS_PROVISIONED);

           //Delete old service
           service.setDeleted(1);
           new ServiceDAS().makePersistent(service);
	}
      } catch (Exception e) {
         LOG.error("Exception occured while Change Plan:Assign device " + e);
         e.printStackTrace();
      }

  }


}
