package nmdl.express.ecomm;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.javiersantos.appupdater.AppUpdater;

import java.util.List;

import nmdl.express.ecomm.ApiUrl.ApiInterface;
import nmdl.express.ecomm.ApiUrl.ApiUrl;
import nmdl.express.ecomm.response.CartNumResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
     int i;
    android.support.v7.widget.Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    CoordinatorLayout relativeLayout;
    FrameLayout frameLayout;
    ImageView cart;
    TextView t1;
    String u_id;
    RelativeLayout r1;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.navigation_home);
        AppUpdater appUpdater = new AppUpdater(this);
        appUpdater.start();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        t1 = findViewById(R.id.cartnum);
        relativeLayout = findViewById(R.id.main);
        frameLayout = findViewById(R.id.homefl);
        cart = findViewById(R.id.tcart);
        String s = SharedPrefManager.getInstance(getApplicationContext()).getUser().getName();
        View hView =  navigationView.getHeaderView(0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        relativeLayout.setOnClickListener(null);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        //drawerLayout.setDrawerListener(toggle);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Menu menu = navigationView.getMenu();
        MenuItem nav_login = menu.findItem(R.id.logout);
        if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
            nav_login.setVisible(true);
        }else{
            nav_login.setVisible(false);
        }
        shopHome n = new shopHome();
        // shophome fragment is used for shop
        //cartnum
        u_id=SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiService = retrofit.create(ApiInterface.class);
        Call<CartNumResponse> call = apiService.cartnum(u_id);
        call.enqueue(new Callback<CartNumResponse>() {
            @Override
            public void onResponse(Call<CartNumResponse> call, Response<CartNumResponse> response) {
                t1.setText(""+response.body().getNum());
            }

            @Override
            public void onFailure(Call<CartNumResponse> call, Throwable t) {

            }
        });

        //

        Fragment manage = getFragmentManager().findFragmentByTag("shop_home_fragment");

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(home.this, "cart", Toast.LENGTH_SHORT).show();
if(!SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
    Intent i = new Intent(view.getContext(), log.class);
    startActivity(i);
}else {
    Cart t = new Cart();
    FragmentManager manager = getFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();
    transaction.replace(R.id.homefl, t);
    transaction.addToBackStack(null);
    transaction.commit();
}
            }
        });



        if(n.isVisible()){
           // Toast.makeText(this, "hoi re", Toast.LENGTH_SHORT).show();
        }
                CatHome t = new CatHome();
        Bundle args = new Bundle();
        args.putString("YourKey", ""+hView);
        t.setArguments(args);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.homefl, t);
        transaction.addToBackStack(null);
        transaction.commit();


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = home.this.getFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        //Toast.makeText(this, ""+count, Toast.LENGTH_SHORT).show();
        if (count > 2) {
            super.onBackPressed();
        } else if(count == 2) {
            Intent i = new Intent(getApplicationContext(), home.class);
            startActivity(i);
            //getFragmentManager().popBackStack();
        }else if(count == 1){
            Intent i = new Intent(getApplicationContext(), home.class);
            startActivity(i);
            finish();
            moveTaskToBack(true);
            System.exit(1);

        }
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            drawer.openDrawer(GravityCompat.START);
//            //super.onBackPressed();
//        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       // Toast.makeText(this, "menu", Toast.LENGTH_SHORT).show();
        int id = item.getItemId();

        if (id == R.id.logout) {
           // Toast.makeText(this, "log", Toast.LENGTH_SHORT).show();
            SharedPrefManager.getInstance(getApplicationContext()).logout();
            Intent i = new Intent(getApplicationContext(), Main.class);
            startActivity(i);
        }else if(id == R.id.wallet){
            if(!SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
               Intent intent = new Intent(getApplicationContext(),log.class);
               startActivity(intent);
            }else{
                Intent intent = new Intent(getApplicationContext(),wallet.class);
                startActivity(intent);
            }
        }else if(id == R.id.cart){
            if(!SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
                Intent intent = new Intent(getApplicationContext(),log.class);
                startActivity(intent);
            }else{
                Cart t = new Cart();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.homefl, t);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }else if(id == R.id.payment){
            if(!SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
                Intent intent = new Intent(getApplicationContext(),log.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(getApplicationContext(),Mypayment.class);
                startActivity(intent);
            }
        }else if(id == R.id.profile){
            if(!SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
                Intent intent = new Intent(getApplicationContext(),log.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(getApplicationContext(),profile.class);
                startActivity(intent);
            }
        }else if(id == R.id.order){
            if(!SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
                Intent intent = new Intent(getApplicationContext(),log.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(getApplicationContext(),Myorder.class);
                startActivity(intent);
            }
        }else if(id == R.id.home){
            if(!SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
                Intent intent = new Intent(getApplicationContext(),log.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(getApplicationContext(),home.class);
                startActivity(intent);
            }
        }
        else if(id == R.id.shareearn)
        {
            if(!SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn())
                {
                    Intent intent = new Intent(getApplicationContext(),log.class);
                    startActivity(intent);
                }
            else
                {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/*");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Try this " + getResources().getString(R.string.app_name) + " App: https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                    startActivity(Intent.createChooser(shareIntent, "Share Using"));

                }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }
}
