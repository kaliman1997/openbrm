
package jbilling

import com.sapienter.jbilling.server.item.db.ItemDTO;
import in.saralam.sbs.server.advancepricing.db.ProductChargeDAS;


import com.sapienter.jbilling.server.item.db.ItemDTO;

import org.springframework.transaction.interceptor.TransactionAspectSupport;
import in.saralam.sbs.server.advancepricing.db.ProductChargeRateDTO;
import com.sapienter.jbilling.server.device.db.DeviceStatusDTO;
import com.sapienter.jbilling.server.device.db.DeviceDTO
import com.sapienter.jbilling.server.device.DeviceWS

import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.process.db.PeriodUnitDAS;
import com.sapienter.jbilling.server.user.UserWS;
import com.sapienter.jbilling.server.user.db.CompanyDTO
import com.sapienter.jbilling.server.user.db.UserDAS;
import com.sapienter.jbilling.server.device.DeviceBL
import com.sapienter.jbilling.common.SessionInternalError
import com.sapienter.jbilling.client.ViewUtils
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

import org.hibernate.FetchMode
import org.hibernate.criterion.Restrictions
import org.hibernate.criterion.Criterion
import org.hibernate.Criteria
import com.sapienter.jbilling.client.util.SortableCriteria



import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.IWebServicesSessionBean;

import org.springframework.jdbc.datasource.DataSourceUtils

//import grails.plugins.springsecurity.Secured
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured

//import in.saralam.sbs.server.advancepricing.ProductChargeRateWS
//import in.saralam.sbs.server.advancepricing.ProductChargeWS
//import in.saralam.sbs.server.advancepricing.RateDependeeWS;
import in.saralam.sbs.server.advancepricing.db.ChargeTypeDAS;
import in.saralam.sbs.server.advancepricing.db.ChargeTypeDTO;
import in.saralam.sbs.server.advancepricing.db.ProductChargeDAS;
import in.saralam.sbs.server.advancepricing.db.ProductChargeDTO;
import in.saralam.sbs.server.advancepricing.db.ProductChargeRateDTO;
import in.saralam.sbs.server.advancepricing.db.RateDependencyTypeDAS
import in.saralam.sbs.server.advancepricing.db.RumTypeDAS
import in.saralam.sbs.server.crm.SupportTicketWS;
import in.saralam.sbs.server.crm.db.SupportTicketDAS;
import in.saralam.sbs.server.crm.db.SupportTicketDTO;
import in.saralam.sbs.server.crm.db.TicketDetailsDAS;
import in.saralam.sbs.server.crm.db.TicketDetailsDTO;
import in.saralam.sbs.server.crm.db.TicketStatusDAS
//import in.saralam.sbs.server.rating.db.EventTypeRateMapDAS
import in.saralam.sbs.server.rating.db.RatingEventTypeDAS;

import javax.sql.DataSource
import com.sapienter.jbilling.server.device.db.DeviceDAS
import com.sapienter.jbilling.server.device.db.DeviceStatusDAS
import com.sapienter.jbilling.server.util.csv.CsvExporter
import com.sapienter.jbilling.server.util.csv.Exporter
import com.sapienter.jbilling.server.util.db.CurrencyDAS
import com.sapienter.jbilling.client.util.DownloadHelper

import java.text.*;
import java.util.*; 

import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;
import java.lang.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.Writer;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.sapienter.jbilling.server.item.CurrencyBL
import com.sapienter.jbilling.server.item.db.ItemDAS;
import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.item.db.ItemTypeDAS
//import grails.plugins.springsecurity.Secured

//@Secured(["MENU_105"])
class CRMController {

    static pagination = [ max: 10, offset: 0, sort: 'id', order: 'desc' ]

    WebServicesSessionSpringBean webServicesSession = new  WebServicesSessionSpringBean();
    def filterService

    ViewUtils viewUtils
    DataSource dataSource
	def breadcrumbService


    def index = {
        redirect action: 'list', params: params
    }
	def private getFilteredPricing(GrailsParameterMap params) 
	{
	
		params.max = params?.max?.toInteger() ?: pagination.max
		params.offset = params?.offset?.toInteger() ?: pagination.offset
		params.sort = params?.sort ?: pagination.sort
		params.order = params?.order ?: pagination.order

		return SupportTicketDTO.createCriteria().list(
				max:    params.max,
				offset: params.offset
		) {
		
		createAlias('baseUser', 'b', Criteria.LEFT_JOIN)
		if(params.uname!='')
		{
		eq('b.userName', params.uname)
		
		}
		
			eq('b.company', new CompanyDTO(session['company_id']))
			}
		SortableCriteria.sort(params, delegate)
	 
	
}
	
