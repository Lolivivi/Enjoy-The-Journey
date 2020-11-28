package com.riven.journey.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.riven.journey.R;
import com.riven.journey.adapter.recyclerAdapter.RecommendAdapter;
import com.riven.journey.bean.RecommendUser;
import com.riven.journey.util.ConfigUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 推荐
 * @author 张硕
 */
public class RecommendFragment extends Fragment {
    //recycler对象
    private RecyclerView recyclerView;
    //推荐集合
    private List<RecommendUser> users;
    //环境上下文
    private Context mContext;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_found_recommend,container,false);
        mContext = view.getContext();
        users = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rec_found_recommend);
        StaggeredGridLayoutManager slm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        slm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(slm);
        getRecommendUser();
        return view;
    }

    private void getRecommendUser() {
        new Thread(){
            @Override
            public void run() {
                URL url = null;
                try {

                    url = new URL(ConfigUtil.BASE_URL+"note/recommendInterface?tel=1");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
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
                        users.add(reUser);
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(new RecommendAdapter(mContext,users));
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
