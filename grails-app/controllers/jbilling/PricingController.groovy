
package jbilling

import com.sapienter.jbilling.server.item.db.ItemDTO;
import in.saralam.sbs.server.advancepricing.db.ProductChargeDAS;


import com.sapienter.jbilling.server.item.db.ItemDTO;
import in.saralam.sbs.server.openRate.destinationMap.db.DestinationMapDTO;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import in.saralam.sbs.server.advancepricing.db.ProductChargeRateDTO;
import in.saralam.sbs.server.advancepricing.db.ProductChargeRateDAS;
import com.sapienter.jbilling.server.device.db.DeviceStatusDTO;
import com.sapienter.jbilling.server.device.db.DeviceDTO
import com.sapienter.jbilling.server.device.DeviceWS
import in.saralam.sbs.server.openRate.destinationMap.db.DestinationMapDAS;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.process.db.PeriodUnitDAS;
import com.sapienter.jbilling.server.user.db.CompanyDTO
import com.sapienter.jbilling.server.device.DeviceBL
import com.sapienter.jbilling.common.SessionInternalError
import com.sapienter.jbilling.client.ViewUtils
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

import org.hibernate.FetchMode
import org.hibernate.criterion.Restrictions
import org.hibernate.criterion.Criterion
import org.hibernate.Criteria
import com.sapienter.jbilling.client.util.SortableCriteria



import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.IWebServicesSessionBean;

import org.springframework.jdbc.datasource.DataSourceUtils

//import grails.plugins.springsecurity.Secured
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured

import in.saralam.sbs.server.advancepricing.ProductChargeRateWS
import in.saralam.sbs.server.advancepricing.ProductChargeWS
import in.saralam.sbs.server.advancepricing.RateDependeeWS;
import in.saralam.sbs.server.advancepricing.db.ChargeTypeDAS;
import in.saralam.sbs.server.advancepricing.db.ChargeTypeDTO;
import in.saralam.sbs.server.advancepricing.db.ProductChargeDAS;
import in.saralam.sbs.server.advancepricing.db.ProductChargeDTO;
import in.saralam.sbs.server.advancepricing.db.ProductChargeRateDTO;
import in.saralam.sbs.server.advancepricing.db.RateDependencyTypeDAS
import in.saralam.sbs.server.advancepricing.db.RumTypeDAS
import in.saralam.sbs.server.rating.db.EventTypeRateMapDAS
import in.saralam.sbs.server.rating.db.RatingEventTypeDAS;

import javax.sql.DataSource
import com.sapienter.jbilling.server.device.db.DeviceDAS
import com.sapienter.jbilling.server.device.db.DeviceStatusDAS
import com.sapienter.jbilling.server.util.csv.CsvExporter
import com.sapienter.jbilling.server.util.csv.Exporter
import com.sapienter.jbilling.server.util.db.CurrencyDAS
import com.sapienter.jbilling.client.util.DownloadHelper

import java.text.*;
import java.util.*; 

import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;
import java.lang.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.Writer;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.sapienter.jbilling.server.item.CurrencyBL
import com.sapienter.jbilling.server.item.db.ItemDAS;
import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.item.db.ItemTypeDAS
import in.saralam.sbs.server.taxCode.db.TaxCodeDTO;
import in.saralam.sbs.server.taxCode.db.TaxCodeDAS;

@Secured(["MENU_102"])
class PricingController {

    static pagination = [ max: 10, offset: 0, sort: 'id', order: 'desc' ]

    WebServicesSessionSpringBean webServicesSession = new  WebServicesSessionSpringBean();
    def filterService

    ViewUtils viewUtils
    DataSource dataSource
	def breadcrumbService


    def index = {
        redirect action: 'list', params: params
    }
	
	
	def private getFilteredPricing(filters, GrailsParameterMap params) {
			params.max = params?.max?.toInteger() ?: pagination.max
			params.offset = params?.offset?.toInteger() ?: pagination.offset
			params.sort = params?.sort ?: pagination.sort
			params.order = params?.order ?: pagination.order
	
			return ProductChargeDTO.createCriteria().list(
					max:    params.max,
					offset: params.offset
			) {
			createAlias('chargeType', 'c', Criteria.LEFT_JOIN)
			
			createAlias('item', 'i', Criteria.LEFT_JOIN)
			eq('i.entity', new CompanyDTO(session['company_id']))
			eq('deleted', 0)
				
			
			SortableCriteria.sort(params, delegate)
		}
    }

    def list = {
		
		def filters = filterService.getFilters(FilterType.PRICING, params)
		log.debug(" filters"+filters)
		def ProductCharges = getFilteredPricing(filters, params)
		
		def products = new ArrayList<ItemDTO>()
		for(ProductChargeDTO productCharge : ProductCharges){
			ItemDTO item = productCharge.getItem()
			if(!(products.contains(item)))
			products.add(item)
		}
		if (params.applyFilter || params.partial) {
		render template: 'pricing', model: [ products : products, filters : filters ,ProductCharges:ProductCharges]
		}else {
			[ products : products, filters : filters ,ProductCharges:ProductCharges]
		}
		
	}
	
