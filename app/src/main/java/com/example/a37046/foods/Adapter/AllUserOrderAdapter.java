package com.example.a37046.foods.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.a37046.foods.R;
import com.example.a37046.foods.entity.AllUserOrder;


import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AllUserOrderAdapter extends RecyclerView.Adapter<AllUserOrderAdapter.Viewholder>{

    List<AllUserOrder> allUserOrders;

    public AllUserOrderAdapter(List<AllUserOrder> allUserOrders) {
        this.allUserOrders = allUserOrders;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_all_user_order,parent,false);
        Viewholder viewholder=new Viewholder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, int position) {
        final AllUserOrder allUserOrder = allUserOrders.get(position);
        holder.shopName.setText(allUserOrder.getShopname());
        holder.foodName.setText(allUserOrder.getFoodname());
        holder.price.setText(allUserOrder.getPrice()+"￥");
        holder.time.setText(allUserOrder.getOrdertime());

        if (allUserOrder.getComment_time()!=null){
            holder.assess.setVisibility(View.GONE);
        }
        holder.assess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new MaterialDialog.Builder(v.getContext())
                        .title("评论")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                submitComments(allUserOrder,input.toString());
                                holder.assess.setVisibility(View.GONE);
                            }
                        }).show();

            }
        });
    }

    public void submitComments(final AllUserOrder allUserOrder, final String content){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://60.205.189.39/insertComment.do?order_id="+allUserOrder.getOrder_id()+"&content="+content)
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public int getItemCount() {
        return allUserOrders.size();
    }

    static class Viewholder extends RecyclerView.ViewHolder{
        private View v;

        @BindView(R.id.adapter_all_user_order_shop_name)
        TextView shopName;

        @BindView(R.id.adapter_all_user_order_food_name)
        TextView foodName;

        @BindView(R.id.adapter_all_user_order_time)
        TextView time;

        @BindView(R.id.adapter_all_user_order_price)
        TextView price;

        @BindView(R.id.adapter_all_user_order_assess)
        Button assess;

        public Viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            v=itemView;

        }
    }
}
