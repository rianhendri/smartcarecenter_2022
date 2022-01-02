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
package com.smartcarecenter.dailyreport3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartcarecenter.DailiReportListFR;
import com.smartcarecenter.DetailsDailyReport2;
import com.smartcarecenter.DetailsDailyReport3;
import com.smartcarecenter.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.smartcarecenter.DailiReportListPM.hide;
import static com.smartcarecenter.DailyReportList.enddate;
import static com.smartcarecenter.DailyReportList.startdate;

public class DailyAdapter3
extends RecyclerView.Adapter<DailyAdapter3.Myviewholder> {
    ArrayList<DailyItem3> addFromItem;
    Context context;
    ImageView mimgpopup;
    public static String username = "";
    public static String valuefilter="";
    public static String noreq = "";
    public DailyAdapter3(Context context, ArrayList<DailyItem3> addFromItem) {
        this.context = context;
        this.addFromItem = addFromItem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_daily,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        String newdate = "";

        myviewholder.msttitledaily.setText(addFromItem.get(i).getServiceTicketCd());
        String oldadate = addFromItem.get(i).getReportDateTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        try {
            newdate = simpleDateFormat2.format(simpleDateFormat.parse(oldadate));
            System.out.println(newdate);
            Log.e((String)"Datexs", (String)newdate);
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        myviewholder.mdatedaily.setText(newdate);
        myviewholder.mpresstypedaily.setText(addFromItem.get(i).getPressSN()+" ("+addFromItem.get(i).getPressTypeName()+")");
        myviewholder.mpressstatudaily.setText(addFromItem.get(i).getPressStatusName());
        if (addFromItem.get(i).getPressStatusName().equals("Mesin Tetap Produksi")){
            myviewholder.mpressstatudaily.setTextColor(Color.parseColor("#0890cc"));
        }else {
            myviewholder.mpressstatudaily.setTextColor(Color.parseColor("#FF0000"));
        }
//        myviewholder.mpressstatudaily.setTextColor(Color.parseColor("#"+addFromItem.get(i).getStatusColorCode()));
        myviewholder.msndaily.setText(addFromItem.get(i).getPressSN());
//        myviewholder.mcaseiddaily.setText(addFromItem.get(i).getCaseProgressName());
        myviewholder.mcaseprogressdaily.setText(addFromItem.get(i).getCaseProgressName());
        Typeface type = Typeface.createFromAsset(context.getAssets(),"font/segoeuib.ttf");
        Typeface type2 = Typeface.createFromAsset(context.getAssets(),"font/segoeui.ttf");
        if (addFromItem.get(i).isFlag()){
            myviewholder.mdot.setVisibility(View.VISIBLE);
            myviewholder.mdatedaily.setTypeface(type);
//            myviewholder.mpressstatudaily.setTypeface(type);
//            myviewholder.mcaseprogressdaily.setTypeface(type);
            myviewholder.mcustomername.setTypeface(type);
            myviewholder.msttitledaily.setTypeface(type);
//            myviewholder.mcaseprog.setTypeface(type);
//            myviewholder.mpressta.setTypeface(type);
        }else {
            myviewholder.mdot.setVisibility(View.GONE);
            myviewholder.mdatedaily.setTypeface(type2);
//            myviewholder.mpressstatudaily.setTypeface(type2);
//            myviewholder.mcaseprogressdaily.setTypeface(type2);
            myviewholder.mcustomername.setTypeface(type2);
            myviewholder.msttitledaily.setTypeface(type2);
//            myviewholder.mcaseprog.setTypeface(type2);
//            myviewholder.mpressta.setTypeface(type2);
        }
        myviewholder.mcustomername.setText(addFromItem.get(i).getCustomerName());
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsDailyReport3.class);
                intent.putExtra("home", "homesa");
                intent.putExtra("pos", valuefilter);
                intent.putExtra("pos", valuefilter);
                intent.putExtra("user", username);
//                intent.putExtra("scrolbawah", scrollnya);
//                intent.putExtra("xhori", xhori);
//                intent.putExtra("yverti", yverti);
                intent.putExtra("hide","yes");

                intent.putExtra("id", (DailiReportListFR.noreq));
                intent.putExtra("noticket", (DailiReportListFR.noticket));
                intent.putExtra("pos", valuefilter);
                intent.putExtra("report", addFromItem.get(i).getReportCd());
                intent.putExtra("user", addFromItem.get(i).getCustomerName());
                intent.putExtra("scrolbawah", "yes");
                intent.putExtra("startd", startdate);
                intent.putExtra("endd", enddate);
                intent.putExtra("hide", hide);
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                ((Activity)context).finish();

            }
        });

//        if (addFromItem.get(i).getAdditionalTextHtml()!=null){
//            myviewholder.mhtml.setVisibility(View.VISIBLE);
//            if (Build.VERSION.SDK_INT >= 24) {
//                myviewholder.mhtml.setText((CharSequence) Html.fromHtml((String)addFromItem.get(i).getAdditionalTextHtml(), Html.FROM_HTML_MODE_COMPACT));
//                myviewholder.mhtml.setMovementMethod(LinkMovementMethod.getInstance());
//            } else {
//                myviewholder.mhtml.setText((CharSequence)Html.fromHtml((String)addFromItem.get(i).getAdditionalTextHtml()));
//                myviewholder.mhtml.setMovementMethod(LinkMovementMethod.getInstance());
//
//            }
//        }else {
//            myviewholder.mhtml.setVisibility(View.GONE);
//        }

    }

    @Override
    public int getItemCount() {
        return addFromItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView mdatedaily, msttitledaily,mpresstypedaily,mpressstatudaily,mhtml,msndaily,mcaseiddaily,mcaseprogressdaily,mcustomername,mpressta,mcaseprog;
        ImageView mdot;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mdatedaily = itemView.findViewById(R.id.datedaily);
            msttitledaily = itemView.findViewById(R.id.sttitledaily);
            mpresstypedaily = itemView.findViewById(R.id.presstypedaily);
            mpressstatudaily = itemView.findViewById(R.id.pressstatusdaily);
            msndaily = itemView.findViewById(R.id.sndaily);
//            mcaseiddaily = itemView.findViewById(R.id.caseiddaily);
            mcaseprogressdaily = itemView.findViewById(R.id.caseprogressdaily);
            mhtml = itemView.findViewById(R.id.textcntn);
            mcustomername = itemView.findViewById(R.id.customername);
            mdot = itemView.findViewById(R.id.dot);
            mpressta = itemView.findViewById(R.id.pressta );
            mcaseprog = itemView.findViewById(R.id.caseprog );

        }
    }
    public void clear() {
        addFromItem.clear();
        notifyDataSetChanged();
    }
    public void setAddFromItem(ArrayList<DailyItem3> addFromItem)
    {
        for (DailyItem3 im : addFromItem)
        {
            addFromItem.add(im);
        }
        notifyDataSetChanged();

    }
}

