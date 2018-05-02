package com.example.a37046.foods.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a37046.foods.R;
import com.example.a37046.foods.entity.CommentDetail;
import com.example.a37046.foods.entity.HomeEntity;

import java.util.List;

public class CommentDetailAdapter extends RecyclerView.Adapter<CommentDetailAdapter.Viewholder>{

    List<CommentDetail> commentDetailList;

    HomeEntity homeEntity;
    public CommentDetailAdapter(List<CommentDetail> commentDetailList) {
        this.commentDetailList = commentDetailList;
    }

    @NonNull
    @Override
    public CommentDetailAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_comment_detail,parent,false);
        final Viewholder holder=new Viewholder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentDetailAdapter.Viewholder holder, int position) {
        CommentDetail commentDetail = commentDetailList.get(position);
        holder.userName.setText(commentDetail.getUsername());
        holder.Content.setText(commentDetail.getContent());
        holder.Time.setText(commentDetail.getComment_time());
    }

    @Override
    public int getItemCount() {
        return commentDetailList.size();
    }

    static class Viewholder extends RecyclerView.ViewHolder{

        View comment;
        TextView userName;
        TextView Content;
        TextView Time;

        public Viewholder(View itemView) {
            super(itemView);
            comment=itemView;
            userName=itemView.findViewById(R.id.adapter_comment_username);
            Content=itemView.findViewById(R.id.adapter_comment_content);
            Time=itemView.findViewById(R.id.adapter_comment_time);
        }
    }
}
