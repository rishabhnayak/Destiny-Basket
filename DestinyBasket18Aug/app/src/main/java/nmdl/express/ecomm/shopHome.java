package nmdl.express.ecomm;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nmdl.express.ecomm.Adapter.ShopAdapter;
import nmdl.express.ecomm.ApiUrl.ApiInterface;
import nmdl.express.ecomm.ApiUrl.ApiUrl;
import nmdl.express.ecomm.response.CatSliderResponse;
import nmdl.express.ecomm.response.ShopResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static nmdl.express.ecomm.ApiUrl.ApiUrl.BASE_URL;

public class shopHome extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    View view;
    android.support.v7.widget.Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RelativeLayout relativeLayout;
    FrameLayout frameLayout;
    RecyclerView recyclerView;
    ShopAdapter shopAdapter;
    SliderLayout sliderLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    List<ShopResponse> shopResponses;
    EditText myAutoComplete;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shop_home_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclershop);
        swipeRefreshLayout = view.findViewById(R.id.swipes);
        myAutoComplete = view.findViewById(R.id.shopauto);

        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        sliderLayout = view.findViewById(R.id.imagSlider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(3); //set scroll delay in seconds :
        setSliderViews();
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_red_light);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(), home.class);
                startActivity(i);
//                CatHome t = new CatHome();
//                Bundle args = new Bundle();
//
//                t.setArguments(args);
//                FragmentManager manager = getFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.homefl, t,"category_home_fragment");
//                // transaction.addToBackStack("second");
//                transaction.commit();
            }
        });


//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle mBundle = new Bundle();
        mBundle = getArguments();
        String city = mBundle.getString("city");
        String cat_id = mBundle.getString("cat_id");
        //Toast.makeText(getContext(), ""+city+cat_id, Toast.LENGTH_SHORT).show();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        shopAdapter = new ShopAdapter(getContext(), shopResponses, city, cat_id);
        recyclerView.setAdapter(shopAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiService = retrofit.create(ApiInterface.class);
        Call<List<ShopResponse>> call = apiService.shop(city, cat_id);
        call.enqueue(new Callback<List<ShopResponse>>() {
            @Override
            public void onResponse(Call<List<ShopResponse>> call, Response<List<ShopResponse>> response) {
                List<ShopResponse> heros = response.body();
                int i;
//                for(ShopResponse h : heros) {
//                    Log.d("name of category is :",""+h.getShop_name());
//                    //Toast.makeText(getContext(), ""+h.getShop_name(), Toast.LENGTH_SHORT).show();
//
//
//                }
                shopResponses = response.body();
                shopAdapter.setProductList(shopResponses);

            }

            @Override
            public void onFailure(Call<List<ShopResponse>> call, Throwable t) {
                // Toast.makeText(getContext(), "aya hi ni hai", Toast.LENGTH_SHORT).show();

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

                List<CatSliderResponse> heros = response.body();
                //ArrayList<String> mylist = new ArrayList<String>();
                HashMap<String, String> url_maps = new HashMap<String, String>();
                for (CatSliderResponse h : heros) {
                    DefaultSliderView sliderView = new DefaultSliderView(getContext());

                    String dbUri = ApiUrl.BASE_URL + h.getImage();
                    sliderView.setImageUrl(dbUri);

                    sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                    sliderView.setDescription("" + h.getName());
                    Log.d("the description is",""+h.getName());
                    //Toast.makeText(getContext(), ""+h.getName(), Toast.LENGTH_SHORT).show();


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
        myAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void filter(String s) {
        List<ShopResponse> heros = shopResponses;
        List<ShopResponse> hero = new ArrayList<>();
try{
    for(ShopResponse h : heros) {
        //Log.d("name of category is :",""+h.getShop_name());
        //Toast.makeText(getContext(), ""+h.getShop_name(), Toast.LENGTH_SHORT).show();
        if(h.getShop_name().toLowerCase().contains(s.toLowerCase())){
            // Toast.makeText(getContext(), ""+h.getShop_name(), Toast.LENGTH_SHORT).show();
            hero.add(h);
        }

    }
    shopAdapter.setProductList(hero);
}catch (Exception e){

}

    }

    @Override
    public void onRefresh() {
        Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                myAutoComplete.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
        myAutoComplete.setVisibility(View.INVISIBLE);
        swipeRefreshLayout.setRefreshing(true);
        //refreshList();
    }

    

}
