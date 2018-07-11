package avanzadagroup.net.plugins;

import java.util.ArrayList;
import java.util.Set;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.common.FormatLogger;

import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.order.db.OrderProcessDTO;
import com.sapienter.jbilling.server.payment.event.PaymentLinkedToInvoiceEvent;
import com.sapienter.jbilling.server.pluggableTask.PluggableTask;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.system.event.Event;
import com.sapienter.jbilling.server.system.event.task.IInternalEventsTask;


public class ActivatePlanTask extends PluggableTask implements IInternalEventsTask {
	private static final Class<Event> events[] = new Class[] {PaymentLinkedToInvoiceEvent.class};

	private static final FormatLogger LOG = new FormatLogger(
			Logger.getLogger(PaymentLinkedToInvoiceEvent.class));

	@Override
	public void process(Event event) throws PluggableTaskException {
		if (event instanceof PaymentLinkedToInvoiceEvent) {
			PaymentLinkedToInvoiceEvent myEvent = (PaymentLinkedToInvoiceEvent) event;
			//PaymentLinkedToInvoiceEvent{paymentId=300, amount=900.00, invoiceId=101}
		
			for (OrderProcessDTO orderPorcess : myEvent.getInvoice().getOrderProcesses()){
				for(OrderLineDTO orderLine:orderPorcess.getPurchaseOrder().getLines()){
					LOG.debug("avanzada :: " + orderLine.getItem().getDescription());
					LOG.debug("avanzada :: " + orderLine.getItem().getInternalNumber());
					LOG.debug("avanzada :: " + orderLine.getItem().getNumber());
				
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
