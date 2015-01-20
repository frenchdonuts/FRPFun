
package com.example.frenchdonuts.frpfun.com.example.frenchdonuts.frpfun.twittermodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status {

    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @Expose
    public String text;
    @Expose
    public User user;
    @SerializedName("retweet_count")
    @Expose
    public Integer retweetCount;
    @SerializedName("favorite_count")
    @Expose
    public Integer favoriteCount;
    @Expose
    public Entities entities;

}
