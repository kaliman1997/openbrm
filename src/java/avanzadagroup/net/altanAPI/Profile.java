/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avanzadagroup.net.altanAPI;

import avanzadagroup.net.altanAPI.responses.ProfileResponse;

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
	ProfileResponse sr = new ProfileResponse();

	public ProfileResponse profile(String MSISDN) {
		OAuth oauth = new OAuth();
		
		String response = sendRequest(oauth.getToken().getAccessToken(), MSISDN);		

		if (response.equals("error")) {
			sr.setStatus("error");
			sr.setStatusDescription("Error en WS Altan");
		} else {
			try {
				sr.setJsonResponse(response);
				
				String[] responseValues = response.split("\\|");

				String responseCode = responseValues[0];
				String responseJSON = responseValues[1];
				System.out.println(responseCode);
				JSONObject jsonObj = new JSONObject(responseJSON);

				if (responseCode.equals("200")) {

					sr.setStatus("success");
					sr.setStatusDescription("Perfil Obtenido");
					sr.setIdSubscriber(jsonObj.getJSONObject("responseSubscriber").
							getJSONObject("information").getString("idSubscriber"));
					sr.setImsi(
							jsonObj.getJSONObject("responseSubscriber").
							getJSONObject("information").getString("IMSI"));
					sr.setIcc(
							jsonObj.getJSONObject("responseSubscriber").
							getJSONObject("information").getString("ICCID"));
					sr.setSubStatus(jsonObj.getJSONObject("responseSubscriber").
							getJSONObject("status").
							getString("subStatus"));
					sr.setPrimaryOffering(jsonObj.getJSONObject("responseSubscriber").
							getJSONObject("primaryOffering").getString("offeringId"));
					

				} else if (responseCode.equals("400")) {
					sr.setStatus("error 400");
					sr.setStatusDescription(jsonObj.getString("description"));
					sr.setErrorCode(jsonObj.getString("errorCode"));
					sr.setDescription(jsonObj.getString("description"));
					try{
						sr.setDetail(jsonObj.getString("detail"));
					} catch (JSONException jsonE){
						System.out.println(jsonE.toString());
						for(StackTraceElement ste : jsonE.getStackTrace()){
							System.out.println(ste.toString());
							
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
				sr.setStatus("error 500");
				sr.setStatusDescription("Server error");

			}

		}

		return sr;

	}

	public String sendRequest(String accessToken, String msisdn) {
/*{                
	"responseSubscriber": {                    
		"information":{                        
			"idSubscriber": "1010000992228",                        
			"IMSI":"334140000004708",                        
			"ICCID":"8952140061701559260"                    },                    
			"status":{                        
				"subStatus": "Active"                    },                    
			"primaryOffering":{                        "offeringId": "1001000537"                    },                    
			"freeUnits": [
				{"name":"Free Data",
				"freeUnit": {"totalAmt":"5220","unusedAmt":"5220"},
				"detailOfferings":[{"offeringId":"1001000537","initialAmt":"5220","unusedAmt":"5220","effectiveDate":"20180722050000","expireDate":"20180723050000"}]
				}]                
	}            
} */;
		
		
		URL url;
		HttpURLConnection connection = null;
		try {
                        url = new URL("https://altanredes-test.apigee.net/"
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
