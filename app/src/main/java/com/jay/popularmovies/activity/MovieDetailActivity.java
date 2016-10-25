package com.jay.popularmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jay.popularmovies.R;
import com.jay.popularmovies.fragment.MovieDetailFragment;

/**
 * Activity to show movie details
 */
public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        loadFragment(savedInstanceState);
    }

    /**
     * Method for loading Movie detail fragment
     *
     * @param savedInstanceState - saved instance state bundle
     */
    private void loadFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MovieDetailFragment())
                    .commit();
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        return super.getSupportParentActivityIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }
}
