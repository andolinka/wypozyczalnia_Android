package com.warsztaty.wypozyczalnia;

import android.app.ProgressDialog;
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
import android.widget.TextView;
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
public class RentACar extends ActionBarActivity {
    // private Button btnMakeObjectRequest, btnMakeArrayRequest;
    private int id = 0;
    // Progress dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_a_car);

        ApiController controller = new AuthController(this);
        final RentACar thisPage = this;

        id = Integer.valueOf((String)getIntent().getExtras().getSerializable("id"))-1;

        controller.SendRequest(Request.Method.GET, R.string.api_cars, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String res) {
                Log.d("RentACar", "Response: " + res);
                try {

                    thisPage.buildSimpleCarList(new JSONObject(res));
                }
                catch(JSONException e) {
                    Log.d("RentACar", "Could not parse JSON: " + e.getMessage());
                }
            }
        }, new ApiController.GenericErrorListener("RentACar"));

        ListView lista = (ListView)findViewById(R.id.listView3);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RentACar.this, SelectAddress.class);
                String item = (String)parent.getItemAtPosition(position);
                Toast.makeText(RentACar.this, "" + (position + 1), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    }
    public void buildSimpleCarList (JSONObject carsDescription){
        ListView lista = (ListView)findViewById(R.id.listView3);
        List<CarDetailsAdapter.CarData> cars = new ArrayList<CarDetailsAdapter.CarData>();
        try {
            JSONArray results = carsDescription.getJSONArray("results");
            //for(int i = 0; i < results.length(); i++) {
                JSONObject obj = results.getJSONObject(id);

                CarDetailsAdapter.CarData data = new CarDetailsAdapter.CarData(obj);
                cars.add(data);
            //}
        }
        catch(JSONException e) {
            Log.d("RentACar", "Could not parse description due to " + e.getMessage());
        }
        ArrayAdapter adapter = new CarDetailsAdapter(this, R.layout.listview_car, cars);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RentACar.this, SelectAddress.class);
                CarDetailsAdapter.CarData data = (CarDetailsAdapter.CarData)parent.getItemAtPosition(position);
                intent.putExtra("id", data.ID);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rent_a_car, menu);
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
