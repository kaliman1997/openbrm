/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avanzadagroup.net.altanAPI;

import avanzadagroup.net.altanAPI.responses.BatchResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.*;

/**
 *
 * @author Arturo Ruiz
 */
public class Batch {

	BatchResponse ar = new BatchResponse();

    public BatchResponse activate(String pathToFile, String operation) {

        OAuth oauth = new OAuth();

        String response = uploadFile(oauth.getToken().getAccessToken(), pathToFile, operation);
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
                    ar.setEffectiveDate(jsonObj.getString("effectiveDate"));
                    ar.setLines(jsonObj.getString("lines"));
                    ar.setTransactionId(jsonObj.getJSONObject("transaction").
                    		getString("id"));

                } else if (responseCode.equals("400")) {
                    ar.setStatus("error 400");
                    ar.setStatusDescription(jsonObj.getString("description"));
                    ar.setErrorCode(jsonObj.getString("errorCode"));
                    ar.setDescription(jsonObj.getString("description"));
                    

                } else if (responseCode.equals("500")) {
                    ar.setStatus("error 500");
                    ar.setStatusDescription(jsonObj.getString("description"));
                    ar.setErrorCode(jsonObj.getString("errorCode"));
                    ar.setDescription(jsonObj.getString("description"));
                    
                }
            } catch (Exception e) {
                System.out.println(e.toString());
                for (StackTraceElement ste : e.getStackTrace()) {
                    System.out.println(ste.toString());

                }
                ar.setStatus("error 500");
                ar.setStatusDescription("Server error");

            }

        }

        return ar;

    }



    private String uploadFile(String accessToken, String pathToFile, String operation) {
        try {
            String url = "https://altanredes-test.apigee.net/"
                    + "cm/v1/subscribers/" + operation;
            
            System.out.println(url);
            System.out.println(pathToFile);
            String charset = "UTF-8";
            String param = "value";
            File textFile = new File(pathToFile);
            
            String boundary ="-----" + Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
            String CRLF = "\r\n"; // Line separator required by multipart/form-data.

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("authorization", "Bearer " + accessToken);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try (
                    OutputStream output = connection.getOutputStream();
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);) {
      
                // Send text file.
                writer.append("--"+boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"archivos[]\"; filename=\"" + textFile.getName() + "\"").append(CRLF);
                writer.append("Content-Type: text/plain").append(CRLF); // Text file itself must be saved in this charset!
                writer.append(CRLF).flush();
                Files.copy(textFile.toPath(), output);
                output.flush(); // Important before continuing with writer!
                writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

                // End of multipart/form-data.
                writer.append("--"+boundary+"--").append(CRLF).flush();
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
            for (StackTraceElement ste : e.getStackTrace()) {
                System.out.println(ste.toString());

            }

            return "error";
        }
    }

}
