package com.riven.journey;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.riven.journey.util.ConfigUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 设置中心-账号设置-修改密码
 * @author 田春雨
 */

public class AccountSettingsActivity extends AppCompatActivity {
    private String phone;
    private ImageView arrow1;
    private LinearLayout llRoot;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    if (msg.obj.equals("true")) {
                        Log.e("lww", msg.obj.toString());
                        Toast.makeText(getApplicationContext(), "成功！", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 2:
                    Log.e("lww", "网络连接错误");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);
        Intent request = getIntent();
        phone = request.getStringExtra("phone");
        arrow1 = findViewById(R.id.iv_arrow1);
        llRoot = findViewById(R.id.ll_root);
        arrow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出窗口修改密码
                showPoPuWindow();
            }
        });

    }

    //修改密码
    private void showPoPuWindow() {
        //创建对象
        final PopupWindow popupWindow = new PopupWindow(getApplicationContext());
        //设置弹出窗口的宽度
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_white));
        popupWindow.setAnimationStyle(R.style.BottomDialog_Animation);
        popupWindow.setFocusable(true);
        //设置视图
        View view = getLayoutInflater().inflate(R.layout.plan_box, null);
        //设置试图当中控件的属性和监听器
        //取消按钮的点击事件
        ImageView btnCancel = view.findViewById(R.id.iv_close);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        TextView tvCheck = view.findViewById(R.id.tv_check);
        tvCheck.setText("修改密码");
        popupWindow.setContentView(view);
        final EditText edPwd = view.findViewById(R.id.ed_name);
        //点击提交上传数据给服务端
        Button btnCommit = view.findViewById(R.id.btn_commit);
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建FormBody对象
                FormBody formBody = new FormBody.Builder()
                        .add("tel", phone)
                        .add("pwd", edPwd.getText().toString())
                        .build();
                Log.e("lww", phone);
                Log.e("lww", edPwd.getText().toString());
                //创建请求对象
                Request request = new Request.Builder()
                        .url(ConfigUtil.BASE_URL + "user/updatePwd")
                        .post(formBody)
                        .build();
                //3. 创建CALL对象
                OkHttpClient okHttpClient = new OkHttpClient();
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
                        Message msg = handler.obtainMessage();
                        msg.what = 1;
                        msg.obj = result;
                        handler.sendMessage(msg);
                    }
                });
            }

        });
        //显示PopupWindow(指定显示的位置)
        popupWindow.showAtLocation(llRoot, Gravity.CENTER, 0, 0);
    }
}
