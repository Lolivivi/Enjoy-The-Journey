package com.riven.journey.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.riven.journey.BookMarkActivity;
import com.riven.journey.ExamineActivity;
import com.riven.journey.R;
import com.riven.journey.SetUpActivity;
import com.riven.journey.ModifyUserInfoActivity;
import com.riven.journey.util.ConfigUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 用户页面
 * @author 田春雨
 */
public class MyPageFragment extends Fragment {
    //两个LinearLayout
    private LinearLayout llHome;
    private LinearLayout llAdd;
    //两个image
    private ImageView ivLike1;
    private ImageView ivLike2;
    private ImageView ivLike3;
    //两个text
    private TextView tvLike1;
    private TextView tvLike2;
    private TextView tvLike3;
    //我的收藏
    private ImageView ivCollection;
    private TextView tvCollection;

    //RelativeLayout控件
    private RelativeLayout rl;

    private ImageView myUserPhoto;
    private String phone;
    private TextView myUserName;
    private TextView myUserSign;
    private TextView myUserFan;
    private TextView myUserFollow;
    private Bitmap bitmap;
    private String sign, name, follows, fans, imgUrl, sex;
    private TextView tvSetUp;
    private ImageView ivSetUp;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("lww", msg.obj.toString());
                    JSONObject object = null;
                    try {
                        object = new JSONObject(msg.obj.toString());
                        //签名
                        sign = object.getString("intro");
                        myUserSign.setText(sign);
                        //用户名
                        name = object.getString("name");
                        myUserName.setText(name);
                        //关注数
                        follows = object.getString("follow");
                        myUserFollow.setText(follows);
                        //粉丝数
                        fans = object.getString("fans");
                        myUserFan.setText(fans);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    Log.e("lww", "网络连接错误");
                    break;
                case 3:
                    //头像
                    Bitmap bitmap = (Bitmap) msg.obj;
                    myUserPhoto.setImageBitmap(bitmap);
                    break;
            }
        }
    };

    public static MyPageFragment newInstance(String param1) {
        MyPageFragment fragment = new MyPageFragment();
        Bundle args = new Bundle();
        args.putString("phone", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_page, container, false);
        phone = getArguments().getString("phone");
        Log.e("lww", phone);
        //获取控件
        myUserPhoto = view.findViewById(R.id.my_user_photo);
        myUserName = view.findViewById(R.id.my_user_name);
        myUserSign = view.findViewById(R.id.my_user_sign);
        myUserFollow = view.findViewById(R.id.tv_foll);
        myUserFan = view.findViewById(R.id.tv_fan);
        tvSetUp = view.findViewById(R.id.tv_set_up);
        ivSetUp = view.findViewById(R.id.iv_set_up);
        rl = view.findViewById(R.id.rl);

        //设置LinearLayout
        llHome = view.findViewById(R.id.ll_home);
        llAdd = view.findViewById(R.id.ll_add);
        //设置ImageView
        ivLike1 = view.findViewById(R.id.iv_like2);
        ivLike3 = view.findViewById(R.id.iv_like3);
        //设置TextView
        tvLike1 = view.findViewById(R.id.tv_like1);
        tvLike3 = view.findViewById(R.id.tv_like3);
        //默认显示第一个页面
        showMyWorks();
        //我的收藏获取控件
        ivCollection = view.findViewById(R.id.iv_collect);
        tvCollection = view.findViewById(R.id.tv_collect);

        //设置监听器
        setListener();
        //返回用户信息
        showUserInfo();


        return view;
    }


    //返回用户信息
    private void showUserInfo() {
        final String s = ConfigUtil.BASE_URL + "user/my";
        String key = "?tel=" + phone;
        //创建请求对象
        final Request request = new Request.Builder()
                .url(s + key)
                .build();
        //创建CALL对象
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        //提交请求并获取响应
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
                Log.e("result", result);
                try {
                    JSONObject object = new JSONObject(result);
                    //性别
                    sex = object.getString("sex");
                    //图片地址
                    imgUrl = ConfigUtil.BASE_URL + object.getString("headSrc");
                    Log.e("lww", imgUrl);
                    URL url = new URL(imgUrl);
                    InputStream in1 = url.openStream();
                    //将输入流解析成Bitmap对象
                    bitmap = BitmapFactory.decodeStream(in1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = result;
                handler.sendMessage(msg);

                Message msg1 = handler.obtainMessage();
                msg1.what = 3;
                msg1.obj = bitmap;
                handler.sendMessage(msg1);
            }
        });
    }

    private void setListener() {
        MyListener listener = new MyListener();
        myUserPhoto.setOnClickListener(listener);
        tvSetUp.setOnClickListener(listener);
        ivSetUp.setOnClickListener(listener);
        llHome.setOnClickListener(listener);
        llAdd.setOnClickListener(listener);
        //我的收藏
        ivCollection.setOnClickListener(listener);
        tvCollection.setOnClickListener(listener);
        rl.setOnClickListener(listener);
    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.my_user_photo:
                    //点击头像跳转到修改用户信息页面
                    Intent intent = new Intent(getContext(), ModifyUserInfoActivity.class);
                    intent.putExtra("phone", getArguments().getString("phone"));
                    intent.putExtra("imgUrl", imgUrl);
                    Log.e("url", imgUrl);
                    intent.putExtra("name", name);
                    intent.putExtra("sign", sign);
                    intent.putExtra("sex", sex);
                    startActivity(intent);
                    break;
                case R.id.tv_set_up:
                case R.id.iv_set_up:
                    //设置中心跳转
                    Intent intent1 = new Intent(getContext(), SetUpActivity.class);
                    intent1.putExtra("phone", phone);
                    startActivity(intent1);
                    break;
                case R.id.ll_home:
                    //我的作品
                    showMyWorks();
                    break;
                case R.id.ll_add:
                    //添加收藏夹按钮
                    Intent intent2 = new Intent(getContext(), BookMarkActivity.class);
                    intent2.putExtra("phone", phone);
                    startActivity(intent2);
                    break;
                case R.id.iv_collect:
                case R.id.tv_collect:
                    //跳转到收藏页面
                    Intent i = new Intent(getContext(), ExamineActivity.class);
                    i.putExtra("phone", phone);
                    startActivity(i);
                    break;
                case R.id.rl:
                    //控件的动画效果
                    TranslateAnimation animation = new TranslateAnimation(0, 550, 0, 550);
                    animation.setFillAfter(true);
                    rl.startAnimation(animation);
                    break;
            }
        }
    }

    private void showMyLoves() {
        FragmentManager fragmentManager1 = getChildFragmentManager();
        FragmentTransaction transaction1 = fragmentManager1.beginTransaction();
        transaction1.add(R.id.content, SecondPageFragment.newInstance(phone));
        transaction1.replace(R.id.content, SecondPageFragment.newInstance(phone));
        tvLike2.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
        tvLike1.setTextColor(getResources().getColor(android.R.color.black));
        ivLike2.setImageDrawable(getResources().getDrawable(R.mipmap.love));
        ivLike1.setImageDrawable(getResources().getDrawable(R.mipmap.like2));
        transaction1.commit();
    }

    private void showMyWorks() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content, FirstPageFragment.newInstance(phone));
        transaction.replace(R.id.content, FirstPageFragment.newInstance(phone));
        tvLike1.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
        ivLike1.setImageDrawable(getResources().getDrawable(R.mipmap.show));
        transaction.commit();
    }


}
