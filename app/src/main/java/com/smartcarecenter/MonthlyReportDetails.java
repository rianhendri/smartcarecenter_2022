package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.detailsdailyreport.DetailsDailyAdapter4;
import com.smartcarecenter.detailsdailyreport.DetailsDailyItem1;
import com.smartcarecenter.detailsdailyreport.DetailsDailyItem2;
import com.smartcarecenter.detailsdailyreport.DetailsDailyItem3;
import com.smartcarecenter.detailsdailyreport.DetailsDailyItem4;
import com.smartcarecenter.detailsdailyreport.DetailsDailyItem5;
import com.smartcarecenter.pressreport.PressAdapter;
import com.smartcarecenter.pressreport.PressList;
import com.smartcarecenter.reportsmenu.ReportAdapter;
import com.smartcarecenter.reportsmenu.ReportsItem;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;

public class MonthlyReportDetails extends AppCompatActivity {
    Boolean xdaily=false;
    Boolean xmonth = false;
    String period = "";
    String guid = "";
    LinearLayout mviewreport,mcalender,mlayoutgrandtotal;
    TextView mdatenya;
    String sesionid_new = "";
    String MhaveToUpdate = "";
    boolean internet = true;
    String MsessionExpired = "";
    TextView mcustomera,mpresstypea,mpressnamea,mconsumi;
    JsonArray listformreq,listformreq2,listformreq3,listformreq4,listformreq5;
    RecyclerView myitem_place1,myitem_place2,myitem_place3,myitem_place4,myitem_place5;
    public static ArrayList<ReportsItem> list4;
    ReportAdapter addFormAdapterAdapter4;
    public static TextView mgrandtotal7,mtanpasper;
    private LinearLayoutManager linearLayoutManager,linearLayoutManager2,linearLayoutManager3,linearLayoutManager4,linearLayoutManager5;
    String consum = "";
    int pos = 0;
    int pos2 = 0;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_report_details);
        mlayoutgrandtotal = findViewById(R.id.layoutgrandtotal);
        mgrandtotal7 = (TextView) findViewById(R.id.grandtotal7);
        mtanpasper = findViewById(R.id.tanpasper);
        mcustomera = findViewById(R.id.customera);
        mpresstypea = findViewById(R.id.presstypea);
        mpressnamea = findViewById(R.id.pressnamea);
        myitem_place4 = findViewById(R.id.sperpartdaily);
        mconsumi = findViewById(R.id.consumi);
        getSessionId();
        cekInternet();
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            xdaily = bundle2.getBoolean("daily");
            xmonth = bundle2.getBoolean("month");
            period = bundle2.getString("date");
            guid = bundle2.getString("guid");
            pos = bundle2.getInt("posi");
            pos2 = bundle2.getInt("posi2");
            consum = bundle2.getString("consum");
            mconsumi.setText(consum);

        }
        //setlayout recyler

        linearLayoutManager4 = new LinearLayoutManager(MonthlyReportDetails.this, LinearLayout.VERTICAL,false);


        myitem_place4.setLayoutManager(linearLayoutManager4);
        myitem_place4.setHasFixedSize(true);

        list4 = new ArrayList<ReportsItem>();


        if (internet){
            prepare();
        }else {

        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(MonthlyReportDetails.this,MonthlyReport.class);
//        back.putExtra("subcd",cdsub);
        back.putExtra("daily",xdaily);
        back.putExtra("month",xmonth);
        back.putExtra("posi",pos);
        back.putExtra("posi2",pos2);
        back.putExtra("consum",consum);
        back.putExtra("date",period);
        startActivity(back);
//        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }
    public void prepare(){

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("pressGuid",guid);
        jsonObject.addProperty("period",period);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.preparereportget(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("qewer",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                sesionid();
                if (statusnya.equals("OK")){
                    JsonObject data = homedata.getAsJsonObject("data");
//                    String oldadate = data.get("minPeriod").getAsString();
                    mcustomera.setText(data.get("customerName").getAsString());
                    mpressnamea.setText(data.get("pressSN").getAsString());
                    mpresstypea.setText(data.get("pressTypeName").getAsString());
                    if (data.getAsJsonArray("sparePartList").toString().equals("[]")){
                        mtanpasper.setVisibility(View.VISIBLE);
                        mlayoutgrandtotal.setVisibility(View.GONE);
                    }else {
//                        mtanpasper.setVisibility(View.GONE);
                        listformreq4 = data.getAsJsonArray("sparePartList");
                        Gson gson = new Gson();
                        Type listType4 = new TypeToken<ArrayList<ReportsItem>>() {
                        }.getType();

                        list4 = gson.fromJson(listformreq4.toString(), listType4);
                        addFormAdapterAdapter4 = new ReportAdapter(MonthlyReportDetails.this, list4);
                        myitem_place4.setAdapter(addFormAdapterAdapter4);
                        myitem_place4.setVisibility(View.VISIBLE);
                        mtanpasper.setVisibility(View.GONE);
                        mlayoutgrandtotal.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(MonthlyReportDetails.this,errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MonthlyReportDetails.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading.dismiss();

            }
        });
        Log.d("readnews",jsonObject.toString());
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) MonthlyReportDetails.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(MonthlyReportDetails.this, NoInternet.class);
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
                Intent gotoupdate = new Intent(MonthlyReportDetails.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(MonthlyReportDetails.this, LoginActivity.class));
            finish();
            Toast.makeText(MonthlyReportDetails.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
}