package nmdl.express.ecomm.Adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
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

import nmdl.express.ecomm.ApiUrl.ApiUrl;
import nmdl.express.ecomm.ProductDetail;
import nmdl.express.ecomm.R;
import nmdl.express.ecomm.response.ProductResponse;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyviewHolder> {
    Context context;
    List<ProductResponse> productResponse;
    String shop_id,shop_cid,name,number,city,cat_id;

    public ProductAdapter(Context context, List<ProductResponse> productResponse,String shop_id,String shop_cid,String name,String number,String city,String cat_id) {
        this.context = context;
        this.productResponse = productResponse;
        this.shop_id = shop_id;
        this.shop_cid = shop_cid;
        this.name = name;
        this.number = number;
        this.city = city;
        this.cat_id = cat_id;
    }

    @NonNull
    @Override
    public ProductAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.product_list,viewGroup,false);
        //Toast.makeText(context, "bna hi", Toast.LENGTH_SHORT).show();
        YoYo.with(Techniques.Landing).duration(1000).repeat(0).playOn(view);
        return new ProductAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter.MyviewHolder myviewHolder, int i) {
        myviewHolder.productname.setText(productResponse.get(i).getName());
        myviewHolder.actualr.setText(productResponse.get(i).getAc_price());
        myviewHolder.discountr.setText(productResponse.get(i).getDiscnt_price());
        myviewHolder.pid.setText(productResponse.get(i).getId());
        if(productResponse.get(i).getQuantity().toString().equalsIgnoreCase("0")){
            myviewHolder.stock.setText("OUT OF STOCK");
        }else{
            myviewHolder.stock.setText("IN STOCK");
        }
        int a = Integer.parseInt(productResponse.get(i).getAc_price().toString());
        int b = Integer.parseInt(productResponse.get(i).getDiscnt_price().toString());
         int c = (b/a)*100;
        myviewHolder.discount.setText(c+"% OFF");
        String imageUri = ApiUrl.BASE_URL+productResponse.get(i).getImage();
        Picasso.get().load(imageUri).into(myviewHolder.imageView);
        myviewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductDetail t = new ProductDetail();
                Bundle args = new Bundle();
                args.putString("p_id", ""+myviewHolder.pid.getText().toString());
                args.putString("shop_id", ""+shop_id);
                args.putString("shop_cid", ""+shop_cid);
                args.putString("name", ""+name);
                args.putString("number", ""+number);
                args.putString("city", ""+city);
                args.putString("cat_id", ""+cat_id);
                t.setArguments(args);
                FragmentManager manager = ((Activity) context).getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                //transaction.remove(l);
//                    if(manager != null)
//                        ((Activity) context).getFragmentManager().beginTransaction().hide(l).commit();
                transaction.replace(R.id.homefl, t);

                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

    }

    public void setProductList(List<ProductResponse> productResponse)
    {
        this.productResponse=productResponse;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(productResponse!= null){

            return productResponse.size();
        }
        return 0;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView productname,discountr,actualr,discount,stock,pid;
        ImageView imageView;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            productname = itemView.findViewById(R.id.productname);
            discountr = itemView.findViewById(R.id.discountr);
            actualr = itemView.findViewById(R.id.actualr);
            discount = itemView.findViewById(R.id.discount);
            stock = itemView.findViewById(R.id.stock);
            imageView = itemView.findViewById(R.id.productimage);
            pid=itemView.findViewById(R.id.pid);

        }
    }
}
