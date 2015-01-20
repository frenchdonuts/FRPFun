
package com.example.frenchdonuts.frpfun.com.example.frenchdonuts.frpfun.twittermodels;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class TwitterSearchResponse {

    @Expose
    public List<Status> statuses = new ArrayList<Status>();
    @Expose
    public Integer count;

}
