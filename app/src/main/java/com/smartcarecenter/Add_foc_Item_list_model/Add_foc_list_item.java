/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.Add_foc_Item_list_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Add_foc_list_item {

    public Add_foc_list_item() {
    }
    public Add_foc_list_item(String itemCd, String name, String itemTypeCd, String categoryCd, String usingMatrix,
                             String mps, String itemsPerPress, Integer shelfLife, String lifeSpan, String remarks,
                             Boolean stsActive, String categoryName, String imageFullURL, String imageThumbFullURL,
                             Integer lastImpression, String stsActiveInfo,String unitName) {
        ItemCd = itemCd;UnitName = unitName;

        Name = name;
        ItemTypeCd = itemTypeCd;
        CategoryCd = categoryCd;
        UsingMatrix = usingMatrix;
        Mps = mps;
        ItemsPerPress = itemsPerPress;
        ShelfLife = shelfLife;
        LifeSpan = lifeSpan;
        Remarks = remarks;
        StsActive = stsActive;
        CategoryName = categoryName;
        ImageFullURL = imageFullURL;
        ImageThumbFullURL = imageThumbFullURL;
        LastImpression = lastImpression;
        StsActiveInfo = stsActiveInfo;
    }
    public String getItemCd() {
        return ItemCd;
    }

    public void setItemCd(String itemCd) {
        ItemCd = itemCd;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getItemTypeCd() {
        return ItemTypeCd;
    }

    public void setItemTypeCd(String itemTypeCd) {
        ItemTypeCd = itemTypeCd;
    }

    public String getCategoryCd() {
        return CategoryCd;
    }

    public void setCategoryCd(String categoryCd) {
        CategoryCd = categoryCd;
    }

    public String getUsingMatrix() {
        return UsingMatrix;
    }

    public void setUsingMatrix(String usingMatrix) {
        UsingMatrix = usingMatrix;
    }

    public String getMps() {
        return Mps;
    }

    public void setMps(String mps) {
        Mps = mps;
    }

    public String getItemsPerPress() {
        return ItemsPerPress;
    }

    public void setItemsPerPress(String itemsPerPress) {
        ItemsPerPress = itemsPerPress;
    }

    public Integer getShelfLife() {
        return ShelfLife;
    }

    public void setShelfLife(Integer shelfLife) {
        ShelfLife = shelfLife;
    }

    public String getLifeSpan() {
        return LifeSpan;
    }

    public void setLifeSpan(String lifeSpan) {
        LifeSpan = lifeSpan;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public Boolean getStsActive() {
        return StsActive;
    }

    public void setStsActive(Boolean stsActive) {
        StsActive = stsActive;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getImageFullURL() {
        return ImageFullURL;
    }

    public void setImageFullURL(String imageFullURL) {
        ImageFullURL = imageFullURL;
    }

    public String getImageThumbFullURL() {
        return ImageThumbFullURL;
    }

    public void setImageThumbFullURL(String imageThumbFullURL) {
        ImageThumbFullURL = imageThumbFullURL;
    }

    public Integer getLastImpression() {
        return LastImpression;
    }

    public void setLastImpression(Integer lastImpression) {
        LastImpression = lastImpression;
    }

    public String getStsActiveInfo() {
        return StsActiveInfo;
    }

    public void setStsActiveInfo(String stsActiveInfo) {
        StsActiveInfo = stsActiveInfo;
    }
    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    @Expose
    @SerializedName("ItemCd")
    private String ItemCd;
    @Expose
    @SerializedName("Name")
    private String Name;
    @Expose
    @SerializedName("ItemTypeCd")
    private String ItemTypeCd;
    @Expose
    @SerializedName("CategoryCd")
    private String CategoryCd;
    @Expose
    @SerializedName("UsingMatrix")
    private String UsingMatrix;
    @Expose
    @SerializedName("Mps")
    private String Mps;
    @Expose
    @SerializedName("ItemsPerPress")
    private String ItemsPerPress;
    @Expose
    @SerializedName("ShelfLife")
    private Integer ShelfLife;
    @Expose
    @SerializedName("LifeSpan")
    private String LifeSpan;
    @Expose
    @SerializedName("Remarks")
    private String Remarks;
    @Expose
    @SerializedName("StsActive")
    private Boolean StsActive;
    @Expose
    @SerializedName("CategoryName")
    private String CategoryName;
    @Expose
    @SerializedName("ImageFullURL")
    private String ImageFullURL;
    @Expose
    @SerializedName("ImageThumbFullURL")
    private String ImageThumbFullURL;
    @Expose
    @SerializedName("LastImpression")
    private Integer LastImpression;
    @Expose
    @SerializedName("StsActiveInfo")
    private String StsActiveInfo;



    @Expose
    @SerializedName("UnitName")
    private String UnitName;





}



