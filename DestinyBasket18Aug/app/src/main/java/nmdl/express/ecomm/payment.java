package nmdl.express.ecomm;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import nmdl.express.ecomm.ApiUrl.ApiInterface;
import nmdl.express.ecomm.ApiUrl.ApiUrl;
import nmdl.express.ecomm.response.OrderResponse;
import nmdl.express.ecomm.response.WalletResponse;
import cn.pedant.SweetAlert.SweetAlertDialog;
import instamojo.library.InstamojoPay;
import instamojo.library.InstapayListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static nmdl.express.ecomm.ApiUrl.ApiUrl.BASE_URL;

public class payment extends AppCompatActivity implements PaytmPaymentTransactionCallback {
    android.support.v7.widget.Toolbar toolbar;
    CheckBox cash,wallet,online;
    int a,b,c;
    CardView cardView;
    TextView textView,pt;
    EditText pe;
    String mode,u_id,place,txn,addr,sub;
    TextView textView1;
    static String email,name,phone,title,total;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_payment);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toolbar = findViewById(R.id.toolbar);
        cash=findViewById(R.id.cash);
        textView1=findViewById(R.id.wmoneyy);
        wallet=findViewById(R.id.wallet);
        pt=findViewById(R.id.pt);
        pe=findViewById(R.id.pe);
        pe.setEnabled(false);
        online=findViewById(R.id.online);
        textView=findViewById(R.id.payt);
        cardView=findViewById(R.id.cardp);
        toolbar.setTitle("Destiny Basket");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), home.class);
                startActivity(i);
            }
        });
        Intent intent = getIntent();
        name=SharedPrefManager.getInstance(getApplicationContext()).getUser().getName();
        phone=SharedPrefManager.getInstance(getApplicationContext()).getUser().getMobile();
        email=SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
        u_id=SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
        addr=SharedPrefManager.getInstance(getApplicationContext()).getUser().getAdress();
        if(!addr.equalsIgnoreCase("")){
            pe.setText(addr);
        }

        //place = SharedPrefManager.getInstance(getApplicationContext()).getCity();
        total=intent.getStringExtra("total");
        sub=intent.getStringExtra("sub");
        pt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pt.getText().toString().equalsIgnoreCase("change")){
                    pe.setText("");
                    //Toast.makeText(payment.this, "click", Toast.LENGTH_SHORT).show();
                    pe.setEnabled(true);
                    pt.setText("SAVE");
                }else{
                    pe.setEnabled(false);
                    pt.setText("change");
                }

            }
        });
        //total="4";
        title="DestinyBasketPayment";
        Retrofit retrof = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiServi = retrof.create(ApiInterface.class);
        Call<WalletResponse> call = apiServi.wallet(u_id);
        call.enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
                textView1.setText(response.body().getAmount());
                Log.d("getamount",""+response.body().getAmount());
                //Toast.makeText(payment.this, ""+response.body().getAmount(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<WalletResponse> call, Throwable t) {
                //Toast.makeText(payment.this, "nhi", Toast.LENGTH_SHORT).show();
            }
        });
        textView.setText("Rs "+total);

        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cash.isChecked()){

                    cash.setChecked(false);
                }
                if(online.isChecked()){
                    //Toast.makeText(payment.this, "Online", Toast.LENGTH_SHORT).show();
                }
            }

        });
        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cash.isChecked()){
                    //Toast.makeText(view.getContext(), "cash"+total, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(this, "cash", Toast.LENGTH_SHORT).show();
                    wallet.setChecked(false);
                    online.setChecked(false);
                    // Toast.makeText(payment.this, "cash", Toast.LENGTH_SHORT).show();
                }
            }
        });
        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wallet.isChecked()){
                    a = Integer.parseInt(textView1.getText().toString());
                    b = Integer.parseInt(total);
                    if (a < b) {
                        c = b - a;
                        Toast.makeText(payment.this, "Insufficient money!! select online payment also", Toast.LENGTH_SHORT).show();
                        textView.setText("" + c);
                        total = String.valueOf(c);
                    }
                    cash.setChecked(false);
                }
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pe.getText().toString().equalsIgnoreCase("Enter your Address")) {

//                    new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.WARNING_TYPE)
//                            .setTitleText("Error")
//                            .setContentText("Please add delivery adress!")
//                            .show();
                    Toast.makeText(payment.this, "Please add delivery adress!", Toast.LENGTH_SHORT).show();
                }else if(pe.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(payment.this, "Please add delivery adress!", Toast.LENGTH_SHORT).show();
                }
                else {

                    place = pe.getText().toString();
                    if (online.isChecked()) {
                        Intent intent = getIntent();
                        //Toast.makeText(payment.this, "Online"+total, Toast.LENGTH_SHORT).show();
                        Log.d("Email:", email);
                        Log.d("Phone:", phone);
                        Log.d("Total:", total);
                        Log.d("Name:", name);
                        Log.d("Title:", title);
                        //callInstamojoPay(email,phone,total,title,name);
                        txn = "online";
                        // paytm integration
                        generateCheckSum();

                    } else if (cash.isChecked()) {
                        mode = "cash";
                        txn = "cash";
                        Retrofit retrof = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        ApiInterface apiServi = retrof.create(ApiInterface.class);
                        Call<OrderResponse> call = apiServi.order(u_id, mode, place, txn,total,sub);
                        call.enqueue(new Callback<OrderResponse>() {
                            @Override
                            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                                //Toast.makeText(payment.this, "Ordered Successfully" + mode, Toast.LENGTH_SHORT).show();
                                new SweetAlertDialog(payment.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("ThanK You!")
                                        .setContentText("Order has been placed!")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                startActivity(new Intent(getApplicationContext(), Main.class));
                                            }
                                        })
                                        .show();

                            }

                            @Override
                            public void onFailure(Call<OrderResponse> call, Throwable t) {

                            }
                        });
                    } else if (wallet.isChecked()) {
                        if (a < b) {
                            textView.setText("" + c);
                            total = String.valueOf(c);
                            if (online.isChecked()) {
                                txn = "online and wallet";
                                generateCheckSum();
                            }
                        } else {
                            mode = "wallet";
                            txn = "wallet";
                            //Toast.makeText(payment.this, "online nhi hi", Toast.LENGTH_SHORT).show();
                            Retrofit retrof = new Retrofit.Builder()
                                    .baseUrl(BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            ApiInterface apiServi = retrof.create(ApiInterface.class);
                            Call<OrderResponse> call = apiServi.order(u_id, mode, place, txn,total,sub);
                            call.enqueue(new Callback<OrderResponse>() {
                                @Override
                                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                                    //Toast.makeText(payment.this, "Ordered Successfully" + mode, Toast.LENGTH_SHORT).show();

                                    //Toast.makeText(payment.this, "Ordered Successfully" + mode, Toast.LENGTH_SHORT).show();
                                    new SweetAlertDialog(payment.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("ThanK You!")
                                            .setContentText("Order has been placed!")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    startActivity(new Intent(getApplicationContext(), Main.class));
                                                }
                                            })
                                            .show();



                                }

                                @Override
                                public void onFailure(Call<OrderResponse> call, Throwable t) {

                                }
                            });
                        }
                    }

                    //retrofit ends
                }
            }

        });


    }

    private void generateCheckSum()
    {

        //getting the tax amount first.
        String txnAmount = total;

        // Toast.makeText(this, "Amount:"+total, Toast.LENGTH_SHORT).show();
        //creating a retrofit object.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL_PAYTM)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //creating the retrofit api service
        ApiInterface apiService = retrofit.create(ApiInterface.class);

        //creating paytm object
        //containing all the values required
        final Paytm paytm = new Paytm(
                Constants.M_ID,
                Constants.CHANNEL_ID,
                txnAmount,
                Constants.WEBSITE,
                Constants.CALLBACK_URL,
                Constants.INDUSTRY_TYPE_ID
        );

        //creating a call object from the apiService
        Call<Checksum> call = apiService.getChecksum(
                paytm.getmId(),
                paytm.getOrderId(),
                paytm.getCustId(),
                paytm.getChannelId(),
                paytm.getTxnAmount(),
                paytm.getWebsite(),
                paytm.getCallBackUrl(),
                paytm.getIndustryTypeId()
        );

        //making the call to generate checksum
        call.enqueue(new Callback<Checksum>() {
            @Override
            public void onResponse(Call<Checksum> call, Response<Checksum> response) {

                //once we get the checksum we will initiailize the payment.
                //the method is taking the checksum we got and the paytm object as the parameter
                //initializePaytmPayment(response.body().getChecksumHash(), paytm);
                initializePaytmPayment(response.body().getChecksumHash(), paytm);

            }

            @Override
            public void onFailure(Call<Checksum> call, Throwable t) {

            }
        });
    }

    private void initializePaytmPayment(String checksumHash, Paytm paytm)
    {
        //use this when using for production
        PaytmPGService Service = PaytmPGService.getProductionService();

        //creating a hashmap and adding all the values required
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", Constants.M_ID);
        paramMap.put("ORDER_ID", paytm.getOrderId());
        paramMap.put("CUST_ID", paytm.getCustId());
        paramMap.put("CHANNEL_ID", paytm.getChannelId());
        paramMap.put("TXN_AMOUNT", paytm.getTxnAmount());
        paramMap.put("WEBSITE", paytm.getWebsite());
        paramMap.put("CALLBACK_URL", paytm.getCallBackUrl());
        paramMap.put("CHECKSUMHASH", checksumHash);
        paramMap.put("INDUSTRY_TYPE_ID", paytm.getIndustryTypeId());

        Log.d("custid",paytm.getCustId());
        Log.d("orderid",paytm.getOrderId());
        Log.d("checksumhash",checksumHash);
        Log.d("checksumhash",paytm.getWebsite());

        //creating a paytm order object using the hashmap
        PaytmOrder order = new PaytmOrder((HashMap<String, String>) paramMap);

        //intializing the paytm service
        Service.initialize(order, null);

        //finally starting the payment transaction
        Service.startPaymentTransaction(this, true, true, this);

    }



    @Override
    public void onTransactionResponse(Bundle bundle)
    {
        // check CallbackResponse values and do action using if-else

        if(online.isChecked() && wallet.isChecked()){
            txn="online and wallet";
        }else{
            txn="online";
        }

        mode=""+bundle.getString("TXNID");
        // Toast.makeText(this, mode, Toast.LENGTH_LONG).show();
        //retrofit strats
        Retrofit retrof = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiServi = retrof.create(ApiInterface.class);
        Call<OrderResponse> call = apiServi.order(u_id, mode, place,txn,total,sub);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {

                //Toast.makeText(payment.this, "Ordered Successfully" + mode, Toast.LENGTH_SHORT).show();
                new SweetAlertDialog(payment.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("ThanK You!")
                        .setContentText("Order has been placed!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                startActivity(new Intent(getApplicationContext(),Main.class));
                            }
                        })
                        .show();



            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {

            }
        });
    }

    //retrofit ends


    @Override
    public void networkNotAvailable() {
        Toast.makeText(this, "Network error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void clientAuthenticationFailed(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void someUIErrorOccurred(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressedCancelTransaction()
    {
        // Do action if backpressed by user
        Toast.makeText(this, "Back Pressed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle)
    {
        // Do action if transaction cancelled by user
        Toast.makeText(this, s + bundle.toString(), Toast.LENGTH_LONG).show();
    }


    // instamojo
    private void callInstamojoPay(String email, String phone, String amount, String purpose, String buyername)
    {
        final Activity activity = this;
        InstamojoPay instamojoPay = new InstamojoPay();
        IntentFilter filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();
        try {
            pay.put("email", email);
            pay.put("phone", phone);
            pay.put("purpose", purpose);
            pay.put("amount", amount);
            pay.put("name", buyername);
            pay.put("send_sms", true);
            pay.put("send_email", true);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        initListener();
        instamojoPay.start(activity, pay, listener);
    }

    InstapayListener listener;


    private void initListener()
    {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {

                Toast.makeText(getApplicationContext(), "Payment Done", Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onFailure(int code, String reason) {
                Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG)
                        .show();
            }
        };
    }


}