	def show = {
		log.debug(" calling wssb");
				log.debug("productId is "+params.int('id'))
				ProductChargeDTO pDTO = webServicesSession.getProductChargeByItemAndType(params.int('id'), Constants.CHARGE_ONE_TIME)
				ProductChargeDTO rDTO = webServicesSession.getProductChargeByItemAndType(params.int('id'), Constants.CHARGE_RECURRING)
				ProductChargeDTO uDTO = webServicesSession.getProductChargeByItemAndType(params.int('id'), Constants.CHARGE_USAGE)
				//log.debug("product charge is "+rDTO.getRates())
				
				render template:'show', model: [pDTO : pDTO, rDTO : rDTO, uDTO : uDTO]
		}
   
	def getEvents(){
		def events = new RatingEventTypeDAS().findByCompany(new CompanyDTO(session['company_id']));
		return events
	}

	def getDepends(){
		def depends = new RateDependencyTypeDAS().findAll();
		return depends
	}
	
	def getMeasured(){
		def measured = new RumTypeDAS().findAll();
		return measured
	}
	
	def getPeriodUnits(){
		def periodUnits = new PeriodUnitDAS().findAll()
		return periodUnits
	}
	
	def getProducts(){
		Integer entityId = webServicesSession.getCallerCompanyId();
		def description = "plans";
		def planList1 = null;
		try{
		planList1 = webServicesSession.getItemByCategory(new ItemTypeDAS().findByDescription(entityId, description).getId());
		}catch(Exception e){
		log.debug(" planlist not found")
		}
		List<ItemDTO> planList = new ArrayList<ItemDTO>()
		for(ItemDTO item : planList1){
			if(item.getDeleted()==0)
			planList.add(item)
		}
		return planList
	}
	
	def getCategory(){
		def category = DestinationMapDTO.list()
		return category
	}
	
	def getCurrencies() {
		def currencies = new CurrencyBL().getCurrencies(session['language_id'].toInteger(), session['company_id'].toInteger())
		return currencies.findAll { it.inUse }
	}
	
