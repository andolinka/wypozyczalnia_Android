package com.warsztaty.wypozyczalnia;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

/**
 * Created by Maverick on 2015-06-21.
 */
public class AuthorizedActivity extends ActionBarActivity {
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if(id == R.id.action_logout) {
            if(AuthController.IsAuthorized()) {
                AuthController auth = new AuthController(this);
                auth.Logout(null, null);
                startActivity(new Intent(this, MainPage.class));
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
