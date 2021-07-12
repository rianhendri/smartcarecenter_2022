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
package com.smartcarecenter.Chat;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.datatransport.BuildConfig;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smartcarecenter.ListChat;
import com.smartcarecenter.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static com.smartcarecenter.ListChat.chatin;
import static com.smartcarecenter.ListChat.mcopylay;
import static com.smartcarecenter.ListChat.mdelcop;
import static com.smartcarecenter.ListChat.name;
import static com.smartcarecenter.ListChat.databaseReference3;
import static com.smartcarecenter.ListChat.sessionnya;
import static com.smartcarecenter.DownloadBroadcastReceiver.key;

import static com.smartcarecenter.DownloadBroadcastReceiver.pathnya;
import static com.smartcarecenter.ListChat.mback;

import static com.smartcarecenter.ListChat.mcopy;
import static com.smartcarecenter.ListChat.mdelet;


//import static com.e.chatforscctest.ListChat.idhcat;


public class Adapterchat
extends RecyclerView.Adapter<Adapterchat.Myviewholder>  {
    StorageReference ref = FirebaseStorage.getInstance().getReference();

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference islandRef=null;
    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();
    public static ArrayList<Itemchat> addFoclistreq;
    public static Integer prog=0;
    Context context;
    ImageView mimgpopup;
    String keynya = "";
    String linkdownload = "";
    Uri uriimg=null;
    Bitmap bitmap2 = null;
    Handler handler = new Handler();
    int pos=0;
    int pro = 0;
    public static int totalqty = 0;
    double subharga = 0;
    double harga = 0.0;
    int qtynya = 1;
    double totalprice=0.0;
    int total1 = 0;
    int total2 = 0;
//    public static String namenya = "";
    public static boolean download = true;
    public static boolean download2 = false;
    public Adapterchat(Context context, ArrayList<Itemchat> addFoclistitem) {
        this.context = context;
        this.addFoclistreq = addFoclistitem;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_chat2,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {

        //setting posisi chat dan visible file
        if (addFoclistreq.get(i).getShowUrl().equals("yes")){
            int pos1 = addFoclistreq.get(i).getPosition()-1;
            Log.d("posi",String.valueOf(addFoclistreq.get(i).getPosition()));
//            keynya1=addFoclistreq.get(pos1).getKey();
            File imagefile2 = new File(addFoclistreq.get(i).getMyuri());
            if (addFoclistreq.get(i).getName().equals(name)){
                /// mulaiupload
                if (addFoclistreq.get(i).getType().equals("video")) {
                    //upload video
                    ref.child("chat"+"/"+sessionnya+"/"+"listchat"+"/"+addFoclistreq.get(i).getKey()+"/"+addFoclistreq.get(i).getMessage()).putFile(Uri.fromFile(imagefile2)).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            String message = e.toString();
//                        loading.dismiss();
                            Log.d("erorupload",message);
//                                loadingBar.dismiss();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                            prog = (int) progress;
                            Log.d("progress", String.valueOf(prog));
                            myviewholder.mbarme.setProgress(prog);

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();
                            Log.d("getdownload",downloadUrl.toString());
                            HashMap hashMap = new HashMap();
                            hashMap.put("url",downloadUrl.toString());
                            hashMap.put("showUrl","done");
                            databaseReference3.child(addFoclistreq.get(i).getKey()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
//                                loading.dismiss();
                                    File mSaveBit; // Your image file
                                    String filePath = imagefile2.getPath();
                                    Bitmap bitmap = null;
                                    if (addFoclistreq.get(i).getType().equals("video")){
                                        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                                        mediaMetadataRetriever.setDataSource(filePath);
                                        bitmap = mediaMetadataRetriever.getFrameAtTime(5000000);
                                    }else {
                                        bitmap = BitmapFactory.decodeFile(filePath);
                                    }
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                                    byte[] data = baos.toByteArray();

                                    UploadTask uploadTask = ref.child("chat"+"/"+sessionnya+"/"+"listchat"+"/"+addFoclistreq.get(i).getKey()+"/"+"thumb"+"/"+addFoclistreq.get(i).getMessage()).putBytes(data);
                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle unsuccessful uploads
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                            // ...
                                            Task<Uri> urlTask2 = taskSnapshot.getStorage().getDownloadUrl();
                                            while (!urlTask2.isSuccessful());
                                            Uri downloadUrl2 = urlTask2.getResult();
                                            Log.d("getdownload2",downloadUrl2.toString());
                                            HashMap hashMap2 = new HashMap();
                                            hashMap2.put("thumb",downloadUrl2.toString());
                                            databaseReference3.child(addFoclistreq.get(i).getKey()).updateChildren(hashMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
//                                loading.dismiss();


                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
//                                loading.dismiss();

                                                }
                                            });
                                            Toast.makeText(context, "File berhasil diupload", Toast.LENGTH_SHORT).show();

                                        }
                                    });



                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
//                                loading.dismiss();
                                    myviewholder.muploadme.setVisibility(View.VISIBLE);
                                    myviewholder.mloadme.setVisibility(View.GONE);
                                }
                            });
                        }
                    });

                }else {

                    String filePath = imagefile2.getPath();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    if (addFoclistreq.get(i).getType().equals("video")){
                        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                        mediaMetadataRetriever.setDataSource(filePath);
                        bitmap2 = mediaMetadataRetriever.getFrameAtTime(5000000);
                    }else {
                        bitmap2 = BitmapFactory.decodeFile(filePath);
                    }
                    bitmap2.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                    byte[] data = baos.toByteArray();
                    ref.child("chat"+"/"+sessionnya+"/"+"listchat"+"/"+addFoclistreq.get(i).getKey()+"/"+addFoclistreq.get(i).getMessage()).putBytes(data).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            String message = e.toString();
//                        loading.dismiss();
                            Log.d("erorupload",message);
//                                loadingBar.dismiss();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                            prog = (int) progress;
                            Log.d("progress", String.valueOf(prog));
                            myviewholder.mbarme.setProgress(prog);

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();
                            Log.d("getdownload",downloadUrl.toString());
                            HashMap hashMap = new HashMap();
                            hashMap.put("url",downloadUrl.toString());
                            hashMap.put("showUrl","done");
                            databaseReference3.child(addFoclistreq.get(i).getKey()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
//                                loading.dismiss();
                                    File mSaveBit; // Your image file
                                    String filePath = imagefile2.getPath();
                                    Bitmap bitmap = null;
                                    if (addFoclistreq.get(i).getType().equals("video")){
                                        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                                        mediaMetadataRetriever.setDataSource(filePath);
                                        bitmap = mediaMetadataRetriever.getFrameAtTime(5000000);
                                    }else {
                                        bitmap = BitmapFactory.decodeFile(filePath);
                                    }
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    bitmap2.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                                    byte[] data = baos.toByteArray();

                                    UploadTask uploadTask = ref.child("chat"+"/"+sessionnya+"/"+"listchat"+"/"+addFoclistreq.get(i).getKey()+"/"+"thumb"+"/"+addFoclistreq.get(i).getMessage()).putBytes(data);
                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle unsuccessful uploads
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                            // ...
                                            Task<Uri> urlTask2 = taskSnapshot.getStorage().getDownloadUrl();
                                            while (!urlTask2.isSuccessful());
                                            Uri downloadUrl2 = urlTask2.getResult();
                                            Log.d("getdownload2",downloadUrl2.toString());
                                            HashMap hashMap2 = new HashMap();
                                            hashMap2.put("thumb",downloadUrl2.toString());
                                            databaseReference3.child(addFoclistreq.get(i).getKey()).updateChildren(hashMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
//                                loading.dismiss();


                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
//                                loading.dismiss();

                                                }
                                            });
                                            Toast.makeText(context, "File berhasil diupload", Toast.LENGTH_SHORT).show();

                                        }
                                    });



                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
