package avanzada


import avanzadagroup.net.altanAPI.Coverage;
import avanzadagroup.net.altanAPI.IMEI
import avanzadagroup.net.altanAPI.OrderStatus
import avanzadagroup.net.altanAPI.Profile
import avanzadagroup.net.altanAPI.responses.AddressCoordinatesResp;
import avanzadagroup.net.altanAPI.responses.CoverageResp
import avanzadagroup.net.altanAPI.responses.IMEIResponse
import avanzadagroup.net.altanAPI.responses.OrderStatusResponse
import avanzadagroup.net.altanAPI.responses.ProfileResponse
import avanzadagroup.net.google.AddressCoordinates

import com.sapienter.jbilling.client.util.SortableCriteria
import com.sapienter.jbilling.common.SessionInternalError

import org.joda.time.format.DateTimeFormat

import com.sapienter.jbilling.server.item.db.ItemDAS;
import com.sapienter.jbilling.server.report.db.ReportDTO
import com.sapienter.jbilling.server.util.Constants
import com.sapienter.jbilling.server.util.PreferenceBL
import com.sapienter.jbilling.server.report.db.ReportTypeDTO
import com.sapienter.jbilling.server.report.ReportBL
import com.sapienter.jbilling.server.report.ReportExportFormat
import com.sapienter.jbilling.client.util.Constants;
import com.sapienter.jbilling.client.util.DownloadHelper
import com.sapienter.jbilling.server.report.db.ReportParameterDTO

import org.apache.commons.lang.StringUtils;
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.hibernate.criterion.MatchMode
import org.hibernate.criterion.Restrictions





import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

/**
 * ReportController
 *
 * @author Brian Cowdery
 * @since 07/03/11
 */
@Secured(["isAuthenticated()"])
class AltanOperationsController {

	static scope = "prototype"
	static pagination = [ max: 10, offset: 0 ]
	static final viewColumnsToFields =
	['reportId': 'id']

	def viewUtils
	def filterService
	def recentItemService
	def breadcrumbService

	def index () {
		list()
	}





	def list () {
		breadcrumbService.addBreadcrumb(controllerName, "index", null, null,  null)
		render view: 'list', model: [ selectedTypeId: params.int('id') ]
	}



	/**
	 * Converts * to JSon
	 */
	private def Object getAsJsonData(elements, GrailsParameterMap params) {
		def jsonCells = elements
		def currentPage = params.page ? Integer.valueOf(params.page) : 1
		def rowsNumber = params.rows ? Integer.valueOf(params.rows): 1
		def totalRecords =  jsonCells ? jsonCells.totalCount : 0
		def numberOfPages = Math.ceil(totalRecords / rowsNumber)

		def jsonData = [rows: jsonCells, page: currentPage, records: totalRecords, total: numberOfPages]

		jsonData
	}

	def operations () {
		def id = params.int('id')
		breadcrumbService.addBreadcrumb(controllerName, actionName, 'operations', id,  null)
		render template:'operations', model: [ id: id ]
	}

	def show () {
		breadcrumbService.addBreadcrumb(controllerName, "index", null, null,  null)
		def id = params.get('id')

		if(id.equals('2_1')){
			render template:'serviciability/qos'
			return
		}

		if(id.equals('3_1')){
			render template:'configuration/orderStatus'
			return
		}

		if(id.equals('4_1')){
			render template:'clients/blockIMEI', model:[id:'4_1']
			return
		}

		if(id.equals('4_2')){
			render template:'clients/blockIMEI', model:[id:'4_2']
			return
		}

		if(id.equals('4_5')){
			render template:'clients/profile'
			return
		}

		render template:'show' , model: [ id: id ]
	}

	/**
	 * Runs the given report using the entered report parameters. If no format is selected, the report
	 * will be rendered as HTML. If an export format is selected, then the generated file will be sent
	 * to the browser.
	 */
	def operationResult () {
		def id = params.get('id')
		breadcrumbService.addBreadcrumb(controllerName, "index", null, null,  null)
		if(params.get('id').equals('2_1')){
			AddressCoordinates ac = new AddressCoordinates();
			AddressCoordinatesResp acr = ac.getCoordinates(params.get('calle'),
					params.get('noExterior'), params.get('cp'), params.get('ciudad'), params.get('estado'),
					'Mexico');
			String location = acr.getLatitude()+","+acr.getLongitude();


			Coverage co = new Coverage();
			CoverageResp cr = co.check(location)

			render template:'serviciability/qosResult', model:[
				location:location,
				coverage: cr]
			return;
		}else if(params.get('id').equals('3_1')){
			OrderStatusResponse osr = new OrderStatus().status(params.get('orderId'));
			render template:'configuration/orderStatusResult', model:[orderId:params.get('orderId'), osr:osr]
			return;
		}else if(id.equals('4_1') || id.equals('4_2')){
			IMEIResponse ir = new IMEI().operation(params.get('imei'),
					id.equals('4_1')?"lock":"unlock");
			render template:'clients/blockIMEIResult', 
			model:[imei: params.get('imei'), ir:ir, id:id]
			return;
		}else if(params.get('id').equals('4_5')){
			Profile profile = new Profile();
			ProfileResponse pr = profile.profile(params.get('msisdn'))
			render template:'clients/profileResult', model:[msisdn: params.get('msisdn'), pr:pr]
			return;
		}
	}

	/**
	 * Returns image data generated by the jasper report HTML rendering.
	 *
	 * Rendering a jasper report to HTML produces a map of images that is stored in the session. This action
	 * retrieves images by name and returns the bytes to the browser. The jasper report HTML contains <code>img</code>
	 * tags that look to this action as their source.
	 */
	def images () {
		Map images = session[ReportBL.SESSION_IMAGE_MAP]
		response.outputStream << images.get(params.name)
	}

	def bindParameters(report, params) {
		params.each { name, value ->
			ReportParameterDTO<?> parameter = report.getParameter(name)
			if (parameter) {

				bindData(parameter, ['value': value])
			}
		}

		try {
			report.childEntities = new ArrayList<Integer>()
			// bind childs to list
			params.list('childs').each { child ->
				report.childEntities.add(Integer.parseInt(child))
			}
		} catch(Exception e) {
			//string is null,
		}
	}
}

