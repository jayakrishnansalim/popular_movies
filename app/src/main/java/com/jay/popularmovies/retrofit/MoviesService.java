package com.jay.popularmovies.retrofit;

import com.jay.popularmovies.BuildConfig;
import com.jay.popularmovies.model.MovieResponseData;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by JK on 22/08/16.
 */
public interface MoviesService {

    @GET(APIConstants.MOST_POPULAR_MOVIES_URL + "?api_key=" + BuildConfig.THE_MOVIE_DB_API_KEY)
    Call<MovieResponseData> getMovieList();
}
