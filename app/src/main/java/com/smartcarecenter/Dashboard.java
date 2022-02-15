package com.smartcarecenter;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.Chat.ItemUid;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.livechatlist.DetailsDate;
import com.smartcarecenter.livechatlist.ListLiveChatAdapter;
import com.smartcarecenter.livechatlist.ListLiveChatItem;
import com.smartcarecenter.menuhome.ChatAdapter;
import com.smartcarecenter.menuhome.ChatItem;
import com.smartcarecenter.menuhome.MenuAdapter;
import com.smartcarecenter.menuhome.MenuItem;
import com.smartcarecenter.messagecloud.check;
import com.smartcarecenter.supportservice.AddFormAdapter;
import com.smartcarecenter.supportservice.AddFromItem;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.LiveChatList.list2;
import static com.smartcarecenter.LiveChatList.list3;
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.getchatnya;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;
import static com.smartcarecenter.AddRequest.requestby;
import static com.smartcarecenter.AddDetailFocView.Nowfoc;
import static com.smartcarecenter.AddDetailsPoView.Nowpo;
import static com.smartcarecenter.DetailsFormActivity.Nowaform;
import static com.smartcarecenter.menuhome.MenuAdapter.chat;
import static com.smartcarecenter.menuhome.MenuAdapter.countDA;
import static com.smartcarecenter.menuhome.MenuAdapter.countPM;
import static com.smartcarecenter.menuhome.MenuAdapter.id;
import static com.smartcarecenter.menuhome.MenuAdapter.liveChatRepor;
import static com.smartcarecenter.menuhome.MenuAdapter.mchatdialog;
import static com.smartcarecenter.menuhome.MenuAdapter.module;
import static com.smartcarecenter.menuhome.MenuAdapter.moduletrans;
import static com.smartcarecenter.menuhome.MenuAdapter.name;
import static com.smartcarecenter.menuhome.MenuAdapter.sessionnya;
import static com.smartcarecenter.menuhome.MenuAdapter.titlenya;
import static com.smartcarecenter.menuhome.MenuAdapter.username;
import static com.smartcarecenter.menuhome.MenuAdapter.xdaily;
import static com.smartcarecenter.menuhome.MenuAdapter.xmonth;
import static com.smartcarecenter.messagecloud.check.tokennya2;
import static com.smartcarecenter.menuhome.MenuAdapter.countSC;
import static com.smartcarecenter.menuhome.MenuAdapter.counter3;
import static com.smartcarecenter.LiveChatList.itemchat;

