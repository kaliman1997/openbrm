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
import in.saralam.sbs.server.PriceModel.PriceModelWS
import com.sapienter.jbilling.server.user.db.CompanyDTO
import in.saralam.sbs.server.PriceModel.db.PriceModelDTO
import in.saralam.sbs.server.PriceModel.db.PriceModelDAS
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

class PriceModelController {

	static pagination = [ max: 10, offset: 0, sort: 'id', order: 'desc' ]
	
		WebServicesSessionSpringBean webServicesSession = new  WebServicesSessionSpringBean();
		def filterService
	
		ViewUtils viewUtils
		DataSource dataSource
		def breadcrumbService
		
		
		def index = {
        redirect (action: 'list', params: params)
    }

    def private getFilteredPriceModel(filters, GrailsParameterMap params) {
	
		
        params.max = params?.max?.toInteger() ?: pagination.max
        params.offset = params?.offset?.toInteger() ?: pagination.offset
        params.sort = params?.sort ?: pagination.sort
        params.order = params?.order ?: pagination.order

        return PriceModelDTO.createCriteria().list(
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
        def filters = filterService.getFilters(FilterType.PRICEMODEL, params)
        log.debug(" filters" + filters)
        def prices = getFilteredPriceModel(filters, params)
         log.debug(" prices" + prices)
		 bindData(prices, params)
		 log.debug " price params is ${params}"
	
	   CurrencyDTO currency = new CurrencyDAS().find( new CurrencyBL().getEntityCurrency( session['company_id'].toInteger()));
	  
        def selected = params.id ? webServicesSession.getPriceModelWS(params.int("id")) : null
       
        log.debug(" selected "+selected)
        breadcrumbService.addBreadcrumb(controllerName, 'list', null, selected?.id)

        if (params.applyFilter || params.partial) {
            render template: 'prices', model: [ prices: prices, selected: selected,currency:currency, filters: filters ]
        } else {
            [ prices: prices, selected: selected,currency:currency, filters: filters ]
        }
    }
		
	
			/* @Secured(["ORDER_24"]) */
	
		def show = {
			PriceModelWS pricews = webServicesSession.getPriceModelWS(params.int('id'))
			CurrencyDTO currency = new CurrencyDAS().find( new CurrencyBL().getEntityCurrency( session['company_id'].toInteger()));
			render template:'show', model: [pricews: pricews,currency:currency]
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
				log.warning("No priceModel products Found... create a product in Plans category");
			} else {
				log.debug(planList.length + " PriceModel product found ");
			}
		
			
			render template: 'edit', model: [planList: planList]
			
		}
	
	
	
