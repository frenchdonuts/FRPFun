
package com.example.frenchdonuts.frpfun.com.example.frenchdonuts.frpfun.twittermodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @Expose
    public String name;
    @SerializedName("screen_name")
    @Expose
    public String screenName;
    @Expose
    public String description;
    @Expose
    public String url;
    @SerializedName("followers_count")
    @Expose
    public Integer followersCount;
    @SerializedName("friends_count")
    @Expose
    public Integer friendsCount;
    @SerializedName("listed_count")
    @Expose
    public Integer listedCount;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("favourites_count")
    @Expose
    public Integer favouritesCount;
    @SerializedName("utc_offset")
    @Expose
    public Integer utcOffset;
    @SerializedName("time_zone")
    @Expose
    public String timeZone;
    @SerializedName("profile_background_color")
    @Expose
    public String profileBackgroundColor;
    @SerializedName("profile_background_image_url")
    @Expose
    public String profileBackgroundImageUrl;
    @SerializedName("profile_background_image_url_https")
    @Expose
    public String profileBackgroundImageUrlHttps;
    @SerializedName("profile_image_url")
    @Expose
    public String profileImageUrl;
    @SerializedName("profile_image_url_https")
    @Expose
    public String profileImageUrlHttps;
    @SerializedName("profile_banner_url")
    @Expose
    public String profileBannerUrl;
    @SerializedName("profile_link_color")
    @Expose
    public String profileLinkColor;
    @SerializedName("profile_sidebar_border_color")
    @Expose
    public String profileSidebarBorderColor;
    @SerializedName("profile_sidebar_fill_color")
    @Expose
    public String profileSidebarFillColor;
    @SerializedName("profile_text_color")
    @Expose
    public String profileTextColor;

}
