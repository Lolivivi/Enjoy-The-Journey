package com.riven.journey;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riven.journey.adapter.GridAdapter;
import com.riven.journey.bean.MyWorks;
import com.riven.journey.util.ConfigUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
public class ExamineDetailActivity extends AppCompatActivity {
    private RecyclerView rv;
    private List<MyWorks> list = new ArrayList<>();
    private String phone;
    private String name;//收藏夹名称
    private GridAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("lww", msg.obj.toString());
                    rv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                    rv.setAdapter(adapter);
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
        setContentView(R.layout.first_page);
        //获取手机号
        Intent request=getIntent();
        phone=request.getStringExtra("phone");
        name=request.getStringExtra("name");

        //获取控件
        rv = findViewById(R.id.rv);
        adapter = new GridAdapter(getApplicationContext(), list);

        //准备数据
        initData();

    }

    private void initData() {
        //显示数据（封面和标题）
        showExaminePageData();
    }

    private void showExaminePageData() {
        final String s = ConfigUtil.BASE_URL + "collection/showOneDirectory";
        String key = "?tel=" + phone+"&name="+name;
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
                Bitmap bitmap = null;
                String title = null;
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.getJSONArray("array");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.getJSONObject(i);
                        title = object1.getString("titlle");
                        Log.e("title", title);
                        String imgUrl = ConfigUtil.BASE_URL + object1.getString("cover_src");
                        Log.e("src", imgUrl);
                        URL url = new URL(imgUrl);
                        InputStream in1 = url.openStream();
                        //将输入流解析成Bitmap对象
                        bitmap = BitmapFactory.decodeStream(in1);
                        in1.close();
                        MyWorks works = new MyWorks(bitmap, title);
                        list.add(works);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Message message = handler.obtainMessage();
                message.obj = list;
                message.what = 1;
                handler.sendMessage(message);

            }
        });
    }

}
