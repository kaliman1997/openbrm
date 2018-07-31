/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avanzadagroup.net.altanAPI;

import avanzadagroup.net.altanAPI.responses.ResumeResponse;
import avanzadagroup.net.dataacess.RegisterOperation;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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
public class Resume {
	private static final FormatLogger LOG = new FormatLogger(
			Logger.getLogger(OAuth.class));
	ResumeResponse sr = new ResumeResponse();

	public ResumeResponse resume(String MSISDN) {
		OAuth oauth = new OAuth();
		
		String response = sendRequest(oauth.getToken().getAccessToken(), MSISDN);		

                sr.setJsonResponse(response);
		if (response.equals("error")) {
			sr.setStatus("error");
			sr.setStatusDescription("Error en WS Altan");
		} else {
			try {

				LOG.debug("CBOSS::"+response);
				String[] responseValues = response.split("\\|");

				String responseCode = responseValues[0];
				String responseJSON = responseValues[1];
				LOG.debug("CBOSS::"+responseCode);
				JSONObject jsonObj = new JSONObject(responseJSON);
				
				RegisterOperation.write("Resume", responseCode, responseJSON, MSISDN);

				if (responseCode.equals("200")) {

					sr.setStatus("success");
					sr.setStatusDescription("Reanudacion correcta");
					sr.setMsisdn(jsonObj.getString("msisdn"));
					sr.setEffectiveDate(jsonObj.getString("effectiveDate"));
					sr.setOrderId(jsonObj.getJSONObject("order").getString("id"));

				} else if (responseCode.equals("400")) {
					sr.setStatus("error 400");
					sr.setStatusDescription(jsonObj.getString("description"));
					sr.setErrorCode(jsonObj.getString("errorCode"));
					sr.setDescription(jsonObj.getString("description"));
					try{
						sr.setDetail(jsonObj.getString("detail"));
					} catch (JSONException jsonE){
						LOG.debug("CBOSS::"+jsonE.toString());
						for(StackTraceElement ste : jsonE.getStackTrace()){
							LOG.debug("CBOSS::"+ste.toString());
							
						}
					}

				} else if (responseCode.equals("500")) {
					sr.setStatus("error 500");
					sr.setStatusDescription(jsonObj.getString("description"));
					sr.setErrorCode(jsonObj.getString("errorCode"));
					sr.setDescription(jsonObj.getString("description"));
					try{
						sr.setDetail(jsonObj.getString("detail"));
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
				sr.setStatus("error 500");
				sr.setStatusDescription("Server error");

			}

		}

		return sr;

	}

	private String sendRequest(String accessToken, String msisdn) {
//		return "200|{\"msisdn\": \"5554316832\"," + 
//				"  \"effectiveDate\": \"20180705223420\"," + 
//				"  \"offeringId\": \"\"," + 
//				"  \"order\": {" + 
//				"    \"id\": \"9e1321375c5f48c1e472c7b69d4406f9\"" + 
//				"  }" + 
//				"}";
		
		
		URL url;
		HttpURLConnection connection = null;
		try {
			url = new URL("https://altanredes-test.apigee.net/"
					+ "cm/v1/subscribers/"+msisdn+"/resume");
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
