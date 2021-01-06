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

    public Add_foc_req_itemView(String itemCd, Integer position, Integer qty, String itemName, String categoryName, String imageThumbFullURL, String imageFullURL, String unitName, int qtyApproved, String deserveQty, int previousImpression,
                                String stockOnHand, double matrixLifeSpanPcs, boolean usingMatrix) {
        ItemCd = itemCd;
        Position = position;
        Qty = qty;
        ItemName = itemName;
        CategoryName = categoryName;
        ImageThumbFullURL = imageThumbFullURL;
        ImageFullURL = imageFullURL;
        UnitName = unitName;
        QtyApproved = qtyApproved;
        DeserveQty = deserveQty;
        PreviousImpression = previousImpression;
        StockOnHand = stockOnHand;
        MatrixLifeSpanPcs = matrixLifeSpanPcs;
        UsingMatrix = usingMatrix;
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

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public int getQtyApproved() {
        return QtyApproved;
    }

    public void setQtyApproved(int qtyApproved) {
        QtyApproved = qtyApproved;
    }

    public String getDeserveQty() {
        return DeserveQty;
    }

    public void setDeserveQty(String deserveQty) {
        DeserveQty = deserveQty;
    }

    public int getPreviousImpression() {
        return PreviousImpression;
    }

    public void setPreviousImpression(int previousImpression) {
        PreviousImpression = previousImpression;
    }

    public String getStockOnHand() {
        return StockOnHand;
    }

    public void setStockOnHand(String stockOnHand) {
        StockOnHand = stockOnHand;
    }

    public double getMatrixLifeSpanPcs() {
        return MatrixLifeSpanPcs;
    }

    public void setMatrixLifeSpanPcs(double matrixLifeSpanPcs) {
        MatrixLifeSpanPcs = matrixLifeSpanPcs;
    }

    public boolean isUsingMatrix() {
        return UsingMatrix;
    }

    public void setUsingMatrix(boolean usingMatrix) {
        UsingMatrix = usingMatrix;
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
    @Expose
    @SerializedName("UnitName")
    private String UnitName;
    @Expose
    @SerializedName("QtyApproved")
    private int QtyApproved;
    @Expose
    @SerializedName("DeserveQty")
    private String DeserveQty;
    @Expose
    @SerializedName("PreviousImpression")
    private int PreviousImpression;
    @Expose
    @SerializedName("StockOnHand")
    private String StockOnHand;
    @Expose
    @SerializedName("MatrixLifeSpanPcs")
    private double MatrixLifeSpanPcs;
    @Expose
    @SerializedName("UsingMatrix")
    private boolean UsingMatrix;
}



