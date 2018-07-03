

package jbilling

import com.sapienter.jbilling.client.util.Constants
import com.sapienter.jbilling.client.util.DownloadHelper
import com.sapienter.jbilling.common.SessionInternalError
import com.sapienter.jbilling.server.customer.CustomerBL
import com.sapienter.jbilling.server.invoice.InvoiceBL
import com.sapienter.jbilling.server.invoice.db.InvoiceDAS
import com.sapienter.jbilling.server.item.CurrencyBL
import com.sapienter.jbilling.server.order.OrderBL
import com.sapienter.jbilling.server.order.OrderWS
import com.sapienter.jbilling.server.order.db.OrderDAS
import com.sapienter.jbilling.server.order.db.OrderDTO
import com.sapienter.jbilling.server.order.db.OrderPeriodDAS
import com.sapienter.jbilling.server.order.db.OrderStatusDAS
import com.sapienter.jbilling.server.user.UserWS
import com.sapienter.jbilling.server.user.db.CustomerDTO
import com.sapienter.jbilling.server.user.db.UserDAS
import com.sapienter.jbilling.server.user.db.UserDTO
import in.saralam.sbs.server.pricing.db.PricePackageDTO
import in.saralam.sbs.server.pricing.db.PricePackageDAS
import in.saralam.sbs.server.pricing.db.PackageProductDTO
import in.saralam.sbs.server.pricing.db.PackageProductDAS
import in.saralam.sbs.server.pricing.db.PackagePriceDTO
import in.saralam.sbs.server.pricing.db.PackagePriceDAS
import in.saralam.sbs.server.pricing.db.PackagePriceTypeDTO
//import com.sapienter.jbilling.server.pricing.db.PackagePriceTypeDAS
import com.sapienter.jbilling.server.util.csv.CsvExporter
import com.sapienter.jbilling.server.util.csv.Exporter
//import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import com.sapienter.jbilling.server.user.db.CompanyDTO
import org.hibernate.FetchMode
import org.hibernate.criterion.Restrictions
import org.hibernate.criterion.Criterion
import org.hibernate.Criteria
import com.sapienter.jbilling.client.util.SortableCriteria
//import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured
import in.saralam.sbs.server.pricing.db.PricePackageDTO;
import in.saralam.sbs.server.pricing.PricePackageWS
import in.saralam.sbs.server.pricing.PurchasedBundleWS;

import com.sapienter.jbilling.server.metafields.MetaFieldBL
import com.sapienter.jbilling.server.metafields.EntityType
import com.sapienter.jbilling.server.metafields.MetaFieldValueWS

import com.sapienter.jbilling.client.metafield.MetaFieldUtils
import org.apache.commons.lang.StringUtils

import in.saralam.sbs.server.pricing.db.PurchasedBundleDAS
import in.saralam.sbs.server.pricing.db.PurchasedBundleDTO

//@Secured(["MENU_101"])
class BundleController {

    static pagination = [ max: 10, offset: 0, sort: 'id', order: 'desc' ]

    def webServicesSession
    def viewUtils
    def filterService
    def recentItemService
    def breadcrumbService

    def index = {
        redirect action: 'list', params: params
    }

    def private getFilteredBundles(filters, GrailsParameterMap params) {	
		
        params.max = params?.max?.toInteger() ?: pagination.max
        params.offset = params?.offset?.toInteger() ?: pagination.offset
        params.sort = params?.sort ?: pagination.sort
        params.order = params?.order ?: pagination.order

        return PricePackageDTO.createCriteria().list(
                max:    params.max,
                offset: params.offset
        ) {
            //createAlias('entity', 'u', Criteria.LEFT_JOIN)
            and {
                filters.each { filter ->
				log.debug("fileter  field ${filter.field}")
					if (filter.value != null) { 
							addToCriteria(filter.getRestrictions());
                    }
                }
            }
              
            eq('deleted', 0)              
			eq('entity', new CompanyDTO(session['company_id']))
			
            // apply sorting
            SortableCriteria.sort(params, delegate)
        }
    }

    def list = {
		
        def filters = filterService.getFilters(FilterType.BUNDLE, params)        
        def bundles = getFilteredBundles(filters, params)   

        def selected = params.id ? webServicesSession.getBundle(params.int("id")) : null        
        log.debug(" selected bundle is for the id ${params.int("id")} "+selected)
		
        breadcrumbService.addBreadcrumb(controllerName, 'list', null, selected?.id)

        if (params.applyFilter || params.partial) {
            render template: 'bundles', model: [ bundles: bundles, bundle: selected, filters: filters ]
        } else {
           render view: 'list', model: [ bundles: bundles, bundle: selected, filters: filters ]
        }
    }

