package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.squareup.picasso.Picasso;

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

public class ReportsMenu extends AppCompatActivity {
    LinearLayout mback;
    LinearLayout mdaily;
    LinearLayout mmonth, mtrasnp;
    Boolean xdaily=false;
    Boolean xmonth = false;
    String sesionid_new = "";
    String MhaveToUpdate = "";
    boolean internet = true;
    String MsessionExpired = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_menu);
        mback = findViewById(R.id.backbtn);
        mdaily = findViewById(R.id.btnfoc);
        mmonth = findViewById(R.id.pobtn);
        mtrasnp = findViewById(R.id.transp);
//        check.checknotif=0;
        //showadd
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            xdaily = bundle2.getBoolean("daily");
//            items = bundle2.getString("items");
            xmonth = bundle2.getBoolean("month");

        }
        getSessionId();
        cekInternet();
        if (internet){
            reqApi();
        }else {

        }
//        if (!xdaily){
//            mdaily.setVisibility(View.GONE);
//            mtrasnp.setVisibility(View.VISIBLE);
//        }else if (!xmonth){
//            mmonth.setVisibility(View.GONE);
//            mtrasnp.setVisibility(View.VISIBLE);
//        }else {
//            mmonth.setVisibility(View.VISIBLE);
//            mdaily.setVisibility(View.VISIBLE);
//        }
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotopo = new Intent(ReportsMenu.this,MonthlyReport.class);
//                gotopo.putExtra("showaddpo",showaddpo);
                gotopo.putExtra("daily",xdaily);
                gotopo.putExtra("month",xmonth);
                startActivity(gotopo);
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });
        mdaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotopo = new Intent(ReportsMenu.this,DailyReportList.class);
//                gotopo.putExtra("showaddfoc",showaddfoc);
                gotopo.putExtra("daily",xdaily);
                gotopo.putExtra("month",xmonth);
                startActivity(gotopo);
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent((Context)this, Dashboard.class));

        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) ReportsMenu.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(ReportsMenu.this, NoInternet.class);
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
                Intent gotoupdate = new Intent(ReportsMenu.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(ReportsMenu.this, LoginActivity.class));
            finish();
            Toast.makeText(ReportsMenu.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

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

                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    JsonObject access = data.get("accessLevel").getAsJsonObject();

                    if (access.get("showDailyReport").getAsBoolean()){
                        xdaily=true;
                        if (access.get("showSPMonthlyUsageReport").getAsBoolean()){
                            xmonth=true;
                        }else {
                            xmonth=false;
                        }
                    }
                    if (!xdaily){
                        mdaily.setVisibility(View.GONE);
                        mtrasnp.setVisibility(View.VISIBLE);
                    }else if (!xmonth){
                        mmonth.setVisibility(View.GONE);
                        mtrasnp.setVisibility(View.VISIBLE);
                    }else {
                        mmonth.setVisibility(View.VISIBLE);
                        mdaily.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    Toast.makeText(ReportsMenu.this, errornya.toString(),Toast.LENGTH_LONG).show();
                    sesionid();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ReportsMenu.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();

            }
        });
        Log.d("confignya",jsonObject.toString());
    }

}
