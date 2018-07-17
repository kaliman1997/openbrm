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
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.json.*;

/**
 *
 * @author Arturo Ruiz
 */
public class SubscriberPatch {
	ActivationResponse ar = new ActivationResponse();

	public ActivationResponse changePrimaryOffer(String MSISDN, String offeringId,
			String address) {
		String body = "{    \"primaryOffering\": {\n" +
                                        "\"address\": \""+address+"\",\n" +
                                        "\"offeringId\": \""+offeringId+"\"\n" +
                                "}"
                        + "}";

		String response = sendRequest(new OAuth().getToken().
                        getAccessToken(), MSISDN, body);		
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
        
	public ActivationResponse changeICC(String MSISDN, String ICC) {
		String body = "{"
                        + " \"changeSubscriberSIM\": {\n" +
                        "        \"newIccid\": \"" + ICC + "\"\n" +
                                "    }"
                        + "}";

		String response = sendRequest(new OAuth().getToken().
                        getAccessToken(), MSISDN, body);		
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

	public String sendRequest(String accessToken, String msisdn, String body) {
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
                        allowMethods("PATCH");
                        
			url = new URL("https://altanredes-prod.apigee.net/cm/v1/subscribers/"+msisdn);
			connection = (HttpURLConnection) url.openConnection();
			
                        connection.setRequestProperty("X-HTTP-Method-Override", "PATCH");
                        connection.setRequestMethod("PATCH");
			connection.setRequestProperty("authorization", "Bearer " + accessToken);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			
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
			System.out.println(e.toString());
			for(StackTraceElement ste: e.getStackTrace()){
				System.out.println(ste.toString());
				
			}

			return "error";
		}

	}

    private static void allowMethods(String... methods) {
        try {
            Field methodsField = HttpURLConnection.class.getDeclaredField("methods");

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

            methodsField.setAccessible(true);

            String[] oldMethods = (String[]) methodsField.get(null);
            Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
            methodsSet.addAll(Arrays.asList(methods));
            String[] newMethods = methodsSet.toArray(new String[0]);

            methodsField.set(null/*static field*/, newMethods);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }        
        
}
