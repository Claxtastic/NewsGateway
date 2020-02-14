package com.claxtastic.newsgateway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SourceArrayAdapter extends ArrayAdapter<Source> {

    ArrayList<Source> sources;

    public SourceArrayAdapter(Context context, int resource, ArrayList<Source> sources) {
        super(context, resource, sources);
        this.sources = sources;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.drawer_item, null);
        TextView sourceNameView = v.findViewById(R.id.textview_source);
        sourceNameView.setText(sources.get(position).getColoredName());
        return v;
    }
}
