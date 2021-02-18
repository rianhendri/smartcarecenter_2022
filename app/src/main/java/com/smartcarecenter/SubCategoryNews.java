package com.smartcarecenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.categorynew.CategoryNewsAdapter;
import com.smartcarecenter.categorynew.CategoryNewsItem;
import com.smartcarecenter.menuhome.MenuItem;
import com.smartcarecenter.subnews.SubCategoryNewsAdapter;
import com.smartcarecenter.subnews.SubCategoryNewsItem;

import static com.smartcarecenter.categorynew.CategoryNewsAdapter.listact;
import static com.smartcarecenter.subnews.SubCategoryNewsAdapter.menuadapter;
import static com.smartcarecenter.subnews.SubCategoryNewsAdapter.pos;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.smartcarecenter.FormActivity.valuefilter;

public class SubCategoryNews extends AppCompatActivity {
    private LinearLayoutManager linearLayoutManager;
    RecyclerView mymenu;
    public static ArrayList<SubCategoryNewsItem> menuItemlist;
    SubCategoryNewsAdapter addmenu;
    TextView mtitle;
    public static String titlecategory = "";
    public static String title = "";
    public static JsonArray listnews2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category_news);
        mymenu = findViewById(R.id.newscontentlist);
        mtitle = findViewById(R.id.title);
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
         mtitle.setText(bundle2.getString("title"));
         title=bundle2.getString("title");
//            titlecategory =bundle2.getString("subtitle");
            pos = bundle2.getInt("pos");


        }

        linearLayoutManager = new GridLayoutManager(SubCategoryNews.this, 2);
        mymenu.setLayoutManager(linearLayoutManager);
        Configuration orientation = new Configuration();

        mymenu.setHasFixedSize(true);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<SubCategoryNewsItem>>() {
        }.getType();
        menuItemlist = gson.fromJson(listnews2.toString(), listType);
//                    Toast.makeText(NewsActivity.this, list2.toString(), Toast.LENGTH_SHORT).show();
        addmenu = new SubCategoryNewsAdapter(SubCategoryNews.this, menuItemlist);
        mymenu.setAdapter(addmenu);
        mymenu.setVisibility(View.VISIBLE);

        Log.d("listnya",listnews2.toString());
//
//        menuItem.setMenuname("Holiday");
//        menuItemlist.add(menuItem);
//
//        menuItem2.setMenuname("System Update");
//        menuItemlist.add(menuItem2);

//        menuItem3.setMenuname("Others");
//        menuItemlist.add(menuItem3);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent((Context)this, CategoryNews.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
}