package com.abdulaziz.moviesapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by abdulaziz on 4/3/17.
 */

public class ReviewResponse {

    @SerializedName("results")
    private ArrayList<Review> reviews;

    public ReviewResponse(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public Review getReview(int position) {
        return reviews.get(position);
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
}
