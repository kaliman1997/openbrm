package jbilling

import grails.plugins.springsecurity.Secured
import grails.util.Holders;

import com.sapienter.jbilling.common.SessionInternalError
import java.text.Format;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap;
import com.sapienter.jbilling.client.ViewUtils
import com.sapienter.jbilling.common.SessionInternalError
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;
import in.saralam.sbs.server.RumMap.RumMapWS
import com.sapienter.jbilling.server.user.db.CompanyDTO
import in.saralam.sbs.server.RumMap.db.RumMapDTO
import in.saralam.sbs.server.RumMap.db.RumMapDAS
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

class RumMapController {

	static pagination = [ max: 10, offset: 0, sort: 'id', order: 'desc' ]
	
		WebServicesSessionSpringBean webServicesSession = new  WebServicesSessionSpringBean();
		def filterService
	
		ViewUtils viewUtils
		DataSource dataSource
		def breadcrumbService
		
		
		def index = {
        redirect (action: 'list', params: params)
    }

    def private getFilteredRumMap(filters, GrailsParameterMap params) {
	
		
        params.max = params?.max?.toInteger() ?: pagination.max
        params.offset = params?.offset?.toInteger() ?: pagination.offset
        params.sort = params?.sort ?: pagination.sort
        params.order = params?.order ?: pagination.order

        return RumMapDTO.createCriteria().list(
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
        def filters = filterService.getFilters(FilterType.RUMMAP, params)
        log.debug(" filters" + filters)
        def rummap = getFilteredRumMap(filters, params)
         log.debug(" rummap" + rummap)
		 bindData(rummap, params)
		 log.debug " rummap params is ${params}"
	
	   CurrencyDTO currency = new CurrencyDAS().find( new CurrencyBL().getEntityCurrency( session['company_id'].toInteger()));
	  
        def selected = params.id ? webServicesSession.getRumMapWS(params.int("id")) : null
       
        log.debug(" selected "+selected)
        breadcrumbService.addBreadcrumb(controllerName, 'list', null, selected?.id)

        if (params.applyFilter || params.partial) {
            render template: 'rummap', model: [ rummap: rummap, selected: selected,currency:currency, filters: filters ]
        } else {
            [ rummap: rummap, selected: selected,currency:currency, filters: filters ]
        }
    }
		
		
	
			/* @Secured(["ORDER_24"]) */
	
		def show = {
			RumMapWS rumMapws = webServicesSession.getRumMapWS(params.int('id'))
			CurrencyDTO currency = new CurrencyDAS().find( new CurrencyBL().getEntityCurrency( session['company_id'].toInteger()));
			render template:'show', model: [rumMapws: rumMapws,currency:currency]
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
				log.warning("No rum map products Found... create a product in Plans category");
			} else {
				log.debug(planList.length + " RumMap product found ");
			}
		
			
			render template: 'edit', model: [planList: planList]
			
		}
	
	
	
