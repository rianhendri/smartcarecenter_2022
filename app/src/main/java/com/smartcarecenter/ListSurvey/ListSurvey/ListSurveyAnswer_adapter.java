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

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.smartcarecenter.ListSurvey.ListSurvey.ListSurvey_adapter.listAnswer;
import static com.smartcarecenter.SurveyActivity.AnswersArray;
import static com.smartcarecenter.SurveyActivity.listformreq;

public class ListSurveyAnswer_adapter
extends RecyclerView.Adapter<ListSurveyAnswer_adapter.Myviewholder> {
    public static ArrayList<ListSurveyAnswer_tem> listsurveyanswer;
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
    int anspos = 0;
    public ListSurveyAnswer_adapter(Context context, ArrayList<ListSurveyAnswer_tem> ListSurveyAnswer) {
        this.context = context;
        this.listsurveyanswer = ListSurveyAnswer;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_multianswer,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        if (selected_position ==  listsurveyanswer.get(i).getAnswerPosition()) {
            // do your stuff here like
            //Change selected item background color and Show sub item views
            myviewholder.mbgselect.setBackgroundColor(Color.parseColor("#F6EAC0"));
            myviewholder.mselect.setChecked(true);

        } else {
            // do your stuff here like
            //Change  unselected item background color and Hide sub item views
            myviewholder.mbgselect.setBackgroundColor(Color.parseColor("#ffffff"));
            myviewholder.mselect.setChecked(false);
        }
        myviewholder.mmultianswer.setText(listsurveyanswer.get(i).getAnswer());
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               pos = listsurveyanswer.get(i).getPosition();
                if (listAnswer.get(pos-1).getAnswerPosition()==i+1){
                    listAnswer.get(pos-1).setAnswerPosition(0);
                }else {
                    listAnswer.get(pos-1).setAnswerPosition(i+1);
                }

               Gson gson = new GsonBuilder().create();
               AnswersArray = gson.toJsonTree(listAnswer).getAsJsonArray();
//               Toast.makeText(context, String.valueOf(pos),Toast.LENGTH_LONG).show();
               Log.d("jawaban",AnswersArray.toString());
               if(selected_position==i+1){
                   selected_position=-1;
                   notifyDataSetChanged();
                   return;
               }
               selected_position = i+1;
               notifyDataSetChanged();


           }
       });
    }

    @Override
    public int getItemCount() {
        return
                listsurveyanswer.size();
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

