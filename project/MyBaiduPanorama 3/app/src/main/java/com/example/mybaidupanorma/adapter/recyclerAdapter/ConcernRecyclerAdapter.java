package com.example.mybaidupanorma.adapter.recyclerAdapter;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.mybaidupanorma.bean.ConcernUser;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.example.mybaidupanorma.R;
import com.example.mybaidupanorma.adapter.expandableAdapter.CommentExpandAdapter;
import com.example.mybaidupanorma.bean.Comments;
import com.example.mybaidupanorma.bean.ConcernUser;
import com.example.mybaidupanorma.util.ConfigUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author 张硕
 */

public class ConcernRecyclerAdapter extends RecyclerView.Adapter<ConcernRecyclerAdapter.VH> {
    //传数据
    private List<ConcernUser> usesInfo;
    private Context mContext;
    private FragmentActivity activity;
    private String tel;

    public static class VH extends RecyclerView.ViewHolder{
        public TextView tvUserName;
        public RecyclerView recyclerView;
        public RecyclerView recComment;
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
            recComment = itemView.findViewById(R.id.rec_found_concern_comment_com);
        }
    }

    public ConcernRecyclerAdapter(List<ConcernUser> usesInfo, Context mContext,FragmentActivity activity) {
        this.usesInfo = usesInfo;
        this.mContext = mContext;
        this.activity = activity;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_found_concern_item,parent,false);
        SharedPreferences sp = mContext.getSharedPreferences("setting", Context.MODE_PRIVATE);
        tel = sp.getString("tel", "1");
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH holder, final int position) {
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
                    if(!usesInfo.get(position).getIsLike().equals("1")){
                        onClickLike(position);
                    }

                }else{
                    int newCount = Integer.parseInt(usesInfo.get(position).getArtLike())+1;
                    usesInfo.get(position).setArtLike(newCount+"");
                    holder.tvLike.setText(usesInfo.get(position).getArtLike());
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
                    holder.tvLike.setText(usesInfo.get(position).getArtLike());
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
//                    postRequest("http://10.7.89.227:8080/journeyjourney/collection/ShowCollectionDirectory?tel=1");
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
                usesInfo.get(position).getImgType(),
                mContext,activity,usesInfo));
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
        //获取展示的评论
        LinearLayoutManager manager2 = new LinearLayoutManager(mContext);
        manager2.setOrientation(RecyclerView.VERTICAL);
        holder.recComment.setLayoutManager(manager2);
        showShortCommets(holder,position);
    }


    @Override
    public int getItemCount() {
        return usesInfo.size();
    }

    //-------------------------------------------------------------------------------------
    //自定义方法

    //上拉显示数据
    public void loadMore(List<ConcernUser> newUser){
        usesInfo.addAll(usesInfo.size(),newUser);
        notifyDataSetChanged();
    }
    //展示界面少数评论
    private void showShortCommets(final VH holder, final int position) {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.BASE_URL+"comments/showComment");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    OutputStream out = conn.getOutputStream();
                    out.write(("note_id="+usesInfo.get(position).getArtId()).getBytes());
                    InputStream in = conn.getInputStream();
                    byte[] bytes = new byte[512];
                    StringBuffer buffer = new StringBuffer();
                    int len = -1;
                    while ((len = in.read(bytes,0,bytes.length))!=-1){
                        buffer.append(new String(bytes,0,len));
                    }
                    String result = buffer.toString();
                    JSONObject jConcernUser = new JSONObject(result);
                    JSONArray jArray = jConcernUser.getJSONArray("array");
                    final List<Comments> comments1 = new ArrayList<>();
                    for (int i = 0;i<jArray.length();i++){
                        Comments com = new Comments();
                        JSONObject jUser = jArray.getJSONObject(i);
                        com.setComUserHeader(jUser.getString("userHeadSrc"));
                        com.setComTime(jUser.getString("comments_time"));
                        com.setComment(jUser.getString("comments_content"));
                        com.setComUser(jUser.getString("userName"));
                        comments1.add(com);
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("size",comments1.size()+"");
                            holder.recComment.setAdapter(new ConcernCommentAdapter(comments1,mContext,position,holder));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //弹出输入评论窗口
    private void showWriteComments(final VH holder, final Context mContext, final int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_write_comments,null);
        final EditText edtWrite = view.findViewById(R.id.edt_popupwindow_write);
        Button btnUp = view.findViewById(R.id.btn_popupwindow_up);


        final PopupWindow popupWindow = new PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //是否具有获取焦点的能力
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        edtWrite.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(10000, InputMethodManager.HIDE_NOT_ALWAYS);

        //显示PopupWindow
        View rootview = LayoutInflater.from(mContext).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

        //发送按钮 监听事件
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = edtWrite.getText().toString().trim();
                if(TextUtils.isEmpty(comment)){
                    Toast.makeText(mContext,"您还没有输入内容",Toast.LENGTH_SHORT).show();
                }else {
                    //获取时间
                    Calendar calendar = Calendar.getInstance();
                    //获取系统的日期
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH)+1;
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    String sim = year+"-"+month+"-"+day+"-"+hour+":"+minute;
                    upDataComments(holder,edtWrite.getText().toString().trim(),position,sim);
                    popupWindow.dismiss();
                }

            }
        });
    }

    //发送评论
    private void upDataComments(@NonNull final VH holder, final String comment, final int position, final String sim) {
//        //获取用户手机号
//        SharedPreferences sp = mContext.getSharedPreferences("setting",Context.MODE_PRIVATE);
//        String tel = sp.getString("tel","1");
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.BASE_URL+"comments/oneComment");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    OutputStream out = conn.getOutputStream();
                    String noteId = usesInfo.get(position).getArtId();
                    //手机号
                    String data = "note_id="+noteId+"&comments_content="+comment+"&tel="+ tel + "&comments_time="+sim;
                    out.write(data.getBytes());
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    final String count = reader.readLine();
                    Log.e("评论数",count);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            holder.tvComm.setText(count);
                        }
                    });
                    usesInfo.get(position).setArtComCount(count);
                    notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
    //弹出评论弹出框
    public void showComments(@NonNull final VH holder, final Context mContext, final int position){
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popView = layoutInflater.inflate(R.layout.upupwindow_comments, null);
        //获取评论对象
        ExpandableListView elComment = popView.findViewById(R.id.el_popupwindow_comment);
        //初始化评论数据
        List<Comments> comments = new ArrayList<>();
        comments = initComment(position);

        //初始化控件内容
        initExpandableListView(elComment,comments);
        //关闭图片监听器
        ImageView ivClose = popView.findViewById(R.id.iv_popupwindow_colse);
        TextView tvComCount = popView.findViewById(R.id.tv_popupwindow_comment_count);
        TextView tvWrite = popView.findViewById(R.id.tv_popupwindow_comment_write);
        tvComCount.setText(usesInfo.get(position).getArtComCount());
        final PopupWindow popupWindow = new PopupWindow(mContext);
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
        ((Activity)holder.linerMain.getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setContentView(popView);
        popupWindow.showAtLocation(holder.linerMain,Gravity.BOTTOM,0,0);
        //设置编辑框监听器
        tvWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWriteCommentsToBottom(holder,mContext,position,popupWindow);
            }
        });
        //关闭图片监听器
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    //初始化弹出框中评论列表
    private void initExpandableListView(ExpandableListView elComment, List<Comments> comments) {
        elComment.setGroupIndicator(null);
        CommentExpandAdapter adapter = new CommentExpandAdapter(comments,mContext);
        elComment.setAdapter(adapter);
    }

    //获取评论
    private List<Comments> initComment(final int position) {
        final List<Comments> comments = new ArrayList<>();
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.BASE_URL+"comments/showComment");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    OutputStream out = conn.getOutputStream();
                    out.write(("note_id="+usesInfo.get(position).getArtId()).getBytes());
                    InputStream in = conn.getInputStream();
                    byte[] bytes = new byte[512];
                    StringBuffer buffer = new StringBuffer();
                    int len = -1;
                    while ((len = in.read(bytes,0,bytes.length))!=-1){
                        buffer.append(new String(bytes,0,len));
                    }
                    String result = buffer.toString();
                    Log.e("ssss",result);
                    JSONObject jConcernUser = new JSONObject(result);
                    JSONArray jArray = jConcernUser.getJSONArray("array");

                    for (int i = 0;i<jArray.length();i++){
                        Comments com = new Comments();
                        JSONObject jUser = jArray.getJSONObject(i);
                        com.setComUserHeader(jUser.getString("userHeadSrc"));
                        com.setComTime(jUser.getString("comments_time"));
                        com.setComment(jUser.getString("comments_content"));
                        com.setComUser(jUser.getString("userName"));
                        comments.add(com);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return comments;
    }

    //在展示区底部弹出评论
    private void showWriteCommentsToBottom(final VH holder, final Context mContext, final int position, PopupWindow popupWindows) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_write_comments,null);
        final EditText edtWrite = view.findViewById(R.id.edt_popupwindow_write);
        Button btnUp = view.findViewById(R.id.btn_popupwindow_up);
        final PopupWindow popupWindow = new PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //是否具有获取焦点的能力
        popupWindow.setFocusable(true);
        //点击外部消失
        WindowManager.LayoutParams lp = ((Activity)holder.linerMain.getContext()).getWindow().getAttributes();
        lp.alpha = 0.8f;
        ((Activity)holder.linerMain.getContext()).getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity)holder.linerMain.getContext()).getWindow().getAttributes();
                lp.alpha = 1.0f;
                ((Activity)holder.linerMain.getContext()).getWindow().setAttributes(lp);
            }
        });
        //软键盘覆盖底部控件
        ((Activity)holder.linerMain.getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        popupWindow.setOutsideTouchable(true);
        edtWrite.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(10000, InputMethodManager.HIDE_NOT_ALWAYS);

        //显示PopupWindow
        View rootview = LayoutInflater.from(mContext).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

        //发送按钮 监听事件
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = edtWrite.getText().toString().trim();
                if(TextUtils.isEmpty(comment)){
                    Toast.makeText(mContext,"您还没有输入内容",Toast.LENGTH_SHORT).show();
                }else {
                    //获取时间
                    Calendar calendar = Calendar.getInstance();
                    //获取系统的日期
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH)+1;
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    String sim = year+"-"+month+"-"+day+"-"+hour+":"+minute;
                    upDataComments(holder,edtWrite.getText().toString().trim(),position,sim);
                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    View v = ((Activity)holder.linerMain.getContext()).getWindow().peekDecorView();
                    if(imm.isActive()){
                        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    popupWindow.dismiss();
                }

            }
        });
    }

    //上传点赞事件
    private void onClickLike(final int position) {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.BASE_URL+"love/followInterfaceLove");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    OutputStream out = conn.getOutputStream();
                    out.write(("tel="+tel+"&note_id="+usesInfo.get(position).getArtId()).getBytes());
                    InputStream in = conn.getInputStream();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //上传取消点赞事件
    private void unClickLike(final int position) {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.BASE_URL+"love/followInterfaceCancelLove");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    OutputStream out = conn.getOutputStream();
                    out.write(("tel=" +tel+"&note_id="+usesInfo.get(position).getArtId()).getBytes());
                    InputStream in = conn.getInputStream();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
