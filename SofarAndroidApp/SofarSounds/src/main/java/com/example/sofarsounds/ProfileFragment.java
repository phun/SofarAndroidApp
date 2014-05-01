package com.example.sofarsounds;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Base64;
import android.util.Base64DataException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.parse.ParseAnalytics;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sofarsounds.R.*;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ProfileFragment extends Fragment {
    private String name;
    private String homeCity;
    private Bitmap profilePic;
    public static  ProfileFragment newInstance(String name, String homeCity, String profilePic) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("homeCity", homeCity);
        args.putString("profilePic", profilePic);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseAnalytics.trackEvent("ProfileFragment");
        if (getArguments() != null) {
            name = getArguments().getString("name");
            homeCity = getArguments().getString("homeCity");
            String encodedPic = getArguments().getString("profilePic");
            byte[] decodedString = Base64.decode(encodedPic, Base64.DEFAULT);
            profilePic = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ((TextView) rootView.findViewById(R.id.profileName)).setText(name);
        ((TextView) rootView.findViewById(R.id.profileHomeCity)).setText(homeCity);
        ((ImageView) rootView.findViewById(id.profile_pic)).setImageBitmap(profilePic);
        return rootView;
    }



}