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


    public SurveyListAnswerItem() {
    }


    public SurveyListAnswerItem(String questionType, String answerText, int answerPosition, int questionPosition, String questionGroupCd, String questionGroupName, String question, Integer position, ArrayList<ListSurveyAnswer_tem> questionAnswerOptions) {
        QuestionType = questionType;
        AnswerText = answerText;
        AnswerPosition = answerPosition;
        QuestionPosition = questionPosition;
        QuestionGroupCd = questionGroupCd;
        QuestionGroupName = questionGroupName;
        Question = question;
        Position = position;
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

    public String getQuestionGroupCd() {
        return QuestionGroupCd;
    }

    public void setQuestionGroupCd(String questionGroupCd) {
        QuestionGroupCd = questionGroupCd;
    }

    public String getQuestionGroupName() {
        return QuestionGroupName;
    }

    public void setQuestionGroupName(String questionGroupName) {
        QuestionGroupName = questionGroupName;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public Integer getPosition() {
        return Position;
    }

    public void setPosition(Integer position) {
        Position = position;
    }

    public ArrayList<ListSurveyAnswer_tem> getQuestionAnswerOptions() {
        return QuestionAnswerOptions;
    }

    public void setQuestionAnswerOptions(ArrayList<ListSurveyAnswer_tem> questionAnswerOptions) {
        QuestionAnswerOptions = questionAnswerOptions;
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
    @SerializedName("QuestionGroupCd")
    private String QuestionGroupCd;
    @SerializedName("QuestionGroupName")
    private String QuestionGroupName;
    @Expose
    @SerializedName("Question")
    private String Question;
    @Expose
    @SerializedName("Position")
    private Integer Position;
    @Expose
    @SerializedName("QuestionAnswerOptions")
    private ArrayList<ListSurveyAnswer_tem> QuestionAnswerOptions = null;
}

