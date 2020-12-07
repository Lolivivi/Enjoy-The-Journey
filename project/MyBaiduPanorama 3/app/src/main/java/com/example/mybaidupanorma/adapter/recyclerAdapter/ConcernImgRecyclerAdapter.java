package com.example.mybaidupanorma.adapter.recyclerAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mybaidupanorma.activity.DetailActivity;
import com.example.mybaidupanorma.R;
import com.example.mybaidupanorma.activity.ShowVideoActivity;
import com.example.mybaidupanorma.bean.ConcernUser;
import com.example.mybaidupanorma.util.ConfigUtil;
import com.example.mybaidupanorma.util.ImageViewPlay;


import java.util.List;

/**
 * @author 张硕
 */
public class ConcernImgRecyclerAdapter extends RecyclerView.Adapter<ConcernImgRecyclerAdapter.ViewHolder>{
    //图片地址数组
    private List<String> urls;
    private String ImgType;
    private Context mContext;
    private FragmentActivity activity;
    private List<ConcernUser> usesInfo;

    public ConcernImgRecyclerAdapter(List<String> urls, String ImgType, Context mContext,FragmentActivity activity,List<ConcernUser> usesInfo) {
        this.urls = urls;
        this.ImgType = ImgType;
        this.mContext = mContext;
        this.activity = activity;
        this.usesInfo = usesInfo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_found_concern_img_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        if(ImgType.equals("video")){
            holder.img.setType(ImageViewPlay.TYPE_VIDEO);
        }

        setViews(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Glide.with(mContext).asBitmap().load(ConfigUtil.BASE_URL+urls.get(position)).into(holder.img);
            }
        });
        if(ImgType.equals("video")){
           holder.img.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(activity, ShowVideoActivity.class);
                   intent.putExtra("path",ConfigUtil.BASE_URL+urls.get(position));
                   intent.putExtra("nodeId",usesInfo.get(position).getArtId());
                   intent.putExtra("islike",usesInfo.get(position).getIsLike());
                   intent.putExtra("numlike",usesInfo.get(position).getArtLike());
                   intent.putExtra("numComm",usesInfo.get(position).getArtComCount());
                   mContext.startActivity(intent);
               }
           });
        }else{
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra("noteId", Integer.parseInt(usesInfo.get(position).getArtId()));
                    mContext.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageViewPlay img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_found_concern_cr_img);

        }
    }
    public void setViews(final ViewHolder holder){
        Glide.with(mContext).asBitmap()
                .load(ConfigUtil.BASE_URL+urls.get(0))
                .listener(new RequestListener<Bitmap>() {//设置图片大小随布局比例填充
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(final Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                int imageWidth = resource.getWidth();
                                int imageHeight = resource.getHeight();
                                Display dp = ((Activity)holder.img.getContext()).getWindowManager().getDefaultDisplay();
                                int screenWidth = dp.getWidth();
                                ViewGroup.LayoutParams params = holder.img.getLayoutParams();
                                params.height = (screenWidth * imageHeight)/ imageWidth;
                                holder.img.setLayoutParams(params);
                            }
                        });

                        return false;
                    }
                } ).into(holder.img);
    }



}
