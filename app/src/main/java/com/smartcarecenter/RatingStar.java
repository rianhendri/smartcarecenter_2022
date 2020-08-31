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
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.FormActivity.valuefilter;

public class RatingStar extends AppCompatActivity {
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    boolean approve = true;
    String errornya = "";
    boolean internet = true;
    ProgressDialog loading;
    EditText mcomment;
    TextView mnoticket;
    RatingBar mratingstar;
    TextView mratingvalue;
    LinearLayout msolved;
    LinearLayout munsolved;
    String noreq = "";
    String noticket = "";
    int ratvalue = 0;
    String sesionid_new = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_star);
        msolved = (LinearLayout)findViewById(R.id.solved);
        munsolved = (LinearLayout)findViewById(R.id.unsolved);
        mratingvalue = (TextView)findViewById(R.id.ratingvalue);
        mnoticket = (TextView)findViewById(R.id.noticket);
        mcomment = (EditText)findViewById(R.id.comment);
        mratingstar = (RatingBar)findViewById(R.id.ratingstar);

        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            noreq = extras.getString("id");
            noticket = extras.getString("noticket");
            valuefilter = extras.getString("pos");
        }
        getSessionId();
        cekInternet();

        if (internet){

        }else {

        }
        mnoticket.setText("#"+noreq);
        mratingstar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
            mratingvalue.setText("Rating: "+ String.valueOf(v));
            ratvalue = (int) v;
            }
        });
        msolved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekInternet();
                if (ratvalue == 0) {
                    Toast.makeText(RatingStar.this, getString(R.string.title_require_rate), Toast.LENGTH_SHORT).show();

                }else {
                    approve = true;
                    if (internet) {
                        sendRate();
                    }
                }
            }
        });
        munsolved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekInternet();
                if (ratvalue == 0) {
                    Toast.makeText(RatingStar.this, getString(R.string.title_require_rate), Toast.LENGTH_SHORT).show();

                }else {
                    approve = true;
                    if (internet) {
                        sendRate();
                    }
                }


            }
        });
    }
    public void sendRate(){
        loading = ProgressDialog.show(RatingStar.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId", sesionid_new);
        jsonObject.addProperty("formRequestCd", noreq);
        jsonObject.addProperty("rating", ratvalue);
        jsonObject.addProperty("comments", mcomment.getText().toString());
        jsonObject.addProperty("isApprove",approve);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, "http://api.smartcarecenter.id/");
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONconfirm(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                String errornya = homedata.get("errorMessage").toString();
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                sesionid();
                if (statusnya.equals("OK")){
                    JsonObject data = homedata.getAsJsonObject("data");
                    String message = data.get("message").getAsString();
                    Toast.makeText(RatingStar.this, message,Toast.LENGTH_LONG).show();
                    onBackPressed();
                    loading.dismiss();
                }else {
                    sesionid();
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(RatingStar.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) RatingStar.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(RatingStar.this, NoInternet.class);
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
                Intent gotoupdate = new Intent(RatingStar.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(RatingStar.this, LoginActivity.class));
            finish();
            Toast.makeText(RatingStar.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(RatingStar.this,DetailsFormActivity.class);
        back.putExtra("id",noreq);
        back.putExtra("pos",valuefilter);
        startActivity(back);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
}