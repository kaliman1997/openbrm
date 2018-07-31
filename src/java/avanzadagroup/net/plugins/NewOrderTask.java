package avanzadagroup.net.plugins;

import java.util.List;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.common.FormatLogger;
import com.sapienter.jbilling.server.metafields.db.MetaFieldValue;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.order.event.NewOrderEvent;
import com.sapienter.jbilling.server.pluggableTask.PluggableTask;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.system.event.Event;
import com.sapienter.jbilling.server.system.event.task.IInternalEventsTask;
import com.sapienter.jbilling.server.user.UserBL;

public class NewOrderTask extends PluggableTask implements IInternalEventsTask {
	private static final Class<Event> events[] = new Class[] { NewOrderEvent.class };

	private static final FormatLogger LOG = new FormatLogger(
			Logger.getLogger(NewOrderTask.class));

	@Override
	public void process(Event event) throws PluggableTaskException {
		if (event instanceof NewOrderEvent) {
			NewOrderEvent myEvent = (NewOrderEvent) event;
			/*
			 * entityId = 10 order = Order = id=2000,baseUserByUserId=100,
			 * baseUserByCreatedBy
			 * =10,currencyDTO=com.sapienter.jbilling.server.util
			 * .db.CurrencyDTO@6b12228c,
			 * orderStatusDTO=com.sapienter.jbilling.server
			 * .order.db.OrderStatusDTO@3f1d492c,orderPeriodDTO=OrderPeriodDTO:[
			 * id=1 company=null periodUnitDTO=null value=null versionNum=1],
			 * orderBillingTypeDTO= OrderBillingType=2,primaryOrderDTO=null,
			 * activeSince=Mon Jul 16 19:16:02 CDT 2018,activeUntil=null,
			 * createDate=Mon Jul 16 19:16:56 CDT 2018,nextBillableDay=null,
			 * deleted
			 * =0,notify=null,lastNotified=null,notificationStep=null,dueDateUnitId
			 * =3,
			 * dueDateValue=null,dfFm=null,anticipatePeriods=null,ownInvoice=null
			 * ,notes=null,notesInInvoice=null,orderProcesses=[],versionNum=0
			 * freeUsageQuantity=nulllines:[OrderLine:[id=2000 orderLineType=1
			 * item=104 order id=2000 amount=700.00000 quantity=1price=700.00000
			 * isPercentage=false createDatetime=Mon Jul 16 19:17:18 CDT 2018
			 * deleted=0useItem=true description=Modem M4 versionNum=0
			 * parentLineId=null metaFields=[] editable=null]-OrderLine:[id=2001
			 * orderLineType=1 item=105 order id=2000 amount=0 quantity=1
			 * price=0isPercentage=false createDatetime=Mon Jul 16 19:17:18 CDT
			 * 2018 deleted=0 useItem=truedescription=Tarjeta SIM versionNum=0
			 * parentLineId=null metaFields=[] editable=null]-OrderLine:[id=2002
			 * orderLineType=1 item=200 order id=2000 amount=350.00000
			 * quantity=1price=350.00000 isPercentage=false createDatetime=Mon
			 * Jul 16 19:17:18 CDT 2018 deleted=0useItem=true
			 * description=T1-HBBIH-5 Pos 10 FUP versionNum=0 parentLineId=null
			 * metaFields=[]editable=null]-]
			 */

			String offeringId = "----";
			String planName = "----";

			for (OrderLineDTO olDTO : myEvent.getOrder().getLines()) {
				if (olDTO.getItem().getCategoryCode("")
						.equalsIgnoreCase("ofertas primarias")) {
					offeringId = olDTO.getItem().getInternalNumber();
					planName = olDTO.getItem().getDescription();

					List<MetaFieldValue> mfvList = new UserBL()
							.getUserEntity(myEvent.getOrder().getUserId())
							.getCustomer().getMetaFields();

					for (MetaFieldValue mfv : mfvList) {
						if (mfv.getField().getName().equalsIgnoreCase("plan")) {
							mfv.setValue(planName);
						}
						if (mfv.getField().getName().equalsIgnoreCase("offeringId")) {
							mfv.setValue(offeringId);
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
