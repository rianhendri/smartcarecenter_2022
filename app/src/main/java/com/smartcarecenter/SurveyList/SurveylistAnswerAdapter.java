/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 *  androidx.recyclerview.widget.RecyclerView
 *  androidx.recyclerview.widget.RecyclerView$Adapter
 *  androidx.recyclerview.widget.RecyclerView$ViewHolder
 *  com.squareup.picasso.Picasso
 *  com.squareup.picasso.RequestCreator
 *  java.io.PrintStream
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.text.ParseException
 *  java.text.SimpleDateFormat
 *  java.util.ArrayList
 *  java.util.Date
 *  java.util.Locale
 */
package com.smartcarecenter.SurveyList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.DetailsSurvey;
import com.smartcarecenter.ListSurvey.ListSurvey.ListSurveyAnswer_adapter;
import com.smartcarecenter.ListSurvey.ListSurvey.ListSurveyAnswer_tem;
import com.smartcarecenter.R;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.smartcarecenter.DetailsSurvey.surveylistanswer;
import static com.smartcarecenter.SurveyActivity.listformreq;
import static com.smartcarecenter.SurveyList_Activity.listnews;

public class SurveylistAnswerAdapter
extends RecyclerView.Adapter<SurveylistAnswerAdapter.Myviewholder> {

    String newdate = "";
    Context context;
    ArrayList<SurveyListAnswerItem> myItem;
    ArrayList<SurveyAnswer_tem> imglist;
    SurveyAnswer_adapter imgAdapter;
    JsonArray listanwermulti;
    public static int answerpos=0;
    private LinearLayoutManager linearLayoutManager;
    public static int positem = 0;
    public static boolean download = true;
    public static String textdownload = "";
    public static String linkdownload = "";

    public SurveylistAnswerAdapter(Context c, ArrayList<SurveyListAnswerItem> p){
        context = c;
        myItem = p;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_survey,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        answerpos = myItem.get(i).getAnswerPosition();
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        myviewholder.Mmultiplerecyler.setLayoutManager(linearLayoutManager);
        myviewholder.Mmultiplerecyler.setHasFixedSize(true);
        imglist = new ArrayList<SurveyAnswer_tem>();
        if (myItem.get(i).getQuestionType().equals("Text")){
            myviewholder.Manstext.setVisibility(View.VISIBLE);
            myviewholder.Mmultiplerecyler.setVisibility(View.GONE);
            myviewholder.Manstext.setText(myItem.get(i).getAnswerText());
        }else {
            myviewholder.Manstext.setVisibility(View.GONE);
            myviewholder.Mmultiplerecyler.setVisibility(View.VISIBLE);
        }
        for(int x=0;x<surveylistanswer.size();x++){
            JsonObject movie = surveylistanswer.get(x).getAsJsonObject();
            if (movie.get("QuestionAnswerOptions").toString().equals("null")){

            }else {
                listanwermulti = movie.getAsJsonArray("QuestionAnswerOptions");
//                Toast.makeText(context, listanwermulti.toString(), Toast.LENGTH_SHORT).show();
            }

//                        for(int j=0;j<characters.size();j++){
//                            temp.add(characters.get(i).toString());
//                        }

//                        Toast.makeText(SubMenuHome.this, characters.toString(), Toast.LENGTH_LONG).show();
        }
        Gson gson2 = new Gson();
        Type listType2 = new TypeToken<ArrayList<SurveyAnswer_tem>>() {
        }.getType();
        imglist = gson2.fromJson(listanwermulti.toString(),listType2);
        if(imglist!=null && imglist.size()!=0){
            if (imgAdapter!=null){
//                                imgAdapter.clear();
            }

            imgAdapter = new SurveyAnswer_adapter(context, imglist);
            myviewholder.Mmultiplerecyler.setAdapter(imgAdapter);
//                            mlihatimg.setVisibility(View.GONE);


        }
        myviewholder.Manstext.setEnabled(false);
        myviewholder.mnoSurvey.setText(String.valueOf(i+1));
        myviewholder.MtitleAnswer.setText(myItem.get(i).getQuestion());
        myviewholder.mrequiredanswer.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return
                myItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView  mnoSurvey,MtitleAnswer,Manstext,mrequiredanswer;
        ImageView mpic;
        ProgressBar mporg;
        RecyclerView Mmultiplerecyler;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mnoSurvey = itemView.findViewById(R.id.noSurvey);
            MtitleAnswer=itemView.findViewById(R.id.titleAnswer);
            Mmultiplerecyler = itemView.findViewById(R.id.multiplerecyler);
            Manstext = itemView.findViewById(R.id.anstext);
            mrequiredanswer=itemView.findViewById(R.id.requiredanswer);

        }
    }
}

