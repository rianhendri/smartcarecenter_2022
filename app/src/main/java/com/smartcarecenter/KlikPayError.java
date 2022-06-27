package com.smartcarecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class KlikPayError extends AppCompatActivity {
    TextView mbtnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klik_pay_error);
        mbtnSubmit = findViewById(R.id.btnSubmit);

        mbtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed() {
            Intent back = new Intent(KlikPayError.this,ChargeableActivity.class);
            startActivity(back);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();







    }
}