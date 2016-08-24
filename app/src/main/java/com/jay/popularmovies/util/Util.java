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

    public static void loadImage(ImageView imageView, String url, Context context) {
        Glide.with(context).load(APIConstants.IMAGE_BASE_URL + url)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
