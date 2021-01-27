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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.Add_Foc_request.Add_foc_req_item;
import com.smartcarecenter.R;
import com.smartcarecenter.SurveyActivity;

import org.jsoup.select.Evaluator;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.smartcarecenter.AddDetailFoc.myCustomArray;
import static com.smartcarecenter.SurveyActivity.AnswersArray;
import static com.smartcarecenter.SurveyActivity.listformreq;
import static java.sql.Types.NULL;

public class ListSurvey_adapter
extends RecyclerView.Adapter<ListSurvey_adapter.Myviewholder> {
    public static ArrayList<ListSurvey_tem> listsurvey;
    public static JsonArray listanwermulti;
    public  static ArrayList<ListSurveyAnswer_tem> imglist;
    public static ArrayList<AnswerSurvey_tem> setanswer ;
    public static ArrayList<AnswerSurvey_tem> listAnswer = new ArrayList<AnswerSurvey_tem>();
    public  static AnswerSurvey_tem Answers;
    private LinearLayoutManager linearLayoutManager;
    ListSurveyAnswer_adapter imgAdapter;
    ListSurvey_tem modelqty;
    Context context;
    ImageView mimgpopup;
    double subharga = 0.0;
    public static int totalqty = 0;
    public static double totalprice = 0.0;
    boolean required = true;
    public ListSurvey_adapter(Context context, ArrayList<ListSurvey_tem> ListSurvey) {
        this.context = context;
        this.listsurvey = ListSurvey;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_survey,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        myviewholder.mmultiplerecyler.setLayoutManager(linearLayoutManager);
        myviewholder.mmultiplerecyler.setHasFixedSize(true);
        imglist = new ArrayList<ListSurveyAnswer_tem>();
        ////load
        for(int x=0;x<listformreq.size();x++){
            JsonObject movie = listformreq.get(x).getAsJsonObject();
            if (movie.get("Answers").toString().equals("null")){

            }else {
                listanwermulti = movie.getAsJsonArray("Answers");
//                Toast.makeText(context, listanwermulti.toString(), Toast.LENGTH_SHORT).show();
            }

//                        for(int j=0;j<characters.size();j++){
//                            temp.add(characters.get(i).toString());
//                        }

//                        Toast.makeText(SubMenuHome.this, characters.toString(), Toast.LENGTH_LONG).show();
        }
        Gson gson2 = new Gson();
        Type listType2 = new TypeToken<ArrayList<ListSurveyAnswer_tem>>() {
        }.getType();
        imglist = gson2.fromJson(listanwermulti.toString(),listType2);
        if(imglist!=null && imglist.size()!=0){
            if (imgAdapter!=null){
//                                imgAdapter.clear();
            }

            imgAdapter = new ListSurveyAnswer_adapter(context, imglist);
            myviewholder.mmultiplerecyler.setAdapter(imgAdapter);
//                            mlihatimg.setVisibility(View.GONE);


        }
        required = listsurvey.get(i).isOptional();
        if (!required){
            myviewholder.mrequired.setVisibility(View.VISIBLE);
        }else {
            myviewholder.mrequired.setVisibility(View.GONE);
        }
        myviewholder.mnosur.setText(String.valueOf(i+1)+".");
        myviewholder.mtitleAnswer.setText(listsurvey.get(i).getQuestion());
        if (listsurvey.get(i).getQuestionType().equals("Text")){
            myviewholder.mmultiplerecyler.setVisibility(View.GONE);
            myviewholder.manstext.setVisibility(View.VISIBLE);
        }else {
            myviewholder.mmultiplerecyler.setVisibility(View.VISIBLE);
            myviewholder.manstext.setVisibility(View.GONE);
        }

        Answers = new AnswerSurvey_tem();
        Answers.setAnswerPosition(0);
        Answers.setQuestionPosition(listsurvey.get(i).getPosition());
        Answers.setAnswerText("");
        Answers.setOptional(listsurvey.get(i).isOptional());
        Answers.setQuestionType(listsurvey.get(i).getQuestionType());
        listAnswer.add(Answers);
        myviewholder.manstext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (myviewholder.manstext.length()==0){
                    listAnswer.get(i).setAnswerText("");
                }else {
                    listAnswer.get(i).setAnswerText(myviewholder.manstext.getText().toString());
//                    Toast.makeText(context, listAnswer.toString(),Toast.LENGTH_LONG).show();
                }
                Gson gson = new GsonBuilder().create();
                AnswersArray = gson.toJsonTree(listAnswer).getAsJsonArray();
//                Toast.makeText(context, AnswersArray.toString(),Toast.LENGTH_LONG).show();
            }

        });
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAnswer.get(i).setAnswerPosition(i);
                Gson gson = new GsonBuilder().create();
                AnswersArray = gson.toJsonTree(listAnswer).getAsJsonArray();
                Toast.makeText(context, AnswersArray.toString(),Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 
                listsurvey.size();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder{

        TextView mnosur, mtitleAnswer,mrequired;
        EditText manstext;
        RecyclerView mmultiplerecyler;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);

            mnosur = itemView.findViewById(R.id.noSurvey);
            mtitleAnswer = itemView.findViewById(R.id.titleAnswer);
            manstext = itemView.findViewById(R.id.anstext);
            mrequired = itemView.findViewById(R.id.requiredanswer);
            mmultiplerecyler = itemView.findViewById(R.id.multiplerecyler);




        }
    }
}

