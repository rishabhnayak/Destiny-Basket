package nmdl.express.ecomm.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import nmdl.express.ecomm.R;
import nmdl.express.ecomm.response.MyPaymentResponse;

public class MyPaymentAdapter extends RecyclerView.Adapter<MyPaymentAdapter.MyviewHolder> {
    Context context;
    List<MyPaymentResponse> myPaymentResponses;

    public MyPaymentAdapter(Context context, List<MyPaymentResponse> myPaymentResponses) {
        this.context = context;
        this.myPaymentResponses = myPaymentResponses;
    }

    @NonNull
    @Override
    public MyPaymentAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.mypayment_list,viewGroup,false);
        YoYo.with(Techniques.Landing).duration(1000).repeat(0).playOn(view);
        return new MyPaymentAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPaymentAdapter.MyviewHolder myviewHolder, int i) {

        myviewHolder.dttime.setText(myPaymentResponses.get(i).getDttime());
        myviewHolder.money.setText(myPaymentResponses.get(i).getSum());
    }

    public void setPaymentList(List<MyPaymentResponse> paymentResponses)
    {
        this.myPaymentResponses=paymentResponses;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(myPaymentResponses!= null){

            return myPaymentResponses.size();
        }
        return 0;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView dttime,money;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            dttime=itemView.findViewById(R.id.dtt);
            money=itemView.findViewById(R.id.mpm);
        }
    }
}
