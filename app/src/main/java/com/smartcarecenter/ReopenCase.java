package com.smartcarecenter;

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
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

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

import static com.smartcarecenter.FormActivity.valuefilter;
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;

public class ReopenCase extends AppCompatActivity {
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
    LinearLayout msubmit, mcapture, mback;
    ConstraintLayout muploadlay;
    ImageView mimage;
    EditText mcomment;
    String noreq = "";
    String noticket = "";
    int ratvalue = 0;
    String sesionid_new = "";
    boolean value;
    RequestBody requestBody = null;
    String imgreq ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reopen_case);

        mimage = findViewById(R.id.imgbanner);
        mcapture = (LinearLayout)this.findViewById(R.id.upladfoto);
        muploadlay = findViewById(R.id.uploadlay);
        mcomment = (EditText)findViewById(R.id.comment);
        msubmit = findViewById(R.id.submit);
        mback = findViewById(R.id.backbtn);
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            noreq = extras.getString("id");
            noticket = extras.getString("noticket");
            valuefilter = extras.getString("pos");
        }
        getSessionId();
        cekInternet();

        if (internet){

        }else {

        }
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        msubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mcomment.length()!=0){
                    uploadData();
                }else {
                    mcomment.setError(getString(R.string.title_deserror));
//                    mcomment.setTextColor(getResources().getColor(R.color.red));
                }
            }
        });
        mcapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestImage();
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
                            Toast.makeText(ReopenCase.this, exception.toString(),Toast.LENGTH_LONG).show();
                        }
//                        mimgvis.setImageBitmap(bitmap3);
                        mimage.setImageBitmap(bitmap);
                        muploadlay.setVisibility(View.VISIBLE);
//                        mrequiredfoto.setVisibility(View.GONE);
                    }
                    catch (IOException iOException) {
                        iOException.printStackTrace();
                        Toast.makeText(ReopenCase.this, iOException.toString(),Toast.LENGTH_LONG).show();
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
        ConnectivityManager connectivityManager = (ConnectivityManager) ReopenCase.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(ReopenCase.this, NoInternet.class);
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
                Intent gotoupdate = new Intent(ReopenCase.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(ReopenCase.this, LoginActivity.class));
            finish();
            Toast.makeText(ReopenCase.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(ReopenCase.this,DetailsFormActivity.class);
        back.putExtra("id",noreq);
        back.putExtra("pos",valuefilter);
        back.putExtra("user", DetailsFormActivity.username);
        startActivity(back);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
    public void uploadData(){
        loading = ProgressDialog.show(ReopenCase.this, "", getString(R.string.title_loading), true);
        if (imagefile==null){
            IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
            jsonPostService.reopen(MultipartBody.Part.createFormData((String)"",
                    ""),
                    RequestBody.create((MediaType)MultipartBody.FORM,sesionid_new),
                    RequestBody.create((MediaType)MultipartBody.FORM,noreq),
                    RequestBody.create((MediaType)MultipartBody.FORM,mcomment.getText().toString()),
                    RequestBody.create((MediaType)MultipartBody.FORM,ver)).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    String errornya = "";
                    JsonObject jsonObject=response.body();
//                    Toast.makeText((Context)ReopenCase.this,jsonObject.toString(),Toast.LENGTH_SHORT).show();
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
                        Toast.makeText((Context)ReopenCase.this, (CharSequence)string4,Toast.LENGTH_SHORT).show();
                        Intent back = new Intent(ReopenCase.this,FormActivity.class);
                        back.putExtra("id",noreq);
                        back.putExtra("pos",valuefilter);
                        back.putExtra("user", DetailsFormActivity.username);
                        startActivity(back);
                        overridePendingTransition(R.anim.left_in, R.anim.right_out);
                        finish();

                    }else {
                        Toast.makeText((Context)ReopenCase.this, (CharSequence)errornya,Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    loading.dismiss();
                    Toast.makeText(ReopenCase.this, t.toString(),Toast.LENGTH_LONG).show();
//                Toast.makeText(RatingStar.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                    cekInternet();
                }
            });
        }else {
            requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),imagefile);
            imgreq = String.valueOf(imagefile.getName());
            IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
            jsonPostService.reopen(
                    MultipartBody.Part.createFormData((String)"",
                    imgreq,requestBody),
                    RequestBody.create((MediaType)MultipartBody.FORM,sesionid_new),
                    RequestBody.create((MediaType)MultipartBody.FORM,noreq),
                    RequestBody.create((MediaType)MultipartBody.FORM,mcomment.getText().toString()),
                    RequestBody.create((MediaType)MultipartBody.FORM,ver)).enqueue(new Callback<JsonObject>() {
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
                        Toast.makeText((Context)ReopenCase.this, (CharSequence)string4,Toast.LENGTH_SHORT).show();
                        Toast.makeText((Context)ReopenCase.this, (CharSequence)string4,Toast.LENGTH_SHORT).show();
                        Intent back = new Intent(ReopenCase.this,FormActivity.class);
                        back.putExtra("id",noreq);
                        back.putExtra("pos",valuefilter);
                        back.putExtra("user", DetailsFormActivity.username);
                        startActivity(back);
                        overridePendingTransition(R.anim.left_in, R.anim.right_out);
                        finish();

                    }else {
                        Toast.makeText((Context)ReopenCase.this, (CharSequence)errornya,Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    loading.dismiss();
                    Toast.makeText(ReopenCase.this, t.toString(),Toast.LENGTH_LONG).show();
//                Toast.makeText(RatingStar.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                    cekInternet();
                }
            });
        }



    }
}