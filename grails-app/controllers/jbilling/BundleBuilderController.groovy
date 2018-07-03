

package jbilling

//import grails.plugins.springsecurity.Secured
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured
import com.sapienter.jbilling.server.item.db.ItemDTO
import com.sapienter.jbilling.server.user.db.CompanyDTO
import com.sapienter.jbilling.server.order.OrderWS
import com.sapienter.jbilling.server.user.db.UserDTO

import com.sapienter.jbilling.server.order.db.OrderPeriodDTO
import com.sapienter.jbilling.server.order.db.OrderBillingTypeDTO
import com.sapienter.jbilling.server.util.Constants
import com.sapienter.jbilling.server.user.contact.db.ContactDTO
import com.sapienter.jbilling.server.order.OrderLineWS
import com.sapienter.jbilling.common.SessionInternalError
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

import com.sapienter.jbilling.server.order.db.OrderStatusDTO
import java.math.RoundingMode
import com.sapienter.jbilling.server.process.db.PeriodUnitDTO
//import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.apache.commons.lang.StringUtils
import com.sapienter.jbilling.server.item.CurrencyBL

import in.saralam.sbs.server.pricing.db.PricePackageDTO
import in.saralam.sbs.server.pricing.db.PricePackageDAS
import in.saralam.sbs.server.pricing.db.PackageProductDTO
import in.saralam.sbs.server.pricing.db.PackageProductDAS
import in.saralam.sbs.server.pricing.db.PackagePriceDTO
import in.saralam.sbs.server.pricing.db.PackagePriceDAS
import in.saralam.sbs.server.pricing.db.PackagePriceTypeDTO
import in.saralam.sbs.server.pricing.PricePackageWS
import in.saralam.sbs.server.pricing.PackageProductWS
import in.saralam.sbs.server.pricing.db.PackageProductDTO;
import in.saralam.sbs.server.pricing.db.PackagePriceDTO;
import in.saralam.sbs.server.pricing.db.PackagePriceTypeDTO;
import in.saralam.sbs.server.pricing.db.PricePackageDTO; 
import in.saralam.sbs.server.pricing.db.PackagePriceTypeDAS;
import com.sapienter.jbilling.server.util.Constants;

import com.sapienter.jbilling.server.metafields.EntityType
import com.sapienter.jbilling.server.metafields.MetaFieldBL
import com.sapienter.jbilling.server.metafields.db.MetaField
import com.sapienter.jbilling.server.metafields.MetaFieldValueWS
import com.sapienter.jbilling.server.metafields.DataType
import com.sapienter.jbilling.client.metafield.MetaFieldUtils
import com.sapienter.jbilling.server.rule.task.test.Bundle


//@Secured(["MENU_92"])
class BundleBuilderController {

    def webServicesSession
    def viewUtils

    def breadcrumbService
    def productService

    def index = {
        redirect action: 'edit'
    }

