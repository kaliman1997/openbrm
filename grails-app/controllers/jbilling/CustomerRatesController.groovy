package jbilling

//import grails.plugins.springsecurity.Secured

import com.sapienter.jbilling.common.SessionInternalError
import java.text.Format;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap;

import com.sapienter.jbilling.client.ViewUtils;
import com.sapienter.jbilling.common.SessionInternalError
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;
import in.saralam.sbs.server.RateCard.RateWS
import com.sapienter.jbilling.server.user.db.CompanyDTO
import in.saralam.sbs.server.RateCard.db.RateDTO
import in.saralam.sbs.server.RateCard.db.RateDAS
import com.sapienter.jbilling.server.util.db.AbstractDAS
import com.sapienter.jbilling.server.item.db.ItemTypeDAS

import com.sapienter.jbilling.common.SessionInternalError
import com.sapienter.jbilling.client.ViewUtils
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

import org.hibernate.FetchMode
import org.hibernate.criterion.Restrictions
import org.hibernate.criterion.Criterion
import org.hibernate.Criteria
import com.sapienter.jbilling.client.util.SortableCriteria
//import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured


import com.sapienter.jbilling.server.util.IWebServicesSessionBean;

import org.springframework.jdbc.datasource.DataSourceUtils
import javax.sql.DataSource

import au.com.bytecode.opencsv.CSVWriter
import com.sapienter.jbilling.server.util.csv.CsvExporter
import com.sapienter.jbilling.server.util.csv.Exporter
import com.sapienter.jbilling.client.util.DownloadHelper
import org.apache.log4j.Logger;
import java.text.*;
import java.util.*;
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;
import grails.util.Holders;

/**
* MediationConfigController
*
* @author Vikas Bodani
* @since 15-Feb-2011
*/

class CustomerRatesController {
   	
	static pagination = [ max: 10, offset: 0, sort: 'id', order: 'desc' ]
	
	WebServicesSessionSpringBean webServicesSession = new  WebServicesSessionSpringBean();
	def filterService

	ViewUtils viewUtils
	DataSource dataSource
	def breadcrumbService
		
		
	def index = {
        	redirect action: 'list', params: params
	}
		
	def private getFilteredRates(filters, GrailsParameterMap params) {
		
        params.max = params?.max?.toInteger() ?: pagination.max
        params.offset = params?.offset?.toInteger() ?: pagination.offset
        params.sort = params?.sort ?: pagination.sort
        params.order = params?.order ?: pagination.order

        return RateDTO.createCriteria().list(
                max:    params.max,
                offset: params.offset
        ) {
           
            and {
                filters.each { filter ->
                       log.debug("fileter  field ${filter.field}")
                    if (filter.value) {
                                   
                             addToCriteria(filter.getRestrictions());
                       }
                       }
					  
                      }
              
                 eq('deleted', 0)
              
		
            // apply sorting
            SortableCriteria.sort(params, delegate)
        }
    }

	def list = {
               
			  log.debug("first comes in action list when click on Bundle");
        def filters = filterService.getFilters(FilterType.RATE, params)
        log.debug(" filters" + filters)
        def rates = getFilteredRates(filters, params)
         log.debug(" rates" + rates)
		 bindData(rates, params)
		 log.debug " rate params is ${params}"
		 
		 
        def selected = params.id ? webServicesSession.getRateWS(params.int("id")) : null
       render view: 'list', model: [ rates: rates, selected: selected, filters: filters ]
        log.debug(" selected"+selected)
        breadcrumbService.addBreadcrumb(controllerName, 'list', null, selected?.id)
			
		}	
		
		def editList ={
                      render view: 'editList'  
		}
		
		
		def allrates = {
        def filters = filterService.getFilters(FilterType.RATE, params)

        def rateslist =  webServicesSession.getRateList(20,1);

        render template: 'rates', model: [ rateslist: rateslist ]
    }
		
		def rates = {
		redirect action: 'rates'
    }
		
	
			/* @Secured(["ORDER_24"]) */
	
		def show = {
			RateWS ratews = webServicesSession.getRateWS(params.int('id'));
			render template:'show', model: [ratews: ratews]
		}
	
	def edit = {
			params.max = params?.max?.toInteger() ?: pagination.max
			params.offset = params?.offset?.toInteger() ?: pagination.offset
		
				render : 'edit'
			
		}
	
       
    

    
		
		
    def customerRates = {
		
	}
		
