package jbilling

import grails.plugins.springsecurity.Secured

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
//import grails.plugins.springsecurity.Secured
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
import in.saralam.sbs.server.openRate.holidayMap.db.HolidayMapDTO;
import in.saralam.sbs.server.openRate.holidayMap.HolidayMapWS;
import grails.util.Holders;

class HolidayMapController {

	static pagination = [ max: 10, offset: 0, sort: 'id', order: 'desc' ]
	
	WebServicesSessionSpringBean webServicesSession = new  WebServicesSessionSpringBean();
	def filterService
	
	ViewUtils viewUtils
	DataSource dataSource
	def breadcrumbService
		
		
	def private getFilteredRates(filters, GrailsParameterMap params) {
	
		
        params.max = params?.max?.toInteger() ?: pagination.max
        params.offset = params?.offset?.toInteger() ?: pagination.offset
        params.sort = params?.sort ?: pagination.sort
        params.order = params?.order ?: pagination.order
        
       return HolidayMapDTO.createCriteria().list(
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
              
               //  eq('deleted', 0)
              
		
            // apply sorting
            SortableCriteria.sort(params, delegate)
        }
    }



		
		
		
		
		def list = {
                 
	

		  log.debug("first comes in action list when click on Bundle");
        def filters = filterService.getFilters(FilterType.HOLIDAYMAP, params)
        log.debug(" filters" + filters)
        def holidmap= getFilteredRates(filters, params)
         log.debug(" holidaymap" +holidmap)
		 bindData(holidmap, params)
		 log.debug " holiday params is ${params}"
		 
		 
        def selected = params.id ? webServicesSession.getHolidayMapWS(params.int("id")) : null
       
        log.debug(" selected"+selected)
        breadcrumbService.addBreadcrumb(controllerName, 'list', null, selected?.id)

        if (params.applyFilter || params.partial) {
            render template: 'holidayMap', model: [ holidmap: holidmap, selected: selected, filters: filters ]
        } else {
            [ holidmap: holidmap, selected: selected, filters: filters ]
        }
                 }

   
	def show = {
			HolidayMapWS holidayMapWS = webServicesSession.getHolidayMapWS(params.int('id'))
			render template:'show', model: [holidayMapWS: holidayMapWS]
		}
	
	
	
		def edit = {
			params.max = params?.max?.toInteger() ?: pagination.max
			params.offset = params?.offset?.toInteger() ?: pagination.offset
		
				render template: 'edit'
			
		}	
		
	
	 def save = {
	
		log.debug("In holiday mpa Controllers save action");
			def holidayMapWS ;
			def  holidayMap=new HolidayMapWS();
			//bindData(rate, params)
	                  def errorCount=0;
			def temp = null
			try {
							// save or update
				def holidayMapFile = request.getFile("holidayMapFile")
				log.debug("holidayMapFile input file " +holidayMapFile + "content " + params.holidayMapFile?.getContentType().toString());
	
					  /* if (params.ratefile?.getContentType().toString().contains('text/csv')) {*/
				if(!holidayMapFile.empty) {
	
					log.debug("holidayMapFile isn't empty");
                    			def grailsApplication = Holders.getGrailsApplication()				
					def tempDir = grailsApplication.config.dir.variable
					tempDir = tempDir +
						  "//resources//temp" 
					File dir = new File(tempDir)
				
					if(!dir.exists()){					
						dirEx=dir.mkdir()
					}
					
					temp = File.createTempFile('holidayMapf', '.txt',dir)
					holidayMapFile.transferTo(temp)
					File file = new File(temp?.getAbsolutePath());
					BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
	
									log.debug("rate csv saved to: " + temp?.getAbsolutePath());
									
					String[] cols = new Object[4]
					def idx
					def ratews
					def holidayMapTemp
					String line
					def i
	
					while((line = reader.readLine())!= null && line.length()!= 0) {
	
						if(line.startsWith("#")) continue;	//comment
						if(("").equals(line)) continue;		//blank line
	
						cols = line.split(",", -1);
						if(cols.size() !=5) {
							 flash.error = 'file is not in format'
							continue;
						 }
							
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
						
						 String mapGroup=cols[0]
						  log.debug(" map group"+mapGroup)
						  Integer day = new Integer(cols[1])
						  Integer month = new Integer(cols[2])
						  Integer year = new Integer(cols[3])
                                                 
                                                 String description=cols[4]
                                                
						 log.debug("month"+month)

					
                                       
					
				
				                       if(errorCount==0) {
							flash.message = 'Holiday Map deatils are Sucessfully Loaded'
							 holidayMapWS = new HolidayMapWS( mapGroup,day,month, year,description);
                                                          holidayMapTemp=webServicesSession.createHolidayMap(holidayMapWS);
						       
						       }
			                            else{
										flash.info = 'Failed load Rates from file'
																				}
										   
					}
					}
					}
			
			catch (SessionInternalError e) {
							viewUtils.resolveException(flash, session.locale, e)
							chain action: 'list', model: [ selected: holidayMap ]
				return
	
					} finally {
						temp?.delete()
					}
				
	
			chain action: 'list', params: [ id: holidayMap?.id ]
		}
	
	
		def splitfields(line) {
	
		log.debug("splitfields is called");
		return line.split(",", -1);
	
		}
		  def delete = {
        if (params.id) {
            webServicesSession.deleteHolidayMap(params.int('id'))

            flash.message = 'holiday.map.deleted'
            flash.args = [ params.id ]
            log.debug("Deleted holidaymap ${params.id}.")

        }

        // render the partial user list
        params.partial = true
        list()
    }		
	
def csv = {
			def filters = filterService.getFilters(FilterType.HOLIDAYMAP, params)
	
			params.max = CsvExporter.MAX_RESULTS
			def rates = getFilteredRates(filters, params)
	
			if (rates.totalCount > CsvExporter.MAX_RESULTS) {
				flash.error = message(code: 'error.export.exceeds.maximum')
				flash.args = [ CsvExporter.MAX_RESULTS ]
				redirect action: 'list', id: params.id
	
			} else {
				DownloadHelper.setResponseHeader(response, "rates.csv")
				Exporter<HolidayMapDTO> exporter = CsvExporter.createExporter( HolidayMapDTO.class);
				render text: exporter.export(rates), contentType: "text/csv"
			}
		}
			
	
		
}
