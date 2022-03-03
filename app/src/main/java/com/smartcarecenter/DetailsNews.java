package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static com.smartcarecenter.DetailsFormActivity.sesionid_new;
import static com.smartcarecenter.FormActivity.valuefilter;
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.listnews.NewsAdapter.download;
import static com.smartcarecenter.listnews.NewsAdapter.linkdownload;
import static com.smartcarecenter.listnews.NewsAdapter.textdownload;

public class DetailsNews extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1000;
    String content_details = "";
    String date = "";
    String img_details = "";
    ImageView mimage,mimgpopup;
    LinearLayout mback,mlaydownload;
    TextView mcontentnews2,mcontentnews,mcountdislike,mcountlike,mcountview,mdate,mtitlenews;
    String title_details = "";
    TextView mbuttondownload;
    String linkdownloadnya = "";
    long queid;
    DownloadManager downloadManager;
    View underline;
    String sesionid_new = "";
    String MhaveToUpdate = "";
    boolean internet = true;
    String MsessionExpired = "";
    String newscd="";
    String newdate="";
    String home="";
    String cdsub = "";
    String subcat = "";
    String mcat = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_news);
        mback = findViewById(R.id.backbtn);
        mimage = findViewById(R.id.imgdetailsnews);
        mcontentnews = findViewById(R.id.contentnews);
        mcontentnews2 = findViewById(R.id.contentnews2);
        mdate = findViewById(R.id.date_detailnews);
        mtitlenews = findViewById(R.id.titlenewsdetail);
        mbuttondownload = findViewById(R.id.linkdownload);
        mlaydownload = findViewById(R.id.layoutdownload);
        underline = findViewById(R.id.underline);
        Bundle bundle2 = getIntent().getExtras();
        getSessionId();
        if (bundle2 == null) {

        } else {
//            if (download){
//                mlaydownload.setVisibility(View.VISIBLE);
//                underline.setVisibility(View.VISIBLE);
//                mbuttondownload.setText(textdownload);
//                linkdownloadnya = linkdownload;
//            }else {
//                mlaydownload.setVisibility(View.GONE);
//                underline.setVisibility(View.GONE);
//            }
//            img_details = bundle2.getString("banner");
//            title_details = bundle2.getString("title");
//            content_details = bundle2.getString("content");
//            date = bundle2.getString("date");
            cdsub = bundle2.getString("subCategoryCd");
            subcat = bundle2.getString("subtitle");
            mcat = bundle2.getString("title");
            home = bundle2.getString("home");
            newscd = bundle2.getString("newscd");
//            Toast.makeText(this, mcat+subcat, Toast.LENGTH_SHORT).show();
//            Picasso.with(DetailsNews.this).load(img_details).into(mimage);
//            mdate.setText(date);
//            readnews();
//            if (Build.VERSION.SDK_INT >= 24) {
//                mcontentnews.setText((CharSequence) Html.fromHtml((String)content_details, Html.FROM_HTML_MODE_COMPACT));
//                mcontentnews.setMovementMethod(LinkMovementMethod.getInstance());
//        } else {
//                mcontentnews.setText((CharSequence)Html.fromHtml((String)content_details));
//                mcontentnews.setMovementMethod(LinkMovementMethod.getInstance());
//
//        }
//            mtitlenews.setText(title_details);

        }
        loadnews();
        mimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(DetailsNews.this, R.style.TransparentDialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.popupfoto);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                mimgpopup = dialog.findViewById(R.id.imagepopup);
                Picasso.with(DetailsNews.this).load(img_details).into(mimgpopup);
                dialog.show();
            }
        });
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mbuttondownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestImage();
//                Toast.makeText(DetailsNews.this, linkdownloadnya,Toast.LENGTH_LONG).show();
            }
        });
