package com.example.booklisting;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Book {

    private String title;
    private List<String> authors;
    private String language;
    private double averageRating;
    private int ratingsCount;
    private String previewLink;

    public Book() {
    }

    public Book(String title, JSONArray authors, String language, double averageRating, int ratingsCount, String previewLink) throws JSONException {
        this.title = title;
        this.authors = new ArrayList<>();
        setAuthors(authors);
        this.language = language.toUpperCase(Locale.ROOT);
        this.averageRating = averageRating;
        this.ratingsCount = ratingsCount;
        this.previewLink = previewLink;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthorsList() {
        return authors;
    }

    public String getAuthors() {
        StringBuilder authors = new StringBuilder();
        for(int i = 0; i<this.authors.size(); i++) {
            authors.append(this.authors.get(i));
            if(i < this.authors.size() - 1) {
                authors.append(" ");
            }
        }
        return authors.toString();
    }

    public String getLanguage() {
        return language;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getRatingsCount() {
        return ratingsCount;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public double getRating() {
        return (averageRating * 5) / ratingsCount;
    }

    private void setAuthors(JSONArray authorsJSONArray) throws JSONException {
        if (authorsJSONArray != null) {
            int len = authorsJSONArray.length();
            for (int i=0;i<len;i++){
                authors.add(authorsJSONArray.get(i).toString());
            }
        }
    }

}
