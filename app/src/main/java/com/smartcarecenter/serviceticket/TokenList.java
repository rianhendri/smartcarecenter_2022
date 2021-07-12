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

public class TokenList {

    public TokenList() {
    }

    public TokenList(String token) {
        Token = token;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    @Expose
    @SerializedName("Token")
    private String Token;
}

