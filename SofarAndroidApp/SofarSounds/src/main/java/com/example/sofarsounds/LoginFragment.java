package com.example.sofarsounds;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by phun on 3/27/14.
 */
public class LoginFragment extends Fragment {
    private EditText email;
    private EditText password;
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

        email = (EditText)rootView.findViewById(R.id.email);
        password = (EditText)rootView.findViewById(R.id.password);
        return rootView;
    }

    public void submitLogin() {
        // TODO: Make this real.
        String user = email.getText().toString();
        String pass = password.getText().toString();
        try {
            SofarSession.openNewSession(getActivity().getApplicationContext(), user, pass);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        } catch (ParseException pe) {
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.registration_errors_dialog);
            dialog.setTitle("Invalid Login");
            final TextView errorView = (TextView) dialog.findViewById(R.id.errors);
            errorView.setText(pe.getMessage());
            final Button okay = (Button) dialog.findViewById(R.id.okay);
            okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}