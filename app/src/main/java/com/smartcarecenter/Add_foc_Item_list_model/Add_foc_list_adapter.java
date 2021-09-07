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
package com.smartcarecenter.Add_foc_Item_list_model;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.smartcarecenter.AddDetailFoc;
import com.smartcarecenter.Add_Foc_Item_List;
import com.smartcarecenter.Add_Foc_request.Add_foc_req_item;
import com.smartcarecenter.DetailsFormActivity;
import com.smartcarecenter.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.smartcarecenter.Add_Foc_Item_List.stsinac;
import static com.smartcarecenter.Add_Foc_request.Add_foc_req_adapter.addFoclistreq;
import static com.smartcarecenter.FormActivity.valuefilter;

public class Add_foc_list_adapter
extends RecyclerView.Adapter<Add_foc_list_adapter.Myviewholder> {
    ArrayList<Add_foc_list_item> addFoclistitem;
    Context context;
    ImageView mimgpopup;
    public static ArrayList<Add_foc_req_item> listpoact = new ArrayList<Add_foc_req_item>();
    Add_foc_req_item tambahitem;
    boolean stsS = true;
    boolean usingMatrix = true;
    public Add_foc_list_adapter(Context context, ArrayList<Add_foc_list_item> addFoclistitem) {
        this.context = context;
        this.addFoclistitem = addFoclistitem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_add_foc_list,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        String newdate = "";
        Picasso.with(context).load(addFoclistitem.get(i).getImageThumbFullURL()).into(myviewholder.xgambar_item);
        myviewholder.xname.setText(addFoclistitem.get(i).getName());
        myviewholder.xcode.setText(addFoclistitem.get(i).getItemCd());
        myviewholder.xcategory.setText(addFoclistitem.get(i).getCategoryName());
        myviewholder.xunit.setText(addFoclistitem.get(i).getUnitName());
        usingMatrix = addFoclistitem.get(i).isUsingMatrix();
        if (usingMatrix){
            myviewholder.maylifespan.setVisibility(View.VISIBLE);
            Locale localeID = new Locale("in", "ID");
            final DecimalFormat formatRupiah = new DecimalFormat("###,###,###,###,###");
            myviewholder.mlifespan.setText(String.valueOf(formatRupiah.format(addFoclistitem.get(i).getMatrixLifeSpanPcs())));
        }else {
            myviewholder.maylifespan.setVisibility(View.GONE);
            myviewholder.mlifespan.setText(addFoclistitem.get(i).getLifeSpan());
        }
        if (addFoclistitem.get(i).getLastImpression()==0){
            myviewholder.xprevimpresion.setText("-");
        }else {
            myviewholder.xprevimpresion.setText(String.valueOf(addFoclistitem.get(i).getLastImpression()));
        }

//        myviewholder.xstatus.setText(addFoclistitem.get(i).getStsActiveInfo());

         stsS = addFoclistitem.get(i).isStsActive();
        if (addFoclistitem.get(i).isStsActive()){
            myviewholder.xadd.setVisibility(View.VISIBLE);
            myviewholder.xstatus.setVisibility(View.GONE);

        }else{
            myviewholder.xadd.setVisibility(View.GONE);
            myviewholder.xstatus.setVisibility(View.VISIBLE);
            myviewholder.xstatus.setText(stsinac);
            myviewholder.xstatus.setTextColor(Color.parseColor(	"#B22222"));

        }
        String status = addFoclistitem.get(i).getStsActiveInfo();
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
                Picasso.with(context).load(addFoclistitem.get(i).getImageFullURL()).into(mimgpopup);
                dialog.show();
            }
        });
        Log.d("MaxSohnya",String.valueOf(addFoclistitem.get(i).getMaxSOH()));
        myviewholder.xadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "ada", Toast.LENGTH_SHORT).show();
                if (addFoclistitem.get(i).isStsActive()){
                    tambahitem = new Add_foc_req_item();
                    tambahitem.setItemcd(addFoclistitem.get(i).getItemCd());
                    tambahitem.setCategory(addFoclistitem.get(i).getCategoryName());
                    tambahitem.setNameitem(addFoclistitem.get(i).getName());
                    tambahitem.setImgpic(addFoclistitem.get(i).getImageThumbFullURL());
                    tambahitem.setImgban(addFoclistitem.get(i).getImageFullURL());
                    tambahitem.setUnitName(addFoclistitem.get(i).getUnitName());
                    tambahitem.setLastImpression(addFoclistitem.get(i).getLastImpression());
                    tambahitem.setMatrixLifeSpanPcs(addFoclistitem.get(i).getMatrixLifeSpanPcs());
                    tambahitem.setUsingMatrix(addFoclistitem.get(i).isUsingMatrix());
                    tambahitem.setMatrixFloor(addFoclistitem.get(i).isMatrixFloor());
                    tambahitem.setLifeSpan(addFoclistitem.get(i).getLifeSpan());
                    tambahitem.setMatrix("-");
                    tambahitem.setStockOnHand("?");
                    tambahitem.setQty(1);
                    tambahitem.setMaxSOH(addFoclistitem.get(i).getMaxSOH());
                    tambahitem.setPosition(addFoclistreq.size()+1);
                    listpoact.add(tambahitem);
                    Intent intent = new Intent(context, AddDetailFoc.class);
                    context.startActivity(intent);
                    ((android.app.Activity)context).overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    ((android.app.Activity)context).finish();

                }else {

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return addFoclistitem.size();
    }

    public void filterList(ArrayList<Add_foc_list_item> filteredList) {
        addFoclistitem = filteredList;
        notifyDataSetChanged();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder{

        TextView xdate_news;
        ImageView xgambar_item,xadd;
        TextView xid;
        TextView xstatus;
        TextView xname,xcode,xcategory,xunit, xprevimpresion, mlifespan;
        LinearLayout maylifespan;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            xgambar_item = itemView.findViewById(R.id.xpic);
            xstatus = itemView.findViewById(R.id.status);
            xcode= itemView.findViewById(R.id.codebarang);
            xunit = itemView.findViewById(R.id.unitname);
            xname = itemView.findViewById(R.id.namabarang);
            xadd = itemView.findViewById(R.id.addpoitem);
            xcategory = itemView.findViewById(R.id.categoryfoclist);
            xprevimpresion = itemView.findViewById(R.id.previmpres);
            mlifespan = itemView.findViewById(R.id.lifespan);
            maylifespan = itemView.findViewById(R.id.lifespanlay);


        }
    }
}

