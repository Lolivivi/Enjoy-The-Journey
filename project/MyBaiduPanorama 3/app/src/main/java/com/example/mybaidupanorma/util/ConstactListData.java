package com.example.mybaidupanorma.util;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.internal.$Gson$Preconditions;
import com.hyphenate.easeui.domain.EaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author 梁钰钊
 * 粉丝列表数据
 */

public class ConstactListData {
    static private ConstactListData ctld = new ConstactListData();
    public static ConstactListData getInstance(){

        if(ctld == null){
            ctld = new ConstactListData();
        }
        return ctld;
    }

    private Map<String,EaseUser> m = new HashMap<>();
    private List<LikeData> likeDataList = new ArrayList<>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 200:
                    m = (Map<String, EaseUser>) msg.obj;
                    Log.e("data",m.toString());
                    break;
                case 100:
                    likeDataList = (List<LikeData>) msg.obj;
                    Log.e("likedatalist",likeDataList.toString());
                    break;
            }
        }
    };
    //粉丝列表
    public Map<String, EaseUser> getConstactList() {
        //Map<String, EaseUser> m = new HashMap<String,EaseUser>();
        FormBody formBody = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(ConfigUtil.BASE_URL + "follow/showMyFollows?tel=1")
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
                String[] str = res.split("&&&");
                //tel数组手机号
                String[] tel = str[0].split(",");
                //img用户头像
                String[] img = str[1].split(",");
                for (int i = 0; i < tel.length; i++) {
                    if(tel[i].equals("")|| img[i].equals("")){
                        Log.e("null","该数据不能放进去");
                    }else{
                        String username = tel[i];
                        EaseUser user = new EaseUser(tel[i]);
                        user.setAvatar(ConfigUtil.BASE_URL + img[i]);
                        m.put(username,user);
                    }
                }
                Log.e("m", m.toString());
                Message msg =handler.obtainMessage();
                msg.what=200;
                msg.obj=m;
                handler.sendMessage(msg);
            }
        });
        Log.e("mapdata",m.toString());
        if(m==null){
            return null;
        }else{
            return m;
        }
    }

}

