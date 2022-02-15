package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

//import com.kal.rackmonthpicker.MonthType;
//import com.kal.rackmonthpicker.RackMonthPicker;
//import com.kal.rackmonthpicker.listener.DateMonthDialogListener;
//import com.kal.rackmonthpicker.listener.OnCancelMonthDialogListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kal.rackmonthpicker.MonthType;
import com.kal.rackmonthpicker.RackMonthPicker;
import com.kal.rackmonthpicker.listener.DateMonthDialogListener;
import com.kal.rackmonthpicker.listener.OnCancelMonthDialogListener;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.menuhome.ChatItem;
import com.smartcarecenter.menuhome.MenuAdapter;
import com.smartcarecenter.menuhome.MenuItem;
import com.smartcarecenter.pmlist.PMAdapter;
import com.smartcarecenter.pmlist.PmListAdd;
import com.smartcarecenter.pressreport.PressAdapter;
import com.smartcarecenter.pressreport.PressList;
import com.squareup.picasso.Picasso;
//import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.lang.reflect.Type;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.smartcarecenter.AddDetailFocView.Nowfoc;
import static com.smartcarecenter.AddDetailsPoView.Nowpo;
import static com.smartcarecenter.AddRequest.requestby;
import static com.smartcarecenter.DetailsFormActivity.Nowaform;
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;
import static com.smartcarecenter.listnews.NewsAdapter.textdownload;
import static com.smartcarecenter.menuhome.MenuAdapter.chat;
import static com.smartcarecenter.menuhome.MenuAdapter.countDA;
import static com.smartcarecenter.menuhome.MenuAdapter.countPM;
import static com.smartcarecenter.menuhome.MenuAdapter.id;
import static com.smartcarecenter.menuhome.MenuAdapter.liveChatRepor;
import static com.smartcarecenter.menuhome.MenuAdapter.module;
import static com.smartcarecenter.menuhome.MenuAdapter.moduletrans;
import static com.smartcarecenter.menuhome.MenuAdapter.name;
import static com.smartcarecenter.menuhome.MenuAdapter.sessionnya;
import static com.smartcarecenter.menuhome.MenuAdapter.titlenya;
import static com.smartcarecenter.menuhome.MenuAdapter.username;
import static com.smartcarecenter.menuhome.MenuAdapter.xdaily;
import static com.smartcarecenter.menuhome.MenuAdapter.xmonth;
import static com.smartcarecenter.messagecloud.check.tokennya2;

public class MonthlyReport extends AppCompatActivity {
    LinearLayout mviewreport,mcalender;
    Spinner mdatenya;
    String sesionid_new = "";
    String MhaveToUpdate = "";
    boolean internet = true;
    String MsessionExpired = "";
    String newscd="";
    String newdate="";
    String home="";
    String cdsub = "";
    String subcat = "";
    String mcat = "";
    Boolean xdaily=false;
    Boolean xmonth = false;
    Spinner mpressspin;
    String guidpress="";
    int pos = 0;
    int pos2 = 0;
    LinearLayout mbackbtn;
    PressAdapter addFormAdapterAdapter;
//    boolean internet = true;
    private LinearLayoutManager linearLayoutManager;
    public static ArrayList<PressList> list2;
    JsonArray listformreq;
    List<String> listvaluepress = new ArrayList();
    List<String> lstnamepress = new ArrayList();
    JsonArray listformreq2;
    List<String> llistvluedate = new ArrayList();
    List<String> llistnamedate = new ArrayList();
    String period="";
    int minMonth=0;
    int maxMonth = 0;
    int monthnya=0;
    String consum = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_report);
        mviewreport = findViewById(R.id.viewreport);
        mcalender = findViewById(R.id.calender);
        mdatenya = findViewById(R.id.datenya);
        mpressspin = findViewById(R.id.pressspin);
        mbackbtn = findViewById(R.id.backbtn);

        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            xdaily = bundle2.getBoolean("daily");
            pos = bundle2.getInt("posi");
            pos2 = bundle2.getInt("posi2");
//            items = bundle2.getString("items");
            xmonth = bundle2.getBoolean("month");
//            period = bundle2.getString("date");
            if (bundle2.getString("date")!=null){
                consum = bundle2.getString("consum");

//                mdatenya.setText(consum);
                period = bundle2.getString("date");
            }else {
                Calendar today = Calendar.getInstance();
                String month = new DateFormatSymbols(Locale.ENGLISH).getMonths()[today.get(Calendar.MONTH)];
//                mdatenya.setText(month + " "+ String.valueOf(today.get(Calendar.YEAR)));
                period = String.valueOf(today.get(Calendar.YEAR))+"-"+String.valueOf(today.get(Calendar.MONTH)+1)+"-"+"01";
            }

        }
        getSessionId();
        cekInternet();
        if (internet){
            prepare();
        }else {

        }
        mbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


