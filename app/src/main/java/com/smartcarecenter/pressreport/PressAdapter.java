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
package com.smartcarecenter.pressreport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartcarecenter.DetailsPM;
import com.smartcarecenter.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.smartcarecenter.FormActivity.valuefilter;

public class PressAdapter
extends RecyclerView.Adapter<PressAdapter.Myviewholder> {
    ArrayList<PressList> addFromItem;
    Context context;
    ImageView mimgpopup;

    public PressAdapter(Context context, ArrayList<PressList> addFromItem) {
        this.context = context;
        this.addFromItem = addFromItem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_pressreport,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {

        myviewholder.msttitle.setText(addFromItem.get(i).getSNAndName());

        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }

    @Override
    public int getItemCount() {
        return addFromItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView mvisitdate, msttitle,mpress,mstatus,mhtml;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mvisitdate = itemView.findViewById(R.id.visitdate);

            mpress = itemView.findViewById(R.id.pressName);


        }
    }
    public interface CallBackUs {
        void addCartItemView();
    }
    // this interface creates for call the invalidateoptionmenu() for refresh the menu item
    public interface HomeCallBack {
        void updateCartCount(Context context);
    }
    public void clear() {
        addFromItem.clear();
        notifyDataSetChanged();
    }
    public void setAddFromItem(ArrayList<PressList> addFromItem)
    {
        for (PressList im : addFromItem)
        {
            addFromItem.add(im);
        }
        notifyDataSetChanged();

    }
}