	def saveAdvancePricing = {
		def minCheckArray = new ArrayList<String>()
		log.debug(" params is  " + params)
		def c = 0
		def s = null
		String oneTimeTaxCode;
                String recurringTaxCode;
		String usageTaxCode;
		for(int i=0;i<params.minChecked.size();i++){
			if(params.minChecked[i]=='1'){
				s = i
				c++
			}
		}
		log.debug("c value is "+c)
			if(c>=2){
				def f = 0
				def j=0
				for(int i=0;i<params.minChecked.size();i++){
					if(params.minChecked[i]=='1'){
						f++
						if(f==1){
							j=i-1
						minCheckArray[j]=params.minChecked[i]
						
						}else {
						j = i-f
						minCheckArray[j] = params.minChecked[i]
						}
					}
				}
			}else if(c == 1){
			minCheckArray[s-1] = params.minChecked[s]
			}
			params.minChecked = minCheckArray

			def maxCheckArray = new ArrayList<String>()
			 c = 0
			 s = null 
			for(int i=0;i<params.maxChecked.size();i++){
				if(params.maxChecked[i]=='1'){
					s = i
					c++
				}
			}
				if(c>=2){
					def f = 0
					def j=0
					for(int i=0;i<params.maxChecked.size();i++){
						if(params.maxChecked[i]=='1'){
							f++
							if(f==1){
								j=i-1
							maxCheckArray[j]=params.maxChecked[i]
							}else{
							j = i-f
							maxCheckArray[j] = params.maxChecked[i]
							}
						}
					}
				}else if(c == 1){
				maxCheckArray[s-1] = params.maxChecked[s]
				}
			
			params.maxChecked = maxCheckArray

					Integer oId = params.pDTO as Integer
		Integer rId = params.rDTO as Integer
		Integer uId = params.uDTO as Integer
		int j = 0
		//if((params.oneTimeFixedAmount).trim()){
		def chargeType
		def fixedAmount
		def scaledAmount
		def currencyId
		def unitId
		def dCurrencyId
		def dTypeId
		def rumId
		def eventId
		def order
		def categoryId
		def Map<Integer, ProductChargeWS> charges = new HashMap<Integer, ProductChargeWS>();
		List<Integer> rateChecks = new ArrayList<Integer>()
		if(params.oneoff=='1')
		rateChecks.add(0)
		if(params.recurring=='2')
		rateChecks.add(1)
		if(params.usage=='3')
		rateChecks.add(2)
		for(int k : rateChecks){
			log.debug("outer for loop "+k)
			List<String> orderArray = new ArrayList<String>()
			List<String> fixedAmountArray = new ArrayList<String>()
			List<String> scaledAmountArray = new ArrayList<String>()
			List<String> currencyIdArray = new ArrayList<String>()
			List<String> unitIdArray = new ArrayList<String>()
			List<String> dCurrencyIdArray = new ArrayList<String>()
			List<String> dTypeIdArray = new ArrayList<String>()
			List<String> rumIdArray = new ArrayList<String>()
			List<String> minAmountArray = new ArrayList<String>()
			List<String> maxAmountArray = new ArrayList<String>()
			//List<String> minCheckArray = new ArrayList<String>()
			//List<String> maxCheckArray = new ArrayList<String>()
			List<String> eventNameArray = new ArrayList<String>()
			List<String> categoryArray = new ArrayList<String>()
			
		def pricing = new ProductChargeWS()
		log.debug("pricing id is "+pricing.getId())
		def itemId
		if(params.pDTO == 0 && params.rDTO == 0 && params.uDTO == 0){
		 itemId = (params.toItem[1]).equals('')? null :params.toItem[1] as Integer
		log.debug("item iddddd isss "+itemId)
		}else{
		 itemId = (params.toItem).equals('')? null :params.toItem as Integer
		log.debug("item iddddd "+itemId)
		}
		
		def item = (itemId==null)?null:new ItemDAS().find(itemId)
		
		pricing.setItem(item)
		if(k==0){
			chargeType = new ChargeTypeDAS().find(Constants.CHARGE_ONE_TIME)
			if(!(params.oneTimeTaxCode).equals('')){
                            oneTimeTaxCode=params.oneTimeTaxCode
			    log.debug(" oneTimeTaxCode"+oneTimeTaxCode)
			    pricing.setTaxCode(new TaxCodeDAS().findTaxCodeDTOByTaxCode(oneTimeTaxCode))
			   
			  }
			}else if (k==1){
			chargeType = new ChargeTypeDAS().find(Constants.CHARGE_RECURRING)
			  if(!(params.recurringTaxCode).equals('')){
                         recurringTaxCode=params.recurringTaxCode
			  log.debug(" recurringTaxCode"+ recurringTaxCode)
			  pricing.setTaxCode(new TaxCodeDAS().findTaxCodeDTOByTaxCode(recurringTaxCode))
			  }
			}else{
			chargeType = new ChargeTypeDAS().find(Constants.CHARGE_USAGE)
			if(!(params.usageTaxCode).equals('')){
                        usageTaxCode=params.usageTaxCode
			  pricing.setTaxCode(new TaxCodeDAS().findTaxCodeDTOByTaxCode(usageTaxCode))
			  }
			}
			pricing.setChargeType(chargeType)
		pricing.setCreatedDate(new Date())
		List<ProductChargeRateWS> rates = new ArrayList<ProductChargeRateWS>()
		
		
		
		try{
			if(k==0){
			
				if(!(params.oneTimeFixedAmount).matches("[a-z]+")){
					
				Integer d = (params.oneTimeFixedAmount).equals('')?null :params.oneTimeFixedAmount as BigDecimal
				//log.debug("d is "+d)
				}
				orderArray.add((params.oneTimeOrder).equals('')?null :params.oneTimeOrder)
				fixedAmountArray.add((params.oneTimeFixedAmount).equals('')?null :params.oneTimeFixedAmount)
				log.debug("rei is "+fixedAmountArray)
				scaledAmountArray.add((params.oneTimeScaledAmount).equals('')?null :params.oneTimeScaledAmount)
				currencyIdArray.add((params.oneTimeCurrencyId).equals('')?null :params.oneTimeCurrencyId)
				unitIdArray.add((params.oneTimePeriodUnits).equals('')?null :params.oneTimePeriodUnits)
			j = fixedAmountArray.size()
			log.debug("fixed amount length is "+j)
				
			}else if (k==1){
			if(!(params.recurringFixedAmount).matches("[a-z]+")){
			Integer d = (params.recurringFixedAmount).equals('')?null :params.recurringFixedAmount as BigDecimal
				log.debug("d is "+d)
			}
			orderArray.add((params.recurringOrder).equals('')?null :params.recurringOrder)
				fixedAmountArray.add((params.recurringFixedAmount).equals('')?null :params.recurringFixedAmount)
				scaledAmountArray.add((params.recurringScaledAmount).equals('')?null :params.recurringScaledAmount)
				currencyIdArray.add((params.recurringCurrencyId).equals('')?null :params.recurringCurrencyId)
				unitIdArray.add((params.recurringPeriodUnits).equals('')?null :params.recurringPeriodUnits)
			j = fixedAmountArray.size()
			
			}else if(k==2){
			if(!(params.usageFixedAmount).matches("[a-z]+")){
			Integer d = (params.usageFixedAmount).equals('')?null :params.usageFixedAmount as BigDecimal
				log.debug("d is "+d)
			}
			log.debug("in k==2")
			orderArray.add((params.usageOrder).equals('')?null :params.usageOrder)
				fixedAmountArray.add((params.usageFixedAmount).equals('')?null :params.usageFixedAmount)
				scaledAmountArray.add((params.usageScaledAmount).equals('')?null :params.usageScaledAmount)
				currencyIdArray.add((params.usageCurrencyId).equals('')?null :params.usageCurrencyId)
				unitIdArray.add((params.usagePeriodUnits).equals('')?null :params.usagePeriodUnits)
				dCurrencyIdArray.add((params.dependsOnCurrencyId).equals('')?null :params.dependsOnCurrencyId)
				dTypeIdArray.add((params.dependsOn).equals('')?null :params.dependsOn)
				rumIdArray.add((params.measured).equals('')?null :params.measured)
				minAmountArray.add((params.minAmount).equals('')?null :params.minAmount)
				maxAmountArray.add((params.maxAmount).equals('')?null :params.maxAmount)
				//minCheckArray.add((params.minCheck).equals('')?null :params.minCheck)
				//maxCheckArray.add((params.maxCheck).equals('')?null :params.maxCheck)
				eventNameArray.add((params.eventName).equals('')?null :params.eventName)
				categoryArray.add((params.category).equals('')?null :params.category)
			j = fixedAmountArray.size()
			log.debug("dcurr "+ params.dependsOnCurrencyId)
			}
		}catch (Exception e) {
			if(k==0){
				orderArray = params.oneTimeOrder
				fixedAmountArray = params.oneTimeFixedAmount 
				scaledAmountArray = params.oneTimeScaledAmount
				currencyIdArray = params.oneTimeCurrencyId
				unitIdArray = params.oneTimePeriodUnits
				j = fixedAmountArray.size()
			}else if(k==1){
			orderArray = params.recurringOrder
			fixedAmountArray = params.recurringFixedAmount
			scaledAmountArray = params.recurringScaledAmount
			currencyIdArray = params.recurringCurrencyId
			unitIdArray = params.recurringPeriodUnits
			j = fixedAmountArray.size()
		}else if(k==2) {
		orderArray = params.usageOrder
		fixedAmountArray = params.usageFixedAmount
		scaledAmountArray = params.usageScaledAmount
		currencyIdArray = params.usageCurrencyId
		unitIdArray = params.usagePeriodUnits
		dCurrencyIdArray = params.dependsOnCurrencyId
		dTypeIdArray = params.dependsOn
		rumIdArray = params.measured
		minAmountArray = params.minAmount
		maxAmountArray = params.maxAmount
		eventNameArray = params.eventName
		//minCheckArray = params.minCheck
		//maxCheckArray = params.maxCheck
		categoryArray = params.category
		j = fixedAmountArray.size()
		log.debug("in catch k==2")
		}
		}
		log.debug("length is "+j)
		log.debug(" oneTime order is "+orderArray)
		for (int i=0; i<j; i++){
		log.debug("for loop repeating time is " +i)
			
				currencyId = currencyIdArray[i]
				order =  orderArray[i]
				fixedAmount =  fixedAmountArray[i]
				scaledAmount = scaledAmountArray[i]
				unitId = unitIdArray[i]
				if(k==2){
				categoryId = categoryArray[i]
				dCurrencyId = dCurrencyIdArray[i]
				dTypeId = dTypeIdArray[i]
				rumId = rumIdArray[i]
				eventId = eventNameArray[i]
				}
			
				if((!itemId.equals(null)) && (!currencyId.equals(null)) && (!order.equals(null)) && (order.matches("[0-9.]+")) && (!fixedAmount.equals(null)) && (fixedAmount.matches("[0-9.]+")) && (!scaledAmount.equals(null)) && (scaledAmount.matches("[0-9.]+")) && (!unitId.equals(null))
					&& (!currencyId.equals('')) && (!fixedAmount.equals('')) && (!scaledAmount.equals('')) && (!unitId.equals(''))){
					
				
		def rate = new ProductChargeRateWS()
		def currency = null
		if (currencyId != null)
		 currency = new CurrencyDAS().find(currencyId as Integer)
		rate.setCurrency(currency)
		if(!(categoryId.equals(''))){
		def destinationMap = new DestinationMapDAS().find(categoryId as Integer)
		rate.setDestinationMap(destinationMap)
		}else{
		rate.setDestinationMap(null)
		}
		rate.setOrder(order as Integer)
		log.debug("order is "+order)
		rate.setFixedAmount(fixedAmount as BigDecimal)
		rate.setScaledAmount(scaledAmount as BigDecimal)
		def unit = new PeriodUnitDAS().find(unitId as Integer)
		rate.setUnitId(unit)
		def dependee = null
		def rum = null
		def event = null
		
		if(k==2){
		if(!(eventId.equals(null)) && !(eventId.equals('')) && !(rumId.equals(null)) && !(rumId.equals(''))){
		
			
		dependee = new RateDependeeWS()
		if(!(dCurrencyId.equals(''))){
		def dcurrency = new CurrencyDAS().find(dCurrencyId as Integer)
		dependee.setCurrency(dcurrency)
		}else{
			dependee.setCurrency(null)
		}
		if(!(dTypeId.equals(''))){
		def dType = new RateDependencyTypeDAS().find(dTypeId  as Integer)
		dependee.setDependencyType(dType)
		}else{
			dependee.setDependencyType(null)
		}
		log.debug("min is "+params._min)
		if(minCheckArray[i].equals(null) && !minAmountArray[i].equals(null) && !minAmountArray[i].equals('')){
			if(minAmountArray[i].matches(".*[0-9].*")){
			dependee.setMinBalance(minAmountArray[i] as BigDecimal)
			}else{
			flash.error = message(code: 'pricing.error.number.char')
			error(params)
			return
			}
		}else{
		dependee.setMinBalance(null)
		}
		if(maxCheckArray[i].equals(null) && !(maxAmountArray[i].equals(null)) && !(maxAmountArray[i].equals(''))){
			if((maxAmountArray[i].matches("[0-9.]+"))){
			dependee.setMaxBalance(maxAmountArray[i] as BigDecimal)
			}else{
			flash.error = message(code: 'pricing.error.number.char')
			error(params)
			return
			}
		}else{
		dependee.setMaxBalance(null)
		}
		if(dependee.getMinBalance()!=null || dependee.getMaxBalance()!=null){
			if(dependee.getCurrency()==null){
				flash.error = message(code: 'pricing.error.dcurrency.blank')
				error(params)
				return
			}
		}
		rate.setRateDependeeWS(dependee)
			rum = new RumTypeDAS().find(rumId as Integer)
			//log.debug("eventId "+eventId)
			event = new RatingEventTypeDAS().find(eventId as Integer)
			
				}else{
				if((eventId.equals(null)) || (eventId.equals(''))){
					flash.error = message(code: 'pricing.error.event.blank')
				}else if((dTypeId.equals(null)) || (dTypeId.equals(''))){
				flash.error = message(code: 'pricing.error.dependson.blank')
				
				}else if((dCurrencyId.equals(null)) || (dCurrencyId.equals(''))){
				flash.error = message(code: 'pricing.error.field.blank')
				}else if((rumId.equals(null)) || (rumId.equals(''))){
				flash.error = message(code: 'pricing.error.measured.blank')
				}
				error(params)
				return
				}
		}
		rate.setRum(rum)
		
		rate.setRatingEvent(event)
		//rate.setActiveSince(null)
		//rate.setActiveUntil(null)
		rates.add(rate)
		
				}else{
				
				
			 
				 
				
										   if(itemId.equals(null)){
											   flash.error = message(code: 'pricing.error.item.blank')
										   }else  if(currencyId.equals(null) || currencyId.equals('')){
											   flash.error = message(code: 'pricing.error.currency.blank')
		}else if(order.equals(null) || order.equals('')){
		flash.error = message(code: 'pricing.error.order.blank')
		}else if(fixedAmount.equals(null) || fixedAmount.equals('')){
		flash.error = message(code: 'pricing.error.fixed.blank')
		}else if(scaledAmount.equals(null) || scaledAmount.equals('')){
		flash.error = message(code: 'pricing.error.scaled.blank')
		}else if(unitId.equals(null) || unitId.equals('')){
		flash.error = message(code: 'pricing.error.unit.blank')
		/*}else if(){*/
		
		}else if (!(order.matches("[0-9.]+")) || !(fixedAmount.matches("[0-9.]+")) || !(scaledAmount.matches("[0-9.]+")) || !(minAmountArray[i].matches("[0-9.]+")) || !(maxAmountArray[i].matches("[0-9.]+"))){
		flash.error = message(code: 'pricing.error.number.char')
		}
		error(params)
		return
		}
		}
		pricing.setRates(rates)
		charges.put(k, pricing)
		log.debug("val is "+charges.get(k))
				}
		if(oId ==0 && rId == 0 && uId == 0){
			log.debug("idssssssss are "+oId+" "+rId+"  "+uId)
		webServicesSession.createAdvancePricing(charges)
		session.message = 'pricing.created'
		session.args = [ params.toItem ]
		chain action: 'list', params: [id:params.toItem]
		}else{
		log.debug("update product charge")
		webServicesSession.updateAdvancePricing(charges)
		session.message = 'pricing.updated'
		session.args = [ params.toItem ]
		chain action: 'list', params: [id:params.toItem]
		
		}
	
		
	}
		
