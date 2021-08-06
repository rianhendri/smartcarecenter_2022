/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Color
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 *  androidx.recyclerview.widget.RecyclerView
 *  androidx.recyclerview.widget.RecyclerView$Adapter
 *  androidx.recyclerview.widget.RecyclerView$ViewHolder
 *  com.squareup.picasso.Picasso
 *  com.squareup.picasso.RequestCreator
 *  java.io.PrintStream
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.text.ParseException
 *  java.text.SimpleDateFormat
 *  java.util.ArrayList
 *  java.util.Date
 *  java.util.Locale
 */
package com.smartcarecenter.historyfr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.BuildConfig;
import com.smartcarecenter.DetailsFormActivity;
import com.smartcarecenter.LoginActivity;
import com.smartcarecenter.NoInternet;
import com.smartcarecenter.R;
import com.smartcarecenter.UpdateActivity;
import com.smartcarecenter.apihelper.IRetrofit;
import com.smartcarecenter.apihelper.ServiceGenerator;
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

import static com.smartcarecenter.DetailsFormActivity.MhaveToUpdate;
import static com.smartcarecenter.DetailsFormActivity.MsessionExpired;
import static com.smartcarecenter.DetailsFormActivity.START_TIME_IN_MILLIS;
import static com.smartcarecenter.DetailsFormActivity.adaptaerfr;
import static com.smartcarecenter.DetailsFormActivity.frhis_layout;
import static com.smartcarecenter.DetailsFormActivity.inforeopen;
import static com.smartcarecenter.DetailsFormActivity.itemfr;
import static com.smartcarecenter.DetailsFormActivity.listticket;
import static com.smartcarecenter.DetailsFormActivity.mTimeLeftInMillis;
import static com.smartcarecenter.DetailsFormActivity.mallowToCancel;
import static com.smartcarecenter.DetailsFormActivity.mallowtoconfirm;
import static com.smartcarecenter.DetailsFormActivity.massistengineer;
import static com.smartcarecenter.DetailsFormActivity.mbackgroundalert;
import static com.smartcarecenter.DetailsFormActivity.mbanner;
import static com.smartcarecenter.DetailsFormActivity.mcancel;
import static com.smartcarecenter.DetailsFormActivity.mconfirm;
import static com.smartcarecenter.DetailsFormActivity.mcreatedate;
import static com.smartcarecenter.DetailsFormActivity.mcs;
import static com.smartcarecenter.DetailsFormActivity.mdate;
import static com.smartcarecenter.DetailsFormActivity.mdateapi;
import static com.smartcarecenter.DetailsFormActivity.mdeskription;
import static com.smartcarecenter.DetailsFormActivity.mdeskriptionapi;
import static com.smartcarecenter.DetailsFormActivity.mformRequestCd;
import static com.smartcarecenter.DetailsFormActivity.mhistoryfr;
import static com.smartcarecenter.DetailsFormActivity.missu;
import static com.smartcarecenter.DetailsFormActivity.mlayoutticket;
import static com.smartcarecenter.DetailsFormActivity.mlayoutunit1;
import static com.smartcarecenter.DetailsFormActivity.mlayoutunit2;
import static com.smartcarecenter.DetailsFormActivity.mlayoutunit3;
import static com.smartcarecenter.DetailsFormActivity.mphotoURL;
import static com.smartcarecenter.DetailsFormActivity.mpressName;
import static com.smartcarecenter.DetailsFormActivity.mreinfolay;
import static com.smartcarecenter.DetailsFormActivity.mreopen;
import static com.smartcarecenter.DetailsFormActivity.mreopenbtn;
import static com.smartcarecenter.DetailsFormActivity.mreopeninfo;
import static com.smartcarecenter.DetailsFormActivity.mreqno;
import static com.smartcarecenter.DetailsFormActivity.mscroll;
import static com.smartcarecenter.DetailsFormActivity.mserviceTicketCd;
import static com.smartcarecenter.DetailsFormActivity.mserviceTicketHistory;
import static com.smartcarecenter.DetailsFormActivity.mservice_layout;
import static com.smartcarecenter.DetailsFormActivity.mservicetype;
import static com.smartcarecenter.DetailsFormActivity.msn;
import static com.smartcarecenter.DetailsFormActivity.mstatusColorCode;
import static com.smartcarecenter.DetailsFormActivity.mstatusName;
import static com.smartcarecenter.DetailsFormActivity.mstatusdetail;
import static com.smartcarecenter.DetailsFormActivity.mstid;
import static com.smartcarecenter.DetailsFormActivity.mtextalert;
import static com.smartcarecenter.DetailsFormActivity.mtimerconfirm;
import static com.smartcarecenter.DetailsFormActivity.mtitle;
import static com.smartcarecenter.DetailsFormActivity.munitcategory;
import static com.smartcarecenter.DetailsFormActivity.noreq;
import static com.smartcarecenter.DetailsFormActivity.scrollnya;
import static com.smartcarecenter.DetailsFormActivity.sesionid_new;
import static com.smartcarecenter.DetailsFormActivity.ticketadapter;
import static com.smartcarecenter.DetailsFormActivity.xhori;
import static com.smartcarecenter.DetailsFormActivity.xlocation;
import static com.smartcarecenter.DetailsFormActivity.yverti;
import static com.smartcarecenter.apihelper.ServiceGenerator.baseurl;


//import static com.e.chatforscctest.ListChat.idhcat;


