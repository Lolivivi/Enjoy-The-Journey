package com.example.mybaidupanorma.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybaidupanorma.R;
import com.example.mybaidupanorma.adapter.recyclerAdapter.ConcernRecyclerAdapter;
import com.example.mybaidupanorma.bean.Comments;
import com.example.mybaidupanorma.bean.ConcernUser;
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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 张硕
 */
public class ConcernFragment extends Fragment {
    private RecyclerView recyclerView;
    private View view;
    private Context mContext;
    private SmartRefreshLayout smartRefreshLayout;
    private int count = 0;

    private FragmentActivity activity;
    public ConcernFragment(){}
    public ConcernFragment(FragmentActivity activity){
        this.activity = activity;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_found_concern,container,false);
        smartRefreshLayout = view.findViewById(R.id.refresh_concern);
        smartRefreshLayout.setEnableLoadMore(true);
        mContext = view.getContext();
        activity = getActivity();
        recyclerView = view.findViewById(R.id.rec_found_concern);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(manager);
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
                getConcernUsers();
                smartRefreshLayout.finishRefresh(1500);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener(){
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                getConcernUsers();
                smartRefreshLayout.finishLoadMore(500);
            }
        });
        return view;
    }


    private void getConcernUsers() {
        final List<ConcernUser> concernUsers = new ArrayList<>();
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.BASE_URL+"note/followInterface");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    OutputStream out = conn.getOutputStream();
                    out.write(("tel=1&count=0").getBytes());
                    InputStream in = conn.getInputStream();
                    byte[] bytes = new byte[512];
                    StringBuffer buffer = new StringBuffer();
                    int len = -1;
                    while ((len = in.read(bytes,0,bytes.length))!=-1){
                        buffer.append(new String(bytes,0,len));
                    }
                    String result = buffer.toString();
//                    if(result.equals("1")){
//                        new Handler(Looper.getMainLooper()).post(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(mContext,"没有数据了哦",Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }else{
                        JSONObject jConcernUser = new JSONObject(result);
                        JSONArray jArray = jConcernUser.getJSONArray("array");
                        for (int i = 0;i<jArray.length();i++){
                            ConcernUser user = new ConcernUser();
                            JSONObject jUser = jArray.getJSONObject(i);
                            user.setUserName(jUser.getString("userName"));
                            user.setRelTime(jUser.getString("upload_time"));
                            user.setUserPhoto(jUser.getString("userHeadSrc"));
                            user.setFirstImg(jUser.getString("cover_src"));
                            user.setArtTitle(jUser.getString("titlle"));
                            user.setArtLike(jUser.getString("like_num"));
                            user.setArtColl(jUser.getString("collection_num"));
                            user.setIsLike(jUser.getString("isLove"));
                            user.setIsColl(jUser.getString("isCollection"));
                            user.setImgType(jUser.getString("res_type"));
                            if(!jUser.getString("nineSrc").equals("1")){
                                List<String> imgs = new ArrayList<>();
                                imgs.add(user.getFirstImg());
                                String[] imgUrls = jUser.getString("nineSrc").split(",");
                                for(int j=0;j<imgUrls.length;j++){
                                    imgs.add(imgUrls[j]);
                                }
                                user.setImgUrls(imgs);
                            }else {
                                List<String> imgs = new ArrayList<>();
                                imgs.add(user.getFirstImg());
                                user.setImgUrls(imgs);
                            }
                            //分割评论
                            List<Comments> commentsList = new ArrayList<>();
                            String[] comments = jUser.getString("twoComments").split("&&&");
                            for(int j = 0;j<comments.length;j++){
                                Comments comt = new Comments();
                                String[] comts = comments[j].split("%%");
                                for (int f=0;f<comts.length;f++){
                                    if(f==0){
                                        comt.setComUser(comts[0]);
                                    }else if(f==1){
                                        comt.setComment(comts[1]);
                                    }else if(f==2){
                                        comt.setComTime(comts[2]);
                                    }
                                }
                                commentsList.add(comt);
                            }
                            user.setArtCom(commentsList);
                            user.setArtComCount(jUser.getString("comments_num"));
                            user.setArtId(jUser.getString("note_id"));
                            concernUsers.add(user);
                        }
//                    }

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(new ConcernRecyclerAdapter(concernUsers,mContext,activity));
                        }
                    });
                    in.close();
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
