package com.warsztaty.wypozyczalnia;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiController {
    public ApiController(Context context) {
        AppContext = context;
    }
    protected class ApiRequest extends AsyncTask<Void, Integer, JSONObject> {
        ApiRequest(String url, String jsonString) {
            URL = url;
            JsonString = jsonString;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            HttpURLConnection connection = null;
            try {
                Log.d("ApiController", "Attempting to send: " + JsonString);
                URL urlObj = new URL(URL);
                connection = (HttpURLConnection)urlObj.openConnection();

                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setFixedLengthStreamingMode(JsonString.getBytes().length);

                connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                Log.d("ApiController", "Attempting connection");
                connection.connect();

                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                out.write(JsonString.getBytes());
                out.flush();
                out.close();

                JSONObject resp = new JSONObject(ReadResponse(connection.getResponseCode() < HttpStatus.SC_BAD_REQUEST ? connection.getInputStream() : connection.getErrorStream()));

                Log.d("ApiController", "Response: " + resp.toString());

                return resp;
            }
            catch(IOException e) {
                Log.d("ApiController", "Could not connect to server: " + e.toString());
                return null;
            }
            catch(JSONException e) {
                Log.d("ApiController", "Failed to parse JSON: " + e.toString());
                return null;
            }
            finally {
                if(connection != null)
                    connection.disconnect();
            }
        }

        private String URL;
        private String JsonString;
    }

    // Returns JSONObject with the response from the server
    protected void SendPost(String url, JSONObject jsonObj) {
        SendPost(url, jsonObj.toString());
    }
    protected void SendPost(String url, String jsonString) {
        ApiRequest req = new ApiRequest(url, jsonString);
        req.execute();
    }

    private String ReadResponse(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        String out = new String("");
        String tmp;
        while((tmp = reader.readLine()) != null) {
            out += tmp;
        }
        return out;
    }

    protected Context AppContext;
}
