package com.jay.popularmovies.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jay.popularmovies.R;
import com.jay.popularmovies.constant.Const;
import com.jay.popularmovies.model.MovieData;
import com.jay.popularmovies.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView movieShortImage;
    private ImageView movieThumbnailIV;
    private TextView synopsisText;
    private TextView movieTitleText;
    private TextView ratingText;
    private TextView releaseDateValueTV;

    private MovieData movieData;

    public MovieDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        checkNetworkState();
    }

    /**
     * Method responsible for checking the network state
     */
    private void checkNetworkState() {
        if (!Util.isOnline(getActivity())) {
            Toast.makeText(getActivity(), R.string.no_network_connectivity,
                    Toast.LENGTH_LONG).show();
        }
    }

    private void initialize(View view) {
        initializeViews(view);
        initToolbar();
    }

    private void initializeViews(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        movieThumbnailIV = (ImageView) view.findViewById(R.id.movie_thumbnail_iv);
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        movieShortImage = (ImageView) view.findViewById(R.id.movie_short_image);
        synopsisText = (TextView) view.findViewById(R.id.synopsisText);
        movieTitleText = (TextView) view.findViewById(R.id.movie_title_tv);
        ratingText = (TextView) view.findViewById(R.id.rating_value_text);
        releaseDateValueTV = (TextView) view.findViewById(R.id.release_date_value_tv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
        getData();
        setFieldValues();
    }

    /**
     * Method for extracting data from intent obj
     */
    private void getData() {
        movieData = getActivity().getIntent().getParcelableExtra(Const.KEY_MOVIE_DATA);
    }

    /**
     * Method for setting values to UI fields.
     */
    private void setFieldValues() {
        Util.loadImage(movieThumbnailIV, movieData.getBackdropPath(), getActivity(), true);
        collapsingToolbarLayout.setTitle(movieData.getOriginalTitle());
        Util.loadImage(movieShortImage, movieData.getPosterPath(), getActivity(), false);
        synopsisText.setText(movieData.getOverview());
        movieTitleText.setText(movieData.getTitle());
        ratingText.setText(String.valueOf(movieData.getVoteAverage()));
        releaseDateValueTV.setText(movieData.getReleaseDate());
    }


    private void initToolbar() {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        if (appCompatActivity.getSupportActionBar() != null) {
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
