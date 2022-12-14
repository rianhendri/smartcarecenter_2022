package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
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
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;

import static android.view.View.GONE;
import static com.smartcarecenter.FormActivity.valuefilter;
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;

public class RatingStar extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1000;
    Uri photo_location;
    int quality = 40;
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    final int REQUEST_CAPTURE_IMAGE = 1;
    final int REQUEST_IMAGE_GALLERY = 2;
    String akunid = "";
    byte[] datatyp;
    File imagefile = null;
    boolean approve = true;
    String errornya = "";
    boolean internet = true;
    ProgressDialog loading;
    EditText mcomment, mcom1,mcom2,mcom3;
    TextView mnoticket;
    RatingBar mratingstar,mrating2;
    TextView mratingvalue;
    LinearLayout msolved;
    LinearLayout munsolved,mcapture, mlayoutmonitor, mlayoutwaktu, mlay1,mlay2,mlay3;
    ConstraintLayout mlayoutimg;
    ImageView mimage;
    String noreq = "";
    String guid = "";
    String page2 = "";
    String noticket = "";
    int ratvalue = 0;
    String sesionid_new = "";
    Spinner msn;
    List<Boolean> listvalue = new ArrayList<>();
    boolean value = true;
    RequestBody requestBody = null;
    String imgreq ="";
    String imgbody ="";
    ConstraintLayout muploadlay;
    Integer solve = null;
    RadioButton myes,mno, mtiga, mpatbelas, mtujuh,miya,mtidak, m1,m2,m3,m4,m5, mmudah,mlumayan,msusah;
    boolean cekmonitor = true;
    Integer days = null;

    int ratingangka=0;
    Boolean iyatidak = true;
    String mudahlumayan="Mudah";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_star);
        mlay1 = findViewById(R.id.layjelas1);
        mlay2 = findViewById(R.id.layjelas2);
        mlay3 = findViewById(R.id.layjelas3);
        mcom1 = findViewById(R.id.alasanrating3);
        mcom2 = findViewById(R.id.alasantidak);
        mcom3 = findViewById(R.id.mudahalasan);
        miya = findViewById(R.id.iya);
        mtidak = findViewById(R.id.tidak);
        mrating2 = findViewById(R.id.ratingstar2);
//        m1 = findViewById(R.id.satu);
//        m2 = findViewById(R.id.dua);
//        m3 = findViewById(R.id.tigaa);
//        m4 = findViewById(R.id.empat);
//        m5 = findViewById(R.id.lima);
        mmudah = findViewById(R.id.mudah);
        mlumayan =findViewById(R.id.lumayan);
        msusah =findViewById(R.id.susah);

        msolved = (LinearLayout)findViewById(R.id.solved);
        munsolved = (LinearLayout)findViewById(R.id.unsolved);
        mratingvalue = (TextView)findViewById(R.id.ratingvalue);
        mnoticket = (TextView)findViewById(R.id.noticket);
        mcomment = (EditText)findViewById(R.id.comment);
        mratingstar = (RatingBar)findViewById(R.id.ratingstar);
        msn = findViewById(R.id.snrat);
        mimage = findViewById(R.id.imgbanner);
        mcapture = (LinearLayout)this.findViewById(R.id.upladfoto);
        muploadlay = findViewById(R.id.uploadlay);
        mlayoutmonitor = findViewById(R.id.layoutmonitor);
        mlayoutwaktu = findViewById(R.id.waktu);
        mtiga = findViewById(R.id.tiga);
        mtujuh = findViewById(R.id.tujuh);
        mpatbelas = findViewById(R.id.patbelas);
        myes = findViewById(R.id.yesin);
        mno = findViewById(R.id.noin);
