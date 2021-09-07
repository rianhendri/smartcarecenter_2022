package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.livechatlist.DetailsDate;
import com.smartcarecenter.livechatlist.ListLiveChatAdapter;
import com.smartcarecenter.livechatlist.ListLiveChatItem;
import com.smartcarecenter.messagecloud.check;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;

public class LiveChatList extends AppCompatActivity {
    String shouldAllowBack = "false";
    int pos1 = 0;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    Query lastQuery;
    public static ArrayList<DetailsDate>  itemchat = new ArrayList<DetailsDate>();
    boolean load = true;
    private static final String TAG = "FormActivity";
    public static boolean refresh = false;
    public static String valuefilter = "-";
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    ListLiveChatAdapter addFormAdapterAdapter;
    boolean internet = true;
    private LinearLayoutManager linearLayoutManager;
    public static ArrayList<ListLiveChatItem> list2;
    JsonArray listformreq;
    List<String> listnamestatus = new ArrayList();
    JsonArray liststatus;
    List<String> listvalue = new ArrayList();
    LinearLayout maddform;
    LinearLayout mback;
    public static ProgressBar mfooterload;
    private LinearLayoutManager mlinear;
    NestedScrollView mnested;
    TextView mrecord,mempetyreq;
    Spinner mstatus_spin;
    public static RecyclerView myitem_place;
    int page = 1;
    int pos = 0;
    boolean refreshscroll = true;
    String sesionid_new = "";
    int spin = 0;
    List<String> spinstatus = new ArrayList();
    int totalpage = 0;
    String totalrec = "";
    SwipeRefreshLayout mswip;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_chat_list);
        mrecord = findViewById(R.id.record);
        mfooterload = findViewById(R.id.footerload);
        mback = findViewById(R.id.backbtn);
        myitem_place = findViewById(R.id.listapproval);
        maddform = findViewById(R.id.addform);
        mstatus_spin = findViewById(R.id.spinstatus);
        mnested = findViewById(R.id.nestedscrol);
        mempetyreq = findViewById(R.id.norequest);
        mswip = findViewById(R.id.swiprefresh);
        check.checklistform=1;
        //showadd
