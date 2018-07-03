package dk.comtalk.billing.server.customer.task;

import in.saralam.sbs.server.advancepricing.AdvancePricingBL;
import in.saralam.sbs.server.advancepricing.db.ChargeTypeDAS;
import in.saralam.sbs.server.advancepricing.db.ChargeTypeDTO;
import in.saralam.sbs.server.advancepricing.db.ProductChargeRateDAS;
import in.saralam.sbs.server.advancepricing.db.ProductChargeRateDTO;
import dk.comtalk.billing.server.customer.balance.BalanceBL;
import dk.comtalk.billing.server.customer.balance.db.BalanceDTO;

import java.math.BigDecimal;
import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.item.db.ItemPriceDTO;
import com.sapienter.jbilling.server.item.db.ItemTypeDTO;
import com.sapienter.jbilling.server.order.OrderBL;
import com.sapienter.jbilling.server.order.OrderLineWS;
import com.sapienter.jbilling.server.order.OrderWS;
import com.sapienter.jbilling.server.order.event.NewOrderEvent;
import com.sapienter.jbilling.server.order.event.OrderToInvoiceEvent;
import com.sapienter.jbilling.server.order.db.OrderBillingTypeDAS;
import com.sapienter.jbilling.server.order.db.OrderDAS;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDAS;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.order.db.OrderPeriodDTO;
import com.sapienter.jbilling.server.pluggableTask.PluggableTask;
import com.sapienter.jbilling.server.system.event.Event;
import com.sapienter.jbilling.server.system.event.task.IInternalEventsTask;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.process.db.PeriodUnitDTO;
import in.saralam.sbs.server.advancepricing.db.ProductChargeDAS;
import in.saralam.sbs.server.advancepricing.db.ProductChargeDTO;
import com.sapienter.jbilling.server.order.db.OrderLineTypeDAS;
import com.sapienter.jbilling.server.order.db.OrderLineTypeDTO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import com.sapienter.jbilling.server.order.task.SubscriptionManagerTask;
import com.sapienter.jbilling.server.process.PeriodOfTime;