    def editFlow = {
        /**
         * Initializes the order builder, putting necessary data into the flow and conversation
         * contexts so that it can be referenced later.
         */
		 
		log.debug(" from adding product edit action comes here")
        initialize {
            action {
			log.debug(" Entering to the flow action")
                
		  
		  def bundle;
		     if(params.int('id')){
                      log.debug(" in  if  block")
		  //PricePackageDTO dto = params.id ? webServicesSession.getBundle(params.int('id')) : new PricePackageWS()
		   bundle= webServicesSession.getBundleWS(params.int('id'))
		  }
		    else {
                         log.debug(" in else  block")
                        log.debug(" getting OrderWS with param id ${params.int('id')}");
		      bundle = params.id ? webServicesSession.getBundle(params.int('id')) : new PricePackageWS()
                     
		   
                         // set sensible defaults for new orders
                     }
                        
	             if (!bundle) {
                       log.error("Could not fetch WS object")
                       session.error = 'bundle.not.found'
                       session.args = [ params.id ]
                       redirect controller: 'bundle', action: 'list'
                       return
                   }
				   def order = params.id ? webServicesSession.getOrder(params.int('id')) : new OrderWS()
                   def user = UserDTO.get(order?.userId ?: params.int('userId'))
					//def selected = webServicesSession.getBundle(params.int('id'))
					//def user = webServicesSession.getUserWS(selected.getBaseUser().getId())
					//def user =  bundle.getOwningEntityId()
					def accountType = user?.customer?.accountType
					log.debug(" accountType===== "+accountType)
                    def company = CompanyDTO.get(session['company_id'])
					log.debug("*****Company Id*****: "+company?.id)
					def itemTypes = productService.getItemTypes(company.id, null)
					log.debug(" itemTypes===== "+itemTypes)
                    def currencies = new CurrencyBL().getCurrencies(session['language_id'], session['company_id'])
                    currencies = currencies.findAll{ it.inUse }

                    // set sensible defaults for new orders
                    if (!bundle.id || bundle.id == 0) {
                    bundle.activeSince   = new Date()
                    bundle.packageProducts    = []
                 }

                   // add breadcrumb for order editing
                    if (params.id) {
                    breadcrumbService.addBreadcrumb(controllerName, actionName, null, params.int('id'))
                    }

                   // available order periods, statuses and order types
				   log.debug("Company Id*****: "+company?.id)
                   
                   def orderStatuses = OrderStatusDTO.list().findAll { it.id != Constants.ORDER_STATUS_SUSPENDED_AGEING }
                   def orderPeriods = company.orderPeriods.collect { new OrderPeriodDTO(it.id) } << new OrderPeriodDTO(Constants.ORDER_PERIOD_ONCE)
                   orderPeriods.sort { it.id }
                   def periodUnits = PeriodUnitDTO.list()
		   def orderBillingTypes = [
                        new OrderBillingTypeDTO(Constants.ORDER_BILLING_PRE_PAID),
                        new OrderBillingTypeDTO(Constants.ORDER_BILLING_POST_PAID)
                ]

                  // model scope for this flow
                   flow.company = company
                   flow.itemTypes = itemTypes
                   flow.orderStatuses = orderStatuses
                   flow.orderPeriods = orderPeriods
                   flow.periodUnits = periodUnits
		   flow.orderBillingTypes = orderBillingTypes

                  // conversation scope
                   conversation.bundle = bundle
				   
	           log.debug("added   ${conversation.bundle} & ${bundle}")
                   conversation.products = productService.getFilteredProducts(company, params, accountType, false, true)
				   conversation.availableFields = getAvailableMetaFields()	
				   conversation.deletedPackageProducts = []
            }
            on("success").to("build")
	    log.debug(" below the  build");
        }

        /**
         * Renders the order details tab panel.
         */
        showDetails {
            action {
                params.template = 'details'
            }
            on("success").to("build")
        }

        /**
         * Renders the product list tab panel, filtering the product list by the given criteria.
         */
        showProducts {
            action {
                
                params.template = 'products'
                
            }
            on("success").to("build")
        }
		
		/**
         * Renders the plans list tab panel, filtering the plans list by the given criteria.
         */
        showPlans {
            action {
                params.max = params?.max?.toInteger() ?: pagination.max
                params.offset = params?.offset?.toInteger() ?: pagination.offset

                if (null == params['filterBy'])
                    params['filterBy'] = ""

                    params.template = 'plans'
                conversation.plans = productService.getFilteredPlans(flow.company, params, true)
                conversation.maxPlansShown = params.max
            }
            on("success").to("build")
        }
		
		/**
         * Show review tab
         */
        showReview {
            action {
               params.errorMessages = conversation.errorMessages
                conversation.errorMessages =  null
                params.message = conversation.message
                conversation.message=null
                params.template = 'review'
            }
            on("success").to("build")
        }

        /**
         * Adds a product to the order as a new order line and renders the review panel.
         */
        addPackageProduct {
            action {
				
                // build line
                def pproduct = new PackageProductWS()
                pproduct.quantity = BigDecimal.ONE
                pproduct.productId = params.int('id')
                
				

                // add line to order
                def bundle = conversation.bundle
                def pproducts = bundle.packageProducts as List
                pproducts.add(pproduct)
                bundle.packageProducts = pproducts.toArray()

                conversation.bundle = bundle
                params.newPproductIndex = pproducts.size() - 1
		params.template = 'review'
            }
            on("success").to("build")
	     
        }

        /**
         * Updates an order line  and renders the review panel.
         */
        updatePackageProduct {
            action {
			log.debug("=====UPDATE PACKAGE PRODUCT =====")
                def bundle = conversation.bundle

                // get existing line
                def index = params.int('index')
		def pproducts = bundle.packageProducts[index]
               
             
                
			if(params?.oneoff=='1'){
                              log.debug(" price cb if condition")
			       String price= params["pproduct-${index}.price"]
                               String discount= params["pproduct-${index}.discount"]
                               def start=params["start"]
                               def startOffset=params["startOffset"]
                               def end=params["end"]
                               def endOffset=params["endOffset"]
                               pproducts.setOneTimePrice(new BigDecimal(price))
		               pproducts.setOneTimeDiscount(new BigDecimal(discount))
		               pproducts.setOneTimeStartOffset(Integer.parseInt(start))
		               pproducts.setOneTimeStartOffsetUnit(Integer.parseInt(startOffset))
		               pproducts.setOneTimeEndOffset(Integer.parseInt(end))
		               pproducts.setOneTimeEndOffsetUnit(Integer.parseInt(endOffset))
			       pproducts.setOneTimeCbValue(Constants.ONE_TIME)
		               log.debug(" added  one time  to ws ")
				 }
                              
			    
                             
			      if(params?.recurring=='2'){
			       log.debug(" recurring cb if condition")
                               def recurringPrice= params["pproduct-${index}.recurringPrice"]
			       def recurringDiscount=params["pproduct-${index}.recurringDiscount"]
			       def recurringStartOffset=params["recurringStartOffset"]
                               def recurringStartOffsetUnit=params["recurringStartOffsetUnit"]
                               def recurringEndOffset=params["recurringEndOffset"]
                               def recurringEndOffsetUnit=params["recurringEndOffsetUnit"]
			       def frequency =params["frequency"]
			       log.debug(" frequency"+frequency)
			       pproducts.setFrequency(Integer.parseInt(frequency))
			        def billingTypeId =params["billingTypeId"]
			       log.debug(" billingTypeId"+billingTypeId)
			       pproducts.setBillingType(Integer.parseInt(billingTypeId))
                               pproducts.setRecurringPrice(new BigDecimal(recurringPrice))
			       pproducts.setRecurringDiscount(new BigDecimal(recurringDiscount))
			       pproducts.setRecurringStartOffset(Integer.parseInt(recurringStartOffset))
	                       pproducts.setRecurringStartOffsetUnit( Integer.parseInt( recurringStartOffsetUnit))
	                       pproducts.setRecurringEndOffset(Integer.parseInt(recurringEndOffset))
	                       pproducts.setRecurringEndOffsetUnit( Integer.parseInt(recurringEndOffsetUnit))
		               pproducts.setRecurringCbValue(Constants.RECURRING)
		              log.debug(" added reucriing  tows")
		            }
		        
			    
		
	       
                          
			      if(params?.cancel=='4'){
			        log.debug(" cancelCb cb if condition")
			        def cancelPrice=params["pproduct-${index}.cancelPrice"]
                                def cancelDiscount=params["pproduct-${index}.cancelDiscount"]
                                def cancelStartOffset=params["cancelStartOffset"]
                                def cancelStartOffsetUnit=params["cancelStartOffsetUnit"]
                                def cancelEndOffset=params["cancelEndOffset"]
                                def cancelEndOffsetUnit=params["cancelEndOffsetUnit"]
				  log.debug(" cancel price"+cancelPrice)
                                pproducts.setCancelPrice(new BigDecimal(cancelPrice))
		                pproducts.setCancelDiscount(new BigDecimal(cancelDiscount))
	                        pproducts.setCancelStartOffset(Integer.parseInt(cancelStartOffset))
	                        pproducts.setCancelStartOffsetUnit(Integer.parseInt(cancelStartOffsetUnit))
	                        pproducts.setCancelEndOffset(Integer.parseInt(cancelEndOffset))
		                pproducts.setCancelEndOffsetUnit(Integer.parseInt(cancelEndOffsetUnit))
		                pproducts.setCancelCbValue(Constants.CANCEL)
				 log.debug(" cancel price"+Constants.CANCEL)
				  log.debug(" cancel price using get"+pproducts.getCancelCbValue())
		                log.debug(" added  usage to ws")
                       }
			
                // update line
		log.debug("Binding form data to Package product...")
               // bindData(pproducts, params["pproduct-${index}"])
                log.debug("  called  bind data");

                // must have a quantity
                if (!pproducts.quantity) {
                    pproducts.quantity = BigDecimal.ONE
                }

                // if product does not support decimals, drop scale of the given quantity
                def product = conversation.products?.find{ it.id == pproducts.productId }
                if (product?.hasDecimals == 0) {
                    pproducts.quantity = pproducts.getQuantity().setScale(0, RoundingMode.HALF_UP)
                }

                // existing line that's stored in the database will be deleted when quantity == 0

                if (pproducts.quantity == BigDecimal.ZERO) {
                    log.debug("zero quantity, marking product to be deleted.")
                    pproducts.deleted = 1

                    if (pproducts.id != 0) {
                        // keep track of persisted lines so that we can make sure they're removed on save
                        conversation.deletedPackageProducts << pproduct
                    }
                }

                // add line to order
                bundle.packageProducts[index] = pproducts


                // sort order lines
                bundle.packageProducts = bundle.packageProducts.sort { it.productId }
                conversation.bundle = bundle
		log.debug("  added to conservation")
                params.template = 'review'
		
            }
            on("success").to("build")
              
        }

        /**
         * Removes a line from the order and renders the review panel.
         */
        removePackageProduct {
            action {
                def bundle = conversation.bundle

                def index = params.int('index')
                def pproducts = bundle.packageProducts as List

                // remove or mark as deleted if already saved to the DB
                def pproduct = pproducts.get(index)
                if (pproduct.id != 0) {
                    log.debug("marking product ${pproduct.id} to be deleted.")
                    pproduct.deleted = 1
                    conversation.deletedPackageProducts << pproduct

                } else {
                    log.debug("removing transient product from bundle.")
                pproducts.remove(index)
                }

                bundle.packageProducts = pproducts.toArray()

                conversation.bundle = bundle

                params.template = 'review'
            }
            on("success").to("build")
        }

        /**
         * Updates order attributes (period, billing type, active dates etc.) and
         * renders the order review panel.
         */
        updateBundle {
		log.debug("=====ENTERING TO UPDATE BUNDLE====")
            action {
                def bundle = conversation.bundle
                bindData(bundle, params)
		log.debug("update bundle : ${bundle}");
		bindMetaFields(bundle, params);
                log.debug("bundle meta fields"+bundle.metaFields);
				
                // one time orders are ALWAYS post-paid
                // rate order
                // sort order lines
                bundle.packageProducts = bundle.packageProducts.sort { it.productId }
                conversation.bundle = bundle

                params.template = 'review'
            }
            on("success").to("build")
        }

        /**
         * Shows the order builder. This is the "waiting" state that branches out to the rest
         * of the flow. All AJAX actions and other states that build on the order should
         * return here when complete.
         *
         * If the parameter 'template' is set, then a partial view template will be rendered instead
         * of the complete 'build.gsp' page view (workaround for the lack of AJAX support in web-flow).
         */
        build {
            on("details").to("showDetails")
            on("products").to("showProducts")
            on("addPackageProduct").to("addPackageProduct")
            on("updatePackageProduct").to("updatePackageProduct")
            on("removePackageProduct").to("removePackageProduct")
            on("update").to("updateBundle")

            on("save").to("saveBundle")
            // on("save").to("checkItem")  // check to see if an item exists, and show an information page before saving
            //on("save").to("beforeSave") // show an information page before saving

            on("cancel").to("finish")
        }

       
        /**
         * Example action that shows a static page before the order is saved.
         *
         * Uncomment the "save" to "beforeSave" transition in the builder() state to use.
         */
        beforeSave {
            on("save").to("saveBundle")
            on("cancel").to("build")
        }

        /**
         * Saves the order and exits the builder flow.
         */
         saveBundle {
            action {
                try {
                    def bundle = conversation.bundle
                
                    if (!bundle.id || bundle.id == 0) {
                        // if(bundle.bundleName != null && bundle.bundleName != '')
						if(bundle.bundleName?.trim()){
                            log.debug("creating Bundle ${bundle}")
			      log.debug("bundle description ${bundle.bundleName}") 
			         log.debug("bundle  metafields ${bundle.metaFields}") 
                            bundle.id = webServicesSession.createBundle(bundle)
                            
                            // set success message in session, contents of the flash scope doesn't survive
                            // the redirect to the order list when the web-flow finishes
                            session.message = 'bundle.created'
                            session.args = [ bundle.id]
                    }else if(bundle.bundleName == null || bundle.bundleName == ''){
				     
				      
						 //flash.error = message(code: 'bundle.error.name.blank')
						 session.message = 'bundle.error.name.blank'
						 //render view: "review", model: [review: review]
                        return
						 }
				    
                    }else {
                        
                            // add deleted lines to our order so that updateOrder() can save them
                            def deletedPackageProducts = conversation.deletedPackageProducts
                            def pproducts = bundle.packageProducts as List

                            log.debug "appending ${deletedPackageProducts.size()} product(s) for deletion."
                            pproducts.addAll(deletedPackageProducts)
                            bundle.packageProducts = pproducts.toArray()

                            // save changes
                            webServicesSession.updateBundle(bundle)

                            session.message = 'bundle.updated'
                            session.args = [ bundle.id ]

                            redirect controller: 'login', action: 'denied'
                        }
                    

                } catch (SessionInternalError e) {
                    viewUtils.resolveException(flow, session.locale, e)
                    error()
                }
            }
            on("error").to("build")
            on("success").to("finish")
        }
        finish {
            redirect controller: 'bundle', action: 'list', id: conversation.bundle?.id
        }
    }

	def getAvailableMetaFields() 
		{
			return MetaFieldBL.getAvailableFieldsList(session['company_id'], EntityType.BUNDLES);
		}
	
		def private bindMetaFields(PricePackageWS pricePackageWS, GrailsParameterMap params) {
			def fieldsArray = MetaFieldUtils.bindMetaFields(availableMetaFields, params);
			pricePackageWS.metaFields = fieldsArray.toArray(new MetaFieldValueWS[fieldsArray.size()])
    }
	
	
}
