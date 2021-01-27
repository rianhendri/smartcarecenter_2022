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
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.smartcarecenter.DetailsNews;
import com.smartcarecenter.DetailsSurvey;
import com.smartcarecenter.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.smartcarecenter.DetailsSurvey.surveylistanswer;
import static com.smartcarecenter.SurveyList_Activity.listnews;

public class SurveylistAdapter
extends RecyclerView.Adapter<SurveylistAdapter.Myviewholder> {

    String newdate = "";
    Context context;
    ArrayList<SurveyListItem> myItem;

    public static int positem = 0;
    public static boolean download = true;
    public static String textdownload = "";
    public static String linkdownload = "";

    public SurveylistAdapter(Context c, ArrayList<SurveyListItem> p){
        context = c;
        myItem = p;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_listsurvey,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        myviewholder.mname.setText(myItem.get(i).getTitle());
        String string2 = (myItem.get(i).getFeedbackDateTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            newdate = simpleDateFormat2.format(simpleDateFormat.parse(string2));
            System.out.println(newdate);
            Log.e((String)"Date", newdate);
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        myviewholder.mdate.setText(newdate);
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int x=0;x<listnews.size();x++){
                    JsonObject movie = listnews.get(i).getAsJsonObject();
                    if (movie.get("answers").toString().equals("null")){

                    }else {
                        surveylistanswer = movie.getAsJsonArray("answers");
//                Toast.makeText(context, listanwermulti.toString(), Toast.LENGTH_SHORT).show();
                    }

//                        for(int j=0;j<characters.size();j++){
//                            temp.add(characters.get(i).toString());
//                        }

//                        Toast.makeText(SubMenuHome.this, characters.toString(), Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(context, DetailsSurvey.class);
                intent.putExtra("title", (myItem.get(i).getTitle()));
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                ((Activity)context).finish();

            }
        });

    }

    @Override
    public int getItemCount() {
        return
                myItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView  mdate,mname,mconten;
        ImageView mpic;
        ProgressBar mporg;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mdate = itemView.findViewById(R.id.datenews);
            mpic=itemView.findViewById(R.id.xpic);
            mname = itemView.findViewById(R.id.titlenews);
            mconten = itemView.findViewById(R.id.contentview);

        }
    }
}

