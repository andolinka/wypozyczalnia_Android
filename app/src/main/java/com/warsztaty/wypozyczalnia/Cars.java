package com.warsztaty.wypozyczalnia;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Cars extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Filters = (HashMap<String,String>)getIntent().getExtras().getSerializable("filters");
        GetCars(Filters);

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
        if (id == R.id.action_filter_by_preferences) {
            Intent intent = new Intent(this, FilterByPreference.class);
            intent.putExtra("filters", Filters);
            startActivityForResult(intent, FilterByPreference.FILTER_RESULTS);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FilterByPreference.FILTER_RESULTS) {
            if(resultCode == RESULT_OK){
                Filters = (HashMap<String,String>)data.getExtras().getSerializable("filters");
                GetCars(Filters);
            }
        }
    }

    private void GetCars(Map<String, String> filters) {
        ApiController api = new ApiController(this);
        Log.d("Cars", "Using filters: " + filters.entrySet().toString());
        api.SendRequest(Request.Method.GET, R.string.api_cars, filters, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("Cars", "Cars: " + s);
                try {
                    JSONObject obj = new JSONObject(s);
                    Cars.this.BuildCarsList(obj);
                } catch (JSONException e) {
                    Log.d("Cars", "Could not parse JSON with requested cars: " + s);
                }
            }
        }, new ApiController.GenericErrorListener("Cars"));
    }

    private void BuildCarsList(JSONObject obj) {
        CarsList = obj;

        List<String> cars = new ArrayList<String>();

        try {
            JSONArray array = obj.getJSONArray("results");

            if(array.length() == 0) {
                findViewById(R.id.noresultsCars).setVisibility(View.VISIBLE);
            }
            else {
                findViewById(R.id.noresultsCars).setVisibility(View.INVISIBLE);
                for(int i = 0; i < array.length(); i++) {
                    cars.add(array.getJSONObject(i).getString("description"));
                }
            }

        }
        catch(JSONException e) {
            Log.d("Cars", "Failed to build car list");
            return;
        }

        ListView carsList = (ListView)findViewById(R.id.carsList);

        carsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cars));

        carsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Cars.this, RentACar.class);
                startActivity(intent);
            }
        });
    }

    private HashMap<String, String> Filters = null;
    private JSONObject CarsList = null;
}
