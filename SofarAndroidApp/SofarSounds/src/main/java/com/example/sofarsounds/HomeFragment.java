package com.example.sofarsounds;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class HomeFragment extends Fragment {
    private SharedPreferences sharedPref;
    private ProfileModel currentUserProfile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);

        final Button attendButton = (Button) rootView.findViewById(R.id.attendButton);
        attendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showAttendScreen();
            }
        });

        Context context = rootView.getContext();
        sharedPref = context.getSharedPreferences(
                getString(R.string.pref_file_key), Context.MODE_PRIVATE);

        final TextView cityText = (TextView) rootView.findViewById(R.id.city);
        final String cityName = sharedPref.getString(getString(R.string.reg_city), "Boston");
        if (cityName != null) {
            cityText.setText(cityName);
        }

        final Button showsButton = (Button) rootView.findViewById(R.id.showsButton);
        showsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showMyShowsScreen();
            }
        });

        final Button profileButton = (Button) rootView.findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showProfileScreen();
            }
        });


        SharedPreferences storage = getProfileStorage(rootView.getContext());
        if (storage.contains("profile_data")) {
            Gson deser = new Gson();
            currentUserProfile = deser.fromJson(storage.getString("profile_data", ""), ProfileModel.class);
        } else {
            new ProfileTask(rootView).execute("http://lucid.scripts.mit.edu/sofar/users/mvanegas/profile");
        }

        return rootView;
    }

    private SharedPreferences getProfileStorage(Context ctx) {
        return ctx.getSharedPreferences(ctx.getString(R.string.profile_preference_id), Context.MODE_PRIVATE);
    }


    private void showAttendScreen() {
        Fragment newFragment = new AttendFragment(currentUserProfile.getShows(), currentUserProfile);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.initial_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showMyShowsScreen() {
//        Fragment newFragment = new MyShowsFragment();
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.initial_container, newFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
        Intent intent = new Intent(getActivity(), ReliveActivity.class);
        startActivity(intent);
    }

    private void showProfileScreen() {
        Fragment newFragment = ProfileFragment.newInstance(currentUserProfile.getName(), currentUserProfile.getHomeCity(), currentUserProfile.getProfilePic());
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.initial_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private class ProfileTask extends AsyncTask<String, Void, String> {
        private final View rootView;
        private ProfileTask(View rootView) {
            this.rootView = rootView;
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpGet get = new HttpGet(urls[0]);
                get.setHeader("Accept", "application/json");
                HttpResponse response = httpclient.execute(get);
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                // json is UTF-8 by default
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                Scanner s = new Scanner(reader).useDelimiter("\\A");
                String result = s.hasNext() ? s.next() : "";
                inputStream.close();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onPostExecute(String result) {
            Gson gson = new Gson();
            ProfileModel profile = gson.fromJson(result, ProfileModel.class);
            currentUserProfile = profile;
            SharedPreferences storage = getProfileStorage(rootView.getContext());
            SharedPreferences.Editor editor = storage.edit();
            editor.putString("profile_data", result);
        }
    }
}
