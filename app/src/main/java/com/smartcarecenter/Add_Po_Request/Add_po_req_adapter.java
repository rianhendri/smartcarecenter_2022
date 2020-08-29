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
package com.smartcarecenter.Add_Po_Request;

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

import static com.smartcarecenter.AddDetailsPo.mgrantotalpo;
import static com.smartcarecenter.AddDetailsPo.mlaytotal;
import static com.smartcarecenter.AddDetailsPo.mlistitem_foc;
import static com.smartcarecenter.AddDetailsPo.mno_order;
import static com.smartcarecenter.AddDetailsPo.mnoitem;
import static com.smartcarecenter.AddDetailsPo.mtotalitem;
import static com.smartcarecenter.AddDetailsPo.mtotalpricepo;
import static com.smartcarecenter.AddDetailsPo.mtotalqty;
import static com.smartcarecenter.AddDetailsPo.mtotaltax;
import static com.smartcarecenter.AddDetailsPo.tax;

public class Add_po_req_adapter
extends RecyclerView.Adapter<Add_po_req_adapter.Myviewholder> {
    public static ArrayList<Add_po_req_item> addFoclistreq;
    Add_po_req_item modelqty;
    Context context;
    ImageView mimgpopup;
    double subharga = 0.0;
    public static int totalqty = 0;
    public static double totalprice = 0.0;
    public Add_po_req_adapter(Context context, ArrayList<Add_po_req_item> addFoclistitem) {
        this.context = context;
        this.addFoclistreq = addFoclistitem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_add_po_buy,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {

        Picasso.with(context).load(addFoclistreq.get(i).getImgpic()).into(myviewholder.mimg);
        myviewholder.mname.setText(addFoclistreq.get(i).getNameitem());
        myviewholder.mcode.setText(addFoclistreq.get(i).getItemcd());
        myviewholder.mcategory.setText(addFoclistreq.get(i).getCategory());
        myviewholder.mqty.setText(String.valueOf(addFoclistreq.get(i).getQty()));
        myviewholder.mpos.setText(String.valueOf(addFoclistreq.get(i).getPosition()));
        myviewholder.munit.setText(addFoclistreq.get(i).getUnitName());
        Double harga = 0.0;
        harga=addFoclistreq.get(i).getSellPrice();
        Locale localeID = new Locale("in", "ID");
        final DecimalFormat formatRupiah = (DecimalFormat) NumberFormat.getNumberInstance(localeID);
        myviewholder.mprice.setText("Rp."+ " "+String.valueOf(formatRupiah.format(harga)));
        myviewholder.msubharga.setText("Rp."+ " "+String.valueOf(formatRupiah.format(harga)));
        mtotalitem.setText(String.valueOf(addFoclistreq.size()));
        totalqty = 0;
        totalprice = 0.0;
        addFoclistreq.get(i).setSubharga(harga);
        myviewholder.mmps.setText(addFoclistreq.get(i).getMps());
        for (int x = 0 ; x < addFoclistreq.size(); x++) {
            totalqty += addFoclistreq.get(x).getQty();
            mtotalqty.setText(String.valueOf(totalqty));
            totalprice +=addFoclistreq.get(x).getSubharga();
            mtotalpricepo.setText("Rp."+ " "+String.valueOf(formatRupiah.format(totalprice)));

        }
        //taxes
        mtotaltax.setText("Rp."+ " "+String.valueOf(formatRupiah.format(totalprice*tax/100)));
        mgrantotalpo.setText("Rp."+ " "+String.valueOf(formatRupiah.format(totalprice+(totalprice*tax/100))));

        myviewholder.mimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context, R.style.TransparentDialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.popupfoto);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                mimgpopup = dialog.findViewById(R.id.imagepopup);
                Picasso.with(context).load(addFoclistreq.get(i).getImgban()).into(mimgpopup);
                dialog.show();
            }
        });
        // delete button
        myviewholder.mdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addFoclistreq.size() >= 0) {
                    addFoclistreq.remove(i);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i, addFoclistreq.size());
                    totalqty = 0;
                    for (int x = 0 ; x < addFoclistreq.size(); x++) {
                        totalqty += addFoclistreq.get(x).getQty();
                        mtotalqty.setText(String.valueOf(totalqty));
                    }
                    mtotalitem.setText(String.valueOf(addFoclistreq.size()));
