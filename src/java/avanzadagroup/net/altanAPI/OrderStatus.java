/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avanzadagroup.net.altanAPI;

import avanzadagroup.net.altanAPI.responses.*;
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
public class OrderStatus {
	private static final FormatLogger LOG = new FormatLogger(
			Logger.getLogger(OAuth.class));
	OrderStatusResponse osr = new OrderStatusResponse();

	public OrderStatusResponse status(String orderId) {

		OAuth oauth = new OAuth();
		
		String response = sendRequest(oauth.getToken().getAccessToken(),
                        orderId);		
                osr.setJsonResponse(response);

		if (response.equals("error")) {
			osr.setStatus("error");
			osr.setStatusDescription("Error en WS Altan");
		} else {
			try {

				LOG.debug("CBOSS::"+response);
				String[] responseValues = response.split("\\|");

				String responseCode = responseValues[0];
				String responseJSON = responseValues[1];
				LOG.debug("CBOSS::"+responseCode);
				JSONObject jsonObj = new JSONObject(responseJSON);
				
				RegisterOperation.write("OrderStatus", responseCode, responseJSON, "");

				if (responseCode.equals("200")) {

					osr.setStatus("success");
					osr.setStatusDescription("OrderStatus");
					osr.setOrderId(jsonObj.getString("orderId"));
					osr.setOrderStatus(jsonObj.getString("status"));
					osr.setType(jsonObj.getString("type"));

				} else if (responseCode.equals("400")) {
					osr.setStatus("error 400");
					osr.setStatusDescription(jsonObj.getString("description"));


				} else if (responseCode.equals("500")) {
					osr.setStatus("error 500");
					osr.setStatusDescription(jsonObj.getString("description"));

				}
			} catch (Exception e) {
				LOG.debug("CBOSS::"+e.toString());
				for(StackTraceElement ste : e.getStackTrace()){
					LOG.debug("CBOSS::"+ste.toString());
					
				}
				osr.setStatus("error 500");
				osr.setStatusDescription("Server error");

			}

		}

		return osr;

	}

	public String sendRequest(String accessToken, String orderId) {
//{
//    "orderId": "107427612",
//    "status": ""
//}
		
		
		URL url;
		HttpURLConnection connection = null;
		try {
			url = new URL("https://altanredes-prod.apigee.net/ac/v1/orders/" + orderId);
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
			LOG.debug("CBOSS::"+e.toString());
			for(StackTraceElement ste: e.getStackTrace()){
				LOG.debug("CBOSS::"+ste.toString());
				
			}

			return "error";
		}

	}

}
