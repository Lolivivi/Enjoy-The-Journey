package com.example.mybaidupanorma.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mybaidupanorma.MainActivity;
import com.example.mybaidupanorma.activity.PanoramaActivity;
import com.example.mybaidupanorma.R;
import com.example.mybaidupanorma.bean.Spot;

import java.util.ArrayList;
import java.util.List;

/**
 * 子景点Adapter
 * @date 2020-11-29
 * @author 姚沅令
 */
public class SpotAdapter extends BaseAdapter {
    private List<Spot> spots = new ArrayList<>();
    private int itemLayoutRes;
    private Context mContext;

    public SpotAdapter(Context context, List<Spot> spots, int journey_item) {
        this.mContext = context;
        this.itemLayoutRes = journey_item;
        this.spots = spots;
    }

    @Override
    public int getCount() {
        return spots.size();
    }

    @Override
    public Object getItem(int position) {
        return spots.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewHolder holder = null;
        if (null == convertView) {
            //获取控件
            holder = new ViewHolder();
            convertView = inflater.inflate(itemLayoutRes, null);
            holder.go_btn = convertView.findViewById(R.id.go_btn);
            holder.spot_img = convertView.findViewById(R.id.spot_img);
            holder.introduce_spot = convertView.findViewById(R.id.introduce_spot);
            holder.spot_title = convertView.findViewById(R.id.title_spot);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //给控件设置参数
        holder.introduce_spot.setText(spots.get(position).getInfo());
        Glide.with(mContext).load( spots.get(position).getImage()).into(holder.spot_img);
        holder.spot_title.setText(spots.get(position).getAddress());
        //在ConvertView中缓存holder对象
        convertView.setTag(holder);
        //跳转到景点详情页
        holder.go_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建Intent对象
                Intent intent = new Intent();
                //设置传递参数
                intent.putExtra("spot_address",spots.get(position).getAddress());
                intent.putExtra("tag","spotAdapter");
                //TODO:不知道是干啥的类
                intent.setClass(mContext, MainActivity.class);
                //跳转到旅游界面
                mContext.startActivity(intent);
            }
        });
        //跳转到全景地图页面
        holder.spot_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入全景地图
                Intent intent = new Intent();
                intent.putExtra("lat",spots.get(position).getLat() + "");
                intent.putExtra("lon",spots.get(position).getLongt() + "");
                Log.i("SpotAdapter","跳转");
                intent.setClass(mContext, PanoramaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    //定义了内部类缓存控件，避免系统消耗过大
    static class ViewHolder {
        private ImageView spot_img;
        private TextView introduce_spot;
        private ImageView go_btn;
        private TextView spot_title;
    }

}