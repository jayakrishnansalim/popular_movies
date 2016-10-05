package com.jay.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for movie list
 * Created by Jay on 30/09/16.
 */

public class MovieListData implements Parcelable {

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public MovieListData createFromParcel(Parcel in) {
            return new MovieListData(in);
        }

        public MovieListData[] newArray(int size) {
            return new MovieListData[size];
        }
    };
    private List<MovieData> movieDataList;

    public MovieListData() {
    }

    @SuppressWarnings("unchecked")
    private MovieListData(Parcel in) {
        movieDataList = new ArrayList<>();
        in.readTypedList(movieDataList, MovieData.CREATOR);
    }

    public List<MovieData> getMovieDataList() {
        return movieDataList;
    }

    public void setMovieDataList(List<MovieData> movieDataList) {
        this.movieDataList = movieDataList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(movieDataList);
    }
}