//                                loading.dismiss();
                                    myviewholder.muploadme.setVisibility(View.VISIBLE);
                                    myviewholder.mloadme.setVisibility(View.GONE);
                                }
                            });
                        }
                    });
                }

                ////bates upload
                myviewholder.muploadme.setVisibility(View.GONE);
                myviewholder.mdownloadme.setVisibility(View.GONE);
                myviewholder.mloadme.setVisibility(View.VISIBLE);
                myviewholder.mbarme.setProgress(prog);

                myviewholder.mmefilename.setText(addFoclistreq.get(i).getMessage());
                myviewholder.mMyname.setText(addFoclistreq.get(i).getMessage());
                myviewholder.mdateme.setText(addFoclistreq.get(i).getTime());

                myviewholder.mlayfileme.setVisibility(View.VISIBLE);
                myviewholder.mlayfileyoyu.setVisibility(View.VISIBLE);
                myviewholder.mMyname.setVisibility(View.GONE);
                myviewholder.myourname.setVisibility(View.GONE);

                myviewholder.mlayyou.setVisibility(View.GONE);
                myviewholder.mlayme.setVisibility(View.VISIBLE);
            }else {
                myviewholder.mdownloadyou.setVisibility(View.GONE);
                myviewholder.mloadyou.setVisibility(View.GONE);

                myviewholder.myourname.setText("uploading...");
                myviewholder.mdateyou.setText(addFoclistreq.get(i).getTime());
                myviewholder.myufilename.setText("Uploading...");

                myviewholder.mlayfileme.setVisibility(View.GONE);
                myviewholder.mlayfileyoyu.setVisibility(View.VISIBLE);

                myviewholder.mMyname.setVisibility(View.GONE);
                myviewholder.myourname.setVisibility(View.GONE);

                myviewholder.mlayyou.setVisibility(View.VISIBLE);
                myviewholder.mlayme.setVisibility(View.GONE);
            }
        }else if (addFoclistreq.get(i).getShowUrl().equals("done")){
            if (addFoclistreq.get(i).getName().equals(name)){
                myviewholder.muploadme.setVisibility(View.GONE);
                myviewholder.mdownloadme.setVisibility(View.GONE);
                myviewholder.mloadme.setVisibility(View.GONE);

                myviewholder.mmefilename.setText(addFoclistreq.get(i).getMessage());
                myviewholder.mMyname.setText(addFoclistreq.get(i).getMessage());
                myviewholder.mdateme.setText(addFoclistreq.get(i).getTime());

                myviewholder.mlayfileme.setVisibility(View.VISIBLE);
                myviewholder.mlayfileyoyu.setVisibility(View.GONE);
                myviewholder.mMyname.setVisibility(View.GONE);
                myviewholder.myourname.setVisibility(View.GONE);

                myviewholder.mlayyou.setVisibility(View.GONE);
                myviewholder.mlayme.setVisibility(View.VISIBLE);
            }else {
                if (addFoclistreq.get(i).getYoururi().equals("-")){
                    myviewholder.mdownloadyou.setVisibility(View.VISIBLE);
                    myviewholder.mloadyou.setVisibility(View.GONE);

                }else {
                    myviewholder.mloadyou.setVisibility(View.GONE);
                    myviewholder.mdownloadyou.setVisibility(View.GONE);
                }

                myviewholder.myourname.setText(addFoclistreq.get(i).getMessage());
                myviewholder.mdateyou.setText(addFoclistreq.get(i).getTime());
                myviewholder.myufilename.setText(addFoclistreq.get(i).getMessage());

                myviewholder.mlayfileme.setVisibility(View.GONE);
                myviewholder.mlayfileyoyu.setVisibility(View.VISIBLE);
                myviewholder.mMyname.setVisibility(View.GONE);
                myviewholder.myourname.setVisibility(View.GONE);



                myviewholder.mlayyou.setVisibility(View.VISIBLE);
                myviewholder.mlayme.setVisibility(View.GONE);
            }
        }else {
            if (addFoclistreq.get(i).getName().equals(name)){
                myviewholder.mmefilename.setText(addFoclistreq.get(i).getMessage());
                myviewholder.mMyname.setText(addFoclistreq.get(i).getMessage());
                myviewholder.mdateme.setText(addFoclistreq.get(i).getTime());

                myviewholder.mlayfileme.setVisibility(View.VISIBLE);
                myviewholder.mlayfileyoyu.setVisibility(View.GONE);
                myviewholder.mMyname.setVisibility(View.GONE);
                myviewholder.myourname.setVisibility(View.GONE);

                myviewholder.mlayyou.setVisibility(View.GONE);
                myviewholder.mlayme.setVisibility(View.VISIBLE);
            }else {
                myviewholder.myourname.setText(addFoclistreq.get(i).getMessage());
                myviewholder.mdateyou.setText(addFoclistreq.get(i).getTime());
                myviewholder.myufilename.setText(addFoclistreq.get(i).getMessage());

                myviewholder.mlayfileme.setVisibility(View.GONE);
                myviewholder.mlayfileyoyu.setVisibility(View.VISIBLE);
                myviewholder.mMyname.setVisibility(View.GONE);
                myviewholder.myourname.setVisibility(View.GONE);

                myviewholder.mlayyou.setVisibility(View.VISIBLE);
                myviewholder.mlayme.setVisibility(View.GONE);
            }
            myviewholder.mlayfileme.setVisibility(View.GONE);
            myviewholder.mlayfileyoyu.setVisibility(View.GONE);
            myviewholder.mMyname.setVisibility(View.VISIBLE);
            myviewholder.myourname.setVisibility(View.VISIBLE);
        }
        //READ
        if (name.equals(addFoclistreq.get(i).getName())){

        }else {
            int posnya = addFoclistreq.size();
            keynya = addFoclistreq.get(i).getKey();

            HashMap hashMap = new HashMap();
            hashMap.put("read","yes");
            databaseReference2.child("chat").child(sessionnya).child("listchat").child(keynya).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
        //ceknama lvl
        if(name.equals(addFoclistreq.get(i).getName())){

        }else {
            myviewholder.mname1.setText(addFoclistreq.get(i).getName());
            myviewholder.mlvl.setText(addFoclistreq.get(i).getLevel());
        }
        if (addFoclistreq.get(i).getRead().equals("yes")){
            myviewholder.mimg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_doublecheckingwhite1));
        }else {
            myviewholder.mimg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_doublecheckingwhite));
        }
        //cekhapus pesan
        if (addFoclistreq.get(i).getMessage().equals("Anda telah menghapus pesan ini")){
            myviewholder.mMyname.setTypeface( myviewholder.mMyname.getTypeface(), Typeface.ITALIC);
            myviewholder.myourname.setTypeface( myviewholder.mMyname.getTypeface(), Typeface.ITALIC);
            myviewholder.myourname.setText("Pesan ini telah di hapus");
            myviewholder.mimg.setVisibility(View.GONE);
            myviewholder.mMyname.setTextColor(Color.parseColor("#DCDADA"));
            myviewholder.myourname.setTextColor(Color.parseColor("#A6A6A6"));
        }else {
            myviewholder.mMyname.setTextColor(Color.parseColor("#ffffff"));
            myviewholder.myourname.setTextColor(Color.parseColor("#000000"));
            myviewholder.mMyname.setTypeface( myviewholder.mMyname.getTypeface(), Typeface.NORMAL);
            myviewholder.myourname.setTypeface( myviewholder.mMyname.getTypeface(), Typeface.NORMAL);
            myviewholder.mimg.setVisibility(View.VISIBLE);
        }
        //showdate
        String date = new SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(new Date());
        if (date.equals(addFoclistreq.get(i).getDate())){
            myviewholder.mdate.setText("Hari Ini");
            myviewholder.mlaydate.setVisibility(View.VISIBLE);
        }
        else {
            myviewholder.mlaydate.setVisibility(View.VISIBLE);
            myviewholder.mdate.setText(addFoclistreq.get(i).getDate());
        }
        if (addFoclistreq.get(i).getShowDate().equals("no")){
            myviewholder.mlaydate.setVisibility(View.GONE);
        }else {
            myviewholder.mlaydate.setVisibility(View.VISIBLE);

        }

        ///cekuri
        File myuri = new File(addFoclistreq.get(i).getMyuri());
        if (myuri.exists()){
            myviewholder.mopenfile.setText("Open file");
        }else {
            myviewholder.mopenfile.setText("Download file");
        }

        File myuri2 = new File(addFoclistreq.get(i).getYoururi());
        if (myuri2.exists()){
            myviewholder.mopenfile2.setText("Open file");
        }else {
            if (addFoclistreq.get(i).getShowUrl().equals("yes")){
                myviewholder.mopenfile2.setText("uploading...");
            }else {
                myviewholder.mopenfile2.setText("Download file");            }

        }

        if (addFoclistreq.get(i).getMyuri().equals("-")){
            myviewholder.mopenfile.setVisibility(View.GONE);
            myviewholder.mopenfile2.setVisibility(View.GONE);
        }else {
            myviewholder.mopenfile.setVisibility(View.VISIBLE);
            myviewholder.mopenfile2.setVisibility(View.VISIBLE);
            String someFilepath = addFoclistreq.get(i).getMyuri();
            String extension = someFilepath.substring(someFilepath.lastIndexOf(".")).replace(".","");
            if (addFoclistreq.get(i).getName().equals(name)) {
                myviewholder.mextc.setText(extension.toUpperCase());
            }else {
                myviewholder.mextc2.setText(extension.toUpperCase());

            }
            Log.d("uriada","ga ada");
        }
        //click chat untuk chek file
        myviewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = i;

