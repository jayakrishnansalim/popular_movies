package com.jay.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * DTO for movie response
 * Created by JK on 22/08/16.
 */
public class MovieResponseData {

    private int page;

    @SerializedName("results")
    private List<MovieData> movieDataList;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public List<MovieData> getMovieDataList() {
        return movieDataList;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public String toString() {
        return "MovieResponseData{" +
                "page=" + page +
                ", movieDataList=" + movieDataList +
                ", totalResults=" + totalResults +
                ", totalPages=" + totalPages +
                '}';
    }
}
