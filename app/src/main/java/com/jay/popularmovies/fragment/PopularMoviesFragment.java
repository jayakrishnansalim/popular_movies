package com.jay.popularmovies.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.jay.popularmovies.R;
import com.jay.popularmovies.adapter.PopularMoviesAdapter;
import com.jay.popularmovies.model.MovieResponseData;
import com.jay.popularmovies.retrofit.MoviesService;
import com.jay.popularmovies.retrofit.RetrofitHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularMoviesFragment extends Fragment {

    private static final int SPAN_COUNT = 2;

    private RecyclerView moviesRV;
    private ProgressBar progressBar;

    private PopularMoviesAdapter adapter;

    public PopularMoviesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_popular_movies, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
    }

    private void initialize(View view) {
        initializeViews(view);
        initializeRecyclerView();
        initializeAdapter();
        getMoviesList();
    }

    private void initializeViews(View view) {
        moviesRV = (RecyclerView) view.findViewById(R.id.movies_rv);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
    }

    private void initializeRecyclerView() {
        GridLayoutManager movieLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        moviesRV.setLayoutManager(movieLayoutManager);
    }

    private void initializeAdapter() {
        adapter = new PopularMoviesAdapter(this);
        moviesRV.setAdapter(adapter);
    }

    private void getMoviesList() {
        MoviesService service = RetrofitHelper.getInstance().getRetrofit().create(MoviesService.class);
        Call<MovieResponseData> data = service.getMovieList();
        data.enqueue(new Callback<MovieResponseData>() {
            @Override
            public void onResponse(Call<MovieResponseData> call, Response<MovieResponseData> response) {
                progressBar.setVisibility(View.GONE);
                processMovieResponse(response.body());
            }

            @Override
            public void onFailure(Call<MovieResponseData> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void processMovieResponse(MovieResponseData responseData) {
        adapter.setMovieDataList(responseData.getMovieDataList());
    }
}
