/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.reportsmenu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportsItem {


    public ReportsItem(String sparePartName, String sparePartCd, String quantity, String pricePerQty) {
        SparePartName = sparePartName;
        SparePartCd = sparePartCd;
        Quantity = quantity;
        PricePerQty = pricePerQty;
    }

    public String getSparePartName() {
        return SparePartName;
    }

    public void setSparePartName(String sparePartName) {
        SparePartName = sparePartName;
    }

    public String getSparePartCd() {
        return SparePartCd;
    }

    public void setSparePartCd(String sparePartCd) {
        SparePartCd = sparePartCd;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPricePerQty() {
        return PricePerQty;
    }

    public void setPricePerQty(String pricePerQty) {
        PricePerQty = pricePerQty;
    }

    @Expose
    @SerializedName("SparePartName")
    private String SparePartName;
    @Expose
    @SerializedName("SparePartCd")
    private String SparePartCd;
    @Expose
    @SerializedName("Quantity")
    private String Quantity;
    @Expose
    @SerializedName("PricePerQty")
    private String PricePerQty;
    public ReportsItem() {
    }

}



