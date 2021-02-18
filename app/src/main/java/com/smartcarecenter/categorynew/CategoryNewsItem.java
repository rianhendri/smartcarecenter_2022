/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.categorynew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.smartcarecenter.ListSurvey.ListSurvey.ListSurveyAnswer_tem;
import com.smartcarecenter.subnews.SubCategoryNewsItem;

import java.util.ArrayList;

public class CategoryNewsItem {
    String badge;
    Integer img;
    String menuname;
    String show;


    public CategoryNewsItem() {
    }

    public CategoryNewsItem(String categoryCd, String name, int position, ArrayList<SubCategoryNewsItem> subCategories, String newsCd, String title, String downloadButtonURL, String downloadButtonText, boolean showDownloadButton) {
        CategoryCd = categoryCd;
        Name = name;
        Position = position;
        SubCategories = subCategories;
        this.newsCd = newsCd;
        this.title = title;
        this.downloadButtonURL = downloadButtonURL;
        this.downloadButtonText = downloadButtonText;
        this.showDownloadButton = showDownloadButton;
    }

    @Expose
    @SerializedName("CategoryCd")
    private String CategoryCd;
    @Expose
    @SerializedName("Name")
    private String Name;
    @Expose
    @SerializedName("Position")
    private int Position;
    @Expose
    @SerializedName("SubCategories")
    private ArrayList<SubCategoryNewsItem> SubCategories = null;
    @Expose
    @SerializedName("newsCd")
    private String newsCd;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("downloadButtonURL")
    private String downloadButtonURL;

    public String getCategoryCd() {
        return CategoryCd;
    }

    public void setCategoryCd(String categoryCd) {
        CategoryCd = categoryCd;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public ArrayList<SubCategoryNewsItem> getSubCategories() {
        return SubCategories;
    }

    public void setSubCategories(ArrayList<SubCategoryNewsItem> subCategories) {
        SubCategories = subCategories;
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

    public String getDownloadButtonURL() {
        return downloadButtonURL;
    }

    public void setDownloadButtonURL(String downloadButtonURL) {
        this.downloadButtonURL = downloadButtonURL;
    }

    public String getDownloadButtonText() {
        return downloadButtonText;
    }

    public void setDownloadButtonText(String downloadButtonText) {
        this.downloadButtonText = downloadButtonText;
    }

    public boolean isShowDownloadButton() {
        return showDownloadButton;
    }

    public void setShowDownloadButton(boolean showDownloadButton) {
        this.showDownloadButton = showDownloadButton;
    }

    @Expose
    @SerializedName("downloadButtonText")
    private String downloadButtonText;
    @Expose
    @SerializedName("showDownloadButton")
    private boolean showDownloadButton;



}

