package in.saralam.sbs.server.deferredAction.action;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.log4j.Logger;
import com.sapienter.jbilling.server.item.ItemBL;
import com.sapienter.jbilling.server.item.ItemDecimalsException;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.item.db.ItemDAS;
import com.sapienter.jbilling.server.item.db.ItemTypeDTO;

import com.sapienter.jbilling.server.user.ContactBL;
import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.db.CompanyDAS;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import com.sapienter.jbilling.server.user.db.UserDAS;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.order.OrderBL;
import com.sapienter.jbilling.server.order.OrderLineBL;

import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.system.event.EventManager;
import com.sapienter.jbilling.server.order.db.OrderPeriodDTO;
import com.sapienter.jbilling.server.process.db.PeriodUnitDTO;

import com.sapienter.jbilling.server.order.db.OrderDAS;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.OrderBL;

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
import in.saralam.sbs.server.subscription.db.ServiceAliasDAS;
import in.saralam.sbs.server.subscription.db.ServiceAliasDTO;
//import in.saralam.sbs.server.deferredAction.DeferredActionSessionBean;
import in.saralam.sbs.server.deferredAction.action.PricePlanTransititon;
import in.saralam.sbs.server.deferredAction.action.IDeferredAction;
import in.saralam.sbs.server.deferredAction.IDeferredActionSessionBean;
import com.sapienter.jbilling.server.user.EntityBL;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import org.hibernate.ScrollableResults;
import com.sapienter.jbilling.server.util.Context;

public class ChangeSubscription implements IDeferredAction {
    
 //private Integer orderId;
 private  Integer fromItemId;
 private Integer toItemId;
 private static final Logger LOG = Logger.getLogger(ChangeSubscription.class);
 private Integer executorId;
 private Date requestDate;
 private  Date  scheduleDate;
 private Integer serviceId;
 private Integer entityId;
 private UserDTO  user;
 private OrderDTO newOrder;
ChangeSubscription priceObj;
  public ChangeSubscription(){
  }
 public  ChangeSubscription(ChangeSubscription  priceObj){
	 this.priceObj=priceObj;
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
  
  
  

  @Override
  public void execute() {
	
   	

    try {
		   
				
			OrderDTO order = new OrderDTO();
       Integer orderId = null;
       ServiceDTO service  = new ServiceDAS().find(serviceId);
       ItemDTO fromPlan = new ItemDAS().find(fromItemId);
       ItemDTO toPlan = new ItemDAS().find(toItemId);
       ItemDTO taxItem = null;
       OrderDTO currentOrder = service.getOrderDTO();
       LOG.debug(" order id"+currentOrder.getId());
       UserBL user = new UserBL(currentOrder.getUserId());
       String TDCSubscriberId = null;
       
       Date activeUntil = currentOrder.getActiveUntil();

       if(activeUntil == null) {
         //activeUntil = currentOrder.getNextBillableDay();
         activeUntil = scheduleDate;		 
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

         bl.updateActiveUntil(user.getEntity().getId(), activeUntil, currentOrder);

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
       pricePlan.setServiceId(service.getId());
       pricePlan.setEntityId(entityId);
       //IDeferredAction iDefredAction= pricePlan ;
       local.create(pricePlan);
	     
       Integer languageId = user.getEntity().getLanguageIdField();
      // ComtalkNotificationBL comtalkNotificationBl= new ComtalkNotificationBL();
       
       OrderDTO newOrderDTO = new OrderDAS().find(orderId);
       OrderLineDTO newLine = (OrderLineDTO) newOrderDTO.getLine(toItemId);
       String newPlanName = newLine.getDescription();
       BigDecimal newCharge = newLine.getAmount();
	   
	   ServiceBL servicebl = new ServiceBL();
	   LOG.debug("oldServiceId in ChagePlan" + service.getId());
	   ServiceAliasDTO serviceAliasDto = new ServiceAliasDAS().findByService(service.getId());
	    if(serviceAliasDto == null) {
		LOG.debug("This Service "+ service.getId() + " has No ServiceAlias "); 
		}  else { 
            	String oldaliasName = serviceAliasDto.getAliasName();
	             LOG.debug("OrderId in ChagePlan" + orderId);
				 ServiceDTO sDto = new ServiceDAS().findByOrder(orderId);
				 Integer newServiceId = sDto.getId();
				 Integer newServiceAliasId = servicebl.addAlias(newServiceId, oldaliasName);
			     serviceAliasDto.setDeleted(1);
				 new ServiceAliasDAS().makePersistent(serviceAliasDto);
			     LOG.debug("NewServiceAliasId "+ newServiceAliasId +" created for this Service "+ newServiceId + " oldServiceAlias " + oldaliasName );
			 }
	   
	           
	     
		  
	}
       catch (Exception e) {
         LOG.error("Exception occured while cancelling the Plan " + e);
         e.printStackTrace();
      }

 }


}
