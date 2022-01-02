package com.smartcarecenter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.Chat.Adapterchat;
import com.smartcarecenter.Chat.Itemchat;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
import com.smartcarecenter.historyfr.AdapterHistoryfr;
import com.smartcarecenter.historyfr.ItemHistoryfr;
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
import static com.smartcarecenter.historyfr.AdapterHistoryfr.frpos;
import static com.smartcarecenter.messagecloud.check.tokennya2;


public class DetailsFormActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    ArrayList<Itemchat> itemchat;
    LinearLayout mdot;
    TextView mnotif;
    LinearLayout mchactclik;
    String engas="";
    int total1 = 0;
    Itemchat itemchat2;
    DatabaseReference databaseReference5;
    public  static String mcustname="";
    String tokennya = "-";
    Adapterchat adapterchat;
    public static String name="";
    public static LinearLayout mlayhistorifr;
    public static boolean inforeopen = true;
    public static EditText mreasonnya;
    public static  TextView mreschdate;
    public static String noreq = "";
    public static String MhaveToUpdate = "";
    public static String MsessionExpired = "";
    public static boolean internet = true;
    public static LinearLayoutManager linearLayoutManager,linearLayoutManager2;
    public static ArrayList<ServicesTicketItem> listticket;
    public static ServiceTicketAdapter ticketadapter;
    public static ArrayList<ItemHistoryfr> itemfr;
    public static AdapterHistoryfr adaptaerfr;
    public static ProgressDialog loading;
    public static String mallowToCancel = "";
    public static String mallowtoconfirm = "";
    public static ImageView mbanner;
    public static LinearLayout mcancel, mconfirm, mcs, mbackgroundalert,mback, mreopenbtn;
    public static TextView mcreatedate, mdate, mdeskription, missu, moperator, mreqno, mservicetype, msn, mstatusdetail,
            mstid, mtitle, munitcategory, mlocation, mtextalert, mrequestby, mreopeninfo;
    public static String mdateapi = "";
    public static String mdeskriptionapi = "";
    public static String mformRequestCd = "";
    public static String mreopen = "";
    public static ImageView mimgpopup;
    public static LinearLayout mlayoutticket,mlayoutunit1, mlayoutunit2, mlayoutunit3, mreinfolay;
    public static  LinearLayoutManager mlinear;
    public static String mphotoURL = "";
    public static  String mpressGuid = "";
    public static String mpressName = "";
    public static String mrequestedBy = "";
    public static String mrequestedDateTime = "";
    public static String mserviceTicketCd = "";
    public static String xlocation = "";
    public static JsonArray mserviceTicketHistory;
    public static JsonArray mhistoryfr;
    public static JsonArray massistengineer;
    public static RecyclerView mservice_layout;
    public static RecyclerView frhis_layout;
    public static String mstatus = "";
    public static String mstatusColorCode = "";
    public static String mstatusName = "";
    public  static String noticket = "";
    public static String sesionid_new = "";
    public static String guid = "";
    public static String mreason="";
    public static String username = "";
    boolean installed= true;
    //timer
    public static String assist="";
    public static int seconds = 0;
    public static String usetime="";
    public static boolean running;
    //timer
    public static int START_TIME_IN_MILLIS = 0;
    public static TextView mtimerconfirm;
    public static CountDownTimer mCountDownTimer;
    public static long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    public static String Nowaform = "0";
    public static  NestedScrollView mscroll;
    public static String scrollnya = "no";
    public static int yverti=0;
    public static int xhori=0;
    TextView mlistdailyst;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_form);
        mlistdailyst = findViewById(R.id.listdailyst);
        mnotif = findViewById(R.id.newnotif);
        mdot = findViewById(R.id.dot);
        mchactclik = findViewById(R.id.chatclik);
        mlayhistorifr = findViewById(R.id.layhistorifr);
        mscroll = findViewById(R.id.scrollnya);
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
        frhis_layout=findViewById(R.id.historyfr);
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
                linearLayoutManager2 = new LinearLayoutManager(DetailsFormActivity.this, LinearLayout.VERTICAL,false);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        mservice_layout.setLayoutManager(linearLayoutManager);
        mservice_layout.setHasFixedSize(true);
        frhis_layout.setLayoutManager(linearLayoutManager2);
        frhis_layout.setHasFixedSize(true);
        listticket = new ArrayList();
        itemfr = new ArrayList<>();

        tokennya2.clear();
        //getsessionId
        seconds=0;
        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            noreq = bundle2.getString("id");
            guid = bundle2.getString("guid");
            username = bundle2.getString("user");
            noticket = bundle2.getString("noticket");
            valuefilter = bundle2.getString("pos");
            scrollnya =   bundle2.getString("scrolbawah");
            xhori=bundle2.getInt("xhori");
            yverti=bundle2.getInt("yverti");
            Log.d("scrolnya",scrollnya+"/"+xhori+"/"+yverti);

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

        mlistdailyst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsFormActivity.this, DailiReportListFR.class);
