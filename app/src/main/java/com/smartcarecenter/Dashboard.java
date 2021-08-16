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
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.menuhome.ChatAdapter;
import com.smartcarecenter.menuhome.ChatItem;
import com.smartcarecenter.menuhome.MenuAdapter;
import com.smartcarecenter.menuhome.MenuItem;
import com.smartcarecenter.messagecloud.check;
import com.smartcarecenter.supportservice.AddFormAdapter;
import com.smartcarecenter.supportservice.AddFromItem;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;
import static com.smartcarecenter.AddRequest.requestby;
import static com.smartcarecenter.AddDetailFocView.Nowfoc;
import static com.smartcarecenter.AddDetailsPoView.Nowpo;
import static com.smartcarecenter.DetailsFormActivity.Nowaform;
import static com.smartcarecenter.menuhome.MenuAdapter.mchatdialog;

public class Dashboard extends AppCompatActivity {
    public static boolean installed = true;
    public static boolean installed2 = true;
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
    public static ArrayList<ChatItem> list2;
    JsonArray listformreq;
    public static ChatAdapter addFormAdapterAdapter;
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
    LinearLayout mlayoutalert, mdot;
    ConstraintLayout mnotif_btn;
    LinearLayout morder;
    LinearLayout mreq;
    LinearLayout msetting;
    LinearLayout msupport;
    LinearLayout msurvey, mtermandcondition;
    TextView mversion, mnewnotif;
    RecyclerView mymenu;
    String sesionid_new = "";
    String notifications_new = "";
    public static String news_new = "";
    boolean notes = true;
    boolean survey = true;
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
        mdot = findViewById(R.id.dot);
        mnewnotif = findViewById(R.id.newnotif);
        mtermandcondition = findViewById(R.id.termandconditionsbtn);
        cekInternet();
        getSessionId();
        check.checknotif=1;
        check.checkhome = 0;

        if (internet){
            reqApi();
            appInstalledOrNot("com.whatsapp");
            appInstalledOrNot2("com.whatsapp.w4b");
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
        mtermandcondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotonews = new Intent(Dashboard.this, TermActivity.class);
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
    public boolean appInstalledOrNot2(String bisnis) {
        PackageManager packageManager = this.getPackageManager();
        try {
            packageManager.getPackageInfo(bisnis, packageManager.GET_ACTIVITIES);
            installed2 = true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            installed2 = false;

        }
        return installed2;
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
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONconfig(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("confignya",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")){
                    mtermandcondition.setVisibility(View.VISIBLE);
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    //cek profile
                    if (data.get("showUpdateProfile").getAsBoolean()){
                        Intent gototoprofile = new Intent(Dashboard.this,Myprofile.class);
                        gototoprofile.putExtra("notifnya","yes");
                        gototoprofile.putExtra("homes","yes");

                        startActivity(gototoprofile);
                        finish();
                    }else {

                    }
                    survey = data.get("showSurvey").getAsBoolean();
                    //Survey
                    if (survey){
                        Intent gotonews = new Intent(Dashboard.this, SurveyActivity.class);
                        startActivity(gotonews);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        finish();
                    }else {

                    }

                    // Chat List
                    listformreq = data.getAsJsonArray("liveChatHome");
                    Nowfoc = data.get("liveChatFOC").getAsString();
                    Nowpo = data.get("liveChatChargeable").getAsString();
                    Nowaform = data.get("liveChatServiceSupport").getAsString();
                    list2 = new ArrayList<ChatItem>();
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<ChatItem>>() {
                    }.getType();
                    list2 = gson.fromJson(listformreq.toString(), listType);
//                    Toast.makeText(Dashboard.this, list2.toString(), Toast.LENGTH_SHORT).show();


                    //alertnotes
                    notes = data.get("showHomeNotes").getAsBoolean();
                    notifications_new = String.valueOf(data.get("newNotification").getAsInt());
                    news_new = String.valueOf(data.get("newNews").getAsInt());
                    if (notifications_new.equals("0")){
                        mdot.setVisibility(View.GONE);
                    }else {
                        mdot.setVisibility(View.VISIBLE);
                        mnewnotif.setText(notifications_new);
                    }
                    if (notes){
                        String background = data.get("homeNotesBackgroundColor").getAsString();
                        String textcolor = data.get("homeNotesTextColor").getAsString();
                        GradientDrawable shape =  new GradientDrawable();
                        shape.setCornerRadius( 15 );
                        shape.setColor(Color.parseColor("#"+background));
                        mlayoutalert.setVisibility(View.VISIBLE);
                        mlayoutalert.setBackground(shape);
                        String text = data.get("homeNotesText").getAsString();
                        malert.setTextColor(Color.parseColor("#"+textcolor));
                        if (Build.VERSION.SDK_INT >= 24) {
                            malert.setText((CharSequence) Html.fromHtml((String)text, Html.FROM_HTML_MODE_COMPACT));
                            malert.setMovementMethod(LinkMovementMethod.getInstance());
                        } else {
                            malert.setText((CharSequence)Html.fromHtml((String)text));
                            malert.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                    }else {
                        mlayoutalert.setVisibility(View.GONE);
                    }
                    //HEADER
                    AddDetailFoc.matrixlabel = data.get("matrixLabel").getAsString() + ": ";
                    AddDetailFocView.matrixlabel = data.get("matrixLabel").getAsString() + ": ";

                    tax = data.get("taxPercentage").getAsInt();
                    taxename = data.get("taxLabel").getAsString();
                    SharedPreferences.Editor editor = getSharedPreferences("Tax", MODE_PRIVATE).edit();
                    editor.putInt("tax", tax);
                    editor.putString("taxname",taxename);
                    editor.apply();
                    mhomeName = data.get("homeName").getAsString();
                    requestby = data.get("homeName").getAsString();
                    AddDetailsPo.username = data.get("homeName").getAsString();
                    AddDetailFoc.username = data.get("homeName").getAsString();
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
                    float width3 = getResources().getDimension(R.dimen.height3);
                    float width4 = getResources().getDimension(R.dimen.height4);
                    float width5 = getResources().getDimension(R.dimen.height5);
                    float width6 = getResources().getDimension(R.dimen.height6);
                    if(mymenu.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                      if (menuItemlist.size()>4)
                      { mymenu.getLayoutParams().height = (int) width3;
                      }
                      else if( menuItemlist.size()>6) {
                          mymenu.getLayoutParams().height = (int) width4;
                        }else if (menuItemlist.size()>8) {
                          mymenu.getLayoutParams().height = (int) width5;
                      }else if( menuItemlist.size()>10) {
                          mymenu.getLayoutParams().height = (int) width6;
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
        Log.d("confignya",jsonObject.toString());
    }
    public void onBackPressed(){
            if (exit) {
                this.finish();
                check.checkhome = 1;
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