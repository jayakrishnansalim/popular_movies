package com.jay.popularmovies.retrofit;

/**
 * Interface for declaring APIs and related constants
 * Created by Jay on 20/08/16.
 */
public interface APIConstants {

    String BASE_URL = "http://api.themoviedb.org";
    String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w342/";
    String YOU_TUBE_URL = "http://www.youtube.com/watch?v=%1$s";
    String YOU_TUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/%1$s/0.jpg";
    String LARGE_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w1000/";
    String MOST_POPULAR_MOVIES_URL = "/3/movie/popular/";
    String TOP_RATED_MOVIES_URL = "/3/movie/top_rated/";
    String MOVIE_TRAILERS_URL = "/3/movie/{id}/videos";
    String MOVIE_REVIEWS_URL = "/3/movie/{id}/reviews";
}
