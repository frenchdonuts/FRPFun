package com.example.frenchdonuts.frpfun;

import com.google.gson.annotations.SerializedName;

/**
 * Created by frenchdonuts on 7/18/14.
 */
public class BearerToken {

    @SerializedName("access_token")
    public String accessToken;

    public BearerToken() {}

    public BearerToken(String access_token) {
        accessToken = access_token;
    }
}
