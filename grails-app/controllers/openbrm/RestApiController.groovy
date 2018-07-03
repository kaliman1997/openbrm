package openbrm

import java.util.logging.Logger

import grails.rest.RestfulController
import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.annotations.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import grails.plugin.springsecurity.SpringSecurityService;
import com.sapienter.jbilling.server.util.IWebServicesSessionBean
import com.sapienter.jbilling.server.util.Context;
import com.sapienter.jbilling.server.item.IItemSessionBean;
import com.sapienter.jbilling.server.item.ItemBL;
import com.sapienter.jbilling.server.item.ItemDTOEx;
import com.sapienter.jbilling.server.item.ItemTypeBL;
import com.sapienter.jbilling.server.item.ItemTypeWS;
import com.sapienter.jbilling.server.user.UserWS;
import com.sapienter.jbilling.server.user.ContactWS;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.item.db.ItemTypeDTO;
import com.sapienter.jbilling.client.authentication.CompanyUserDetails;
import com.sapienter.jbilling.server.user.ContactBL;
import com.sapienter.jbilling.server.user.ContactDTOEx;
import com.sapienter.jbilling.server.user.ContactWS;
import com.sapienter.jbilling.server.user.CreateResponseWS;

import com.sapienter.jbilling.server.user.IUserSessionBean;
import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.UserDTOEx;
import com.sapienter.jbilling.server.user.UserTransitionResponseWS;
import com.sapienter.jbilling.server.user.UserWS;
import com.sapienter.jbilling.server.user.ValidatePurchaseWS;

import com.sapienter.jbilling.server.user.db.CompanyDTO;
import com.sapienter.jbilling.server.user.db.CustomerDAS;
import com.sapienter.jbilling.server.user.db.CustomerDTO;
import com.sapienter.jbilling.server.user.db.UserDAS;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.user.partner.PartnerBL;
import com.sapienter.jbilling.server.user.partner.PartnerWS;

import com.sapienter.jbilling.server.device.DeviceWS;
import com.sapienter.jbilling.server.device.UserDeviceWS;
import com.sapienter.jbilling.server.device.db.DeviceDTO;
import com.sapienter.jbilling.server.device.db.DeviceDAS;
import com.sapienter.jbilling.server.device.db.DeviceStatusDAS;
import com.sapienter.jbilling.server.device.db.DeviceStatusDTO;
import com.sapienter.jbilling.server.device.db.UserDeviceDTO;
import com.sapienter.jbilling.server.device.db.UserDeviceStatusDTO;
import com.sapienter.jbilling.server.device.db.UserDeviceStatusDAS;
import com.sapienter.jbilling.server.device.DeviceBL;
import com.sapienter.jbilling.server.device.UserDeviceBL;
import com.sapienter.jbilling.server.device.db.UserDeviceDAS;
import com.sapienter.jbilling.server.item.CurrencyBL;
import com.sapienter.jbilling.server.mediation.db.MediationRecordLineDAS;
import com.sapienter.jbilling.server.order.OrderHelper;
import com.sapienter.jbilling.server.user.ContactTypeWS;
import com.sapienter.jbilling.server.user.db.CompanyDAS;
import com.sapienter.jbilling.server.util.db.CurrencyDTO;
import com.sapienter.jbilling.server.util.db.LanguageDAS;
import com.sapienter.jbilling.server.util.db.LanguageDTO;
import com.sapienter.jbilling.server.util.db.PreferenceTypeDAS;
import com.sapienter.jbilling.server.util.db.PreferenceTypeDTO;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.user.db.UserDAS;

import grails.plugins.springsecurity.Secured
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured


class RestApiController extends RestfulController {

   static responseFormats = ['json', 'xml']
   IWebServicesSessionBean webServicesSessionBean = Context.getBean(Context.Name.WEB_SERVICES_SESSION);
   def SpringSecurityService springSecurityService;
   RestApiController() {
        super()
        log.info ("ApiResourceController created")
	/*
   	SpringSecurityService springSecurityService = webServicesSessionBean.getSpringSecurityService()
	if(springSecurityService == null) {
		log.debug("springSecurityService is null")
		springSecurityService = Context.getBean(Context.Name.SPRING_SECURITY_SERVICE);
	}
	try {

		log.debug("lets try to fetch authenticated user")
		CompanyUserDetails details = (CompanyUserDetails) springSecurityService().getPrincipal();
        	def userId = details.getUserId()
		log.debug("authenticated user ${userId}")
	} catch (Exception ex) {
		log.error(ex.getMessage())

	}*/
   }

