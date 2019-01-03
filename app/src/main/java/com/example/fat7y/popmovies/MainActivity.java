package com.example.fat7y.popmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.fat7y.popmovies.Adapter.MovieAdapter;
import com.example.fat7y.popmovies.DB.FavouriteDB;
import com.example.fat7y.popmovies.ViewModel.MainViewModel;
import com.example.fat7y.popmovies.model.Movies;

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

public class MainActivity extends AppCompatActivity {

    private String mApiKey = "";
    private String mUrl = "http://api.themoviedb.org/3/movie/popular?api_key=" + mApiKey;
    private GridView mGridView;
    private ArrayList<Movies> data = new ArrayList<>();
    private static MovieAdapter mAdapter;
    private static int mGridViewPosition = 0;
    public static String GRID_VIEW_POSITION="grid";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("MyMovies", data);
        int index = mGridView.getFirstVisiblePosition();
        outState.putInt(GRID_VIEW_POSITION, index);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = (GridView) findViewById(R.id.gridview);
        if (savedInstanceState != null) {
            data = savedInstanceState.getParcelableArrayList("MyMovies");
            RestoreGridPosition();

        } else {
            loadPosters();
            mAdapter = new MovieAdapter(this,data);

        }

        showMovies();

    }


    private void RestoreGridPosition(){
        if(mGridViewPosition > 0 && mAdapter.getCount() >= mGridViewPosition)
            mGridView.setSelection(mGridViewPosition);
    }

    private void showMovies() {
        mAdapter = new MovieAdapter(this, data);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent = new Intent(getApplicationContext(), Details.class);
                intent.putExtra("Title", data.get(position).getTitle());
                intent.putExtra("Overview", data.get(position).getOverview());
                intent.putExtra("Id", data.get(position).getId());
                intent.putExtra("Poster", data.get(position).getPoster_path());
                intent.putExtra("ImageBackdrop", data.get(position).getBackdrop_path());
                intent.putExtra("ReleaseDate", data.get(position).getRelease_date());
                intent.putExtra("VoteAverage", data.get(position).getVote_average());
                intent.putExtra("ApiKey", mApiKey);
                startActivity(intent);

            }
        });
    }


    private void loadPosters() {
        new DownloadTask().execute(mUrl);
    }

    private void loadFavourite() {
        new LoadFav().execute();

    }


    private void loadDB() {
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        LiveData<List<FavouriteDB>> favourite = mainViewModel.getMovies();
        favourite.observe(this, new Observer<List<FavouriteDB>>() {
            @Override
            public void onChanged(@Nullable List<FavouriteDB> favouriteDBS) {
                for (int i = 0; i < favouriteDBS.size(); i++) {
                    String favPoster = favouriteDBS.get(i).getFavPoster();
                    int id = favouriteDBS.get(i).getId();
                    String favImageBackdrop = favouriteDBS.get(i).getFavImageBackdrop();
                    String favOriginalTitle = favouriteDBS.get(i).getFavOriginalTitle();
                    String favOverview = favouriteDBS.get(i).getFavOverview();
                    String favReleaseDate = favouriteDBS.get(i).getFavReleaseDate();
                    float favVoteAverage = favouriteDBS.get(i).getFavVoteAverage();
                    Movies dataView = new Movies(id, favOriginalTitle, favPoster, favImageBackdrop, favOverview, favReleaseDate, favVoteAverage);
                    data.add(dataView);
                }
                mAdapter.setMovie(data);
            }
        });

    }

    public class LoadFav extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            loadDB();
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mAdapter.notifyDataSetChanged();
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                HttpGet mHttpGet = new HttpGet(params[0]);
                HttpClient mHttpClient = new DefaultHttpClient();
                HttpResponse mHttpResponse = mHttpClient.execute(mHttpGet);
                int statusCode = mHttpResponse.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    HttpEntity mHttpEntity = mHttpResponse.getEntity();
                    String MyJsonData = EntityUtils.toString(mHttpEntity);
                    JSONObject mJsonObject = new JSONObject(MyJsonData);
                    JSONArray mJsonArray = mJsonObject.getJSONArray("results");
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        JSONObject mJsonObject2 = mJsonArray.getJSONObject(i);
                        String poster_path = mJsonObject2.getString("poster_path");
                        String original_title = mJsonObject2.getString("original_title");
                        String release_date = mJsonObject2.getString("release_date");
                        String overview = mJsonObject2.getString("overview");
                        String backdrop_path = mJsonObject2.getString("backdrop_path");
                        int id = mJsonObject2.getInt("id");
                        float vote_average = (float) mJsonObject2.getDouble("vote_average");
                        Movies movies = new Movies(id, original_title, poster_path, backdrop_path, overview, release_date, vote_average);
                        data.add(movies);
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
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_Most_Popular:
                mUrl = "http://api.themoviedb.org/3/movie/popular?api_key=" + mApiKey;
                data.clear();
                loadPosters();
                break;
            case R.id.action_Highest_Rate:
                mUrl = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + mApiKey;
                data.clear();
                loadPosters();
                break;

            case R.id.action_Favourite :
                data.clear();
                loadFavourite();
                break;
        }
        return true;
    }
}