//        mcalender.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new RackMonthPicker(MonthlyReport.this)
//                        .setLocale(Locale.ENGLISH)
//                        .setSelectedMonth(7)
//                        .setMonthType(MonthType.TEXT)
//                        .setColorTheme(Color.parseColor("#F6C100"))
//                        .setPositiveButton(new DateMonthDialogListener() {
//                            @Override
//                            public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
//                                String montha = new DateFormatSymbols(Locale.ENGLISH).getMonths()[month-1];
//                                mdatenya.setText(montha + " "+ String.valueOf(year));
//                                period = String.valueOf(year)+"-"+String.valueOf(month)+"-"+"01";
//                                Log.d("periodd",period);
//                                monthnya=month;
//                            }
//                        })
//                        .setNegativeButton(new OnCancelMonthDialogListener() {
//                            @Override
//                            public void onCancel(AlertDialog dialog) {
//
//                            }
//                        }).show();
////                Calendar today = Calendar.getInstance();
////                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(MonthlyReport.this,
////                        new MonthPickerDialog.OnDateSetListener() {
////                            @Override
////                            public void onDateSet(int selectedMonth, int selectedYear) {
////                                String month = new DateFormatSymbols(Locale.ENGLISH).getMonths()[selectedMonth];
////                                mdatenya.setText(month + " "+ String.valueOf(selectedYear));
////                                period = String.valueOf(selectedYear)+"-"+String.valueOf(selectedMonth+1)+"-"+"01";
////                            }
////                        }, today.get(Calendar.YEAR),today.get(Calendar.MONTH));
////                        builder.setActivatedMonth(today.get(Calendar.MONTH))
////                            .setMinYear(2022)
////                            .setActivatedYear(2022)
////                            .setMaxYear(today.get(Calendar.YEAR))
//////                            .setMinMonth(minMonth-1)
//////                            .setMaxMonth(today.get(Calendar.MONTH))
//////                                .set
//////                            .setTitle("Select trading month")
//////                            .setMonthRange(today.get(Calendar.MONTH)-minMonth, today.get(Calendar.MONTH))
////
////                        // .setMaxMonth(Calendar.OCTOBER)
////                        // .setYearRange(1890, 1890)
//////                         .setMonthAndYearRange(Calendar.JANUARY, Calendar.DECEMBER, 2020, 2022)
////                        //.showMonthOnly()
////                        // .showYearOnly()
//////                                .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
//////                                    @Override
//////                                    public void onYearChanged(int year) {
//////                                        Log.d("changeyears","change: "+ String.valueOf(year));
//////                                        if (year==today.get(Calendar.YEAR)){
////////                                            builder.build().
////////                                            builder.build().;
//////                                            builder.setMaxMonth(today.get(Calendar.MONTH))
//////                                                    .setActivatedYear(year)
//////                                                    .setMinMonth(minMonth-1).build().show();
//////                                        }else {
//////                                            builder.build().dismiss();
//////                                            builder.setMaxMonth(Calendar.DECEMBER)
//////                                                    .setMinMonth(minMonth-1)
//////                                                    .setActivatedYear(year)
//////                                                    .build().getDatePicker();
//////                                        }
//////                                    }
//////                                })
////                            .build().show();
//            }
//        });
        mviewreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotonews = new Intent(MonthlyReport.this, MonthlyReportDetails.class);
                gotonews.putExtra("date",period);
                gotonews.putExtra("guid",guidpress);
                gotonews.putExtra("daily",xdaily);
                gotonews.putExtra("month",xmonth);
                gotonews.putExtra("posi",pos);
                gotonews.putExtra("posi2",pos2);
//                gotonews.putExtra("gu",pos);
                gotonews.putExtra("consum",consum);
                startActivity(gotonews);
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        mpressspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("pos",String.valueOf(position));
                guidpress = listvaluepress.get(position);
                pos = position;
                Log.d("spin",guidpress+String.valueOf(position));
                cekInternet();
//                for (int i = 0; i < listfoto.size(); ++i) {
//                    String valuefilter = listfoto.get(position);
//                    if (valuefilter.equals("null")) {
//                        mlogopay.setVisibility(GONE);
//                    }else {
//                        mlogopay.setVisibility(VISIBLE);
//                        Picasso.with(AddDetailsPo.this).load(valuefilter).into(mlogopay);
//                    }
//
//                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mdatenya.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("pos",String.valueOf(position));
                period = llistvluedate.get(position);
                pos2 = position;
                consum = llistnamedate.get(position);
                Log.d("spin",guidpress+String.valueOf(position));
                cekInternet();
//                for (int i = 0; i < listfoto.size(); ++i) {
//                    String valuefilter = listfoto.get(position);
//                    if (valuefilter.equals("null")) {
//                        mlogopay.setVisibility(GONE);
//                    }else {
//                        mlogopay.setVisibility(VISIBLE);
//                        Picasso.with(AddDetailsPo.this).load(valuefilter).into(mlogopay);
//                    }
//
//                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private DatePickerDialog createDialogWithoutDateField() {
        DatePickerDialog dpd = new DatePickerDialog(this, null, 2014, 1, 24);
        try {
            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
                        Log.i("test", datePickerField.getName());
                        if ("mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
        }
        return dpd;
    }
