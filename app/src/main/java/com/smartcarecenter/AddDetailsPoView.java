package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.Add_Foc_Request_view.Add_foc_req_adapterView;
import com.smartcarecenter.Add_Foc_Request_view.Add_foc_req_itemView;
import com.smartcarecenter.Add_Po_Rewuest_View.Add_po_req_adapterView;
import com.smartcarecenter.Add_Po_Rewuest_View.Add_po_req_itemView;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.messagecloud.check;

import java.io.File;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.smartcarecenter.Dashboard.installed2;
import static com.smartcarecenter.FormActivity.refresh;
import static com.smartcarecenter.FormActivity.valuefilter;
import static com.smartcarecenter.ChargeableActivity.list2;
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;

public class AddDetailsPoView extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1000;
    String linkPo = "";
    boolean showprep = true;
    String colortextrep = "";
    String textprep="";
    String bgprep = "";
    public static JsonArray listsn;
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    public static String matrixlabel = "";
    String akunid = "";
    double tax = 0.0;
    String taxname = "";
    Boolean internet = false;
    boolean installed= true;
    ProgressDialog loading;
    LinearLayout mback,mbgalert, mlayinvpo, mlaypo,mlayinv;
    public static LinearLayout mlaytotal;
    public static TextView mdate,mstartimpresi,moperator,mno_order,mtotalitem,msend,mtotalqty,
            mnoitem,mpono,mstatus, mtotalprice,mtax,mgrandtotal,mtitle,mlabeltax, mnotes;

    String mpressId = "";
    String mpressId2 = "";
    Integer previmpressvlaue = 100;
    LinearLayout madd_item,mchat, mcopy;
    TextView msn,mtextalert, mdeskrip, mlinktext;
    DatabaseReference reference;
    public static RecyclerView mlistitem_foc;
    String sesionid_new = "";
    List<String> snid = new ArrayList();
    List<String> snname = new ArrayList();
    List<Integer> previmpression = new ArrayList();
    //list item add
    public static ArrayList<Add_po_req_itemView> reitem;
    Add_po_req_adapterView req_adapter;
    private LinearLayoutManager linearLayoutManager;
    String jsonarayitem = "";
    JsonArray myCustomArray;
    String noOrder="";
    String pressid= "";
    Gson gson;
    String guid = "";
    String username = "";
    public static String Nowpo = "0";
    private ClipboardManager myClipboard;
    private ClipData myClip;
    Boolean showlinkdownload = true;
    String mmustUpload = "yes";
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details_po_view);
        mlistitem_foc = findViewById(R.id.listitemfoc);
        mdate = findViewById(R.id.datefoc);
        mno_order = findViewById(R.id.noorder);
        mnoitem = findViewById(R.id.noitem);
        msend = findViewById(R.id.submit);
        msn = findViewById(R.id.sn);
        moperator = findViewById(R.id.operator);
        mpono = findViewById(R.id.pono);
        mstartimpresi = findViewById(R.id.startimpresi);
        mback = findViewById(R.id.backbtn);
        mtotalitem = findViewById(R.id.totalitempo);
        mtotalqty = findViewById(R.id.totalqtypo);
        mlaytotal = findViewById(R.id.totallay);
        madd_item = findViewById(R.id.btnadditem_po);
        mstatus = findViewById(R.id.status);
        mchat = findViewById(R.id.chatcspo);
        mtotalprice = findViewById(R.id.totalpricepo);
        mtax =findViewById(R.id.totaltax);
        mgrandtotal = findViewById(R.id.grantotalpo);
        mtitle = findViewById(R.id.title);
        mlabeltax = findViewById(R.id.labeltax);
        mnotes = findViewById(R.id.mnotes);
        mbgalert = findViewById(R.id.backgroundalert);
        mtextalert = findViewById(R.id.textalert);
        mlayinvpo = findViewById(R.id.laypoinv);
        mlayinv = findViewById(R.id.downloadinvoice);
        mlaypo = findViewById(R.id.downloadpo);
        mdeskrip = findViewById(R.id.descrip);
        mcopy =findViewById(R.id.copylink);
        mlinktext = findViewById(R.id.textlink);
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            noOrder = bundle2.getString("id");
            valuefilter = bundle2.getString("pos");
            guid = bundle2.getString("guid");
            username = bundle2.getString("username");
            mmustUpload = bundle2.getString("pdfyes");
        }
        mtitle.setText("View Chargeable #"+noOrder);
        cekInternet();
        getSessionId();
        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(AddDetailsPoView.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        mlistitem_foc.setLayoutManager(linearLayoutManager);
        mlistitem_foc.setHasFixedSize(true);
        reitem = new ArrayList<Add_po_req_itemView>();
        if (internet){
//            LoadPress();
            LoadData();
            prepform();
            if (guid==null){

            }else {
                ReadNotif();
            }
        }else {

        }

        mlaypo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestImage();
            }
        });
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
                if (mmustUpload.equals("yes")){
                    Intent back = new Intent(AddDetailsPoView.this,ChargeableActivity.class);
                    back.putExtra("pos",valuefilter);
                    startActivity(back);
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    finish();
                }else {
                    onBackPressed();
                }

            }
        });
        mchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appInstalledOrNot("com.whatsapp");
                appInstalledOrNot2("com.whatsapp.w4b");
                if (installed) {
                    String message = "Hi Support, "+getString(R.string.title_tanyafoc)+noOrder;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(android.net.Uri.parse(
                            String.format("https://api.whatsapp.com/send?phone=%s&text=%s", Nowpo, message)));
                    startActivity(intent);

                }else {
                    if (installed2){
                        String message = "Hi Support, "+getString(R.string.title_tanyafoc)+noOrder;
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(android.net.Uri.parse(
                                String.format("https://api.whatsapp.com/send?phone=%s&text=%s", Nowpo, message)));
                        startActivity(intent);
                    }
                    Toast.makeText(AddDetailsPoView.this,"Whatsapp blum di instal", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                String text;
                text = mlinktext.getText().toString();

                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);

                Toast.makeText(getApplicationContext(), "Link Copied",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) AddDetailsPoView.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(AddDetailsPoView.this, NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");
        SharedPreferences taxes = getSharedPreferences("Tax",MODE_PRIVATE);
        tax = taxes.getInt("tax", 0);
        taxname = taxes.getString("taxname","");

    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
                Intent gotoupdate = new Intent(AddDetailsPoView.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(AddDetailsPoView.this, LoginActivity.class));
            finish();
            Toast.makeText(AddDetailsPoView.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (mmustUpload.equals("yes")){
            Intent back = new Intent(AddDetailsPoView.this,ChargeableActivity.class);
            back.putExtra("pos",valuefilter);
            startActivity(back);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
        }else {
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
//              refresh=true;
                    Intent back = new Intent(AddDetailsPoView.this,ChargeableActivity.class);
                    back.putExtra("pos",valuefilter);
                    startActivity(back);
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    finish();
                }
            }else {
                Intent back = new Intent(AddDetailsPoView.this,Dashboard.class);
                back.putExtra("pos",valuefilter);
                startActivity(back);
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                finish();
            }
        }



    }
    public void LoadPress(){
        loading = ProgressDialog.show(AddDetailsPoView.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);

        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONpresslist(jsonObject);
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
                    loading.dismiss();
                    if (data.getAsJsonArray("pressList")!=null){
                        listsn=data.getAsJsonArray("pressList");
                        for (int i = 0; i < listsn.size(); ++i) {
                            JsonObject jsonObject2 = (JsonObject)listsn.get(i);
                            String string4 = jsonObject2.getAsJsonObject().get("name").getAsString();
                            String string5 = jsonObject2.getAsJsonObject().get("id").getAsString();
                            Integer previmpress = jsonObject2.getAsJsonObject().get("previousImpression").getAsInt();
                            snname.add(string4);
                            snid.add(string5);
                            previmpression.add(previmpress);
                            loading.dismiss();
                        }
                    }

                }else {
                    Toast.makeText(AddDetailsPoView.this, errornya.toString(),Toast.LENGTH_LONG).show();
                    loading.dismiss();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AddDetailsPoView.this, t.toString(),Toast.LENGTH_LONG).show();
                cekInternet();


            }
        });
    }
    public void LoadData(){
        gson = new Gson();
        loading = ProgressDialog.show(AddDetailsPoView.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("orderNo",noOrder);
        jsonObject.addProperty("ver",ver);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.viewchargeable(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loading.dismiss();
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

                    showlinkdownload = data.get("showUploadPOLink").getAsBoolean();
                    if (showlinkdownload){
                        mcopy.setVisibility(VISIBLE);
                        String linkpdf = data.get("uploadPOLink").getAsString();
                        mlinktext.setText(linkpdf);
                    }else {
                        mcopy.setVisibility(GONE);
                    }
                    mdeskrip.setText(data.get("custNotes").getAsString());
                    //laydownloadpoinv
                    if (data.get("poDownloadURL").toString().equals("null")){
                        mlayinvpo.setVisibility(GONE);
                        mlaypo.setVisibility(GONE);

                    }else {
                        mlayinvpo.setVisibility(VISIBLE);
                        mlaypo.setVisibility(VISIBLE);
                        linkPo = data.get("poDownloadURL").getAsString();

                    }
                    String showalert = data.get("showMessage").toString();
                    if (showalert.equals("true")){

                        String text = data.get("messageText").getAsString();
                        String textcolor = data.get("messageTextColor").getAsString();
                        String bgcolor = data.get("messageBackgroundColor").getAsString();

                        GradientDrawable shape =  new GradientDrawable();
                        shape.setCornerRadius( 15 );
                        shape.setColor(Color.parseColor("#"+bgcolor));

                        mbgalert.setVisibility(View.VISIBLE);
                        mtextalert.setTextColor(Color.parseColor("#"+textcolor));
                        mbgalert.setBackground(shape);
                        if (Build.VERSION.SDK_INT >= 24) {
                            mtextalert.setText((CharSequence)Html.fromHtml((String)text, Html.FROM_HTML_MODE_COMPACT));
                            mtextalert.setMovementMethod(LinkMovementMethod.getInstance());
                        } else {
                            mtextalert.setText((CharSequence)Html.fromHtml((String)text));
                            mtextalert.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                    }else {
                        mbgalert.setVisibility(View.GONE);
                    }
                    String pressname = data.get("pressTypeName").getAsString();
                    //setnotes
                    String note = data.get("notes").getAsString();
                    mnotes.setText(note);
                    mnotes.setTextColor(Color.parseColor("#"+data.get("notesTextColor").getAsString()));

                        msn.setText(pressname);
                    tax = data.get("totalTax").getAsDouble();
                    Locale localeID = new Locale("in", "ID");
                    final DecimalFormat formatRupiah = new DecimalFormat("###,###,###,###,###.00");
                    mtax.setText("Rp. "+String.valueOf(formatRupiah.format(tax)));
                    mlabeltax.setText(taxname+":");
                    mtotalprice.setText("Rp. "+String.valueOf(formatRupiah.format(data.get("totalPrice").getAsDouble())));
                    mgrandtotal.setText("Rp. "+String.valueOf(formatRupiah.format(data.get("grandTotal").getAsDouble())));
                    pressid = data.get("pressGuid").getAsString();
                    String nopo = data.get("poNo").getAsString();
                    mpono.setText(nopo);

                    String orderno = data.get("orderNo").getAsString();
                    String date = data.get("date").getAsString();
//                    String pressstart = data.get("previousImpression").getAsString();
//                    String presimpres = data.get("presentImpression").getAsString();
                    String status = data.get("statusName").getAsString();
                    String statuscolor = data.get("statusColorCode").getAsString();
                    String operator = data.get("username").getAsString();
                    String cancelshow = data.get("allowToCancel").toString();
                    if (cancelshow.equals("true")){
                        msend.setVisibility(View.VISIBLE);
                        mchat.setVisibility(View.VISIBLE);

                    }else {
                        msend.setVisibility(View.GONE);
                        mchat.setVisibility(View.VISIBLE);
                    }
//                    mstartimpresi.setText(pressstart);
                    mno_order.setText(orderno);
//                    mlastimpresi.setText(presimpres);
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
                    if (data.get("itemList").toString().equals("null")){
                        madd_item.setVisibility(GONE);
                        mlaytotal.setVisibility(GONE);
                    }else {

                        myCustomArray = data.getAsJsonArray("itemList");
                        madd_item.setVisibility(VISIBLE);
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<Add_po_req_itemView>>() {
                        }.getType();
                        reitem = gson.fromJson(myCustomArray.toString(), listType);
                        req_adapter = new Add_po_req_adapterView(AddDetailsPoView.this, reitem);
                        mlistitem_foc.setAdapter(req_adapter);
                        mlistitem_foc.setVisibility(View.VISIBLE);
                        mlaytotal.setVisibility(VISIBLE);
                    }


                }else {
                    cekInternet();
                    sesionid();
                    Toast.makeText(AddDetailsPoView.this, errornya.toString(),Toast.LENGTH_LONG).show();
                    loading.dismiss();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AddDetailsPoView.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
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
        Call<JsonObject> panggilkomplek = jsonPostService.cancelpo(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loading.dismiss();
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
                    Toast.makeText(AddDetailsPoView.this, getString(R.string.title_cancelorder),Toast.LENGTH_LONG).show();
                    onBackPressed();

                }else {
                    Toast.makeText(AddDetailsPoView.this, errornya.toString(),Toast.LENGTH_LONG).show();
                    loading.dismiss();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AddDetailsPoView.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
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
    public boolean appInstalledOrNot2(String string2) {
        PackageManager packageManager = this.getPackageManager();

        try {
            packageManager.getPackageInfo(string2, packageManager.GET_ACTIVITIES);
            installed2 = true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            installed2 = false;

        }
        return installed2;
    }
    public void prepform(){
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("sessionId",sesionid_new);
//        jsonObject.addProperty("ver",ver);
//        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
//        Call<JsonObject> panggilkomplek = jsonPostService.prepcharge(jsonObject);
//        panggilkomplek.enqueue(new Callback<JsonObject>() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//
//                String errornya = "";
//                JsonObject homedata=response.body();
//                String statusnya = homedata.get("status").getAsString();
//                if (homedata.get("errorMessage").toString().equals("null")) {
//
//                }else {
//                    errornya = homedata.get("errorMessage").getAsString();
//                }
//                MhaveToUpdate = homedata.get("haveToUpdate").toString();
//                MsessionExpired = homedata.get("sessionExpired").toString();
//                if (statusnya.equals("OK")) {
//                    JsonObject data = homedata.getAsJsonObject("data");
//                    showprep = data.get("showMessage").getAsBoolean();
//
//                    if (showprep){
//                        colortextrep = data.get("messageTextColor").getAsString();
//                        textprep=data.get("messageText").getAsString();
//                        bgprep = data.get("messageBackgroundColor").getAsString();
//                        mbgalert.setVisibility(VISIBLE);
//
////                        mtextalert.setText(textprep);
//                        mtextalert.setTextColor(Color.parseColor("#"+colortextrep));
//                        if (Build.VERSION.SDK_INT >= 24) {
//                            mtextalert.setText((CharSequence) Html.fromHtml((String)textprep, Html.FROM_HTML_MODE_COMPACT));
//                            mtextalert.setMovementMethod(LinkMovementMethod.getInstance());
//                        } else {
//                            mtextalert.setText((CharSequence)Html.fromHtml((String)textprep));
//                            mtextalert.setMovementMethod(LinkMovementMethod.getInstance());
//                        }
//                        GradientDrawable shape =  new GradientDrawable();
//                        shape.setCornerRadius( 15 );
//                        shape.setColor(Color.parseColor("#"+bgprep));
//                        mbgalert.setBackground(shape);
//                    }else {
//                        mbgalert.setVisibility(GONE);
//                    }
//
//                }else {
//                    loading.dismiss();
//                    Toast.makeText(AddDetailsPoView.this, errornya,Toast.LENGTH_LONG).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                loading.dismiss();
//                Toast.makeText(AddDetailsPoView.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
//                cekInternet();
//
//
//            }
//        });
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

                }else {
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(AddDetailsPoView.this,errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(AddDetailsPoView.this,getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
    }
    private void setRequestImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) this, Manifest.permission.CAMERA)
                    && ActivityCompat.shouldShowRequestPermissionRationale((Activity) this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                //Show permission dialog
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity)this, new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);


            }

        }else {
            startDownload();
        }
    }
    public void startDownload() {
        File direct = new File(Environment.getExternalStorageDirectory()
                + "/dhaval_files");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager mgr = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(linkPo);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

//        request.setDestinationInExternalPublicDir("/SmartcareCenter", downloadUri.getLastPathSegment());
//        Long referese = dm.enqueue(request);
        request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, downloadUri.getLastPathSegment());
        mgr.enqueue(request);
        Toast.makeText(getApplicationContext(), getString(R.string.title_unduhan), Toast.LENGTH_SHORT).show();

//        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(linkdownloadnya));
//
//        queid = downloadManager.enqueue(request);
    }
}