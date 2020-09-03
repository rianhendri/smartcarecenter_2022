package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsNotification extends AppCompatActivity {
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    String id = "";
    boolean internet = true;
    private LinearLayoutManager linearLayoutManager;
    ImageView mback;
    TextView mcontent;
    private LinearLayoutManager mlinear;
    RecyclerView mlistnotif;
    TextView mtitle;
    String sesionid_new = "";
    String username = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_notification);
        mback = (ImageView)this.findViewById(R.id.backbtn);
        mtitle = (TextView)this.findViewById(R.id.title);
        mcontent = (TextView)this.findViewById(R.id.content);
        Bundle bundle2 = this.getIntent().getExtras();
        if (bundle2 != null) {
            id = bundle2.getString("id");
            username = bundle2.getString("username");
        }
        getSessionId();
        cekInternet();
        if (internet){
            loadNotif();
        }
        else {

        }
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.length()==0){
                    startActivity(new Intent(DetailsNotification.this, Dashboard.class));
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    finish();
                }else {
                    onBackPressed();

                }
            }
        });
    }
    public void loadNotif(){
        cekInternet();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("guid", id);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, "http://api.smartcarecenter.id/");
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONgetnotifget(jsonObject);
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
                    //HEADER
                    String string3 = data.get("title").getAsString();
                    String string4 = data.get("message").getAsString();
                    mtitle.setText((CharSequence)string3);
                    mcontent.setText((CharSequence)string4);

                }else {
                    sesionid();

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsNotification.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();


            }
        });
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) DetailsNotification.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(DetailsNotification.this, NoInternet.class);
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
                Intent gotoupdate = new Intent(DetailsNotification.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(DetailsNotification.this, LoginActivity.class));
            finish();
            Toast.makeText(DetailsNotification.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (username==null){
            super.onBackPressed();
        }else {
            startActivity(new Intent((Context)this, Notification.class));
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
        }

    }
}