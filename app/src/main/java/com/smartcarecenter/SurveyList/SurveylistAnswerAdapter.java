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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import static com.smartcarecenter.DetailsSurvey.mrecylersurvey;
import static com.smartcarecenter.DetailsSurvey.surveylistanswer;
import static com.smartcarecenter.SurveyActivity.MrecylerSurvey;
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
        int liat = myItem.get(i).getQuestionPosition();
//        if (liat==myItem.size()){
//            Log.e("liat", i + " :posisi: " + String.valueOf(liat));
//            mrecylersurvey.setNestedScrollingEnabled(false);
//        }
        if (myItem.get(i).getQuestionType().equals("Text")){
            myviewholder.mansgr.setVisibility(View.GONE);
            myviewholder.Manstext.setVisibility(View.VISIBLE);
            myviewholder.Manstext.setText(myItem.get(i).getAnswerText());

        }else {
            myviewholder.mansgr.setVisibility(View.VISIBLE);
            myviewholder.Manstext.setVisibility(View.GONE);
            String ada = "";
            if (myItem.get(i).getQuestionAnswerOptions()!=null){
                Log.e("liat", i + " :posisi: " + String.valueOf(myItem.get(i).getQuestionAnswerOptions().size()));

                ada = String.valueOf(myItem.get(i).getQuestionAnswerOptions().size());
                if (myItem.get(i).getQuestionAnswerOptions().size()==2){
                    if (myItem.get(i).getAnswerPosition()==1){
                        myviewholder.mans1.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==2){
                        myviewholder.mans2.setChecked(true);
                    }
                    myviewholder.mans1.setText(myItem.get(i).getQuestionAnswerOptions().get(0).getAnswer());
                    myviewholder.mans2.setText(myItem.get(i).getQuestionAnswerOptions().get(1).getAnswer());
                }
                if (myItem.get(i).getQuestionAnswerOptions().size()==3){
                    if (myItem.get(i).getAnswerPosition()==1){
                        myviewholder.mans1.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==2){
                        myviewholder.mans2.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==3){
                        myviewholder.mans3.setChecked(true);
                    }
                    myviewholder.mans1.setText(myItem.get(i).getQuestionAnswerOptions().get(0).getAnswer());
                    myviewholder.mans2.setText(myItem.get(i).getQuestionAnswerOptions().get(1).getAnswer());
                    myviewholder.mans3.setText(myItem.get(i).getQuestionAnswerOptions().get(2).getAnswer());
                }
                if (myItem.get(i).getQuestionAnswerOptions().size()==4){
                    if (myItem.get(i).getAnswerPosition()==1){
                        myviewholder.mans1.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==2){
                        myviewholder.mans2.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==3){
                        myviewholder.mans3.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==4){
                        myviewholder.mans4.setChecked(true);
                    }
                    myviewholder.mans1.setText(myItem.get(i).getQuestionAnswerOptions().get(0).getAnswer());
                    myviewholder.mans2.setText(myItem.get(i).getQuestionAnswerOptions().get(1).getAnswer());
                    myviewholder.mans3.setText(myItem.get(i).getQuestionAnswerOptions().get(2).getAnswer());
                    myviewholder.mans4.setText(myItem.get(i).getQuestionAnswerOptions().get(3).getAnswer());
                }
                if (myItem.get(i).getQuestionAnswerOptions().size()==5){
                    if (myItem.get(i).getAnswerPosition()==1){
                        myviewholder.mans1.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==2){
                        myviewholder.mans2.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==3){
                        myviewholder.mans3.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==4){
                        myviewholder.mans4.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==5){
                        myviewholder.mans5.setChecked(true);
                    }
                    myviewholder.mans1.setText(myItem.get(i).getQuestionAnswerOptions().get(0).getAnswer());
                    myviewholder.mans2.setText(myItem.get(i).getQuestionAnswerOptions().get(1).getAnswer());
                    myviewholder.mans3.setText(myItem.get(i).getQuestionAnswerOptions().get(2).getAnswer());
                    myviewholder.mans4.setText(myItem.get(i).getQuestionAnswerOptions().get(3).getAnswer());
                    myviewholder.mans5.setText(myItem.get(i).getQuestionAnswerOptions().get(4).getAnswer());
                }
                if (myItem.get(i).getQuestionAnswerOptions().size()==6){
                    if (myItem.get(i).getAnswerPosition()==1){
                        myviewholder.mans1.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==2){
                        myviewholder.mans2.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==3){
                        myviewholder.mans3.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==4){
                        myviewholder.mans4.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==5){
                        myviewholder.mans5.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==6){
                        myviewholder.mans6.setChecked(true);
                    }
                    myviewholder.mans1.setText(myItem.get(i).getQuestionAnswerOptions().get(0).getAnswer());
                    myviewholder.mans2.setText(myItem.get(i).getQuestionAnswerOptions().get(1).getAnswer());
                    myviewholder.mans3.setText(myItem.get(i).getQuestionAnswerOptions().get(2).getAnswer());
                    myviewholder.mans4.setText(myItem.get(i).getQuestionAnswerOptions().get(3).getAnswer());
                    myviewholder.mans5.setText(myItem.get(i).getQuestionAnswerOptions().get(4).getAnswer());
                    myviewholder.mans6.setText(myItem.get(i).getQuestionAnswerOptions().get(5).getAnswer());
                }
                if (myItem.get(i).getQuestionAnswerOptions().size()==7){
                    if (myItem.get(i).getAnswerPosition()==1){
                        myviewholder.mans1.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==2){
                        myviewholder.mans2.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==3){
                        myviewholder.mans3.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==4){
                        myviewholder.mans4.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==5){
                        myviewholder.mans5.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==6){
                        myviewholder.mans6.setChecked(true);
                    }if (myItem.get(i).getAnswerPosition()==7){
                        myviewholder.mans7.setChecked(true);
                    }
                    myviewholder.mans1.setText(myItem.get(i).getQuestionAnswerOptions().get(0).getAnswer());
                    myviewholder.mans2.setText(myItem.get(i).getQuestionAnswerOptions().get(1).getAnswer());
                    myviewholder.mans3.setText(myItem.get(i).getQuestionAnswerOptions().get(2).getAnswer());
                    myviewholder.mans4.setText(myItem.get(i).getQuestionAnswerOptions().get(3).getAnswer());
                    myviewholder.mans5.setText(myItem.get(i).getQuestionAnswerOptions().get(4).getAnswer());
                    myviewholder.mans6.setText(myItem.get(i).getQuestionAnswerOptions().get(5).getAnswer());
                    myviewholder.mans7.setText(myItem.get(i).getQuestionAnswerOptions().get(6).getAnswer());
                }
                if (myItem.get(i).getQuestionAnswerOptions().size()==8){
                    if (myItem.get(i).getAnswerPosition()==1){
                        myviewholder.mans1.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==2){
                        myviewholder.mans2.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==3){
                        myviewholder.mans3.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==4){
                        myviewholder.mans4.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==5){
                        myviewholder.mans5.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==6){
                        myviewholder.mans6.setChecked(true);
                    }if (myItem.get(i).getAnswerPosition()==7){
                        myviewholder.mans7.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==8){
                        myviewholder.mans8.setChecked(true);
                    }
                    myviewholder.mans1.setText(myItem.get(i).getQuestionAnswerOptions().get(0).getAnswer());
                    myviewholder.mans2.setText(myItem.get(i).getQuestionAnswerOptions().get(1).getAnswer());
                    myviewholder.mans3.setText(myItem.get(i).getQuestionAnswerOptions().get(2).getAnswer());
                    myviewholder.mans4.setText(myItem.get(i).getQuestionAnswerOptions().get(3).getAnswer());
                    myviewholder.mans5.setText(myItem.get(i).getQuestionAnswerOptions().get(4).getAnswer());
                    myviewholder.mans6.setText(myItem.get(i).getQuestionAnswerOptions().get(5).getAnswer());
                    myviewholder.mans7.setText(myItem.get(i).getQuestionAnswerOptions().get(6).getAnswer());
                    myviewholder.mans8.setText(myItem.get(i).getQuestionAnswerOptions().get(7).getAnswer());
                }
                if (myItem.get(i).getQuestionAnswerOptions().size()==9){
                    if (myItem.get(i).getAnswerPosition()==1){
                        myviewholder.mans1.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==2){
                        myviewholder.mans2.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==3){
                        myviewholder.mans3.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==4){
                        myviewholder.mans4.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==5){
                        myviewholder.mans5.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==6){
                        myviewholder.mans6.setChecked(true);
                    }if (myItem.get(i).getAnswerPosition()==7){
                        myviewholder.mans7.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==8){
                        myviewholder.mans8.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==9){
                        myviewholder.mans9.setChecked(true);
                    }
                    myviewholder.mans1.setText(myItem.get(i).getQuestionAnswerOptions().get(0).getAnswer());
                    myviewholder.mans2.setText(myItem.get(i).getQuestionAnswerOptions().get(1).getAnswer());
                    myviewholder.mans3.setText(myItem.get(i).getQuestionAnswerOptions().get(2).getAnswer());
                    myviewholder.mans4.setText(myItem.get(i).getQuestionAnswerOptions().get(3).getAnswer());
                    myviewholder.mans5.setText(myItem.get(i).getQuestionAnswerOptions().get(4).getAnswer());
                    myviewholder.mans6.setText(myItem.get(i).getQuestionAnswerOptions().get(5).getAnswer());
                    myviewholder.mans7.setText(myItem.get(i).getQuestionAnswerOptions().get(6).getAnswer());
                    myviewholder.mans8.setText(myItem.get(i).getQuestionAnswerOptions().get(7).getAnswer());
                    myviewholder.mans9.setText(myItem.get(i).getQuestionAnswerOptions().get(8).getAnswer());
                }
                if (myItem.get(i).getQuestionAnswerOptions().size()==10){
                    if (myItem.get(i).getAnswerPosition()==1){
                        myviewholder.mans1.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==2){
                        myviewholder.mans2.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==3){
                        myviewholder.mans3.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==4){
                        myviewholder.mans4.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==5){
                        myviewholder.mans5.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==6){
                        myviewholder.mans6.setChecked(true);
                    }if (myItem.get(i).getAnswerPosition()==7){
                        myviewholder.mans7.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==8){
                        myviewholder.mans8.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==9){
                        myviewholder.mans9.setChecked(true);
                    }
                    if (myItem.get(i).getAnswerPosition()==10){
                        myviewholder.mans9.setChecked(true);
                    }
                    myviewholder.mans1.setText(myItem.get(i).getQuestionAnswerOptions().get(0).getAnswer());
                    myviewholder.mans2.setText(myItem.get(i).getQuestionAnswerOptions().get(1).getAnswer());
                    myviewholder.mans3.setText(myItem.get(i).getQuestionAnswerOptions().get(2).getAnswer());
                    myviewholder.mans4.setText(myItem.get(i).getQuestionAnswerOptions().get(3).getAnswer());
                    myviewholder.mans5.setText(myItem.get(i).getQuestionAnswerOptions().get(4).getAnswer());
                    myviewholder.mans6.setText(myItem.get(i).getQuestionAnswerOptions().get(5).getAnswer());
                    myviewholder.mans7.setText(myItem.get(i).getQuestionAnswerOptions().get(6).getAnswer());
                    myviewholder.mans8.setText(myItem.get(i).getQuestionAnswerOptions().get(7).getAnswer());
                    myviewholder.mans9.setText(myItem.get(i).getQuestionAnswerOptions().get(8).getAnswer());
                    myviewholder.mans10.setText(myItem.get(i).getQuestionAnswerOptions().get(9).getAnswer());
                }
            }
            Log.d("test", ada);


            if (myviewholder.mans1.getText().toString().equals(""))
            {
                myviewholder.mans1.setVisibility(View.GONE);
            }else {
                myviewholder.mans1.setVisibility(View.VISIBLE);
            }
            if (myviewholder.mans2.getText().toString().equals(""))
            {
                myviewholder.mans2.setVisibility(View.GONE);
            }else {
                myviewholder.mans2.setVisibility(View.VISIBLE);
            }
            if (myviewholder.mans3.getText().toString().equals(""))
            {
                myviewholder.mans3.setVisibility(View.GONE);
            }else {
                myviewholder.mans3.setVisibility(View.VISIBLE);
            }
            if (myviewholder.mans4.getText().toString().equals(""))
            {
                myviewholder.mans4.setVisibility(View.GONE);
            }else {
                myviewholder.mans4.setVisibility(View.VISIBLE);
            }
            if (myviewholder.mans5.getText().toString().equals(""))
            {
                myviewholder.mans5.setVisibility(View.GONE);
            }else {
                myviewholder.mans5.setVisibility(View.VISIBLE);
            }
            if (myviewholder.mans6.getText().toString().equals(""))
            {
                myviewholder.mans6.setVisibility(View.GONE);
            }else {
                myviewholder.mans6.setVisibility(View.VISIBLE);
            }
            if (myviewholder.mans7.getText().toString().equals(""))
            {
                myviewholder.mans7.setVisibility(View.GONE);
            }else {
                myviewholder.mans7.setVisibility(View.VISIBLE);
            }
            if (myviewholder.mans8.getText().toString().equals(""))
            {
                myviewholder.mans8.setVisibility(View.GONE);
            }else {
                myviewholder.mans8.setVisibility(View.VISIBLE);
            }
            if (myviewholder.mans9.getText().toString().equals(""))
            {
                myviewholder.mans9.setVisibility(View.GONE);
            }else {
                myviewholder.mans9.setVisibility(View.VISIBLE);
            }
            if (myviewholder.mans10.getText().toString().equals(""))
            {
                myviewholder.mans10.setVisibility(View.GONE);
            }else {
                myviewholder.mans10.setVisibility(View.VISIBLE);
            }
            myviewholder.mans1.setEnabled(false);
            myviewholder.mans2.setEnabled(false);
            myviewholder.mans3.setEnabled(false);
            myviewholder.mans4.setEnabled(false);
            myviewholder.mans5.setEnabled(false);
            myviewholder.mans6.setEnabled(false);
            myviewholder.mans7.setEnabled(false);
            myviewholder.mans8.setEnabled(false);
            myviewholder.mans9.setEnabled(false);
            myviewholder.mans10.setEnabled(false);


//            myviewholder.mansgr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup group, int checkedId) {
//                    int radioBtnID = group.getCheckedRadioButtonId();
//                    View radioB = group.findViewById(radioBtnID);
//                    int position = group.indexOfChild(radioB);
//                    listAnswer.get(i).setAnswerPosition(listsurvey.get(i).getAnswer().get(position).getAnswerPosition());
//                    Log.e("POSISI", i + " :onCheckedChanged: " + String.valueOf(listsurvey.get(i).getAnswer().get(position).getAnswerPosition()));
//                }
//            });
        }