	def save = {
	
		log.debug("In PriceModel Controllers save action");
		def price = new PriceModelWS();
		//bindData(price, params)
	    def errorCount=0;
		def temp = null
		
		try {
			// save or update
			def pricefile = request.getFile("pricefile")
			log.debug("pricemodel input file " + pricefile + "content " + params.pricefile?.getContentType().toString());
				  
			if(!pricefile.empty) {
			    log.debug("pricemodelfile isn't empty");
				def grailsApplication = Holders.getGrailsApplication()	
				
				//def tempDir = grailsApplication.config.dir.variable as String
				//tempDir = tempDir + "//resources//temp"
				//log.debug " directory to store the files ${tempDir}"
				

				/*File dir = new File(tempDir)
				
				if(!dir.exists()){					
					dir.mkdir()
				}*/
								
				def csvFile = File.createTempFile("pricemodel",".csv")				
				pricefile.transferTo(csvFile)				
				File file = new File(csvFile?.getAbsolutePath());				
				BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
				csvFile.deleteOnExit()
	
				log.debug("pricemodel csv saved to: " + csvFile?.getAbsolutePath());
									
				String[] cols = new Object[6]
				def idx
				def pricews
				def y
				String line
				def i
				    log.debug(" before Integer plan");
				    Integer plan =   params.int('toItemId');
					log.debug(" before selected PriceModel  plan " + plan);
				
				
				if(plan == null) {
						flash.error= 'Missing Plan or PriceModel File ! Please select Plan and PriceModel File'
						/*flash.message= 'Missing Plan or PriceModel File ! Please select Plan and PriceModel File'*/
						          
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
					if(cols.size() !=7) {
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
					String priceModel = col0;
					log.debug("priceModel :"+priceModel);
					Integer qtyStep = Integer.parseInt(col1);
					log.debug("qtyStep : "+qtyStep);
					Integer tierFrom = Integer.parseInt(cols[2]);
					log.debug("tierFrom :"+tierFrom);
					Integer tierTo = Integer.parseInt(cols[3]); 
					log.debug("tierTo :"+tierTo);
                    BigDecimal beat;
					
                    BigDecimal factor;
					
                    
					
                    if(!cols[4].isEmpty()){
						beat = new BigDecimal(cols[4]); 
					}
					log.debug("beat : "+beat);
                    if(!cols[5].isEmpty()){
						factor = new BigDecimal(cols[5]); 
					}
					log.debug("factor : "+factor);
					Integer chargeBase = Integer.parseInt(cols[6]);
					log.debug("chargeBase : "+chargeBase);
                    Date createdDate = today;
					
					
					
					
							
					sdf.setLenient(false);
					Calendar c = Calendar.getInstance();
					
					
					
						
					
					
								
                    if(plan != null){
					     log.debug("  plan not null");
                        errorCount++
						log.debug(" errorCount1 " + errorCount);
                        def entityId = new CompanyDTO(session['company_id'])						
						log.debug("pricews method is called; with entity id " +entityId);
						pricews = new PriceModelWS(priceModel, qtyStep, tierFrom, tierTo, beat, factor, chargeBase , plan, createdDate,lastUpdatedDate, entityId);
						log.debug("pricews is with : " + priceModel + qtyStep + tierFrom + tierTo + beat + factor + chargeBase + plan + createdDate + lastUpdatedDate + entityId);
						y = webServicesSession.createPrice(pricews);
                    }						  
				}				
			}
				
			log.debug(" errorCount2 " + errorCount);		
			//def pricewsd = new PriceModelWS();
			if(errorCount!=0) {
				flash.message = 'PRICE MODEL deatils are Sucessfully Loaded'
			}else{
				flash.info = 'Failed to load price model from file'
			}										   
		}catch (SessionInternalError e) {
				viewUtils.resolveException(flash, session.locale, e)
				chain action: 'list', model: [ selected: price ]
				return
	
		}finally {
			temp?.delete()
		}				
	
		chain action: 'list', params: [ id: price?.id ]		
	}	
	
	
	def splitfields(line) {
	
		log.debug("splitfields is called");
		return line.split(",", -1);
	}
	
    def deletePriceModel = {
		log.debug "delete action is triggered"
        if (params.id) {
            webServicesSession.deletePriceModel(params.int('id'))

            flash.message = 'priceModel.deleted'
            flash.args = [ params.id ]
            log.debug("Deleted priceModel ${params.id}.")

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
		def filters = filterService.getFilters(FilterType.PRICEMODEL, params)
	
		params.max = CsvExporter.MAX_RESULTS
		def prices = getFilteredPriceModel(filters, params)
	
		if (prices.totalCount > CsvExporter.MAX_RESULTS) {
			flash.error = message(code: 'error.export.exceeds.maximum')
			flash.args = [ CsvExporter.MAX_RESULTS ]
			redirect action: 'list', id: params.id
	
		} else {
			DownloadHelper.setResponseHeader(response, "prices.csv")
			Exporter<PriceModelDTO> exporter = CsvExporter.createExporter(PriceModelDTO.class);
			render text: exporter.export(prices), contentType: "text/csv"
		}
	}
		
  Integer entityId = webServicesSession.getCallerCompanyId();
       	  def description = "Plans";
	      def planList = webServicesSession.getItemByCategory(new ItemTypeDAS().findByDescription(entityId, description).getId());

}
