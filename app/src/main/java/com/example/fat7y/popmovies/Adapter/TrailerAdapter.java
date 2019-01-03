package com.example.fat7y.popmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fat7y.popmovies.R;
import com.example.fat7y.popmovies.model.TrailerValues;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailerAdapter extends ArrayAdapter implements View.OnClickListener {
    private final int mRes;
    private final ArrayList<TrailerValues> mArray;
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private String mTrailerKey;

    public TrailerAdapter(Context context, int resource, ArrayList<TrailerValues> objects) {
        super(context, resource, objects);
        mContext = context;
        mRes = resource;
        mArray = objects;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(mRes, parent, false);
        ImageView mImageTrailers = (ImageView) view.findViewById(R.id.imageTrailer);
        TextView mTextTrailers = (TextView) view.findViewById(R.id.textTrailer);
        mTextTrailers.setText(mArray.get(position).getTrailerName());
        mTrailerKey = mArray.get(position).getTrailerKey();
        Picasso.get().load("http://img.youtube.com/vi/" + mTrailerKey + "/0.jpg").fit().into(mImageTrailers);
        mImageTrailers.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + mTrailerKey));
        mContext.startActivity(mIntent);
    }
}
