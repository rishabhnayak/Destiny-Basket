package nmdl.express.ecomm;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.HashMap;
import java.util.List;

import nmdl.express.ecomm.Adapter.ShopCategoryAdapter;
import nmdl.express.ecomm.ApiUrl.ApiInterface;
import nmdl.express.ecomm.ApiUrl.ApiUrl;
import nmdl.express.ecomm.response.CatSliderResponse;
import nmdl.express.ecomm.response.ShopCategoryResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static nmdl.express.ecomm.ApiUrl.ApiUrl.BASE_URL;

public class ShopCategory extends Fragment {
    View view;
    android.support.v7.widget.Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RelativeLayout relativeLayout;
    FrameLayout frameLayout;
    RecyclerView recyclerView;
    SliderLayout sliderLayout;
    LinearLayout linearLayout;
    ShopCategoryAdapter shopCategoryAdapter;
    List<ShopCategoryResponse> shopCategoryResponse;
    TextView shopname,shopno;


    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shop_category_fragment, container, false);
        recyclerView = view.findViewById(R.id.recscat);
        toolbar = getActivity().findViewById(R.id.toolbar);
        linearLayout = view.findViewById(R.id.call);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        sliderLayout = view.findViewById(R.id.imageSlider);
        shopname=view.findViewById(R.id.shopn);
        shopno=view.findViewById(R.id.shopnu);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(3); //set scroll delay in seconds :

        setSliderViews();
        Bundle mBundle = new Bundle();
        mBundle = getArguments();
        String shop_id=mBundle.getString("shop_id");
        final String city=mBundle.getString("city");
        final String cat_id=mBundle.getString("cat_id");
        final String name=mBundle.getString("name");
        final String number=mBundle.getString("number");
        shopno.setText(number);
        shopname.setText(name);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shopHome t = new shopHome();
                Login l = new Login();
                Bundle args = new Bundle();
                args.putString("city", ""+city);
                args.putString("cat_id", ""+cat_id);
                t.setArguments(args);
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                //transaction.remove(l);
//                    if(manager != null)
//                        ((Activity) context).getFragmentManager().beginTransaction().hide(l).commit();
                transaction.replace(R.id.homefl, t);

                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = "+91"+number;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });



        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        shopCategoryAdapter = new ShopCategoryAdapter(getContext(),shopCategoryResponse,name,number,city,cat_id);
        recyclerView.setAdapter(shopCategoryAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiService = retrofit.create(ApiInterface.class);
        Call<List<ShopCategoryResponse>> call = apiService.shopCategoryResponse(shop_id);
        call.enqueue(new Callback<List<ShopCategoryResponse>>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<List<ShopCategoryResponse>> call, Response<List<ShopCategoryResponse>> response) {
                List<ShopCategoryResponse> heros=response.body();
                shopCategoryResponse=response.body();
                shopCategoryAdapter.setShopCategory(shopCategoryResponse);


            }

            @Override
            public void onFailure(Call<List<ShopCategoryResponse>> call, Throwable t) {

            }
        });

        return view;
    }
    //slider
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setSliderViews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiService = retrofit.create(ApiInterface.class);
        Call<List<CatSliderResponse>> call = apiService.slider();
        call.enqueue(new Callback<List<CatSliderResponse>>() {
            @Override
            public void onResponse(Call<List<CatSliderResponse>> call, Response<List<CatSliderResponse>> response) {

                List<CatSliderResponse> heros=response.body();
                //ArrayList<String> mylist = new ArrayList<String>();
                HashMap<String,String> url_maps = new HashMap<String, String>();
                for(CatSliderResponse h: heros){
                    DefaultSliderView sliderView = new DefaultSliderView(getContext());

                    String dbUri= ApiUrl.BASE_URL+h.getImage();
                    sliderView.setImageUrl(dbUri);

                    sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                    sliderView.setDescription(""+h.getName());


                    sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(SliderView sliderView) {
                            //Toast.makeText(getContext(), "This is slider " +h.getName(), Toast.LENGTH_SHORT).show();

                        }
                    });

                    //at last add this view in your layout :
                    sliderLayout.addSliderView(sliderView);

                }



            }

            @Override
            public void onFailure(Call<List<CatSliderResponse>> call, Throwable t) {

            }
        });










    }

}
