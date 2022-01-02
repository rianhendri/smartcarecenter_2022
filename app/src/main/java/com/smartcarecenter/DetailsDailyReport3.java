package com.smartcarecenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;

public class DetailsDailyReport3 extends AppCompatActivity {
    String noreq = "";
    String home = "";
    String guid = "";
    String username = "";
    String noticket = "";
    String scrollnya = "";
    Integer xhori = 0;
    Integer yverti = 0;
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
    String startdate = "";
    String enddate="";
    TextView mcaseid,mreportdate,mpresssn,mpresssn2,mcaseprogress,mpressstatus,mticketlink,mservicetype,mtanpasper,mcustname,mnotesdal;
    String hide="";
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_daily_report3);
        mrecord = findViewById(R.id.record);
        mnotesdal = findViewById(R.id.notesdal);
        mtanpasper=findViewById(R.id.tanpasper);
        mcustname=findViewById(R.id.custname);
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
        mpresssn2 = findViewById(R.id.presssn2);
        mcaseprogress = findViewById(R.id.caseprogress);
        mpressstatus = findViewById(R.id.pressstatus);
        mticketlink = findViewById(R.id.ticketlink);
        mservicetype = findViewById(R.id.servicetype);

        check.checklistform=1;
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            noreq = bundle2.getString("id");
            home = bundle2.getString("home");
            guid = bundle2.getString("guid");
            username = bundle2.getString("user");
            noticket = bundle2.getString("noticket");
