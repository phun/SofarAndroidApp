package com.example.sofarsounds;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by phun on 3/29/14.
 */
public class MapListFragment extends Fragment {

    private static final String TAG = "MapListFragment";

    private SharedPreferences sharedPref;
    private static String[] CITIES = new String[] { "Aalborg (Denmark)", "Aarhus (Denmark)", "Amsterdam", "Atlanta",
            "Auckland", "Austin", "Barcelona", "Beijing", "Beirut", "Belo Horizonte", "Bergamo (Italy)",
            "Bergen (Norway)", "Berlin", "Birmingham", "Boston, MA", "Brasilia", "Brighton", "Brisbane",
            "Buenos Aires", "Cardiff", "Chicago", "Copenhagen", "Dallas", "Edinburgh", "Galway",
            "Glasgow", "Hamburg", "Houston", "Istanbul", "Johanesburg", "Leeds", "Lima", "Lisbon",
            "Liverpool", "London", "Los Angeles", "Madrid", "Manchester", "Manilla", "Melbourne",
            "Mexico City", "Miami", "Milan", "Minneapolis", "Montevideo", "Mumbai", "Nashville",
            "New York", "Newcastle, UK", "Oxford", "Paris", "Perth", "Philadelphia", "Queretaro",
            "Reykjavik", "Rio", "San Francisco", "San Jose (Costa Rica)", "Santiago", "Seattle",
            "Singapore", "Stockholm", "Sydney", "São Paulo", "Tallinn", "Tokyo", "Toronto",
            "Vilnius", "Winchester", "York" };
    private String nearestCity = "Boston, MA";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_list_fragment, container, false);

        final ListView listview = (ListView) rootView.findViewById(R.id.list_view);
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < CITIES.length; ++i) {
            list.add(CITIES[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(rootView.getContext(),
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showConfirmationDialog(CITIES[i]);
            }
        });

        Context context = rootView.getContext();
        sharedPref = context.getSharedPreferences(
                getString(R.string.pref_file_key), Context.MODE_PRIVATE);

        showNearestCityDialog();

        final SearchView searchView = (SearchView) rootView.findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Geocoder coder = new Geocoder(getActivity().getApplicationContext());
                List<Address> address;

                try {
                    address = coder.getFromLocationName(s, 5);
                    if (address == null) {
                        return false;
                    }

                    Address location = address.get(0);
                    location.getLatitude();
                    location.getLongitude();

                    Location locationObj = new Location("Searched Location");
                    locationObj.setLongitude(location.getLongitude());
                    locationObj.setLatitude(location.getLatitude());


                    ChangeNearestCity(locationObj);
                    showNearestCityDialog();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return rootView;
    }

    private void showNearestCityDialog() {
        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.nearest_city_dialog);
        dialog.setTitle("Nearest City Found!");

        TextView textView = (TextView) dialog.findViewById(R.id.city);
        textView.setText(nearestCity);

        Button saveButton = (Button) dialog.findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                setCity(nearestCity);
            }
        });
        Button btnCancel= (Button) dialog.findViewById(R.id.cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showConfirmationDialog(final String city) {
        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.confirm_city_dialog);
        dialog.setTitle("Confirmation");

        TextView cityView = (TextView) dialog.findViewById(R.id.city);
        cityView.setText(city);

        Button saveButton = (Button) dialog.findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                setCity(city);
            }
        });
        Button btnCancel= (Button) dialog.findViewById(R.id.cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void setCity(String city) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.reg_city), city);
        editor.commit();
        Log.v(TAG, sharedPref.getString(getString(R.string.reg_city), "Boston"));
        submitRegistration();
    }

    public void submitRegistration() {
        SofarSession.openNewSession(getActivity().getApplicationContext(), "foo", "bar");

        Fragment newFragment = new Home();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.initial_container, newFragment);
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        transaction.commit();
    }

    private void ChangeNearestCity(Location location) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        float nearestDistance = Float.MAX_VALUE;

        Log.v(TAG, "LONG: " + String.valueOf(longitude));
        Log.v(TAG, "LAT: " + String.valueOf(latitude));
        if (Geocoder.isPresent()) {
            try {
                Geocoder gc = new Geocoder(getActivity().getApplicationContext());

                for (String city : CITIES) {
                    List<Address> address = gc.getFromLocationName(city, 1);
                    Log.v(TAG, "size:" + address.size());

                    if (address.size() > 0 && address.get(0).hasLatitude() && address.get(0).hasLongitude()) {
                        Location locationB = new Location("city");

                        locationB.setLatitude(address.get(0).getLatitude());
                        locationB.setLongitude(address.get(0).getLongitude());
                        Log.v(TAG, "city: " + city + " - dist: " + String.valueOf(location.distanceTo(locationB)));
                        if (location.distanceTo(locationB) < nearestDistance) {
                            nearestDistance = location.distanceTo(locationB);
                            nearestCity = city;
                        }
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
            Log.v(TAG, "nearest: " + nearestCity);
        }
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}