/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avanzadagroup.net.altanAPI;

import avanzadagroup.net.altanAPI.responses.OAuthResp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import org.json.*;

/**
 *
 * @author Arturo Ruiz
 */
public class OAuth {
//	private static final FormatLogger LOG = new FormatLogger(
//			Logger.getLogger(OAuth.class));
	private final String base64Credentials = 
			"aGhZbU1BbUJtR3FraHJrN3h5N21KNGh2UEt0SklpMUU6b3FHUFcwcnRkZ255Mm45Ug==";
	

	private static class AccessToken{
		private static String accessToken;
		private static Calendar tokenAdquireTime;
		private static Integer expiresIn;
		
		public static String getAccessToken() {
			return accessToken;
		}
		public static void setAccessToken(String accessToken) {
			AccessToken.accessToken = accessToken;
		}
		public static Calendar getTokenAdquireTime() {
			return tokenAdquireTime;
		}
		public static void setTokenAdquireTime(Long tokenAdquireTime) {
			AccessToken.tokenAdquireTime = Calendar.getInstance();
			AccessToken.tokenAdquireTime.setTimeInMillis(tokenAdquireTime);
		}
		public static Integer getExpiresIn() {
			return expiresIn;
		}
		public static void setExpiresIn(String expiresIn) {
			AccessToken.expiresIn = new Integer(expiresIn);
		}

		
		
	}

	OAuthResp or = new OAuthResp();

	public OAuthResp getToken() {
		try {
			System.out.println("Oauth::" + AccessToken.getAccessToken());
			
			if(AccessToken.getAccessToken()!= null && (Calendar.getInstance().getTimeInMillis()/1000 < 
					((AccessToken.getTokenAdquireTime().getTimeInMillis() /1000 ) + 
							AccessToken.getExpiresIn() - 600) 
					)){
				
				System.out.println("Oauth:: no genera");
				or.setStatus("success");
				or.setStatusDescription("Credenciales correctas");		
				
				or.setAccessToken(AccessToken.getAccessToken());	
				return or;
				
			}
			

			String response = sendRequest(base64Credentials);
			
			System.out.println("Oauth::" + response);

			if (response.equals("error")) {
				or.setStatus("error");
				or.setStatusDescription("Error en WS Altan");
			} else {
				String[] responseValues = response.split("\\|");
				String responseCode = responseValues[0];
				String responseJSON = responseValues[1];
				System.out.println(responseCode + " Oauth" + responseJSON);
				JSONObject jsonObj = new JSONObject(responseJSON);

				if (responseCode.equals("200")) {

					or.setStatus("success");
					or.setStatusDescription("Credenciales correctas");
					
					
					or.setAccessToken(jsonObj.getString("accessToken"));
					AccessToken.setAccessToken(or.getAccessToken());
					
					or.setClientId(jsonObj.getString("clientId"));
					or.setTokenType(jsonObj.getString("tokenType"));
					
					or.setIssuedAt(jsonObj.getString("issuedAt"));
					AccessToken.setTokenAdquireTime(					
									new Long(or.getIssuedAt()));
					
					or.setExpiresIn(jsonObj.getString("expiresIn"));					
					AccessToken.setExpiresIn(or.getExpiresIn());
					
					or.setStatus(jsonObj.getString("status"));
					or.setScopes(jsonObj.getString("scopes"));

				} else if (responseCode.equals("401")) {
					or.setStatus("error 401");
					or.setStatusDescription(jsonObj.getString("description"));
					or.setErrorCode(jsonObj.getString("errorCode"));
					or.setSummary(jsonObj.getString("summary"));
					or.setDescription(jsonObj.getString("description"));

				} else if (responseCode.equals("500")) {
					or.setStatus("error 500");
					or.setStatusDescription("Server Error");
				}
			}

		} catch (Exception ex) {
			or.setStatus("error 500");
			or.setStatusDescription("Server Error");
		}

		return or;

	}

	public String sendRequest(String base64Credentials) {
		URL url;
		HttpURLConnection connection = null;
		try {
			url = new URL("https://altanredes-prod.apigee.net/v1/oauth/"
					+ "accesstoken?grant-type=client_credentials");
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Basic "
					+ base64Credentials);

			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.flush();
			wr.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			String inputLine;

			StringBuilder buf = new StringBuilder();
			while ((inputLine = br.readLine()) != null) {
				buf.append(inputLine);

			}

			System.out.println(buf.toString());

			br.close();
			connection.disconnect();

			return connection.getResponseCode() + "|" + buf.toString();
		} catch (Exception e) {

			System.out.println(e);
			return "error";
		}

	}// registro

}
