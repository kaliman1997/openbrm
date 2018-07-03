package in.saralam.sbs.server.deferredAction.action;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Calendar;
import java.util.List;
import org.apache.log4j.Logger;
import com.sapienter.jbilling.server.order.db.OrderDAS;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.OrderBL;
import com.sapienter.jbilling.server.device.UserDeviceBL;
import com.sapienter.jbilling.server.device.UserDeviceWS;
import com.sapienter.jbilling.server.device.db.DeviceDAS;
import com.sapienter.jbilling.server.device.db.DeviceDTO;
import com.sapienter.jbilling.server.device.db.UserDeviceDAS;
import com.sapienter.jbilling.server.device.db.UserDeviceDTO;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.service.db.ServiceDAS;
import com.sapienter.jbilling.server.service.db.ServiceDTO;
import com.sapienter.jbilling.server.util.Constants;
//import com.sapienter.jbilling.server.service.ServiceFeatureBL;
//import com.sapienter.jbilling.server.service.SubscriptionConstants;
//import com.sapienter.jbilling.server.service.ServiceBL;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.service.db.ServiceStatusDAS;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.user.db.UserStatusDAS;
import com.sapienter.jbilling.server.user.UserDTOEx;
import in.saralam.sbs.server.pricing.db.PurchasedBundleDAS;
import in.saralam.sbs.server.pricing.db.PurchasedBundleDTO;
import in.saralam.sbs.server.pricing.db.BundleStatusDTO;
import in.saralam.sbs.server.pricing.db.BundleStatusDAS;

//import in.saralam.sbs.server.pricing.db.PackagePriceDTO;
//import in.saralam.sbs.server.pricing.db.PackagePriceDAS;
//import in.saralam.sbs.server.pricing.db.PackageProductDAS;
//import in.saralam.sbs.server.pricing.db.PackageProductDTO;
import in.saralam.sbs.server.pricing.db.PricePackageDAS;
import in.saralam.sbs.server.pricing.db.PricePackageDTO;
import com.sapienter.jbilling.server.user.EntityBL;
import in.saralam.sbs.server.pricing.db.PurchasedBundleProductDTO;
import in.saralam.sbs.server.pricing.db.PurchasedBundleProductDAS;
import in.saralam.sbs.server.deferredAction.IDeferredActionSessionBean;
import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.order.db.OrderProcessDAS;
//import in.saralam.sbs.server.pricing.db.PackagePriceTypeDTO;
//import in.saralam.sbs.server.pricing.db.PackagePriceTypeDAS;
import in.saralam.sbs.server.pricing.PurchasedBundleBL;


/**
*@aouthor Shashi
*@since 09/03/2015
*/
public class CancelBundle implements IDeferredAction {
    
 
  private static final Logger LOG = Logger.getLogger(CancelBundle.class);
  //private Integer executorId;
  private Date requestDate;
  private  Date  scheduleDate;
  private Integer purchaseId;
  private Integer entityId;
  private UserDTO  user;
  CancelBundle bundleObj;
 
  public CancelBundle(){
  }
 
