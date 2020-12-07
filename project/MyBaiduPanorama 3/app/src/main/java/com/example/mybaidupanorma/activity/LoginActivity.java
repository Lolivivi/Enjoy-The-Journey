package com.example.mybaidupanorma.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mybaidupanorma.MainActivity;
import com.example.mybaidupanorma.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.example.mybaidupanorma.util.ConfigUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 登录页面
 * @author 田春雨
 */
public class LoginActivity extends AppCompatActivity {
    private EditText edPhone;
    private EditText edPwd;
    private Button btnLogin;
    private Button btnRegister;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    if (msg.obj.equals("true")) {
                        Toast.makeText(getApplicationContext(), "登录成功！", Toast.LENGTH_LONG).show();
                        EMClient.getInstance().login(edPhone.getText().toString(), edPwd.getText().toString(), new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                SharedPreferences sp = getApplicationContext().getSharedPreferences("setting", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("tel", edPhone.getText().toString());
                                editor.putString("pwd", edPwd.getText().toString());
                                editor.commit();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onError(int i, String s) {
                                Looper.prepare();
                                Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }

                            @Override
                            public void onProgress(int i, String s) {

                            }
                        });

                    } else {
                        Log.e("lww", msg.obj.toString());
                        Toast.makeText(getApplicationContext(), "未注册请先注册", Toast.LENGTH_LONG).show();
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
        setContentView(R.layout.login_journey);
        edPhone = findViewById(R.id.ed_phone);
        edPwd = findViewById(R.id.ed_pwd);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        //点击登录按钮发送数据验证后登录
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1. OkClient对象
                //2. 创建Request请求对象（提前准备好Form表单数据封装）
                //创建FormBody对象
                FormBody formBody = new FormBody.Builder()
                        .add("tel", edPhone.getText().toString())
                        .add("pwd", edPwd.getText().toString())
                        .build();
                Log.e("lww", edPhone.getText().toString());
                Log.e("lww", edPwd.getText().toString());
                //创建请求对象
                Request request = new Request.Builder()
                        .url(ConfigUtil.BASE_URL + "user/login")
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
        //点击注册按钮跳转到注册页面
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}
