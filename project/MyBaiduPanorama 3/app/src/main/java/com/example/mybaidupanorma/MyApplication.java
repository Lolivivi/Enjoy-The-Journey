package com.example.mybaidupanorma;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.multidex.MultiDex;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseUI;
import com.example.mybaidupanorma.provider.MyUserProvider;

/**
 * @author 梁钰钊 姚沅令
 * easeui初始化
 * 创建百度地图地图引擎管理类
 */
public class MyApplication extends Application {
    private static MyApplication mInstance = null;
    public BMapManager mBMapManager = null;

    @Override
    public void onCreate() {
        super.onCreate();
        EaseUI.getInstance().init(this,null);
        Log.e("init","初始化成功!");
        EMClient.getInstance().setDebugMode(true);
        EaseUI.getInstance().setUserProfileProvider(MyUserProvider.getInstance());
        mInstance = this;
        MultiDex.install(this);
        initEngineManager(this);
    }

    public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }
        Log.d("ljx", "initEngineManager");
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    // 常用事件监听，用来处理通常的网络错误，授权验证错误等
    public static class MyGeneralListener implements MKGeneralListener {

        @Override
        public void onGetPermissionState(int iError) {
            // 非零值表示key验证未通过
            if (iError != 0) {
                // 授权Key错误：
                Toast.makeText(MyApplication.getInstance().getApplicationContext(),
                        "请在AndoridManifest.xml中输入正确的授权Key,并检查您的网络连接是否正常！error: " + iError, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "key认证成功", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }
}
