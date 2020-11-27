/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.Freeofcharge;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FocItem {

    public FocItem(String orderNo, String date, String pressGuid, String pressName, String status,
                   String statusName, String statusColorCode, String createdBy,
                   String notes, String createdDateTime) {
        this.orderNo = orderNo;
        this.date = date;
        this.pressGuid = pressGuid;
        this.pressName = pressName;
        this.status = status;
        this.statusName = statusName;
        this.statusColorCode = statusColorCode;
        this.createdBy = createdBy;
        this.notes = notes;
        this.createdDateTime = createdDateTime;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
    public FocItem() {
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
    @SerializedName("notes")
    private String notes;
    @Expose
    @SerializedName("createdDateTime")
    private String createdDateTime;


}



