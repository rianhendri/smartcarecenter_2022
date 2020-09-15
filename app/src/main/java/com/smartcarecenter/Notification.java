package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.notification.NotificationAdapter;
import com.smartcarecenter.notification.NotificationItem;
import com.smartcarecenter.supportservice.AddFromItem;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;

public class Notification extends AppCompatActivity {
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    String id = "";
    boolean internet = true;
    private LinearLayoutManager linearLayoutManager;
    JsonArray listnotif;
    LinearLayout mback;
    private LinearLayoutManager mlinear;
    RecyclerView mlistnotif;
    TextView mnonotif;
    TextView mtitle;
    NotificationAdapter notificationAdapter;
    ArrayList<NotificationItem> notiflist;
    String sesionid_new = "";

    ProgressDialog loading;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mback = findViewById(R.id.backbtn);
        mtitle = findViewById(R.id.title);
        mlistnotif = findViewById(R.id.listnotif);
        mnonotif = findViewById(R.id.nonotif);
        linearLayoutManager = new LinearLayoutManager(Notification.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        mlistnotif.setLayoutManager(linearLayoutManager);
        mlistnotif.setHasFixedSize(true);
        notiflist = new ArrayList();
        Bundle bundle2 = this.getIntent().getExtras();
        if (bundle2 != null) {
            this.id = bundle2.getString("id");
        }
        getSessionId();
        cekInternet();
        if (internet){
            loadNotiflist();
        }else {

        }
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) Notification.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(Notification.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void loadNotiflist(){
        loading = ProgressDialog.show(Notification.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",ver);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONgetnotiflist(jsonObject);
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
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    //HEADER
                    listnotif = data.getAsJsonArray("notificationList");
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<NotificationItem>>() {
                    }.getType();
                    notiflist = gson.fromJson(listnotif.toString(), listType);
                    notificationAdapter = new NotificationAdapter(Notification.this, notiflist);
                    mlistnotif.setAdapter(notificationAdapter);
                    if (listnotif.size() == 0) {
                        mnonotif.setVisibility(View.VISIBLE);
                        mlistnotif.setVisibility(View.GONE);

                    }else {
                        mnonotif.setVisibility(View.GONE);
                        mlistnotif.setVisibility(View.VISIBLE);
                    }
                    loading.dismiss();

                }else {
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(Notification.this, errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Notification.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
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
                Intent gotoupdate = new Intent(Notification.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(Notification.this, LoginActivity.class));
            finish();
            Toast.makeText(Notification.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
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