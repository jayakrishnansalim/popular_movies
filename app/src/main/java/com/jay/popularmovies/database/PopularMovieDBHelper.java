package com.jay.popularmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jay.popularmovies.model.MovieData;

import java.util.ArrayList;
import java.util.List;

import static com.jay.popularmovies.database.FavoriteMovieContract.FavoriteMovie.COLUMN_NAME_BACKDROP_PATH;
import static com.jay.popularmovies.database.FavoriteMovieContract.FavoriteMovie.COLUMN_NAME_MOVIE_ID;
import static com.jay.popularmovies.database.FavoriteMovieContract.FavoriteMovie.COLUMN_NAME_ORIGINAL_TITLE;
import static com.jay.popularmovies.database.FavoriteMovieContract.FavoriteMovie.COLUMN_NAME_OVERVIEW;
import static com.jay.popularmovies.database.FavoriteMovieContract.FavoriteMovie.COLUMN_NAME_POSTER_PATH;
import static com.jay.popularmovies.database.FavoriteMovieContract.FavoriteMovie.COLUMN_NAME_RELEASE_DATE;
import static com.jay.popularmovies.database.FavoriteMovieContract.FavoriteMovie.COLUMN_NAME_TITLE;
import static com.jay.popularmovies.database.FavoriteMovieContract.FavoriteMovie.COLUMN_NAME_VOTE_AVERAGE;
import static com.jay.popularmovies.database.FavoriteMovieContract.FavoriteMovie.TABLE_NAME;

/**
 * DB helper class
 * Created by JK on 16/02/17.
 */

@SuppressWarnings("TryFinallyCanBeTryWithResources")
public class PopularMovieDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PopularMovie.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_MOVIE_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_POSTER_PATH + " TEXT," +
                    COLUMN_NAME_OVERVIEW + " TEXT," +
                    COLUMN_NAME_RELEASE_DATE + " TEXT," +
                    COLUMN_NAME_ORIGINAL_TITLE + " TEXT," +
                    COLUMN_NAME_TITLE + " TEXT," +
                    COLUMN_NAME_VOTE_AVERAGE + " REAL," +
                    COLUMN_NAME_BACKDROP_PATH + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public PopularMovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long insertFavMovie(MovieData movieData) {
        SQLiteDatabase db = getWritableDatabase();
        long id = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_MOVIE_ID, movieData.getId());
            values.put(COLUMN_NAME_POSTER_PATH, movieData.getPosterPath());
            values.put(COLUMN_NAME_OVERVIEW, movieData.getOverview());
            values.put(COLUMN_NAME_RELEASE_DATE, movieData.getReleaseDate());
            values.put(COLUMN_NAME_ORIGINAL_TITLE, movieData.getOriginalTitle());
            values.put(COLUMN_NAME_TITLE, movieData.getTitle());
            values.put(COLUMN_NAME_BACKDROP_PATH, movieData.getBackdropPath());
            values.put(COLUMN_NAME_VOTE_AVERAGE, movieData.getVoteAverage());
            id = db.insert(TABLE_NAME, null, values);
        } finally {
            db.close();
        }
        return id;
    }

    public List<MovieData> getFavMovies() {
        SQLiteDatabase db = getReadableDatabase();
        List<MovieData> favMovieList = null;
        try {
            String[] projection = {
                    COLUMN_NAME_MOVIE_ID,
                    COLUMN_NAME_POSTER_PATH,
                    COLUMN_NAME_OVERVIEW,
                    COLUMN_NAME_RELEASE_DATE,
                    COLUMN_NAME_ORIGINAL_TITLE,
                    COLUMN_NAME_TITLE,
                    COLUMN_NAME_BACKDROP_PATH,
                    COLUMN_NAME_VOTE_AVERAGE
            };
            Cursor cursor = db.query(
                    TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            favMovieList = new ArrayList<>();
            try {
                while (cursor.moveToNext()) {
                    MovieData movieData = setMovieData(cursor);
                    favMovieList.add(movieData);
                }
            } finally {
                cursor.close();
            }
        } finally {
            db.close();
        }
        return favMovieList;
    }

    public MovieData getMovieDataById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        MovieData movieData = null;
        try {
            String[] projection = {
                    COLUMN_NAME_MOVIE_ID,
                    COLUMN_NAME_POSTER_PATH,
                    COLUMN_NAME_OVERVIEW,
                    COLUMN_NAME_RELEASE_DATE,
                    COLUMN_NAME_ORIGINAL_TITLE,
                    COLUMN_NAME_TITLE,
                    COLUMN_NAME_BACKDROP_PATH,
                    COLUMN_NAME_VOTE_AVERAGE
            };
            String selection = COLUMN_NAME_MOVIE_ID + " = ?";
            String[] selectionArgs = {String.valueOf(id)};
            cursor = db.query(
                    TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            if (cursor.moveToFirst()) {
                movieData = setMovieData(cursor);
            }
        } finally {
            cursor.close();
            db.close();
        }
        return movieData;
    }

    private MovieData setMovieData(Cursor cursor) {
        MovieData movieData = new MovieData();
        movieData.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_MOVIE_ID)));
        movieData.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_POSTER_PATH)));
        movieData.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_OVERVIEW)));
        movieData.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_RELEASE_DATE)));
        movieData.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_NAME_VOTE_AVERAGE)));
        movieData.setOriginalTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_ORIGINAL_TITLE)));
        movieData.setTitle(cursor.getColumnName(cursor.getColumnIndexOrThrow(COLUMN_NAME_TITLE)));
        movieData.setBackdropPath(cursor.getColumnName(cursor.getColumnIndexOrThrow(COLUMN_NAME_BACKDROP_PATH)));
        return movieData;
    }

    public boolean deleteMovieFromFav(int movieId) {
        SQLiteDatabase db = getWritableDatabase();
        int rowsAffected = 0;
        try {
            String selection = COLUMN_NAME_MOVIE_ID + " = ?";
            String[] selectionArgs = {String.valueOf(movieId)};
            rowsAffected = db.delete(TABLE_NAME, selection, selectionArgs);
        } finally {
            db.close();
        }
        return rowsAffected > 0;
    }
}
