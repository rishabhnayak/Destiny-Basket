package nmdl.express.ecomm;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Rishabh on 19-08-2019.
 */

public class MyPushNotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNoti(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }

    public void showNoti(String title,String message){
        Intent intent=new Intent(getApplicationContext(),SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 999,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"db")
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setContentText(message);
        NotificationManagerCompat managerCompat= NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());
    }
}
