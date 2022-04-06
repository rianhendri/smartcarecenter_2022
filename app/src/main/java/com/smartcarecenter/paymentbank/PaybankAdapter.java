/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
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
 *  java.io.PrintStream
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.text.ParseException
 *  java.text.SimpleDateFormat
 *  java.util.ArrayList
 *  java.util.Date
 *  java.util.Locale
 */
package com.smartcarecenter.paymentbank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartcarecenter.OrderSumary;
import com.smartcarecenter.R;
import com.smartcarecenter.ResultPayment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.smartcarecenter.PaymentAct.username;
import static com.smartcarecenter.PaymentAct.valuefilter;
import static com.smartcarecenter.PaymentAct.Grandtotal;
import static com.smartcarecenter.PaymentAct.noOrder;
import static com.smartcarecenter.PaymentAct.guid;
import static com.smartcarecenter.PaymentAct.mmustUpload;
import static com.smartcarecenter.PaymentAct.nopo;
public class PaybankAdapter
extends RecyclerView.Adapter<PaybankAdapter.Myviewholder> {

    Context context;
    ArrayList<PaybankItem> myItem;
    public static int positem = 0;

    public PaybankAdapter(Context c, ArrayList<PaybankItem> p){
        context = c;
        myItem = p;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_bankbayar,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        myviewholder.mnamebank.setText(myItem.get(i).getName());
        myviewholder.mdeskripbank.setText(myItem.get(i).getDescription());
        if (myItem.get(i).getPaymentGatewayCd().equals("KLIKPAY")){
            myItem.get(i).setImg(R.drawable.logo_bcaklikpay);
        }
        if (myItem.get(i).getPaymentGatewayCd().equals("CREDITCARD")){
            myItem.get(i).setImg(R.drawable.cc);
        }
        if (myItem.get(i).getPaymentGatewayCd().equals("VAMANDIRI")){
            myItem.get(i).setImg(R.drawable.mandiri);
        }
        if ((myItem.get(i).getPaymentGatewayCd().equals("VABCA"))){
            myItem.get(i).setImg(R.drawable.bca);
        }
        if ((myItem.get(i).getPaymentGatewayCd().equals("VADANAMON"))){
            myItem.get(i).setImg(R.drawable.danamon);
        }
        if ((myItem.get(i).getPaymentGatewayCd().equals("VAPERMATA"))){
            myItem.get(i).setImg(R.drawable.permata);
        }
        if ((myItem.get(i).getPaymentGatewayCd().equals("VABNI"))){
            myItem.get(i).setImg(R.drawable.bni);
        }
        if ((myItem.get(i).getPaymentGatewayCd().equals("VACIMB"))){
            myItem.get(i).setImg(R.drawable.cimb);
        }
        if ((myItem.get(i).getPaymentGatewayCd().equals("VAMAYBANK"))){
            myItem.get(i).setImg(R.drawable.mybank);
        }
        Picasso.with(context).load(myItem.get(i).getImg()).into(myviewholder.mlogobank);
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myItem.get(i).getPaymentGatewayCd().equals("KLIKPAY")){
                    Intent gotoaddfoc = new Intent(context, OrderSumary.class);
                    gotoaddfoc.putExtra("grandtotal",Grandtotal);
                    gotoaddfoc.putExtra("id",noOrder);
                    gotoaddfoc.putExtra("guid",guid);
                    gotoaddfoc.putExtra("username",username);
                    gotoaddfoc.putExtra("pdfyes",mmustUpload);
                    gotoaddfoc.putExtra("pos",valuefilter);
                    gotoaddfoc.putExtra("nopo",nopo);
                    gotoaddfoc.putExtra("paycd","KLIKPAY");
                    gotoaddfoc.putExtra("method",myItem.get(i).getName());
                    context.startActivity(gotoaddfoc);
                    ((Activity)context).finish();
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
                if (myItem.get(i).getPaymentGatewayCd().equals("CREDITCARD")){
                    Intent gotoaddfoc = new Intent(context, OrderSumary.class);
                    gotoaddfoc.putExtra("grandtotal",Grandtotal);
                    gotoaddfoc.putExtra("id",noOrder);
                    gotoaddfoc.putExtra("guid",guid);
                    gotoaddfoc.putExtra("username",username);
                    gotoaddfoc.putExtra("pdfyes",mmustUpload);
                    gotoaddfoc.putExtra("pos",valuefilter);
                    gotoaddfoc.putExtra("nopo",nopo);
                    gotoaddfoc.putExtra("ss","");
                    gotoaddfoc.putExtra("paycd","CREDITCARD");
                    gotoaddfoc.putExtra("method",myItem.get(i).getName());
                    context.startActivity(gotoaddfoc);
                    ((Activity)context).finish();
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
                if (myItem.get(i).getPaymentGatewayCd().equals("VAMANDIRI")){
                    Intent gotoaddfoc = new Intent(context, OrderSumary.class);
                gotoaddfoc.putExtra("grandtotal",Grandtotal);
                gotoaddfoc.putExtra("id",noOrder);
                gotoaddfoc.putExtra("guid",guid);
                gotoaddfoc.putExtra("username",username);
                gotoaddfoc.putExtra("pdfyes",mmustUpload);
                gotoaddfoc.putExtra("pos",valuefilter);
                gotoaddfoc.putExtra("nopo",nopo);
                    gotoaddfoc.putExtra("paycd","VAMANDIRI");
                    gotoaddfoc.putExtra("method",myItem.get(i).getName());
                    context.startActivity(gotoaddfoc);
                    ((Activity)context).finish();
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
                if ((myItem.get(i).getPaymentGatewayCd().equals("VABCA"))){
                    Intent gotoaddfoc = new Intent(context, OrderSumary.class);
                    gotoaddfoc.putExtra("grandtotal",Grandtotal);
                    gotoaddfoc.putExtra("id",noOrder);
                    gotoaddfoc.putExtra("guid",guid);
                    gotoaddfoc.putExtra("username",username);
                    gotoaddfoc.putExtra("pdfyes",mmustUpload);
                    gotoaddfoc.putExtra("pos",valuefilter);
                    gotoaddfoc.putExtra("nopo",nopo);
                    gotoaddfoc.putExtra("paycd","VABCA");
                    gotoaddfoc.putExtra("method",myItem.get(i).getName());
                    context.startActivity(gotoaddfoc);
                    ((Activity)context).finish();
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
                if ((myItem.get(i).getPaymentGatewayCd().equals("VADANAMON"))){
                    Intent gotoaddfoc = new Intent(context, OrderSumary.class);
                    gotoaddfoc.putExtra("grandtotal",Grandtotal);
                    gotoaddfoc.putExtra("id",noOrder);
                    gotoaddfoc.putExtra("guid",guid);
                    gotoaddfoc.putExtra("username",username);
                    gotoaddfoc.putExtra("pdfyes",mmustUpload);
                    gotoaddfoc.putExtra("pos",valuefilter);
                    gotoaddfoc.putExtra("nopo",nopo);
                    gotoaddfoc.putExtra("paycd","VADANAMON");
                    gotoaddfoc.putExtra("method",myItem.get(i).getName());
                    context.startActivity(gotoaddfoc);
                    ((Activity)context).finish();
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
                if ((myItem.get(i).getPaymentGatewayCd().equals("VAPERMATA"))){
                    Intent gotoaddfoc = new Intent(context, OrderSumary.class);
                    gotoaddfoc.putExtra("grandtotal",Grandtotal);
                    gotoaddfoc.putExtra("id",noOrder);
                    gotoaddfoc.putExtra("guid",guid);
                    gotoaddfoc.putExtra("username",username);
                    gotoaddfoc.putExtra("pdfyes",mmustUpload);
                    gotoaddfoc.putExtra("pos",valuefilter);
                    gotoaddfoc.putExtra("nopo",nopo);
                    gotoaddfoc.putExtra("paycd","VAPERMATA");
                    gotoaddfoc.putExtra("method",myItem.get(i).getName());
                    context.startActivity(gotoaddfoc);
                    ((Activity)context).finish();
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
                if ((myItem.get(i).getPaymentGatewayCd().equals("VABNI"))){
                    Intent gotoaddfoc = new Intent(context, OrderSumary.class);
                    gotoaddfoc.putExtra("grandtotal",Grandtotal);
                    gotoaddfoc.putExtra("id",noOrder);
                    gotoaddfoc.putExtra("guid",guid);
                    gotoaddfoc.putExtra("username",username);
                    gotoaddfoc.putExtra("pdfyes",mmustUpload);
                    gotoaddfoc.putExtra("pos",valuefilter);
                    gotoaddfoc.putExtra("nopo",nopo);
                    gotoaddfoc.putExtra("paycd","VABNI");
                    gotoaddfoc.putExtra("method",myItem.get(i).getName());
                    context.startActivity(gotoaddfoc);
                    ((Activity)context).finish();
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
                if ((myItem.get(i).getPaymentGatewayCd().equals("VACIMB"))){
                    Intent gotoaddfoc = new Intent(context, OrderSumary.class);
                    gotoaddfoc.putExtra("grandtotal",Grandtotal);
                    gotoaddfoc.putExtra("id",noOrder);
                    gotoaddfoc.putExtra("guid",guid);
                    gotoaddfoc.putExtra("username",username);
                    gotoaddfoc.putExtra("pdfyes",mmustUpload);
                    gotoaddfoc.putExtra("pos",valuefilter);
                    gotoaddfoc.putExtra("nopo",nopo);
                    gotoaddfoc.putExtra("paycd","VACIMB");
                    gotoaddfoc.putExtra("method",myItem.get(i).getName());
                    context.startActivity(gotoaddfoc);
                    ((Activity)context).finish();
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
                if ((myItem.get(i).getPaymentGatewayCd().equals("VAMAYBANK"))){
                    Intent gotoaddfoc = new Intent(context, OrderSumary.class);
                    gotoaddfoc.putExtra("grandtotal",Grandtotal);
                    gotoaddfoc.putExtra("id",noOrder);
                    gotoaddfoc.putExtra("guid",guid);
                    gotoaddfoc.putExtra("username",username);
                    gotoaddfoc.putExtra("pdfyes",mmustUpload);
                    gotoaddfoc.putExtra("pos",valuefilter);
                    gotoaddfoc.putExtra("nopo",nopo);
                    gotoaddfoc.putExtra("paycd","VAMAYBANK");
                    gotoaddfoc.putExtra("method",myItem.get(i).getName());
                    context.startActivity(gotoaddfoc);
                    ((Activity)context).finish();
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return
                myItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView mnamebank,mdeskripbank;
        ImageView mlogobank;


        public Myviewholder(@NonNull View view) {
            super(view);
            mnamebank = (TextView)view.findViewById(R.id.namebank);
            mdeskripbank = view.findViewById(R.id.deskripbank);
            mlogobank = view.findViewById(R.id.logobank);

        }
    }

}

