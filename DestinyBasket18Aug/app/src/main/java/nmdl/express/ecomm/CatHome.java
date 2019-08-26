package nmdl.express.ecomm;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

import nmdl.express.ecomm.Adapter.CategoryAdapter;
import nmdl.express.ecomm.ApiUrl.ApiInterface;
import nmdl.express.ecomm.ApiUrl.ApiUrl;
import nmdl.express.ecomm.response.CatSliderResponse;
import nmdl.express.ecomm.response.CategoryResponse;
import nmdl.express.ecomm.response.CityResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static nmdl.express.ecomm.ApiUrl.ApiUrl.BASE_URL;

public class CatHome extends Fragment implements TextWatcher ,SwipeRefreshLayout.OnRefreshListener{
    View view;
    android.support.v7.widget.Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RelativeLayout relativeLayout;
    FrameLayout frameLayout;
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    List<CategoryResponse> categoryResponse;
    AutoCompleteTextView myAutoComplete,myauto;
    SwipeRefreshLayout swipeRefreshLayout;
    SliderLayout sliderLayout;
    ArrayList<String> mylist = new ArrayList<String>();
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.category_home_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerCat);

        toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        sliderLayout = view.findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(3); //set scroll delay in seconds :

        setSliderViews();
        //myAutoComplete =  toolbar.findViewById(R.id.tooltext);
        myAutoComplete = view.findViewById(R.id.auto);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        //swipeRefreshLayout.setr
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_red_light);

        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),2);
        //layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        categoryAdapter= new CategoryAdapter(getContext(),categoryResponse,myAutoComplete);
        recyclerView.setAdapter(categoryAdapter);
        Retrofit retrof = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiServi = retrof.create(ApiInterface.class);
        Call<List<CategoryResponse>> calll = apiServi.category();
        calll.enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {

                categoryResponse=response.body();
                //List<SiteResponse> heros=response.body();
                for(CategoryResponse h: categoryResponse){
                    h.getCategory_name();
                    //Log.d("name of category is :",""+h.getCategory_image());
                }



                categoryAdapter.setCategoryList(categoryResponse);
                //Toast.makeText(getContext().getApplicationContext(), "aya", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {

            }
        });
        if(mylist.isEmpty()) {
            Retrofit retrofi = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiInterface apiServic = retrofi.create(ApiInterface.class);

            Call<List<CityResponse>> cal = apiServic.city();
            cal.enqueue(new Callback<List<CityResponse>>() {
                @Override
                public void onResponse(Call<List<CityResponse>> call, Response<List<CityResponse>> response) {

                    List<CityResponse> heros = response.body();
                    int i;
                    for (CityResponse h : heros) {
                        Log.d("name of category is :", "" + h.getCity_name());
                        mylist.add(h.getCity_name());

                    }
                }

                @Override
                public void onFailure(Call<List<CityResponse>> call, Throwable t) {

                    Log.d("msss", "aya hi ni");
                }
            });
        }
        if(SharedPrefManager.getInstance(getContext()).isCitySet()){
            String n = SharedPrefManager.getInstance(getContext()).getCity();
            myAutoComplete.setText(n);
        }
        myAutoComplete.addTextChangedListener(this);
        myAutoComplete.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, mylist));

       myAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               String selectedItem=myAutoComplete.getAdapter().getItem(i).toString();
               String a=myAutoComplete.getText().toString();
               //Toast.makeText(getContext(), ""+selectedItem, Toast.LENGTH_SHORT).show();
               if (SharedPrefManager.getInstance(getContext()).isCitySet()){
                   SharedPrefManager.getInstance(getContext()).clearCity();
                   SharedPrefManager.getInstance(getContext()).userCity(selectedItem);
               }else{
                   SharedPrefManager.getInstance(getContext()).userCity(selectedItem);
               }

               //String n = SharedPrefManager.getInstance(getContext()).getUser().getName();
           }
       });
       recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
           @Override
           public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {


               //super.onScrollStateChanged(recyclerView, newState);
           }

           @Override
           public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
               super.onScrolled(recyclerView, dx, dy);
           }
       });
        return view;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

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
    void refreshList(){
        //do processing to get new data and set your listview's adapter, maybe  reinitialise the loaders you may be using or so
        //when your data has finished loading, cset the refresh state of the view to false
        swipeRefreshLayout.setRefreshing(false);

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
