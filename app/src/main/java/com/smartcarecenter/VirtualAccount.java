package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.ordersumary.OrderSumaryAdapter;
import com.smartcarecenter.ordersumary.OrderSumaryItem;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.OrderSumary.payCd;
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;

public class VirtualAccount extends AppCompatActivity {
    String nopo = "";
    String Grandtotal= "";
    TextView mkode, msalin, mno, mbackbtn, mhtmlnya,mtotal, minvoic;
    LinearLayout mback;
    String noOrder="";
    String valuefilter= "";
    String guid = "";
    String username = "";
    String mmustUpload = "";
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    Boolean internet = false;
    String sesionid_new = "";
    ProgressDialog loading;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_account);
        mtotal = findViewById(R.id.totalValue);
        minvoic = findViewById(R.id.invoiceValue);
        mhtmlnya = findViewById(R.id.htmlnya);
        mbackbtn = findViewById(R.id.backbtn1);
        mback = findViewById(R.id.backbtn);
        mkode = findViewById(R.id.kodePembayaran);
        msalin = findViewById(R.id.salin);
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            nopo = bundle2.getString("nopo");
            Grandtotal = bundle2.getString("grandtotal");
            noOrder=bundle2.getString("id");;
            valuefilter= bundle2.getString("pos");;
            guid = bundle2.getString("guid");;
            username = bundle2.getString("username");
            mmustUpload = bundle2.getString("pdfyes");
            payCd = bundle2.getString("paycd");
//            Log.d("noorder",noOrder);
//            String paymentmethod = bundle2.getString("method");
//            mpaymentMethod.setText(paymentmethod);

        }
        cekInternet();
        getSessionId();
        if (internet){
//            LoadPress();
//            LoadData();
            loaddata();

//            if (guid==null){
//
//            }else {
//                ReadNotif();
//            }
        }else {

        }
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        msalin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                String text;
                text = mkode.getText().toString();

                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);

                Toast.makeText(getApplicationContext(), "Code Copied",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void loaddata(){
        loading = ProgressDialog.show(this, "", getString(R.string.title_loading), true);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("orderNo",noOrder);
        jsonObject.addProperty("paymentGatewayCd",payCd);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.varequest(jsonObject);
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
                if (statusnya.equals("OK")) {
                    loading.dismiss();
                    JsonObject data = homedata.getAsJsonObject("data");
                    Locale localeID = new Locale("in", "ID");
                    if (data.get("isRequestSuccess").getAsBoolean()){
                        mkode.setText(data.get("virtualAccountNumber").getAsString());
                        final DecimalFormat formatRupiah = new DecimalFormat("###,###,###,###,###");
                        mtotal.setText("Rp. "+String.valueOf(formatRupiah.format(data.get("grandTotal").getAsInt())));
                        minvoic.setText(data.get("orderNo").getAsString());
                        if (Build.VERSION.SDK_INT >= 24) {
                            mhtmlnya.setText((CharSequence) Html.fromHtml((String)data.get("content").getAsString(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            mhtmlnya.setText((CharSequence)Html.fromHtml((String)data.get("content").getAsString()));

                        }
                        msalin.setVisibility(View.VISIBLE);
                    }else {
                        mkode.setText("Request Gagal");
                        msalin.setVisibility(View.GONE);
                }

                }else {
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(VirtualAccount.this, errornya,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                sesionid();
                loading.dismiss();
                Toast.makeText(VirtualAccount.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();


            }
        });
        Log.d("jsonbank",jsonObject.toString());
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) VirtualAccount.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(VirtualAccount.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");
//        SharedPreferences taxes = getSharedPreferences("Tax",MODE_PRIVATE);
//        tax = taxes.getInt("tax", 0);
//        taxname = taxes.getString("taxname","");

    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
                Intent gotoupdate = new Intent(VirtualAccount.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(VirtualAccount.this, LoginActivity.class));
            finish();
            Toast.makeText(VirtualAccount.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent back = new Intent(this,AddDetailsPoView.class);
        back.putExtra("id",noOrder);
        back.putExtra("guid",guid);
        back.putExtra("username",username);
        back.putExtra("pdfyes",mmustUpload);
        back.putExtra("pos",valuefilter);
        startActivity(back);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
}