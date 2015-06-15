package com.warsztaty.wypozyczalnia;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import java.io.IOException;

public class AuthController extends ApiController {
    public AuthController(Context context) { super(context); }

    public void Login(String username, String password) {
        Map<String, String> obj = new HashMap<String, String>();

        obj.put("username", username);
        obj.put("password", password);


        SendRequest(Request.Method.POST, R.string.api_login, obj, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("AuthController", "Logged in: " + response.toString());
                    }
                },
                new GenericErrorListener("AuthController"));
    }

    public void Register(String username, String password, String email) {
        Map<String, String> obj = new HashMap<String, String>();

        obj.put("username", username);
        obj.put("password", password);
        obj.put("email", email);

        SendRequest(Request.Method.POST, R.string.api_register, obj, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("AuthController", "Registered: " + response.toString());
                    }
                },
                new GenericErrorListener("AuthController"));
    }




}
