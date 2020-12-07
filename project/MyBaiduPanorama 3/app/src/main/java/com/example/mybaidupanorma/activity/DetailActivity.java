package com.example.mybaidupanorma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mybaidupanorma.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.example.mybaidupanorma.adapter.CommentExpandAdapter;
import com.example.mybaidupanorma.adapter.CommentExpandableListView;
import com.example.mybaidupanorma.adapter.ConcernImgRecyclerAdapter;
import com.example.mybaidupanorma.util.CommentDetailBean;
import com.example.mybaidupanorma.util.ConfigUtil;
import com.example.mybaidupanorma.util.ReplyDetailBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author 于喜梅
 */
public class DetailActivity extends AppCompatActivity {
    private List<String> urls = new ArrayList<>();
    private RecyclerView recyclerView;
    private Button btnBack;
    private ImageView ivHeadImg;//头像
    private TextView tvUserName;//昵称
    private Button btnFollow;//关注
    private Button btnNote;//发布者作品按钮
    private TextView tvTitle;//笔记标题
    private Button btnTag;//笔记标签
    private TextView tvText;//笔记内容
    private TextView edtComments;//编辑评论
    private Button btnLove;//点赞
    private TextView tvLoveCount;//点赞数
    private Button btnCollection;//收藏
    private TextView tvCollectionCount;//点赞数
    private Button btnComments;//评论
    private TextView tvCommentsCount;//点赞数
    private int loveCount;//点赞数
    private int collectionCount;//被收藏数
    private int commentsCount;//评论数
    private TextView tvUploadPosiition;//地点
    private TextView tvUploadTime;//时间
    private String tel;
    private OkHttpClient okHttpClient;
    private int noteId;//笔记id
    private int followClick;//点击关注按钮的次数
    private int loveClick;//点击点赞按钮的次数
    private int collectionClick;//点击收藏按钮的次数
    private String operation;
    private String ownTel;//自己的手机号
    private String noteOwnTel;//被关注的人的手机号
    private String ownHeadSrc;//自己的头像
    private String ownNickName;//自己的名称
    private List<String> favoriteNamesList = new ArrayList<>();
    private String[] favoriteNames;//收藏夹列表
    private String favoriteName;//选择的收藏夹名字
    private AlertDialog fAlertDialog;//收藏夹弹出框
    private AlertDialog newFAlertDialog;//新建收藏夹弹出框
    //评论
    private int parentCommentsPosition;//父评论位置
    private CommentExpandableListView expandableListView;
    private CommentExpandAdapter commentExpandAdapter;
    private List<ReplyDetailBean> replyDetailBeans;
    private List<CommentDetailBean> commentsList = new ArrayList<>();
    private BottomSheetDialog dialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String result = (String) msg.obj;
                    if (operation.equals("follow")) {
                        setBtnFollow(result);
                    } else if (operation.equals("love")) {
                        setBtnLove(result);
                    } else if (operation.equals("collection")) {
                        setBtnCollection(result);
                    } else if (operation.equals("getfavoriteNames")) {
                        favoriteNames = result.split(",");
                        for (int i = 0; i < favoriteNames.length; i++) {
                            favoriteNamesList.add(favoriteNames[i].toString());
                        }
                        //选择收藏夹的弹出框
                        showFavoriteAlert();
                    } else if (operation.equals("getfavoriteNames")) {
                        setBtnCollection(result);
                    } else if (operation.equals("getChildComments")) {
                        replyDetailBeans = new ArrayList<>();
                        try {
                            JSONObject obj = new JSONObject(result);
                            JSONArray replyArray = obj.getJSONArray("array");
                            List<ReplyDetailBean> replyDetailBeans = new ArrayList<>();
                            for (int j = 0; j < replyArray.length(); j++) {
                                JSONObject object = replyArray.getJSONObject(j);
                                String replyNickName = object.getString("userName");
                                String replyUserLogo = ConfigUtil.BASE_URL + object.getString("userHeadSrc");
                                String replyContent = object.getString("comments_content");
                                String replyCreateDate = object.getString("comments_time");
                                ReplyDetailBean replyDetailBean = new ReplyDetailBean(replyUserLogo,replyNickName, replyContent,replyCreateDate);
                                replyDetailBeans.add(replyDetailBean);
                                CommentDetailBean commentDetailBean=commentsList.get(parentCommentsPosition);
                                commentsList.get(parentCommentsPosition).setReplyList(replyDetailBeans);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        commentExpandAdapter.notifyDataSetChanged();
                    }
                    operation = "";
                    break;
                case 2:
                    String json = (String) msg.obj;
                    Log.e("rrrrr", json);                    try {
                        JSONObject obj = new JSONObject(json);
                        //设置发布者的头像，昵称
                        String headSrc = ConfigUtil.BASE_URL + obj.getString("userHeadSrc");
                        Glide.with(getApplicationContext()).load(headSrc).circleCrop().into(ivHeadImg);
                        tvUserName.setText(obj.getString("userName"));
                        noteOwnTel = obj.getString("tel");
                        //设置笔记标题
                        tvTitle.setText(obj.getString("titlle"));
                        //设置笔记标签
//                        btnTag.setText(obj.getString("tag_name"));
                        //设置笔记内容
                        tvText.setText(obj.getString("content"));
                        //设置地点，时间
                        tvUploadPosiition.setText(obj.getString("upload_position"));
                        tvUploadTime.setText(obj.getString("upload_time"));
                        //设置点赞数
                        loveCount = Integer.parseInt(obj.getString("like_num"));
                        tvLoveCount.setText(obj.getString("like_num"));
                        //设置被收藏数
                        collectionCount = Integer.parseInt(obj.getString("collection_num"));
                        tvCollectionCount.setText(obj.getString("collection_num"));
                        //设置评论数
                        commentsCount = Integer.parseInt(obj.getString("comments_num"));
                        tvCommentsCount.setText(obj.getString("comments_num"));
                        //设置关注、点赞、收藏按钮
                        String followFlag = obj.getString("isFollow");
                        setBtnFollow(followFlag);
                        String loveFlag = obj.getString("isLove");
                        setBtnLove(loveFlag);
                        String collectionFlag = obj.getString("isCollection");
                        setBtnCollection(collectionFlag);
                        //自己的头像地址
                        ownHeadSrc = ConfigUtil.BASE_URL + obj.getString("myUserHeadSrc");
                        //自己的名称
                        ownNickName = obj.getString("myUserName");
                        //评论
                        JSONArray commentsArray = obj.getJSONArray("array");
                        for (int i = 0; i < commentsArray.length(); i++) {
                            JSONObject commentObject = commentsArray.getJSONObject(i);
                            int id = Integer.parseInt(commentObject.getString("id"));
//                            Log.e("lww",id+"");
                            String nickName = commentObject.getString("userName");
//                            Log.e("lww",nickName);
                            String userLogo = ConfigUtil.BASE_URL + commentObject.getString("userHeadSrc");
//                            Log.e("lww",userLogo);
                            String content = commentObject.getString("comments_content");
//                            Log.e("lww",content);
                            String createDate = commentObject.getString("comments_time");
//                            Log.e("lww",createDate);
                            int replyTotal = Integer.parseInt(commentObject.getString("sonCount"));
                            Log.e("lww",replyTotal+"");
                            CommentDetailBean commentDetailBean = new CommentDetailBean(userLogo, nickName, content, createDate);
                            commentDetailBean.setId(id);
                            commentDetailBean.setReplyTotal(replyTotal);
                            commentsList.add(commentDetailBean);
                        }
                        //默认展开所有回复
                        commentExpandAdapter = new CommentExpandAdapter(DetailActivity.this, commentsList);
                        expandableListView.setAdapter(commentExpandAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    };

    /*
        选择收藏夹的弹出框
     */
    private void showFavoriteAlert() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        favoriteName = favoriteNames[0];
        alertBuilder.setTitle("选择收藏夹");
        alertBuilder.setSingleChoiceItems(favoriteNames, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                favoriteName = favoriteNames[i];
            }
        });
        alertBuilder.setNeutralButton("+新建收藏夹", new DialogInterface.OnClickListener() {//添加普通按钮
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                View newFavoriteNameView = LayoutInflater.from(DetailActivity.this).inflate(R.layout.new_favorite_name, null);
                AlertDialog.Builder alertBuilder1 = new AlertDialog.Builder(DetailActivity.this);
                alertBuilder1.setView(newFavoriteNameView);
                final EditText userInput = (EditText) newFavoriteNameView.findViewById(R.id.edt_favorite_name);
                alertBuilder1
                        .setCancelable(false)
                        .setPositiveButton("确认",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        favoriteName = userInput.getText().toString();
                                        //添加收藏夹名称
                                        favoriteNamesList.add(favoriteName);
                                        favoriteNames = new String[favoriteNamesList.size() + 1];
                                        if (favoriteNamesList.size() > 0) {
                                            for (int i = 0; i < favoriteNamesList.size(); i++) {
                                                if (favoriteName.equals(favoriteNamesList.get(i))) {
                                                    favoriteNames[i] = favoriteNamesList.get(i);
                                                }
                                            }
                                        }
                                        //向服务端传递数据
                                        operation = "newFavorite";
                                        Log.e("lww", favoriteName);
//                                        String key = "?tel=2&name=" + favoriteName;
                                        String key="?tel="+ownTel+"&name="+favoriteName;
                                        postRequest(ConfigUtil.BASE_URL + "collection/addCollectionDirectory" + key);

                                        //收藏
                                        //改变收藏图标
                                        btnCollection.setBackgroundResource(R.mipmap.ic_collection1);
                                        //修改收藏数
                                        collectionCount += 1;
                                        tvCollectionCount.setText(collectionCount + "");
                                        //上传数据，收藏笔记
                                        operation = "colletion";
//                                        String key2 = "?note_id=1&tel=2&name=" + favoriteName;
                                        String key2="?note_id="+noteId+"&tel="+ownTel+"&name="+favoriteName;
                                        postRequest(ConfigUtil.BASE_URL +"collection/addOneNoteToDirectory" + key2);
                                        newFAlertDialog.dismiss();
//                                        showFavoriteAlert();
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        newFAlertDialog.dismiss();
                                        fAlertDialog.show();
                                    }
                                });

                newFAlertDialog = alertBuilder1.create();
                newFAlertDialog.show();
            }
        });

        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //改变收藏图标
                btnCollection.setBackgroundResource(R.mipmap.ic_collection1);
                //修改收藏数
                collectionCount += 1;
                tvCollectionCount.setText(collectionCount + "");
                fAlertDialog.dismiss();
                //上传数据，收藏了该笔记
                operation = "colletion";
