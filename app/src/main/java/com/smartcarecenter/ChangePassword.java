package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {
    EditText moldpassword, mnewpassword, mretypepassword;
    ImageView mback;
    TextView msave;
    boolean internet = true;
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    String sesionid_new = "";
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mback = findViewById(R.id.backbtn);
        mnewpassword = findViewById(R.id.newpassword);
        moldpassword = findViewById(R.id.oldpassword);
        mretypepassword = findViewById(R.id.repassword);
        msave = findViewById(R.id.save);
        cekInternet();
        getSessionId();


        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        msave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mnewpassword.length()==0){
                    mnewpassword.setError(getString(R.string.title_passwordRequired));
                    if (moldpassword.length()==0){
                        moldpassword.setError(getString(R.string.title_passwordRequired));
                        if (mretypepassword.length()==0){
                            mretypepassword.setError(getString(R.string.title_passwordRequired));
                        }else {
                            if (internet){
                                changepassword();
                            }else {
                                cekInternet();
                            }
                        }
                    }
                }

            }
        });
        mretypepassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mretypepassword.getText().toString().equals(mnewpassword.getText().toString())){
                    msave.setEnabled(true);
                }else {
                    mretypepassword.setError(getString(R.string.title_Passwordwrong));

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) ChangePassword.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(ChangePassword.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void changepassword(){
        loading = ProgressDialog.show(ChangePassword.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("oldPassword", moldpassword.getText().toString());
        jsonObject.addProperty("newPassword", mnewpassword.getText().toString());
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, "http://api.smartcarecenter.id/");
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONchangepassword(jsonObject);
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
                    Toast.makeText(ChangePassword.this, getString(R.string.title_changepass_succsess),Toast.LENGTH_LONG).show();
                    mnewpassword.setText("");
                    moldpassword.setText("");
                    mretypepassword.setText("");
                    loading.dismiss();

                }else {
                    sesionid();
                    loading.dismiss();
                    if (errornya.equals((Object)"null")) {
                        cekInternet();
                    }else {
                        Toast.makeText(ChangePassword.this, errornya.toString(), (Toast.LENGTH_SHORT)).show();
                        cekInternet();
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ChangePassword.this, t.toString(),Toast.LENGTH_LONG).show();
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
                Intent gotoupdate = new Intent(ChangePassword.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(ChangePassword.this, LoginActivity.class));
            finish();
            Toast.makeText(ChangePassword.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent((Context)this, SettingActivity.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
}