//                    grandTotalplus = 0;
//                    intSum = 0;
//                    for (int i = 0; i < list.size(); i++) {
//                        grandTotalplus = grandTotalplus + list.get(i).getTotal();
//                    }
                    if (addFoclistreq.size()==0){
                        mlaytotal.setVisibility(View.GONE);
                        mnoitem.setVisibility(View.VISIBLE);
                        mlistitem_foc.setVisibility(View.GONE);
                    }

                }else {
//                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    mlaytotal.setVisibility(View.GONE);
                    mno_order.setVisibility(View.VISIBLE);

                }
            }
        });
        //button plus minus qty
        myviewholder.mplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qtynya = Integer.parseInt(myviewholder.mqty.getText().toString());
                totalqty=0;
                totalprice = 0.0;
                qtynya +=1;
                myviewholder.mqty.setText(String.valueOf(qtynya));
                addFoclistreq.get(i).setQty(qtynya);
                mtotalitem.setText(String.valueOf(addFoclistreq.size()));
                subharga = qtynya*addFoclistreq.get(i).getSellPrice();
                addFoclistreq.get(i).setSubharga(subharga);
                myviewholder.msubharga.setText("Rp."+ " "+String.valueOf(formatRupiah.format(subharga)));
                for (int i = 0 ; i < addFoclistreq.size(); i++) {
                    totalqty += addFoclistreq.get(i).getQty();
                    mtotalqty.setText(String.valueOf(totalqty));
                    totalprice +=addFoclistreq.get(i).getSubharga();
                    mtotalpricepo.setText("Rp."+ " "+String.valueOf(formatRupiah.format(totalprice)));
                    mtotaltax.setText("Rp."+ " "+String.valueOf(formatRupiah.format(totalprice*tax/100)));
                    mgrantotalpo.setText("Rp."+ " "+String.valueOf(formatRupiah.format(totalprice+(totalprice*tax/100))));
                }


            }
        });
        myviewholder.mminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qtynya = Integer.parseInt(myviewholder.mqty.getText().toString());
                if (qtynya==1){

                }else {
                    totalprice = 0.0;
                    totalqty=0;
                    qtynya -=1;
                    myviewholder.mqty.setText(String.valueOf(qtynya));
                    addFoclistreq.get(i).setQty(qtynya);
                    mtotalitem.setText(String.valueOf(addFoclistreq.size()));
                    //subharga
                    subharga = qtynya*addFoclistreq.get(i).getSellPrice();
                    addFoclistreq.get(i).setSubharga(subharga);
                    myviewholder.msubharga.setText("Rp."+ " "+String.valueOf(formatRupiah.format(subharga)));
                    for (int i = 0 ; i < addFoclistreq.size(); i++) {
                        totalqty += addFoclistreq.get(i).getQty();
                        mtotalqty.setText(String.valueOf(totalqty));
                        totalprice +=addFoclistreq.get(i).getSubharga();
                        mtotalpricepo.setText("Rp."+ " "+String.valueOf(formatRupiah.format(totalprice)));
                        mtotaltax.setText("Rp."+ " "+String.valueOf(formatRupiah.format(totalprice*tax/100)));
                        mgrantotalpo.setText("Rp."+ " "+String.valueOf(formatRupiah.format(totalprice+(totalprice*tax/100))));
                    }
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

        TextView mcode, mname, mcategory, mqty, mpos, munit,mprice,msubharga, mmps;
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
            mprice = itemView.findViewById(R.id.price);
            msubharga = itemView.findViewById(R.id.subharga);
            mmps = itemView.findViewById(R.id.mps);



        }
    }
}

