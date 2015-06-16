package com.warsztaty.wypozyczalnia;


import android.content.Context;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

public class ApiController {
    public ApiController(Context context) {
        AppContext = context;
    }

    public void SendRequest(int method, int apiUrlResourceId, String urlParameter, Map<String, String> requestParams, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        SendRequest(method, AppContext.getResources().getString(apiUrlResourceId) + urlParameter + "/", requestParams, listener, errorListener);
    }

    public void SendRequest(int method, int apiUrlResourceId, Map<String, String> requestParams, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        SendRequest(method, AppContext.getResources().getString(apiUrlResourceId), requestParams, listener, errorListener);
    }
    public void SendRequest(int method, String apiUrl, Map<String, String> requestParams, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        RequestQueue queue = GetQueue(AppContext);

        String fullURL = AppContext.getResources().getString(R.string.api_path) + apiUrl;
        if(method == Request.Method.GET && requestParams != null && requestParams.size() > 0) {
            fullURL += "?";
            for(Map.Entry<String, String> entry : requestParams.entrySet()) {
                fullURL += String.format("%s=%s", entry.getKey(), entry.getValue());
                fullURL += "&";
            }
            requestParams = null;
        }

        final Map<String, String> finalParameters = requestParams;

        StringRequest req = new StringRequest(method, fullURL, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> pars = new HashMap<String, String>();
                pars.put("Content-Type", "application/x-www-form-urlencoded");
                if(AuthController.IsAuthorized())
                    pars.put("Authorization", "Token " + AuthController.GetAuthToken());
                return pars;
            }

            @Override
            public Map<String, String> getParams(){
                return finalParameters;
            }

            @Override
            protected Response<String> parseNetworkResponse (NetworkResponse response) {
                try {
                    String respString = new String(response.data, "UTF-8");
                    return Response.success(respString, HttpHeaderParser.parseCacheHeaders(response));
                }
                catch(UnsupportedEncodingException e) {
                    Log.d("ApiController", "Wrong encoding of response data, reverting to default response");
                    return super.parseNetworkResponse(response);
                }

            }
        };

        Log.d("ApiController", "Sending a request to: " + fullURL + " with params: " + (requestParams != null ? requestParams.toString() : ""));
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

    static public class GenericErrorListener implements Response.ErrorListener {
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
