package avanzadagroup.net.plugins;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import avanzadagroup.net.banwire.Oxxo;
import avanzadagroup.net.banwire.Store;
import cboss.BanwireOxxoBarcode;

import com.cboss.util.OsUtils;
import com.sapienter.jbilling.common.FormatLogger;
import com.sapienter.jbilling.server.invoice.db.InvoiceDAS;
import com.sapienter.jbilling.server.invoice.db.InvoiceDTO;
import com.sapienter.jbilling.server.metafields.db.MetaFieldValue;
import com.sapienter.jbilling.server.pluggableTask.PluggableTask;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskException;
import com.sapienter.jbilling.server.process.event.InvoicesGeneratedEvent;
import com.sapienter.jbilling.server.system.event.Event;
import com.sapienter.jbilling.server.system.event.task.IInternalEventsTask;
import com.sapienter.jbilling.server.user.UserBL;

public class InvoiceBarCodeTask extends PluggableTask implements
		IInternalEventsTask {
	private static final Class<Event> events[] = new Class[] { InvoicesGeneratedEvent.class };

	private static final FormatLogger LOG = new FormatLogger(
			Logger.getLogger(InvoiceBarCodeTask.class));

	private Integer customerId;
	private String loginName;
	private String customerPhone;
	private String customerName;
	private String customerEmail;
	private Integer invoiceId;
	private InvoiceDTO invoiceDTO;
	private BigDecimal invoiceTotal;
	
	@Override
	public void process(Event event) throws PluggableTaskException {
		if (event instanceof InvoicesGeneratedEvent) {
			InvoicesGeneratedEvent myEvent = (InvoicesGeneratedEvent) event;
			// InvoicesGeneratedEvent{entityId=10billingProcessId=null,
			// invoiceIds=[1300]}

			for (Integer invoiceId : myEvent.getInvoiceIds()) {
				this.invoiceId = invoiceId;

				getInvoiceValues();

				LOG.debug("CBOSS: generating OXXO BarCode for invoice "
						+ invoiceId);

				generateOXXOBarCode();

				LOG.debug("CBOSS: generating Store BarCode for invoice "
						+ invoiceId);

				generateStoreBarCode();

			}

		} else {
			throw new PluggableTaskException("Cannot process event " + event);

		}
	}

	private void getInvoiceValues() {
		this.invoiceDTO = new InvoiceDAS().find(invoiceId);
		this.invoiceTotal = invoiceDTO.getTotal();
		this.customerId = invoiceDTO.getBaseUser().getCustomer().getId();
		this.loginName = invoiceDTO.getBaseUser().getUserName();

		ArrayList<MetaFieldValue> values = new ArrayList<MetaFieldValue>();
		new UserBL().getCustomerEffectiveAitMetaFieldValues(values, invoiceDTO
				.getBaseUser().getCustomer().getAitTimelineMetaFieldsMap());

		String nombre = "", apellidos = "";

		for (MetaFieldValue mfv : values) {
			// LOG.debug("CBOSS:: mf name " + mfv.getField().getName());
			if (mfv.getField().getName().equalsIgnoreCase("Nombre")) {
				nombre = (String) mfv.getValue();
			} else if (mfv.getField().getName().equalsIgnoreCase("apellidos")) {
				apellidos = (String) mfv.getValue();
			} else if (mfv.getField().getName().equalsIgnoreCase("email")) {
				this.customerEmail = (String) mfv.getValue();
			} else if (mfv.getField().getName().equalsIgnoreCase("Telefono")) {
				this.customerPhone = (String) mfv.getValue();
			}
		}

		this.customerName = nombre + " " + apellidos;

	}

	private void generateOXXOBarCode() {

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("referencia", "" + invoiceId);
		params.put("usuario", "pruebasbw");
		params.put("dias_vigencia", "12");
		params.put("monto", "" + invoiceTotal);
		params.put("url_respuesta", "http://18.219.51.80:8080/openbrm");
		params.put("cliente", customerName);
		params.put("formato", "JSON");
		params.put("email", customerEmail);
		params.put("sendPDF", "FALSE");

		String response = new Oxxo().sendRequest(params);

		LOG.debug("CBOSS:: banwire_oxxo " + response);

		String[] responses = response.split("\\|");

		if (responses[0].equals("200")) {
			try {
				JSONObject jsonObj = new JSONObject(responses[1]);
				
				avanzadagroup.net.dataacess.BanwireOxxoBarcode.write(invoiceId, responses[0], 
						new Integer(jsonObj.getJSONObject("response").getString("id")), 
						jsonObj.getJSONObject("response").getString("barcode"), 
						jsonObj.getJSONObject("response").getString("barcode_img"), 
						jsonObj.getJSONObject("response").getString("referencia"), 
						jsonObj.getJSONObject("response").getString("fecha_vigencia"), 
						jsonObj.getJSONObject("response").getString("monto"));
			} catch (Exception e) {
				LOG.debug("CBOSS:: " + e);
				for (StackTraceElement ste : e.getStackTrace()) {
					LOG.debug("CBOSS:: " + ste);
				}
			}

		}else {
			LOG.debug("CBOSS:: oxxo_banwire " + response);
			
			avanzadagroup.net.dataacess.BanwireOxxoBarcode.write(invoiceId, responses[0],0, "", responses[1], "", "", "");
		}
		
		
		
		

	}

	private void generateStoreBarCode() {
		String body = "{" 
	+ "\"user\":\"pruebasbw\"," 
	+ "\"ord_id\":"	+ this.invoiceId + ",\n" 
	+ "\"ord_amount\":" + this.invoiceTotal + ",\n" 
	+ "\"ord_concept\":\"Pago TConekta\",\n" 
	+ "\"notify_url\":\"http://18.219.51.80:8080/openbrm\",\n" 
	+ "\"customer\":\n"
				+ "{\"user_id\":\"" + this.loginName + "\",\n"
				+ "\"name\":\"un nombre del cliente\",\n"
				+ "\"phone\":\"" + this.customerPhone + "\",\n"
				+ "\"email\":\"" + this.customerEmail + "\"},\n"
				+ "\"items\":["
					+ "{\"quantity\":1,\n"
					+ "\"unit_price\":" + this.invoiceTotal + ",\n"
					+ "\"amount\":" + this.invoiceTotal + ",\n"
					+ "\"description\":\"Factura " + this.invoiceId + "\"\n" 
					+ "}\n" 
				 + "]"
		+ "}";
		
		LOG.debug("CBOSS:: store body " + body);

		String response = new Store().sendRequest(body);

		LOG.debug("CBOSS:: store_banwire "+response);

		String[] responses = response.split("\\|");

		if (responses[0].equals("200")) {
			try {
				JSONObject jsonObj = new JSONObject(responses[1]);
				
				avanzadagroup.net.dataacess.BanwireStoreBarcode.write(invoiceId, 
						responses[0], new Integer(jsonObj.getString("id")),
						jsonObj.getString("barcode"), jsonObj.getString("barcode_img"));

			} catch (Exception e) {
				LOG.debug("CBOSS:: store_banwire error " + e);
				for (StackTraceElement ste : e.getStackTrace()) {
					LOG.debug("CBOSS:: store_banwire ste" + ste);
				}
			}

		} else {
			LOG.debug("CBOSS:: store_banwire " + response);
			avanzadagroup.net.dataacess.BanwireOxxoBarcode.write(invoiceId, responses[0],0, "", responses[1], "", "", "");
		}

	}

	@Override
	public Class<Event>[] getSubscribedEvents() {

		return events;
	}

}
