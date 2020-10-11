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
package com.smartcarecenter.Add_Foc_Request_view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import static com.smartcarecenter.AddDetailFoc.reitem;
import static com.smartcarecenter.AddDetailFocView.matrixlabel;
import static com.smartcarecenter.AddDetailFocView.mlaytotal;
import static com.smartcarecenter.AddDetailFocView.mlistitem_foc;
import static com.smartcarecenter.AddDetailFocView.mno_order;
import static com.smartcarecenter.AddDetailFocView.mnoitem;
import static com.smartcarecenter.AddDetailFocView.mtotalapproved;
import static com.smartcarecenter.AddDetailFocView.mtotalitem;
import static com.smartcarecenter.AddDetailFocView.mtotalqty;

public class Add_foc_req_adapterView
extends RecyclerView.Adapter<Add_foc_req_adapterView.Myviewholder> {
    public static ArrayList<Add_foc_req_itemView> addFoclistreq;
    Add_foc_req_itemView modelqty;
    Context context;
    ImageView mimgpopup;
    public static int totalqty = 0;
    int qtynya = 1;
    public static int totalapproved =0;
    public Add_foc_req_adapterView(Context context, ArrayList<Add_foc_req_itemView> addFoclistitem) {
        this.context = context;
        this.addFoclistreq = addFoclistitem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_add_foc_buy_view,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        myviewholder.mmatrixlabel.setText(matrixlabel);
        Picasso.with(context).load(addFoclistreq.get(i).getImageThumbFullURL()).into(myviewholder.mimg);
        myviewholder.mname.setText(addFoclistreq.get(i).getItemName());
        myviewholder.mcode.setText(addFoclistreq.get(i).getItemCd());
        myviewholder.mcategory.setText(addFoclistreq.get(i).getCategoryName());
        myviewholder.mqty.setText(String.valueOf(addFoclistreq.get(i).getQty()));
        myviewholder.mpos.setText(String.valueOf(addFoclistreq.get(i).getPosition()));
        myviewholder.munit.setText(addFoclistreq.get(i).getUnitName());
        myviewholder.mapprove.setText(String.valueOf(addFoclistreq.get(i).getQtyApproved()));
        myviewholder.mprevimpress.setText(String.valueOf(addFoclistreq.get(i).getPreviousImpression()));
        Integer matrixnya = addFoclistreq.get(i).getMatrixResult();
        if (matrixnya==null){
            myviewholder.mmatrix.setText("-");
        }else {
            myviewholder.mmatrix.setText(String.valueOf(addFoclistreq.get(i).getMatrixResult()));
        }

        mtotalitem.setText(String.valueOf(addFoclistreq.size()));
        totalqty = 0;
        totalapproved = 0;
        for (int x = 0 ; x < addFoclistreq.size(); x++) {
            totalapproved +=addFoclistreq.get(x).getQtyApproved();
            totalqty += addFoclistreq.get(x).getQty();
            mtotalqty.setText(String.valueOf(totalqty));
            mtotalapproved.setText(String.valueOf(totalapproved));
        }
        myviewholder.mimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context, R.style.TransparentDialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.popupfoto);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                mimgpopup = dialog.findViewById(R.id.imagepopup);
                Picasso.with(context).load(addFoclistreq.get(i).getImageFullURL()).into(mimgpopup);
                dialog.show();
            }
        });
        // delete button
        myviewholder.mdelete.setVisibility(View.GONE);
//        myviewholder.mplus.setVisibility(View.GONE);
//        myviewholder.mminus.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return 
                addFoclistreq.size();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder{

        TextView mcode, mname, mcategory, mqty, mpos,munit, mapprove, mprevimpress, mmatrix, mmatrixlabel;
        ImageView mimg, mminus, mplus,mdelete;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);

            mcode = itemView.findViewById(R.id.codebarang);
            mname = itemView.findViewById(R.id.namabarang);
            mcategory = itemView.findViewById(R.id.categorypolist);
            mqty = itemView.findViewById(R.id.qtyitempo);
            mpos = itemView.findViewById(R.id.nolistpo);
            mimg = itemView.findViewById(R.id.xpic);
//            mminus = itemView.findViewById(R.id.minus);
//            mplus = itemView.findViewById(R.id.plus);
            mdelete = itemView.findViewById(R.id.deletelistpo);
            munit = itemView.findViewById(R.id.unitname);
            mapprove = itemView.findViewById(R.id.qtyapprov);
            mprevimpress = itemView.findViewById(R.id.previmpres);
            mmatrix = itemView.findViewById(R.id.matrix);
            mmatrixlabel = itemView.findViewById(R.id.matrixlabel);



        }
    }
}

