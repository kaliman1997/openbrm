/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avanzadagroup.net;

import avanzadagroup.net.google.*;
import avanzadagroup.net.altanAPI.*;
import avanzadagroup.net.altanAPI.responses.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arturo Ruiz
 */
public class AltanAPITest {
	

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
//				System.out.println(cr.getResult());
				
				Activation act = new Activation();
				ActivationResponse ar = act.activate("5584614839", "1002001037", "19.3959336,-99.176576");
				System.out.println(ar.getStatusDescription() + " " + ar.getStatus());
			}
			;

		} catch (Exception ex) {
			System.out.println(ex);
		}

	}

	private static OAuthResp testOauth() {
		OAuth oa = new OAuth();
		OAuthResp oar = null;
		try {

			oar = oa.getToken();

			System.out.println(oar.getAccessToken());

		} catch (Exception e) {
			System.out.println(e);
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

			System.out.println(acr.getStatus() + " "
					+ acr.getStatusDescription());
			System.out.println(acr.getLatitude() + " " + acr.getLongitude());

			acr = ac.getCoordinates("Avenida Pacifico", "284", "04330",
					"Coyoacan", "CDMX", "Mexico");

			System.out.println(acr.getStatus() + " "
					+ acr.getStatusDescription());
			System.out.println(acr.getLatitude() + " " + acr.getLongitude());

			Thread.sleep(200);

			acr = ac.getCoordinates("Oriente 26", "289", "",
					"Cd Nezahualcoyotl", "Mex", "Mexico");

			System.out.println(acr.getStatus() + " "
					+ acr.getStatusDescription());
			System.out.println(acr.getLatitude() + " " + acr.getLongitude());
		} catch (InterruptedException ex) {
			Logger.getLogger(AltanAPITest.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

}
