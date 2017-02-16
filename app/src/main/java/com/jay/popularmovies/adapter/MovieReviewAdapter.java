package com.jay.popularmovies.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jay.popularmovies.R;
import com.jay.popularmovies.model.ReviewListItemData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter class for movie review
 * Created by JK on 21/12/16.
 */

public class MovieReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Fragment fragment;
    private List<ReviewListItemData> list;

    public MovieReviewAdapter(Fragment fragment) {
        this.fragment = fragment;
        list = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.review_list_layout, parent, false);
        return new MovieReviewViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MovieReviewViewHolder) {
            MovieReviewViewHolder viewHolder = (MovieReviewViewHolder) holder;
            ReviewListItemData data = list.get(position);
            setFields(viewHolder, data);
        }
    }

    private void setFields(MovieReviewViewHolder viewHolder, ReviewListItemData data) {
        viewHolder.nameTV.setText(data.getAuthor());
        viewHolder.reviewTV.setText(data.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<ReviewListItemData> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public static class MovieReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MovieReviewAdapter adapter;
        @BindView(R.id.name_tv)
        TextView nameTV;
        @BindView(R.id.review_tv)
        TextView reviewTV;

        public MovieReviewViewHolder(View itemView, MovieReviewAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.adapter = adapter;
        }

        @Override
        public void onClick(View view) {

        }
    }
}
