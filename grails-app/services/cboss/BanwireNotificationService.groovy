package cboss

import java.text.SimpleDateFormat

import javax.jws.WebMethod;
import javax.jws.WebResult;

import org.apache.log4j.Logger;
import org.json.JSONObject

import avanzadagroup.net.altanAPI.responses.ActivationResponse

import com.sapienter.jbilling.common.FormatLogger;
import com.sapienter.jbilling.server.invoice.db.InvoiceDAS
import com.sapienter.jbilling.server.invoice.db.InvoiceDTO
import com.sapienter.jbilling.server.metafields.db.MetaFieldValue
import com.sapienter.jbilling.server.payment.PaymentBL
import com.sapienter.jbilling.server.payment.PaymentDTOEx;
import com.sapienter.jbilling.server.payment.PaymentSessionBean
import com.sapienter.jbilling.server.payment.db.PaymentDAS
import com.sapienter.jbilling.server.payment.db.PaymentDTO
import com.sapienter.jbilling.server.payment.db.PaymentInformationDAS
import com.sapienter.jbilling.server.payment.db.PaymentInformationDTO
import com.sapienter.jbilling.server.payment.db.PaymentInstrumentInfoDAS
import com.sapienter.jbilling.server.payment.db.PaymentInstrumentInfoDTO
import com.sapienter.jbilling.server.payment.db.PaymentMethodDAS
import com.sapienter.jbilling.server.payment.db.PaymentMethodTypeDAS;
import com.sapienter.jbilling.server.payment.db.PaymentMethodTypeDTO
import com.sapienter.jbilling.server.payment.db.PaymentResultDAS
import com.sapienter.jbilling.server.user.db.UserDTO
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;
import com.sapienter.jbilling.server.util.db.CurrencyDAS

import grails.transaction.Transactional

@Transactional
class BanwireNotificationService {
	private static final FormatLogger LOG = new FormatLogger(
		Logger.getLogger(WebServicesSessionSpringBean.class));
	
	
	static expose = ['cxfjax']

	@WebResult
	@WebMethod
	def oxxoPayment(String oxxoPayment) {
		JSONObject jsonObject = new JSONObject(oxxoPayment);
		String plaza = jsonObject.getString("plaza");
		String tienda = jsonObject.getString("tienda");
		String fecha = jsonObject.getString("fecha");
		String hora = jsonObject.getString("hora");
		String cb = jsonObject.getString("cb");
		String referencia = jsonObject.getString("referencia");
		String monto = jsonObject.getString("monto");
		String flag = jsonObject.getString("flag");

		InvoiceDTO invoiceDTO = new InvoiceDAS().find(new Integer(referencia));
		UserDTO userDTO = invoiceDTO.getBaseUser();

		PaymentMethodTypeDTO pmtDTO = null;

		for (pmt in userDTO.getCustomer().getAccountType().getPaymentMethodTypes()) {
			LOG.debug("CBOSS:: 0" + pmt.methodName);
			
			pmt.methodName.equalsIgnoreCase("Banwire_OXXO")? pmtDTO = pmt:{}
		}
		
		
		List<MetaFieldValue> mfvList = new ArrayList();
		
		for (mf in pmtDTO.getMetaFields()){
			LOG.debug("CBOSS:: 1" + mf.getName());
			MetaFieldValue mfv = mf.createValue();
			
			if(mf.name.equalsIgnoreCase("oxxo.referencia")){
				mfv.setValue(referencia);
			} else if (mf.name.equalsIgnoreCase("oxxo.flag")){
				mfv.setValue(flag);
			} else if (mf.name.equalsIgnoreCase("oxxo.fecha")){
				mfv.setValue(fecha);
			} else if (mf.name.equalsIgnoreCase("oxxo.hora")){
				mfv.setValue(hora);
			} else if (mf.name.equalsIgnoreCase("oxxo.monto")){
				mfv.setValue(monto);
			} else if (mf.name.equalsIgnoreCase("oxxo.tienda")){
				mfv.setValue(tienda);
			} else if (mf.name.equalsIgnoreCase("oxxo.cb")){
				mfv.setValue(cb);
			}
			
			mfvList.add(mfv);
			
		}

		PaymentInformationDTO piDTO = new PaymentInformationDTO(1, userDTO, pmtDTO);
		piDTO.setMetaFields(mfvList);
		
		for(mfv in piDTO.getMetaFields()){
			LOG.debug("CBOSS:: 2 " + mfv.field.name + "::" + mfv.value);
		}
		
		new PaymentInformationDAS().save(piDTO);
		
		
		PaymentDTO paymentDTO = new PaymentDAS().create(new BigDecimal(monto), new PaymentMethodDAS().find(16), userDTO.getId(), 1, 
			new PaymentResultDAS().find(1), new CurrencyDAS().find(110));
		
		
		
		paymentDTO.setBalance(new BigDecimal(monto));
		
		paymentDTO.setPaymentDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fecha + " " + hora))
		
		new PaymentDAS().save(paymentDTO);
		
		new PaymentInstrumentInfoDAS().save(new PaymentInstrumentInfoDTO(paymentDTO, new PaymentResultDAS().find(1), new PaymentMethodDAS().find(16), piDTO));
		
		new PaymentSessionBean().applyPayment(paymentDTO, invoiceDTO, true);
		
		//new PaymentSessionBean().applyPayment(new PaymentDTOEx(paymentDTO) , invoiceDTO.id, null);
		
		PaymentBL paymentBL = new PaymentBL(paymentDTO);
		
		paymentBL.createMap(invoiceDTO, paymentDTO.getAmount() );
		
		
		return "success";
		
	}
}
