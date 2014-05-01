package com.example.sofarsounds;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseAnalytics;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by phun on 3/27/14.
 */
public class RegisterFragment extends Fragment {

    private SharedPreferences sharedPref;

    private EditText fullname;
    private EditText email;
    private EditText password;
    private EditText password2;
    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.register_fragment, container, false);
        ParseAnalytics.trackEvent("RegisterFragment");

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
            }
        });

        return rootView;
    }

    private void saveRegistration() {

        ArrayList<String> errors = new ArrayList<String>();

        if (!isValidEmail(email.getText().toString())) {
            errors.add("Invalid e-mail address.");
        }

        if (validPassword(password.getText().toString(), password2.getText().toString()) != null) {
            errors.add(validPassword(password.getText().toString(), password2.getText().toString()));
        }

        if (errors.size() > 0) {
            final Dialog dialog = new Dialog(getActivity());

            dialog.setContentView(R.layout.registration_errors_dialog);
            dialog.setTitle("Uh oh!");

            String errorHtml = "";
            Iterator<String> iterator = errors.iterator();
            while(iterator.hasNext()) {
                errorHtml += "&#8226; \t" + iterator.next() + "<br/>";
            }

            final TextView errorView = (TextView) dialog.findViewById(R.id.errors);
            errorView.setText(Html.fromHtml(errorHtml));

            final Button okay = (Button) dialog.findViewById(R.id.okay);
            okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        } else {
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putString(getString(R.string.reg_fullname), fullname.getText().toString());
            editor.putString(getString(R.string.reg_email), email.getText().toString());
            editor.putString(getString(R.string.reg_password), password.getText().toString());
            editor.commit();

            Log.v("REG", sharedPref.getString(getString(R.string.reg_fullname), "DEFAULT"));
            Log.v("REG", sharedPref.getString(getString(R.string.reg_email), "DEFAULT"));
            Log.v("REG", sharedPref.getString(getString(R.string.reg_password), "DEFAULT"));
            showCameraScreen();
        }
    }

    private void showCameraScreen() {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);

        Fragment newFragment = new CameraFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.register_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public final static String validPassword(CharSequence p1, CharSequence p2) {
        if (p1 != null && p1.length() <= 0) {
            return "No password entered.";
        } else if (p2 != null && p2.length() <= 0) {
            return "No confirmation password entered.";
        } else if (!p1.equals(p2)) {
            return "Passwords don't match.";
        }
        return null;
    }
}