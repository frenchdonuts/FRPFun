
package com.example.frenchdonuts.frpfun.com.example.frenchdonuts.frpfun.twittermodels;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medium {

    @SerializedName("id_str")
    @Expose
    public String idStr;
    @Expose
    public List<Integer> indices = new ArrayList<Integer>();
    @SerializedName("media_url")
    @Expose
    public String mediaUrl;
    @SerializedName("media_url_https")
    @Expose
    public String mediaUrlHttps;
    @Expose
    public String url;
    @SerializedName("display_url")
    @Expose
    public String displayUrl;
    @SerializedName("expanded_url")
    @Expose
    public String expandedUrl;
    @Expose
    public String type;

}
