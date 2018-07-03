/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avanzadagroup.net.altanAPI;

import avanzadagroup.net.altanAPI.responses.ActivationResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.*;

/**
 *
 * @author Arturo Ruiz
 */
public class Activation {
	ActivationResponse ar = new ActivationResponse();

	public ActivationResponse activate(String MSISDN, String offeringId,
			String address) {
		String body = "{\" msisdn\": \"" + MSISDN + "\"," + "\"offeringId\":\""
				+ offeringId + "\"," + "\"address\":\"" + address + "\"}";

		String response = sendRequest(body);

		if (response.equals("error")) {
			ar.setStatus("error");
			ar.setStatusDescription("Error en WS Altan");
		} else {
			try {

				String[] responseValues = response.split("|");

				String responseCode = responseValues[0];
				String responseJSON = responseValues[1];
				JSONObject jsonObj = new JSONObject(responseJSON);

				if (responseCode.equals("200")) {

					ar.setStatus("success");
					ar.setStatusDescription("Activacion correcta");
					ar.setMsisdn(jsonObj.getString("msisdn"));
					ar.setEffectiveDate(jsonObj.getString("efectiveDate"));

				} else if (responseCode.equals("400")) {
					ar.setStatus("error 400");
					ar.setStatusDescription(jsonObj.getString("description"));
					ar.setErrorCode(jsonObj.getString("errorCode"));
					ar.setDescription(jsonObj.getString("description"));
					ar.setDetail(jsonObj.getString("detail"));

				} else if (responseCode.equals("500")) {
					ar.setStatus("error 500");
					ar.setStatusDescription(jsonObj.getString("description"));
					ar.setErrorCode(jsonObj.getString("errorCode"));
					ar.setDescription(jsonObj.getString("description"));
					ar.setDetail(jsonObj.getString("detail"));
					ar.setTicket(jsonObj.getString("ticket"));
				}
			} catch (Exception e) {
				ar.setStatus("error 500");
				ar.setStatusDescription("Server error");

			}

		}

		return ar;

	}

	private String sendRequest(String body) {
		URL url;
		HttpURLConnection connection = null;
		try {
			url = new URL("https://altanHost:port/v1/activations/msisdn");
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("authorization", body);
			connection.setRequestProperty("Operation-User", body);
			connection.setRequestProperty("Operation-Password", body);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Accept-Language",
					"es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3");
			connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
			connection.setRequestProperty("Content-Length", "" + body.length());
			connection.setRequestProperty("Cache-Control", "max-age=0");
			connection
					.setRequestProperty("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; rv:35.0) Gecko/20100101 Firefox/35.0");
			connection.setRequestProperty("Pragma", "no-cache");

			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(body);
			wr.flush();
			wr.close();

 
		BufferedReader d
			          = new BufferedReader(new InputStreamReader(
			        		  connection.getInputStream()));
			 
			
		
			String inputLine;

			StringBuilder buf = new StringBuilder();
			while ((inputLine = d.readLine()) != null) {
				buf.append(inputLine);
			}

			d.close();
			connection.disconnect();

			return connection.getResponseCode() + "|" + buf.toString();
		} catch (Exception e) {

			return "error";
		}

	}// registro

}
