

package jbilling

import com.sapienter.jbilling.client.util.Constants
import com.sapienter.jbilling.common.CommonConstants
import in.saralam.sbs.server.pricing.db.BundleStatusDTO
import in.saralam.sbs.server.pricing.db.BundleStatusDAS
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
//import in.saralam.sbs.server.pricing.db.PricePackageDTO
import in.saralam.sbs.server.pricing.PricePackageWS
import in.saralam.sbs.server.pricing.PurchasedBundleWS
import in.saralam.sbs.server.pricing.db.PurchasedBundleDTO
import in.saralam.sbs.server.pricing.db.PurchasedBundleDAS

//@Secured(["MENU_92"])
class PurchaseBundleController {

    static pagination = [ max: 10, offset: 0, sort: 'id', order: 'desc' ]

    def webServicesSession
    def viewUtils
    def filterService
    def recentItemService
    def breadcrumbService

    def index = {
        
        redirect (action: 'list', params: params)
        
    }

    def private getFilteredPurchasedBundles(filters, GrailsParameterMap params) {
	
		log.debug(" This is the first to be executed when clicked on Bundle menu");
        params.max = params?.max?.toInteger() ?: pagination.max
        params.offset = params?.offset?.toInteger() ?: pagination.offset
        params.sort = params?.sort ?: pagination.sort
        params.order = params?.order ?: pagination.order

        return PurchasedBundleDTO.createCriteria().list(
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
				createAlias('userDto', 'u')
                eq('u.company', new CompanyDTO(session['company_id']))
                // eq('deleted', 0)
              
		//eq('entity', new CompanyDTO(session['company_id']))
            // apply sorting
            SortableCriteria.sort(params, delegate)
        }
    }

    def list = {
      log.debug("first comes in action list when click on purchase  Bundle");
        def filters = filterService.getFilters(FilterType.PURCHASEDBUNDLE, params)
        log.debug(" filters"+filters)
        def purchasedBundles = getFilteredPurchasedBundles(filters, params)
         log.debug(" bundles"+purchasedBundles)
        def selected = params.id ? webServicesSession.getPurchasedBundle(params.int("id")) : null
        //def user = selected ? webServicesSession.getUserWS(selected.userId) : null
        log.debug(" selected"+selected)
        breadcrumbService.addBreadcrumb(controllerName, 'list', null, selected?.id)

        if (params.applyFilter || params.partial) {
            render template: 'bundles', model: [ bundles: bundles, bundle: selected, filters: filters ]
        } else {
            [ purchasedBundles: purchasedBundles, bundle: selected, filters: filters ]
        }
    }

    //@Secured(["ORDER_24"])
    def show () {
log.debug(" calling wssb");
        PurchasedBundleWS purchased = webServicesSession.getPurchasedBundle(params.int('id'))
        log.debug("called wssb"+purchased);
         OrderWS [] orders = webServicesSession.getPurchasedOrder(params.int('id'))
        //UserWS user = webServicesSession.getUserWS(order.getUserId())

       /* breadcrumbService.addBreadcrumb(controllerName, 'list', null, order.id)
        recentItemService.addRecentItem(order.id, RecentItemType.ORDER)*/
        Integer langauageId=session['language_id'].toInteger()
          log.debug("langauageId"+langauageId)

        render template:'show', model: [purchased:purchased,langauageId:langauageId,orders:orders,currencies: currencies]
    }

    /**
     * Applies the set filters to the order list, and exports it as a CSV for download.
     */
    //@Secured(["ORDER_25"])
    def csv () {
        def filters = filterService.getFilters(FilterType.ORDER, params)

        params.max = CsvExporter.MAX_RESULTS
        def bundles = getFilteredPurchasedBundles(filters, params)

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

   def user = {
        def filter = new Filter(type: FilterType.PURCHASEDBUNDLE, constraintType: FilterConstraint.EQ, field: 'userDto.id', template: 'id', visible: true, integerValue: params.int('id'))
        filterService.setFilter(FilterType.PURCHASEDBUNDLE,filter)
        redirect action: 'list'
    }
   
    
     
     //@Secured(["ORDER_22"])
    def deletePurchasedBundle () {
        try {
            webServicesSession.deletePurchasedBundle(params.int('id'))
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

	def cancelBundle = {
		log.debug("Purchased Bundle Requested for Cancel "+params.int('purchasedId'))
		
		BundleStatusDTO status = new BundleStatusDAS().find(CommonConstants.BUNDLE_STATUS_CANCEL);		
		PurchasedBundleDTO purchaseBundleDto = new PurchasedBundleDAS().find(params.int('purchasedId'));
		BundleStatusDTO bundleStstusDto = purchaseBundleDto.getStatusId();		
		Integer bundleStatusDtoId =  bundleStstusDto.getId();
		Integer StatusId =  status.getId();
		if(bundleStatusDtoId == StatusId) {
			log.debug("Sorry , Bundle already Cancelled : " + params.int('purchasedId'))
			session.error = 'Sorry , Bundle already Cancelled'
			session.args = [ params.int('purchasedId')]
			redirect action: 'list'
		} else {	
			log.debug("The Bundle is going to cancel : " + params.int('purchasedId'))
			[purchasedId:params.int('purchasedId')]
		}
	}

	def saveCancelBundle = {
		String dateTemp=params['cancelDate']		
		def cancelDate = new Date().parse("d/M/yyyy", dateTemp)
		log.debug("cancelDate  :"+cancelDate)
		webServicesSession.cancelBundle( params.int('purchaseId'),cancelDate)		
		session.message = 'Bundle Cancelled'
		session.args = [ params.int('purchasedId')]
		[cancelDate: cancelDate]
		redirect action: 'list'
	}
   
}
