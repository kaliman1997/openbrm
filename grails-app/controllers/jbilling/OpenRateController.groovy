
package jbilling

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
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured
import com.sapienter.jbilling.server.util.db.CurrencyDTO
import com.sapienter.jbilling.server.item.CurrencyBL
import com.sapienter.jbilling.server.util.CurrencyWS
import com.sapienter.jbilling.server.user.ContactWS
import com.sapienter.jbilling.server.user.CompanyWS
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap


//@Secured(["MENU_104"])
class OpenRateController {

    def breadcrumbService
	def webServicesSession
	def viewUtils
	def userSession

    /*
        Show/edit all preferences
     */

   
     def index = {
		//render view: 'index', model: [rates: rates, selected: selected]
     }
    /*
        Ageing configuration
     */

	
	def destinationMap = {
		flash.message= 'destination map'
	}
	
	def serviceMap = {
		flash.message= 'service map'
		
	}
	def customerRates = {
		flash.message= 'customer rates'
		
	}
	def holidayMap = {
		flash.message= 'holiday map'
		
	}
	def worldZoneMap = {
		flash.message= 'worldZone map'
		
	}
	def UploadCDRFile = {
		flash.message= 'UploadCDR File'
		
	}
	
}
