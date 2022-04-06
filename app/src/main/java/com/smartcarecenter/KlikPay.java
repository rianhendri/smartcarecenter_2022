package com.smartcarecenter;

import static com.smartcarecenter.PaymentAct.Grandtotal;
import static com.smartcarecenter.PaymentAct.guid;
import static com.smartcarecenter.PaymentAct.mmustUpload;
import static com.smartcarecenter.PaymentAct.noOrder;
import static com.smartcarecenter.PaymentAct.nopo;
import static com.smartcarecenter.PaymentAct.username;
import static com.smartcarecenter.PaymentAct.valuefilter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class KlikPay extends AppCompatActivity {
    WebView mwebView;
    String payCd = "";
    String urlklikpay = "";
    TextView mtitle;
    String nopo = "";
    String Grandtotal= "";
    TextView mnoponya, mharganya, mno, mbackbtn, mwaiting;
    String noOrder="";
    String valuefilter= "";
    String guid = "";
    String username = "";
    String mmustUpload = "";
    LinearLayout mback, mcc, mva;
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    Boolean internet = false;
    String sesionid_new = "";

    String textnya2 = "";
    String items = "";
    TelephonyManager telephonyManager;
    String invoiceNumber;
    String jsonRespon;
    String paymentmethod="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klik_pay);
        getSessionId();
        cekInternet();
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            nopo = bundle2.getString("nopo");
            Grandtotal = bundle2.getString("grandtotal");
            noOrder=bundle2.getString("id");;
            valuefilter= bundle2.getString("pos");;
            guid = bundle2.getString("guid");;
            username = bundle2.getString("username");
            mmustUpload = bundle2.getString("pdfyes");
            payCd = bundle2.getString("paycd");
            paymentmethod = bundle2.getString("method");
            Log.d("noorder",noOrder+"/-"+items);
            urlklikpay ="https://www.smartcarecenter.id/gateway/doku/gate.aspx?orderno="+noOrder+"&"+"sessionid="+sesionid_new;
            Log.d("noorder","https://www.smartcarecenter.id/gateway/doku/gate.aspx?orderno="+noOrder+"&"+"sessionid="+sesionid_new);

        }
        mwebView = (WebView)findViewById(R.id.help_webview);
        mwebView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl("https://exitme");
        mwebView.loadUrl(urlklikpay);
//        mwebView.setWebViewClient(new WebViewController());
        mwebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //hide loading image
                if (url.equals("https://www.smartcarecenter.id/gateway/doku/end.aspx")){
                    Intent intent = new Intent(KlikPay.this, KlikPaySuccess.class);
                    startActivity(intent);
                    finish();
                    Log.d("eros","gagal1");
                }
                if (url.equals("https://www.smartcarecenter.id/gateway/doku/error.aspx")){
                    Intent intent = new Intent(KlikPay.this, KlikPayError.class);
                    startActivity(intent);
                    finish();
                    Log.d("eros","gagal1");
                }
                Log.d("eros",url);
//                findViewById(R.id.imageLoading1).setVisibility(View.GONE);
//                //show webview
//                findViewById(R.id.webView1).setVisibility(View.VISIBLE);
            }




        });
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) KlikPay.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(KlikPay.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");
        SharedPreferences sharedPreferences2 = getSharedPreferences("ITEMS_CART",MODE_PRIVATE);
        items = sharedPreferences.getString("items_cart", "");
//        SharedPreferences taxes = getSharedPreferences("Tax",MODE_PRIVATE);
//        tax = taxes.getInt("tax", 0);
//        taxname = taxes.getString("taxname","");

    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
                Intent gotoupdate = new Intent(KlikPay.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(KlikPay.this, LoginActivity.class));
            finish();
            Toast.makeText(KlikPay.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
//    public class WebViewController extends WebViewClient {
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            if (url.equals("https://www.smartcarecenter.id/gateway/doku/end.aspx")) {
//                Intent intent = new Intent(KlikPay.this, KlikPaySuccess.class);
//                startActivity(intent);
//                Log.d("eros","gagal2");
//                return false;
//
//            }else if (url.equals("https://www.smartcarecenter.id/gateway/doku/error.aspx")){
//                Intent intent = new Intent(KlikPay.this, KlikPayError.class);
//                startActivity(intent);
//                finish();
//                Log.d("eros","gagal1");
//                return false;
//            }
//            return false;
//        }
//
//    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent back = new Intent(KlikPay.this,OrderSumary.class);
        back.putExtra("grandtotal",Grandtotal);
        back.putExtra("id",noOrder);
        back.putExtra("guid",guid);
        back.putExtra("username",username);
        back.putExtra("pdfyes",mmustUpload);
        back.putExtra("pos",valuefilter);
        back.putExtra("nopo",nopo);
        back.putExtra("ss","Payment Failed");
        back.putExtra("cc","Failed");
        back.putExtra("tokennya","");
        back.putExtra("paycd",payCd);
        back.putExtra("method",paymentmethod);
        startActivity(back);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
}