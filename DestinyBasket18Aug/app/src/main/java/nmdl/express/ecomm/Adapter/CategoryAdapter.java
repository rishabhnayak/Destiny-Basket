package nmdl.express.ecomm.Adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import nmdl.express.ecomm.ApiUrl.ApiUrl;
import nmdl.express.ecomm.Login;
import nmdl.express.ecomm.R;
import nmdl.express.ecomm.SharedPrefManager;
import nmdl.express.ecomm.response.CategoryResponse;
import nmdl.express.ecomm.shopHome;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyviewHolder> {
    Context context;
    Toolbar toolbar;
    AutoCompleteTextView myAutoComplete;
    List<CategoryResponse> categoryResponse;
    String s;
    String a;

    public CategoryAdapter(Context context, List<CategoryResponse> categoryResponse,AutoCompleteTextView myAutoComplete){
        this.context = context;
        this.categoryResponse = categoryResponse;
        this.myAutoComplete=myAutoComplete;
        //Toast.makeText(context, "hu to hi", Toast.LENGTH_SHORT).show();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public CategoryAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.cat_list,viewGroup,false);
        YoYo.with(Techniques.Landing).duration(1000).repeat(0).playOn(view);
        return new CategoryAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryAdapter.MyviewHolder myviewHolder, int i) {

        myviewHolder.textView.setText(categoryResponse.get(i).getCategory_name());
        myviewHolder.in.setText(categoryResponse.get(i).getCategory_id());

        String imageUri = ApiUrl.BASE_URL+categoryResponse.get(i).getCategory_image();
        Picasso.get().load(imageUri).into(myviewHolder.imageView);
        //Toast.makeText(context, "aya hi"+categoryResponse.get(i).getCategory_name(), Toast.LENGTH_SHORT).show();
        myviewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (!SharedPrefManager.getInstance(context).isCitySet()){

                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Note")
                            .setContentText("Please Select the City First!")
                            .show();
                    //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();



                }else{
                    shopHome t = new shopHome();
                    Login l = new Login();
                    Bundle args = new Bundle();
                    args.putString("city", ""+SharedPrefManager.getInstance(context).getCity());
                    args.putString("cat_id", ""+myviewHolder.in.getText().toString());
                    t.setArguments(args);
                    FragmentManager manager = ((Activity) context).getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    //transaction.remove(l);
//                    if(manager != null)
//                        ((Activity) context).getFragmentManager().beginTransaction().hide(l).commit();
                    transaction.replace(R.id.homefl, t);

                     transaction.addToBackStack(null);
                    transaction.commit();

                    //Toast.makeText(context, ""+SharedPrefManager.getInstance(context).getCity(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void setCategoryList(List<CategoryResponse> categoryResponse)
    {
        this.categoryResponse=categoryResponse;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(categoryResponse!= null){

            return categoryResponse.size();
        }
        return 0;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView,in;
        FrameLayout frameLayout;


        public MyviewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.imgcat);
            textView = itemView.findViewById(R.id.catName);
            in = itemView.findViewById(R.id.in);
            frameLayout = itemView.findViewById(R.id.homefl);



        }
    }
}
