package com.jay.popularmovies.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit helper class
 * Created by Jay on 20/08/16.
 */
public class RetrofitHelper {

    private static RetrofitHelper retrofitHelper;

    private Retrofit retrofit;

    private RetrofitHelper() {
        retrofit = new Retrofit.Builder()
                .baseUrl(APIConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    /**
     * Method responsible for returning singleton instance of RetrofitHelper
     *
     * @return RetrofitHelper
     */
    public static RetrofitHelper getInstance() {
        if (retrofitHelper == null) {
            retrofitHelper = new RetrofitHelper();
        }
        return retrofitHelper;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
