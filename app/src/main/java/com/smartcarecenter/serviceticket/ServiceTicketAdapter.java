/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.Html
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.RatingBar
 *  android.widget.TextView
 *  androidx.recyclerview.widget.RecyclerView
 *  androidx.recyclerview.widget.RecyclerView$Adapter
 *  androidx.recyclerview.widget.RecyclerView$ViewHolder
 *  java.io.PrintStream
 *  java.lang.CharSequence
 *  java.lang.Float
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
package com.smartcarecenter.serviceticket;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartcarecenter.Chat.Adapterchat;
import com.smartcarecenter.Chat.Itemchat;
import com.smartcarecenter.ListChat;
import com.smartcarecenter.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.smartcarecenter.DetailsFormActivity.noreq;
import static com.smartcarecenter.DetailsFormActivity.seconds;
import static com.smartcarecenter.DetailsFormActivity.username;
import static com.smartcarecenter.DetailsFormActivity.usetime;
import static com.smartcarecenter.DetailsFormActivity.xhori;
import static com.smartcarecenter.DetailsFormActivity.yverti;
import static com.smartcarecenter.ListChat.name;
import static com.smartcarecenter.messagecloud.check.tokennya2;


public class ServiceTicketAdapter
extends RecyclerView.Adapter<ServiceTicketAdapter.Myviewholder> {
    DatabaseReference databaseReference7;
    ArrayList<Itemchat> itemchat;
    Itemchat itemchat2;
    Adapterchat adapterchat;
    int total1 = 0;
    boolean solved = true;
    boolean showprogress = true;
    Context context;
    ArrayList<ServicesTicketItem> myItem;
    public static int positem = 0;
    ImageView mimgpopup;
    //timer
    long MillisecondTime, StartTime, UpdateTime = 0L ;
    long TimeBuff = seconds ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds, Jam ;
    boolean showmore = true;
    public ServiceTicketAdapter(Context c, ArrayList<ServicesTicketItem> p){
        context = c;
        myItem = p;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_serviceticket,
                viewGroup, false));


    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        if (myItem.get(i).getPressStatusCd().equals("")){
            myviewholder.mstatusadress.setText("-");
        }else {
            myviewholder.mstatusadress.setText(myItem.get(i).getPressStatusCd());
        }
        TextView textView = myviewholder.mstatustik;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#");
        stringBuilder.append(String.valueOf((int)((ServicesTicketItem)this.myItem.get(i)).getPosition()));
        textView.setText((CharSequence)stringBuilder.toString());

        if (myItem.get(i).getFeedbackPhotoFullURL()==null){
            myviewholder.murlfoto.setVisibility(View.GONE);
        }else {
            myviewholder.murlfoto.setVisibility(View.VISIBLE);
        }

        myviewholder.murlfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context, R.style.TransparentDialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.popupfoto);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                mimgpopup = dialog.findViewById(R.id.imagepopup);
                Picasso.with(context).load(myItem.get(i).getFeedbackPhotoFullURL()).into(mimgpopup);
                dialog.show();
            }
        });

        showprogress = myItem.get(i).isShowOnProgressAction();
        if (showprogress){
            myviewholder.mlayac.setVisibility(View.VISIBLE);
            myviewholder.mactionprogress.setText(myItem.get(i).getOnProgressActionName());
        }else {
            myviewholder.mlayac.setVisibility(View.GONE);
        }
        if (myItem.get(i).getPosition()==myItem.size()){
            if (usetime.equals("true")){
                myviewholder.msupport.setVisibility(View.GONE);
                myviewholder.mtimer.setVisibility(View.VISIBLE);
            }else {
                myviewholder.msupport.setVisibility(View.VISIBLE);
                myviewholder.mtimer.setVisibility(View.GONE);
            }
        }
