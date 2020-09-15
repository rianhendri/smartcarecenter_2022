package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;

public class Langueage extends AppCompatActivity {
    boolean internet = true;
    String stringlang = "EN";
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    String sesionid_new = "";
    ProgressDialog loading;
    RadioButton meng,mind;
    ImageView mindonesia, menglish;
    LinearLayout mback;
    TextView msave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_langueage);
        mindonesia =findViewById(R.id.flagid);
        menglish = findViewById(R.id.flageng);
        meng = findViewById(R.id.eng);
        mind = findViewById(R.id.indo);
        msave = findViewById(R.id.save);
        mback = findViewById(R.id.backbtn);

        getSessionId();
        loadLanguage();
        cekInternet();
        setLocale(stringlang);
        if (internet){
            if (stringlang.equals("EN")){
                meng.setChecked(true);
            }else {
                mind.setChecked(true);
            }
        }else {

        }
        mindonesia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mind.setChecked(true);
                stringlang = "ID";
            }
        });
        menglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meng.setChecked(true);
                stringlang="EN";
            }
        });
        msave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekInternet();
                if (internet){
                    changelanguage();
                }else {

                }
            }
        });
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) Langueage.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(Langueage.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void changelanguage(){
        if (mind.isChecked()){
            stringlang = "ID";
        }else {
            stringlang = "EN";
        }
        loading = ProgressDialog.show(Langueage.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("languageCd", stringlang);
        jsonObject.addProperty("ver",ver);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONlanguage(jsonObject);
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
                    SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
                    editor.putString("My_Lang", stringlang);
                    editor.apply();
                    setLocale(stringlang);
                    Toast.makeText(Langueage.this, getString(R.string.title_languagechange),Toast.LENGTH_LONG).show();
                    loading.dismiss();
                    onBackPressed();

                }else {
                    sesionid();
                    loading.dismiss();
                    if (errornya.equals((Object)"null")) {
                        cekInternet();
                    }else {
                        Toast.makeText(Langueage.this, errornya.toString(), (Toast.LENGTH_SHORT)).show();
                        cekInternet();
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Langueage.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
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
                Intent gotoupdate = new Intent(Langueage.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(Langueage.this, LoginActivity.class));
            finish();
            Toast.makeText(Langueage.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent((Context)this, SettingActivity.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
    //SETLANGUAGE
    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }
    public void loadLanguage() {
        SharedPreferences preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        stringlang = preferences.getString("My_Lang","");

    }

}