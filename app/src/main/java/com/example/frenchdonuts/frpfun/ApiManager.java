package com.example.frenchdonuts.frpfun;

import android.util.Log;

import com.example.frenchdonuts.frpfun.com.example.frenchdonuts.frpfun.twittermodels.TwitterSearchResponse;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by frenchdonuts on 7/18/14.
 */
public class ApiManager {

    private static final OkHttpClient httpClient = new OkHttpClient().setProtocols(Arrays.asList(Protocol.HTTP_1_1));

    private interface TwitterSearchService {
        @GET("/search/tweets.json")
        TwitterSearchResponse getTweets(
                @Header("Authorization") String accessToken,
                @Query("q") String from
        );
    }

    private static final RestAdapter restAdapter = new RestAdapter.Builder()
            .setClient(new OkClient(httpClient))
            .setEndpoint("https://api.twitter.com/1.1")
            //.setLogLevel(RestAdapter.LogLevel.FULL)
            .setLog(new AndroidLog("TwitterSearchApi"))
            .build();

    private static final TwitterSearchService twitterSearch = restAdapter.create(TwitterSearchService.class);

    public static Observable<TwitterSearchResponse> getTweets(final String from) {
        return Observable.create((Subscriber<? super TwitterSearchResponse> subscriber) -> {
               try {
                   subscriber.onNext(twitterSearch.getTweets("Bearer " + FRPFunActivity.ACCESS_TOKEN, from));
                   subscriber.onCompleted();
               } catch (Exception e) {
                   subscriber.onError(e);
               }

        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }


    private interface TwitterTokenService {
        @FormUrlEncoded
        @POST("/oauth2/token")
        BearerToken getBearerToken(
                @Header("Authorization") String authorization,
                @Field("grant_type") String grant_type
        );
    }

    private static final RestAdapter apiTwitterRestAdapter = new RestAdapter.Builder()
            .setClient(new OkClient(httpClient))
            .setEndpoint("https://api.twitter.com")
            //.setLogLevel(RestAdapter.LogLevel.FULL)
            .setLog(new AndroidLog("TwitterTokenService"))
            .setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    //request.addHeader("User-Agent", "FRPExample");
                }
            })
            .build();

    private static final TwitterTokenService twitterTokenService = apiTwitterRestAdapter.create(TwitterTokenService.class);

    public static Observable<BearerToken> getBearerToken() {
        return Observable.create((Subscriber<? super BearerToken> subscriber) -> {
                try {
                    final String authorization = "Basic " + FRPFunActivity.CONSUMER_KEY_AND_SECRET_64;
                    // Check to see if we have twitter access token in shared prefs
                    if(!FRPFunActivity.preferences.contains("access_token")) {
                        BearerToken token = twitterTokenService.getBearerToken(authorization, "client_credentials");
                        FRPFunActivity.preferences.edit().putString("access_token", token.accessToken).commit();
                        subscriber.onNext(token);
                    } else {
                        subscriber.onNext(new BearerToken(FRPFunActivity.preferences.getString("access_token", "")));
                    }

                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
        }).subscribeOn(Schedulers.newThread());
    }
}
