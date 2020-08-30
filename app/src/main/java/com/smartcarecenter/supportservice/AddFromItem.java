/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.supportservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddFromItem {
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFormRequestCd() {
        return formRequestCd;
    }

    public void setFormRequestCd(String formRequestCd) {
        this.formRequestCd = formRequestCd;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getPhotoThumbURL() {
        return photoThumbURL;
    }

    public void setPhotoThumbURL(String photoThumbURL) {
        this.photoThumbURL = photoThumbURL;
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

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getRequestedDateTime() {
        return requestedDateTime;
    }

    public void setRequestedDateTime(String requestedDateTime) {
        this.requestedDateTime = requestedDateTime;
    }

    public String getServiceTicketCd() {
        return serviceTicketCd;
    }

    public void setServiceTicketCd(String serviceTicketCd) {
        this.serviceTicketCd = serviceTicketCd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusColorCode() {
        return statusColorCode;
    }

    public void setStatusColorCode(String statusColorCode) {
        this.statusColorCode = statusColorCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }


    public AddFromItem(String date, String formRequestCd, String photoURL, String photoThumbURL,
                       String pressGuid, String pressName, String requestedBy, String requestedDateTime,
                       String serviceTicketCd, String status, String statusColorCode, String statusName) {
        this.date = date;
        this.formRequestCd = formRequestCd;
        this.photoURL = photoURL;
        this.photoThumbURL = photoThumbURL;
        this.pressGuid = pressGuid;
        this.pressName = pressName;
        this.requestedBy = requestedBy;
        this.requestedDateTime = requestedDateTime;
        this.serviceTicketCd = serviceTicketCd;
        this.status = status;
        this.statusColorCode = statusColorCode;
        this.statusName = statusName;
    }

    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("formRequestCd")
    private String formRequestCd;
    @Expose
    @SerializedName("photoURL")
    private String photoURL;
    @Expose
    @SerializedName("photoThumbURL")
    private String photoThumbURL;
    @Expose
    @SerializedName("pressGuid")
    private String pressGuid;
    @Expose
    @SerializedName("pressName")
    private String pressName;
    @Expose
    @SerializedName("requestedBy")
    private String requestedBy;
    @Expose
    @SerializedName("requestedDateTime")
    private String requestedDateTime;
    @Expose
    @SerializedName("serviceTicketCd")
    private String serviceTicketCd;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("statusColorCode")
    private String statusColorCode;
    @Expose
    @SerializedName("statusName")
    private String statusName;


    public AddFromItem() {
    }

}



