package com.example.a37046.foods.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

public class AllUserCommentAdapter extends RecyclerView.Adapter<AllUserCommentAdapter.ViewHolder>{
    List<AllUserOrder> allUserComment;


    public AllUserCommentAdapter(List<AllUserOrder> allUserComment) {
        this.allUserComment = allUserComment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_all_user_comment,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final AllUserOrder userComment = allUserComment.get(position);
        holder.content.setText(userComment.getContent());
        holder.shopName.setText(userComment.getShopname());
        holder.foodName.setText(userComment.getFoodname());
        holder.orderTime.setText(userComment.getOrdertime());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteComments(userComment);
                allUserComment.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });
        holder.modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(v.getContext())
                        .title("修改评论")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                updateComment(userComment,input.toString());
                                holder.content.setText(input.toString());
                            }
                        }).show();
            }
        });
    }
    //updateComment(userComment,content);
    private void updateComment(final AllUserOrder userComment, final String content) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://60.205.189.39/updateComment.do?order_id="+userComment.getOrder_id()+"&content="+content)
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void deleteComments(final AllUserOrder allUserOrder){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://60.205.189.39/deleteComment.do?order_id="+allUserOrder.getOrder_id())
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
        return allUserComment.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.adapter_all_user_comment_food_name)
        TextView foodName;
        @BindView(R.id.adapter_all_user_comment_content)
        TextView content;
        @BindView(R.id.adapter_all_user_comment_shop_name)
        TextView shopName;
        @BindView(R.id.adapter_all_user_comment_order_time)
        TextView orderTime;
        @BindView(R.id.adapter_all_user_comment_modify)
        Button modify;
        @BindView(R.id.adapter_all_user_comment_delete)
        Button delete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
