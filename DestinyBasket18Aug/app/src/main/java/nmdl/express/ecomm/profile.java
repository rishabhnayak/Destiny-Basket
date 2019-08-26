package nmdl.express.ecomm;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import nmdl.express.ecomm.ApiUrl.ApiInterface;
import nmdl.express.ecomm.response.AdressResponse;
import nmdl.express.ecomm.response.WalletResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static nmdl.express.ecomm.ApiUrl.ApiUrl.BASE_URL;

public class profile extends AppCompatActivity {
TextView textView,tname,temail,pphone,ch;
String u_id,name,adress,email,phone;
EditText tadress;
Toolbar toolbar;
ImageView aadr;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        textView=findViewById(R.id.pmoneyy);
        tname=findViewById(R.id.pnam);
        tadress=findViewById(R.id.padress);
        temail=findViewById(R.id.pemail);
        ch=findViewById(R.id.ch);
        aadr=findViewById(R.id.addr);
        pphone=findViewById(R.id.phone);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Destiny Basket");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), home.class);
                startActivity(i);
            }
        });

        u_id=SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
        email=SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
        adress=SharedPrefManager.getInstance(getApplicationContext()).getUser().getAdress();
        name=SharedPrefManager.getInstance(getApplicationContext()).getUser().getName();
        phone=SharedPrefManager.getInstance(getApplicationContext()).getUser().getMobile();
        temail.setText(email);
        tname.setText(name);

        pphone.setText(phone);
        if(adress.equalsIgnoreCase("")){
            tadress.setText("No Saved place");
        }else{
            tadress.setText(adress);
        }



        //retrofit for wallet
        u_id=SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
        Retrofit retrof = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiServi = retrof.create(ApiInterface.class);
        Call<WalletResponse> call = apiServi.wallet(u_id);
        call.enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
                textView.setText(response.body().getAmount());
                Log.d("kjhjhjj",""+response.body().getAmount());
               // Toast.makeText(profile.this, ""+response.body().getAmount(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<WalletResponse> call, Throwable t) {
               // Toast.makeText(profile.this, "nhi", Toast.LENGTH_SHORT).show();
            }
        });
        //retrofit for wallet ends
        aadr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(ch.getText().toString().equalsIgnoreCase("edit")) {
                    aadr.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_black_24dp));
                    tadress.setEnabled(true);
                    ch.setText("save");

                }else{
                    adress=tadress.getText().toString();
                    Retrofit retrof = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ApiInterface apiServi = retrof.create(ApiInterface.class);
                    Call<AdressResponse> calll = apiServi.adress(u_id,adress);
                    calll.enqueue(new Callback<AdressResponse>() {
                        @Override
                        public void onResponse(Call<AdressResponse> call, Response<AdressResponse> response) {
                           // Toast.makeText(profile.this, "savae vala", Toast.LENGTH_SHORT).show();
                            tadress.setEnabled(false);
                            aadr.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_black_24dp));
                            ch.setText("edit");
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(name,phone,email,u_id,adress);
                        }

                        @Override
                        public void onFailure(Call<AdressResponse> call, Throwable t) {

                        }
                    });


                }

            }
        });

    }

}
