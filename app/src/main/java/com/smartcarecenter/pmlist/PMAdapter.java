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
package com.smartcarecenter.pmlist;

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

import com.smartcarecenter.DetailsFormActivity;
import com.smartcarecenter.DetailsPM;
import com.smartcarecenter.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.smartcarecenter.FormActivity.valuefilter;

public class PMAdapter
extends RecyclerView.Adapter<PMAdapter.Myviewholder> {
    ArrayList<PmListAdd> addFromItem;
    Context context;
    ImageView mimgpopup;

    public PMAdapter(Context context, ArrayList<PmListAdd> addFromItem) {
        this.context = context;
        this.addFromItem = addFromItem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_pmlist,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        String newdate = "";

        myviewholder.msttitle.setText(addFromItem.get(i).getServiceTicketCd());
        String oldadate = addFromItem.get(i).getVisitDateTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy  HH:mm", Locale.getDefault());
        try {
            newdate = simpleDateFormat2.format(simpleDateFormat.parse(oldadate));
            System.out.println(newdate);
            Log.e((String)"Datexs", (String)newdate);
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        myviewholder.mvisitdate.setText(newdate);
//        String[] separated = newdate.split("T");
//        separated[0].trim();; // this will contain "Fruit"
//        separated[1].trim();;
//        myviewholder.xby.setText(addFromItem.get(i).getDescription());
        myviewholder.mpress.setText(addFromItem.get(i).getPressName());
        myviewholder.mstatus.setText(addFromItem.get(i).getStatusName());
        myviewholder.mstatus.setTextColor(Color.parseColor("#"+addFromItem.get(i).getStatusColorCode()));
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsPM.class);
                intent.putExtra("id", (addFromItem.get(i).getServiceTicketCd()));
                intent.putExtra("noticket", (addFromItem.get(i)).getServiceTicketCd());
                intent.putExtra("pos", valuefilter);
                intent.putExtra("user", addFromItem.get(i).getStatusName());
                intent.putExtra("scrolbawah", "yes");
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                ((Activity)context).finish();

            }
        });

        if (addFromItem.get(i).getAdditionalTextHtml()!=null){
            myviewholder.mhtml.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= 24) {
                myviewholder.mhtml.setText((CharSequence) Html.fromHtml((String)addFromItem.get(i).getAdditionalTextHtml(), Html.FROM_HTML_MODE_COMPACT));
                myviewholder.mhtml.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                myviewholder.mhtml.setText((CharSequence)Html.fromHtml((String)addFromItem.get(i).getAdditionalTextHtml()));
                myviewholder.mhtml.setMovementMethod(LinkMovementMethod.getInstance());

            }
        }else {
            myviewholder.mhtml.setVisibility(View.GONE);
        }

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
            msttitle = itemView.findViewById(R.id.sttitle);
            mpress = itemView.findViewById(R.id.press);
            mstatus = itemView.findViewById(R.id.status);
            mhtml = itemView.findViewById(R.id.textcntn);

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
    public void setAddFromItem(ArrayList<PmListAdd> addFromItem)
    {
        for (PmListAdd im : addFromItem)
        {
            addFromItem.add(im);
        }
        notifyDataSetChanged();

    }
}

