package com.warsztaty.wypozyczalnia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ewelina on 11.06.15.
 */
public class FilterByPreference extends ActionBarActivity {
    public static final int FILTER_RESULTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_by_preference);

        if(getIntent().hasExtra("filters")) {
            Filters = (HashMap<String, String>)getIntent().getExtras().getSerializable("filters");
            fillFiltersControls(Filters);
        }
        else
            Filters = new HashMap<String, String>();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter_by_preference, menu);
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

    public void applyClick(View view){
        Intent ret = new Intent();

        if(applyFilters(Filters, "year_of_production") && applyFilters(Filters, "price_cents") &&
                applyFilters(Filters, "engine_capacity") && applyFilters(Filters, "engine_power")) {
            ret.putExtra("filters", Filters);
            setResult(RESULT_OK, ret);
            finish();
        }
        else {
            View v = findViewById(R.id.filtersError);
            v.setVisibility(View.VISIBLE);
        }



    }

    private boolean applyFilters(Map<String, String> filters, String filterName) {

        List<View> controls = getFiltersControls(filterName);
        return applyFilters((CheckBox)controls.get(0), (EditText)controls.get(1), (EditText)controls.get(2), filters, filterName);
    }
    private boolean applyFilters(CheckBox checkbox, EditText minText, EditText maxText, Map<String, String> filters, String filterName) {
        if(!checkbox.isChecked())
            return true;

        try {
            Integer min = null;
            Integer max = null;

            if(minText.getText().length() > 0) {
                min = Integer.parseInt(minText.getText().toString());
            }

            if(maxText.getText().length() > 0) {
                max = Integer.parseInt(maxText.getText().toString());
            }

            if(min != null && max != null && min.intValue() > max.intValue())
                return false;

            if(min != null)
                filters.put("min_" + filterName, String.valueOf(min));

            if(max != null)
                filters.put("max_" + filterName, String.valueOf(max));
        }
        catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    // Returns controls for a filter in the following order: Checkbox, minimum edit text, maximum edit text
    private List<View> getFiltersControls(String filterName) {
        List<View> ret = new ArrayList<View>();

        ret.add(findViewById(getResources().getIdentifier("checkbox_" + filterName, "id", getPackageName())));
        ret.add(findViewById(getResources().getIdentifier("min_" + filterName, "id", getPackageName())));
        ret.add(findViewById(getResources().getIdentifier("max_" + filterName, "id", getPackageName())));

        return ret;
    }

    private void fillFiltersControls(Map<String, String> filters) {
        for(Map.Entry<String, String> entry : filters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(key.contains("min") || key.contains("max")) {
                String filterName = key.substring(4);
                List<View> controls = getFiltersControls(filterName);

                ((CheckBox)controls.get(0)).setChecked(true);

                int control = 1;
                if(key.contains("max"))
                    control = 2;

                ((EditText)controls.get(control)).setText(value);
            }
        }
    }

    HashMap<String, String> Filters = null;
}
