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

import org.apache.log4j.Logger;
import org.json.*;

import com.sapienter.jbilling.common.FormatLogger;

/**
 *
 * @author Arturo Ruiz
 */
public class OAuth {
	private static final FormatLogger LOG = new FormatLogger(
			Logger.getLogger(OAuth.class));

	OAuthResp or = new OAuthResp();

	public OAuthResp getToken(String base64Credentials) {
		try {

			String response = sendRequest(base64Credentials);

			if (response.equals("error")) {
				or.setStatus("error");
				or.setStatusDescription("Error en WS Altan");
			} else {
				String[] responseValues = response.split("\\|");
				String responseCode = responseValues[0];
				String responseJSON = responseValues[1];
				System.out.println(responseCode + " sasdasd" + responseJSON);
				JSONObject jsonObj = new JSONObject(responseJSON);

				if (responseCode.equals("200")) {

					or.setStatus("success");
					or.setStatusDescription("Credenciales correctas");
					or.setAccessToken(jsonObj.getString("accessToken"));
					or.setClientId(jsonObj.getString("clientId"));
					or.setTokenType(jsonObj.getString("tokenType"));
					or.setIssuedAt(jsonObj.getString("issuedAt"));
					or.setExpiresIn(jsonObj.getString("expiresIn"));
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

	private String sendRequest(String base64Credentials) {
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
