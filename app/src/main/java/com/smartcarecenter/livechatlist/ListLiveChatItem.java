/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.livechatlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListLiveChatItem {

    public ListLiveChatItem() {
    }


    public ListLiveChatItem(String titleInList, String module, String moduleTransactionNo, String liveChatID, boolean allowToChat, String title, String userID, String datea, String time, String details, String userName, ArrayList<TokenItem> othersFirebaseToken) {
        TitleInList = titleInList;
        Module = module;
        ModuleTransactionNo = moduleTransactionNo;
        LiveChatID = liveChatID;
        AllowToChat = allowToChat;
        Title = title;
        UserID = userID;
        this.datea = datea;
        Time = time;
        this.details = details;
        UserName = userName;
        OthersFirebaseToken = othersFirebaseToken;
    }

    public String getTitleInList() {
        return TitleInList;
    }

    public void setTitleInList(String titleInList) {
        TitleInList = titleInList;
    }

    public String getModule() {
        return Module;
    }

    public void setModule(String module) {
        Module = module;
    }

    public String getModuleTransactionNo() {
        return ModuleTransactionNo;
    }

    public void setModuleTransactionNo(String moduleTransactionNo) {
        ModuleTransactionNo = moduleTransactionNo;
    }

    public String getLiveChatID() {
        return LiveChatID;
    }

    public void setLiveChatID(String liveChatID) {
        LiveChatID = liveChatID;
    }

    public boolean isAllowToChat() {
        return AllowToChat;
    }

    public void setAllowToChat(boolean allowToChat) {
        AllowToChat = allowToChat;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getDatea() {
        return datea;
    }

    public void setDatea(String datea) {
        this.datea = datea;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public ArrayList<TokenItem> getOthersFirebaseToken() {
        return OthersFirebaseToken;
    }

    public void setOthersFirebaseToken(ArrayList<TokenItem> othersFirebaseToken) {
        OthersFirebaseToken = othersFirebaseToken;
    }

    @Expose
    @SerializedName("TitleInList")
    private String TitleInList;
    @Expose
    @SerializedName("Module")
    private String Module;
    @Expose
    @SerializedName("ModuleTransactionNo")
    private String ModuleTransactionNo;
    @Expose
    @SerializedName("LiveChatID")
    private String LiveChatID;
    @Expose
    @SerializedName("AllowToChat")
    private boolean AllowToChat;
    @Expose
    @SerializedName("Title")
    private String Title;
    @Expose
    @SerializedName("UserID")
    private String UserID;
    @Expose
    @SerializedName("datea")
    private String datea;
    @Expose
    @SerializedName("Time")
    private String Time;
    @Expose
    @SerializedName("details")
    private String details;
    @Expose
    @SerializedName("UserName")
    private String UserName;
    @Expose
    @SerializedName("OthersFirebaseToken")
        private ArrayList<TokenItem> OthersFirebaseToken = null;

}

