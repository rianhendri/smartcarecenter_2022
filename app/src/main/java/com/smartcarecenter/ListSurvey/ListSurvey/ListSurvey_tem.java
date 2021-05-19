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


    @SerializedName("SurveyCd")
    private String SurveyCd;

    public ListSurvey_tem(String surveyCd, String groupCd, String groupName, Integer position, Integer maxTextLength, String question, String questionType, boolean optional, ArrayList<ListSurveyAnswer_tem> answers) {
        SurveyCd = surveyCd;
        GroupCd = groupCd;
        GroupName = groupName;
        Position = position;
        MaxTextLength = maxTextLength;
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

    public String getGroupCd() {
        return GroupCd;
    }

    public void setGroupCd(String groupCd) {
        GroupCd = groupCd;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public Integer getPosition() {
        return Position;
    }

    public void setPosition(Integer position) {
        Position = position;
    }

    public Integer getMaxTextLength() {
        return MaxTextLength;
    }

    public void setMaxTextLength(Integer maxTextLength) {
        MaxTextLength = maxTextLength;
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

    public ArrayList<ListSurveyAnswer_tem> getAnswers() {
        return Answers;
    }

    public void setAnswers(ArrayList<ListSurveyAnswer_tem> answers) {
        Answers = answers;
    }
    @SerializedName("GroupCd")
    private String GroupCd;
    @SerializedName("GroupName")
    private String GroupName;
    @Expose
    @SerializedName("Position")
    private Integer Position;
    @Expose
    @SerializedName("MaxTextLength")
    private Integer MaxTextLength;
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
    private ArrayList<ListSurveyAnswer_tem> Answers = null;

}



