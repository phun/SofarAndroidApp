package com.example.sofarsounds;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class Home extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        final Button attendButton = (Button) rootView.findViewById(R.id.attendButton);
        attendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showAttendScreen();
            }
        });

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

        return rootView;
    }


    private void showAttendScreen() {
        Fragment newFragment = new AttendFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.initial_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showMyShowsScreen() {
        Fragment newFragment = new MyShowsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.initial_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showProfileScreen() {
        Fragment newFragment = new ProfileFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.initial_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
