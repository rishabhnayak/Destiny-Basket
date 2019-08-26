package nmdl.express.ecomm;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;

import de.hdodenhof.circleimageview.CircleImageView;


public class SplashActivity extends AppCompatActivity
{
    CircleImageView image;
    TextView name;
    ImageView sign;
    private static SharedPrefManager mInstance;
    private static Context mCtx;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel("db","db", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager managera =getSystemService(NotificationManager.class);
            managera.createNotificationChannel(notificationChannel);
            FirebaseMessaging.getInstance().subscribeToTopic("db");
        }

        final Intent intent = new Intent(this,log.class);
        final Thread thread = new Thread(){
            public void run(){
                try
                {
                    sleep(2000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn())
                    {
                        startActivity(new Intent(getApplicationContext(), Main.class));
                    }
                    else
                    {
                        startActivity(intent);
                    }
                }
            }
        };
        thread.start();

    }

}