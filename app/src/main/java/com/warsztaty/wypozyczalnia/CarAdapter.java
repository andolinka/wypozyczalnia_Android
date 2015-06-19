package com.warsztaty.wypozyczalnia;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Maverick on 2015-06-19.
 */

public class CarAdapter extends ArrayAdapter<CarAdapter.CarData> {
    static public class CarData {
        public CarData() {

        }
        public CarData(JSONObject obj) throws JSONException {
            ID = obj.getInt("id");
            Description = obj.getString("description");
            Available = obj.getBoolean("available");
            ImageURL = obj.getString("image");
        }
        public int ID;
        public String Description;
        public String ImageURL;
        public boolean Available;
    }

    public CarAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CarAdapter(Context context, int resource, List<CarData> items) {
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

        CarAdapter.CarData data = getItem(position);

        if (data != null) {
            TextView carName = (TextView)view.findViewById(R.id.carName);

            carName.setText(data.Description);

            NetworkImageView img = (NetworkImageView)view.findViewById(R.id.carImage);
            img.setImageUrl(data.ImageURL, ApiController.GetImageLoader(getContext()));
        }

        return view;
    }

    private int LayoutID;


}
