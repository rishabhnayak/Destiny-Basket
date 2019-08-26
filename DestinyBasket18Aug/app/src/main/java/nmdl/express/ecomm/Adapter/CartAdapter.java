package nmdl.express.ecomm.Adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.List;

import nmdl.express.ecomm.ApiUrl.ApiInterface;
import nmdl.express.ecomm.ApiUrl.ApiUrl;
import nmdl.express.ecomm.Cart;
import nmdl.express.ecomm.R;
import nmdl.express.ecomm.response.CartAddResponse;
import nmdl.express.ecomm.response.CartResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static nmdl.express.ecomm.ApiUrl.ApiUrl.BASE_URL;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyviewHolder> {
    Context context;
    List<CartResponse> cartResponses;
    TextView cartnum;

    public CartAdapter(Context context, List<CartResponse> cartResponses,TextView cartnum) {
        this.context = context;
        this.cartResponses = cartResponses;
        this.cartnum = cartnum;
    }

    @NonNull
    @Override
    public CartAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.cart_item_list,viewGroup,false);
        YoYo.with(Techniques.Landing).duration(1000).repeat(0).playOn(view);
        // Toast.makeText(context, "bna", Toast.LENGTH_SHORT).show();
        return new CartAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.MyviewHolder myviewHolder, final int i) {
        myviewHolder.name.setText(cartResponses.get(i).getName());
        String imageUri = ApiUrl.BASE_URL+cartResponses.get(i).getImage();
        Picasso.get().load(imageUri).into(myviewHolder.image);
        myviewHolder.price.setText(cartResponses.get(i).getPrice());
        myviewHolder.quantity.setText(cartResponses.get(i).getQuantity());
        myviewHolder.cartid.setText(cartResponses.get(i).getCart_id());
        myviewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qnt = Integer.parseInt(myviewHolder.quantity.getText().toString());
                qnt++;
                final String a = String.valueOf(qnt);
                String s = myviewHolder.cartid.getText().toString();
                Retrofit retrof = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiInterface apiServi = retrof.create(ApiInterface.class);
                Call<CartAddResponse> call = apiServi.cartad(s,a);
                call.enqueue(new Callback<CartAddResponse>() {
                    @Override
                    public void onResponse(Call<CartAddResponse> call, Response<CartAddResponse> response) {
                        Cart t = new Cart();
                        FragmentManager manager = ((Activity) context).getFragmentManager();
                        manager.popBackStackImmediate();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.homefl, t);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        //myviewHolder.quantity.setText(""+a);
                    }

                    @Override
                    public void onFailure(Call<CartAddResponse> call, Throwable t) {

                    }
                });
            }
        });
        myviewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qnt = Integer.parseInt(myviewHolder.quantity.getText().toString());
                qnt--;
                if(qnt == 0){
                    final String a = String.valueOf(qnt);
                    String s = myviewHolder.cartid.getText().toString();
                    Retrofit retrof = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ApiInterface apiServi = retrof.create(ApiInterface.class);
                    Call<CartAddResponse> call = apiServi.cartad(s,a);
                    call.enqueue(new Callback<CartAddResponse>() {
                        @Override
                        public void onResponse(Call<CartAddResponse> call, Response<CartAddResponse> response) {
                            Cart t = new Cart();
                            FragmentManager manager = ((Activity) context).getFragmentManager();
                            manager.popBackStackImmediate();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.replace(R.id.homefl,t);
                            transaction.commit();
                        }

                        @Override
                        public void onFailure(Call<CartAddResponse> call, Throwable t) {

                        }
                    });
                }else{
                    final String a = String.valueOf(qnt);
                    String s = myviewHolder.cartid.getText().toString();
                    Retrofit retrof = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ApiInterface apiServi = retrof.create(ApiInterface.class);
                    Call<CartAddResponse> call = apiServi.cartad(s,a);
                    call.enqueue(new Callback<CartAddResponse>() {
                        @Override
                        public void onResponse(Call<CartAddResponse> call, Response<CartAddResponse> response) {
                            Cart t = new Cart();
                            FragmentManager manager = ((Activity) context).getFragmentManager();
                            manager.popBackStackImmediate();
                            FragmentTransaction transaction = manager.beginTransaction();
                            //transaction.remove(t);
                            transaction.replace(R.id.homefl, t);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            //myviewHolder.quantity.setText(""+a);
                        }

                        @Override
                        public void onFailure(Call<CartAddResponse> call, Throwable t) {

                        }
                    });
                }

            }
        });
        myviewHolder.cartremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qnt = 0;

                final String a = String.valueOf(qnt);
                String s = myviewHolder.cartid.getText().toString();
                Retrofit retrof = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiInterface apiServi = retrof.create(ApiInterface.class);
                Call<CartAddResponse> call = apiServi.cartad(s,a);
                call.enqueue(new Callback<CartAddResponse>() {
                    @Override
                    public void onResponse(Call<CartAddResponse> call, Response<CartAddResponse> response) {
                        cartnum.setText(response.body().getNum());
                        Cart t = new Cart();
                        FragmentManager manager = ((Activity) context).getFragmentManager();
                        manager.popBackStackImmediate();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.homefl,t);
                        transaction.commit();
                    }

                    @Override
                    public void onFailure(Call<CartAddResponse> call, Throwable t) {

                    }
                });
            }
        });

    }
    public void setCartList(List<CartResponse> cartResponses)
    {
        this.cartResponses=cartResponses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(cartResponses!= null){
            return cartResponses.size();
        }

        return 0;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,price,quantity,cartremove,cartid;
        ImageView add,remove;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.cimage);
            name=itemView.findViewById(R.id.cpname);
            price=itemView.findViewById(R.id.cprice);
            quantity=itemView.findViewById(R.id.cqnt);
            cartremove=itemView.findViewById(R.id.cartr);
            add=itemView.findViewById(R.id.cadd);
            remove=itemView.findViewById(R.id.cremove);
            cartid=itemView.findViewById(R.id.cartid);
        }
    }
}
