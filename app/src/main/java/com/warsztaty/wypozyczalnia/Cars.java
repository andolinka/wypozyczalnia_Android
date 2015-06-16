package com.warsztaty.wypozyczalnia;

import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;


public class Cars extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int categoryId = getIntent().getExtras().getInt("category_id");

        ApiController api = new ApiController(this);
        api.SendRequest(Request.Method.GET, R.string.api_cars, String.valueOf(categoryId), null, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }
        }, new ApiController.GenericErrorListener("Cars"));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cars, menu);
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
}