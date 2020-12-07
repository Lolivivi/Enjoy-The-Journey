package com.example.mybaidupanorma.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mybaidupanorma.R;
import com.example.mybaidupanorma.adapter.expandableAdapter.CommentExpandAdapter;
import com.example.mybaidupanorma.bean.Comments;
import com.example.mybaidupanorma.bean.VideoUser;
import com.example.mybaidupanorma.util.ConfigUtil;
import com.like.LikeButton;
import com.like.OnLikeListener;

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

import cn.jzvd.JzvdStd;

/**
 * @author 张硕
 */
public class ShowVideoActivity extends AppCompatActivity {
    private JzvdStd jzVideo;
    private TextView tvLike;
    private TextView tvColl;
    private TextView tvComm;
    private LikeButton likeBtn;
    private LikeButton collBtn;
    private ImageView ivComImg;
    private String tel;
    private VideoUser users = new VideoUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_show_video);
        //绑定控件
        setView();
        jzVideo = findViewById(R.id.jz_show_video);
        Intent request = getIntent();
        String path    = request.getStringExtra("path");
        users.setIsLike(request.getStringExtra("islike"));
        users.setNodeId(request.getStringExtra("nodeId"));
        users.setNumLike(request.getStringExtra("numlike"));
        users.setNumComments(request.getStringExtra("numComm"));
        jzVideo.setUp(path,"");
        jzVideo.startVideoAfterPreloading();
        SharedPreferences sp = getSharedPreferences("setting",Context.MODE_PRIVATE);
        tel = sp.getString("tel","1");
        tvComm.setText(users.getNumComments());
        //点赞
        if(users.getIsLike().equals("1")){
            likeBtn.onClick(likeBtn);
            tvLike.setText(users.getNumLike());
        }
        likeBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if(users.getNumLike().equals("0")){
                    tvLike.setText("1");
                    users.setNumLike("1");
                    if(!users.getIsLike().equals("1")){
                        onClickLike();
                    }
                }else{
                    int newCount = Integer.parseInt(users.getNumLike())+1;
                    users.setNumLike(newCount+"");
                    tvLike.setText(newCount+"");
                    onClickLike();
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if(users.getNumLike().equals("1")){
                    tvLike.setText("点赞");
                    users.setNumLike("0");
                    unClickLike();
                }else{
                    int newCount = Integer.parseInt(users.getNumLike())-1;
                    users.setNumLike(newCount+"");
                    tvLike.setText(newCount+"");
                }
            }
        });

        //评论监听器
        ivComImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showComments();
            }
        });

    }


    //绑定控件
    private void setView() {
        jzVideo = findViewById(R.id.jz_show_video);
        tvLike = findViewById(R.id.tv_show_video_like);
        tvColl = findViewById(R.id.tv_show_video_coll);
        tvComm = findViewById(R.id.tv_show_video_comments);
        likeBtn = findViewById(R.id.iv_show_video_like);
        collBtn = findViewById(R.id.iv_show_video_coll);
        ivComImg = findViewById(R.id.iv_show_video_comm);

    }
    //上传点赞事件
    private void onClickLike() {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.BASE_URL+"love/followInterfaceLove");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    OutputStream out = conn.getOutputStream();
                    out.write(("tel="+tel+"&note_id="+users.getNodeId()).getBytes());
                    InputStream in = conn.getInputStream();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //上传取消点赞事件
    private void unClickLike() {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.BASE_URL+"love/followInterfaceCancelLove");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    OutputStream out = conn.getOutputStream();
                    out.write(("tel="+tel+"&note_id="+users.getNodeId()).getBytes());
                    InputStream in = conn.getInputStream();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //初始化弹出框中评论列表
    private void initExpandableListView(ExpandableListView elComment, List<Comments> comments) {
        elComment.setGroupIndicator(null);
        CommentExpandAdapter adapter = new CommentExpandAdapter(comments,this);
        elComment.setAdapter(adapter);
    }

    //弹出评论弹出框
    public void showComments(){
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popView = layoutInflater.inflate(R.layout.upupwindow_comments, null);
        //获取评论对象
        ExpandableListView elComment = popView.findViewById(R.id.el_popupwindow_comment);
        //初始化评论数据
        List<Comments> comments = new ArrayList<>();
        comments = initComment();

        //初始化控件内容
        initExpandableListView(elComment,comments);
        //关闭图片监听器
        ImageView ivClose = popView.findViewById(R.id.iv_popupwindow_colse);
        TextView tvComCount = popView.findViewById(R.id.tv_popupwindow_comment_count);
        TextView tvWrite = popView.findViewById(R.id.tv_popupwindow_comment_write);
        tvComCount.setText(users.getNumComments());
        final PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(1600);
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        WindowManager.LayoutParams lp = ((Activity)popView.getContext()).getWindow().getAttributes();
        lp.alpha = 0.6f;
        ((Activity)popView.getContext()).getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity)popView.getContext()).getWindow().getAttributes();
                lp.alpha = 1.0f;
                ((Activity)popView.getContext()).getWindow().setAttributes(lp);
            }
        });
        ((Activity)popView.getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setContentView(popView);
        popupWindow.showAtLocation(popView, Gravity.BOTTOM,0,0);
        //设置编辑框监听器
        tvWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWriteCommentsToBottom(popupWindow);
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
    private List<Comments> initComment() {
        final List<Comments> comments = new ArrayList<>();
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.BASE_URL+"comments/showComment");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    OutputStream out = conn.getOutputStream();
                    out.write(("note_id="+users.getNodeId()).getBytes());
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
    private void showWriteCommentsToBottom(PopupWindow popupWindows) {
        final View view = LayoutInflater.from(this).inflate(R.layout.popupwindow_write_comments,null);
        final EditText edtWrite = view.findViewById(R.id.edt_popupwindow_write);
        Button btnUp = view.findViewById(R.id.btn_popupwindow_up);
        final PopupWindow popupWindow = new PopupWindow(view, android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //是否具有获取焦点的能力
        popupWindow.setFocusable(true);
        //点击外部消失
        WindowManager.LayoutParams lp = ((Activity)view.getContext()).getWindow().getAttributes();
        lp.alpha = 0.8f;
        ((Activity)view.getContext()).getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity)view.getContext()).getWindow().getAttributes();
                lp.alpha = 1.0f;
                ((Activity)view.getContext()).getWindow().setAttributes(lp);
            }
        });
        //软键盘覆盖底部控件
        ((Activity)view.getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        popupWindow.setOutsideTouchable(true);
        edtWrite.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(10000, InputMethodManager.HIDE_NOT_ALWAYS);

        //显示PopupWindow
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

        //发送按钮 监听事件
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = edtWrite.getText().toString().trim();
                if(TextUtils.isEmpty(comment)){
                    Toast.makeText(getApplicationContext(),"您还没有输入内容",Toast.LENGTH_SHORT).show();
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
                    upDataComments(edtWrite.getText().toString().trim(),sim);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    View v = ((Activity)view.getContext()).getWindow().peekDecorView();
                    if(imm.isActive()){
                        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    popupWindow.dismiss();
                }

            }
        });
    }
    //发送评论
    private void upDataComments(final String comment, final String sim) {
        //获取用户手机号
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.BASE_URL+"comments/oneComment");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    OutputStream out = conn.getOutputStream();
                    String noteId = users.getNodeId();
                    //手机号
                    String data = "note_id="+noteId+"&comments_content="+comment+"&tel="+tel+"&comments_time="+sim;
                    out.write(data.getBytes());
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    final String count = reader.readLine();
                    Log.e("评论数",count);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            tvComm.setText(count);
                        }
                    });
                    users.setNumComments(count);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}