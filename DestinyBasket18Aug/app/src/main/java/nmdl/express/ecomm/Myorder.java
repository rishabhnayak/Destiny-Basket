package nmdl.express.ecomm;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import java.util.List;

import nmdl.express.ecomm.Adapter.MyOrderAdapter;
import nmdl.express.ecomm.ApiUrl.ApiInterface;
import nmdl.express.ecomm.ApiUrl.ApiUrl;
import nmdl.express.ecomm.response.MyOrderResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Myorder extends AppCompatActivity {
    RecyclerView recyclerView;
    MyOrderAdapter myOrderAdapter;
    List<MyOrderResponse> myOrderResponses;
    String u_id;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_my_order);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerView=findViewById(R.id.reco);
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
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        //layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        myOrderAdapter = new MyOrderAdapter(getApplicationContext(),myOrderResponses);
        recyclerView.setAdapter(myOrderAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiService = retrofit.create(ApiInterface.class);
        Call<List<MyOrderResponse>> call = apiService.myorder(u_id);
        call.enqueue(new Callback<List<MyOrderResponse>>() {
            @Override
            public void onResponse(Call<List<MyOrderResponse>> call, Response<List<MyOrderResponse>> response) {
                myOrderResponses=response.body();
                myOrderAdapter.setMyorderList(myOrderResponses);
            }

            @Override
            public void onFailure(Call<List<MyOrderResponse>> call, Throwable t) {

            }
        });

    }
}
