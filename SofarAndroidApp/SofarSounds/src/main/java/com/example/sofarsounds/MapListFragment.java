package com.example.sofarsounds;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by phun on 3/29/14.
 */
public class MapListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_list_fragment, container, false);

        final ListView listview = (ListView) rootView.findViewById(R.id.list_view);
        String[] values = new String[] { "Aalborg (Denmark)", "Aarhus (Denmark)", "Amsterdam", "Atlanta",
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

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(rootView.getContext(),
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);


        return rootView;
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