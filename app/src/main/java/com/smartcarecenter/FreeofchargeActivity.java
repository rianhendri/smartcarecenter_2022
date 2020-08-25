package com.smartcarecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class FreeofchargeActivity extends AppCompatActivity {
    LinearLayout maddFoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeofcharge);
        maddFoc = findViewById(R.id.addfoc);

        maddFoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoaddfoc = new Intent(FreeofchargeActivity.this, AddDetailFoc.class);
                startActivity(gotoaddfoc);
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }
}