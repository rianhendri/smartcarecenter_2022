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
import android.text.Editable;
import android.text.TextWatcher;
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

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.smartcarecenter.Add_Foc_request.Add_foc_req_adapter;
import com.smartcarecenter.Add_Foc_request.Add_foc_req_item;
import com.smartcarecenter.Add_Po_Request.Add_po_req_adapter;
import com.smartcarecenter.Add_Po_Request.Add_po_req_item;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.smartcarecenter.Add_Po_Item_List.PaymentTypeCd2;
import static com.smartcarecenter.Add_Po_Item_list_model.Add_po_list_adapter.listpoact;
import static com.smartcarecenter.FormActivity.valuefilter;
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;

public class AddDetailsPo extends AppCompatActivity {
    boolean showprep = true;
    String colortextrep = "";
    String textprep="";
    String bgprep = "";
    String message = "";
    String noOrder = "";
    public static JsonArray listsn;
    public static  String username = "";
    public static String matrixlabel = "";
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    String akunid = "";
    Boolean internet = false;
    public static int tax = 0;
    public static String taxname = "";
    ProgressDialog loading;
    LinearLayout mback, mbtnchose, mcopy;
    int pos = 0;
    public static LinearLayout mlaytotal;
    public static TextView mdate,mstartimpresi,moperator,mno_order,mtotalitem,msend,msend2,mtotalqty,mnoitem,mtotaltax,mgrantotalpo
            ,mtotalpricepo,mLabeltax;
    EditText mnopo, mnotes;
    public static String pono="";
    public static String mpressId = "";
    String mpressId2 = "";
    Integer previmpressvlaue = 100;
    LinearLayout madd_item, mbgalert, mlaypembayaran;
    TextView mtextalert, mnamafile, mrequiredpdf, mchosepdf, mlinktext;
    Spinner msn;
    DatabaseReference reference;
    public static RecyclerView mlistitem_foc;
    String sesionid_new = "";
    List<String> snid = new ArrayList();
    List<String> snname = new ArrayList();
    List<Integer> previmpression = new ArrayList();
    ImageView mlogopay;
    //list item add
    public static ArrayList<Add_po_req_item> reitem;
    public static Add_po_req_adapter req_adapter;
    private LinearLayoutManager linearLayoutManager;
    public static String jsonarayitem = "";
    public static JsonArray myCustomArray;
    public static Gson gson;
    String notes = "";
    private static final int PICK_PDF_REQUEST = 1000;
    Uri filePath = null;
    public static File imagefile = null;
    boolean muploadPOPdf = true;
    boolean mmustUpload = true;
    boolean mrequpload = true;
    private String pdfPath;
    double sizeflekb = 0.0;
    double sizeflemb = 0.0;
    double sizereq = 0.0;
    public static String notes1 = "";
    private ClipboardManager myClipboard;
    private ClipData myClip;
    JsonArray liststatus;
    List<String> listvalue = new ArrayList();
    List<String> listnamestatus = new ArrayList();
    List<String> listfoto = new ArrayList();
    Spinner mstatus_spin;
    public  static String PaymentTypeCd = "null";
    boolean payment = false;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details_po);
        mlaypembayaran = findViewById(R.id.pembayaranlay);
        mlistitem_foc = findViewById(R.id.listitemfoc);
        mdate = findViewById(R.id.datefoc);
        mno_order = findViewById(R.id.noorder);
        mnoitem = findViewById(R.id.noitem);
        msend = findViewById(R.id.submit);
        msend2 = findViewById(R.id.submit2);
        msn = findViewById(R.id.sn);
        moperator = findViewById(R.id.operator);
        mnopo = findViewById(R.id.lastimprsi);
        mstartimpresi = findViewById(R.id.startimpresi);
        mback = findViewById(R.id.backbtn);
        mtotalitem = findViewById(R.id.totalitempo);
        mtotalqty = findViewById(R.id.totalqtypo);
        mlaytotal = findViewById(R.id.totallay);
        madd_item = findViewById(R.id.btnadditem_po);
        mtotalpricepo = findViewById(R.id.totalpricepo);
        mtotaltax =findViewById(R.id.totaltax);
        mgrantotalpo = findViewById(R.id.grantotalpo);
        mLabeltax = findViewById(R.id.labeltax);
        mbgalert = findViewById(R.id.backgroundalert);
        mtextalert = findViewById(R.id.textalert);
        mnotes = findViewById(R.id.descrip);
        mbtnchose = findViewById(R.id.btnchose);
        mnamafile = findViewById(R.id.namafile);
        mrequiredpdf = findViewById(R.id.requiredpdf);
        mchosepdf = findViewById(R.id.chosepdf);
        mcopy = findViewById(R.id.copylink);
        mlinktext = findViewById(R.id.textlink);
        mstatus_spin = findViewById(R.id.spinstatus);
        mlogopay = findViewById(R.id.logopay);
        cekInternet();
        getSessionId();
        Bundle bundle2 = getIntent().getExtras();
