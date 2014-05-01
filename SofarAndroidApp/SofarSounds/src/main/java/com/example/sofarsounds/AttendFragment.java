package com.example.sofarsounds;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.internal.util.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.parse.ParseAnalytics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AttendFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AttendFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class AttendFragment extends Fragment {

    private OnShowSelectedListener mListener;

    private List<ShowModel> shows;
    private ProfileModel profile;
    private List<ShowModel> homeShows;
    private List<ShowModel> recentShows;
    private List<ShowModel> otherShows;
    // TODO: Why do the convoluted newInstance / Bundle storage thing and not this:
    public AttendFragment(List<ShowModel> shows, ProfileModel profile) {
        this.profile = profile;
        this.shows = shows;

        homeShows = Lists.newArrayList();
        recentShows = Lists.newArrayList();
        otherShows = Lists.newArrayList();
        // Filter shows into categories:
        for (ShowModel show : shows) {
            if (show.getCity().equals(profile.getHomeCity())) {
                homeShows.add(show);
            } else if (profile.isRecentCity(show.getCity())) {
                recentShows.add(show);
            } else {
                otherShows.add(show);
            }
        }
    }
    public static final String[] cities = new String[] { "Strawberry",
            "Banana", "Orange", "Mixed" };

    public static final String[] dates = new String[] {
            "It is an aggregate accessory fruit",
            "It is the largest herbaceous flowering plant", "Citrus Fruit",
            "Mixed Fruits" };

    private final int HOME_ICON = R.drawable.home;
    private final int RECENT_ICON = R.drawable.android_location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ParseAnalytics.trackEvent("AttendFragment");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_attend, container, false);

        List<ShowRowItem> rowItems = Lists.newArrayList();
        final List<ShowModel> showModels = Lists.newArrayList();
        // Show HOME shows first, then RECENT shows, then the rest.
        for (ShowModel show : homeShows) {
            ShowRowItem item = new ShowRowItem(HOME_ICON, show.getCity(), show.getDate());
            rowItems.add(item);
            showModels.add(show);
        }

        for (ShowModel show : recentShows) {
            ShowRowItem item = new ShowRowItem(RECENT_ICON, show.getCity(), show.getDate());
            rowItems.add(item);
            showModels.add(show);
        }

        for (ShowModel show : otherShows) {
            ShowRowItem item = new ShowRowItem(show.getCity(), show.getDate());
            rowItems.add(item);
            showModels.add(show);
        }

        ListView listView = (ListView) rootView.findViewById(R.id.shows_listview);
        ShowListViewAdapter adapter = new ShowListViewAdapter(rootView.getContext(),
                R.layout.show_list_item, rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mListener != null)
                    mListener.onShowSelected(showModels.get(i));
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnShowSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnShowSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnShowSelectedListener {
        public void onShowSelected(ShowModel show);
    }
}
