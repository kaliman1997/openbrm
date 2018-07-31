package avanzada


import avanzadagroup.net.altanAPI.Batch
import avanzadagroup.net.altanAPI.Coverage;
import avanzadagroup.net.altanAPI.IMEI
import avanzadagroup.net.altanAPI.OAuth
import avanzadagroup.net.altanAPI.OrderStatus
import avanzadagroup.net.altanAPI.Profile
import avanzadagroup.net.altanAPI.SubscriberPatch
import avanzadagroup.net.altanAPI.responses.ActivationResponse
import avanzadagroup.net.altanAPI.responses.AddressCoordinatesResp;
import avanzadagroup.net.altanAPI.responses.BatchResponse
import avanzadagroup.net.altanAPI.responses.CoverageResp
import avanzadagroup.net.altanAPI.responses.IMEIResponse
import avanzadagroup.net.altanAPI.responses.OAuthResp
import avanzadagroup.net.altanAPI.responses.OrderStatusResponse
import avanzadagroup.net.altanAPI.responses.ProfileResponse
import avanzadagroup.net.google.AddressCoordinates

import com.sapienter.jbilling.client.util.SortableCriteria
import com.sapienter.jbilling.common.SessionInternalError

import org.joda.time.format.DateTimeFormat

import com.sapienter.jbilling.common.Util
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
import org.apache.commons.io.FilenameUtils
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.hibernate.criterion.MatchMode
import org.hibernate.criterion.Restrictions





import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import jbilling.Breadcrumb

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
	 * Upload a file containing new assets. Start a batch job to import the assets.
	 *
	 * @param assetFile - CSV file containing asset definitions
	 * @param prodId - product the assets will belong to
	 */
	def uploadBatch () {
		def operation = params.get('operation');
		def file = request.getFile('assetFile');
		def reportAssetDir = new File(Util.getSysProp("base_dir") + File.separator + "reports" + File.separator + "assets");

		//csv file we are uploading
		String fileExtension = FilenameUtils.getExtension(file.originalFilename)
		Breadcrumb lastBreadcrumb = breadcrumbService.lastBreadcrumb as Breadcrumb
		def csvFile = File.createTempFile("assets", ".csv", reportAssetDir)
		if (fileExtension && !fileExtension.equals("csv")) {
			flash.error = "csv.error.found"
			redirect(controller: lastBreadcrumb.controller, action: lastBreadcrumb.action, params: [id: lastBreadcrumb.objectId, showAssetUploadTemplate: true])
			return
		} else if(!fileExtension) {
			flash.error = "validation.file.upload"
			redirect(controller: lastBreadcrumb.controller, action: lastBreadcrumb.action, params: [id: lastBreadcrumb.objectId, showAssetUploadTemplate: true])
			return
		}
		//file which will contain a list of errors
		def csvErrorFile = File.createTempFile("assetsError", ".csv", reportAssetDir)

		//copy the uploaded file to a temp file
		file.transferTo(csvFile)

		BatchResponse br = new Batch().activate(csvFile.getPath(), operation)
		//render template: 'clients/batchResult', model: [br:br]
		flash.message = "Resultado Operacion Batch " 
		+ br.status.equals("success")?
		"Estado: "+ br.status +", Fecha" + br.effectiveDate + ", Lineas " + br.Lines + ", Id Transaccion "  + br.transactionId
		:
		"Error" +br.errorCode + ", Descripcion" + br.description;
		redirect(action: "list", id: 1)
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
		def id = params.get('id')
		breadcrumbService.addBreadcrumb(controllerName, "index", null, null,  null)
		if(id.equals('5')){
			render template:'clients/batch', model: [ id: id ]
			return;

		} else {
			render template:'operations', model: [ id: params.int('id') ]
			return;
		}
	}

	def show () {
		breadcrumbService.addBreadcrumb(controllerName, "index", null, null,  null)
		def id = params.get('id')

		if(params.get('id').equals('1_1')){
			OAuthResp oar = new OAuth().getToken();
			render template:'security/authenticationResult', model:[oar:oar]
			return;
		}


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

		if(id.equals('4_3') || id.equals('4_4')){
			render template:'clients/subscriberPatch', model:[id:id]
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
		}else if(id.equals('4_3') || id.equals('4_4')){
			if(id.equals('4_3')){
				AddressCoordinates ac = new AddressCoordinates();
				AddressCoordinatesResp acr = ac.getCoordinates(params.get('calle'),
						params.get('noExterior'), params.get('cp'), params.get('ciudad'), params.get('estado'),
						'Mexico');
				String location = acr.getLatitude()+","+acr.getLongitude();

				ActivationResponse ar = new SubscriberPatch().changePrimaryOffer(
						params.get('msisdn'), params.get('offeringId'), location);
				render template:'clients/subscriberPatchResult',
				model:[msisdn: params.get('msisdn'), ar:ar, id:id]
				return;
			} else {
				ActivationResponse ar = new SubscriberPatch().changeICC(params.get('msisdn'),
						params.get('icc'));
				render template:'clients/subscriberPatchResult',
				model:[msisdn: params.get('msisdn'), ar:ar, id:id]
			}
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

