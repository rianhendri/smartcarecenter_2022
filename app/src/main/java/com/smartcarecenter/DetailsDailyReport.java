package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.detailsdailyreport.DetailsDailyAdapter1;
import com.smartcarecenter.detailsdailyreport.DetailsDailyAdapter2;
import com.smartcarecenter.detailsdailyreport.DetailsDailyAdapter3;
import com.smartcarecenter.detailsdailyreport.DetailsDailyAdapter4;
import com.smartcarecenter.detailsdailyreport.DetailsDailyAdapter5;
import com.smartcarecenter.detailsdailyreport.DetailsDailyItem1;
import com.smartcarecenter.detailsdailyreport.DetailsDailyItem2;
import com.smartcarecenter.detailsdailyreport.DetailsDailyItem3;
import com.smartcarecenter.detailsdailyreport.DetailsDailyItem4;
import com.smartcarecenter.detailsdailyreport.DetailsDailyItem5;
import com.smartcarecenter.messagecloud.check;
import com.smartcarecenter.pmlist.PMAdapter;
import com.smartcarecenter.pmlist.PmListAdd;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.Dashboard.mshowPurchaseOrderFOC;
import static com.smartcarecenter.Dashboard.mshowPurchaseOrderPO;
import static com.smartcarecenter.Dashboard.showaddfoc;
import static com.smartcarecenter.Dashboard.showaddform;
import static com.smartcarecenter.Dashboard.showaddpo;
import static com.smartcarecenter.DailyReportList.valuefilter;
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;

