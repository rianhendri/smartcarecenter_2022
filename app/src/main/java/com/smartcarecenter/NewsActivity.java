package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.listnews.NewsAdapter;
import com.smartcarecenter.listnews.NewsItem;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    boolean internet = true;
    RecyclerView layoutnews;
    private LinearLayoutManager linearLayoutManager;
    ArrayList<NewsItem> list2;
    JsonArray listnews;
    JsonArray liststatus;
    ImageView mback;
    DatabaseReference mbannerlink;
    ProgressBar mfooterload;
    private LinearLayoutManager mlinear;
    DatabaseReference mlist_news;
    NestedScrollView mnested;
    TextView mnonews;
    NewsAdapter newsAdapter;
    int page = 1;
    boolean refreshscroll = true;
    String sesionid_new = "";
    int totalpage = 0;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mfooterload = findViewById(R.id.loadingfooter);
        mback = findViewById(R.id.backbtn);
        layoutnews = findViewById(R.id.newscontentlist);
        mnonews = findViewById(R.id.nonews);
        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(NewsActivity.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        layoutnews.setLayoutManager(linearLayoutManager);
        layoutnews.setHasFixedSize(true);
        list2 = new ArrayList<NewsItem>();
        getSessionId();
        cekInternet();
        if (internet){
            loadnews();
        }else {

        }
    }
    public void loadnews(){

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("page",page);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, "http://api.smartcarecenter.id/");
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONnews(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                String errornya = homedata.get("errorMessage").toString();
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                if (statusnya.equals("OK")){
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    totalpage = data.get("totalPage").getAsInt();
                    listnews = data.getAsJsonArray("frList");
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<NewsItem>>() {
                    }.getType();
                    list2 = gson.fromJson(listnews.toString(), listType);
                    newsAdapter = new NewsAdapter(NewsActivity.this, list2);
                    layoutnews.setAdapter(newsAdapter);
                    layoutnews.setVisibility(View.VISIBLE);
                    mfooterload.setVisibility(View.GONE);
                    if (totalpage == 1) {
                        mfooterload.setVisibility(View.GONE);
                    }
                    if (totalpage == 0) {
                        mfooterload.setVisibility(View.GONE);
                    } else if (list2 != null) {
                        list2.size();
                    }
                    if (listnews.size() == 0) {
                        mnonews.setVisibility(View.VISIBLE);
                        layoutnews.setVisibility(View.GONE);

                    }else {
                        mnonews.setVisibility(View.GONE);
                        layoutnews.setVisibility(View.VISIBLE);
                    }

                }else {
                    sesionid();
                    mfooterload.setVisibility(View.GONE);
                    Toast.makeText(NewsActivity.this, errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(NewsActivity.this, t.toString(),Toast.LENGTH_LONG).show();
                cekInternet();
                mfooterload.setVisibility(View.GONE);

            }
        });
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) NewsActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(NewsActivity.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");

    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
                Intent gotoupdate = new Intent(NewsActivity.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(NewsActivity.this, LoginActivity.class));
            finish();
            Toast.makeText(NewsActivity.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent((Context)this, Dashboard.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
}