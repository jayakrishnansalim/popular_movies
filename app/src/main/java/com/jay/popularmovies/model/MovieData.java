package com.jay.popularmovies.model;

import com.google.gson.annotations.SerializedName;

/**
 * DTO for movie
 * Created by JK on 20/08/16.
 */
public class MovieData {

    @SerializedName("poster_path")
    private String posterPath;

    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("original_title")
    private String originalTitle;

    private String title;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("backdrop_path")
    private String backdropPath;

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    @Override
    public String toString() {
        return "MovieData{" +
                "posterPath='" + posterPath + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", title='" + title + '\'' +
                ", voteAverage=" + voteAverage +
                ", backdropPath='" + backdropPath + '\'' +
                '}';
    }
}
