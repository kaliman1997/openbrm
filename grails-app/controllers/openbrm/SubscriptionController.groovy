package openbrm

import com.sapienter.jbilling.client.util.Constants
import com.sapienter.jbilling.client.util.DownloadHelper
import com.sapienter.jbilling.common.SessionInternalError
import com.sapienter.jbilling.server.customer.CustomerBL
import com.sapienter.jbilling.server.invoice.InvoiceBL
import com.sapienter.jbilling.server.invoice.db.InvoiceDAS
import com.sapienter.jbilling.server.item.CurrencyBL
import com.sapienter.jbilling.server.order.OrderBL
import com.sapienter.jbilling.server.order.OrderWS
import com.sapienter.jbilling.server.order.db.OrderDAS
import com.sapienter.jbilling.server.order.db.OrderDTO
import com.sapienter.jbilling.server.order.db.OrderPeriodDAS
import com.sapienter.jbilling.server.order.db.OrderStatusDAS
import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.user.UserWS
import com.sapienter.jbilling.server.user.db.CustomerDTO
import com.sapienter.jbilling.server.user.db.UserDAS
import com.sapienter.jbilling.server.user.db.UserDTO
import com.sapienter.jbilling.server.device.UserDeviceWS
import in.saralam.sbs.server.subscription.db.ServiceDTO
import in.saralam.sbs.server.subscription.ServiceWS
import com.sapienter.jbilling.server.util.csv.CsvExporter
import com.sapienter.jbilling.server.util.csv.Exporter
import dk.comtalk.billing.server.customer.balance.UserBalanceWS
import dk.comtalk.billing.server.customer.balance.BalanceWS
//import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import com.sapienter.jbilling.server.user.db.CompanyDTO
import com.sapienter.jbilling.server.device.db.DeviceDTO;
import com.sapienter.jbilling.server.device.db.UserDeviceDTO;
import com.sapienter.jbilling.server.device.DeviceWS;
import org.hibernate.FetchMode
import org.hibernate.criterion.Restrictions
import org.hibernate.criterion.Criterion
import org.hibernate.Criteria
import com.sapienter.jbilling.client.util.SortableCriteria
//import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured
import in.saralam.sbs.server.subscription.ServiceFeatureBL;
import in.saralam.sbs.server.subscription.db.ServiceFeatureDTO;
import in.saralam.sbs.server.subscription.db.ServiceAliasDTO;
import in.saralam.sbs.server.subscription.db.ServiceSiteDTO;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;
import com.sapienter.jbilling.server.process.db.BillingProcessConfigurationDTO;
import com.sapienter.jbilling.server.process.db.BillingProcessConfigurationDAS;
import in.saralam.sbs.server.subscription.db.ServiceDAS;
import com.sapienter.jbilling.server.item.db.ItemTypeDAS
import com.sapienter.jbilling.server.user.contact.db.ContactDTO
import com.sapienter.jbilling.server.user.contact.db.ContactDAS


import jbilling.Filter
import jbilling.FilterType

//@Secured(["MENU_103"])
class SubscriptionController {

    static pagination = [ max: 10, offset: 0, sort: 'id', order: 'desc' ]

    def webServicesSession
    def viewUtils
    def filterService
    def recentItemService
    def breadcrumbService

    def index = {
        redirect (action: 'list', params: params)
    }

