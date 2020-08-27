/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.Add_Po_Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Add_po_req_item {

    public Add_po_req_item() {
    }

    public Add_po_req_item(String itemcd, Integer position, Integer qty, String imgpic, String imgban, String nameitem,
                           String category, String unitName, double sellPrice, double subharga, String mps) {
        this.itemcd = itemcd;
        this.position = position;
        this.qty = qty;
        this.imgpic = imgpic;
        this.imgban = imgban;
        this.nameitem = nameitem;
        this.category = category;
        UnitName = unitName;
        SellPrice = sellPrice;
        Subharga = subharga;
        Mps = mps;
    }

    public String getItemcd() {
        return itemcd;
    }

    public void setItemcd(String itemcd) {
        this.itemcd = itemcd;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getImgpic() {
        return imgpic;
    }

    public void setImgpic(String imgpic) {
        this.imgpic = imgpic;
    }

    public String getImgban() {
        return imgban;
    }

    public void setImgban(String imgban) {
        this.imgban = imgban;
    }

    public String getNameitem() {
        return nameitem;
    }

    public void setNameitem(String nameitem) {
        this.nameitem = nameitem;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public double getSellPrice() {
        return SellPrice;
    }

    public void setSellPrice(double sellPrice) {
        SellPrice = sellPrice;
    }

    public double getSubharga() {
        return Subharga;
    }

    public void setSubharga(double subharga) {
        Subharga = subharga;
    }

    public String getMps() {
        return Mps;
    }

    public void setMps(String mps) {
        Mps = mps;
    }

    @SerializedName("itemcd")
    private String itemcd;
    @Expose
    @SerializedName("position")
    private Integer position;
    @Expose
    @SerializedName("qty")
    private Integer qty;
    @Expose
    @SerializedName("imgpic")
    private String imgpic;
    @Expose
    @SerializedName("imgban")
    private String imgban;
    @Expose
    @SerializedName("nameitem")
    private String nameitem;
    @Expose
    @SerializedName("category")
    private String category;

    @Expose
    @SerializedName("UnitName")
    private String UnitName;

    @Expose
    @SerializedName("SellPrice")
    private double SellPrice;

    @Expose
    @SerializedName("Subharga")
    private double Subharga;
    @Expose
    @SerializedName("Mps")
    private String Mps;

}



