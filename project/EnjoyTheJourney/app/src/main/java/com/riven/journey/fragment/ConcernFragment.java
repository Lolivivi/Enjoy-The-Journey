package com.riven.journey.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riven.journey.R;
import com.riven.journey.adapter.recyclerAdapter.ConcernRecyclerAdapter;
import com.riven.journey.bean.Comments;
import com.riven.journey.bean.ConcernUser;
import com.riven.journey.util.ConfigUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 关注
 * @author 张硕
 */
public class ConcernFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<ConcernUser> concernUsers;
    private View view;
    private Context mContext;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {

        }
    };
    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_found_concern,container,false);
        mContext = view.getContext();
        //网络输入流获取关注的对象
        concernUsers = new ArrayList<>();
        getConcernUsers();
        recyclerView = view.findViewById(R.id.rec_found_concern);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(manager);
        return view;
    }

    private void getConcernUsers() {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.BASE_URL+"note/followInterface?tel=1");
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
                        if(jUser.getString("nineSrc")!=null){
                            List<String> imgs = new ArrayList<>();
                            String[] imgUrls = jUser.getString("nineSrc").split(",");
                            for(int j=0;j<imgUrls.length;j++){
                                imgs.add(imgUrls[j]);
                            }
                            imgs.add(user.getFirstImg());
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
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(new ConcernRecyclerAdapter(concernUsers,mContext));
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
