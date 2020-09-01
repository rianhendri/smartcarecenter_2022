package com.smartcarecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static com.smartcarecenter.Dashboard.mshowPurchaseOrderFOC;
import static com.smartcarecenter.Dashboard.mshowPurchaseOrderPO;
import static com.smartcarecenter.Dashboard.showaddfoc;
import static com.smartcarecenter.Dashboard.showaddform;
import static com.smartcarecenter.Dashboard.showaddpo;

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
        //showadd
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            showaddfoc = bundle2.getString("showaddfoc");
            showaddpo = bundle2.getString("showaddpo");
        }
        if (mshowPurchaseOrderFOC.equals("false")){
            mbtnfoc.setVisibility(View.GONE);
        }else if (mshowPurchaseOrderPO.equals("false")){
            mbtnpo.setVisibility(View.GONE);
        }else {
            mbtnpo.setVisibility(View.VISIBLE);
            mbtnfoc.setVisibility(View.VISIBLE);
        }
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
                gotopo.putExtra("showaddpo",showaddpo);
                startActivity(gotopo);
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });
        mbtnfoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotopo = new Intent(PurchaseMenu.this,FreeofchargeActivity.class);
                gotopo.putExtra("showaddfoc",showaddfoc);
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