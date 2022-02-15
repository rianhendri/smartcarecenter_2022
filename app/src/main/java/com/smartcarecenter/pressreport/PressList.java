/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.pressreport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PressList {


    public PressList(String SNAndName, String guid) {
        this.SNAndName = SNAndName;
        Guid = guid;
    }

    public String getSNAndName() {
        return SNAndName;
    }

    public void setSNAndName(String SNAndName) {
        this.SNAndName = SNAndName;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String guid) {
        Guid = guid;
    }

    @Expose
    @SerializedName("SNAndName")
    private String SNAndName;
    @Expose
    @SerializedName("Guid")
    private String Guid;

    public PressList() {
    }

}



