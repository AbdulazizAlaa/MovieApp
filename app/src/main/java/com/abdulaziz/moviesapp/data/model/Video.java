package com.abdulaziz.moviesapp.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abdulaziz on 4/3/17.
 */

public class Video {

    @SerializedName("key")
    private String url;

    @SerializedName("site")
    private String site;

    public Video(String url, String site) {
        this.url = url;
        this.site = site;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
