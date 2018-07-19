package avanzadagroup.net.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import avanzadagroup.net.altanAPI.Activation;
import avanzadagroup.net.altanAPI.Purchase;

import com.sapienter.jbilling.common.FormatLogger;
import com.sapienter.jbilling.server.item.db.AssetDTO;
import com.sapienter.jbilling.server.item.db.AssetStatusDAS;
import com.sapienter.jbilling.server.item.db.AssetStatusDTO;
import com.sapienter.jbilling.server.item.db.ItemTypeDAS;
import com.sapienter.jbilling.server.item.db.ItemTypeDTO;
import com.sapienter.jbilling.server.metafields.db.MetaFieldValue;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.order.db.OrderProcessDTO;
import com.sapienter.jbilling.server.payment.event.PaymentLinkedToInvoiceEvent;
import com.sapienter.jbilling.server.pluggableTask.PluggableTask;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.system.event.Event;
import com.sapienter.jbilling.server.system.event.task.IInternalEventsTask;
import com.sapienter.jbilling.server.user.UserBL;

public class AssignAssetTask extends PluggableTask implements
		IInternalEventsTask {
	private static final Class<Event> events[] = new Class[] { PaymentLinkedToInvoiceEvent.class };

	private static final FormatLogger LOG = new FormatLogger(
			Logger.getLogger(PaymentLinkedToInvoiceEvent.class));

	@Override
	public void process(Event event) throws PluggableTaskException {
		if (event instanceof PaymentLinkedToInvoiceEvent) {
			PaymentLinkedToInvoiceEvent myEvent = (PaymentLinkedToInvoiceEvent) event;
			// PaymentLinkedToInvoiceEvent{paymentId=300, amount=900.00,
			// invoiceId=101}

			for (OrderProcessDTO opDTO : myEvent.getInvoice()
					.getOrderProcesses()) {
				for (OrderLineDTO olDTO : opDTO.getPurchaseOrder().getLines()) {

					if (olDTO.getItem().getCategoryCode("")
							.equalsIgnoreCase("CPEs")
							|| olDTO.getItem().getCategoryCode("")
									.equalsIgnoreCase("SIMS")) {
						ItemTypeDTO itDTO = null;
						
						for(ItemTypeDTO itDTO2 : olDTO.getItem().getItemTypes()){
							if (itDTO2.getDescription().equalsIgnoreCase(olDTO.getItem().getCategoryCode(""))){
								itDTO = itDTO2;
								break;
							}
							
						}
						
						LOG.debug("AVANZADA::category " + itDTO.getId() + " "+ itDTO.getDescription());

						for (AssetDTO assetDTO : olDTO.getAssets()) {

							for (AssetStatusDTO asDTO : new AssetStatusDAS()
									.getStatuses(itDTO.getId(), false)) {
								LOG.debug("AVANZADA:: Status Desc " + asDTO.getDescription());
								
								if (asDTO.getDescription().equalsIgnoreCase(
										"Asignado")) {
									assetDTO.setAssetStatus(asDTO);
									break;
								}

							}

						}

					}

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
