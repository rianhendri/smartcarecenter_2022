package com.smartcarecenter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.Chat.Adapterchat;
import com.smartcarecenter.Chat.ItemUid;
import com.smartcarecenter.Chat.Itemchat;
import com.smartcarecenter.Chat.Itemchat2;
import com.smartcarecenter.SendNotificationPack.APIService;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.historyfr.AdapterHistoryfr;
import com.smartcarecenter.historyfr.ItemHistoryfr;
import com.smartcarecenter.messagecloud.check;
import com.smartcarecenter.serviceticket.ServiceTicketAdapter;
import com.smartcarecenter.serviceticket.ServicesTicketItem;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.smartcarecenter.Chat.Adapterchat.addFoclistreq;
import static com.smartcarecenter.DetailsFormActivity.mcustname;
import static com.smartcarecenter.DetailsFormActivity.mformRequestCd;
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;
import static com.smartcarecenter.apihelper.ServiceGenerator.fcmbase;
import static com.smartcarecenter.historyfr.AdapterHistoryfr.frpos;
import static com.smartcarecenter.messagecloud.check.tokennya2;
import static com.smartcarecenter.LiveChatList.itemchat;
public class ListChat extends AppCompatActivity {

    ///chtregst
    //
    FirebaseAuth mAuth;
    ItemUid ietmuid ;
    String nme="";
    String uidnya="";
    String emainya="";
    //note prepare
    LinearLayout mlaypreparenote;
    TextView mmessageprepare;
    String colorbacground="";
    String colortextnya="";
    String mustupload = "";
    int ping=0;
    String nofr = "";
    String custnmae="";
    Boolean liveChatRepor=false;
    String id = "";
    String titlenya= "";
    String module = "";
    String pagechat="";
    String idnotif = "";
    String ModuleTransactionNo = "";
    Boolean internet = false;
    String MhaveToUpdate = "";
    String MsessionExpired = "";
    String sesionid_new = "";
    public static String modultrans="null";
    RecyclerView recyclerView;
    Itemchat2 itemchat2 ;
    ArrayList<Itemchat> itemchat3;
    Adapterchat adapterchat;
    public static DatabaseReference databaseReference,databaseReference3,databaseReference4;
    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();
    LinearLayoutManager linearLayoutManager;
    LinearLayout mlayketk;
    public static String name="";
    String sendto="";
    EditText sendtext;
    ImageView mpaperclip;
    public static ImageView msend,mdelet,mcopy;
    String show="no";
    String showid="-";
    private static final int PICK_PDF_REQUEST = 1000;
    private String pdfPath="";
    public static String keynya1 = "";
    String url = "-";
    String showurl ="-";
    String message = "";
    String myuri = "-";
    String youuri = "-";
    StorageReference ref = FirebaseStorage.getInstance().getReference();
    public static File imagefile = null;
    public static LinearLayout mdelcop,mback, mcopylay;
    ProgressDialog loading;
    public static String user = "admin";
    String idhcat="admin1";
    public static String levelStatus="admin";
    public static String sessionnya = "";

    final int REQUEST_IMAGE_GALLERY = 2;
    Uri photo_location;
    public static boolean chatin = false;
    String noreq = "";
    String username = "";
    String idnya = "";
    TextView mfrnya,mstnya;
    String enginername = "";
    String fr="";
    String tokennya = "";
    int xhori = 0;
    int yverti = 0;
    String scrollnya = "-";
    private APIService apiService;
    int PERMISSION_CODE = 100;

    ProgressBar mloadingchat;
    String mimeType="";
    String mimeType2="-";
    int tokenpos=0;
    LinearLayout mmnodatas;
    boolean kirim = true;
    List<String> tokenlist;
    JsonArray myCustomArray;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chat);
        mlaypreparenote = findViewById(R.id.backgroundalert);
        mmnodatas = findViewById(R.id.nodatas);
        mmessageprepare = findViewById(R.id.textalert);
        mloadingchat = findViewById(R.id.loadingchat);
        mfrnya = findViewById(R.id.frnya);
        mstnya = findViewById(R.id.stnya);
        mpaperclip = findViewById(R.id.paperclip);
        recyclerView=findViewById(R.id.lischat);
        sendtext = findViewById(R.id.textsend);
        msend = findViewById(R.id.send);
        mback = findViewById(R.id.backbtn);
        mdelcop = findViewById(R.id.delcop);
        mcopylay = findViewById(R.id.cop);
        mdelet = findViewById(R.id.delete);
        mcopy = findViewById(R.id.copy);
        mlayketk = findViewById(R.id.layketk);
        getSessionId();
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            module=bundle2.getString("module");
            modultrans=bundle2.getString("moduletrans");
            ping=bundle2.getInt("ping");
            sessionnya = bundle2.getString("sessionnya");
            name = bundle2.getString("name");
            noreq = bundle2.getString("id");
            id = bundle2.getString("page");
            username = bundle2.getString("user");
            mustupload = bundle2.getString("pdfyes");
            chatin = bundle2.getBoolean("chat");
            liveChatRepor = bundle2.getBoolean("liveChatRepor");
            custnmae = bundle2.getString("engname");
            tokennya = bundle2.getString("tokennya");
            nofr = bundle2.getString("nofr");
            xhori=bundle2.getInt("xhori");
            yverti=bundle2.getInt("yverti");
            titlenya=bundle2.getString("titlenya");
            scrollnya =   bundle2.getString("scrolbawah");
