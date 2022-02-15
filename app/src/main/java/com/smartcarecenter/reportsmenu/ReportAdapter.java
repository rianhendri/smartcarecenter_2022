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
package com.smartcarecenter.reportsmenu;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartcarecenter.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.smartcarecenter.DetailsDailyReport.grandt1;
import static com.smartcarecenter.DetailsDailyReport.mgrandtotal;
import static com.smartcarecenter.DetailsDailyReport2.grandt2;
import static com.smartcarecenter.DetailsDailyReport2.mgrandtotal2;
import static com.smartcarecenter.DetailsDailyReport3.grandt3;
import static com.smartcarecenter.DetailsDailyReport3.mgrandtotal3;
import static com.smartcarecenter.MonthlyReportDetails.mgrandtotal7;

public class ReportAdapter
extends RecyclerView.Adapter<ReportAdapter.Myviewholder> {
    ArrayList<ReportsItem> addFromItem;
    Context context;
    ImageView mimgpopup;
    Double GRrandprie=0.0;
    public ReportAdapter(Context context, ArrayList<ReportsItem> addFromItem) {
        this.context = context;
        this.addFromItem = addFromItem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_detaildaily4a,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        myviewholder.msparepartname.setText(addFromItem.get(i).getSparePartName());
        myviewholder.mcode.setText(addFromItem.get(i).getSparePartCd());
        myviewholder.mqty.setText(addFromItem.get(i).getQuantity());
        myviewholder.mno.setText(String.valueOf(i+1));
        //Price Total
        if (addFromItem.get(i).getPricePerQty()==null){
            myviewholder.mprice.setText("");
            myviewholder.mtotal.setText("");
        }else {
            Locale locale = new Locale("en", "US");
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
            myviewholder.mprice.setText(currencyFormatter.format(Double.valueOf(addFromItem.get(i).getPricePerQty())));
            Locale locale2 = new Locale("en", "US");
            NumberFormat currencyFormatter2 = NumberFormat.getCurrencyInstance(locale2);
            myviewholder.mtotal.setText(currencyFormatter2.format(Double.valueOf(addFromItem.get(i).getPricePerQty())*
                    Integer.parseInt(myviewholder.mqty.getText().toString())));
            GRrandprie += Double.valueOf(addFromItem.get(i).getPricePerQty())*
                    Integer.parseInt(myviewholder.mqty.getText().toString());
            Log.d("grandtotal",String.valueOf(GRrandprie));
//            if (grandt2.equals("dua")){
//                mgrandtotal2.setText("2");
//            }
//            if (grandt3.equals("tiga")){
//                mgrandtotal3.setText(currencyFormatter.format(Double.valueOf(GRrandprie)));
//            }
//            if (grandt1.equals("satu")){
//                  mgrandtotal.setText(currencyFormatter.format(Double.valueOf(GRrandprie)));
//            }
            mgrandtotal7.setText(currencyFormatter.format(Double.valueOf(GRrandprie)));
            Log.d("grandtotal",String.valueOf(GRrandprie));
//            myviewholder.mpricea.setText("$"+String.valueOf(new DecimalFormat("##,###,###.00").format(Double.valueOf(addFoclistitem.get(i).getPricePerQty()))));
//            myviewholder.mtotalpricea.setText("$"+String.valueOf(new DecimalFormat("##,###,###.00").format(Double.valueOf(addFoclistitem.get(i).getPricePerQty())*
//                    Integer.parseInt(myviewholder.mqtysper.getText().toString()))));
            if ( myviewholder.mtotal.getText().toString().equals("$,00")){
                myviewholder.mtotal.setText("$0");
            }
        }
    }

    @Override
    public int getItemCount() {
        return addFromItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView mtotal,mprice, mcode, msttitledaily,mpresstypedaily,mpressstatudaily,mhtml,msndaily,mcaseiddaily,mcaseprogressdaily,msparepartname,mqty,mno;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            msparepartname = itemView.findViewById(R.id.sparepartname);
            mqty = itemView.findViewById(R.id.qty);
            mno = itemView.findViewById(R.id.nono);
            mprice = itemView.findViewById(R.id.price);
            mtotal = itemView.findViewById(R.id.total);
            mcode = itemView.findViewById(R.id.code);
        }
    }
    public void clear() {
        addFromItem.clear();
        notifyDataSetChanged();
    }
    public void setAddFromItem(ArrayList<ReportsItem> addFromItem)
    {
        for (ReportsItem im : addFromItem)
        {
            addFromItem.add(im);
        }
        notifyDataSetChanged();

    }
}

