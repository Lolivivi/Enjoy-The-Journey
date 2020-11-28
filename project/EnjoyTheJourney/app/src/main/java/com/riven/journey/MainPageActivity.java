//package com.riven.journey;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.ashokvarma.bottomnavigation.BadgeItem;
//import com.ashokvarma.bottomnavigation.BottomNavigationBar;
//import com.ashokvarma.bottomnavigation.BottomNavigationItem;
//import com.getbase.floatingactionbutton.FloatingActionButton;
//import com.riven.journey.fragment.MyPageFragment;
//
//import java.util.ArrayList;
//
///**
// * @author 田春雨
// */
//
//public class MainPageActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{
//    private BottomNavigationBar mBottomNavigationBar;
//    private ArrayList<Fragment> fragments;
//    private String phone;
//    private FloatingActionButton buttonActionGrid;
//    private RelativeLayout llRoot;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.frag_ment);
//        Intent request=getIntent();
//        phone=request.getStringExtra("phone");
//        Log.e("lww",phone);
//        mBottomNavigationBar=findViewById(R.id.bottom_navigation_bar);
//        buttonActionGrid=findViewById(R.id.action_grid);
//        llRoot=findViewById(R.id.ll_root);
//        buttonActionGrid.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //悬浮按钮的点击事件
//                clickButton();
//            }
//        });
//        assignViews();
//
//    }
//
//    private void clickButton() {
//        //创建对象
//        final PopupWindow popupWindow = new PopupWindow(this);
//        //设置弹出窗口的宽度
//        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
//        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_white));
//        popupWindow.setAnimationStyle(R.style.BottomDialog_Animation);
//        //设置他的视图
//        View view = getLayoutInflater().inflate(R.layout.upload_template, null);
//        //设置试图当中控件的属性和监听器
//        //取消按钮的点击事件
//        ImageView btnCancel = view.findViewById(R.id.iv_cancel);
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });
//        //风景模板上传效果
//        TextView tvScenery=view.findViewById(R.id.tv_scenery);
//        tvScenery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(), TemplateActivity.class);
//                intent.putExtra("phone",phone);
//                startActivity(intent);
//            }
//        });
//        popupWindow.setContentView(view);
//        //显示PopupWindow(指定显示的位置)
//        popupWindow.showAtLocation(llRoot, Gravity.CENTER, 0, 0);
//    }
//
//
//    private void assignViews(){
//        //添加标签的消息数量
//        BadgeItem numberBadgeItem=new BadgeItem()
//                .setBorderWidth(4)
//                .setBackgroundColorResource(R.color.colorAccent)
//                .setText("1")
//                .setHideOnSelect(true);
//        mBottomNavigationBar=findViewById(
//                R.id.bottom_navigation_bar);
//        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
//        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
//        mBottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.photo,"photo").setInActiveColor(R.color.colorAccent).setBadgeItem(numberBadgeItem))
//                .setFirstSelectedPosition(0)
//                .initialise();
//        fragments=getFragments();
//        setDefaultFragment();//设置默认选项
//        mBottomNavigationBar.setTabSelectedListener(this); //设置监听
//
//    }
//    private void setDefaultFragment(){
//        FragmentManager fragmentManager=getSupportFragmentManager();  //获取FragmentManager
//        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();//开启一个事务
//        fragmentTransaction.add(R.id.content, MyPageFragment.newInstance(phone));
//        fragmentTransaction.commit();
//    }
//    private ArrayList<Fragment> getFragments(){
//        ArrayList<Fragment> fragments=new ArrayList<>();
//        fragments.add(MyPageFragment.newInstance(phone));
//        return fragments;
//    }
//
//    @Override
//    public void onTabSelected(int position) {   //被选中
//        if(fragments!=null){
//            if(position<fragments.size()){
//                FragmentManager fm=getSupportFragmentManager();
//                FragmentTransaction ft=fm.beginTransaction();
//                Fragment fragment=fragments.get(position);
//                if(fragment.isAdded()){
//                    ft.replace(R.id.content,fragment);
//                }else {
//                    ft.add(R.id.content,fragment);
//                }
//                ft.commitAllowingStateLoss();
//            }
//        }
//
//    }
//
//    @Override
//    public void onTabUnselected(int position) {  //未被选中
//        if(fragments!=null){
//            FragmentManager fm=getSupportFragmentManager();
//            FragmentTransaction ft=fm.beginTransaction();
//            Fragment fragment=fragments.get(position);
//            ft.remove(fragment);
//            ft.commitAllowingStateLoss();
//        }
//
//    }
//
//    @Override
//    public void onTabReselected(int position) { //重新选中
//
//    }
//}
