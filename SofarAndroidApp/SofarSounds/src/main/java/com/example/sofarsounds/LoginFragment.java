package com.example.sofarsounds;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by phun on 3/27/14.
 */
public class LoginFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment, container, false);

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

        Fragment newFragment = new Home();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.initial_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}