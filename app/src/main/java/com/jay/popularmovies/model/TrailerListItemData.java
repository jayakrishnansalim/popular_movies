package com.jay.popularmovies.model;

/**
 * DTO for movie trailer list item data
 * Created by Jay on 06/12/16.
 */

public class TrailerListItemData {

    private String key;
    private String site;

    public String getKey() {
        return key;
    }

    public String getSite() {
        return site;
    }

    @Override
    public String toString() {
        return "TrailerData{" +
                "key='" + key + '\'' +
                ", site='" + site + '\'' +
                '}';
    }
}
