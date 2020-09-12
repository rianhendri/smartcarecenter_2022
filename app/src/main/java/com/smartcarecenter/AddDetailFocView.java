package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.Add_Foc_Request_view.Add_foc_req_adapterView;
import com.smartcarecenter.Add_Foc_Request_view.Add_foc_req_itemView;
import com.smartcarecenter.Add_Foc_request.Add_foc_req_adapter;
import com.smartcarecenter.Add_Foc_request.Add_foc_req_item;
import com.smartcarecenter.Freeofcharge.FocAdapter;
import com.smartcarecenter.Freeofcharge.FocItem;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.Add_foc_Item_list_model.Add_foc_list_adapter.listpoact;
import static com.smartcarecenter.FormActivity.valuefilter;
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;

public class AddDetailFocView extends AppCompatActivity {
    public static JsonArray listsn;
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    String akunid = "";
    Boolean internet = false;
    boolean installed= true;
    ProgressDialog loading;
    ImageView mback;
    public static LinearLayout mlaytotal;
    public static TextView mdate,mstartimpresi,moperator,mno_order,mtotalitem,msend,mtotalqty,
            mnoitem,mlastimpresi,mstatus, mtilte;
    String mpressId = "";
    String mpressId2 = "";
    Integer previmpressvlaue = 100;
    LinearLayout madd_item,mchat;
    TextView msn;
    DatabaseReference reference;
    public static RecyclerView mlistitem_foc;
    String sesionid_new = "";
    List<String> snid = new ArrayList();
    List<String> snname = new ArrayList();
    List<Integer> previmpression = new ArrayList();
    //list item add
    public static ArrayList<Add_foc_req_itemView> reitem;
    Add_foc_req_adapterView req_adapter;
    private LinearLayoutManager linearLayoutManager;
    String jsonarayitem = "";
    JsonArray myCustomArray;
    String noOrder="";
    String pressid= "";
    Gson gson;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail_foc_view);
        mlistitem_foc = findViewById(R.id.listitemfoc);
        mdate = findViewById(R.id.datefoc);
        mno_order = findViewById(R.id.noorder);
        mnoitem = findViewById(R.id.noitem);
        msend = findViewById(R.id.submit);
        msn = findViewById(R.id.sn);
        moperator = findViewById(R.id.operator);
        mlastimpresi = findViewById(R.id.lastimprsi);
        mstartimpresi = findViewById(R.id.startimpresi);
        mback = findViewById(R.id.backbtn);
        mtotalitem = findViewById(R.id.totalitemfoc);
        mtotalqty = findViewById(R.id.totalqtyfoc);
        mlaytotal = findViewById(R.id.totallay);
        madd_item = findViewById(R.id.btnadditem_po);
        mstatus = findViewById(R.id.status);
        mchat = findViewById(R.id.chatcspo);
        mtilte = findViewById(R.id.title);
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            noOrder = bundle2.getString("id");
            valuefilter = bundle2.getString("pos");
        }
        mtilte.setText("View FOC #"+noOrder);
        cekInternet();
        getSessionId();
        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(AddDetailFocView.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        mlistitem_foc.setLayoutManager(linearLayoutManager);
        mlistitem_foc.setHasFixedSize(true);
        reitem = new ArrayList<Add_foc_req_itemView>();
        if (internet){
//            LoadPress();
            LoadData();
        }else {

        }


        msend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekInternet();
                if (internet){
                    showDialog();
                }else {

                }
            }
        });
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appInstalledOrNot("com.whatsapp");
                if (installed) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("http://api.whatsapp.com/send?phone=+62822 9868 0099&text=Hi Support,  ");
                    stringBuilder.append(getString(R.string.title_tanyafoc));
