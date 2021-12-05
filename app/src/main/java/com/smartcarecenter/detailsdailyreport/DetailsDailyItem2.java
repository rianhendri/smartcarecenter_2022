/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.detailsdailyreport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailsDailyItem2 {

    public DetailsDailyItem2(String reportCd, String actionTaken) {
        ReportCd = reportCd;
        ActionTaken = actionTaken;
    }

    public String getReportCd() {
        return ReportCd;
    }

    public void setReportCd(String reportCd) {
        ReportCd = reportCd;
    }

    public String getActionTaken() {
        return ActionTaken;
    }

    public void setActionTaken(String actionTaken) {
        ActionTaken = actionTaken;
    }

    @Expose
    @SerializedName("ReportCd")
    private String ReportCd;
    @Expose
    @SerializedName("ActionTaken")
    private String ActionTaken;
    public DetailsDailyItem2() {
    }

}



