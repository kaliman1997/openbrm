package avanzadagroup.net.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import avanzadagroup.net.altanAPI.Activation;
import avanzadagroup.net.altanAPI.Purchase;

import com.sapienter.jbilling.common.FormatLogger;
import com.sapienter.jbilling.server.metafields.db.MetaFieldValue;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.order.db.OrderProcessDTO;
import com.sapienter.jbilling.server.payment.event.PaymentLinkedToInvoiceEvent;
import com.sapienter.jbilling.server.pluggableTask.PluggableTask;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.system.event.Event;
import com.sapienter.jbilling.server.system.event.task.IInternalEventsTask;
import com.sapienter.jbilling.server.user.UserBL;


public class ActivatePlanTask extends PluggableTask implements IInternalEventsTask {
	private static final Class<Event> events[] = new Class[] {PaymentLinkedToInvoiceEvent.class};

	private static final FormatLogger LOG = new FormatLogger(
			Logger.getLogger(ActivatePlanTask.class));

	@Override
	public void process(Event event) throws PluggableTaskException {
		if (event instanceof PaymentLinkedToInvoiceEvent) {
			PaymentLinkedToInvoiceEvent myEvent = (PaymentLinkedToInvoiceEvent) event;
			//PaymentLinkedToInvoiceEvent{paymentId=300, amount=900.00, invoiceId=101}
			
			String MSISDN = "";
			String planType = "";
			String offeringId = "";
			String latitude = "";
			String longitude = "";
			
		
		
			for (OrderProcessDTO orderProcess : myEvent.getInvoice().getOrderProcesses()){
				List<MetaFieldValue> mfvList = new UserBL()
				.getUserEntity(orderProcess.getPurchaseOrder().getUserId()).
				getCustomer().getMetaFields();
				
				for (MetaFieldValue mfv : mfvList) {
					if (mfv.getField().getName().equalsIgnoreCase("MSISDN")) {
						MSISDN = (String) mfv.getValue();
					}
					
					if (mfv.getField().getName().equalsIgnoreCase("Latitud")) {
						latitude = (String) mfv.getValue();
					}
					
					if (mfv.getField().getName().equalsIgnoreCase("Longitud")) {
						longitude = (String) mfv.getValue();
					}
				}	
				
				if(MSISDN.equalsIgnoreCase("----") || 
						MSISDN.equalsIgnoreCase("")){
					break;					
				}
				
				for(OrderLineDTO orderLine:orderProcess.getPurchaseOrder().getLines()){
					if(orderLine.getItem().getCategoryCode("").equalsIgnoreCase("ofertas primarias")){
						planType="primaryOffer";
						offeringId = orderLine.getItem().getInternalNumber();
					} else if (orderLine.getItem().getCategoryCode("").
							equalsIgnoreCase("ofertas suplementarias")){
						planType="suplementaryOffer";
						offeringId = orderLine.getItem().getInternalNumber();						
					}
				
				}
			}
			
			if(!planType.equals("") && !offeringId.equals("") 
					&& !MSISDN.equals("") && !MSISDN.equals("----")){
				if(planType.equalsIgnoreCase("primaryOffer")){
					Activation activation = new Activation();
					LOG.debug("CBOSS::"+ activation.activate(MSISDN, offeringId, latitude + "," + longitude).getJsonResponse());
					
				}else {
					Purchase purchase = new Purchase();
					LOG.debug("CBOSS::"+ purchase.activate(MSISDN, offeringId).getJsonResponse());
				}
				
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
