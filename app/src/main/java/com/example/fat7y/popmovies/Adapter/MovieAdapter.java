package com.example.fat7y.popmovies.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.fat7y.popmovies.DB.FavouriteDB;
import com.example.fat7y.popmovies.model.Movies;
import com.example.fat7y.popmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends BaseAdapter {

    Context context;
    ArrayList<Movies> data;

    public MovieAdapter(Context context, ArrayList<Movies> data) {

        this.context = context;
        this.data = data;
    }
    public void setMovie(ArrayList<Movies> data) {
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.poster, parent, false);
        }

        ImageView poster = (ImageView) convertView.findViewById(R.id.poster);


        String url = data.get(position).getPoster_path();

        Picasso.get()
                .load("http://image.tmdb.org/t/p/w185" + url)
                .into(poster);
        return poster;
    }
}
