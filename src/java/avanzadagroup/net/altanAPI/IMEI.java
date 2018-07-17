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
public class IMEI {
	ActivationResponse ar = new ActivationResponse();

	public ActivationResponse operation(String IMEI, String operation) {
		
		String response = sendRequest(new OAuth().getToken().
                        getAccessToken(), IMEI, operation);		
                ar.setJsonResponse(response);

		if (response.equals("error")) {
			ar.setStatus("error");
			ar.setStatusDescription("Error en WS Altan");
		} else {
			try {

				System.out.println(response);
				String[] responseValues = response.split("\\|");

				String responseCode = responseValues[0];
				String responseJSON = responseValues[1];
				System.out.println(responseCode);
				JSONObject jsonObj = new JSONObject(responseJSON);

				if (responseCode.equals("200")) {

					ar.setStatus("success");
					ar.setStatusDescription("Activacion correcta");
					ar.setMsisdn(jsonObj.getString("msisdn"));
					ar.setEffectiveDate(jsonObj.getString("effectiveDate"));
					ar.setOrderId(jsonObj.getJSONObject("order").getString("id"));

				} else if (responseCode.equals("400")) {
					ar.setStatus("error 400");
					ar.setStatusDescription(jsonObj.getString("description"));
					ar.setErrorCode(jsonObj.getString("errorCode"));
					ar.setDescription(jsonObj.getString("description"));
					try{
						ar.setDetail(jsonObj.getString("detail"));
					} catch (JSONException jsonE){
						System.out.println(jsonE.toString());
						for(StackTraceElement ste : jsonE.getStackTrace()){
							System.out.println(ste.toString());
							
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
						System.out.println(jsonE.toString());
						for(StackTraceElement ste : jsonE.getStackTrace()){
							System.out.println(ste.toString());
							
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e.toString());
				for(StackTraceElement ste : e.getStackTrace()){
					System.out.println(ste.toString());
					
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
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("authorization", "Bearer " + accessToken);

			connection.setRequestProperty("Accept", "application/json");
			connection.setDoInput(true);
			
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
			System.out.println(e.toString());
			for(StackTraceElement ste: e.getStackTrace()){
				System.out.println(ste.toString());
				
			}

			return "error";
		}

	}

}
