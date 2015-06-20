package com.warsztaty.wypozyczalnia;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class AuthController extends ApiController {


    public AuthController(Context context) { super(context); }

    public void Login(String username, String password, final Response.Listener<String> listener, Response.ErrorListener errorListener) {
        Map<String, String> obj = new HashMap<String, String>();

        obj.put("username", username);
        obj.put("password", password);


        SendRequest(Request.Method.POST, R.string.api_login, obj, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject respObj = new JSONObject(s);
                    AuthToken = respObj.get("auth_token").toString();
                    Log.d("AuthController", "AuthToken: " + AuthToken);
                }
                catch(JSONException e) {
                    Log.d("AuthController", "Could not parse JSON");
                    return;
                }
                listener.onResponse(s);

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

    /*public void AddAddress(String street, String number, String zipcode, String city, String country, Response.Listener<String> listener, Response.ErrorListener errorListener){
        Map<String, String> obj = new HashMap<String, String>();

        obj.put("street", street);
        obj.put("number", number);
        obj.put("zipcode", zipcode);
        obj.put("city", city);
        obj.put("country", country);

        SendRequest(Request.Method.POST, R.string.api_address, obj, listener, errorListener);
    }*/


    public static boolean IsAuthorized() { return AuthToken != null; }
    public static String GetAuthToken() { return AuthToken; }

    private static String AuthToken = null;




}
