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
package com.smartcarecenter.categorynew;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.smartcarecenter.Add_Foc_request.Add_foc_req_item;
import com.smartcarecenter.Add_Po_Request.Add_po_req_item;
import com.smartcarecenter.CategoryNews;
import com.smartcarecenter.FormActivity;
import com.smartcarecenter.NewsActivity;
import com.smartcarecenter.PurchaseMenu;
import com.smartcarecenter.R;
import com.smartcarecenter.SettingActivity;
import com.smartcarecenter.SubCategoryNews;
import com.smartcarecenter.SurveyList_Activity;
import com.smartcarecenter.menuhome.ChatAdapter;
import com.smartcarecenter.menuhome.MenuItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.smartcarecenter.Dashboard.addFormAdapterAdapter;
import static com.smartcarecenter.Dashboard.installed;
import static com.smartcarecenter.Dashboard.installed2;
import static com.smartcarecenter.Dashboard.list2;
import static com.smartcarecenter.Dashboard.mshowPurchaseOrderFOC;
import static com.smartcarecenter.Dashboard.mshowPurchaseOrderPO;
import static com.smartcarecenter.Dashboard.news_new;
import static com.smartcarecenter.Dashboard.showaddfoc;
import static com.smartcarecenter.Dashboard.showaddform;
import static com.smartcarecenter.Dashboard.showaddpo;
import static com.smartcarecenter.CategoryNews.menuItemlist;
import static com.smartcarecenter.DetailsSurvey.surveylistanswer;
import static com.smartcarecenter.CategoryNews.listnews;
import static com.smartcarecenter.SubCategoryNews.listnews2;

public class CategoryNewsAdapter extends RecyclerView.Adapter<CategoryNewsAdapter.Myviewholder> {
    private LinearLayoutManager linearLayoutManager;
    public static String categorinya = "category";
    Context context;
    ArrayList<CategoryNewsItem> myItem;
    public static ArrayList<CategoryNewsItem> listact = new ArrayList<CategoryNewsItem>();
    public static int positem = 0;
    public static RecyclerView mchatdialog;
    public CategoryNewsAdapter(Context c, ArrayList<CategoryNewsItem> p){
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
                for(int x=0;x<listnews.size();x++){
                    JsonObject movie = listnews.get(i).getAsJsonObject();
                    if (movie.get("SubCategories").toString().equals("null")){

                    }else {
                        listnews2 = movie.getAsJsonArray("SubCategories");
//                Toast.makeText(context, listanwermulti.toString(), Toast.LENGTH_SHORT).show();
                    }

//                        for(int j=0;j<characters.size();j++){
//                            temp.add(characters.get(i).toString());
//                        }

//                        Toast.makeText(SubMenuHome.this, characters.toString(), Toast.LENGTH_LONG).show();
                }
                String namemenu = myItem.get(i).getName();
                        Intent gotonews = new Intent(context, SubCategoryNews.class);
                        gotonews.putExtra("title",myItem.get(i).getName());
                        gotonews.putExtra("pos",i);
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
