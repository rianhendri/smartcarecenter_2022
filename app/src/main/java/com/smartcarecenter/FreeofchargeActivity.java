package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.Chargeable.ChargeableItem;
import com.smartcarecenter.Freeofcharge.FocAdapter;
import com.smartcarecenter.Freeofcharge.FocItem;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.supportservice.AddFormAdapter;
import com.smartcarecenter.supportservice.AddFromItem;

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
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;

public class FreeofchargeActivity extends AppCompatActivity {
    LinearLayout maddFoc;
    public static String valuefilter = "-";
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    FocAdapter addFormAdapterAdapter;
    boolean internet = true;
    private LinearLayoutManager linearLayoutManager;
    ArrayList<FocItem> list2;
    JsonArray listformreq;
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
    RecyclerView myitem_place;
    int page = 1;
    int pos = 0;
    boolean refreshscroll = true;
    String sesionid_new = "";
    int spin = 0;
    List<String> spinstatus = new ArrayList();
    int totalpage = 0;
    String totalrec = "";
    SwipeRefreshLayout mswip;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeofcharge);
        maddFoc = findViewById(R.id.addfoc);
        mback = findViewById(R.id.backbtn);
        myitem_place = findViewById(R.id.listpo);
        mstatus_spin = findViewById(R.id.spinstatus);
        mnested = findViewById(R.id.nestedscrol);
        mempetyreq = findViewById(R.id.norequest);
        mrecord = findViewById(R.id.totalorder);
        mfooterload =findViewById(R.id.footerload);
        mswip = findViewById(R.id.swiprefresh);
        //showadd
        if (showaddfoc.equals("false")){
            maddFoc.setVisibility(View.GONE);
            myitem_place.setPadding(0,0,0,0);
        }else {
            maddFoc.setVisibility(View.VISIBLE);
            myitem_place.setPadding(0,0,0,150);

        }
        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(FreeofchargeActivity.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        myitem_place.setLayoutManager(linearLayoutManager);
        myitem_place.setHasFixedSize(true);
        list2 = new ArrayList<FocItem>();
        getSessionId();
        cekInternet();
        if (internet){
            loadData();
            loadSpin();
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
                                mfooterload.setVisibility(View.VISIBLE);
                                refreshscroll=false;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable()
                                {
                                    @Override
                                    public void run() {
                                        if (page <=totalpage){
                                            myitem_place.setLayoutFrozen(true);
                                            pagination();
                                            mfooterload.setVisibility(View.GONE);
                                        }else {
                                            mfooterload.setVisibility(View.GONE);
                                            refreshscroll=false;
                                        }
                                    }
                                },500);

                            }

                        }else {
                            mfooterload.setVisibility(View.GONE);
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
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mstatus_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshscroll = true;
                page=1;
                cekInternet();
                for (int i = 0; i < listvalue.size(); ++i) {
                    valuefilter = listvalue.get(position);
                    if (internet) {
                        loadData();
                    }else {

                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        maddFoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoaddfoc = new Intent(FreeofchargeActivity.this, AddDetailFoc.class);
                startActivity(gotoaddfoc);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                finish();

            }
        });
        int color = getResources().getColor(R.color.colorPrimary);
        mswip.setColorSchemeColors(color);
        mswip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                cekInternet();

                if (internet){

                    new Handler().postDelayed(new Runnable() {
                        @Override public void run() {

                            // Berhenti berputar/refreshing

                            mswip.setRefreshing(false);
                            loadData();

                            // fungsi-fungsi lain yang dijalankan saat refresh berhenti

                        }
                    }, 500);

                }else {
//                    mswip.setEnabled(false);
//                    mswip.setRefreshing(false);
//                    mcontent.setVisibility(View.GONE);


                }
            }
        });
    }
    public void loadData(){
        page=1;

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("page",page);
        jsonObject.addProperty("status",valuefilter);
        jsonObject.addProperty("ver",ver);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.list_foc(jsonObject);
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
                    mfooterload.setVisibility(View.GONE);
                    JsonObject data = homedata.getAsJsonObject("data");
                    totalpage = data.get("totalPage").getAsInt();
                    listformreq = data.getAsJsonArray("frList");
                    totalrec = data.get("totalRec").toString();
                    mrecord.setText("Record: "+totalrec);
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<FocItem>>() {
                    }.getType();
                    list2 = gson.fromJson(listformreq.toString(), listType);
                    addFormAdapterAdapter = new FocAdapter(FreeofchargeActivity.this, list2);
                    myitem_place.setAdapter(addFormAdapterAdapter);
                    myitem_place.setVisibility(View.VISIBLE);

                    if (totalpage == 1) {
                        mfooterload.setVisibility(View.GONE);
                    }
                    if (totalpage == 0) {
                        mfooterload.setVisibility(View.GONE);
                    } else if (list2 != null) {
                        list2.size();
                        mfooterload.setVisibility(View.GONE);
                    }
                    if (listformreq.size() == 0) {
                        myitem_place.setVisibility(View.GONE);
                        mempetyreq.setVisibility(View.VISIBLE);
                        mfooterload.setVisibility(View.GONE);

                    }else {
                        myitem_place.setVisibility(View.VISIBLE);
                        mempetyreq.setVisibility(View.GONE);
                        mfooterload.setVisibility(View.GONE);
                    }
                    mfooterload.setVisibility(View.GONE);
                }else {
                    sesionid();
                    mfooterload.setVisibility(View.GONE);
                    Toast.makeText(FreeofchargeActivity.this, errornya,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(FreeofchargeActivity.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                mfooterload.setVisibility(View.GONE);

            }
        });
    }
    public void loadSpin(){
        mfooterload.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("page",page);
        jsonObject.addProperty("status","-");
        jsonObject.addProperty("ver",ver);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.list_foc(jsonObject);
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
                    JsonObject data = homedata.getAsJsonObject("data");
                    listformreq = data.getAsJsonArray("frList");
                    liststatus = data.getAsJsonArray("statusList");
                    for (int i = 0; i < liststatus.size(); ++i) {
                        JsonObject jsonObject3 = (JsonObject)liststatus.get(i);
                        String string3 = jsonObject3.getAsJsonObject().get("Value").getAsString();
                        String string4 = jsonObject3.getAsJsonObject().get("Text").getAsString();
                        listvalue.add(string3);
                        listnamestatus.add(string4);
                        for (int j = 0; j < listvalue.size(); ++j) {
                            if (listvalue.get(i).equals(valuefilter)){
                                pos=j;
                            }
                        }
                        final ArrayAdapter<String> kategori = new ArrayAdapter<String>(FreeofchargeActivity.this, R.layout.spinstatus_layout,
                                listnamestatus);
                        kategori.setDropDownViewResource(R.layout.spinkategori);
                        kategori.notifyDataSetChanged();
                        mstatus_spin.setAdapter(kategori);
                        mstatus_spin.setSelection(pos);
                        mfooterload.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(FreeofchargeActivity.this, errornya,Toast.LENGTH_LONG).show();
                    mfooterload.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(FreeofchargeActivity.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                mfooterload.setVisibility(View.GONE);

            }
        });
    }
    public void pagination(){
        mfooterload.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("page",page);
        jsonObject.addProperty("status",valuefilter);
        jsonObject.addProperty("ver",ver);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.list_foc(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                String errornya = homedata.get("errorMessage").toString();
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                sesionid();
                if (statusnya.equals("OK")){
                    JsonObject data = homedata.getAsJsonObject("data");
                    totalpage = data.get("totalPage").getAsInt();
                    listformreq = data.getAsJsonArray("frList");
                    totalrec = data.get("totalRec").toString();
                    mrecord.setText("Record: "+totalrec);
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<FocItem>>() {
                    }.getType();
                    ArrayList<FocItem> list;
                    list=new ArrayList<>();
                    list = gson.fromJson(listformreq.toString(), listType);
                    list2.addAll(list);

                    addFormAdapterAdapter = new FocAdapter(FreeofchargeActivity.this, list2);
                    myitem_place.setAdapter(addFormAdapterAdapter);
                    myitem_place.setVisibility(View.VISIBLE);
                    mfooterload.setVisibility(View.GONE);
                    if (totalpage == 1) {
                        mfooterload.setVisibility(View.GONE);
                    }
                    if (totalpage == 0) {
                        mfooterload.setVisibility(View.GONE);
                    } else if (list2 != null) {
                        list2.size();
                        mfooterload.setVisibility(View.GONE);
                    }
                    mfooterload.setVisibility(View.GONE);
//                    page++;
                    refreshscroll=true;
                }else {
                    sesionid();
                    mfooterload.setVisibility(View.GONE);
                    Toast.makeText(FreeofchargeActivity.this, errornya,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(FreeofchargeActivity.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                mfooterload.setVisibility(View.GONE);

            }
        });
    }

    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) FreeofchargeActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(FreeofchargeActivity.this, NoInternet.class);
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
                Intent gotoupdate = new Intent(FreeofchargeActivity.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(FreeofchargeActivity.this, LoginActivity.class));
            finish();
            Toast.makeText(FreeofchargeActivity.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent((Context)this, PurchaseMenu.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
}