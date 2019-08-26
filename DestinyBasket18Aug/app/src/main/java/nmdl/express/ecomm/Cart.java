package nmdl.express.ecomm;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import nmdl.express.ecomm.Adapter.CartAdapter;
import nmdl.express.ecomm.ApiUrl.ApiInterface;
import nmdl.express.ecomm.ApiUrl.ApiUrl;
import nmdl.express.ecomm.response.CartResponse;
import nmdl.express.ecomm.response.CartSumResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static nmdl.express.ecomm.ApiUrl.ApiUrl.BASE_URL;

public class Cart extends Fragment {
    View view;
    android.support.v7.widget.Toolbar toolbar;
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    List<CartResponse> cartResponses;
    List<String> cart_id,shop_id,p_id,u_id,quantity,subtotal,mode;
    TextView total,cartnum;
    int sum,su;
    Button cartdetail;
    CardView cardView;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cart_fragment, container, false);
        recyclerView = view.findViewById(R.id.reccart);
        toolbar = getActivity().findViewById(R.id.toolbar);
        cartnum=toolbar.findViewById(R.id.cartnum);
        total = view.findViewById(R.id.itt);
        cartdetail=view.findViewById(R.id.cartdetail);
        //AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        //`params.setScrollFlags(0);
        //recyclerView.setNestedScrollingEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), home.class);
                startActivity(i);
            }
        });
        //toolbar.setScrollbarFadingEnabled(false);
        //toolbar.setVerticalScrollBarEnabled(false);

        cardView = view.findViewById(R.id.cardo);


        String s=SharedPrefManager.getInstance(getContext()).getUser().getId();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        //layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        cartAdapter= new CartAdapter(getContext(),cartResponses,cartnum);
        recyclerView.setAdapter(cartAdapter);
        Retrofit retrof = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiServi = retrof.create(ApiInterface.class);
        Call<List<CartResponse>> call = apiServi.cart(s);
        call.enqueue(new Callback<List<CartResponse>>() {
            @Override
            public void onResponse(Call<List<CartResponse>> call, Response<List<CartResponse>> response) {
                cartResponses=response.body();
                ArrayList<String> shop = new ArrayList<>();
                List<CartResponse> response1 = response.body();
                cartAdapter.setCartList(cartResponses);
                for(CartResponse h: cartResponses){
                    //String shop = h.getShop_id();
                    int a,b;
                    h.getName();
                    Log.d("name of category is :",""+h.getName());
                    a= Integer.parseInt(h.getPrice());
                    b= Integer.parseInt(h.getQuantity());
                    h.getP_id();
                    h.getCart_id();
                    h.getQuantity();
                    //av
                    if(shop.isEmpty()){
                        shop.add(h.getShop_id());
                    }else{
                        if(shop.contains(h.getShop_id())){

                        }else{
                            shop.add(h.getShop_id());
                        }
                    }
                    //
                    su=su+(a*b);
                }
                String shopl="";
                Iterator iterator = shop.iterator();
                while(iterator.hasNext()) {
                    if(shopl.equalsIgnoreCase("")){
                        shopl=shopl+iterator.next();
                    }else{
                        shopl=shopl+","+iterator.next();
                    }
                    //Log.d("this is the message",""+iterator.next());
                    //System.out.println(iterator.next());
                }
                Log.d("this is the message",shopl);
                String u_id=SharedPrefManager.getInstance(getContext()).getUser().getId();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ApiUrl.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiInterface apiService = retrofit.create(ApiInterface.class);
                //Toast.makeText(getContext(), ""+u_id, Toast.LENGTH_SHORT).show();
                Log.d("uid is:",""+u_id);
                //Log.d("uid is:",""+u_id);
                Call<CartSumResponse> cal = apiService.cartsum(u_id,shopl);
                cal.enqueue(new Callback<CartSumResponse>() {
                    @Override
                    public void onResponse(Call<CartSumResponse> call, Response<CartSumResponse> response) {
                        //Toast.makeText(getContext(), "hua", Toast.LENGTH_SHORT).show();
                        total.setText("Rs "+response.body().getAmnt());
                        sum= Integer.parseInt(response.body().getAmnt());
                        // Toast.makeText(getContext(), ""+response.body().getAmnt(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<CartSumResponse> call, Throwable t) {
                        //Toast.makeText(getContext(), " nhi hua", Toast.LENGTH_SHORT).show();
                    }
                });

                //total.setText("Rs "+sum);
                //Toast.makeText(getContext(), "aya hi", Toast.LENGTH_SHORT).show();
                //sum= Integer.parseInt(total.getText().toString());
            }

            @Override
            public void onFailure(Call<List<CartResponse>> call, Throwable t) {

            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String u_id=SharedPrefManager.getInstance(getContext()).getUser().getId();

                if(sum>0)
                {
                    p_id=new ArrayList<String>();
                    cart_id=new ArrayList<String>();
                    quantity=new ArrayList<String>();
                    Intent i = new Intent(getContext(), payment.class);
                    i.putExtra("total",""+sum);
                    i.putExtra("sub", ""+su);
                    startActivity(i);
                    //Toast.makeText(getContext(), "card"+sum, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Error")
                            .setContentText("No Items in the Cart!")
                            .show();
                }

            }
        });
        cartdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("total", ""+sum);
                args.putString("sub", ""+su);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                DialogFragment dialogFragment = new ShopCategoryDialogFragmant();
                dialogFragment.setArguments(args);
                dialogFragment.show(ft, "dialog");
                dialogFragment.setCancelable(true);
            }
        });

        return view;
    }
}
