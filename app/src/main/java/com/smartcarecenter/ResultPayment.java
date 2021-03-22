package com.smartcarecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.xml.transform.Result;

public class ResultPayment extends AppCompatActivity {
    TextView mresult, mback, mtitle, mketerangan;
    ImageView micon;
    String nopo = "";
    String Grandtotal= "";
    TextView mnoponya, mharganya, mno, mbackbtn;
    String noOrder="";
    String valuefilter= "";
    String guid = "";
    String username = "";
    String mmustUpload = "";
    String mcc= "";
    EditText mtoken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_payment);
        mresult = findViewById(R.id.resultText);
        mback = findViewById(R.id.btnSubmit);
        mtitle = findViewById(R.id.title);
        micon = findViewById(R.id.imageVar);
        mketerangan = findViewById(R.id.suces);
        mtoken = findViewById(R.id.token);

        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            nopo = bundle2.getString("nopo");
            Grandtotal = bundle2.getString("grandtotal");
            noOrder=bundle2.getString("id");;
            valuefilter= bundle2.getString("pos");;
            guid = bundle2.getString("guid");;
            username = bundle2.getString("username");;
            mmustUpload = bundle2.getString("pdfyes");;
            mcc= bundle2.getString("cc");
            mtitle.setText(bundle2.getString("cc"));
            mresult.setText(bundle2.getString("ss"));
            if (mcc.equals("Success")){
                Picasso.with(ResultPayment.this).load(R.drawable.ico_payment_success).into(micon);
                mketerangan.setText("barang yg diminta akan di antar sesuai jadwal delivery");
                mback.setText(getString(R.string.title_back));
            }else{
                mketerangan.setText("Pembayaran gagal mohon periksa kembali data anda dengan benar!");
                mback.setText(getString(R.string.title_btngagal));
                Picasso.with(ResultPayment.this).load(R.drawable.ico_payment_failed).into(micon);
                mtoken.setText(bundle2.getString("tokennya"));
            }
        }
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent back = new Intent(ResultPayment.this,PaymentAct.class);
        back.putExtra("grandtotal",Grandtotal);
        back.putExtra("id",noOrder);
        back.putExtra("guid",guid);
        back.putExtra("username",username);
        back.putExtra("pdfyes",mmustUpload);
        back.putExtra("pos",valuefilter);
        back.putExtra("nopo",nopo);
        back.putExtra("ss","Payment Success");
        back.putExtra("cc","Success");
        startActivity(back);
        startActivity(back);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();





    }
}