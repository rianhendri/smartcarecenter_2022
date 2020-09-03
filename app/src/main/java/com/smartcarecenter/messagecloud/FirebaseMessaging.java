package com.smartcarecenter.messagecloud;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.smartcarecenter.Dashboard;
import com.smartcarecenter.DetailsFormActivity;
import com.smartcarecenter.DetailsNotification;
import com.smartcarecenter.Notification;
import com.smartcarecenter.R;
import com.smartcarecenter.SpalshScreen;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FirebaseMessaging extends FirebaseMessagingService {
    private static final String TAG = "FirebaseMessaging";
//    RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_small);
//    RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.notification_large);
    String getid="";
    Bitmap bitmap;
    String imageuri;
    String click_action;
    String message;
    String title;
    String data;
    public FirebaseMessaging(){
        super();
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        if (isAppIsInBackground(getApplicationContext())){
            if (remoteMessage.getData().size()>0){
                data = remoteMessage.getData().get("id");



            }
            if (remoteMessage.getNotification()!=null){
                title = remoteMessage.getNotification().getTitle();

                message = remoteMessage.getNotification().getBody();
                click_action = remoteMessage.getNotification().getClickAction();
                imageuri = remoteMessage.getData().get("image");
                if (imageuri!=null){

                }
                bitmap = getBitmapfromUrl(imageuri);

                Log.d(TAG,"Title:"+title);
                Log.d(TAG,"Message:"+message);
                Log.d(TAG,"ClickAction:"+click_action);


            }
            sendNotification(title,message,click_action,bitmap,data);
            /**
             * Called if InstanceID token is updated. This may occur if the security of
             * the previous token had been compromised. Note that this is called when the InstanceID token
             * is initially generated so this is where you would retrieve the token.
             */
        }else {

        }

    }
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
    }


    private void sendNotification(String title, String message, String click_action, Bitmap bitmap, String data) {
        Intent intent=null;

//        if (click_action.equals("Notification")) {
//            intent = new Intent(this, Notification.class);
//            intent.putExtra("id", data);
////            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            this.startActivity(intent);
//            this.onDestroy();
//        } else if (click_action.equals("FormRequest")) {
//            intent = new Intent(this, DetailsFormActivity.class);
//            intent.putExtra("id", data);
//            intent.setAction(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_LAUNCHER);
////            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            this.startActivity(intent);
//            this.onDestroy();
////            Toast.makeText(this,data, Toast.LENGTH_SHORT).show();
//            Log.d(TAG,"Datanya:"+data);
//        } else {
//            intent = new Intent((Context)this, Dashboard.class);
//            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        }
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(this,"M_CH_ID")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setLargeIcon(bitmap)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(9,notificationbuilder.build());
}
    public Bitmap getBitmapfromUrl(String imageUrl){
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap= BitmapFactory.decodeStream(input);
            return bitmap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
}
    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        }
        else
        {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }
}
