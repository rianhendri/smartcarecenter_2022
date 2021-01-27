/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 *  androidx.recyclerview.widget.RecyclerView
 *  androidx.recyclerview.widget.RecyclerView$Adapter
 *  androidx.recyclerview.widget.RecyclerView$ViewHolder
 *  com.squareup.picasso.Picasso
 *  com.squareup.picasso.RequestCreator
 *  java.lang.CharSequence
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 */
package com.smartcarecenter.menuhome;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartcarecenter.Dashboard;
import com.smartcarecenter.FormActivity;
import com.smartcarecenter.LoginActivity;
import com.smartcarecenter.NewsActivity;
import com.smartcarecenter.PurchaseMenu;
import com.smartcarecenter.R;
import com.smartcarecenter.SettingActivity;
import com.smartcarecenter.SurveyList_Activity;
import com.smartcarecenter.menuhome.MenuAdapter;
import com.smartcarecenter.menuhome.MenuItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;

import static com.smartcarecenter.Dashboard.addFormAdapterAdapter;
import static com.smartcarecenter.Dashboard.installed;
import static com.smartcarecenter.Dashboard.installed2;
import static com.smartcarecenter.Dashboard.mshowPurchaseOrderFOC;
import static com.smartcarecenter.Dashboard.mshowPurchaseOrderPO;
import static com.smartcarecenter.Dashboard.news_new;
import static com.smartcarecenter.Dashboard.showaddfoc;
import static com.smartcarecenter.Dashboard.showaddform;
import static com.smartcarecenter.Dashboard.showaddpo;
import static com.smartcarecenter.Dashboard.list2;

public class MenuAdapter  extends RecyclerView.Adapter<MenuAdapter.Myviewholder> {
    private LinearLayoutManager linearLayoutManager;
    Context context;
    ArrayList<MenuItem> myItem;
    public static int positem = 0;
    public static RecyclerView mchatdialog;
    public MenuAdapter(Context c, ArrayList<MenuItem> p){
        context = c;
        myItem = p;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_menu,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        if (myItem.get(i).getMenuname().equals((context.getString(R.string.title_News)))){
            if (news_new.equals("0")){
                myviewholder.mdot.setVisibility(View.GONE);
            }else {
                myviewholder.mdot.setVisibility(View.VISIBLE);
                myviewholder.mnews_new.setText(news_new);
            }
        }

        Picasso.with(context).load(myItem.get(i).getImg()).into(myviewholder.mimg_menu);
        myviewholder.mnama_menu.setText(myItem.get(i).getMenuname());
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                String namemenu = myItem.get(i).getMenuname();
                if (namemenu.equals(context.getString(R.string.title_ServiceSupport))){
                    Intent gotonews = new Intent(context, FormActivity.class);
                    gotonews.putExtra("showaddform",showaddform);
                    context.startActivity(gotonews);
                    ((Activity)context).finish();
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
                if (namemenu.equals(context.getString(R.string.title_purchase_order))){
                    Intent gotonews = new Intent(context, PurchaseMenu.class);
                    gotonews.putExtra("mshowPurchaseOrderPO",mshowPurchaseOrderPO);
                    gotonews.putExtra("mshowPurchaseOrderPO",mshowPurchaseOrderFOC);
                    gotonews.putExtra("showaddpo",showaddpo);
                    gotonews.putExtra("showaddfoc",showaddfoc);
                    context.startActivity(gotonews);
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    ((Activity)context).finish();

                }
                if (namemenu.equals(context.getString(R.string.title_survei))){
                    Intent gotosurvey = new Intent(context, SurveyList_Activity.class);
                    context.startActivity(gotosurvey);
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    ((Activity)context).finish();
                }
                if (namemenu.equals(context.getString(R.string.title_News))){
                    Intent gotonews = new Intent(context, NewsActivity.class);
                    context.startActivity(gotonews);
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    ((Activity)context).finish();

                }
                if (namemenu.equals(context.getString(R.string.title_live_chat))){
                    if (installed) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Live Chat:");
                        // set the custom layout
                        final View customLayout = LayoutInflater.from(context).inflate(R.layout.chatdialoghome, null);
                        mchatdialog = (RecyclerView) customLayout.findViewById(R.id.chatdialog);
                        linearLayoutManager = new LinearLayoutManager(context, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
                        mchatdialog.setLayoutManager(linearLayoutManager);
                        mchatdialog.setHasFixedSize(true);
                        addFormAdapterAdapter = new ChatAdapter(context, list2);
                        mchatdialog.setAdapter(addFormAdapterAdapter);
                        builder.setView(customLayout);
                        // add a button

                        // create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }else {
                        if (installed2){
                            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                            builder.setTitle("Live Chat:");
                            // set the custom layout
                            final View customLayout = LayoutInflater.from(context).inflate(R.layout.chatdialoghome, null);
                            mchatdialog = (RecyclerView) customLayout.findViewById(R.id.chatdialog);
                            linearLayoutManager = new LinearLayoutManager(context, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
                            mchatdialog.setLayoutManager(linearLayoutManager);
                            mchatdialog.setHasFixedSize(true);
                            addFormAdapterAdapter = new ChatAdapter(context, list2);
                            mchatdialog.setAdapter(addFormAdapterAdapter);
                            builder.setView(customLayout);
                            // add a button

                            // create and show the alert dialog
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }else {
                            Toast.makeText(context,"Whatsapp blum di instal", Toast.LENGTH_SHORT).show();
                        }

                    }

                }
                if (namemenu.equals(context.getString(R.string.title_Setting))){
                    Intent gotosetting = new Intent(context, SettingActivity.class);
                    context.startActivity(gotosetting);
                    ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    ((Activity)context).finish();

                }
//
            }
        });

    }

    @Override
    public int getItemCount() {
        return
                myItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView  mnama_menu,mnews_new;
        ImageView mimg_menu;
        ProgressBar mporg;
        LinearLayout mdot;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mnama_menu = itemView.findViewById(R.id.namemenu);
            mimg_menu=itemView.findViewById(R.id.menuimg);
            mnews_new=itemView.findViewById(R.id.newnotif);
            mdot=itemView.findViewById(R.id.dot);

        }
    }
}
