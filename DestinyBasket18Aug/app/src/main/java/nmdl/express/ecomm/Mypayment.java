package nmdl.express.ecomm;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import nmdl.express.ecomm.Adapter.MyPaymentAdapter;
import nmdl.express.ecomm.ApiUrl.ApiInterface;
import nmdl.express.ecomm.response.MyPaymentResponse;
import nmdl.express.ecomm.response.WalletResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static nmdl.express.ecomm.ApiUrl.ApiUrl.BASE_URL;

public class Mypayment extends AppCompatActivity {
    TextView textView;
    String u_id;
    RecyclerView recyclerView;
    MyPaymentAdapter myPaymentAdapter;
    List<MyPaymentResponse> myPaymentResponses;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.mypayment);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        textView = findViewById(R.id.mpw);
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
        recyclerView=findViewById(R.id.myprecy);

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
               // Toast.makeText(Mypayment.this, ""+response.body().getAmount(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<WalletResponse> call, Throwable t) {
               // Toast.makeText(Mypayment.this, "nhi", Toast.LENGTH_SHORT).show();
            }
        });
        //retrofit for wallet ends
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        //layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        myPaymentAdapter = new MyPaymentAdapter(getApplicationContext(),myPaymentResponses);
        recyclerView.setAdapter(myPaymentAdapter);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiService = retrofit.create(ApiInterface.class);
        Call<List<MyPaymentResponse>> cal = apiService.mypayment(u_id);
        cal.enqueue(new Callback<List<MyPaymentResponse>>() {
            @Override
            public void onResponse(Call<List<MyPaymentResponse>> call, Response<List<MyPaymentResponse>> response) {
                myPaymentResponses=response.body();
                myPaymentAdapter.setPaymentList(myPaymentResponses);
            }

            @Override
            public void onFailure(Call<List<MyPaymentResponse>> call, Throwable t) {

            }
        });


    }
}
