package com.example.fat7y.popmovies.model;

public class TrailerValues {
    private String TrailerName;
    private String TrailerKey;

    public TrailerValues(String trailerName, String trailerKey) {
        TrailerName = trailerName;
        TrailerKey = trailerKey;
    }

    public String getTrailerName() {
        return TrailerName;
    }

    public void setTrailerName(String trailerName) {
        TrailerName = trailerName;
    }

    public String getTrailerKey() {
        return TrailerKey;
    }

    public void setTrailerKey(String trailerKey) {
        TrailerKey = trailerKey;
    }
}