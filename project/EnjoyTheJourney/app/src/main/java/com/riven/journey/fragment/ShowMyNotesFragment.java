package com.riven.journey.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riven.journey.R;
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
 * 展示我的作品
 * @author 田春雨
 */

public class ShowMyNotesFragment extends Fragment {

    public static ShowMyNotesFragment newInstance(String param1) {
        ShowMyNotesFragment fragment = new ShowMyNotesFragment();
        Bundle args = new Bundle();
        args.putString("phone", param1);
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView rv;
    private List<MyWorks> list = new ArrayList<>();
    private String phone;
    private GridAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("lww", msg.obj.toString());
                    rv.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    rv.setAdapter(adapter);
                    break;
                case 2:
                    Log.e("lww", "网络连接错误");
                    break;

            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_notes_layout, container, false);
        //得到手机号
        phone = getArguments().getString("phone");
        Log.e("phone", phone);

        //获取控件
        rv = view.findViewById(R.id.rv);
        adapter = new GridAdapter(getContext(), list);

        //准备数据
        initData();
        return view;
    }


    private void initData() {
        //显示数据（封面和标题）
        showFirstPageData();
    }

    private void showFirstPageData() {
        final String s = ConfigUtil.BASE_URL + "user/myWorks";
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
                Bitmap bitmap = null;
                String title = null;
                String id = null;
                if (null != list) {
                    list.clear();
                }
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.getJSONArray("array");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.getJSONObject(i);
                        title = object1.getString("titlle");
                        id = object1.getString("note_id");
                        Log.e("title", title);
                        String imgUrl = ConfigUtil.BASE_URL + object1.getString("cover_src");
                        Log.e("src", imgUrl);
                        URL url = new URL(imgUrl);
                        InputStream in1 = url.openStream();
                        //将输入流解析成Bitmap对象
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        bitmap = BitmapFactory.decodeStream(in1,null,options);
                        in1.close();
                        MyWorks works = new MyWorks(bitmap, title, id);
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
