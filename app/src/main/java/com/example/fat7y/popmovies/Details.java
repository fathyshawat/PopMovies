package com.example.fat7y.popmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fat7y.popmovies.Adapter.ReviewAdapter;
import com.example.fat7y.popmovies.Adapter.TrailerAdapter;
import com.example.fat7y.popmovies.DB.FavouriteDB;
import com.example.fat7y.popmovies.model.ReviewValues;
import com.example.fat7y.popmovies.model.TrailerValues;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Details extends AppCompatActivity {

    private ReviewAdapter mReviewAdapter;
    private ImageView img;
    private String title, date, desc, mImageBackdrop, poster, mApiKey;
    private float rate;
    private TextView tvTitle, tvDate, tvRate, tvDesc;
    private Button fav;
    private int id;
    private AppDatabase mDb;
    private ArrayList<ReviewValues> mReviewValues;
    private ListView mListTrailers;
    private TrailerAdapter mTrailerAdapter;
    private ArrayList<TrailerValues> mTrailerValues;
    private ListView mListReview;
    public Boolean like = true;
    private LiveData<List<FavouriteDB>> mMyFavData;
    private int DbId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        init();
        getDetails();

        checkFavourite();

        mReviewValues = new ArrayList<ReviewValues>();
        new Review().execute("http://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=" + mApiKey);
        mReviewAdapter = new ReviewAdapter(this, R.layout.item_review, mReviewValues);
        mListReview.setAdapter(mReviewAdapter);
        mTrailerValues = new ArrayList<TrailerValues>();
        new MyTrailer().execute("http://api.themoviedb.org/3/movie/" + id + "/videos?api_key=" + mApiKey);
        mTrailerAdapter = new TrailerAdapter(this, R.layout.trailer, mTrailerValues);
        mListTrailers.setAdapter(mTrailerAdapter);
        Picasso.get()
                .load("http://image.tmdb.org/t/p/w185" + mImageBackdrop)
                .into(img);
        tvTitle.setText(title);
        tvRate.setText(Float.toString(rate));
        tvDesc.setText(desc);
        tvDate.setText(date);


        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (like) {
                    FavouriteDB favouriteDB = new FavouriteDB(poster, desc, date, id, title, rate, mImageBackdrop);
                    mDb.taskDao().insertMovie(favouriteDB);
                    fav.setText("UnFavourite");
                    fav.setBackgroundColor(Color.GRAY);
                    like = false;
                } else {
                    FavouriteDB favouriteDB = new FavouriteDB(DbId, poster, desc, date, id, title, rate, mImageBackdrop);

                    mDb.taskDao().deleteMovie(favouriteDB);
                    fav.setText("Favourite");
                    fav.setBackgroundColor(Color.GREEN);
                    like = true;

                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {

        tvDate = (TextView) findViewById(R.id.date_tv);
        tvDesc = (TextView) findViewById(R.id.description_tv);
        tvRate = (TextView) findViewById(R.id.vote_tv);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        img = findViewById(R.id.img);
        mDb = AppDatabase.getInstance(this);
        fav = (Button) findViewById(R.id.bt_fav);
        mListReview = (ListView) findViewById(R.id.listReview);
        mListTrailers = (ListView) findViewById(R.id.list_trialer);


    }

    private class Review extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                HttpGet mHttpGet = new HttpGet(params[0]);
                HttpClient mHttpClient = new DefaultHttpClient();
                HttpResponse mHttpResponse = mHttpClient.execute(mHttpGet);
                int statusCode = mHttpResponse.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    HttpEntity mHttpEntity = mHttpResponse.getEntity();
                    String ReviewData = EntityUtils.toString(mHttpEntity);
                    JSONObject mJsonObject = new JSONObject(ReviewData);
                    JSONArray mJsonArray = mJsonObject.getJSONArray("results");
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        JSONObject mJsonObject2 = mJsonArray.getJSONObject(i);
                        String author = mJsonObject2.getString("author");
                        String content = mJsonObject2.getString("content");
                        ReviewValues reviewValues = new ReviewValues(author, content);
                        mReviewValues.add(reviewValues);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mReviewAdapter.notifyDataSetChanged();
        }
    }

    private class MyTrailer extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                HttpGet mHttpGet = new HttpGet(params[0]);
                HttpClient mHttpClient = new DefaultHttpClient();
                HttpResponse mHttpResponse = mHttpClient.execute(mHttpGet);
                int statusCode = mHttpResponse.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    HttpEntity mHttpEntity = mHttpResponse.getEntity();
                    String trailerData = EntityUtils.toString(mHttpEntity);
                    JSONObject mJsonObject = new JSONObject(trailerData);
                    JSONArray mJsonArray = mJsonObject.getJSONArray("results");
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        JSONObject mJsonObject2 = mJsonArray.getJSONObject(i);
                        String key = mJsonObject2.getString("key");
                        String name = mJsonObject2.getString("name");
                        TrailerValues trailerValues = new TrailerValues(name, key);
                        mTrailerValues.add(trailerValues);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mTrailerAdapter.notifyDataSetChanged();
        }
    }

    private void checkFavourite() {

        mMyFavData = mDb.taskDao().loadAllMovies();
        mMyFavData.observe(this, new Observer<List<FavouriteDB>>() {
            @Override
            public void onChanged(@Nullable List<FavouriteDB> favouriteDBS) {
                for (int i = 0; i < favouriteDBS.size(); i++) {
                    if (title.equals(favouriteDBS.get(i).getFavOriginalTitle())) {
                        like = false;
                        Toast.makeText(getApplicationContext(), "this Movie has been saved to Favourite", Toast.LENGTH_LONG).show();
                        fav.setText("UnFavourite");
                        fav.setBackgroundColor(Color.GRAY);
                        DbId = favouriteDBS.get(i).getDbId();
                        break;
                    }
                }
            }
        });

    }

    private void getDetails() {
        title = getIntent().getExtras().getString("Title");
        desc = getIntent().getExtras().getString("Overview");
        mImageBackdrop = getIntent().getExtras().getString("ImageBackdrop");
        date = getIntent().getExtras().getString("ReleaseDate");
        rate = getIntent().getExtras().getFloat("VoteAverage");
        poster = getIntent().getExtras().getString("Poster");
        id = getIntent().getExtras().getInt("Id");
        mApiKey = getIntent().getExtras().getString("ApiKey");

    }


}
