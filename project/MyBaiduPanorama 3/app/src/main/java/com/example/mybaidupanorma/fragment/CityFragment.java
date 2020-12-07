package com.example.mybaidupanorma.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.mybaidupanorma.R;
import com.example.mybaidupanorma.adapter.recyclerAdapter.RecommendAdapter;
import com.example.mybaidupanorma.bean.RecommendUser;
import com.example.mybaidupanorma.util.ConfigUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 张硕
 */

public class CityFragment extends Fragment {
    private RecyclerView recyclerView;
    //推荐集合
    private List<RecommendUser> users;
    //环境上下文
    private Context mContext;
    protected boolean isTouch = false;
    private SmartRefreshLayout smartRefreshLayout;
    private int count = 1;
    private int page = 0;
    private String tel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_found_city,container,false);
        smartRefreshLayout = view.findViewById(R.id.refresh_city);
        recyclerView = view.findViewById(R.id.rec_found_city);
        mContext = view.getContext();
        tel = mContext.getSharedPreferences("setting", Context.MODE_PRIVATE).getString("tel", "1");
        users = new ArrayList<>();
        StaggeredGridLayoutManager slm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        slm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(slm);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //网络输入流获取关注的对象
                if(isTouch==true && count==1){
                    getRecommendUser();
                    Toast.makeText(mContext,"4444444444",Toast.LENGTH_SHORT).show();
                    count+=1;
                }
                smartRefreshLayout.finishRefresh(1000);
            }
        });
        return view;
    }
    //更改istouch属性
    public void setTouch(boolean isTouch,int i){
        this.isTouch = isTouch;
        if(i==1){
            smartRefreshLayout.autoRefresh();
        }
    }
    private void getRecommendUser() {
        new Thread(){
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL(ConfigUtil.BASE_URL+"note/sameCityInterface");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    OutputStream out = conn.getOutputStream();
                    out.write(("city=石家庄&tel="+tel+"&count="+page).getBytes());
                    page+=5;
                    InputStream in = conn.getInputStream();
                    byte[] bytes = new byte[512];
                    StringBuffer buffer = new StringBuffer();
                    int len = -1;
                    while ((len = in.read(bytes,0,bytes.length))!=-1){
                        buffer.append(new String(bytes,0,len));
                    }
                    String result = buffer.toString();
                    JSONObject jConcernUser = new JSONObject(result);
                    JSONArray jArray = jConcernUser.getJSONArray("array");
                    for (int i = 0;i<jArray.length();i++){
                        RecommendUser reUser = new RecommendUser();
                        JSONObject jUser = jArray.getJSONObject(i);
                        reUser.setUserImg(jUser.getString("userHeadSrc"));
                        reUser.setLikeCount(jUser.getString("like_num"));
                        reUser.setTitle(jUser.getString("titlle"));
                        reUser.setImgUrl(jUser.getString("cover_src"));
                        reUser.setUserName(jUser.getString("userName"));
                        reUser.setArtIsLike(jUser.getString("isLove"));
                        reUser.setArtId(jUser.getString("note_id"));
                        reUser.setImgType(jUser.getString("res_type"));
                        users.add(reUser);
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(new RecommendAdapter(mContext,users));
                            smartRefreshLayout.finishRefresh();

                        }
                    });
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }.start();
    }
}
