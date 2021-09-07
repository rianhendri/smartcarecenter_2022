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

public class ItemUid {

    public ItemUid() {

    }
    public ItemUid(String email, String username) {
        Email = email;
        Username = username;
    }
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }



    @Expose
    @SerializedName("Email")
    private String Email;
    @Expose
    @SerializedName("Username")
    private String Username;
}



