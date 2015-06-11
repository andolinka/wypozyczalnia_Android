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

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

public class ApiController {
    public ApiController(Context context) {
        AppContext = context;
    }

    public void SendRequest(int method, int apiUrlResourceId, JSONObject request, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        SendRequest(method, AppContext.getResources().getString(apiUrlResourceId), request, listener, errorListener);
    }
    public void SendRequest(int method, String apiUrl, JSONObject request, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        RequestQueue queue = GetQueue(AppContext);

        JsonObjectRequest req = new JsonObjectRequest(method, AppContext.getResources().getString(R.string.api_path) + apiUrl, request, listener, errorListener);

        Log.d("ApiController", "Sending request: " + request.toString());
        queue.add(req);
    }

    static protected RequestQueue GetQueue(Context context) {
        if(ApiQueue == null) {
            Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());

            ApiQueue = new RequestQueue(cache, network);

            ApiQueue.start();
        }

        return ApiQueue;
    }

    protected class GenericErrorListener implements Response.ErrorListener {
        GenericErrorListener(String tag) {
            Tag = tag;
        }
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(Tag, "Failed to connect due to " + error.networkResponse.statusCode);
        }

        private String Tag;
    }
    protected Context AppContext;

    static private RequestQueue ApiQueue = null;
}
