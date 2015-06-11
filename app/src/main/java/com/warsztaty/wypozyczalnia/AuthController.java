package com.warsztaty.wypozyczalnia;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AuthController extends ApiController {
    public AuthController(Context context) { super(context); }

    public void Login(String username, String password) {
        JSONObject obj = new JSONObject();

        try {
            obj.put("username", username);
            obj.put("password", password);
        }
        catch(JSONException e) {
            Log.d("AuthController", "Could not parse JSON: " + e.toString());
        }

        SendRequest(Request.Method.POST, R.string.api_login, obj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("AuthController", "Logged in: " + response.toString());
                    }
                },
                new GenericErrorListener("AuthController"));
    }

    public void Register(String username, String password, String email) {
        JSONObject obj = new JSONObject();

        try {
            obj.put("username", username);
            obj.put("password", password);
            obj.put("email", email);
        }
        catch(JSONException e) {
            Log.d("AuthController", "Could not parse JSON: " + e.toString());
        }

        SendRequest(Request.Method.POST, R.string.api_register, obj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("AuthController", "Registered: " + response.toString());
                    }
                },
                new GenericErrorListener("AuthController"));
    }



}
