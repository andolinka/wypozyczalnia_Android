package com.warsztaty.wypozyczalnia;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ewelina on 11.06.15.
 */

class Address {
    public int ID;
    public String Street;
    public String City;
    public String Country;
    public String ZipCode;
}

class AddressAdapter extends ArrayAdapter<Address> {


    public AddressAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public AddressAdapter(Context context, int resource, List<Address> items) {
        super(context, resource, items);
        LayoutID = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inf;
            inf = LayoutInflater.from(getContext());
            view = inf.inflate(LayoutID, null);
        }



        return view;
    }

    private int LayoutID;


}

public class SelectAddress extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);

        final ListView addresses = (ListView)findViewById(R.id.addressesList);

        ApiController api = new ApiController(this);

        api.SendRequest(Request.Method.GET, R.string.api_address, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String res) {
                try {
                    JSONObject obj = new JSONObject(res);
                    Log.d("SelectAddress", res);

                    //addresses.setAdapter(new AddressAdapter(this, R.layout.listview_address, null));
                }
                catch(JSONException e) {
                    Log.d("SelectAddress", "Could not parse a JSON obj");
                }
            }
        }, new ApiController.GenericErrorListener("SelectAddress"));


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_address, menu);
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


}
