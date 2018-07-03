/*
 * SARALAM CONFIDENTIAL
 * _____________________
 *
 * [2012] - [4000] Enterprise Saralam Open IT Services
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Enterprise Saralam Software.
 * The intellectual and technical concepts contained
 * herein are proprietary to Enterprise Saralam Software
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden.
 */



package in.saralam.sbs.server.pricing;

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
import com.sapienter.jbilling.server.mediation.Record;
import com.sapienter.jbilling.server.notification.INotificationSessionBean;
import com.sapienter.jbilling.server.notification.MessageDTO;
import com.sapienter.jbilling.server.notification.NotificationBL;
import com.sapienter.jbilling.server.notification.NotificationNotFoundException;
import com.sapienter.jbilling.server.order.OrderLineWS;
import com.sapienter.jbilling.server.order.OrderBL;
import com.sapienter.jbilling.server.order.db.OrderBillingTypeDAS;
import com.sapienter.jbilling.server.order.db.OrderDAS;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDAS;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.order.db.OrderLineTypeDAS;
import com.sapienter.jbilling.server.order.db.OrderLineTypeDTO;
import com.sapienter.jbilling.server.order.db.OrderPeriodDAS;
import com.sapienter.jbilling.server.order.db.OrderPeriodDTO;
import com.sapienter.jbilling.server.order.db.OrderProcessDTO;
import com.sapienter.jbilling.server.order.db.OrderProcessDAS;
import com.sapienter.jbilling.server.order.db.OrderStatusDAS;
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
import in.saralam.sbs.server.pricing.db.PackagePriceDTO;
import in.saralam.sbs.server.pricing.db.PackagePriceDAS;
import in.saralam.sbs.server.pricing.db.PackageProductDAS;
import in.saralam.sbs.server.pricing.db.PackageProductDTO;
import in.saralam.sbs.server.pricing.db.PricePackageDAS;
import in.saralam.sbs.server.pricing.db.PricePackageDTO;
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
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import javax.sql.rowset.CachedRowSet;
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.sapienter.jbilling.server.item.db.ItemPriceDAS;
import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.item.db.ItemTypeDTO;
import com.sapienter.jbilling.server.item.db.ItemDAS;

import in.saralam.sbs.server.pricing.db.BundleStatusDTO;
import in.saralam.sbs.server.pricing.db.BundleStatusDAS;
import in.saralam.sbs.server.pricing.db.PricePackageDTO;
import in.saralam.sbs.server.pricing.db.PricePackageDAS;
import in.saralam.sbs.server.pricing.db.PackageProductDTO;
import in.saralam.sbs.server.pricing.db.PackagePriceDTO;
import in.saralam.sbs.server.pricing.db.PackagePriceTypeDTO;
import in.saralam.sbs.server.pricing.db.PackagePriceTypeDAS;
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
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import in.saralam.sbs.server.pricing.PackageProductWS;
import in.saralam.sbs.server.pricing.PricePackageWS;
import in.saralam.sbs.server.pricing.db.PurchasedBundleDAS;
import in.saralam.sbs.server.pricing.db.PurchasedBundleDTO;
import in.saralam.sbs.server.pricing.db.PurchasedBundleProductDTO;
import in.saralam.sbs.server.pricing.db.PurchasedBundleProductDAS;
import in.saralam.sbs.server.pricing.PurchasedBundleWS;
import com.sapienter.jbilling.server.order.db.OrderStatusDTO;
import com.sapienter.jbilling.server.user.db.UserStatusDTO;

import com.sapienter.jbilling.server.order.OrderWS;
import in.saralam.sbs.server.deferredAction.DeferredActionSessionBean;
import in.saralam.sbs.server.deferredAction.action.CancelBundle;
import in.saralam.sbs.server.deferredAction.action.IDeferredAction;
import in.saralam.sbs.server.deferredAction.IDeferredActionSessionBean;
import com.sapienter.jbilling.server.user.EntityBL;
import org.hibernate.ScrollableResults;
//import in.saralam.sbs.server.billing.task.SBSBillingProcessFilterTask;
import in.saralam.sbs.server.deferredAction.action.CancelBundle;

public class PurchasedBundleBL {
	private static final Logger LOG = Logger.getLogger(PurchasedBundleBL.class);
	private EventLogger eLogger = null;     
	