//            Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
//            mfrnya.setText(noreq+" (Customer: "+custnmae+")");
            mfrnya.setText(titlenya);
            if (id==null){
                String currentString = noreq;
                String[] separated = currentString.split("/");
                modultrans = separated[1];
                module = separated[2];
                idnotif = separated[0];
                //edit
//                String token = separated[3].replace("[","").replace("]","");
//                tokenlist = new ArrayList<String>(Arrays.asList(token.split(",")));
//                Gson gson = new GsonBuilder().create();
//                myCustomArray = gson.toJsonTree(tokenlist).getAsJsonArray();
//                String jsonarayitem = myCustomArray.toString();
//                Log.d("tokesa",jsonarayitem);
//                Log.d("dzsa","adaid");
                //pagechat
//                pagechat = separated[3];
//                Log.d("pagechatnya",pagechat.toString());

            }else {

            }
        }
        mloadingchat.setVisibility(View.VISIBLE);
        Log.d("notifnyaas", sessionnya+"-"+modultrans+"-"+module+"/"+sessionnya);
        getShowid();
        reqApi();
        if (id==null){
//            Log.d("pagechatnya",pagechat.toString());
            Log.d("dsa3","nulls");
            if (module.equals("ChatWithSupport")){
                reqnotif2();
                id="ChatWithSupport";

//                scs="yes";
//                titlelist="Support Live Chat List";
            }else {
                reqnotif();

//                scs="no";
//                titlelist="List Live Chat";
            }


        }else {
//            Log.d("pagechatnya",pagechat.toString());
            Gson gson = new GsonBuilder().create();
            myCustomArray = gson.toJsonTree(tokennya2).getAsJsonArray();
            tokenlist = new ArrayList<>();
            tokenlist.addAll(tokennya2);
            Log.d("dsa1",tokenlist.toString());
            databaseReference= FirebaseDatabase.getInstance().getReference().child("chat").child(sessionnya).child("listchat");
            databaseReference3= FirebaseDatabase.getInstance().getReference().child("chat").child(sessionnya).child("listchat");
            loadchat();
        }
        if (chatin){
            mlayketk.setVisibility(View.VISIBLE);
            checkpreparechat();
        }else {
            mlayketk.setVisibility(GONE);
        }
        if (modultrans.equals("kosong")){
            mstnya.setVisibility(GONE);
        }else {
            mstnya.setText(Html.fromHtml("<u>"+getString(R.string.title_chatdetailst)+"</u>"));
            mstnya.setVisibility(View.VISIBLE);
        }
        linearLayoutManager = new LinearLayoutManager(ListChat.this, LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setHasFixedSize(true);
        itemchat3 = new ArrayList<Itemchat>();
        itemchat2= new Itemchat2();
//        databaseReference= FirebaseDatabase.getInstance().getReference().child("chat").child(sessionnya).child("listchat");
//        databaseReference3= FirebaseDatabase.getInstance().getReference().child("chat").child(sessionnya).child("listchat");


//        loadchat();

        msend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message=sendtext.getText().toString();
                showurl="-";
                myuri = "-";
//                ping();
//                if (liveChatRepor){
//                    if (ping==1){
//                        ping();
//                    }else {
//                        if (ping==2){
//                            pingfoc();
//                        }else {
//                            if (ping==3){
//                                pingcha();
//                            }else {
//                                if (ping==4){
//                                    pingcs();
//                                }
//                            }
//                        }
//                    }
//
//                }else {
//
//                }
                if (sendtext.length()==0){

                }else {
                    mback.setVisibility(View.VISIBLE);
                    mdelcop.setVisibility(View.GONE);
                    String date = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH).format(new Date());
                    getShowid();
                    if (showid.equals(date)){
                        show="no";
                    }else {
                        show="yes";
                    }
                    SharedPreferences sharedPreferences = getSharedPreferences("SHOW_ID", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("show_id", date);
                    editor.apply();
                    sendDb();
                    loadchat();
                    sendtext.setText("");
                }

            }
        });
        mpaperclip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ContextCompat.checkSelfPermission(ListChat.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

                    ActivityCompat.requestPermissions(ListChat.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
                    return;
                }else {
                    gallery();
                }

//                launchPicker();
            }
        });
        mstnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.checknotif=1;
                if (module.equals("ServiceSupport")){
                    Intent gotonews = new Intent(ListChat.this, DetailsFormActivity.class);
                    gotonews.putExtra("name",username);
                    gotonews.putExtra("sessionnya",sessionnya);
                    gotonews.putExtra("chat",chatin);
                    gotonews.putExtra("user",name);
                    gotonews.putExtra("id",modultrans);
//                gotonews.putExtra("tokennya",token);
                    gotonews.putExtra("engname", mcustname);
                    gotonews.putExtra("nofr", mformRequestCd);
                    gotonews.putExtra("xhori", xhori);
                    gotonews.putExtra("yverti", yverti);
                    gotonews.putExtra("scrolbawah","-");
                    startActivity(gotonews);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    finish();
                }else {
                    if (module.equals("FOC")){
                        Intent gotonews = new Intent(ListChat.this, AddDetailFocView.class);
                        gotonews.putExtra("name",username);
                        gotonews.putExtra("sessionnya",sessionnya);
                        gotonews.putExtra("chat",chatin);
                        gotonews.putExtra("user",name);
                        gotonews.putExtra("id",modultrans);
                        gotonews.putExtra("username",username);
//                gotonews.putExtra("tokennya",token);
                        gotonews.putExtra("engname", mcustname);
                        gotonews.putExtra("nofr", mformRequestCd);
                        gotonews.putExtra("xhori", xhori);
                        gotonews.putExtra("yverti", yverti);
                        gotonews.putExtra("scrolbawah","-");
                        startActivity(gotonews);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        finish();
                    }else {
                        if (module.equals("Chargeable")){
                            Intent gotonews = new Intent(ListChat.this, AddDetailsPoView.class);
                            gotonews.putExtra("name",username);
                            gotonews.putExtra("sessionnya",sessionnya);
                            gotonews.putExtra("chat",chatin);
                            gotonews.putExtra("username",name);
                            gotonews.putExtra("id",modultrans);
//                gotonews.putExtra("tokennya",token);
                            gotonews.putExtra("engname", mcustname);
                            gotonews.putExtra("nofr", mformRequestCd);
                            gotonews.putExtra("xhori", xhori);
                            gotonews.putExtra("yverti", yverti);
                            gotonews.putExtra("scrolbawah","-");
                            gotonews.putExtra("pdfyes", "no");
                            startActivity(gotonews);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            finish();
                        }
                    }
                }

            }
        });
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 100 && grantResults.length>0 && (grantResults[0]
                == PackageManager.PERMISSION_GRANTED)){


        }else {
            Toast.makeText(this, "Storage Wajib", Toast.LENGTH_LONG).show();

        }
    }
    public void  checkpreparechat(){
        loading = ProgressDialog.show(ListChat.this, "", "", true);
//        loading .setVisibility(View.VISIBLE);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.preparechat(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("configeng",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
//                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")) {
                    loading.dismiss();
//                    loading .setVisibility(View.GONE);
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    if (data.get("showMessage").getAsBoolean()){
//                        data.get("showMessage").getAsBoolean()
                        String text = data.get("messageText").getAsString();
                        String textcolor = data.get("messageTextColor").getAsString();
                        String bgcolor = data.get("messageBackgroundColor").getAsString();
//                        String text = "test text test texttest texttest texttest texttest texttest texttest text";
//                        String bgcolor = "ed6a24";
//                        String textcolor = "ffffff";
                        GradientDrawable shape =  new GradientDrawable();
                        shape.setCornerRadius( 15 );
                        shape.setColor(Color.parseColor("#"+bgcolor));

                        mlaypreparenote.setVisibility(View.VISIBLE);
                        mmessageprepare.setTextColor(Color.parseColor("#"+textcolor));
                        mlaypreparenote.setBackground(shape);
                        if (Build.VERSION.SDK_INT >= 24) {
                            mmessageprepare.setText((CharSequence) Html.fromHtml((String)text, Html.FROM_HTML_MODE_COMPACT));
                            mmessageprepare.setMovementMethod(LinkMovementMethod.getInstance());
                        } else {
                            mmessageprepare.setText((CharSequence)Html.fromHtml((String)text));
                            mmessageprepare.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                    }else {
                        mlaypreparenote.setVisibility(GONE);
                    }
                }else {
                    sesionid();
                    loading.dismiss();
                    //// error message
//                    loading .setVisibility(View.GONE);
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ListChat.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ListChat.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading .setVisibility(View.GONE);
                loading.dismiss();

            }
        });
        Log.d("reqpreparechat",jsonObject.toString());
    }
    public void sendDb(){
//        int posinya = 0;
//        if ((addFoclistreq!=null)){
//            posinya = 0;
//        }else{
//            posinya = addFoclistreq.size()+1;
//        }
        String date = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH).format(new Date());
        String time = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date());
        itemchat2.setDate(date);
        itemchat2.setName(name);
        itemchat2.setShowDate(show);
        itemchat2.setTime(time);
        itemchat2.setRead("no");
        itemchat2.setLevel("Customer");
        itemchat2.setThumb("-");
        itemchat2.setType(mimeType2);
        itemchat2.setUrl(url);
        itemchat2.setMyuri(myuri);
        itemchat2.setYoururi(youuri);
