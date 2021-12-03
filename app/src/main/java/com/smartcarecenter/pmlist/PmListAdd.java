/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.pmlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PmListAdd {


    public PmListAdd(String serviceTicketCd, String date, String pressGuid, String pressName, String status, String statusName, String statusColorCode, String visitDateTime, String additionalTextHtml) {
        this.serviceTicketCd = serviceTicketCd;
        this.date = date;
        this.pressGuid = pressGuid;
        this.pressName = pressName;
        this.status = status;
        this.statusName = statusName;
        this.statusColorCode = statusColorCode;
        this.visitDateTime = visitDateTime;
        this.additionalTextHtml = additionalTextHtml;
    }

    public String getServiceTicketCd() {
        return serviceTicketCd;
    }

    public void setServiceTicketCd(String serviceTicketCd) {
        this.serviceTicketCd = serviceTicketCd;
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

    public String getVisitDateTime() {
        return visitDateTime;
    }

    public void setVisitDateTime(String visitDateTime) {
        this.visitDateTime = visitDateTime;
    }

    public String getAdditionalTextHtml() {
        return additionalTextHtml;
    }

    public void setAdditionalTextHtml(String additionalTextHtml) {
        this.additionalTextHtml = additionalTextHtml;
    }

    @Expose
    @SerializedName("serviceTicketCd")
    private String serviceTicketCd;
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
    @SerializedName("visitDateTime")
    private String visitDateTime;
    @Expose
    @SerializedName("additionalTextHtml")
    private String additionalTextHtml;

    public PmListAdd() {
    }

}



