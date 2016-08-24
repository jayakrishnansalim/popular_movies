package com.jay.popularmovies.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jay.popularmovies.R;
import com.jay.popularmovies.model.MovieData;
import com.jay.popularmovies.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for showing popular movies
 * Created by JK on 24/08/16.
 */
public class PopularMoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Fragment fragment;
    private List<MovieData> movieDataList;

    public PopularMoviesAdapter(Fragment fragment) {
        this.fragment = fragment;
        movieDataList = new ArrayList<>(20);
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

    public void setMovieDataList(List<MovieData> movieDataList) {
        this.movieDataList.addAll(movieDataList);
        notifyDataSetChanged();
    }

    private static class PopularMoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView movieThumbnail;
        private TextView movieTitleTV;

        public PopularMoviesViewHolder(View itemView, PopularMoviesAdapter adapter) {
            super(itemView);
            movieThumbnail = (ImageView) itemView.findViewById(R.id.movie_thumbnail_iv);
            movieTitleTV = (TextView) itemView.findViewById(R.id.movie_title_tv);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
