package nmdl.express.ecomm;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.card.MaterialCardView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import nmdl.express.ecomm.ApiUrl.ApiInterface;
import nmdl.express.ecomm.response.WalletResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static nmdl.express.ecomm.ApiUrl.ApiUrl.BASE_URL;

public class wallet extends AppCompatActivity {
MaterialCardView materialCardView;
TextView textView;
ImageView imageView;
LinearLayout viewwallet;
Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wallet);
        materialCardView = findViewById(R.id.wcard);
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

        textView=findViewById(R.id.wmoney);
        viewwallet=findViewById(R.id.view_wallet);
        viewwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Mypayment.class));
            }
        });
        String u_id=SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();

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
               // Toast.makeText(wallet.this, ""+response.body().getAmount(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<WalletResponse> call, Throwable t) {

            }
        });
    }
}
