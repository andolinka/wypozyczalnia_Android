package com.warsztaty.wypozyczalnia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
//TODO:wczytywanie z bazy
        String[] elementy = {"Element 1", "Element 2", "Element 3", "Element 4", "Element 5", "Element 6", "Element 7",
                "Element 8", "Element 9", "Element 10"};

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.elementy_listy_main, R.id.textView29, elementy);

        ListView lista = (ListView)findViewById(R.id.listView);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                Toast.makeText(FirstPage.this, "" + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cathegoryClick(View view){
        Intent intent = new Intent(this, Cathegory.class);
        startActivity(intent);

    }

    public void filterClick(View view){
        Intent intent = new Intent(this, FilterByPreference.class);
        startActivity(intent);

    }

}
