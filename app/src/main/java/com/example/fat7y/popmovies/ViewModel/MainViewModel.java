package com.example.fat7y.popmovies.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.fat7y.popmovies.AppDatabase;
import com.example.fat7y.popmovies.DB.FavouriteDB;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<FavouriteDB>> movies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(getApplication());
        movies = database.taskDao().loadAllMovies();

    }

    public LiveData<List<FavouriteDB>> getMovies() {
        return movies;
    }
}