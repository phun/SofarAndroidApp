package com.example.sofarsounds;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class InitialActivity extends Activity implements AttendFragment.OnShowSelectedListener, AttendRequestFragment.OnRequestSubmit{

    private static final String TAG = "InitialActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intial);

        if (savedInstanceState == null) {

            if (!SofarSession.hasValidSession(this)) {
                getFragmentManager().beginTransaction()
                        .add(R.id.initial_container, new MainFragment())
                        .commit();
            } else {
                getFragmentManager().beginTransaction()
                        .add(R.id.initial_container, new Home())
                        .commit();
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
        // automatically handle clicks on the Home/Up button, so long
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
                .add(R.id.initial_container, AttendRequestFragment.newInstance(show.getCity(), show.getDate()))
                .commit();
    }

    @Override
    public void onRequestSubmit(String request) {

    }
}
