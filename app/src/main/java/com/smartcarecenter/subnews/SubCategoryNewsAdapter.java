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
package com.smartcarecenter.subnews;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartcarecenter.NewsActivity;
import com.smartcarecenter.R;
import com.smartcarecenter.SubCategoryNews;
import com.smartcarecenter.categorynew.CategoryNewsItem;
import com.smartcarecenter.menuhome.MenuItem;

import java.util.ArrayList;

import static com.smartcarecenter.Dashboard.showaddform;
import static com.smartcarecenter.SubCategoryNews.titlecategory;
import static com.smartcarecenter.SubCategoryNews.title;

public class SubCategoryNewsAdapter extends RecyclerView.Adapter<SubCategoryNewsAdapter.Myviewholder> {
    private LinearLayoutManager linearLayoutManager;
    public static String categorinya = "category";
    Context context;
    ArrayList<SubCategoryNewsItem> myItem;
    public static ArrayList<SubCategoryNewsItem> menuadapter = new ArrayList<SubCategoryNewsItem>();
    public static int positem = 0;
    public static RecyclerView mchatdialog;
    public static int pos = 0;
    public SubCategoryNewsAdapter(Context c, ArrayList<SubCategoryNewsItem> p){
        context = c;
        myItem = p;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_categorynews,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {

        myviewholder.mnama_menu.setText(myItem.get(i).getName());
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                        Intent gotonews = new Intent(context, NewsActivity.class);
                        gotonews.putExtra("subtitle",myItem.get(i).getName());
                        gotonews.putExtra("title",title);
                        gotonews.putExtra("subcd",myItem.get(i).getCategoryCd());
                        context.startActivity(gotonews);
                        ((Activity)context).finish();
                        ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);





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
