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

public class DetailsDailyItem1 {

    public String getFinding() {
        return Finding;
    }

    public void setFinding(String finding) {
        Finding = finding;
    }

    public String getReportCd() {
        return ReportCd;
    }

    public void setReportCd(String reportCd) {
        ReportCd = reportCd;
    }

    public DetailsDailyItem1(String finding, String reportCd) {
        Finding = finding;
        ReportCd = reportCd;
    }

    @Expose
    @SerializedName("Finding")
    private String Finding;
    @Expose
    @SerializedName("ReportCd")
    private String ReportCd;

    public DetailsDailyItem1() {
    }

}



