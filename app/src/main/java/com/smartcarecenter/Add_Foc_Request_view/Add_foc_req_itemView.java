/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.Add_Foc_Request_view;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Add_foc_req_itemView {

    public Add_foc_req_itemView() {
    }


    public Add_foc_req_itemView(String itemCd, Integer position, Integer qty, String itemName,
                                String categoryName, String imageThumbFullURL, String imageFullURL) {
        ItemCd = itemCd;
        Position = position;
        Qty = qty;
        ItemName = itemName;
        CategoryName = categoryName;
        ImageThumbFullURL = imageThumbFullURL;
        ImageFullURL = imageFullURL;
    }

    public String getItemCd() {
        return ItemCd;
    }

    public void setItemCd(String itemCd) {
        ItemCd = itemCd;
    }

    public Integer getPosition() {
        return Position;
    }

    public void setPosition(Integer position) {
        Position = position;
    }

    public Integer getQty() {
        return Qty;
    }

    public void setQty(Integer qty) {
        Qty = qty;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getImageThumbFullURL() {
        return ImageThumbFullURL;
    }

    public void setImageThumbFullURL(String imageThumbFullURL) {
        ImageThumbFullURL = imageThumbFullURL;
    }

    public String getImageFullURL() {
        return ImageFullURL;
    }

    public void setImageFullURL(String imageFullURL) {
        ImageFullURL = imageFullURL;
    }
    @SerializedName("ItemCd")
    private String ItemCd;
    @Expose
    @SerializedName("Position")
    private Integer Position;
    @Expose
    @SerializedName("Qty")
    private Integer Qty;
    @Expose
    @SerializedName("ItemName")
    private String ItemName;
    @Expose
    @SerializedName("CategoryName")
    private String CategoryName;
    @Expose
    @SerializedName("ImageThumbFullURL")
    private String ImageThumbFullURL;
    @Expose
    @SerializedName("ImageFullURL")
    private String ImageFullURL;

}



