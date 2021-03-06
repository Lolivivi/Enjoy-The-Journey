package com.example.mybaidupanorma.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mybaidupanorma.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.example.mybaidupanorma.util.ConfigUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText edPhone;
    private EditText edPwd;
    private Button btnRegister;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    if(msg.obj.equals("true")){
                        Toast.makeText(getApplicationContext(),"注册成功！",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"手机号已被注册过，请登录！",Toast.LENGTH_LONG).show();
                    }
                    break;
                case 2:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_journey);
        //获取控件对象
        edPhone=findViewById(R.id.ed_telephone_number);
        edPwd=findViewById(R.id.ed_pwd);
        btnRegister=findViewById(R.id.btn_register);

        //注册按钮的监听事件
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1. OkClient对象
                //2. 创建Request请求对象（提前准备好Form表单数据封装）
                //创建FormBody对象
                if (edPhone.length() == 0 || edPwd.length() == 0) {
                    Toast.makeText(getApplicationContext(), "手机号或密码不能为空！", Toast.LENGTH_LONG).show();
                } else {
                    FormBody formBody = new FormBody.Builder()
                            .add("tel", edPhone.getText().toString())
                            .add("pwd", edPwd.getText().toString())
                            .build();
                    //创建请求对象
                    Request request = new Request.Builder()
                            .url(ConfigUtil.BASE_URL + "user/registerUser")
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
                            final String result = response.body().string();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        EMClient.getInstance().createAccount(edPhone.getText().toString(),edPwd.getText().toString());
                                        Log.e("res","注册成功");
                                    } catch (HyphenateException e) {
                                        e.printStackTrace();
                                        Looper.prepare();
                                        Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }
                                    Message msg = handler.obtainMessage();
                                    msg.what = 1;
                                    msg.obj = result;
                                    handler.sendMessage(msg);
                                }
                            }).start();
                        }
                    });
                }
            }
        });

    }
}
