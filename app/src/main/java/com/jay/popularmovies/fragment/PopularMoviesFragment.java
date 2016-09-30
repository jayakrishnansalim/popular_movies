package com.jay.popularmovies.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jay.popularmovies.R;
import com.jay.popularmovies.adapter.PopularMoviesAdapter;
import com.jay.popularmovies.model.MovieResponseData;
import com.jay.popularmovies.retrofit.MoviesService;
import com.jay.popularmovies.retrofit.RetrofitHelper;
import com.jay.popularmovies.util.Util;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularMoviesFragment extends Fragment implements OnItemSelectedListener {

    private static final int SPAN_COUNT = 2;
    private static final int POSITION_POPULAR = 0;
    private static final int POSITION_TOP_RATED = 1;

    private static final String SORT_TYPE_POPULAR = "Popular";
    private static final String SORT_TYPE_TOP_RATED = "Top Rated";

    private RecyclerView moviesRV;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private AppCompatSpinner sortTypeSpinner;

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
        setHasOptionsMenu(true);
        initialize(view);
    }

    /**
     * Method for initializing views
     *
     * @param view - View
     */
    private void initialize(View view) {
        initializeViews(view);
        initToolbar();
        initializeSpinner();
        initializeRecyclerView();
        initializeAdapter();
    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void initializeSpinner() {
        List<String> choices = new ArrayList<>(2);
        choices.add(getString(R.string.spinner_choice_popular));
        choices.add(getString(R.string.spinner_choice_top_rated));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.simple_spinner_item, choices);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortTypeSpinner.setOnItemSelectedListener(this);
        sortTypeSpinner.setAdapter(dataAdapter);
    }

    private void initializeViews(View view) {
        moviesRV = (RecyclerView) view.findViewById(R.id.movies_rv);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        sortTypeSpinner = (AppCompatSpinner) view.findViewById(R.id.sort_type_spinner);
    }

    private void initializeRecyclerView() {
        GridLayoutManager movieLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        moviesRV.setLayoutManager(movieLayoutManager);
    }

    private void initializeAdapter() {
        adapter = new PopularMoviesAdapter(this);
        moviesRV.setAdapter(adapter);
    }

    /**
     * Method for fetching movied list from API
     * @param sortType - sort type
     * @param clearExistingData - Whether existing data needs to be cleared or not
     */
    private void getMoviesList(String sortType, final boolean clearExistingData) {
        MoviesService service = RetrofitHelper.getInstance().getRetrofit().create(MoviesService.class);
        Call<MovieResponseData> data;
        switch (sortType) {
            case SORT_TYPE_POPULAR:
                data = service.getPopularMovieList();
                break;
            case SORT_TYPE_TOP_RATED:
                data = service.getTopRatedMovieList();
                break;
            default:
                data = service.getPopularMovieList();
        }
        data.enqueue(new Callback<MovieResponseData>() {
            @Override
            public void onResponse(Call<MovieResponseData> call, Response<MovieResponseData> response) {
                progressBar.setVisibility(View.GONE);
                processMovieResponse(response.body(), clearExistingData);
            }

            @Override
            public void onFailure(Call<MovieResponseData> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void processMovieResponse(MovieResponseData responseData, boolean clearExistingData) {
        adapter.setMovieDataList(responseData.getMovieDataList(), clearExistingData);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (Util.isOnline(getActivity())) {
            progressBar.setVisibility(View.VISIBLE);
            switch (position) {
                case POSITION_POPULAR:
                    getMoviesList(SORT_TYPE_POPULAR, true);
                    break;
                case POSITION_TOP_RATED:
                    getMoviesList(SORT_TYPE_TOP_RATED, true);
            }
        } else {
            Toast.makeText(getActivity(), R.string.no_network_connectivity, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
