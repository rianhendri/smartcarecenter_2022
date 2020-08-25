/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.Add_Foc_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Add_foc_req_item {

    public Add_foc_req_item() {
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
    public String getImgban() {
        return imgban;
    }

    public void setImgban(String imgban) {
        this.imgban = imgban;
    }



    public Add_foc_req_item(String itemcd, Integer position, Integer qty,
                            String imgpic, String nameitem, String category,String imgban) {
        this.itemcd = itemcd;
        this.position = position;
        this.qty = qty;
        this.imgpic = imgpic;
        this.imgban = imgban;
        this.nameitem = nameitem;
        this.category = category;
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



}



