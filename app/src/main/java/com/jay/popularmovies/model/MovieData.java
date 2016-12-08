package com.jay.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * DTO for movie
 * Created by Jay on 20/08/16.
 */
public class MovieData implements Parcelable {

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

    private int id;
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

    private MovieData(Parcel in) {
        id = in.readInt();
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        originalTitle = in.readString();
        title = in.readString();
        backdropPath = in.readString();
        voteAverage = in.readByte();
    }

    public int getId() {
        return id;
    }

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
                "id=" + id +
                ", posterPath='" + posterPath + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", title='" + title + '\'' +
                ", voteAverage=" + voteAverage +
                ", backdropPath='" + backdropPath + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeString(originalTitle);
        parcel.writeString(title);
        parcel.writeString(backdropPath);
        parcel.writeDouble(voteAverage);
    }
}
