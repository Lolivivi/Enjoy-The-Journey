package com.riven.journey;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.riven.journey.fragment.IndexFragment;
import com.riven.journey.fragment.FoundFragment;
import com.riven.journey.fragment.MyPageFragment;

public class MainActivity extends AppCompatActivity {
    private Fragment currentFragment = new Fragment();
    private IndexFragment indexFragment;
    private FoundFragment foundFragment;
    private MyPageFragment myPageFragment;
    private TextView tvFirst;
    private TextView tvFound;
    private TextView tvMine;
    private RelativeLayout rlRoot;
    //悬浮控件
    private FloatingActionButton buttonActionGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);

        tvFirst = findViewById(R.id.tv_main_index);
        tvFound = findViewById(R.id.tv_main_found);
        tvMine = findViewById(R.id.tv_main_mine);
        buttonActionGrid=findViewById(R.id.action_grid);
        rlRoot = findViewById(R.id.rl_root);
        indexFragment = new IndexFragment();
        foundFragment = new FoundFragment();
        myPageFragment = new MyPageFragment();
        changeTab(indexFragment);
        currentFragment = indexFragment;
        buttonActionGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //悬浮按钮的点击事件
                clickButton();
            }
        });
    }

    public void textClick(View view){
        switch (view.getId()){
            case R.id.tv_main_index:
                tvFirst.setTextColor(getResources().getColor(R.color.textClick));
                tvFound.setTextColor(getResources().getColor(R.color.black));
                tvMine.setTextColor(getResources().getColor(R.color.black));
                changeTab(indexFragment);
                break;
            case R.id.tv_main_found:
                tvFirst.setTextColor(getResources().getColor(R.color.black));
                tvFound.setTextColor(getResources().getColor(R.color.textClick));
                tvMine.setTextColor(getResources().getColor(R.color.black));
                changeTab(foundFragment);
                break;
            case R.id.tv_main_mine:
                tvFirst.setTextColor(getResources().getColor(R.color.black));
                tvFound.setTextColor(getResources().getColor(R.color.black));
                tvMine.setTextColor(getResources().getColor(R.color.textClick));
                changeTab(myPageFragment);
                break;
        }

    }

    private void changeTab(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if(currentFragment!=fragment){
            if(!fragment.isAdded()){
                transaction.add(R.id.fram_main,fragment);
            }
            transaction.hide(currentFragment);
            transaction.show(fragment);
            transaction.commit();
            currentFragment = fragment;
        }
    }

    private void clickButton() {
        //创建对象
        final PopupWindow popupWindow = new PopupWindow(this);
        //设置弹出窗口的宽度
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_white));
        popupWindow.setAnimationStyle(R.style.BottomDialog_Animation);
        //设置他的视图
        View view = getLayoutInflater().inflate(R.layout.upload_template, null);
        //设置试图当中控件的属性和监听器
        //取消按钮的点击事件
        ImageView btnCancel = view.findViewById(R.id.iv_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //风景模板上传效果
        TextView tvScenery=view.findViewById(R.id.tv_scenery);
        tvScenery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), TemplateActivity.class);
                startActivity(intent);
            }
        });
        popupWindow.setContentView(view);
        //显示PopupWindow(指定显示的位置)
        popupWindow.showAtLocation(rlRoot, Gravity.CENTER, 0, 0);
    }
}