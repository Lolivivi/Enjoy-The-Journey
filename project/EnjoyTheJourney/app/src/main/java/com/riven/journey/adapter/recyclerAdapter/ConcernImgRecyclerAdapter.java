package com.riven.journey.adapter.recyclerAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.riven.journey.R;
import com.riven.journey.util.ConfigUtil;

import java.util.List;

/**
 * @author 张硕
 */
public class ConcernImgRecyclerAdapter extends RecyclerView.Adapter<ConcernImgRecyclerAdapter.viewHolder>{
    //图片地址数组
    private List<String> urls;
    private String firstImg;
    private Context mContext;

    public ConcernImgRecyclerAdapter(List<String> urls, String firstImg, Context mContext) {
        this.urls = urls;
        this.firstImg = firstImg;
        this.mContext = mContext;
        Log.e("啊实打实的多多多多多多多多多多多Glid:",urls.size()+"");
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_found_concern_img_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        if(position==0){
            Glide.with(mContext).load(ConfigUtil.BASE_URL+firstImg).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.img);
        }else {
            if(position!=urls.size()-1){
                Glide.with(mContext).load(ConfigUtil.BASE_URL+urls.get(position)).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.img);
            }
        }

    }

    @Override
    public int getItemCount() {
        return urls.size()-1;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        public ImageView img;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_found_concern_cr_img);
            //Glide获取图片宽高
            Glide.with(mContext).asBitmap().load(ConfigUtil.BASE_URL+firstImg).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    int imageWidth = resource.getWidth();
                    int imageHeight = resource.getHeight();
                    Display dp = ((Activity)img.getContext()).getWindowManager().getDefaultDisplay();
                    int screenWidth = dp.getWidth();
                    ViewGroup.LayoutParams params = img.getLayoutParams();
                    params.height = (screenWidth * imageHeight)/ imageWidth;
                    img.setLayoutParams(params);
                }
            });
        }
    }


}