	def save = {
	
		log.debug("In RumMap Controllers save action");
		def rumMap = new RumMapWS();
		//bindData(price, params)
	    def errorCount=0;
		def temp = null
		
		try {
			// save or update
			def rumfile = request.getFile("rumfile")
			log.debug("rumfile : "+ rumfile);
			log.debug("rummap input file " + rumfile + "content " + params.rumfile?.getContentType().toString());
				  
			if(!rumfile.empty) {
			    log.debug("rummapfile isn't empty");
				def grailsApplication = Holders.getGrailsApplication()	
				
				//def tempDir = grailsApplication.config.dir.variable as String
				//tempDir = tempDir + "//resources//temp"
				//log.debug " directory to store the files ${tempDir}"
				

				/*File dir = new File(tempDir)
				
				if(!dir.exists()){					
					dir.mkdir()
				}*/
								
				def csvFile = File.createTempFile("rummap",".csv")				
				rumfile.transferTo(csvFile)				
				File file = new File(csvFile?.getAbsolutePath());				
				BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
				csvFile.deleteOnExit()
	
				log.debug("rummap csv saved to: " + csvFile?.getAbsolutePath());
									
				String[] cols = new Object[7]
				def idx
				def rumws
				def y
				String line
				def i
				    log.debug(" before Integer plan");
				    Integer plan =   params.int('toItemId');
					log.debug(" before selected RumMap  plan " + plan);
				
				
				if(plan == null) {
						flash.error= 'Missing Plan or RumMap File ! Please select Plan and RumMap File'
						/*flash.message= 'Missing Plan or RumMap File ! Please select Plan and RumMap File'*/
						          
					}		
				
					
					
	            log.debug("before while ");
				while((line = reader.readLine())!= null && line.length()!= 0) {
				   log.debug("reader.readLine() : " + reader.readLine());
				   log.debug("line.length() : " + line.length());
				   
				   
				   try{
				    log.debug("try start");
					if(line.startsWith("#")){
					 log.debug("if");
					 continue;
					 }
					log.debug("try end");
					
					}
					catch(Exception e){
					   log.debug("error :"+e);
					}
							
					log.debug("line after :"+line);
					if(("").equals(line)) continue;		//blank line
	                  log.debug("hello");
					cols = line.split(",", -1);
					if(cols.size() !=8) {
						flash.error = 'file is not in format'
						continue;
					}
					log.debug("hello1");		
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
					
					def col0 = cols[0];
					
					def col1 = cols[1];
					
		
					def today = new Date()
					Date lastUpdatedDate = today;
					//idx = col0.indexOf("F089");
					String priceGroup = col0;
					log.debug("priceGroup :"+priceGroup);
					Integer step = Integer.parseInt(col1);
					log.debug("Step : "+step);
					String priceModel = cols[2];
					log.debug("priceModel :"+priceModel);
					String rum1 = cols[3]; 
					log.debug("rum :"+rum1);
                    String resource = cols[4];
					log.debug("resource :"+resource);
					
					Integer resourceId = Integer.parseInt(cols[5]);
					log.debug("resourceId : "+resourceId);
					
					String rumType = cols[6];
					log.debug("rumType :"+rumType);
					
					Integer consumeFlag = Integer.parseInt(cols[7]);
					log.debug("consumeFlag :"+consumeFlag);
					
                    
                    Date createdDate = today;
							
					sdf.setLenient(false);
					Calendar c = Calendar.getInstance();
					
					
					
						
					
					
								
                    if(plan != null){
					     log.debug("  plan not null");
                        errorCount++
						log.debug(" errorCount1 " + errorCount);
                        def entityId = new CompanyDTO(session['company_id'])						
						log.debug("rumws method is called; with entity id " +entityId);
						rumws = new RumMapWS(priceGroup, step, priceModel, rum1, resource, resourceId, rumType , consumeFlag, plan, createdDate,lastUpdatedDate, entityId);
						log.debug("rumws is with : " + priceGroup + step + priceModel + rum1 + resource + resourceId + rumType + consumeFlag + plan + createdDate + lastUpdatedDate + entityId);
						y = webServicesSession.createRumMap(rumws);
                    }						  
				}				
			}
				
			log.debug(" errorCount2 " + errorCount);		
			//def rumwsd = new RumMapWS();
			if(errorCount!=0) {
				flash.message = 'Rum Map deatils are Sucessfully Loaded'
			}else{
				flash.info = 'Failed to load rum map from file'
			}										   
		}catch (SessionInternalError e) {
				viewUtils.resolveException(flash, session.locale, e)
				chain action: 'list', model: [ selected: rumMap ]
				return
	
		}finally {
			temp?.delete()
		}				
	
		chain action: 'list', params: [ id: rumMap?.id ]		
	}	
	
	
	def splitfields(line) {
	
		log.debug("splitfields is called");
		return line.split(",", -1);
	}
	
    def deleteRumMap = {
		log.debug "delete action is triggered"
        if (params.id) {
            webServicesSession.deleteRumMap(params.int('id'))

            flash.message = 'rumMap.deleted'
            flash.args = [ params.id ]
            log.debug("Deleted rumMap ${params.id}.")

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
		def filters = filterService.getFilters(FilterType.RUMMAP, params)
	
		params.max = CsvExporter.MAX_RESULTS
		def rummap = getFilteredRumMap(filters, params)
	
		if (rummap.totalCount > CsvExporter.MAX_RESULTS) {
			flash.error = message(code: 'error.export.exceeds.maximum')
			flash.args = [ CsvExporter.MAX_RESULTS ]
			redirect action: 'list', id: params.id
	
		} else {
			DownloadHelper.setResponseHeader(response, "rummap.csv")
			Exporter<RumMapDTO> exporter = CsvExporter.createExporter(RumMapDTO.class);
			render text: exporter.export(prices), contentType: "text/csv"
		}
	}
		
  Integer entityId = webServicesSession.getCallerCompanyId();
       	  def description = "Plans";
	      def planList = webServicesSession.getItemByCategory(new ItemTypeDAS().findByDescription(entityId, description).getId());

}
