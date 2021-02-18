/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.subnews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategoryNewsItem {
    String badge;
    Integer img;
    String menuname;
    String show;




    public SubCategoryNewsItem() {
    }


    public SubCategoryNewsItem(String subCategoryCd, String categoryCd, String name) {
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

