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

public class DetailsDate {

    public DetailsDate() {
    }

    public DetailsDate(String message, String date, String time, String read, String name) {
        this.message = message;
        this.date = date;
        this.time = time;
        this.read = read;
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("time")
    private String time;

    @Expose
    @SerializedName("read")
    private String read;
    @Expose
    @SerializedName("name")
    private String name;
}

