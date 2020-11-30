package com.riven.journey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.riven.journey.adapter.ExamineAdapter;
import com.riven.journey.bean.Examine;
import com.riven.journey.util.ConfigUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author 田春雨
 */
public class ExamineActivity extends AppCompatActivity {
    private String phone;
    private List<Examine> em = new ArrayList<>();
    private ExamineAdapter adapter;
    private ListView listView;
    private TextView tvExamine;
    private ImageView ivExamine;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("lww", msg.obj.toString());
                    listView.setAdapter(adapter);
                    break;
                case 2:
                    Log.e("lww", "网络连接错误");
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        SharedPreferences sp = getSharedPreferences("setting", MODE_PRIVATE);
        phone = sp.getString("tel", "1");
        tvExamine = findViewById(R.id.tv_examine);
        ivExamine = findViewById(R.id.iv_examine);
        //准备数据
        initData();
        adapter = new ExamineAdapter(getApplicationContext(), em, R.layout.history_download, phone);
        //获取控件
        listView = findViewById(R.id.lv_list);
    }


    private void initData() {
        //显示收藏夹页面
        showThemes();
    }

    private void showThemes() {
        final String s = ConfigUtil.BASE_URL + "collection/ShowCollectionDirectory";
        String key = "?tel=" + phone;
        //创建请求对象
        final Request request = new Request.Builder()
                .url(s + key)
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
                Log.e("result", result);
               if(result.equals("false")){
                   Examine examine=new Examine();
                   examine.setTitle("默认收藏夹");
                   em.add(examine);
               }else {
                   String r[] = result.split(",");
                   for (int i = 0; i < r.length; i++) {
                       Examine examine1 = new Examine();
                       examine1.setTitle(r[i]);
                       em.add(examine1);
                   }
               }
                Message message = handler.obtainMessage();
                message.obj = em;
                message.what = 1;
                handler.sendMessage(message);
            }
        });
    }
}
