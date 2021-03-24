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
package com.smartcarecenter.Add_Po_Item_list_model;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartcarecenter.AddDetailFoc;
import com.smartcarecenter.AddDetailsPo;
import com.smartcarecenter.Add_Foc_request.Add_foc_req_item;
import com.smartcarecenter.Add_Po_Item_List;
import com.smartcarecenter.Add_Po_Request.Add_po_req_item;
import com.smartcarecenter.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.smartcarecenter.AddDetailsPo.PaymentTypeCd;
import static com.smartcarecenter.Add_Foc_Item_List.stsinac;
//import static com.smartcarecenter.Add_Po_Item_List.PaymentTypeCd;
import static com.smartcarecenter.Add_Po_Item_List.PaymentTypeCd2;
import static com.smartcarecenter.Add_Po_Request.Add_po_req_adapter.addFoclistreq;

public class Add_po_list_adapter
extends RecyclerView.Adapter<Add_po_list_adapter.Myviewholder> {
    ArrayList<Add_po_list_item> addPolistitem;
    Context context;
    ImageView mimgpopup;
    public static ArrayList<Add_po_req_item> listpoact = new ArrayList<Add_po_req_item>();
    Add_po_req_item tambahitem;
    public Add_po_list_adapter(Context context, ArrayList<Add_po_list_item> addFoclistitem) {
        this.context = context;
        this.addPolistitem = addFoclistitem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_add_po_list,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        String newdate = "";
        Picasso.with(context).load(addPolistitem.get(i).getImageThumbFullURL()).into(myviewholder.xgambar_item);
        myviewholder.xname.setText(addPolistitem.get(i).getName());
        myviewholder.xcode.setText(addPolistitem.get(i).getItemCd());
        myviewholder.xcategory.setText(addPolistitem.get(i).getCategoryName());
        myviewholder.xunit.setText(addPolistitem.get(i).getUnitName());
        myviewholder.mmps.setText(addPolistitem.get(i).getMps());
        Double harga = 0.0;
        harga=addPolistitem.get(i).getSellPrice();
        Locale localeID = new Locale("in", "ID");
        final DecimalFormat formatRupiah = new DecimalFormat("###,###,###,###,###.00");
        myviewholder.xharga.setText("Rp."+ " "+String.valueOf(formatRupiah.format(harga)));
//        myviewholder.xstatus.setText(addPolistitem.get(i).getStsActiveInfo());
        boolean sts = true;
         sts = addPolistitem.get(i).getStsActive();
        if (sts){
            myviewholder.xadd.setVisibility(View.VISIBLE);
            myviewholder.xharga.setVisibility(View.VISIBLE);
            myviewholder.xstatus.setVisibility(View.GONE);
        }else{
            myviewholder.xstatus.setText(Add_Po_Item_List.stsinac);
            myviewholder.xstatus.setVisibility(View.VISIBLE);
            myviewholder.xstatus.setTextColor(Color.parseColor(	"#B22222"));
            myviewholder.xadd.setVisibility(View.GONE);
            myviewholder.xharga.setVisibility(View.GONE);
        }
        String status = addPolistitem.get(i).getStsActiveInfo();
//        if (Build.VERSION.SDK_INT >= 24) {
//            myviewholder.xstatus.setText((CharSequence) Html.fromHtml((String)status, Html.FROM_HTML_MODE_COMPACT));
//
//        } else {
//            myviewholder.xstatus.setText((CharSequence)Html.fromHtml((String)status));
//
//        }
        myviewholder.xgambar_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context, R.style.TransparentDialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.popupfoto);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                mimgpopup = dialog.findViewById(R.id.imagepopup);
                Picasso.with(context).load(addPolistitem.get(i).getImageFullURL()).into(mimgpopup);
                dialog.show();
            }
        });
        myviewholder.xadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status = true;
                status = addPolistitem.get(i).getStsActive();
                if (status){
                    PaymentTypeCd = PaymentTypeCd2;
                    tambahitem = new Add_po_req_item();
                    tambahitem.setItemcd(addPolistitem.get(i).getItemCd());
                    tambahitem.setCategory(addPolistitem.get(i).getCategoryName());
                    tambahitem.setNameitem(addPolistitem.get(i).getName());
                    tambahitem.setImgpic(addPolistitem.get(i).getImageThumbFullURL());
                    tambahitem.setImgban(addPolistitem.get(i).getImageFullURL());
                    tambahitem.setUnitName(addPolistitem.get(i).getUnitName());
                    tambahitem.setQty(1);
                    tambahitem.setPosition(addFoclistreq.size()+1);
                    tambahitem.setSellPrice(addPolistitem.get(i).getSellPrice());
                    tambahitem.setMps(addPolistitem.get(i).getMps());
                    listpoact.add(tambahitem);
                    Intent intent = new Intent(context, AddDetailsPo.class);
                    intent.putExtra("pay",PaymentTypeCd2);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                    ((Activity)context).overridePendingTransition(R.anim.left_in, R.anim.right_out);
                }else {

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return addPolistitem.size();
    }

    public void filterList(ArrayList<Add_po_list_item> filteredList) {
        addPolistitem = filteredList;
        notifyDataSetChanged();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder{

        TextView xdate_news;
        ImageView xgambar_item,xadd;
        TextView xid;
        TextView xstatus;
        TextView xname,xcode,xcategory,xunit,xharga, mmps;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            xgambar_item = itemView.findViewById(R.id.xpic);
            xstatus = itemView.findViewById(R.id.status);
            xcode = itemView.findViewById(R.id.codebarang);
            xunit = itemView.findViewById(R.id.unitname);
            xname = itemView.findViewById(R.id.namabarang);
            xadd = itemView.findViewById(R.id.addpoitem);
            xcategory = itemView.findViewById(R.id.categorypolist);
            xharga = itemView.findViewById(R.id.harga);
            mmps = itemView.findViewById(R.id.mps);


        }
    }
}