//        for(int x=0;x<surveylistanswer.size();x++){
//            JsonObject movie = surveylistanswer.get(x).getAsJsonObject();
//            if (movie.get("QuestionAnswerOptions").toString().equals("null")){
//
//            }else {
//                listanwermulti = movie.getAsJsonArray("QuestionAnswerOptions");
////                Toast.makeText(context, listanwermulti.toString(), Toast.LENGTH_SHORT).show();
//            }
//
////                        for(int j=0;j<characters.size();j++){
////                            temp.add(characters.get(i).toString());
////                        }
//
////                        Toast.makeText(SubMenuHome.this, characters.toString(), Toast.LENGTH_LONG).show();
//        }
//        Gson gson2 = new Gson();
//        Type listType2 = new TypeToken<ArrayList<SurveyAnswer_tem>>() {
//        }.getType();
//        imglist = gson2.fromJson(listanwermulti.toString(),listType2);
//        if(imglist!=null && imglist.size()!=0){
//            if (imgAdapter!=null){
////                                imgAdapter.clear();
//            }
//
//            imgAdapter = new SurveyAnswer_adapter(context, imglist);
//            myviewholder.Mmultiplerecyler.setAdapter(imgAdapter);
////                            mlihatimg.setVisibility(View.GONE);
//
//
//        }
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
        private RadioGroup mansgr;
        private RadioButton mans1, mans2, mans3,mans4,mans5,mans6,mans7,mans8,mans9,mans10 ;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mnoSurvey = itemView.findViewById(R.id.noSurvey);
            MtitleAnswer=itemView.findViewById(R.id.titleAnswer);
            Mmultiplerecyler = itemView.findViewById(R.id.multiplerecyler);
            Manstext = itemView.findViewById(R.id.anstext);
            mrequiredanswer=itemView.findViewById(R.id.requiredanswer);
            mans1 = itemView.findViewById(R.id.ans1);
            mans2 = itemView.findViewById(R.id.ans2);
            mans3 = itemView.findViewById(R.id.ans3);
            mans4 = itemView.findViewById(R.id.ans4);
            mans5 = itemView.findViewById(R.id.ans5);
            mans6 = itemView.findViewById(R.id.ans6);
            mans7 = itemView.findViewById(R.id.ans7);
            mans8 = itemView.findViewById(R.id.ans8);
            mans9 = itemView.findViewById(R.id.ans9);
            mans10 = itemView.findViewById(R.id.ans10);
            mansgr = itemView.findViewById(R.id.ansgr);
        }
    }
}