public class Dashboard extends AppCompatActivity {
    //adapter count notif live chat
    ListLiveChatAdapter addFormAdapterAdapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    int pos1 = 0;
    Query lastQuery;
    JsonArray myCustomArray;
    JSONObject rolejson = null;
    JSONObject rolejson2 = null;
    JsonObject homedata2=null;
    JsonObject homedata3=null;
    //
    FirebaseAuth mAuth;
    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();
    ItemUid ietmuid ;
    String nme="";
    String uidnya="";
    String emainya="";
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
    public static ArrayList<ChatItem> list5;
    JsonArray listformreq;
//    public static ChatAdapter addFormAdapterAdapter;
    int tax = 0;
    JsonArray listformreq3;
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
    TextView mrunningtext;
    LinearLayout mlayoutrunning;
    String cdnews = "";
    boolean notes = true;
    boolean survey = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mrunningtext = findViewById(R.id.runningtext);
        mlayoutrunning = findViewById(R.id.layoutrunning);
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
        mlayoutrunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, DetailsNews.class);
                intent.putExtra("newscd", cdnews);
                intent.putExtra("home", "home");
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                finish();
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

                    //running news
                    if (data.get("showNewsSticker").getAsBoolean()){
                        mlayoutrunning.setVisibility(View.VISIBLE);
                        cdnews = data.get("newsStickerNewsCd").getAsString();
                        //test running text
                        mrunningtext.setSelected(true);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            mrunningtext.setText(Html.fromHtml(data.get("newsStickerRunningText").getAsString(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            mrunningtext.setText(Html.fromHtml(data.get("newsStickerRunningText").getAsString()));
                        }
                    }else {
                        mlayoutrunning.setVisibility(View.GONE);
                    }
                    emainya=data.get("liveChatUserID").getAsString();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        //User is Logged in
                        Log.d("user2","nggak kosong");

                        uidnya=user.getUid();
                        Log.d("trag2", uidnya);
                        setregistuser();
                    }else{
                        //No User is Logged in
                        reglogauth();
                    }
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
                    list5 = new ArrayList<ChatItem>();
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<ChatItem>>() {
                    }.getType();
                    list5 = gson.fromJson(listformreq.toString(), listType);
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
                    MenuItem menuItem8 = new MenuItem();
                    MenuItem menuItem9 = new MenuItem();
                    MenuItem menuItem10 = new MenuItem();
                    MenuItem menuItem11 = new MenuItem();
                    new MenuItem();
                    MenuItem menuItem7 = new MenuItem();
                    menuItemlist = new ArrayList();

                    if (mshowFormRequest.equals("true")){
                        menuItem.setMenuname(getString(R.string.title_ServiceSupport));
                        menuItem.setImg(R.drawable.req);
                        menuItem.setShow(mshowFormRequest);
                        menuItemlist.add(menuItem);
                    }
                    if (access.get("showPM").getAsBoolean()){
                        menuItem10.setMenuname(getString(R.string.title_pmticket));
                        menuItem10.setImg(R.drawable.repairtools);
                        menuItem10.setShow(access.get("showPM").toString());
                        menuItemlist.add(menuItem10);
                    }
                    if (access.get("showDailyReport").getAsBoolean()){
                        menuItem11.setMenuname("Reports");
                        menuItem11.setImg(R.drawable.folder);
                        menuItem11.setShow(access.get("showDailyReport").toString());
                        menuItemlist.add(menuItem11);
                        xdaily=true;
                        if (access.get("showSPMonthlyUsageReport").getAsBoolean()){
                            xmonth=true;
                        }else {
                            xmonth=false;
                        }
                    }else {
                        xdaily=false;
                        if (access.get("showSPMonthlyUsageReport").getAsBoolean()){
                            menuItem11.setMenuname("Reports");
                            menuItem11.setImg(R.drawable.folder);
                            menuItem11.setShow(access.get("showDailyReport").toString());
                            menuItemlist.add(menuItem11);
                            xmonth=true;
                        }else {
                            xmonth=false;
                        }
                    }
                    countPM = access.get("showPMCounter").getAsInt();
                    countDA = access.get("showDailyReportCounter").getAsInt();
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
                    if (access.get("showChatWithSupport").getAsBoolean()){
                        JsonObject chatas = data.getAsJsonObject("chatWithSupport");
                        name=chatas.get("UserName").getAsString();
                        sessionnya=chatas.get("LiveChatID").getAsString();
                        chat=chatas.get("AllowToChat").getAsBoolean();
                        titlenya=chatas.get("Title").getAsString();
                        username=chatas.get("UserName").getAsString();
                        module=chatas.get("Module").getAsString();
                        if (chatas.get("ModuleTransactionNo").getAsString().equals("")){
                            moduletrans="null";
                        }else {
                            moduletrans=chatas.get("ModuleTransactionNo").getAsString();
                        }

                         id="homes";
                        liveChatRepor=data.get("chatWithSupportReportWhenUserChat").getAsBoolean();
                        if (chatas.get("OthersFirebaseToken").toString().equals("null")){
//                            tokennya = "-";
                        }else {
                            tokennya2.clear();
                            JsonArray tokeny = chatas.getAsJsonArray("OthersFirebaseToken");
                            for (int c = 0; c < tokeny.size(); ++c) {
                                JsonObject assobj2 = tokeny.get(c).getAsJsonObject();
                                tokennya2.add(assobj2.get("Token").getAsString());
                            }

                            Log.d("listToken", tokennya2.toString());
                        }
                        menuItem9.setMenuname("Chat With Support");
                        menuItem9.setImg(R.drawable.ic_supportchat);
                        menuItem9.setShow(access.get("showChatWithSupport").toString());
                        menuItemlist.add(menuItem9);
                        loadscc();
                    }

                    if (access.get("showCurrentLiveChatList").getAsBoolean()){
                        menuItem8.setMenuname("Live Chat List");
                        menuItem8.setImg(R.drawable.ic_chat);
                        menuItem8.setShow(access.get("showCurrentLiveChatList").toString());
                        menuItemlist.add(menuItem8);
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
                    float width7 = getResources().getDimension(R.dimen.height7);
                    if(mymenu.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        Log.d("sizesa", String.valueOf(menuItemlist.size())+"4");
                      if (menuItemlist.size()<5)
                      {
                          mymenu.getLayoutParams().height = (int) width3;
                      }else {
                          if( menuItemlist.size()<7) {
                              mymenu.getLayoutParams().height = (int) width3;
                          }else {
                              if (menuItemlist.size()<9) {
                                  mymenu.getLayoutParams().height = (int) width4;
                              }else {
                                  if( menuItemlist.size()<11) {
                                      mymenu.getLayoutParams().height = (int) width5;
                                  }
                              }
                          }
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
    public void reglogauth(){
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(emainya, "x8x8x8")
                .addOnCompleteListener(Dashboard.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("trag", "signInWithCustomToken:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            uidnya=user.getUid();
                            setregistuser();

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                mAuth.signInWithEmailAndPassword(emainya, "x8x8x8")
                                        .addOnCompleteListener(Dashboard.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    // Sign in success, update UI with the signed-in user's information

                                                    FirebaseUser user = mAuth.getCurrentUser();
                                                    uidnya=user.getUid();
                                                    Log.d("trag", uidnya);
                                                    setregistuser();
//
                                                } else {
                                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
//                                                                            Toast.makeText(DetailsST.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();
                                                    }else {

                                                    }
                                                    // If sign in fails, display a message to the user.
                                                    Log.w("uii", "signInWithCustomToken:failure", task.getException());
//                                    Toast.makeText(CustomAuthActivity.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);

                                                }
                                            }
                                        });
//                                                    Toast.makeText(DetailsST.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();
//                                            loading.dismiss();
                            }else {
//                                            loading.dismiss();
                            }
//                                        loading.dismiss();
                            // If sign in fails, display a message to the user.
                            Log.w("uii", "signInWithCustomToken:failure", task.getException());
//                                    Toast.makeText(CustomAuthActivity.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);

                        }
                    }
                }).addOnFailureListener(Dashboard.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                            loading.dismiss();
                Log.d("gagal login",e.toString());
            }
        });
    }
    public void setregistuser(){
//        int posinya = 0;
//        if ((addFoclistreq!=null)){
//            posinya = 0;
//        }else{
//            posinya = addFoclistreq.size()+1;
//        }
        ietmuid= new ItemUid();

        ietmuid.setEmail(emainya);
        ietmuid.setUsername(nme);

        databaseReference2.child("akunregist").child(uidnya).setValue(ietmuid).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Log.d("failue","succes");


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("failue",e.toString());
            }
        });
    }
    //load badge notif live chat
    public void  loadscc(){
        countSC=0;
        counter3=0;
        list2 = new ArrayList<ListLiveChatItem>();
//        itemchat.clear();
        list2.clear();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId","");
//        Toast.makeText(DetailsFormActivity.this,jsonObject.toString(), Toast.LENGTH_SHORT).show();
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, getchatnya);
        Call<JsonObject> panggilkomplek = jsonPostService.getjsonchat();
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                homedata2=response.body();
                JsonObject data = homedata2.getAsJsonObject(sessionnya);
                String nameme = username;
                try {
                        if(data!=null){
                            rolejson = new JSONObject(data.get("listchat").toString());
                        }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                    if (data!=null){
                        for(int i = 0; i<rolejson.names().length(); i++){
                            try {
                                if (rolejson.getJSONObject(rolejson.names().getString(i)).getString("name").equals(nameme)){

                                }else {
                                    if (rolejson.getJSONObject(rolejson.names().getString(i)).getString("read").equals("no")){
                                        countSC +=1;
                                        mymenu.setAdapter(addmenu);
                                    }
                                    if (rolejson.names().length()==i+1){

                                    }
                                }
//                        Log.d("TestJson",rolejson.names().getJSONObject(i).getString("message"));
                                Log.d("TAGet", "key = " + rolejson.names().getString(i) + " value = " + rolejson.getJSONObject(rolejson.names().getString(i)).getString("message"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("TAGet", e.toString());
                            }
                        }
                    }




                loadDataeng();
//                Log.d("jsonchatt",outputJsonArray.toString());

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(DetailsST.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                            loading.dismiss();

            }
        });
        Log.d("loadDetailst",jsonObject.toString());


    }
    public void loadDataeng(){
        itemchat.clear();
        list3 = new ArrayList<ListLiveChatItem>();
        list3.clear();


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.livechastlist(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                Log.d("zasazz",homedata.toString());
                String statusnya = homedata.get("status").getAsString();
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                if (statusnya.equals("OK")){
                    JsonObject data = homedata.getAsJsonObject("data");

//                    totalpage = 0;
                    listformreq3 = data.getAsJsonArray("chatList");
//                    totalrec = "0";
//                    mrecord.setText("Record: "+totalrec);

                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<ListLiveChatItem>>() {
                    }.getType();
                    list3 = gson.fromJson(listformreq3.toString(), listType);
//                    addFormAdapterAdapter = new ListLiveChatAdapter(Home.this, list3);
//                    addFormAdapterAdapter.notifyDataSetChanged();
//                    homedata3=response.body();

                    if (list3!=null){
                        for(int x = 0; x<list3.size(); x++){
                            JsonObject data2 = homedata2.getAsJsonObject(list3.get(x).getLiveChatID());
                            String nameme = list3.get(x).getUserName();

                            try {
                                rolejson2 = new JSONObject(data2.get("listchat").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

//                            try {
//                                rolejson = new JSONObject(data.get("listchat").toString());
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
                            for(int i = 0; i<rolejson2.names().length(); i++){
                                try {


                                    if (rolejson2.getJSONObject(rolejson2.names().getString(i)).getString("name").equals(nameme)){
                                    }else {
                                        if (rolejson2.getJSONObject(rolejson2.names().getString(i)).getString("read").equals("no")){
                                            counter3 +=1;
                                            mymenu.setAdapter(addmenu);
                                        }
                                    }
//                        Log.d("TestJson",rolejson.names().getJSONObject(i).getString("message"));
//                                    Log.d("TAGet", "key = " + rolejson.names().getString(i) + " value = " + rolejson.getJSONObject(rolejson.names().getString(i)).getString("message"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d("TAGet", e.toString());
                                }
                            }
                        }
                    }

                }else {
                    sesionid();
//                    mfooterload.setVisibility(View.GONE);
                    if (MsessionExpired.equals("true")) {
                        Toast.makeText(Dashboard.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Dashboard.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                mfooterload.setVisibility(View.GONE);

            }
        });
        Log.d("livechatlistreq",jsonObject.toString());
    }
}