    def private getFilteredServices(filters, GrailsParameterMap params) {
        params.max = params?.max?.toInteger() ?: pagination.max
        params.offset = params?.offset?.toInteger() ?: pagination.offset
        params.sort = params?.sort ?: pagination.sort
        params.order = params?.order ?: pagination.order

        return ServiceDTO.createCriteria().list(
                max:    params.max,
                offset: params.offset
        ) {
            createAlias('baseUserByUserId', 'u', Criteria.LEFT_JOIN)
            and {
                filters.each { filter ->
                    if (filter.value) {
                        //handle orderStatus & orderPeriod separately
                       if (filter.field == 'userDevice.telephone') {
								String typeId = params['contactFieldTypes']
								String ssf = filter.stringValue
								WebServicesSessionSpringBean wssb = new WebServicesSessionSpringBean()
                              ServiceDTO service = wssb.getServiceByTelephoneNumber(ssf)
							  if (service == null){
							  eq("id",0)
							  } else{
							  eq("id",service.getId())
							  }
							  
                        } else {
                            addToCriteria(filter.getRestrictions());
                        }
                    }
                }
                eq('u.company', new CompanyDTO(session['company_id']))
                eq('deleted', 0)

                // limit list to only this customer's orders
                /*if (SpringSecurityUtils.ifNotGranted("ORDER_28")) {
                    eq('u.id', session['user_id'])
                }*/
            }

            // apply sorting
            SortableCriteria.sort(params, delegate)
        }
    }

    def list = {
        def filters = filterService.getFilters(FilterType.SERVICE, params)
        def services = getFilteredServices(filters, params)
         
		 
        def selected = params.id ? webServicesSession.getService(params.int("id")) : null
        def user = selected ? webServicesSession.getUserWS(selected.userId) : null

        breadcrumbService.addBreadcrumb(controllerName, 'list', null, selected?.id)

        if (params.applyFilter || params.partial) {
            render template: 'services', model: [ services: services, service: selected, user: user, filters: filters ]
        } else {
            [ services: services, service: selected, user: user, filters: filters ]
        }
    }

    //@Secured(["ORDER_24"])
    def show () {

        ServiceWS service = webServicesSession.getService(params.int('id'))
        UserDeviceWS userDevice = webServicesSession.getUserDeviceByOrderAndLine(service.getOrderId(), service.getOrderLineId())
	DeviceWS device =  webServicesSession.getDeviceWS(userDevice?.getDevice()?.getId());
        UserWS user = webServicesSession.getUserWS(service.getUserId())
	BalanceWS[] userBalances = webServicesSession.getUserBalances(service.getUserId(), service.getOrderId(), service.getOrderLineId());
	ServiceFeatureDTO [] serviceFeatures = new ServiceFeatureDTO()
	serviceFeatures = webServicesSession.getServiceFeatures(service.getId())
	
	ServiceAliasDTO [] serviceAlias = new ServiceAliasDTO()
	serviceAlias = webServicesSession.getServiceAlias(service.getId())
	ServiceSiteDTO [] serviceSites = new ServiceSiteDTO()
	serviceSites = webServicesSession.getServiceSites(service.getId())
	//device list
	UserDeviceDTO [] userDevices = new UserDeviceDTO()
        userDevices = webServicesSession.getServiceDevices(service.getId())

        breadcrumbService.addBreadcrumb(controllerName, 'list', null, service.id)
        //recentItemService.addRecentItem(order.id, RecentItemType.ORDER)

        render template:'show', model: [service: service, user: user, userDevice: userDevice, userBalances: userBalances, device:device, serviceFeatures:serviceFeatures, serviceAlias:serviceAlias, serviceSites:serviceSites, userDevices:userDevices] 
    }

    def user = {
        def filter = new Filter(type: FilterType.SERVICE, constraintType: FilterConstraint.EQ, field: 'baseUserByUserId.id', template: 'id', visible: true, integerValue: params.int('id'))
        filterService.setFilter(FilterType.SERVICE, filter)
        redirect action: 'list'
    }

    //@Secured(["ORDER_22"])
    def changePlan () {
	
	Integer entityId = webServicesSession.getCallerCompanyId();
	def description = "plans";
	def planList1 = webServicesSession.getItemByCategory(new ItemTypeDAS().findByDescription(entityId, description).getId());
	List<ItemDTO> planList = new ArrayList<ItemDTO>()
	for(ItemDTO item : planList1){
		if(item.getDeleted()==0)
		planList.add(item)
	}
	
	ItemDTO fromItem = webServicesSession.getPlanBySubscriptionId(params.int('serviceId'));
	
	
	[fromItem: fromItem, planList: planList, serviceId : params.int('serviceId')]
    }

