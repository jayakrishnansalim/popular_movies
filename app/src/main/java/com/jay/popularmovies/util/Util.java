package com.jay.popularmovies.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jay.popularmovies.retrofit.APIConstants;

/**
 * Util class for popular movies app
 * Created by Jay on 24/08/16.
 */
public class Util {

    private static final float THUMBNAIL_SIZE_MULTIPLIER = 0.5f;

    /**
     * Method responsible for loading image to image view
     *
     * @param imageView - ImageView
     * @param url       - Image URL
     * @param context   - Context
     * @param large     - Whether large thumbnail needs to be fetched.
     */
    public static void loadImage(ImageView imageView, String url, Context context, boolean large) {
        String baseURL;
        if (large) {
            baseURL = APIConstants.LARGE_IMAGE_BASE_URL;
        } else {
            baseURL = APIConstants.IMAGE_BASE_URL;
        }
        Glide.with(context).load(baseURL + url)
                .thumbnail(THUMBNAIL_SIZE_MULTIPLIER)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * Method responsible for checking whether network connection is there or not.
     *
     * @param context - Context
     * @return - Network connection status
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
