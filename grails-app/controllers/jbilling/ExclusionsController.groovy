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
import in.saralam.sbs.server.Exclusions.ExclusionWS
import com.sapienter.jbilling.server.user.db.CompanyDTO
import in.saralam.sbs.server.Exclusions.db.ExclusionDTO
import in.saralam.sbs.server.Exclusions.db.ExclusionDAS
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

import com.sapienter.jbilling.server.item.CurrencyBL
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
import com.sapienter.jbilling.server.util.db.CurrencyDTO;
import com.sapienter.jbilling.server.util.db.CurrencyDAS;
import grails.util.Holders;
class ExclusionsController {

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

		return ExclusionDTO.createCriteria().list(
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
				 eq('entity', new CompanyDTO(session['company_id']))	  
				 eq('deleted', 0)
			  
		
			// apply sorting
			SortableCriteria.sort(params, delegate)
		}
	}

	  
	def list = {
		log.debug("first comes in action list when click on Bundle");
		def filters = filterService.getFilters(FilterType.EXCLUSION, params)
		log.debug(" filters" + filters)
		
	def exclusions = getFilteredRates(filters, params)
		 log.debug(" exclusions" + exclusions)
		 bindData(exclusions, params)
		 log.debug " exclusions params is ${params}"       
	
	
	   CurrencyDTO currency = new CurrencyDAS().find( new CurrencyBL().getEntityCurrency( session['company_id'].toInteger()));
	  
		def selected = params.id ? webServicesSession.getExclusionWS(params.int("id")) : null
	   
		log.debug(" selected"+selected)
		breadcrumbService.addBreadcrumb(controllerName, 'list', null, selected?.id)

		if (params.applyFilter || params.partial) {
			render template: 'exclusion', model: [ exclusions: exclusions,selected: selected,currency:currency, filters: filters ]
		} else {
			[exclusions: exclusions, selected: selected,currency:currency, filters: filters ]
		}
	}
		
	
			/* @Secured(["ORDER_24"]) */
	
		def show = {
			ExclusionWS exclusionws = webServicesSession.getExclusionWS(params.int('id'))
			 CurrencyDTO currency = new CurrencyDAS().find( new CurrencyBL().getEntityCurrency( session['company_id'].toInteger()));
			render template:'show', model: [exclusionws: exclusionws,currency:currency]
		}
	
	
	
		def edit = {
			params.max = params?.max?.toInteger() ?: pagination.max
			params.offset = params?.offset?.toInteger() ?: pagination.offset
		Integer entityId = webServicesSession.getCallerCompanyId();
			def description = "Plans";
			Integer catId = new ItemTypeDAS().findByDescription(entityId, description).getId();
		
			log.debug(" Fetching Plan type products for entity  " + entityId + " description = " + description + " Item Type Id " + catId);
			def planList = webServicesSession.getItemByCategory(catId);
		
			if(planList == null ) {
				log.warning("No Rating products Found... create a product in Plans category");
			} else {
				log.debug(planList.length + " Rating product found ");
			}
		
			
			render template: 'edit', model: [planList: planList]
			
		}
	
	
	
		 def save = {
	
		log.debug("In Exclusion Controllers save action");
			def exclusion = new ExclusionWS();
			//bindData(exclusion, params)
					  def errorCount=0;
			def temp = null
			try {
							// save or update
				def exclusionfile = request.getFile("exclusionfile")
				log.debug("exclusion input file " + exclusionfile + "content " + params.exclusionfile?.getContentType().toString());
	
					  /* if (params.exclusionfile?.getContentType().toString().contains('text/csv')) {*/
				if(!exclusionfile.empty) {
	
					log.debug("exclusionfile isn't empty");
					def grailsApplication = Holders.getGrailsApplication()				
					//def tempDir = grailsApplication.config.dir.variable
					//tempDir = tempDir + "//resources//temp"
						  
					/*File dir = new File(tempDir)
				
					if(!dir.exists()){					
						dirEx=dir.mkdir()
					}*/
					
					def csvFile = File.createTempFile("exclusions",".csv")
					exclusionfile.transferTo(csvFile)
					File file = new File(csvFile?.getAbsolutePath());
					BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
					csvFile.deleteOnExit()
	
				   log.debug("exclusion csv saved to: " + temp?.getAbsolutePath());
									
					String[] cols = new Object[5]
					log.debug("exclusionws method is called..1" + cols);
					def idx
					def exclusionws
					def y
					String line
					def i
					while((line = reader.readLine())!= null && line.length()!= 0) {
					log.debug("exclusionws method is called..11");
	
						if(line.startsWith("#")) continue;	//comment
						log.debug("exclusionws method is called..12");
						if(("").equals(line)) continue;		//blank line
						log.debug("exclusionws method is called..13");
	
						cols = line.split(",", -1);
						if(cols.size() !=6) {
							 flash.error = 'file is not in format'
							continue;
						 }
							log.debug("exclusionws method is called..2");
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
						
						def col0 = cols[0];
						def col1 = cols[1];
		                def col2 = cols[2];
						def col3 = cols[3];
						def today = new Date()
						Date lastUpdatedDate = today;
						//idx = col0.indexOf("F089");
						String prefix = col0;
						String destination = col1;
						String field1 = col2;
						String field2 = col3;
										  
								log.debug("exclusionws method is called..3");		 
						Date createdDate = today;
						Date validFrom = today;
						Date validTo = null;
						
							
							sdf.setLenient(false);
							Calendar c = Calendar.getInstance();
							
							if (cols[4] != "") {
							validFrom = sdf.parse(cols[4]);
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
							log.debug("exclusionws method is called..4");
							if(cols[5] != ""){
							validTo = sdf.parse(cols[5]);
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
						
						
						log.debug("exclusionws method is called..5");
								Integer plan =   params.int('toItemId')
								log.debug(" selected exclusion " + plan)
								
								
								if(plan == null) {
						 flash.error= 'Missing Plan or Rates File ! Please select Plan and Rates File'
						  /*flash.message= 'Missing Plan or Rates File ! Please select Plan and Rates File'*/
								  
					  }
								
								if(plan != null){
								errorCount++
												   
						log.debug("exclusionws method is called..");
						def entityId = new CompanyDTO(session['company_id'])
						exclusionws = new ExclusionWS(prefix, destination,field1,field2, plan, createdDate, validFrom, validTo, lastUpdatedDate, entityId);
						log.debug("exclusionws is with : " + prefix + destination + field1 + field2 );
						y = webServicesSession.createExclusion(exclusionws);
												   }
						 
						 
						  
					}
				
				}
				
					
				//def exclusionwsd = new ExclusionWS();
									   if(errorCount!=0) {
							flash.message = 'Exclusion details are Sucessfully Loaded'
							 
													   }
										else{
										flash.info = 'Failed load Exclusions from file'
																				}
										   
					}
			
			catch (SessionInternalError e) {
							viewUtils.resolveException(flash, session.locale, e)
							chain action: 'list', model: [ selected: exclusion ]
				return
	
					} finally {
						temp?.delete()
					}
				
	
			chain action: 'list', params: [ id: exclusion?.id ]
		}
	
	
		def splitfields(line) {
	
		log.debug("splitfields is called");
		return line.split(",", -1);
	
		}
	
	def delete = {
		if (params.id) {
			webServicesSession.deleteExclusion(params.int('id'))

			flash.message = 'excluion.deleted'
			flash.args = [ params.id ]
			log.debug("Deleted exclusion ${params.id}.")

		}

		// render the partial user list
		params.partial = true
		list()
	}
	  def getCurrencies() {
		def currencies = new CurrencyBL().getCurrencies(session['language_id'].toInteger(), session['company_id'].toInteger())
		return currencies.findAll{ it.inUse }
	}

	def csv = {
			def filters = filterService.getFilters(FilterType.EXCLUSION, params)
	
			params.max = CsvExporter.MAX_RESULTS
			def exclusions = getFilteredRates(filters, params)
	
			if (exclusions.totalCount > CsvExporter.MAX_RESULTS) {
				flash.error = message(code: 'error.export.exceeds.maximum')
				flash.args = [ CsvExporter.MAX_RESULTS ]
				redirect action: 'list', id: params.id
	
			} else {
				DownloadHelper.setResponseHeader(response, "exclusions.csv")
				Exporter<ExclusionDTO> exporter = CsvExporter.createExporter(ExclusionDTO.class);
				render text: exporter.export(exclusions), contentType: "text/csv"
			}
		}
		
		
	   
		   Integer entityId = webServicesSession.getCallerCompanyId();
		 def description = "Plans";
		  def planList = webServicesSession.getItemByCategory(new ItemTypeDAS().findByDescription(entityId, description).getId());

}