	def private error(GrailsParameterMap params){
			Integer l=0, m=0, n=0
			log.debug("para,esad "+params )
			def taxCodeList=webServicesSession.getTaxCodeList();
			try{
			if(!(params.oneTimeFixedAmount).matches("[a-z]+")){
				Integer d = (params.oneTimeFixedAmount).equals('')?0:params.oneTimeFixedAmount as BigDecimal
						log.debug("d length is "+d)
						 }
						def oo = new ArrayList<String>()
						def ofA = new ArrayList<String>()
						def osA = new ArrayList<String>()
						def ocI = new ArrayList<String>()
						def ouI = new ArrayList<String>()
						oo.add(params.oneTimeOrder)
						ofA.add(params.oneTimeFixedAmount)
						osA.add(params.oneTimeScaledAmount)
						ocI.add(params.oneTimeCurrencyId)
						ouI.add(params.oneTimePeriodUnits)
						params.oneTimeOrder = oo
						params.oneTimeFixedAmount = ofA
						params.oneTimeScaledAmount = osA
						params.oneTimeCurrencyId = ocI
						params.oneTimePeriodUnits = ouI
					log.debug("fix id "+params.oneTimeFixedAmount)
						 }catch (Exception e) {
					log.debug("fix id "+params.oneTimeFixedAmount)
					 }
						 try{
							 if(!(params.recurringFixedAmount).matches("[a-z]+")){
							 Integer d = (params.recurringFixedAmount).equals('')?0:params.recurringFixedAmount as BigDecimal
									 log.debug("d length is "+d)
							 }
							   		def ro = new ArrayList<String>()
									 def rfA = new ArrayList<String>()
									 def rsA = new ArrayList<String>()
									 def rcI = new ArrayList<String>()
									 def ruI = new ArrayList<String>()
									 ro.add(params.recurringOrder)
									 rfA.add(params.recurringFixedAmount)
									 rsA.add(params.recurringScaledAmount)
									 rcI.add(params.recurringCurrencyId)
									 ruI.add(params.recurringPeriodUnits)
									 params.recurringOrder = ro
									 params.recurringFixedAmount = rfA
									 params.recurringScaledAmount = rsA
									 params.recurringCurrencyId = rcI
									 params.recurringPeriodUnits = ruI
								 log.debug("fix id "+params.recurringFixedAmount)
									  }catch (Exception e) {
								 log.debug("fix id "+params.recurringFixedAmount)
								  }
									  try{
										  if(!(params.usageFixedAmount).matches("[a-z]+")){
										  Integer d = (params.usageFixedAmount).equals('')?0:params.usageFixedAmount as BigDecimal
												  log.debug("d length is "+d)
										  }
										  		def uo = new ArrayList<String>()
												  def ufA = new ArrayList<String>()
												  def usA = new ArrayList<String>()
												  def ucI = new ArrayList<String>()
												  def uuI = new ArrayList<String>()
												  def udcI = new ArrayList<String>()
												  def ud = new ArrayList<String>()
												  def ur = new ArrayList<String>()
												  def umin = new ArrayList<String>()
												  def umax = new ArrayList<String>()
												  def event = new ArrayList<String>()
												 // def uminc = new ArrayList<String>()
												  def umaxc = new ArrayList<String>()
												  def dmap = new ArrayList<String>()
												  uo.add(params.usageOrder)
												  ufA.add(params.usageFixedAmount)
												  usA.add(params.usageScaledAmount)
												  ucI.add(params.usageCurrencyId)
												  uuI.add(params.usagePeriodUnits)
												  udcI.add(params.dependsOnCurrencyId)
												  ud.add(params.dependsOn)
												  ur.add(params.measured)
												  umin.add(params.minAmount)
												  umax.add(params.maxAmount)
												  event.add(params.eventName)
												  //uminc.add(params.minChecked)
												  //umaxc.add(params.maxCheck)
												  dmap.add(params.category)
												  params.usageOrder = uo
												  params.usageFixedAmount = ufA
												  params.usageScaledAmount = usA
												  params.usageCurrencyId = ucI
												  params.usagePeriodUnits = uuI
												  params.dependsOnCurrencyId = udcI
												  params.dependsOn = ud
												  params.measured = ur
												  params.minAmount = umin
												  params.maxAmount = umax
												  params.eventName = event
												 // params.minChecked = uminc
												 // params.maxCheck = umaxc
												 params.category = dmap
												  log.debug("fix id "+params.usageFixedAmount)
												   }catch (Exception e) {
											  log.debug("fix id "+params.usageFixedAmount)
											   }
												   def pId = params.pDTO as Integer
												   def rId = params.rDTO as Integer
												   def uId = params.uDTO as Integer
												   log.debug("par are "+params.pDTO+" "+params.uDTO+" "+params.rDTO)
												   render view: "edit", model: [planList: getProducts(),  currencies: getCurrencies(), periodUnits: getPeriodUnits(), measured: getMeasured(), depends: getDepends(), categoryList:getCategory(), params:params, l:l, m:m, n:n, pId:pId, rId:rId, uId:uId,  events:getEvents(),  description:params.description ,taxCodeList:taxCodeList  ]
												   return
		} 
	
