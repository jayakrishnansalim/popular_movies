package com.jay.popularmovies.model;

/**
 * DTO for transferring review list item data
 * Created by Jay on 06/12/16.
 */

public class ReviewListItemData {

    private String id;
    private String author;
    private String content;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "ReviewListItemData{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
