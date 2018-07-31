package avanzadagroup.net.plugins;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.common.FormatLogger;
import com.sapienter.jbilling.server.item.AssetAssignmentDAS;
import com.sapienter.jbilling.server.item.AssetBL;
import com.sapienter.jbilling.server.item.db.AssetAssignmentDTO;
import com.sapienter.jbilling.server.item.event.AssetUpdatedEvent;
import com.sapienter.jbilling.server.metafields.MetaFieldBL;
import com.sapienter.jbilling.server.metafields.MetaFieldValueWS;
import com.sapienter.jbilling.server.metafields.db.MetaField;
import com.sapienter.jbilling.server.metafields.db.MetaFieldDAS;
import com.sapienter.jbilling.server.metafields.db.MetaFieldValue;
import com.sapienter.jbilling.server.order.db.OrderDAS;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.order.db.OrderProcessDTO;
import com.sapienter.jbilling.server.payment.event.PaymentLinkedToInvoiceEvent;
import com.sapienter.jbilling.server.pluggableTask.PluggableTask;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.system.event.Event;
import com.sapienter.jbilling.server.system.event.task.IInternalEventsTask;
import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.db.UserDAS;
import com.sapienter.jbilling.server.user.db.UserDTO;

public class AssetUpdateTask extends PluggableTask implements
		IInternalEventsTask {
	private static final Class<Event> events[] = new Class[] { AssetUpdatedEvent.class };

	private static final FormatLogger LOG = new FormatLogger(
			Logger.getLogger(AssetUpdateTask.class));

	@Override
	public void process(Event event) throws PluggableTaskException {
		if (event instanceof AssetUpdatedEvent) {
			AssetUpdatedEvent myEvent = (AssetUpdatedEvent) event;
			/*
			 * ADD SIM AssetUpdatedEvent{entityId=10, name=Asset Updated event,
			 * asset=AssetDTO{id=102, identifier='8952140061700047084F',
			 * assetStatus=209, deleted=0, versionNum=30,
			 * createDatetime=2018-07-08 13:23:56.411, notes='', orderLine=1200,
			 * item=105, isReserved='false'}, assignedTo=130, user=10,
			 * oldStatus=AssetStatusDTO{id=210, itemType=406, isDefault=1,
			 * isOrderSaved=0, isAvailable=1, isInternal=0, versionNum=0,
			 * deleted=0}} with
			 * avanzadagroup.net.plugins.AssetUpdateTask@1af807a8
			 * 
			 * DELETE SIM AssetUpdatedEvent{entityId=10, name=Asset Updated
			 * event, asset=AssetDTO{id=102, identifier='8952140061700047084F',
			 * assetStatus=210, deleted=0, versionNum=32,
			 * createDatetime=2018-07-08 13:23:56.411, notes='', orderLine=null,
			 * item=105, isReserved='false'}, assignedTo=null, user=10,
			 * oldStatus=AssetStatusDTO{id=209, itemType=406, isDefault=0,
			 * isOrderSaved=1, isAvailable=0, isInternal=0, versionNum=0,
			 * deleted=0}}
			 */

			LOG.debug("CBOSS:: ItemCategory "
					+ myEvent.getAsset().getItem().getCategoryCode(""));

			if (myEvent.getAsset().getItem().getCategoryCode("")
					.equalsIgnoreCase("SIMS")) {
				// InternalNumber is productCode
				LOG.debug("CBOSS::  ICC NUMBER"
						+ myEvent.getAsset().getItem().getNumber());
				LOG.debug("CBOSS:: ICC "
						+ myEvent.getAsset().getIdentifier());

				String ICC = myEvent.getAsset().getIdentifier();

				String IMSI = (String) new AssetBL()
						.find(myEvent.getAsset().getId()).getMetaField("IMSI")
						.getValue();
				String MSISDN = (String) new AssetBL()
						.find(myEvent.getAsset().getId())
						.getMetaField("MSISDN").getValue();

				if (myEvent.getAssignedTo() != null) {
					List<MetaFieldValue> mfvList = new UserBL()
							.getUserEntity(myEvent.getAssignedTo().getId())
							.getCustomer().getMetaFields();

					for (MetaFieldValue mfv : mfvList) {
						if (mfv.getField().getName().equalsIgnoreCase("ICC")) {
							mfv.setValue(ICC);
						}
						if (mfv.getField().getName().equalsIgnoreCase("IMSI")) {
							mfv.setValue(IMSI);
						}
						if (mfv.getField().getName().equalsIgnoreCase("MSISDN")) {
							mfv.setValue(MSISDN);
						}

					}

				}

				LOG.debug("CBOSS::"+
						 myEvent.getAsset().getAssetStatus().getDescription());

				if (myEvent.getAsset().getAssetStatus().getDescription()
						.equalsIgnoreCase("DISPONIBLE")) {

					ArrayList<AssetAssignment> aaList = new ArrayList<AssetAssignment>();

					for (AssetAssignmentDTO aaDTO : new AssetAssignmentDAS()
							.getAssignmentsForAsset(myEvent.getAsset().getId())) {

						aaList.add(new AssetAssignment(aaDTO.getEndDatetime(),
								aaDTO.getOrderId()));

					}

					Collections.sort(aaList);

					LOG.debug("CBOSS:: FIRST " + aaList.get(0).getOrderId());
					LOG.debug("CBOSS:: LAST "
							+ aaList.get(aaList.size() - 1).getOrderId());
					{

						UserDTO user = new UserDAS().find(new OrderDAS().find(
								aaList.get(aaList.size() - 1).getOrderId())
								.getUserId());

						if (user != null) {
							LOG.debug("CBOSS::" + user.getUserName());

							List<MetaFieldValue> mfvList2 = new UserBL()
									.getUserEntity(user.getId()).getCustomer()
									.getMetaFields();

							for (MetaFieldValue mfv : mfvList2) {
								if (mfv.getField().getName()
										.equalsIgnoreCase("ICC")) {
									mfv.setValue("----");
								}
								if (mfv.getField().getName()
										.equalsIgnoreCase("IMSI")) {
									mfv.setValue("----");
								}
								if (mfv.getField().getName()
										.equalsIgnoreCase("MSISDN")) {
									mfv.setValue("----");
								}

							}
						}
					}

				}

			} else { // CPEs
				LOG.debug(" CBOSS:: SERIAL "
						+ myEvent.getAsset().getItem().getInternalNumber());

				String serialNumber = myEvent.getAsset().getIdentifier();

				String IMEI = (String) new AssetBL()
						.find(myEvent.getAsset().getId()).getMetaField("IMEI")
						.getValue();

				if (myEvent.getAssignedTo() != null) {
					List<MetaFieldValue> mfvList = new UserBL()
							.getUserEntity(myEvent.getAssignedTo().getId())
							.getCustomer().getMetaFields();

					for (MetaFieldValue mfv : mfvList) {
						if (mfv.getField().getName().equalsIgnoreCase("IMEI")) {
							mfv.setValue(IMEI);
						}

					}

				}

				LOG.debug("CBOSS::"
						+ myEvent.getAsset().getAssetStatus().getDescription());

				if (myEvent.getAsset().getAssetStatus().getDescription()
						.equalsIgnoreCase("DISPONIBLE")) {

					ArrayList<AssetAssignment> aaList = new ArrayList<AssetAssignment>();

					for (AssetAssignmentDTO aaDTO : new AssetAssignmentDAS()
							.getAssignmentsForAsset(myEvent.getAsset().getId())) {

						aaList.add(new AssetAssignment(aaDTO.getEndDatetime(),
								aaDTO.getOrderId()));

					}

					Collections.sort(aaList);

					LOG.debug("CBOSS:: FIRST " + aaList.get(0).getOrderId());
					LOG.debug("CBOSS:: LAST "
							+ aaList.get(aaList.size() - 1).getOrderId());
					{

						UserDTO user = new UserDAS().find(new OrderDAS().find(
								aaList.get(aaList.size() - 1).getOrderId())
								.getUserId());

						if (user != null) {
							LOG.debug("CBOSS:: " + user.getUserName());

							List<MetaFieldValue> mfvList2 = new UserBL()
									.getUserEntity(user.getId()).getCustomer()
									.getMetaFields();

							for (MetaFieldValue mfv : mfvList2) {
								if (mfv.getField().getName()
										.equalsIgnoreCase("IMEI")) {
									mfv.setValue("----");
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

	class AssetAssignment implements Comparable<AssetAssignment> {
		private Date endDate;
		private int OrderId;

		public AssetAssignment(Date endDate, int orderId) {
			super();
			this.endDate = endDate;
			OrderId = orderId;
		}

		public Date getEndDate() {
			return endDate;
		}

		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}

		public int getOrderId() {
			return OrderId;
		}

		public void setOrderId(int orderId) {
			OrderId = orderId;
		}

		@Override
		public int compareTo(AssetAssignment o) {
			// TODO Auto-generated method stub
			return getEndDate().compareTo(o.getEndDate());
		}

	}

	@Override
	public Class<Event>[] getSubscribedEvents() {

		return events;
	}

}
