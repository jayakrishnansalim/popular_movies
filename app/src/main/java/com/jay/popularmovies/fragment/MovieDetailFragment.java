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

import com.jay.popularmovies.R;
import com.jay.popularmovies.constant.Const;
import com.jay.popularmovies.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {

    private static final int AVG_RATING_DEFAULT_VAL = 0;

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView movieShortImage;
    private ImageView movieThumbnailIV;
    private TextView synopsisText;
    private TextView movieTitleText;
    private TextView ratingText;
    private TextView releaseDateValueTV;

    private String originalTitle;
    private String movieThumbnail;
    private String backDropPath;
    private String plotSynopsis;
    private String title;
    private String releaseDate;
    private double averageRating;

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
        originalTitle = getActivity().getIntent().getStringExtra(Const.KEY_ORIGINAL_TITLE);
        movieThumbnail = getActivity().getIntent().getStringExtra(Const.KEY_IMAGE_THUMBNAIL);
        plotSynopsis = getActivity().getIntent().getStringExtra(Const.KEY_PLOT_SYNOPSIS);
        averageRating = getActivity().getIntent().getDoubleExtra(Const.KEY_AVERAGE_RATING,
                AVG_RATING_DEFAULT_VAL);
        releaseDate = getActivity().getIntent().getStringExtra(Const.KEY_RELEASE_DATE);
        backDropPath = getActivity().getIntent().getStringExtra(Const.KEY_BACK_DROP_PATH);
        title = getActivity().getIntent().getStringExtra(Const.KEY_TITLE);
    }

    /**
     * Method for setting values to UI fields.
     */
    private void setFieldValues() {
        Util.loadImage(movieThumbnailIV, backDropPath, getActivity(), true);
        collapsingToolbarLayout.setTitle(originalTitle);
        Util.loadImage(movieShortImage, movieThumbnail, getActivity(), false);
        synopsisText.setText(plotSynopsis);
        movieTitleText.setText(title);
        ratingText.setText(String.valueOf(averageRating));
        releaseDateValueTV.setText(releaseDate);
    }


    private void initToolbar() {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        if (appCompatActivity.getSupportActionBar() != null) {
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
