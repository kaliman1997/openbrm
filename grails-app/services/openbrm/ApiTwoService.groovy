package openbrm

import com.sapienter.jbilling.server.invoice.InvoiceWS
import com.sapienter.jbilling.server.item.ItemDTOEx
import com.sapienter.jbilling.server.item.ItemTypeWS
import com.sapienter.jbilling.server.mediation.MediationConfigurationWS
import com.sapienter.jbilling.server.mediation.MediationProcessWS
import com.sapienter.jbilling.server.mediation.MediationRecordLineWS
import com.sapienter.jbilling.server.mediation.MediationRecordWS
import com.sapienter.jbilling.server.order.OrderLineWS
import com.sapienter.jbilling.server.order.OrderWS
import com.sapienter.jbilling.server.payment.PaymentAuthorizationDTOEx
import com.sapienter.jbilling.server.payment.PaymentWS
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskWS
import com.sapienter.jbilling.server.process.BillingProcessConfigurationWS
import com.sapienter.jbilling.server.process.BillingProcessWS
import com.sapienter.jbilling.server.user.ContactWS
import com.sapienter.jbilling.server.user.CreateResponseWS
import com.sapienter.jbilling.server.user.UserTransitionResponseWS
import com.sapienter.jbilling.server.user.UserWS
import com.sapienter.jbilling.server.user.ValidatePurchaseWS
import com.sapienter.jbilling.server.user.partner.PartnerWS
import com.sapienter.jbilling.server.util.IWebServicesSessionBean
import com.sapienter.jbilling.server.mediation.RecordCountWS
import com.sapienter.jbilling.server.notification.MessageDTO;
import com.sapienter.jbilling.server.util.PreferenceWS
import com.sapienter.jbilling.server.order.OrderProcessWS
import com.sapienter.jbilling.server.user.ContactTypeWS;
import com.sapienter.jbilling.server.process.AgeingWS;
import com.sapienter.jbilling.server.order.OrderPeriodWS
import com.sapienter.jbilling.server.util.CurrencyWS;
import com.sapienter.jbilling.server.user.CompanyWS;
import com.sapienter.jbilling.server.util.db.CurrencyDTO
import com.sapienter.jbilling.server.util.db.CurrencyDAS
import com.sapienter.jbilling.server.user.db.CompanyDTO
import com.sapienter.jbilling.server.user.db.CompanyDAS
import com.sapienter.jbilling.server.user.contact.db.ContactDTO
import com.sapienter.jbilling.server.user.contact.db.ContactDAS
import com.sapienter.jbilling.server.user.contact.db.ContactTypeDTO
import com.sapienter.jbilling.server.user.contact.db.ContactMapDTO
import com.sapienter.jbilling.server.user.contact.db.ContactMapDAS
import com.sapienter.jbilling.server.user.db.UserDTO
import com.sapienter.jbilling.server.user.db.UserDAS
import com.sapienter.jbilling.server.user.db.UserStatusDAS
import com.sapienter.jbilling.server.user.db.SubscriberStatusDAS
import com.sapienter.jbilling.server.user.permisson.db.RoleDTO
import com.sapienter.jbilling.server.user.permisson.db.RoleDAS
import com.sapienter.jbilling.server.util.db.LanguageDTO
import com.sapienter.jbilling.server.util.db.LanguageDAS
import com.sapienter.jbilling.server.util.db.JbillingTableDAS
import com.sapienter.jbilling.common.SessionInternalError
import com.sapienter.jbilling.server.device.DeviceWS
import com.sapienter.jbilling.server.util.MediationErrorWS;
import in.saralam.sbs.server.RateCard.RateWS;
import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService
import javax.jws.WebMethod
import grails.transaction.Transactional
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.grails.cxf.utils.EndpointType
import javax.jws.soap.SOAPBinding

/**
 * Grails managed remote service bean for exported web-services. This bean delegates to
 * the WebServicesSessionBean just like the core JbillingAPI.
 */
@Transactional
@GrailsCxfEndpoint(expose = EndpointType.JAX_WS_WSDL, soap12 = true)
class ApiTwoService  {

	def IWebServicesSessionBean webServicesSession

	//static transactional = true

	//static expose = ['hessian', 'cxfjax', 'httpinvoker']
	//static expose = [ "cxfjax" ]

	public Integer getCallerId() {
		return webServicesSession.getCallerId();
	}

	public Integer getCallerCompanyId() {
		return webServicesSession.getCallerCompanyId();
	}

	public Integer getCallerLanguageId() {
		return webServicesSession.getCallerLanguageId();
	}

	@WebMethod
	@WebResult
	public Integer createUser(UserWS newUser) {
		return webServicesSession.createUser(newUser)
	}

	@WebMethod
	@WebResult
	public Integer getUserId(String username) {
		return webServicesSession.getUserId(username)
	}