	 def list = {
			log.debug "paras "+params
			if(params.uname == null)
			params.uname = ''
			def filters = filterService.getFilters(FilterType.PRICING, params)
			
			def tickets = getFilteredPricing(params)
			log.debug "tickets"+tickets
			breadcrumbService.addBreadcrumb(controllerName, actionName, null, null)
			
			if (params.applyFilter || params.partial) {
			
				render template: 'pricing', model: [ tickets : tickets , params : params]
				
				
			}else {
				log.debug "else"
					render view: 'list', model: [ tickets : tickets , params : params]
			}
		
	}
	
	

   
	
	def show = {
		def selected = new SupportTicketDAS().find(params.int('id'))
		def details =  new TicketDetailsDAS().findByticket(params.int('id'))
		UserWS user = webServicesSession.getUserWS(selected.getBaseUser().getId())
		UserWS assigned = webServicesSession.getUserWS(selected.getAssignedUser().getId())
		render template:'show', model: [ selected : selected , details : details, user : user , assigned : assigned ]
		}
	
	def saveTicket = {
		
		if(params.button=='fetch'){
			log.debug "fetch button"
			if(params.userName!='' && params.userName!=null){
				def user = new UserDAS().findByUserName(params.userName, new CompanyDTO(session['company_id']).getId())
				if(user!=null){
				params.userId = user.getId()
				}else{
				params.userId = '0'
				}
				params.id = params.tid
				edit(params)
				return
			}else{
			flash.error = 'Enter existed user name'
			edit(params)
			return
			}
		}else{
		SupportTicketWS ticketWS = new SupportTicketWS()
		def user = null
		if(params.userName!='' && params.subject!='' && params.details0!='' ){
			user = new UserDAS().findByUserName(params.userName, new CompanyDTO(session['company_id']).getId())
			ticketWS.setSubject(params.subject)
			ticketWS.setTicketBody(params["details${params.tid}"])
			ticketWS.setTicketStatus(new TicketStatusDAS().find(params.status as Integer))
			if(user == null){
				flash.error = 'User Name can not exist'
				params.id = params.tid
				edit(params)
				return
			}
			ticketWS.setBaseUserID(user.getId())
			//ticketWS.setAssignedUserID(user.getId())
			
			if(params.tid as Integer == 0){
			def ticketId = webServicesSession.createTicket(ticketWS)
			session.message = 'ticket.created'
			session.args = [ ticketId ]
			chain action: 'list'
			}else{
			//Integer tid = Integer.parseInt(params.tid)
			ticketWS.setId(params.tid as Integer)
			def ticketId = webServicesSession.updateTicket(ticketWS)
			session.message = 'ticket.updated'
			session.args = [ params.tid ]
			chain action: 'list'
			}
		}else{
			if(params.userName==''){
				flash.error = 'Enter existed user name'	
			}else if(params.subject==''){
			flash.error = 'Subject can not empty'
			}else if(params.details0==''){
			flash.error = 'Ticket Body can not empty'
			}
			params.id = params.tid
		 	edit(params)
		}
		}
    }
	
	def editList = {
		edit(params)
	}
	def private edit(GrailsParameterMap params) {
		
		Integer tid = null;
		def details = new ArrayList<TicketDetailsDTO>()
		if( params.int('id')!=null && params.int('id')!=0 ){
		def selected = new SupportTicketDAS().find(params.int('id'))
		details =  new TicketDetailsDAS().findByticket(params.int('id'))
		
		params.userName = selected.baseUser.userName
		params.subject = selected.subject
		//params.details = details
		params.userId = selected.baseUser.id
		params.status = selected.ticketStatus.id
		tid = params.int('id')
		}else{
		tid = 0
		}
		render view :'editList',model:[params : params, tid:tid, status:getStatus(),details:details]
	}
	
	def findUserId = {
		
		log.debug "find userid method "+params
		
	}
   
	def getStatus(){
		def ticketStatus = new TicketStatusDAS().findAll ()
	
		return ticketStatus
	}
}
