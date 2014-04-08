package com.example.sofarsounds;

/**
 * Created by lucid on 4/8/14.
 */
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowListViewAdapter extends ArrayAdapter<ShowRowItem> {

    Context context;

    public ShowListViewAdapter(Context context, int resourceId,
                               List<ShowRowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ShowRowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.show_list_item, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtDesc.setText(rowItem.getDate());
        holder.txtTitle.setText(rowItem.getCity());
        if (rowItem.hasIcon()) {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.imageView.setImageResource(rowItem.getIconId());
        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}