
package com.example.frenchdonuts.frpfun.com.example.frenchdonuts.frpfun.twittermodels;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Entities {

    @Expose
    public List<Hashtag> hashtags = new ArrayList<Hashtag>();
    @Expose
    public List<Object> symbols = new ArrayList<Object>();
    @Expose
    public List<Url> urls = new ArrayList<Url>();
    @SerializedName("user_mentions")
    @Expose
    public List<Object> userMentions = new ArrayList<Object>();
    @Expose
    public List<Medium> media = new ArrayList<Medium>();

}
