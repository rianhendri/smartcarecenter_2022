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

import java.util.ArrayList;

public class ListSurvey_tem {

    public ListSurvey_tem() {
    }

    public ListSurvey_tem(String surveyCd, Integer position, String question, String questionType,
                          boolean optional, ArrayList<ListSurveyAnswer_tem> answers) {
        SurveyCd = surveyCd;
        Position = position;
        Question = question;
        QuestionType = questionType;
        Optional = optional;
        Answers = answers;
    }

    public String getSurveyCd() {
        return SurveyCd;
    }

    public void setSurveyCd(String surveyCd) {
        SurveyCd = surveyCd;
    }

    public Integer getPosition() {
        return Position;
    }

    public void setPosition(Integer position) {
        Position = position;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getQuestionType() {
        return QuestionType;
    }

    public void setQuestionType(String questionType) {
        QuestionType = questionType;
    }

    public boolean isOptional() {
        return Optional;
    }

    public void setOptional(boolean optional) {
        Optional = optional;
    }

    public ArrayList<ListSurveyAnswer_tem> getAnswer() {
        return Answers;
    }

    public void setAnswer(ArrayList<ListSurveyAnswer_tem> answer) {
        Answers = answer;
    }

    @SerializedName("SurveyCd")
    private String SurveyCd;
    @Expose
    @SerializedName("Position")
    private Integer Position;
    @Expose
    @SerializedName("Question")
    private String Question;
    @Expose
    @SerializedName("QuestionType")
    private String QuestionType;
    @Expose
    @SerializedName("Optional")
    private boolean Optional;
    @Expose
    @SerializedName("Answers")
    private ArrayList<ListSurveyAnswer_tem> Answers;

}



