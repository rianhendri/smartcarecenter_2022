package com.smartcarecenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.squareup.picasso.Picasso;

import java.io.File;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
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
    TextView mcontentnews,mcountdislike,mcountlike,mcountview,mdate,mtitlenews;
    String title_details = "";
    TextView mbuttondownload;
    String linkdownloadnya = "";
    long queid;
    DownloadManager downloadManager;
    View underline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_news);
        mback = findViewById(R.id.backbtn);
        mimage = findViewById(R.id.imgdetailsnews);
        mcontentnews = findViewById(R.id.contentnews);
        mdate = findViewById(R.id.date_detailnews);
        mtitlenews = findViewById(R.id.titlenewsdetail);
        mbuttondownload = findViewById(R.id.linkdownload);
        mlaydownload = findViewById(R.id.layoutdownload);
        underline = findViewById(R.id.underline);
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 == null) {
            ;
        } else {
            if (download){
                mlaydownload.setVisibility(View.VISIBLE);
                underline.setVisibility(View.VISIBLE);
                mbuttondownload.setText(textdownload);
                linkdownloadnya = linkdownload;
            }else {
                mlaydownload.setVisibility(View.GONE);
                underline.setVisibility(View.GONE);
            }
            img_details = bundle2.getString("banner");
            title_details = bundle2.getString("title");
            content_details = bundle2.getString("content");
            date = bundle2.getString("date");

            Picasso.with(DetailsNews.this).load(img_details).into(mimage);
            mdate.setText(date);
            if (Build.VERSION.SDK_INT >= 24) {
                mcontentnews.setText((CharSequence) Html.fromHtml((String)content_details, Html.FROM_HTML_MODE_COMPACT));
                mcontentnews.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
                mcontentnews.setText((CharSequence)Html.fromHtml((String)content_details));
                mcontentnews.setMovementMethod(LinkMovementMethod.getInstance());

        }
            mtitlenews.setText(title_details);

        }
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
        super.onBackPressed();
        startActivity(new Intent((Context)this, NewsActivity.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
}