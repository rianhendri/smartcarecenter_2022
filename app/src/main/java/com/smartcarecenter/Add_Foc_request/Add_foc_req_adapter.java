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
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartcarecenter.AddDetailFoc;
import com.smartcarecenter.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.smartcarecenter.AddDetailFoc.imagefile;
import static com.smartcarecenter.AddDetailFoc.lastimpresi;
import static com.smartcarecenter.AddDetailFoc.matrixlabel;
import static com.smartcarecenter.AddDetailFoc.mlastimpresi;
import static com.smartcarecenter.AddDetailFoc.mlaytotal;
import static com.smartcarecenter.AddDetailFoc.mlistitem_foc;
import static com.smartcarecenter.AddDetailFoc.mno_order;
import static com.smartcarecenter.AddDetailFoc.mnoitem;
import static com.smartcarecenter.AddDetailFoc.mtotalitem;
import static com.smartcarecenter.AddDetailFoc.mtotalqty;
import static com.smartcarecenter.AddDetailFoc.myCustomArray;
import static com.smartcarecenter.AddDetailFoc.jsonarayitem;
import static com.smartcarecenter.AddDetailFoc.reitem;
import static com.smartcarecenter.Add_foc_Item_list_model.Add_foc_list_adapter.listpoact;

public class Add_foc_req_adapter
extends RecyclerView.Adapter<Add_foc_req_adapter.Myviewholder> {
    public static ArrayList<Add_foc_req_item> addFoclistreq;
    public static long lastimpresivalue = 0;
    public static Double matrixcount = 0.00;
    public static double matrixmeter = 0.00;
    public static double selisih = 0.00;
    double spanmax = 0.00;
    boolean cekdel = true;
    boolean usingmatrix = true;
    Add_foc_req_item modelqty;
    Context context;
    ImageView mimgpopup;
    boolean floor = true;
    int deservqty = 0;
    public static int totalqty = 0;
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
        usingmatrix = addFoclistreq.get(i).isUsingMatrix();
        floor = addFoclistreq.get(i).isMatrixFloor();
        if (usingmatrix){
            myviewholder.mlaylife.setVisibility(View.VISIBLE);
            Locale localeID = new Locale("in", "ID");
            final DecimalFormat formatRupiah = new DecimalFormat("###,###,###,###,###");
            myviewholder.mlifespan.setText(String.valueOf(formatRupiah.format(addFoclistreq.get(i).getMatrixLifeSpanPcs())));
        }else {
            myviewholder.mlaylife.setVisibility(View.GONE);
        }
        if (addFoclistreq.get(i).getLastImpression()==0){
            myviewholder.mmatrix.setText("-");
        }else if (!usingmatrix){
            myviewholder.mmatrix.setText("-");
        }else {
            if (addFoclistreq.get(i).getMatrixLifeSpanPcs()==0){
                myviewholder.mmatrix.setText("-");
            }
            if (mlastimpresi.length()==0){
                lastimpresivalue = 0;
            }else {
                lastimpresivalue = Long.parseLong(mlastimpresi.getText().toString());
            }
            selisih = lastimpresivalue-addFoclistreq.get(i).getLastImpression();
            spanmax = addFoclistreq.get(i).getMatrixLifeSpanPcs();
            DecimalFormat form = new DecimalFormat("0.00");
            double test = 0.04;
            addFoclistreq.get(i).setMatrixCount(matrixcount = (selisih/spanmax)*matrixmeter);
//                    Toast.makeText(context, String.valueOf(form.format(matrixcount)),Toast.LENGTH_LONG).show();
            //udah foor
            if (floor){
                if ((long)Math.floor(addFoclistreq.get(i).getMatrixCount())<=0){
                    myviewholder.mmatrix.setText("0");
                }else {
                    myviewholder.mmatrix.setText(String.valueOf((long) Math.floor(addFoclistreq.get(i).getMatrixCount())));
                }
            }else {
                if ((long)Math.ceil(addFoclistreq.get(i).getMatrixCount())<=0){
                    myviewholder.mmatrix.setText("0");
                }else {
                    myviewholder.mmatrix.setText(String.valueOf((long) Math.ceil(addFoclistreq.get(i).getMatrixCount())));
                }
            }

        }
        cekdel=true;
        addFoclistreq.get(i).setPosition(i+1);
        floor = addFoclistreq.get(i).isMatrixFloor();
        myviewholder.mmatrixlabel.setText(matrixlabel);
        Picasso.with(context).load(addFoclistreq.get(i).getImgpic()).into(myviewholder.mimg);
        myviewholder.mname.setText(addFoclistreq.get(i).getNameitem());
        myviewholder.mcode.setText(addFoclistreq.get(i).getItemcd());
        myviewholder.mcategory.setText(addFoclistreq.get(i).getCategory());
        myviewholder.mqty.setText(String.valueOf(addFoclistreq.get(i).getQty()));
        myviewholder.mpos.setText(String.valueOf(addFoclistreq.get(i).getPosition()));
        myviewholder.munit.setText(addFoclistreq.get(i).getUnitName());
        myviewholder.mprevimpress.setText(String.valueOf(addFoclistreq.get(i).getLastImpression()));

        if (addFoclistreq.get(i).getStockOnHand().equals("?")){
            myviewholder.mstockhand.setText("");
        }else {
            myviewholder.mstockhand.setText(String.valueOf(addFoclistreq.get(i).getStockOnHand()));
        }

        mtotalitem.setText(String.valueOf(addFoclistreq.size()));
        totalqty = 0;
        for (int x = 0 ; x < addFoclistreq.size(); x++) {
            totalqty += addFoclistreq.get(x).getQty();
            mtotalqty.setText(String.valueOf(totalqty));


        }
        /// set list untuk json
        Gson gson = new GsonBuilder().create();
        myCustomArray = gson.toJsonTree(reitem).getAsJsonArray();
        jsonarayitem = myCustomArray.toString();
        mtotalitem.setText(String.valueOf(addFoclistreq.size()));
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
                cekdel=false;
//                myviewholder.mstockhand.clearFocus();
                if (addFoclistreq.size() >= 0) {
                    addFoclistreq.remove(i);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i, addFoclistreq.size());
                    totalqty = 0;
                    for (int x = 0 ; x < addFoclistreq.size(); x++) {
                        totalqty += addFoclistreq.get(x).getQty();
                        mtotalqty.setText(String.valueOf(totalqty));
//                        Picasso.with(context).load(addFoclistreq.get(x).getImgpic()).into(myviewholder.mimg);
//                        myviewholder.mname.setText(addFoclistreq.get(x).getNameitem());
//                        myviewholder.mcode.setText(addFoclistreq.get(x).getItemcd());
//                        myviewholder.mcategory.setText(addFoclistreq.get(x).getCategory());
//                        myviewholder.mqty.setText(String.valueOf(addFoclistreq.get(x).getQty()));
//                        myviewholder.mpos.setText(String.valueOf(addFoclistreq.get(x).getPosition()));
//                        myviewholder.munit.setText(addFoclistreq.get(x).getUnitName());
//                        myviewholder.mprevimpress.setText(String.valueOf(addFoclistreq.get(x).getLastImpression()));
//                        myviewholder.mstockhand.setText(String.valueOf(addFoclistreq.get(x).getStockOnHand()));
                    }
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
                Gson gson = new GsonBuilder().create();
                myCustomArray = gson.toJsonTree(addFoclistreq).getAsJsonArray();
                jsonarayitem = myCustomArray.toString();
                mtotalitem.setText(String.valueOf(addFoclistreq.size()));
            }
        });
        //button plus minus qty
        myviewholder.mplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qtynya = Integer.parseInt(myviewholder.mqty.getText().toString());
                totalqty=0;
                qtynya +=1;
                myviewholder.mqty.setText(String.valueOf(qtynya));
                addFoclistreq.get(i).setQty(qtynya);
                mtotalitem.setText(String.valueOf(addFoclistreq.size()));
                for (int i = 0 ; i < addFoclistreq.size(); i++) {
                    totalqty += addFoclistreq.get(i).getQty();
                    mtotalqty.setText(String.valueOf(totalqty));
                }
                Gson gson = new GsonBuilder().create();
                myCustomArray = gson.toJsonTree(addFoclistreq).getAsJsonArray();
