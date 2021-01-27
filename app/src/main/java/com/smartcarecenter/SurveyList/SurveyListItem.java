/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.SurveyList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SurveyListItem {

    public SurveyListItem(String title, String feedbackDateTime) {
        this.title = title;
        this.feedbackDateTime = feedbackDateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFeedbackDateTime() {
        return feedbackDateTime;
    }

    public void setFeedbackDateTime(String feedbackDateTime) {
        this.feedbackDateTime = feedbackDateTime;
    }

    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("feedbackDateTime")
    private String feedbackDateTime;

}