//        PaymentTypeCd =PaymentTypeCd2;
//        if (bundle2 != null) {
//            PaymentTypeCd = bundle2.getString("pay");
//        }else {
//            PaymentTypeCd ="null";
//        }
        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(AddDetailsPo.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        mlistitem_foc.setLayoutManager(linearLayoutManager);
        mlistitem_foc.setHasFixedSize(false);
        reitem = new ArrayList<Add_po_req_item>();
        mnotes.setText(notes1);
        if (imagefile!=null){
            mnamafile.setText(imagefile.getName());
        }
        if (internet){
            LoadPress();
            prepform();
        }else {

        }
        if (listpoact.isEmpty()){
            mlaytotal.setVisibility(View.GONE);
            mnoitem.setVisibility(View.VISIBLE);
            mlistitem_foc.setVisibility(View.GONE);
        }else {
            mlaytotal.setVisibility(View.VISIBLE);
            msend2.setVisibility(GONE);
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
        mnopo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String result = s.toString().replaceAll(" ", "");
                if (!s.toString().equals(result)) {
                    mnopo.setText(result);
                    mnopo.setSelection(result.length());

                    // alert the user
                }
                pono = mnopo.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mnopo.setText(pono);
////////////////////// adapter di masukan ke recyler//

        req_adapter = new Add_po_req_adapter(this, reitem);
        mlistitem_foc.setAdapter(req_adapter);
        String string2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        mdate.setText((CharSequence)string2);
        msn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mpressId = snid.get(position);
                previmpressvlaue = previmpression.get(position);
//                    Toast.makeText(AddDetailsPo.this, mpressId,Toast.LENGTH_SHORT).show();
                if (pos==position){

                }else {
                    listpoact.clear();
                    reitem.clear();
                    req_adapter = new Add_po_req_adapter(AddDetailsPo.this, reitem);
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
                notes1 = mnotes.getText().toString();
                Intent gotoaddfoc = new Intent(AddDetailsPo.this, Add_Po_Item_List.class);
                gotoaddfoc.putExtra("pressId",mpressId);
                gotoaddfoc.putExtra("nopo",pono);
                gotoaddfoc.putExtra("notesa",notes1);
                gotoaddfoc.putExtra("pay",PaymentTypeCd);
                gotoaddfoc.putExtra("file",imagefile);
                startActivity(gotoaddfoc);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                finish();

                listpoact.addAll(reitem);
            }
        });
        mstatus_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("pos",String.valueOf(position));
                PaymentTypeCd = listvalue.get(position);
                Log.d("spin",PaymentTypeCd+String.valueOf(position));
                cekInternet();
                for (int i = 0; i < listfoto.size(); ++i) {
                    String valuefilter = listfoto.get(position);
                    if (valuefilter.equals("null")) {
                       mlogopay.setVisibility(GONE);
                    }else {
                        mlogopay.setVisibility(VISIBLE);
                        Picasso.with(AddDetailsPo.this).load(valuefilter).into(mlogopay);
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        msend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mnotes.setText(mpressId+myCustomArray.toString());
                if (internet){
                    if (mnopo.getText().toString().length()!=0){
                        message = getString(R.string.title_submitorder);
                    }else {
                        message = getString(R.string.title_tanpanopo);
                    }
                    if (muploadPOPdf){
                        if (mmustUpload){
                            if (payment){
                                if (PaymentTypeCd.equals("null")){

                                    Toast.makeText(AddDetailsPo.this, "Pembayaran Wajib di pilih", Toast.LENGTH_SHORT).show();


                                }else {
                                    showDialog();

                                }
                            }else {
                                showDialog();
                            }


//                            if (imagefile==null){
//                                Toast.makeText((Context)AddDetailsPo.this, getString(R.string.title_reqpdf),Toast.LENGTH_SHORT).show();
//                            }else {
//                                showDialog();
//                            }
                        }
                        else {
                            if (payment){
                                if (PaymentTypeCd.equals("null")){

                                    Toast.makeText(AddDetailsPo.this, "Pembayaran Wajib di pilih", Toast.LENGTH_SHORT).show();


                                }else {
                                    showDialog();

                                }
                            }else {
                                showDialog();
                            }
                        }
                    }else {
                        if (payment){
                            if (PaymentTypeCd.equals("null")){

                                Toast.makeText(AddDetailsPo.this, "Pembayaran Wajib di pilih", Toast.LENGTH_SHORT).show();


                            }else {
                                showDialog();

                            }
                        }else {
                            showDialog();
                        }
                    }


                }
            }
        });
        msend2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mnotes.setText(mpressId+myCustomArray.toString());
                if (internet){
                    if (mnopo.getText().toString().length()!=0){
                        message = getString(R.string.title_submitorder);
                    }else {
                        message = getString(R.string.title_tanpanopo);
                    }
                    if (muploadPOPdf){
                        if (mmustUpload){
                            if (payment){
                                if (PaymentTypeCd.equals("null")){

                                    Toast.makeText(AddDetailsPo.this, "Pembayaran Wajib di pilih", Toast.LENGTH_SHORT).show();


                                }else {
                                    showDialog();

                                }
                            }else {
                                showDialog();
                            }
//                            if (imagefile==null){
//                                Toast.makeText((Context)AddDetailsPo.this, getString(R.string.title_reqpdf),Toast.LENGTH_SHORT).show();
//                            }else {
//                                showDialog();
//                            }
                        }
                        else {
                            if (payment){
                                if (PaymentTypeCd.equals("null")){

                                    Toast.makeText(AddDetailsPo.this, "Pembayaran Wajib di pilih", Toast.LENGTH_SHORT).show();


                                }else {
                                    showDialog();

                                }
                            }else {
                                showDialog();
                            }
                        }
                    }else {
                        if (payment){
                            if (PaymentTypeCd.equals("null")){

                                Toast.makeText(AddDetailsPo.this, "Pembayaran Wajib di pilih", Toast.LENGTH_SHORT).show();


                            }else {
                                showDialog();

                            }
                        }else {
                            showDialog();
                        }
                    }


                }
            }
        });
        mLabeltax.setText(taxname+":");

        // mbtnchose pdf
        mbtnchose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showFileChooser();
                launchPicker();
            }
        });

    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) AddDetailsPo.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(AddDetailsPo.this, NoInternet.class);
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
                Intent gotoupdate = new Intent(AddDetailsPo.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(AddDetailsPo.this, LoginActivity.class));
            finish();
            Toast.makeText(AddDetailsPo.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        pono="";
        notes1 = "";
        imagefile=null;
        Intent back = new Intent(AddDetailsPo.this,ChargeableActivity.class);
        back.putExtra("pos",valuefilter);
        startActivity(back);
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
    public void goGet() {
        Intent back = new Intent(AddDetailsPo.this,AddDetailsPoView.class);
        back.putExtra("id",noOrder);
        back.putExtra("pdfyes","yes");
        startActivity(back);
        finish();
        overridePendingTransition(0, 0);
    }
    public void LoadPress(){
        loading = ProgressDialog.show(AddDetailsPo.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
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
                    moperator.setText(username);
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
                        ArrayAdapter arrayAdapter = new ArrayAdapter(AddDetailsPo.this, R.layout.spinstatus_layout, snname);
                        arrayAdapter.setDropDownViewResource(R.layout.spinkategori);
                        arrayAdapter.notifyDataSetChanged();
                        msn.setAdapter(arrayAdapter);
                        msn.setSelection(pos);
                        loading.dismiss();
                    }
                }else {
                    Toast.makeText(AddDetailsPo.this, errornya.toString(),Toast.LENGTH_LONG).show();
                    loading.dismiss();
                    sesionid();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AddDetailsPo.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();


            }
        });
    }
    public void sendData(){
        gson = new Gson();
        loading = ProgressDialog.show(AddDetailsPo.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("pressGuid",mpressId);
        jsonObject.addProperty("poNo",mnopo.getText().toString());
        jsonObject.addProperty("notes",mnotes.getText().toString());
        jsonObject.add("items", myCustomArray);
        jsonObject.addProperty("ver",ver);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.sendPo(jsonObject);
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
                    loading.dismiss();
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    String ok=data.get("message").getAsString();
                    Toast.makeText(AddDetailsPo.this, ok,Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else {
                    Toast.makeText(AddDetailsPo.this, errornya.toString(),Toast.LENGTH_LONG).show();
                    loading.dismiss();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AddDetailsPo.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
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
                .setMessage(message)
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.title_yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
//                        sendData();
                        uploadData();
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
    public void prepform(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.prepcharge(jsonObject);
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

                    listvalue.add("null");
                    listnamestatus.add("-pilih-");
                    listfoto.add("null");
                    JsonObject data = homedata.getAsJsonObject("data");
                    if (data.get("showPaymentType").getAsBoolean()){
                        payment = true;
                        liststatus = data.getAsJsonArray("paymentTypeList");
                        for (int i = 0; i < liststatus.size(); ++i) {
                            JsonObject jsonObject3 = (JsonObject)liststatus.get(i);
                            String string3 = jsonObject3.getAsJsonObject().get("PaymentTypeCd").getAsString();
                            String string4 ="-pilih-";
                            string4 = jsonObject3.getAsJsonObject().get("Name").getAsString();
                            String string5 = "";
                            if (jsonObject3.getAsJsonObject().get("ImageURL").toString().equals("null")){
                                string5="null";
                            }else {
                                string5 = jsonObject3.getAsJsonObject().get("ImageURL").getAsString();
                            }

                            listvalue.add(string3);
                            listnamestatus.add(string4);
                            listfoto.add(string5);
                            for (int j = 0; j < listvalue.size(); ++j) {
                                if (listvalue.get(j).equals(PaymentTypeCd)){
                                    pos=j;
                                }
                            }
                            final ArrayAdapter<String> kategori = new ArrayAdapter<String>(AddDetailsPo.this, R.layout.spinstatus_layout,
                                    listnamestatus);
                            kategori.setDropDownViewResource(R.layout.spinkategori);
                            kategori.notifyDataSetChanged();
                            mstatus_spin.setAdapter(kategori);
                            mstatus_spin.setSelection(pos);
                        }
                        mlaypembayaran.setVisibility(VISIBLE);
                    }else {
                        payment = false;
                        mlaypembayaran.setVisibility(GONE);
                    }



                    showprep = data.get("showMessage").getAsBoolean();
                    muploadPOPdf = data.get("uploadPOPdf").getAsBoolean();
                    mmustUpload = data.get("mustUpload").getAsBoolean();
                    if (muploadPOPdf){
                        mcopy.setVisibility(VISIBLE);
                        sizereq = (double) data.get("maxFileSize").getAsInt();
                        if (sizereq>1000.0){
                            sizeflekb = Double.parseDouble(String.valueOf(sizereq/1000));
                            mchosepdf.setText(getString(R.string.title_choosefile)+" Max: "+String.valueOf(sizeflekb)+"MB");
                        }else {
                            sizeflekb = Double.parseDouble(String.valueOf(sizereq));
                            mchosepdf.setText(getString(R.string.title_choosefile)+"Max: "+String.valueOf(sizeflekb)+"KB");
                        }
                        if (mmustUpload){
                            //UPDATE
                            if (muploadPOPdf){
                                madd_item.setEnabled(false);
                                msend2.setVisibility(VISIBLE);
                                mnoitem.setVisibility(GONE);
                                madd_item.setVisibility(GONE);
                            }
                                                        mrequpload = true;
                            mrequiredpdf.setVisibility(VISIBLE);
                        }else {
                            //UPDATE
                            madd_item.setEnabled(true);
                            msend2.setVisibility(GONE);
                            mnoitem.setVisibility(VISIBLE);
                            madd_item.setVisibility(VISIBLE);
                            mrequpload = false;
                            mrequiredpdf.setVisibility(GONE);
                        }
                    }else {
                        mcopy.setVisibility(GONE);
                    }
                    if (showprep){
                        colortextrep = data.get("messageTextColor").getAsString();
                        textprep=data.get("messageText").getAsString();
                        bgprep = data.get("messageBackgroundColor").getAsString();
                        mbgalert.setVisibility(VISIBLE);
                        mtextalert.setText(textprep);
                        mtextalert.setTextColor(Color.parseColor("#"+colortextrep));
                        GradientDrawable shape =  new GradientDrawable();
                        shape.setCornerRadius( 15 );
                        shape.setColor(Color.parseColor("#"+bgprep));
                        mbgalert.setBackground(shape);
                    }else {
                        mbgalert.setVisibility(GONE);
                    }

                }else {
                    loading.dismiss();
                    Toast.makeText(AddDetailsPo.this, errornya,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AddDetailsPo.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();


            }
        });
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK ) {
            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            File file = new File(path);
            if (path != null) {
                Log.d("Path: ", path);

                pdfPath = path;
                imagefile = new File(pdfPath);
                if (sizereq>1000.0){
                    sizeflekb = Double.parseDouble(String.valueOf(imagefile.length()/1000));
                    sizeflemb = Double.parseDouble(String.valueOf(sizeflekb/1000));

                    if (sizeflekb>sizereq){
                        mnamafile.setText(getString(R.string.title_bigfile));
                    }else {
                        mnamafile.setText(imagefile.getName());
                    }
                }else {
                    sizeflekb = Double.parseDouble(String.valueOf(imagefile.length()/1000));
                    if (sizeflekb>sizereq){
                        mnamafile.setText(getString(R.string.title_bigfile));
                    }else {
                        mnamafile.setText(imagefile.getName());
                    }
                }


//                Toast.makeText(this, "Picked file: " + path, Toast.LENGTH_LONG).show();
            }else {
                mnamafile.setText("-");
            }
        }
    }
    public void uploadData(){
        loading = ProgressDialog.show(this, "", getString(R.string.title_loading), true);
        if (imagefile==null){
//        Toast.makeText(getApplicationContext(), "" + imgreq, Toast.LENGTH_LONG).show();
            IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
//        mnotes.setText(myCustomArray.toString()+mpressId);
            //
            jsonPostService.uploadcharge(MultipartBody.Part.createFormData((String)"",
                    ""),
                    RequestBody.create((MediaType)MultipartBody.FORM,sesionid_new),
                    RequestBody.create((MediaType)MultipartBody.FORM,mpressId),
                    RequestBody.create((MediaType)MultipartBody.FORM,mnopo.getText().toString()),
                    RequestBody.create((MediaType)MultipartBody.FORM,mnotes.getText().toString()),
                    RequestBody.create((MediaType)MultipartBody.FORM,String.valueOf(myCustomArray)),
                    RequestBody.create((MediaType)MultipartBody.FORM,PaymentTypeCd),
                    RequestBody.create((MediaType)MultipartBody.FORM,BuildConfig.VERSION_NAME))
                    .enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            String errornya = "";
                            JsonObject jsonObject=response.body();
//                Toast.makeText((Context)AddDetailFoc.this, jsonObject.toString(),Toast.LENGTH_SHORT).show();
                            String statusnya = jsonObject.get("status").getAsString();
                            if (jsonObject.get("errorMessage").toString().equals("null")) {

                            }else {
                                errornya = jsonObject.get("errorMessage").getAsString();
                            }
                            MhaveToUpdate = jsonObject.get("haveToUpdate").toString();
                            MsessionExpired = jsonObject.get("sessionExpired").toString();
                            sesionid();
                            if (statusnya.equals((Object)"OK")) {
                                String string4 = jsonObject.getAsJsonObject("data").get("message").getAsString();
                                JsonObject data = jsonObject.getAsJsonObject("data");
                                noOrder = data.get("orderNo").getAsString();
                                loading.dismiss();
                                Toast.makeText((Context)AddDetailsPo.this, (CharSequence)string4,Toast.LENGTH_SHORT).show();
                                if (mmustUpload){
                                        goGet();
                                }else {
                                    onBackPressed();
                                }


                            }else {
                                Toast.makeText((Context)AddDetailsPo.this, (CharSequence)errornya,Toast.LENGTH_SHORT).show();

                                loading.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            loading.dismiss();
//                        Toast.makeText(AddDetailsPo.this, t.toString(),Toast.LENGTH_LONG).show();
//                Toast.makeText(RatingStar.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                            cekInternet();
                        }
                    });

        }else{
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/pdf"), imagefile);
            String imgreq = String.valueOf(imagefile.getName());
//        Toast.makeText(getApplicationContext(), "" + imgreq, Toast.LENGTH_LONG).show();
            IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
//        mnotes.setText(myCustomArray.toString()+mpressId);
            //
            jsonPostService.uploadcharge(MultipartBody.Part.createFormData((String)"",
                    imgreq,requestBody),
                    RequestBody.create((MediaType)MultipartBody.FORM,sesionid_new),
                    RequestBody.create((MediaType)MultipartBody.FORM,mpressId),
                    RequestBody.create((MediaType)MultipartBody.FORM,mnopo.getText().toString()),
                    RequestBody.create((MediaType)MultipartBody.FORM,mnotes.getText().toString()),
                    RequestBody.create((MediaType)MultipartBody.FORM,String.valueOf(myCustomArray)),
                    RequestBody.create((MediaType)MultipartBody.FORM,PaymentTypeCd),
                    RequestBody.create((MediaType)MultipartBody.FORM,BuildConfig.VERSION_NAME))
                    .enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            String errornya = "";
                            JsonObject jsonObject=response.body();
//                Toast.makeText((Context)AddDetailFoc.this, jsonObject.toString(),Toast.LENGTH_SHORT).show();
                            String statusnya = jsonObject.get("status").getAsString();
                            if (jsonObject.get("errorMessage").toString().equals("null")) {

                            }else {
                                errornya = jsonObject.get("errorMessage").getAsString();
                            }
                            MhaveToUpdate = jsonObject.get("haveToUpdate").toString();
                            MsessionExpired = jsonObject.get("sessionExpired").toString();
                            sesionid();
                            if (statusnya.equals((Object)"OK")) {
                                String string4 = jsonObject.getAsJsonObject("data").get("message").getAsString();
                                loading.dismiss();
                                JsonObject data = jsonObject.getAsJsonObject("data");
                                noOrder = data.get("orderNo").getAsString();
                                Toast.makeText((Context)AddDetailsPo.this, (CharSequence)string4,Toast.LENGTH_SHORT).show();
                                if (mmustUpload){
                                    goGet();
                                }else {
                                    onBackPressed();
                                }

                            }else {
                                Toast.makeText((Context)AddDetailsPo.this, (CharSequence)errornya,Toast.LENGTH_SHORT).show();
                                loading.dismiss();
//                                Toast.makeText((Context)AddDetailsPo.this, "apinya",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            loading.dismiss();
//                        Toast.makeText(AddDetailsPo.this, t.toString(),Toast.LENGTH_LONG).show();
//                Toast.makeText(RatingStar.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                            cekInternet();
                        }
                    });

        }

    }
    private void launchPicker() {
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
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_PDF_REQUEST);

            }

        }else {
            new MaterialFilePicker()
                    .withActivity(this)
                    .withRequestCode(PICK_PDF_REQUEST)
                    .withHiddenFiles(true)
                    .withFilter(Pattern.compile(".*\\.pdf$"))
                    .withFilterDirectories(false)
                    .withTitle("Select PDF file")
                    .start();
        }

    }
    //handling the image chooser activity result

}