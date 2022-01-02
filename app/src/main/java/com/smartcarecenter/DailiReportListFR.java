package com.smartcarecenter;

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
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.dailyreport2.DailyAdapter2;
import com.smartcarecenter.dailyreport2.DailyItem2;
import com.smartcarecenter.dailyreport3.DailyAdapter3;
import com.smartcarecenter.dailyreport3.DailyItem3;
import com.smartcarecenter.messagecloud.check;

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

import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;

public class DailiReportListFR extends AppCompatActivity {
    private static final String TAG = "FormActivity";
    public static boolean refresh = false;
    public static String valuefilter = "-";
    public static String startdate = "";
    public static  String enddate="";
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    DailyAdapter3 addFormAdapterAdapter;
    boolean internet = true;
    private LinearLayoutManager linearLayoutManager;
    public static ArrayList<DailyItem3> list2;
    JsonArray listformreq;
    List<String> listnamestatus = new ArrayList();
    JsonArray liststatus;
    List<String> listvalue = new ArrayList();
    LinearLayout maddform;
    LinearLayout mback;
    ProgressBar mfooterload;
    private LinearLayoutManager mlinear;
    NestedScrollView mnested;
    TextView mrecord, mempetyreq;
    RecyclerView myitem_place;
    int page = 1;
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
    public static  String noreq = "";
    String home = "";
    String guid = "";
    String username = "";
    public static  String noticket = "";
    String scrollnya = "";
    Integer xhori = 0;
    Integer yverti = 0;
    TextView msttitle;
    LinearLayout mlayoutdaterange;
    public static String hide="no";
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daili_report_list_f_r);
        mrecord = findViewById(R.id.record);
        mlayoutdaterange=findViewById(R.id.layoutdaterange);
        msttitle = findViewById(R.id.sttitle);
        mstartdate =findViewById(R.id.startdate);
        menddate = findViewById(R.id.enddate);
        mfooterload = findViewById(R.id.footerload);
        mback = findViewById(R.id.backbtn);
        myitem_place = findViewById(R.id.formlist);
        maddform = findViewById(R.id.addform);
        mnested = findViewById(R.id.nestedscrol);
        mempetyreq = findViewById(R.id.norequest);
        mswip = findViewById(R.id.swiprefresh);
        check.checklistform=1;

        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            noreq = bundle2.getString("id");
            home = bundle2.getString("home");
            guid = bundle2.getString("guid");
            username = bundle2.getString("user");
            noticket = bundle2.getString("noticket");
            valuefilter = bundle2.getString("pos");
            scrollnya = bundle2.getString("scrolbawah");
            xhori = bundle2.getInt("xhori");
            yverti = bundle2.getInt("yverti");
            hide = bundle2.getString("hide");
        }
        if (hide.equals("yes")){
//            Log.d("noreqnya",noreq);
            msttitle.setText("#"+noticket);
            mlayoutdaterange.setVisibility(View.GONE);
        }else {
            mlayoutdaterange.setVisibility(View.VISIBLE);
            msttitle.setText("");
        }
        String string2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        mstartdate.setText((CharSequence)string2);
        menddate.setText((CharSequence)string2);
        startdate = string2;
        enddate = string2;
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
                new DatePickerDialog(DailiReportListFR.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        menddate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DailiReportListFR.this, date2, myCalendar
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
        linearLayoutManager = new LinearLayoutManager(DailiReportListFR.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        myitem_place.setLayoutManager(linearLayoutManager);
        myitem_place.setHasFixedSize(true);
        list2 = new ArrayList<DailyItem3>();
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
        jsonObject.addProperty("serviceTicketCd",noticket);
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
                    Type listType = new TypeToken<ArrayList<DailyItem3>>() {
                    }.getType();
                    list2 = gson.fromJson(listformreq.toString(), listType);
                    addFormAdapterAdapter = new DailyAdapter3(DailiReportListFR.this, list2);
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
                    Toast.makeText(DailiReportListFR.this, errornya,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DailiReportListFR.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
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
        jsonObject.addProperty("serviceTicketCd",noreq);
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
                    Type listType = new TypeToken<ArrayList<DailyItem3>>() {
                    }.getType();
                    ArrayList<DailyItem3> list;
                    list=new ArrayList<>();
                    list = gson.fromJson(listformreq.toString(), listType);
                    list2.addAll(list);
                    addFormAdapterAdapter = new DailyAdapter3(DailiReportListFR.this, list2);
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
                    Toast.makeText(DailiReportListFR.this, errornya,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DailiReportListFR.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                mfooterload.setVisibility(View.GONE);

            }
        });
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) DailiReportListFR.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(DailiReportListFR.this, NoInternet.class);
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
//

    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
//                Intent gotoupdate = new Intent(DailyReportList.this, UpdateActivity.class);
//                startActivity(gotoupdate);
//                finish();
            }

        }else {
            startActivity(new Intent(DailiReportListFR.this, LoginActivity.class));
            finish();
            Toast.makeText(DailiReportListFR.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (hide.equals("yes")){
            Intent back = new Intent(DailiReportListFR.this, DetailsFormActivity.class);
//            back.putExtra("id", noreq);
            back.putExtra("home", home);
            back.putExtra("guid", guid);
            back.putExtra("user", username);
            back.putExtra("id", noreq);
            back.putExtra("noticket", noticket);
            back.putExtra("pos", valuefilter);
            back.putExtra("scrolbawah", scrollnya);
            back.putExtra("xhori", xhori);
            back.putExtra("yverti", yverti);
            startActivity(back);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
        }else {
            startActivity(new Intent((Context)this, Dashboard.class));
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
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
        }, 700);

    }
}