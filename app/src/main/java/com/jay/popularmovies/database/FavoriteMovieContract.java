package com.jay.popularmovies.database;

import android.provider.BaseColumns;

/**
 * DB contract class for storing favorite movies
 * Created by JK on 16/02/17.
 */

class FavoriteMovieContract {

    private FavoriteMovieContract() {
    }

    static class FavoriteMovie implements BaseColumns {
        static final String TABLE_NAME = "favorite_movie";

        static final String COLUMN_NAME_MOVIE_ID = "id";
        static final String COLUMN_NAME_POSTER_PATH = "poster_path";
        static final String COLUMN_NAME_OVERVIEW = "overview";
        static final String COLUMN_NAME_RELEASE_DATE = "release_date";
        static final String COLUMN_NAME_ORIGINAL_TITLE = "original_title";
        static final String COLUMN_NAME_TITLE = "title";
        static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
        static final String COLUMN_NAME_BACKDROP_PATH = "backdrop_path";
    }
}