//        Toast.makeText(context, String.valueOf(myItem.size()),Toast.LENGTH_LONG).show();

        String string5 = ((ServicesTicketItem)this.myItem.get(i)).getAssignedDateTime();
        String string6 = ((ServicesTicketItem)this.myItem.get(i)).getSupportStartDateTime();
        String string7 = ((ServicesTicketItem)this.myItem.get(i)).getSupportEndDateTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String string8 = ((ServicesTicketItem)this.myItem.get(i)).getBar1Text();
        String string9 = ((ServicesTicketItem)this.myItem.get(i)).getBar2Text();
        String string10 = ((ServicesTicketItem)this.myItem.get(i)).getBar3Text();
        String string11 = ((ServicesTicketItem)this.myItem.get(i)).getBar4Text();
        if (Build.VERSION.SDK_INT >= 24) {
            myviewholder.mbar1.setText((CharSequence)Html.fromHtml((String)string8, Html.FROM_HTML_MODE_COMPACT));
            myviewholder.mbar2.setText((CharSequence)Html.fromHtml((String)string9, Html.FROM_HTML_MODE_COMPACT));
            myviewholder.mbar3.setText((CharSequence)Html.fromHtml((String)string10, Html.FROM_HTML_MODE_COMPACT));
            myviewholder.mbar4.setText((CharSequence)Html.fromHtml((String)string11, Html.FROM_HTML_MODE_COMPACT));
        } else {
            myviewholder.mbar1.setText((CharSequence)Html.fromHtml((String)string8));
            myviewholder.mbar2.setText((CharSequence)Html.fromHtml((String)string9));
            myviewholder.mbar3.setText((CharSequence)Html.fromHtml((String)string10));
            myviewholder.mbar4.setText((CharSequence)Html.fromHtml((String)string11));
        }
        solved = myItem.get(i).isBar4Red();
        String startdate = "";
        String enddate = "";
        String assigndate = "";
        if (myItem.get(i).getSupportStartDateTime()!=null){
            if (myItem.get(i).getSupportEndDateTime()!=null){
                String oladend = myItem.get(i).getSupportEndDateTime();
                try {
                    enddate = simpleDateFormat2.format(simpleDateFormat.parse(oladend));
                    System.out.println(enddate);
                    Log.e((String)"Date", (String)startdate);
                }
                catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }
            String oladstart = myItem.get(i).getSupportStartDateTime();

            try {
                startdate = simpleDateFormat2.format(simpleDateFormat.parse(oladstart));
                System.out.println(startdate);
                Log.e((String)"Date", (String)startdate);
            }
            catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        }
        if (myItem.get(i).getAssignedDateTime()!=null){
            String oldass = myItem.get(i).getAssignedDateTime();
            try {
                assigndate = simpleDateFormat2.format(simpleDateFormat.parse(oldass));
                System.out.println(enddate);
                Log.e((String)"Date", (String)startdate);
            }
            catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            myviewholder.massigndate.setText(assigndate);
        }

        if (myItem.get(i).getWaitingEstimationDate()!=null){
            myviewholder.mlayestima.setVisibility(View.VISIBLE);
            SimpleDateFormat datefor = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String estima = myItem.get(i).getWaitingEstimationDate();
            String estimadate = "";
            try {
                estimadate = datefor.format(simpleDateFormat.parse(estima));
                System.out.println(estimadate);
                Log.e((String)"Date", (String)estimadate);
            }
            catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            myviewholder.mestima.setText(estimadate);
        }else {
            myviewholder.mlayestima.setVisibility(View.GONE);
        }

        if (myItem.get(i).getFeedbackRating()==0){
            myviewholder.mlayoutstart.setAlpha(0);
        }else {
            myviewholder.mlayoutstart.setAlpha(1);
            myviewholder.mrating.setRating(myItem.get(i).getFeedbackRating());
            myviewholder.mcomment.setText(myItem.get(i).getFeedbackComments());
        }
        if (myItem.get(i).getBarPosition()==1){
            myviewholder.mposbar.setImageResource(R.drawable.asign);
            myviewholder.mbar1.setTextColor(context.getResources().getColor(R.color.black));
        } else if (myItem.get(i).getBarPosition() == 2) {
            myviewholder.mposbar.setImageResource(R.drawable.onprogress);

            myviewholder.mbar2.setTextColor(context.getResources().getColor(R.color.black));

        }else if (myItem.get(i).getBarPosition() == 3){
            myviewholder.mposbar.setImageResource(R.drawable.waiting);

            myviewholder.mbar2.setTextColor(context.getResources().getColor(R.color.black));
            myviewholder.mbar3.setTextColor(context.getResources().getColor(R.color.black));

        }else if (solved){
            myviewholder.mposbar.setImageResource(R.drawable.unsolved);

            myviewholder.mbar2.setTextColor(context.getResources().getColor(R.color.black));
            myviewholder.mbar3.setTextColor(context.getResources().getColor(R.color.black));
            myviewholder.mbar4.setTextColor(context.getResources().getColor(R.color.black));

        }
        else {
            myviewholder.mposbar.setImageResource(R.drawable.complete);

            myviewholder.mbar2.setTextColor(context.getResources().getColor(R.color.black));
            myviewholder.mbar3.setTextColor(context.getResources().getColor(R.color.black));
            myviewholder.mbar4.setTextColor(context.getResources().getColor(R.color.black));

        }
        if (myItem.get(i).getSupportStartDateTime()==null){
            myviewholder.mstarttime.setText("-");
        }else {
            myviewholder.mstarttime.setText(startdate);

            if (myItem.get(i).getSupportEndDateTime()==null){
                myviewholder.mendtime.setText("-");
            }else {
                myviewholder.mendtime.setText(enddate);
            }
        }
        myviewholder.mengineer.setText(myItem.get(i).getEngineerName());
        if (myItem.get(i).getAssist()==null){
            myviewholder.mlasyass.setVisibility(View.GONE);
        }else {
            myviewholder.massengineer.setText(myItem.get(i).getAssist());
            myviewholder.mlasyass.setVisibility(View.VISIBLE);
        }
        String last = null;
        last = String.valueOf(myItem.get(i).getLastImpression());
        if (myItem.get(i).getLastImpression()==0){
            myviewholder.mlayimpres.setVisibility(View.GONE);
        }else {
            myviewholder.mlayimpres.setVisibility(View.VISIBLE);
            myviewholder.mlastimpresi.setText(last);
        }

        //timer

        handler = new Handler() ;
        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MillisecondTime = SystemClock.uptimeMillis() - StartTime;
