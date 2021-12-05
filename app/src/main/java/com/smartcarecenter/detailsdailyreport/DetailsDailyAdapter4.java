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
package com.smartcarecenter.detailsdailyreport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartcarecenter.DetailsDailyReport;
import com.smartcarecenter.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.smartcarecenter.FormActivity.valuefilter;

public class DetailsDailyAdapter4
extends RecyclerView.Adapter<DetailsDailyAdapter4.Myviewholder> {
    ArrayList<DetailsDailyItem4> addFromItem;
    Context context;
    ImageView mimgpopup;

    public DetailsDailyAdapter4(Context context, ArrayList<DetailsDailyItem4> addFromItem) {
        this.context = context;
        this.addFromItem = addFromItem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_detaildaily4,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        myviewholder.msparepartname.setText(addFromItem.get(i).getSparePartCd());
        myviewholder.mqty.setText(addFromItem.get(i).getQuantity());
    }

    @Override
    public int getItemCount() {
        return addFromItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView mdatedaily, msttitledaily,mpresstypedaily,mpressstatudaily,mhtml,msndaily,mcaseiddaily,mcaseprogressdaily,msparepartname,mqty;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            msparepartname = itemView.findViewById(R.id.sparepartname);
            mqty = itemView.findViewById(R.id.qty);

        }
    }
    public void clear() {
        addFromItem.clear();
        notifyDataSetChanged();
    }
    public void setAddFromItem(ArrayList<DetailsDailyItem4> addFromItem)
    {
        for (DetailsDailyItem4 im : addFromItem)
        {
            addFromItem.add(im);
        }
        notifyDataSetChanged();

    }
}

