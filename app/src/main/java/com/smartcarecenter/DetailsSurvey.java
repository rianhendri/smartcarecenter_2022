package com.smartcarecenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.ListSurvey.ListSurvey.ListSurveyAnswer_adapter;
import com.smartcarecenter.ListSurvey.ListSurvey.ListSurveyAnswer_tem;
import com.smartcarecenter.SurveyList.SurveyListAnswerItem;
import com.smartcarecenter.SurveyList.SurveylistAnswerAdapter;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.smartcarecenter.listnews.NewsAdapter.download;
import static com.smartcarecenter.listnews.NewsAdapter.linkdownload;
import static com.smartcarecenter.listnews.NewsAdapter.textdownload;

public class DetailsSurvey extends AppCompatActivity {

    public static JsonArray surveylistanswer;
    public  static ArrayList<SurveyListAnswerItem> answerlist;
    SurveylistAnswerAdapter imgAdapter;
    private LinearLayoutManager linearLayoutManager;
    RecyclerView mrecylersurvey;
    String titlesurvey = "";
    TextView mtitlesurvey;
    LinearLayout mback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_survey);
        mrecylersurvey = findViewById(R.id.recylersurvey);
        mback = findViewById(R.id.backbtn);
        mtitlesurvey = findViewById(R.id.titlesurvey);
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 == null) {

        } else {
            titlesurvey = bundle2.getString("title");

        }
        mtitlesurvey.setText(titlesurvey);
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        linearLayoutManager = new LinearLayoutManager(DetailsSurvey.this, LinearLayoutManager.VERTICAL,false);
        mrecylersurvey.setLayoutManager(linearLayoutManager);
        mrecylersurvey.setHasFixedSize(true);
        answerlist = new ArrayList<SurveyListAnswerItem>();
        Gson gson2 = new Gson();
        Type listType2 = new TypeToken<ArrayList<SurveyListAnswerItem>>() {
        }.getType();
        answerlist = gson2.fromJson(surveylistanswer.toString(),listType2);
        if(answerlist!=null && answerlist.size()!=0){
            if (imgAdapter!=null){
//                                imgAdapter.clear();
            }

            imgAdapter = new SurveylistAnswerAdapter(DetailsSurvey.this, answerlist);
            mrecylersurvey.setAdapter(imgAdapter);
//                            mlihatimg.setVisibility(View.GONE);


        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent((Context)this, SurveyList_Activity.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
}