  public  CancelBundle(CancelBundle  bundleObj){
	 this.bundleObj=bundleObj;
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
 public void setPurchaseId(Integer  purchaseId){
	 this.purchaseId=purchaseId;
 }
 public Integer getPurchaseId(){
	  return purchaseId;
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
	Boolean  retValue =  true;
	Integer oneOffOrderId=null;
	Integer recurringOrderId=null; 
	Integer cancellationOrderId=null; 
	PurchasedBundleBL purchaseBundleBL = new PurchasedBundleBL();
	  
	try {	
		LOG.debug(" Cancelling bundle's purchase  id"+purchaseId);
		PurchasedBundleDTO purchasedBundle = new PurchasedBundleDAS().find(purchaseId);
		UserDTO  user = purchasedBundle.getUserDto();
		EntityBL  entitybl = new   EntityBL();
		Integer executorId =  entitybl.getRootUser(user.getCompany().getId());
		
		
		PricePackageDTO packageDTO = new PricePackageDAS().find(purchasedBundle.getBundleId());		
		Date bundleCreatedDate = new Date(purchasedBundle.getCreatedDateTime().getTime());
		
		Date objectiveDate = bundleCreatedDate;
		//Objective Date = Bundle Created Date + MBG days
		if(packageDTO.getMbgDays() != null){
			Calendar cal = Calendar.getInstance();  
			cal.setTime(bundleCreatedDate);  
			cal.add(Calendar.DATE, packageDTO.getMbgDays());    
			objectiveDate = cal.getTime();
		}
		
		List<PurchasedBundleProductDTO> purchasedBundleProductDTO  = new PurchasedBundleProductDAS().findByPurchaseId(purchaseId);	
		
			
		for(PurchasedBundleProductDTO dto:purchasedBundleProductDTO){
			
			OrderDTO currentOrder = null;
			BigDecimal orderTotal = BigDecimal.ZERO;
			Integer currentOrderId = null;
			/** We have to create a -ve order with the order total which already included discount.*/
			BigDecimal discount = BigDecimal.ZERO;
			
			if(objectiveDate.compareTo(scheduleDate) >= 0){
				
				LOG.debug(" Bundle is getting cancelled with in MBG days");				
				oneOffOrderId = dto.getOneOffOrderId();				
				
				if(oneOffOrderId != 0) {
					LOG.debug(" PurchasedBundleProduct is onetime and order is " +oneOffOrderId);
					
					// Creating one time -ve order for refund			
					currentOrder=new OrderDAS().find(oneOffOrderId);
					currentOrderId = purchaseBundleBL.createBundleOrder(dto.getProductId(),scheduleDate,null,Constants.ORDER_PERIOD_ONCE,
										Constants.ORDER_BILLING_PRE_PAID,Constants.ORDER_LINE_TYPE_PENALTY,discount,currentOrder.getTotal().negate(),user);					
					//	Cancelling the purchasedBundleOrder and purchasedBundle also
					Boolean oneOrderRetValue = purchaseBundleBL.updateBundleOrderAndCancelBundle(oneOffOrderId,executorId,scheduleDate,purchaseId,user);					
				}
				recurringOrderId = dto.getRecurringOrderId();
				
				if(recurringOrderId!=0){
					LOG.debug(" PurchasedBundleProduct is recurring and order id is " +recurringOrderId);
					// Creating -ve order for recurring order with amount which was billed i.e for recurring get amount of all invoices billed.
					
					currentOrder = new OrderDAS().find(recurringOrderId);
					List<Integer> invoiceCount = new OrderProcessDAS().findActiveInvoicesForOrder(recurringOrderId);
					
					orderTotal = currentOrder.getTotal().multiply(BigDecimal.valueOf(invoiceCount.size()));
					
					currentOrderId = purchaseBundleBL.createBundleOrder(dto.getProductId(),scheduleDate,null,Constants.ORDER_PERIOD_ONCE,
										Constants.ORDER_BILLING_PRE_PAID,Constants.ORDER_LINE_TYPE_PENALTY,discount,orderTotal.negate(),user);
					
					//	Cancelling the purchasedBundleOrder and purchasedBundle also
					Boolean oneOrderRetValue = purchaseBundleBL.updateBundleOrderAndCancelBundle(recurringOrderId,executorId,scheduleDate,purchaseId,user);					
					
				}				
				
				/** If cancel item order is valid till the scheduled cancel date then only create cancellation order */
				if(dto.getCancelCharge()!=null && (dto.getCancelEndTime()==null || (dto.getCancelEndTime().compareTo(scheduleDate)>=0))){
					LOG.debug(" Creating cancellation order with in MBG ");
										
					orderTotal = dto.getCancelCharge();
					currentOrderId = purchaseBundleBL.createBundleOrder(dto.getProductId(),scheduleDate,null,Constants.ORDER_PERIOD_ONCE,
										Constants.ORDER_BILLING_PRE_PAID,Constants.ORDER_LINE_TYPE_PENALTY,discount,orderTotal,user);
				}
			}else {
				LOG.debug("Bundle is gonna be cancelled after MBG days");
				oneOffOrderId = dto.getOneOffOrderId();
				if(oneOffOrderId != 0) {
					Boolean oneOrderRetValue = purchaseBundleBL.updateBundleOrderAndCancelBundle(oneOffOrderId,executorId,scheduleDate,purchaseId,user);					
				}
				
				recurringOrderId = dto.getRecurringOrderId();
				if(recurringOrderId!=0){
					Boolean oneOrderRetValue = purchaseBundleBL.updateBundleOrderAndCancelBundle(recurringOrderId,executorId,scheduleDate,purchaseId,user);					
				}				
				
				/** If cancel item order is valid till the scheduled cancel date then only create cancellation order */
				if(dto.getCancelCharge()!=null && (dto.getCancelEndTime()==null || (dto.getCancelEndTime().compareTo(scheduleDate)>=0))){
					LOG.debug(" Creating cancellation order outta MBG ");
					//discount = dto.getCancelDiscount();
					orderTotal = dto.getCancelCharge();
					currentOrderId = purchaseBundleBL.createBundleOrder(dto.getProductId(),scheduleDate,null,Constants.ORDER_PERIOD_ONCE,
										Constants.ORDER_BILLING_PRE_PAID,Constants.ORDER_LINE_TYPE_PENALTY,discount,orderTotal,user);
				}			
			}
		}
	} catch( Exception e){
			e.printStackTrace();
	} 
  }
}
