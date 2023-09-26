package org.webrtc.kite.telemed;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIClient {

    public String getToken(String email, String password) {
        String postData = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
        try {
            String response = makePostRequest("https://falgsc.eu.org:8080/login", postData);
            JSONObject jsonObject = new JSONObject(response);

            return jsonObject.getString("token");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int createVisit(String bearerToken, String email) {
        String putData = "{\"visitName\":\"\",\"visitDate\":\"2023-08-10\",\"visitTime\":\"04:44\",\"visitEmail\":\"" + email  + "\"}";
        try {
            String response = makePut("https://falgsc.eu.org:8080/visit", bearerToken , putData);
            JSONObject jsonObject = new JSONObject(response);

            return jsonObject.getInt("visitID");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String makePostRequest(String apiUrl, String postData) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        connection.setRequestProperty("Content-Type", "application/json");

        return readResponse(postData, connection);
    }

    private String makePut(String apiUrl, String bearerToken, String putData) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization","Bearer "+ bearerToken);

        return readResponse(putData, connection);
    }

    private static String readResponse(String putData, HttpURLConnection connection) throws IOException {
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(putData.getBytes());
        outputStream.flush();
        outputStream.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        connection.disconnect();

        return response.toString();
    }
}
