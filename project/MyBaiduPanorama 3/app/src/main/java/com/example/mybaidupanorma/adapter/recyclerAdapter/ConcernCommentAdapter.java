package com.example.mybaidupanorma.adapter.recyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybaidupanorma.R;
import com.example.mybaidupanorma.bean.Comments;

import java.util.List;

/**
 * @author 张硕
 */
public class ConcernCommentAdapter extends RecyclerView.Adapter<ConcernCommentAdapter.ViewHolder>{
    //图片地址数组
    private List<Comments> comments;
    private Context mContext;
    private int position;
    private ConcernRecyclerAdapter.VH holder;

    public ConcernCommentAdapter(List<Comments> comments, Context mContext, int position, ConcernRecyclerAdapter.VH holder) {
        this.comments = comments;
        this.mContext = mContext;
        this.position = position;
        this.holder = holder;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_found_concern_comment_item,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvComment.setText(comments.get(position).getComment());
        holder.tvName.setText(comments.get(position).getComUser());
    }

    @Override
    public int getItemCount() {
        if(comments.size()>2){
            return 2;
        }
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private TextView tvComment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_found_concern_comments_name);
            tvComment = itemView.findViewById(R.id.tv_found_concern_comments_comment);
        }
    }

}
