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

import org.json.*;

/**
 *
 * @author Arturo Ruiz
 */
public class PagoOnDemand {

    public String sendRequest(String action, Map<String, Object> params) {

 
        URL url;
        HttpURLConnection connection = null;
        try {

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            url = new URL("https://cr.banwire.com/?action="+action);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));

            connection.setDoInput(true);
            connection.setDoOutput(true);

            // Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.write(postDataBytes);
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