public class AdapterHistoryfr
extends RecyclerView.Adapter<AdapterHistoryfr.Myviewholder>  {

    public static ArrayList<ItemHistoryfr> addFoclistreq;
    Context context;
    public static String frpos = "";
    String historeq="";
    ProgressDialog loading;
    public AdapterHistoryfr(Context context, ArrayList<ItemHistoryfr> addFoclistitem) {
        this.context = context;
        this.addFoclistreq = addFoclistitem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_historyfr,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        myviewholder.mnamafr.setText(addFoclistreq.get(i).getText());
        myviewholder.mnamafr.setPaintFlags( myviewholder.mnamafr.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        if (frpos.equals(addFoclistreq.get(i).getValue())){
            myviewholder.mbackhis.setBackgroundColor(Color.parseColor("#E6E6E6"));
        }else {
            myviewholder.mbackhis.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historeq=addFoclistreq.get(i).getValue();
                loadData();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 
                addFoclistreq.size();
    }


    public static class Myviewholder extends RecyclerView.ViewHolder{
        TextView mnamafr;
        LinearLayout mbackhis;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            mnamafr = itemView.findViewById(R.id.namafr);
            mbackhis = itemView.findViewById(R.id.backhis);
        }
    }
    public void loadData(){
        loading = ProgressDialog.show(context, "", context.getString(R.string.title_loading), true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sessionId",sesionid_new);
        jsonObject.addProperty("formRequestCd",historeq);
        jsonObject.addProperty("ver", BuildConfig.VERSION_NAME);
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

                    if(data.get("relatedFormRequestList").toString().equals("null")){

                    }else {
                        mhistoryfr = data.getAsJsonArray("relatedFormRequestList");
                        Log.d("relatedFormRequestList",mhistoryfr.toString());
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<ItemHistoryfr>>(){}.getType();
                        itemfr = gson.fromJson(mhistoryfr.toString(), type);
                        adaptaerfr = new AdapterHistoryfr(context,itemfr);
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
                            mtextalert.setText((CharSequence) Html.fromHtml((String)text, Html.FROM_HTML_MODE_COMPACT));
                            mtextalert.setMovementMethod(LinkMovementMethod.getInstance());
                        } else {
                            mtextalert.setText((CharSequence)Html.fromHtml((String)text));
                            mtextalert.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                    }else {
                        mbackgroundalert.setVisibility(View.GONE);
                    }
                    //timer
                    DetailsFormActivity.usetime = data.get("useTimer").toString();
                    int hours = data.get("timerStartHours").getAsInt();
                    int minute = data.get("timerStartMinutes").getAsInt();
                    int second = data.get("timerStartSeconds").getAsInt();
                    DetailsFormActivity.seconds = (hours*60*60*1000)+(minute*60*1000)+(second*1000);
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
                    DetailsFormActivity.mpressGuid = data.get("pressGuid").getAsString();
                    mpressName = data.get("pressName").getAsString();
                    mphotoURL = data.get("photoURL").getAsString();
                    DetailsFormActivity.mstatus = data.get("status").getAsString();
                    mstatusName = data.get("statusName").getAsString();
                    mstatusColorCode = data.get("statusColorCode").getAsString();
                    mdeskriptionapi = data.get("description").getAsString();
                    DetailsFormActivity.mrequestby.setText(data.get("requestedBy").getAsString());
                    DetailsFormActivity.moperator.setText(data.get("operatorName").getAsString());
                    DetailsFormActivity.mrequestedDateTime = data.get("requestedDateTime").getAsString();
                    mallowToCancel = data.get("allowToCancel").toString();
                    mallowtoconfirm = data.get("allowToConfirm").toString();
                    xlocation = data.get("locationName").getAsString();
                    DetailsFormActivity.mlocation.setText(xlocation);
                    if (mserviceTicketCd.equals("null")){
                        mlayoutticket.setVisibility(View.GONE);
                    }else {
                        mlayoutticket.setVisibility(View.VISIBLE);
                        mserviceTicketHistory = data.getAsJsonArray("serviceTicketHistory");

                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<ServicesTicketItem>>(){}.getType();
                        listticket = gson.fromJson(mserviceTicketHistory.toString(), type);
                        ticketadapter = new ServiceTicketAdapter(context,listticket);
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
                    Picasso.with(context).load(mphotoURL).into(mbanner);
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
                    Toast.makeText(context, errornya,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(context, context.getString(R.string.title_excpetation),Toast.LENGTH_LONG).show();
                cekInternet();
                loading.dismiss();

            }
        });
        Log.d("requestSTdetails",jsonObject.toString());
    }
    public void sesionid() {
        if (MsessionExpired.equals("false")) {
            if (MhaveToUpdate.equals("false")) {


            }else {
                Intent gotoupdate = new Intent(context, UpdateActivity.class);
                context.startActivity(gotoupdate);
                ((Activity) context).finish();
            }

        }else {
            context.startActivity(new Intent(context, LoginActivity.class));
            ((Activity) context).finish();;
            Toast.makeText(context, context.getString(R.string.title_session_Expired),Toast.LENGTH_LONG).show();
        }

    }
    public void cekInternet(){
        /// cek internet apakah internet terhubung atau tidak
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
           DetailsFormActivity.internet = true;


        }else {
            DetailsFormActivity.internet=false;
            Intent noconnection = new Intent(context, NoInternet.class);
            context.startActivity(noconnection);
            ((Activity) context).finish();
        }
        //// pengecekan internet selesai

    }
    private void startTimer() {
        DetailsFormActivity.mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
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
        mtimerconfirm.setText(context.getString(R.string.title_confirm)+" ("+timeLeftFormatted+")");
    }
}