    def saveChangePlan = {
	String   dateTemp = params['changeDate']
	log.debug(" date "+dateTemp)
	def changDate = new Date().parse("d/M/yyyy", dateTemp)
	webServicesSession.changeSubscriptionPlan(params.int('serviceId'),  params.int('fromItemId'), params.int('toItemId'), changDate)
	redirect action: 'list'

    }
    def cancelPlan = {
	
	ItemDTO fromItem = webServicesSession.getPlanBySubscriptionId(params.int('serviceId'));
	ServiceDTO  service= new ServiceDAS().find(params.int('serviceId'));
	OrderDTO orderDto = service.getOrderDTO()
	[fromItem: fromItem,  orderDto:  orderDto ,  serviceId : params.int('serviceId')]
    }
    def saveCancelPlan = {
       
	String userId=params.int('serviceId')
	String   dateTemp = params['cancelDate']
	log.debug(" date "+dateTemp)
	def canDate = new Date().parse("d/M/yyyy", dateTemp)
       webServicesSession.cancelSubscription( userId,canDate)
	redirect action: 'list'

    }
	def addAlias = {
	
	ItemDTO fromItem = webServicesSession.getPlanBySubscriptionId(params.int('serviceId'));
		[fromItem: fromItem, serviceId : params.int('serviceId')]
    }

    def addSite = {
	ServiceWS service = webServicesSession.getService(params.int('serviceId'))
        def contacts  = webServicesSession.getUserContactsWS(service.getUserId());
        [userContactList: contacts, serviceId : params.int('serviceId')]
    }
    def addDevice={
	[serviceId : params.int('serviceId')]
    }
    
    def savealiasName={
	            
	String aliasNames = params.aliasName
	log.debug("aliasName is ${aliasNames}")
	Integer sid = params.int('serviceId')
	log.debug("sid is ${sid}")
	def newaliasid = webServicesSession.addServiceAlias(params.int('serviceId'),aliasNames)
	log.debug("newaliasid is ${newaliasid}")
	flash.message = 'service.change.aliasName.success'
	flash.args = [params.id, params.id]
	redirect action: 'list'
   }
	
   def saveDevice={

	Integer deviceId = params.int('deviceId');  
	Integer sid = params.int('serviceId')
	
        def userDeviceId = webServicesSession.addDevice(sid, deviceId)
        flash.message = 'service.change.device.success'
        flash.args = [params.id, params.id]
        redirect action: 'list'
    } 
	
   def saveSite={

	Integer contactId = params.int('contactId')
        Integer sid = params.int('serviceId')
	def contact = new ContactDAS().find(contactId)
	def siteAddr = new StringBuilder()
	siteAddr.append(contact.getAddress1() == null ? "" : contact.getAddress1())
	siteAddr.append(" ")
	siteAddr.append(contact.getAddress2() == null ? "" : contact.getAddress2())
	siteAddr.append(" ")
	siteAddr.append(contact.getCity() == null ? "" : contact.getCity())
	siteAddr.append(" ")
	siteAddr.append(contact.getStateProvince() == null ? "" : contact.getStateProvince())
	siteAddr.append(" ")
	siteAddr.append(contact.getPostalCode() == null ? "" : contact.getPostalCode())

        def newaliasid = webServicesSession.addServiceSite(params.int('serviceId'), siteAddr.toString())
        flash.message = 'service.change.site.success'
        flash.args = [params.id, params.id]
        redirect action: 'list'
    } 
	 
   def deleteAliasName={
     	log.debug("In deleteAliasname")
    	webServicesSession.deleteAliasName(params.int('serviceAliaseId'))
	flash.info = 'service.delete.aliasName.success'
	flash.args = [params.serviceAliaseId, params.serviceAliaseId]
	redirect action: 'list'
   }
	
