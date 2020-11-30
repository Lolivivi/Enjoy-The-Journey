package com.riven.journey.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;


import com.riven.journey.R;
import com.riven.journey.adapter.MyFragmentPageAdapter;

import java.util.ArrayList;
import java.util.List;

public class FoundFragment extends Fragment {
    //ViewPager+Fragment滑动布局
    private List<Fragment> list;
    private ViewPager mViewPager;
    private FragmentManager fragmentManager;
    //视图
    private View view;
    private Fragment currentFragment = new Fragment();
    //控件
    private TextView tvConcern;
    private TextView tvRecommend;
    private TextView tvCity;
    private TextView tvOnline;
    //fragment控件
    private CityFragment cityFragment;
    private ConcernFragment concernFragment;
    private OnlineFragment onlineFragment;
    private RecommendFragment recommendFragment;
    private Context mContext;
    //滑动监听是第几次滑动
    private int i=0;
    private int j=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_found,container,false);
        mContext = view.getContext();
        //注册控件
        findView();
        cityFragment = new CityFragment();
        concernFragment = new ConcernFragment();
        onlineFragment = new OnlineFragment();
        recommendFragment = new RecommendFragment();
        fragmentManager = getFragmentManager();
        //把fragment加入集合中
        list.add(concernFragment);
        list.add(recommendFragment);
        list.add(onlineFragment);
        list.add(cityFragment);

        setListeners();
        //初始化页面内容
        initViewPager();
        return view;
    }


    private void setListeners() {
        tvConcern.setOnClickListener(new MyOnClickListener(0));
        tvRecommend.setOnClickListener(new MyOnClickListener(1));
        tvCity.setOnClickListener(new MyOnClickListener(3));
        tvOnline.setOnClickListener(new MyOnClickListener(2));
    }
    //标题栏监听
    public class MyOnClickListener implements View.OnClickListener{
        private int index = 0 ;
        public MyOnClickListener(int i) {
            index = i;
        }
        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(index);
        }
    }

    //初始化viewpager事件
    private void initViewPager() {
        mViewPager.setAdapter(new MyFragmentPageAdapter(fragmentManager,list));
        //让ViewPager缓存2个页面
        mViewPager.setOffscreenPageLimit(3);

        //设置默认打开第一页
        mViewPager.setCurrentItem(0);
        //将顶部文字恢复默认值
        initTextStlye();
        tvConcern.setTextColor(getResources().getColor(R.color.white));
        tvConcern.setBackground(getResources().getDrawable(R.drawable.title_background_style));
        mViewPager.setOnPageChangeListener(new MyPagerChangeListener());
    }
    //页面切换监听
    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Animation animation = null;
            switch (position){
                case 0:
                    changeTitle(0);
                    break;
                case 1:
                    i+=1;
//                    recommendFragment.setTouch(true,i);
                    changeTitle(1);
                    break;
                case 2:
                    changeTitle(3);
                    break;
                case 3:
                    j+=1;
//                    cityFragment.setTouch(true,j);
                    changeTitle(2);
                    break;

            }

        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    //z注册控件
    private void findView() {
        tvCity = view.findViewById(R.id.tv_found_city);
        tvConcern = view.findViewById(R.id.tv_found_concern);
        tvOnline = view.findViewById(R.id.tv_found_online);
        tvRecommend = view.findViewById(R.id.tv_found_recommend);
        list = new ArrayList<>();
        mViewPager = view.findViewById(R.id.my_view_pager);

    }

    private void changeTitle(int position){
        switch (position){
            case 0:
                initTextStlye();
                tvConcern.setTextColor(getResources().getColor(R.color.white));
                tvConcern.setBackground(getResources().getDrawable(R.drawable.title_background_style));
                break;
            case 1:
                initTextStlye();
                tvRecommend.setTextColor(getResources().getColor(R.color.white));
                tvRecommend.setBackground(getResources().getDrawable(R.drawable.title_background_style));
                break;
            case 2:
                initTextStlye();
                tvCity.setTextColor(getResources().getColor(R.color.white));
                tvCity.setBackground(getResources().getDrawable(R.drawable.title_background_style));
                break;
            case 3:
                initTextStlye();
                tvOnline.setTextColor(getResources().getColor(R.color.white));
                tvOnline.setBackground(getResources().getDrawable(R.drawable.title_background_style));
                break;
        }
    }
    public void initTextStlye(){
        tvOnline.setTextColor(getResources().getColor(R.color.gray));
        tvOnline.setBackground(getResources().getDrawable(R.drawable.title_background_null));
        tvRecommend.setTextColor(getResources().getColor(R.color.gray));
        tvRecommend.setBackground(getResources().getDrawable(R.drawable.title_background_null));
        tvCity.setTextColor(getResources().getColor(R.color.gray));
        tvCity.setBackground(getResources().getDrawable(R.drawable.title_background_null));
        tvConcern.setTextColor(getResources().getColor(R.color.gray));
        tvConcern.setBackground(getResources().getDrawable(R.drawable.title_background_null));
    }
}
