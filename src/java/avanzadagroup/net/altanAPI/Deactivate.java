/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avanzadagroup.net.altanAPI;

import avanzadagroup.net.altanAPI.responses.DeactivateResponse;
import avanzadagroup.net.dataacess.RegisterOperation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.json.*;

import com.sapienter.jbilling.common.FormatLogger;

/**
 *
 * @author Arturo Ruiz
 */
public class Deactivate {
	private static final FormatLogger LOG = new FormatLogger(
			Logger.getLogger(OAuth.class));	
	DeactivateResponse dr = new DeactivateResponse();

	public DeactivateResponse deactivate(String MSISDN) {
		OAuth oauth = new OAuth();
		
		String response = sendRequest(oauth.getToken().getAccessToken(), MSISDN);		

		if (response.equals("error")) {
			dr.setStatus("error");
			dr.setStatusDescription("Error en WS Altan");
		} else {
			try {

				LOG.debug("CBOSS::"+response);
				String[] responseValues = response.split("\\|");

				String responseCode = responseValues[0];
				String responseJSON = responseValues[1];
				LOG.debug("CBOSS::"+responseCode);
				JSONObject jsonObj = new JSONObject(responseJSON);
				
				RegisterOperation.write("Deactivate", responseCode, responseJSON, MSISDN);

				if (responseCode.equals("200")) {

					dr.setStatus("success");
					dr.setStatusDescription("Deactivate correcta");
					dr.setMsisdn(jsonObj.getString("msisdn"));
					dr.setEffectiveDate(jsonObj.getString("effectiveDate"));
					dr.setOrderId(jsonObj.getJSONObject("order").getString("id"));

				} else if (responseCode.equals("400")) {
					dr.setStatus("error 400");
					dr.setStatusDescription(jsonObj.getString("description"));
					dr.setErrorCode(jsonObj.getString("errorCode"));
					dr.setDescription(jsonObj.getString("description"));
					try{
						dr.setDetail(jsonObj.getString("detail"));
					} catch (JSONException jsonE){
						LOG.debug("CBOSS::"+jsonE.toString());
						for(StackTraceElement ste : jsonE.getStackTrace()){
							LOG.debug("CBOSS::"+ste.toString());
							
						}
					}

				} else if (responseCode.equals("500")) {
					dr.setStatus("error 500");
					dr.setStatusDescription(jsonObj.getString("description"));
					dr.setErrorCode(jsonObj.getString("errorCode"));
					dr.setDescription(jsonObj.getString("description"));
					try{
						dr.setDetail(jsonObj.getString("detail"));
					} catch (JSONException jsonE){
						LOG.debug("CBOSS::"+jsonE.toString());
						for(StackTraceElement ste : jsonE.getStackTrace()){
							LOG.debug("CBOSS::"+ste.toString());
							
						}
					}
				}
			} catch (Exception e) {
				LOG.debug("CBOSS::"+e.toString());
				for(StackTraceElement ste : e.getStackTrace()){
					LOG.debug("CBOSS::"+ste.toString());
					
				}
				dr.setStatus("error 500");
				dr.setStatusDescription("Server error");

			}

		}

		return dr;

	}

	private String sendRequest(String accessToken, String msisdn) {
		
		URL url;
		HttpURLConnection connection = null;
		try {
			url = new URL("https://altanredes-test.apigee.net/"
					+ "cm/v1/subscribers/"+msisdn+"/deactivate");
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("authorization", "Bearer " + accessToken);
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Length", "0");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			String body = "";
			
			if (body != null) {
				connection.setRequestProperty("Content-Length", Integer.toString(body.length()));
				connection.getOutputStream().write(body.getBytes("UTF8"));
				}
		
			
			BufferedReader d;
			
			if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
				 d = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
			} else {
				d = new BufferedReader(new InputStreamReader(
						connection.getErrorStream()));
			}



			String inputLine;

			StringBuilder buf = new StringBuilder();
			while ((inputLine = d.readLine()) != null) {
				buf.append(inputLine);
			}

			d.close();
			connection.disconnect();

			return connection.getResponseCode() + "|" + buf.toString();
		} catch (Exception e) {
			LOG.debug("CBOSS::"+e.toString());
			for(StackTraceElement ste: e.getStackTrace()){
				LOG.debug("CBOSS::"+ste.toString());
				
			}

			return "error";
		}

	}

}
