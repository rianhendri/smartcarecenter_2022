package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.Add_Foc_request.Add_foc_req_adapter;
import com.smartcarecenter.Add_foc_Item_list_model.Add_foc_list_adapter;
import com.smartcarecenter.Add_foc_Item_list_model.Add_foc_list_item;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.supportservice.AddFormAdapter;
import com.smartcarecenter.supportservice.AddFromItem;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.AddDetailFoc.imagefile;
import static com.smartcarecenter.AddDetailFoc.lastimpresi;
import static com.smartcarecenter.AddDetailFoc.mpressId;

import static com.smartcarecenter.AddDetailFoc.notes1;
import static com.smartcarecenter.AddDetailFoc.photo_location;
import static com.smartcarecenter.FormActivity.valuefilter;
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;

public class Add_Foc_Item_List extends AppCompatActivity {
    LinearLayout mback;
    EditText msearch;
    RecyclerView mlistFoc_item;
    public static JsonArray listsn;
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    String akunid = "";
    Boolean internet = false;
    ProgressBar loading;
    LinearLayout mlaytotal;
    TextView mdate,mstartimpresi,moperator,mno_order,mtotalitem,msend,mtotalqty;
    EditText mlastimpresi;
    Integer previmpressvlaue = 100;
    LinearLayout mreadyfoto;
    Spinner msn;
    RecyclerView mlistitem_foc;
    String sesionid_new = "";
    List<String> snid = new ArrayList();
    List<String> snname = new ArrayList();
    List<Integer> previmpression = new ArrayList();
    JsonArray getArrayItem;
    ArrayList<Add_foc_list_item> list2;
    private LinearLayoutManager linearLayoutManager;
    Add_foc_list_adapter addfocitemadapter;
    public static String stsinac="";
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__foc__item__list);
        mback = findViewById(R.id.backbtn);
        msearch = findViewById(R.id.searchitem);
        mlistFoc_item = findViewById(R.id.listadditemfoc);
        loading = findViewById(R.id.loadingfooter);
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            mpressId = bundle2.getString("pressId");
            lastimpresi = bundle2.getString("lastImpres");
            imagefile = (File) bundle2.get("foto");
            photo_location = (Uri) bundle2.get("uri");
            notes1 = bundle2.getString("notesa");
        }
        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(Add_Foc_Item_List.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        mlistFoc_item.setLayoutManager(linearLayoutManager);
        mlistFoc_item.setHasFixedSize(true);
        list2 = new ArrayList<Add_foc_list_item>();
        getSessionId();
        cekInternet();
        if (internet){
            LoadItem();
        }
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        msearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) Add_Foc_Item_List.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;
        }else {
            internet=false;
            Intent noconnection = new Intent(Add_Foc_Item_List.this, NoInternet.class);
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
                Intent gotoupdate = new Intent(Add_Foc_Item_List.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(Add_Foc_Item_List.this, LoginActivity.class));
            finish();
            Toast.makeText(Add_Foc_Item_List.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(Add_Foc_Item_List.this,AddDetailFoc.class);
        back.putExtra("pos",valuefilter);
        back.putExtra("pressId",mpressId);
        startActivity(back);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
    public void LoadItem(){
        loading.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("pressGuid",mpressId);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.list_add_item_foc(jsonObject);
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

                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    Add_foc_req_adapter.matrixmeter=data.get("matrixMeter").getAsDouble();
//                    Toast.makeText(Add_Foc_Item_List.this, String.valueOf(Add_foc_req_adapter.matrixmeter),Toast.LENGTH_LONG).show();
                    getArrayItem = data.getAsJsonArray("list");
                    stsinac = data.get("inactiveStatusName").getAsString();
                    Log.d("listfoc",getArrayItem.toString());
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Add_foc_list_item>>() {
                    }.getType();
                    list2 = gson.fromJson(getArrayItem.toString(), listType);
                    addfocitemadapter = new Add_foc_list_adapter(Add_Foc_Item_List.this, list2);
                    mlistFoc_item.setAdapter(addfocitemadapter);
                    mlistFoc_item.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                }else {
                    Toast.makeText(Add_Foc_Item_List.this, errornya.toString(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Toast.makeText(Add_Foc_Item_List.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();


            }
        });
        Log.d("focjson",jsonObject.toString());
    }
    private void filter(String text) {
        if (list2.toString().equals("[]")){
            Log.d("filter",list2.toString());

        }else {
            ArrayList<Add_foc_list_item> filteredList = new ArrayList<>();
            for (Add_foc_list_item item : list2) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }else if (item.getCategoryName().toLowerCase().contains(text.toLowerCase())){
                    filteredList.add(item);
                }else if (item.getItemCd().toLowerCase().contains(text.toLowerCase())){
                    filteredList.add(item);
                }else if (item.getStsActiveInfo().toLowerCase().contains(text.toLowerCase())){
                    filteredList.add(item);
                }
            }
            Log.d("filter",list2.toString());
            addfocitemadapter.filterList(filteredList);
        }

    }
}