//        if (Build.VERSION.SDK_INT >= 24) {
//            mcontentnews2.setText((CharSequence) Html.fromHtml((String)"<p><a href=\"https://www.w3schools.com/html/tryit.asp?filename=tryhtml_links_w3schools\">Visit Link</a></p>", Html.FROM_HTML_MODE_COMPACT));
//            mcontentnews2.setMovementMethod(LinkMovementMethod.getInstance());
//        } else {
//            mcontentnews2.setText((CharSequence)Html.fromHtml((String)"<p><a href=\"https://www.w3schools.com/html/tryit.asp?filename=tryhtml_links_w3schools\">Visit Link</a></p>"));
//            mcontentnews2.setMovementMethod(LinkMovementMethod.getInstance());
//
//        }

    }
    private void setRequestImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) this, Manifest.permission.CAMERA)
                    && ActivityCompat.shouldShowRequestPermissionRationale((Activity) this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                //Show permission dialog
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity)this, new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);


            }

        }else {
            startDownload();
        }
    }
    public void startDownload() {
        File direct = new File(Environment.getExternalStorageDirectory()
                + "/dhaval_files");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager mgr = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(linkdownloadnya);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

//        request.setDestinationInExternalPublicDir("/SmartcareCenter", downloadUri.getLastPathSegment());
//        Long referese = dm.enqueue(request);
        request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, downloadUri.getLastPathSegment());
        mgr.enqueue(request);
        Toast.makeText(getApplicationContext(), getString(R.string.title_unduhan), Toast.LENGTH_SHORT).show();

//        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(linkdownloadnya));
//
//        queid = downloadManager.enqueue(request);
    }
    public void godownload(){
        Intent liat = new Intent();
        liat.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(liat);
    }
    @Override
    public void onBackPressed() {
        if (home!=null){
            super.onBackPressed();
            startActivity(new Intent((Context)this, Dashboard.class));
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
        }else {
            super.onBackPressed();
            Intent back = new Intent(DetailsNews.this,NewsActivity.class);
            back.putExtra("subcd",cdsub);
            back.putExtra("title",subcat);
            back.putExtra("subtitle",mcat);
            startActivity(back);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();

            overridePendingTransition(R.anim.left_in, R.anim.right_out);

        }

    }
    public void readnews(){

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("newsCd",newscd);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.readnews(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("responread",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                sesionid();
                if (statusnya.equals("OK")){
                    JsonObject data = homedata.getAsJsonObject("data");
//                    String message = data.get("message").getAsString();
////                    Toast.makeText(DetailsNews.this, message,Toast.LENGTH_LONG).show();
////                    onBackPressed();
                }else {
//                    sesionid();
//                    loading.dismiss();
                    Toast.makeText(DetailsNews.this,errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsNews.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading.dismiss();

            }
        });
        Log.d("readnews",jsonObject.toString());
    }
    public void loadnews(){

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("newsCd",newscd);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.loadnews(jsonObject);
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
                    JsonObject news = homedata.getAsJsonObject("data");
                    JsonObject data = news.getAsJsonObject("news");
                    if (data.get("ShowDownloadButton").getAsBoolean()){
                        mlaydownload.setVisibility(View.VISIBLE);
                        underline.setVisibility(View.VISIBLE);
                        mbuttondownload.setText(data.get("DownloadButtonText").getAsString());
                        linkdownloadnya = data.get("DownloadButtonURL").getAsString();
                        Log.d("booleannya",textdownload);
                    }else {
                        mlaydownload.setVisibility(View.GONE);
                        underline.setVisibility(View.GONE);
                    }
                    img_details = data.get("BannerThumbFullURL").getAsString();
                    title_details = data.get("Title").getAsString();
                    content_details = data.get("Content").getAsString();
                    date = data.get("CreatedDateTime").getAsString();
                    String string2 = date;

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    try {
                        newdate = simpleDateFormat2.format(simpleDateFormat.parse(string2));
                        System.out.println(newdate);
                        Log.e((String)"Date", newdate);
                    }
                    catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    newscd = data.get("NewsCd").getAsString();
                    Picasso.with(DetailsNews.this).load(img_details).into(mimage);
                    mdate.setText(newdate);
                    readnews();
                    if (Build.VERSION.SDK_INT >= 24) {
                        mcontentnews.setText((CharSequence) Html.fromHtml((String)content_details, Html.FROM_HTML_MODE_COMPACT));
                        mcontentnews.setMovementMethod(LinkMovementMethod.getInstance());
                    } else {
                        mcontentnews.setText((CharSequence)Html.fromHtml((String)content_details));
                        mcontentnews.setMovementMethod(LinkMovementMethod.getInstance());

                    }
                    mtitlenews.setText(title_details);

                }else {
//                    sesionid();
//                    loading.dismiss();
                    Toast.makeText(DetailsNews.this,errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsNews.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading.dismiss();

            }
        });
        Log.d("readnews",jsonObject.toString());
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) DetailsNews.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(DetailsNews.this, NoInternet.class);
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
                Intent gotoupdate = new Intent(DetailsNews.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(DetailsNews.this, LoginActivity.class));
            finish();
            Toast.makeText(DetailsNews.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
}