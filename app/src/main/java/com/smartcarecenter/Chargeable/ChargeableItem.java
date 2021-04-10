/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.Chargeable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChargeableItem {


    public ChargeableItem(String orderNo, String date, String pressGuid, String pressName, String status, String statusName, String statusColorCode, String createdBy, String poNo, String createdDateTime, String paymentStatusName, String paymentStatusColorCode, double grandTotal, boolean showIconPO) {
        this.orderNo = orderNo;
        this.date = date;
        this.pressGuid = pressGuid;
        this.pressName = pressName;
        this.status = status;
        this.statusName = statusName;
        this.statusColorCode = statusColorCode;
        this.createdBy = createdBy;
        this.poNo = poNo;
        this.createdDateTime = createdDateTime;
        this.paymentStatusName = paymentStatusName;
        this.paymentStatusColorCode = paymentStatusColorCode;
        this.grandTotal = grandTotal;
        this.showIconPO = showIconPO;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPressGuid() {
        return pressGuid;
    }

    public void setPressGuid(String pressGuid) {
        this.pressGuid = pressGuid;
    }

    public String getPressName() {
        return pressName;
    }

    public void setPressName(String pressName) {
        this.pressName = pressName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusColorCode() {
        return statusColorCode;
    }

    public void setStatusColorCode(String statusColorCode) {
        this.statusColorCode = statusColorCode;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getPaymentStatusName() {
        return paymentStatusName;
    }

    public void setPaymentStatusName(String paymentStatusName) {
        this.paymentStatusName = paymentStatusName;
    }

    public String getPaymentStatusColorCode() {
        return paymentStatusColorCode;
    }

    public void setPaymentStatusColorCode(String paymentStatusColorCode) {
        this.paymentStatusColorCode = paymentStatusColorCode;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public boolean isShowIconPO() {
        return showIconPO;
    }

    public void setShowIconPO(boolean showIconPO) {
        this.showIconPO = showIconPO;
    }

    @Expose
    @SerializedName("orderNo")
    private String orderNo;
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("pressGuid")
    private String pressGuid;
    @Expose
    @SerializedName("pressName")
    private String pressName;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("statusName")
    private String statusName;
    @Expose
    @SerializedName("statusColorCode")
    private String statusColorCode;
    @Expose
    @SerializedName("createdBy")
    private String createdBy;
    @Expose
    @SerializedName("poNo")
    private String poNo;
    @Expose
    @SerializedName("createdDateTime")
    private String createdDateTime;
    @Expose
    @SerializedName("paymentStatusName")
    private String paymentStatusName;
    @Expose
    @SerializedName("paymentStatusColorCode")
    private String paymentStatusColorCode;
    @Expose
    @SerializedName("grandTotal")
    private double grandTotal;
    @Expose
    @SerializedName("showIconPO")
    private boolean showIconPO;

    public ChargeableItem() {
    }

}



