package com.warsztaty.wypozyczalnia;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AuthController extends ApiController {
    public AuthController(Context context) { super(context); }

    public void Login(String username, String password) {
        SendAuthPost(AppContext.getResources().getString(R.string.api_path) + AppContext.getResources().getString(R.string.api_login),
                username, password);
    }

    public void Register(String username, String password) {
        SendAuthPost(AppContext.getResources().getString(R.string.api_path) + AppContext.getResources().getString(R.string.api_register),
                username, password);
    }

    private void SendAuthPost(String path, String username, String password) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("userName", username);
            obj.put("authorizationKey", password);
            SendPost(path, obj);
        }
        catch(JSONException e) {
            Log.d("AuthController", "Could not parse JSON: " + e.toString());
        }
    }


}
