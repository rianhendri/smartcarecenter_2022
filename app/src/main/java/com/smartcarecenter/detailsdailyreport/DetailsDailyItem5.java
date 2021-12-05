/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.detailsdailyreport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailsDailyItem5 {


    public DetailsDailyItem5(String stringValue) {
        StringValue = stringValue;
    }

    public String getStringValue() {
        return StringValue;
    }

    public void setStringValue(String stringValue) {
        StringValue = stringValue;
    }

    @Expose
    @SerializedName("StringValue")
    private String StringValue;

    public DetailsDailyItem5() {
    }

}



