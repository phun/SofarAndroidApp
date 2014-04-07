package com.example.sofarsounds;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ProfileFragment extends Fragment {
    private class ProfileTask extends AsyncTask<String, Void, JSONObject> {
        private final Map<String, TextView> fields;
        private ProfileTask(Map<String, TextView> fields) {
            this.fields = fields;
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            try {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpGet get = new HttpGet(urls[0]);
                get.setHeader("Accept", "application/json");
                HttpResponse response = httpclient.execute(get);
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                // json is UTF-8 by default
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString();
                JSONObject jObject = new JSONObject(result);
                return jObject;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onPostExecute(JSONObject result) {
            for (Map.Entry<String, TextView> entry : fields.entrySet()) {
                TextView view = entry.getValue();
                String jsonKey = entry.getKey();
                try {
                    view.setText(result.getString(jsonKey));
                } catch (JSONException je) {
                    Log.e("Profile", "Missing JSON key: " + jsonKey, je);
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        Map<String, TextView> fields = new HashMap<String, TextView>(){{
            put("Name", (TextView) rootView.findViewById(R.id.profileName));
            put("HomeCity", (TextView) rootView.findViewById(R.id.profileHomeCity));
            put("Interested", (TextView) rootView.findViewById(R.id.profileInterested));
            put("Registered", (TextView) rootView.findViewById(R.id.profileRegistered));
            put("Waitlisted", (TextView) rootView.findViewById(R.id.profileWaitlisted));
            put("Shows", (TextView) rootView.findViewById(R.id.profileShows));
        }};
        new ProfileTask(fields).execute("http://lucid.scripts.mit.edu/sofar/users/mvanegas/profile");
        return rootView;
    }
}