//              intent.putExtra("id", (addFromItem.get(i).getFormRequestCd()));
                intent.putExtra("home", "homesa");
                intent.putExtra("pos", valuefilter);
                intent.putExtra("noticket", noticket);
                intent.putExtra("id", noreq);
                intent.putExtra("pos", valuefilter);
                intent.putExtra("user", username);
                intent.putExtra("scrolbawah", scrollnya);
                intent.putExtra("xhori", xhori);
                intent.putExtra("yverti", yverti);
                intent.putExtra("hide","yes");
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                finish();
            }
        });

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mscroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    yverti = scrollY;
                    xhori = scrollX;
                    Log.d("scrollabe", String.valueOf(scrollX)+"/"+String.valueOf(scrollY)+"/");
                }
            });

        }
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
//        mreschdate=v.findViewById(R.id.reasondes);
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
                    if (data.get("showViewSTDailyReport").getAsBoolean()){
                        mlistdailyst.setVisibility(View.VISIBLE);
                        mlistdailyst.setText(data.get("stDailyReportButtonText").getAsString());
                    }else {
                        mlistdailyst.setVisibility(View.GONE);
                    }
                    //chat baru pasang
                    if(data.get("liveChatShowButton").getAsBoolean()){
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.createUserWithEmailAndPassword(data.get("liveChatUserID").getAsString(), "x8x8x8")
                                .addOnCompleteListener(DetailsFormActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d("trag", "signInWithCustomToken:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
//
                                            loading.dismiss();
                                        } else {
                                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                                mAuth.signInWithEmailAndPassword(data.get("liveChatUserID").getAsString(), "x8x8x8")
                                                        .addOnCompleteListener(DetailsFormActivity.this, new OnCompleteListener<AuthResult>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                                if (task.isSuccessful()) {
                                                                    // Sign in success, update UI with the signed-in user's information
                                                                    Log.d("trag", "signInWithCustomToken:success");
                                                                    FirebaseUser user = mAuth.getCurrentUser();
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
                                                loading.dismiss();
                                            }else {
                                                loading.dismiss();
                                            }
                                            loading.dismiss();
                                            // If sign in fails, display a message to the user.
                                            Log.w("uii", "signInWithCustomToken:failure", task.getException());
//                                    Toast.makeText(CustomAuthActivity.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);

                                        }
                                    }
                                }).addOnFailureListener(DetailsFormActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loading.dismiss();
                                Log.d("gagal login",e.toString());
                            }
                        });

                        itemchat = new ArrayList<Itemchat>();
                        itemchat2 = new Itemchat();
                        databaseReference5= FirebaseDatabase.getInstance().getReference().child("chat").child(data.get("liveChatID").getAsString()).child("listchat");
                        //
                        name=data.get("liveChatUserName").getAsString();
                        mchactclik.setVisibility(View.VISIBLE);
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
                        databaseReference5.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemchat.clear();
                                total1 = 0;
                                if (dataSnapshot.exists()){
                                    for(DataSnapshot ds: dataSnapshot.getChildren())
                                    {
                                        Itemchat fetchDatalist=ds.getValue(Itemchat.class);
                                        fetchDatalist.setKey(ds.getKey());
                                        itemchat.add(fetchDatalist);
                                    }

                                    adapterchat=new Adapterchat(DetailsFormActivity.this, itemchat);
                                    for (int i = 0; i < itemchat.size(); i++) {
                                        if (itemchat.get(i).getName().equals(name)){
                                            mdot.setVisibility(View.GONE);
                                        }else {

                                            if (itemchat.get(i).getRead().equals("yes")){
                                                mdot.setVisibility(View.GONE);
                                            }else {
                                                total1 +=1;
                                                mdot.setVisibility(View.VISIBLE);
                                                mnotif.setText(String.valueOf(total1));
                                            }
                                        }
                                    }

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        engas = "";
                    }else {
                        mchactclik.setVisibility(View.GONE);

                    }
                    mchactclik.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            loading.show();
