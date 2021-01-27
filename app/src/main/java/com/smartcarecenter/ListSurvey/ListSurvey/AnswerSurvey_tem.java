/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 *  com.google.gson.annotations.SerializedName
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.ListSurvey.ListSurvey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnswerSurvey_tem {

    public AnswerSurvey_tem() {
    }

    public AnswerSurvey_tem(Integer questionPosition, Integer answerPosition, String answerText, boolean optional, String questionType) {
        this.questionPosition = questionPosition;
        this.answerPosition = answerPosition;
        this.answerText = answerText;
        Optional = optional;
        QuestionType = questionType;
    }

    public Integer getQuestionPosition() {
        return questionPosition;
    }

    public void setQuestionPosition(Integer questionPosition) {
        this.questionPosition = questionPosition;
    }

    public Integer getAnswerPosition() {
        return answerPosition;
    }

    public void setAnswerPosition(Integer answerPosition) {
        this.answerPosition = answerPosition;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isOptional() {
        return Optional;
    }

    public void setOptional(boolean optional) {
        Optional = optional;
    }

    public String getQuestionType() {
        return QuestionType;
    }

    public void setQuestionType(String questionType) {
        QuestionType = questionType;
    }

    @SerializedName("questionPosition")
    private Integer questionPosition;
    @Expose
    @SerializedName("answerPosition")
    private Integer answerPosition;
    @Expose
    @SerializedName("answerText")
    private String answerText;
    @Expose
    @SerializedName("Optional")
    private boolean Optional;
    @Expose
    @SerializedName("QuestionType")
    private String QuestionType;




}



