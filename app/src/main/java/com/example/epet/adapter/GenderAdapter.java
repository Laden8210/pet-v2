package com.example.epet.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.epet.R;

public class GenderAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private String[] mGenderList;

    public GenderAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        mContext = context;
        mGenderList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(mGenderList[position]);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(mGenderList[position]);

        return convertView;
    }
}
