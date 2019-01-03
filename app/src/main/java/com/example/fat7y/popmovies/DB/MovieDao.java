package com.example.fat7y.popmovies.DB;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie ORDER BY DbId")
    LiveData<List<FavouriteDB>> loadAllMovies();

    @Insert
    void insertMovie(FavouriteDB movieEntry);

    @Delete
    void deleteMovie(FavouriteDB movieEntry);

}
