package in.saralam.sbs.server.subscription.task;

import java.math.BigDecimal;
import java.util.List;
import org.apache.log4j.Logger;

import com.sapienter.jbilling.common.Constants;
import com.sapienter.jbilling.server.item.ItemBL;
//import com.sapienter.jbilling.common.ResourcePoolManager;
import com.sapienter.jbilling.server.item.ItemDecimalsException;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.item.db.ItemPriceDTO;
//import com.sapienter.jbilling.server.mediation.Record;
import in.saralam.sbs.server.subscription.ServiceBL;
//import com.sapienter.jbilling.server.user.balance.BalanceBL;
//import com.sapienter.jbilling.server.user.balance.db.BalanceDTO;
import in.saralam.sbs.server.subscription.event.NewStatusEvent;
import in.saralam.sbs.server.subscription.db.ServiceDTO;
//import com.sapienter.jbilling.server.service.db.ServiceLineDTO;
import com.sapienter.jbilling.server.pluggableTask.PluggableTask;
import com.sapienter.jbilling.server.pluggableTask.TaskException;
import com.sapienter.jbilling.server.system.event.Event;
import com.sapienter.jbilling.server.system.event.task.IInternalEventsTask;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import java.util.ArrayList;
import java.util.Date;

public class StatusEventManager extends PluggableTask implements IInternalEventsTask {
	private static final Class<Event> events[] = new Class[] 
	{ NewStatusEvent.class};
	private static final Logger LOG =
	Logger.getLogger(StatusEventManager.class);
	public Class<Event>[] getSubscribedEvents() {
		return events;
	}
	public void process(Event event) throws PluggableTaskException {
		if (event instanceof NewStatusEvent) {
			NewStatusEvent newService = (NewStatusEvent) event;
			LOG.debug("The Service " + newService.getNewStatusId());
			/* get the line, it should have only one line otherwise let him go (it may be normal service) */
			//ResourcePoolManager rpm = new ResourcePoolManager();
			//BalanceBL balanceBl = new BalanceBL();
			BigDecimal poolAmount = BigDecimal.ZERO;
			/*for (ServiceLineDTO line : newService.getService().getLines()) {
				ItemDTO item = line.getItem();
				if (item.getCategoryCode("Family-Shared") != null || item.getCategoryCode("Personal-Pool") != null) {
					//create a common pool (this service nust be for a parent account)
					for(ItemPriceDTO price : item.getItemPrices()) {
						if (price.getCurrencyId() > 10000 ) {
							Integer resource = price.getCurrencyId();
							poolAmount = price.getPrice();
							//create a pool, consider passing validity dates i.e active since -active untill to manage resource validity too
							if (rpm.createPool(newService.getService().getBaseUserByUserId().getUserId(), resource, poolAmount) == null) {
								LOG.debug("unable to create pool for the user " + newService.getService().getBaseUserByUserId().getUserId() + " Please retry");
								
							}
							BalanceDTO balance = new BalanceDTO();
							balance.setBaseUserByUserId(newService.getService().getBaseUserByUserId());
							balance.setActiveSince(new Date());
							balance.setCreateDate(new Date());
							balance.setActiveUntil(null);
							balance.setBalance(poolAmount);
							balance.setCurrency(price.getCurrency());
							balanceBl.create(newService.getService().getBaseUserByUserId().getUserId(), balance);							
						}
						
					}
				}
			}*/
			
			
		} else {
			throw new PluggableTaskException("Cant not process event " + event);
		}
	}

}