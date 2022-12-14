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
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.SurveyList.SurveyListItem;
import com.smartcarecenter.SurveyList.SurveylistAdapter;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.listnews.NewsAdapter;
import com.smartcarecenter.listnews.NewsItem;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.LinearLayout.VERTICAL;
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;

public class SurveyList_Activity extends AppCompatActivity {
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    boolean internet = true;
    RecyclerView layoutsurvey;
    private LinearLayoutManager linearLayoutManager;
    ArrayList<SurveyListItem> list2;
    public static JsonArray listnews;
    JsonArray liststatus;
    LinearLayout mback;
    DatabaseReference mbannerlink;
    ProgressBar mfooterload;
    private LinearLayoutManager mlinear;
    DatabaseReference mlist_news;
    NestedScrollView mnested;
    TextView mnonews;
    SurveylistAdapter newsAdapter;
    ProgressDialog loading;
    int page = 1;
    boolean refreshscroll = true;
    String sesionid_new = "";
    int totalpage = 0;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_list_);
        mfooterload = findViewById(R.id.loadingfooter);
        mback = findViewById(R.id.backbtn);
        layoutsurvey = findViewById(R.id.surveylist);
        mnonews = findViewById(R.id.nonews);
        mnested = findViewById(R.id.nested);
        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(SurveyList_Activity.this, VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        layoutsurvey.setLayoutManager(linearLayoutManager);
        layoutsurvey.setHasFixedSize(true);
        list2 = new ArrayList<SurveyListItem>();
        getSessionId();
        cekInternet();
        if (internet){
            loadnews();
        }else {

        }
        mnested.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {

                if(nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1) != null)
                {
                    if ((i1 >= (nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1)
                            .getMeasuredHeight() - nestedScrollView.getMeasuredHeight()))
                            && i1 > i3)
                    {
                        cekInternet();
                        if (internet){
                            if (refreshscroll){
                                page++;
                                loading = ProgressDialog.show(SurveyList_Activity.this, "", getString(R.string.title_loading), true);
                                refreshscroll=false;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable()
                                {
                                    @Override
                                    public void run() {
                                        if (page <=totalpage){
                                            layoutsurvey.setLayoutFrozen(true);
                                            pagination();
                                            loading.dismiss();
                                        }else {
                                            loading.dismiss();
                                            refreshscroll=false;
                                        }
                                    }
                                },500);

                            }

                        }else {
                            loading.dismiss();
//                                    Toast.makeText(getActivity(), String.valueOf(page), Toast.LENGTH_SHORT).show();
//                                    mfooterload.setVisibility(View.GONE);
//                                    mdatahabis.setVisibility(View.GONE);
//                                    mrefreshcoba.setVisibility(View.VISIBLE);

                        }





                    }
                }

            }
        });
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void pagination(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("page",page);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.listsurveyapi(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                sesionid();
                if (statusnya.equals("OK")){
                    JsonObject data = homedata.getAsJsonObject("data");
                    totalpage = data.get("totalPage").getAsInt();
                    listnews = data.getAsJsonArray("surveyFeedbackList");
                    Log.d("listanswerdetail",listnews.toString());
//                    totalrec = data.get("totalRec").toString();
//                    mrecord.setText("Record: "+totalrec);
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<SurveyListItem>>() {
                    }.getType();
                    ArrayList<SurveyListItem> list;
                    list=new ArrayList<>();
                    list = gson.fromJson(listnews.toString(), listType);

                    list2.addAll(list);
                    newsAdapter = new SurveylistAdapter(SurveyList_Activity.this, list2);
                    layoutsurvey.setAdapter(newsAdapter);
                    layoutsurvey.setVisibility(View.VISIBLE);
                    loading.dismiss();
                    if (totalpage == 1) {
                        loading.dismiss();
                    }
                    if (totalpage == 0) {
                        loading.dismiss();
                    } else if (list2 != null) {
                        list2.size();
                        loading.dismiss();
                    }
                    loading.dismiss();
//                    page++;
                    refreshscroll=true;
                }else {
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(SurveyList_Activity.this, errornya,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(SurveyList_Activity.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
    }

    public void loadnews(){
        loading = ProgressDialog.show(this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("page",page);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.listsurveyapi(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JsonObject homedata=response.body();
                Log.d("answer",homedata.toString());
                String statusnya = homedata.get("status").getAsString();
                String errornya = homedata.get("errorMessage").toString();
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                if (statusnya.equals("OK")){
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    totalpage = data.get("totalPage").getAsInt();
                    listnews = data.getAsJsonArray("surveyFeedbackList");
                    Log.d("listanswerdetail",jsonObject.toString());
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<SurveyListItem>>() {
                    }.getType();
                    list2 = gson.fromJson(listnews.toString(), listType);
//                    Toast.makeText(NewsActivity.this, list2.toString(), Toast.LENGTH_SHORT).show();
                    newsAdapter = new SurveylistAdapter(SurveyList_Activity.this, list2);
                    layoutsurvey.setAdapter(newsAdapter);
                    layoutsurvey.setVisibility(View.VISIBLE);
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
                        layoutsurvey.setVisibility(View.GONE);

                    }else {
                        mnonews.setVisibility(View.GONE);
                        layoutsurvey.setVisibility(View.VISIBLE);
                    }
                    loading.dismiss();

                }else {
                    loading.dismiss();
                    sesionid();
                    mfooterload.setVisibility(View.GONE);
                    Toast.makeText(SurveyList_Activity.this, errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(SurveyList_Activity.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                mfooterload.setVisibility(View.GONE);
                loading.dismiss();

            }
        });
        Log.d("surveyapilist",jsonObject.toString());
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) SurveyList_Activity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(SurveyList_Activity.this, NoInternet.class);
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
                Intent gotoupdate = new Intent(SurveyList_Activity.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(SurveyList_Activity.this, LoginActivity.class));
            finish();
            Toast.makeText(SurveyList_Activity.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
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