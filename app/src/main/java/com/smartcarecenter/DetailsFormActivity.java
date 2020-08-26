package com.smartcarecenter;

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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.smartcarecenter.serviceticket.ServiceTicketAdapter;
import com.smartcarecenter.serviceticket.ServicesTicketItem;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.FormActivity.valuefilter;

public class DetailsFormActivity extends AppCompatActivity {
    public static String noreq = "";
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    boolean internet = true;
    private LinearLayoutManager linearLayoutManager;
    ArrayList<ServicesTicketItem> listticket;
    ServiceTicketAdapter ticketadapter;
    ProgressDialog loading;
    String mallowToCancel = "";
    String mallowtoconfirm = "";
    ImageView mback,mbanner;
    LinearLayout mcancel, mconfirm, mcs;
    TextView mcreatedate, mdate, mdeskription, missu, moperator, mreqno, mservicetype, msn, mstatusdetail,
            mstid, mtitle, munitcategory;
    String mdateapi = "";
    String mdeskriptionapi = "";
    String mformRequestCd = "";
    ImageView mimgpopup;
    LinearLayout mlayoutticket,mlayoutunit1, mlayoutunit2, mlayoutunit3;
    private LinearLayoutManager mlinear;
    String mphotoURL = "";
    String mpressGuid = "";
    String mpressName = "";
    String mrequestedBy = "";
    String mrequestedDateTime = "";
    String mserviceTicketCd = "";
    JsonArray mserviceTicketHistory;
    RecyclerView mservice_layout;
    String mstatus = "";
    String mstatusColorCode = "";
    String mstatusName = "";
    String noticket = "";
    String sesionid_new = "";
    boolean installed= true;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_form);
        missu = findViewById(R.id.issucategroy);
        mservicetype = findViewById(R.id.servicetype);
        mcreatedate =findViewById(R.id.createdate);
        mdate=findViewById(R.id.datereq);
        mdeskription=findViewById(R.id.descrip);
        moperator=findViewById(R.id.operator);
        mreqno=findViewById(R.id.request_no);
        msn=findViewById(R.id.sn);
        mstatusdetail=findViewById(R.id.statusdetail);
        mstid=findViewById(R.id.stid);
        mtitle=findViewById(R.id.title);
        munitcategory=findViewById(R.id.unitcategory);
        mcancel=findViewById(R.id.laycancel);
        mconfirm=findViewById(R.id.confirm);
        mcs=findViewById(R.id.chatcspo);
        mback=findViewById(R.id.backbtn);
        mbanner=findViewById(R.id.imgbanner);
        mlayoutunit1=findViewById(R.id.layoutunit1);
        mlayoutunit2=findViewById(R.id.layoutunit2);
        mlayoutunit3=findViewById(R.id.layoutunit3);
        mlayoutticket=findViewById(R.id.layoutticket);
        mservice_layout=findViewById(R.id.serviceticket);

        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(DetailsFormActivity.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        mservice_layout.setLayoutManager(linearLayoutManager);
        mservice_layout.setHasFixedSize(true);
        listticket = new ArrayList();

        //getsessionId
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            noreq = bundle2.getString("id");
            noticket = bundle2.getString("noticket");
            valuefilter = bundle2.getString("pos");
        }
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
        mcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            appInstalledOrNot("com.whatsapp");
                if (installed) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("http://api.whatsapp.com/send?phone=+62822 9868 0099&text=Hi Support,  ");
                    stringBuilder.append(getString(R.string.title_tanyacs));
                    stringBuilder.append("#");
                    stringBuilder.append(noreq);
                    intent.setData(android.net.Uri.parse((String)stringBuilder.toString()));
                    startActivity(intent);
                }else {
                    Toast.makeText(DetailsFormActivity.this,"Whatsapp blum di instal", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekInternet();
                if (internet){
                    showDialog();
                }else {
                }
            }
        });
        mconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekInternet();
                if (internet){
                    Intent gotorating = new Intent(DetailsFormActivity.this, RatingStar.class);
                    gotorating.putExtra("id", noreq);
                    gotorating.putExtra("noticket", noticket);
                    startActivity(gotorating);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }else {

                }
            }
        });
        mbanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(DetailsFormActivity.this, R.style.TransparentDialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.popupfoto);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                mimgpopup = dialog.findViewById(R.id.imagepopup);
                Picasso.with(DetailsFormActivity.this).load(mphotoURL).into(mimgpopup);
                dialog.show();
            }
        });
    }
    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle(getString(R.string.title_deleteReq));

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage(getString(R.string.title_canelreq))
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.title_yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        cancelreq();
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
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) DetailsFormActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(DetailsFormActivity.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void loadData(){
        loading = ProgressDialog.show(DetailsFormActivity.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("formRequestCd",noreq);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, "http://api.smartcarecenter.id/");
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONgetform(jsonObject);
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
                    mformRequestCd = data.get("formRequestCd").getAsString();
                    mserviceTicketCd = data.get("serviceTicketCd").toString();
                    mdateapi = data.get("date").getAsString();
                    mpressGuid = data.get("pressGuid").getAsString();
                    mpressName = data.get("pressName").getAsString();
                    mphotoURL = data.get("photoURL").getAsString();
                    mstatus = data.get("status").getAsString();
                    mstatusName = data.get("statusName").getAsString();
                    mstatusColorCode = data.get("statusColorCode").getAsString();
                    mdeskriptionapi = data.get("description").getAsString();
                    mrequestedBy = data.get("requestedBy").getAsString();
                    mrequestedDateTime = data.get("requestedDateTime").getAsString();
                    mallowToCancel = data.get("allowToCancel").toString();
                    mallowtoconfirm = data.get("allowToConfirm").toString();
                    if (mserviceTicketCd.equals("null")){
                        mlayoutticket.setVisibility(View.GONE);
                    }else {
                        mlayoutticket.setVisibility(View.VISIBLE);
                        mserviceTicketHistory = data.getAsJsonArray("serviceTicketHistory");
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<ServicesTicketItem>>(){}.getType();
                        listticket = gson.fromJson(mserviceTicketHistory.toString(), type);
                        ticketadapter = new ServiceTicketAdapter(DetailsFormActivity.this,listticket);
                        mservice_layout.setAdapter(ticketadapter);
                        mservice_layout.setVisibility(View.VISIBLE);
                        for (int i = 0; i < mserviceTicketHistory.size(); ++i) {
                            String string6 = (mserviceTicketHistory.get(0)).getAsJsonObject().get("ServiceTicketCd").getAsString();
                            mstid.setText(string6);
                        }
                        String string7 = data.get("serviceTicketCreated").getAsString();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.getDefault());
                        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("d-MM-yyyy", Locale.getDefault());
                        String string5 = null;
                        try {
                            string5 = simpleDateFormat2.format(simpleDateFormat.parse(string7));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        mcreatedate.setText(string5);
                    }
                    if (data.get("unitCategoryName") == null) {
                        mlayoutunit2.setVisibility(View.GONE);
                    } else {
                       munitcategory.setText(data.get("unitCategoryName").getAsString());
                       mlayoutunit2.setVisibility(View.VISIBLE);
                    }
                    if (data.get("issueCategoryName") == null) {
                       mlayoutunit3.setVisibility(View.GONE);
                    } else {
                       missu.setText(data.get("issueCategoryName").getAsString());
                       mlayoutunit3.setVisibility(View.VISIBLE);
                    }
                    if (data.get("serviceTypeName").toString().equals("null")) {
                       mlayoutunit1.setVisibility(View.GONE);
                    } else {
                        mservicetype.setText(data.get("serviceTypeName").getAsString());
                        mlayoutunit1.setVisibility(View.VISIBLE);
                    }
                    if (mallowtoconfirm.equals("true")) {
                        mconfirm.setVisibility(View.VISIBLE);
                    } else {
                        mconfirm.setVisibility(View.GONE);
                    }
                    if (mallowToCancel.equals("true")) {
                       mcancel.setVisibility(View.VISIBLE);
                    } else {
                        mcancel.setVisibility(View.GONE);
                    }
                    loading.dismiss();
                    ////set
                    Picasso.with(DetailsFormActivity.this).load(mphotoURL).into(mbanner);
                    mtitle.setText("#"+mformRequestCd);
                    mreqno.setText(mformRequestCd);
                    String datenew = "";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.getDefault());
                    SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("d-MM-yyyy", Locale.getDefault());
                    try {
                        datenew = simpleDateFormat3.format(simpleDateFormat.parse(mdateapi));
                        System.out.println(datenew);
                        Log.e((String)"Date",datenew);
                    }
                    catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    mdate.setText((CharSequence)datenew);
                    msn.setText(mpressName);
                    moperator.setText(mrequestedBy);
                    mdeskription.setText(mdeskriptionapi);
                    mstatusdetail.setText(mstatusName);
                    mstatusdetail.setTextColor(Color.parseColor("#"+mstatusColorCode));
                }else {
                    sesionid();
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsFormActivity.this, t.toString(),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
    }
    public void cancelreq(){
        loading = ProgressDialog.show(DetailsFormActivity.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("formRequestCd",noreq);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, "http://api.smartcarecenter.id/");
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONcancelform(jsonObject);
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
                    String message = data.get("message").getAsString();
                    Toast.makeText(DetailsFormActivity.this, message,Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else {
                    sesionid();
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsFormActivity.this, t.toString(),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");

    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
                Intent gotoupdate = new Intent(DetailsFormActivity.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(DetailsFormActivity.this, LoginActivity.class));
            finish();
            Toast.makeText(DetailsFormActivity.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(DetailsFormActivity.this,FormActivity.class);
        back.putExtra("pos",valuefilter);
        startActivity(back);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
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
}