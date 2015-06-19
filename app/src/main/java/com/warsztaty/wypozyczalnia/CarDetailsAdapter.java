package com.warsztaty.wypozyczalnia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Maverick on 2015-06-19.
 */

public class CarDetailsAdapter extends ArrayAdapter<CarDetailsAdapter.CarData> {
    static public class CarData {
        public CarData() {

        }
        public CarData(JSONObject obj) throws JSONException {
            ID = obj.getInt("id");
            Description = obj.getString("description");
            Available = obj.getBoolean("available");
            ImageURL = obj.getString("image");
            Price = obj.getString("price_cents");
            Marka = obj.getString("manufacturer");
            VIN = obj.getString("model");
            PowerEngine = obj.getString("engine_power");
            CapacityEngine = obj.getString("engine_capacity");
        }
        public int ID;
        public String Description;
        public String ImageURL;
        public boolean Available;
        public String Price;
        public String Marka;
        public String VIN;
        public String PowerEngine;
        public String CapacityEngine;
    }

    public CarDetailsAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CarDetailsAdapter(Context context, int resource, List<CarData> items) {
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

        CarDetailsAdapter.CarData data = getItem(position);

        if (data != null) {
            TextView carName = (TextView)view.findViewById(R.id.nazwaAuta);
            carName.setText(data.Marka);

            TextView carDescription = (TextView)view.findViewById(R.id.opisAuta);
            carDescription.setText(data.Description);

            TextView priceCar = (TextView)view.findViewById(R.id.cenaAuta1);
            priceCar.setText(data.Price);

            TextView vin = (TextView)view.findViewById(R.id.VINAuta);
            vin.setText(data.VIN);

            TextView capacityEngine = (TextView)view.findViewById(R.id.pojemnoscSilnika);
            capacityEngine.setText(data.CapacityEngine);

            TextView powerEngine = (TextView)view.findViewById(R.id.mocAuta);
            powerEngine.setText(data.PowerEngine);

            if (data.Available==true){
                TextView avilable = (TextView)view.findViewById(R.id.dostepnoscAuta);
                avilable.setText(" dostępny.");
                TextView click = (TextView)view.findViewById(R.id.klikajka);
                click.setText("Kliknij, aby wypożyczyć.");

            }
            else {
                TextView avilable = (TextView)view.findViewById(R.id.dostepnoscAuta);
                avilable.setText(" niedostępny.");
                TextView click = (TextView)view.findViewById(R.id.klikajka);
                click.setText("Kliknij, aby wrócić do wyszukiwania.");
            }

            NetworkImageView img = (NetworkImageView)view.findViewById(R.id.obrazAuta);
            img.setImageUrl(data.ImageURL, ApiController.GetImageLoader(getContext()));
        }

        return view;
    }

    private int LayoutID;

}
