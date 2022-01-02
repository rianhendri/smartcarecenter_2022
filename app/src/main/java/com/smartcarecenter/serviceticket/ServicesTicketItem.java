/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.serviceticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ServicesTicketItem {

    public ServicesTicketItem() {
    }


    public ServicesTicketItem(String assignedDateTime, String bar1Text, String bar2Text, String bar3Text, boolean bar4Red, String bar4Text, int barPosition, String engineerName, String feedbackComments, int feedbackRating, String issueCategoryName, int position, String serviceTypeName, String statusName, String serviceTicketCd, String supportEndDateTime, String supportStartDateTime, String unitCategoryName, String assist, int lastImpression, String description, boolean showOnProgressAction, String onProgressActionName, String feedbackPhotoFullURL, String waitingEstimationDate, boolean showLiveChat, String liveChatName, String guid, String pressStatusCd, boolean liveChatAllowChat, ArrayList<TokenList> liveChatFirebaseToken) {
        AssignedDateTime = assignedDateTime;
        Bar1Text = bar1Text;
        Bar2Text = bar2Text;
        Bar3Text = bar3Text;
        Bar4Red = bar4Red;
        Bar4Text = bar4Text;
        BarPosition = barPosition;
        EngineerName = engineerName;
        FeedbackComments = feedbackComments;
        FeedbackRating = feedbackRating;
        IssueCategoryName = issueCategoryName;
        Position = position;
        ServiceTypeName = serviceTypeName;
        StatusName = statusName;
        ServiceTicketCd = serviceTicketCd;
        SupportEndDateTime = supportEndDateTime;
        SupportStartDateTime = supportStartDateTime;
        UnitCategoryName = unitCategoryName;
        Assist = assist;
        LastImpression = lastImpression;
        Description = description;
        ShowOnProgressAction = showOnProgressAction;
        OnProgressActionName = onProgressActionName;
        FeedbackPhotoFullURL = feedbackPhotoFullURL;
        WaitingEstimationDate = waitingEstimationDate;
        ShowLiveChat = showLiveChat;
        LiveChatName = liveChatName;
        Guid = guid;
        PressStatusCd = pressStatusCd;
        LiveChatAllowChat = liveChatAllowChat;
        LiveChatFirebaseToken = liveChatFirebaseToken;
    }

    public String getAssignedDateTime() {
        return AssignedDateTime;
    }

    public void setAssignedDateTime(String assignedDateTime) {
        AssignedDateTime = assignedDateTime;
    }

    public String getBar1Text() {
        return Bar1Text;
    }

    public void setBar1Text(String bar1Text) {
        Bar1Text = bar1Text;
    }

    public String getBar2Text() {
        return Bar2Text;
    }

    public void setBar2Text(String bar2Text) {
        Bar2Text = bar2Text;
    }

    public String getBar3Text() {
        return Bar3Text;
    }

    public void setBar3Text(String bar3Text) {
        Bar3Text = bar3Text;
    }

    public boolean isBar4Red() {
        return Bar4Red;
    }

    public void setBar4Red(boolean bar4Red) {
        Bar4Red = bar4Red;
    }

    public String getBar4Text() {
        return Bar4Text;
    }

    public void setBar4Text(String bar4Text) {
        Bar4Text = bar4Text;
    }

    public int getBarPosition() {
        return BarPosition;
    }

    public void setBarPosition(int barPosition) {
        BarPosition = barPosition;
    }

    public String getEngineerName() {
        return EngineerName;
    }

    public void setEngineerName(String engineerName) {
        EngineerName = engineerName;
    }

    public String getFeedbackComments() {
        return FeedbackComments;
    }

    public void setFeedbackComments(String feedbackComments) {
        FeedbackComments = feedbackComments;
    }

    public int getFeedbackRating() {
        return FeedbackRating;
    }

    public void setFeedbackRating(int feedbackRating) {
        FeedbackRating = feedbackRating;
    }

    public String getIssueCategoryName() {
        return IssueCategoryName;
    }

    public void setIssueCategoryName(String issueCategoryName) {
        IssueCategoryName = issueCategoryName;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public String getServiceTypeName() {
        return ServiceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        ServiceTypeName = serviceTypeName;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    public String getServiceTicketCd() {
        return ServiceTicketCd;
    }

    public void setServiceTicketCd(String serviceTicketCd) {
        ServiceTicketCd = serviceTicketCd;
    }

    public String getSupportEndDateTime() {
        return SupportEndDateTime;
    }

    public void setSupportEndDateTime(String supportEndDateTime) {
        SupportEndDateTime = supportEndDateTime;
    }

    public String getSupportStartDateTime() {
        return SupportStartDateTime;
    }

    public void setSupportStartDateTime(String supportStartDateTime) {
        SupportStartDateTime = supportStartDateTime;
    }

    public String getUnitCategoryName() {
        return UnitCategoryName;
    }

    public void setUnitCategoryName(String unitCategoryName) {
        UnitCategoryName = unitCategoryName;
    }

    public String getAssist() {
        return Assist;
    }

    public void setAssist(String assist) {
        Assist = assist;
    }

    public int getLastImpression() {
        return LastImpression;
    }

    public void setLastImpression(int lastImpression) {
        LastImpression = lastImpression;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isShowOnProgressAction() {
        return ShowOnProgressAction;
    }

    public void setShowOnProgressAction(boolean showOnProgressAction) {
        ShowOnProgressAction = showOnProgressAction;
    }

    public String getOnProgressActionName() {
        return OnProgressActionName;
    }

    public void setOnProgressActionName(String onProgressActionName) {
        OnProgressActionName = onProgressActionName;
    }

    public String getFeedbackPhotoFullURL() {
        return FeedbackPhotoFullURL;
    }

    public void setFeedbackPhotoFullURL(String feedbackPhotoFullURL) {
        FeedbackPhotoFullURL = feedbackPhotoFullURL;
    }

    public String getWaitingEstimationDate() {
        return WaitingEstimationDate;
    }

    public void setWaitingEstimationDate(String waitingEstimationDate) {
        WaitingEstimationDate = waitingEstimationDate;
    }

    public boolean isShowLiveChat() {
        return ShowLiveChat;
    }

    public void setShowLiveChat(boolean showLiveChat) {
        ShowLiveChat = showLiveChat;
    }

    public String getLiveChatName() {
        return LiveChatName;
    }

    public void setLiveChatName(String liveChatName) {
        LiveChatName = liveChatName;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String guid) {
        Guid = guid;
    }

    public String getPressStatusCd() {
        return PressStatusCd;
    }

    public void setPressStatusCd(String pressStatusCd) {
        PressStatusCd = pressStatusCd;
    }

    public boolean isLiveChatAllowChat() {
        return LiveChatAllowChat;
    }

    public void setLiveChatAllowChat(boolean liveChatAllowChat) {
        LiveChatAllowChat = liveChatAllowChat;
    }

    public ArrayList<TokenList> getLiveChatFirebaseToken() {
        return LiveChatFirebaseToken;
    }

    public void setLiveChatFirebaseToken(ArrayList<TokenList> liveChatFirebaseToken) {
        LiveChatFirebaseToken = liveChatFirebaseToken;
    }

    //
    @Expose
    @SerializedName("AssignedDateTime")
    private String AssignedDateTime;
    @Expose
    @SerializedName("Bar1Text")
    private String Bar1Text;
    @Expose
    @SerializedName("Bar2Text")
    private String Bar2Text;
    @Expose
    @SerializedName("Bar3Text")
    private String Bar3Text;
    @Expose
    @SerializedName("Bar4Red")
    private boolean Bar4Red;
    @Expose
    @SerializedName("Bar4Text")
    private String Bar4Text;
    @Expose
    @SerializedName("BarPosition")
    private int BarPosition;
    @Expose
    @SerializedName("EngineerName")
    private String EngineerName;
    @Expose
    @SerializedName("FeedbackComments")
    private String FeedbackComments;
    @Expose
    @SerializedName("FeedbackRating")
    private int FeedbackRating;
    @Expose
    @SerializedName("IssueCategoryName")
    private String IssueCategoryName;
    @Expose
    @SerializedName("Position")
    private int Position;
    @Expose
    @SerializedName("ServiceTypeName")
    private String ServiceTypeName;
    @Expose
    @SerializedName("StatusName")
    private String StatusName;
    @Expose
    @SerializedName("ServiceTicketCd")
    private String ServiceTicketCd;
    @Expose
    @SerializedName("SupportEndDateTime")
    private String SupportEndDateTime;
    @Expose
    @SerializedName("SupportStartDateTime")
    private String SupportStartDateTime;
    @Expose
    @SerializedName("UnitCategoryName")
    private String UnitCategoryName;

    @Expose
    @SerializedName("Assist")
    private String Assist;

    @Expose
    @SerializedName("LastImpression")
    private int LastImpression;

    @Expose
    @SerializedName("Description")
    private String Description;

    @SerializedName("ShowOnProgressAction")
    private boolean ShowOnProgressAction;
    @Expose
    @SerializedName("OnProgressActionName")
    private String OnProgressActionName;
    @Expose
    @SerializedName("FeedbackPhotoFullURL")
    private String FeedbackPhotoFullURL;
    @Expose
    @SerializedName("WaitingEstimationDate")
    private String WaitingEstimationDate;
    @Expose
    @SerializedName("ShowLiveChat")
    private boolean ShowLiveChat;
    @Expose
    @SerializedName("LiveChatName")
    private String LiveChatName;
    @Expose
    @SerializedName("Guid")
    private String Guid;
    @Expose
    @SerializedName("PressStatusCd")
    private String PressStatusCd;
    @Expose
    @SerializedName("LiveChatAllowChat")
    private boolean LiveChatAllowChat;
    @SerializedName("LiveChatFirebaseToken")
    private ArrayList<TokenList> LiveChatFirebaseToken = null;

}

