/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avanzadagroup.net.google;

import avanzadagroup.net.altanAPI.responses.AddressCoordinatesResp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

/**
 *
 * @author Arturo Ruiz
 */
public class AddressCoordinates {

	public AddressCoordinatesResp getCoordinates(String streetName,
			String streetNumber, String zipCode, String city, String state,
			String Country) {
		int intentos = 0;

		AddressCoordinatesResp acr = new AddressCoordinatesResp();
		acr.setStatus("error");

		do {

			URL url;
			HttpURLConnection connection = null;
			try {
				String sURL = "http://maps.google.com/maps/api/geocode/json?"
						+ "address=" + streetName + " " + streetNumber + ", "
						+ city + "," + state + "" + ", " + zipCode + ","
						+ Country;

				url = new URL(sURL.replace(" ", "%20"));
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");

				connection.setRequestProperty("Content-Type",
						"application/json");
				connection
						.setRequestProperty("Accept",
								"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				connection.setRequestProperty("Accept-Language",
						"es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3");
				// connection.setRequestProperty("Accept-Encoding",
				// "gzip, deflate");

				connection.setRequestProperty("Cache-Control", "max-age=0");
				connection
						.setRequestProperty("User-Agent",
								"Mozilla/5.0 (Windows NT 6.1; rv:35.0) Gecko/20100101 Firefox/35.0");
				connection.setRequestProperty("Pragma", "no-cache");

				connection.setDoInput(true);
				connection.setDoOutput(true);

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

				org.json.JSONObject jsonObj = new JSONObject(buf.toString());

				org.json.JSONArray results = jsonObj.getJSONArray("results");

				acr.setStatus(connection.getResponseCode() + "");
				acr.setStatusDescription("Cordenadas Obtenidas");
				acr.setLatitude(results.getJSONObject(0)
						.getJSONObject("geometry").getJSONObject("location")
						.getDouble("lat")
						+ "");
				acr.setLongitude(results.getJSONObject(0)
						.getJSONObject("geometry").getJSONObject("location")
						.getDouble("lng")
						+ "");

			} catch (Exception e) {
				System.out.println(e);
				acr.setStatus("error");
				acr.setStatusDescription("Error al obtener coordenadas");

			}
			intentos++;
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (acr.getStatus().equals("error") && intentos < 10);
		return acr;

	}

}
