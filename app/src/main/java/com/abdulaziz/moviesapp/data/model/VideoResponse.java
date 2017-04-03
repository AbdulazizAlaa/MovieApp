package com.abdulaziz.moviesapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by abdulaziz on 4/3/17.
 */

public class VideoResponse {

    @SerializedName("results")
    private ArrayList<Video> videos;

    public VideoResponse(ArrayList<Video> videos) {
        this.videos = videos;
    }

    public Video getVideo(int position) {
        return videos.get(position);
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }
}
