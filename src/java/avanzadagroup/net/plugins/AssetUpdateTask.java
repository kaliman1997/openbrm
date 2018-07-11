package avanzadagroup.net.plugins;

import java.util.ArrayList;
import java.util.Set;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.common.FormatLogger;
import com.sapienter.jbilling.server.item.AssetBL;
import com.sapienter.jbilling.server.item.event.AssetUpdatedEvent;
import com.sapienter.jbilling.server.metafields.MetaFieldValueWS;
import com.sapienter.jbilling.server.metafields.db.MetaFieldValue;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.order.db.OrderProcessDTO;
import com.sapienter.jbilling.server.payment.event.PaymentLinkedToInvoiceEvent;
import com.sapienter.jbilling.server.pluggableTask.PluggableTask;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.system.event.Event;
import com.sapienter.jbilling.server.system.event.task.IInternalEventsTask;
import com.sapienter.jbilling.server.user.UserBL;


public class AssetUpdateTask extends PluggableTask implements IInternalEventsTask {
	private static final Class<Event> events[] = new Class[] {AssetUpdatedEvent.class};

	private static final FormatLogger LOG = new FormatLogger(
			Logger.getLogger(AssetUpdatedEvent.class));

	@Override
	public void process(Event event) throws PluggableTaskException {
		if (event instanceof AssetUpdatedEvent) {
			AssetUpdatedEvent myEvent = (AssetUpdatedEvent) event;
			//AssetUpdatedEvent{entityId=10, name=Asset Updated event, 
			//asset=AssetDTO{id=300, identifier='22973048972041', assetStatus=205, 
//			deleted=0, versionNum=1, createDatetime=2018-07-10 21:24:57.89, notes='', 
//					orderLine=200, item=104, isReserved='false'}, assignedTo=130, user=10, 
//					oldStatus=AssetStatusDTO{id=206, itemType=405, isDefault=1, isOrderSaved=0, 
//					isAvailable=1, isInternal=0, versionNum=0, deleted=0}}
			
			LOG.debug("ItemCategory " + myEvent.getAsset().getItem().getCategoryCode(""));
			
			if (myEvent.getAsset().getItem().getCategoryCode("").equalsIgnoreCase("SIMS")){
				//InternalNumber is productCode
				LOG.debug("23  ICC NUMBER" +myEvent.getAsset().getItem().getNumber());
				LOG.debug("ICC " +myEvent.getAsset().getIdentifier());
				
				String IMSI = (String) new AssetBL().find(myEvent.getAsset().getId()).getMetaField("IMSI").getValue();
				String MSISDN = (String)new AssetBL().find(myEvent.getAsset().getId()).getMetaField("MSISDN").getValue();
				
				new UserBL().getUserEntity(myEvent.getAssignedTo().getId()).getCustomer().getMetaField("IMSI").getId();
					
					
			
			} else { //CPEs
				LOG.debug("SERIAL " +myEvent.getAsset().getItem().getInternalNumber());
				
			}
		
		
			
		} else {
			throw new PluggableTaskException("Cannot process event " + event);

		}
	}

	@Override
	public Class<Event>[] getSubscribedEvents() {

		return events;
	}

}
