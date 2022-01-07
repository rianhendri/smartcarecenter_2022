package com.smartcarecenter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.Add_Foc_Request_view.Add_foc_req_adapterView;
import com.smartcarecenter.Add_Foc_Request_view.Add_foc_req_itemView;
import com.smartcarecenter.Chat.Adapterchat;
import com.smartcarecenter.Chat.Itemchat;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.messagecloud.check;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.smartcarecenter.Dashboard.installed2;
import static com.smartcarecenter.FormActivity.refresh;
import static com.smartcarecenter.FormActivity.valuefilter;
import static com.smartcarecenter.FreeofchargeActivity.list2;
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.messagecloud.check.tokennya2;

public class DetailsOrderFoc extends AppCompatActivity {
    FirebaseAuth mAuth;
    LinearLayout mdot;
    TextView mnotif;
    int total1 = 0;
    public static String name="";
    LinearLayout mchactclik;
    String tokennya = "-";
    ArrayList<Itemchat> itemchat;
    Itemchat itemchat2;
    DatabaseReference databaseReference5;
    Adapterchat adapterchat;
    boolean showprep = true;
    String colortextrep = "";
    String textprep="";
    String bgprep = "";
    public static JsonArray listsn;
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    public static String matrixlabel = "";
    String akunid = "";
    Boolean internet = false;
    boolean installed= true;
    ProgressDialog loading;
    LinearLayout mback, mbgalert, mlayoutphotoimpressi;
    public static LinearLayout mlaytotal;
    public static TextView mdate,mstartimpresi,moperator,mno_order,mtotalitem,msend,mtotalqty,
            mnoitem,mlastimpresi,mstatus, mtilte,mnotes, mtotalapproved;
    String mpressId = "";
    String mpressId2 = "";
    Integer previmpressvlaue = 100;
    LinearLayout madd_item,mchat;
    TextView msn, mtextalert,mdescrip;
    DatabaseReference reference;
    public static RecyclerView mlistitem_foc;
    String sesionid_new = "";
    List<String> snid = new ArrayList();
    List<String> snname = new ArrayList();
    List<Integer> previmpression = new ArrayList();
    //list item add
    public static ArrayList<Add_foc_req_itemView> reitem;
    Add_foc_req_adapterView req_adapter;
    private LinearLayoutManager linearLayoutManager;
    String jsonarayitem = "";
    JsonArray myCustomArray;
    String noOrder="";
    String pressid= "";
    Gson gson;
    ImageView mimgbanner,mimgpopup;
    public static String username = "";
    String guid = "";
    public static String Nowfoc = "0";
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_order);
        mlistitem_foc = findViewById(R.id.listitemfoc);
        mdate = findViewById(R.id.datefoc);
        mchactclik = findViewById(R.id.chatclik);
        mdot = findViewById(R.id.dot);
        mnotif = findViewById(R.id.newnotif);
        mno_order = findViewById(R.id.noorder);
        mnoitem = findViewById(R.id.noitem);
        msend = findViewById(R.id.submit);
        msn = findViewById(R.id.sn);
        moperator = findViewById(R.id.operator);
        mlastimpresi = findViewById(R.id.lastimprsi);
        mstartimpresi = findViewById(R.id.startimpresi);
        mback = findViewById(R.id.backbtn);
        mtotalitem = findViewById(R.id.totalitemfoc);
        mtotalqty = findViewById(R.id.totalqtyfoc);
        mlaytotal = findViewById(R.id.totallay);
        madd_item = findViewById(R.id.btnadditem_po);
        mstatus = findViewById(R.id.status);
        mchat = findViewById(R.id.chatcspo);
        mtilte = findViewById(R.id.title);
        mnotes = findViewById(R.id.mnotes);
        mtotalapproved = findViewById(R.id.totalaproved);
        mbgalert = findViewById(R.id.backgroundalert);
        mtextalert = findViewById(R.id.textalert);
        mimgbanner = findViewById(R.id.imgbanner);
        mlayoutphotoimpressi = findViewById(R.id.layoutphotoimpressi);
        mdescrip = findViewById(R.id.descrip);
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            noOrder = bundle2.getString("id");
            guid = bundle2.getString("guid");
            valuefilter = bundle2.getString("pos");
            username = bundle2.getString("username");
        }
        mtilte.setText("View FOC #"+noOrder);
        cekInternet();
        getSessionId();
        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(DetailsOrderFoc.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        mlistitem_foc.setLayoutManager(linearLayoutManager);
        mlistitem_foc.setHasFixedSize(true);
        reitem = new ArrayList<Add_foc_req_itemView>();
        if (internet){
//            LoadPress();
            LoadData();
            prepform();
            if (guid==null){

            }else {
                ReadNotif();
            }
        }else {

        }


        msend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekInternet();
                if (internet){
                    showDialog();
                }else {

                }
            }
        });
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appInstalledOrNot("com.whatsapp");
                appInstalledOrNot2("com.whatsapp.w4b");
                Log.d("whats",String.valueOf(installed)+"/"+String.valueOf(installed2));
                if (installed) {
                    String message = "Hi Support, "+getString(R.string.title_tanyafoc)+noOrder;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(android.net.Uri.parse(
                            String.format("https://api.whatsapp.com/send?phone=%s&text=%s", Nowfoc, message)));
                    startActivity(intent);
                }else {
                    if (installed2){
                        String message = "Hi Support, "+getString(R.string.title_tanyafoc)+noOrder;
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(android.net.Uri.parse(
                                String.format("https://api.whatsapp.com/send?phone=%s&text=%s", Nowfoc, message)));
                        startActivity(intent);
                    }else {
                        Toast.makeText(DetailsOrderFoc.this,"Whatsapp blum di instal", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) DetailsOrderFoc.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(DetailsOrderFoc.this, NoInternet.class);
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
                Intent gotoupdate = new Intent(DetailsOrderFoc.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(DetailsOrderFoc.this, LoginActivity.class));
            finish();
            Toast.makeText(DetailsOrderFoc.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    public void LoadData(){
        gson = new Gson();
        loading = ProgressDialog.show(DetailsOrderFoc.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("orderNo",noOrder);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.vieworderfoc(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loading.dismiss();
                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                if (statusnya.equals("OK")) {
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    //chat baru pasang
                    if(data.get("liveChatShowButton").getAsBoolean()){

                        itemchat = new ArrayList<Itemchat>();
                        itemchat2 = new Itemchat();
                        databaseReference5= FirebaseDatabase.getInstance().getReference().child("chat").child(data.get("liveChatID").getAsString()).child("listchat");
                        //
                        name=data.get("liveChatUserName").getAsString();
                        mchactclik.setVisibility(View.VISIBLE);
                        if (data.get("liveChatOthersFirebaseToken").toString().equals("null")){
                            tokennya = "-";
                        }else {
                            tokennya2.clear();
                            JsonArray tokeny = data.getAsJsonArray("liveChatOthersFirebaseToken");
                            for (int c = 0; c < tokeny.size(); ++c) {
                                JsonObject assobj2 = tokeny.get(c).getAsJsonObject();
                                tokennya2.add(assobj2.get("Token").getAsString());
                            }

                            Log.d("listToken", tokennya2.toString());
                        }
                        databaseReference5.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemchat.clear();
                                total1 = 0;
                                if (dataSnapshot.exists()){
                                    for(DataSnapshot ds: dataSnapshot.getChildren())
                                    {
                                        Itemchat fetchDatalist=ds.getValue(Itemchat.class);
                                        fetchDatalist.setKey(ds.getKey());
                                        itemchat.add(fetchDatalist);
                                    }

                                    adapterchat=new Adapterchat(DetailsOrderFoc.this, itemchat);
                                    for (int i = 0; i < itemchat.size(); i++) {
                                        if (itemchat.get(i).getName().equals(name)){
                                            mdot.setVisibility(View.GONE);
                                        }else {

                                            if (itemchat.get(i).getRead().equals("yes")){
                                                mdot.setVisibility(View.GONE);
                                            }else {
                                                total1 +=1;
                                                mdot.setVisibility(View.VISIBLE);
                                                mnotif.setText(String.valueOf(total1));
                                            }
                                        }
                                    }

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

//                        engas = "";
                    }else {
                        mchactclik.setVisibility(View.GONE);

                    }
                    mchactclik.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//                            loading.show();
//                            Window window = loading.getWindow();
//                            window.setLayout(300, 300);
                            Intent gotonews = new Intent(DetailsOrderFoc.this, ListChat.class);
                            gotonews.putExtra("name",data.get("liveChatUserName").getAsString());
                            gotonews.putExtra("sessionnya",data.get("liveChatID").getAsString());
                            gotonews.putExtra("chat",data.get("liveChatAllowToChat").getAsBoolean());
                            gotonews.putExtra("titlenya",data.get("liveChatTitle").getAsString());
                            gotonews.putExtra("user",username);
                            gotonews.putExtra("id",data.get("liveChatTitle").getAsString());
                            gotonews.putExtra("moduletrans", "kosong");
                            gotonews.putExtra("ping",2);
                            gotonews.putExtra("liveChatRepor",data.get("liveChatReportWhenUserChat").getAsBoolean());
                            gotonews.putExtra("page","detailst");
//                            gotonews.putExtra("tokennya",tokennya);
//                            gotonews.putExtra("engname", mcustname);
//                            gotonews.putExtra("nofr", mformRequestCd);
                            startActivity(gotonews);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            finish();
                            /////
                            Log.d("pingtrue",String.valueOf(data.get("liveChatReportWhenUserChat").getAsBoolean()));

                        }
                    });

                    mdescrip.setText(data.get("custNotes").getAsString());
                    if (data.get("photoURL").toString().equals("null")){
                        mlayoutphotoimpressi.setVisibility(GONE);
                    }else {
                        mlayoutphotoimpressi.setVisibility(VISIBLE);
                        String photourl = data.get("photoURL").getAsString();
                        Picasso.with(DetailsOrderFoc.this).load(photourl+"?w=100").into(mimgbanner);
                        mimgbanner.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Dialog dialog = new Dialog(DetailsOrderFoc.this, R.style.TransparentDialog);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.setContentView(R.layout.popupfoto);
                                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                mimgpopup = dialog.findViewById(R.id.imagepopup);
                                Picasso.with(DetailsOrderFoc.this).load(photourl).into(mimgpopup);
                                dialog.show();
                            }
                        });
                    }

                    pressid = data.get("pressGuid").getAsString();
                    String pressname = data.get("pressTypeName").getAsString();
                    //setNotes
                    String note = data.get("notes").getAsString();
                    String showalert = data.get("showMessage").toString();
                    if (showalert.equals("true")){

                        String text = data.get("messageText").getAsString();
                        String textcolor = data.get("messageTextColor").getAsString();
                        String bgcolor = data.get("messageBackgroundColor").getAsString();

                        GradientDrawable shape =  new GradientDrawable();
                        shape.setCornerRadius( 15 );
                        shape.setColor(Color.parseColor("#"+bgcolor));

                        mbgalert.setVisibility(View.VISIBLE);
                        mtextalert.setTextColor(Color.parseColor("#"+textcolor));
                        mbgalert.setBackground(shape);
                        if (Build.VERSION.SDK_INT >= 24) {
                            mtextalert.setText((CharSequence) Html.fromHtml((String)text, Html.FROM_HTML_MODE_COMPACT));
                            mtextalert.setMovementMethod(LinkMovementMethod.getInstance());
                        } else {
                            mtextalert.setText((CharSequence)Html.fromHtml((String)text));
                            mtextalert.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                    }else {
                        mbgalert.setVisibility(View.GONE);
                    }
                    mnotes.setText(note);
//                    if (Build.VERSION.SDK_INT >= 24) {
//                        mnotes.setText((CharSequence) Html.fromHtml((String)note+"<font color=red>", Html.FROM_HTML_MODE_COMPACT));
//                    } else {
//                        mnotes.setText((CharSequence)Html.fromHtml((String)note+"<font color=red>"));
//                    }
                    msn.setText(pressname);
                    String orderno = data.get("orderNo").getAsString();
                    String date = data.get("createdDateTime").getAsString();
                    String pressstart = data.get("previousImpression").getAsString();
                    String presimpres = data.get("presentImpression").getAsString();
                    String status = data.get("statusName").getAsString();
                    String statuscolor = data.get("statusColorCode").getAsString();
                    String operator = data.get("username").getAsString();
                    String cancelshow = data.get("allowToCancel").toString();
                    if (cancelshow.equals("true")){
                        msend.setVisibility(View.VISIBLE);
                        mchat.setVisibility(View.GONE);

                    }else {
                        msend.setVisibility(View.GONE);
                        mchat.setVisibility(View.GONE);
                    }
                    mstartimpresi.setText(pressstart);
                    mno_order.setText(orderno);
                    mlastimpresi.setText(presimpres);
                    mstatus.setText(status);
                    mstatus.setTextColor(Color.parseColor("#"+statuscolor));
                    String newdate = "";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    try {
                        newdate = simpleDateFormat.format(simpleDateFormat.parse(date));
                        System.out.println(newdate);
                        Log.e((String)"Date", (String)newdate);
                    }
                    catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    String[] separated = newdate.split("T");
                    separated[0].trim();; // this will contain "Fruit"
                    separated[1].trim();;
                    mdate.setText(separated[0]+" "+ separated[1]);
                    moperator.setText(operator);
                    myCustomArray = data.getAsJsonArray("itemList");
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Add_foc_req_itemView>>() {
                    }.getType();
                    reitem = gson.fromJson(myCustomArray.toString(), listType);
                    req_adapter = new Add_foc_req_adapterView(DetailsOrderFoc.this, reitem);
                    mlistitem_foc.setAdapter(req_adapter);
                    mlistitem_foc.setVisibility(View.VISIBLE);
                }else {
                    sesionid();
                    cekInternet();
                    Toast.makeText(DetailsOrderFoc.this, errornya.toString(),Toast.LENGTH_LONG).show();
                    loading.dismiss();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(DetailsOrderFoc.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();


            }
        });
        Log.d("focdetail",jsonObject.toString());
    }
    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle(getString(R.string.title_cancelordertitledialog));

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage(getString(R.string.title_areyoucancelorder))
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.title_yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        Cancel();
                    }
                })
                .setNegativeButton(getString(R.string.title_no),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }
    public void Cancel(){
        gson = new Gson();
//        loading = ProgressDialog.show(AddDetailFocView.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("orderNo",noOrder);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.cancelfoc(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loading.dismiss();
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                String errornya = homedata.get("errorMessage").toString();
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                if (statusnya.equals("OK")) {
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    Toast.makeText(DetailsOrderFoc.this, getString(R.string.title_cancelorder),Toast.LENGTH_LONG).show();
                    onBackPressed();

                }else {
                    Toast.makeText(DetailsOrderFoc.this, errornya.toString(),Toast.LENGTH_LONG).show();
                    loading.dismiss();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(DetailsOrderFoc.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();


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
    public boolean appInstalledOrNot2(String string2) {
        PackageManager packageManager = this.getPackageManager();

        try {
            packageManager.getPackageInfo(string2, packageManager.GET_ACTIVITIES);
            installed2 = true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            installed2 = false;

        }
        return installed2;
    }
    public void prepform(){
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("sessionId",sesionid_new);
//        jsonObject.addProperty("ver",ver);
//        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
//        Call<JsonObject> panggilkomplek = jsonPostService.prepfoc(jsonObject);
//        panggilkomplek.enqueue(new Callback<JsonObject>() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//
//                String errornya = "";
//                JsonObject homedata=response.body();
//                String statusnya = homedata.get("status").getAsString();
//                if (homedata.get("errorMessage").toString().equals("null")) {
//
//                }else {
//                    errornya = homedata.get("errorMessage").getAsString();
//                }
//                MhaveToUpdate = homedata.get("haveToUpdate").toString();
//                MsessionExpired = homedata.get("sessionExpired").toString();
//                if (statusnya.equals("OK")) {
//                    JsonObject data = homedata.getAsJsonObject("data");
//                    showprep = data.get("showMessage").getAsBoolean();
//
//                    if (showprep){
//                        colortextrep = data.get("messageTextColor").getAsString();
//                        textprep=data.get("messageText").getAsString();
//                        bgprep = data.get("messageBackgroundColor").getAsString();
//                        mbgalert.setVisibility(VISIBLE);
////                        mtextalert.setText(textprep);
//                        mtextalert.setTextColor(Color.parseColor("#"+colortextrep));
//                        if (Build.VERSION.SDK_INT >= 24) {
//                            mtextalert.setText((CharSequence) Html.fromHtml((String)textprep, Html.FROM_HTML_MODE_COMPACT));
//                            mtextalert.setMovementMethod(LinkMovementMethod.getInstance());
//                        } else {
//                            mtextalert.setText((CharSequence)Html.fromHtml((String)textprep));
//                            mtextalert.setMovementMethod(LinkMovementMethod.getInstance());
//                        }
//                        GradientDrawable shape =  new GradientDrawable();
//                        shape.setCornerRadius( 15 );
//                        shape.setColor(Color.parseColor("#"+bgprep));
//                        mbgalert.setBackground(shape);
//                    }else {
//                        mbgalert.setVisibility(GONE);
//                    }
//
//                }else {
//                    loading.dismiss();
//                    Toast.makeText(AddDetailFocView.this, errornya,Toast.LENGTH_LONG).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                loading.dismiss();
//                Toast.makeText(AddDetailFocView.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
//                cekInternet();
//
//
//            }
//        });
    }
    public void onBackPressed() {
//        super.onBackPressed();
        super.onBackPressed();
//            refresh=true;
        Intent back = new Intent(DetailsOrderFoc.this,ListChat.class);
        back.putExtra("pos",valuefilter);
        startActivity(back);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();


    }
    public void ReadNotif(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("guid",guid);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.Read(jsonObject);
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
//                    String message = data.get("message").getAsString();
//                    Toast.makeText(DetailsFormActivity.this, message,Toast.LENGTH_LONG).show();

                }else {
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(DetailsOrderFoc.this,errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsOrderFoc.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
    }
}