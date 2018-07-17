/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avanzadagroup.net.altanAPI;

import avanzadagroup.net.altanAPI.responses.ResumeResponse;

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
public class Profile {
//	ProfileResponse pr = new ProfileResponse();
//
//	public ProfileResponse profile(String MSISDN) {
//		OAuth oauth = new OAuth();
//		
//		String response = sendRequest(oauth.getToken().getAccessToken(), MSISDN);		
//
//		if (response.equals("error")) {
//			sr.setStatus("error");
//			sr.setStatusDescription("Error en WS Altan");
//		} else {
//			try {
//
//				System.out.println(response);
//				String[] responseValues = response.split("\\|");
//
//				String responseCode = responseValues[0];
//				String responseJSON = responseValues[1];
//				System.out.println(responseCode);
//				JSONObject jsonObj = new JSONObject(responseJSON);
//
//				if (responseCode.equals("200")) {
//
//					sr.setStatus("success");
//					sr.setStatusDescription("Reanudacion correcta");
//					sr.setMsisdn(jsonObj.getString("msisdn"));
//					sr.setEffectiveDate(jsonObj.getString("effectiveDate"));
//					sr.setOrderId(jsonObj.getJSONObject("order").getString("id"));
//
//				} else if (responseCode.equals("400")) {
//					sr.setStatus("error 400");
//					sr.setStatusDescription(jsonObj.getString("description"));
//					sr.setErrorCode(jsonObj.getString("errorCode"));
//					sr.setDescription(jsonObj.getString("description"));
//					try{
//						sr.setDetail(jsonObj.getString("detail"));
//					} catch (JSONException jsonE){
//						System.out.println(jsonE.toString());
//						for(StackTraceElement ste : jsonE.getStackTrace()){
//							System.out.println(ste.toString());
//							
//						}
//					}
//
//				} else if (responseCode.equals("500")) {
//					sr.setStatus("error 500");
//					sr.setStatusDescription(jsonObj.getString("description"));
//					sr.setErrorCode(jsonObj.getString("errorCode"));
//					sr.setDescription(jsonObj.getString("description"));
//					try{
//						sr.setDetail(jsonObj.getString("detail"));
//					} catch (JSONException jsonE){
//						System.out.println(jsonE.toString());
//						for(StackTraceElement ste : jsonE.getStackTrace()){
//							System.out.println(ste.toString());
//							
//						}
//					}
//				}
//			} catch (Exception e) {
//				System.out.println(e.toString());
//				for(StackTraceElement ste : e.getStackTrace()){
//					System.out.println(ste.toString());
//					
//				}
//				sr.setStatus("error 500");
//				sr.setStatusDescription("Server error");
//
//			}
//
//		}
//
//		return sr;
//
//	}

	public String sendRequest(String accessToken, String msisdn) {
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
                        url = new URL("https://altanredes-prod.apigee.net/"
                                + "cm/v1/subscribers/"+msisdn+"/profile");
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
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