//
                if (addFoclistreq.get(i).getName().equals(name)){

                    if (addFoclistreq.get(i).getMyuri().equals("-")){

                    }else {
                        File myuri = new File(addFoclistreq.get(i).getMyuri());
                        Log.d("youruri",myuri.toString());
                        if (myuri.exists()){
                                Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", myuri);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                String mime = "*/*";
                                MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
                                if (mimeTypeMap.hasExtension(
                                        mimeTypeMap.getFileExtensionFromUrl(uri.toString())))
                                    mime = mimeTypeMap.getMimeTypeFromExtension(
                                            mimeTypeMap.getFileExtensionFromUrl(uri.toString()));
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                intent.setDataAndType(uri,mime);
                                context.startActivity(intent);

                        }else {
                            if (download2){
                                download2=false;
                                Toast.makeText(context, "Downloading", Toast.LENGTH_SHORT).show();
                                linkdownload = addFoclistreq.get(i).getUrl();

                                startDownload();
                                Log.d("uriada","ga ada");
                            }else {

                            }

                        }

                    }
                }else {
                    if (addFoclistreq.get(i).getMyuri().equals("-")){

                    }else {
//                        Toast.makeText(context, "clik2"+name, Toast.LENGTH_SHORT).show();
                        key = addFoclistreq.get(i).getKey();
                        String file = addFoclistreq.get(i).getYoururi();
                        File youruri = new File( addFoclistreq.get(i).getYoururi());
                        Log.d("filepath",file);
                        if (youruri.exists()){
                            // Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();

                            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", youruri);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            String mime = "*/*";
                            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
                            if (mimeTypeMap.hasExtension(
                                    mimeTypeMap.getFileExtensionFromUrl(uri.toString())))
                                mime = mimeTypeMap.getMimeTypeFromExtension(
                                        mimeTypeMap.getFileExtensionFromUrl(uri.toString()));
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            intent.setDataAndType(uri,mime);
                            context.startActivity(intent);

                        }else {
//                        Toast.makeText(context, String.valueOf(download), Toast.LENGTH_SHORT).show();
                            if (download){
                                myviewholder.mloadyou.setVisibility(View.VISIBLE);
                                myviewholder.mdownloadyou.setVisibility(View.GONE);
                                download=false;
                                Toast.makeText(context, "Downloading", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(context, "sas", Toast.LENGTH_SHORT).show();
//                        islandRef = storage.getReference().child("chat"+"/"+sessionnya+"/"+"listchat"+"/"+addFoclistreq.get(i).getKey()+"/"+addFoclistreq.get(i).getMessage()) ;
                                linkdownload = addFoclistreq.get(i).getUrl();
                                if (addFoclistreq.get(i).getYoururi().equals("-")){
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            while (pro<90){
                                                pro +=1;

                                                handler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        myviewholder.mbaryou.setProgress(pro);
                                                    }
                                                });
                                                try {
                                                    Thread.sleep(400);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }).start();

                                    startDownload();
                                }else {

                                }

                            }else {
                            }
                        }
                    }
