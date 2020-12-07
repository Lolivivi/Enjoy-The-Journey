package com.example.mybaidupanorma.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.example.mybaidupanorma.activity.ChatActivity;
import com.example.mybaidupanorma.util.ConstactListData;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
/**
 * @author 梁钰钊
 * 粉丝列表界面
 */
public class ConstactListFragment extends EaseContactListFragment {

    private Map<String, EaseUser> m = new HashMap<>();
    private ConstactListData constactListData;

    @Override
    protected void initView() {
        super.initView();
        //add loading view
        registerForContextMenu(listView);
    }

    //刷新
    @Override
    public void refresh() {
//        Map<String, EaseUser> m = ConstactListData.getInstance().getConstactList();
        Map <String,EaseUser> m = new HashMap<>();
        String username = "123";
        EaseUser easeUser = new EaseUser(username);
        m.put(username,easeUser);
        if (m instanceof Hashtable<?, ?>) {
            //noinspection unchecked
            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>) m).clone();
        }
        setContactsMap(m);
        super.refresh();

    }


    //获取粉丝列表展示
    @Override
    protected void setUpView() {
        super.setUpView();
        //设置粉丝列表数据
        Map<String, EaseUser> m = constactListData.getInstance().getConstactList();
//        Map <String,EaseUser> m = new HashMap<>();
//        String username = "123";
//        EaseUser easeUser = new EaseUser(username);
//        m.put(username,easeUser);
        if (m instanceof Hashtable<?, ?>) {
            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>) m).clone();
        }
        setContactsMap(m);
        super.setUpView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EaseUser user = (EaseUser) listView.getItemAtPosition(position);
                if (user != null) {
                    String username = user.getUsername();
                    // demo中直接进入聊天页面，实际一般是进入用户详情页
                    startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("userId", username));
                }
            }
        });
    }
}
