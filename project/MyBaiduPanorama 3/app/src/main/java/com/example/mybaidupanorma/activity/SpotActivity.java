package com.example.mybaidupanorma.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;


import com.baidu.lbsapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.mybaidupanorma.MyApplication;
import com.example.mybaidupanorma.R;
import com.example.mybaidupanorma.adapter.SpotAdapter;
import com.example.mybaidupanorma.bean.Spot;
import com.example.mybaidupanorma.util.AddressToLatitudeLongitude;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 景点页面
 * @author 姚沅令
 * @date 2020-11-30
 */
public class SpotActivity extends AppCompatActivity {
    private MapView mapView;
    private BaiduMap mBaiduMap;
    private List<Spot> spots;
    private SpotAdapter mAdapter;
    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 12:
                    spots = (List<Spot>) msg.obj;
                    mAdapter = new SpotAdapter(getApplicationContext(), spots, R.layout.scenic_spot);
                    ListView scListView = findViewById(R.id.spot_list);
                    scListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    //给各个景点设置覆盖物
                    ArrayList<LatLng> latLngs = new ArrayList<>();
                    for (int i = 0; i < spots.size(); i++) {
                        Log.i("i", "" + i);
                        LatLng latLng = new LatLng(spots.get(i).getLat(), spots.get(i).getLongt());
                        latLngs.add(latLng);
                    }
                    showMarkerOptions(latLngs);
                    //定位到第一个景点
                    if (spots.size() != 0) {
                        Log.i("spots.get(0).getLat()", spots.get(0).getLat() + "");
                        getLocationByLL(spots.get(0).getLat(), spots.get(0).getLongt());
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        MyApplication app = (MyApplication) this.getApplication();
        if (app.mBMapManager == null) {
            app.mBMapManager = new BMapManager(app);
            app.mBMapManager.init(new MyApplication.MyGeneralListener());
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_spot2);
        //获取控件ID
        initView();
        //下载景点信息
        loadSpots();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开启定位
        mBaiduMap.setMyLocationEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止定位
        mBaiduMap.setMyLocationEnabled(false);
    }

    /**
     * 获取控件
     */
    private void initView() {
        //获取地图控件引用
        mapView = (MapView) findViewById(R.id.mbmapView);
        mBaiduMap = mapView.getMap();
    }

    /**
     * 根据经纬度返回当前位置
     *
     * @param la
     * @param lg
     */
    private void getLocationByLL(double la, double lg) {
        //描述地图状态将要发生的变化,通过当前经纬度来使地图显示到该位置
        LatLng latLng = new LatLng(la, lg);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.setMapStatus(msu);
    }

