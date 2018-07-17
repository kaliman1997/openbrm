/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avanzadagroup.net.altanAPI;

import avanzadagroup.net.altanAPI.responses.*;

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
public class OrderStatus {
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

				System.out.println(response);
				String[] responseValues = response.split("\\|");

				String responseCode = responseValues[0];
				String responseJSON = responseValues[1];
				System.out.println(responseCode);
				JSONObject jsonObj = new JSONObject(responseJSON);

				if (responseCode.equals("200")) {

					osr.setStatus("success");
					osr.setStatusDescription("OrderStatus");
					osr.setOrderId(jsonObj.getString("orderId"));
                                        osr.setStatus(jsonObj.getString("status"));

				} else if (responseCode.equals("400")) {
					osr.setStatus("error 400");
					osr.setStatusDescription(jsonObj.getString("description"));


				} else if (responseCode.equals("500")) {
					osr.setStatus("error 500");
					osr.setStatusDescription(jsonObj.getString("description"));

				}
			} catch (Exception e) {
				System.out.println(e.toString());
				for(StackTraceElement ste : e.getStackTrace()){
					System.out.println(ste.toString());
					
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
			System.out.println(e.toString());
			for(StackTraceElement ste: e.getStackTrace()){
				System.out.println(ste.toString());
				
			}

			return "error";
		}

	}

}
