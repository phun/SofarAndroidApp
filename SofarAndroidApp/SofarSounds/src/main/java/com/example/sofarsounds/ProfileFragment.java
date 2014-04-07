package com.example.sofarsounds;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
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


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    class RetrieveProfileTask extends AsyncTask<URL, Void, JSONObject> {
        private Exception exception;

        View view;
        public RetrieveProfileTask(View view) {
            this.view = view;
        }
        protected JSONObject doInBackground(URL... url) {
            DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
            HttpGet httpGet = new HttpGet("http://lucid.scripts.mit.edu/sofar/users/mvanegas/profile");
            InputStream inputStream = null;
            String result = null;
            JSONObject jObject;

            try {
                HttpResponse response = httpclient.execute(httpGet);
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
                e.printStackTrace();

            }
            finally {
                try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
            }
            try {
                jObject = new JSONObject(result);
                return jObject;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void OnPostExecute(JSONObject jObject){

            try {
                String name = jObject.getString("Name");
                String homeCity = jObject.getString("HomeCity");
                String interested = jObject.getString("Interested");
                String registered = jObject.getString("Registered");
                String waitlisted = jObject.getString("Waitlisted");
                String shows = jObject.getString("Shows");
                ((TextView) this.view.findViewById(R.id.profileName)).setText(name);
                ((TextView) this.view.findViewById(R.id.profileHomeCity)).setText(homeCity);
                ((TextView) this.view.findViewById(R.id.profileInterested)).setText(interested);
                ((TextView) this.view.findViewById(R.id.profileRegistered)).setText(registered);
                ((TextView) this.view.findViewById(R.id.profileWaitlisted)).setText(waitlisted);
                ((TextView) this.view.findViewById(R.id.profileShows)).setText(shows);
            } catch (JSONException e) {
                e.printStackTrace();
            }

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

// Depends on your web service
 //       httpGet.setHeader("Accept-type", "application/json");
        View V = inflater.inflate(R.layout.fragment_profile, container, false);
        new RetrieveProfileTask(V).execute();
        return V;
    }
    }}


