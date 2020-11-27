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
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.smartcarecenter.Add_Foc_request.Add_foc_req_adapter.addFoclistreq;
import static com.smartcarecenter.Add_foc_Item_list_model.Add_foc_list_adapter.listpoact;
import static com.smartcarecenter.FormActivity.valuefilter;
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;

public class AddDetailFoc extends AppCompatActivity {
    boolean showprep = true;
    boolean stockhandcek = true;
    String colortextrep = "";
    String textprep="";
    String bgprep = "";
    public static JsonArray listsn;
    public static  String username = "";
    public static String matrixlabel = "";
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    String akunid = "";
    Boolean internet = false;
    ProgressDialog loading;
    LinearLayout mback;
    ImageView mimgbanner,mimgvis;
    int pos = 0;
    public static LinearLayout mlaytotal;
    public static TextView mdate,mstartimpresi,moperator,mno_order,mtotalitem,msend,mtotalqty,mnoitem;
    public static EditText mlastimpresi;
    public static String mpressId = "";
    public static String lastimpresi = "";
    String mpressId2 = "";
    Integer previmpressvlaue = 100;
    LinearLayout madd_item, mbgalert, mcapture;
    TextView mtextalert;
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
    public static String jsonarayitem = "";
    public static JsonArray myCustomArray;
    Gson gson;
    EditText mnotes;
    public static String notes1 = "";
    final int REQUEST_CAPTURE_IMAGE = 1;
    final int REQUEST_IMAGE_GALLERY = 2;
    byte[] datatyp;
    public static File imagefile = null;
    private static final int PERMISSION_CODE = 1000;
    public static Uri photo_location=null;
    int quality = 40;
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
        mbgalert = findViewById(R.id.backgroundalert);
        mtextalert = findViewById(R.id.textalert);
        mnotes = findViewById(R.id.descrip);
        mcapture = findViewById(R.id.upladfoto);
//        mimgvis = (ImageView)this.findViewById(R.id.imagevisible);
        mimgbanner = (ImageView)this.findViewById(R.id.imgbanner);
        cekInternet();
        getSessionId();
//        Toast.makeText(AddDetailFoc.this, notes1.toString(),Toast.LENGTH_LONG).show();
        if (notes1.equals("")){

        }else{
            mnotes.setText(notes1);
        }
        if (imagefile!=null){
            try {
                String imgreq = String.valueOf(imagefile.getName());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(new File(String.valueOf(imagefile))));
//                Toast.makeText(AddDetailFoc.this, imagefile.toString(),Toast.LENGTH_LONG).show();
//                Toast.makeText(AddDetailFoc.this, photo_location.toString(),Toast.LENGTH_LONG).show();
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap((ContentResolver)getContentResolver(), (Uri)this.photo_location);
                mimgbanner.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            Toast.makeText(AddDetailFoc.this, imagefile.toString(),Toast.LENGTH_LONG).show();
        }

        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(AddDetailFoc.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        mlistitem_foc.setLayoutManager(linearLayoutManager);
        mlistitem_foc.setHasFixedSize(false);
        reitem = new ArrayList<Add_foc_req_item>();
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
            mnoitem.setVisibility(View.GONE);
            mlistitem_foc.setVisibility(View.VISIBLE);
        }
        mlastimpresi.setText(lastimpresi);
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
        String string2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
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
                notes1=mnotes.getText().toString();
                lastimpresi = mlastimpresi.getText().toString();
                if (imagefile!=null){

                    Intent gotoaddfoc = new Intent(AddDetailFoc.this, Add_Foc_Item_List.class);
                    gotoaddfoc.putExtra("pressId",mpressId);
                    gotoaddfoc.putExtra("lastImpres",lastimpresi);
                    gotoaddfoc.putExtra("foto",imagefile);
                    gotoaddfoc.putExtra("uri",photo_location);
                    gotoaddfoc.putExtra("notesa",notes1);
//                    Toast.makeText(AddDetailFoc.this, imagefile.toString(),Toast.LENGTH_LONG).show();
//                    Toast.makeText(AddDetailFoc.this, photo_location.toString(),Toast.LENGTH_LONG).show();
                    startActivity(gotoaddfoc);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    finish();
                    listpoact.addAll(reitem);
                }else {
                    Intent gotoaddfoc = new Intent(AddDetailFoc.this, Add_Foc_Item_List.class);
                    gotoaddfoc.putExtra("pressId",mpressId);
                    gotoaddfoc.putExtra("lastImpres",lastimpresi);
                    gotoaddfoc.putExtra("notesa",notes1);
                    startActivity(gotoaddfoc);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    finish();
                    listpoact.addAll(reitem);
                }

            }
        });
        msend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internet){
                    if (mlastimpresi.getText().toString().length()==0){
                        mlastimpresi.setError(getString(R.string.title_requiredimpressi));
                        Toast.makeText(AddDetailFoc.this, getString(R.string.title_requiredimpressi),Toast.LENGTH_SHORT).show();

                    }else {

                        for (int ad = 0; ad < addFoclistreq.size(); ++ad) {
                            if (addFoclistreq.get(ad).getStockOnHand().equals(" ")){
                                stockhandcek = false;
                                Toast.makeText(AddDetailFoc.this, getString(R.string.title_stockhandrequired),Toast.LENGTH_SHORT).show();
                            }else {

                            }
                        }
                        if (stockhandcek){
                            if (imagefile!=null){
                                    showDialog();
                                }else {
                                Toast.makeText(AddDetailFoc.this, getString(R.string.title_fotorequired),Toast.LENGTH_LONG).show();
                                }

                        }else {
                            stockhandcek = true;
                        }
                    }
                }
            }
        });
        mcapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRequestImage();
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
        lastimpresi="";
        imagefile = null;
        notes1 ="";
        Intent back = new Intent(AddDetailFoc.this,FreeofchargeActivity.class);
        back.putExtra("pos",valuefilter);
        startActivity(back);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();

    }
    public void LoadPress(){
        loading = ProgressDialog.show(AddDetailFoc.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",ver);
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
                    sesionid();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AddDetailFoc.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
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
        jsonObject.addProperty("currentImpression",mlastimpresi.getText().toString());
        jsonObject.addProperty("notes",mnotes.getText().toString());
        jsonObject.add("items", myCustomArray);
        jsonObject.addProperty("ver",ver);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.sendData(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String errornya="";
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
                Toast.makeText(AddDetailFoc.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();


            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,  resultCode,  data);
        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap((ContentResolver)getContentResolver(), (Uri)this.photo_location);
                Bitmap bitmap2 = ThumbnailUtils.extractThumbnail((Bitmap)bitmap, (int)220, (int)220);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, this.quality, (OutputStream)byteArrayOutputStream);
                datatyp = byteArrayOutputStream.toByteArray();
                imagefile = null;
                try {
                    File file;
                    String string2 = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("JPEG_");
                    stringBuilder.append(string2);
                    stringBuilder.append("_");
                    imagefile = file = File.createTempFile((String)stringBuilder.toString(), (String)".jpg", (File)this.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(imagefile);
                    fileOutputStream.write(datatyp);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
//                this.mimgvis.setImageBitmap(bitmap2);
                this.mimgbanner.setImageBitmap(bitmap);
//                this.mrequiredfoto.setVisibility(GONE);

            }
            catch (IOException iOException) {
                iOException.printStackTrace();

            }
        }
        if (requestCode==REQUEST_IMAGE_GALLERY) {

            if (photo_location!=null){

                if (data!=null){
                    photo_location = data.getData();
//                    Toast.makeText(AddDetailFoc.this, photo_location.toString(),Toast.LENGTH_LONG).show();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photo_location);
                        Bitmap bitmap3 = ThumbnailUtils.extractThumbnail(bitmap, 220, 220);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
                        datatyp = byteArrayOutputStream.toByteArray();
                        imagefile = null;
                        try {
                            File file;
                            String string2 = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("JPEG_");
                            stringBuilder.append(string2);
                            stringBuilder.append("_");
                            imagefile = file = File.createTempFile((String)stringBuilder.toString(), (String)".jpg", (File)this.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
                            file.createNewFile();
                            FileOutputStream fileOutputStream = new FileOutputStream(imagefile);
                            fileOutputStream.write(datatyp);
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        }
                        catch (Exception exception) {
                            exception.printStackTrace();
                            Toast.makeText(AddDetailFoc.this, exception.toString(),Toast.LENGTH_LONG).show();
                        }
//                        mimgvis.setImageBitmap(bitmap3);
                        mimgbanner.setImageBitmap(bitmap);
//                        mrequiredfoto.setVisibility(View.GONE);
                    }
                    catch (IOException iOException) {
                        iOException.printStackTrace();
                        Toast.makeText(AddDetailFoc.this, iOException.toString(),Toast.LENGTH_LONG).show();
                    }
                }

            }

        }
        else {

        }

    }
    private void openCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", "New Picture");
        contentValues.put("description", "From the Camera");
        photo_location = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("output", photo_location);
        startActivityForResult(intent, REQUEST_CAPTURE_IMAGE);
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

        }
        CharSequence[] arrcharSequence = new CharSequence[]{"Kamera", "Galeri"};
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this).setTitle((CharSequence)"Add Image")
                .setItems(arrcharSequence,
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialogInterface, int n) {
                                if (n != 0) {
                                    if (n != 1) {
                                        return;
                                    }
                                    Intent imageIntentGallery = new Intent(Intent.ACTION_PICK,
                                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(imageIntentGallery, REQUEST_IMAGE_GALLERY);
                                    photo_location=imageIntentGallery.getData();


                                }else {
                                    openCamera();
                                }

                            }
                        });
        builder.create();
        builder.show();
    }
    public void uploadData(){
        loading = ProgressDialog.show(this, "", getString(R.string.title_loading), true);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),imagefile);
        String imgreq = String.valueOf(imagefile.getName());
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
//        mnotes.setText(myCustomArray.toString()+mpressId);
        //
        jsonPostService.uploadfoc(MultipartBody.Part.createFormData((String)"",
                imgreq,requestBody),
                RequestBody.create((MediaType)MultipartBody.FORM,sesionid_new),
                RequestBody.create((MediaType)MultipartBody.FORM,mpressId),
                RequestBody.create((MediaType)MultipartBody.FORM,mlastimpresi.getText().toString()),
                RequestBody.create((MediaType)MultipartBody.FORM,mnotes.getText().toString()),
                RequestBody.create((MediaType)MultipartBody.FORM,String.valueOf(myCustomArray)),
                RequestBody.create((MediaType)MultipartBody.FORM,ver))
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
                    Toast.makeText((Context)AddDetailFoc.this, (CharSequence)string4,Toast.LENGTH_SHORT).show();
                    onBackPressed();

                }else {
                    Toast.makeText((Context)AddDetailFoc.this, (CharSequence)errornya,Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AddDetailFoc.this, t.toString(),Toast.LENGTH_LONG).show();
//                Toast.makeText(RatingStar.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
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
        jsonObject.addProperty("ver",ver);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.prepfoc(jsonObject);
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
                    JsonObject data = homedata.getAsJsonObject("data");
                    showprep = data.get("showMessage").getAsBoolean();

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
                    Toast.makeText(AddDetailFoc.this, errornya,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AddDetailFoc.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();


            }
        });
    }
}