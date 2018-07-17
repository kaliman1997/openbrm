/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avanzadagroup.net.altanAPI;

import avanzadagroup.net.altanAPI.responses.CoverageResp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.*;

/**
 *
 * @author Arturo Ruiz
 */
public class Coverage {
	CoverageResp ar = new CoverageResp();

	public CoverageResp check(String token, String location) {
		try {
			//String location = lat + "%2C" + lng;

			location = location.replaceAll("-", "%2D");

			System.out.println(token);

			String response = sendRequest(token, location);

			if (response.equals("error")) {
				ar.setStatus("error");
				ar.setStatusDescription("Error en WS Altan");
			} else {
				String[] responseValues = response.split("\\|");
				String responseCode = responseValues[0];
				String responseJSON = responseValues[1];
				JSONObject jsonObj = new JSONObject(responseJSON);

				if (responseCode.equals("200")) {

					ar.setStatus("success");
					ar.setStatusDescription("Covertura Obtenida");
					ar.setResult(jsonObj.getString("result"));

				} else if (responseCode.equals("401")) {
					ar.setStatus("error 401");
					ar.setStatusDescription(jsonObj.getString("description"));
					ar.setErrorCode(jsonObj.getString("errorCode"));
					ar.setDescription(jsonObj.getString("description"));

				} else if (responseCode.equals("500")) {
					ar.setStatus("error 500");
					ar.setStatusDescription(jsonObj.getString("description"));
					ar.setErrorCode(jsonObj.getString("errorCode"));
					ar.setDescription(jsonObj.getString("description"));
				}
			}
		} catch (Exception e) {
			ar.setStatus("error 500");
			ar.setStatusDescription("Server error");
		}
		return ar;

	}

	private String sendRequest(String token, String loc) {
		URL url;

		try {
			url = new URL("https://altanredes-prod.apigee.net/sqm/v1/"
					+ "network-services/serviceability?address=" + loc);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			con.setRequestProperty("Authorization", "Bearer " + token);
			con.setRequestProperty("Accept", "application/json");

			con.setDoInput(true);

			BufferedReader d = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			String inputLine;

			StringBuilder buf = new StringBuilder();
			while ((inputLine = d.readLine()) != null) {
				buf.append(inputLine);
			}

			d.close();
			con.disconnect();

			return con.getResponseCode() + "|" + buf.toString();
		} catch (Exception e) {

			System.out.println(e);
			return "error";
		}

	}// registro

}
