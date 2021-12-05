/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.daliyreport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyItem {

    public DailyItem(String serviceTicketCd, String reportCd, String guid, String reportDateTime, String reportBy, String caseProgress, String pressStatus, String serviceTypeCd, String actions, String findings, String followups, String spareParts, String createdByEngineers, String pressTypeName, String pressSN, String issueTitle, String customerName, String customerCd, String customerGroupCd, String caseProgressName, String pressStatusName, boolean flag) {
        this.serviceTicketCd = serviceTicketCd;
        ReportCd = reportCd;
        Guid = guid;
        ReportDateTime = reportDateTime;
        ReportBy = reportBy;
        CaseProgress = caseProgress;
        PressStatus = pressStatus;
        ServiceTypeCd = serviceTypeCd;
        Actions = actions;
        Findings = findings;
        Followups = followups;
        SpareParts = spareParts;
        CreatedByEngineers = createdByEngineers;
        PressTypeName = pressTypeName;
        PressSN = pressSN;
        IssueTitle = issueTitle;
        CustomerName = customerName;
        CustomerCd = customerCd;
        CustomerGroupCd = customerGroupCd;
        CaseProgressName = caseProgressName;
        PressStatusName = pressStatusName;
        Flag = flag;
    }

    public String getServiceTicketCd() {
        return serviceTicketCd;
    }

    public void setServiceTicketCd(String serviceTicketCd) {
        this.serviceTicketCd = serviceTicketCd;
    }

    public String getReportCd() {
        return ReportCd;
    }

    public void setReportCd(String reportCd) {
        ReportCd = reportCd;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String guid) {
        Guid = guid;
    }

    public String getReportDateTime() {
        return ReportDateTime;
    }

    public void setReportDateTime(String reportDateTime) {
        ReportDateTime = reportDateTime;
    }

    public String getReportBy() {
        return ReportBy;
    }

    public void setReportBy(String reportBy) {
        ReportBy = reportBy;
    }

    public String getCaseProgress() {
        return CaseProgress;
    }

    public void setCaseProgress(String caseProgress) {
        CaseProgress = caseProgress;
    }

    public String getPressStatus() {
        return PressStatus;
    }

    public void setPressStatus(String pressStatus) {
        PressStatus = pressStatus;
    }

    public String getServiceTypeCd() {
        return ServiceTypeCd;
    }

    public void setServiceTypeCd(String serviceTypeCd) {
        ServiceTypeCd = serviceTypeCd;
    }

    public String getActions() {
        return Actions;
    }

    public void setActions(String actions) {
        Actions = actions;
    }

    public String getFindings() {
        return Findings;
    }

    public void setFindings(String findings) {
        Findings = findings;
    }

    public String getFollowups() {
        return Followups;
    }

    public void setFollowups(String followups) {
        Followups = followups;
    }

    public String getSpareParts() {
        return SpareParts;
    }

    public void setSpareParts(String spareParts) {
        SpareParts = spareParts;
    }

    public String getCreatedByEngineers() {
        return CreatedByEngineers;
    }

    public void setCreatedByEngineers(String createdByEngineers) {
        CreatedByEngineers = createdByEngineers;
    }

    public String getPressTypeName() {
        return PressTypeName;
    }

    public void setPressTypeName(String pressTypeName) {
        PressTypeName = pressTypeName;
    }

    public String getPressSN() {
        return PressSN;
    }

    public void setPressSN(String pressSN) {
        PressSN = pressSN;
    }

    public String getIssueTitle() {
        return IssueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        IssueTitle = issueTitle;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerCd() {
        return CustomerCd;
    }

    public void setCustomerCd(String customerCd) {
        CustomerCd = customerCd;
    }

    public String getCustomerGroupCd() {
        return CustomerGroupCd;
    }

    public void setCustomerGroupCd(String customerGroupCd) {
        CustomerGroupCd = customerGroupCd;
    }

    public String getCaseProgressName() {
        return CaseProgressName;
    }

    public void setCaseProgressName(String caseProgressName) {
        CaseProgressName = caseProgressName;
    }

    public String getPressStatusName() {
        return PressStatusName;
    }

    public void setPressStatusName(String pressStatusName) {
        PressStatusName = pressStatusName;
    }

    public boolean isFlag() {
        return Flag;
    }

    public void setFlag(boolean flag) {
        Flag = flag;
    }

    @Expose
    @SerializedName("ServiceTicketCd")
    private String serviceTicketCd;
    @Expose
    @SerializedName("ReportCd")
    private String ReportCd;
    @Expose
    @SerializedName("Guid")
    private String Guid;
    @Expose
    @SerializedName("ReportDateTime")
    private String ReportDateTime;
    @Expose
    @SerializedName("ReportBy")
    private String ReportBy;
    @Expose
    @SerializedName("CaseProgress")
    private String CaseProgress;
    @Expose
    @SerializedName("PressStatus")
    private String PressStatus;
    @Expose
    @SerializedName("ServiceTypeCd")
    private String ServiceTypeCd;
    @Expose
    @SerializedName("Actions")
    private String Actions;
    @Expose
    @SerializedName("Findings")
    private String Findings;
    @Expose
    @SerializedName("Followups")
    private String Followups;
    @Expose
    @SerializedName("SpareParts")
    private String SpareParts;
    @Expose
    @SerializedName("CreatedByEngineers")
    private String CreatedByEngineers;
    @Expose
    @SerializedName("PressTypeName")
    private String PressTypeName;
    @Expose
    @SerializedName("PressSN")
    private String PressSN;
    @Expose
    @SerializedName("IssueTitle")
    private String IssueTitle;
    @Expose
    @SerializedName("CustomerName")
    private String CustomerName;
    @Expose
    @SerializedName("CustomerCd")
    private String CustomerCd;
    @Expose
    @SerializedName("CustomerGroupCd")
    private String CustomerGroupCd;
    @Expose
    @SerializedName("CaseProgressName")
    private String CaseProgressName;
    @Expose
    @SerializedName("PressStatusName")
    private String PressStatusName;
    @Expose
    @SerializedName("Flag")
    private boolean Flag;

    public DailyItem() {
    }

}



