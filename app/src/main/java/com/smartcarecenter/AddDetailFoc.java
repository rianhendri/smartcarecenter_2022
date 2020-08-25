package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.FormActivity.valuefilter;

public class AddDetailFoc extends AppCompatActivity {
    public static JsonArray listsn;
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    String akunid = "";
    Boolean internet = false;
    ProgressDialog loading;
    ImageView mback,mimgbanner,mimgvis;
    Spinner mbranch;
    LinearLayout mcapture;
    TextView mdate,mstartimpresi,moperator,mno_order,mrequiredfoto,msend;
    EditText mlastimpresi;
    String mpressId = "";
    LinearLayout mreadyfoto;
    Spinner msn;
    DatabaseReference reference;
    RecyclerView mlistitem_foc;
    String sesionid_new = "";
    List<String> snid = new ArrayList();
    List<String> snname = new ArrayList();
    StorageReference storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail_foc);
        mlistitem_foc = findViewById(R.id.listitemfoc);
        mdate = findViewById(R.id.datefoc);
        mno_order = findViewById(R.id.noorder);
        msend = findViewById(R.id.send);
        msn = findViewById(R.id.sn);
        moperator = findViewById(R.id.operator);
        mlastimpresi = findViewById(R.id.lastimprsi);
        mstartimpresi = findViewById(R.id.startimpresi);
        mback = findViewById(R.id.backbtn);
        cekInternet();
        getSessionId();
        if (internet){
            LoadPress();
        }else {

        }
        msn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < snid.size(); ++i) {
                    mpressId = snid.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) AddDetailFoc.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(AddDetailFoc.this, NoInternet.class);
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
                Intent gotoupdate = new Intent(AddDetailFoc.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(AddDetailFoc.this, LoginActivity.class));
            finish();
            Toast.makeText(AddDetailFoc.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(AddDetailFoc.this,FormActivity.class);
        back.putExtra("pos",valuefilter);
        startActivity(back);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
    public void LoadPress(){
        loading = ProgressDialog.show(AddDetailFoc.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, "http://api.smartcarecenter.id/");
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONpresslist(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                String errornya = homedata.get("errorMessage").toString();
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                if (statusnya.equals("OK")) {
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    listsn=data.getAsJsonArray("pressList");
                    for (int i = 0; i < listsn.size(); ++i) {
                        JsonObject jsonObject2 = (JsonObject)listsn.get(i);
                        String string4 = jsonObject2.getAsJsonObject().get("name").getAsString();
                        String string5 = jsonObject2.getAsJsonObject().get("id").getAsString();
                        snname.add(string4);
                        snid.add(string5);
                        ArrayAdapter arrayAdapter = new ArrayAdapter(AddDetailFoc.this, R.layout.spinstatus_layout, snname);
                        arrayAdapter.setDropDownViewResource(R.layout.spinkategori);
                        arrayAdapter.notifyDataSetChanged();
                        msn.setAdapter(arrayAdapter);
                        loading.dismiss();
                    }
                }else {
                    Toast.makeText(AddDetailFoc.this, errornya.toString(),Toast.LENGTH_LONG).show();
                    loading.dismiss();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AddDetailFoc.this, t.toString(),Toast.LENGTH_LONG).show();
                cekInternet();


            }
        });
    }
}