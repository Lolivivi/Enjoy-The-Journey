package com.example.mybaidupanorma.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybaidupanorma.activity.DetailActivity;
import com.example.mybaidupanorma.R;
import com.example.mybaidupanorma.bean.MyWorks;

import java.util.List;

/**
 * @author 田春雨
 * 我的作品布局
 */
public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<MyWorks> list;


    public GridAdapter(Context context, List<MyWorks> list) {
        this.context = context;
        this.list = list;
    }


//    @NonNull
//    @Override
//    public GridAdapter.GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new GridViewHolder(LayoutInflater.from(context).inflate(R.layout.grid_view, parent, false));
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0) {
            //你的item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.grid_view, viewGroup, false);
            GridViewHolder viewHolder =new GridViewHolder(view);
            return viewHolder;
        } else {
            //底部“加载更多”item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.foot_view, viewGroup, false);
            FooterHolder footerHolder = new FooterHolder(view);
            return footerHolder;
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof GridViewHolder) {
            //展示数据
            ((GridViewHolder)holder).photo.setImageBitmap(list.get(position).getPhoto());
            ((GridViewHolder)holder).text.setText(list.get(position).getTitle());
            ((GridViewHolder)holder).main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("noteId", list.get(position).getId());
                    context.startActivity(intent);
                }
            });
        }else if(holder instanceof FooterHolder){
            //底部“加载更多”item （等待动画用一个gif去实现）
//            Glide.with(context).load(R.mipmap.download)
//                    .into(((FooterHolder) holder).ivLoad);
        }
    }

//    @Override
//    public void onBindViewHolder(@NonNull GridAdapter.GridViewHolder holder, final int position) {
//        holder.text.setText(list.get(position).getTitle());
//        holder.photo.setImageBitmap(list.get(position).getPhoto());
//    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) {
            //最后一个 是底部item
            return 1;
        } else {
            return 0;
        }
    }

    class GridViewHolder extends RecyclerView.ViewHolder {

        private TextView text;
        private ImageView photo;
        private LinearLayout main;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            text = itemView.findViewById(R.id.text);
            main=itemView.findViewById(R.id.ll_main);

        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder{

        private ImageView ivLoad;
        public FooterHolder(@NonNull View itemView) {
            super(itemView);
            ivLoad=itemView.findViewById(R.id.foot_view_progressbar);
        }
    }


}

