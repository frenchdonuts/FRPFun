
package com.example.frenchdonuts.frpfun.com.example.frenchdonuts.frpfun.twittermodels;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Hashtag {

    @Expose
    public String text;
    @Expose
    public List<Integer> indices = new ArrayList<Integer>();

}