    		 def save = {
	
		log.debug("In Rate Controllers save action");
			def rate = new RateWS();
			//bindData(rate, params)
	                  def errorCount=0;
			def temp = null
			try {
							// save or update
				def ratefile = request.getFile("ratefile")
				log.debug("rate input file " + ratefile + "content " + params.ratefile?.getContentType().toString());
	
					  /* if (params.ratefile?.getContentType().toString().contains('text/csv')) {*/
				if(!ratefile.empty) {
	
					log.debug("ratefile isn't empty");
                    			def grailsApplication = Holders.getGrailsApplication()				
					def tempDir = grailsApplication.config.dir.variable
					tempDir = tempDir +
						  "//resources//temp"
						  
					File dir = new File(tempDir)
				
					if(!dir.exists()){					
						dirEx=dir.mkdir()
					}
					
					temp = File.createTempFile('rates', '.txt', dir)
					ratefile.transferTo(temp)
					File file = new File(temp?.getAbsolutePath());
					BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
	
									log.debug("rate csv saved to: " + temp?.getAbsolutePath());
									
					String[] cols = new Object[6]
					def idx
					def ratews
					def y
					String line
					def i
	
					while((line = reader.readLine())!= null && line.length()!= 0) {
	
						if(line.startsWith("#")) continue;	//comment
						if(("").equals(line)) continue;		//blank line
	
						cols = line.split(",", -1);
						if(cols.size() != 7) {
							 flash.error = 'file is not in format'
							continue;
						 }
							
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
						
						def col0 = cols[0];
						def col1 = cols[1];
		
						def today = new Date()
						Date lastUpdatedDate = today;
						//idx = col0.indexOf("F089");
						String prefix = col0;
						String destination = col1;
                                          BigDecimal flatRate;
                                          BigDecimal conncharge;
                                          BigDecimal scaledRate;
                                          
                                          if(!cols[2].isEmpty()){
                                          flatRate = new BigDecimal(cols[2]); }
                                          if(!cols[3].isEmpty()){
						conncharge = new BigDecimal(cols[3]); }
                                          if(!cols[4].isEmpty()){
						scaledRate = new BigDecimal(cols[4]); }
						
                                          Date createdDate = today;
						Date validFrom = today;
						Date validTo = null;
						
							
							sdf.setLenient(false);
							Calendar c = Calendar.getInstance();
							
							if (cols[5] != "") {
							validFrom = sdf.parse(cols[5]);
							c.setTime(validFrom);
							int vf = c.get(Calendar.YEAR);
							log.debug("valid from is : " +validFrom);
							if (vf > 1000){
								log.debug("valid from year..." +vf);
							}
							else{
								continue;
							}
							}
							if(cols[6] != ""){
							validTo = sdf.parse(cols[6]);
							c.setTime(validTo);
							int vt = c.get(Calendar.YEAR);
							log.debug("valid to is : " +validTo);
							if (vt > 1000){
								log.debug("valid to  year..." +vt);
							}
							else{
								continue;
							}
							}
						
						
						
	                            Integer plan =  3200
                                log.debug(" selected rate  plan " + plan)
								
								
								if(plan == null) {
						 flash.error= 'Missing Plan or Rates File ! Please select Plan and Rates File'
						  /*flash.message= 'Missing Plan or Rates File ! Please select Plan and Rates File'*/
						          
						  }
								
                                if(plan != null){
                                errorCount++
                               					
						log.debug("ratews method is called..");
						ratews = new RateWS(prefix, destination, flatRate, conncharge, scaledRate, plan, createdDate, validFrom, validTo, lastUpdatedDate);
						log.debug("ratews is with : " + prefix + destination + flatRate + conncharge + scaledRate);
						y = webServicesSession.createRate(ratews);
                                                   }
						 
						 
						  
					}
				
				}
				
					
				//def ratewsd = new RateWS();
				                       if(errorCount!=0) {
							flash.message = 'RATE CARD deatils are Sucessfully Loaded'
							 
                                                       }
			                            else{
										flash.info = 'Failed load Rates from file'
																				}
										   
					}
			
			catch (SessionInternalError e) {
							viewUtils.resolveException(flash, session.locale, e)
							chain action: 'list', model: [ selected: rate ]
				return
	
					} finally {
						temp?.delete()
					}
				
	
			chain action: 'list', params: [ id: rate?.id ]
		}
	
	
		def splitfields(line) {
	
		log.debug("splitfields is called");
		return line.split(",", -1);
	
		}
	
	    Integer entityId = webServicesSession.getCallerCompanyId();
       	def description = "Plans";
	      def planList = webServicesSession.getItemByCategory(new ItemTypeDAS().findByDescription(entityId, description).getId());
	
		  
	
}



