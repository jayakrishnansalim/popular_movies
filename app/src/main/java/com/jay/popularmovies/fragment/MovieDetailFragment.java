package com.jay.popularmovies.fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jay.popularmovies.R;
import com.jay.popularmovies.adapter.MovieReviewAdapter;
import com.jay.popularmovies.adapter.MovieTrailerAdapter;
import com.jay.popularmovies.constant.Const;
import com.jay.popularmovies.database.PopularMovieDBHelper;
import com.jay.popularmovies.model.MovieData;
import com.jay.popularmovies.model.ReviewData;
import com.jay.popularmovies.model.ReviewListItemData;
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

import static com.jay.popularmovies.R.drawable.ic_star_black_24dp;
import static com.jay.popularmovies.R.drawable.ic_star_border_black_24dp;


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
    @BindView(R.id.review_rv)
    RecyclerView reviewRV;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    private static final int TRANSLATE_DURATION_MILLIS = 200;

    private MovieData movieData;
    private MovieTrailerAdapter trailerAdapter;
    private MovieReviewAdapter reviewAdapter;

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
        setScrollChangeListener();
        setFABClickListener();
    }

    /**
     * Method for setting FAB click listener
     */
    private void setFABClickListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopularMovieDBHelper dbHelper = new PopularMovieDBHelper(getContext());
                if (!isAlreadyFavorited()) {
                    dbHelper.insertFavMovie(movieData);
                    setFABIcon(ic_star_black_24dp);
                    Toast.makeText(getContext(), R.string.favorited, Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.deleteMovieFromFav(movieData.getId());
                    setFABIcon(ic_star_border_black_24dp);
                    Toast.makeText(getContext(), R.string.unfavorited, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Method for setting scroll change listener for nested scroll view
     */
    private void setScrollChangeListener() {
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int translationY = 0;
                if ((scrollY - oldScrollY) > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                        int height = fab.getHeight();
                        translationY = height + getMarginBottom();
                        showOrHideFAB(translationY);
                    }

                } else if ((scrollY - oldScrollY) < 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                        showOrHideFAB(-translationY);
                    }
                }
            }
        });
    }

    /**
     * Method for translating fab in order to show and hiding it
     *
     * @param y - distance to translate
     */
    private void showOrHideFAB(int y) {
        fab.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(TRANSLATE_DURATION_MILLIS)
                .translationY(y);
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

    private int getMarginBottom() {
        int marginBottom = 0;
        final ViewGroup.LayoutParams layoutParams = fab.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }
        return marginBottom;
    }

    /**
     * Method for initializing views
     */
    private void initialize() {
        initToolbar();
        initializeTrailerRecyclerView();
        initializeReviewRecyclerView();
    }

    /**
     * Method for initializing FAB
     */
    private void initializeFAB() {
        if (isAlreadyFavorited()) {
            setFABIcon(ic_star_black_24dp);
        } else {
            setFABIcon(ic_star_border_black_24dp);
        }
    }

    /**
     * Method for changing FAB icon when user favorite or unfavorite movie
     * @param resId - Drawable resource id
     */
    @SuppressWarnings("deprecation")
    private void setFABIcon(@DrawableRes int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fab.setImageDrawable(getResources().getDrawable(resId, getContext().getTheme()));
        } else {
            fab.setImageDrawable(getResources().getDrawable(resId));
        }
    }

    /**
     * Method for checking if the movie has been already favorited
     * @return boolean - status of the check
     */
    private boolean isAlreadyFavorited() {
        PopularMovieDBHelper dbHelper = new PopularMovieDBHelper(getContext());
        MovieData dbMovieData = dbHelper.getMovieDataById(movieData.getId());
        return (dbMovieData != null);
    }

    /**
     * Method for initializing Trailer recycler view
     */
    private void initializeTrailerRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        trailerRV.setLayoutManager(linearLayoutManager);
        trailerAdapter = new MovieTrailerAdapter(this);
        trailerRV.setAdapter(trailerAdapter);
    }

    /**
     * Method for initializing review recycler view
     */
    private void initializeReviewRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        reviewRV.setLayoutManager(linearLayoutManager);
        reviewAdapter = new MovieReviewAdapter(this);
        reviewRV.setAdapter(reviewAdapter);
    }

    /**
     * Method responsible for fetching movie trailers from server
     */
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

    /**
     * Method for fetching movie reviews from server
     */
    private void getMovieReviews() {
        MoviesService service = RetrofitHelper.getInstance().getRetrofit().create(MoviesService.class);
        Call<ReviewData> data = service.getReviewList(movieData.getId());
        data.enqueue(new Callback<ReviewData>() {
            @Override
            public void onResponse(Call<ReviewData> call, Response<ReviewData> response) {
                processReviewResponse(response);
            }

            @Override
            public void onFailure(Call<ReviewData> call, Throwable t) {

            }
        });
    }

    /**
     * Method responsible for processing movie review response
     * @param response - Response from server
     */
    private void processReviewResponse(Response<ReviewData> response) {
        List<ReviewListItemData> reviewData = response.body().getResults();
        reviewAdapter.setList(reviewData);
    }

    /**
     * Method responsible for processing movie trailer response
     * @param response - Response from the server
     */
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
        getMovieReviews();
        initializeFAB();
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

    /**
     * Method for initializing toolbar
     */
    private void initToolbar() {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        if (appCompatActivity.getSupportActionBar() != null) {
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
