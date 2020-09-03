/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.Html
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.RatingBar
 *  android.widget.TextView
 *  androidx.recyclerview.widget.RecyclerView
 *  androidx.recyclerview.widget.RecyclerView$Adapter
 *  androidx.recyclerview.widget.RecyclerView$ViewHolder
 *  java.io.PrintStream
 *  java.lang.CharSequence
 *  java.lang.Float
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
package com.smartcarecenter.serviceticket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartcarecenter.FormActivity;
import com.smartcarecenter.NewsActivity;
import com.smartcarecenter.R;
import com.smartcarecenter.SettingActivity;
import com.smartcarecenter.menuhome.MenuAdapter;
import com.smartcarecenter.menuhome.MenuItem;
import com.smartcarecenter.serviceticket.ServicesTicketItem;
import com.squareup.picasso.Picasso;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.smartcarecenter.Dashboard.installed;

public class ServiceTicketAdapter
extends RecyclerView.Adapter<ServiceTicketAdapter.Myviewholder> {
    boolean solved = true;
    Context context;
    ArrayList<ServicesTicketItem> myItem;
    public static int positem = 0;

    public ServiceTicketAdapter(Context c, ArrayList<ServicesTicketItem> p){
        context = c;
        myItem = p;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_serviceticket,
                viewGroup, false));


    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        TextView textView = myviewholder.mstatustik;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#");
        stringBuilder.append(String.valueOf((int)((ServicesTicketItem)this.myItem.get(i)).getPosition()));
        textView.setText((CharSequence)stringBuilder.toString());

        String string5 = ((ServicesTicketItem)this.myItem.get(i)).getAssignedDateTime();
        String string6 = ((ServicesTicketItem)this.myItem.get(i)).getSupportStartDateTime();
        String string7 = ((ServicesTicketItem)this.myItem.get(i)).getSupportEndDateTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.getDefault());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault());
        String string8 = ((ServicesTicketItem)this.myItem.get(i)).getBar1Text();
        String string9 = ((ServicesTicketItem)this.myItem.get(i)).getBar2Text();
        String string10 = ((ServicesTicketItem)this.myItem.get(i)).getBar3Text();
        String string11 = ((ServicesTicketItem)this.myItem.get(i)).getBar4Text();
        if (Build.VERSION.SDK_INT >= 24) {
            myviewholder.mbar1.setText((CharSequence)Html.fromHtml((String)string8, Html.FROM_HTML_MODE_COMPACT));
            myviewholder.mbar2.setText((CharSequence)Html.fromHtml((String)string9, Html.FROM_HTML_MODE_COMPACT));
            myviewholder.mbar3.setText((CharSequence)Html.fromHtml((String)string10, Html.FROM_HTML_MODE_COMPACT));
            myviewholder.mbar4.setText((CharSequence)Html.fromHtml((String)string11, Html.FROM_HTML_MODE_COMPACT));
        } else {
            myviewholder.mbar1.setText((CharSequence)Html.fromHtml((String)string8));
            myviewholder.mbar2.setText((CharSequence)Html.fromHtml((String)string9));
            myviewholder.mbar3.setText((CharSequence)Html.fromHtml((String)string10));
            myviewholder.mbar4.setText((CharSequence)Html.fromHtml((String)string11));
        }
        solved = myItem.get(i).isBar4Red();
        String startdate = "";
        String enddate = "";
        String assigndate = "";
        if (myItem.get(i).getSupportStartDateTime()!=null){
            if (myItem.get(i).getSupportEndDateTime()!=null){
                String oladend = myItem.get(i).getSupportEndDateTime();
                try {
                    enddate = simpleDateFormat2.format(simpleDateFormat.parse(oladend));
                    System.out.println(enddate);
                    Log.e((String)"Date", (String)startdate);
                }
                catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }
            String oladstart = myItem.get(i).getSupportStartDateTime();

            try {
                startdate = simpleDateFormat2.format(simpleDateFormat.parse(oladstart));
                System.out.println(startdate);
                Log.e((String)"Date", (String)startdate);
            }
            catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        }
        if (myItem.get(i).getAssignedDateTime()!=null){
            String oldass = myItem.get(i).getAssignedDateTime();
            try {
                assigndate = simpleDateFormat2.format(simpleDateFormat.parse(oldass));
                System.out.println(enddate);
                Log.e((String)"Date", (String)startdate);
            }
            catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            myviewholder.massigndate.setText(assigndate);
        }


        if (myItem.get(i).getFeedbackComments().equals("")){
            myviewholder.mlayoutstart.setAlpha(0);
        }else {
            myviewholder.mlayoutstart.setAlpha(1);
            myviewholder.mrating.setRating(myItem.get(i).getFeedbackRating());
            myviewholder.mcomment.setText(myItem.get(i).getFeedbackComments());
        }
        if (myItem.get(i).getBarPosition()==1){
            myviewholder.mposbar.setImageResource(R.drawable.asign);
            myviewholder.mbar1.setTextColor(context.getResources().getColor(R.color.black));
        } else if (myItem.get(i).getBarPosition() == 2) {
            myviewholder.mposbar.setImageResource(R.drawable.onprogress);

            myviewholder.mbar2.setTextColor(context.getResources().getColor(R.color.black));

        }else if (myItem.get(i).getBarPosition() == 3){
            myviewholder.mposbar.setImageResource(R.drawable.waiting);

            myviewholder.mbar2.setTextColor(context.getResources().getColor(R.color.black));
            myviewholder.mbar3.setTextColor(context.getResources().getColor(R.color.black));

        }else if (solved){
            myviewholder.mposbar.setImageResource(R.drawable.unsolved);

            myviewholder.mbar2.setTextColor(context.getResources().getColor(R.color.black));
            myviewholder.mbar3.setTextColor(context.getResources().getColor(R.color.black));
            myviewholder.mbar4.setTextColor(context.getResources().getColor(R.color.black));
        }
        else {
            myviewholder.mposbar.setImageResource(R.drawable.complete);

            myviewholder.mbar2.setTextColor(context.getResources().getColor(R.color.black));
            myviewholder.mbar3.setTextColor(context.getResources().getColor(R.color.black));
            myviewholder.mbar4.setTextColor(context.getResources().getColor(R.color.black));

        }
        myviewholder.mstarttime.setText(startdate);
        myviewholder.mendtime.setText(enddate);
        myviewholder.mengineer.setText(myItem.get(i).getEngineerName());


    }

    @Override
    public int getItemCount() {
        return
                myItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView massigndate;
        TextView mbar1,mbar2,mbar3,mbar4,mcomment,mendtime,mengineer,mservicetype,mstarttime
                ,mstatusservice,mstatustik;

        ImageView mposbar;
        RatingBar mrating;
        LinearLayout mlayoutstart;
        public Myviewholder(@NonNull View view) {
            super(view);

            massigndate = (TextView)view.findViewById(R.id.assigndate);
            mengineer = (TextView)view.findViewById(R.id.engineer);

            mstarttime = (TextView)view.findViewById(R.id.starttime);
            mendtime = (TextView)view.findViewById(R.id.endtime);
            mrating = (RatingBar)view.findViewById(R.id.ratingstar);
            mbar1 = (TextView)view.findViewById(R.id.bar1);
            mbar2 = (TextView)view.findViewById(R.id.bar2);
            mbar3 = (TextView)view.findViewById(R.id.bar3);
            mbar4 = (TextView)view.findViewById(R.id.bar4);
            mposbar = (ImageView)view.findViewById(R.id.posbar);
            mstatustik = (TextView)view.findViewById(R.id.statustick);
            mcomment = (TextView)view.findViewById(R.id.comment);
            mlayoutstart = view.findViewById(R.id.ratingstarlayout);

        }
    }

}