   @Override
   def show() {
        // We pass which fields to be rendered with the includes attributes,
        // we exclude the class property for all responses.

        respond queryForResource(params.id), [includes: includeFields, excludes: ['class']]
   }

   @Override
   def save () {

        def action = params.action;
        respond queryForResource(params.id), [includes: includeFields, excludes: ['class']]



   }

   def getCallerCompanyId () {

	def action = params.action;

        try {

                def user = webServicesSessionBean.getCallerCompanyId()
                respond user, [includes: includeFields, excludes: ['class']]

        } catch (Exception ex) {

                def error = [result:'failed', message:ex.getMessage()]
                respond error
        }

   }

 
   
   def getCustomer () {

	def action = params.action;
	def payload = request.JSON;

	try {

		Integer userId = payload.userId?.toInteger();
		def user = webServicesSessionBean.getUserWS(userId)
        respond user, [includes: includeFields, excludes: ['class']]

	} catch (Exception ex) {

		def error = [result:'failed', message:ex.getMessage()]
		respond error
	}
	

   }

   def getProductCategories() {

	def payload = request.JSON
	def entityId = params.company_id?.toInteger()

	try {
	
		def cats = new ItemTypeBL().getAllItemTypesByEntity(entityId)
		respond cats, [includes: includeFields, excludes: ['class']]

	} catch (Exception ex) {

		def error = [result:'failed', message:ex.getMessage()]
                respond error

	}
   }

   def getProduct () {


	def payload = request.JSON
        def productId = params.productId?.toInteger()

        try {

                def cats = new ItemTypeBL().getAllItemTypesByEntity(entityId)
                respond cats, [includes: includeFields, excludes: ['class']]

        } catch (Exception ex) {

                def error = [result:'failed', message:ex.getMessage()]
                respond error

        }

   }

   def createCustomer () {

	def payload = request.JSON

        try {
		ObjectMapper mapper = new ObjectMapper()
		//UserWS userData = mapper.readValue(payload.toString(), UserWS.class)
		UserWS user = new UserWS();
		user.setUserName(payload.userName);
		user.setPassword(payload.password);
		user.setCurrencyId(payload.currencyId);
		user.setMainRoleId(payload.mainRoleId);
		user.setCompanyName(payload.companyName);

		ContactWS contact = new ContactWS();
		contact = mapper.readValue(payload.contact.toString(), ContactWS.class)
		user.setContact(contact);

		//def result = webServicesSessionBean.createUser(user)
		user.setUserId(0);

        	Integer entityId = params.company_id?.toInteger()
        	UserBL bl = new UserBL()
		log.info "checking for duplicate user"

        	if (bl.exists(user.getUserName(), entityId)) {
			def error = [result:'failed', message:"User already exists with username"]
			respons error;
        	}

        	ContactBL cBl = new ContactBL()
        	UserDTOEx dto = new UserDTOEx(user, entityId);
		log.info "creating  user ..."
        	Integer userId = bl.create(dto);
        	if (user.getContact() != null) {
            		user.getContact().setId(0);
            		cBl.createPrimaryForUser(new ContactDTOEx(user.getContact()), userId, entityId);
        	}

		def result = [result:"success", userId:userId]

		respond result, [includes: includeFields, excludes: ['class']]

        } catch (Exception ex) {

                def error = [result:'failed', message:ex.getMessage()]
                respond error

        }




   }

   def updateCustomer () {



   }


   def createOrder () {



   }

   def updateOrder () {

   }

    @Override
    protected Object queryForResource(Serializable id) {

    //return new ApiResource();
	def fun = [result:"success", message:"OpenBRM: you are heard. !"]
        return fun;


    }

   @Override
   def index(final Integer max) {
        params.max = Math.min(max ?: 10, 100)
        System.out.println ("Index method invoked in rest controller with params " + params)
        respond queryForResource(params), [includes: includeFields, excludes: ['class']]
   }

   private getIncludeFields() {
        params.fields?.tokenize(',')
   }
}