//        itemchat2.setPosition(posinya);
        itemchat2.setShowUrl(showurl);
        itemchat2.setMessage(message);
        databaseReference2.child("chat").child(sessionnya).child("listchat").push().setValue(itemchat2).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (liveChatRepor){
                    if (ping==1){
                        ping();
                    }else {
                        if (ping==2){
                            pingfoc();
                        }else {
                            if (ping==3){
                                pingcha();
                            }else {
                                if (ping==4){
                                    pingcs();
                                }
                            }
                        }
                    }

                }else {

                }
                sendnotifchat();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        mimeType2 = "-";
    }
    public void ping() {
        loading = ProgressDialog.show(ListChat.this, "", "", true);
//        loading .setVisibility(View.VISIBLE);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("serviceTicketCd",sessionnya);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.ping(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("configeng",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
//                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")) {
                    loading.dismiss();
//                    loading .setVisibility(View.GONE);
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    Log.d("rekping","success");
                }else {
                    sesionid();
                    loading.dismiss();
                    //// error message
//                    loading .setVisibility(View.GONE);
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ListChat.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ListChat.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading .setVisibility(View.GONE);
                loading.dismiss();

            }
        });
        Log.d("reqnotifnya",jsonObject.toString());
    }
    public void pingfoc() {
        loading = ProgressDialog.show(ListChat.this, "", "", true);
//        loading .setVisibility(View.VISIBLE);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("orderNo",sessionnya);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.ping2(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("configeng",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
//                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")) {
                    loading.dismiss();
//                    loading .setVisibility(View.GONE);
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    Log.d("rekping","successfoc");
                }else {
                    sesionid();
                    loading.dismiss();
                    //// error message
//                    loading .setVisibility(View.GONE);
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ListChat.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ListChat.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading .setVisibility(View.GONE);
                loading.dismiss();

            }
        });
        Log.d("reqnotifnya",jsonObject.toString());
    }
    public void pingcha() {
        loading = ProgressDialog.show(ListChat.this, "", "", true);
//        loading .setVisibility(View.VISIBLE);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("orderNo",sessionnya);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.ping3(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("configeng",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
//                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")) {
                    loading.dismiss();
//                    loading .setVisibility(View.GONE);
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    Log.d("rekping","successcharge");
                }else {
                    sesionid();
                    loading.dismiss();
                    //// error message
//                    loading .setVisibility(View.GONE);
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ListChat.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ListChat.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading .setVisibility(View.GONE);
                loading.dismiss();

            }
        });
        Log.d("reqnotifnya",jsonObject.toString());
    }
    public void pingcs() {
        loading = ProgressDialog.show(ListChat.this, "", "", true);
//        loading .setVisibility(View.VISIBLE);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
//        jsonObject.addProperty("orderNo",sessionnya);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.ping4(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("configeng",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
//                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")) {
                    loading.dismiss();
//                    loading .setVisibility(View.GONE);
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    Log.d("rekping","successSupport");
                }else {
                    sesionid();
                    loading.dismiss();
                    //// error message
//                    loading .setVisibility(View.GONE);
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ListChat.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ListChat.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading .setVisibility(View.GONE);
                loading.dismiss();

            }
        });
        Log.d("reqnotifnya",jsonObject.toString());
    }
    public void loadchat(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemchat3.clear();
                if (dataSnapshot.exists()){
                    for(DataSnapshot ds: dataSnapshot.getChildren())
                    {
                        Itemchat fetchDatalist=ds.getValue(Itemchat.class);
                        fetchDatalist.setKey(ds.getKey());
                        itemchat3.add(fetchDatalist);
                    }

                    adapterchat=new Adapterchat(ListChat.this, itemchat3);
                    recyclerView.setAdapter(adapterchat);
                    recyclerView.scrollToPosition(adapterchat.getItemCount()-1);
//                recyclerView.scrollToPosition(adapterchat.getItemCount());
                    mloadingchat.setVisibility(GONE);
                    mmnodatas.setVisibility(GONE);
                    readchat();
                }else {
                    mmnodatas.setVisibility(View.VISIBLE);
                    mloadingchat.setVisibility(GONE);
                }

//                Log.d("posi",String.valueOf(recyclerView.getAdapter().getItemCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mloadingchat.setVisibility(GONE);
                Toast.makeText(ListChat.this, databaseError.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void getShowid(){

        SharedPreferences sharedPreferences = getSharedPreferences("SHOW_ID",MODE_PRIVATE);
        showid = sharedPreferences.getString("show_id", "");

    }
    private void dialogfile() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("kirim:"+imagefile.getName())
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        int position = 1;

                        if (itemchat3.toString().equals("[]")){
                            Log.e("listkosong",itemchat3.toString());


                        }else {
//                            Log.e("listkosong",itemchat.toString());
                            position = addFoclistreq.size()+1;


                        }
//                        loading = ProgressDialog.show(ListChat.this, "", "Uploading...", true);
                        // jika tombol diklik, maka akan menutup activity ini
                        showurl="yes";
                        message = imagefile.getName();
                        myuri = pdfPath;
                        String date = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH).format(new Date());
                        String time = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date());
                        itemchat2.setDate(date);
                        itemchat2.setName(name);
                        itemchat2.setShowDate(show);
                        itemchat2.setTime(time);
                        itemchat2.setRead("no");
                        itemchat2.setSendto(sendto);
                        itemchat2.setUrl(url);
                        itemchat2.setLevel("Customer");
                        itemchat2.setThumb("-");
                        itemchat2.setType(mimeType2);
                        itemchat2.setMyuri(myuri);
                        itemchat2.setYoururi(youuri);
                        itemchat2.setPosition(position);
                        itemchat2.setShowUrl(showurl);
                        itemchat2.setMessage(message);
                        databaseReference2.child("chat").child(sessionnya).child("listchat").push().setValue(itemchat2).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if (liveChatRepor){
                                    if (ping==1){
                                        ping();
                                    }else {
                                        if (ping==2){
                                            pingfoc();
                                        }else {
                                            if (ping==3){
                                                pingcha();
                                            }else {
                                                if (ping==4){
                                                    pingcs();
                                                }
                                            }
                                        }
                                    }

                                }else {

                                }
                                sendnotifchat();
                                int posi = addFoclistreq.size()-1;
                                InputStream stream = null;
                                try {
                                    stream = new FileInputStream(new File(imagefile.toString()));
                                    Log.d("strm",stream.toString());
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }


