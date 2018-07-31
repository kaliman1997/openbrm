/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avanzadagroup.net;

import org.apache.log4j.Logger;

import avanzadagroup.net.google.*;
import avanzadagroup.net.altanAPI.*;
import avanzadagroup.net.altanAPI.responses.*;

import com.sapienter.jbilling.common.FormatLogger;

/**
 *
 * @author Arturo Ruiz
 */
public class AltanAPITest {
	private static final FormatLogger LOG = new FormatLogger(
			Logger.getLogger(OAuth.class));		

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
	
		try {
			AddressCoordinates ac = new AddressCoordinates();

			AddressCoordinatesResp acr = null;

			acr = ac.getCoordinates("Av de la palma", "8", "52787",
					"Huixquilucan", "Mex", "Mexico");

			if (acr.getStatus().equals("200")) {

				OAuthResp oar = testOauth();
//				CoverageResp cr = new Coverage().check(oar.getAccessToken(),
//						acr.getLatitude(), acr.getLongitude());
//
//				LOG.debug("CBOSS::"+cr.getResult());
				
				String MSISDN = "5584614839";
				
				Activation act = new Activation();
				ActivationResponse ar = act.activate(MSISDN, "1002001037", "19.3959336,-99.176576");
				LOG.debug("CBOSS::"+ar.getStatusDescription() + " " + ar.getStatus());
				
				Suspend suspend = new Suspend();
				SuspendResponse sr = suspend.suspend(MSISDN);
				LOG.debug("CBOSS::"+sr.getStatusDescription() + " " + sr.getStatus());
				
				if (sr.getStatus().equals("success")){
					LOG.debug("CBOSS::"+"orderid" + sr.getOrderId());					
				} else {
					LOG.debug("CBOSS::"+"detail" + sr.getDetail());
				}
				
				Thread.sleep(30000);
				
				Resume resume = new Resume();
				ResumeResponse rr = resume.resume(MSISDN);
				LOG.debug("CBOSS::"+rr.getStatusDescription() + " " + rr.getStatus());
				
				if (rr.getStatus().equals("success")){
					LOG.debug("CBOSS::"+"orderid" + rr.getOrderId());					
				} else {
					LOG.debug("CBOSS::"+"detail" + rr.getDetail());
				}				
			}
			;

		} catch (Exception ex) {
			LOG.debug("CBOSS::"+ex);
		}

	}

	private static OAuthResp testOauth() {
		OAuth oa = new OAuth();
		OAuthResp oar = null;
		try {

			oar = oa.getToken();

			LOG.debug("CBOSS::"+oar.getAccessToken());

		} catch (Exception e) {
			LOG.debug("CBOSS::"+e);
		}

		return oar;

	}

	private static void testLocation() {
		try {
			AddressCoordinates ac = new AddressCoordinates();

			boolean coordenadasObtenidas = false;
			int intentos = 0;
			AddressCoordinatesResp acr = null;

			while (!coordenadasObtenidas && intentos < 5) {
				acr = ac.getCoordinates("Julio Garcia", "87", "08620",
						"Iztacalco", "CDMX", "Mexico");

				Thread.sleep(200);
				intentos++;
			}

			LOG.debug("CBOSS::"+acr.getStatus() + " "
					+ acr.getStatusDescription());
			LOG.debug("CBOSS::"+acr.getLatitude() + " " + acr.getLongitude());

			acr = ac.getCoordinates("Avenida Pacifico", "284", "04330",
					"Coyoacan", "CDMX", "Mexico");

			LOG.debug("CBOSS::"+acr.getStatus() + " "
					+ acr.getStatusDescription());
			LOG.debug("CBOSS::"+acr.getLatitude() + " " + acr.getLongitude());

			Thread.sleep(200);

			acr = ac.getCoordinates("Oriente 26", "289", "",
					"Cd Nezahualcoyotl", "Mex", "Mexico");

			LOG.debug("CBOSS::"+acr.getStatus() + " "
					+ acr.getStatusDescription());
			LOG.debug("CBOSS::"+acr.getLatitude() + " " + acr.getLongitude());
		} catch (InterruptedException ex) {
			LOG.debug(ex);
		}
	}

}