//                String key = "?note_id=1&tel=2&name=" + favoriteName;
                String key="?note_id="+noteId+"&tel="+ownTel+"&name="+favoriteName;
                postRequest(ConfigUtil.BASE_URL + "collection/addOneNoteToDirectory" + key);
            }
        });

        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (collectionCount >= 0) {
                    collectionCount -= 1;
                } else {
                    collectionCount = 0;
                }
                collectionClick -= 1;
                fAlertDialog.dismiss();
            }
        });

        fAlertDialog = alertBuilder.create();
        fAlertDialog.show();

    }

    /*
        是否收藏过该笔记
     */
    private void setBtnCollection(String collectionFlag) {
        if (collectionFlag.equals("0")) {
            collectionClick = 0;
            btnCollection.setBackgroundResource(R.drawable.ic_collection0);
        } else {
            collectionClick = 1;
            btnCollection.setBackgroundResource(R.drawable.ic_collection1);
        }
    }

    /*
        是否点赞过该笔记
     */
    private void setBtnLove(String loveFlag) {
        if (loveFlag.equals("0")) {
            loveClick = 0;
            btnLove.setBackgroundResource(R.drawable.ic_like0);
            if (loveCount > 0) {
                loveCount -= 1;
            } else {
                loveCount = 0;
            }
        } else {
            loveClick = 1;
            btnLove.setBackgroundResource(R.drawable.ic_like1);
            loveCount += 1;
        }
        tvLoveCount.setText(loveCount + "");
    }

    /*
        设置关注按钮，是否关注过发布者
        是否关注成功
    */
    private void setBtnFollow(String flag) {
        if (flag.equals("0")) {
            followClick = 0;
            btnFollow.setBackgroundResource(R.drawable.input_bg3);
            btnFollow.setText("+关注");
            btnFollow.setTextColor(getResources().getColor(R.color.myRed));
        } else {
            followClick = 1;
            btnFollow.setBackgroundResource(R.drawable.input_bg2);
            btnFollow.setText("已关注");
            btnFollow.setTextColor(getResources().getColor(R.color.myDarkGray));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //获取控件
        getViews();
        //获取笔记id
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", 1);
        SharedPreferences sp = getSharedPreferences("setting", Context.MODE_PRIVATE);
        ownTel = sp.getString("tel", "1");
        //获取发布者与笔记信息
        getNoteInfo();
        //设置监听器
        setListener();
        //初始化九图
        setRecycler();
        //初始化评论和回复列表
        initExpandableListView(commentsList);
    }

    /*
        初始化评论和回复列表
     */
    private void initExpandableListView(final List<CommentDetailBean> commentsList) {
        expandableListView.setGroupIndicator(null);
//        //默认展开所有回复
//        commentExpandAdapter = new CommentExpandAdapter(this, commentsList);
//        expandableListView.setAdapter(commentExpandAdapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                //点击某一条评论，然后@ta,那么我们需要在group的点击事件里弹起回复框
                showReplyDialog(groupPosition);
                return true;
            }
        });


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Toast.makeText(DetailActivity.this, "点击了回复", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


    }

    /*
       展开收缩回复
    */
    public void OnFlagClickListener(int position) {
        if (expandableListView.isGroupExpanded(position)) {  //如果是打开状态则关闭
            expandableListView.collapseGroup(position);
        } else { //如果是关闭状态则打开
            expandableListView.expandGroup(position);
            //获取当前评论的回复
            int id = commentsList.get(position).getId();
            parentCommentsPosition = position;
            String key = "?parentId=" + id;
            operation = "getChildComments";
            postRequest(ConfigUtil.BASE_URL + "comments/showSonComment" + key);
        }
    }

    /*
    弹出评论框
     */
    private void showCommentDialog() {
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0, 0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(commentContent)) {

                    //commentOnWork(commentContent);
                    dialog.dismiss();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
                    String creatDate = formatter.format(new Date());
                    CommentDetailBean detailBean = new CommentDetailBean(ownHeadSrc, ownNickName, commentContent, creatDate);
                    if (detailBean != null) {
                        commentExpandAdapter.addTheCommentData(detailBean);
                    }

                    Toast.makeText(DetailActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    //向服务端传递数据，添加评论
                    operation = "postComments";
                    String key = "?note_id="+noteId+"&comments_content=" + commentContent + "&tel="+ownTel+"&comments_time=" + creatDate;
//                    String key = "?note_id=1&comments_content=" + commentContent + "&tel=2&comments_time=" + creatDate;
                    postRequest(ConfigUtil.BASE_URL + "comments/oneComment" + key);
                } else {
                    Toast.makeText(DetailActivity.this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() > 2) {
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                } else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    /*
        弹出回复框
        点击某一条评论，然后@ta,那么我们需要在group的点击事件里弹起回复框
     */
    private void showReplyDialog(final int position) {
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + commentsList.get(position).getNickName() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(replyContent)) {
                    dialog.dismiss();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
                    String creatDate = formatter.format(new Date());
                    int parentId=commentsList.get(position).getId();
                    ReplyDetailBean detailBean = new ReplyDetailBean(ownHeadSrc,ownNickName, replyContent,creatDate);
                    commentExpandAdapter.addTheReplyData(detailBean, position);
                    //向服务端传递数据，添加回复
                    operation = "postChildComments";
                    String key = "?note_id="+noteId+"&comments_content=" + replyContent + "&tel="+ownTel+"&comments_time=" + creatDate+"&parentId="+parentId;
//                    String key = "?note_id=1&comments_content=" + replyContent + "&tel=2&comments_time=" + creatDate+"&parentId="+parentId;
                    postRequest(ConfigUtil.BASE_URL + "comments/oneSonComment" + key);
                    expandableListView.expandGroup(position);
                    Toast.makeText(DetailActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailActivity.this, "回复内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() > 2) {
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                } else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }


    /*
        获取发布者与笔记信息
     */
    private void getNoteInfo() {
        //1. OkClient对象
        okHttpClient = new OkHttpClient();
        String key = "?note_id=" + noteId + "&tel=" + ownTel ;
        //创建请求对象
        Request request = new Request.Builder()
                .url(ConfigUtil.BASE_URL + "note/detailsInterface"+key)
//                .url("http://10.7.89.227:8080/journeyjourney/note/detailsInterface?note_id=1&tel=2")
                .build();
        //3. 创建CALL对象
        Call call = okHttpClient.newCall(request);
        //4. 提交请求并获取响应
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String result = "网络连接错误";
                Message msg = handler.obtainMessage();
                msg.what = 2;
                msg.obj = result;
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取响应的字符串信息
                String result = response.body().string();
                JSONObject obj = null;
                try {
                    obj = new JSONObject(result);
                    String coverSrc = ConfigUtil.BASE_URL + obj.getString("cover_src");
                    urls.add(coverSrc);
                    //获取九图
                    String[] strs = obj.getString("nineSrc").split(",");
                    for (int i = 0; i < strs.length; i++) {
                        String url = ConfigUtil.BASE_URL + strs[i];
                        urls.add(url);
                    }
                    recyclerView.setAdapter(new ConcernImgRecyclerAdapter(urls, getApplicationContext()));
                    new PagerSnapHelper().attachToRecyclerView(recyclerView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Message msg = handler.obtainMessage();
                msg.what = 2;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        });
    }


    /*
        设置监听器
     */
    private void setListener() {
        MyListener myListener = new MyListener();
        //关注按钮
        btnFollow.setOnClickListener(myListener);
        //作品按钮控件
        btnNote.setOnClickListener(myListener);
        //点赞
        btnLove.setOnClickListener(myListener);
        //收藏
        btnCollection.setOnClickListener(myListener);
        //点击评论编辑框
        edtComments.setOnClickListener(myListener);
        //点击评论按钮
        btnComments.setOnClickListener(myListener);
        btnBack.setOnClickListener(myListener);
    }

    /*
        获取控件
     */
    private void getViews() {
        btnBack = findViewById(R.id.btn_back);
        ivHeadImg = findViewById(R.id.iv_headImg);
        tvUserName = findViewById(R.id.tv_userName);
        btnNote = findViewById(R.id.btn_note);
        tvTitle = findViewById(R.id.tv_title);
        btnFollow = findViewById(R.id.btn_follow);
        btnTag = findViewById(R.id.btn_tag);
        tvText = findViewById(R.id.tv_text);
        edtComments = findViewById(R.id.edt_comments);
        btnLove = findViewById(R.id.btn_love);
        tvLoveCount = findViewById(R.id.tv_love_count);
        btnCollection = findViewById(R.id.btn_collection);
        tvCollectionCount = findViewById(R.id.tv_collection_count);
        btnComments = findViewById(R.id.btn_comments);
        tvCommentsCount = findViewById(R.id.tv_comments_count);
        tvUploadPosiition = findViewById(R.id.tv_upload_position);
        tvUploadTime = findViewById(R.id.tv_upload_time);
        //评论
        expandableListView = findViewById(R.id.lv_commment);
    }

    /*
        初始化九图
     */
    private void setRecycler() {
        recyclerView = findViewById(R.id.cusom_swipe_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
//        recyclerView.setAdapter(new ConcernImgRecyclerAdapter(urls,getApplicationContext()));
//        new PagerSnapHelper().attachToRecyclerView(recyclerView);
    }


    /*
            内部类
         */
    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_back:
                    finish();
                    break;
                case R.id.btn_follow:
                    operation = "follow";
                    //关注按钮
                    followClick += 1;
                    Log.e("lww1", followClick + "");
                    if (followClick % 2 == 1) {
                        //关注发布者，向服务端传递数据（向数据库添加数据）
//                        postRequest("http://10.7.89.227:8080/journeyjourney/follow/detailsInterfaceFollow?fans_tel=2&follow_tel=1");
                        String key = "?fans_tel=" + ownTel + "&follow_tel=" + noteOwnTel;
                        postRequest(ConfigUtil.BASE_URL + "note/detailsInterfaceFollow"+key);

                    } else {
                        //取消关注
                        //弹出对话框
                        AlertDialog.Builder adBuilder
                                = new AlertDialog.Builder(DetailActivity.this);
                        adBuilder.setMessage("确定不再关注");
                        adBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //不在关注，向服务端传递数据
//                                postRequest("http://10.7.89.227:8080/journeyjourney/follow/detailsInterfaceCancelFollow?fans_tel=2&follow_tel=1");
                                postRequest(ConfigUtil.BASE_URL + "note/detailsInterfaceFollow?fans_tel='"+ownTel+"'&follow_tel='"+noteOwnTel+"'");
                            }
                        });
                        adBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //关注
                                followClick -= 1;
                                btnFollow.setBackgroundResource(R.drawable.input_bg2);
                                btnFollow.setText("已关注");
                                btnFollow.setTextColor(getResources().getColor(R.color.myDarkGray));
                            }
                        });
                        AlertDialog ad = adBuilder.create();
                        ad.show();
                    }
                    break;
                case R.id.btn_note:
                    Intent intent1 = new Intent(DetailActivity.this, NotesActivity.class);
                    intent1.putExtra("tel", noteOwnTel);
                    startActivity(intent1);
                    break;
                case R.id.btn_love:
                    //点赞
                    operation = "love";
                    loveClick += 1;
                    if (loveClick % 2 == 1) {
                        //点赞
                        //向服务端传送数据
//                        postRequest("http://10.7.89.227:8080/journeyjourney/love/followInterfaceLove?tel=2&note_id=1");
                        postRequest("http://10.7.89.227:8080/journeyjourney/note/followInterfaceLove?tel='"+ownTel+"'&note_id='"+noteId+"'");
                    } else {
                        //取消点赞
                        //向服务端传送数据
//                        postRequest("http://10.7.89.227:8080/journeyjourney/love/followInterfaceCancelLove?tel=2&note_id=1");
                        postRequest(ConfigUtil.BASE_URL +"note/followInterfaceLove?tel='"+ownTel+"'&note_id='"+noteId+"'");
                    }
                    break;
                case R.id.btn_collection:
                    //收藏
                    operation = "collection";
                    collectionClick += 1;
                    if (collectionClick % 2 == 1) {
                        //收藏
//                        btnCollection.setBackgroundResource(R.mipmap.ic_collection1);
                        //获取所有收藏夹名称
                        operation = "getfavoriteNames";
                        postRequest(ConfigUtil.BASE_URL +"collection/ShowCollectionDirectory?tel=" + ownTel);

                    } else {
                        //取消收藏
                        btnCollection.setBackgroundResource(R.mipmap.ic_collection0);
                        if (collectionCount > 0) {
                            collectionCount -= 1;
                        } else {
                            collectionCount = 0;
                        }
                        //上传数据，取消收藏
                        postRequest(ConfigUtil.BASE_URL + "collection/deleteOneNoteToDirectory?tel="
                                + ownTel + "&note_id=" + noteId);
                        tvCollectionCount.setText(collectionCount + "");
                    }
                    break;
                case R.id.btn_comments:
                    //翻看评论
                    //向服务端获取数据
                    break;
                case R.id.edt_comments:
                    showCommentDialog();
                    break;
            }
        }
    }


    /*
        向服务端传递数据(关注，取消关注)
     */
    private void postRequest(String url) {
        //1. OkClient对象
        okHttpClient = new OkHttpClient();
        //创建请求对象
        Request request = new Request.Builder()
                .url(url)
                .build();
        //3. 创建CALL对象
        Call call = okHttpClient.newCall(request);
        //4. 提交请求并获取响应
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String result = "网络连接错误";
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = result;
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取响应的字符串信息
                String result = response.body().string();
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        });
    }
    /**
     * by moos on 2018/04/20
     * func:生成测试数据
     * @return 评论数据
     */

}
