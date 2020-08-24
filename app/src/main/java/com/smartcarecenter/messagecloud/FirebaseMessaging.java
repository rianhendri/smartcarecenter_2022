package com.smartcarecenter.messagecloud;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.smartcarecenter.Dashboard;
import com.smartcarecenter.DetailsFormActivity;
import com.smartcarecenter.Notification;
import com.smartcarecenter.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FirebaseMessaging extends FirebaseMessagingService {
    private static final String TAG = "FirebaseMessaging";
//    RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_small);
//    RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.notification_large);
    String getid="";


    public FirebaseMessaging(){
        super();
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        if (remoteMessage.getData().size()>0){
            String data = remoteMessage.getData().get("id");
            getid = data;

        }
        if (remoteMessage.getNotification()!=null){
            String title = remoteMessage.getNotification().getTitle();
            getid=title;
            String message = remoteMessage.getNotification().getBody();
            String click_action = remoteMessage.getNotification().getClickAction();
            String imageuri = remoteMessage.getData().get("image");
            if (imageuri!=null){

            }
            Bitmap bitmap = getBitmapfromUrl(imageuri);

            Log.d(TAG,"Title:"+title);
            Log.d(TAG,"Message:"+message);
            Log.d(TAG,"ClickAction:"+click_action);

            sendNotification(title,message,click_action,bitmap);
        }

        /**
         * Called if InstanceID token is updated. This may occur if the security of
         * the previous token had been compromised. Note that this is called when the InstanceID token
         * is initially generated so this is where you would retrieve the token.
         */

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


    private void sendNotification(String title, String message, String click_action, Bitmap bitmap) {
        Intent intent;
        if (click_action.equals("Notification")) {
            intent = new Intent(this, Notification.class);
            intent.putExtra("id", getid);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
            this.onDestroy();
        } else if (click_action.equals("FormRequest")) {
            intent = new Intent(this, DetailsFormActivity.class);
            intent.putExtra("id", getid);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            this.startActivity(intent);

        } else {
            intent = new Intent((Context)this, Dashboard.class);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setLargeIcon(bitmap)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationbuilder.build());
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

}
