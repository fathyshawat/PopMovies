package com.example.fat7y.popmovies.DB;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "movie")
public class FavouriteDB {


    @PrimaryKey(autoGenerate = true)
    private int DbId;
    private String FavPoster;
    private String FavOverview;
    private String FavReleaseDate;
    private int mId;
    private String FavOriginalTitle;
    private float FavVoteAverage;
    private String FavImageBackdrop;


    @Ignore
    public FavouriteDB(String favPoster, String favOverview, String favReleaseDate
            , int mId, String favOriginalTitle, float favVoteAverage, String favImageBackdrop) {
        FavPoster = favPoster;
        FavOverview = favOverview;
        FavReleaseDate = favReleaseDate;
        this.mId = mId;
        FavOriginalTitle = favOriginalTitle;
        FavVoteAverage = favVoteAverage;
        FavImageBackdrop = favImageBackdrop;
    }
    public FavouriteDB(){}
    public FavouriteDB(int dbId, String favPoster, String favOverview, String favReleaseDate
            , int mId, String favOriginalTitle, float favVoteAverage, String favImageBackdrop) {
        DbId = dbId;
        FavPoster = favPoster;
        FavOverview = favOverview;
        FavReleaseDate = favReleaseDate;
        this.mId = mId;
        FavOriginalTitle = favOriginalTitle;
        FavVoteAverage = favVoteAverage;
        FavImageBackdrop = favImageBackdrop;
    }



    public int getDbId() {
        return DbId;
    }

    public void setDbId(int dbId) {
        DbId = dbId;
    }

    public String getFavPoster() {
        return FavPoster;
    }

    public void setFavPoster(String favPoster) {
        FavPoster = favPoster;
    }

    public String getFavOverview() {
        return FavOverview;
    }

    public void setFavOverview(String favOverview) {
        FavOverview = favOverview;
    }

    public String getFavReleaseDate() {
        return FavReleaseDate;
    }

    public void setFavReleaseDate(String favReleaseDate) {
        FavReleaseDate = favReleaseDate;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getFavOriginalTitle() {
        return FavOriginalTitle;
    }

    public void setFavOriginalTitle(String favOriginalTitle) {
        FavOriginalTitle = favOriginalTitle;
    }

    public float getFavVoteAverage() {
        return FavVoteAverage;
    }

    public void setFavVoteAverage(float favVoteAverage) {
        FavVoteAverage = favVoteAverage;
    }

    public String getFavImageBackdrop() {
        return FavImageBackdrop;
    }

    public void setFavImageBackdrop(String favImageBackdrop) {
        FavImageBackdrop = favImageBackdrop;
    }


}
