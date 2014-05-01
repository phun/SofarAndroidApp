package com.example.sofarsounds;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parse.ParseAnalytics;

/**
 * Created by phun on 3/27/14.
 */
public class LoginFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment, container, false);
        ParseAnalytics.trackEvent("LoginFragment");
        final Button loginButton = (Button) rootView.findViewById(R.id.login_submit);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submitLogin();
            }
        });

        return rootView;
    }

    public void submitLogin() {
        // TODO: Make this real.
        SofarSession.openNewSession(getActivity().getApplicationContext(), "foo", "bar");

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}