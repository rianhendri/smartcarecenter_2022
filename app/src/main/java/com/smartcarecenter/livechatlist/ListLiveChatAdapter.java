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
package com.smartcarecenter.livechatlist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.gson.JsonArray;
import com.smartcarecenter.Chat.Itemchat;
import com.smartcarecenter.ListChat;
import com.smartcarecenter.R;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


import static com.smartcarecenter.messagecloud.check.tokennya2;
import static com.smartcarecenter.LiveChatList.itemchat;
import static com.smartcarecenter.ListChat.modultrans;

public class ListLiveChatAdapter
extends RecyclerView.Adapter<ListLiveChatAdapter.Myviewholder> {
    Context context;
    ArrayList<ListLiveChatItem> myItem;
    String namea = "";

    public ListLiveChatAdapter(Context c, ArrayList<ListLiveChatItem> p){
        context = c;
        myItem = p;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_livechatlist,
                viewGroup, false));


    }


    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        namea=myItem.get(i).getUserName();

        myviewholder.mstnya.setText(myItem.get(i).getTitle());
        myviewholder.mdetailchat.setText(myItem.get(i).getDetails());

        String date = new SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(new Date());
        if (date.equals(myItem.get(i).getDatea())){
            myviewholder.mdatechatlist.setText(myItem.get(i).getTime());
        }else {
            myviewholder.mdatechatlist.setText(myItem.get(i).getDatea());
        }
        if (myItem.get(i).getPengirim().equals(namea)) {
            myviewholder.mdot.setVisibility(View.GONE);
        }else {
            if (myItem.get(i).getRead().equals("yes")){
                myviewholder.mdot.setVisibility(View.GONE);
            }else {
                myviewholder.mdot.setVisibility(View.VISIBLE);
            }
        }
//        lastQuery = databaseReference.child("chat").child(myItem.get(i).getLiveChatID()).child("listchat").orderByKey().limitToLast(1);
//        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                dataSnapshot.exists();
//                if (dataSnapshot.exists()){
//                    for(DataSnapshot ds: dataSnapshot.getChildren())
//                    {
//                        DetailsDate fetchDatalist=ds.getValue(DetailsDate.class);
////                        fetchDatalist.setKey(ds.getKey());
//                        itemchat.add(fetchDatalist);
//                        myviewholder.mdetailchat.setText(itemchat.get(i).getMessage());
//
//                        String date = new SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(new Date());
//                        if (date.equals(itemchat.get(i).getDate())){
//                            myviewholder.mdatechatlist.setText(itemchat.get(i).getTime());
//                        }else {
//                            myviewholder.mdatechatlist.setText(itemchat.get(i).getDate());
//                        }
//                        if (itemchat.get(i).getName().equals(namea)) {
//                            myviewholder.mdot.setVisibility(View.GONE);
//                        }else {
//                            if (itemchat.get(i).getRead().equals("yes")){
//                                myviewholder.mdot.setVisibility(View.GONE);
//                            }else {
//                                myviewholder.mdot.setVisibility(View.VISIBLE);
//                            }
//                        }
//
//                        mfooterload.setVisibility(View.GONE);
//                        myitem_place.setAlpha(1);
//                    }
////                recyclerView.scrollToPosition(adapterchat.getItemCount());
//                }
////                String message = dataSnapshot.child("message").getValue().toString();
////                myItem.get(i).setDetails(message);
////                myviewholder.mdetailchat.setText(myItem.get(i).getDetails());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle possible errors.
//            }
//        });

        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tokennya2.clear();
                for (int c = 0; c < myItem.get(i).getOthersFirebaseToken().size(); ++c) {
//                    JsonObject assobj2 = myItem.get(i).getOthersFirebaseToken().get(c).getAsJsonObject();
                    tokennya2.add(myItem.get(i).getOthersFirebaseToken().get(c).getToken());
                }
//                tokennya2.add(myItem.get(i).getOthersFirebaseToken().get(i).getToken());

                if (myItem.get(i).getModuleTransactionNo()==null){
                    modultrans="nill";
                }else {
                    modultrans=myItem.get(i).getModuleTransactionNo();
                }
                Intent gotonews = new Intent(context, ListChat.class);
                gotonews.putExtra("name",myItem.get(i).getUserName());
                gotonews.putExtra("sessionnya",myItem.get(i).getLiveChatID());
                gotonews.putExtra("chat",myItem.get(i).isAllowToChat());
                gotonews.putExtra("titlenya",myItem.get(i).getTitle());
                gotonews.putExtra("user",myItem.get(i).getUserName());
                gotonews.putExtra("moduletrans",modultrans);
                gotonews.putExtra("id",myItem.get(i).getTitle());
                gotonews.putExtra("module",myItem.get(i).getModule());
                gotonews.putExtra("page","listchat");
//                gotonews.putExtra("tokennya",tokennya);
//                gotonews.putExtra("engname", mcustname);
//                gotonews.putExtra("nofr", mformRequestCd);
                context.startActivity(gotonews);
                ((Activity)context).finish();
                ((Activity)context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                Log.d("module",myItem.get(i).getModule());
            }
        });
    }

    @Override
    public int getItemCount() {
        return
                myItem.size();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder{

        TextView minitialname, mstnya, mdetailchat, mdatechatlist, mnewnotif;
        LinearLayout mdot;
        public Myviewholder(@NonNull View view) {
            super(view);
//            minitialname = view.findViewById(R.id.initialname);
            mstnya = view.findViewById(R.id.stnya);
            mdetailchat = view.findViewById(R.id.detailchat);
            mdatechatlist = view.findViewById(R.id.datechatlist);
            mnewnotif = view.findViewById(R.id.newnotif);
            mdot = view.findViewById(R.id.dot);
        }
    }

}

