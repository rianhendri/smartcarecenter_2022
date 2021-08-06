/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.historyfr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemHistoryfr {

    public ItemHistoryfr() {

    }

    public ItemHistoryfr(String value, String text) {
        Value = value;
        Text = text;
    }

    @Expose
    @SerializedName("Value")
    private String Value;

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    @Expose
    @SerializedName("Text")
    private String Text;
}



