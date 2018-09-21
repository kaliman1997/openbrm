package cboss

import javax.jws.WebMethod;
import javax.jws.WebResult;

import avanzadagroup.net.altanAPI.responses.ActivationResponse
import grails.transaction.Transactional

@Transactional
class BanwireNotificationService {
	static expose = ['cxfjax']
	
	@WebResult
	@WebMethod
    def serviceMethod(ActivationResponse ar) {
		return ar.jsonResponse;
    }
}
