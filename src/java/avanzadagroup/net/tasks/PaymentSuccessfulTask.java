package avanzadagroup.net.tasks;

import java.util.Iterator;
import java.util.List;





import org.apache.log4j.Logger;

import avanzadagroup.net.altanAPI.Coverage;
import avanzadagroup.net.altanAPI.OAuth;
import avanzadagroup.net.altanAPI.responses.AddressCoordinatesResp;
import avanzadagroup.net.altanAPI.responses.CoverageResp;
import avanzadagroup.net.altanAPI.responses.OAuthResp;
import avanzadagroup.net.google.AddressCoordinates;

import com.sapienter.jbilling.common.FormatLogger;
import com.sapienter.jbilling.server.item.AssetStatusBL;
import com.sapienter.jbilling.server.item.db.AssetDAS;
import com.sapienter.jbilling.server.item.db.AssetDTO;
import com.sapienter.jbilling.server.item.db.AssetStatusDAS;
import com.sapienter.jbilling.server.metafields.MetaFieldValueWS;
import com.sapienter.jbilling.server.payment.PaymentDTOEx;
import com.sapienter.jbilling.server.payment.event.PaymentSuccessfulEvent;
import com.sapienter.jbilling.server.pluggableTask.PluggableTask;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.system.event.task.IInternalEventsTask;
import com.sapienter.jbilling.server.system.event.Event;


public class PaymentSuccessfulTask extends PluggableTask implements
		IInternalEventsTask {
	private static final FormatLogger LOG = new FormatLogger(
			Logger.getLogger(PaymentSuccessfulTask.class));

	private static final Class<Event> events[] = new Class[] {
		PaymentSuccessfulEvent.class };

	@Override
	public void process(Event event) throws PluggableTaskException {
		if (event instanceof PaymentSuccessfulEvent) {
			PaymentDTOEx pde = ((PaymentSuccessfulEvent) event).getPayment();
			
			
			AssetDAS assetDas = new AssetDAS();
			List<AssetDTO> assetList = 
					assetDas.findAssetsByUser(pde.getUserId());
			
			for(Iterator<AssetDTO> it = assetList.iterator();it.hasNext();){
				AssetDTO assetDTO = it.next();
				LOG.debug(assetDTO.getIdentifier() + "status " 
						+
				assetDTO.getAssetStatus().getId()
				+ ":"+ assetDTO.getAssetStatus().getDescription());
				
				assetDTO.setAssetStatus(new AssetStatusBL(201).getEntity());
				
				assetDas.save(assetDTO);
				
			}
			
		} else {
			LOG.debug("ARTURO Evento desconocido");
		}

	}

	@Override
	public Class<Event>[] getSubscribedEvents() {
		return events;
	}

}
