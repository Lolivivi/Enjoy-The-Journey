package com.riven.journey.adapter.recyclerAdapter;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.riven.journey.R;
import com.riven.journey.bean.ConcernUser;
import com.riven.journey.util.ConfigUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * @author 张硕
 */
public class ConcernRecyclerAdapter extends RecyclerView.Adapter<ConcernRecyclerAdapter.VH> {
    //传数据
    private List<String> list;
    private List<String> urls;
    private List<ConcernUser> usesInfo;
    private Context mContext;

    public static class VH extends RecyclerView.ViewHolder{
        public TextView tvUserName;
        public RecyclerView recyclerView;
        private ImageView ivUserPhoto;
        private TextView tvRelTime;
        private TextView tvLike;
        private TextView tvColl;
        private TextView tvComm;
        private TextView tvRelTitle;
        private LikeButton likeBtn;
        private LikeButton collBtn;
        private LinearLayout linerComments;
        private ImageView ivComImg;
        private LinearLayout linerMain;
        public VH(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tv_found_concern_user_name);
            recyclerView = itemView.findViewById(R.id.rec_found_concern_img);
            ivUserPhoto = itemView.findViewById(R.id.iv_found_concern_user_photo);
            tvRelTime = itemView.findViewById(R.id.tv_found_concern_relTime);
            tvLike = itemView.findViewById(R.id.tv_found_concern_like);
            tvColl = itemView.findViewById(R.id.tv_found_concern_coll);
            tvComm = itemView.findViewById(R.id.tv_found_concern_comments);
            tvRelTitle = itemView.findViewById(R.id.tv_found_concern_rel_title);
            likeBtn = itemView.findViewById(R.id.iv_found_concern_like);
            collBtn = itemView.findViewById(R.id.iv_found_concern_coll);
            linerComments = itemView.findViewById(R.id.liner_found_concern_comment);
            ivComImg = itemView.findViewById(R.id.iv_found_concern_comm);
            linerMain = itemView.findViewById(R.id.liner_found_concern_main);
        }
    }

    public ConcernRecyclerAdapter(List<ConcernUser> usesInfo, Context mContext) {
        this.usesInfo = usesInfo;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_found_concern_item,parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        //用户名
        holder.tvUserName.setText(usesInfo.get(position).getUserName());
        //头像
        Glide
            .with(mContext)
            .asBitmap()
            .load(ConfigUtil.BASE_URL +usesInfo.get(position).getUserPhoto())
            .apply(RequestOptions.bitmapTransform(new CircleCrop()))
            .into(holder.ivUserPhoto);
        //获取时间
        holder.tvRelTime.setText(usesInfo.get(position).getRelTime());
        //点赞数
        if(usesInfo.get(position).getArtLike().equals("0")){
            holder.tvLike.setText("点赞");
        }else {
            holder.tvLike.setText(usesInfo.get(position).getArtLike());
        }
        //收藏数
        if(!usesInfo.get(position).getArtColl().equals("0")){
            holder.tvColl.setText(usesInfo.get(position).getArtColl());
        }else{
            holder.tvColl.setText("收藏");
        }
        //评论数
        if(!usesInfo.get(position).getArtComCount().equals("0")){
            holder.tvComm.setText(usesInfo.get(position).getArtComCount());
        }else{
            holder.tvComm.setText("评论");
        }
        //标题
        holder.tvRelTitle.setText(usesInfo.get(position).getArtTitle());

        //判断是否点赞
        if(usesInfo.get(position).getIsLike().equals("1")){
            holder.likeBtn.onClick(holder.likeBtn);
        }
        //判断是否收藏
        if (usesInfo.get(position).getIsColl().equals("1")){
            holder.collBtn.onClick(holder.collBtn);
        }
        //点赞监听事件
        holder.likeBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if(usesInfo.get(position).getArtLike().equals("0")){
                    holder.tvLike.setText("1");
                    usesInfo.get(position).setArtLike("1");
                    onClickLike(position);
                }else{
                    int newCount = Integer.parseInt(usesInfo.get(position).getArtLike())+1;
                    usesInfo.get(position).setArtLike(newCount+"");
                    holder.tvLike.setText(newCount+"");
                    onClickLike(position);
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if(usesInfo.get(position).getArtLike().equals("1")){
                    holder.tvLike.setText("点赞");
                    usesInfo.get(position).setArtLike("0");
                    unClickLike(position);
                }else{
                    int newCount = Integer.parseInt(usesInfo.get(position).getArtLike())-1;
                    usesInfo.get(position).setArtLike(newCount+"");
                    holder.tvLike.setText(newCount+"");
                }
            }
        });
        //收藏事件监听
        holder.collBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if(usesInfo.get(position).getArtColl().equals("0")){
                    holder.tvColl.setText("1");
                    usesInfo.get(position).setArtColl("1");
                }else{
                    int newCount = Integer.parseInt(usesInfo.get(position).getArtColl())+1;
                    usesInfo.get(position).setArtColl(newCount+"");
                    holder.tvColl.setText(newCount+"");
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if(usesInfo.get(position).getArtColl().equals("1")){
                    holder.tvColl.setText("点赞");
                    usesInfo.get(position).setArtColl("0");
                }else{
                    int newCount = Integer.parseInt(usesInfo.get(position).getArtColl())-1;
                    usesInfo.get(position).setArtColl(newCount+"");
                    holder.tvColl.setText(newCount+"");
                }
            }
        });
        //横向滑动图片
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        holder.recyclerView.setLayoutManager(manager);
        holder.recyclerView.setAdapter(new ConcernImgRecyclerAdapter(
                usesInfo.get(position).getImgUrls(),
                usesInfo.get(position).getFirstImg(),
                mContext));
        holder.recyclerView.setOnFlingListener(null);
        new PagerSnapHelper().attachToRecyclerView(holder.recyclerView);
        //评论图片监听器
        if(!usesInfo.get(position).getArtComCount().equals("0")){
            holder.ivComImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showComments(holder,mContext,position);
                }
            });
        }else{
            holder.ivComImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showWriteComments(holder,mContext,position);
                }
            });
        }
        //评论监听器
        holder.linerComments.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
               showComments(holder,mContext,position);
            }

        });
    }




    @Override
    public int getItemCount() {
        return usesInfo.size();
    }

    //弹出输入评论窗口
    private void showWriteComments(VH holder, Context mContext, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_write_comments,null);
        EditText edtWrite = view.findViewById(R.id.edt_popupwindow_write);
        PopupWindow popupWindow = new PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
