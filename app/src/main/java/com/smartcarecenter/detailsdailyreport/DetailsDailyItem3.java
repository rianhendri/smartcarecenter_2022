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

public class DetailsDailyItem3 {


    public DetailsDailyItem3(String reportCd, String followUp) {
        ReportCd = reportCd;
        FollowUp = followUp;
    }

    public String getReportCd() {
        return ReportCd;
    }

    public void setReportCd(String reportCd) {
        ReportCd = reportCd;
    }

    public String getFollowUp() {
        return FollowUp;
    }

    public void setFollowUp(String followUp) {
        FollowUp = followUp;
    }

    @Expose
    @SerializedName("ReportCd")
    private String ReportCd;
    @Expose
    @SerializedName("FollowUp")
    private String FollowUp;
    public DetailsDailyItem3() {
    }

}



