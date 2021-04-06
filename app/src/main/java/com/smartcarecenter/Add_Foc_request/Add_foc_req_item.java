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


    public Add_foc_req_item(String itemcd, Integer position, Integer qty, String imgpic, String imgban, String nameitem, String category, String unitName, double matrixLifeSpanPcs, boolean usingMatrix, Integer lastImpression, String matrix, String stockOnHand, boolean matrixFloor, Double matrixCount, String lifeSpan, int maxSOH) {
        this.itemcd = itemcd;
        this.position = position;
        this.qty = qty;
        this.imgpic = imgpic;
        this.imgban = imgban;
        this.nameitem = nameitem;
        this.category = category;
        UnitName = unitName;
        MatrixLifeSpanPcs = matrixLifeSpanPcs;
        UsingMatrix = usingMatrix;
        LastImpression = lastImpression;
        Matrix = matrix;
        this.stockOnHand = stockOnHand;
        MatrixFloor = matrixFloor;
        MatrixCount = matrixCount;
        LifeSpan = lifeSpan;
        MaxSOH = maxSOH;
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

    public Integer getLastImpression() {
        return LastImpression;
    }

    public void setLastImpression(Integer lastImpression) {
        LastImpression = lastImpression;
    }

    public String getMatrix() {
        return Matrix;
    }

    public void setMatrix(String matrix) {
        Matrix = matrix;
    }

    public String getStockOnHand() {
        return stockOnHand;
    }

    public void setStockOnHand(String stockOnHand) {
        this.stockOnHand = stockOnHand;
    }

    public boolean isMatrixFloor() {
        return MatrixFloor;
    }

    public void setMatrixFloor(boolean matrixFloor) {
        MatrixFloor = matrixFloor;
    }

    public Double getMatrixCount() {
        return MatrixCount;
    }

    public void setMatrixCount(Double matrixCount) {
        MatrixCount = matrixCount;
    }

    public String getLifeSpan() {
        return LifeSpan;
    }

    public void setLifeSpan(String lifeSpan) {
        LifeSpan = lifeSpan;
    }

    public int getMaxSOH() {
        return MaxSOH;
    }

    public void setMaxSOH(int maxSOH) {
        MaxSOH = maxSOH;
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
    @SerializedName("MatrixLifeSpanPcs")
    private double MatrixLifeSpanPcs;
    @Expose
    @SerializedName("UsingMatrix")
    private boolean UsingMatrix;
    @Expose
    @SerializedName("LastImpression")
    private Integer LastImpression;
    @Expose
    @SerializedName("Matrix")
    private String Matrix;

    @Expose
    @SerializedName("stockOnHand")
    private String stockOnHand;

    @Expose
    @SerializedName("MatrixFloor")
    private boolean MatrixFloor;

    @Expose
    @SerializedName("MatrixCount")
    private Double MatrixCount;

    @Expose
    @SerializedName("LifeSpan")
    private String LifeSpan;

    @Expose
    @SerializedName("MaxSOH")
    private int MaxSOH;
}



