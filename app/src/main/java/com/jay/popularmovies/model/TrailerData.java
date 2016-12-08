package com.jay.popularmovies.model;

import java.util.List;

/**
 * Model class for transferring trailer data.
 * Created by Jay on 06/12/16.
 */

public class TrailerData {

    private int id;
    private List<TrailerListItemData> results;

    public int getId() {
        return id;
    }

    public List<TrailerListItemData> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "TrailerData{" +
                "id=" + id +
                ", results=" + results +
                '}';
    }
}
