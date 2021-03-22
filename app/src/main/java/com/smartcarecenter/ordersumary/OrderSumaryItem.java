/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.ordersumary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderSumaryItem {
    public OrderSumaryItem() {
    }


    public OrderSumaryItem(String itemName, String itemDescription, String nominal, String nominalStrikeout, boolean extraMarginBottom) {
        ItemName = itemName;
        ItemDescription = itemDescription;
        Nominal = nominal;
        NominalStrikeout = nominalStrikeout;
        ExtraMarginBottom = extraMarginBottom;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }

    public String getNominal() {
        return Nominal;
    }

    public void setNominal(String nominal) {
        Nominal = nominal;
    }

    public String getNominalStrikeout() {
        return NominalStrikeout;
    }

    public void setNominalStrikeout(String nominalStrikeout) {
        NominalStrikeout = nominalStrikeout;
    }

    public boolean isExtraMarginBottom() {
        return ExtraMarginBottom;
    }

    public void setExtraMarginBottom(boolean extraMarginBottom) {
        ExtraMarginBottom = extraMarginBottom;
    }

    @Expose
    @SerializedName("ItemName")
    private String ItemName;
    @Expose
    @SerializedName("ItemDescription")
    private String ItemDescription;
    @Expose
    @SerializedName("Nominal")
    private String Nominal;
    @Expose
    @SerializedName("NominalStrikeout")
    private String NominalStrikeout;
    @Expose
    @SerializedName("ExtraMarginBottom")
    private boolean ExtraMarginBottom;





}