//                        UploadTask uploadTask = ref.child("images").putStream(stream);

//                                ref.child("chat"+"/"+sessionnya+"/"+"listchat"+"/"+addFoclistreq.get(posi).getKey()+"/"+message).putFile(Uri.fromFile(imagefile)).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e)
//                                    {
//                                        String message = e.toString();
//                                        loading.dismiss();
//                                        Log.d("erorupload",message);
////                                loadingBar.dismiss();
//                                    }
//                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                                    @Override
//                                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                                        double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
//                                        prog = (int) progress;
//                                        Log.d("progress", String.valueOf(prog));
//
//                                    }
//                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                    @Override
//                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
//                                    {
//                                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
//                                        while (!urlTask.isSuccessful());
//                                        Uri downloadUrl = urlTask.getResult();
//                                        Log.d("getdownload",downloadUrl.toString());
//                                        HashMap hashMap = new HashMap();
//                                        hashMap.put("url",downloadUrl.toString());
//                                        hashMap.put("showUrl","done");
//                                        databaseReference3.child(addFoclistreq.get(posi).getKey()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                loading.dismiss();
//                                                Toast.makeText(ListChat.this, "uploaded Successfully...", Toast.LENGTH_SHORT).show();
//
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                loading.dismiss();
//                                            }
//                                        });
//                                    }
//                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
//                            loading.dismiss();
                            }
                        });
                        mimeType2 = "-";
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,  resultCode,  data);
//        Log.d("photolocation",photo_location.toString());
        if (requestCode==REQUEST_IMAGE_GALLERY) {
            if (data!=null){
                photo_location = data.getData();

            }

            if (photo_location!=null){
//                    Toast.makeText(AddRequest.this, photo_location.toString(),Toast.LENGTH_LONG).show();
                if (data!=null){
                    photo_location = data.getData();
                    String yourRealPath ="";
                    try {
                        File file;

                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(photo_location, filePathColumn, null, null, null);
                        if(cursor.moveToFirst()){
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            yourRealPath = cursor.getString(columnIndex);
                            pdfPath = yourRealPath;
                            imagefile =new File(yourRealPath);
                            String fileExtension= MimeTypeMap.getFileExtensionFromUrl(imagefile.toString());
                            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
                            String[] separated = mimeType.split("/");
                            separated[0].trim();; // this will contain "Fruit"
                            separated[1].trim();;
                            mimeType2=separated[0];
                            dialogfile();
                        } else {
                            //boooo, cursor doesn't have rows ...
                        }
                        Log.d("pathgalery",yourRealPath);

                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                        Toast.makeText(ListChat.this, exception.toString(),Toast.LENGTH_LONG).show();
                    }

                }

            }

        }else {

        }

    }

    public void gallery( ) {
        if (Build.VERSION.SDK_INT < 25) {
            //coba settingan baru
//            Intent mediaChooser = new Intent(Intent.ACTION_GET_CONTENT);
//            mediaChooser.setType("*/*");
//            startActivityForResult(mediaChooser, REQUEST_IMAGE_GALLERY);
//            photo_location=mediaChooser.getData();
//            Log.d("versionnya","dibawah android 7.0");
//

            Intent imageIntentGallery = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imageIntentGallery.setType("*/*");
        startActivityForResult(imageIntentGallery, REQUEST_IMAGE_GALLERY);
            String[] mimetypes = {"image/*", "video/*"};
            imageIntentGallery.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
            photo_location=imageIntentGallery.getData();
            ///close

//            Intent mediaChooser = new Intent(Intent.ACTION_PICK);
////comma-separated MIME types
//            mediaChooser.setType("video/*, image/*");
//            startActivityForResult(mediaChooser, REQUEST_IMAGE_GALLERY);
////        Intent imageIntentGallery = new Intent(Intent.ACTION_PICK,
////                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////        startActivityForResult(imageIntentGallery, REQUEST_IMAGE_GALLERY);
////            String[] mimetypes = {"image/*", "video/*"};
////            mediaChooser.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
//            photo_location=mediaChooser.getData();
            Log.d("versionnya","dibawah android 7.0");
        } else {
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setType("*/*");

            String[] mimetypes = {"image/*", "video/*"};
            i.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
            startActivityForResult(i, REQUEST_IMAGE_GALLERY);
            Log.d("urinya",String.valueOf(i.getData()));
            photo_location=i.getData();
            Log.d("versionnya","diatas android 7.0");
        }



    }

    public void sendNotifications(String usertoken, String title, String message) {
//        Data data = new Data(title, message);
//        NotificationSender sender = new NotificationSender(data, usertoken);
//        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
//        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
//            @Override
//            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
//                if (response.code() == 200) {
//                    if (response.body().success != 1) {
//                      Log.d("sendnotif2","gagal"+tokennya+" + "+name+" + "+message);
//                    }else {
//                        Log.d("sendnotif2","bisa"+tokennya+" + "+name+" + "+message);
//
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MyResponse> call, Throwable t) {
//                Log.d("sendnotif3","bisa"+tokennya+" + "+name+" + "+message);
//            }
//        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        itemchat.clear();
        Log.d("idnya",id);
        if (id.equals("listchat")){
            Intent back = new Intent(ListChat.this,LiveChatList.class);
            startActivity(back);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
        }else{
            if (ping==1){
                Intent back = new Intent(ListChat.this,DetailsFormActivity.class);
                back.putExtra("name",name);
                back.putExtra("id",noreq);
                back.putExtra("user",username);
                back.putExtra("home", "homesa");
                back.putExtra("xhori", xhori);
                back.putExtra("yverti", yverti);
                back.putExtra("scrolbawah","-");
                startActivity(back);
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                finish();
            }else {
                if (ping==2){
//                    check.checknotif=1;
                    Intent back = new Intent(ListChat.this,AddDetailFocView.class);
                    back.putExtra("name",name);
                    back.putExtra("id",sessionnya);
                    back.putExtra("username",username);
                    back.putExtra("home", "homesa");
                    back.putExtra("xhori", xhori);
                    back.putExtra("yverti", yverti);
                    back.putExtra("scrolbawah","-");
                    startActivity(back);
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    finish();
                }else {
                    if (ping==3){
                        Intent back = new Intent(ListChat.this,AddDetailsPoView.class);
                        back.putExtra("name",name);
                        back.putExtra("id",sessionnya);
                        back.putExtra("username",username);
                        back.putExtra("home", "homesa");
                        back.putExtra("xhori", xhori);
                        back.putExtra("yverti", yverti);
                        back.putExtra("pdfyes", "no");
                        back.putExtra("scrolbawah","-");
                        startActivity(back);
                        overridePendingTransition(R.anim.left_in, R.anim.right_out);
                        finish();
                    }else {
                        if (ping==4){
                            Intent back = new Intent(ListChat.this,Dashboard.class);
                            back.putExtra("name",name);
                            back.putExtra("id",noreq);
                            back.putExtra("username",username);
                            back.putExtra("home", "homesa");
                            back.putExtra("xhori", xhori);
                            back.putExtra("yverti", yverti);
                            back.putExtra("pdfyes", "no");
                            back.putExtra("scrolbawah","-");
                            startActivity(back);
                            overridePendingTransition(R.anim.left_in, R.anim.right_out);
                            finish();
                        }else {
                            if (ping==77){
                                Intent back = new Intent(ListChat.this,DetailsPM.class);
                                back.putExtra("name",name);
                                back.putExtra("id",noreq);
                                back.putExtra("user",username);
                                back.putExtra("home", "homesa");
                                back.putExtra("xhori", xhori);
                                back.putExtra("yverti", yverti);
                                back.putExtra("pdfyes", "no");
                                back.putExtra("scrolbawah","-");
                                startActivity(back);
                                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                                finish();
                            }
                        }
                    }
                }
            }

        }
//        Intent back = new Intent(ListChat.this,DetailsFormActivity.class);
//        back.putExtra("name",name);
//        back.putExtra("id",noreq);
//        back.putExtra("user",username);
//        back.putExtra("xhori", xhori);
//        back.putExtra("yverti", yverti);
//        back.putExtra("scrolbawah","sadadasd");
//        startActivity(back);
//        finish();
    }
    public void sendnotifchat(){
//        loading = ProgressDialog.show(DetailsFormActivity.this, "", getString(R.string.title_loading), true);
        //tambahtokennya2 ke dataid

            JsonObject dataid = new JsonObject();
            dataid.addProperty("id", sessionnya+"/"+modultrans+"/"+module+"/"+pagechat);

            JsonObject notifikasidata = new JsonObject();
            notifikasidata.addProperty("title", noreq + "-" + name);
            notifikasidata.addProperty("body", message);
            notifikasidata.addProperty("click_action", "notifchat");


//            Gson gson = new GsonBuilder().create();
//            JsonArray myCustomArray = gson.toJsonTree(tokennya2).getAsJsonArray();
////             jsonarayitem = myCustomArray.toString();
//            Log.d("tokes",tokens);
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("registration_ids", myCustomArray);
//        jsonObject.addProperty("to", tokennya2.get(tokenpos));
            jsonObject.add("data", dataid);
            jsonObject.add("notification", notifikasidata);
//        Toast.makeText(DetailsFormActivity.this,jsonObject.toString(), Toast.LENGTH_SHORT).show();
            APIService jsonPostService = ServiceGenerator.createService(APIService.class, fcmbase);
            Call<JsonObject> panggilkomplek = jsonPostService.sendNotifcation(jsonObject);
            panggilkomplek.enqueue(new Callback<JsonObject>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.d("tokenplusisze", String.valueOf(tokennya2.size()-1)+"=="+String.valueOf(tokenpos));
                    Log.d("tokenlist",tokennya2.get(tokenpos).toString());
//                    if (tokennya2.size()==tokenpos+1){
//                        tokenpos =0;
//                    }else {
//                        tokenpos +=1;
//                        sendnotifchat();
//                    }
                    String errornya = "";
                    JsonObject homedata = response.body();
                    int statusnya = homedata.get("success").getAsInt();
                    if (statusnya == 1) {
//                    JsonObject data = homedata.getAsJsonObject("data");
                        Log.d("responnotif2", homedata.toString());
                    } else {
//                    sesionid();
//                        loading.dismiss();
//                    Toast.makeText(DetailsFormActivity.this, errornya,Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(DetailsFormActivity.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
//                cekInternet();
//                    loading.dismiss();

                }
            });
            Log.d("requestnotif", jsonObject.toString());

    }
    public void reqnotif() {
        loading = ProgressDialog.show(ListChat.this, "", "", true);
//        loading .setVisibility(View.VISIBLE);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("liveChatID",idnotif);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.getlivechat(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("configeng",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
//                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")) {
                    loading.dismiss();
//                    loading .setVisibility(View.GONE);
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    JsonObject data2 = data.getAsJsonObject("details");
                    if (data2.get("OthersFirebaseToken").toString().equals("null")){
                        tokennya = "-";
                    }else {
                        tokennya2.clear();
                        JsonArray tokeny = data2.getAsJsonArray("OthersFirebaseToken");
                        for (int c = 0; c < tokeny.size(); ++c) {
                            JsonObject assobj2 = tokeny.get(c).getAsJsonObject();
                            tokennya2.add(assobj2.get("Token").getAsString());
                        }

                        Log.d("listTokennotif", tokennya2.toString());
                        Gson gson = new GsonBuilder().create();
                        myCustomArray = gson.toJsonTree(tokennya2).getAsJsonArray();
                        tokenlist = new ArrayList<>();
                        tokenlist.addAll(tokennya2);
                    }
                    id="listchat";
                    titlenya = data2.get("Title").getAsString();
                    name = data2.get("UserName").getAsString();
                    module = data2.get("Module").getAsString();
                    String iduser = data2.get("UserID").getAsString();
                    ModuleTransactionNo = data2.get("ModuleTransactionNo").getAsString();
                    chatin = data2.get("AllowToChat").getAsBoolean();
                    sessionnya = data2.get("LiveChatID").getAsString();
                    databaseReference= FirebaseDatabase.getInstance().getReference().child("chat").child(sessionnya).child("listchat");
                    databaseReference3= FirebaseDatabase.getInstance().getReference().child("chat").child(sessionnya).child("listchat");
                    modultrans = ModuleTransactionNo;
                    noreq=titlenya;
                    Log.d("moduletrasn",name+"-"+noreq+"/"+iduser);
                    if (modultrans.equals("")){
                        mstnya.setVisibility(GONE);
                    }else {
                        mstnya.setText(Html.fromHtml("<u>"+getString(R.string.title_chatdetailst)+"</u>"));
                        mstnya.setVisibility(View.VISIBLE);
                    }
                    if (chatin){
                        mlayketk.setVisibility(View.VISIBLE);
                    }else {
                        mlayketk.setVisibility(GONE);
                    }
                    mfrnya.setText(titlenya);
                    loadchat();
                }else {
                    sesionid();
                    loading.dismiss();
                    //// error message
//                    loading .setVisibility(View.GONE);
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ListChat.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ListChat.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading .setVisibility(View.GONE);
                loading.dismiss();

            }
        });
        Log.d("reqnotifnya",jsonObject.toString());
    }
    public void reqnotif2() {
        loading = ProgressDialog.show(ListChat.this, "", "", true);
//        loading .setVisibility(View.VISIBLE);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
//        jsonObject.addProperty("liveChatID",idnotif);
        jsonObject.addProperty("ver",BuildConfig.VERSION_NAME);
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, baseurl);
        Call<JsonObject> panggilkomplek = jsonPostService.getlivechatcs(jsonObject);
        panggilkomplek.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String errornya = "";
                JsonObject homedata=response.body();
                String statusnya = homedata.get("status").getAsString();
                Log.d("configeng",homedata.toString());
                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
