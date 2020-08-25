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
package com.smartcarecenter.Add_Foc_request;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartcarecenter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.smartcarecenter.AddDetailFoc.mlaytotal;
import static com.smartcarecenter.AddDetailFoc.mno_order;
import static com.smartcarecenter.AddDetailFoc.mtotalitem;

public class Add_foc_req_adapter
extends RecyclerView.Adapter<Add_foc_req_adapter.Myviewholder> {
    public static ArrayList<Add_foc_req_item> addFoclistreq;
    Context context;
    ImageView mimgpopup;
    public Add_foc_req_adapter(Context context, ArrayList<Add_foc_req_item> addFoclistitem) {
        this.context = context;
        this.addFoclistreq = addFoclistitem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_add_foc_buy,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {

        Picasso.with(context).load(addFoclistreq.get(i).getImgpic()).into(myviewholder.mimg);
        myviewholder.mname.setText(addFoclistreq.get(i).getNameitem());
        myviewholder.mcode.setText(addFoclistreq.get(i).getItemCd());
        myviewholder.mcategory.setText(addFoclistreq.get(i).getCategory());
        myviewholder.mqty.setText(String.valueOf(addFoclistreq.get(i).getQty()));
        myviewholder.mpos.setText(String.valueOf(addFoclistreq.get(i).getPosition()));


        myviewholder.mdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addFoclistreq.size() >= 0) {
                    addFoclistreq.remove(i);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i, addFoclistreq.size());
//                    grandTotalplus = 0;
//                    intSum = 0;
//                    for (int i = 0; i < list.size(); i++) {
//                        grandTotalplus = grandTotalplus + list.get(i).getTotal();
//                    }
                    if (addFoclistreq.size()==0){
                        mlaytotal.setVisibility(View.GONE);
                        mno_order.setVisibility(View.VISIBLE);
                    }

                }else {
//                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    mlaytotal.setVisibility(View.GONE);
                    mno_order.setVisibility(View.VISIBLE);

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return 
                addFoclistreq.size();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder{

        TextView mcode, mname, mcategory, mqty, mpos;
        ImageView mimg, mminus, mplus,mdelete;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);

            mcode = itemView.findViewById(R.id.codebarang);
            mname = itemView.findViewById(R.id.namabarang);
            mcategory = itemView.findViewById(R.id.categorypolist);
            mqty = itemView.findViewById(R.id.qtyitempo);
            mpos = itemView.findViewById(R.id.nolistpo);
            mimg = itemView.findViewById(R.id.xpic);
            mminus = itemView.findViewById(R.id.minus);
            mplus = itemView.findViewById(R.id.plus);
            mdelete = itemView.findViewById(R.id.deletelistpo);



        }
    }
}

