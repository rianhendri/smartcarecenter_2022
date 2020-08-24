/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.listnews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsItem {
    public NewsItem(String bannerThumbURL, String bannerURL, String content, String date, String newsCd, String title) {
        this.bannerThumbURL = bannerThumbURL;
        this.bannerURL = bannerURL;
        this.content = content;
        this.date = date;
        this.newsCd = newsCd;
        this.title = title;
    }

    public String getBannerThumbURL() {
        return bannerThumbURL;
    }

    public void setBannerThumbURL(String bannerThumbURL) {
        this.bannerThumbURL = bannerThumbURL;
    }

    public String getBannerURL() {
        return bannerURL;
    }

    public void setBannerURL(String bannerURL) {
        this.bannerURL = bannerURL;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNewsCd() {
        return newsCd;
    }

    public void setNewsCd(String newsCd) {
        this.newsCd = newsCd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NewsItem() {
    }

    @Expose
    @SerializedName("bannerThumbURL")
    private String bannerThumbURL;
    @Expose
    @SerializedName("bannerURL")
    private String bannerURL;
    @Expose
    @SerializedName("content")
    private String content;
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("newsCd")
    private String newsCd;
    @Expose
    @SerializedName("title")
    private String title;


}

