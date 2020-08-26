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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.smartcarecenter.Add_Foc_request.Add_foc_req_adapter;
import com.smartcarecenter.Add_Foc_request.Add_foc_req_item;
import com.smartcarecenter.Add_foc_Item_list_model.Add_foc_list_item;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;

import java.io.File;
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

public class AddDetailFoc extends AppCompatActivity {
    public static JsonArray listsn;
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    String akunid = "";
    Boolean internet = false;
    ProgressDialog loading;
    ImageView mback;
    int pos = 0;
    public static LinearLayout mlaytotal;
    public static TextView mdate,mstartimpresi,moperator,mno_order,mtotalitem,msend,mtotalqty,mnoitem;
    EditText mlastimpresi;
    public static String mpressId = "";
    String mpressId2 = "";
    Integer previmpressvlaue = 100;
    LinearLayout madd_item;
    Spinner msn;
    DatabaseReference reference;
    public static RecyclerView mlistitem_foc;
    String sesionid_new = "";
    List<String> snid = new ArrayList();
    List<String> snname = new ArrayList();
    List<Integer> previmpression = new ArrayList();
    //list item add
    public static ArrayList<Add_foc_req_item> reitem;
    Add_foc_req_adapter req_adapter;
    private LinearLayoutManager linearLayoutManager;
    String jsonarayitem = "";
    JsonArray myCustomArray;
    Gson gson;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail_foc);
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

        cekInternet();
        getSessionId();
        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(AddDetailFoc.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        mlistitem_foc.setLayoutManager(linearLayoutManager);
        mlistitem_foc.setHasFixedSize(true);
        reitem = new ArrayList<Add_foc_req_item>();
        if (internet){
            LoadPress();
        }else {

        }
        if (listpoact.isEmpty()){
            mlaytotal.setVisibility(View.GONE);
            mnoitem.setVisibility(View.VISIBLE);
            mlistitem_foc.setVisibility(View.GONE);
        }else {
            mlaytotal.setVisibility(View.VISIBLE);
            mnoitem.setVisibility(View.GONE);
            mlistitem_foc.setVisibility(View.VISIBLE);
        }
        ////////////////// kalo item sama quatity di replace////
        for (int i = 0; i < listpoact.size(); i++) {
            for (int j = i + 1; j < listpoact.size(); j++) {
                if (listpoact.get(i).getItemcd().equals(listpoact.get(j).getItemcd())) {
//                    listpoact.get(i).setQuantity(listpoact.get(j).getQuantity());
//                    listpoact.get(i).setHarga(listpoact.get(j).getHarga());
                    listpoact.remove(j);
                    j--;
//                    Log.d("remove", String.valueOf(cartModels.size()));

                }
            }

        }

        reitem.addAll(listpoact);
        Gson gson = new GsonBuilder().create();
        myCustomArray = gson.toJsonTree(reitem).getAsJsonArray();
        jsonarayitem = myCustomArray.toString();

        listpoact.clear();
        Log.d("sizecart_11", String.valueOf(reitem.size()));
        Log.d("sizecart_22", String.valueOf(listpoact.size()));
////////////////////// adapter di masukan ke recyler//
        req_adapter = new Add_foc_req_adapter(this, reitem);
        mlistitem_foc.setAdapter(req_adapter);
        String string2 = new SimpleDateFormat("d-MM-yyyy", Locale.getDefault()).format(new Date());
        mdate.setText((CharSequence)string2);
        msn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    mpressId = snid.get(position);
                    previmpressvlaue = previmpression.get(position);
                    mstartimpresi.setText(String.valueOf(previmpressvlaue));
//                    Toast.makeText(AddDetailFoc.this, mpressId,Toast.LENGTH_SHORT).show();
//                Bundle bundle2 = getIntent().getExtras();
//                if (bundle2 != null) {
//                    mpressId = bundle2.getString("pressId");
//                }
                if (pos==position){

                }else {
                    listpoact.clear();
                    reitem.clear();
                    req_adapter = new Add_foc_req_adapter(AddDetailFoc.this, reitem);
                    mlistitem_foc.setAdapter(req_adapter);
                    mlaytotal.setVisibility(View.GONE);
                    mnoitem.setVisibility(View.VISIBLE);
                    mlistitem_foc.setVisibility(View.GONE);
                }
//
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        madd_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoaddfoc = new Intent(AddDetailFoc.this, Add_Foc_Item_List.class);
                gotoaddfoc.putExtra("pressId",mpressId);
                startActivity(gotoaddfoc);
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                listpoact.addAll(reitem);
            }
        });
        msend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internet){
                    if (mlastimpresi.getText().toString().length()!=0){
                        showDialog();
                    }else {
                        mlastimpresi.setError(getString(R.string.title_requiredimpressi));
                        Toast.makeText(AddDetailFoc.this, getString(R.string.title_requiredimpressi),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) AddDetailFoc.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(AddDetailFoc.this, NoInternet.class);
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
                Intent gotoupdate = new Intent(AddDetailFoc.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(AddDetailFoc.this, LoginActivity.class));
            finish();
            Toast.makeText(AddDetailFoc.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(AddDetailFoc.this,FreeofchargeActivity.class);
        back.putExtra("pos",valuefilter);
        startActivity(back);
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
    public void LoadPress(){
        loading = ProgressDialog.show(AddDetailFoc.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, "http://api.smartcarecenter.id/");
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONpresslist(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                String errornya = homedata.get("errorMessage").toString();
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                if (statusnya.equals("OK")) {
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    listsn=data.getAsJsonArray("pressList");
                    for (int i = 0; i < listsn.size(); ++i) {
                        JsonObject jsonObject2 = (JsonObject)listsn.get(i);
                        String string4 = jsonObject2.getAsJsonObject().get("name").getAsString();
                        String string5 = jsonObject2.getAsJsonObject().get("id").getAsString();
                        Integer previmpress = jsonObject2.getAsJsonObject().get("previousImpression").getAsInt();
                        snname.add(string4);
                        snid.add(string5);

                        for (int j = 0; j < snid.size(); ++j) {
                            if (snid.get(i).equals(mpressId)){
                                pos=j;
                            }
                        }
                        previmpression.add(previmpress);
                        ArrayAdapter arrayAdapter = new ArrayAdapter(AddDetailFoc.this, R.layout.spinstatus_layout, snname);
                        arrayAdapter.setDropDownViewResource(R.layout.spinkategori);
                        arrayAdapter.notifyDataSetChanged();
                        msn.setAdapter(arrayAdapter);
                        msn.setSelection(pos);
                        loading.dismiss();
                    }
                }else {
                    Toast.makeText(AddDetailFoc.this, errornya.toString(),Toast.LENGTH_LONG).show();
                    loading.dismiss();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AddDetailFoc.this, t.toString(),Toast.LENGTH_LONG).show();
                cekInternet();


            }
        });
    }
    public void sendData(){
        gson = new Gson();
        loading = ProgressDialog.show(AddDetailFoc.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("pressGuid",mpressId);
        jsonObject.addProperty("currentImpression",2222);
        jsonObject.add("items", myCustomArray);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, "http://api.smartcarecenter.id/");
        Call<JsonObject> panggilkomplek = jsonPostService.sendData(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                String errornya = homedata.get("errorMessage").toString();
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                if (statusnya.equals("OK")) {
                    loading.dismiss();
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    String ok=data.get("message").getAsString();
                    Toast.makeText(AddDetailFoc.this, ok,Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else {
                    Toast.makeText(AddDetailFoc.this, errornya.toString(),Toast.LENGTH_LONG).show();
                    loading.dismiss();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AddDetailFoc.this, t.toString(),Toast.LENGTH_LONG).show();
                cekInternet();


            }
        });
    }
    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle(getString(R.string.title_sendorder));

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage(getString(R.string.title_submitorder))
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.title_yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        sendData();
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
}