/*
 * JBILLING CONFIDENTIAL
 * _____________________
 *
 * [2003] - [2012] Enterprise jBilling Software Ltd.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Enterprise jBilling Software.
 * The intellectual and technical concepts contained
 * herein are proprietary to Enterprise jBilling Software
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden.
 */

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
import java.util.List;

//import grails.plugins.springsecurity.Secured;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import com.sapienter.jbilling.server.util.db.LanguageDTO;
import com.sapienter.jbilling.server.util.db.InternationalDescriptionDTO;
import com.sapienter.jbilling.server.util.db.InternationalDescriptionDAS;
import com.sapienter.jbilling.server.util.InternationalDescriptionWS;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.order.db.OrderPeriodDTO;
import com.sapienter.jbilling.server.order.OrderPeriodWS;
//import in.saralam.sbs.server.Scheduler.db.SchedulerTypeDTO;
import in.saralam.sbs.server.BulkNotification.db.NotificationDTO;
import com.sapienter.jbilling.server.payment.tasks.PaymentSageTask.Params;
import com.sapienter.jbilling.server.process.db.PeriodUnitDTO;
import com.sapienter.jbilling.server.util.db.PreferenceTypeDTO
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Query;
import com.sapienter.jbilling.client.ViewUtils
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import java.util.*;
/**
 * BulkNotificationController 
 *
 * @author Vikas Bodani
 * @since 09-Mar-2011
 */


//@Secured(["isAuthenticated()", "MENU_99"])
class BulkNotificationController {

	static pagination = [ max: 10, offset: 0 ]
	def breadcrumbService
	def webServicesSession
	def viewUtils
	
    def index = {
        redirect action: 'list', params: params
    }

    def list = {
        params.max = params?.max?.toInteger() ?: pagination.max
        params.offset = params?.offset?.toInteger() ?: pagination.offset
		
	 def notifications = getPeriodsForEntity()
		
        breadcrumbService.addBreadcrumb(controllerName, actionName, null, null)
		
		if (params.applyFilter) {
            render template: 'notifications', model: [ notifications: notifications ]
		} else {
			render view: 'list', model: [ notifications: notifications ]
		}
	}
	
  def getPeriodsForEntity () {
		return NotificationDTO.createCriteria().list(
			max:    params.max,
			offset: params.offset
		) {
			
		}
	}
	
	
	
    def edit = {
       
    }
    
	def sms = {
        
		def bulkSms = InternationalDescriptionDTO.executeQuery( "select distinct a.content from InternationalDescriptionDTO a where a.id.tableId ='47' and a.id.foreignId >= '100' and a.id.foreignId < '200'");
		if(bulkSms == null)
		{
		log.debug("bulkSms to "+bulkSms) 
		
			String sms = bulkSms;	
			
			String delimiter = ",";
			String[] str = sms.split(delimiter);
			String[] mapPair;
	
	  Map<String,String> valueMap= new HashMap<String, String>();
			
        for (int i=0 ; i<str.length ; i++) {
		
             mapPair =str[i].split(":");	
			  mapPair[0] = mapPair[0].replaceAll("[\\[\\]]","");
			  mapPair[1] = mapPair[1].replaceAll("[\\[\\]]","");
			 valueMap.put(mapPair[0], mapPair[1]);
          }
		    render template: 'sms', model: [ bulkSms : bulkSms,valueMap : valueMap ]
		  }else{
		  log.debug("bulkSms to "+bulkSms) 
		
			String sms = bulkSms;	
			
			String delimiter = ",";
			String[] str = sms.split(delimiter);
			String[] mapPair;
	
	  Map<String,String> valueMap= new HashMap<String, String>();
			
        for (int i=0 ; i<str.length ; i++) {
		
             mapPair =str[i].split(":");	
			  mapPair[0] = mapPair[0].replaceAll("[\\[\\]]","");
			  mapPair[1] = mapPair[1].replaceAll("[\\[\\]]","");
			 valueMap.put(mapPair[0], mapPair[1]);
		  }
		    render template: 'sms', model: [ bulkSms : bulkSms,valueMap : valueMap ]
		  }
      
    }	
	
	
	def email = {
	
	def bulkEmail = InternationalDescriptionDTO.executeQuery( "select distinct a.content from InternationalDescriptionDTO a where a.id.tableId ='47' and a.id.foreignId >= '200' ");
		if (bulkEmail == null){
		
			String email = bulkEmail;	
			String delimiter = ",";
			String[] str = email.split(delimiter);
			String[] mapPair;
				      
        Map<String,String> valueMap= new HashMap<String, String>();
         for (int i=0 ; i<str.length ; i++) {
		
             mapPair =str[i].split(":");	
			 mapPair[0] = mapPair[0].replaceAll("[\\[\\]]","");
			  mapPair[1] = mapPair[1].replaceAll("[\\[\\]]","");
			 valueMap.put(mapPair[0], mapPair[1]);
          }
		  render template: 'email', model: [ bulkEmail : bulkEmail,valueMap : valueMap , mapPair : mapPair]
		  }
		  else {
		  String email = bulkEmail;	
			String delimiter = ",";
			String[] str = email.split(delimiter);
			String[] mapPair;
				      
        Map<String,String> valueMap= new HashMap<String, String>();
         for (int i=0 ; i<str.length ; i++) {
		
             mapPair =str[i].split(":");	
			 mapPair[0] = mapPair[0].replaceAll("[\\[\\]]","");
			  mapPair[1] = mapPair[1].replaceAll("[\\[\\]]","");
			 valueMap.put(mapPair[0], mapPair[1]);
		  }
		  render template: 'email', model: [ bulkEmail : bulkEmail,valueMap : valueMap , mapPair : mapPair]
		  }
        
	 
	   }
	   
	   
	 def  sendsms = {
	 		
			String recepient = params.recepient;
			String message = params.message;
		try {
                def sent = webServicesSession.bulkSMS(recepient,message)
					log.debug("sent to "+sent) 
					
                if (sent) {
                    flash.message = 'SMS sent Successfully'
                
                } else {
                    flash.error = 'SMS sending Failed'
              
                }

            } catch (Exception e) {
                log.error("Exception occurred sending email", e)
                flash.error = 'Failed to send the sms'
               
            }
       
        redirect action: 'list', params: [id: params.id]
	 
	 }
	   
    def save = {
   			
			String recepient = params.recepient;
			Integer message = params.int('message');
   
            try {
                def sent = webServicesSession.bulkEmail(recepient,message)
					log.debug("sent to "+sent) 
                if (sent) {
                    flash.message = 'success.email'
                
                } else {
                    flash.error = 'failure.email'
              
                }

            } catch (Exception e) {
                log.error("Exception occurred sending email", e)
                flash.error = 'invoice.prompt.failure.email.invoice'
               
            }
       

        redirect action: 'list', params: [id: params.id]
    }
	   
}
