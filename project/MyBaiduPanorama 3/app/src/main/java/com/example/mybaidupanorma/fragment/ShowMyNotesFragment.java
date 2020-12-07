package com.example.mybaidupanorma.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybaidupanorma.R;
import com.example.mybaidupanorma.adapter.GridAdapter;
import com.example.mybaidupanorma.bean.MyWorks;
import com.example.mybaidupanorma.util.ConfigUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 展示我的作品
 *
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

    //智能刷新控件
    private SmartRefreshLayout smartRefreshLayout;
    private int count = 0;

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

    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_notes_layout, container, false);
        SharedPreferences sp = getContext().getSharedPreferences("setting", Context.MODE_PRIVATE);
        //得到手机号
        phone = sp.getString("tel", "1");
        Log.e("phone", phone);

        //获取控件
        rv = view.findViewById(R.id.rv);
        adapter = new GridAdapter(getContext(), list);
        smartRefreshLayout = view.findViewById(R.id.refreshLayout);
        smartRefreshLayout.setEnableLoadMore(true);

        //智能刷新控件
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayout.VERTICAL);
        rv.setLayoutManager(manager);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                smartRefreshLayout.autoRefresh();
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //网络输入流获取关注的对象
                initData();
                smartRefreshLayout.finishRefresh(1500);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                initData();
                smartRefreshLayout.finishLoadMore(500);
            }
        });

        return view;
    }


    private void initData() {
        //显示数据（封面和标题）
        showFirstPageData();
    }

    private void showFirstPageData() {
        final String s = ConfigUtil.BASE_URL + "user/myWorks";
        String key = "?tel=" + phone + "&count=" + count;
        count += 5;
        Log.e("count", count + "");
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
                if (result.equals("1")) {

                } else {
                    Bitmap bitmap = null;
                    String title = null;
                    String id = null;
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
                            //获取视频缩略图
                            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(imgUrl);
                            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
                            if (mimeType != null && mimeType.contains("video")) {
                                Bitmap bitmap1 = getVideoThumbnail(imgUrl);
                                Log.e("lww", "视频缩略图成功！" + bitmap1);
                                MyWorks works = new MyWorks();
                                works.setTitle(title);
                                works.setPhoto(bitmap1);
                                works.setId(id);
                                list.add(works);
                            } else {
                                URL url = new URL(imgUrl);
                                InputStream in1 = url.openStream();
                                //将输入流解析成Bitmap对象
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inSampleSize = 2;
                                bitmap = BitmapFactory.decodeStream(in1, null, options);
                                in1.close();
                                MyWorks works = new MyWorks(bitmap, title, id);
                                list.add(works);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Message message = handler.obtainMessage();
                    message.obj = list;
                    message.what = 1;
                    handler.sendMessage(message);
                }

            }
        });
    }

    //获取视频缩略图
    public Bitmap getVideoThumbnail(String filePath) {
        Bitmap b = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath, new HashMap<String, String>());
            b = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();

        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return b;
    }

}
