package nmdl.express.ecomm;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.firebase.messaging.FirebaseMessaging;

public class Main extends AppCompatActivity
{
    FrameLayout frameLayout;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            //Toast.makeText(this, "login to hi", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, home.class);
            startActivity(i);
        }
        frameLayout = findViewById(R.id.fl);
        if (findViewById(R.id.fl)!=null) {
            if (savedInstanceState!=null){
                return;
            }else if (getFragmentManager().getBackStackEntryCount() != 0) {
                getFragmentManager().popBackStack();
            }else {
                getFragmentManager().beginTransaction().replace(R.id.fl,
                        new Login()).addToBackStack(null).commit();
            }
        }
        //WifiManager wifiManager = (WifiManager) getSystemService()

        //Login t = new Login();
        Login t = new Login();
        //Bundle args = new Bundle();
        //args.putString("YourKey", "jhg");
        //t.setArguments(args);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //transaction.addToBackStack("first");
        transaction.replace(R.id.fl,t,"first");
        transaction.commit();



//        Register t = new Register();
//        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
//        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.fl, t,"first");
//       // transaction.addToBackStack("second");
//        transaction.commit();

    }
}
