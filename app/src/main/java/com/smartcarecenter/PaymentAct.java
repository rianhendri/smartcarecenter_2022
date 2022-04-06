package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.doku.sdkocov2.DirectSDK;
//import com.doku.sdkocov2.interfaces.iPaymentCallback;
//import com.doku.sdkocov2.model.PaymentItems;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.messagecloud.check;
import com.smartcarecenter.paymentbank.PaybankAdapter;
import com.smartcarecenter.paymentbank.PaybankItem;
import com.smartcarecenter.supportservice.AddFormAdapter;
import com.smartcarecenter.supportservice.AddFromItem;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.ChargeableActivity.list2;
import static com.smartcarecenter.FormActivity.refresh;
import static com.smartcarecenter.FormActivity.valuefilter;
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;

public class PaymentAct extends AppCompatActivity {
    TextView mtitle;
    public static  String nopo = "";
    public static String Grandtotal= "";
    TextView mnoponya, mharganya, mno, mbackbtn, mwaiting;
    public static String noOrder="";
    public static String valuefilter= "";
    public static String guid = "";
    public static  String username = "";
    public static String mmustUpload = "";
    LinearLayout mback, mcc, mva;
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    Boolean internet = false;
    String sesionid_new = "";
    TelephonyManager telephonyManager;
    String invoiceNumber;
    String jsonRespon;
    JSONObject respongetTokenSDK;
    private static final int REQUEST_PHONE = 1;
    private static String[] PERMISSION_PHONE = {Manifest.permission.READ_PHONE_STATE};
    int PayChanChoosed = 0;
//    DirectSDK directSDK;
    ProgressDialog loading;
    long totalnya = 0;
    PaybankAdapter addFormAdapterAdapter;
    RecyclerView myitem_place;
    private LinearLayoutManager linearLayoutManager;
    public static ArrayList<PaybankItem> list2;
    JsonArray listformreq;
    String items="";
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        myitem_place = findViewById(R.id.menubank);
        mtitle = findViewById(R.id.title);
        mwaiting = findViewById(R.id.waiting);
        mharganya = findViewById(R.id.totalharga);
        mnoponya = findViewById(R.id.noponya);
        mback = findViewById(R.id.backbtn);
        mno = findViewById(R.id.orderno);
        mbackbtn =findViewById(R.id.backin);
        mcc = findViewById(R.id.creditcardbtn);
//        mva = findViewById(R.id.vabtn);
        linearLayoutManager = new LinearLayoutManager(PaymentAct.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        myitem_place.setLayoutManager(linearLayoutManager);
        myitem_place.setHasFixedSize(true);
        list2 = new ArrayList<PaybankItem>();
        telephonyManager = (TelephonyManager) getSystemService(PaymentAct.this.TELEPHONY_SERVICE);

        invoiceNumber = String.valueOf(AppsUtil.nDigitRandomNo(10));
//        directSDK = new DirectSDK();
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            nopo = bundle2.getString("nopo");
            Grandtotal = bundle2.getString("grandtotal");

            noOrder=bundle2.getString("id");;
            valuefilter= bundle2.getString("pos");;
            guid = bundle2.getString("guid");;
            username = bundle2.getString("username");;
            mmustUpload = bundle2.getString("pdfyes");
            items = bundle2.getString("items");
            Log.d("noorder",noOrder);

        }
        cekInternet();
        getSessionId();
        if (internet){
//            LoadPress();
//            LoadData();
            prepform();
//            if (guid==null){
//
//            }else {
//                ReadNotif();
//            }
        }else {

        }

        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        mva.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent gotoaddfoc = new Intent(PaymentAct.this, ResultPayment.class);
//                gotoaddfoc.putExtra("grandtotal",Grandtotal);
//                gotoaddfoc.putExtra("id",noOrder);
//                gotoaddfoc.putExtra("guid",guid);
//                gotoaddfoc.putExtra("username",username);
//                gotoaddfoc.putExtra("pdfyes",mmustUpload);
//                gotoaddfoc.putExtra("pos",valuefilter);
//                gotoaddfoc.putExtra("nopo",nopo);
//                gotoaddfoc.putExtra("ss","Payment Failed");
//                gotoaddfoc.putExtra("cc","Failed");
//                startActivity(gotoaddfoc);
//                overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                finish();
//            }
//        });
        mcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gotoaddfoc = new Intent(PaymentAct.this, OrderSumary.class);
                gotoaddfoc.putExtra("grandtotal",Grandtotal);
                gotoaddfoc.putExtra("id",noOrder);
                gotoaddfoc.putExtra("guid",guid);
                gotoaddfoc.putExtra("username",username);
                gotoaddfoc.putExtra("pdfyes",mmustUpload);
                gotoaddfoc.putExtra("pos",valuefilter);
                gotoaddfoc.putExtra("nopo",nopo);
                gotoaddfoc.putExtra("ss","Payment Failed");
                gotoaddfoc.putExtra("paycd","CREDITCARD");
                gotoaddfoc.putExtra("method","Credit Card");
                gotoaddfoc.putExtra("items",items);
                startActivity(gotoaddfoc);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                finish();
            }
        });
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) PaymentAct.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(PaymentAct.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");
//        SharedPreferences taxes = getSharedPreferences("Tax",MODE_PRIVATE);
//        tax = taxes.getInt("tax", 0);
//        taxname = taxes.getString("taxname","");

    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
                Intent gotoupdate = new Intent(PaymentAct.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(PaymentAct.this, LoginActivity.class));
            finish();
            Toast.makeText(PaymentAct.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    public void prepform(){
        loading = ProgressDialog.show(PaymentAct.this, "", getString(R.string.title_loading), true);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("orderNo",noOrder);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.preparpayget(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                if (statusnya.equals("OK")) {
                    JsonObject data = homedata.getAsJsonObject("data");
                    listformreq = data.getAsJsonArray("channelList");
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<PaybankItem>>() {
                    }.getType();
                    list2 = gson.fromJson(listformreq.toString(), listType);
                    addFormAdapterAdapter = new PaybankAdapter(PaymentAct.this, list2);
//                    addFormAdapterAdapter.notifyDataSetChanged();
                    myitem_place.setAdapter(addFormAdapterAdapter);

                    Locale localeID = new Locale("in", "ID");
                    final DecimalFormat formatRupiah = new DecimalFormat("###,###,###,###,###.00");
                    mno.setText(data.get("orderNo").getAsString());
                    mtitle.setText(getString(R.string.title_checkout)+" "+data.get("orderNo").getAsString());
                    mnoponya.setText("PO: "+data.get("poNo").getAsString());
                    mwaiting.setText(data.get("paymentStatusName").getAsString());
                    mharganya.setText("Rp. "+String.valueOf(formatRupiah.format(data.get("grandTotal").getAsDouble())));
                    totalnya = (long)data.get("grandTotal").getAsDouble();
                    loading.dismiss();
                }else {
                    loading.dismiss();
                    Toast.makeText(PaymentAct.this, errornya,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(PaymentAct.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();


            }
        });
        Log.d("prefpay",jsonObject.toString());
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
            Intent back = new Intent(PaymentAct.this,AddDetailsPoView.class);
            back.putExtra("id",noOrder);
            back.putExtra("guid",guid);
            back.putExtra("username",username);
            back.putExtra("pdfyes",mmustUpload);
            back.putExtra("pos",valuefilter);
            startActivity(back);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
    }
    @TargetApi(Build.VERSION_CODES.M)
    private void getPermissionFirst(int paymentChanel) {
        PayChanChoosed = paymentChanel;
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)) {

            ActivityCompat.requestPermissions(PaymentAct.this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_PHONE);

        } else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_PHONE);
        }

    }
    private class RequestPayment extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PaymentAct.this);
            pDialog.setMessage("Mohon Tunggu ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject defResp = null;

            try {
                List<NameValuePair> data = new ArrayList<NameValuePair>(3);
                data.add(new BasicNameValuePair("data", jsonRespon));

                // Getting JSON from URL
                String conResult = ApiConnection.httpsConnection(PaymentAct.this,
                        Constants.URL_CHARGING_DOKU_DAN_CC, data);
                Log.d("CHARGING PAYMENT", conResult);

                defResp = new JSONObject(conResult);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("data4",e.toString());
            }
            return defResp;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            pDialog.dismiss();

            if (json != null) {
                try {
                    Log.d("data1",json.toString());
                    if (json.getString("res_response_code").equalsIgnoreCase("0000") && json != null) {
                        Intent intent = new Intent(getApplicationContext(), ResultPayment.class);
                        intent.putExtra("data", json.toString());
                        startActivity(intent);
                        finish();
                        Toast.makeText(getApplicationContext(), " PAYMENT SUKSES", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("data2",json.toString());
                        Intent intent = new Intent(getApplicationContext(), ResultPayment.class);
                        intent.putExtra("data", json.toString());
                        startActivity(intent);
                        finish();
                        Toast.makeText(getApplicationContext(), "PAYMENT ERROR", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("data3",e.toString());

                }

            }
        }
    }
    private void checkPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        REQUEST_PHONE);

                // MY_PERMISSIONS_REQUEST_READ_PHONE_STATE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}