public class SimpleBundleResourceManager extends PluggableTask implements IInternalEventsTask {
	private static final Class<Event> events[] = new Class[] 
	{ NewOrderEvent.class, OrderToInvoiceEvent.class};
	private static final Logger LOG = Logger.getLogger(SimpleBundleResourceManager.class);
	public Class<Event>[] getSubscribedEvents() {
		return events;
	}
	public void process(Event event) throws PluggableTaskException {
			
		LOG.debug("SimpleBundleResourceManager is triggered....");
		if (event instanceof NewOrderEvent) {
			NewOrderEvent newOrder = (NewOrderEvent) event;
			LOG.debug("The Order " + newOrder.getOrder());
			Integer orderId=0;
			
			
			if(newOrder.getOrder().getOrderPeriod().getDescription().equalsIgnoreCase("One time")){
				LOG.debug("new orders id is "+newOrder.getOrder().getId());
				createBundleBalance(newOrder.getOrder());
			}else {
				LOG.debug("new orders period is "+newOrder.getOrder().getOrderPeriod().getDescription());
				orderId = newOrder.getOrder().getId();
				Integer periodId = newOrder.getOrder().getOrderPeriod().getId();
			
				 
				createBundleBalance(newOrder.getOrder());
			}

		} else if (event instanceof OrderToInvoiceEvent) {

			OrderToInvoiceEvent newOrder = (OrderToInvoiceEvent) event;
      LOG.debug("The Order " + newOrder.getOrder());
      Integer orderId=0;


                        if(newOrder.getOrder().getOrderPeriod().getDescription().equalsIgnoreCase("One time")){
                                LOG.debug("new orders id is "+newOrder.getOrder().getId());
                                createBundleBalance(newOrder.getOrder());

                        }else {
                                LOG.debug("new orders period is "+newOrder.getOrder().getOrderPeriod().getDescription());
                                orderId = newOrder.getOrder().getId();
                                Integer periodId = newOrder.getOrder().getOrderPeriod().getId();

                                createBundleBalanceOnInvoice(newOrder, newOrder.getOrder());
			}

			
		} else {
			throw new PluggableTaskException("Cant not process event " + event);
		}
	}
	private void createBundleBalance(OrderDTO order) {
		LOG.debug("order in create bundle is "+order);
		BalanceBL balanceBl = new BalanceBL();
		BigDecimal poolAmount = BigDecimal.ZERO;
		for (OrderLineDTO line : order.getLines()) {
			ItemDTO item = line.getItem();
			boolean ub_create = false;
			Set<ItemTypeDTO> cats = item.getItemTypes();
			for (ItemTypeDTO cat : cats){
				if (cat.getDescription().equalsIgnoreCase("plans")){
					ub_create = true;
				}
			}
			
			//adding advance pricing to it
			LOG.debug("created order period is "+order.getOrderPeriod().getDescription());
            
			if (ub_create ){
				for(ItemPriceDTO price : item.getItemPrices()) {
					if(price.getCurrencyId() > 9999) {

						BalanceDTO balance = new BalanceDTO();
						balance.setBaseUserByUserId(order.getBaseUserByUserId());
						balance.setOrderLineDTO(line);
						balance.setOrderDTO(order);
						balance.setActiveSince(order.getActiveSince());
						balance.setCreateDate(new Date());
						
						Date oActiveUntil = order.getActiveUntil();
						Date activeUntil = null;
						if(activeUntil == null){
				 	 		OrderPeriodDTO orderPeriod = order.getOrderPeriod();
				    	 		PeriodUnitDTO unit = orderPeriod.getPeriodUnit();
				    	 		if (unit != null){
				    	 			Integer unitNum = unit.getId();
					    			Integer value = orderPeriod.getValue();
					    			Date activeSince =order.getActiveSince();
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
							if(oActiveUntil != null && oActiveUntil.before(activeUntil)) {
								LOG.debug("Active until is in future..");
				    				balance.setActiveUntil(oActiveUntil);
							}else {
				    				balance.setActiveUntil(activeUntil);
							}
						} else {
							activeUntil = order.getActiveUntil();
							balance.setActiveUntil(activeUntil);
						}
						
			    			BigDecimal quantity = line.getQuantity();
						balance.setBalance(price.getPrice().multiply(quantity));
						balance.setCurrency(price.getCurrency());
						LOG.debug("balance is " +balance);
						balanceBl.create(order.getBaseUserByUserId().getCompany().getId(), balance);							
					}
						
				}
			}
		}

	}

	private void createBundleBalanceOnInvoice(OrderToInvoiceEvent event, OrderDTO order) {
		LOG.debug("replenish bundle balance on new invoice "+ event.getStart() + "to " + event.getEnd());
		BalanceBL balanceBl = new BalanceBL();
		BigDecimal poolAmount = BigDecimal.ZERO;
		for (OrderLineDTO line : order.getLines()) {
			ItemDTO item = line.getItem();
			boolean ub_create = false;
			Set<ItemTypeDTO> cats = item.getItemTypes();
			for (ItemTypeDTO cat : cats){
				if (cat.getDescription().equalsIgnoreCase("plans")){
					ub_create = true;
				}
			}
			
			//adding advance pricing to it
			LOG.debug("created order period is "+order.getOrderPeriod().getDescription());
            
			if (ub_create ){
				//get the invoice periods
				
				for(ItemPriceDTO price : item.getItemPrices()) {
					if(price.getCurrencyId() > 9999) {

						BalanceDTO balance = new BalanceDTO();
						balance.setBaseUserByUserId(order.getBaseUserByUserId());
						balance.setOrderLineDTO(line);
						balance.setOrderDTO(order);
						balance.setActiveSince(event.getEnd());
						balance.setCreateDate(new Date());
						
						Date oActiveUntil = order.getActiveUntil();
						Date activeUntil = event.getEnd();

						OrderPeriodDTO orderPeriod = order.getOrderPeriod();
                                                PeriodUnitDTO unit = orderPeriod.getPeriodUnit();
                                                if (unit != null){
                                                	Integer unitNum = unit.getId();
                                                        Integer value = orderPeriod.getValue();
                                                        Date activeSince = event.getEnd();
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

						
						if(oActiveUntil != null && oActiveUntil.before(activeUntil)) {
							LOG.debug("Active until is in future..");
				    			balance.setActiveUntil(oActiveUntil);
						}else {
				    			balance.setActiveUntil(activeUntil);
						}
						
			    			BigDecimal quantity = line.getQuantity();
						balance.setBalance(price.getPrice().multiply(quantity));
						balance.setCurrency(price.getCurrency());
						LOG.debug("balance is " +balance);
						balanceBl.create(order.getBaseUserByUserId().getCompany().getId(), balance);							
					}
						
				}
			}
		}

	}

}