	public  Integer  createPurchaseBundle(PurchasedBundleWS purchasedBundleWS){
		LOG.debug(" Creating purchaseBundle ");
		PurchasedBundleDTO  dto =  new PurchasedBundleDTO();
		BundleStatusDTO  bundleStatusDTO  =  new BundleStatusDAS().find(Constants.BUNDLE_STATUS_ACTIVE);
		
		dto.setBundleId(purchasedBundleWS.getBundleId());
		dto.setStatusId(bundleStatusDTO);
		dto.setValidFrom(purchasedBundleWS.getValidFrom());
		dto.setValidTo(purchasedBundleWS.getValidTo());
		dto.setCreatedDateTime(purchasedBundleWS.getCreatedDateTime());
		dto.setUserDto(purchasedBundleWS.getUserDto());
		
		PurchasedBundleDTO purchaseDto = new PurchasedBundleDAS().save(dto);
		LOG.debug("Purchase Bundle Id is :: "+purchaseDto.getId());
		return purchaseDto.getId();  
	}
	
	public  Integer createPurchaseBundleProduct(PurchasedBundleWS purchasedBundleWS, Integer purchaseBundleId) {
		
		LOG.debug(" Creating purchasedbundle products");
		PurchasedBundleProductDTO dto=new PurchasedBundleProductDTO();		
		WebServicesSessionSpringBean  webservice= new WebServicesSessionSpringBean();
		
		//fetch all products in the purchased bundle
		PackageProductWS [] packageProductWS = purchasedBundleWS.getPackageProducts();
		//packageProductWS.length --> Gives No.of Products in Bundle
		LOG.debug(" No. of products in the Bundle : " +packageProductWS.length);
		PurchasedBundleDTO purchaseDto =  new  PurchasedBundleDAS().find(purchaseBundleId);

		//Loop iterates for each product exist in the purchased bundle
		for(PackageProductWS products:packageProductWS) {			
			
			PurchasedBundleProductDTO pdto = new PurchasedBundleProductDTO();
			pdto.setPbId(purchaseBundleId);
			pdto.setProductId(products.getProductId());
			LOG.debug("Products Recurring CB Value is :  " +products.getRecurringCbValue());
			//Checks weather product belongs to recurring or not
			if(products.getRecurringCbValue()!=null){
				if(products.getRecurringCbValue().equals(Constants.RECURRING)){
					LOG.debug(" in recurring    charge"+products.getRecurringPrice());
					Date  recurringStartTime=webservice.getDateByOffSet(products.getRecurringStartOffsetUnit(),products.getRecurringStartOffset()); 
					Date  recurringEndTime=webservice.getDateByOffSet(products.getRecurringEndOffsetUnit(),products.getRecurringEndOffset());
					pdto.setRecurringCharge(products.getRecurringPrice());
					pdto.setRecurringDiscount(products.getRecurringDiscount());					
					pdto.setRecurringStartTime(recurringStartTime);					
					pdto.setRecurringEndTime(recurringEndTime);
					/*Creates Recurring Order; frequency refers monthly/onetime && Billing Type Refers postpaid/prepaid.
					We are fetching these two values from GSP page. If frequency & billing type values set it is Recurring order else it is one time or Cancel product(those values are equal to '0' in this case)  */
					Integer recurringOrderId = createBundleOrder(products.getProductId(),recurringStartTime,recurringEndTime,new Integer(products.getFrequency()),
							new Integer(products.getBillingType()),Constants.ORDER_LINE_TYPE_ITEM, products.getRecurringDiscount(),products.getRecurringPrice(),purchaseDto.getUserDto());
					//Updates Purchase Bundle Product
					pdto.setRecurringOrderId(recurringOrderId);           
				}
			}
			LOG.debug(" products getOneTimeCbValue CB Value is :  " +products.getOneTimeCbValue());
			//Checks weather product belongs to OneTime or not
			if(products.getOneTimeCbValue()!=null){
				if(products.getOneTimeCbValue().equals(Constants.ONE_TIME)){
					Date  oneOffStartTime = webservice.getDateByOffSet(products.getOneTimeStartOffsetUnit(),products.	getOneTimeStartOffset()); 
					Date  oneOffEndTime=webservice.getDateByOffSet(products.getOneTimeEndOffsetUnit(),products.getOneTimeEndOffset()); 
					
					pdto.setOneOffCharge(products.getOneTimePrice());
					pdto.setOneOffDiscount(products.getOneTimeDiscount());					
					pdto.setOneOffStartTime(oneOffStartTime);					
					pdto.setOneOffEndTime(oneOffEndTime);
					//Creates One Time Order
					Integer oneTimeOrderId = createBundleOrder(products.getProductId(),oneOffStartTime,oneOffEndTime,Constants.ORDER_PERIOD_ONCE,Constants.ORDER_BILLING_PRE_PAID,Constants.ORDER_LINE_TYPE_ITEM, products.getOneTimeDiscount(),products.getOneTimePrice(),purchaseDto.getUserDto());
					pdto.setOneOffOrderId(oneTimeOrderId);         
				}
			}
			LOG.debug(" products getCancelCbValue :  " +products.getCancelCbValue());
			//Checks weather product belongs to Cancel or not
			if(products.getCancelCbValue() != null){
				if(products.getCancelCbValue().equals(Constants.CANCEL)){
					LOG.debug(" in cancel   charge"+products.getCancelPrice());
					Date  cancelStartTime=webservice.getDateByOffSet(products.getCancelStartOffsetUnit(),products.getCancelStartOffset()); 
					Date cancelEndTime=webservice.getDateByOffSet(products.getCancelEndOffsetUnit(),products.getCancelEndOffset()); 
					
					pdto.setCancelCharge(products.getCancelPrice());
					pdto.setCancelDiscount(products.getCancelDiscount());					
					pdto.setCancelStartTime(cancelStartTime);					
					pdto.setCancelEndTime(cancelEndTime);            
				}
			}
			dto= new PurchasedBundleProductDAS().save(pdto);			    
		}
		return  dto.getId();
	}