	@WebMethod
        @WebResult
	public void updateUserContact(Integer userId, Integer typeId, com.sapienter.jbilling.server.user.ContactWS contact) {
                webServicesSession.updateUserContact(userId, typeId, contact)
        }

	@WebMethod
        @WebResult
	public void updateUser(com.sapienter.jbilling.server.user.UserWS user) {
                webServicesSession.updateUser(user)
        }
	
	@WebMethod
        @WebResult
	public Integer createOrder(OrderWS order) {
                return webServicesSession.createOrder(order)
        }

	@WebMethod
        @WebResult
	public Integer [] createInvoice(Integer userId, boolean onlyRecurring) {
                return webServicesSession.createInvoice(userId, onlyRecurring)
        }

	@WebMethod
        @WebResult
        public Integer applyOrderToInvoice(Integer orderId, InvoiceWS invoiceWs){
                return webServicesSession.applyOrderToInvoice(orderId,invoiceWs)
        }

	@WebMethod
        @WebResult
	public Integer createSubscription(OrderWS newOrder, String aliasName){
                return webServicesSession.createSubscription(newOrder, aliasName)

        }

	@WebMethod
        @WebResult
	public Integer applyPayment(PaymentWS payment, Integer invoiceId) {
                return webServicesSession.applyPayment(payment, invoiceId)
        }

	@WebMethod
        @WebResult
	public com.sapienter.jbilling.server.invoice.InvoiceWS getInvoiceWS(Integer invoiceId) {
                return webServicesSession.getInvoiceWS(invoiceId)
        }
	
	@WebMethod
        @WebResult
	public com.sapienter.jbilling.server.user.UserWS getUserWS(Integer userId) {
                return webServicesSession.getUserWS(userId)
        }

	@WebMethod
        @WebResult
	public com.sapienter.jbilling.server.order.OrderWS getOrder(Integer orderId) {
                return webServicesSession.getOrder(orderId)
        }

	@WebMethod
        @WebResult
	public com.sapienter.jbilling.server.payment.PaymentWS getPayment(Integer paymentId) {
                return webServicesSession.getPayment(paymentId)
        }

	@WebMethod
        @WebResult
 	public Integer createPayment(PaymentWS payment) {
                return webServicesSession.createPayment(payment);
        }
	
	@WebMethod
        @WebResult
	public com.sapienter.jbilling.server.invoice.InvoiceWS getLatestInvoice(Integer userId) {
                return webServicesSession.getLatestInvoice(userId)
        }
	
	@WebMethod
        @WebResult
	public void getCallReport(Integer invoiceId){
		webServicesSession.getCallReport(invoiceId);
	}

	@WebMethod
        @WebResult
	public byte[] getESRPDF(Integer invoiceId){
                return webServicesSession.getESRPDF(invoiceId);
        }

	@WebMethod
        @WebResult
	public byte[] getAgeingESRPDF(Integer userId){
                return webServicesSession.getAgeingESRPDF(userId);
        }	

	@WebMethod
        @WebResult
	public void triggerAgeing(Date runDate) {
                webServicesSession.triggerAgeing(runDate)
        }	

	@WebMethod
        @WebResult
        public Integer addServiceAliasByOrder(Integer orderId, String aliasName) {
                return webServicesSession.addServiceAliasByOrder(orderId, aliasName)
        }
		
	@WebMethod
        @WebResult
	public void triggerAgeingAsync(final Date runDate) {
                webServicesSession.triggerAgeingAsync(runDate)
        }	

	@WebMethod
        @WebResult
	public void deletePayment(Integer paymentId) {
                webServicesSession.deletePayment(paymentId);
        }

	@WebMethod
        @WebResult
        public void removePaymentLink(Integer invoiceId, Integer paymentId) {
                webServicesSession.removePaymentLink (invoiceId, paymentId)
        }

	@WebMethod
        @WebResult
        public void createPaymentLink(Integer invoiceId, Integer paymentId) {
                webServicesSession.createPaymentLink(invoiceId, paymentId);
	}
	
	@WebMethod
        @WebResult
        public com.sapienter.jbilling.server.payment.PaymentAuthorizationDTOEx payInvoice(Integer invoiceId) {
                return webServicesSession.payInvoice(invoiceId)
	}

	@WebMethod
        @WebResult
        public Integer createInvoiceFromOrder(Integer orderId, Integer invoiceId) {
                return webServicesSession.createInvoiceFromOrder(orderId, invoiceId)
        }

	@WebMethod
        @WebResult
        public com.sapienter.jbilling.server.payment.PaymentWS getLatestPayment(Integer userId) {
                return webServicesSession.getLatestPayment(userId)
        }

	@WebMethod
        @WebResult
        public Integer[] getLastPayments(Integer userId, Integer number) {
                return webServicesSession.getLastPayments(userId, number)
        }

	@WebMethod
        @WebResult
	public byte[] getPaperInvoicePDF(Integer invoiceId) {
                return webServicesSession.getPaperInvoicePDF(invoiceId)
        }


}
