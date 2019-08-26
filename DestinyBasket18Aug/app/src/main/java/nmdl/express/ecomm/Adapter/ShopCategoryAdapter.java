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
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import nmdl.express.ecomm.Login;
import nmdl.express.ecomm.Product;
import nmdl.express.ecomm.R;
import nmdl.express.ecomm.response.ShopCategoryResponse;

public class ShopCategoryAdapter extends RecyclerView.Adapter<ShopCategoryAdapter.MyviewHolder> {
    Context context;
    List<ShopCategoryResponse> shopCategoryResponseResponses;
    String name,number,city,cat_id;



    public ShopCategoryAdapter(Context context, List<ShopCategoryResponse> shopCategoryResponseResponses, String name, String number,String city, String cat_id) {
        this.context = context;
        this.shopCategoryResponseResponses = shopCategoryResponseResponses;
        this.name = name;
        this.number = number;
        this.city = city;
        this.cat_id = cat_id;

    }

    @NonNull
    @Override
    public ShopCategoryAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.shop_category_list,viewGroup,false);
        YoYo.with(Techniques.Landing).duration(1000).repeat(0).playOn(view);
        return new ShopCategoryAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopCategoryAdapter.MyviewHolder myviewHolder, int i) {
        myviewHolder.textView.setText(shopCategoryResponseResponses.get(i).getName());
        myviewHolder.textView1.setText(shopCategoryResponseResponses.get(i).getShop_id());
        myviewHolder.textView2.setText(shopCategoryResponseResponses.get(i).getSub_cid());

        myviewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, ""+myviewHolder.textView1.getText().toString(), Toast.LENGTH_SHORT).show();
                Product t = new Product();
                Login l = new Login();
                Bundle args = new Bundle();
                args.putString("shop_id", ""+myviewHolder.textView1.getText().toString());
                args.putString("shop_cid", ""+myviewHolder.textView2.getText().toString());
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
    public void setShopCategory(List<ShopCategoryResponse> shopCategoryResponseResponses)
    {
        this.shopCategoryResponseResponses=shopCategoryResponseResponses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(shopCategoryResponseResponses!= null){

            return shopCategoryResponseResponses.size();
        }
        return 0;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView textView,textView1,textView2;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.scname);
            textView1 = itemView.findViewById(R.id.scid);
            textView2 = itemView.findViewById(R.id.sccid);


        }
    }
}
