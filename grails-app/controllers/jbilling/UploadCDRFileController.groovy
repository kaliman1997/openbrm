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

import in.saralam.sbs.server.RateCard.db.RateDAS;
import in.saralam.sbs.server.RateCard.db.RateDTO;

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
import in.saralam.sbs.server.openRate.uploadCDRFile.db.UploadCDRFileDTO;
import in.saralam.sbs.server.openRate.uploadCDRFile.db.UploadCDRFileDAS;
import in.saralam.sbs.server.openRate.uploadCDRFile.UploadCDRFileWS;
import grails.util.Holders;
import com.sapienter.jbilling.server.util.PreferenceBL;
import com.sapienter.jbilling.server.util.db.PreferenceDTO;
import com.sapienter.jbilling.server.util.db.PreferenceTypeDTO;
import com.sapienter.jbilling.server.util.PreferenceWS;
import com.sapienter.jbilling.common.CommonConstants;
class UploadCDRFileController {
     def PREFERENCE_CDR_STORAGE_DIRECTORY= new Integer(999);
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
        
       return UploadCDRFileDTO.createCriteria().list(
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

		def list = 
		{
                 
		  log.debug("first comes in action list when click on Bundle");
        def filters = filterService.getFilters(FilterType.UPLOADCDRFILE, params)
        log.debug(" filters" + filters)
        def upldcdr= getFilteredRates(filters, params)
         log.debug(" uploadcdrfile" +upldcdr)
		 bindData(upldcdr, params)
		 log.debug " upload cdr  params is ${params}"
		 
		 
        def selected = params.id ? webServicesSession.getUploadCDRFileWS(params.int("id")) : null 
      
        log.debug(" selected"+selected)
        breadcrumbService.addBreadcrumb(controllerName, 'list', null, selected?.id)

        if (params.applyFilter || params.partial) {
            render template: 'uploadCDRFile', model: [ upldcdr: upldcdr, selected: selected, filters: filters ]
        } else {
            [ upldcdr: upldcdr, selected: selected, filters: filters ]
        }
                 } 

   
	def show = {
		UploadCDRFileWS uploadCDRFileWS = webServicesSession.getUploadCDRFileWS(params.int('id'))
			render template:'show', model: [uploadCDRFileWS: uploadCDRFileWS]  
			
		}
	
	
	
		def edit = {
			params.max = params?.max?.toInteger() ?: pagination.max
			params.offset = params?.offset?.toInteger() ?: pagination.offset
		
				render template: 'edit'
			
		}	
		
	 def save = {
	
		    log.debug("In upload cdr file Controllers save action");
			def uploadCDRFileWS ;
			def  UploadCDR=new UploadCDRFileWS();
			//bindData(rate, params)
	                  def errorCount=0;
			def mediation = null
			
			try {
							// save or update
			  
				def UploadcdrFile = request.getFile("UploadcdrFile")
				String name = UploadcdrFile.getOriginalFilename()
				String type = UploadcdrFile.getContentType()
		/*		log.debug("Upload CDR input file " +UploadcdrFile + "content " + params.UploadcdrFile?.getContentType().toString()); */
	
					  /* if (params.ratefile?.getContentType().toString().contains('text/csv')) {*/
				if(!UploadcdrFile.empty) {

					log.debug("UploadCDRFile isn't empty");
					
					PreferenceWS preferenceWS =  webServicesSession.getPreference(PREFERENCE_CDR_STORAGE_DIRECTORY)
					log.debug("preferenceWS..."+preferenceWS)
					def mediationDir = preferenceWS.getValue();
				log.debug("mediationDir..."+mediationDir)
				File dir = new File(mediationDir) 
                          if (!dir.exists())
						  {
                                dir.mkdir();
                          } 
				mediation = File.createTempFile(name,'.txt',dir)
			    UploadcdrFile.transferTo(mediation)
				File file = new File(mediation?.getAbsolutePath());
				BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
				log.debug("Upload CDR csv saved to: " + mediation?.getAbsolutePath()); 
									
					String[] cols = new Object[2]
					def idx
					def ratews
					def UploadCDRFileTemp
					String line
					def i
						SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
					         Date date = new Date();
							 uploadCDRFileWS = new UploadCDRFileWS(name,date,"Uploaded",type);
                             UploadCDRFileTemp=webServicesSession.createUploadCDRFile(uploadCDRFileWS);
					}
					}
			
			catch (SessionInternalError e) {
							viewUtils.resolveException(flash, session.locale, e)
							chain action: 'list', model: [ selected: UploadCDR ]
				return
	
					} finally {
						mediation?.delete()
					}
				
	
			chain action: 'list', params: [ id: UploadCDR?.id ]
		}
	
	
		def splitfields(line) {
	
		log.debug("splitfields is called");
		return line.split(",", -1);
	
		}
		  def deleteUploadCDRFile = {
        if (params.id) {
            webServicesSession.deleteUploadCDRFile(params.int('id'))

            flash.message = 'uploadcdr.deleted'
            flash.args = [ params.id ]
            log.debug("Deleted UploadCDRFile ${params.id}.")

        }

        // render the partial user list
        params.partial = true
        list()
    }		
	
def csv = {
			def filters = filterService.getFilters(FilterType.UPLOADCDRFILE, params)
	
			params.max = CsvExporter.MAX_RESULTS
			def rates = getFilteredRates(filters, params)
	
			if (rates.totalCount > CsvExporter.MAX_RESULTS) {
				flash.error = message(code: 'error.export.exceeds.maximum')
				flash.args = [ CsvExporter.MAX_RESULTS ]
				redirect action: 'list', id: params.id
	
			} else {
				DownloadHelper.setResponseHeader(response, "rates.csv")
				Exporter<UploadCDRFileDTO> exporter = CsvExporter.createExporter( UploadCDRFileDTO.class);
				render text: exporter.export(rates), contentType: "text/csv"
			}
		}
			
	
		
}
