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

import jline.internal.Log;

import org.apache.log4j.Logger;
import org.json.*;

import avanzadagroup.net.plugins.InvoiceBarCodeTask;

import com.sapienter.jbilling.common.FormatLogger;

/**
 *
 * @author Arturo Ruiz
 */
public class Oxxo {
	private static final FormatLogger LOG = new FormatLogger(
			Logger.getLogger(InvoiceBarCodeTask.class));	

    public String sendRequest(Map<String, Object> params) {
        

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

            url = new URL("https://test.banwire.com/api.oxxo");
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

            LOG.debug("CBOSS:: banwire_oxxo " + buf.toString());

            br.close();
            connection.disconnect();

            return connection.getResponseCode() + "|" + buf.toString();
        } catch (Exception e) {
        	LOG.debug("CBOSS:: banwire_oxxo Exception");
            LOG.debug(e);
            return "error";
        }

    }// registro

}
