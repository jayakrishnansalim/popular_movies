package com.jay.popularmovies.retrofit;

import com.jay.popularmovies.BuildConfig;
import com.jay.popularmovies.constant.Const;
import com.jay.popularmovies.model.MovieResponseData;
import com.jay.popularmovies.model.ReviewData;
import com.jay.popularmovies.model.TrailerData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Retrofit movie service class.
 */
public interface MoviesService {

    /**
     * Method responsible for fetching most popular movie list
     *
     * @return Call<MovieResponseData>
     */
    @GET(APIConstants.MOST_POPULAR_MOVIES_URL + "?api_key=" + BuildConfig.THE_MOVIE_DB_API_KEY)
    Call<MovieResponseData> getPopularMovieList();

    /**
     * Method responsible for fetching top rated movies list
     * @return Call<MovieResponseData>
     */
    @GET(APIConstants.TOP_RATED_MOVIES_URL + "?api_key=" + BuildConfig.THE_MOVIE_DB_API_KEY)
    Call<MovieResponseData> getTopRatedMovieList();

    /**
     * Method responsible for fetching movie trailers
     *
     * @param id - Movie Id
     * @return Call<TrailerData>
     */
    @GET(APIConstants.MOVIE_TRAILERS_URL + "?api_key=" + BuildConfig.THE_MOVIE_DB_API_KEY)
    Call<TrailerData> getTrailerList(@Path(Const.KEY_MOVIE_ID) int id);

    /**
     * Method responsible for fetching movie reviews
     *
     * @param id - Movie Id
     * @return Call<ReviewData>
     */
    @GET(APIConstants.MOVIE_REVIEWS_URL + "?api_key=" + BuildConfig.THE_MOVIE_DB_API_KEY)
    Call<ReviewData> getReviewList(@Path(Const.KEY_MOVIE_ID) int id);
}