//        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //是否具有获取焦点的能力
        popupWindow.setFocusable(true);
        edtWrite.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(10000, InputMethodManager.HIDE_NOT_ALWAYS);

        //显示PopupWindow
        View rootview = LayoutInflater.from(mContext).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    //弹出评论弹出框
    private void showComments(@NonNull VH holder,Context mContext,int position){
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popView = layoutInflater.inflate(R.layout.upupwindow_comments, null);
        TextView tvComCount = popView.findViewById(R.id.tv_popupwindow_comment_count);
        TextView tvWrite = popView.findViewById(R.id.tv_popupwindow_comment_write);
        //设置编辑框监听器
        tvWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWriteComments(holder,mContext,position);
            }
        });
        tvComCount.setText(usesInfo.get(position).getArtComCount());
        PopupWindow popupWindow = new PopupWindow(mContext);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(1600);
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        WindowManager.LayoutParams lp = ((Activity)holder.linerMain.getContext()).getWindow().getAttributes();
        lp.alpha = 0.6f;
        ((Activity)holder.linerMain.getContext()).getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity)holder.linerMain.getContext()).getWindow().getAttributes();
                lp.alpha = 1.0f;
                ((Activity)holder.linerMain.getContext()).getWindow().setAttributes(lp);
            }
        });
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setContentView(popView);
        popupWindow.showAtLocation(holder.linerMain,Gravity.BOTTOM,0,0);
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
                    out.write(("tel=1&note_id="+usesInfo.get(position).getArtId()).getBytes());
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
                    out.write(("tel=1&note_id="+usesInfo.get(position).getArtId()).getBytes());
                    InputStream in = conn.getInputStream();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