//            ServiceTicket.valuefilter = bundle2.getString("pos");
            scrollnya =   bundle2.getString("scrolbawah");
            xhori=bundle2.getInt("xhori");
            yverti=bundle2.getInt("yverti");
            reportcd = bundle2.getString("report");
            startdate = bundle2.getString("startd");
            enddate = bundle2.getString("endd");
            hide = bundle2.getString("hide");
            Log.d("startdat",noreq);

        }
        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(DetailsDailyReport3.this, LinearLayout.VERTICAL,false);
        linearLayoutManager2 = new LinearLayoutManager(DetailsDailyReport3.this, LinearLayout.VERTICAL,false);
        linearLayoutManager3 = new LinearLayoutManager(DetailsDailyReport3.this, LinearLayout.VERTICAL,false);
        linearLayoutManager4 = new LinearLayoutManager(DetailsDailyReport3.this, LinearLayout.VERTICAL,false);
        linearLayoutManager5 = new LinearLayoutManager(DetailsDailyReport3.this, LinearLayout.VERTICAL,false);

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
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
                Log.d("respombody",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                if (statusnya.equals("OK")){
                    ReadNotif();
                    JsonObject data = homedata.getAsJsonObject("data");
                    if (data.get("notes").getAsString().equals("")){
                        mnotesdal.setText("-");
                    }else {
                        mnotesdal.setText(data.get("notes").getAsString());
                    }

                    mcustname.setText(data.get("customerName").getAsString());
                    mcaseid.setText(data.get("caseID").getAsString());
                    String newdate = "";
                    String oldadate = data.get("reportDateTime").getAsString();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
                    try {
                        newdate = simpleDateFormat2.format(simpleDateFormat.parse(oldadate));
                        System.out.println(newdate);
                        Log.e((String)"Datexs", (String)newdate);
                    }
                    catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    mreportdate.setText(newdate);
                    mpresssn.setText(data.get("pressSN").getAsString());
                    mpresssn2.setText(data.get("pressType").getAsString());
                    mcaseprogress.setText(data.get("caseProgress").getAsString());
//                    if (data.get("caseProgress").getAsString().equals("Masalah Sudah Selesai")){
//                        mcaseprogress.setTextColor(Color.parseColor("#"));
//                    }else {
//
//                    }
                    mpressstatus.setText(data.get("pressStatus").getAsString());
                    if (data.get("pressStatus").getAsString().equals("Mesin Tetap Produksi")){
                        mpressstatus.setTextColor(Color.parseColor("#0890cc"));
                    }else {
                        mpressstatus.setTextColor(Color.parseColor("#FF0000"));
                    }
                    mticketlink.setText(data.get("ticketUntuk").getAsString());
                    mservicetype.setText(data.get("serviceType").getAsString());

                    if (data.getAsJsonArray("temuanList").toString().equals("[]")){

                    }else {
                        listformreq = data.getAsJsonArray("temuanList");
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<DetailsDailyItem1>>() {
                        }.getType();
                        list1 = gson.fromJson(listformreq.toString(), listType);
                        addFormAdapterAdapter1 = new DetailsDailyAdapter1(DetailsDailyReport3.this, list1);
                        myitem_place1.setAdapter(addFormAdapterAdapter1);
                        myitem_place1.setVisibility(View.VISIBLE);
                    }
                    if (data.getAsJsonArray("tindakanList").toString().equals("[]")){

                    }else {
                        listformreq2 = data.getAsJsonArray("tindakanList");
                        Gson gson = new Gson();
                        Type listType2 = new TypeToken<ArrayList<DetailsDailyItem2>>() {
                        }.getType();

                        list2 = gson.fromJson(listformreq2.toString(), listType2);
                        addFormAdapterAdapter2 = new DetailsDailyAdapter2(DetailsDailyReport3.this, list2);
                        myitem_place2.setAdapter(addFormAdapterAdapter2);
                        myitem_place2.setVisibility(View.VISIBLE);
                    }
                    if (data.getAsJsonArray("langkahLanjutanList").toString().equals("[]")){

                    }else {
                        listformreq3 = data.getAsJsonArray("langkahLanjutanList");
                        Gson gson = new Gson();
                        Type listType3 = new TypeToken<ArrayList<DetailsDailyItem3>>() {
                        }.getType();
                        list3 = gson.fromJson(listformreq3.toString(), listType3);
                        addFormAdapterAdapter3 = new DetailsDailyAdapter3(DetailsDailyReport3.this, list3);
                        myitem_place3.setAdapter(addFormAdapterAdapter3);
                        myitem_place3.setVisibility(View.VISIBLE);
                    }
                    if (data.getAsJsonArray("sparePartList").toString().equals("[]")){
                        mtanpasper.setVisibility(View.VISIBLE);
                    }else {
                        listformreq4 = data.getAsJsonArray("sparePartList");
                        Gson gson = new Gson();
                        Type listType4 = new TypeToken<ArrayList<DetailsDailyItem4>>() {
                        }.getType();

                        list4 = gson.fromJson(listformreq4.toString(), listType4);
                        addFormAdapterAdapter4 = new DetailsDailyAdapter4(DetailsDailyReport3.this, list4);
                        myitem_place4.setAdapter(addFormAdapterAdapter4);
                        myitem_place4.setVisibility(View.VISIBLE);
                        mtanpasper.setVisibility(View.GONE);
                    }
                    if (data.getAsJsonArray("createdBy").toString().equals("[]")){
                        mtanpasper.setVisibility(View.VISIBLE);
                    }else {
                        listformreq5 = data.getAsJsonArray("createdBy");
                        Gson gson = new Gson();
                        Type listType5 = new TypeToken<ArrayList<DetailsDailyItem5>>() {
                        }.getType();
                        list5 = gson.fromJson(listformreq5.toString(), listType5);
                        addFormAdapterAdapter5 = new DetailsDailyAdapter5(DetailsDailyReport3.this, list5);
                        myitem_place5.setAdapter(addFormAdapterAdapter5);
                        myitem_place5.setVisibility(View.VISIBLE);

                    }

//                    Log.d("lisffromm",listformreq5.toString());
//                    if (listformreq4.toString().equals("[]")){
//                        myitem_place4.setVisibility(View.GONE);
//                        mtanpasper.setVisibility(View.VISIBLE);
//                    }else {
//                        myitem_place4.setVisibility(View.VISIBLE);
//                        mtanpasper.setVisibility(View.GONE);
//                    }


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
//                    mfooterload.setVisibility(View.GONE);
                    Toast.makeText(DetailsDailyReport3.this, errornya,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsDailyReport3.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                mfooterload.setVisibility(View.GONE);

            }
        });
        Log.d("loaddatadailyget",jsonObject.toString());
    }
    public void ReadNotif(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("reportCd",reportcd);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.dailyread(jsonObject);
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
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
//                    String message = data.get("message").getAsString();
//                    Toast.makeText(DetailsNotification.this, message,Toast.LENGTH_LONG).show();

                }else {
                    sesionid();
//                    loading.dismiss();
                    Toast.makeText(DetailsDailyReport3.this,errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsDailyReport3.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading.dismiss();

            }
        });
        Log.d("readnotifdaily",jsonObject.toString());
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) DetailsDailyReport3.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(DetailsDailyReport3.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");
        SharedPreferences show = getSharedPreferences("Show",MODE_PRIVATE);
//        showaddpo = show.getString("showaddpo", "");
//        showaddfoc = show.getString("showaddfoc", "");
//        showaddform = show.getString("showaddform", "");
//        mshowPurchaseOrderPO = show.getString("mshowPurchaseOrderPO", "");
//        mshowPurchaseOrderFOC = show.getString("mshowPurchaseOrderFOC", "");


    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
//                Intent gotoupdate = new Intent(DetailsDailyReport.this, UpdateActivity.class);
//                startActivity(gotoupdate);
//                finish();
            }

        }else {
            startActivity(new Intent(DetailsDailyReport3.this, LoginActivity.class));
            finish();
            Toast.makeText(DetailsDailyReport3.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(DetailsDailyReport3.this, DailiReportListFR.class);
        back.putExtra("home", home);
        back.putExtra("guid", guid);
        back.putExtra("user", username);
        back.putExtra("noticket", noticket);
        back.putExtra("id", noreq);
//        back.putExtra("pos", ServiceTicket.valuefilter);
        back.putExtra("scrolbawah", scrollnya);
        back.putExtra("xhori", xhori);
        back.putExtra("yverti", yverti);
//        back.putExtra("pos",valuefilter);
        back.putExtra("startd",startdate);
        back.putExtra("endd",enddate);
        back.putExtra("hide","yes");
        startActivity(back);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
        startActivity(back);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();

    }
}