//


                }
                myviewholder.mlayyou.setBackground(ContextCompat.getDrawable(context, R.drawable.bgwhite_cornerblack));
                myviewholder.mlayme.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_cornerblue));
                myviewholder.mMyname.setTextColor(Color.parseColor("#ffffff"));
                mback.setVisibility(View.VISIBLE);
                mdelcop.setVisibility(View.GONE);
                mcopylay.setVisibility(View.GONE);
            }
        });
        //click chat long
        myviewholder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (chatin){
                    if (addFoclistreq.get(i).getMyuri().equals("-")){

                    }else {
                        File myuri = new File(addFoclistreq.get(i).getMyuri());
                        if (myuri.exists()){

                        }else {
//                        startDownload();
                        }
                    }
                    pos = i;
                    if (name.equals(addFoclistreq.get(i).getName())){
                        mdelcop.setVisibility(View.VISIBLE);
                    }else {
                        mdelcop.setVisibility(View.GONE);
                    }
                    mcopylay.setVisibility(View.VISIBLE);

                    mback.setVisibility(View.GONE);
                    myviewholder.mlayyou.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corneryl));
                    myviewholder.mlayme.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corneryl));
                    myviewholder.mMyname.setTextColor(Color.parseColor("#000000"));
                }else {

                }

//                Toast.makeText(context, "Berhasil", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        //copy and delete chat
        mcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(addFoclistreq.get(pos).getMessage());
                Toast.makeText(context, addFoclistreq.get(pos).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        mdelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        //showicon download upload progress me
//        if (addFoclistreq.get(i).getName().equals(name)){
//            if (addFoclistreq.get(i).getMyuri().equals("-")){
//
//            }else {
//                if (addFoclistreq.get(i).getShowUrl()){
//
//                }
//            }
//        }
//        //showicon download upload progress me
//        else {
//
//        }
// set thumbnail
        if (addFoclistreq.get(i).getName().equals(name)){
            if (addFoclistreq.get(i).getThumb().equals("-")){
                myviewholder.mbgimgme.setVisibility(View.VISIBLE);
                myviewholder.mloadimgme.setVisibility(View.VISIBLE);

                myviewholder.mimgme.setVisibility(View.GONE);
                myviewholder.mplaybtn.setVisibility(View.GONE);



            }else {
                if (addFoclistreq.get(i).getType().equals("image")){
                    myviewholder.mplaybtn.setVisibility(View.GONE);
                    Picasso.with(context).load(addFoclistreq.get(i).getThumb()).into(myviewholder.mimgme, new Callback() {
                        @Override
                        public void onSuccess() {
                            myviewholder.mbgimgme2.setVisibility(View.GONE);
                            myviewholder.mloadimgme2.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });

                }else {
                    Picasso.with(context).load(addFoclistreq.get(i).getThumb()).into(myviewholder.mimgme, new Callback() {
                        @Override
                        public void onSuccess() {
                            myviewholder.mbgimgme2.setVisibility(View.GONE);
                            myviewholder.mloadimgme2.setVisibility(View.GONE);
                            myviewholder.mplaybtn.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onError() {

                        }
                    });

                }
//                Picasso.with(context).load(addFoclistreq.get(i).getThumb()).into(myviewholder.mimgme);

                myviewholder.mimgme.setVisibility(View.VISIBLE);
                myviewholder.mbgimgme.setVisibility(View.GONE);
                myviewholder.mloadimgme.setVisibility(View.GONE);
            }
        }else {
            if (addFoclistreq.get(i).getThumb().equals("-")){
                if (addFoclistreq.get(i).getType().equals("-")){

                }else {
                    myviewholder.mlayyou.setVisibility(View.GONE);
                }
                myviewholder.mbgimgyou.setVisibility(View.VISIBLE);
                myviewholder.mloadimgyou.setVisibility(View.VISIBLE);

                myviewholder.mimgyou.setVisibility(View.GONE);
                myviewholder.mplaybtnyou.setVisibility(View.GONE);
            }else {
                if (addFoclistreq.get(i).getType().equals("image")){
                    myviewholder.mplaybtnyou.setVisibility(View.GONE);
                    Picasso.with(context).load(addFoclistreq.get(i).getThumb()).into(myviewholder.mimgyou, new Callback() {
                        @Override
                        public void onSuccess() {
                            myviewholder.mbgimgyou2.setVisibility(View.GONE);
                            myviewholder.mloadimgyou2.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
                }else {

                    Picasso.with(context).load(addFoclistreq.get(i).getThumb()).into(myviewholder.mimgyou, new Callback() {
                        @Override
                        public void onSuccess() {
                            myviewholder.mplaybtnyou.setVisibility(View.VISIBLE);
                            myviewholder.mbgimgyou2.setVisibility(View.GONE);
                            myviewholder.mloadimgyou2.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
                }



                myviewholder.mimgyou.setVisibility(View.VISIBLE);
                myviewholder.mbgimgyou.setVisibility(View.GONE);
                myviewholder.mloadimgyou.setVisibility(View.GONE);
            }
        }
/// set visible load image
        if (addFoclistreq.get(i).getName().equals(name)) {

            if (addFoclistreq.get(i).getType().equals("-")){
                myviewholder.mbgimgme2.setVisibility(View.GONE);
                myviewholder.mloadimgme2.setVisibility(View.GONE);
                myviewholder.mbgimgme.setVisibility(View.GONE);
                myviewholder.mloadimgme.setVisibility(View.GONE);
                myviewholder.mgarisatasme.setVisibility(View.GONE);

            }
        }else {
            if (addFoclistreq.get(i).getType().equals("-")){
                myviewholder.mbgimgyou2.setVisibility(View.GONE);
                myviewholder.mloadimgyou2.setVisibility(View.GONE);
                myviewholder.mbgimgyou.setVisibility(View.GONE);
                myviewholder.mloadimgyou.setVisibility(View.GONE);
                myviewholder.mgarisatasyou.setVisibility(View.GONE);
            }
        }


    }

    @Override
    public int getItemCount() {
        return 
                addFoclistreq.size();
    }


    public static class Myviewholder extends RecyclerView.ViewHolder{

        TextView mextc2, mlvl,mopenfile2, mopenfile,mextc,mmefilename,myufilename,mMyname, myourname,mname1,mname2,mdate, mcategory, mqty, mpos,munit,msubharga, mharga, mps, mdateyou,mdateme;
        ImageView mimg, mminus,mfileme,mfileyour, mplus,mdelete,mbgimgme,mbgimgyou,mbgimgme2,mbgimgyou2;
        LinearLayout mlayyou, mlayme,mlaydate, mlayfileme, mlayfileyoyu;
        ImageView muploadme,mdownloadme,mdownloadyou,mimgme,mimgyou,mplaybtn,mplaybtnyou;
        ConstraintLayout mloadme,mloadyou;
        View mgarisatasme,mgarisatasyou;
        ProgressBar mbarme,mbaryou,mloadimgme,mloadimgyou,mloadimgme2,mloadimgyou2;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            muploadme = itemView.findViewById(R.id.upload);
            mgarisatasme = itemView.findViewById(R.id.garisatasme);
            mgarisatasyou = itemView.findViewById(R.id.garisatasyou);
            mloadimgme = itemView.findViewById(R.id.loadingimg);
            mloadimgme2 = itemView.findViewById(R.id.loadingimg2);
            mplaybtn = itemView.findViewById(R.id.playbtn);
            mplaybtnyou = itemView.findViewById(R.id.playbtnyou);
            mloadimgyou = itemView.findViewById(R.id.loadingimgyou);
            mloadimgyou2 = itemView.findViewById(R.id.loadingimgyou2);
            mbgimgme = itemView.findViewById(R.id.loadimg);
            mbgimgyou = itemView.findViewById(R.id.loadimgyou);
            mbgimgme2 = itemView.findViewById(R.id.loadimg2);
            mbgimgyou2 = itemView.findViewById(R.id.loadimgyou2);
            mdownloadme = itemView.findViewById(R.id.download);
            mimgme = itemView.findViewById(R.id.imgme);
            mimgyou = itemView.findViewById(R.id.imgyou);
            mdownloadyou = itemView.findViewById(R.id.downloadyou);
            mloadme = itemView.findViewById(R.id.progressme);
            mloadyou = itemView.findViewById(R.id.progressyou);
            mbarme = itemView.findViewById(R.id.progress_bar);
            mbaryou = itemView.findViewById(R.id.progress_baryou);

            mMyname = itemView.findViewById(R.id.me);
            myourname = itemView.findViewById(R.id.your);
            mlayyou = itemView.findViewById(R.id.layyou);
            mlayme = itemView.findViewById(R.id.layme);
            mname1 = itemView.findViewById(R.id.name1);
            mlvl = itemView.findViewById(R.id.lvl);
//            mname2 = itemView.findViewById(R.id.name2);
            mlaydate = itemView.findViewById(R.id.datelay);
            mdate = itemView.findViewById(R.id.date);
            mdateyou = itemView.findViewById(R.id.dateyou);
            mdateme = itemView.findViewById(R.id.dateme);
            mimg = itemView.findViewById(R.id.read);
            mfileme = itemView.findViewById(R.id.fileme);
            mfileyour = itemView.findViewById(R.id.fileyour);
            mlayfileme = itemView.findViewById(R.id.layfileme);
            mlayfileyoyu = itemView.findViewById(R.id.layfileyou);
            mmefilename = itemView.findViewById(R.id.mefile);
            myufilename = itemView.findViewById(R.id.yourfiletext);
            mextc = itemView.findViewById(R.id.extc);
            mextc2= itemView.findViewById(R.id.extc2);
            mopenfile = itemView.findViewById(R.id.openile);
            mopenfile2 = itemView.findViewById(R.id.openile2);
        }
    }
    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title dialog
        alertDialogBuilder.setTitle("");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Hapus Pesan?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        keynya = addFoclistreq.get(pos).getKey();
                        HashMap hashMap = new HashMap();
                        hashMap.put("message","Anda telah menghapus pesan ini");
                        databaseReference2.child("chat").child(sessionnya).child("listchat").child(keynya).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
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
    public void startDownload() {

//        File direct2 = new File(
//                "/SccEngineer"+ "/Media");
        File direct = new File("SccEngineer","Media");
        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager mgr = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(linkdownload);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, direct+"/"+downloadUri.getLastPathSegment());
        mgr.enqueue(request);

        String external = Environment.getExternalStorageDirectory().toString()+"/";
        Log.d("pathsimpen","di sini: "+ external+DIRECTORY_DOWNLOADS+"/"+direct+"/"+downloadUri.getLastPathSegment());
        pathnya=external+DIRECTORY_DOWNLOADS+"/"+direct+"/"+downloadUri.getLastPathSegment();

    }
//    public void startDownload() {
//        Toast.makeText(context, "Download", Toast.LENGTH_SHORT).show();
//        File rootPath = new File(Environment.getExternalStorageDirectory() +
//                File.separator + "SccEng");
//        if(!rootPath.exists()) {
//            rootPath.mkdirs();
//        }
//        final File localFile = new File(rootPath,"Media");
////        File localFile = null;
////        try {
////            localFile = File.createTempFile("images", "jpg");
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//////
////
////        File finalLocalFile = localFile;
//        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                Log.d("firebase2 ",";local tem file created  created " + localFile.toString());
//                //  updateDb(timestamp,localFile.toString(),position);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Log.e("firebase ",";local tem file not created  created " +exception.toString());
//            }
//        });
//    }

}

