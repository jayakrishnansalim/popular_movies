package com.jay.popularmovies.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jay.popularmovies.R;
import com.jay.popularmovies.activity.MovieDetailActivity;
import com.jay.popularmovies.constant.Const;
import com.jay.popularmovies.model.MovieData;
import com.jay.popularmovies.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for showing popular movies
 * Created by JK on 24/08/16.
 */
public class PopularMoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int LIST_INITIAL_SIZE = 20;

    private Fragment fragment;
    private List<MovieData> movieDataList;

    public PopularMoviesAdapter(Fragment fragment) {
        this.fragment = fragment;
        movieDataList = new ArrayList<>(LIST_INITIAL_SIZE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.movie_adapter_layout, parent, false);
        return new PopularMoviesViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PopularMoviesViewHolder) {
            PopularMoviesViewHolder viewHolder = (PopularMoviesViewHolder) holder;
            MovieData movieData = movieDataList.get(position);
            setFields(viewHolder, movieData);
        }
    }

    private void setFields(PopularMoviesViewHolder viewHolder, MovieData data) {
        Util.loadImage(viewHolder.movieThumbnail, data.getPosterPath(), fragment.getActivity());
        viewHolder.movieTitleTV.setText(data.getTitle());
    }


    @Override
    public int getItemCount() {
        return movieDataList.size();
    }

    public void setMovieDataList(List<MovieData> movieDataList, boolean clearExistingData) {
        if (clearExistingData) {
            this.movieDataList.clear();
        }
        this.movieDataList.addAll(movieDataList);
        notifyDataSetChanged();
    }

    private static class PopularMoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView movieThumbnail;
        private TextView movieTitleTV;
        private PopularMoviesAdapter adapter;

        public PopularMoviesViewHolder(View itemView, PopularMoviesAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            movieThumbnail = (ImageView) itemView.findViewById(R.id.movie_thumbnail_iv);
            movieTitleTV = (TextView) itemView.findViewById(R.id.movie_title_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Activity activity = adapter.fragment.getActivity();
            MovieData movieData = adapter.movieDataList.get(getAdapterPosition());
            Intent detailActivityIntent = new Intent(activity, MovieDetailActivity.class);
            detailActivityIntent.putExtra(Const.KEY_ORIGINAL_TITLE, movieData.getOriginalTitle());
            detailActivityIntent.putExtra(Const.KEY_IMAGE_THUMBNAIL, movieData.getPosterPath());
            detailActivityIntent.putExtra(Const.KEY_PLOT_SYNOPSIS, movieData.getOverview());
            detailActivityIntent.putExtra(Const.KEY_AVERAGE_RATING, movieData.getVoteAverage());
            detailActivityIntent.putExtra(Const.KEY_RELEASE_DATE, movieData.getReleaseDate());
            activity.startActivity(detailActivityIntent);
        }
    }
}
