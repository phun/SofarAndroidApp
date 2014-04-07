package com.example.sofarsounds;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Profile extends ActionBarActivity {

  //  JSONObject jObject = new JSONObject(result);

    public static String name;
    public static String homeCity;
    public static String interested;
    public static String registered;
    public static String waitlisted;
    public static String shows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpPost httppost = new HttpPost("http://lucid.scripts.mit.edu/sofar/users/mvanegas/profile");
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;

        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            //

        }
        finally {
            try{if(inputStream != null)inputStream.close();}catch(Exception squish){}

        }

        try {
            JSONObject jObject = new JSONObject(result);


            name = jObject.getString("Name");
            ((TextView) findViewById(R.id.profileName)).setText(name);
            homeCity = jObject.getString("HomeCity");
            ((TextView) findViewById(R.id.profileHomeCity)).setText(homeCity);
            interested = jObject.getString("Interested");
            ((TextView) findViewById(R.id.profileInterested)).setText(interested);
            registered = jObject.getString("Registered");
            ((TextView) findViewById(R.id.profileRegistered)).setText(registered);
            waitlisted = jObject.getString("Waitlisted");
            ((TextView) findViewById(R.id.profileWaitlisted)).setText(waitlisted);
            shows = jObject.getString("Shows");
            ((TextView) findViewById(R.id.profileShows)).setText(shows);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
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

}
