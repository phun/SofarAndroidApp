package com.example.sofarsounds;

/**
 * Created by phun on 3/27/14.
 */

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        final Button loginButton = (Button) rootView.findViewById(R.id.initial_login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showLoginScreen();
            }
        });

        final Button registerButton = (Button) rootView.findViewById(R.id.initial_register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showRegisterScreen();
            }
        });

        return rootView;
    }

    private void showLoginScreen() {
        Fragment newFragment = new LoginFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.initial_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showRegisterScreen() {
        Fragment newFragment = new RegisterFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.initial_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}