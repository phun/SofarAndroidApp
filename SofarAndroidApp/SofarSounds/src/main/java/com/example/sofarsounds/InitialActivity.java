package com.example.sofarsounds;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.parse.ParseAnalytics;

public class InitialActivity extends Activity implements AttendFragment.OnShowSelectedListener, AttendRequestFragment.OnRequestSubmit{

    private static final String TAG = "InitialActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intial);
        ParseAnalytics.trackAppOpened(this.getIntent());

        if (savedInstanceState == null) {
            if (!SofarSession.hasValidSession(this)) {
                getFragmentManager().beginTransaction()
                        .add(R.id.initial_container, new InitialFragment())
                        .commit();
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.intial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeFragment/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onShowSelected(ShowModel show) {
        getFragmentManager().beginTransaction()
                .replace(R.id.initial_container, AttendRequestFragment.newInstance(show.getCity(), show.getDate()))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onRequestSubmit(String request) {

    }
}
