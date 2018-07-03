

package jbilling

//import grails.plugins.springsecurity.Secured

import com.sapienter.jbilling.common.SessionInternalError
import java.text.Format;
import java.text.SimpleDateFormat;
import com.sapienter.jbilling.server.item.db.ItemDTO;
import javax.sql.DataSource;
import java.lang.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.Writer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.*;
import java.util.*;
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap;
import com.sapienter.jbilling.client.ViewUtils
import com.sapienter.jbilling.common.SessionInternalError
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;

import com.sapienter.jbilling.server.user.db.CompanyDTO
import in.saralam.sbs.server.voucher.VoucherUtils
import in.saralam.sbs.server.voucher.db.VoucherDTO
import in.saralam.sbs.server.voucher.db.VoucherDAS
import in.saralam.sbs.server.voucher.db.VoucherStatusDTO
import in.saralam.sbs.server.voucher.db.VoucherStatusDAS
import in.saralam.sbs.server.voucher.VoucherWS
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
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.SpringSecurityUtils


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
import java.lang.String;
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;


import com.sapienter.jbilling.server.item.db.ItemTypeDAS
import com.sapienter.jbilling.common.SessionInternalError
import com.sapienter.jbilling.server.process.AgeingWS
import com.sapienter.jbilling.server.user.contact.db.ContactDTO
import com.sapienter.jbilling.server.user.contact.db.ContactMapDTO
import com.sapienter.jbilling.server.user.contact.db.ContactTypeDTO
import com.sapienter.jbilling.server.user.db.CompanyDTO
import com.sapienter.jbilling.server.util.Constants
import com.sapienter.jbilling.server.util.PreferenceTypeWS
import com.sapienter.jbilling.server.util.PreferenceWS
import com.sapienter.jbilling.server.util.db.PreferenceTypeDTO
import com.sapienter.jbilling.common.Util
//import grails.plugins.springsecurity.Secured
//import grails.plugin.springsecurity.annotation.Secured
import com.sapienter.jbilling.server.util.db.CurrencyDTO
import com.sapienter.jbilling.server.item.CurrencyBL
import com.sapienter.jbilling.server.util.CurrencyWS
import com.sapienter.jbilling.server.user.ContactWS
import com.sapienter.jbilling.server.user.CompanyWS
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import jbilling.FilterType

/**
 * VoucherController
 *
 * @author Brian Cowdery
 * @since 03-Jan-2011
 */
//@Secured(["isAuthenticated()"])
class VoucherController {
static pagination = [ max: 10, offset: 0, sort: 'id', order: 'desc' ]
	
		WebServicesSessionSpringBean webServicesSession = new  WebServicesSessionSpringBean();
		def filterService
	
		ViewUtils viewUtils
		DataSource dataSource
		def breadcrumbService
		def userSession
		def recentItemService
		def springSecurityService

	//@Secured(["hasAnyRole('MENU_90', 'CUSTOMER_15')"])
	def index () {
		redirect (action: 'list', params: params)
	}
	def show = {
		VoucherWS voucherWS = webServicesSession.getVoucherWS(params.int('id'))
		render template:'show', model: [voucherWS: voucherWS ]
	}
				
	 def private getVoucherList(filters, statuses, GrailsParameterMap params) {
		params.max = params?.max?.toInteger() ?: pagination.max
		params.offset = params?.offset?.toInteger() ?: pagination.offset
		params.sort = params?.sort ?: pagination.sort
		params.order = params?.order ?: pagination.order
			   return VoucherDTO.createCriteria().list(
				max:    params.max,
				offset: params.offset
		){
		try{
            		and {
				filters.each { filter ->
					//log.debug "Filter value: '${filter.field}'"
					if (filter.value != null) {
						// handle voucher status separately from the other constraints
						// we need to find the VoucherStatusDTO to compare to
						if (filter.constraintType == FilterConstraint.STATUS) {
							eq("voucherStatus", statuses.find{ it.id == filter.integerValue })
							}
					else{
							addToCriteria(filter.getRestrictions());
					}
				}
				}
				eq('entityId', session['company_id'])
			}		
        	}catch (Exception e) {
			log.debug("exception is " + e)
		}
			SortableCriteria.sort(params, delegate)
		}
	}
	
	
	 
	  /**
	 * Applies the set filters to the voucher list, and exports it as a CSV for download.
	 */
	
	def csv = {
	
		def filters = filterService.getFilters(FilterType.VOUCHER, params)
		def statuses = new VoucherStatusDAS().findAll()
		params.max = CsvExporter.MAX_RESULTS
		def vouchers = getVoucherList(filters, statuses,params)
		

		if (vouchers.totalCount > CsvExporter.MAX_RESULTS) {
			flash.error = message(code: 'error.export.exceeds.maximum')
			flash.args = [ CsvExporter.MAX_RESULTS ]
			redirect action: 'list'

		} else {
			DownloadHelper.setResponseHeader(response, "vouchers.csv")
			Exporter<VoucherDTO> exporter = CsvExporter.createExporter(VoucherDTO.class);
			render text: exporter.export(vouchers), contentType: "text/csv"
		}
	}
	
