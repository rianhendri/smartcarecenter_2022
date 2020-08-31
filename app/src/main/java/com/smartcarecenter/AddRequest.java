package com.smartcarecenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
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
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.serviceticket.ServiceTicketAdapter;
import com.smartcarecenter.serviceticket.ServicesTicketItem;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.text.ParseException;
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

import static android.os.Build.VERSION.SDK_INT;
import static android.widget.AdapterView.*;
import static com.smartcarecenter.DetailsFormActivity.noreq;
import static com.smartcarecenter.FormActivity.valuefilter;

public class AddRequest extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1000;
    public static JsonArray listsn;
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    final int REQUEST_CAPTURE_IMAGE = 1;
    final int REQUEST_IMAGE_GALLERY = 2;
    String akunid = "";
    byte[] datatyp;
    File imagefile = null;
    Uri imguri;
    Boolean internet = false;
    ProgressDialog loading;
    ImageView mback,mimgbanner,mimgvis;
    Spinner mbranch;
    LinearLayout mcapture;
    TextView mdate,mlocation,moperator,mrequest_no,mrequiredfoto,msend;
    EditText mdescrip;
    String mpressId = "";
    LinearLayout mreadyfoto;
    Spinner msn;
    String nameFile;
    String pathImage;
    Uri photo_location;
    int quality = 40;
    DatabaseReference reference;
    String sesionid_new = "";
    List<String> snid = new ArrayList();
    List<String> snname = new ArrayList();
    StorageReference storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
        mback = (ImageView)this.findViewById(R.id.backbtn);
        msn = (Spinner)this.findViewById(R.id.sn);
        moperator = (TextView)this.findViewById(R.id.operator);
        mrequest_no = (TextView)this.findViewById(R.id.request_no);
        mdescrip = (EditText)this.findViewById(R.id.descrip);
        mdate = (TextView)this.findViewById(R.id.datereq);
        msend = (TextView)this.findViewById(R.id.send);
        mrequiredfoto = (TextView)this.findViewById(R.id.requiredfoto);
        mcapture = (LinearLayout)this.findViewById(R.id.upladfoto);
        mreadyfoto = (LinearLayout)this.findViewById(R.id.readyfoto);
        mimgvis = (ImageView)this.findViewById(R.id.imagevisible);
        mimgbanner = (ImageView)this.findViewById(R.id.imgbanner);
        mlocation = (TextView)this.findViewById(R.id.locationsn);
        mbranch = (Spinner)this.findViewById(R.id.branchspin);
        //getsessionId
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {

            valuefilter = bundle2.getString("pos");
        }
        String string2 = new SimpleDateFormat("d-MM-yyyy", Locale.getDefault()).format(new Date());
        mdate.setText((CharSequence)string2);
        getSessionId();
        cekInternet();
        if (internet){
            LoadPress();
        }else {

        }
        mcapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRequestImage();
            }
        });
        msend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekInternet();
                if (internet){

                    showDialog();
                }
            }
        });
        msn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < snid.size(); ++i) {
                    mpressId = snid.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                    this.mimgvis.setImageBitmap(bitmap2);
                    this.mimgbanner.setImageBitmap(bitmap);
                    this.mrequiredfoto.setVisibility(GONE);

                }
                catch (IOException iOException) {
                    iOException.printStackTrace();

                }
            }
            if (requestCode==REQUEST_IMAGE_GALLERY) {
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
                        Toast.makeText(AddRequest.this, exception.toString(),Toast.LENGTH_LONG).show();
                    }
                    mimgvis.setImageBitmap(bitmap3);
                    mimgbanner.setImageBitmap(bitmap);
                    mrequiredfoto.setVisibility(View.GONE);
                }
                catch (IOException iOException) {
                    iOException.printStackTrace();
                    Toast.makeText(AddRequest.this, iOException.toString(),Toast.LENGTH_LONG).show();
                }
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
        ConnectivityManager connectivityManager = (ConnectivityManager) AddRequest.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(AddRequest.this, NoInternet.class);
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
                Intent gotoupdate = new Intent(AddRequest.this, UpdateActivity.class);
                startActivity(gotoupdate);
                finish();
            }

        }else {
            startActivity(new Intent(AddRequest.this, LoginActivity.class));
            finish();
            Toast.makeText(AddRequest.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(AddRequest.this,FormActivity.class);
        back.putExtra("pos",valuefilter);
        startActivity(back);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }
    public void LoadPress(){
        loading = ProgressDialog.show(AddRequest.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, "http://api.smartcarecenter.id/");
        Call<JsonObject> panggilkomplek = jsonPostService.postRawJSONpresslist(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                String errornya = homedata.get("errorMessage").toString();
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
                if (statusnya.equals("OK")) {
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    listsn=data.getAsJsonArray("pressList");
                    for (int i = 0; i < listsn.size(); ++i) {
                        JsonObject jsonObject2 = (JsonObject)listsn.get(i);
                        String string4 = jsonObject2.getAsJsonObject().get("name").getAsString();
                        String string5 = jsonObject2.getAsJsonObject().get("id").getAsString();
                        snname.add(string4);
                        snid.add(string5);
                        ArrayAdapter arrayAdapter = new ArrayAdapter(AddRequest.this, R.layout.spinstatus_layout, snname);
                        arrayAdapter.setDropDownViewResource(R.layout.spinkategori);
                        arrayAdapter.notifyDataSetChanged();
                        msn.setAdapter(arrayAdapter);
                        loading.dismiss();
                    }
                }else {
                    Toast.makeText(AddRequest.this, errornya.toString(),Toast.LENGTH_LONG).show();
                    loading.dismiss();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AddRequest.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();


            }
        });
    }
    public void uploadData(){
        loading = ProgressDialog.show(AddRequest.this, "", getString(R.string.title_loading), true);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),imagefile);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, "http://api.smartcarecenter.id/");
        jsonPostService.uploadImage(MultipartBody.Part.createFormData((String)"",
                        imagefile.getName(),requestBody), RequestBody.create(MultipartBody.FORM,sesionid_new), RequestBody.create((MediaType)MultipartBody.FORM,
                mpressId), RequestBody.create((MediaType)MultipartBody.FORM,
                mdescrip.getText().toString())).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = (JsonObject)response.body();
                String string2 = jsonObject.get("status").getAsString();
                String string3 = jsonObject.get("errorMessage").toString();
                MhaveToUpdate = jsonObject.get("haveToUpdate").toString();
                MsessionExpired = jsonObject.get("sessionExpired").toString();
                sesionid();
                if (string2.equals((Object)"OK")) {
                    String string4 = jsonObject.getAsJsonObject("data").get("message").getAsString();
                    loading.dismiss();
                    Toast.makeText((Context)AddRequest.this, (CharSequence)string4,Toast.LENGTH_SHORT).show();
                    onBackPressed();

                }else {
                    Toast.makeText((Context)AddRequest.this, (CharSequence)string3,Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AddRequest.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
            }
        });

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
                        if (imagefile!=null){
                            if (mdescrip.length()!=0){
                                uploadData();
                            }else {
                                mdescrip.setError(getString(R.string.title_deserror));
                                mrequiredfoto.setText(getString(R.string.title_deserror));
                                mrequiredfoto.setTextColor(getResources().getColor(R.color.red));
                            }

                        }else {
                            mrequiredfoto.setText(getString(R.string.title_fotorequired));
                            mrequiredfoto.setTextColor(getResources().getColor(R.color.red));

                        }

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