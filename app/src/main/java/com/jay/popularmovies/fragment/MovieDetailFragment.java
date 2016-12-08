package com.jay.popularmovies.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jay.popularmovies.R;
import com.jay.popularmovies.adapter.MovieTrailerAdapter;
import com.jay.popularmovies.constant.Const;
import com.jay.popularmovies.model.MovieData;
import com.jay.popularmovies.model.TrailerData;
import com.jay.popularmovies.model.TrailerListItemData;
import com.jay.popularmovies.retrofit.MoviesService;
import com.jay.popularmovies.retrofit.RetrofitHelper;
import com.jay.popularmovies.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.movie_short_image)
    ImageView movieShortImage;
    @BindView(R.id.movie_thumbnail_iv)
    ImageView movieThumbnailIV;
    @BindView(R.id.synopsisText)
    TextView synopsisText;
    @BindView(R.id.movie_title_tv)
    TextView movieTitleText;
    @BindView(R.id.rating_value_text)
    TextView ratingText;
    @BindView(R.id.release_date_value_tv)
    TextView releaseDateValueTV;
    @BindView(R.id.trailer_rv)
    RecyclerView trailerRV;

    private MovieData movieData;
    private MovieTrailerAdapter trailerAdapter;

    public MovieDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
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

    private void initialize() {
        initToolbar();
        initializeTrailerRecyclerView();
    }

    private void initializeTrailerRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        trailerRV.setLayoutManager(linearLayoutManager);
        trailerAdapter = new MovieTrailerAdapter(this);
        trailerRV.setAdapter(trailerAdapter);
    }

    private void getMovieTrailers() {
        MoviesService service = RetrofitHelper.getInstance().getRetrofit().create(MoviesService.class);
        Call<TrailerData> data = service.getTrailerList(movieData.getId());
        data.enqueue(new Callback<TrailerData>() {
            @Override
            public void onResponse(Call<TrailerData> call, Response<TrailerData> response) {
                processTrailerResponse(response);
            }

            @Override
            public void onFailure(Call<TrailerData> call, Throwable t) {

            }
        });
    }

    private void processTrailerResponse(Response<TrailerData> response) {
        List<TrailerListItemData> trailerData = response.body().getResults();
        trailerAdapter.setList(trailerData);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
        getData();
        setFieldValues();
        getMovieTrailers();
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