//                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")) {
                    loading.dismiss();
//                    loading .setVisibility(View.GONE);
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");
                    JsonObject data2 = data.getAsJsonObject("details");
                    if (data2.get("OthersFirebaseToken").toString().equals("null")){
                        tokennya = "-";
                    }else {
                        tokennya2.clear();
                        JsonArray tokeny = data2.getAsJsonArray("OthersFirebaseToken");
                        for (int c = 0; c < tokeny.size(); ++c) {
                            JsonObject assobj2 = tokeny.get(c).getAsJsonObject();
                            tokennya2.add(assobj2.get("Token").getAsString());
                        }

                        Log.d("listTokennotif", tokennya2.toString());
                        Gson gson = new GsonBuilder().create();
                        myCustomArray = gson.toJsonTree(tokennya2).getAsJsonArray();
                        tokenlist = new ArrayList<>();
                        tokenlist.addAll(tokennya2);
                    }
                    id="cs";
                    titlenya = data2.get("Title").getAsString();
                    name = data2.get("UserName").getAsString();
                    String iduser = data2.get("UserID").getAsString();
                    module = data2.get("Module").getAsString();
                    ModuleTransactionNo = data2.get("ModuleTransactionNo").getAsString();
                    chatin = data2.get("AllowToChat").getAsBoolean();
                    sessionnya = data2.get("LiveChatID").getAsString();
                    databaseReference= FirebaseDatabase.getInstance().getReference().child("chat").child(sessionnya).child("listchat");
                    databaseReference3= FirebaseDatabase.getInstance().getReference().child("chat").child(sessionnya).child("listchat");
                    modultrans = ModuleTransactionNo;
                    noreq=titlenya;
                    Log.d("moduletrasn",name+"-"+noreq+"/"+iduser);
                    if (modultrans.equals("")){
                        mstnya.setVisibility(GONE);
                    }else {
                        if (module.equals("ServiceSupport")){
//                            mstnya.setText(Html.fromHtml("<u>"+getString(R.string.title_detailchatst)+"</u>"));
                            mstnya.setVisibility(View.VISIBLE);
                            Log.d("moduletrasn",modultrans);
                        }else {
                            mstnya.setVisibility(GONE);
                        }

                    }
                    if (chatin){
                        mlayketk.setVisibility(View.VISIBLE);
                    }else {
                        mlayketk.setVisibility(GONE);
                    }
                    mfrnya.setText(titlenya);
                    loadchat();
                }else {
                    sesionid();
                    loading.dismiss();
                    //// error message
//                    loading .setVisibility(View.GONE);
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ListChat.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ListChat.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading .setVisibility(View.GONE);
                loading.dismiss();

            }
        });
        Log.d("reqnotifnya",jsonObject.toString());
    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