    /**
     * 下载景点信息
     * parm:服务器地址
     */
    private void loadSpots() {
        if (spots == null || spots.size() == 0) {
            spots = new ArrayList<Spot>();
            //从服务端下载订单列表
            //获取Intent对象
            Intent intent = getIntent();
            String tag = intent.getStringExtra("tag");
            if (null != intent && tag.equals("journeyAdapter")) {
                //获取传递的参数
                final String[] journeyOneInfo = intent.getStringExtra("journeyOneInfo").split("&&");
                final String[] journeyTwoInfo = intent.getStringExtra("journeyTwoInfo").split("&&");
                final String[] journeyThreeInfo = intent.getStringExtra("journeyThreeInfo").split("&&");
                //创建journeyOnline列表
                final List<Spot> spots = new ArrayList<>();
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            //获取网络输入流
                            URL urll = new URL(journeyOneInfo[1]);
                            URL url2 = new URL(journeyTwoInfo[1]);
                            URL url3 = new URL(journeyThreeInfo[1]);
                            InputStream in1 = urll.openStream();
                            InputStream in2 = url2.openStream();
                            InputStream in3 = url3.openStream();
                            //获取图片
                            Bitmap bitmap1 = BitmapFactory.decodeStream(in1);
                            Bitmap bitmap2 = BitmapFactory.decodeStream(in2);
                            Bitmap bitmap3 = BitmapFactory.decodeStream(in3);
                            //创建Spot对象
                            Spot spot1 = new Spot();
                            spot1.setInfo(journeyOneInfo[2]);
                            spot1.setAddress(journeyOneInfo[0]);
                            //获取地址坐标
                            AddressToLatitudeLongitude at = new AddressToLatitudeLongitude(journeyOneInfo[0]);
                            at.getLatAndLngByAddress();
                            spot1.setLat(at.getLatitude());
                            spot1.setLongt(at.getLongitude());
                            spot1.setImage(bitmap1);
                            Spot spot2 = new Spot();
                            spot2.setInfo(journeyTwoInfo[2]);
                            spot2.setAddress(journeyTwoInfo[0]);
                            //获取地址坐标
                            AddressToLatitudeLongitude at1 = new AddressToLatitudeLongitude(journeyTwoInfo[0]);
                            at1.getLatAndLngByAddress();
                            spot2.setLat(at1.getLatitude());
                            spot2.setLongt(at1.getLongitude());
                            spot2.setImage(bitmap2);
                            Spot spot3 = new Spot();
                            spot3.setInfo(journeyThreeInfo[2]);
                            spot3.setAddress(journeyThreeInfo[0]);
                            AddressToLatitudeLongitude at2 = new AddressToLatitudeLongitude(journeyThreeInfo[0]);
                            at2.getLatAndLngByAddress();
                            spot3.setLat(at2.getLatitude());
                            spot3.setLongt(at2.getLongitude());
                            spot3.setImage(bitmap3);
                            spots.add(spot1);
                            spots.add(spot2);
                            spots.add(spot3);
                            //获取Message对象
                            Message msg = handler.obtainMessage();
                            //设置Message对象的属性（what、obj）
                            msg.what = 12;
                            msg.obj = spots;
                            //发送Message对象
                            handler.sendMessage(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
        }
    }

    /**
     * 显示标注覆盖物
     * param:image景点图片，position景点经纬度坐标
     */
    private void showMarkerOptions(final ArrayList<LatLng> position) {
        //显示一个单一的标注覆盖物
        //1. 创建标注覆盖物对象，并设置其参数
        //获取图标（BitmapDescriptor对象)
        BitmapDescriptor bDescriptor = BitmapDescriptorFactory
                .fromResource(R.mipmap.blue_marker);
        for(final LatLng latLng : position) {
            //创建覆盖物显示位置的坐标对象
            MarkerOptions markerOptions = new MarkerOptions()
                    .icon(bDescriptor)//必须项，设置覆盖物图标
                    .position(latLng)//必须项，设置覆盖物位置
                    .draggable(true)// 可选项，设置是否允许拖拽
                    .alpha(0.8f)//可选项，设置透明度
                    .animateType(MarkerOptions.MarkerAnimateType.grow);//从地上生长出来样式
            //2. 在地图上显示覆盖物对象
            mBaiduMap.addOverlay(markerOptions);
        }
        //创建折线覆盖物选项对象
        PolylineOptions polylineOptions = new PolylineOptions()
                .points(position)
                .dottedLine(true)
                .zIndex(3);
        //显示
        mBaiduMap.addOverlay(polylineOptions);
        // 注册标注覆盖物点击事件监听器
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //进入全景地图
                Log.i("跳了", "kk");
                Intent intent = new Intent();
                intent.putExtra("lat", marker.getPosition().latitude + "");
                intent.putExtra("lon", marker.getPosition().longitude + "");
                intent.setClass(SpotActivity.this, PanoramaActivity.class);
                startActivity(intent);
                return true;
            }
        });
        //注册标注覆盖物拖拽事件监听器
        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(Marker marker) {
                //正在拖拽时触发
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

            }

            @Override
            public void onMarkerDragStart(Marker marker) {
                //开始拖拽前触发
            }
        });
    }

}