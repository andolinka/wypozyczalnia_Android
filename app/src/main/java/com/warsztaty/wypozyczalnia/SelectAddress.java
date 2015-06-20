package com.warsztaty.wypozyczalnia;

import android.content.Context;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

        Address address = getItem(position);
        ((TextView)view.findViewById(R.id.cityText)).setText(address.City);
        ((TextView)view.findViewById(R.id.countryText)).setText(address.Country);
        ((TextView)view.findViewById(R.id.zipcodeText)).setText(address.ZipCode);
        ((TextView)view.findViewById(R.id.streetText)).setText(address.Street);

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
                    JSONArray array = new JSONObject(res).getJSONArray("results");
                    List<Address> addressesList = new ArrayList<Address>();
                    for(int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Address ad = new Address();
                        ad.City = obj.getString("city");
                        ad.Country = obj.getString("country");
                        ad.ID = obj.getInt("id");
                        ad.ZipCode = obj.getString("zipcode");
                        ad.Street = obj.getString("street");
                        addressesList.add(ad);
                    }
                    addresses.setAdapter(new AddressAdapter(SelectAddress.this, R.layout.listview_address, addressesList));
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

    public void addAddressClick(View view) {
        startActivity(new Intent(this, AddAddress.class));
    }


}
