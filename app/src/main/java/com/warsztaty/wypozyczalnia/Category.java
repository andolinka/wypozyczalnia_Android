package com.warsztaty.wypozyczalnia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by ewelina on 11.06.15.
 */
public class Category extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ApiController controller = new ApiController(this);

        final Category thisActivity = this;
        controller.SendRequest(Request.Method.GET, R.string.api_car_categories, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String res) {
                Log.d("Category", "Response: " + res);
                try {
                    thisActivity.BuildCategoriesList(new JSONObject(res));
                }
                catch(JSONException e) {
                    Log.d("Category", "Could not parse JSON: " + e.getMessage());
                }
            }
        }, new ApiController.GenericErrorListener("Category"));


        ListView categoryList = (ListView)findViewById(R.id.categoryList);
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Category.this, Cars.class);
                String item = (String)parent.getItemAtPosition(position);
                HashMap<String, String> filters = new HashMap<String, String>();
                filters.put("car_category", String.valueOf(position + 1));
                intent.putExtra("filters", filters);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);


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


    public void BuildCategoriesList(JSONObject categories) {
        ListView categoryList = (ListView)findViewById(R.id.categoryList);
        List<String> categoriesStrings = new ArrayList<String>();
        try {
            JSONArray results = categories.getJSONArray("results");

            for(int i = 0; i < results.length(); i++) {
                JSONObject obj = results.getJSONObject(i);

                categoriesStrings.add(obj.getString("name"));
            }
        }
        catch(JSONException e) {
            Log.d("Category", "Could not parse categories due to " + e.getMessage());
        }
        categoryList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoriesStrings));
    }

}
