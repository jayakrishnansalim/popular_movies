package com.jay.popularmovies.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.jay.popularmovies.constant.Const;
import com.jay.popularmovies.model.MovieData;
import com.jay.popularmovies.model.MovieListData;
import com.jay.popularmovies.model.MovieResponseData;
import com.jay.popularmovies.retrofit.MoviesService;
import com.jay.popularmovies.retrofit.RetrofitHelper;
import com.jay.popularmovies.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @BindView(R.id.movies_rv)
    RecyclerView moviesRV;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sort_type_spinner)
    AppCompatSpinner sortTypeSpinner;

    private NetworkReceiver networkReceiver;
    private PopularMoviesAdapter adapter;
    private MovieListData movieListData;

    private boolean hasActivityRecreated;

    public PopularMoviesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        initialize(savedInstanceState);
    }

    @Override
    public void onPause() {
        registerBroadcastReceiver();
        super.onPause();
    }

    @Override
    public void onResume() {
        unregisterBroadcastReceiver();
        super.onResume();
    }

    /**
     * Method for initializing views
     *
     * @param savedInstanceState - saved instance state
     */
    private void initialize(Bundle savedInstanceState) {
        initToolbar();
        initializeSpinner();
        initializeRecyclerView();
        initializeAdapter(savedInstanceState);
    }

    /**
     * Method for unregistering network broadcast receiver
     */
    public void unregisterBroadcastReceiver() {
        networkReceiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getContext().registerReceiver(networkReceiver, filter);
    }

    /**
     * Method for registering network broadcast receiver
     */
    public void registerBroadcastReceiver() {
        if (networkReceiver != null) {
            getContext().unregisterReceiver(networkReceiver);
        }
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

    private void initializeRecyclerView() {
        GridLayoutManager movieLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        moviesRV.setLayoutManager(movieLayoutManager);
    }

    private void initializeAdapter(Bundle savedInstanceState) {
        adapter = new PopularMoviesAdapter(this);
        moviesRV.setAdapter(adapter);
        if (savedInstanceState != null && savedInstanceState.containsKey(Const.KEY_MOVIE_DATA)) {
            hasActivityRecreated = true;
            movieListData = savedInstanceState.getParcelable(Const.KEY_MOVIE_DATA);
            if (movieListData != null) {
                adapter.setMovieDataList(movieListData.getMovieDataList(), true);
            }
        }
    }

    /**
     * Method for fetching movies list from API
     * @param sortType - sort type
     * @param clearExistingData - Whether existing data needs to be cleared or not
     */
    private void getMoviesList(String sortType, final boolean clearExistingData) {
        MoviesService service = RetrofitHelper.getInstance().getRetrofit().create(MoviesService.class);
        Call<MovieResponseData> data = getMovieResponseData(sortType, service);
        data.enqueue(new Callback<MovieResponseData>() {
            @Override
            public void onResponse(Call<MovieResponseData> call, Response<MovieResponseData> response) {
                progressBar.setVisibility(View.GONE);
                processMovieResponse(response.body(), clearExistingData);
            }

            @Override
            public void onFailure(Call<MovieResponseData> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.something_went_wrong_text, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private Call<MovieResponseData> getMovieResponseData(String sortType, MoviesService service) {
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
        return data;
    }

    private void processMovieResponse(MovieResponseData responseData, boolean clearExistingData) {
        List<MovieData> movieDataList = responseData.getMovieDataList();
        movieListData = new MovieListData();
        movieListData.setMovieDataList(movieDataList);
        adapter.setMovieDataList(movieDataList, clearExistingData);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (Util.isOnline(getActivity())) {
            if (!hasActivityRecreated) {
                fetchMovieList(position);
            } else {
                hasActivityRecreated = false;
            }
        } else {
            showNoConnectivityToast();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Const.KEY_MOVIE_DATA, movieListData);
        super.onSaveInstanceState(outState);
    }

    private void fetchMovieList(int position) {
        progressBar.setVisibility(View.VISIBLE);
        switch (position) {
            case POSITION_POPULAR:
                getMoviesList(SORT_TYPE_POPULAR, true);
                break;
            case POSITION_TOP_RATED:
                getMoviesList(SORT_TYPE_TOP_RATED, true);
        }
    }

    private void showNoConnectivityToast() {
        Toast.makeText(getActivity(), R.string.no_network_connectivity, Toast.LENGTH_LONG).show();
    }

    /**
     * Broadcast receiver which gets information about change in network state
     */
    public class NetworkReceiver extends BroadcastReceiver {

        @SuppressWarnings("deprecation")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                    if (movieListData == null) {
                        int selectedPosition = sortTypeSpinner.getSelectedItemPosition();
                        fetchMovieList(selectedPosition);
                    }
                } else if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.DISCONNECTED) {
                    showNoConnectivityToast();
                }
            }
        }
    }
}
