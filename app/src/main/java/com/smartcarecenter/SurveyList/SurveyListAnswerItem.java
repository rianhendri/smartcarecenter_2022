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
import com.smartcarecenter.ListSurvey.ListSurvey.ListSurveyAnswer_tem;

import java.util.ArrayList;

public class SurveyListAnswerItem {

    public SurveyListAnswerItem(String questionType, String answerText, int answerPosition, int questionPosition, String question, ArrayList<ListSurveyAnswer_tem> questionAnswerOptions) {
        QuestionType = questionType;
        AnswerText = answerText;
        AnswerPosition = answerPosition;
        QuestionPosition = questionPosition;
        Question = question;
        QuestionAnswerOptions = questionAnswerOptions;
    }

    public String getQuestionType() {
        return QuestionType;
    }

    public void setQuestionType(String questionType) {
        QuestionType = questionType;
    }

    public String getAnswerText() {
        return AnswerText;
    }

    public void setAnswerText(String answerText) {
        AnswerText = answerText;
    }

    public int getAnswerPosition() {
        return AnswerPosition;
    }

    public void setAnswerPosition(int answerPosition) {
        AnswerPosition = answerPosition;
    }

    public int getQuestionPosition() {
        return QuestionPosition;
    }

    public void setQuestionPosition(int questionPosition) {
        QuestionPosition = questionPosition;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public ArrayList<ListSurveyAnswer_tem> getQuestionAnswerOptions() {
        return QuestionAnswerOptions;
    }

    public void setQuestionAnswerOptions(ArrayList<ListSurveyAnswer_tem> questionAnswerOptions) {
        QuestionAnswerOptions = questionAnswerOptions;
    }
    public SurveyListAnswerItem() {
    }

    @Expose
    @SerializedName("QuestionType")
    private String QuestionType;
    @Expose
    @SerializedName("AnswerText")
    private String AnswerText;
    @Expose
    @SerializedName("AnswerPosition")
    private int AnswerPosition;
    @Expose
    @SerializedName("QuestionPosition")
    private int QuestionPosition;
    @Expose
    @SerializedName("Question")
    private String Question;
    @Expose
    @SerializedName("QuestionAnswerOptions")
    private ArrayList<ListSurveyAnswer_tem> QuestionAnswerOptions = null;
}

