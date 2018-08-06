package openbrm

import com.sapienter.jbilling.server.metafields.MetaFieldValueWS
import com.sapienter.jbilling.server.order.OrderChangeWS
import com.sapienter.jbilling.server.order.OrderWS
import com.sapienter.jbilling.server.user.UserWS
import com.sapienter.jbilling.server.util.IWebServicesSessionBean
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.discount.DiscountWS;
import com.sapienter.jbilling.server.mediation.MediationConfigurationWS
import com.sapienter.jbilling.server.mediation.MediationProcessWS
import com.sapienter.jbilling.server.mediation.MediationRecordLineWS
import com.sapienter.jbilling.server.mediation.MediationRecordWS
import com.sapienter.jbilling.server.invoice.InvoiceWS;
import com.sapienter.jbilling.server.item.*;
import com.sapienter.jbilling.server.metafields.MetaFieldGroupWS;
import com.sapienter.jbilling.server.metafields.MetaFieldWS;
import com.sapienter.jbilling.server.notification.MessageDTO;
import com.sapienter.jbilling.server.order.*;
import com.sapienter.jbilling.server.payment.PaymentAuthorizationDTOEx;
import com.sapienter.jbilling.server.payment.PaymentMethodTemplateWS;
import com.sapienter.jbilling.server.payment.PaymentMethodTypeWS;
import com.sapienter.jbilling.server.payment.PaymentWS;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskTypeCategoryWS;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskTypeWS;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskWS;
import com.sapienter.jbilling.server.mediation.RecordCountWS
import com.sapienter.jbilling.server.process.AgeingWS;
import com.sapienter.jbilling.server.process.BillingProcessConfigurationWS;
import com.sapienter.jbilling.server.process.BillingProcessWS;
import com.sapienter.jbilling.server.process.ProcessStatusWS;
import com.sapienter.jbilling.server.user.AccountInformationTypeWS;
import com.sapienter.jbilling.server.user.AccountTypeWS;
import com.sapienter.jbilling.server.user.CompanyWS;
import com.sapienter.jbilling.server.user.ContactWS;
import com.sapienter.jbilling.server.user.CreateResponseWS;
import com.sapienter.jbilling.server.user.CustomerNoteWS;
import com.sapienter.jbilling.server.user.UserCodeWS;
import com.sapienter.jbilling.server.user.UserTransitionResponseWS;
import com.sapienter.jbilling.server.user.UserWS;
import com.sapienter.jbilling.server.user.ValidatePurchaseWS;
import com.sapienter.jbilling.server.user.partner.CommissionProcessConfigurationWS;
import com.sapienter.jbilling.server.user.partner.CommissionProcessRunWS;
import com.sapienter.jbilling.server.user.partner.CommissionWS;
import com.sapienter.jbilling.server.user.partner.PartnerWS;
import com.sapienter.jbilling.server.util.search.SearchCriteria;
import com.sapienter.jbilling.server.item.AssetSearchResult;
import com.sapienter.jbilling.server.util.PreferenceWS;
import com.sapienter.jbilling.server.util.CurrencyWS;
import com.sapienter.jbilling.server.util.LanguageWS;
import com.sapienter.jbilling.server.util.EnumerationWS;
import com.sapienter.jbilling.server.util.MediationErrorWS;

import in.saralam.sbs.server.RateCard.RateWS;
import in.saralam.sbs.server.crm.SupportTicketWS;
import in.saralam.sbs.server.subscription.ServiceWS;
import in.saralam.sbs.server.voucher.VoucherWS;
import in.saralam.sbs.server.voucher.db.VoucherDTO;
import in.saralam.sbs.server.voucher.db.VoucherStatusDTO;
import in.saralam.sbs.server.ConvergentRateCard.CRateWS;

import javax.jws.WebService;
import javax.persistence.criteria.CriteriaBuilder.In;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.sapienter.jbilling.server.util.search.SearchResultString;

import javax.jws.WebMethod
import javax.jws.WebResult

import org.junit.After;


class ApiService implements IWebServicesSessionBean {

	
	def IWebServicesSessionBean webServicesSession

    static transactional = true

    static expose = ['cxfjax']

	
	
	public Integer getCallerId() {
		return webServicesSession.getCallerId();
	}

	public Integer getCallerCompanyId() {
		return webServicesSession.getCallerCompanyId();
	}

	public Integer getCallerLanguageId() {
		return webServicesSession.getCallerLanguageId();
	}
	
	public Integer getCallerCurrencyId() {
	    return webServicesSession.getCallerCurrencyId();
	}

	@WebMethod
	@WebResult
	public PaymentAuthorizationDTOEx[] processPayments(PaymentWS[] payments, Integer invoiceId) {
		return webServicesSession.processPayments(payments, invoiceId)
	}
	
