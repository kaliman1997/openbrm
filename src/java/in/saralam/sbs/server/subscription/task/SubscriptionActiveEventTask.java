package in.saralam.sbs.server.subscription.task;

import java.math.BigDecimal;
import java.util.*;
import java.lang.*;
import java.text.*;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.item.ItemBL;
import com.sapienter.jbilling.server.item.ItemDecimalsException;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.item.db.ItemDTO;
//import com.sapienter.jbilling.server.mediation.Record;
import com.sapienter.jbilling.server.order.OrderBL;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderStatusDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.pluggableTask.NotificationTask;
import com.sapienter.jbilling.server.pluggableTask.PluggableTask;
import com.sapienter.jbilling.server.pluggableTask.TaskException;
import com.sapienter.jbilling.server.system.event.Event;
import com.sapienter.jbilling.server.system.event.EventManager;
import com.sapienter.jbilling.server.system.event.task.IInternalEventsTask;
import com.sapienter.jbilling.server.item.event.NewItemEvent;
import com.sapienter.jbilling.server.util.db.CurrencyDTO;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
//import com.sapienter.jbilling.server.user.event.NewCustomerEvent;
import com.sapienter.jbilling.server.pluggableTask.admin.ParameterDescription;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.user.UserWS;
import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.ContactWS;
import com.sapienter.jbilling.server.user.contact.db.ContactDTO;
import com.sapienter.jbilling.server.user.contact.db.ContactDAS;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.provisioning.event.SubscriptionActiveEvent;
import com.sapienter.jbilling.server.provisioning.event.SubscriptionInactiveEvent;
//import com.sapienter.jbilling.server.user.event.NewEmailNotificationEvent;
import com.sapienter.jbilling.server.notification.MessageSection;
import com.sapienter.jbilling.server.notification.NotificationBL;
import com.sapienter.jbilling.server.notification.MessageDTO;
import com.sapienter.jbilling.server.notification.db.NotificationMessageArchDAS;
import com.sapienter.jbilling.server.notification.db.NotificationMessageArchDTO;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskManager;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
//import com.sapienter.jbilling.server.device.event.NewUserDeviceEvent;
//import dk.comtalk.billing.server.notification.ComtalkNotificationBL;

import com.sapienter.jbilling.server.order.event.NewActiveUntilEvent;

import java.io.*;
import com.sapienter.jbilling.common.Util;



import java.util.ArrayList;

public class SubscriptionActiveEventTask extends PluggableTask implements IInternalEventsTask {


	private static final Logger LOG = Logger.getLogger(SubscriptionActiveEventTask.class);	

	private static final Class<Event> events[] = new Class[] { 
		SubscriptionActiveEvent.class };
	
	public Class<Event>[] getSubscribedEvents() {
		return events;
	}

	public void process(Event event) throws PluggableTaskException {
		
		LOG.debug("SubscriptionEventManagerTask is triggered");

		if (event instanceof SubscriptionActiveEvent) {
			SubscriptionActiveEvent newSubscription = (SubscriptionActiveEvent) event;
			LOG.debug("Handling SubscriptionActiveEvent");

			/*--DeviceEmailNotification code--*/

			Integer deviceId = newSubscription.getEntityId();			
			//Integer userId = newSubscription.getOrder();
			OrderDTO orderDTO = newSubscription.getOrder();
			//List<OrderLineDTO> orderLineDTO = orderDTO.getLines();
			UserDTO userdto=orderDTO.getUser();
			//UserBL userBL = new UserBL(userId);
			//UserDTO userdto = userBL.getEntity();
			Integer languageId = userdto.getLanguageIdField();
			CompanyDTO company = userdto.getCompany();
			Integer userId = userdto.getId();

			Integer entityId = company.getId();
			LOG.debug("EntityId value:"+entityId+"and useId:"+userdto.getId());
			LOG.debug("EntityId from newSubscription entity is"+newSubscription.getEntityId());
			
			LOG.debug("UserDTO values are"+userdto.getCreateDatetime()+","+userdto.getUserName()+","+userdto.getCompany());
            		//ComtalkNotificationBL notif = new ComtalkNotificationBL();
			try {
				
				Iterator orderLineIt = orderDTO.getLines().iterator();
				while(orderLineIt.hasNext()){
					
					LOG.debug("looping through order lines");
					OrderLineDTO orderLineDTO = (OrderLineDTO)orderLineIt.next();
					//if(orderLineDTO.getProvisioningStatusId().equals(Constants.PROVISIONING_STATUS_ACTIVE)){
						
						LOG.debug("about to call ComtalkNotification sendServiceActivationEmail()");
						//notif.sendServiceActivationEmail(entityId, userId, orderDTO.getId(), languageId);									
						//}
					}
				}catch (Exception e){
            			LOG.warn("Exception in web service: notifying invoice by email " + e);
        		}

		}	
		
		else if(event instanceof SubscriptionInactiveEvent)
		{
			LOG.debug("SubscriptionInactiveEvent event is being processed");
		}
		
		
		else {
			throw new PluggableTaskException("Cant not process event " + event);
		}		
	}
	}
	
