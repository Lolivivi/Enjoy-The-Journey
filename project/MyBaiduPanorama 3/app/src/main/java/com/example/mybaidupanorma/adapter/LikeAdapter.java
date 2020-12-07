package com.example.mybaidupanorma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mybaidupanorma.R;
import com.example.mybaidupanorma.util.ConfigUtil;
import com.example.mybaidupanorma.util.LikeData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 梁钰钊
 * 喜欢我的列表的adapter
 */
public class LikeAdapter extends BaseAdapter {

    private Context context;
    private List<LikeData> likeData = new ArrayList<>();
    private int itemLayoutRes;

    private TextView username;
    private TextView coment;
    private ImageView userimg;
    private TextView describe;
    private ImageView coverimg;

    public LikeAdapter(Context context, List<LikeData> likeData, int itemLayoutRes) {
        this.context = context;
        this.likeData = likeData;
        this.itemLayoutRes = itemLayoutRes;
    }

    @Override
    public int getCount() {
        if(likeData!=null){
            return likeData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (likeData!=null){
            return likeData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RequestOptions mrequestoptions = RequestOptions.circleCropTransform();
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(itemLayoutRes,null);
        username = convertView.findViewById(R.id.username);
        userimg = convertView.findViewById(R.id.user_img);
        coment = convertView.findViewById(R.id.coment);
        describe = convertView.findViewById(R.id.food);
        coverimg = convertView.findViewById(R.id.coverimg);



        username.setText(likeData.get(position).getUsername());;
        coment.setText("点赞了我的作品");
        describe.setText(likeData.get(position).getName());
        //用户头像
        Glide.with(context)
                .load(ConfigUtil.BASE_URL +likeData.get(position).getUserHeadsrc())
                .apply(mrequestoptions)
                .into(userimg);
        Glide.with(context)
                .load(ConfigUtil.BASE_URL + likeData.get(position).getCoverimg())
                .into(coverimg);
        return convertView;
    }
}
