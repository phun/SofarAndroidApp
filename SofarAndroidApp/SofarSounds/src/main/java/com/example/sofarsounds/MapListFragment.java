package com.example.sofarsounds;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    private SearchView searchView;

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

        searchView = (SearchView) rootView.findViewById(R.id.search);
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

                    FindNearestCityTask t = new FindNearestCityTask();
                    t.execute(locationObj);
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

        ImageButton gpsButton = (ImageButton) rootView.findViewById(R.id.gps);
        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNearestCityDialog();
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
        submitRegistration();
    }

    public void submitRegistration() {
        SofarSession.openNewSession(getActivity().getApplicationContext(), "foo", "bar");

        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void ChangeNearestCity(Location location) {

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

    private class FindNearestCityTask extends AsyncTask<Location, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(Location... locations) {
            Location location = locations[0];

            String nearestCity = null;
            float nearestDistance = Float.MAX_VALUE;

            if (Geocoder.isPresent()) {
                try {
                    Geocoder gc = new Geocoder(getActivity().getApplicationContext());

                    for (String city : CITIES) {
                        List<Address> address = gc.getFromLocationName(city, 1);

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
            }
            return nearestCity;
        }

        @Override
        protected void onPreExecute() {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
            progressDialog = ProgressDialog.show(getActivity(),
                    "Searching for Nearest City", "Please wait...");
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            nearestCity = result;
            showNearestCityDialog();
        }
    }
}