	def edit = {
		log.debug("in update edit product id is "+params)
		def pricing
		String description
		Integer l=0,m=0,n=0,pId = 0, rId = 0, uId=0
		params.ssbox = new ArrayList<String>()
		params.ssbox.add('')
		def taxCodeList=webServicesSession.getTaxCodeList();
		log.debug("edit params "+params)
		try{
			ProductChargeDTO pDTO = webServicesSession.getProductChargeByItemAndType(params.int('itemId'), Constants.CHARGE_ONE_TIME)
			ProductChargeDTO rDTO = webServicesSession.getProductChargeByItemAndType(params.int('itemId'), Constants.CHARGE_RECURRING)
			ProductChargeDTO uDTO = webServicesSession.getProductChargeByItemAndType(params.int('itemId'), Constants.CHARGE_USAGE)
			params.toItem = params.itemId
			
			params.oneTimeOrder = new ArrayList<String>()
			params.oneTimeFixedAmount = new ArrayList<String>()
			params.oneTimeScaledAmount = new ArrayList<String>()
			params.oneTimeCurrencyId = new ArrayList<String>()
			params.oneTimePeriodUnits = new ArrayList<String>()
		if(pDTO != null){
			params.oneoff = '1'
			params.oneTimeTaxCode=pDTO.getTaxCode()?.taxCode
			pId = pDTO.getId()
			description = pDTO.getItem().getDescription()
				def prates = new ProductChargeRateDAS().findByProductCharge(pDTO.getId())
			for(ProductChargeRateDTO chargeRate : prates){
				if(chargeRate.getDeleted()==0){
					params.oneTimeOrder.add(chargeRate.getOrder())
					params.oneTimeFixedAmount.add(chargeRate.getFixedAmount() as String)
				params.oneTimeScaledAmount.add(chargeRate.getScaledAmount())
				params.oneTimeCurrencyId.add(chargeRate.getCurrency().getId())
				params.oneTimePeriodUnits.add(chargeRate.getUnitId().getId())
				}
			}
		}else{
		params.oneTimeOrder.add('')
		params.oneTimeFixedAmount.add('')
		params.oneTimeScaledAmount.add('')
		params.oneTimeCurrencyId.add('')
		params.oneTimePeriodUnits.add('')
		}
			params.recurringOrder = new ArrayList<String>()
			params.recurringFixedAmount = new ArrayList<String>()
			params.recurringScaledAmount = new ArrayList<String>()
			params.recurringCurrencyId = new ArrayList<String>()
			params.recurringPeriodUnits = new ArrayList<String>()
			if (rDTO!=null){
				params.recurring = '2'
				params.recurringTaxCode=rDTO.getTaxCode()?.taxCode
				rId = rDTO.getId()
				description = rDTO.getItem().getDescription()
			def rrates = new ProductChargeRateDAS().findByProductCharge(rDTO.getId())

			params.rDTO = rDTO
			
			for(ProductChargeRateDTO chargeRate : rrates){
				if(chargeRate.getDeleted()==0){
					params.recurringOrder.add(chargeRate.getOrder())
				params.recurringFixedAmount.add((chargeRate.getFixedAmount() == null)?'':chargeRate.getFixedAmount())
				params.recurringScaledAmount.add(chargeRate.getScaledAmount())
				params.recurringCurrencyId.add(chargeRate.getCurrency().getId())
				params.recurringPeriodUnits.add(chargeRate.getUnitId().getId())
				}
			}
			}else{
			params.recurringOrder.add('')
			params.recurringFixedAmount.add('')
			params.recurringScaledAmount.add('')
			params.recurringCurrencyId.add('')
			params.recurringPeriodUnits.add('')
			}
			params.usageOrder = new ArrayList<String>()
			params.usageFixedAmount = new ArrayList<String>()
			params.usageScaledAmount = new ArrayList<String>()
			params.usageCurrencyId = new ArrayList<String>()
			params.usagePeriodUnits = new ArrayList<String>()
			params.measured = new ArrayList<String>()
			params.dependsOn = new ArrayList<String>()
			params.dependsOnCurrencyId = new ArrayList<String>()
			params.minAmount = new ArrayList<String>()
			params.maxAmount = new ArrayList<String>()
			params.minChecked = new ArrayList<String>()
			params.maxChecked = new ArrayList<String>()
			params.eventName = new ArrayList<String>()
			params.category = new ArrayList<String>() 
			if (uDTO!=null){
				params.usage = '3'
				params.usageTaxCode=uDTO.getTaxCode()?.taxCode
				uId = uDTO.getId()
				description = uDTO.getItem().getDescription()
			def urates = new ProductChargeRateDAS().findByProductCharge(uDTO.getId())
			for(ProductChargeRateDTO chargeRate : urates){
				if(chargeRate.getDeleted()==0){
				def eventRateMap = new EventTypeRateMapDAS().findByRate(chargeRate)
				params.usageOrder.add(chargeRate.getOrder())
				params.usageFixedAmount.add(chargeRate.getFixedAmount())
				params.usageScaledAmount.add(chargeRate.getScaledAmount())
				params.usageCurrencyId.add(chargeRate.getCurrency().getId())
				params.usagePeriodUnits.add(chargeRate.getUnitId().getId())
				params.measured.add(chargeRate.getRum().getId())
				params.eventName.add(eventRateMap.getRatingEventTypeDTO().getId())
				if(chargeRate.getDestinationMap()!=null){
				params.category.add(chargeRate.getDestinationMap().getId())
				}else{
				params.category.add('')
				}
				if(chargeRate.getRateDependee() != null){
				if(chargeRate.getRateDependee().getDependencyType()!=null){
				params.dependsOn.add(chargeRate.getRateDependee().getDependencyType().getId())
				}else{
				params.dependsOn.add('')
				}
				if(chargeRate.getRateDependee().getCurrency()!=null){
				params.dependsOnCurrencyId.add(chargeRate.getRateDependee().getCurrency().getId())
				}else{
				params.dependsOnCurrencyId.add('')
				}
				if(chargeRate.getRateDependee().getMinBalance()!=null){
				params.minAmount.add(chargeRate.getRateDependee().getMinBalance())
				}else{
				params.minAmount.add('')
				}
				if(chargeRate.getRateDependee().getMaxBalance()!=null){
				params.maxAmount.add(chargeRate.getRateDependee().getMaxBalance())
				}else{
				params.maxAmount.add('')
				}
				log.debug("event name is "+eventRateMap.getRatingEventTypeDTO().getEventName())
				
				if (chargeRate.getRateDependee().getMinBalance() == null){
					params.minChecked.add('1')
				}else{
				params.minChecked.add(null)
				}
				if (chargeRate.getRateDependee().getMaxBalance() == null){
					params.maxChecked.add('1')
				}else{
				params.maxChecked.add(null)
				}
				}else{
				params.dependsOn.add('')
				params.dependsOnCurrencyId.add('')
				params.minAmount.add('')
				params.maxAmount.add('')
				params.minChecked.add('1')
				params.maxChecked.add('1')
				}
				}
			}
			}else{
			params.usageOrder.add('')
						params.usageFixedAmount.add('')
			params.usageScaledAmount.add('')
			params.usageCurrencyId.add('')
			params.usagePeriodUnits.add('')
			params.category.add('')
			}
			if(pDTO==null && rDTO==null && uDTO==null){
				params.oneoff = '1'
				params.recurring = '2'
				params.usage = '3'
			}
			log.debug("update fixed amount "+params.recurringFixedAmount)
		}catch (Exception e) {
			log.error(e)
		}
		log.debug("item is "+params)
		render view: "edit", model: [planList: getProducts(),  currencies: getCurrencies(), periodUnits: getPeriodUnits(), measured: getMeasured(), depends: getDepends(), categoryList:getCategory() , params:params, l:l, m:m, n:n, pId:pId, rId:rId, uId:uId, description:description, events:getEvents(),taxCodeList:taxCodeList ]
		return
	}
	
	def delete = {
		try {
			webServicesSession.deleteProductCharge(params.int('itemId'))
			flash.message = 'pricing.delete.success'
			flash.args = [params.itemId, params.itemId]
		} catch (SessionInternalError e){
			flash.error ='pricing.error.delete'
			viewUtils.resolveException(flash, session.locale, e);
		} catch (Exception e) {
			log.error e
			flash.error= e.getMessage()
		}
		redirect action: 'list'
	}
	
	/*def csv = {
		def filters = filterService.getFilters(FilterType.PRICING, params)

		params.max = CsvExporter.MAX_RESULTS
		def ProductCharges = getFilteredPricing(filters, params)

		if (ProductCharges.totalCount > CsvExporter.MAX_RESULTS) {
			flash.error = message(code: 'error.export.exceeds.maximum')
			flash.args = [ CsvExporter.MAX_RESULTS ]
			redirect action: 'list', id: params.id

		} else {
			DownloadHelper.setResponseHeader(response, "prices.csv")
			Exporter<ProductChargeDTO> exporter = CsvExporter.createExporter(ProductChargeDTO.class);
			render text: exporter.export(ProductCharges), contentType: "text/csv"
		}
	}*/
}
