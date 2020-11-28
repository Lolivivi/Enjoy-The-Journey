package com.riven.journey.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.riven.journey.MainActivity;
import com.riven.journey.R;
import com.riven.journey.bean.RecommendUser;
import com.riven.journey.util.ConfigUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.MyViewHolder> {
    //声明引用
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<RecommendUser> users;
    private String imgUrl;

    public IndexAdapter(Context mContext, List<RecommendUser> users) {
        this.mContext = mContext;
        this.users = users;
        //利用LayoutInflater把控件所在的布局文件加载到当前类当中
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //返回一个ViewHolder类型的数据，返回ViewHolder子类的对象，把布局文件加载到当前类返回
        return new MyViewHolder(mLayoutInflater.inflate(R.layout.list_found_recommend_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //设置图片
        Glide
                .with(mContext)
                .asBitmap()
                .load(ConfigUtil.BASE_URL+users.get(position).getImgUrl())
                .skipMemoryCache(true)//缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)//缓存
                .listener(new RequestListener<Bitmap>() {//设置图片大小随布局比例填充
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        int width = resource.getWidth();
                        int height = resource.getHeight();
                        Display dp = ((Activity)holder.ivMain.getContext()).getWindowManager().getDefaultDisplay();
                        int screenWidth = dp.getWidth();
                        ViewGroup.LayoutParams params = holder.ivMain.getLayoutParams();
                        params.width = screenWidth/2;
                        params.height = (params.width * height)/ width;
                        holder.ivMain.setLayoutParams(params);
                        return false;
                    }
                })
                .into(holder.ivMain);
        imgUrl = ConfigUtil.BASE_URL+users.get(position).getImgUrl();
        //标题
        holder.tvTitle.setText(users.get(position).getTitle());
        //头像
        Glide
                .with(mContext)
                .asBitmap()
                .load(ConfigUtil.BASE_URL+users.get(position).getUserImg())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))//圆形剪裁
                .into(holder.ivHeader);
        //作者名
        holder.userName.setText(users.get(position).getUserName());
        //点赞数
        if(users.get(position).getLikeCount().equals("0")){
            holder.likeCount.setText("点赞");
        }else {
            holder.likeCount.setText(users.get(position).getLikeCount());
        }
        //判断是否点赞
        if(users.get(position).getArtIsLike().equals("1")){
            holder.likeBtn.onClick(holder.likeBtn);
        }
        //点赞监听器
        holder.likeBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if(users.get(position).getLikeCount().equals("0")){
                    holder.likeCount.setText("1");
                    users.get(position).setLikeCount("1");
                }else{
                    int newCount = Integer.parseInt(users.get(position).getLikeCount())+1;
                    users.get(position).setLikeCount(newCount+"");
                    holder.likeCount.setText(newCount+"");
                }
                onClickLike(position);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if(users.get(position).getLikeCount().equals("1")){
                    holder.likeCount.setText("点赞");
                    users.get(position).setLikeCount("0");
                }else{
                    int newCount = Integer.parseInt(users.get(position).getLikeCount())-1;
                    users.get(position).setLikeCount(newCount+"");
                    holder.likeCount.setText(newCount+"");
                }
            }
        });

        //跳转详情
        holder.ivMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:修改类名
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("noteId", users.get(position).getArtId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivMain;
        private TextView tvTitle;
        private ImageView ivHeader;
        private TextView userName;
        private TextView likeCount;
        private LikeButton likeBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMain = itemView.findViewById(R.id.iv_found_recommend_main);
            tvTitle = itemView.findViewById(R.id.tv_found_recommend_title);
            ivHeader = itemView.findViewById(R.id.iv_found_recommend_user_photo);
            userName = itemView.findViewById(R.id.tv_found_recommend_user_name);
            likeCount = itemView.findViewById(R.id.tv_found_recommend_like_count);
            likeBtn = itemView.findViewById(R.id.iv_found_recommend_like);
        }
    }

    //上传点赞事件
    private void onClickLike(int position) {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.BASE_URL+"love/followInterfaceLove");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    OutputStream out = conn.getOutputStream();
                    out.write(("tel=1&note_id="+users.get(position).getArtId()).getBytes());
                    InputStream in = conn.getInputStream();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //上传取消点赞事件
    private void unClickLike(int position) {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.BASE_URL+"love/followInterfaceCancelLove");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    OutputStream out = conn.getOutputStream();
                    out.write(("tel=1&note_id="+users.get(position).getArtId()).getBytes());
                    InputStream in = conn.getInputStream();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
