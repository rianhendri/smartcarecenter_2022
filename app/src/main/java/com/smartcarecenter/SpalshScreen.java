package com.smartcarecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.messagecloud.check;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpalshScreen extends AppCompatActivity {
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    String sesionid_new = "";
    String stringlang = "EN";
    boolean internet = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSessionId();
        cekInternet();
        loadLanguage();
        check.checknotif=1;
        if (stringlang.equals("")) {
            setLocale("EN");
        } else {
            setLocale(stringlang);
        }
        if (internet){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (sesionid_new.equals("")){
                        Intent gogetstarted = new Intent(SpalshScreen.this, LoginActivity.class);
                        startActivity(gogetstarted);
                        finish();

                    }else{
                        /////////// cek sesion id nya
                        final JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("sessionId",sesionid_new);
//                    jsonObject.addProperty("ver","1.0.0");
                        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class,
                                "http://api.smartcarecenter.id/");
                        Call<JsonObject> call = jsonPostService.postRawJSONping(jsonObject);
                        call.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                JsonObject post=response.body();
                                String statusnya = post.get("status").getAsString();
                                String errornya = post.get("errorMessage").toString();
                                MhaveToUpdate = post.get("haveToUpdate").toString();
//                           Toast.makeText(SplashScreen.this, logonya, Toast.LENGTH_SHORT).show();
                                ////////////
                                if (statusnya.equals("OK")){
                                    JsonObject data= post.getAsJsonObject("data");
                                    MsessionExpired = data.get("sessionExpired").toString();
                                    sesionid();

                                }else{
                                    sesionid();
                                    //// error message
                                    if (errornya.equals("null")){

                                    }else {
                                        Toast.makeText(SpalshScreen.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                                    }

//                              Toast.makeText(EditProfile.this, sesionid_new.toString(), Toast.LENGTH_SHORT).show();

                                }


                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                cekInternet();
                                Toast.makeText(SpalshScreen.this, getString(R.string.title_excpetation), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            }, 2000);
        }else {
            cekInternet();

        }

    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {
                startActivity(new Intent(SpalshScreen.this, Dashboard.class));
                finish();

            }
//            startActivity(new Intent(SpalshScreen.this, UpdateActivity.class));
            finish();

        }else {
            startActivity(new Intent(SpalshScreen.this, LoginActivity.class));
            finish();
            Toast.makeText(SpalshScreen.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    public void getSessionId(){
        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");
    }
    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
//        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
//        editor.putString("My_Lang", lang);
//        editor.apply();
    }
    public void loadLanguage() {
        SharedPreferences preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        stringlang = preferences.getString("My_Lang","");

    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager)SpalshScreen.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;
        }else {
            internet=false;
            Intent noconnection = new Intent(SpalshScreen.this,NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
}