package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.daliyreport.DailyAdapter;
import com.smartcarecenter.daliyreport.DailyItem;
import com.smartcarecenter.messagecloud.check;
import com.smartcarecenter.pmlist.PMAdapter;
import com.smartcarecenter.pmlist.PmListAdd;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.Dashboard.mshowPurchaseOrderFOC;
import static com.smartcarecenter.Dashboard.mshowPurchaseOrderPO;
import static com.smartcarecenter.Dashboard.showaddfoc;
import static com.smartcarecenter.Dashboard.showaddform;
import static com.smartcarecenter.Dashboard.showaddpo;
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;

public class DailyReportList extends AppCompatActivity {
    private static final String TAG = "FormActivity";
    public static boolean refresh = false;
    public static String valuefilter = "-";
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    DailyAdapter addFormAdapterAdapter;
    boolean internet = true;
    private LinearLayoutManager linearLayoutManager;
    public static ArrayList<DailyItem> list2;
    JsonArray listformreq;
    List<String> listnamestatus = new ArrayList();
    JsonArray liststatus;
    List<String> listvalue = new ArrayList();
    LinearLayout maddform;
    LinearLayout mback;
    ProgressBar mfooterload;
    private LinearLayoutManager mlinear;
    NestedScrollView mnested;
    TextView mrecord,mempetyreq;
    RecyclerView myitem_place;
    int page = 1;
    public static String startdate = "";
    public static  String enddate="";
    int pos = 0;
    boolean refreshscroll = true;
    String sesionid_new = "";
    int spin = 0;
    List<String> spinstatus = new ArrayList();
    int totalpage = 0;
    String totalrec = "";
    SwipeRefreshLayout mswip;
    TextView mstartdate, menddate;
    final Calendar myCalendar = Calendar.getInstance();
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report_list);
//        mrecord = findViewById(R.id.record);
    mstartdate =findViewById(R.id.startdate);
    menddate = findViewById(R.id.enddate);
    mfooterload = findViewById(R.id.footerload);
    mback = findViewById(R.id.backbtn);
    myitem_place = findViewById(R.id.formlist);
    maddform = findViewById(R.id.addform);
    mnested = findViewById(R.id.nestedscrol);
    mempetyreq = findViewById(R.id.norequest);
    mswip = findViewById(R.id.swiprefresh);
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
//            reportcd = bundle2.getString("id");
            startdate = bundle2.getString("startd");
            enddate = bundle2.getString("endd");

            mstartdate.setText(startdate);
            menddate.setText(enddate);
        }else {
            String string2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            mstartdate.setText((CharSequence)string2);
            menddate.setText((CharSequence)string2);
            startdate = string2;
            enddate = string2;
        }
    check.checklistform=1;

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            startatepick();
        }

    };
    DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            endatepick();
        }

    };
        mstartdate.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            new DatePickerDialog(DailyReportList.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    });
        menddate.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            new DatePickerDialog(DailyReportList.this, date2, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    });
    //showadd
