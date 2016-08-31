package com.jay.popularmovies.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jay.popularmovies.retrofit.APIConstants;

/**
 * Util class for popular movies app
 * Created by JK on 24/08/16.
 */
public class Util {

    public static void loadImage(ImageView imageView, String url, Context context, boolean large) {
        String baseURL;
        if (large) {
            baseURL = APIConstants.LARGE_IMAGE_BASE_URL;
        } else {
            baseURL = APIConstants.IMAGE_BASE_URL;
        }
        Glide.with(context).load(baseURL + url)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
