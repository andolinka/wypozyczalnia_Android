package com.warsztaty.wypozyczalnia;

import android.app.DownloadManager;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import java.io.IOException;

public class AuthController extends ApiController {


    public AuthController(Context context) { super(context); }

    public void Login(String username, String password, final Response.Listener<String> listener, Response.ErrorListener errorListener) {
        Map<String, String> obj = new HashMap<String, String>();

        obj.put("username", username);
        obj.put("password", password);


        SendRequest(Request.Method.POST, R.string.api_login, obj, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                listener.onResponse(s);
                try {
                    JSONObject respObj = new JSONObject(s);
                    Log.d("AuthController", "AuthToken: " + respObj.get("authToken"));
                }
                catch(JSONException e) {

                }

            }
        }, errorListener);
    }

    public void Register(String username, String password, String email, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        Map<String, String> obj = new HashMap<String, String>();

        obj.put("username", username);
        obj.put("password", password);
        obj.put("email", email);

        SendRequest(Request.Method.POST, R.string.api_register, obj, listener, errorListener);
    }

    public void AddAddress(String street, String number, String zipcode, String city, String country, Response.Listener<String> listener, Response.ErrorListener errorListener){
        Map<String, String> obj = new HashMap<String, String>();

        obj.put("street", street);
        obj.put("number", number);
        obj.put("zipcode", zipcode);
        obj.put("city", city);
        obj.put("country", country);

        SendRequest(Request.Method.POST, R.string.api_add_address, obj, listener, errorListener);
    }


    public static boolean IsAuthorized() { return AuthToken != null; }
    public static String GetAuthToken() { return AuthToken; }

    private static String AuthToken = null;




}