	public PurchasedBundleWS getPurchasedBundleWS(Integer  id){
		PurchasedBundleDTO  dto  = new PurchasedBundleDAS().find(id);
		PricePackageDTO pricePackageDTO = new PricePackageDAS().find(dto.getBundleId());
		PurchasedBundleWS  ws  =  new PurchasedBundleWS(dto.getId(),dto.getBundleId(),dto.getStatusId(),dto.getValidFrom(),dto.getValidTo(),dto.getUpdateDateTime(),dto.getCreatedDateTime(),dto.getUserDto(),pricePackageDTO.getDescription());
		return ws;
	}
	
	//Triggers when user cancel the purchased Bundle
	public  void cancelBundle(Integer purchaseId, Date reqDate){
		
		Date today = new Date();
		IDeferredActionSessionBean local = (IDeferredActionSessionBean) Context.getBean(Context.Name.DEFERRED_ACTION_SESSION);
		CancelBundle cancelBundle = new CancelBundle();		
		PurchasedBundleDTO purchasedBundle = new PurchasedBundleDAS().find(purchaseId);
		UserDTO user = purchasedBundle.getUserDto();		
		Integer entityId= user.getCompany().getId();
		
		cancelBundle.setRequestDate(new Date());
		cancelBundle.setScheduleDate(reqDate);		
		cancelBundle.setPurchaseId(purchaseId);
		cancelBundle.setEntityId(entityId);
		LOG.debug ("Checking requestDate is after today !!" +reqDate.after(today));
		if(reqDate.after(today)){
			local.create(cancelBundle,reqDate,entityId,user);
		}else{
			cancelBundle.execute();
		}
		/****
		Cancellation of bundle code written in CnacleBundle instead of here.
		****/
	}
	
	
	public Boolean updateBundleOrderAndCancelBundle(Integer orderId,Integer executorId,Date reqDate,Integer purchaseId,UserDTO user){
		
		LOG.debug("Updating order for cancellation "+orderId);
		Boolean retValue = false;
		try {
			OrderDTO orderDTO = new OrderDAS().find(orderId);		
			OrderBL order = new OrderBL(orderDTO);		
			//--this condition isn't really necessary 
			//if(orderDTO.getActiveSince().equals(new Date()) || orderDTO.getActiveSince().before(new Date())){	
			//Cancel the Order by setting AU to current Date
			order.updateActiveUntil(executorId, reqDate, orderDTO);		
				  
			PurchasedBundleDTO purchasedProductDTO= new PurchasedBundleDAS().find(purchaseId);	               
            purchasedProductDTO.setStatusId(new BundleStatusDAS().find(Constants.BUNDLE_STATUS_CANCEL));
            new PurchasedBundleDAS().makePersistent(purchasedProductDTO);
			
		    LOG.debug("Canceled bundle to inactive " +purchasedProductDTO.getId());			
			retValue= true;
		}catch (Exception e) {
            LOG.error("Exception occured while cancelling the bundle " + e);
            e.printStackTrace();
        }		
		return retValue;
	}
	