//                            Window window = loading.getWindow();
//                            window.setLayout(300, 300);
                            Intent gotonews = new Intent(DetailsFormActivity.this, ListChat.class);
                            gotonews.putExtra("name",data.get("liveChatUserName").getAsString());
                            gotonews.putExtra("sessionnya",data.get("liveChatID").getAsString());
                            gotonews.putExtra("chat",data.get("liveChatAllowToChat").getAsBoolean());
                            gotonews.putExtra("titlenya",data.get("liveChatTitle").getAsString());
                            gotonews.putExtra("user",username);
                            gotonews.putExtra("id",noreq);
                            gotonews.putExtra("moduletrans", "kosong");
                            gotonews.putExtra("ping",1);
                            gotonews.putExtra("liveChatRepor",data.get("liveChatReportWhenUserChat").getAsBoolean());
                            gotonews.putExtra("page","detailst");
                            gotonews.putExtra("tokennya",tokennya);
                            gotonews.putExtra("engname", mcustname);
                            gotonews.putExtra("nofr", mformRequestCd);
                            startActivity(gotonews);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            finish();
                            /////

                        }
                    });
//                    mchactclik.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            loading.show();
////                            Window window = loading.getWindow();
////                            window.setLayout(300, 300);
//                            mAuth = FirebaseAuth.getInstance();
//                            mAuth.createUserWithEmailAndPassword(data.get("liveChatUserID").getAsString(), "x8x8x8")
//                                    .addOnCompleteListener(DetailsFormActivity.this, new OnCompleteListener<AuthResult>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<AuthResult> task) {
//                                            if (task.isSuccessful()) {
//                                                // Sign in success, update UI with the signed-in user's information
//                                                Log.d("trag", "signInWithCustomToken:success");
//                                                FirebaseUser user = mAuth.getCurrentUser();
////                                                Toast.makeText(DetailsST.this,"hgjgh", Toast.LENGTH_SHORT).show();
////                                    updateUI(user);
//                                                Intent gotonews = new Intent(DetailsFormActivity.this, ListChat.class);
//                                                gotonews.putExtra("name",data.get("liveChatUserName").getAsString());
//                                                gotonews.putExtra("sessionnya",data.get("liveChatID").getAsString());
//                                                gotonews.putExtra("chat",data.get("liveChatAllowToChat").getAsBoolean());
//                                                gotonews.putExtra("titlenya",data.get("liveChatTitle").getAsString());
//                                                gotonews.putExtra("user",username);
//                                                gotonews.putExtra("id",noreq);
//                                                gotonews.putExtra("tokennya",tokennya);
//                                                gotonews.putExtra("engname", mcustname);
//                                                gotonews.putExtra("nofr", mformRequestCd);
//                                                startActivity(gotonews);
//                                                overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                                                finish();
//                                                loading.dismiss();
//                                            } else {
//                                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
//                                                    mAuth.signInWithEmailAndPassword(data.get("liveChatUserID").getAsString(), "x8x8x8")
//                                                            .addOnCompleteListener(DetailsFormActivity.this, new OnCompleteListener<AuthResult>() {
//                                                                @Override
//                                                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                                                    if (task.isSuccessful()) {
//                                                                        // Sign in success, update UI with the signed-in user's information
//                                                                        Log.d("trag", "signInWithCustomToken:success");
//                                                                        FirebaseUser user = mAuth.getCurrentUser();
////                                                                        Toast.makeText(Chating.this,"hgjgh", Toast.LENGTH_SHORT).show();
////                                    updateUI(user);
//                                                                        Intent gotonews = new Intent(DetailsFormActivity.this, ListChat.class);
//                                                                        gotonews.putExtra("name",data.get("liveChatUserName").getAsString());
//                                                                        gotonews.putExtra("sessionnya",data.get("liveChatID").getAsString());
//                                                                        gotonews.putExtra("chat",data.get("liveChatAllowToChat").getAsBoolean());
//                                                                        gotonews.putExtra("titlenya",data.get("liveChatTitle").getAsString());
//                                                                        gotonews.putExtra("user",username);
//                                                                        gotonews.putExtra("id",noreq);
//                                                                        gotonews.putExtra("tokennya",tokennya);
//                                                                        gotonews.putExtra("engname", mcustname);
//                                                                        gotonews.putExtra("nofr", mformRequestCd);
//                                                                        startActivity(gotonews);
//                                                                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                                                                        finish();
//                                                                        loading.dismiss();
//                                                                    } else {
//                                                                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
////                                                                            Toast.makeText(DetailsST.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();
//                                                                        }else {
//
//                                                                        }
//                                                                        // If sign in fails, display a message to the user.
//                                                                        Log.w("uii", "signInWithCustomToken:failure", task.getException());
////                                    Toast.makeText(CustomAuthActivity.this, "Authentication failed.",
////                                            Toast.LENGTH_SHORT).show();
////                                    updateUI(null);
//
//                                                                    }
//                                                                }
//                                                            });
////                                                    Toast.makeText(DetailsST.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();
//
//                                                }else {
//                                                    loading.dismiss();
//                                                }
////                                                loading.dismiss();
//                                                // If sign in fails, display a message to the user.
//                                                Log.w("uii", "signInWithCustomToken:failure", task.getException());
////                                    Toast.makeText(CustomAuthActivity.this, "Authentication failed.",
////                                            Toast.LENGTH_SHORT).show();
////                                    updateUI(null);
//
//                                            }
//                                        }
//                                    }).addOnFailureListener(DetailsFormActivity.this, new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    loading.dismiss();
//                                    Log.d("gagal login",e.toString());
//                                }
//                            });
//
//                            /////
//
//                        }
//                    });
                    /////
                    if(data.get("relatedFormRequestList").toString().equals("null")){
                            mlayhistorifr.setVisibility(View.GONE);
                    }else {
                        mlayhistorifr.setVisibility(View.VISIBLE);
                        mhistoryfr = data.getAsJsonArray("relatedFormRequestList");
                        Log.d("relatedFormRequestList",mhistoryfr.toString());
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<ItemHistoryfr>>(){}.getType();
                        itemfr = gson.fromJson(mhistoryfr.toString(), type);
                        adaptaerfr = new AdapterHistoryfr(DetailsFormActivity.this,itemfr);
                        frhis_layout.setAdapter(adaptaerfr);


                    }

                    if (data.get("showFooterWA").getAsBoolean()){
                        mcs.setVisibility(View.VISIBLE);
                    }else {
                        mcs.setVisibility(View.GONE);
                    }
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
                    frpos = mformRequestCd;
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
                    if (scrollnya==null){
                        mscroll.scrollTo(0,1900);
                    }else {
                        mscroll.scrollTo(xhori,yverti);
                    }
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
                if (check.checkhome==0){
                    if (check.checklistform==1){
                        list2.clear();
                        refresh=true;
                    }
//                    Intent back = new Intent(DetailsST.this,Home.class);
//                    back.putExtra("pos",valuefilter);
//                    startActivity(back);
//                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
//                    finish();
                    super.onBackPressed();
                    finish();
                }else {
                    Intent back = new Intent(DetailsFormActivity.this,Dashboard.class);
                    back.putExtra("pos",valuefilter);
                    startActivity(back);
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    finish();
                }

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