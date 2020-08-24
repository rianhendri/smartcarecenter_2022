package com.smartcarecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PurchaseMenu extends AppCompatActivity {
    ImageView mback;
    LinearLayout mbtnfoc;
    LinearLayout mbtnpo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_menu);
        mback = findViewById(R.id.backbtn);
        mbtnfoc = findViewById(R.id.btnfoc);
        mbtnpo = findViewById(R.id.pobtn);

        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mbtnpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotopo = new Intent(PurchaseMenu.this,ChargeableActivity.class);
                startActivity(gotopo);
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });
        mbtnfoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotopo = new Intent(PurchaseMenu.this,FreeofchargeActivity.class);
                startActivity(gotopo);
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent((Context)this, Dashboard.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
}