	public Integer createBundleOrder(Integer itemId,Date reqDate,Date activeUntil,Integer periodId,
						Integer billingTypeId,Integer orderLineType,BigDecimal discount,BigDecimal price,UserDTO  user) {
		//creates order for bundle		
		OrderBL orderBl = new OrderBL();
		OrderLineDTO orderLineDTO = new OrderLineDTO();
		OrderDTO orderDTO =  new OrderDTO();
		List<OrderLineDTO> orderLines = new ArrayList<OrderLineDTO>();
		
		ItemDTO itemdto = new ItemDAS().find(itemId);		
		LOG.debug("Item DTO is :"+itemdto);
		
		orderDTO.setActiveSince(reqDate);
		if(activeUntil!= null){
			orderDTO.setActiveUntil(activeUntil);
		} 
		
		LOG.debug("Discount "+discount+" and Price "+price);
		//amount = price-discount;
		BigDecimal amount = price.subtract(discount);
		
		orderLineDTO.setItem(new ItemDAS().find(itemId));
		orderLineDTO.setAmount(amount);
		orderLineDTO.setOrderLineType(new OrderLineTypeDAS().find(orderLineType));
		//orderLineDTO.setOrderLineType(new OrderLineTypeDAS().find(Constants.ORDER_LINE_TYPE_ITEM));
		orderLineDTO.setCreateDatetime(new Date());
		orderLineDTO.setUseItem(true);
		orderLineDTO.setQuantity(1);
		orderLineDTO.setPrice(price);
		orderLineDTO.setDescription(new ItemDAS().find(itemId).getDescription());
		orderLineDTO.setDeleted(0);
		
		orderLines.add(orderLineDTO);
		orderDTO.setLines(orderLines);
		orderDTO.setOrderPeriodId(periodId);   
		orderDTO.setBaseUserByUserId(user);
		orderDTO.setCurrency(user.getCurrency());
		//orderDTO.setOrderStatus(new OrderStatusDAS().find(Constants.ORDER_STATUS_ACTIVE));
		String notes= " created  order - "+new ItemDAS().find(itemId).getDescription()+". This order is created by bundle,  don't edit  this order";
		
		orderDTO.setNotes(notes);
		orderDTO.setCreateDate(new Date());
		orderDTO.setDeleted(0);
		orderDTO.setOrderBillingType(new OrderBillingTypeDAS().find(billingTypeId));
		
		Integer OrderId = orderBl.create(user.getCompany().getId(), user.getId(), orderDTO); 
		LOG.debug("Order Created with ID " +OrderId);
		return OrderId;			
	} 
	
	public List<OrderWS> getPurchasedOrder(Integer purchaseId){
		Integer oneOffOrderId=null;
		Integer recurringOrderId=null;
		Integer cancelOrderId=null;
		List<OrderWS> ws= new ArrayList<OrderWS>();
		WebServicesSessionSpringBean  webservice= new WebServicesSessionSpringBean();
		List<PurchasedBundleProductDTO> purchasedBundleProductDTO  = new PurchasedBundleProductDAS().findByPurchaseId(purchaseId);
		LOG.debug("   dto size is"+purchasedBundleProductDTO.size());
		for(PurchasedBundleProductDTO dto:purchasedBundleProductDTO){
			LOG.debug("   dto "+dto);
			oneOffOrderId=dto.getOneOffOrderId();
			LOG.debug("oneOffOrderId"+oneOffOrderId);        
			if(oneOffOrderId!=0){
				OrderWS oneOrderWS=webservice.getOrder(oneOffOrderId);
				LOG.debug("orderWS"+oneOrderWS);
				ws.add(oneOrderWS);
			}       
			recurringOrderId=dto.getRecurringOrderId();
			LOG.debug("recurringOrderId"+recurringOrderId);
			if(recurringOrderId!=0){
				OrderWS recurringOrderWS=webservice.getOrder(recurringOrderId);
				LOG.debug("orderWS"+recurringOrderWS);
				ws.add(recurringOrderWS);
			}       
			cancelOrderId=dto.getCancelOrderId();
			LOG.debug("cancelOrderId"+cancelOrderId);
			if(cancelOrderId!=0){
				OrderWS cancelOrderWS=webservice.getOrder(cancelOrderId);
				LOG.debug("cancelOrderWS"+cancelOrderWS);
				ws.add(cancelOrderWS);
			}   
		}
		LOG.debug(" orderlist ws   size"+ws.size());
		return  ws;
	}
}