//            mest.setText(String.valueOf(MillisecondTime));
                UpdateTime = TimeBuff + MillisecondTime;

                Seconds = (int) (UpdateTime / 1000);
                Jam = (Seconds/60/60);
                if (TimeBuff>=3600000){
                    Minutes = (Seconds/60)-(Jam*60);
                }else {
                    Minutes = Seconds / 60;
                }
                Seconds = Seconds % 60;
                MilliSeconds = (int) (UpdateTime % 1000);

                myviewholder.mtimer.setText(String.format("%02d", Jam) + ":"
                        + String.format("%02d", Minutes) + ":"
                        + String.format("%02d", Seconds));



                handler.postDelayed(this, 0);
            }
        }, 2000);

        // notes engineering
        if (myItem.get(i).getDescription().equals("")){
            myviewholder.mlayoutnotes.setVisibility(View.GONE);
        }else {
            myviewholder.mlayoutnotes.setVisibility(View.VISIBLE);
            myviewholder.mnotes.setText(myItem.get(i).getDescription());
        }
        int sizenya = myItem.size();
        if (myItem.get(i).getPosition()==sizenya){

        }
//        myviewholder.mnotes.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
//            @Override
//            public void onExpandStateChanged(TextView textView, boolean isExpanded) {
//                if (!isExpanded){
//                    myviewholder.mreadmore.setText("Read more");
//
//                }else {
//                    myviewholder.mreadmore.setText("show less");
//
//                }
//            }
//        });
//        myviewholder.mnotes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!showmore){
//                    myviewholder.mreadmore.setText("Read more");
//                    showmore = true;
//                }else {
//                    myviewholder.mreadmore.setText("show less");
//                    showmore = false;
//                }
//            }
//        });

        // chatclik
        if (myItem.get(i).isShowLiveChat()){
            myviewholder.mchatclik.setVisibility(View.VISIBLE);
            itemchat = new ArrayList<Itemchat>();
            itemchat2 = new Itemchat();
            databaseReference7= FirebaseDatabase.getInstance().getReference().child("chat").child(myItem.get(i).getGuid()).child("listchat");

            databaseReference7.addValueEventListener(new ValueEventListener() {
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

                        adapterchat=new Adapterchat(context, itemchat);

                        for (int i = 0; i < itemchat.size(); i++) {
                            if (itemchat.get(i).getName().equals(name)){

                            }else {
                                if (itemchat.get(i).getRead().equals("yes")){
                                    myviewholder.mdot.setVisibility(View.GONE);
                                }else {

                                    total1 +=1;
                                    myviewholder.mdot.setVisibility(View.VISIBLE);
                                    myviewholder.mnotif.setText(String.valueOf(total1));
                                }
                            }
                        }



//                    recyclerView.setAdapter(adapterchat);
//                    recyclerView.scrollToPosition(adapterchat.getItemCount()-1);
//                recyclerView.scrollToPosition(adapterchat.getItemCount());
                    }

//                Log.d("posi",String.valueOf(recyclerView.getAdapter().getItemCount()));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else {
            myviewholder.mchatclik.setVisibility(View.GONE);
        }
        myviewholder.mchatclik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = "";
                if (myItem.get(i).getLiveChatFirebaseToken()==null){
                    token = "-";
                }else {

                    for (int c = 0; c < myItem.get(i).getLiveChatFirebaseToken().size(); ++c) {
//                        String assobj2 = myItem.get(i).getLiveChatFirebaseToken().get(c).getToken();
                        tokennya2.add(myItem.get(i).getLiveChatFirebaseToken().get(c).getToken());
                    }
//                    token = myItem.get(i).getLiveChatFirebaseToken();
                }
                Intent gotonews = new Intent(context, ListChat.class);
                gotonews.putExtra("name",myItem.get(i).getLiveChatName());
                gotonews.putExtra("sessionnya",myItem.get(i).getGuid());
                gotonews.putExtra("chat",myItem.get(i).isLiveChatAllowChat());
                gotonews.putExtra("user",username);
                gotonews.putExtra("id",myItem.get(i).getServiceTicketCd());
                gotonews.putExtra("frnya",noreq);

                gotonews.putExtra("tokennya",token);
                gotonews.putExtra("engname", myItem.get(i).getEngineerName());
                gotonews.putExtra("yverti", yverti);
                gotonews.putExtra("xhori", xhori);
                gotonews.putExtra("scrolbawah","-");
                context.startActivity(gotonews);
                ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                ((Activity)context).finish();
//                Log.d("tokennya1",myItem.get(i).getLiveChatFirebaseToken());
            }
        });
    }

    @Override
    public int getItemCount() {
        return
                myItem.size();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder{

        TextView massigndate;
        TextView mbar1,mbar2,mbar3,mbar4,mcomment,mendtime,mengineer,mservicetype,mstarttime
                ,mstatusservice,mstatustik;
        TextView mstatusadress,mnotif,mtimer,msupport,massengineer,mlastimpresi, mactionprogress, mfeedbackfoto, mestima, mreadmore, mnotes;
//        ExpandableTextView mnotes;
        ImageView mposbar;
        RatingBar mrating;
        LinearLayout mlayoutstart,mlasyass,mlayimpres,mlayoutnotes,mlayac, murlfoto, mlayestima, mread, mdot;
        LinearLayout mchatclik;
        public Myviewholder(@NonNull View view) {
            super(view);

            mdot = view.findViewById(R.id.dot);
            mnotif = view.findViewById(R.id.newnotif);
            mstatusadress = view.findViewById(R.id.statusadress);
            mchatclik = view.findViewById(R.id.chatclik);
            mread = view.findViewById(R.id.read);
            massigndate = (TextView)view.findViewById(R.id.assigndate);
            mengineer = (TextView)view.findViewById(R.id.engineer);
            mstarttime = (TextView)view.findViewById(R.id.starttime);
            mendtime = (TextView)view.findViewById(R.id.endtime);
            mrating = (RatingBar)view.findViewById(R.id.ratingstar);
            mbar1 = (TextView)view.findViewById(R.id.bar1);
            mbar2 = (TextView)view.findViewById(R.id.bar2);
            mbar3 = (TextView)view.findViewById(R.id.bar3);
            mbar4 = (TextView)view.findViewById(R.id.bar4);
            mposbar = (ImageView)view.findViewById(R.id.posbar);
            mstatustik = (TextView)view.findViewById(R.id.statustick);
            mcomment = (TextView)view.findViewById(R.id.comment);
            mlayoutstart = view.findViewById(R.id.ratingstarlayout);
            mtimer = view.findViewById(R.id.timer);
            msupport = view.findViewById(R.id.support);
            massengineer = view.findViewById(R.id.asengineer);
            mlasyass = view.findViewById(R.id.layass);
            mlastimpresi = view.findViewById(R.id.lastimpression);
            mlayimpres = view.findViewById(R.id.layimpress);
            mlayoutnotes = view.findViewById(R.id.layoutnotes);
            mnotes = view.findViewById(R.id.noteseng);
            mactionprogress = view.findViewById(R.id.actionprogress);
            mlayac = view.findViewById(R.id.layaction);
            mfeedbackfoto = view.findViewById(R.id.feedbackfoto);
            murlfoto = view.findViewById(R.id.urlfeedback);
            mlayestima = view.findViewById(R.id.layEstima);
            mestima = view.findViewById(R.id.estimasi);
            mreadmore = view.findViewById(R.id.readmore);

        }
    }
    public void loadchat(){

    }

}