	@WebMethod
	@WebResult
	public OrderWS[] rateOrders(OrderWS[] orders, OrderChangeWS[] orderChanges) {
		return webServicesSession.rateOrders(orders, orderChanges)
	
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
	public UserWS getUserWS(Integer userId) {
		return webServicesSession.getUserWS(userId)
	}
	
	@WebMethod
	@WebResult
	public Integer createUserWithCompanyId(UserWS newUser, Integer entityId) {
		return webServicesSession.createUserWithCompanyId(newUser, entityId)
	}
	
	@WebMethod
	@WebResult
	public void updateUser(UserWS user) {
		 webServicesSession.updateUser(user)
	}
	
	@WebMethod
	@WebResult
	public void updateUserWithCompanyId(UserWS user, Integer entityId) {
		 webServicesSession.updateUserWithCompanyId(user, entityId)
	}
	
	@WebMethod
	@WebResult
	public void deleteUser(Integer userId) {
		 webServicesSession.deleteUser(userId)
	}
	
	@WebMethod
	@WebResult
	public boolean userExistsWithName(String userName) {
		return webServicesSession.userExistsWithName(userName)
	}
	
	@WebMethod
	@WebResult
	public boolean userExistsWithId(Integer userId) {
		return webServicesSession.userExistsWithId(userId)
	}
	
	@WebMethod
	@WebResult
	public ContactWS[] getUserContactsWS(Integer userId)  {
		return webServicesSession.getUserContactsWS(userId)
	}
	
	@WebMethod
	@WebResult
	public void updateUserContact(Integer userId, ContactWS contact)  {
		 webServicesSession.updateUserContact(userId, contact)
	}
	
	@WebMethod
	@WebResult
	public void setAuthPaymentType(Integer userId, Integer autoPaymentType, boolean use)  {
		 webServicesSession.setAuthPaymentType(userId, autoPaymentType, use)
	}
	
	@WebMethod
	@WebResult
	public Integer getAuthPaymentType(Integer userId)  {
		return webServicesSession.getAuthPaymentType(userId)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getUsersByStatus(Integer statusId, boolean inn)  {
		return webServicesSession.getUsersByStatus(statusId, inn)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getUsersInStatus(Integer statusId)  {
		return webServicesSession.getUsersInStatus(statusId)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getUsersNotInStatus(Integer statusId)  {
		return webServicesSession.getUsersNotInStatus(statusId)
	}
	
	@WebMethod
	@WebResult
	public Integer getUserIdByEmail(String email)  {
		return webServicesSession.getUserIdByEmail(email)
	}
	
	@WebMethod
	@WebResult
	public UserTransitionResponseWS[] getUserTransitions(Date from, Date to)  {
		return webServicesSession.getUserTransitions(from, to)
	}
	
	@WebMethod
	@WebResult
	public UserTransitionResponseWS[] getUserTransitionsAfterId(Integer id)  {
		return webServicesSession.getUserTransitionsAfterId(id)
	}
	
	@WebMethod
	@WebResult
	public CreateResponseWS create(UserWS user, OrderWS order, OrderChangeWS[] orderChanges)  {
		return webServicesSession.create(user, order, orderChanges)
	}
	
	@WebMethod
	@WebResult
	public Integer createUserCode(UserCodeWS userCode)  {
		return webServicesSession.createUserCode(userCode)
	}
	
	@WebMethod
	@WebResult
	public UserCodeWS[] getUserCodesForUser(Integer userId)  {
		return webServicesSession.getUserCodesForUser(userId)
	}
	
	@WebMethod
	@WebResult
	public void updateUserCode(UserCodeWS userCode)  {
		 webServicesSession.updateUserCode(userCode)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getCustomersByUserCode(String userCode)  {
		return webServicesSession.getCustomersByUserCode(userCode)
	}
	@WebMethod
	@WebResult
	public Integer[] getOrdersByUserCode(String userCode) {
		return webServicesSession.getOrdersByUserCode(userCode)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getOrdersLinkedToUser(Integer userId) {
		return webServicesSession.getOrdersLinkedToUser(userId)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getCustomersLinkedToUser(Integer userId) {
		return webServicesSession.getCustomersLinkedToUser(userId)
	}
	
	@WebMethod
	@WebResult
	public void resetPassword(int userId) {
		 webServicesSession.resetPassword(userId)
	}
	
	@WebMethod
	@WebResult
	public PartnerWS getPartner(Integer partnerId) {
		return webServicesSession.getPartner(partnerId)
	}
	
	@WebMethod
	@WebResult
	public Integer createPartner(UserWS newUser, PartnerWS partner) {
		return webServicesSession.createPartner(newUser, partner)
	}
	
	@WebMethod
	@WebResult
	public void updatePartner(UserWS newUser, PartnerWS partner) {
		 webServicesSession.updatePartner(newUser, partner)
	}
	
	@WebMethod
	@WebResult
	public void deletePartner (Integer partnerId) {
		 webServicesSession.deletePartner(partnerId)
	}
	
	@WebMethod
	@WebResult
	public ItemTypeWS[] getItemCategoriesByPartner(String partner, boolean parentCategoriesOnly) {
		return webServicesSession.getItemCategoriesByPartner(partner, parentCategoriesOnly)
	}
	
	@WebMethod
	@WebResult
	public ItemTypeWS[] getChildItemCategories(Integer itemTypeId) {
		return webServicesSession.getChildItemCategories(itemTypeId)
	}
	
	@WebMethod
	@WebResult
	public ItemDTOEx getItem(Integer itemId, Integer userId, String pricing) {
		return webServicesSession.getItem(itemId, userId, pricing)
	}
	
	@WebMethod
	@WebResult
	public ItemDTOEx[] getAllItems() {
		return webServicesSession.getAllItems()
	}
	
	@WebMethod
	@WebResult
	public Integer createItem(ItemDTOEx item) {
		return webServicesSession.createItem(item)
	}
	
	@WebMethod
	@WebResult
	public void updateItem(ItemDTOEx item) {
		 webServicesSession.updateItem(item)
	}
	
	@WebMethod
	@WebResult
	public void deleteItem(Integer itemId) {
		 webServicesSession.deleteItem(itemId)
	}
	
	@WebMethod
	@WebResult
	public ItemDTOEx[] getAddonItems(Integer itemId) {
		return webServicesSession.getAddonItems(itemId)
	}
	
	@WebMethod
	@WebResult
	public ItemDTOEx[] getItemByCategory(Integer itemTypeId) {
		return webServicesSession.getItemByCategory(itemTypeId)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getUserItemsByCategory(Integer userId, Integer categoryId) {
		return webServicesSession.getUserItemsByCategory(userId, categoryId)
	}
	
	@WebMethod
	@WebResult
	public ItemTypeWS getItemCategoryById(Integer id) {
		return webServicesSession.getItemCategoryById(id)
	}
	
	/*@WebMethod
	@WebResult
	public ItemTypeWS[] getAllItemCategories() {
		return webServicesSession.getAllItemCategories()
	}*/
	
	@WebMethod
	@WebResult
	public Integer createItemCategory(ItemTypeWS itemType) {
		return webServicesSession.createItemCategory(itemType)
	}
	
	@WebMethod
	@WebResult
	public void updateItemCategory(ItemTypeWS itemType) {
		webServicesSession.updateItemCategory(itemType)
	}
	
	@WebMethod
	@WebResult
	public void deleteItemCategory(Integer itemCategoryId) {
	    webServicesSession.deleteItemCategory(itemCategoryId)
	}
	
	/*@WebMethod
	@WebResult
	public ItemTypeWS[] getAllItemCategoriesByEntityId(Integer entityId) {
		return webServicesSession.getAllItemCategoriesByEntityId(entityId)
	}*/
	
	@WebMethod
	@WebResult
	public ItemDTOEx[] getAllItemsByEntityId(Integer entityId) {
		return webServicesSession.getAllItemsByEntityId(entityId)
	}
	
	@WebMethod
	@WebResult
	public String isUserSubscribedTo(Integer userId, Integer itemId) {
		return webServicesSession.isUserSubscribedTo(userId, itemId)
	}
	
	/*@WebMethod
	@WebResult
	public Integer[] getUserItemsByCategory(Integer userId, Integer categoryId) {
		return webServicesSession.getUserItemsByCategory(userId, categoryId)
	}*/
	
	/*@WebMethod
	@WebResult
	public ItemTypeWS getItemCategoryById(Integer id) {
		return webServicesSession.getItemCategoryById(id)
	}*/
	
	@WebMethod
	@WebResult
	public ItemTypeWS[] getAllItemCategories() {
		return webServicesSession.getAllItemCategories()
	}
	
	/*@WebMethod
	@WebResult
	public Integer createItemCategory(ItemTypeWS itemType) {
		return webServicesSession.createItemCategory(itemType)
	}*/
	
	/*@WebMethod
	@WebResult
	public void updateItemCategory(ItemTypeWS itemType) {
		 webServicesSession.updateItemCategory(itemType)
	}*/
	
	/*@WebMethod
	@WebResult
	public void deleteItemCategory(Integer itemCategoryId) {
		webServicesSession.deleteItemCategory(itemCategoryId)
	}*/
	
	@WebMethod
	@WebResult
	public ItemTypeWS[] getAllItemCategoriesByEntityId(Integer entityId) {
		return webServicesSession.getAllItemCategoriesByEntityId(entityId)
	}
	
	/*@WebMethod
	@WebResult
	public ItemDTOEx[] getAllItemsByEntityId(Integer entityId) {
		return webServicesSession.getAllItemsByEntityId(entityId)
	}*/
	
	/*@WebMethod
	@WebResult
	public String isUserSubscribedTo(Integer userId, Integer itemId) {
		return webServicesSession.isUserSubscribedTo(userId, itemId)
	}*/
	
	@WebMethod
	@WebResult
	public InvoiceWS getLatestInvoiceByItemType(Integer userId, Integer itemTypeId) {
		return webServicesSession.getLatestInvoiceByItemType(userId, itemTypeId)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getLastInvoicesByItemType(Integer userId, Integer itemTypeId, Integer number) {
		return webServicesSession.getLastInvoicesByItemType(userId, itemTypeId, number)
	}
	
	@WebMethod
	@WebResult
	public OrderWS getLatestOrderByItemType(Integer userId, Integer itemTypeId) {
		return webServicesSession.getLatestOrderByItemType(userId, itemTypeId)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getLastOrdersByItemType(Integer userId, Integer itemTypeId, Integer number) {
		return webServicesSession.getLastOrdersByItemType(userId, itemTypeId, number)
	}
	
	@WebMethod
	@WebResult
	public ValidatePurchaseWS validatePurchase(Integer userId, Integer itemId, String fields) {
		return webServicesSession.validatePurchase(userId, itemId, fields)
	}
	
	@WebMethod
	@WebResult
	public ValidatePurchaseWS validateMultiPurchase(Integer userId, Integer[] itemId, String[] fields) {
		return webServicesSession.validateMultiPurchase(userId, itemId, fields)
	}
	
	@WebMethod
	@WebResult
	public Integer getItemID(String productCode) {
		return webServicesSession.getItemID(productCode)
	}
	
	@WebMethod
	@WebResult
	public OrderWS getOrder(Integer orderId) {
		return webServicesSession.getOrder(orderId)
	}
	
	@WebMethod
	@WebResult
	public Integer createOrder(OrderWS order, OrderChangeWS[] orderChanges) {
		return webServicesSession.createOrder(order, orderChanges)
	}
	
	@WebMethod
	@WebResult
	public void updateOrder(OrderWS order, OrderChangeWS[] orderChanges) {
		webServicesSession.updateOrder(order, orderChanges)
	}
	
	@WebMethod
	@WebResult
	public Integer createUpdateOrder(OrderWS order, OrderChangeWS[] orderChanges) {
		return webServicesSession.createUpdateOrder(order, orderChanges)
	}
	
	@WebMethod
	@WebResult
	public String deleteOrder(Integer id) {
		return webServicesSession.deleteOrder(id)
	}
	
	@WebMethod
	@WebResult
	public Integer createOrderAndInvoice(OrderWS order, OrderChangeWS[] orderChanges) {
		return webServicesSession.createOrderAndInvoice(order, orderChanges)
	}
	
	@WebMethod
	@WebResult
	public OrderWS getCurrentOrder(Integer userId, Date date) {
		return webServicesSession.getCurrentOrder(userId, date)
	}
	
	@WebMethod
	@WebResult
	public OrderWS updateCurrentOrder(Integer userId, OrderLineWS[] lines, String pricing, Date date, String eventDescription) {
		return webServicesSession.updateCurrentOrder(userId, lines, pricing, date, eventDescription)
	}
	
	@WebMethod
	@WebResult
	public OrderLineWS getOrderLine(Integer orderLineId) {
		return webServicesSession.getOrderLine(orderLineId)
	}
	
	@WebMethod
	@WebResult
	public void updateOrderLine(OrderLineWS line) {
		webServicesSession.updateOrderLine(line)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getOrderByPeriod(Integer userId, Integer periodId) {
		return webServicesSession.getOrderByPeriod(userId, periodId)
	}
	
	@WebMethod
	@WebResult
	public OrderWS getLatestOrder(Integer userId) {
		return webServicesSession.getLatestOrder(userId)
	}
	
	@WebMethod
	@WebResult
	public List<MediationErrorWS> getUsageErrors(String fromDate, String toDate, int limit){
		return webServicesSession.getUsageErrors(fromDate, toDate, limit)
	}
	@WebMethod
	@WebResult
	public OrderWS[] getUserSubscriptions(Integer userId) {
		return webServicesSession.getUserSubscriptions(userId)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getLastOrders(Integer userId, Integer number) {
		return webServicesSession.getLastOrders(userId, number)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getOrdersByDate (Integer userId, Date since, Date until) {
		return webServicesSession.getOrdersByDate(userId, since, until)
	}
	
	@WebMethod
	@WebResult
	public void triggerMediationAsync() {
		webServicesSession.triggerMediationAsync()
	}
	@WebMethod
	@WebResult
	public OrderWS[] getUserOrdersPage(Integer user, Integer limit, Integer offset) {
		return webServicesSession.getUserOrdersPage(user, limit, offset)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getLastOrdersPage(Integer userId, Integer limit, Integer offset) {
		return webServicesSession.getLastOrdersPage(user, limit, offset)
	}
	
	@WebMethod
	@WebResult
	public OrderWS rateOrder(OrderWS order, OrderChangeWS[] orderChanges) {
		return webServicesSession.rateOrder(order, orderChanges)
	}

	@WebMethod
	@WebResult
	public boolean updateOrderPeriods(OrderPeriodWS[] orderPeriods) {
		return webServicesSession.updateOrderPeriods(orderPeriods)
	}
	
	@WebMethod
	@WebResult
	public boolean updateOrCreateOrderPeriod(OrderPeriodWS orderPeriod) {
		return webServicesSession.updateOrCreateOrderPeriod(orderPeriod)
	}
	
	@WebMethod
	@WebResult
	public boolean deleteOrderPeriod(Integer periodId) {
		return webServicesSession.deleteOrderPeriod(periodId)
	}
	
	@WebMethod
	@WebResult
	public PaymentAuthorizationDTOEx createOrderPreAuthorize(OrderWS order, OrderChangeWS[] orderChanges) {
		return webServicesSession.createOrderPreAuthorize(order, orderChanges)
	}
	
	@WebMethod
	@WebResult
	public OrderPeriodWS[] getOrderPeriods() {
		return webServicesSession.getOrderPeriods()
	}
	
	/*
	   Mediation process
	*/

	void triggerMediation() {
		webServicesSession.triggerMediation()
	}
	@WebMethod
	@WebResult
	boolean isMediationProcessing() {
		return webServicesSession.isMediationProcessing()
	}
	@WebMethod
	@WebResult
	public List<MediationProcessWS> getAllMediationProcesses() {
		return webServicesSession.getAllMediationProcesses()
	}
	@WebMethod
	@WebResult
	public List<MediationRecordLineWS> getMediationEventsForOrder(Integer orderId) {
		return webServicesSession.getMediationEventsForOrder(orderId)
	}
	@WebMethod
	@WebResult
	public List<MediationRecordLineWS> getMediationEventsForInvoice(Integer invoiceId) {
		return webServicesSession.getMediationEventsForInvoice(invoiceId);
	}
	@WebMethod
	@WebResult
	public List<MediationRecordWS> getMediationRecordsByMediationProcess(Integer mediationProcessId) {
		return webServicesSession.getMediationRecordsByMediationProcess(mediationProcessId)
	}
	@WebMethod
	@WebResult
	public List<RecordCountWS> getNumberOfMediationRecordsByStatuses() {
		return webServicesSession.getNumberOfMediationRecordsByStatuses()
	}
	@WebMethod
	@WebResult
	public List<MediationConfigurationWS> getAllMediationConfigurations() {
		return webServicesSession.getAllMediationConfigurations()
	}
	@WebMethod
	@WebResult
	public void createMediationConfiguration(MediationConfigurationWS cfg) {
		webServicesSession.createMediationConfiguration(cfg)
	}
	@WebMethod
	@WebResult
	public List<Integer> updateAllMediationConfigurations(List<MediationConfigurationWS> configurations) {
		return webServicesSession.updateAllMediationConfigurations(configurations)
	}
	@WebMethod
	@WebResult
	public void deleteMediationConfiguration(Integer cfgId) {
		webServicesSession.deleteMediationConfiguration(cfgId)
	}
	
	@WebMethod
	@WebResult
	public OrderPeriodWS getOrderPeriodWS(Integer orderPeriodId) {
		return webServicesSession.getOrderPeriodWS(orderPeriodId)
	}
	
	@WebMethod
	@WebResult
	public void updateOrders(OrderWS[] orders, OrderChangeWS[] orderChanges) {
		webServicesSession.updateOrders(orders, orderChanges)
	}
	
	@WebMethod
	@WebResult
	public Integer createAccountType(AccountTypeWS accountType) {
		return webServicesSession.createAccountType(accountType)
	}
	
	@WebMethod
	@WebResult
	public AccountTypeWS getAccountType(Integer accountTypeId) {
		return webServicesSession.getAccountType(accountTypeId)
	}
	
	
	@WebMethod
	@WebResult
	public boolean updateAccountType(AccountTypeWS accountType) {
		return webServicesSession.updateAccountType(accountType)
	}
	
	@WebMethod
	@WebResult
	 public boolean deleteAccountType(Integer accountTypeId) {
		return webServicesSession.deleteAccountType(accountTypeId)
	}
	
	@WebMethod
	@WebResult
	public AccountTypeWS[] getAllAccountTypes() {
		return webServicesSession.getAllAccountTypes()
	}
	
	@WebMethod
	@WebResult
	public AccountInformationTypeWS[] getInformationTypesForAccountType(Integer accountTypeId) {
		return webServicesSession.getInformationTypesForAccountType(accountTypeId)
	}
	
	@WebMethod
	@WebResult
	public Integer createAccountInformationType(AccountInformationTypeWS accountInformationType) {
		return webServicesSession.createAccountInformationType(accountInformationType)
	}
	
	@WebMethod
	@WebResult
	public void updateAccountInformationType(AccountInformationTypeWS accountInformationType) {
		webServicesSession.updateAccountInformationType(accountInformationType)
	}
	
	@WebMethod
	@WebResult
	public boolean deleteAccountInformationType(Integer accountInformationTypeId) {
		return webServicesSession.deleteAccountInformationType(accountInformationTypeId)
	}
	
	@WebMethod
	@WebResult
	public AccountInformationTypeWS getAccountInformationType(Integer accountInformationType) {
		return webServicesSession.getAccountInformationType(accountInformationType)
	}
	
	@WebMethod
	@WebResult
	public OrderWS[] getLinkedOrders(Integer primaryOrderId) {
		return webServicesSession.getLinkedOrders(primaryOrderId)
	}
	
	@WebMethod
	@WebResult
	public Integer createOrderPeriod(OrderPeriodWS orderPeriod) {
		return webServicesSession.createOrderPeriod(orderPeriod)
	}
	
	@WebMethod
	@WebResult
	public InvoiceWS getInvoiceWS(Integer invoiceId) {
		return webServicesSession.getInvoiceWS(invoiceId)
	}
	
	@WebMethod
	@WebResult
	public Integer[] createInvoice(Integer userId, boolean onlyRecurring) {
		return webServicesSession.createInvoice(userId, onlyRecurring)
	}
	
	@WebMethod
	@WebResult
	public Integer[] createInvoiceWithDate(Integer userId, Date billingDate, Integer dueDatePeriodId, Integer dueDatePeriodValue, boolean onlyRecurring) {
		return webServicesSession.createInvoiceWithDate(userId, billingDate, dueDatePeriodId, dueDatePeriodValue, onlyRecurring)
	}
	
	@WebMethod
	@WebResult
	public Integer createInvoiceFromOrder(Integer orderId, Integer invoiceId) {
		return webServicesSession.createInvoiceFromOrder(orderId, invoiceId)
	}
	
	@WebMethod
	@WebResult
	public Integer applyOrderToInvoice(Integer orderId, InvoiceWS invoiceWs) {
		return webServicesSession.applyOrderToInvoice(orderId, invoiceWs)
	}
	
	@WebMethod
	@WebResult
	public void deleteInvoice(Integer invoiceId) {
		webServicesSession.deleteInvoice(invoiceId)
	}
	
	@WebMethod
	@WebResult
	public Integer saveLegacyInvoice(InvoiceWS invoiceWS) {
		return webServicesSession.saveLegacyInvoice(invoiceWS)
	}
	
	@WebMethod
	@WebResult
	public Integer saveLegacyPayment(PaymentWS paymentWS) {
		return webServicesSession.saveLegacyPayment(paymentWS)
	}
	
	@WebMethod
	@WebResult
	public Integer saveLegacyOrder(OrderWS orderWS) {
		return webServicesSession.saveLegacyOrder(orderWS)
	}
	
	@WebMethod
	@WebResult
	public InvoiceWS[] getAllInvoicesForUser(Integer userId) {
		return webServicesSession.getAllInvoicesForUser(userId)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getAllInvoices(Integer userId) {
		return webServicesSession.getAllInvoices(userId)
	}
	
	@WebMethod
	@WebResult
	public InvoiceWS getLatestInvoice(Integer userId) {
		return webServicesSession.getLatestInvoice(userId)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getLastInvoices(Integer userId, Integer number) {
		return webServicesSession.getLastInvoices(userId, number)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getInvoicesByDate(String since, String until) {
		return webServicesSession.getInvoicesByDate(since, until)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getUserInvoicesByDate(Integer userId, String since, String until) {
		return webServicesSession.getUserInvoicesByDate(userId, since, until)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getUnpaidInvoices(Integer userId) {
		return webServicesSession.getUnpaidInvoices(userId)
	}
	
	@WebMethod
	@WebResult
	public InvoiceWS[] getUserInvoicesPage(Integer userId, Integer limit, Integer offset) {
		return webServicesSession.getUserInvoicesPage(userId, limit, offset)
	}
	
	@WebMethod
	@WebResult
	public byte[] getPaperInvoicePDF(Integer invoiceId) {
		return webServicesSession.getPaperInvoicePDF(invoiceId)
	}
	
	@WebMethod
	@WebResult
	public boolean notifyInvoiceByEmail(Integer invoiceId) {
		return webServicesSession.notifyInvoiceByEmail(invoiceId)
	}
	
	@WebMethod
	@WebResult
	public boolean notifyPaymentByEmail(Integer paymentId) {
		return webServicesSession.notifyPaymentByEmail(paymentId)
	}
	
	@WebMethod
	@WebResult
	public PaymentWS getPayment(Integer paymentId) {
		return webServicesSession.getPayment(paymentId)
	}
	
	@WebMethod
	@WebResult
	public PaymentWS getLatestPayment(Integer userId) {
		return webServicesSession.getLatestPayment(userId)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getLastPayments(Integer userId, Integer number) {
		return webServicesSession.getLastPayments(userId, number)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getLastPaymentsPage(Integer userId, Integer limit, Integer offset) {
		return webServicesSession.getLastPaymentsPage(userId, limit, offset)
	}
	
	@WebMethod
	@WebResult
	public Integer[] getPaymentsByDate(Integer userId, Date since, Date until) {
		return webServicesSession.getPaymentsByDate(userId, since, until)
	}
	
	@WebMethod
	@WebResult
	public BigDecimal getTotalRevenueByUser (Integer userId) {
		return webServicesSession.getTotalRevenueByUser(userId)
	}
	
	@WebMethod
	@WebResult
	public PaymentWS getUserPaymentInstrument(Integer userId) {
		return webServicesSession.getUserPaymentInstrument(userId)
	}
	
	@WebMethod
	@WebResult
	public PaymentWS[] getUserPaymentsPage(Integer userId, Integer limit, Integer offset) {
		return webServicesSession.getUserPaymentsPage(userId, limit, offset)
	}
	
	
	@WebMethod
	@WebResult
	public Integer createPayment(PaymentWS payment) {
		return webServicesSession.createPayment(payment)
	}
	
	@WebMethod
	@WebResult
	public void updatePayment(PaymentWS payment) {
		webServicesSession.updatePayment(payment)
	}
	
	@WebMethod
	@WebResult
	public void deletePayment(Integer paymentId) {
		webServicesSession.deletePayment(paymentId)
	}
	
	@WebMethod
	@WebResult
	public void removePaymentLink(Integer invoiceId, Integer paymentId) {
		webServicesSession.removePaymentLink(invoiceId, paymentId)
	}
	
	
	@WebMethod
	@WebResult
	public void createPaymentLink(Integer invoiceId, Integer paymentId) {
		webServicesSession.createPaymentLink(invoiceId, paymentId)
	}
	
	@WebMethod
	@WebResult
	public void removeAllPaymentLinks(Integer paymentId) {
		webServicesSession.removeAllPaymentLinks(paymentId)
	}
	
	
	@WebMethod
	@WebResult
	public PaymentAuthorizationDTOEx payInvoice(Integer invoiceId) {
		return webServicesSession.payInvoice(invoiceId)
	}
	
	@WebMethod
	@WebResult
	public Integer applyPayment(PaymentWS payment, Integer invoiceId) {
		return webServicesSession.applyPayment(payment, invoiceId)
	}
	
	@WebMethod
	@WebResult
	public PaymentAuthorizationDTOEx processPayment(PaymentWS payment, Integer invoiceId) {
		return webServicesSession.processPayment(payment, invoiceId)
	}
	

	
	@WebMethod
	@WebResult
	public Integer[] createPayments(PaymentWS[] payment) {
		return webServicesSession.createPayments(payment)
	}
	
	@WebMethod
	@WebResult
	public boolean isBillingRunning(Integer entityId) {
		return webServicesSession.isBillingRunning(entityId)
	}
	
    @WebMethod
    @WebResult
    public ProcessStatusWS getBillingProcessStatus() {
		return webServicesSession.getBillingProcessStatus()
	}
	
	@WebMethod
	@WebResult
	public void triggerBillingAsync(final Date runDate){
		webServicesSession.triggerBillingAsync(runDate)
	}
	

    @WebMethod
	@WebResult
	public boolean triggerBilling(Date runDate){
	    return webServicesSession.triggerBilling(runDate)
	}


    @WebMethod
	@WebResult
	public void triggerAgeing(Date runDate){
		webServicesSession.triggerAgeing(runDate)
	}

    @WebMethod
	@WebResult
	public void triggerCollectionsAsync (final Date runDate){
		webServicesSession.triggerCollectionsAsync(runDate)
	}

    @WebMethod
	@WebResult
	public boolean isAgeingProcessRunning(){
		returnwebServicesSession.isAgeingProcessRunning()
	}

    @WebMethod
	@WebResult
	public ProcessStatusWS getAgeingProcessStatus(){
		return webServicesSession.getAgeingProcessStatus()
	}


    @WebMethod
	@WebResult
	public BillingProcessConfigurationWS getBillingProcessConfiguration() {
		return webServicesSession.getBillingProcessConfiguration()
	}

    @WebMethod
	@WebResult
	public Integer createUpdateBillingProcessConfiguration(BillingProcessConfigurationWS ws) {
		return webServicesSession.createUpdateBillingProcessConfiguration(ws)
	}


    @WebMethod
	@WebResult
	public Integer createUpdateCommissionProcessConfiguration(CommissionProcessConfigurationWS ws) {
		return webServicesSession.createUpdateCommissionProcessConfiguration(ws)
	}

    @WebMethod
	@WebResult
	public void calculatePartnerCommissions() {
		webServicesSession.calculatePartnerCommissions()
	}

    @WebMethod
	@WebResult
	public void calculatePartnerCommissionsAsync() {
		webServicesSession.calculatePartnerCommissionsAsync()
	}

    @WebMethod
	@WebResult
	public boolean isPartnerCommissionRunning(){
		return webServicesSession.isPartnerCommissionRunning()
	}

    @WebMethod
	@WebResult
	public CommissionProcessRunWS[] getAllCommissionRuns() {
		return webServicesSession.getAllCommissionRuns()
	}

    @WebMethod
	@WebResult
	public CommissionWS[] getCommissionsByProcessRunId(Integer processRunId) {
		return webServicesSession.getCommissionsByProcessRunId(processRunId)
	}


    @WebMethod
	@WebResult
	public BillingProcessWS getBillingProcess(Integer processId){
		return webServicesSession.getBillingProcess(processId)
	}

    @WebMethod
	@WebResult
	public Integer getLastBillingProcess() {
		return webServicesSession.getLastBillingProcess()
	}

    
    @WebMethod
	@WebResult
	public OrderProcessWS[] getOrderProcesses(Integer orderId){
		return webServicesSession.getOrderProcesses(orderId)
	}

    @WebMethod
	@WebResult
	public OrderProcessWS[] getOrderProcessesByInvoice(Integer invoiceId){
		return webServicesSession.getOrderProcessesByInvoice(invoiceId)
	}


    @WebMethod
	@WebResult
	public BillingProcessWS getReviewBillingProcess(){
		return webServicesSession.getReviewBillingProcess()
	}

    @WebMethod
	@WebResult
	public BillingProcessConfigurationWS setReviewApproval(Boolean flag) {
		return webServicesSession.setReviewApproval(flag)
	}


    @WebMethod
	@WebResult
	public Integer[] getBillingProcessGeneratedInvoices(Integer processId){
		return webServicesSession.getBillingProcessGeneratedInvoices(processId)
	}


    @WebMethod
	@WebResult
	public AgeingWS[] getAgeingConfiguration(Integer languageId)  {
		return webServicesSession.getAgeingConfiguration(languageId)
	}

    @WebMethod
	@WebResult
	public void saveAgeingConfiguration(AgeingWS[] steps, Integer languageId) {
		webServicesSession.saveAgeingConfiguration(steps, languageId)
	}


    /*
        Preferences
     */

    @WebMethod
	@WebResult
	public void updatePreferences(PreferenceWS[] prefList){
		webServicesSession.updatePreferences(prefList)
	}

    @WebMethod
	@WebResult
	public void updatePreference(PreferenceWS preference){
		webServicesSession.updatePreference(preference)
	}

    @WebMethod
	@WebResult
	public PreferenceWS getPreference(Integer preferenceTypeId){
		return webServicesSession.getPreference(preferenceTypeId)
	}


    /*
        Currencies
     */

    @WebMethod
	@WebResult
	public CurrencyWS[] getCurrencies(){
		return webServicesSession.getCurrencies()
	}

    @WebMethod
	@WebResult
	public void updateCurrencies(CurrencyWS[] currencies){
		webServicesSession.updateCurrencies(currencies)
	}

    @WebMethod
	@WebResult
	public void updateCurrency(CurrencyWS currency){
		webServicesSession.updateCurrency(currency)
	}

    @WebMethod
	@WebResult
	public Integer createCurrency(CurrencyWS currency){
		return webServicesSession.createCurrency(currency)
	}

    @WebMethod
	@WebResult
	public boolean deleteCurrency(Integer currencyId){
		return webServicesSession.deleteCurrency(currencyId)
	}


    @WebMethod
	@WebResult
	public CompanyWS getCompany(){
		return webServicesSession.getCompany()
	}

    @WebMethod
	@WebResult
	public void updateCompany(CompanyWS companyWS){
		webServicesSession.updateCompany(companyWS)
	}

    
    /*
        Notifications
    */

    @WebMethod
	@WebResult
	public void createUpdateNotification(Integer messageId, MessageDTO dto){
		webServicesSession.createUpdateNotification(messageId, dto)
	}



    /*
        Plug-ins
     */

    @WebMethod
	@WebResult
	public PluggableTaskWS getPluginWS(Integer pluginId){
		return webServicesSession.getPluginWS(pluginId)
	}

    @WebMethod
	@WebResult
	public Integer createPlugin(PluggableTaskWS plugin){
		return webServicesSession.createPlugin(plugin)
	}

    @WebMethod
	@WebResult
	public void updatePlugin(PluggableTaskWS plugin){
		webServicesSession.updatePlugin(plugin)
	}

    @WebMethod
	@WebResult
	public void deletePlugin(Integer plugin){
		webServicesSession.deletePlugin(plugin)
	}
	
	public Integer uploadRates(List<RateWS>  rates)  {
		return webServicesSession.uploadRates(rates);
	}
	
	
	public Integer uploadCrates(List<CRateWS>  crates)  {
		return webServicesSession.uploadCrates(crates);
	}


	/*
	 * Quartz jobs
	 */
	@WebMethod
	@WebResult
	public void rescheduleScheduledPlugin(Integer pluginId){
		webServicesSession.rescheduleScheduledPlugin(pluginId)
	}

    @WebMethod
	@WebResult
	public void unscheduleScheduledPlugin(Integer pluginId){
		webServicesSession.unscheduleScheduledPlugin(pluginId)
	}


    @WebMethod
	@WebResult
	public Usage getItemUsage(Integer excludedOrderId, Integer itemId, Integer owner, List<Integer> userIds , Date startDate, Date endDate){
		return webServicesSession.getItemUsage(excludedOrderId, itemId, owner, userIds, startDate, endDate)
	}


    @WebMethod
	@WebResult
	public void createCustomerNote(CustomerNoteWS note){
		webServicesSession.createCustomerNote(note)
	}

    /*
     * Assets
     */

    @WebMethod
	@WebResult
	public Integer createAsset(AssetWS asset) {
		return webServicesSession.createAsset(asset)
	}

    @WebMethod
	@WebResult
	public void updateAsset(AssetWS asset) {
		webServicesSession.updateAsset(asset)
	}

    @WebMethod
	@WebResult
	public AssetWS getAsset(Integer assetId){
		return webServicesSession.getAsset(assetId)
	}

    @WebMethod
	@WebResult
	public AssetWS getAssetByIdentifier(String assetIdentifier){
		return webServicesSession.getAssetByIdentifier(assetIdentifier)
	}

    @WebMethod
	@WebResult
	public void deleteAsset(Integer assetId) {
		webServicesSession.deleteAsset(assetId)
	}

    @WebMethod
	@WebResult
	public Integer[] getAssetsForCategory(Integer categoryId){
		return webServicesSession.getAssetsForCategory(categoryId)
	}

    @WebMethod
	@WebResult
	public Integer[] getAssetsForItem(Integer itemId) {
		return webServicesSession.getAssetsForItem(itemId)
	}

    @WebMethod
	@WebResult
	public AssetTransitionDTOEx[] getAssetTransitions(Integer assetId){
		return webServicesSession.getAssetTransitions(assetId)
	}

    @WebMethod
	@WebResult
	public Long startImportAssetJob(int itemId, String identifierColumnName, String notesColumnName,String globalColumnName,String entitiesColumnName, String sourceFilePath, String errorFilePath) {
		return webServicesSession.startImportAssetJob(itemId, identifierColumnName, notesColumnName, globalColumnName, entitiesColumnName, sourceFilePath, errorFilePath)
	}

    @WebMethod
	@WebResult
	public AssetSearchResult findAssets(int productId, SearchCriteria criteria) {
		return webServicesSession.findAssets(productId, criteria)
	}

    @WebMethod
	@WebResult
	public AssetWS[] findAssetsByProductCode(String productCode) {
		return webServicesSession.findAssetsByProductCode(productCode)
	}

    @WebMethod
	@WebResult
	public AssetStatusDTOEx[] findAssetStatuses(String identifier) {
		return webServicesSession.findAssetStatuses(identifier)
	}

    @WebMethod
	@WebResult
	public AssetWS findAssetByProductCodeAndIdentifier(String productCode, String identifier) {
		return webServicesSession.findAssetByProductCodeAndIdentifier(productCode, identifier)
	}

    @WebMethod
	@WebResult
	public AssetWS[] findAssetsByProductCodeAndStatus(String productCode, Integer assetStatusId) {
		return webServicesSession.findAssetsByProductCodeAndStatus(productCode, assetStatusId)
	}


    @WebMethod
	@WebResult
	public Integer reserveAsset(Integer assetId, Integer userId){
		return webServicesSession.reserveAsset(assetId, userId)
	}

    @WebMethod
	@WebResult
	public void releaseAsset(Integer assetId, Integer userId){
		webServicesSession.releaseAsset(assetId, userId)
	}


	@WebMethod
	@WebResult
	public AssetAssignmentWS[] getAssetAssignmentsForAsset(Integer assetId){
		return webServicesSession.getAssetAssignmentsForAsset(assetId)
	}

	@WebMethod
	@WebResult
	public AssetAssignmentWS[] getAssetAssignmentsForOrder(Integer orderId){
		return webServicesSession.getAssetAssignmentsForOrder(orderId)
	}

	@WebMethod
	@WebResult
	public Integer findOrderForAsset(Integer assetId, Date date){
		return webServicesSession.findOrderForAsset(assetId, date)
	}

	@WebMethod
	@WebResult
	public Integer[] findOrdersForAssetAndDateRange(Integer assetId, Date startDate, Date endDate){
		return webServicesSession.findOrdersForAssetAndDateRange(assetId, startDate, endDate)
	}

    @WebMethod
	@WebResult
	public List<AssetWS> getAssetsByUserId(Integer userId){
		return webServicesSession.getAssetsByUserId(userId)
	}


    /*
     *  MetaField Group
     */
    
    @WebMethod
	@WebResult
	public Integer createMetaFieldGroup(MetaFieldGroupWS metafieldGroup){
		return webServicesSession.createMetaFieldGroup(metafieldGroup)
	}

	@WebMethod
	@WebResult
	public void updateMetaFieldGroup(MetaFieldGroupWS metafieldGroupWs){
		webServicesSession.updateMetaFieldGroup(metafieldGroupWs)
	}

	@WebMethod
	@WebResult
	public void deleteMetaFieldGroup(Integer metafieldGroupId){
		webServicesSession.deleteMetaFieldGroup(metafieldGroupId)
	}

	@WebMethod
	@WebResult
	public MetaFieldGroupWS getMetaFieldGroup(Integer metafieldGroupId){
		return webServicesSession.getMetaFieldGroup(metafieldGroupId)
	}

    @WebMethod
	@WebResult
	public MetaFieldGroupWS[] getMetaFieldGroupsForEntity(String entityType){
		return webServicesSession.getMetaFieldGroupsForEntity(entityType)
	}


	@WebMethod
	@WebResult
	public Integer createMetaField(MetaFieldWS metafield){
		return webServicesSession.createMetaField(metafield)
	}

	@WebMethod
	@WebResult
	public void updateMetaField(MetaFieldWS metafieldWs){
		webServicesSession.updateMetaField(metafieldWs)
	}

	@WebMethod
	@WebResult
	public void deleteMetaField(Integer metafieldId){
		webServicesSession.deleteMetaField(metafieldId)
	}

	@WebMethod
	@WebResult
	public MetaFieldWS getMetaField(Integer metafieldId){
		return webServicesSession.getMetaField(metafieldId)
	}

    @WebMethod
	@WebResult
	public MetaFieldWS[] getMetaFieldsForEntity(String entityType){
		return webServicesSession.getMetaFieldsForEntity(entityType)
	}


    /*
        Discounts
     */
    @WebMethod
	@WebResult
	public Integer createOrUpdateDiscount(DiscountWS discount){
		return webServicesSession.createOrUpdateDiscount(discount)
	}

    @WebMethod
	@WebResult
	public DiscountWS getDiscountWS(Integer discountId){
		return webServicesSession.getDiscountWS(discountId)
	}

    @WebMethod
	@WebResult
	public void deleteDiscount(Integer discountId){
		webServicesSession.deleteDiscount(discountId)
	}


    /*
     * OrderChangeStatus
     */
    @WebMethod
	@WebResult
	public OrderChangeStatusWS[] getOrderChangeStatusesForCompany(){
		return webServicesSession.getOrderChangeStatusesForCompany()
	}

    @WebMethod
	@WebResult
	public Integer createOrderChangeStatus(OrderChangeStatusWS orderChangeStatusWS) {
		return webServicesSession.createOrderChangeStatus(orderChangeStatusWS)
	}

    @WebMethod
	@WebResult
	public void updateOrderChangeStatus(OrderChangeStatusWS orderChangeStatusWS) {
		webServicesSession.updateOrderChangeStatus(orderChangeStatusWS)
	}

    @WebMethod
	@WebResult
	public void deleteOrderChangeStatus(Integer id) {
		webServicesSession.deleteOrderChangeStatus(id)
	}

    @WebMethod
	@WebResult
	public void saveOrderChangeStatuses(OrderChangeStatusWS[] orderChangeStatuses) {
		webServicesSession.saveOrderChangeStatuses(orderChangeStatuses)
	}


    /*
     * OrderChangeType
     */
    @WebMethod
	@WebResult
	public OrderChangeTypeWS[] getOrderChangeTypesForCompany(){
		return webServicesSession.getOrderChangeTypesForCompany()
	}

    @WebMethod
	@WebResult
	public OrderChangeTypeWS getOrderChangeTypeByName(String name){
		return webServicesSession.getOrderChangeTypeByName(name)
	}

    @WebMethod
	@WebResult
	public OrderChangeTypeWS getOrderChangeTypeById(Integer orderChangeTypeId){
		return webServicesSession.getOrderChangeTypeById(orderChangeTypeId)
	}

    @WebMethod
	@WebResult
	public Integer createUpdateOrderChangeType(OrderChangeTypeWS orderChangeTypeWS){
		return webServicesSession.createUpdateOrderChangeType(orderChangeTypeWS)
	}

    @WebMethod
	@WebResult
	public void deleteOrderChangeType(Integer orderChangeTypeId){
		webServicesSession.deleteOrderChangeType(orderChangeTypeId)
	}


    /*
     * OrderChange
     */
    @WebMethod
	@WebResult
	public OrderChangeWS[] getOrderChanges(Integer orderId){
		return webServicesSession.getOrderChanges(orderId)
	}

    
    /*
     *Payment Method 
     */
    @WebMethod
	@WebResult
	public PaymentMethodTemplateWS getPaymentMethodTemplate(Integer templateId){
		return webServicesSession.getPaymentMethodTemplate(templateId)
	}

    
    @WebMethod
	@WebResult
	public Integer createPaymentMethodType(PaymentMethodTypeWS paymentMethod){
		return webServicesSession.createPaymentMethodType(paymentMethod)
	}

    @WebMethod
	@WebResult
	public void updatePaymentMethodType(PaymentMethodTypeWS paymentMethod){
		webServicesSession.updatePaymentMethodType(paymentMethod)
	}

    @WebMethod
	@WebResult
	public boolean deletePaymentMethodType(Integer paymentMethodTypeId){
		return webServicesSession.deletePaymentMethodType(paymentMethodTypeId)
	}

    @WebMethod
	@WebResult
	public PaymentMethodTypeWS getPaymentMethodType(Integer paymentMethodTypeId){
		return webServicesSession.getPaymentMethodType(paymentMethodTypeId)
	}

    
    @WebMethod
	@WebResult
	public boolean removePaymentInstrument(Integer instrumentId){
		return webServicesSession.removePaymentInstrument(instrumentId)
	}

	
    /*
     *  Order status
     */
    
    @WebMethod
	@WebResult
	public Integer createUpdateOrderStatus(OrderStatusWS newOrderStatus) {
		return webServicesSession.createUpdateOrderStatus(newOrderStatus)
	}

    @WebMethod
	@WebResult
	public void deleteOrderStatus(OrderStatusWS orderStatus){
		webServicesSession.deleteOrderStatus(orderStatus)
	}

    @WebMethod
	@WebResult
	public OrderStatusWS findOrderStatusById(Integer orderStatusId){
		return webServicesSession.findOrderStatusById(orderStatusId)
	}

    @WebMethod
	@WebResult
	public int getDefaultOrderStatusId(OrderStatusFlag flag, Integer entityId){
		return webServicesSession.getDefaultOrderStatusId(flag, entityId)
	}

    
    /*
     * Plugin
     */
    
    @WebMethod
	@WebResult
	public PluggableTaskTypeWS getPluginTypeWS(Integer id){
		return webServicesSession.getPluginTypeWS(id)
	}

    @WebMethod
	@WebResult
	public PluggableTaskTypeWS getPluginTypeWSByClassName(String className){
		return webServicesSession.getPluginTypeWSByClassName(className)
	}

    @WebMethod
	@WebResult
	public PluggableTaskTypeCategoryWS getPluginTypeCategory(Integer id){
		return webServicesSession.getPluginTypeCategory(id)
	}

    @WebMethod
	@WebResult
	public PluggableTaskTypeCategoryWS getPluginTypeCategoryByInterfaceName(String interfaceName){
		return webServicesSession.getPluginTypeCategoryByInterfaceName(interfaceName)
	}

    
    /*
     * Subscription category
     */
    
    @WebMethod
	@WebResult
	public Integer[] createSubscriptionAccountAndOrder(Integer parentAccountId, OrderWS order, boolean createInvoice, List<OrderChangeWS> orderChanges){
		return webServicesSession.createSubscriptionAccountAndOrder(parentAccountId, order, createInvoice, orderChanges)
	}


    /* Language */
    @WebMethod
	@WebResult
	public Integer createOrEditLanguage(LanguageWS languageWS){
		return webServicesSession.createOrEditLanguage(languageWS)
	}


    /*
     * Enumerations
     */
    @WebMethod
	@WebResult
	public EnumerationWS getEnumeration(Integer enumerationId){
		return webServicesSession.getEnumeration(enumerationId)
	}

    @WebMethod
	@WebResult
	public EnumerationWS getEnumerationByName(String name){
		return webServicesSession.getEnumerationByName(name)
	}

    @WebMethod
	@WebResult
	public List<EnumerationWS> getAllEnumerations(Integer max, Integer offset){
		return webServicesSession.getAllEnumerations(max, offset)
	}

    @WebMethod
	@WebResult
	public Long getAllEnumerationsCount(){
		return webServicesSession.getAllEnumerationsCount()
	}

    @WebMethod
	@WebResult
	public Integer createUpdateEnumeration(EnumerationWS enumerationWS) {
		return webServicesSession.createUpdateEnumeration(enumerationWS)
	}

    @WebMethod
	@WebResult
	public boolean deleteEnumeration(Integer enumerationId) {
		return webServicesSession.deleteEnumeration(enumerationId)
	}

	@Override
	public Integer addServiceAlias(Integer serviceId, String aliasName)
			throws SessionInternalError {
		return webServicesSession.addServiceAlias( serviceId,  aliasName)
	}

	@Override
	public Integer addServiceAliasByOrder(Integer orderId, String aliasName)
			throws SessionInternalError {
		return webServicesSession.addServiceAliasByOrder( orderId,  aliasName)
	}

	@Override
	public Integer createSubscription(OrderWS newOrder,
			OrderChangeWS[] orderChange, String aliasName) {
		return webServicesSession.createSubscription(newOrder,
			orderChange, aliasName)
	}

	@Override
	public void createSubscriptionWithAliasList(OrderWS order,
			OrderChangeWS[] orderChange, List<String> aliasList) {
			webServicesSession.createSubscriptionWithAliasList(order,
			orderChange, aliasList)
		
	}

	@Override
	public List<ServiceWS> getSubscriptionList(Integer userId) {
		return webServicesSession.getSubscriptionList(userId)
	}

	@Override
	public Integer createVoucher(VoucherWS voucherWS) {
		return webSessionService.createVoucher(voucherWS)
	}

	@Override
	public VoucherWS getVoucherWS(Integer voucherId) {
			return webSessionService.getVoucherWS(voucherId)
	}

	@Override
	public Integer RedeemVoucher(String pinCode, Integer userId) {
		return webSessionService.RedeemVoucher(pinCode, userId)
	}

	@Override
	public void updateVoucherStatus(VoucherStatusDTO status,
			VoucherDTO voucherDTO) throws SessionInternalError {
			webSessionService.updateVoucherStatus(status, voucherDTO);
	}

	@Override
	public Integer purchaseBundleUsinBundleId(Integer bundleId, Integer userId) {
		return webSessionService.purchaseBundleUsinBundleId(bundleId, userId)
	}

	@Override
	public Integer createTicket(SupportTicketWS ticketWS) {
		return webSessionService.createTicket(ticketWS)
	}

	@Override
	public Integer updateTicket(SupportTicketWS ticketWS) {
		return webSessionService.updateTicket(ticketWS);
	}

	@Override
	public List<SupportTicketWS> getTicketDetailsByUser(String userName) {
		return webSessionService.getTicketDetaisByUser(userName);
	}

	@Override
	public List<SupportTicketWS> getTicketBodyUsingTicket(Integer ticketID) {
		return webSessionService.getTicketBodyUsingTicket(ticketID);
	}

	


}
	