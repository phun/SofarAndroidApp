package com.example.sofarsounds;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by phun on 3/27/14.
 */
public class RegisterFragment extends Fragment {

    private SharedPreferences sharedPref;

    private EditText fullname;
    private EditText email;
    private EditText password;
    private EditText password2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.register_fragment, container, false);

        Context context = rootView.getContext();
        sharedPref = context.getSharedPreferences(
                getString(R.string.pref_file_key), Context.MODE_PRIVATE);

        fullname = (EditText)rootView.findViewById(R.id.fullname);
        email = (EditText)rootView.findViewById(R.id.email);
        password = (EditText)rootView.findViewById(R.id.password);
        password2 = (EditText)rootView.findViewById(R.id.password2);

        final Button nextButton = (Button) rootView.findViewById(R.id.register_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveRegistration();
                showCameraScreen();
            }
        });

        return rootView;
    }

    private void saveRegistration() {
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(getString(R.string.reg_fullname), fullname.getText().toString());
        editor.putString(getString(R.string.reg_email), email.getText().toString());
        editor.putString(getString(R.string.reg_password), password.getText().toString());
        editor.commit();

        Log.v("REG", sharedPref.getString(getString(R.string.reg_fullname), "DEFAULT"));
        Log.v("REG", sharedPref.getString(getString(R.string.reg_email), "DEFAULT"));
        Log.v("REG", sharedPref.getString(getString(R.string.reg_password), "DEFAULT"));

    }

    private void showCameraScreen() {
        Fragment newFragment = new CameraFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.initial_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}