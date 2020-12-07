package com.example.mybaidupanorma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mybaidupanorma.R;

import java.util.List;

/**
 * @author 于喜梅
 */
public class ConcernImgRecyclerAdapter extends RecyclerView.Adapter<ConcernImgRecyclerAdapter.viewHolder>{
        //图片地址数组
        private List<String> urls;
        private Context mContext;

        public ConcernImgRecyclerAdapter(List<String> urls,Context context) {
            this.urls = urls;
            this.mContext = context;
        }


        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder holder, int position) {
            Glide.with(mContext)
                    .load(urls.get(position))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.loading)//正在加载时显示的图片
                    .error(R.drawable.failure)//永久加载失败时显示的图片
                    .fallback(R.drawable.notexist)//表示图片地址为null时显示的图片
                    .into(holder.img);
        }

        @Override
        public int getItemCount() {
            return urls.size();
        }

        public class viewHolder extends RecyclerView.ViewHolder{
            public ImageView img;
            public viewHolder(@NonNull View itemView) {
                super(itemView);
                img = itemView.findViewById(R.id.iv_found_concern_cr_img);
            }
        }

}
