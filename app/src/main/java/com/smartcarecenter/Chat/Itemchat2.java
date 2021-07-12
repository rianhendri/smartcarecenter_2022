/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.Chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Itemchat2 {

    public Itemchat2() {

    }


    public Itemchat2(String date, String name, String message, String read, String key, String showDate, String time, String sendto, String url, String showUrl, int position, String myuri, String youruri, String level, String type, String thumb) {
        Date = date;
        Name = name;
        Message = message;
        Read = read;
        Key = key;
        ShowDate = showDate;
        Time = time;
        Sendto = sendto;
        Url = url;
        ShowUrl = showUrl;
        Position = position;
        Myuri = myuri;
        Youruri = youruri;
        Level = level;
        Type = type;
        Thumb = thumb;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getRead() {
        return Read;
    }

    public void setRead(String read) {
        Read = read;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getShowDate() {
        return ShowDate;
    }

    public void setShowDate(String showDate) {
        ShowDate = showDate;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getSendto() {
        return Sendto;
    }

    public void setSendto(String sendto) {
        Sendto = sendto;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getShowUrl() {
        return ShowUrl;
    }

    public void setShowUrl(String showUrl) {
        ShowUrl = showUrl;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public String getMyuri() {
        return Myuri;
    }

    public void setMyuri(String myuri) {
        Myuri = myuri;
    }

    public String getYoururi() {
        return Youruri;
    }

    public void setYoururi(String youruri) {
        Youruri = youruri;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getThumb() {
        return Thumb;
    }

    public void setThumb(String thumb) {
        Thumb = thumb;
    }

    @SerializedName("Date")
    private String Date;
    @Expose
    @SerializedName("Name")
    private String Name;
    @Expose
    @SerializedName("Message")
    private String Message;
    @Expose
    @SerializedName("Read")
    private String Read;
    @Expose
    @SerializedName("Key")
    private String Key;
    @Expose
    @SerializedName("ShowDate")
    private String ShowDate;
    @Expose
    @SerializedName("Time")
    private String Time;
    @Expose
    @SerializedName("Sendto")
    private String Sendto;
    @Expose
    @SerializedName("Url")
    private String Url;
    @Expose
    @SerializedName("ShowUrl")
    private String ShowUrl;
    @Expose
    @SerializedName("Position")
    private int Position;
    @Expose
    @SerializedName("Myuri")
    private String Myuri;
    @Expose
    @SerializedName("Youruri")
    private String Youruri;
    @Expose
    @SerializedName("Level")
    private String Level;
    @Expose
    @SerializedName("Type")
    private String Type;
    @Expose
    @SerializedName("Thumb")
    private String Thumb;
}



