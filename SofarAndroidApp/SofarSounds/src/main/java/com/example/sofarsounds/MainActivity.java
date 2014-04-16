package com.example.sofarsounds;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intial);

        if (savedInstanceState == null) {
            if (!SofarSession.hasValidSession(getApplicationContext())) {
                Intent intent = new Intent(this, InitialActivity.class);
                startActivity(intent);
            } else {
                getFragmentManager().beginTransaction()
                        .add(R.id.initial_container, new HomeFragment())
                        .commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        } else if (id == R.id.action_logout) {
            final SofarSession session = SofarSession.getCurrentSession(getApplicationContext());
            session.close();
            Intent intent = new Intent(this, InitialActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void viewProfile(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

}
