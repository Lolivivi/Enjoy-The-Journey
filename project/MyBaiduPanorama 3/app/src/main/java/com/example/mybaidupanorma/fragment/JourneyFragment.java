package com.example.mybaidupanorma.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mybaidupanorma.R;
import com.example.mybaidupanorma.adapter.JourneyAdapter;
import com.example.mybaidupanorma.bean.Journey;
import com.example.mybaidupanorma.util.ConfigUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 姚沅令
 */
public class JourneyFragment extends Fragment {
    private View root;
    private List<Journey> journeyOnlineList;
    private JourneyAdapter mAdapter;
    private boolean isGetData = false;
    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1211:
                    journeyOnlineList = (List<Journey>) msg.obj;
                    mAdapter = new JourneyAdapter(getContext(), journeyOnlineList, R.layout.journey_item);
                    ListView scListView = root.findViewById(R.id.journey_list);
                    scListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    Log.i("yyl", "Buyer页面cakelist下载且导入成功！");
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.journey_online_fragment, container, false);
        return root;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (!isGetData && enter) {
            isGetData = true;
            loadJourneyList(ConfigUtil.BASE_URL + "journeyonline/journeyOnline");
            Log.i("hhh", ConfigUtil.BASE_URL + "journeyonline/journeyOnline");
        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onResume() {
        if (!isGetData) {
            loadJourneyList(ConfigUtil.BASE_URL + "journeyonline/journeyOnline");
            Log.i("hhh", ConfigUtil.BASE_URL + "journeyonline/journeyOnline");
            isGetData = true;
        }
        super.onResume();
    }

    public void onPause() {
        super.onPause();
        isGetData = false;
    }

    /**
     * 下载景点信息
     * parm:服务器地址
     */
    private void loadJourneyList(final String s) {
        if (journeyOnlineList == null || journeyOnlineList.size() == 0) {
            journeyOnlineList = new ArrayList<Journey>();
            //从服务端下载订单列表
            new Thread() {
                @Override
                public void run() {
                    try {
                        //获取Url
                        URL url = new URL(s);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        //获取输入流
                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                        String result = reader.readLine();
                        List<Journey> journeys = new ArrayList<>();
                        //创建外层JSONObject对象
                        JSONObject jUsers = null;
                        jUsers = new JSONObject(result);
                        JSONArray jArray = jUsers.getJSONArray("array");
                        //遍历JSONArray对象,解析其中的每个元素（Order）
                        for (int i = 0; i < jArray.length(); i++) {
                            Journey journey = new Journey();
                            //获取当前的JSONObject对象
                            JSONObject jBbject = jArray.getJSONObject(i);
                            //获取当前元素信息
                            String title = jBbject.getString("title");
                            String intruduce = jBbject.getString("introduce");
                            String image = jBbject.getString("image");
                            String imag_one = ConfigUtil.BASE_URL + "journeyonline/" + jBbject.getString("image_one");
                            String imag_two = ConfigUtil.BASE_URL + "journeyonline/" + jBbject.getString("image_two");
                            String imag_there = ConfigUtil.BASE_URL + "journeyonline/" + jBbject.getString("image_three");
                            String address_one = jBbject.getString("address_one");
                            String address_two = jBbject.getString("address_two");
                            String address_three = jBbject.getString("address_three");
                            String introduce_one = jBbject.getString("introduce_one");
                            String introduce_two = jBbject.getString("introduce_two");
                            String introduce_there = jBbject.getString("introduce_three");
                            Log.i("jf",title + intruduce + image + imag_one + address_three);
                            //获取站点目录图片
                            String mPath1 = ConfigUtil.BASE_URL + "journeyonline/" + image;
                            Log.i("000",mPath1);
                            URL urll = new URL(mPath1);
                            //设置参数
                            journey.setAddress_one(address_one);
                            journey.setAddress_two(address_two);
                            journey.setAddress_there(address_three);
                            journey.setImag_one(imag_one);
                            journey.setImag_two(imag_two);
                            journey.setImag_there(imag_there);
                            journey.setIntroduce_one(introduce_one);
                            journey.setIntroduce_two(introduce_two);
                            journey.setIntroduce_there(introduce_there);
                            journey.setImage(mPath1);
                            journey.setIntroduce(intruduce);
                            journey.setTitle(title);
                            //把当前的JourneyOnline对象添加到集合中
                            journeys.add(journey);
                        }
                        //通过发送Message对象，将数据发布出去
                        //获取Message对象
                        Message msg = handler.obtainMessage();
                        //设置Message对象的属性（what、obj）
                        msg.what = 1211;
                        msg.obj = journeys;
                        //发送Message对象
                        handler.sendMessage(msg);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
