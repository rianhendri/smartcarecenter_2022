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
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.ver;

public class AddRequest extends AppCompatActivity {
    boolean showprep = true;
    String colortextrep = "";
    String textprep="";
    String bgprep = "";
    private static final int PERMISSION_CODE = 1000;
    public static JsonArray listsn;
    public static JsonArray listoperator;
    public static String requestby="";
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
    ImageView mimgbanner,mimgvis;
    Spinner mbranch;
    LinearLayout mback, mbgalert;
    ConstraintLayout mcapture;
    TextView mdate,mlocation,mrequest_no,mrequiredfoto,msend,mrequestname,mtextalert;
    EditText mdescrip;
    String mpressId = "";
    String moperatorcd="";
    int operatorpos = 0;
    String xlocation = "";
    LinearLayout mreadyfoto;
    Spinner msn;
    Spinner moperator;
    String nameFile;
    String pathImage;
    Uri photo_location;
    int quality = 40;
    DatabaseReference reference;
    String sesionid_new = "";
    List<String> snid = new ArrayList();
    List<String> locations = new ArrayList();
    List<String> snname = new ArrayList();
    List<String> operatorname = new ArrayList();
    List<String> operatorcd = new ArrayList();
    StorageReference storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
        mback = findViewById(R.id.backbtn);
        msn = (Spinner)this.findViewById(R.id.sn);
        moperator = findViewById(R.id.operator);
        mrequest_no = (TextView)this.findViewById(R.id.request_no);
        mdescrip = (EditText)this.findViewById(R.id.descrip);
        mdate = (TextView)this.findViewById(R.id.datereq);
        msend = (TextView)this.findViewById(R.id.send);
        mrequiredfoto = (TextView)this.findViewById(R.id.requiredfoto);
        mcapture = findViewById(R.id.upladfoto);
        mreadyfoto = (LinearLayout)this.findViewById(R.id.readyfoto);
        mimgvis = (ImageView)this.findViewById(R.id.imagevisible);
        mimgbanner = (ImageView)this.findViewById(R.id.imgbanner);
        mlocation = (TextView)this.findViewById(R.id.locationsn);
        mbranch = (Spinner)this.findViewById(R.id.branchspin);
        mlocation = findViewById(R.id.locationsn);
        mrequestname = findViewById(R.id.requestname);
        mbgalert = findViewById(R.id.backgroundalert);
        mtextalert = findViewById(R.id.textalert);
        //getsessionId
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {

            valuefilter = bundle2.getString("pos");
        }
        String string2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        mdate.setText((CharSequence)string2);
        getSessionId();
        cekInternet();
        if (internet){
            LoadPress();
            prepform();
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
                operatorname.clear();
                operatorcd.clear();
                mpressId = snid.get(position);
                xlocation = locations.get(position);
                    mlocation.setText(xlocation);
                JsonObject jsonObject2 = (JsonObject)listsn.get(position);
                listoperator=jsonObject2.getAsJsonArray("operatorList");
                for (int i = 0; i < listoperator.size(); ++i) {
                    JsonObject jobc = (JsonObject)listoperator.get(i);
                    String string4 = jobc.getAsJsonObject().get("operatorCd").getAsString();
                    String string5 = jobc.getAsJsonObject().get("operatorName").getAsString();
                    operatorcd.add(string4);
                    operatorname.add(string5);

                    if (operatorcd.get(i).equals(moperatorcd)){
                        operatorpos = i;
//                        Toast.makeText(AddRequest.this, String.valueOf(operatorpos),Toast.LENGTH_LONG).show();
                    }else {
                        operatorpos = 0;
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(AddRequest.this, R.layout.spinstatus_layout, operatorname);
                    arrayAdapter.setDropDownViewResource(R.layout.spinkategori);
                    arrayAdapter.notifyDataSetChanged();
                    moperator.setAdapter(arrayAdapter);
                    moperator.setSelection(operatorpos);
                }
//                Toast.makeText(AddRequest.this, listoperator.toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        moperator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                moperatorcd = operatorcd.get(position);
//                Toast.makeText(AddRequest.this, moperatorcd.toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                    mrequestname.setText(requestby);
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    listsn=data.getAsJsonArray("pressList");
                    for (int i = 0; i < listsn.size(); ++i) {
                        JsonObject jsonObject2 = (JsonObject)listsn.get(i);
                        String string4 = jsonObject2.getAsJsonObject().get("name").getAsString();
                        String string5 = jsonObject2.getAsJsonObject().get("id").getAsString();
                        String location = jsonObject2.getAsJsonObject().get("locationName").getAsString();
                        snname.add(string4);
                        snid.add(string5);
                        locations.add(location);
                        ArrayAdapter arrayAdapter = new ArrayAdapter(AddRequest.this, R.layout.spinstatus_layout, snname);
                        arrayAdapter.setDropDownViewResource(R.layout.spinkategori);
                        arrayAdapter.notifyDataSetChanged();
                        msn.setAdapter(arrayAdapter);
                        loading.dismiss();
                    }
                    loading.dismiss();
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
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        jsonPostService.uploadImage(MultipartBody.Part.createFormData((String)"",
                        imagefile.getName(),requestBody), RequestBody.create(MultipartBody.FORM,sesionid_new), RequestBody.create((MediaType)MultipartBody.FORM,
                mpressId), RequestBody.create((MediaType)MultipartBody.FORM,
                mdescrip.getText().toString()),RequestBody.create((MediaType)MultipartBody.FORM,
                moperatorcd),RequestBody.create((MediaType)MultipartBody.FORM,ver)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String errornya = "";
                JsonObject jsonObject=response.body();
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
                    Toast.makeText((Context)AddRequest.this, (CharSequence)string4,Toast.LENGTH_SHORT).show();
                    onBackPressed();

                }else {
                    Toast.makeText((Context)AddRequest.this, (CharSequence)errornya,Toast.LENGTH_SHORT).show();
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
                                mrequiredfoto.setText(getString(R.string.title_fotorequired));
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
    public void prepform(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",ver);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.prepform(jsonObject);
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
                    Toast.makeText(AddRequest.this, errornya,Toast.LENGTH_LONG).show();
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
}