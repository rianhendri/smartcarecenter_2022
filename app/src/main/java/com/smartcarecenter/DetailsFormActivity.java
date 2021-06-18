package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.messagecloud.FirebaseMessaging;
import com.smartcarecenter.messagecloud.check;
import com.smartcarecenter.serviceticket.ServiceTicketAdapter;
import com.smartcarecenter.serviceticket.ServicesTicketItem;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.smartcarecenter.FormActivity.refresh;
import static com.smartcarecenter.FormActivity.list2;
import static com.smartcarecenter.FormActivity.valuefilter;
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;


public class DetailsFormActivity extends AppCompatActivity {
    boolean inforeopen = true;
    EditText mreasonnya;
    public static String noreq = "";
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    boolean internet = true;
    private LinearLayoutManager linearLayoutManager;
    ArrayList<ServicesTicketItem> listticket;
    ServiceTicketAdapter ticketadapter;
    ProgressDialog loading;
    String mallowToCancel = "";
    String mallowtoconfirm = "";
    ImageView mbanner;
    LinearLayout mcancel, mconfirm, mcs, mbackgroundalert,mback, mreopenbtn;
    TextView mcreatedate, mdate, mdeskription, missu, moperator, mreqno, mservicetype, msn, mstatusdetail,
            mstid, mtitle, munitcategory, mlocation, mtextalert, mrequestby, mreopeninfo;
    String mdateapi = "";
    String mdeskriptionapi = "";
    String mformRequestCd = "";
    String mreopen = "";
    ImageView mimgpopup;
    LinearLayout mlayoutticket,mlayoutunit1, mlayoutunit2, mlayoutunit3, mreinfolay;
    private LinearLayoutManager mlinear;
    String mphotoURL = "";
    String mpressGuid = "";
    String mpressName = "";
    String mrequestedBy = "";
    String mrequestedDateTime = "";
    String mserviceTicketCd = "";
    String xlocation = "";
    JsonArray mserviceTicketHistory;
    JsonArray massistengineer;
    RecyclerView mservice_layout;
    String mstatus = "";
    String mstatusColorCode = "";
    String mstatusName = "";
    String noticket = "";
    String sesionid_new = "";
    String guid = "";
    String mreason="";
    public static String username = "";
    boolean installed= true;
    //timer
    public static String assist="";
    public static int seconds = 0;
    public static String usetime="";
    private boolean running;
    //timer
    private static int START_TIME_IN_MILLIS = 0;
    private TextView mtimerconfirm;
    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    public static String Nowaform = "0";
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_form);
        missu = findViewById(R.id.issucategroy);
        mservicetype = findViewById(R.id.servicetype);
        mcreatedate =findViewById(R.id.createdate);
        mdate=findViewById(R.id.datereq);
        mdeskription=findViewById(R.id.descrip);
        moperator=findViewById(R.id.operator);
        mreqno=findViewById(R.id.request_no);
        msn=findViewById(R.id.sn);
        mstatusdetail=findViewById(R.id.statusdetail);
        mstid=findViewById(R.id.stid);
        mtitle=findViewById(R.id.title);
        munitcategory=findViewById(R.id.unitcategory);
        mcancel=findViewById(R.id.laycancel);
        mconfirm=findViewById(R.id.confirm);
        mcs=findViewById(R.id.chatcspo);
        mback=findViewById(R.id.backbtn);
        mbanner=findViewById(R.id.imgbanner);
        mlayoutunit1=findViewById(R.id.layoutunit1);
        mlayoutunit2=findViewById(R.id.layoutunit2);
        mlayoutunit3=findViewById(R.id.layoutunit3);
        mlayoutticket=findViewById(R.id.layoutticket);
        mservice_layout=findViewById(R.id.serviceticket);
        mlocation = findViewById(R.id.locationsn);
        mtextalert = findViewById(R.id.textalert);
        mbackgroundalert = findViewById(R.id.backgroundalert);
        mtimerconfirm = findViewById(R.id.timerconfirm);
        mreopenbtn = findViewById(R.id.reopen);
        mrequestby = findViewById(R.id.requestby);
        mreinfolay = findViewById(R.id.reinfolay);
        mreopeninfo = findViewById(R.id.reopeninfo);
        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(DetailsFormActivity.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        mservice_layout.setLayoutManager(linearLayoutManager);
        mservice_layout.setHasFixedSize(true);
        listticket = new ArrayList();

        //getsessionId
        seconds=0;
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            noreq = bundle2.getString("id");
            guid = bundle2.getString("guid");
            username = bundle2.getString("user");
            noticket = bundle2.getString("noticket");
            valuefilter = bundle2.getString("pos");

        }
        getSessionId();
        cekInternet();
        if (internet){
            loadData();
            if (guid==null){

            }else {
                ReadNotif();
            }
        }else {

        }
        String TAG = "FirebaseMessaging";
        Log.d(TAG,"noreq:"+noreq);



        mback.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onBackPressed();
           }
       });
        mcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            appInstalledOrNot("com.whatsapp");
                if (installed) {
                    String message = "Hi Support, "+getString(R.string.title_tanyacs)+noreq;
                     Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(android.net.Uri.parse(
                            String.format("https://api.whatsapp.com/send?phone=%s&text=%s", Nowaform, message)));
                    startActivity(intent);
                }else {
                    Toast.makeText(DetailsFormActivity.this,"Whatsapp blum di instal", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekInternet();
                if (internet){
                    showDialog();
                }else {
                }
            }
        });
        mconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekInternet();
                if (internet){
                    Intent gotorating = new Intent(DetailsFormActivity.this, RatingStar.class);
                    gotorating.putExtra("id", noreq);
                    gotorating.putExtra("noticket", noticket);
                    gotorating.putExtra("user", username);
                    startActivity(gotorating);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    finish();
                }else {

                }
            }
        });
        mbanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(DetailsFormActivity.this, R.style.TransparentDialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.popupfoto);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                mimgpopup = dialog.findViewById(R.id.imagepopup);
                Picasso.with(DetailsFormActivity.this).load(mphotoURL).into(mimgpopup);
                dialog.show();
            }
        });
        //REOPEN
        mreopenbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotorating = new Intent(DetailsFormActivity.this, ReopenCase.class);
                gotorating.putExtra("id", noreq);
                gotorating.putExtra("noticket", noticket);
                gotorating.putExtra("user", username);
                startActivity(gotorating);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                finish();
