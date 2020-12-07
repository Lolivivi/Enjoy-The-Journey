package com.example.mybaidupanorma.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.baidu.lbsapi.panoramaview.PanoramaView;
import com.baidu.lbsapi.panoramaview.PanoramaViewListener;
import com.example.mybaidupanorma.R;


/**
 * 全景地图页面
 * @author 姚沅令
 */
public class PanoramaActivity extends AppCompatActivity {
    private PanoramaView panoramaView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama);
        panoramaView = findViewById(R.id.panorama);
        //获取坐标
        final Intent intent = getIntent();
        if(null != intent){
            Double lat = Double.valueOf(intent.getStringExtra("lat"));
            Double lon = Double.valueOf(intent.getStringExtra("lon"));
            Log.i("全景","" + lat + lon);
            getPanorMap(lat,lon);
        }
    }
    /**
     * 显示全景图
     */
    private void getPanorMap(double la, double lg) {
        //设置显示级别
        panoramaView.setPanoramaImageLevel(PanoramaView.ImageDefinition.ImageDefinitionHigh);
        panoramaView.getPanoramaPitch();
        //设置全景图的偏航角
        panoramaView.setPanoramaHeading(60);
        //获取当前全景图的偏航角
        panoramaView.getPanoramaHeading();
        //设置监听器
        panoramaView.setPanoramaViewListener(new PanoramaViewListener() {

            @Override
            public void onMoveStart() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onMoveEnd() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onMessage(String arg0, int arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onLoadPanoramaError(String arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onLoadPanoramaEnd(String arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onLoadPanoramaBegin() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDescriptionLoadEnd(String arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onCustomMarkerClick(String arg0) {
                // TODO Auto-generated method stub

            }
        });
        panoramaView.setPanorama(lg, la);
    }
}