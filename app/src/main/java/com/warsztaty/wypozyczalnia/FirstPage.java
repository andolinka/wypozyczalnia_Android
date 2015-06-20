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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ewelina on 11.06.15.
 */
public class FirstPage extends ActionBarActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first_page, menu);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        ApiController controller = new AuthController(this);
        final FirstPage thisPage = this;

        controller.SendRequest(Request.Method.GET, R.string.api_cars, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String res) {
                Log.d("FirstPage", "Response: " + res);
                try {

                    thisPage.buildSimpleCarList(new JSONObject(res));
                }
                catch(JSONException e) {
                    Log.d("FirstPage", "Could not parse JSON: " + e.getMessage());
                }
            }
        }, new ApiController.GenericErrorListener("Category"));

//        ListView lista = (ListView)findViewById(R.id.listView);
//        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(FirstPage.this, RentACar.class);
//                String item = (String)parent.getItemAtPosition(position);
//                Toast.makeText(FirstPage.this, "" + (position + 1), Toast.LENGTH_SHORT).show();
//                startActivity(intent);
//            }
//        });

    }
    public void buildSimpleCarList (JSONObject carsDescription){
        ListView lista = (ListView)findViewById(R.id.listView);
        List<CarAdapter.CarData> cars = new ArrayList<CarAdapter.CarData>();
        try {
            JSONArray results = carsDescription.getJSONArray("results");

            for(int i = 0; i < results.length(); i++) {
                JSONObject obj = results.getJSONObject(i);

                CarAdapter.CarData data = new CarAdapter.CarData(obj);
                cars.add(data);
            }
        }
        catch(JSONException e) {
            Log.d("FirstPage", "Could not parse description due to " + e.getMessage());
        }
        ArrayAdapter adapter = new CarAdapter(this, R.layout.elementy_listy_main, cars);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FirstPage.this, RentACar.class);
                CarAdapter.CarData data = (CarAdapter.CarData)parent.getItemAtPosition(position);
                intent.putExtra("id", String.valueOf(data.ID));
                startActivity(intent);
            }
        });

    }

    public void cathegoryClick(View view){
        Intent intent = new Intent(FirstPage.this, Category.class);
        startActivity(intent);

    }

    public void filterClick(View view){
        Intent intent = new Intent(FirstPage.this, FilterByPreference.class);
        startActivity(intent);

    }

    public void addressClick(View view) {
        Intent intent = new Intent(this, AddAddress.class);
        startActivity(intent);
    }

}
