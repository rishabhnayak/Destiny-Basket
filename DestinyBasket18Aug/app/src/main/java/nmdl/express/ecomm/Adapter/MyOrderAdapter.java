package nmdl.express.ecomm.Adapter;

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

import nmdl.express.ecomm.ApiUrl.ApiUrl;
import nmdl.express.ecomm.R;
import nmdl.express.ecomm.response.MyOrderResponse;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyviewHolder> {
    Context context;
    List<MyOrderResponse> myOrderResponses;

    public MyOrderAdapter(Context context, List<MyOrderResponse> myOrderResponses) {
        this.context = context;
        this.myOrderResponses = myOrderResponses;
    }

    @NonNull
    @Override
    public MyOrderAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.myorder_list,viewGroup,false);
        YoYo.with(Techniques.Landing).duration(1000).repeat(0).playOn(view);
        return new MyOrderAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.MyviewHolder myviewHolder, int i) {
        myviewHolder.shopname.setText(myOrderResponses.get(i).getSname());
        myviewHolder.status.setText(myOrderResponses.get(i).getStatus());
        myviewHolder.date.setText(myOrderResponses.get(i).getDttime());

        int ship=Integer.parseInt(myOrderResponses.get(i).getShipping());
        int total=Integer.parseInt(myOrderResponses.get(i).getSum());


        myviewHolder.sum.setText("RS. "+total);
        myviewHolder.shipping.setText("RS. "+ship);
        String imageUri = ApiUrl.BASE_URL+myOrderResponses.get(i).getImage();
        Picasso.get().load(imageUri).into(myviewHolder.imageView);

            myviewHolder.item.setText(myOrderResponses.get(i).getQuantity()+" x "+myOrderResponses.get(i).getName());

    }

    public void setMyorderList(List<MyOrderResponse> myOrderResponses)
    {
        this.myOrderResponses=myOrderResponses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(myOrderResponses!= null){

            return myOrderResponses.size();
        }
        return 0;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView shopname,item,date,sum,status,shipping;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.oi);
            shopname=itemView.findViewById(R.id.osname);
            item=itemView.findViewById(R.id.oitem);
            date=itemView.findViewById(R.id.odt);
            sum=itemView.findViewById(R.id.oamt);
            shipping=itemView.findViewById(R.id.ship_amt);
            status=itemView.findViewById(R.id.ostat);


        }
    }
}
