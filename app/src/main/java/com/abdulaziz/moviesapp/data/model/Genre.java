package com.abdulaziz.moviesapp.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abdulaziz on 3/27/17.
 */

public class Genre {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public Genre(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
