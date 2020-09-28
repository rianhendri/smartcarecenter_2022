package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.menuhome.MenuAdapter;
import com.smartcarecenter.menuhome.MenuItem;
import com.smartcarecenter.messagecloud.check;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;

public class Dashboard extends AppCompatActivity {
    public static boolean installed = true;
    public static String mshowFormRequest = "";
    public static String mshowLiveChat = "";
    public static String mshowNews = "";
    public static String mshowNotification = "";
    public static String mshowPressList = "";
    public static String mshowPurchaseOrderFOC = "";
    public static String mshowPurchaseOrderPO = "";
    public static String mshowSettings = "";
    public static String mshowSurvey = "";
    public static String showaddform = "";
    public static String showaddfoc = "";
    public static String showaddpo = "";
    int tax = 0;
    String taxename = "";
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    MenuAdapter addmenu;
    String akunid = "";
    Boolean internet = false;
    Boolean exit = false;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager2;
    String mcompanyLogoURL = "";
    String mcompanyName = "";
    ArrayList<MenuItem> menuItemlist;
    ImageView mfoto;
    String mgroupName = "";
    TextView mgrouppt,malert;
    String mhomeName = "";
    LinearLayout mmechine;
    TextView mnamaid;
    TextView mnamapt;
    LinearLayout mnews;
    LinearLayout mnotif;
    LinearLayout mnotif_btn,mlayoutalert;
    LinearLayout morder;
    LinearLayout mreq;
    LinearLayout msetting;
    LinearLayout msupport;
    LinearLayout msurvey;
    TextView mversion;
    RecyclerView mymenu;
    String sesionid_new = "";
    boolean notes = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mversion = findViewById(R.id.version_name);
        mymenu = findViewById(R.id.menu);
        mnamaid = findViewById(R.id.namauser);
        mnamapt = findViewById(R.id.ptname);
        mgrouppt = findViewById(R.id.groupname);
        mfoto = findViewById(R.id.foto);
        mnotif_btn = findViewById(R.id.notifikasi);
        mlayoutalert = findViewById(R.id.backgroundalert);
        malert = findViewById(R.id.textalert);
        cekInternet();
        getSessionId();
        check.checknotif=1;
        if (internet){
            reqApi();
            appInstalledOrNot("com.whatsapp");
        }else {

        }
        String versionName = BuildConfig.VERSION_NAME;
        mversion.setText("Samafitro Smart Care Center - "+getString(R.string.title_Version)+ versionName);
        mnotif_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotonews = new Intent(Dashboard.this, Notification.class);
                startActivity(gotonews);
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }
    public boolean appInstalledOrNot(String string2) {
        PackageManager packageManager = this.getPackageManager();
        try {
            packageManager.getPackageInfo(string2, packageManager.GET_ACTIVITIES);
            installed = true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            installed = false;

        }
        return installed;
    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
                Intent gotoupdate = new Intent(Dashboard.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(Dashboard.this, LoginActivity.class));
            finish();
            Toast.makeText(Dashboard.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");

    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager)Dashboard.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(Dashboard.this,NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void reqApi() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",ver);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONconfig(jsonObject);
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
                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")){
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    //alertnotes

                    notes = data.get("showHomeNotes").getAsBoolean();
                    if (notes){
                        String background = data.get("homeNotesBackgroundColor").getAsString();
                        GradientDrawable shape =  new GradientDrawable();
                        shape.setCornerRadius( 15 );
                        shape.setColor(Color.parseColor(background));
                        mlayoutalert.setVisibility(View.VISIBLE);
                        mlayoutalert.setBackground(shape);
                        String text = data.get("homeNotesText").getAsString();
                        if (Build.VERSION.SDK_INT >= 24) {
                            malert.setText((CharSequence) Html.fromHtml((String)text, Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            malert.setText((CharSequence)Html.fromHtml((String)text));
                        }
                    }else {
                        mlayoutalert.setVisibility(View.GONE);
                    }
                    //HEADER
                    tax = data.get("taxPercentage").getAsInt();
                    taxename = data.get("taxLabel").getAsString();
                    SharedPreferences.Editor editor = getSharedPreferences("Tax", MODE_PRIVATE).edit();
                    editor.putInt("tax", tax);
                    editor.putString("taxname",taxename);
                    editor.apply();
                    mhomeName = data.get("homeName").getAsString();
                    mcompanyName = data.get("companyName").getAsString();
                    mgroupName = data.get("groupName").getAsString();
                    mcompanyLogoURL = data.get("companyLogoURL").getAsString();
                    mnamaid.setText(mhomeName);
                    mnamapt.setText(mcompanyName);
                    mgrouppt.setText(mgroupName);
                    Picasso.with(Dashboard.this).load(mcompanyLogoURL).into(mfoto);
                    //MENU
                    JsonObject access = data.getAsJsonObject("accessLevel");
                    mshowFormRequest = access.get("showFormRequest").toString();
                    mshowPurchaseOrderPO = access.get("showPurchaseOrderPO").toString();
                    mshowPurchaseOrderFOC = access.get("showPurchaseOrderFOC").toString();
                    mshowSurvey = access.get("showSurvey").toString();
                    mshowNews = access.get("showNews").toString();
                    mshowPressList = access.get("showPressList").toString();
                    mshowLiveChat = access.get("showLiveChat").toString();
                    mshowNotification = access.get("showNotification").toString();
                    mshowSettings = access.get("showSettings").toString();
                    showaddfoc = access.get("allowAddPurchaseOrderFOC").toString();
                    showaddpo = access.get("allowAddPurchaseOrderPO").toString();
                    showaddform = access.get("allowAddFormRequest").toString();
                    SharedPreferences.Editor show = getSharedPreferences("Show", MODE_PRIVATE).edit();
                    show.putString("showaddpo", showaddpo);
                    show.putString("showaddfoc",showaddfoc);
                    show.putString("showaddform",showaddform);
                    show.putString("mshowPurchaseOrderPO",mshowPurchaseOrderPO);
                    show.putString("mshowPurchaseOrderFOC",mshowPurchaseOrderFOC);
                    show.apply();
                    linearLayoutManager = new GridLayoutManager(Dashboard.this, 2);
                    mymenu.setLayoutManager(linearLayoutManager);
                    Configuration orientation = new Configuration();

                    mymenu.setHasFixedSize(true);
                    MenuItem menuItem = new MenuItem();
                    MenuItem menuItem2 = new MenuItem();
                    MenuItem menuItem3 = new MenuItem();
                    MenuItem menuItem4 = new MenuItem();
                    MenuItem menuItem5 = new MenuItem();
                    MenuItem menuItem6 = new MenuItem();
                    new MenuItem();
                    MenuItem menuItem7 = new MenuItem();
                    menuItemlist = new ArrayList();

                    if (mshowFormRequest.equals("true")){
                        menuItem.setMenuname(getString(R.string.title_ServiceSupport));
                        menuItem.setImg(R.drawable.req);
                        menuItem.setShow(mshowFormRequest);
                        menuItemlist.add(menuItem);
                    }
                    if (mshowPurchaseOrderPO.equals("false") && mshowPurchaseOrderFOC.equals("false")){

                    }else{
                        menuItem2.setMenuname(getString(R.string.title_purchase_order));
                        menuItem2.setImg(R.drawable.purchase);
                        menuItem2.setShow("true");
                        menuItemlist.add(menuItem2);
                    }

                    if (mshowPressList.equals("true")){
                        menuItem3.setMenuname(getString(R.string.title_Presslist));
                        menuItem3.setImg(R.drawable.req);
                        menuItem3.setShow(mshowPressList);
                        menuItemlist.add(menuItem3);
                    }
                    if (mshowSurvey.equals("true")){
                        menuItem4.setMenuname(getString(R.string.title_survei));
                        menuItem4.setImg(R.drawable.survey);
                        menuItem4.setShow(mshowSurvey);
                        menuItemlist.add(menuItem4);
                    }
                    if (mshowNews.equals("true")){
                        menuItem5.setMenuname(getString(R.string.title_News));
                        menuItem5.setImg(R.drawable.news);
                        menuItem5.setShow(mshowNews);
                        menuItemlist.add(menuItem5);
                    }
                    if (mshowLiveChat.equals("true")){
                        menuItem6.setMenuname(getString(R.string.title_live_chat));
                        menuItem6.setImg(R.drawable.wa);
                        menuItem6.setShow(mshowLiveChat);
                        menuItemlist.add(menuItem6);
                    }

                    if (mshowSettings.equals("true")){
                        menuItem7.setMenuname(getString(R.string.title_Setting));
                        menuItem7.setImg(R.drawable.settings);
                        menuItem7.setShow(mshowSettings);
                        menuItemlist.add(menuItem7);
                    }
                    addmenu = new MenuAdapter(Dashboard.this, menuItemlist);
                    mymenu.setAdapter(addmenu);
                    if(mymenu.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                      if (menuItemlist.size()>4)
                      { mymenu.getLayoutParams().height = 1400;
                      }
                      else if( menuItemlist.size()>6) {
                          mymenu.getLayoutParams().height = 1700;
                        }else if (menuItemlist.size()>8) {
                          mymenu.getLayoutParams().height = 2000;
                      }else if( menuItemlist.size()>10) {
                          mymenu.getLayoutParams().height = 2300;
                      }
                        mymenu.setNestedScrollingEnabled(false);
                        mymenu.setLayoutManager(new GridLayoutManager(Dashboard.this, 2));
                    } else if (mymenu.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        mymenu.setNestedScrollingEnabled(true);
                        mymenu.setLayoutManager(new GridLayoutManager(Dashboard.this, 3));
                        ViewGroup.LayoutParams params=mymenu.getLayoutParams();
                        params.height=700;
                        mymenu.setLayoutParams(params);
                    }
                }
                else {
                    Toast.makeText(Dashboard.this, errornya.toString(),Toast.LENGTH_LONG).show();
                    sesionid();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Dashboard.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();

            }
        });
    }
    public void onBackPressed(){
            if (exit) {
                this.finish();

            } else {
                Toast.makeText(this, getString(R.string.title_exit),
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);

            }



    }
}