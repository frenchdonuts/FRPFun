
package com.example.frenchdonuts.frpfun.com.example.frenchdonuts.frpfun.twittermodels;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Url {

    @Expose
    public String url;
    @SerializedName("expanded_url")
    @Expose
    public String expandedUrl;
    @SerializedName("display_url")
    @Expose
    public String displayUrl;
    @Expose
    public List<Integer> indices = new ArrayList<Integer>();

}