//    private void chooseMonthOnly() {
//        setContentView(R.layout.activity_choose_month);
//
//        findViewById(R.id.choose_month).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(MonthlyReport.this, new MonthPickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(int selectedMonth, int selectedYear) {
//
//                    }
//                }, /* activated number in year */ 3, 5);
//
//                builder.showMonthOnly()
//                        .build()
//                        .show();
//            }
//        });
//    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(MonthlyReport.this,ReportsMenu.class);
//        back.putExtra("subcd",cdsub);
        back.putExtra("daily",xdaily);
        back.putExtra("month",xmonth);
        startActivity(back);
//        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }
    public void prepare(){

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
//        jsonObject.addProperty("newsCd",newscd);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.preparereport(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("qewer",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                sesionid();
                if (statusnya.equals("OK")){
//                    reqApi();
                    JsonObject data = homedata.getAsJsonObject("data");
                    String oldadate = data.get("minPeriod").getAsString();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
                    try {
                        newdate = simpleDateFormat2.format(simpleDateFormat.parse(oldadate));
                        System.out.println(newdate);
                        Log.e((String)"Datexs", (String)newdate);
                    }
                    catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    String perioddefault = "";
                    SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("MM", Locale.ENGLISH);
                    try {
                        perioddefault = simpleDateFormat3.format(simpleDateFormat.parse(oldadate));
                        System.out.println(newdate);
                        Log.e((String)"Datexs", (String)newdate);
                    }
                    catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    minMonth= Integer.parseInt(perioddefault);
//                    mdatenya.setText(newdate);
//                    period = perioddefault;
                    Log.d("periods", String.valueOf(minMonth));
                    //press spiner
                    listformreq = data.getAsJsonArray("pressList");

                    listformreq2 = data.getAsJsonArray("listPeriod");
//                    totalrec = data.get("totalRec").toString();
//                    mrecord.setText("Record: "+totalrec);
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<PressList>>() {
                    }.getType();
                    list2 = gson.fromJson(listformreq.toString(), listType);

                    addFormAdapterAdapter = new PressAdapter(MonthlyReport.this, list2);
                    for (int j = 0; j < listformreq2.size(); ++j) {

                        llistvluedate.add(listformreq2.get(j).getAsJsonObject().get("Value").getAsString());
                        llistnamedate.add(listformreq2.get(j).getAsJsonObject().get("Text").getAsString());
                    }
                    final ArrayAdapter<String> kategori2 = new ArrayAdapter<String>(MonthlyReport.this, R.layout.spinstatus_layout7,
                            llistnamedate);
                    kategori2.setDropDownViewResource(R.layout.spinkategori);
                    kategori2.notifyDataSetChanged();
                    mdatenya.setAdapter((SpinnerAdapter) kategori2);
                    mdatenya.setSelection(pos2);
//                    String string3 = data.getAsJsonObject().get("pressList").getAsString();
//                    listvaluepress.add(string3);
//                    lstnamepress.add(string4);
//                    listfoto.add(string5);
                    for (int j = 0; j < listformreq.size(); ++j) {

                        lstnamepress.add(listformreq.get(j).getAsJsonObject().get("SNAndName").getAsString());
                        listvaluepress.add(listformreq.get(j).getAsJsonObject().get("Guid").getAsString());
                    }
                    final ArrayAdapter<String> kategori = new ArrayAdapter<String>(MonthlyReport.this, R.layout.spinstatus_layout7,
                            lstnamepress);
                    kategori.setDropDownViewResource(R.layout.spinkategori);
                    kategori.notifyDataSetChanged();
                    mpressspin.setAdapter((SpinnerAdapter) kategori);
                    mpressspin.setSelection(pos);

                }else {
                    Toast.makeText(MonthlyReport.this,errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MonthlyReport.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading.dismiss();

            }
        });
        Log.d("readnews",jsonObject.toString());
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) MonthlyReport.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(MonthlyReport.this, NoInternet.class);
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
                Intent gotoupdate = new Intent(MonthlyReport.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(MonthlyReport.this, LoginActivity.class));
            finish();
            Toast.makeText(MonthlyReport.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    public void reqApi() {
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
                Log.d("confignya",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")){

                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    JsonObject access = data.get("accessLevel").getAsJsonObject();

                    if (access.get("showDailyReport").getAsBoolean()){
                        xdaily=true;
                        if (access.get("showSPMonthlyUsageReport").getAsBoolean()){
                            xmonth=true;
                        }else {
                            xmonth=false;
                        }
                    }
                }
                else {
                    Toast.makeText(MonthlyReport.this, errornya.toString(),Toast.LENGTH_LONG).show();
                    sesionid();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MonthlyReport.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();

            }
        });
        Log.d("confignya",jsonObject.toString());
    }
}