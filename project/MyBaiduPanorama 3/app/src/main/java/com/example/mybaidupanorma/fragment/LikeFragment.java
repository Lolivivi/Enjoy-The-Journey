package com.example.mybaidupanorma.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mybaidupanorma.R;
import com.example.mybaidupanorma.adapter.LikeAdapter;
import com.example.mybaidupanorma.util.ConstactListData;
import com.example.mybaidupanorma.util.LikeData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author 梁钰钊
 * 喜欢列表界面
 */
public class LikeFragment extends Fragment {
    private List<LikeData> likeDataList = new ArrayList<>();
    private ConstactListData constactListData;
    private ListView listView;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 200:
                    likeDataList = (List<LikeData>) msg.obj;
                    LikeAdapter cakeAdapter = new LikeAdapter(getContext(), likeDataList, R.layout.activity_like);
                    listView.setAdapter(cakeAdapter);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_listview,container,false);
        initdata();
        listView = view.findViewById(R.id.listview);
        return view;
    }


    //初始化数据
    private void initdata(){
        FormBody formBody = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url("http://10.7.89.227:8080/journeyjourney/user/myLoveWork?tel=1")
                .post(formBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("请求失败", "error");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.e("res", res);
                try {
                    JSONObject object = new JSONObject(res);
                    JSONArray jsonArray = object.getJSONArray("array");
                    likeDataList = new ArrayList<>();
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        LikeData likeData = new LikeData(jsonObject.getString("userName"),
                                jsonObject.getString("name"),
                                jsonObject.getString("userHeadSrc"),
                                jsonObject.getString("coverImg"));
                        likeDataList.add(likeData);
                    }
                    Message msg = handler.obtainMessage();
                    msg.what=200;
                    msg.obj=likeDataList;
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
