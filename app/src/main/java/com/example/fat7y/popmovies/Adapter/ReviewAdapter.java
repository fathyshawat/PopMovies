package com.example.fat7y.popmovies.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fat7y.popmovies.R;
import com.example.fat7y.popmovies.model.ReviewValues;

import java.util.ArrayList;


public class ReviewAdapter extends ArrayAdapter {
    private final int mRes;
    private final ArrayList <ReviewValues> mArrayList;
    private final LayoutInflater mLayoutInflater;
    public ReviewAdapter(Context context, int resource ,ArrayList <ReviewValues> rev) {
        super(context, resource ,rev);
        mRes = resource;
        mArrayList = rev;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(mRes, parent ,false );
        TextView textAuthor = (TextView)view.findViewById(R.id.textAuthor);
        TextView textContent = (TextView) view.findViewById(R.id.textContent);
        textAuthor.setText(mArrayList.get(position).getAuthor());
        textContent.setText(mArrayList.get(position).getContent());

        return view;
    }
}



