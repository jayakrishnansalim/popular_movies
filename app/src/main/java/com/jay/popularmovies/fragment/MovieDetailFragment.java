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

    private Toolbar toolbar;
    private ImageView movieThumbnailIV;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView movieShortImage;
    private TextView synopsisText;

    private String originalTitle;
    private String movieThumbnail;
    private String plotSynopsis;
    private double averageRating;
    private String releaseDate;

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
        getData();
        setFieldValues();
    }

    private void getData() {
        originalTitle = getActivity().getIntent().getStringExtra(Const.KEY_ORIGINAL_TITLE);
        movieThumbnail = getActivity().getIntent().getStringExtra(Const.KEY_IMAGE_THUMBNAIL);
        plotSynopsis = getActivity().getIntent().getStringExtra(Const.KEY_PLOT_SYNOPSIS);
        averageRating = getActivity().getIntent().getDoubleExtra(Const.KEY_AVERAGE_RATING, 0);
        releaseDate = getActivity().getIntent().getStringExtra(Const.KEY_RELEASE_DATE);
    }

    private void setFieldValues() {
        Util.loadImage(movieThumbnailIV, movieThumbnail, getActivity());
        collapsingToolbarLayout.setTitle(originalTitle);
        Util.loadImage(movieShortImage, movieThumbnail, getActivity());
        synopsisText.setText(plotSynopsis);
    }

    @SuppressWarnings("ConstantConditions")
    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