//                Toast.makeText(context, myCustomArray.toString(),Toast.LENGTH_LONG).show();

            }
        });
        myviewholder.mminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qtynya = Integer.parseInt(myviewholder.mqty.getText().toString());
                if (qtynya==1){

                }else {

                    totalqty=0;
                    qtynya -=1;
                    myviewholder.mqty.setText(String.valueOf(qtynya));
                    addFoclistreq.get(i).setQty(qtynya);
                    mtotalitem.setText(String.valueOf(addFoclistreq.size()));
                    for (int i = 0 ; i < addFoclistreq.size(); i++) {
                        totalqty += addFoclistreq.get(i).getQty();
                        mtotalqty.setText(String.valueOf(totalqty));
                    }
                    Gson gson = new GsonBuilder().create();
                    myCustomArray = gson.toJsonTree(addFoclistreq).getAsJsonArray();
//                    Toast.makeText(context, myCustomArray.toString(),Toast.LENGTH_LONG).show();
                }


            }
        });
        mlastimpresi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                floor = addFoclistreq.get(i).isMatrixFloor();
                if (addFoclistreq.size()==0){

                }else {
                    if (addFoclistreq.get(i).getLastImpression()==0) {
                        myviewholder.mmatrix.setText("-");
                    }else if (!usingmatrix){
                        myviewholder.mmatrix.setText("-");
                    }else {
                        if (addFoclistreq.get(i).getMatrixLifeSpanPcs()==0){
                            myviewholder.mmatrix.setText("-");
                        }
                        if (mlastimpresi.length()==0){
                            lastimpresivalue=0;
                        }else {
                            
                            lastimpresivalue = Long.parseLong(mlastimpresi.getText().toString());
                        }
                        selisih = lastimpresivalue-addFoclistreq.get(i).getLastImpression();
                        spanmax = addFoclistreq.get(i).getMatrixLifeSpanPcs();
                        addFoclistreq.get(i).setMatrixCount(matrixcount = (selisih/spanmax)*matrixmeter);

//                    Toast.makeText(context, String.valueOf(form.format(matrixcount)),Toast.LENGTH_LONG).show();
                        //udah foor
                        if (floor){
                            if ((int)Math.floor(addFoclistreq.get(i).getMatrixCount())<=0){
                                myviewholder.mmatrix.setText("0");
                                addFoclistreq.get(i).setMatrix(myviewholder.mmatrix.getText().toString());
                            }
                            else {
                                if (myviewholder.mstockhand.length()==0){
                                    myviewholder.mmatrix.setText(String.valueOf((int)Math.floor(addFoclistreq.get(i).getMatrixCount())));
                                    addFoclistreq.get(i).setMatrix(myviewholder.mmatrix.getText().toString());
                                }else {
                                    int deservqty = (int)Math.floor(addFoclistreq.get(i).getMatrixCount());
                                    int stockhand = Integer.parseInt(myviewholder.mstockhand.getText().toString());
                                    if (deservqty-stockhand<=0){
                                        myviewholder.mmatrix.setText("0");
                                        addFoclistreq.get(i).setMatrix(myviewholder.mmatrix.getText().toString());
                                    }else {
                                        myviewholder.mmatrix.setText(String.valueOf(deservqty-stockhand));
                                        addFoclistreq.get(i).setMatrix(myviewholder.mmatrix.getText().toString());
                                    }
                                }

                            }
                        }
                        else {
                            if ((int)Math.ceil(addFoclistreq.get(i).getMatrixCount())<=0){
                                myviewholder.mmatrix.setText("0");
                                addFoclistreq.get(i).setMatrix(myviewholder.mmatrix.getText().toString());
                            }
                            else {
                                if (myviewholder.mstockhand.length()==0){
                                    myviewholder.mmatrix.setText(String.valueOf((int)Math.ceil(addFoclistreq.get(i).getMatrixCount())));
                                    addFoclistreq.get(i).setMatrix(myviewholder.mmatrix.getText().toString());
                                }else {
                                    int deservqty = (int)Math.ceil(addFoclistreq.get(i).getMatrixCount());
                                    int stockhand = Integer.parseInt(myviewholder.mstockhand.getText().toString());
                                    if (deservqty-stockhand<=0){
                                        myviewholder.mmatrix.setText("0");
                                        addFoclistreq.get(i).setMatrix(myviewholder.mmatrix.getText().toString());
                                    }else {
                                        myviewholder.mmatrix.setText(String.valueOf(deservqty-stockhand));
                                        addFoclistreq.get(i).setMatrix(myviewholder.mmatrix.getText().toString());
                                    }
                                }

                            }
                        }

                    }
                }

                Gson gson = new GsonBuilder().create();
                myCustomArray = gson.toJsonTree(addFoclistreq).getAsJsonArray();
