/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Color
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
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.text.ParseException
 *  java.text.SimpleDateFormat
 *  java.util.ArrayList
 *  java.util.Date
 *  java.util.Locale
 */
package com.smartcarecenter.ListSurvey.ListSurvey;

import static com.smartcarecenter.DetailsSurvey.surveylistanswer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.smartcarecenter.R;

import java.util.ArrayList;

public class SurveyAnswer_adaptercircle
extends RecyclerView.Adapter<SurveyAnswer_adaptercircle.Myviewholder> {
    public static ArrayList<SurveyAnswer_temCircle> listsurveyanswercircle;
    public static JsonArray listanwermulti;
    ListSurvey_tem modelqty;
    Context context;
    ImageView mimgpopup;
    double subharga = 0.0;
    public static int totalqty = 0;
    public static double totalprice = 0.0;
    boolean required = true;
    private int selected_position = -1;
    public static int pos = 0;
    public SurveyAnswer_adaptercircle(Context context, ArrayList<SurveyAnswer_temCircle> ListSurveyAnswerCircle) {
        this.context = context;
        this.listsurveyanswercircle = ListSurveyAnswerCircle;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_multianswer,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        myviewholder.mmultianswer.setText(listsurveyanswercircle.get(i).getAnswer());
        for(int x=0;x<surveylistanswer.size();x++){
            JsonObject movie = surveylistanswer.get(x).getAsJsonObject();
            if (movie.get("AnswerPosition").getAsInt()==listsurveyanswercircle.get(i).getAnswerPosition()){
                myviewholder.mbgselect.setBackgroundColor(Color.parseColor("#F6EAC0"));
                myviewholder.mselect.setChecked(true);
            }else {
                myviewholder.mbgselect.setBackgroundColor(Color.parseColor("#ffffff"));
                myviewholder.mselect.setChecked(false);
            }

        }


    }

    @Override
    public int getItemCount() {
        return
                listsurveyanswercircle.size();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder{

        TextView mmultianswer, mtitleAnswer,mrequired;
        EditText manstext;
        RecyclerView mmultiplerecyler;
        RadioButton mselect;
        LinearLayout mbgselect;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);

            mmultianswer = itemView.findViewById(R.id.multianswer);
            mselect = itemView.findViewById(R.id.radioselect);
            mbgselect = itemView.findViewById(R.id.bgselect);




        }
    }
}