//                showDialogreopen();
            }
        });
        updateCountDownText();

    }
    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle(getString(R.string.title_deleteReq));
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        View v = getLayoutInflater().inflate(R.layout.item_cancel, null);
        mreasonnya=v.findViewById(R.id.reasondes);
        mreasonnya.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mreason = mreasonnya.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // set pesan dari dialog
        AlertDialog d = new AlertDialog.Builder(DetailsFormActivity.this)
                .setView(v)
                .setMessage(R.string.title_canelreq)
                .setPositiveButton(getString(R.string.title_yes), null) //Set to null. We override the onclick
                .setNegativeButton(getString(R.string.title_no), null)
                .create();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            alertDialogBuilder
//                    .setMessage(getString(R.string.title_canelreq))
//                    .setView(v)
//                    .setIcon(R.mipmap.ic_launcher)
//                    .setCancelable(false)
//                    .setPositiveButton(getString(R.string.title_yes),null)
//                    .setNegativeButton(getString(R.string.title_no),new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // jika tombol ini diklik, akan menutup dialog
//                            // dan tidak terjadi apa2
//                            dialog.cancel();
//                        }
//                    });
//        }

        // membuat alert dialog dari builder
//        AlertDialog alertDialog = alertDialogBuilder.create();
//
//        // menampilkan alert dialog
        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
                Button C = d.getButton(AlertDialog.BUTTON_NEGATIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (mreasonnya.length()==0){
                            Toast.makeText(DetailsFormActivity.this, getString(R.string.title_reasonrequired),Toast.LENGTH_LONG).show();
                        }else {
                            cancelreq();
                            d.dismiss();
                        }
                    }
                });
                C.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
            }
        });
        d.show();

    }
    private void showDialogreopen() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle(getString(R.string.title_reopendialod));

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage(getString(R.string.title_dialogreopen))
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.title_yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        reopenreq();
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
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) DetailsFormActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(DetailsFormActivity.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void loadData(){
        loading = ProgressDialog.show(DetailsFormActivity.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("formRequestCd",noreq);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
//        Toast.makeText(DetailsFormActivity.this,jsonObject.toString(), Toast.LENGTH_SHORT).show();
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONgetform(jsonObject);
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

                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");

                    inforeopen = data.get("allowToReopenCase").getAsBoolean();
                    if (inforeopen){
                        mreinfolay.setVisibility(View.VISIBLE);
                        mreopeninfo.setText(data.get("reopenCaseInformation").getAsString());
                    }else {
                        mreinfolay.setVisibility(View.GONE);
                    }
                    String showalert = data.get("showMessage").toString();
                    if (data.get("confirmCountDown").toString().equals("false")){

                    }else{
                        int hour = data.get("confirmHours").getAsInt();
                        int minutes = data.get("confirmMinutes").getAsInt();
                        int secondss = data.get("confirmSeconds").getAsInt();
                        int totalsecond = (hour*60*60*1000)+(minutes*60*1000)+(secondss*1000);
                        START_TIME_IN_MILLIS = totalsecond;
                        mTimeLeftInMillis=START_TIME_IN_MILLIS;
                        //timer
//                        Toast.makeText(DetailsFormActivity.this, String.valueOf(mTimeLeftInMillis),Toast.LENGTH_LONG).show();
                        startTimer();
                        updateCountDownText();
                    }
                    //check alert
                    if (showalert.equals("true")){

                        String text = data.get("messageText").getAsString();
                        String textcolor = data.get("messageTextColor").getAsString();
                        String bgcolor = data.get("messageBackgroundColor").getAsString();

                        GradientDrawable shape =  new GradientDrawable();
                        shape.setCornerRadius( 15 );
                        shape.setColor(Color.parseColor("#"+bgcolor));

                        mbackgroundalert.setVisibility(View.VISIBLE);
                        mtextalert.setTextColor(Color.parseColor("#"+textcolor));
                        mbackgroundalert.setBackground(shape);
                        if (Build.VERSION.SDK_INT >= 24) {
                            mtextalert.setText((CharSequence)Html.fromHtml((String)text, Html.FROM_HTML_MODE_COMPACT));
                            mtextalert.setMovementMethod(LinkMovementMethod.getInstance());
                        } else {
                            mtextalert.setText((CharSequence)Html.fromHtml((String)text));
                            mtextalert.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                    }else {
                        mbackgroundalert.setVisibility(View.GONE);
                    }
                    //timer
                    usetime = data.get("useTimer").toString();
                    int hours = data.get("timerStartHours").getAsInt();
                    int minute = data.get("timerStartMinutes").getAsInt();
                    int second = data.get("timerStartSeconds").getAsInt();
                    seconds = (hours*60*60*1000)+(minute*60*1000)+(second*1000);
//                    Toast.makeText(DetailsFormActivity.this, String.valueOf(seconds),Toast.LENGTH_LONG).show();

                    mformRequestCd = data.get("formRequestCd").getAsString();
                    mreopen = data.get("allowToReopenCase").toString();
                    if (mreopen.equals("true")){
                        mreopenbtn.setVisibility(View.VISIBLE);
                    }else {
                        mreopenbtn.setVisibility(View.GONE);
                    }
                    mserviceTicketCd = data.get("serviceTicketCd").toString();
                    mdateapi = data.get("requestedDateTime").getAsString();
                    mpressGuid = data.get("pressGuid").getAsString();
                    mpressName = data.get("pressName").getAsString();
                    mphotoURL = data.get("photoURL").getAsString();
                    mstatus = data.get("status").getAsString();
                    mstatusName = data.get("statusName").getAsString();
                    mstatusColorCode = data.get("statusColorCode").getAsString();
                    mdeskriptionapi = data.get("description").getAsString();
                   mrequestby.setText(data.get("requestedBy").getAsString());
                    moperator.setText(data.get("operatorName").getAsString());
                    mrequestedDateTime = data.get("requestedDateTime").getAsString();
                    mallowToCancel = data.get("allowToCancel").toString();
                    mallowtoconfirm = data.get("allowToConfirm").toString();
                    xlocation = data.get("locationName").getAsString();
                    mlocation.setText(xlocation);
                    if (mserviceTicketCd.equals("null")){
                        mlayoutticket.setVisibility(View.GONE);
                    }else {
                        mlayoutticket.setVisibility(View.VISIBLE);
                        mserviceTicketHistory = data.getAsJsonArray("serviceTicketHistory");

                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<ServicesTicketItem>>(){}.getType();
                        listticket = gson.fromJson(mserviceTicketHistory.toString(), type);
                        ticketadapter = new ServiceTicketAdapter(DetailsFormActivity.this,listticket);
                        mservice_layout.setAdapter(ticketadapter);
                        mservice_layout.setVisibility(View.VISIBLE);
                        for (int i = 0; i < mserviceTicketHistory.size(); ++i) {
                            String string6 = (mserviceTicketHistory.get(0)).getAsJsonObject().get("ServiceTicketCd").getAsString();
                            mstid.setText(string6);
                            String asist = "";
                           JsonObject ass = mserviceTicketHistory.get(i).getAsJsonObject();
                            massistengineer = ass.getAsJsonArray("Assists");
                            for (int x = 0; x < massistengineer.size(); ++x){
                                JsonObject assobj = massistengineer.get(x).getAsJsonObject();
                                asist += assobj.get("Name").getAsString();
                                asist += "\n";
                                listticket.get(i).setAssist(asist);

                            }
                        }

                        String string7 = data.get("serviceTicketCreated").getAsString();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        String string5 = null;
                        String string6="";
                        try {
                            string6 = simpleDateFormat2.format(simpleDateFormat.parse(string7));
                            string5 = simpleDateFormat.format(simpleDateFormat.parse(string7));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String[] separated = string5.split("T");
                        separated[0].trim();; // this will contain "Fruit"
                        separated[1].trim();;
                        mcreatedate.setText(separated[0]+" "+ separated[1]);
                    }
                    if (data.get("unitCategoryName") == null) {
                        mlayoutunit2.setVisibility(View.GONE);
                    } else {
                       munitcategory.setText(data.get("unitCategoryName").getAsString());
                       mlayoutunit2.setVisibility(View.VISIBLE);
                    }
                    if (data.get("issueCategoryName") == null) {
                       mlayoutunit3.setVisibility(View.GONE);
                    } else {
                       missu.setText(data.get("issueCategoryName").getAsString());
                       mlayoutunit3.setVisibility(View.VISIBLE);
                    }
                    if (data.get("serviceTypeName").toString().equals("null")) {
                       mlayoutunit1.setVisibility(View.GONE);
                    } else {
                        mservicetype.setText(data.get("serviceTypeName").getAsString());
                        mlayoutunit1.setVisibility(View.VISIBLE);
                    }
                    if (mallowtoconfirm.equals("true")) {
                        mconfirm.setVisibility(View.VISIBLE);
                    } else {
                        //RATINGVISIBLE
                        mconfirm.setVisibility(View.GONE);
                    }
                    if (mallowToCancel.equals("true")) {
                       mcancel.setVisibility(View.VISIBLE);
                    } else {
                        mcancel.setVisibility(View.GONE);
                    }
                    loading.dismiss();
                    ////set
                    Picasso.with(DetailsFormActivity.this).load(mphotoURL).into(mbanner);
                    mtitle.setText("#"+mformRequestCd);
                    mreqno.setText(mformRequestCd);
                    String datenew = "";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                    SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    try {
                        datenew = simpleDateFormat.format(simpleDateFormat.parse(mdateapi));
                        System.out.println(datenew);
                        Log.e((String)"Date",datenew);
                    }
                    catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    String[] separated = datenew.split("T");
                    separated[0].trim();; // this will contain "Fruit"
                    separated[1].trim();;
                    mdate.setText(separated[0]+" "+ separated[1]);
                    msn.setText(mpressName);

                    mdeskription.setText(mdeskriptionapi);
                    mstatusdetail.setText(mstatusName);
                    mstatusdetail.setTextColor(Color.parseColor("#"+mstatusColorCode));
                }else {
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(DetailsFormActivity.this, errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsFormActivity.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
        Log.d("requestSTdetails",jsonObject.toString());
    }
    public void cancelreq(){
        loading = ProgressDialog.show(DetailsFormActivity.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("formRequestCd",noreq);
        jsonObject.addProperty("reason",mreason);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONcancelform(jsonObject);
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
                    String message = data.get("message").getAsString();
                    Toast.makeText(DetailsFormActivity.this, message,Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else {
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(DetailsFormActivity.this,errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsFormActivity.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
    }
    public void reopenreq(){
        loading = ProgressDialog.show(DetailsFormActivity.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("formRequestCd",noreq);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONreopen(jsonObject);
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
                    String message = data.get("message").getAsString();
                    Toast.makeText(DetailsFormActivity.this, message,Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else {
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(DetailsFormActivity.this,errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsFormActivity.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");



    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
                Intent gotoupdate = new Intent(DetailsFormActivity.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(DetailsFormActivity.this, LoginActivity.class));
            finish();
            Toast.makeText(DetailsFormActivity.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
//    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (check.checknotif==1){
            if (username==null){
                if (check.checklistform==1){
                    list2.clear();
                    refresh=true;
                }
                super.onBackPressed();
                finish();

            }else {
                super.onBackPressed();
//            refresh=true;
                Intent back = new Intent(DetailsFormActivity.this,FormActivity.class);
                back.putExtra("pos",valuefilter);
                startActivity(back);
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                finish();
            }
        }else {
            Intent back = new Intent(DetailsFormActivity.this,Dashboard.class);
            back.putExtra("pos",valuefilter);
            startActivity(back);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
        }


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
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
//                mtimerconfirm.setText("00:00:00");

            }
        }.start();

    }
    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d",hours, minutes, seconds);
        mtimerconfirm.setText(getString(R.string.title_confirm)+" ("+timeLeftFormatted+")");
    }
    public void ReadNotif(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("guid",guid);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.Read(jsonObject);
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
//                    String message = data.get("message").getAsString();
//                    Toast.makeText(DetailsFormActivity.this, message,Toast.LENGTH_LONG).show();

                }else {
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(DetailsFormActivity.this,errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DetailsFormActivity.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
    }
}