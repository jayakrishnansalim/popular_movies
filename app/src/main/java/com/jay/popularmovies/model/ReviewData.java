package com.jay.popularmovies.model;

import java.util.List;

/**
 * DTO for transferring Review data
 * Created by Jay on 06/12/16.
 */

public class ReviewData {

    private int id;
    private int page;
    private List<ReviewListItemData> results;

    public int getId() {
        return id;
    }

    public int getPage() {
        return page;
    }

    public List<ReviewListItemData> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "ReviewData{" +
                "id=" + id +
                ", page=" + page +
                ", results=" + results +
                '}';
    }
}
