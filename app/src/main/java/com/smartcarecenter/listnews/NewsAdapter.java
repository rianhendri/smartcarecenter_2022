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
 *  com.squareup.picasso.Picasso
 *  com.squareup.picasso.RequestCreator
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
package com.smartcarecenter.listnews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartcarecenter.DetailsNews;
import com.smartcarecenter.NewsActivity;
import com.smartcarecenter.R;
import com.smartcarecenter.SettingActivity;
import com.smartcarecenter.listnews.NewsAdapter;
import com.smartcarecenter.listnews.NewsItem;
import com.smartcarecenter.menuhome.MenuAdapter;
import com.smartcarecenter.menuhome.MenuItem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.smartcarecenter.Dashboard.installed;

public class NewsAdapter
extends RecyclerView.Adapter<NewsAdapter.Myviewholder> {

    String newdate = "";
    Context context;
    ArrayList<NewsItem> myItem;
    public static int positem = 0;
    public static boolean download = true;
    public static String textdownload = "";
    public static String linkdownload = "";

    public NewsAdapter(Context c, ArrayList<NewsItem> p){
        context = c;
        myItem = p;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_news,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        Picasso.with(context).load(myItem.get(i).getBannerThumbURL()).into(myviewholder.mpic);
        myviewholder.mname.setText(myItem.get(i).getTitle());
        if (Build.VERSION.SDK_INT >= 24) {
            myviewholder.mconten.setText((CharSequence) Html.fromHtml((String)myItem.get(i).getContent(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            myviewholder.mconten.setText((CharSequence)Html.fromHtml((String)myItem.get(i).getContent()));


        }
//        myviewholder.mconten.setText(myItem.get(i).getContent());
        String string2 = (myItem.get(i).getDate());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            newdate = simpleDateFormat2.format(simpleDateFormat.parse(string2));
            System.out.println(newdate);
            Log.e((String)"Date", newdate);
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        myviewholder.mdate.setText(newdate);
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download = myItem.get(i).isShowDownloadButton();
                textdownload = myItem.get(i).getDownloadButtonText();
                linkdownload = myItem.get(i).getDownloadButtonURL();
                Intent intent = new Intent(context, DetailsNews.class);
                intent.putExtra("banner", (myItem.get(i).getBannerURL()));
                intent.putExtra("title", (myItem.get(i).getTitle()));
                intent.putExtra("date", (myviewholder.mdate.getText().toString()));
                intent.putExtra("content", (myItem.get(i)).getContent());
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                ((Activity)context).finish();

            }
        });

    }

    @Override
    public int getItemCount() {
        return
                myItem.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        TextView  mdate,mname,mconten;
        ImageView mpic;
        ProgressBar mporg;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mdate = itemView.findViewById(R.id.datenews);
            mpic=itemView.findViewById(R.id.xpic);
            mname = itemView.findViewById(R.id.titlenews);
            mconten = itemView.findViewById(R.id.contentview);

        }
    }
}

