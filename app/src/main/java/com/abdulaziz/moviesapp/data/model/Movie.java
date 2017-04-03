package com.abdulaziz.moviesapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by abdulaziz on 3/3/17.
 */

public class Movie {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("overview")
    private String description;
    @SerializedName("release_date")
    private String date;
    @SerializedName("backdrop_path")
    private String backdrop;
    @SerializedName("vote_average")
    private String rating;
    @SerializedName("vote_count")
    private String count;
    @SerializedName("genres")
    private ArrayList<Genre> genres;

    public Movie(String id, String title, String poster, String rating){
        this(id, title, poster, "", "", "", rating, "", new ArrayList<Genre>());
    }

    public Movie(String id, String title, String poster, String description, String date, String backdrop, String rating, String count, ArrayList<Genre> genres) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.description = description;
        this.date = date;
        this.backdrop = backdrop;
        this.rating = rating;
        this.count = count;
        this.genres = genres;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterURLName() {
        return poster;
    }

    public String getPoster() {
        return "http://image.tmdb.org/t/p/w500"+poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackdrop() {
        return "http://image.tmdb.org/t/p/w500"+backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }
}