   def deleteDevice={
        webServicesSession.deleteUserDevice(params.int('userDeviceId'))
        flash.info = 'service.delete.device.success'
        flash.args = [params.id, params.id]
        redirect action: 'list'
   }
   
   def deleteSite={
        webServicesSession.deleteSite(params.int('serviceSiteId'))
        flash.info = 'service.delete.site.success'
        flash.args = [params.serviceSiteId, params.serviceSiteId]
        redirect action: 'list'
   }
	
       def activate = {
    	try {
            //webServicesSession.activateService(params.extId1, "SPS")
			webServicesSession.activateService(params.int('serviceId'))
            flash.message = 'service.change.status.success'
            flash.args = [params.id, params.id]
        } catch (SessionInternalError e){
            flash.error ='service.change.status.error'
            viewUtils.resolveException(flash, session.locale, e);
        } catch (Exception e) {
            log.error e
            flash.error= e.getMessage()
        }
        redirect action: 'list'

   }

   def inactivate = {
        try {
	
            //webServicesSession.inactivateService(params.extId1, "SPS")
			webServicesSession.inactivateService(params.int('serviceId'))
            flash.message = 'service.change.status.success'
            flash.args = [params.id, params.id]
        } catch (SessionInternalError e){
            flash.error ='service.change.status.error'
            viewUtils.resolveException(flash, session.locale, e);
        } catch (Exception e) {
            log.error e
            flash.error= e.getMessage()
        }
        redirect action: 'list'

   }


   def editDevice = {
	
	UserDeviceWS userDevice = webServicesSession.getUserDeviceWSById(params.int('id'))
	DeviceWS device =  webServicesSession.getDeviceWS(userDevice?.getDevice()?.getId());
	[userDevice: userDevice, device: device]

   }

   def save = {

   def userDeviceId = params.int('userDeviceId');   
   def tel;
   

   log.info "got value is $params.icc"
   log.info "got value is $params.telephoneNumber"
   log.info "got value is $params.imsi"

   
   UserDeviceWS userDevice = webServicesSession.getUserDeviceWSById(userDeviceId);
   DeviceDTO device =  webServicesSession.getDeviceDTO(params.icc);
   log.info"UserDevice value $userDevice.telephoneNumber"

   if(userDevice.telephoneNumber != params.telephoneNumber){
      tel = params.telephoneNumber;
   }
   else{
   tel=userDevice.telephoneNumber;
   }

   userDevice.setTelephoneNumber(params.telephoneNumber)

   log.info"userDevice tel value is $userDevice.telephoneNumber"

   if(device == null){
   flash.error ='Device does not exist'
   } else if(device.getDeviceStatus().getId() == Constants.DEVICE_STATUS_ASSIGNED){
   flash.error ='Device is already assigned'
   
   }
   else{
      if(!webServicesSession.changeICC(userDevice, params.icc))
	flash.error ='Unable to change ICC'
   }
        redirect action: 'list'

   }

   def addFeature = {
        try {

            webServicesSession.addFeature(params.msisdn,  params.int('featureId'))
            flash.message = 'service.change.status.success'
            flash.args = [params.id, params.id]
        } catch (SessionInternalError e){
            flash.error ='service.change.status.error'
            viewUtils.resolveException(flash, session.locale, e);
        } catch (Exception e) {
            log.error e
            flash.error= e.getMessage()
        }
        redirect action: 'list'

   }


   def removeFeature = {

        try {

            webServicesSession.removeFeature(params.msisdn, params.int('featureId'))
            flash.message = 'service.change.status.success'
            flash.args = [params.id, params.id]
        } catch (SessionInternalError e){
            flash.error ='service.change.status.error'
            viewUtils.resolveException(flash, session.locale, e);
        } catch (Exception e) {
            log.error e
            flash.error= e.getMessage()
        }
        redirect action: 'list'

   }



   

}