	 def list = {
			def filters = filterService.getFilters(FilterType.VOUCHER, params)
			def statuses = new VoucherStatusDAS().findAll()
			def vouchers = getVoucherList(filters,statuses,params)
			
			log.debug ("vouchers in list :" + vouchers);
			if (params.applyFilter || params.partial) {
			render template: 'voucher', model: [ planList: getProducts(), filters: filters , statuses: statuses, vouchers : vouchers]
			} else {
			render view: 'list', model: [ planList: getProducts(), filters: filters , statuses: statuses , vouchers:vouchers]
					}
			}
	def edit = {
			params.max = params?.max?.toInteger() ?: pagination.max
			params.offset = params?.offset?.toInteger() ?: pagination.offset
		
			render template: 'edit' ,model :[planList: getProducts()]
				}
		
	def getProducts(){
			Integer entityId = webServicesSession.getCallerCompanyId();
			def description = "Voucher";
			def planList = null;
			try{
				planList = webServicesSession.getItemByCategory(new ItemTypeDAS().findByDescription(entityId, description).getId());
			}catch(Exception e){

				log.debug(" planlist not found")
			}
			return planList
	}
		
	def update = {
		
		VoucherWS voucherWS = webServicesSession.getVoucherWS(params.int('id'))
		log.debug("voucherWS in update def : " + voucherWS);
		String status = voucherWS.getVoucherStatus().getDescription();
		
		render template: 'update', model: [voucherWS: voucherWS , status : status]		
		
		}
		
		
	def statusupdate = {

        try{

            VoucherWS voucherWS = webServicesSession.getVoucherWS(params.int('id'))
            VoucherDTO voucherDTO=new VoucherDAS().find(voucherWS.getId());
            Integer status =params.int('toStatus');
            Integer statuss=new VoucherStatusDAS().findById(status);
            VoucherStatusDTO voucherStatus=new VoucherStatusDAS().find(Integer.valueOf(statuss));
            def x=webServicesSession.updateVoucherStatus(voucherStatus,voucherDTO);
            flash.message = 'Voucher details have been sucessfully updated'
            chain action:'list'    
          }
        catch (SessionInternalError e) {
            viewUtils.resolveException(flash, session.locale, e)
           chain action: 'list'
           return
       }        
   }	 
			
	def save = {
		def voucherws = new VoucherWS();
		def errorCount=0;
		
		try{
			def statuses = new VoucherStatusDAS().findAll()
			
			VoucherStatusDTO voucherStatus = statuses.first();
			
			int productId = params.int('toItemId');
			int batchSize = params.int('BatchSize');
			String batchId = params.BatchId;
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		
			def today = new Date()
			Date createdDateTime = today;
			def voucherWS;
			def y;
				
			sdf.setLenient(false);
			Calendar c = Calendar.getInstance();
			c.setTime(createdDateTime);
			int vf = c.get(Calendar.YEAR);
				if (vf > 1000){
					log.debug("createdDateTime year..." +vf);
					}
				else{
						log.debug("continue");
					}
								
				if(productId == null) {
			
				flash.error= 'Missing productId ! Please select productId'
								  
					  }
						if(productId != null){
						errorCount++
						
			}
				
				Integer entityId = webServicesSession.getCallerCompanyId();
				
				VoucherUtils voucherGen = new VoucherUtils();

				List<String> vouchers = voucherGen.generateVoucherNumber(batchId, batchSize);
			
			
				String pinCode;
				Integer serialNo=0;
				for(String voucher : vouchers ) {
				
				pinCode = voucher.toString();
				voucherWS = new VoucherWS(createdDateTime,voucherStatus,entityId,serialNo,pinCode,batchId,productId);
				log.debug("voucherWS is with : " +  createdDateTime+ " " + voucherStatus + " " +entityId + " "  + serialNo + " " + pinCode + " "+ batchId +" "  + productId );
				y = webServicesSession.createVoucher(voucherWS);
				serialNo++;
				log.debug("y  in wssb save def . .{ { [ [ [ [ [ [[ [: " + y);
			}
		 if(errorCount!=0) {
							flash.message = 'VOUCHER CARD details are Sucessfully Loaded'
							}
				else{
						flash.info = 'Failed Generating vouchers'
					}
										   
				}
	
			catch (SessionInternalError e) {
					viewUtils.resolveException(flash, session.locale, e)
					chain action: 'list', model: [ selected: voucherws ]
				return
					}
				
			chain action: 'list', params: [ id: voucherws?.id ]
	
		}
	
	def splitfields(line) {
	
		log.debug("splitfields is called");
		return line.split(",", -1);
	
		}
		
		def delete = {
		if (params.id) {
			webServicesSession.deleteVoucher(params.int('id'))

			flash.message = 'voucher.deleted'
			flash.args = [ params.id ]
			log.debug("Deleted voucher ${params.id}.")

		}

		// render the partial user list
		params.partial = true
		list()
	}
		}