//                Intent gotoupdate = new Intent(Home.this, UpdateActivity.class);
//                startActivity(gotoupdate);
//                finish();
            }

        }else {
            startActivity(new Intent(ListChat.this, LoginActivity.class));
            finish();
//            Toast.makeText(Home.this, getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    public void getSessionId(){

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_ID",MODE_PRIVATE);
        sesionid_new = sharedPreferences.getString("session_id", "");
        Log.d("session",sesionid_new);

    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager)ListChat.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            internet = true;


        }else {
            internet=false;
            Intent noconnection = new Intent(ListChat.this,NoInternet.class);
            startActivity(noconnection);
            finish();
        }
        //// pengecekan internet selesai

    }
    public void reqApi() {
//        loading = ProgressDialog.show(this, "", "", true);

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
                Log.d("reqapi1",homedata.toString());

                if (homedata.get("errorMessage").toString().equals("null")) {

                }else {
                    errornya = homedata.get("errorMessage").getAsString();
                }
                MhaveToUpdate = homedata.get("haveToUpdate").toString();
                MsessionExpired = homedata.get("sessionExpired").toString();
//                jsonObject.addProperty("ver",ver);
                if (statusnya.equals("OK")) {

//                    loading.dismiss();
                    sesionid();
                    JsonObject data = homedata.getAsJsonObject("data");

//                    boolean clocksts = data.get("alreadyClockIn").getAsBoolean();

//                    if (clocksts){
//                        ;
//                    }else {
//                        startActivity(new Intent(ListChat.this, ClockInActivity.class));
//                        finish();
////                        mcheck.setVisibility(View.VISIBLE)
//
//                    }
                }else {
//                    mrefresh.setVisibility(View.VISIBLE);
//                    mcheck.setVisibility(View.GONE);
                    sesionid();
                    //// error message
//                    loading.dismiss();
//                    if (MsessionExpired.equals("true")) {
//                        Toast.makeText(Home.this, errornya.toString(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(ListChat.this, errornya.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
//                mrefresh.setVisibility(View.VISIBLE);
//                mcheck.setVisibility(View.GONE);
                Toast.makeText(ListChat.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
//                loading.dismiss();
            }
        });
        Log.d("reqapi",jsonObject.toString());

    }


    public void reglogauth(){
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(emainya, "x8x8x8")
                .addOnCompleteListener(ListChat.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("trag", "signInWithCustomToken:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            uidnya=user.getUid();
                            setregistuser();

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                mAuth.signInWithEmailAndPassword(emainya, "x8x8x8")
                                        .addOnCompleteListener(ListChat.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    // Sign in success, update UI with the signed-in user's information

                                                    FirebaseUser user = mAuth.getCurrentUser();
                                                    uidnya=user.getUid();
                                                    Log.d("trag", uidnya);
                                                    setregistuser();
//
                                                } else {
                                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
//                                                                            Toast.makeText(DetailsST.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();
                                                    }else {

                                                    }
                                                    // If sign in fails, display a message to the user.
                                                    Log.w("uii", "signInWithCustomToken:failure", task.getException());
//                                    Toast.makeText(CustomAuthActivity.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);

                                                }
                                            }
                                        });
