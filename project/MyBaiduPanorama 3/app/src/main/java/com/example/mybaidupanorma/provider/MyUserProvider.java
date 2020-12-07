package com.example.mybaidupanorma.provider;

import android.util.Log;

import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.example.mybaidupanorma.util.ConstactListData;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 梁钰钊
 * 用户头像设置
 */
public class MyUserProvider implements EaseUI.EaseUserProfileProvider {

    private static MyUserProvider myUserProvider;
    private Map<String, EaseUser> userList = new HashMap<>();
    private ConstactListData constactListData;


    @Override
    public EaseUser getUser(String username) {
        userList = constactListData.getInstance().getConstactList();
        if (userList.containsKey(username)){
            return userList.get(username);
        }
        Log.e("error,没有数据",username);
        return null;
    }


    private MyUserProvider(){
        Log.e("init","init");
    }

    public static MyUserProvider getInstance(){
        if (myUserProvider==null){
            myUserProvider=new MyUserProvider();
        }
        return myUserProvider;
    }
}
