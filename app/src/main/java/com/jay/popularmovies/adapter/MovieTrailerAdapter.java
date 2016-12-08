package com.jay.popularmovies.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jay.popularmovies.R;
import com.jay.popularmovies.model.TrailerListItemData;
import com.jay.popularmovies.retrofit.APIConstants;
import com.jay.popularmovies.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for showing movie trailers
 * Created by Jay on 06/12/16.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TrailerListItemData> list;
    private Fragment fragment;

    public MovieTrailerAdapter(Fragment fragment) {
        list = new ArrayList<>();
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.trailer_list_layout, parent, false);
        return new MovieTrailerViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MovieTrailerViewHolder) {
            MovieTrailerViewHolder movieTrailerViewHolder = (MovieTrailerViewHolder) holder;
            TrailerListItemData data = list.get(position);
            setFields(movieTrailerViewHolder, data);
        }
    }

    /**
     * Method responsible for setting the thumbnail to the image views in the adapter
     *
     * @param viewHolder - View Holder
     * @param data       - TrailerListItemData
     */
    private void setFields(MovieTrailerViewHolder viewHolder, TrailerListItemData data) {
        String thumbnailUrl = Util.getYouTubeThumbnail(data.getKey());
        Util.loadImage(viewHolder.trailerThumbnailIV, thumbnailUrl, fragment.getActivity());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Method responsible for setting the trailer list.
     *
     * @param list List<TrailerListItemData>
     */
    public void setList(List<TrailerListItemData> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @SuppressWarnings("WeakerAccess")
    public static class MovieTrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.trailer_thumbnail_iv)
        ImageView trailerThumbnailIV;

        private MovieTrailerAdapter adapter;

        MovieTrailerViewHolder(View itemView, MovieTrailerAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String uri = String.format(APIConstants.YOU_TUBE_URL, adapter.list.get(getAdapterPosition()).getKey());
            Intent youTubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            adapter.fragment.getActivity().startActivity(youTubeIntent);
        }
    }
}
