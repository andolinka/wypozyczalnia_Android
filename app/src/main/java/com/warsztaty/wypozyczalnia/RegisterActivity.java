package com.warsztaty.wypozyczalnia;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends ActionBarActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void registerClick(View view) {
        String email = ((EditText)findViewById(R.id.emailText)).getText().toString();
        String username =  ((EditText)findViewById(R.id.usernameText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordText)).getText().toString();
        String rptPassword = ((EditText)findViewById(R.id.rptPasswordText)).getText().toString();

        if(!password.equals(rptPassword))
        {
            ((TextView)findViewById(R.id.registerError)).setText("Hasła się nie zgadzają");
            Log.d(TAG, "Passwords do not match: " + password + " " + rptPassword);
            findViewById(R.id.registerError).setVisibility(View.VISIBLE);
            // Do something here
            return;
        }

        AuthController controller = new AuthController(this);
        controller.Register(username, password, email, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                Log.d("RegisterActivity", "Register response: " + s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((TextView)findViewById(R.id.registerError)).setText("Wystąpił błąd rejestracji");
                findViewById(R.id.registerError).setVisibility(View.VISIBLE);
                Log.d("RegisterActivity", "Could not register: " + error.toString());
            }
        });
    }
}
