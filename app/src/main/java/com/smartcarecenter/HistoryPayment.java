package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.ordersumary.OrderSumaryAdapter;
import com.smartcarecenter.ordersumary.OrderSumaryItem;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;

public class HistoryPayment extends AppCompatActivity {
    TextView mtitle;
    String nopo = "";
    String Grandtotal= "";

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
    TextView mnoponya, mharganya, mno, mbackbtn, mwaiting, mmethod,mdatepay,mtracpay,mpatst;
    ImageView mlogomethod;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_payment);
        mmethod = findViewById(R.id.method);
        mdatepay = findViewById(R.id.datepay);
        mtracpay = findViewById(R.id.tracpay);
        mpatst = findViewById(R.id.patst);
        mlogomethod = findViewById(R.id.logomethod);
        mlogoorder = findViewById(R.id.logoorder);
        mback = findViewById(R.id.backbtn);
        mordrSumarylist = findViewById(R.id.ordersumarylist);
        mpaymentMethod = findViewById(R.id.paymentmethod);
        mchangepayment = findViewById(R.id.changepayemnt);
        maddress = findViewById(R.id.adress);
        mchangeadress = findViewById(R.id.changeadress);
        mtotalharga = findViewById(R.id.totalharga);
        msubmit = findViewById(R.id.submit);

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
//            if (payCd.equals("VAMANDIRI")){
//                Picasso.with(HistoryPayment.this).load(R.drawable.mandiri).into(mlogoorder);
//            }
//            if (payCd.equals("CREDITCARD")){
//                Picasso.with(HistoryPayment.this).load(R.drawable.cc).into(mlogoorder);
//
//            }

        }
        linearLayoutManager = new LinearLayoutManager(HistoryPayment.this, LinearLayout.VERTICAL,false);
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
        loading = ProgressDialog.show(HistoryPayment.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("orderNo",noOrder);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.historypayment(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("history2",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                if (statusnya.equals("OK")) {
                    JsonObject data = homedata.getAsJsonObject("data");
                    String date = data.get("transactionDateTime").getAsString();
                    String newdate = "";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    try {
                        newdate = simpleDateFormat.format(simpleDateFormat.parse(date));
                        System.out.println(newdate);
                        Log.e((String)"Date", (String)newdate);
                    }
                    catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    String[] separated = newdate.split("T");
                    separated[0].trim();; // this will contain "Fruit"
                    separated[1].trim();;
                    mdatepay.setText(separated[0]+" "+ separated[1]);
                    mmethod.setText(data.get("paymentChannelName").getAsString());
//                    mdatepay.setText(data.get("transactionDateTime").getAsString());
                    mtracpay.setText(data.get("transactionNo").getAsString());
                    mpatst.setText(data.get("status").getAsString());
//                    mlogomethod
                    arraysumary = data.getAsJsonArray("summaryList");
//                    totalrec = data.get("totalRec").toString();
//                    mrecord.setText("Record: "+totalrec);
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<OrderSumaryItem>>() {
                    }.getType();
                    list2 = gson.fromJson(arraysumary.toString(), listType);
                    Log.d("list3",arraysumary.toString());
                    orderSumaryAdapter = new OrderSumaryAdapter(HistoryPayment.this, list2);
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
                    Toast.makeText(HistoryPayment.this, errornya,Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                sesionid();
                loading.dismiss();
                if (MsessionExpired.equals("true")) {

                }else {
                    Toast.makeText(HistoryPayment.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();

                }
                cekInternet();


            }
        });
        Log.d("history3",jsonObject.toString());
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) HistoryPayment.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(HistoryPayment.this, NoInternet.class);
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
                Intent gotoupdate = new Intent(HistoryPayment.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(HistoryPayment.this, LoginActivity.class));
            finish();
            Toast.makeText(HistoryPayment.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        Intent back = new Intent(HistoryPayment.this,AddDetailsPoView.class);
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