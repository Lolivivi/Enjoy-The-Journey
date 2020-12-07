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
import com.example.mybaidupanorma.R;
import com.example.mybaidupanorma.activity.SpotActivity;
import com.example.mybaidupanorma.bean.Journey;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 姚沅令
 * @date 2020-11-29
 */
public class JourneyAdapter extends BaseAdapter {
    private List<Journey> journeyOnlineList = new ArrayList<>();
    private int itemLayoutRes;
    private Context mContext;

    public JourneyAdapter(Context context, List<Journey> journeyOnlineList, int journey_item) {
        this.mContext = context;
        this.itemLayoutRes = journey_item;
        this.journeyOnlineList = journeyOnlineList;
    }

    @Override
    public int getCount() {
        return journeyOnlineList.size();
    }

    @Override
    public Object getItem(int position) {
        return journeyOnlineList.get(position);
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
            holder.go_journey = convertView.findViewById(R.id.go_journey);
            holder.online_img = convertView.findViewById(R.id.online_img);
            holder.online_introduce = convertView.findViewById(R.id.introduce);
            holder.online_title = convertView.findViewById(R.id.title);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //给控件设置参数
        holder.online_title.setText(journeyOnlineList.get(position).getTitle());
        holder.online_introduce.setText(journeyOnlineList.get(position).getIntroduce());
        Glide.with(mContext).load(journeyOnlineList.get(position).getImage()).into(holder.online_img);
        //在ConvertView中缓存holder对象
        convertView.setTag(holder);
        //跳转到景点详情页
        holder.go_journey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建Intent对象
                Intent intent = new Intent();
                //设置传递参数
                String journeyOneInfo = journeyOnlineList.get(position).getAddress_one() + "&&" + journeyOnlineList.get(position).getImag_one() + "&&" + journeyOnlineList.get(position).getIntroduce_one();
                String journeyTwoInfo = journeyOnlineList.get(position).getAddress_two() + "&&" + journeyOnlineList.get(position).getImag_two() + "&&" + journeyOnlineList.get(position).getIntroduce_two();
                String journeyThreeInfo = journeyOnlineList.get(position).getAddress_there() + "&&" + journeyOnlineList.get(position).getImag_there() + "&&" + journeyOnlineList.get(position).getIntroduce_there();
                Log.i("qqq",journeyOneInfo);
                Log.i("www",journeyTwoInfo);
                Log.i("ddd",journeyThreeInfo);
                intent.putExtra("journeyOneInfo",journeyOneInfo);
                intent.putExtra("journeyTwoInfo",journeyTwoInfo);
                intent.putExtra("journeyThreeInfo",journeyThreeInfo);
                intent.putExtra("tag","journeyAdapter");
                intent.setClass(mContext, SpotActivity.class);
                //跳转到旅游界面
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    //定义了内部类缓存控件，避免系统消耗过大
    static class ViewHolder {
        private ImageView online_img;
        private TextView online_title;
        private TextView online_introduce;
        private ImageView go_journey;
    }
}
