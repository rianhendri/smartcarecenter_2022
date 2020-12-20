/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.menuhome;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatItem {
    public ChatItem() {
    }

    public ChatItem(String name, String phoneNumber) {
        Name = name;
        PhoneNumber = phoneNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    @Expose
    @SerializedName("Name")
    private String Name;
    @Expose
    @SerializedName("PhoneNumber")
    private String PhoneNumber;


}

