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

import com.doku.sdkocov2.DirectSDK;
import com.doku.sdkocov2.interfaces.iPaymentCallback;
import com.doku.sdkocov2.model.PaymentItems;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.Chargeable.ChargeableItem;
import com.smartcarecenter.Chargeable.Chargeabledapter;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.ordersumary.OrderSumaryAdapter;
import com.smartcarecenter.ordersumary.OrderSumaryItem;
import com.squareup.picasso.Picasso;

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

import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;

public class OrderSumary extends AppCompatActivity {
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
    TelephonyManager telephonyManager;
    String invoiceNumber;
    String jsonRespon;
    JSONObject respongetTokenSDK;
    private static final int REQUEST_PHONE = 1;
    private static String[] PERMISSION_PHONE = {Manifest.permission.READ_PHONE_STATE};
    int PayChanChoosed = 0;
    DirectSDK directSDK;
    ProgressDialog loading;
    public static String payCd = "";
    RecyclerView mordrSumarylist;
    TextView mpaymentMethod, maddress, mtotalharga;
    LinearLayout mchangepayment, mchangeadress, msubmit;
    JsonArray arraysumary;
    ArrayList<OrderSumaryItem> list2;
    OrderSumaryAdapter orderSumaryAdapter;
    private LinearLayoutManager linearLayoutManager;
    long totalnya = 0;
    ImageView mlogoorder;
    String imeiHp = "";

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_sumary);
        mlogoorder = findViewById(R.id.logoorder);
        mback = findViewById(R.id.backbtn);
        mordrSumarylist = findViewById(R.id.ordersumarylist);
        mpaymentMethod = findViewById(R.id.paymentmethod);
        mchangepayment = findViewById(R.id.changepayemnt);
        maddress = findViewById(R.id.adress);
        mchangeadress = findViewById(R.id.changeadress);
        mtotalharga = findViewById(R.id.totalharga);
        msubmit = findViewById(R.id.submit);
        telephonyManager = (TelephonyManager) getSystemService(OrderSumary.this.TELEPHONY_SERVICE);
        invoiceNumber = String.valueOf(AppsUtil.nDigitRandomNo(10));
        directSDK = new DirectSDK();
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
            Log.d("noorder",noOrder);
            String paymentmethod = bundle2.getString("method");
            mpaymentMethod.setText(paymentmethod);
            if (payCd.equals("VAMANDIRI")){
                Picasso.with(OrderSumary.this).load(R.drawable.mandiri).into(mlogoorder);
            }
            if (payCd.equals("CREDITCARD")){
                Picasso.with(OrderSumary.this).load(R.drawable.cc).into(mlogoorder);

            }
            if (payCd.equals("VABCA")){
                Picasso.with(OrderSumary.this).load(R.drawable.bca).into(mlogoorder);
            }
            if (payCd.equals("VADANAMON")){
                Picasso.with(OrderSumary.this).load(R.drawable.danamon).into(mlogoorder);
            }
            if (payCd.equals("VAPERMATA")){
                Picasso.with(OrderSumary.this).load(R.drawable.permata).into(mlogoorder);
            }
            if (payCd.equals("VABNI")){
                Picasso.with(OrderSumary.this).load(R.drawable.bni).into(mlogoorder);
            }
            if (payCd.equals("VACIMB")){
                Picasso.with(OrderSumary.this).load(R.drawable.cimb).into(mlogoorder);
            }
            if (payCd.equals("VAMAYBANK")){
                Picasso.with(OrderSumary.this).load(R.drawable.mybank).into(mlogoorder);
            }
        }
        linearLayoutManager = new LinearLayoutManager(OrderSumary.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        mordrSumarylist.setLayoutManager(linearLayoutManager);
        mordrSumarylist.setHasFixedSize(true);
        list2 = new ArrayList<OrderSumaryItem>();
        cekInternet();
        getSessionId();
        if (internet){
//            LoadPress();
//            LoadData();
            loaddata();

//            if (guid==null){
//
//            }else {
//                ReadNotif();
//            }
        }else {

        }
        msubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payCd.equals("CREDITCARD")){
                    int permissionCheck = ContextCompat.checkSelfPermission(OrderSumary.this, Manifest.permission.READ_PHONE_STATE);

                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        getPermissionFirst(1);
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
                    } else {
//                    Toast.makeText(PaymentAct.this, String.valueOf(AppsUtil.SHA1(AppsUtil.generateMoneyFormat2("15000") + "8878" +
//                            "f599rtEZtH5A" + invoiceNumber + 360 +
//                            telephonyManager.getDeviceId())), Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            imeiHp = android.provider.Settings.Secure.getString(
                                    OrderSumary.this.getContentResolver(),
                                    android.provider.Settings.Secure.ANDROID_ID);
                        } else {
                            imeiHp = telephonyManager.getDeviceId();
                        }
                        PaymentItems paymentItems = new PaymentItems();
                        paymentItems.setDataAmount(AppsUtil.generateMoneyFormat(String.valueOf(totalnya)));
                        paymentItems.setDataBasket("[{\"name\":\"sayur\",\"amount\":\"10000.00\",\"quantity\":\"1\",\"subtotal\":\"10000.00\"},{\"name\":\"buah\",\"amount\":\"10000.00\",\"quantity\":\"1\",\"subtotal\":,\"10000.00\"}]");
                        paymentItems.setDataCurrency("360");
                        paymentItems.setDataWords(AppsUtil.SHA1(AppsUtil.generateMoneyFormat(String.valueOf(totalnya)) + "8878" +
                                "f599rtEZtH5A" + noOrder + 360 +
                                imeiHp));
                        paymentItems.setDataMerchantChain("NA");
                        paymentItems.setDataSessionID(String.valueOf(AppsUtil.nDigitRandomNo(9)));
                        paymentItems.setDataTransactionID(noOrder);
                        paymentItems.setDataMerchantCode("8878");
                        paymentItems.setDataImei(imeiHp);
                        paymentItems.setMobilePhone("");
                        paymentItems.isProduction(false); //set ‘true’ for production and ‘false’ for
                        paymentItems.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsBkd2EipFMMn3hy/rgQ3UBYs0WFPiei2RFSU0r/ClJXgyh88Eq+BpKtSCivbCjCZE7YOhcdbtYonFIi+isheNv00zqo5msQNCvhT45uYZ2Arvh8+F9xGE+y1KTS7ruYnzsDHYTBv+MHOJxs0Yn1mi3+y0KSMIBhz5iSIPzQgnLdNww0VnhwNdCwlm1EeBBE4ijWAm7IWxrFAsmMynUVCZRzZ5tTU4mb8BEDc854Pu94m1YAugw74f7JzMol7tPf5MO79moXdvDmPKVzNrEvMVFDLk+KnvI/yYe4uReQA4H2glNB+hGRPjqDXztY/6EJBHDo79cjKSBmuU5WGYReRiwIDAQAB"); //PublicKey c
                        directSDK.setCart_details(paymentItems);
                        directSDK.setPaymentChannel(1);
                        directSDK.getResponse(new iPaymentCallback() {
                            @Override
                            public void onSuccess(final String text) {
                                Log.d("dddd",text);
                                JsonObject test = new JsonObject();
                                test.addProperty("sessionId",sesionid_new);
                                String textnya = text.replace("}","");

                                String sesion=test.toString().replace("{","");
                                String textnya2 = textnya+","+sesion;
                                Log.d("testq",textnya2);
//                            Toast.makeText(DokuAct.this, respongetTokenSDK.toString(), Toast.LENGTH_SHORT).show();
                                try {
                                    respongetTokenSDK = new JSONObject(text);

                                    if (respongetTokenSDK.getString("res_response_code").equalsIgnoreCase("0000")) {

                                        Log.d("brs",text);
//                                    tokenid = respongetTokenSDK.getString("res_token_id");
//                                    pairingcode = respongetTokenSDK.getString("res_pairing_code");
//                                    jsonRespon = text;
//                                    new RequestPayment().execute();
//                                    Toast.makeText(DokuAct.this, text, Toast.LENGTH_SHORT).show();
                                        Intent gotoa = new Intent(OrderSumary.this,ResultPayment.class);
                                        gotoa.putExtra("grandtotal",Grandtotal);
                                        gotoa.putExtra("id",noOrder);
                                        gotoa.putExtra("guid",guid);
                                        gotoa.putExtra("username",username);
                                        gotoa.putExtra("pdfyes",mmustUpload);
                                        gotoa.putExtra("pos",valuefilter);
                                        gotoa.putExtra("nopo",nopo);
                                        gotoa.putExtra("ss","Payment Failed");
                                        gotoa.putExtra("cc","Failed");
                                        gotoa.putExtra("tokennya",textnya2);
                                        startActivity(gotoa);
                                        finish();
                                    }
                                } catch (JSONException e)
                                {
                                    e.printStackTrace();
                                    Log.e("liateror2",e.toString());
//                                Toast.makeText(PaymentAct.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onError(final String text) {
                                Log.e("liateror",text);
//                            Log.d("brs",jsonRespon);
//error handling here                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onException(Exception eSDK) {
                                eSDK.printStackTrace();
                                Log.e("liateror1",eSDK.toString());
//                            Toast.makeText(PaymentAct.this, eSDK.toString(), Toast.LENGTH_SHORT).show();

                            }
                        }, getApplicationContext());
                    }
                }
                if (payCd.equals("VAMANDIRI")){
                    Intent gotoa = new Intent(OrderSumary.this,VirtualAccount.class);
                    gotoa.putExtra("grandtotal",Grandtotal);
                    gotoa.putExtra("id",noOrder);
                    gotoa.putExtra("guid",guid);
                    gotoa.putExtra("username",username);
                    gotoa.putExtra("pdfyes",mmustUpload);
                    gotoa.putExtra("pos",valuefilter);
                    gotoa.putExtra("nopo",nopo);
                    gotoa.putExtra("ss","Payment Failed");
                    gotoa.putExtra("cc","Failed");
                    gotoa.putExtra("tokennya","");
                    gotoa.putExtra("paycd",payCd);
                    startActivity(gotoa);
                    finish();
                }
                if (payCd.equals("VABCA")){
                    Intent gotoa = new Intent(OrderSumary.this,VirtualAccount.class);
                    gotoa.putExtra("grandtotal",Grandtotal);
                    gotoa.putExtra("id",noOrder);
                    gotoa.putExtra("guid",guid);
                    gotoa.putExtra("username",username);
                    gotoa.putExtra("pdfyes",mmustUpload);
                    gotoa.putExtra("pos",valuefilter);
                    gotoa.putExtra("nopo",nopo);
                    gotoa.putExtra("ss","Payment Failed");
                    gotoa.putExtra("cc","Failed");
                    gotoa.putExtra("tokennya","");
                    gotoa.putExtra("paycd",payCd);
                    startActivity(gotoa);
                    finish();
                }
                if (payCd.equals("VADANAMON")){
                    Intent gotoa = new Intent(OrderSumary.this,VirtualAccount.class);
                    gotoa.putExtra("grandtotal",Grandtotal);
                    gotoa.putExtra("id",noOrder);
                    gotoa.putExtra("guid",guid);
                    gotoa.putExtra("username",username);
                    gotoa.putExtra("pdfyes",mmustUpload);
                    gotoa.putExtra("pos",valuefilter);
                    gotoa.putExtra("nopo",nopo);
                    gotoa.putExtra("ss","Payment Failed");
                    gotoa.putExtra("cc","Failed");
                    gotoa.putExtra("tokennya","");
                    gotoa.putExtra("paycd",payCd);
                    startActivity(gotoa);
                    finish();
                }
                if (payCd.equals("VAPERMATA")){
                    Intent gotoa = new Intent(OrderSumary.this,VirtualAccount.class);
                    gotoa.putExtra("grandtotal",Grandtotal);
                    gotoa.putExtra("id",noOrder);
                    gotoa.putExtra("guid",guid);
                    gotoa.putExtra("username",username);
                    gotoa.putExtra("pdfyes",mmustUpload);
                    gotoa.putExtra("pos",valuefilter);
                    gotoa.putExtra("nopo",nopo);
                    gotoa.putExtra("ss","Payment Failed");
                    gotoa.putExtra("cc","Failed");
                    gotoa.putExtra("tokennya","");
                    gotoa.putExtra("paycd",payCd);
                    startActivity(gotoa);
                    finish();
                }
                if (payCd.equals("VABNI")){
                    Intent gotoa = new Intent(OrderSumary.this,VirtualAccount.class);
                    gotoa.putExtra("grandtotal",Grandtotal);
                    gotoa.putExtra("id",noOrder);
                    gotoa.putExtra("guid",guid);
                    gotoa.putExtra("username",username);
                    gotoa.putExtra("pdfyes",mmustUpload);
                    gotoa.putExtra("pos",valuefilter);
                    gotoa.putExtra("nopo",nopo);
                    gotoa.putExtra("ss","Payment Failed");
                    gotoa.putExtra("cc","Failed");
                    gotoa.putExtra("tokennya","");
                    gotoa.putExtra("paycd",payCd);
                    startActivity(gotoa);
                    finish();
                }
                if (payCd.equals("VACIMB")){
                    Intent gotoa = new Intent(OrderSumary.this,VirtualAccount.class);
                    gotoa.putExtra("grandtotal",Grandtotal);
                    gotoa.putExtra("id",noOrder);
                    gotoa.putExtra("guid",guid);
                    gotoa.putExtra("username",username);
                    gotoa.putExtra("pdfyes",mmustUpload);
                    gotoa.putExtra("pos",valuefilter);
                    gotoa.putExtra("nopo",nopo);
                    gotoa.putExtra("ss","Payment Failed");
                    gotoa.putExtra("cc","Failed");
                    gotoa.putExtra("tokennya","");
                    gotoa.putExtra("paycd",payCd);
                    startActivity(gotoa);
                    finish();
                }
                if (payCd.equals("VAMAYBANK")){
                    Intent gotoa = new Intent(OrderSumary.this,VirtualAccount.class);
                    gotoa.putExtra("grandtotal",Grandtotal);
                    gotoa.putExtra("id",noOrder);
                    gotoa.putExtra("guid",guid);
                    gotoa.putExtra("username",username);
                    gotoa.putExtra("pdfyes",mmustUpload);
                    gotoa.putExtra("pos",valuefilter);
                    gotoa.putExtra("nopo",nopo);
                    gotoa.putExtra("ss","Payment Failed");
                    gotoa.putExtra("cc","Failed");
                    gotoa.putExtra("tokennya","");
                    gotoa.putExtra("paycd",payCd);
                    startActivity(gotoa);
                    finish();
                }
            }
        });
        mchangepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void loaddata(){
        loading = ProgressDialog.show(OrderSumary.this, "", getString(R.string.title_loading), true);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("orderNo",noOrder);
        jsonObject.addProperty("paymentGatewayCd",payCd);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.ordersumary(jsonObject);
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
                    arraysumary = data.getAsJsonArray("summaryList");
//                    totalrec = data.get("totalRec").toString();
//                    mrecord.setText("Record: "+totalrec);
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<OrderSumaryItem>>() {
                    }.getType();
                    list2 = gson.fromJson(arraysumary.toString(), listType);
                    Log.d("list3",arraysumary.toString());
                    orderSumaryAdapter = new OrderSumaryAdapter(OrderSumary.this, list2);
                    mordrSumarylist.setAdapter(orderSumaryAdapter);
                    mordrSumarylist.setVisibility(View.VISIBLE);
                    loading.dismiss();
                    Locale localeID = new Locale("in", "ID");
                    final DecimalFormat formatRupiah = new DecimalFormat("###,###,###,###,###");
                    mtotalharga.setText("Rp. "+String.valueOf(formatRupiah.format(data.get("grandTotal").getAsInt())));
                    totalnya = (long)data.get("grandTotal").getAsDouble();
                }else {
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(OrderSumary.this, errornya,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                sesionid();
                loading.dismiss();
                Toast.makeText(OrderSumary.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();


            }
        });
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) OrderSumary.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(OrderSumary.this, NoInternet.class);
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
                Intent gotoupdate = new Intent(OrderSumary.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(OrderSumary.this, LoginActivity.class));
            finish();
            Toast.makeText(OrderSumary.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        Intent back = new Intent(OrderSumary.this,PaymentAct.class);
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
    @TargetApi(Build.VERSION_CODES.M)
    private void getPermissionFirst(int paymentChanel) {
        PayChanChoosed = paymentChanel;
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)) {

            ActivityCompat.requestPermissions(OrderSumary.this,
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
            pDialog = new ProgressDialog(OrderSumary.this);
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
                String conResult = ApiConnection.httpsConnection(OrderSumary.this,
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