public class DetailsDailyReport extends AppCompatActivity {
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    DetailsDailyAdapter1 addFormAdapterAdapter1;
    DetailsDailyAdapter2 addFormAdapterAdapter2;
    DetailsDailyAdapter3 addFormAdapterAdapter3;
    DetailsDailyAdapter4 addFormAdapterAdapter4;
    DetailsDailyAdapter5 addFormAdapterAdapter5;
    boolean internet = true;
    private LinearLayoutManager linearLayoutManager,linearLayoutManager2,linearLayoutManager3,linearLayoutManager4,linearLayoutManager5;
    public static ArrayList<DetailsDailyItem1> list1;
    public static ArrayList<DetailsDailyItem2> list2;
    public static ArrayList<DetailsDailyItem3> list3;
    public static ArrayList<DetailsDailyItem4> list4;
    public static ArrayList<DetailsDailyItem5> list5;
    JsonArray listformreq,listformreq2,listformreq3,listformreq4,listformreq5;
    List<String> listnamestatus = new ArrayList();
    JsonArray liststatus;
    List<String> listvalue = new ArrayList();
    LinearLayout maddform;
    LinearLayout mback;
    ProgressBar mfooterload;
    private LinearLayoutManager mlinear;
    NestedScrollView mnested;
    TextView mrecord,mempetyreq;
    Spinner mstatus_spin;
    RecyclerView myitem_place1,myitem_place2,myitem_place3,myitem_place4,myitem_place5;
    int page = 1;
    int pos = 0;
    boolean refreshscroll = true;
    String sesionid_new = "";
    String reportcd = "";
    TextView mcaseid,mreportdate,mpresssn,mcaseprogress,mpressstatus,mticketlink,mservicetype;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_daily_report);
        mrecord = findViewById(R.id.record);
        mfooterload = findViewById(R.id.footerload);
        mback = findViewById(R.id.backbtn);
        myitem_place1 = findViewById(R.id.temuanlist);
        myitem_place2 = findViewById(R.id.tindakanlist);
        myitem_place3 = findViewById(R.id.langkahlanjutanlist);
        myitem_place4 = findViewById(R.id.sperpartdaily);
        myitem_place5 = findViewById(R.id.createdby);

        mcaseid = findViewById(R.id.caseid);
        mreportdate = findViewById(R.id.reportdate);
        mpresssn = findViewById(R.id.presssn);
        mcaseprogress = findViewById(R.id.caseprogress);
        mpressstatus = findViewById(R.id.pressstatus);
        mticketlink = findViewById(R.id.ticketlink);
        mservicetype = findViewById(R.id.servicetype);

        check.checklistform=1;
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            reportcd = bundle2.getString("id");

        }
        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(DetailsDailyReport.this, LinearLayout.VERTICAL,false);
        linearLayoutManager2 = new LinearLayoutManager(DetailsDailyReport.this, LinearLayout.VERTICAL,false);
        linearLayoutManager3 = new LinearLayoutManager(DetailsDailyReport.this, LinearLayout.VERTICAL,false);
        linearLayoutManager4 = new LinearLayoutManager(DetailsDailyReport.this, LinearLayout.VERTICAL,false);
        linearLayoutManager5 = new LinearLayoutManager(DetailsDailyReport.this, LinearLayout.VERTICAL,false);

        myitem_place1.setLayoutManager(linearLayoutManager);
        myitem_place1.setHasFixedSize(true);
        myitem_place2.setLayoutManager(linearLayoutManager2);
        myitem_place2.setHasFixedSize(true);
        myitem_place3.setLayoutManager(linearLayoutManager3);
        myitem_place3.setHasFixedSize(true);
        myitem_place4.setLayoutManager(linearLayoutManager4);
        myitem_place4.setHasFixedSize(true);
        myitem_place5.setLayoutManager(linearLayoutManager5);
        myitem_place5.setHasFixedSize(true);
        list1 = new ArrayList<DetailsDailyItem1>();
        list2= new ArrayList<DetailsDailyItem2>();
        list3 = new ArrayList<DetailsDailyItem3>();
        list4 = new ArrayList<DetailsDailyItem4>();
        list5 = new ArrayList<DetailsDailyItem5>();

        getSessionId();
        cekInternet();
        if (internet){
            loadData();
        }else {

        }
    }
    public void loadData(){
//        page=1;
//        mfooterload.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("reportCd",reportcd);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.dailyrget(jsonObject);
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
                if (statusnya.equals("OK")){
                    JsonObject data = homedata.getAsJsonObject("data");

                    mcaseid.setText(data.get("caseID").getAsString());
                    mreportdate.setText(data.get("caseID").getAsString());
                    mpresssn.setText(data.get("pressSN").getAsString()+"("+data.get("pressType").getAsString()+")");
                    mcaseprogress.setText(data.get("caseProgress").getAsString());
                    mpressstatus.setText(data.get("pressStatus").getAsString());
                    mticketlink.setText(data.get("ticketUntuk").getAsString());
                    mservicetype.setText(data.get("serviceType").getAsString());

                    if (data.getAsJsonArray("temuanList")!=null){
                        listformreq = data.getAsJsonArray("temuanList");
                    }else {

                    }
                    if (data.getAsJsonArray("tindakanList")!=null){
                        listformreq2 = data.getAsJsonArray("tindakanList");
                    }else {

                    }
                    if (data.getAsJsonArray("langkahLanjutanList")!=null){
                        listformreq3 = data.getAsJsonArray("langkahLanjutanList");
                    }else {

                    }
                    if (data.getAsJsonArray("sparePartList")!=null){
                        listformreq4 = data.getAsJsonArray("sparePartList");
                    }else {

                    }
                    if (data.getAsJsonArray("createdBy")!=null){
                        listformreq5 = data.getAsJsonArray("createdBy");
                    }else {

                    }

                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<DetailsDailyItem1>>() {
                    }.getType();
                    Type listType2 = new TypeToken<ArrayList<DetailsDailyItem2>>() {
                    }.getType();
                    Type listType3 = new TypeToken<ArrayList<DetailsDailyItem3>>() {
                    }.getType();
                    Type listType4 = new TypeToken<ArrayList<DetailsDailyItem4>>() {
                    }.getType();
                    Type listType5 = new TypeToken<ArrayList<DetailsDailyItem4>>() {
                    }.getType();
                    list1 = gson.fromJson(listformreq.toString(), listType);
                    list2 = gson.fromJson(listformreq2.toString(), listType2);
                    list3 = gson.fromJson(listformreq3.toString(), listType3);
                    list4 = gson.fromJson(listformreq4.toString(), listType4);
                    list5 = gson.fromJson(listformreq4.toString(), listType5);
                    addFormAdapterAdapter1 = new DetailsDailyAdapter1(DetailsDailyReport.this, list1);
                    addFormAdapterAdapter2 = new DetailsDailyAdapter2(DetailsDailyReport.this, list2);
                    addFormAdapterAdapter3 = new DetailsDailyAdapter3(DetailsDailyReport.this, list3);
                    addFormAdapterAdapter4 = new DetailsDailyAdapter4(DetailsDailyReport.this, list4);

                    myitem_place1.setAdapter(addFormAdapterAdapter1);
                    myitem_place1.setVisibility(View.VISIBLE);
                    myitem_place2.setAdapter(addFormAdapterAdapter2);
                    myitem_place2.setVisibility(View.VISIBLE);
                    myitem_place3.setAdapter(addFormAdapterAdapter3);
                    myitem_place3.setVisibility(View.VISIBLE);
                    myitem_place4.setAdapter(addFormAdapterAdapter4);
                    myitem_place4.setVisibility(View.VISIBLE);
                    myitem_place5.setAdapter(addFormAdapterAdapter5);
                    myitem_place5.setVisibility(View.VISIBLE);


//                    if (listformreq.size() == 0) {
//                        myitem_place1.setVisibility(View.GONE);
//                        mempetyreq.setVisibility(View.VISIBLE);
//
//                    }else {
//                        myitem_place1.setVisibility(View.VISIBLE);
//                        mempetyreq.setVisibility(View.GONE);
//                    }
//                    if (listformreq2.size() == 0) {
//                        myitem_place2.setVisibility(View.GONE);
//                        mempetyreq.setVisibility(View.VISIBLE);
//
//                    }else {
//                        myitem_place2.setVisibility(View.VISIBLE);
//                        mempetyreq.setVisibility(View.GONE);
//                    }
//                    if (listformreq3.size() == 0) {
//                        myitem_place3.setVisibility(View.GONE);
//                        mempetyreq.setVisibility(View.VISIBLE);
//
//                    }else {
//                        myitem_place3.setVisibility(View.VISIBLE);
//                        mempetyreq.setVisibility(View.GONE);
//                    }
//                    if (listformreq4.size() == 0) {
//                        myitem_place4.setVisibility(View.GONE);
//                        mempetyreq.setVisibility(View.VISIBLE);
//
//                    }else {
//                        myitem_place4.setVisibility(View.VISIBLE);
//                        mempetyreq.setVisibility(View.GONE);
//                    }
//                    if (listformreq5.size() == 0) {
//                        myitem_place5.setVisibility(View.GONE);
//                        mempetyreq.setVisibility(View.VISIBLE);
//
//                    }else {
//                        myitem_place5.setVisibility(View.VISIBLE);
//                        mempetyreq.setVisibility(View.GONE);
//                    }


                }else {
                    sesionid();
                    mfooterload.setVisibility(View.GONE);
                    Toast.makeText(DetailsDailyReport.this, errornya,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsDailyReport.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                mfooterload.setVisibility(View.GONE);

            }
        });
        Log.d("reqlistfr",jsonObject.toString());
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) DetailsDailyReport.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(DetailsDailyReport.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");
        SharedPreferences show = getSharedPreferences("Show",MODE_PRIVATE);
        showaddpo = show.getString("showaddpo", "");
        showaddfoc = show.getString("showaddfoc", "");
        showaddform = show.getString("showaddform", "");
        mshowPurchaseOrderPO = show.getString("mshowPurchaseOrderPO", "");
        mshowPurchaseOrderFOC = show.getString("mshowPurchaseOrderFOC", "");


    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
                Intent gotoupdate = new Intent(DetailsDailyReport.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(DetailsDailyReport.this, LoginActivity.class));
            finish();
            Toast.makeText(DetailsDailyReport.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(DetailsDailyReport.this,DailyReportList.class);
        back.putExtra("pos",valuefilter);
        startActivity(back);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
}