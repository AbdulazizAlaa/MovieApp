package com.abdulaziz.moviesapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdulaziz on 3/3/17.
 */

public class Response {

    @SerializedName("results")
    private List<Movie> movies;

    public Response(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
