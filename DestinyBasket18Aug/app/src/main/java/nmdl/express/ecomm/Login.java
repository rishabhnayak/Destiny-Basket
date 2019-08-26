package nmdl.express.ecomm;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import cn.pedant.SweetAlert.SweetAlertDialog;
import nmdl.express.ecomm.ApiUrl.ApiInterface;
import nmdl.express.ecomm.ApiUrl.ApiUrl;
import nmdl.express.ecomm.response.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends Fragment {
    View view;
    Button button;
    EditText username,password;
    TextView t1,t2,forgot;
    Button button1;
    LinearLayout linearLayout;

    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment

        view = inflater.inflate(R.layout.login, container, false);

        button = view.findViewById(R.id.btn);
        username = view.findViewById(R.id.name);
        password = view.findViewById(R.id.pass);
        t1 = view.findViewById(R.id.t);
        t2=view.findViewById(R.id.t1);
        forgot=view.findViewById(R.id.forgotpwd);
        //button1 = view.findViewById(R.id.);


        YoYo.with(Techniques.FadeIn).duration(1000).repeat(0).playOn(view.findViewById(R.id.rl));
        YoYo.with(Techniques.RotateInUpLeft).duration(1000).repeat(0).playOn(view.findViewById(R.id.li));
        //YoYo.with(Techniques.Flash).duration(6000).repeat(Animation.INFINITE).playOn(view.findViewById(R.id.lt));
        YoYo.with(Techniques.StandUp).duration(1000).repeat(0).playOn(button);
        //YoYo.with(Techniques.FadeIn).duration(1000).repeat(0).playOn(view.findViewById(R.id.cr));
        YoYo.with(Techniques.StandUp).duration(1500).repeat(0).playOn(username);
        YoYo.with(Techniques.StandUp).duration(1500).repeat(0).playOn(password);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call to send sms
                progressDialog=new ProgressDialog(getActivity());
                progressDialog.setMessage("Working...!!");
                progressDialog.show();
                Log.d("nmdl","click hua hai");

                String s;
                s=username.getText().toString();
                if(s != null && !s.isEmpty())
                {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(ApiUrl.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ApiInterface apiService = retrofit.create(ApiInterface.class);
                    Call<LoginResponse> call=apiService.forgotPwd(username.getText().toString());
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.body().getSuccess().equalsIgnoreCase("yes"))
                            {
                                progressDialog.dismiss();
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Success..")
                                        .setContentText("Please Wait An SMS has been sent to your registered mobile number!")
                                        .show();
                                //Toast.makeText(getActivity(), ".", Toast.LENGTH_LONG).show();
                                Log.d("msg","Yes");
                            }
                            else
                            {
                                progressDialog.dismiss();
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Error")
                                        .setContentText("No Account Exist!")
                                        .show();
                                //startActivity(new Intent(getApplicationContext(),Main.class));
                                username.setText("");
                                username.requestFocus();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Log.d("msg",""+t.getMessage());
                        }
                    });

                }
                else
                {
                    Log.d("nmdl","khaali hai");

                    progressDialog.dismiss();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Note")
                            .setContentText("Please Enter Mobile Number First")
                            .show();
                    //startActivity(new Intent(getApplicationContext(),Main.class));
                    username.requestFocus();
                }

            }
        });


       button.setOnClickListener(new View.OnClickListener() {
           @RequiresApi(api = Build.VERSION_CODES.M)
           @Override
           public void onClick(View view) {
               String s1,s2;
               s1=username.getText().toString();
               s2=password.getText().toString();
               if(s1.equalsIgnoreCase("")){
                   YoYo.with(Techniques.Tada).duration(1000).repeat(0).playOn(username);
                   if(s2.equalsIgnoreCase("")){
                       YoYo.with(Techniques.Tada).duration(1000).repeat(0).playOn(password);
                   }
               }else if(s2.equalsIgnoreCase("")){
                   YoYo.with(Techniques.Tada).duration(1000).repeat(0).playOn(password);
                   if(s1.equalsIgnoreCase("")){
                       YoYo.with(Techniques.Tada).duration(1000).repeat(0).playOn(username);
                   }
               }else{
                   final ProgressDialog progressDialog = new ProgressDialog(getContext());;
                   progressDialog.setMessage("Please Wait...");
                   progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                   progressDialog.show();


                   Retrofit retrofit = new Retrofit.Builder()
                           .baseUrl(ApiUrl.BASE_URL)
                           .addConverterFactory(GsonConverterFactory.create())
                           .build();
                   ApiInterface apiService = retrofit.create(ApiInterface.class);
                   Call<LoginResponse> call = apiService.LoginResponse(s1, s2);
                   call.enqueue(new Callback<LoginResponse>() {
                       @Override
                       public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                           progressDialog.dismiss();
                           if (response.body().getSuccess().equalsIgnoreCase("yes")) {
                               String name = response.body().getName();
                               String email = response.body().getEmail();
                               String id = response.body().getId();
                               String mobile = response.body().getMobile();
                               String adress = response.body().getAdress();

                               SharedPrefManager.getInstance(getContext()).userLogin(name, mobile, email, id, adress);
                               String n = SharedPrefManager.getInstance(getContext()).getUser().getName();
                               Intent i = new Intent(getContext(), home.class);
                               startActivity(i);
//                               CatHome t = new CatHome();
//                               Bundle args = new Bundle();
//                              // args.putString("YourKey", "jhg");
//                               //t.setArguments(args);
//                               FragmentManager manager = getFragmentManager();
//                               FragmentTransaction transaction = manager.beginTransaction();
//                               transaction.addToBackStack("first");
//                               transaction.replace(R.id.fl,t,"second");
//                               transaction.commit();

                              // Toast.makeText(getContext(), "" + n, Toast.LENGTH_SHORT).show();
                           } else {
                               //Toast.makeText(getContext(), "galat hai", Toast.LENGTH_SHORT).show();
                           }
                       }

                       @Override
                       public void onFailure(Call<LoginResponse> call, Throwable t) {

                       }

                   });
               }

           }
       });
      t2.setOnClickListener(new View.OnClickListener() {
           @RequiresApi(api = Build.VERSION_CODES.M)
           @Override
           public void onClick(View view) {
               Intent i = new Intent(getContext(),home.class);
               startActivity(i);
           }
       });

       t1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Register t = new Register();
               Bundle args = new Bundle();
               args.putString("YourKey", "jhg");
               t.setArguments(args);
               FragmentManager manager = getFragmentManager();
               FragmentTransaction transaction = manager.beginTransaction();
               transaction.addToBackStack("first");
               transaction.replace(R.id.fl,t,"second");
               transaction.commit();

           }
       });


        return view;
    }


}