//        if (showaddform.equals("false")){
//            maddform.setVisibility(View.GONE);
//            myitem_place.setPadding(0,0,0,0);
//        }else {
//            maddform.setVisibility(View.VISIBLE);
//            myitem_place.setPadding(0,0,0,120);
//
//        }
    //setlayout recyler
    linearLayoutManager = new LinearLayoutManager(DailyReportList.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        myitem_place.setLayoutManager(linearLayoutManager);
        myitem_place.setHasFixedSize(true);
    list2 = new ArrayList<DailyItem>();
    getSessionId();
    cekInternet();
    refreshnotif();
        if (internet){
        loadData();
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
                                        pagination();
                                    }else {
                                        mfooterload.setVisibility(View.GONE);
                                        refreshscroll=false;
                                    }
                                }
                            },500);

                        }

                    }else {

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
    //        maddform.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent gotonews = new Intent(DailyReportList.this, DetailsPM.class);
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
    private void startatepick() {
        String myFormat = "yyyy-MM-dd "; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mstartdate.setText(sdf.format(myCalendar.getTime()));
        startdate=sdf.format(myCalendar.getTime());
        loadData();
    }
    private void endatepick() {
        String myFormat = "yyyy-MM-dd "; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        menddate.setText(sdf.format(myCalendar.getTime()));
        enddate=sdf.format(myCalendar.getTime());
        loadData();
    }
    public void loadData(){
        page=1;
        mfooterload.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("page",page);
        jsonObject.addProperty("startDate",startdate);
        jsonObject.addProperty("endDate",enddate);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.dailyrlist(jsonObject);
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
                    JsonObject data = homedata.getAsJsonObject("data");
                    Log.d("dataload",data.toString());
                    totalpage = data.get("totalPage").getAsInt();
                    listformreq = data.getAsJsonArray("dailyReportList");
                    totalrec = data.get("totalRec").getAsString();
//                    mrecord.setText("Record: "+totalrec);
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<DailyItem>>() {
                    }.getType();
                    list2 = gson.fromJson(listformreq.toString(), listType);
                    addFormAdapterAdapter = new DailyAdapter(DailyReportList.this, list2);
//                    addFormAdapterAdapter.notifyDataSetChanged();
                    myitem_place.setAdapter(addFormAdapterAdapter);
                    myitem_place.setVisibility(View.VISIBLE);
                    mfooterload.setVisibility(View.GONE);
                    if (totalpage == 1) {
                        mfooterload.setVisibility(View.GONE);
                    }
                    if (totalpage == 0) {
                        mfooterload.setVisibility(View.GONE);
                    } else if (list2 != null) {
                        list2.size();
                    }
                    if (listformreq.size() == 0) {
                        myitem_place.setVisibility(View.GONE);
                        mempetyreq.setVisibility(View.VISIBLE);

                    }else {
                        myitem_place.setVisibility(View.VISIBLE);
                        mempetyreq.setVisibility(View.GONE);
                    }
                    mfooterload.setVisibility(View.GONE);
                }else {
                    sesionid();
                    mfooterload.setVisibility(View.GONE);
                    Toast.makeText(DailyReportList.this, errornya,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DailyReportList.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                mfooterload.setVisibility(View.GONE);

            }
        });
        Log.d("reqlistfr",jsonObject.toString());
    }
    public void pagination(){
        mfooterload.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("page",page);
        jsonObject.addProperty("startDate",startdate);
        jsonObject.addProperty("endDate",enddate);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.dailyrlist(jsonObject);
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
                sesionid();
                if (statusnya.equals("OK")){
                    JsonObject data = homedata.getAsJsonObject("data");
                    totalpage = data.get("totalPage").getAsInt();
                    listformreq = data.getAsJsonArray("dailyReportList");
                    totalrec = data.get("totalRec").toString();
//                    mrecord.setText("Record: "+totalrec);
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<DailyItem>>() {
                    }.getType();
                    ArrayList<DailyItem> list;
                    list=new ArrayList<>();
                    list = gson.fromJson(listformreq.toString(), listType);
                    list2.addAll(list);
                    addFormAdapterAdapter = new DailyAdapter(DailyReportList.this, list2);
                    myitem_place.setAdapter(addFormAdapterAdapter);
                    myitem_place.setVisibility(View.VISIBLE);
                    mfooterload.setVisibility(View.GONE);
                    if (totalpage == 1) {
                        mfooterload.setVisibility(View.GONE);
                    }
                    if (totalpage == 0) {
                        mfooterload.setVisibility(View.GONE);
                    } else if (list2 != null) {
                        list2.size();
                    }
                    if (listformreq.size() == 0) {
                        myitem_place.setVisibility(View.GONE);
                        mempetyreq.setVisibility(View.VISIBLE);

                    }else {
                        myitem_place.setVisibility(View.VISIBLE);
                        mempetyreq.setVisibility(View.GONE);
                    }
                    mfooterload.setVisibility(View.GONE);
//                    page++;
                    refreshscroll=true;
                }else {

                    mfooterload.setVisibility(View.GONE);
                    Toast.makeText(DailyReportList.this, errornya,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DailyReportList.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                mfooterload.setVisibility(View.GONE);

            }
        });
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) DailyReportList.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(DailyReportList.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");
        SharedPreferences show = getSharedPreferences("Show",MODE_PRIVATE);
        showaddpo = show.getString("showaddpo", "");
        showaddfoc = show.getString("showaddfoc", "");
        showaddform = show.getString("showaddform", "");
        mshowPurchaseOrderPO = show.getString("mshowPurchaseOrderPO", "");
        mshowPurchaseOrderFOC = show.getString("mshowPurchaseOrderFOC", "");


    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
                Intent gotoupdate = new Intent(DailyReportList.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(DailyReportList.this, LoginActivity.class));
            finish();
            Toast.makeText(DailyReportList.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent((Context)this, Dashboard.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
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
        }, 700);

    }
}