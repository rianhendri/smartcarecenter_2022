package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.Chargeable.ChargeableItem;
import com.smartcarecenter.Chargeable.Chargeabledapter;
import com.smartcarecenter.ListSurvey.ListSurvey.ListSurvey_adapter;
import com.smartcarecenter.ListSurvey.ListSurvey.ListSurvey_tem;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.menuhome.ChatItem;
import com.smartcarecenter.menuhome.MenuAdapter;
import com.smartcarecenter.menuhome.MenuItem;
import com.smartcarecenter.messagecloud.check;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.LinearLayout.*;
import static com.smartcarecenter.AddDetailFocView.Nowfoc;
import static com.smartcarecenter.AddDetailsPoView.Nowpo;
import static com.smartcarecenter.AddRequest.requestby;
import static com.smartcarecenter.DetailsFormActivity.Nowaform;
import static com.smartcarecenter.ListSurvey.ListSurvey.ListSurvey_adapter.listAnswer;
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;

public class SurveyActivity extends AppCompatActivity {
    String surveycd="";
    Boolean exit = false;
    Boolean internet = false;
    String sesionid_new = "";
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    ListSurvey_adapter addSurveyAdapter;
    TextView mtitlesurvey, msend;
    private LinearLayoutManager linearLayoutManager;
    public static ArrayList<ListSurvey_tem> listsurvey;
    public static JsonArray listformreq;
    RecyclerView MrecylerSurvey;
    LinearLayout mback;
    LinearLayout mprogress;
    ProgressBar mloading;
    TextView mgagalload;
    boolean multichose=true;
    boolean text = true;
    public static JsonArray AnswersArray;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        MrecylerSurvey = findViewById(R.id.recylersurvey);
        mback = findViewById(R.id.backbtn);
        mtitlesurvey = findViewById(R.id.titlesurvey);
        mprogress = findViewById(R.id.progressbar);
        mloading = findViewById(R.id.loading);
        mgagalload = findViewById(R.id.gagalload);
        msend = findViewById(R.id.send);
        //setlayout recyler;
        linearLayoutManager = new LinearLayoutManager(SurveyActivity.this, LinearLayoutManager.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        MrecylerSurvey.setLayoutManager(linearLayoutManager);
        MrecylerSurvey.setHasFixedSize(true);
        listsurvey = new ArrayList<ListSurvey_tem>();
        cekInternet();
        getSessionId();
        check.checknotif=1;
        if (internet){
            loadData();
        }else {

        }
        listAnswer.clear();
        msend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int x=0;x<listAnswer.size();x++){
                        if (listAnswer.get(x).getQuestionType().toString().equals("Text")){
                            if (listAnswer.get(x).isOptional()){

                        }else {
                            if (listAnswer.get(x).getAnswerText().equals("")){
                                text=false;
                            }
                        }
                    }else {
                        if (listAnswer.get(x).isOptional()){
                          
                        }else {
                            if (listAnswer.get(x).getAnswerPosition()==0){
                              multichose=false;
                            }else {
                                
                            }
                        }
                    }
                }
                if (multichose){
                    if (text){
                        showDialog();

                    }else {
                        text=true;
                        Toast.makeText(SurveyActivity.this, "Jawaban Berbintang Merah Wajib Diisi", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    multichose=true;
                    Toast.makeText(SurveyActivity.this, "Jawaban Berbintang Merah Wajib Diisi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendSurvey() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("surveyCd",surveycd);
        jsonObject.addProperty("ver",ver);
        jsonObject.add("answers",AnswersArray);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.sendsurvey(jsonObject);
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
                sesionid();
                if (statusnya.equals("OK")){
                    mprogress.setVisibility(GONE);
                    mgagalload.setVisibility(GONE);
                    mprogress.setVisibility(GONE);
                    startActivity(new Intent(SurveyActivity.this, Dashboard.class));
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    finish();
                    Toast.makeText(SurveyActivity.this, "Terimakasih Atas Jawaban Anda", Toast.LENGTH_SHORT).show();
                }else {
                    sesionid();
                    Toast.makeText(SurveyActivity.this, errornya,Toast.LENGTH_LONG).show();
                    mloading.setVisibility(GONE);
                    mgagalload.setText(errornya);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                mloading.setVisibility(GONE);
                mgagalload.setText(R.string.title_excpetation);
                Toast.makeText(SurveyActivity.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();

            }
        });
    }
    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle(getString(R.string.Title_kirimsurvey));

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage(getString(R.string.title_kirimsekarang))
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.title_yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
//                        sendData();
                        sendSurvey();
                    }
                })
                .setNegativeButton(getString(R.string.title_no),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }
    public void loadData(){
        mloading.setVisibility(VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",ver);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONconfig(jsonObject);
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
                sesionid();
                if (statusnya.equals("OK")){
                    mprogress.setVisibility(GONE);
                    mgagalload.setVisibility(GONE);
                    mprogress.setVisibility(GONE);
                    JsonObject data = homedata.getAsJsonObject("data");
                    mtitlesurvey.setText(data.get("surveyTitle").getAsString());
                    surveycd=data.get("surveyCd").getAsString();
                    listformreq = data.getAsJsonArray("surveyQuestions");
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<ListSurvey_tem>>() {
                    }.getType();
                    ArrayList<ListSurvey_tem> list;
                    list=new ArrayList<>();
                    list = gson.fromJson(listformreq.toString(), listType);
                    listsurvey.addAll(list);

                    addSurveyAdapter = new ListSurvey_adapter(SurveyActivity.this, listsurvey);
                    MrecylerSurvey.setAdapter(addSurveyAdapter);
                    MrecylerSurvey.setVisibility(View.VISIBLE);


                }else {
                    sesionid();
                    Toast.makeText(SurveyActivity.this, errornya,Toast.LENGTH_LONG).show();
                    mloading.setVisibility(GONE);
                    mgagalload.setText(errornya);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                mloading.setVisibility(GONE);
                mgagalload.setText(R.string.title_excpetation);
                Toast.makeText(SurveyActivity.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();

            }
        });
    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
                Intent gotoupdate = new Intent(SurveyActivity.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(SurveyActivity.this, LoginActivity.class));
            finish();
            Toast.makeText(SurveyActivity.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");

    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager)SurveyActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(SurveyActivity.this,NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void onBackPressed(){
        if (exit) {
            this.finish();

        } else {
            Toast.makeText(this, getString(R.string.title_exit),
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }



    }
}