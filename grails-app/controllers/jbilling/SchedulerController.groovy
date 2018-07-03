

package jbilling


import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.sapienter.jbilling.client.ViewUtils
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import com.sapienter.jbilling.server.user.UserWS;

import org.hibernate.FetchMode
import org.hibernate.criterion.Restrictions
import org.hibernate.criterion.Criterion
import org.hibernate.Criteria
import com.sapienter.jbilling.client.util.SortableCriteria
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.IWebServicesSessionBean;

import org.springframework.jdbc.datasource.DataSourceUtils

//import grails.plugins.springsecurity.Secured
import javax.sql.DataSource
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;
import java.lang.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.Writer;
import java.io.FileNotFoundException;
import java.io.IOException;
//import grails.plugins.springsecurity.Secured
import in.saralam.sbs.server.crm.SupportTicketWS;
import in.saralam.sbs.server.crm.db.SupportTicketDAS;
import in.saralam.sbs.server.crm.db.SupportTicketDTO;
import in.saralam.sbs.server.crm.db.TicketDetailsDAS;
import in.saralam.sbs.server.crm.db.TicketDetailsDTO;
import in.saralam.sbs.server.crm.db.TicketStatusDAS
//import in.saralam.sbs.server.rating.db.EventTypeRateMapDAS
import in.saralam.sbs.server.rating.db.RatingEventTypeDAS;
import com.sapienter.jbilling.server.user.db.CompanyDTO
import in.saralam.sbs.server.Scheduler.db.ScheduleDTO
import in.saralam.sbs.server.pricing.db.PricePackageDTO;
import com.sapienter.jbilling.server.user.db.UserDAS;
import in.saralam.sbs.server.Scheduler.ScheduleWS

//@Secured(["MENU_105"])
class SchedulerController {

    static pagination = [ max: 10, offset: 0, sort: 'id', order: 'desc' ]

    WebServicesSessionSpringBean webServicesSession = new  WebServicesSessionSpringBean();
    def filterService

    ViewUtils viewUtils
    DataSource dataSource
	def breadcrumbService


    def index = {
	
          redirect action: list, params: params
    }

	
	def getFilteredBundles(filters, params) {
	
        params.max = params?.max?.toInteger() ?: pagination.max
        params.offset = params?.offset?.toInteger() ?: pagination.offset
        params.sort = params?.sort ?: pagination.sort
        params.order = params?.order ?: pagination.order

        return ScheduleDTO.createCriteria().list(
                max:    params.max,
                offset: params.offset
        ) {
            //createAlias('entity', 'u', Criteria.LEFT_JOIN)
            and {
                filters.each { filter ->
                       log.debug("fileter  field ${filter.field}")
                    if (filter.value) {
                                   
                             addToCriteria(filter.getRestrictions());
                       }
                       }
                      }
              
                 
              
		eq('entityId', new CompanyDTO(session['company_id']))
            // apply sorting
            SortableCriteria.sort(params, delegate)
        }
    }

    def list = {
		log.debug("first comes in action list when click on Scheduler");
        def filters = filterService.getFilters(FilterType.SCHEDULER, params)
        def schedulers = getFilteredBundles(filters, params)
        def selected = params.id ? webServicesSession.getScheduleWS(params.int("id")) : null
        //def user = selected ? webServicesSession.getUserWS(selected.userId) : null
        log.debug(" selected"+selected)
        breadcrumbService.addBreadcrumb(controllerName, 'list', null, selected?.id)

        if (params.applyFilter || params.partial) {
            render template: 'scheduler', model: [ schedulers: schedulers,  filters: filters ,selected : selected]
        } else {
            [ schedulers: schedulers,  filters: filters ,selected : selected]
        }
    }

	
   def show = {

        ScheduleWS scheduleWS = webServicesSession.getScheduleWS(params.int('id'))
      
        //UserWS user = webServicesSession.getUserWS(order.getUserId())

       /* breadcrumbService.addBreadcrumb(controllerName, 'list', null, order.id)
        recentItemService.addRecentItem(order.id, RecentItemType.ORDER)*/
        Integer languageId=session['language_id'].toInteger()
        
        render template:'show', model: [scheduleWS : scheduleWS,languageId:languageId]
       }
	   
	
	
  }