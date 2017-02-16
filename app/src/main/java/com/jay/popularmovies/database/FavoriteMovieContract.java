package com.jay.popularmovies.database;

import android.provider.BaseColumns;

/**
 * DB contract class for storing favorite movies
 * Created by JK on 16/02/17.
 */

public class FavoriteMovieContract {

    private FavoriteMovieContract() {
    }

    public static class FavoriteMovie implements BaseColumns {
        public static final String TABLE_NAME = "favorite_movie";

        public static final String COLUMN_NAME_MOVIE_ID = "id";
        public static final String COLUMN_NAME_POSTER_PATH = "poster_path";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        public static final String COLUMN_NAME_RELEASE_DATE = "release_date";
        public static final String COLUMN_NAME_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_NAME_BACKDROP_PATH = "backdrop_path";
    }
}
