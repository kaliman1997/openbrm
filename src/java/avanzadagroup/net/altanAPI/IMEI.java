/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avanzadagroup.net.altanAPI;

import avanzadagroup.net.altanAPI.responses.IMEIResponse;
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
public class IMEI {
	private static final FormatLogger LOG = new FormatLogger(
			Logger.getLogger(OAuth.class));
	IMEIResponse ar = new IMEIResponse();

	public IMEIResponse operation(String IMEI, String operation) {
		
		String response = sendRequest(new OAuth().getToken().
                        getAccessToken(), IMEI, operation);		
                ar.setJsonResponse(response);

		if (response.equals("error")) {
			ar.setStatus("error");
			ar.setStatusDescription("Error en WS Altan");
		} else {
			try {

				LOG.debug("CBOSS::"+response);
				String[] responseValues = response.split("\\|");

				String responseCode = responseValues[0];
				String responseJSON = responseValues[1];
				LOG.debug("CBOSS::"+responseCode);
				JSONObject jsonObj = new JSONObject(responseJSON);
				
				RegisterOperation.write("Imei", responseCode, responseJSON, "");

				if (responseCode.equals("200")) {

					ar.setStatus("success");
					ar.setStatusDescription("Activacion correcta");
					ar.setImei(jsonObj.getString("imei"));
					ar.setEffectiveDate(jsonObj.getString("effectiveDate"));

				} else if (responseCode.equals("400")) {
					ar.setStatus("error 400");
					ar.setStatusDescription(jsonObj.getString("description"));
					ar.setErrorCode(jsonObj.getString("errorCode"));
					ar.setDescription(jsonObj.getString("description"));
					try{
						ar.setDetail(jsonObj.getString("detail"));
					} catch (JSONException jsonE){
						LOG.debug("CBOSS::"+jsonE.toString());
						for(StackTraceElement ste : jsonE.getStackTrace()){
							LOG.debug("CBOSS::"+ste.toString());
							
						}
					}

				} else if (responseCode.equals("500")) {
					ar.setStatus("error 500");
					ar.setStatusDescription(jsonObj.getString("description"));
					ar.setErrorCode(jsonObj.getString("errorCode"));
					ar.setDescription(jsonObj.getString("description"));
					try{
						ar.setDetail(jsonObj.getString("detail"));
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
				ar.setStatus("error 500");
				ar.setStatusDescription("Server error");

			}

		}

		return ar;

	}

	public String sendRequest(String accessToken, String IMEI, 
                String operation) {
		
		
		URL url;
		HttpURLConnection connection = null;
		try {
			url = new URL("https://altanredes-prod.apigee.net/"
                                + "cm/v1/imei/"+IMEI+"/" + operation);
			LOG.debug("CBOSS::"+url.getPath());
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("authorization", "Bearer " + accessToken);
			
		
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestProperty("Content-Length", Integer.toString("...".length()));
            connection.getOutputStream().write("...".getBytes("UTF8"));
			LOG.debug("CBOSS::"+"Length " + connection.getRequestProperty("Content-Length"));
			
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
