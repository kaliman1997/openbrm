package jbilling

//import grails.plugins.springsecurity.Secured

import com.sapienter.jbilling.common.SessionInternalError
import java.text.Format;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap;
import com.sapienter.jbilling.client.ViewUtils
import com.sapienter.jbilling.common.SessionInternalError
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;

import com.sapienter.jbilling.server.user.db.CompanyDTO

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
import grails.util.Holders;

import au.com.bytecode.opencsv.CSVWriter
import com.sapienter.jbilling.server.util.csv.CsvExporter
import com.sapienter.jbilling.server.util.csv.Exporter
import com.sapienter.jbilling.client.util.DownloadHelper
import org.apache.log4j.Logger;
import java.text.*;
import java.util.*;
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;
import in.saralam.sbs.server.openRate.destinationMap.db.DestinationMapDTO;
import in.saralam.sbs.server.openRate.destinationMap.DestinationMapWS;
class DestinationMapController {

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
        
       return DestinationMapDTO.createCriteria().list(
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
        def filters = filterService.getFilters(FilterType.DESTINATIONMAP, params)
        log.debug(" filters" + filters)
        def destionmap= getFilteredRates(filters, params)
         log.debug(" destionmap" +destionmap)
		 bindData(destionmap, params)
		 log.debug " rate params is ${params}"
		  log.debug " destination params is ${params.int("id")}"
		 
        def selected = params.id ? webServicesSession.getDestinationMapWS(params.int("id")) : null
       
        log.debug(" selected"+selected)
        breadcrumbService.addBreadcrumb(controllerName, 'list', null, selected?.id)

        if (params.applyFilter || params.partial) {
            render template: 'destinationMap', model: [ destionmap: destionmap, selected: selected, filters: filters ]
        } else {
            [ destionmap: destionmap, selected: selected, filters: filters ]
        }
                 }

   
	def show = {
			DestinationMapWS destinationMapWS = webServicesSession.getDestinationMapWS(params.int('id'))
			render template:'show', model: [destinationMapWS: destinationMapWS]
		}
	
	
	
		def edit = {
			params.max = params?.max?.toInteger() ?: pagination.max
			params.offset = params?.offset?.toInteger() ?: pagination.offset
		
				render template: 'edit'
			
		}
		
		 def save = {
	
		log.debug("In Rate Controllers save action");
			def destinationMapWS ;
			def destinationMap=new DestinationMapWS();
			//bindData(rate, params)
	                  def errorCount=0;
			def temp = null
			try {
							// save or update
				def destinationMapFile = request.getFile("destinationMapFile")
				log.debug("destinationMapFile input file " +destinationMapFile + "content " + params.destinationMapFile?.getContentType().toString());
	
					  /* if (params.ratefile?.getContentType().toString().contains('text/csv')) {*/
				if(!destinationMapFile.empty) {
	
					log.debug("destinationMapFile isn't empty");
                   			def grailsApplication = Holders.getGrailsApplication()				
					def tempDir = grailsApplication.config.dir.variable
					tempDir = tempDir +
						  "//resources//temp" 
						  
					File dir = new File(tempDir)
				
					if(!dir.exists()){					
						dirEx=dir.mkdir()
					}
					
					temp = File.createTempFile('destinationMapf', '.txt',dir)
					destinationMapFile.transferTo(temp)
					File file = new File(temp?.getAbsolutePath());
					BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
	
									log.debug("rate csv saved to: " + temp?.getAbsolutePath());
									
					String[] cols = new Object[5]
					def idx
					def ratews
					def destinationMapTemp
					String line
					def i
	
					while((line = reader.readLine())!= null && line.length()!= 0) {
	
						if(line.startsWith("#")) continue;	//comment
						if(("").equals(line)) continue;		//blank line
	
						cols = line.split(",", -1);
						if(cols.size() !=6) {
							 flash.error = 'file is not in format'
							continue;
						 }
							
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
						
						 String mapGroup=cols[0]
						  log.debug(" map group"+mapGroup)
                                                 String prefix=cols[1]
                                                 String tierCode=cols[2]
                                                 String description=cols[3]
                                                 String category=cols[4]
						 log.debug("category"+category)

						// String  rankTemp=cols[5];
                                                // int rank=Integer.parseInt(rankTemp)
						Integer rank = new Integer(cols[5])
						 log.debug(" rank is"+rank)
						
                                       
					
				
				                       if(errorCount==0) {
							flash.message = 'Destination Map deatils are Sucessfully Loaded'
							 destinationMapWS = new DestinationMapWS( mapGroup,prefix,tierCode,description,category,rank);
                                                          destinationMapTemp=webServicesSession.createDestinationMap(destinationMapWS);
						       
						       }
			                            else{
										flash.info = 'Failed load Rates from file'
																				}
										   
					}
					}
					}
			
			catch (SessionInternalError e) {
							viewUtils.resolveException(flash, session.locale, e)
							chain action: 'list', model: [ selected: destinationMap ]
				return
	
					} finally {
						temp?.delete()
					}
				
	
			chain action: 'list', params: [ id: destinationMap?.id ]
		}
	
	
		def splitfields(line) {
	
		log.debug("splitfields is called");
		return line.split(",", -1);
	
		}
	
	
	  def deleteDestinationMap = {
	  log.debug("Deletin destinationmap ${params.id}.")
        if (params.id) {
	    log.debug("Deletin destinationmap ${params.id}.")
            webServicesSession.deleteDestinationMap(params.int('id'))

            flash.message = 'destination.map.deleted'
            flash.args = [ params.id ]
            log.debug("Deleted destinationmap ${params.id}.")

        }

        // render the partial user list
        params.partial = true
        list()
    }		
	
def csv = {
			def filters = filterService.getFilters(FilterType.DESTINATIONMAP, params)
	
			params.max = CsvExporter.MAX_RESULTS
			def rates = getFilteredRates(filters, params)
	
			if (rates.totalCount > CsvExporter.MAX_RESULTS) {
				flash.error = message(code: 'error.export.exceeds.maximum')
				flash.args = [ CsvExporter.MAX_RESULTS ]
				redirect action: 'list', id: params.id
	
			} else {
				DownloadHelper.setResponseHeader(response, "rates.csv")
				Exporter<DestinationMapDTO> exporter = CsvExporter.createExporter( DestinationMapDTO.class);
				render text: exporter.export(rates), contentType: "text/csv"
			}
		}
}
