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
package com.smartcarecenter.Add_Po_Rewuest_View;

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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.smartcarecenter.AddDetailsPoView.mgrandtotal;
import static com.smartcarecenter.AddDetailsPoView.mtax;
import static com.smartcarecenter.AddDetailsPoView.mtotalitem;
import static com.smartcarecenter.AddDetailsPoView.mtotalprice;
import static com.smartcarecenter.AddDetailsPoView.mtotalqty;

public class Add_po_req_adapterView
extends RecyclerView.Adapter<Add_po_req_adapterView.Myviewholder> {
    public static ArrayList<Add_po_req_itemView> addFoclistreq;
    Add_po_req_itemView modelqty;
    Context context;
    ImageView mimgpopup;
    public static int totalqty = 0;
    double subharga = 0;
    double harga = 0.0;
    int qtynya = 1;
    double totalprice=0.0;
    public Add_po_req_adapterView(Context context, ArrayList<Add_po_req_itemView> addFoclistitem) {
        this.context = context;
        this.addFoclistreq = addFoclistitem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_add_po_buy_view,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        Picasso.with(context).load(addFoclistreq.get(i).getImageThumbFullURL()).into(myviewholder.mimg);
        myviewholder.mname.setText(addFoclistreq.get(i).getItemName());
        myviewholder.mcode.setText(addFoclistreq.get(i).getItemCd());
        myviewholder.mcategory.setText(addFoclistreq.get(i).getCategoryName());
        myviewholder.mqty.setText(String.valueOf(addFoclistreq.get(i).getQty()));
        myviewholder.mpos.setText(String.valueOf(addFoclistreq.get(i).getPosition()));
        myviewholder.munit.setText(addFoclistreq.get(i).getUnitName());
        mtotalitem.setText(String.valueOf(addFoclistreq.size()));
        myviewholder.mps.setText(addFoclistreq.get(i).getMps());
        harga = addFoclistreq.get(i).getPricePerQty();
        totalqty = 0;
        subharga = addFoclistreq.get(i).getTotalPrice();
        Locale localeID = new Locale("in", "ID");
        final DecimalFormat formatRupiah = new DecimalFormat("###,###,###,###,###.00");
        myviewholder.mharga.setText("Rp."+ " "+String.valueOf(formatRupiah.format(harga)));
        myviewholder.msubharga.setText("Rp."+ " "+String.valueOf(formatRupiah.format(subharga)));
        for (int x = 0 ; x < addFoclistreq.size(); x++) {
            totalqty += addFoclistreq.get(x).getQty();
            totalprice +=addFoclistreq.get(x).getTotalPrice();
            mtotalqty.setText(String.valueOf(totalqty));
//            mtotalprice.setText("Rp."+ " "+String.valueOf(formatRupiah.format(totalprice)));
//            double taxnya = totalprice*10/100;
//            mtax.setText("Rp."+ " "+String.valueOf(formatRupiah.format(taxnya)));
//            mgrandtotal.setText("Rp."+ " "+String.valueOf(formatRupiah.format(totalprice)));
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
        myviewholder.mplus.setVisibility(View.GONE);
        myviewholder.mminus.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return 
                addFoclistreq.size();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder{

        TextView mcode, mname, mcategory, mqty, mpos,munit,msubharga, mharga, mps;
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
            munit = itemView.findViewById(R.id.unitname);
            msubharga = itemView.findViewById(R.id.subharga);
            mharga = itemView.findViewById(R.id.price);
            mps = itemView.findViewById(R.id.mps);



        }
    }
}

