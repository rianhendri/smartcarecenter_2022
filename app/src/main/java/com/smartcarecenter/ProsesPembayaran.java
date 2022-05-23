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
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;

public class ProsesPembayaran extends AppCompatActivity {
    ProgressBar mloading;
    String tokennya2="";
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    Boolean internet = false;
    String sesionid_new = "";
    String nopo = "";
    String Grandtotal= "";
    String mmustUpload = "";
    TextView mnoponya, mharganya, mno, mbackbtn, mwaiting;
    String noOrder="";
    String valuefilter= "";
    String guid = "";
    String username = "";
    String payCd = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proses_pembayaran);
//        mloading = findViewById(R.id.progress);
        getSessionId();
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            tokennya2 = bundle2.getString("tokennya");
//            Grandtotal = bundle2.getString("grandtotal");
            nopo = bundle2.getString("nopo");
            Grandtotal = bundle2.getString("grandtotal");
            noOrder=bundle2.getString("id");;
            valuefilter= bundle2.getString("pos");;
            guid = bundle2.getString("guid");;
            username = bundle2.getString("username");
            mmustUpload = bundle2.getString("pdfyes");
            payCd = bundle2.getString("paycd");

        }
//        tokennya2="{\"res_token_id\":\"1657b67c77259ee88b9139150ba78080c348f4a8\",\"res_pairing_code\":\"18102114594521389815\",\"res_response_msg\":\"SUCCESS\",\"res_response_code\":\"0000\",\"res_device_id\":\"866334030634329\",\"res_amount\":\"3466980.00\",\"res_token_code\":\"0000\",\"res_transaction_id\":\"SO211018011\",\"res_data_email\":\"test@gmail.com\",\"res_name\":\"test\",\"res_payment_channel\":\"15\",\"res_data_mobile_phone\":\"083806532251\",\"sessionId\":\"51c6f436-d4d8-4fc8-ad0f-1e14e82ad6d7\"}";
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reqccapi();
            }
        }, 2000);

    }
    public void reqccapi(){
//        loading = ProgressDialog.show(ChangePassword.this, "", getString(R.string.title_loading), true);
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("sessionId",sesionid_new);
//        jsonObject.addProperty("oldPassword", moldpassword.getText().toString());
//        jsonObject.addProperty("newPassword", mnewpassword.getText().toString());
//        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        JsonObject jsonObject = new JsonParser().parse(tokennya2).getAsJsonObject();

        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.credtcard(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("testorder",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                if (statusnya.equals("OK")){
//                    sesionid();
                    Log.d("testorder",homedata.toString());
                    String success = "Failed";
                    if(homedata.getAsJsonObject("data").get("paymentSuccess").getAsBoolean()){
                        success = "Success";
                    }else {
                        success= "Failed";
                    }
//                    Intent gotoa = new Intent(ProsesPembayaran.this,ResultPayment.class);
//                    gotoa.putExtra("grandtotal",Grandtotal);
//                    gotoa.putExtra("id",noOrder);
//                    gotoa.putExtra("guid",guid);
//                    gotoa.putExtra("username",username);
//                    gotoa.putExtra("pdfyes",mmustUpload);
//                    gotoa.putExtra("pos",valuefilter);
//                    gotoa.putExtra("nopo",nopo);
//                    gotoa.putExtra("ss","Payment "+success);
//                    gotoa.putExtra("cc",success);
//                    gotoa.putExtra("message",homedata.getAsJsonObject("data").get("paymentSuccessMessage").getAsString());
//
//                    startActivity(gotoa);
//                    finish();
                    SharedPreferences sharedPreferences = getSharedPreferences("NOORDERNYA", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("no_order", noOrder);
//                    editor.putString("user",user);
                    editor.apply();

                    Intent gotoa = new Intent(ProsesPembayaran.this,KlikPaySuccess.class);
                    gotoa.putExtra("grandtotal",Grandtotal);
                    gotoa.putExtra("id",noOrder);
                    gotoa.putExtra("guid",guid);
                    gotoa.putExtra("username",username);
                    gotoa.putExtra("pdfyes",mmustUpload);
                    gotoa.putExtra("pos",valuefilter);
                    gotoa.putExtra("nopo",nopo);
                    gotoa.putExtra("ss","Payment "+success);
                    gotoa.putExtra("cc",success);
                    gotoa.putExtra("message",homedata.getAsJsonObject("data").get("paymentSuccessMessage").getAsString());

                    startActivity(gotoa);
                    finish();

                }else {
                    Intent gotoa = new Intent(ProsesPembayaran.this,ResultPayment.class);
                    gotoa.putExtra("grandtotal",Grandtotal);
                    gotoa.putExtra("id",noOrder);
                    gotoa.putExtra("guid",guid);
                    gotoa.putExtra("username",username);
                    gotoa.putExtra("pdfyes",mmustUpload);
                    gotoa.putExtra("pos",valuefilter);
                    gotoa.putExtra("nopo",nopo);
                    gotoa.putExtra("ss","Payment Failed");
                    gotoa.putExtra("cc","Failed");
                    gotoa.putExtra("message",errornya);

                    startActivity(gotoa);
                    finish();

                    sesionid();
//                    loading.dismiss();
                    Log.d("testorder",homedata.toString());
                    if (errornya.equals((Object)"null")) {
                        cekInternet();
                    }else {

                        cekInternet();
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Intent gotoa = new Intent(ProsesPembayaran.this,ResultPayment.class);
                gotoa.putExtra("grandtotal",Grandtotal);
                gotoa.putExtra("id",noOrder);
                gotoa.putExtra("guid",guid);
                gotoa.putExtra("username",username);
                gotoa.putExtra("pdfyes",mmustUpload);
                gotoa.putExtra("pos",valuefilter);
                gotoa.putExtra("nopo",nopo);
                gotoa.putExtra("ss","Payment Failed");
                gotoa.putExtra("cc","Failed");
                gotoa.putExtra("message","Koneksi internet terputus");

                startActivity(gotoa);
                finish();
                Toast.makeText(ProsesPembayaran.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading.dismiss();
                Log.d("testorder",t.toString());

            }
        });
        Log.d("reqorde",jsonObject.toString());

    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) ProsesPembayaran.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(ProsesPembayaran.this, NoInternet.class);
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
                Intent gotoupdate = new Intent(ProsesPembayaran.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(ProsesPembayaran.this, LoginActivity.class));
            finish();
            Toast.makeText(ProsesPembayaran.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
}