//                                                    Toast.makeText(DetailsST.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();
//                                            loading.dismiss();
                            }else {
//                                            loading.dismiss();
                            }
//                                        loading.dismiss();
                            // If sign in fails, display a message to the user.
                            Log.w("uii", "signInWithCustomToken:failure", task.getException());
//                                    Toast.makeText(CustomAuthActivity.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);

                        }
                    }
                }).addOnFailureListener(ListChat.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                            loading.dismiss();
                Log.d("gagal login",e.toString());
            }
        });
    }
    public void setregistuser(){
//        int posinya = 0;
//        if ((addFoclistreq!=null)){
//            posinya = 0;
//        }else{
//            posinya = addFoclistreq.size()+1;
//        }
        ietmuid= new ItemUid();

        ietmuid.setEmail(emainya);
        ietmuid.setUsername(nme);

        databaseReference2.child("akunregist").child(uidnya).setValue(ietmuid).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Log.d("failue","succes");


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("failue",e.toString());
            }
        });
    }

    public void readchat (){
        String keyread = "";
        for (int v = 0; v < addFoclistreq.size(); ++v) {
            if (name.equals(addFoclistreq.get(v).getName())){

            }else {
                if (addFoclistreq.get(v).getRead().equals("yes")){

                }else {
                    keyread = addFoclistreq.get(v).getKey();
//                    kirim=false;
                }
            }
        }
            if (kirim){
                kirim=false;
                Log.d("testresdad",keyread);
                if (keyread.equals("")){

                }else {
                    HashMap hashMap = new HashMap();
                    hashMap.put("read","yes");
                    databaseReference2.child("chat").child(sessionnya).child("listchat").child(keyread).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            kirim=true;
                            readchat();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            kirim=true;
                            readchat();
                        }
                    });
                }

                }else {

            }
    }

    ///Load Token
    public void tokenFr(){
        loading = ProgressDialog.show(ListChat.this, "", getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("formRequestCd",modultrans);
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
                    if (data.get("liveChatOthersFirebaseToken").toString().equals("null")){
                        tokennya = "-";
                    }else {
                        tokennya2.clear();
                        JsonArray tokeny = data.getAsJsonArray("liveChatOthersFirebaseToken");
                        for (int c = 0; c < tokeny.size(); ++c) {
                            JsonObject assobj2 = tokeny.get(c).getAsJsonObject();
                            tokennya2.add(assobj2.get("Token").getAsString());
                        }

                        Log.d("listToken", tokennya2.toString());
                    }
                }else {
                    sesionid();
                    loading.dismiss();
                    Toast.makeText(ListChat.this, errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ListChat.this, getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
        Log.d("requestSTdetails",jsonObject.toString());
    }

}