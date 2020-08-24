/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationItem {
    public NotificationItem(String content, String guid, String postedDateTime, String title, String username) {
        Content = content;
        Guid = guid;
        PostedDateTime = postedDateTime;
        Title = title;
        Username = username;
    }
    public NotificationItem() {
    }


    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String guid) {
        Guid = guid;
    }

    public String getPostedDateTime() {
        return PostedDateTime;
    }

    public void setPostedDateTime(String postedDateTime) {
        PostedDateTime = postedDateTime;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    @Expose
    @SerializedName("Username")
    private String Username;
    @Expose
    @SerializedName("Content")
    private String Content;
    @Expose
    @SerializedName("Guid")
    private String Guid;
    @Expose
    @SerializedName("PostedDateTime")
    private String PostedDateTime;
    @Expose
    @SerializedName("Title")
    private String Title;




}

