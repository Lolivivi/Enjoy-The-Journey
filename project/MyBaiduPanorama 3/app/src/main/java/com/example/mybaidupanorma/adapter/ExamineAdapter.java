package com.example.mybaidupanorma.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.mybaidupanorma.activity.ExamineDetailActivity;
import com.example.mybaidupanorma.R;
import com.example.mybaidupanorma.bean.Examine;
import com.example.mybaidupanorma.util.ConfigUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author 田春雨
 */
public class ExamineAdapter extends BaseAdapter {
    private Context mContext;
    private List<Examine> em = new ArrayList<>();
    private int itemLayoutRes;
    private String phone;
    private TextView title;
    private Button btnDelete;
    private Button btnShow;

    public ExamineAdapter(Context mContext, List<Examine> em, int itemLayoutRes,String phone) {
        this.mContext = mContext;
        this.em = em;
        this.itemLayoutRes = itemLayoutRes;
        this.phone=phone;
    }

    @Override
    public int getCount() {
        if(null!=em){
            return em.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(null!=em){
            return em.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(itemLayoutRes, null);
        }
        title=convertView.findViewById(R.id.tv_theme);
        btnDelete=convertView.findViewById(R.id.btn_delete);
        btnShow=convertView.findViewById(R.id.btn_show);
        title.setText(em.get(position).getTitle());
        //删除按钮的点击事件
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除收藏夹
                deleteExamine(em.get(position).getTitle());
                em.remove(position);
                //更新数据
                notifyDataSetChanged();
            }
        });
        //查看
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到具体详情
                Intent intent=new Intent(mContext, ExamineDetailActivity.class);
                intent.putExtra("phone",phone);
                intent.putExtra("name",em.get(position).getTitle());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    //删除收藏夹
    private void deleteExamine(String name) {
        final String s = ConfigUtil.BASE_URL + "collection/deleteCollectionDirectory";
        String key = "?tel=" + phone+"&name="+name;
        //创建请求对象
        final Request request = new Request.Builder()
                .url(s + key)
                .build();
        //3. 创建CALL对象
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        //4. 提交请求并获取响应
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String result = "网络连接错误";
                Log.e("lww",result);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取响应的字符串信息
                String result = response.body().string();
                Log.e("result", result);
            }
        });
    }
}
