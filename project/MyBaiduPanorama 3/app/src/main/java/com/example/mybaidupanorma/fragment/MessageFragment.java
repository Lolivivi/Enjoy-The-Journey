package com.example.mybaidupanorma.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.example.mybaidupanorma.activity.ChatActivity;
import com.example.mybaidupanorma.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

/**
 * @author 梁钰钊
 */
public class MessageFragment extends Fragment {
    private View view;
    private Context mContext;
    private ConstactListFragment contactListFragment;
    private LikeFragment likeFragment;
    private EaseConversationListFragment conversationFragment;
    private TextView text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message,container,false);
        text = view.findViewById(R.id.text);
        mContext = view.getContext();
//        //退出登录
//        logout = view.findViewById(R.id.logout);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EMClient.getInstance().logout(false, new EMCallBack() {
//                    @Override
//                    public void onSuccess() {
//                        startActivity(new Intent(mContext, LoginActivity.class));
//                    }
//
//                    @Override
//                    public void onError(int i, String s) {
//                        Log.e("logout","退出失败");
//                    }
//
//                    @Override
//                    public void onProgress(int i, String s) {
//
//                    }
//                });
//            }
//        });

        //导航栏
        BottomBar bottomBar = view.findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId){
                    case R.id.tab_favorites:
                        conversationfragment();
                        text.setText("全部私信");
                        conversationFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
                            @Override
                            public void onListItemClicked(EMConversation conversation) {
                                startActivity(new Intent(mContext, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID,conversation.conversationId()));
                            }
                        });
                        break;
                    case R.id.tab_nearby:
                        contactListfragment();
                        text.setText("我的粉丝");
                        break;
                    case R.id.tab_friends:
                        likefragment();
                        text.setText("喜欢我的");
                        break;
                }
            }
        });
        return view;
    }

    //会话列表
    private void conversationfragment(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if(conversationFragment==null){
            conversationFragment = new EaseConversationListFragment();
            transaction.add(R.id.contentContainer,conversationFragment);
        }
        transaction.setCustomAnimations(
                R.anim.slide_right_in,
                R.anim.slide_left_out,
                R.anim.slide_left_in,
                R.anim.slide_right_out
        );
        hideFragment(transaction);
        transaction.show(conversationFragment);
        transaction.commit();
    }

    //联系人列表
    private void contactListfragment(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if(contactListFragment==null){
            contactListFragment = new ConstactListFragment();
            transaction.add(R.id.contentContainer,contactListFragment);
        }
        transaction.setCustomAnimations(
                R.anim.slide_right_in,
                R.anim.slide_left_out,
                R.anim.slide_left_in,
                R.anim.slide_right_out
        );
        hideFragment(transaction);
        transaction.show(contactListFragment);
        transaction.commit();
    }

    //喜欢我的列表
    private void likefragment(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (likeFragment==null){
            likeFragment = new LikeFragment();
            transaction.add(R.id.contentContainer,likeFragment);
        }
        transaction.setCustomAnimations(
                R.anim.slide_right_in,
                R.anim.slide_left_out,
                R.anim.slide_left_in,
                R.anim.slide_right_out
        );
        hideFragment(transaction);
        transaction.show(likeFragment);
        transaction.commit();
    }

    //隐藏fragment
    private void hideFragment(FragmentTransaction transaction){
        if (contactListFragment!=null){
            transaction.hide(contactListFragment);
        }
        if (conversationFragment!=null){
            transaction.hide(conversationFragment);
        }
        if (likeFragment!=null){
            transaction.hide(likeFragment);
        }
        if (likeFragment !=null){
            transaction.hide(likeFragment);
        }
    }
}