//        if (showaddform.equals("false")){
//            maddform.setVisibility(View.GONE);
//            myitem_place.setPadding(0,0,0,0);
//        }else {
//            maddform.setVisibility(View.VISIBLE);
//            myitem_place.setPadding(0,0,0,120);
//
//        }
        Bundle bundle2 = this.getIntent().getExtras();
        if (bundle2 != null) {
            valuefilter = bundle2.getString("filter");

//            Toast.makeText(DetailsNotification.this, guid,Toast.LENGTH_LONG).show();
        }
        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(LiveChatList.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        myitem_place.setLayoutManager(linearLayoutManager);
        myitem_place.setHasFixedSize(true);
        list2 = new ArrayList<ListLiveChatItem>();
        getSessionId();
        cekInternet();
//        refreshnotif();
        if (internet){
            reqApi();
            loadData();
//            loadSpin();
        }else {

        }
        mnested.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {

                if(nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1) != null)
                {
                    if ((i1 >= (nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1)
                            .getMeasuredHeight() - nestedScrollView.getMeasuredHeight()))
                            && i1 > i3)
                    {
                        cekInternet();
                        if (internet){
                            if (refreshscroll){
                                page++;
                                mfooterload.setVisibility(View.VISIBLE);
                                refreshscroll=false;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable()
                                {
                                    @Override
                                    public void run() {
                                        if (page <=totalpage){
                                            myitem_place.setLayoutFrozen(true);
//                                            pagination();
                                        }else {
                                            mfooterload.setVisibility(View.GONE);
                                            refreshscroll=false;
                                        }
                                    }
                                },500);

                            }

                        }else {
                            Toast.makeText(LiveChatList.this, "Data Sudah di tampilkan semua", Toast.LENGTH_SHORT).show();
//                                    mfooterload.setVisibility(View.GONE);
//                                    mdatahabis.setVisibility(View.GONE);
//                                    mrefreshcoba.setVisibility(View.VISIBLE);

                        }





                    }
                }

            }
        });
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mstatus_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshscroll = true;
                page=1;
                cekInternet();
                for (int i = 0; i < listvalue.size(); ++i) {
                    valuefilter = listvalue.get(position);
                    if (internet) {
                        loadData();
                    }else {

                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        maddform.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent gotonews = new Intent(ServiceTicket.this, AddRequest.class);
//                startActivity(gotonews);
//                finish();
//                overridePendingTransition(R.anim.right_in, R.anim.left_out);
//            }
//        });
        int color = getResources().getColor(R.color.colorPrimary);
        mswip.setColorSchemeColors(color);
        mswip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                cekInternet();

                if (internet){

                    new Handler().postDelayed(new Runnable() {
                        @Override public void run() {

                            // Berhenti berputar/refreshing

                            mswip.setRefreshing(false);
                            loadData();

                            // fungsi-fungsi lain yang dijalankan saat refresh berhenti

                        }
                    }, 500);

                }else {
//                    mswip.setEnabled(false);
//                    mswip.setRefreshing(false);
//                    mcontent.setVisibility(View.GONE);


                }
            }
        });
    }
    public void loadData(){
        itemchat.clear();
        list2.clear();
        page=1;
        if (load){
            load = false;
        }else {
            mfooterload.setVisibility(View.VISIBLE);
            load = true;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("page",page);
        jsonObject.addProperty("status",valuefilter);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.livechastlist(jsonObject);
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
                if (statusnya.equals("OK")){
                    shouldAllowBack = "true";
                    JsonObject data = homedata.getAsJsonObject("data");
                    totalpage = 0;
                    listformreq = data.getAsJsonArray("chatList");
                    totalrec = "0";
                    mrecord.setText("Record: "+totalrec);
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<ListLiveChatItem>>() {
                    }.getType();
                    list2 = gson.fromJson(listformreq.toString(), listType);
                    addFormAdapterAdapter = new ListLiveChatAdapter(LiveChatList.this, list2);
//                    addFormAdapterAdapter.notifyDataSetChanged();


                    if (listformreq.toString().equals("[]")){
                        mfooterload.setVisibility(View.GONE);

                    }else {
                        lastQuery = databaseReference.child("chat").child(list2.get(pos1).getLiveChatID()).child("listchat").orderByKey().limitToLast(1);
                        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                dataSnapshot.exists();
                                if (dataSnapshot.exists()){
                                    for(DataSnapshot ds: dataSnapshot.getChildren())
                                    {
                                        DetailsDate fetchDatalist=ds.getValue(DetailsDate.class);
//                        fetchDatalist.setKey(ds.getKey());
                                        itemchat.add(fetchDatalist);

                                        if (list2.size()==pos1+1){
                                            list2.get(pos1).setDatea(itemchat.get(pos1).getDate());
                                            list2.get(pos1).setTime(itemchat.get(pos1).getTime());
                                            Collections.sort(list2, new Comparator<ListLiveChatItem>() {
                                                DateFormat f = new SimpleDateFormat("d MMM yyyy HH:mm");
                                                @Override
                                                public int compare(ListLiveChatItem lhs, ListLiveChatItem rhs) {
                                                    try {
                                                        Log.d("compare","successs");

                                                        return f.parse(lhs.getDatea()+" "+lhs.getTime()).compareTo(f.parse(rhs.getDatea()+" "+rhs.getTime()));

                                                    } catch (ParseException e) {
                                                        Log.d("compare",e.toString());
                                                        throw new IllegalArgumentException(e);
                                                    }
                                                }
                                            });
                                            Collections.sort(itemchat, new Comparator<DetailsDate>() {
                                                DateFormat f = new SimpleDateFormat("d MMM yyyy HH:mm");
                                                @Override
                                                public int compare(DetailsDate lhs, DetailsDate rhs) {
                                                    try {
                                                        Log.d("compare","successs");

                                                        return f.parse(lhs.getDate()+" "+lhs.getTime()).compareTo(f.parse(rhs.getDate()+" "+rhs.getTime()));

                                                    } catch (ParseException e) {
                                                        Log.d("compare",e.toString());
                                                        throw new IllegalArgumentException(e);
                                                    }
                                                }
                                            });
                                            Collections.reverse(list2);
                                            Collections.reverse(itemchat);
                                            pos1 =0;
                                            myitem_place.setAdapter(addFormAdapterAdapter);
                                            myitem_place.setVisibility(View.VISIBLE);
                                            mfooterload.setVisibility(View.GONE);

                                        }else {
                                            list2.get(pos1).setDatea(itemchat.get(pos1).getDate());
                                            list2.get(pos1).setTime(itemchat.get(pos1).getTime());
                                            pos1 +=1;
                                            showdetail();

                                        }
                                    }
//                recyclerView.scrollToPosition(adapterchat.getItemCount());
                                }else{
                                    if (list2.size()==pos1+1){
                                        pos1 =0;
                                        myitem_place.setAdapter(addFormAdapterAdapter);
                                        myitem_place.setVisibility(View.VISIBLE);
                                        mfooterload.setVisibility(View.GONE);

                                    }else {
                                        pos1 +=1;
                                        showdetail();

                                    }
                                    mfooterload.setVisibility(View.GONE);
                                }
//                String message = dataSnapshot.child("message").getValue().toString();
//                myItem.get(i).setDetails(message);
//                myviewholder.mdetailchat.setText(myItem.get(i).getDetails());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Handle possible errors.

                                mfooterload.setVisibility(View.GONE);


                            }
                        });
                    }


                }else {
                    sesionid();
                    mfooterload.setVisibility(View.GONE);

                    if (MsessionExpired.equals("true")) {
                        Toast.makeText(LiveChatList.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(LiveChatList.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                mfooterload.setVisibility(View.GONE);
                shouldAllowBack = "true";

            }
        });
        Log.d("livechatlistreq",jsonObject.toString());
    }
    public void showdetail(){
        lastQuery = databaseReference.child("chat").child(list2.get(pos1).getLiveChatID()).child("listchat").orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.exists();
                if (dataSnapshot.exists()){
                    for(DataSnapshot ds: dataSnapshot.getChildren())
                    {
                        DetailsDate fetchDatalist=ds.getValue(DetailsDate.class);
//                        fetchDatalist.setKey(ds.getKey());
                        itemchat.add(fetchDatalist);

                        if (list2.size()==pos1+1){
                            list2.get(pos1).setDatea(itemchat.get(pos1).getDate());
                            list2.get(pos1).setTime(itemchat.get(pos1).getTime());
                            Collections.sort(list2, new Comparator<ListLiveChatItem>() {
                                DateFormat f = new SimpleDateFormat("d MMM yyyy HH:mm");
                                @Override
                                public int compare(ListLiveChatItem lhs, ListLiveChatItem rhs) {
                                    try {
                                        Log.d("compare","successs");

                                        return f.parse(lhs.getDatea()+" "+lhs.getTime()).compareTo(f.parse(rhs.getDatea()+" "+rhs.getTime()));

                                    } catch (ParseException e) {
                                        Log.d("compare",e.toString());
                                        throw new IllegalArgumentException(e);
                                    }
                                }
                            });
                            Collections.sort(itemchat, new Comparator<DetailsDate>() {
                                DateFormat f = new SimpleDateFormat("d MMM yyyy HH:mm");
                                @Override
                                public int compare(DetailsDate lhs, DetailsDate rhs) {
                                    try {
                                        Log.d("compare","successs");

                                        return f.parse(lhs.getDate()+" "+lhs.getTime()).compareTo(f.parse(rhs.getDate()+" "+rhs.getTime()));

                                    } catch (ParseException e) {
                                        Log.d("compare",e.toString());
                                        throw new IllegalArgumentException(e);
                                    }
                                }
                            });
                            Collections.reverse(list2);
                            Collections.reverse(itemchat);
                            pos1 =0;
                            myitem_place.setAdapter(addFormAdapterAdapter);
                            myitem_place.setVisibility(View.VISIBLE);
                            mfooterload.setVisibility(View.GONE);
                        }else {
                            if (pos1==list2.size()){


                            }else {
                                list2.get(pos1).setDatea(itemchat.get(pos1).getDate());
                                list2.get(pos1).setTime(itemchat.get(pos1).getTime());
                                pos1 +=1;
                                showdetail();
                            }

                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) LiveChatList.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(LiveChatList.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");
        SharedPreferences show = getSharedPreferences("Show",MODE_PRIVATE);
//        showaddpo = show.getString("showaddpo", "");
//        showaddfoc = show.getString("showaddfoc", "");
//        showaddform = show.getString("showaddform", "");
//        mshowPurchaseOrderPO = show.getString("mshowPurchaseOrderPO", "");
//        mshowPurchaseOrderFOC = show.getString("mshowPurchaseOrderFOC", "");


    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
//                Intent gotoupdate = new Intent(ServiceTicket.this, UpdateActivity.class);
//                startActivity(gotoupdate);
//                finish();
            }

        }else {
            startActivity(new Intent(LiveChatList.this, LoginActivity.class));
            finish();
//            Toast.makeText(ServiceTicket.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {

        if (shouldAllowBack.equals("true")) {
            super.onBackPressed();
            itemchat.clear();
            startActivity(new Intent((Context)this, Dashboard.class));
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
        } else {
//            doSomething();
            Log.d("waitsd","wait");
        }

    }
    public  void refreshnotif() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!refresh){
                    refreshnotif();
//
//
                }else {

                    loadData();
//                    Toast.makeText(FormActivity.this, "true",Toast.LENGTH_SHORT).show();
                    refresh=false;
                }

            }
        }, 100);

    }
    public void reqApi() {
//        loading = ProgressDialog.show(this, "", "", true);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONconfig(jsonObject);
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
//                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")) {

//                    loading.dismiss();
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");

                }else {

                    sesionid();

                    Toast.makeText(LiveChatList.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
//                mrefresh.setVisibility(View.VISIBLE);
//                mcheck.setVisibility(View.GONE);
                Toast.makeText(LiveChatList.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading.dismiss();
            }
        });

    }
}