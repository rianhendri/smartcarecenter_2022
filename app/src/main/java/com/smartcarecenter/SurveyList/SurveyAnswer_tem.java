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

public class SurveyAnswer_tem {

    public SurveyAnswer_tem() {
    }


    public SurveyAnswer_tem(String answer, int position, int answerPosition) {
        Answer = answer;
        Position = position;
        AnswerPosition = answerPosition;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public int getAnswerPosition() {
        return AnswerPosition;
    }

    public void setAnswerPosition(int answerPosition) {
        AnswerPosition = answerPosition;
    }

    @Expose
    @SerializedName("Answer")
    private String Answer;
    @Expose
    @SerializedName("Position")
    private int Position;
    @Expose
    @SerializedName("AnswerPosition")
    private int AnswerPosition;



}



