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
import nmdl.express.ecomm.R;
import nmdl.express.ecomm.SharedPrefManager;
import nmdl.express.ecomm.ShopCategory;
import nmdl.express.ecomm.response.ShopResponse;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.MyviewHolder>{
    Context context;
    List<ShopResponse> shopResponseList;
    String s;
    String a;
    String city,cat_id;

    public ShopAdapter(Context context, List<ShopResponse> shopResponseList,String city,String cat_id)
    {
        this.context = context;
        this.shopResponseList = shopResponseList;
        this.cat_id=cat_id;
        this.city=city;
    }

    @NonNull
    @Override
    public ShopAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view= LayoutInflater.from(context).inflate(R.layout.shop_list,viewGroup,false);
        YoYo.with(Techniques.Landing).duration(1000).repeat(0).playOn(view);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopAdapter.MyviewHolder myviewHolder, int i)
    {
        //Toast.makeText(context, "aya hi yar"+shopResponseList.get(i).getShop_name(), Toast.LENGTH_SHORT).show();
        myviewHolder.sn.setText(shopResponseList.get(i).getShop_name());
        myviewHolder.sr.setText(shopResponseList.get(i).getShop_rating());
        myviewHolder.time.setText(shopResponseList.get(i).getShop_timing());
        myviewHolder.sin.setText(shopResponseList.get(i).getShop_id());
        myviewHolder.shopnum.setText(shopResponseList.get(i).getPhone());
        //myviewHolder.number.setText(shopResponseList.get(i).);
        String imageUri = ApiUrl.BASE_URL+shopResponseList.get(i).getShop_image();
        Picasso.get().load(imageUri).into(myviewHolder.si);

        myviewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShopCategory t = new ShopCategory();
                Bundle args = new Bundle();
                args.putString("shop_id", ""+ myviewHolder.sin.getText().toString());
                args.putString("city", ""+ SharedPrefManager.getInstance(context).getCity());
                args.putString("cat_id", ""+cat_id);
                args.putString("number", ""+myviewHolder.shopnum.getText().toString());
                args.putString("name", ""+myviewHolder.sn.getText().toString());
                //args.putString("cat_id", ""+myviewHolder.sin.getText().toString());
                t.setArguments(args);
                FragmentManager manager = ((Activity) context).getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                //transaction.remove(l);
//                    if(manager != null)
//                        ((Activity) context).getFragmentManager().beginTransaction().hide(l).commit();
                transaction.replace(R.id.homefl, t);

                transaction.addToBackStack(null);
                transaction.commit();
//                String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
//                        "WebOS","Ubuntu","Windows7","Max OS X"};
//                ArrayAdapter adapter = new ArrayAdapter<String>(context,
//                        R.layout.shop_category_dialog, mobileArray);
//
//
//                myviewHolder.listView.setAdapter(adapter);
//
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
//                LayoutInflater factory = LayoutInflater.from(view.getContext());
//                final View view1 = factory.inflate(R.layout.shop_category_dialog, null);
//                alertDialog.setView(view1);
//                alertDialog.setNegativeButton("Schließen", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                alertDialog.show();


            }
        });

    }

    public void setProductList(List<ShopResponse> productList)
    {
        this.shopResponseList=productList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(shopResponseList!= null){

            return shopResponseList.size();
        }
        return 0;
    }


    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView sn,sr,time,sin,number,shopnum;
        ImageView si;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            sn=itemView.findViewById(R.id.shopListName);
            sr=itemView.findViewById(R.id.shopListRate);
            time=itemView.findViewById(R.id.shopListTime);
            si=itemView.findViewById(R.id.shopListImage);
            sin=itemView.findViewById(R.id.sin);
            number=itemView.findViewById(R.id.shopnumber);

            shopnum=itemView.findViewById(R.id.shopnum);



        }
    }
}
