package com.riven.journey.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.riven.journey.R;
import com.riven.journey.adapter.IndexAdapter;
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
 * 首页
 * @author 任锐媛
 */
public class IndexFragment extends Fragment {
    //recycler对象
    private RecyclerView recyclerView;
    private ImageView ivSearch;
    private EditText etSearch;
    //推荐集合
    private List<RecommendUser> users;
    //环境上下文
    private Context mContext;
    IndexAdapter adapter;
    //当前用户手机号
    private String tel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index,container,false);
        mContext = view.getContext();
        users = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rec_index);
        ivSearch = view.findViewById(R.id.iv_search);
        etSearch = view.findViewById(R.id.et_search);
        StaggeredGridLayoutManager slm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        slm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(slm);
        getRecommendUser();

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != etSearch.getText()) {
                    search(etSearch.getText().toString());
                    Log.e("rry", "rry");
                }
            }
        });
        return view;
    }

    private void search(String s) {
        if (null != users)
            users.clear();
        new Thread(){
            @Override
            public void run() {
                URL url = null;
                try {
//                    SharedPreferences sp = mContext.getSharedPreferences("setting", Context.MODE_PRIVATE);
//                    tel = sp.getString("tel", "1");
                    url = new URL(ConfigUtil.BASE_URL+"note/searchInterface?tel=1&param=" + s);
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
                            adapter.notifyDataSetChanged();
//                            recyclerView.setAdapter(new IndexAdapter(mContext,users));
                        }
                    });
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getRecommendUser() {
        new Thread(){
            @Override
            public void run() {
                URL url = null;
                try {
//                    SharedPreferences sp = mContext.getSharedPreferences("setting", Context.MODE_PRIVATE);
//                    tel = sp.getString("tel", "1");
                    url = new URL(ConfigUtil.BASE_URL+"note/homeInterface?tel=1");
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
                            adapter = new IndexAdapter(mContext,users);
                            recyclerView.setAdapter(adapter);
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
