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
package com.smartcarecenter.Chargeable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartcarecenter.AddDetailFocView;
import com.smartcarecenter.AddDetailsPoView;
import com.smartcarecenter.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.smartcarecenter.FreeofchargeActivity.valuefilter;

public class Chargeabledapter
extends RecyclerView.Adapter<Chargeabledapter.Myviewholder> {
    ArrayList<ChargeableItem> chargeableItem;
    Context context;
    ImageView mimgpopup;

    public Chargeabledapter(Context context, ArrayList<ChargeableItem> chargeableItem) {
        this.context = context;
        this.chargeableItem = chargeableItem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_po,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        String newdate = "";

        myviewholder.mnofoc.setText("Ref: "+"#"+ chargeableItem.get(i).getOrderNo());
        String oldadate = chargeableItem.get(i).getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.getDefault());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            newdate = simpleDateFormat2.format(simpleDateFormat.parse(oldadate));
            System.out.println(newdate);
            Log.e((String)"Date", (String)newdate);
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        final DecimalFormat formatRupiah = new DecimalFormat("###,###,###,###,###.00");
        myviewholder.mgrand.setText(context.getString(R.string.title_grandtotal)+" Rp. "+String.valueOf(formatRupiah.format(chargeableItem.get(i).getGrandTotal())));
        myviewholder.mdate.setText(newdate);
        myviewholder.mstatus.setText(chargeableItem.get(i).getStatusName());
        myviewholder.mstatus.setTextColor(Color.parseColor("#"+ chargeableItem.get(i).getStatusColorCode()));
        myviewholder.mpos.setText(String.valueOf(i+1));
        myviewholder.mNoPo.setText(context.getString(R.string.title_nopo)+chargeableItem.get(i).getPoNo());
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddDetailsPoView.class);
                intent.putExtra("id", (chargeableItem.get(i).getOrderNo()));
//                intent.putExtra("noticket", (focItem.get(i)).getServiceTicketCd());
                intent.putExtra("pos", valuefilter);
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                ((Activity)context).finish();

            }
        });
    }

    @Override
    public int getItemCount() {
        return chargeableItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView mnofoc,mstatus,mdate,mpos,mNoPo,mgrand;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mnofoc = itemView.findViewById(R.id.nopolist);
            mstatus = itemView.findViewById(R.id.statusfoc);
            mdate = itemView.findViewById(R.id.tanggallistpo);
            mpos = itemView.findViewById(R.id.nolist);
            mNoPo = itemView.findViewById(R.id.NoPo);
            mgrand = itemView.findViewById(R.id.grandtotal);

        }
    }
}