//                    stringBuilder.append(" #");
                    stringBuilder.append(noOrder);
                    String message =stringBuilder.toString();
                    intent.setData(android.net.Uri.parse(message));
                    startActivity(intent);
                }else {
                    Toast.makeText(AddDetailFocView.this,"Whatsapp blum di instal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) AddDetailFocView.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(AddDetailFocView.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");

    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
                Intent gotoupdate = new Intent(AddDetailFocView.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(AddDetailFocView.this, LoginActivity.class));
            finish();
            Toast.makeText(AddDetailFocView.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(AddDetailFocView.this,FreeofchargeActivity.class);
        back.putExtra("pos",valuefilter);
        startActivity(back);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();

    }
    public void LoadData(){
        gson = new Gson();
        loading = ProgressDialog.show(AddDetailFocView.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("orderNo",noOrder);
        jsonObject.addProperty("ver",ver);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.vieworderfoc(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loading.dismiss();
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                String errornya = homedata.get("errorMessage").toString();
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                if (statusnya.equals("OK")) {
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    pressid = data.get("pressGuid").getAsString();
                    String pressname = data.get("pressTypeName").getAsString();
                    msn.setText(pressname);
                    String orderno = data.get("orderNo").getAsString();
                    String date = data.get("date").getAsString();
                    String pressstart = data.get("previousImpression").getAsString();
                    String presimpres = data.get("presentImpression").getAsString();
                    String status = data.get("statusName").getAsString();
                    String statuscolor = data.get("statusColorCode").getAsString();
                    String operator = data.get("username").getAsString();
                    String cancelshow = data.get("allowToCancel").toString();
                    if (cancelshow.equals("true")){
                        msend.setVisibility(View.VISIBLE);
                        mchat.setVisibility(View.GONE);

                    }else {
                        msend.setVisibility(View.GONE);
                        mchat.setVisibility(View.VISIBLE);
                    }
                    mstartimpresi.setText(pressstart);
                    mno_order.setText(orderno);
                    mlastimpresi.setText(presimpres);
                    mstatus.setText(status);
                    mstatus.setTextColor(Color.parseColor("#"+statuscolor));
                    String newdate = "";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.getDefault());
                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    try {
                        newdate = simpleDateFormat2.format(simpleDateFormat.parse(date));
                        System.out.println(newdate);
                        Log.e((String)"Date", (String)newdate);
                    }
                    catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    mdate.setText(newdate);
                    moperator.setText(operator);
                    myCustomArray = data.getAsJsonArray("itemList");
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Add_foc_req_itemView>>() {
                    }.getType();
                    reitem = gson.fromJson(myCustomArray.toString(), listType);
                    req_adapter = new Add_foc_req_adapterView(AddDetailFocView.this, reitem);
                    mlistitem_foc.setAdapter(req_adapter);
                    mlistitem_foc.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(AddDetailFocView.this, errornya.toString(),Toast.LENGTH_LONG).show();
                    loading.dismiss();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AddDetailFocView.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();


            }
        });
    }
    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle(getString(R.string.title_cancelordertitledialog));

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage(getString(R.string.title_areyoucancelorder))
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.title_yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        Cancel();
                    }
                })
                .setNegativeButton(getString(R.string.title_no),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }
    public void Cancel(){
        gson = new Gson();
//        loading = ProgressDialog.show(AddDetailFocView.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("orderNo",noOrder);
        jsonObject.addProperty("ver",ver);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.cancelfoc(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loading.dismiss();
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                String errornya = homedata.get("errorMessage").toString();
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                if (statusnya.equals("OK")) {
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    Toast.makeText(AddDetailFocView.this, getString(R.string.title_cancelorder),Toast.LENGTH_LONG).show();
                    onBackPressed();

                }else {
                    Toast.makeText(AddDetailFocView.this, errornya.toString(),Toast.LENGTH_LONG).show();
                    loading.dismiss();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AddDetailFocView.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();


            }
        });
    }
    public boolean appInstalledOrNot(String string2) {
        PackageManager packageManager = this.getPackageManager();

        try {
            packageManager.getPackageInfo(string2, packageManager.GET_ACTIVITIES);
            installed = true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            installed = false;

        }
        return installed;
    }
}