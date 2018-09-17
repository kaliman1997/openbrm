/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avanzadagroup.net.banwire;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.json.*;

import avanzadagroup.net.altanAPI.OAuth;

import com.sapienter.jbilling.common.FormatLogger;

/**
 *
 * @author Arturo Ruiz
 */
public class Store {
	private static final FormatLogger LOG = new FormatLogger(
			Logger.getLogger(OAuth.class));

    public String sendRequest(String body) {
        

        URL url;
        HttpURLConnection connection = null;
        try {



            url = new URL("https://test.banwire.com/api/1/payment/store");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Content-Length", String.valueOf(body.length()));
            
            connection.setDoInput(true);
            connection.setDoOutput(true);
            
            //body = URLEncoder.encode(body , "UTF-8");
            
            Log.debug("CBOSS:: store " + body);

            // Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.write(body.getBytes());
            wr.flush();
            wr.close();
            
            BufferedReader br;

            if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
            	br = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
            } else {
            	br = new BufferedReader(new InputStreamReader(
                        connection.getErrorStream()));
            }

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

            LOG.debug(e);
            return "error";
        }

    }// registro

}
