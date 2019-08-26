package nmdl.express.ecomm;

import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import nmdl.express.ecomm.ApiUrl.ApiInterface;
import nmdl.express.ecomm.ApiUrl.ApiUrl;
import nmdl.express.ecomm.response.ShopCategoryResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShopCategoryDialogFragmant extends DialogFragment {
    Button b;
    TextView sub,ship,total;

    // this method create view for your Dialog
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //inflate layout with recycler view
        View v = inflater.inflate(R.layout.shop_category_dialog, container, false);
        YoYo.with(Techniques.DropOut).duration(1000).repeat(0).playOn(v);
        b=v.findViewById(R.id.clos);
        sub=v.findViewById(R.id.cdsub);
        ship=v.findViewById(R.id.cdship);
        total=v.findViewById(R.id.cdtotal);


        Bundle mArgs = getArguments();
        String tot = mArgs.getString("total");
        String subt = mArgs.getString("sub");
        int a=Integer.parseInt(tot)-Integer.parseInt(subt);
        sub.setText("Rs "+subt);
        ship.setText("Rs "+a);
        total.setText("Rs "+tot);
        //YoYo.with(Techniques.SlideInDown).duration(1000).repeat(0).playOn(v);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                //dialogFragment.dismiss();
            }
        });


        return v;
    }
}
