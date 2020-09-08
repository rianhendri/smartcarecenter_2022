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
package com.smartcarecenter.supportservice;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartcarecenter.DetailsFormActivity;
import com.smartcarecenter.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.smartcarecenter.FormActivity.valuefilter;

public class AddFormAdapter
extends RecyclerView.Adapter<AddFormAdapter.Myviewholder> {
    ArrayList<AddFromItem> addFromItem;
    Context context;
    ImageView mimgpopup;

    public AddFormAdapter(Context context, ArrayList<AddFromItem> addFromItem) {
        this.context = context;
        this.addFromItem = addFromItem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_form,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        String newdate = "";
        Picasso.with(context).load(addFromItem.get(i).getPhotoThumbURL()).into(myviewholder.xgambar_item);
        myviewholder.xtitlenews.setText("#"+ addFromItem.get(i).getFormRequestCd());
        String oldadate = addFromItem.get(i).getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            newdate = simpleDateFormat2.format(simpleDateFormat.parse(oldadate));
            System.out.println(newdate);
            Log.e((String)"Date", (String)newdate);
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        myviewholder.xby.setText(addFromItem.get(i).getRequestedBy());
        myviewholder.xpress.setText(addFromItem.get(i).getPressName());
        myviewholder.xdate_news.setText(newdate);
        myviewholder.xstatus.setText(addFromItem.get(i).getStatusName());
        myviewholder.xstatus.setTextColor(Color.parseColor("#"+addFromItem.get(i).getStatusColorCode()));
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsFormActivity.class);
                intent.putExtra("id", (addFromItem.get(i).getFormRequestCd()));
                intent.putExtra("noticket", (addFromItem.get(i)).getServiceTicketCd());
                intent.putExtra("pos", valuefilter);
                intent.putExtra("user", addFromItem.get(i).getRequestedBy());
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                ((Activity)context).finish();

            }
        });
        myviewholder.xgambar_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context, R.style.TransparentDialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.popupfoto);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                mimgpopup = dialog.findViewById(R.id.imagepopup);
                Picasso.with(context).load(addFromItem.get(i).getPhotoURL()).into(mimgpopup);
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return addFromItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView xdate_news;
        ImageView xgambar_item;
        TextView xid;
        TextView xstatus;
        TextView xtitlenews, xby, xpress;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            xgambar_item = itemView.findViewById(R.id.xpic);
            xstatus = itemView.findViewById(R.id.donestatus);
            xtitlenews = itemView.findViewById(R.id.tetleform);
            xdate_news = itemView.findViewById(R.id.dateform);
            xpress = itemView.findViewById(R.id.press);
            xby = itemView.findViewById(R.id.by);

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
    public void setAddFromItem(ArrayList<AddFromItem> addFromItem)
    {
        for (AddFromItem im : addFromItem)
        {
            addFromItem.add(im);
        }
        notifyDataSetChanged();

    }
}

