package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.menuhome.MenuAdapter;
import com.smartcarecenter.menuhome.MenuItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;

public class Myprofile extends AppCompatActivity {
    LinearLayout mback;
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    String user = "";
    String sesionid_new = "";
    ProgressDialog loading;
    ImageView mfoto;
    String mgroupName = "";
    TextView mnamapt2,mgrouppt2;
    String mhomeName = "";
    String mcompanyLogoURL = "";
    String mcompanyName = "";
    TextView mnamaid,mnamaid2,muser;
    TextView mnamapt,mupdatebtn;
    EditText mphone,memail,mjobtitle;
    String notifnya = "no";
    String homes = "no";

    LinearLayout mshownotif;
    boolean internet = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        mjobtitle = findViewById(R.id.jobtitle);
        mupdatebtn  =findViewById(R.id.updatebtn);
        mphone = findViewById(R.id.phone);
        memail = findViewById(R.id.email);
        mshownotif = findViewById(R.id.pleaseshow);

        mback = findViewById(R.id.backbtn);
        mnamaid = findViewById(R.id.username);
        mnamaid2 = findViewById(R.id.username2);
        mnamapt2 = findViewById(R.id.namapt2);
        mnamapt = findViewById(R.id.namapt);
        mgrouppt2 = findViewById(R.id.group2);
        mfoto = findViewById(R.id.foto);
        muser = findViewById(R.id.user);

        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            notifnya = bundle2.getString("notifnya");
            homes = bundle2.getString("homes");
        }
        if (notifnya.equals("yes")){
            mshownotif.setVisibility(View.VISIBLE);
        }else {
            mshownotif.setVisibility(View.GONE);
        }
        getSessionId();
        cekInternet();
        if (internet){
            reqApi();
        }else {

        }
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";
                if(mphone.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"No Telepon kosong",Toast.LENGTH_SHORT).show();
                }else {
                    if(memail.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Email kosong",Toast.LENGTH_SHORT).show();
                    }else {
                        if (mjobtitle.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(),"Job Title kosong",Toast.LENGTH_SHORT).show();
                        }else {
                            updateprofil();
                        }
//                        if (memail.getText().toString().trim().matches(emailPattern)) {
//                            if (mjobtitle.getText().toString().isEmpty()){
//                                Toast.makeText(getApplicationContext(),"Job Title kosong",Toast.LENGTH_SHORT).show();
//                            }else {
//                                updateprofil();
//                            }
//                        }else if (memail.getText().toString().trim().matches(emailPattern2)){
//
//                                if (mjobtitle.getText().toString().isEmpty()){
//                                    Toast.makeText(getApplicationContext(),"Job Title kosong",Toast.LENGTH_SHORT).show();
//                                }else {
//                                    updateprofil();
//                                }
//
//                        }
//                        else {
//                            Toast.makeText(getApplicationContext(),"Format email salah", Toast.LENGTH_SHORT).show();
//                        }
                    }
                }

            }
        });

    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) Myprofile.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(Myprofile.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void reqApi() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.getprofil(jsonObject);
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
                      mnamaid.setText(data.get("name").getAsString());
                      mnamaid2.setText(data.get("name").getAsString());
                      mnamaid2.setText(data.get("name").getAsString());
                      muser.setText(data.get("username").getAsString());
                      mnamapt.setText(data.get("companyName").getAsString());
                      mnamapt2.setText(data.get("companyName").getAsString());
                      mgrouppt2.setText(data.get("groupName").getAsString());
                      if (data.get("phone").getAsString().equals("")){
                          mphone.setText("");
                      }else {
                          mphone.setText(data.get("phone").getAsString());
                      }
                    if (data.get("email").getAsString().equals("")){
                        memail.setText("");
                    }else {
                        memail.setText(data.get("email").getAsString());
                    }
                    if (data.get("jobTitle").getAsString().equals("")){
                        mjobtitle.setText("");
                    }else {
                        mjobtitle.setText(data.get("jobTitle").getAsString());
                    }
//                    mhomeName = data.get("homeName").getAsString();
//                    mcompanyName = data.get("companyName").getAsString();
//                    mgroupName = data.get("groupName").getAsString();
//                    mcompanyLogoURL = data.get("companyLogoURL").getAsString();
//                    mnamaid.setText(mhomeName);
//                    mnamaid2.setText(mhomeName);
//                    mnamapt.setText(mcompanyName);
//                    mnamapt2.setText(mcompanyName);
//                    mgrouppt2.setText(mgroupName);
//                    muser.setText(user);
                    Picasso.with(Myprofile.this).load(data.get("companyLogoURL").getAsString()).into(mfoto);
                    //MENU

                }
                else {
                    sesionid();
                    Toast.makeText(Myprofile.this, errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Myprofile.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();

            }
        });
        Log.d("loadprofilereq",jsonObject.toString());
    }
    public void updateprofil() {
        loading = ProgressDialog.show(Myprofile.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("phone",mphone.getText().toString());
        jsonObject.addProperty("email",memail.getText().toString());
        jsonObject.addProperty("jobTitle",mjobtitle.getText().toString());
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.updateprofil(jsonObject);
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
                    loading.dismiss();
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    mshownotif.setVisibility(View.GONE);
                    Toast.makeText(Myprofile.this, "Update Success",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Myprofile.this, Dashboard.class));
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    finish();
                    //HEADER
//                    mhomeName = data.get("homeName").getAsString();
//                    mcompanyName = data.get("companyName").getAsString();
//                    mgroupName = data.get("groupName").getAsString();
//                    mcompanyLogoURL = data.get("companyLogoURL").getAsString();
//                    mnamaid.setText(mhomeName);
//                    mnamaid2.setText(mhomeName);
//                    mnamapt.setText(mcompanyName);
//                    mnamapt2.setText(mcompanyName);
//                    mgrouppt2.setText(mgroupName);
//                    muser.setText(user);
//                    Picasso.with(Myprofile.this).load(mcompanyLogoURL).into(mfoto);
                    //MENU

                }
                else {
                    loading.dismiss();
                    sesionid();
                    Toast.makeText(Myprofile.this, errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(Myprofile.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();

            }
        });
        Log.d("updateprofile",jsonObject.toString());
    }

    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");
        user = sharedPreferences.getString("user","");
    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
                Intent gotoupdate = new Intent(Myprofile.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(Myprofile.this, LoginActivity.class));
            finish();
            Toast.makeText(Myprofile.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        if (homes.equals("yes")){
            super.onBackPressed();
            startActivity(new Intent((Context)this, Dashboard.class));
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
        }else {
            super.onBackPressed();
            startActivity(new Intent((Context)this, SettingActivity.class));
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
        }

    }
}