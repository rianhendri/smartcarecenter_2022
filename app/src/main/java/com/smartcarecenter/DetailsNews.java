package com.smartcarecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsNews extends AppCompatActivity {
    String content_details = "";
    String date = "";
    String img_details = "";
    ImageView mback,mimage,mimgpopup;
    TextView mcontentnews,mcountdislike,mcountlike,mcountview,mdate,mtitlenews;
    String title_details = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_news);
        mback = findViewById(R.id.backbtn);
        mimage = findViewById(R.id.imgdetailsnews);
        mcontentnews = findViewById(R.id.contentnews);
        mdate = findViewById(R.id.date_detailnews);
        mtitlenews = findViewById(R.id.titlenewsdetail);
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 == null) {
            ;
        } else {
            img_details = bundle2.getString("banner");
            title_details = bundle2.getString("title");
            content_details = bundle2.getString("content");
            date = bundle2.getString("date");

            Picasso.with(DetailsNews.this).load(img_details).into(mimage);
            mdate.setText(date);
            mtitlenews.setText(title_details);
            mcontentnews.setText(content_details);
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

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent((Context)this, NewsActivity.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
}