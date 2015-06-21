package com.warsztaty.wypozyczalnia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ewelina on 11.06.15.
 */
public class AddAddress extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_address, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    public void addAddressClick(View view){
        String street = ((EditText)findViewById(R.id.editText)).getText().toString();
        String number = ((EditText)findViewById(R.id.editText2)).getText().toString();
        String zipcode = ((EditText)findViewById(R.id.editText3)).getText().toString();
        String city = ((EditText)findViewById(R.id.editText4)).getText().toString();
        String country = ((EditText)findViewById(R.id.editText5)).getText().toString();

        ApiController controller = new ApiController(this);
//        final Activity thisActivity = this;

        Map<String, String> obj = new HashMap<String, String>();

        obj.put("street", street);
        obj.put("number", number);
        obj.put("zipcode", zipcode);
        obj.put("city", city);
        obj.put("country", country);
        obj.put("user", String.valueOf(AuthController.GetUserID()));

        controller.SendRequest(Request.Method.POST, R.string.api_address, obj, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Intent intent = new Intent();
                intent.putExtra("reload", true);
                setResult(RESULT_OK, intent);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((TextView)findViewById(R.id.invalidAddress)).setText("Wystąpił błąd podczas dodawania nowego adresu.");
                findViewById(R.id.invalidAddress).setVisibility(View.VISIBLE);
                Log.d("AddAddress", "Could not add new address: " + error.toString());
            }
        });
    }

}
