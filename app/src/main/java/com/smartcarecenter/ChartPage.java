package com.smartcarecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class ChartPage extends AppCompatActivity {
    WebView mchart;
    String link ="";
    String sesionid_new = "";
    LinearLayout mback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_page);
        mchart = findViewById(R.id.chartweb);
        mback = findViewById(R.id.backbtn);
        getSessionId();

        link = "https://www.smartcarecenter.id/board/dashboard.aspx?a="+sesionid_new;
//        webView.loadUrl("https://exitme");
        Log.d("linknya",link);
        mchart.getSettings().setJavaScriptEnabled(true);
//        mchart.getSettings().setLoadWithOverviewMode(true);
//        mchart.getSettings().setUseWideViewPort(true);
//        mchart.getSettings().setBuiltInZoomControls(true);
        mchart.getSettings().setLoadWithOverviewMode(true);
        mchart.getSettings().setUseWideViewPort(true);
        mchart.loadUrl(link);
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent((Context)this, Dashboard.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
}