//        mlayoutimg= findViewById(R.id.layoutimg);
        String[] arraySpinner = new String[]{
                getString(R.string.title_yes), getString(R.string.title_no)
        };
        listvalue.add(true);
        listvalue.add(false);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinstatus_layout, arraySpinner);
        arrayAdapter.setDropDownViewResource(R.layout.spinkategori);
        arrayAdapter.notifyDataSetChanged();
        msn.setAdapter(arrayAdapter);
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            page2 = extras.getString("page");
            noreq = extras.getString("id");
            guid = extras.getString("guid");
            noticket = extras.getString("noticket");
            valuefilter = extras.getString("pos");
        }
        getSessionId();
        cekInternet();

        if (internet){

        }else {

        }
//        m1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ratingangka = 1;
//
//                if (ratingangka<4){
//                    mlay1.setVisibility(View.VISIBLE);
//                }else {
//                    mlay1.setVisibility(GONE);
//                }
//            }
//        });
//        m2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ratingangka = 2;
//
//                if (ratingangka<4){
//                    mlay1.setVisibility(View.VISIBLE);
//                }else {
//                    mlay1.setVisibility(GONE);
//                }
//            }
//        });
//        m3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ratingangka = 3;
//
//                if (ratingangka<4){
//                    mlay1.setVisibility(View.VISIBLE);
//                }else {
//                    mlay1.setVisibility(GONE);
//                }
//            }
//        });
//        m4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ratingangka = 4;
//
//                if (ratingangka<4){
//                    mlay1.setVisibility(View.VISIBLE);
//                }else {
//                    mlay1.setVisibility(GONE);
//                }
//            }
//        });
//        m5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ratingangka = 5;
//
//                if (ratingangka<4){
//                    mlay1.setVisibility(View.VISIBLE);
//                }else {
//                    mlay1.setVisibility(GONE);
//                }
//            }
//        });

        miya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iyatidak = true;

                if (iyatidak){
                    mlay2.setVisibility(GONE);
                }else {
                    mlay2.setVisibility(View.VISIBLE);
                }
            }
        });
        mtidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iyatidak = false;

                if (iyatidak){
                    mlay2.setVisibility(GONE);
                }else {
                    mlay2.setVisibility(View.VISIBLE);
                }
            }
        });

        mmudah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mudahlumayan = "Mudah";

                if (mudahlumayan.equals("Mudah")){
                    mlay3.setVisibility(GONE);
                }else {
                    mlay3.setVisibility(View.VISIBLE);
                }
            }
        });
        mlumayan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mudahlumayan = "Lumayan";

                if (mudahlumayan.equals("Lumayan")){
                    mlay3.setVisibility(View.VISIBLE);
                }else {
                    mlay3.setVisibility(GONE);
                }
            }
        });
        msusah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mudahlumayan = "Susah";

                if (mudahlumayan.equals("Susah")){
                    mlay3.setVisibility(View.VISIBLE);
                }else {
                    mlay3.setVisibility(GONE);
                }
            }
        });
        myes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekmonitor = true;
                days = 3;
                if (cekmonitor){
                    mlayoutwaktu.setVisibility(View.VISIBLE);
                }else {
                    mlayoutwaktu.setVisibility(GONE);
                }
            }
        });
        mno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekmonitor = false;
                days = null;
                if (cekmonitor){
                    mlayoutwaktu.setVisibility(View.VISIBLE);
                }else {
                    mlayoutwaktu.setVisibility(GONE);
                }
            }
        });
        mtiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days = 3;
            }
        });
        mtujuh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days = 7;
            }
        });
        mpatbelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days = 14;
            }
        });
        msn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                value = listvalue.get(position);
//                Toast.makeText(RatingStar.this, String.valueOf(value), Toast.LENGTH_SHORT).show();
                if (value){
                    mlayoutmonitor.setVisibility(View.VISIBLE);
                    if (cekmonitor){
                        mlayoutwaktu.setVisibility(View.VISIBLE);
                    }else {
                        mlayoutwaktu.setVisibility(GONE);
                    }
                }else {
                    mlayoutmonitor.setVisibility(GONE);
                        mlayoutwaktu.setVisibility(GONE);
                    days = null;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mnoticket.setText("#"+noreq);
        mratingstar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
            mratingvalue.setText("Rating: "+ String.valueOf(v));
            ratvalue = (int) v;
            }
        });
        mrating2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
