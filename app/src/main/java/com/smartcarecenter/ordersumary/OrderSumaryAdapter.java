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
package com.smartcarecenter.ordersumary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.smartcarecenter.DetailsNotification;
import com.smartcarecenter.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class OrderSumaryAdapter
extends RecyclerView.Adapter<OrderSumaryAdapter.Myviewholder> {

    Context context;
    ArrayList<OrderSumaryItem> myItem;
    public static int positem = 0;

    public OrderSumaryAdapter(Context c, ArrayList<OrderSumaryItem> p){
        context = c;
        myItem = p;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_ordersumary,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        if (myItem.get(i).isExtraMarginBottom()){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 100);
            myviewholder.mbottom.setLayoutParams(params);
            myviewholder.mtitlename.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
//            myviewholder.mdeskripsi.setLayoutParams(params2);
        }
        if (myItem.get(i).getItemName()!=null){
            myviewholder.mtitlename.setText(myItem.get(i).getItemName());
        }
        if (myItem.get(i).getItemDescription()!=null){
            myviewholder.mdeskripsi.setText(myItem.get(i).getItemDescription());
        }
        if (myItem.get(i).getNominalStrikeout()!=null){
            myviewholder.mstrike.setText(myItem.get(i).getNominalStrikeout());
//            myviewholder.mstrike.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
            myviewholder.mstrike.setPaintFlags( myviewholder.mstrike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (myItem.get(i).getNominal()!=null){
            myviewholder.mnilai.setText(myItem.get(i).getNominal());
//            myviewholder.mstrike.setPaintFlags( myviewholder.mstrike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
//        if (myItem.get(i).getItemName()!=null){
//            myviewholder.mtitlename.setText(myItem.get(i).getItemName());
//        }



    }

    @Override
    public int getItemCount() {
        return
                myItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView mtitlename;
        TextView mdeskripsi;
        TextView mnilai,mstrike;
//        ImageView mstrike;
        LinearLayout mbottom;

        public Myviewholder(@NonNull View view) {
            super(view);
            mtitlename = (TextView)view.findViewById(R.id.titlename);
            mdeskripsi = (TextView)view.findViewById(R.id.deskripsinya);
            mnilai = (TextView)view.findViewById(R.id.nominal);
            mstrike = view.findViewById(R.id.strike);
            mbottom = view.findViewById(R.id.marginbutom);
        }
    }

}