//                Toast.makeText(context, myCustomArray.toString(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        myviewholder.mstockhand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//

            }

            @Override
            public void afterTextChanged(Editable s) {

                for (int x = 0 ; x < addFoclistreq.size(); x++) {

                }
                if (cekdel){
                    floor = addFoclistreq.get(i).isMatrixFloor();
                    if (myviewholder.mstockhand.length()==0){
                    addFoclistreq.get(i).setStockOnHand("?");
                }else{
                    addFoclistreq.get(i).setStockOnHand(myviewholder.mstockhand.getText().toString());
                }
                if (addFoclistreq.get(i).getLastImpression()==0){
                    myviewholder.mmatrix.setText("-");
                }else if (!usingmatrix){
                    myviewholder.mmatrix.setText("-");
                }else{
                    if (addFoclistreq.get(i).getMatrixLifeSpanPcs()==0){
                        myviewholder.mmatrix.setText("-");
                    }
                    if (myviewholder.mstockhand.length()==0){
                        addFoclistreq.get(i).setStockOnHand("?");
                        //udah foor
                        if (floor){
                            if ((int)Math.floor(addFoclistreq.get(i).getMatrixCount())<=0){
                                myviewholder.mmatrix.setText("0");
                                addFoclistreq.get(i).setMatrix(myviewholder.mmatrix.getText().toString());
                            }
                            else {
                                myviewholder.mmatrix.setText(String.valueOf((int)Math.floor(addFoclistreq.get(i).getMatrixCount())));
                                addFoclistreq.get(i).setMatrix(myviewholder.mmatrix.getText().toString());
                            }
                        }
                        else {

                            if ((int)Math.ceil(addFoclistreq.get(i).getMatrixCount())<=0){
                                myviewholder.mmatrix.setText("0");
                                addFoclistreq.get(i).setMatrix(myviewholder.mmatrix.getText().toString());
                            }
                            else {
                                myviewholder.mmatrix.setText(String.valueOf((int)Math.ceil(addFoclistreq.get(i).getMatrixCount())));
                                addFoclistreq.get(i).setMatrix(myviewholder.mmatrix.getText().toString());
                            }
                        }

                        myviewholder.mstockhand.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
                    }else {
                        addFoclistreq.get(i).setStockOnHand(myviewholder.mstockhand.getText().toString());
                        myviewholder.mstockhand.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
                        if (myviewholder.mmatrix.equals("0")){

                        }else {
                            //udahfloor
                            if (floor){

                                deservqty = (int)Math.floor(addFoclistreq.get(i).getMatrixCount());
                            }
                            else {

                                deservqty = (int)Math.ceil(addFoclistreq.get(i).getMatrixCount());
                            }

                            int stockhand = Integer.parseInt(myviewholder.mstockhand.getText().toString());
                            if (deservqty-stockhand<=0){
                                myviewholder.mmatrix.setText("0");
                                addFoclistreq.get(i).setMatrix(myviewholder.mmatrix.getText().toString());
                            }else {
                                myviewholder.mmatrix.setText(String.valueOf(deservqty-stockhand));
                                addFoclistreq.get(i).setMatrix(myviewholder.mmatrix.getText().toString());
                            }

                        }
                    }
                }
                }else {
                    
                }
//

                    Gson gson = new GsonBuilder().create();
                    myCustomArray = gson.toJsonTree(addFoclistreq).getAsJsonArray();
//                Toast.makeText(context, myCustomArray.toString(),Toast.LENGTH_LONG).show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return 
                addFoclistreq.size();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder{

        TextView mcode, mname, mcategory, mqty, mpos, munit, mprevimpress,mmatrix, mmatrixlabel, mlifespan;
        ImageView mimg, mminus, mplus,mdelete;
        EditText mstockhand;
        LinearLayout mlaylife;

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
            mprevimpress = itemView.findViewById(R.id.previmpres);
            mmatrix = itemView.findViewById(R.id.matrixvalue);
            mmatrixlabel = itemView.findViewById(R.id.matrixlabel);
            mstockhand = itemView.findViewById(R.id.stockhanda);
            mlifespan = itemView.findViewById(R.id.lifespan);
            mlaylife = itemView.findViewById(R.id.lifespanlay);


        }
    }
}

