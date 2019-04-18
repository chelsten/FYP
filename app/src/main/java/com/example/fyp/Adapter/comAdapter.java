package com.example.fyp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fyp.AskQuestion;
import com.example.fyp.Message;
import com.example.fyp.R;
import com.example.fyp.SessionHandler;
import com.example.fyp.msgList;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by ROG on 4/4/2018.
 */

public class comAdapter extends RecyclerView.Adapter<comAdapter.HistoryViewHolder> {
    private Context mCtx;
    private List<commentAdapterList> commentList;
    private SessionHandler session;

    public comAdapter(Context mCtx, List<commentAdapterList> commentList) {
        this.mCtx = mCtx;
        this.commentList = commentList;
    }

    @Override
    @NonNull
    public HistoryViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.comment_list_item, null);
        return new HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  HistoryViewHolder holder, int position) {
        commentAdapterList product = commentList.get(position);
        Glide.with(mCtx)
                .load(product.getPic())
                .transition(withCrossFade())
                .into(holder.pic);
        holder.full_name.setText(product.getFull_name());
        holder.comment.setText(product.getFarmerPost());
        holder.comment_id.setText(product.getCommentID());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView full_name;
        TextView comment;
        TextView comment_id;
        ImageView pic;
        RelativeLayout rl2;
        public HistoryViewHolder(View itemView) {
            super(itemView);
            full_name = itemView.findViewById(R.id.full_name);
            comment = itemView.findViewById(R.id.comment);
            pic = itemView.findViewById(R.id.pic);
            comment_id = itemView.findViewById(R.id.comment_id);
            rl2 = itemView.findViewById(R.id.rl2);
            rl2.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Intent i = new Intent(mCtx, Message.class);
            String commentID = comment_id.getText().toString();
            i.putExtra("id", commentID);
            mCtx.startActivity(i);
        }
    }
}