    //@Secured(["ORDER_24"])
    def show () {

        PricePackageDTO dto = webServicesSession.getBundle(params.int('id'))

        //UserWS user = webServicesSession.getUserWS(order.getUserId())

        /*breadcrumbService.addBreadcrumb(controllerName, 'list', null, order.id)
        recentItemService.addRecentItem(order.id, RecentItemType.ORDER)*/

        Integer langauageId=session['language_id'].toInteger()

        render template:'show', model: [dto: dto,langauageId:langauageId]
    }

    /**
     * Applies the set filters to the order list, and exports it as a CSV for download.
     */
    //@Secured(["ORDER_25"])
    def csv () {
        def filters = filterService.getFilters(FilterType.ORDER, params)

        params.max = CsvExporter.MAX_RESULTS
        def bundles = getFilteredBundles(filters, params)

        if (bundles.totalCount > CsvExporter.MAX_RESULTS) {
            flash.error = message(code: 'error.export.exceeds.maximum')
            flash.args = [ CsvExporter.MAX_RESULTS ]
            redirect action: 'list', id: params.id

        } else {
            DownloadHelper.setResponseHeader(response, "bundles.csv")
            Exporter<OrderDTO> exporter = CsvExporter.createExporter(PricePackageDTO.class);
            render text: exporter.export(bundles), contentType: "text/csv"
        }
    }

    def getCurrencies() {
        def currencies = new CurrencyBL().getCurrencies(session['language_id'].toInteger(), session['company_id'].toInteger())
        return currencies.findAll{ it.inUse }
    }


   //@Secured(["ORDER_22"])
    def deleteBundle () {
        try {
            webServicesSession.deleteBundle(params.int('id'))
            flash.message = 'bundle.delete.success'
            flash.args = [params.id, params.id]
        } catch (SessionInternalError e){
            flash.error ='bundle.error.delete'
            viewUtils.resolveException(flash, session.locale, e);
        } catch (Exception e) {
            log.error e
            flash.error= e.getMessage()
        }
        redirect action: 'list'
    }

      def  purchaseBundle={

          // PricePackageDTO dto = webServicesSession.getBundle(params.int('bundleId'))
	   PricePackageWS  bundle=webServicesSession.getBundleWS(params.int('bundleId'))
          [bundle :bundle]

     }
	 
	def retrieveAvailableMetaFields() {
        return MetaFieldBL.getAvailableFieldsList(session['company_id'], EntityType.BUNDLES);
    }

    def private bindMetaFields(PricePackageDTO dto, GrailsParameterMap params) {
       def fieldsArray = MetaFieldUtils.bindMetaFields(retrieveAvailableMetaFields(), params)
       dto.metaFields = fieldsArray.toArray(new MetaFieldValueWS[fieldsArray.size()])
    } 
	 
    def  proceedToPurchase={
	
        log.debug(" bundle id"+params.int('bundleId'));
        PricePackageWS  bundle=webServicesSession.getBundleWS(params.int('bundleId'))
		
	PurchasedBundleWS purchasedBundleWS= new PurchasedBundleWS()
        purchasedBundleWS.setBundleId(params.int('bundleId'))
        purchasedBundleWS.setValidFrom(bundle.getActiveSince())
        purchasedBundleWS.setValidTo(bundle.getActiveUntil())
        purchasedBundleWS.setCreatedDateTime(new Date())
        purchasedBundleWS.setPackageProducts(bundle.getPackageProducts())     
        purchasedBundleWS.setBundleName(bundle.getBundleName())        
        purchasedBundleWS.setUserDto(new UserDAS().find(params.int('customerId')))
		
        webServicesSession.purchaseBundle(purchasedBundleWS)
		
        flash.message = 'bundle.purchase.success'
        flash.args = [params.bundleId, params.bundleId ]                  
        redirect action: 'list'            
    }
	
	def user = {
		def purchasedBundleId =  params.int('id')
		def purchasedBundleDTO = new PurchasedBundleDAS().find(purchasedBundleId)
		def id = purchasedBundleDTO.bundleId
		def userId = purchasedBundleDTO.userDto.id		
        	def filter = new Filter(type: FilterType.BUNDLE, constraintType: FilterConstraint.EQ, field: 'id' ,template: 'id', visible: true, integerValue: id)
	        filterService.setFilter(FilterType.BUNDLE, filter)		
	        redirect (action: 'list', params:[id:id])
    }
}
