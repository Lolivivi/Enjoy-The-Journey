package com.riven.journey;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.riven.journey.fragment.MyPageFragment;
import com.riven.journey.util.ConfigUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建收藏夹
 * @author 田春雨
 */

public class BookMarkActivity extends AppCompatActivity {
    private EditText edName;
    private Button btnCreate;
    private String phone;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    if (msg.obj.equals("true")) {
                        Log.e("lww", msg.obj.toString());
                        Toast.makeText(getApplicationContext(), "创建成功！", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MyPageFragment.class);
                        intent.putExtra("phone", phone);
                        startActivity(intent);
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
        setContentView(R.layout.book_mark);
        Intent request = getIntent();
        phone = request.getStringExtra("phone");

        //获取控件
        findViews();
        //设置监听器
        setListener();


    }

    private void setListener() {
        MyListener myListener = new MyListener();
        btnCreate.setOnClickListener(myListener);
    }

    private void findViews() {
        edName = findViewById(R.id.ed_name);
        btnCreate = findViewById(R.id.btn_edit);
    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_edit:
                    //将数据传给服务端
                    upLoadData();
                    break;
            }
        }
    }

    //将收藏夹名称传给服务端
    private void upLoadData() {
        //1. OkClient对象
        //2. 创建Request请求对象（提前准备好Form表单数据封装）
        //创建FormBody对象
        FormBody formBody = new FormBody.Builder()
                .add("tel", phone)
                .add("name", edName.getText().toString())
                .build();
        Log.e("lww", phone);
        Log.e("lww", edName.getText().toString());
        //创建请求对象
        Request request = new Request.Builder()
                .url(ConfigUtil.BASE_URL + "collection/addCollectionDirectory")
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
}
