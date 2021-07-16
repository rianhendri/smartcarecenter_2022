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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.JsonObject;
import com.smartcarecenter.Chat.Adapterchat;
import com.smartcarecenter.Chat.Itemchat;
import com.smartcarecenter.Chat.Itemchat2;
import com.smartcarecenter.SendNotificationPack.APIService;
import com.smartcarecenter.apihelper.ServiceGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.smartcarecenter.Chat.Adapterchat.addFoclistreq;
import static com.smartcarecenter.apihelper.ServiceGenerator.fcmbase;
import static com.smartcarecenter.messagecloud.check.tokennya2;

public class ListChat extends AppCompatActivity {
    RecyclerView recyclerView;
    Itemchat2 itemchat2 ;
    ArrayList<Itemchat> itemchat;
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
    TextView mfrnya;
    String enginername = "";
    String fr="";
    String tokennya = "";
    int xhori = 0;
    int yverti = 0;
    String scrollnya = "-";
    private APIService apiService;
    int PERMISSION_CODE = 100;

    String mimeType="";
    String mimeType2="-";
    int tokenpos=0;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chat);
        mfrnya = findViewById(R.id.frnya);
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

        Bundle bundle2 = getIntent().getExtras();
        if (bundle2 != null) {
            sessionnya = bundle2.getString("sessionnya");
            name = bundle2.getString("name");
            noreq =  bundle2.getString("id");
            username = bundle2.getString("user");
            fr = bundle2.getString("frnya");
            xhori=bundle2.getInt("xhori");
            yverti=bundle2.getInt("yverti");
            scrollnya =   bundle2.getString("scrolbawah");
            chatin = bundle2.getBoolean("chat");
            enginername = bundle2.getString("engname");
            tokennya = bundle2.getString("tokennya");
//            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
//            mfrnya.setText(noreq+" (Engineer: "+enginername+")");
            mfrnya.setText(noreq);
        }
//        Log.d("tokenlist", String.valueOf(tokennya2.size())+"/"+tokennya2.get(0));
        getShowid();
        if (chatin){
            mlayketk.setVisibility(View.VISIBLE);
        }else {
            mlayketk.setVisibility(GONE);
        }
        linearLayoutManager = new LinearLayoutManager(ListChat.this, LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setHasFixedSize(true);
        itemchat = new ArrayList<Itemchat>();
        itemchat2= new Itemchat2();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("chat").child(sessionnya).child("listchat");
        databaseReference3= FirebaseDatabase.getInstance().getReference().child("chat").child(sessionnya).child("listchat");


        loadchat();

        msend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message=sendtext.getText().toString();
                showurl="-";
                myuri = "-";
                if (sendtext.length()==0){

                }else {
                    mback.setVisibility(View.VISIBLE);
                    mdelcop.setVisibility(View.GONE);
                    String date = new SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(new Date());
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
    public void sendDb(){
//        int posinya = 0;
//        if ((addFoclistreq!=null)){
//            posinya = 0;
//        }else{
//            posinya = addFoclistreq.size()+1;
//        }
        String date = new SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(new Date());
        String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
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

                sendnotifchat();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        mimeType2 = "-";
    }
    public void loadchat(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemchat.clear();
                if (dataSnapshot.exists()){
                    for(DataSnapshot ds: dataSnapshot.getChildren())
                    {
                        Itemchat fetchDatalist=ds.getValue(Itemchat.class);
                        fetchDatalist.setKey(ds.getKey());
                        itemchat.add(fetchDatalist);
                    }

                    adapterchat=new Adapterchat(ListChat.this, itemchat);
                    recyclerView.setAdapter(adapterchat);
                    recyclerView.scrollToPosition(adapterchat.getItemCount()-1);
//                recyclerView.scrollToPosition(adapterchat.getItemCount());
                }

//                Log.d("posi",String.valueOf(recyclerView.getAdapter().getItemCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

                        if (itemchat.toString().equals("[]")){
                            Log.e("listkosong",itemchat.toString());


                        }else {
//                            Log.e("listkosong",itemchat.toString());
                            position = addFoclistreq.size()+1;


                        }
//                        loading = ProgressDialog.show(ListChat.this, "", "Uploading...", true);
                        // jika tombol diklik, maka akan menutup activity ini
                        showurl="yes";
                        message = imagefile.getName();
                        myuri = pdfPath;
                        String date = new SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(new Date());
                        String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
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
        Intent mediaChooser = new Intent(Intent.ACTION_PICK);
//comma-separated MIME types
        mediaChooser.setType("video/*, image/*");
        startActivityForResult(mediaChooser, REQUEST_IMAGE_GALLERY);
//        Intent imageIntentGallery = new Intent(Intent.ACTION_PICK,
//                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(imageIntentGallery, REQUEST_IMAGE_GALLERY);

        photo_location=mediaChooser.getData();

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
        Intent back = new Intent(ListChat.this,DetailsFormActivity.class);
        back.putExtra("name",name);
        back.putExtra("id",fr);
        back.putExtra("user",username);
        back.putExtra("xhori", xhori);
        back.putExtra("yverti", yverti);
        back.putExtra("scrolbawah","sadadasd");
        startActivity(back);
        finish();
    }
    public void sendnotifchat(){
//        loading = ProgressDialog.show(DetailsFormActivity.this, "", getString(R.string.title_loading), true);
            JsonObject dataid = new JsonObject();
            dataid.addProperty("id", noreq);

            JsonObject notifikasidata = new JsonObject();
            notifikasidata.addProperty("title", noreq + "-" + name);
            notifikasidata.addProperty("body", message);
            notifikasidata.addProperty("click_action", "ListChat");

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("to", tokennya2.get(tokenpos));
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
                    if (tokennya2.size()-1==tokenpos){
                        tokenpos =0;
                    }else {
                        tokenpos +=1;
                        sendnotifchat();
                    }
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
}