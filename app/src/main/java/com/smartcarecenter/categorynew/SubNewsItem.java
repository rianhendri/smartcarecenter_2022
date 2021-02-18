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

import java.util.ArrayList;

public class SubNewsItem {
    String badge;
    Integer img;
    String menuname;
    String show;


    public SubNewsItem() {
    }


    public SubNewsItem(String badge, Integer img, String menuname, String show, String subCategoryCd, String categoryCd, String name) {
        this.badge = badge;
        this.img = img;
        this.menuname = menuname;
        this.show = show;
        SubCategoryCd = subCategoryCd;
        CategoryCd = categoryCd;
        Name = name;
    }

    public String getSubCategoryCd() {
        return SubCategoryCd;
    }

    public void setSubCategoryCd(String subCategoryCd) {
        SubCategoryCd = subCategoryCd;
    }

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

    @Expose
    @SerializedName("SubCategoryCd")
    private String SubCategoryCd;
    @Expose
    @SerializedName("CategoryCd")
    private String CategoryCd;
    @Expose
    @SerializedName("Name")
    private String Name;



}

