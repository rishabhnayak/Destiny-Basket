package nmdl.express.ecomm;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nmdl.express.ecomm.ApiUrl.ApiInterface;
import nmdl.express.ecomm.ApiUrl.ApiUrl;
import nmdl.express.ecomm.response.CartAddResponse;
import nmdl.express.ecomm.response.ProductResponse;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetail extends Fragment {
    View view;
    android.support.v7.widget.Toolbar toolbar;
    //ProductResponse productResponse;
    TextView pname,pprice,pdiscount,qnt,cartnum;
    ImageView pimage;
    String pid,shop_id,shop_cid,name,number,city,cat_id;
    Button cart,wish;
    ImageView add,remove;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.product_detail_fragment, container, false);
        pname = view.findViewById(R.id.pname);
        pprice = view.findViewById(R.id.paprice);
        //pdiscount = view.findViewById(R.id.pdiscount);
        pimage = view.findViewById(R.id.pimage);
        cart = view.findViewById(R.id.ca);
//        wish = view.findViewById(R.id.wi);
        qnt = view.findViewById(R.id.qnt);
        remove = view.findViewById(R.id.ad);
        add = view.findViewById(R.id.re);
        toolbar = getActivity().findViewById(R.id.toolbar);
        cartnum=toolbar.findViewById(R.id.cartnum);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        Bundle mBundle = new Bundle();
        mBundle = getArguments();
        final String p_id=mBundle.getString("p_id");
        final String shop_id=mBundle.getString("shop_id");
        final String shop_cid=mBundle.getString("shop_cid");
        name=mBundle.getString("name");
        number=mBundle.getString("number");
        city=mBundle.getString("city");
        cat_id=mBundle.getString("cat_id");
        final String uid = SharedPrefManager.getInstance(getContext()).getUser().getId();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product t = new Product();
                Login l = new Login();
                Bundle args = new Bundle();
                args.putString("shop_id", ""+shop_id);
                args.putString("shop_cid", ""+shop_cid);
                args.putString("name", ""+name);
                args.putString("number", ""+number);
                args.putString("city", ""+city);
                args.putString("cat_id", ""+cat_id);
                t.setArguments(args);
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.homefl, t);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SharedPrefManager.getInstance(getContext()).isLoggedIn()) {
                    Intent i = new Intent(getContext(),log.class);
                    startActivity(i);
                } else {
                    String quantity = qnt.getText().toString();
                    String type = "cart";

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(ApiUrl.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ApiInterface apiService = retrofit.create(ApiInterface.class);
                    Call<CartAddResponse> call = apiService.cartadd(p_id, shop_id, uid, quantity, type);
                    call.enqueue(new Callback<CartAddResponse>() {
                        @Override
                        public void onResponse(Call<CartAddResponse> call, Response<CartAddResponse> response) {
                            cartnum.setText(response.body().getNum());
                            //Toast.makeText(getContext(), "Product succesfully added", Toast.LENGTH_SHORT).show();
                            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Good Job!")
                                    .setContentText("Product Successfully Added!")
                                    .show();

                        }

                        @Override
                        public void onFailure(Call<CartAddResponse> call, Throwable t) {
                           // Toast.makeText(getContext(), "nhi aya", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                }

        });

//        wish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (!SharedPrefManager.getInstance(getContext()).isLoggedIn()) {
//                    Intent i = new Intent(getContext(),log.class);
//                    startActivity(i);
//                } else {
//                    String quantity = qnt.getText().toString();
//                    String type = "wish";
//
//                    Retrofit retrofit = new Retrofit.Builder()
//                            .baseUrl(ApiUrl.BASE_URL)
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build();
//                    ApiInterface apiService = retrofit.create(ApiInterface.class);
//                    Call<CartAddResponse> call = apiService.cartadd(p_id, shop_id, uid, quantity, type);
//                    call.enqueue(new Callback<CartAddResponse>() {
//                        @Override
//                        public void onResponse(Call<CartAddResponse> call, Response<CartAddResponse> response) {
//                            if (response.body().getSuccess().equalsIgnoreCase("yes")) {
////                                Toast.makeText(getContext(), "Product succesfully added", Toast.LENGTH_SHORT).show();
//                                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
//                                        .setTitleText("Good Job!")
//                                        .setContentText("Product Successfully Added!")
//                                        .show();
//                            } else {
//                               // Toast.makeText(getContext(), "problem", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<CartAddResponse> call, Throwable t) {
//                           // Toast.makeText(getContext(), "nhi aya", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(qnt.getText().toString());
                a++;
                qnt.setText(""+a);

            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(qnt.getText().toString());
                if(a==1){

                }else{
                    a--;
                    qnt.setText(""+a);
                }

            }
        });



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiService = retrofit.create(ApiInterface.class);
        Call<ProductResponse> call = apiService.productDetail(p_id);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                pname.setText(""+response.body().getName());
                pprice.setText("Rs. "+response.body().getDiscnt_price());
                //pdiscount.setText("10");
                String imageUri = ApiUrl.BASE_URL+response.body().getImage();
                Picasso.get().load(imageUri).into(pimage);
                pid=response.body().getId();

            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                //Toast.makeText(getContext(), "nhi hi", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
}