//                mratingvalue.setText("Rating: "+ String.valueOf(v));
                ratingangka = (int) v;
                Log.d("ratingnya",String.valueOf(ratingangka));
                if (ratingangka<1){
                    mlay1.setVisibility(GONE);
                }else if (ratingangka>3){
                    mlay1.setVisibility(GONE);
                }else {
                    mlay1.setVisibility(View.VISIBLE);
                }
            }
        });
        msolved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekInternet();
                if (ratvalue == 0) {
                    Toast.makeText(RatingStar.this, getString(R.string.title_require_rate), Toast.LENGTH_SHORT).show();

                }else {
                    if (ratingangka==0){
                        Toast.makeText(RatingStar.this, "mohon beri rating pada pertanyaan pertama", Toast.LENGTH_SHORT).show();

                    }else {
                        if (ratingangka<4){
                            if (mcom1.length()==0){
                                Toast.makeText(RatingStar.this, "mohon beri penjelasan pada pertanyaan pertama", Toast.LENGTH_SHORT).show();
                            }else {
                                ceksend();
                            }
                        }else {
                            ceksend();
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
        muploadlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestImage();
            }
        });
//        munsolved.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cekInternet();
//                if (ratvalue == 0) {
//                    Toast.makeText(RatingStar.this, getString(R.string.title_require_rate), Toast.LENGTH_SHORT).show();
//
//                }else {
//                    approve = true;
//                    if (internet) {
//                        sendRate();
//                    }
//                }
//
//
//            }
//        });
    }
    public void ceksend()
    {

            if (iyatidak){
                if (mudahlumayan.equals("Mudah")){
                    if (internet)
                    {
//                        Toast.makeText(RatingStar.this, "kirim", Toast.LENGTH_SHORT).show();
                                                    uploadData();
                    }
                }else {
                    if (mcom3.length()==0){
                        Toast.makeText(RatingStar.this, "mohon beri penjelasan pada pertanyaan ketiga", Toast.LENGTH_SHORT).show();
                    }else {
                        if (internet)
                        {
//                            Toast.makeText(RatingStar.this, "kirim", Toast.LENGTH_SHORT).show();
                                                    uploadData();
                        }
                    }

                }
            }else {
                if (mcom2.length()==0){
                    Toast.makeText(RatingStar.this, "mohon beri penjelasan pada pertanyaan kedua", Toast.LENGTH_SHORT).show();

                }else {
                    if (mudahlumayan.equals("Mudah")){
//                                     approve = true;
                        if (internet) {
//                            Toast.makeText(RatingStar.this, "kirim", Toast.LENGTH_SHORT).show();
                                                    uploadData();
                        }
                    }else {
                        if (mcom3.length()==0){
                            Toast.makeText(RatingStar.this, "mohon beri penjelasan pada pertanyaan ketiga", Toast.LENGTH_SHORT).show();
                        }else {
                            approve = true;
                            if (internet) {
//                                Toast.makeText(RatingStar.this, "kirim", Toast.LENGTH_SHORT).show();
                                                    uploadData();
                            }
//
                        }

                    }
                }

            }

    }
    public void sendRate(){
        loading = ProgressDialog.show(RatingStar.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId", sesionid_new);
        jsonObject.addProperty("formRequestCd", noreq);
        jsonObject.addProperty("rating", ratvalue);
        jsonObject.addProperty("comments", mcomment.getText().toString());
        jsonObject.addProperty("isApprove",value);
        jsonObject.addProperty("monitorCase",String.valueOf(cekmonitor));
        jsonObject.addProperty("monitorDays",String.valueOf(days));
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
//        Toast.makeText(RatingStar.this,  String.valueOf(jsonObject),Toast.LENGTH_LONG).show();
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONconfirm(jsonObject);
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
                    Toast.makeText(RatingStar.this, message,Toast.LENGTH_LONG).show();
                    Intent back = new Intent(RatingStar.this,FormActivity.class);
                    back.putExtra("pos",valuefilter);
                    startActivity(back);
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    finish();
                    loading.dismiss();
                }else {
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(RatingStar.this, errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(RatingStar.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
        Log.d("sendrat",jsonObject.toString());
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
                this.mimage.setImageBitmap(bitmap);
                muploadlay.setVisibility(View.VISIBLE);
//                this.mrequiredfoto.setVisibility(GONE);

            }
            catch (IOException iOException) {
                iOException.printStackTrace();

            }
        }
        if (requestCode==REQUEST_IMAGE_GALLERY) {

            if (photo_location!=null){
//                    Toast.makeText(AddRequest.this, photo_location.toString(),Toast.LENGTH_LONG).show();
                if (data!=null){
                    photo_location = data.getData();
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
                            Toast.makeText(RatingStar.this, exception.toString(),Toast.LENGTH_LONG).show();
                        }
//                        mimgvis.setImageBitmap(bitmap3);
                        mimage.setImageBitmap(bitmap);
                        muploadlay.setVisibility(View.VISIBLE);
//                        mrequiredfoto.setVisibility(View.GONE);
                    }
                    catch (IOException iOException) {
                        iOException.printStackTrace();
                        Toast.makeText(RatingStar.this, iOException.toString(),Toast.LENGTH_LONG).show();
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) this, Manifest.permission.CAMERA)&& ActivityCompat.shouldShowRequestPermissionRationale((Activity) this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                //Show permission dialog
            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity)this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);

            }

        }
        CharSequence[] arrcharSequence = new CharSequence[]{"Kamera", "Galeri"};
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this).setTitle((CharSequence)"Add Image").setItems(arrcharSequence,
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
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) RatingStar.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(RatingStar.this, NoInternet.class);
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
                Intent gotoupdate = new Intent(RatingStar.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(RatingStar.this, LoginActivity.class));
            finish();
            Toast.makeText(RatingStar.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (page2==null){
            Intent back = new Intent(RatingStar.this,DetailsFormActivity.class);
            back.putExtra("id",noreq);
            back.putExtra("pos",valuefilter);
            back.putExtra("user", DetailsFormActivity.username);
            startActivity(back);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
        }else {
            Intent back = new Intent(RatingStar.this,DetailsNotification.class);
            back.putExtra("id",noreq);
            back.putExtra("guid",guid);
            startActivity(back);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();

        }

    }
    public void uploadData(){
        loading = ProgressDialog.show(RatingStar.this, "", getString(R.string.title_loading), true);
        if (imagefile==null){
            MediaType json = MediaType.parse("application/json");
            MediaType image = MediaType.parse("image/*");
            MediaType text = MediaType.parse("text/plain");
            IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
            jsonPostService.uploadRating(
                    MultipartBody.Part.createFormData((String)"",
                    ""),
                    RequestBody.create((MediaType)MultipartBody.FORM,sesionid_new),
                    RequestBody.create((MediaType)MultipartBody.FORM,noreq),
                    RequestBody.create((MediaType)MultipartBody.FORM,String.valueOf(ratvalue)),
                    RequestBody.create((MediaType)MultipartBody.FORM,mcomment.getText().toString()),
                   value,
                    RequestBody.create((MediaType)MultipartBody.FORM,BuildConfig.VERSION_NAME),
                   cekmonitor,
                    RequestBody.create((MediaType)MultipartBody.FORM,String.valueOf(days)),

                   ratingangka,
                    RequestBody.create((MediaType)MultipartBody.FORM,mcom1.getText().toString()),
                    iyatidak,
                    RequestBody.create((MediaType)MultipartBody.FORM,mcom2.getText().toString()),
                    RequestBody.create((MediaType)MultipartBody.FORM,mudahlumayan),
                    RequestBody.create((MediaType)MultipartBody.FORM,mcom3.getText().toString())
                    ).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    String errornya = "";
                    JsonObject jsonObject=response.body();
                    Log.d("respone",jsonObject.toString());
//                    Toast.makeText((Context)RatingStar.this,jsonObject.toString(),Toast.LENGTH_SHORT).show();
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
                        Toast.makeText((Context)RatingStar.this, (CharSequence)string4,Toast.LENGTH_SHORT).show();

                        if (page2==null){
                            onBackPressed();
                        }else {
                            Intent back = new Intent(RatingStar.this,Dashboard.class);
                            startActivity(back);
                            overridePendingTransition(R.anim.left_in, R.anim.right_out);
                            finish();
                        }


//                        if (page2.equals("notif")){
//                            Intent back = new Intent(RatingStar.this,Dashboard.class);
//                            startActivity(back);
//                            overridePendingTransition(R.anim.left_in, R.anim.right_out);
//                            finish();
//                        }else {
//
//                        }

                    }else {
                        Toast.makeText((Context)RatingStar.this, (CharSequence)errornya,Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    loading.dismiss();
                    Toast.makeText(RatingStar.this, t.toString(),Toast.LENGTH_LONG).show();
//                Toast.makeText(RatingStar.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                    cekInternet();
                }
            });
        }else {
            requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),imagefile);
            imgreq = String.valueOf(imagefile.getName());
            IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
            jsonPostService.uploadRating(MultipartBody.Part.createFormData((String)"",
                    imgreq,requestBody),
                    RequestBody.create((MediaType)MultipartBody.FORM,sesionid_new),
                    RequestBody.create((MediaType)MultipartBody.FORM,noreq),
                    RequestBody.create((MediaType)MultipartBody.FORM,String.valueOf(ratvalue)),
                    RequestBody.create((MediaType)MultipartBody.FORM,mcomment.getText().toString()),
                   value,
                    RequestBody.create((MediaType)MultipartBody.FORM,BuildConfig.VERSION_NAME),
                   cekmonitor,
                    RequestBody.create((MediaType)MultipartBody.FORM,String.valueOf(days)),

                    ratingangka,
                    RequestBody.create((MediaType)MultipartBody.FORM,mcom1.getText().toString()),
                    iyatidak,
                    RequestBody.create((MediaType)MultipartBody.FORM,mcom2.getText().toString()),
                    RequestBody.create((MediaType)MultipartBody.FORM,mudahlumayan),
                    RequestBody.create((MediaType)MultipartBody.FORM,mcom3.getText().toString())
            ).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    String errornya = "";
                    JsonObject jsonObject=response.body();
//                    Toast.makeText((Context)RatingStar.this,jsonObject.toString(),Toast.LENGTH_SHORT).show();
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
                        Toast.makeText((Context)RatingStar.this, (CharSequence)string4,Toast.LENGTH_SHORT).show();
                        if (page2==null){
                            onBackPressed();
                        }else {
                            Intent back = new Intent(RatingStar.this,Dashboard.class);
                            startActivity(back);
                            overridePendingTransition(R.anim.left_in, R.anim.right_out);
                            finish();
                        }
//                        if (page2.equals("notif")){
//                            Intent back = new Intent(RatingStar.this,Dashboard.class);
//                            startActivity(back);
//                            overridePendingTransition(R.anim.left_in, R.anim.right_out);
//                            finish();
//                        }else {
//                            onBackPressed();
//                        }


                    }else {
                        Toast.makeText((Context)RatingStar.this, (CharSequence)errornya,Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    loading.dismiss();
                    Toast.makeText(RatingStar.this, t.toString(),Toast.LENGTH_LONG).show();
//                Toast.makeText(RatingStar.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                    cekInternet();
                }
            });
        }



    }
    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle(getString(R.string.title_send_request));

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage(getString(R.string.title_Submitreq))
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.title_yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
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
}