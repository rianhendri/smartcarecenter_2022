package com.smartcarecenter;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.smartcarecenter.Chat.Adapterchat.download;
import static com.smartcarecenter.Chat.Adapterchat.download2;
import static com.smartcarecenter.ListChat.sessionnya;
import static com.smartcarecenter.ListChat.sessionnya;

public class DownloadBroadcastReceiver extends BroadcastReceiver {
    public static String pathnya = "";
    public static String urinya = "";
    public static String key="0";
    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            //Show a notification
            HashMap hashMap = new HashMap();
            if (urinya.equals("me")){
                hashMap.put("myuri",pathnya);
            }else {
                hashMap.put("youruri",pathnya);
            }
            databaseReference2.child("chat").child(sessionnya).child("listchat").child(key).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(context, "File berhasil didownload", Toast.LENGTH_SHORT).show();
                    Log.d("hasmapnya", hashMap.toString());
                    download=true;
                    download2=true;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    download=true;
                    download2=true;
                    Toast.makeText(context, "ggl", Toast.LENGTH_SHORT).show();
                }
            });
//            Toast.makeText(context, "Kelar", Toast.LENGTH_SHORT).show();
        }
    }
}
