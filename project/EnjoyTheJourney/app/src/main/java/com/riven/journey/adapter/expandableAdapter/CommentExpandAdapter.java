package com.riven.journey.adapter.expandableAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.riven.journey.R;
import com.riven.journey.bean.Comments;
import com.riven.journey.util.ConfigUtil;

import java.util.List;

/**
 * @author 张硕
 */
public class CommentExpandAdapter extends BaseExpandableListAdapter {
    private List<Comments> comments;
    private Context mContext;

    public CommentExpandAdapter(List<Comments> comments, Context mContext) {
        this.comments = comments;
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {
        return comments.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if(comments.get(i).getChildComments() == null){
            return 0;
        }else{
            return comments.get(i).getChildComments().size()>0?comments.get(i).getChildComments().size():0;
        }
    }

    @Override
    public Object getGroup(int i) {
        return comments.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return comments.get(i).getChildComments().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return getCombinedChildId(i,i1);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    boolean isLike = false;
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupHolder groupHolder;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_comments_list,viewGroup,false);
            groupHolder = new GroupHolder(view);
            view.setTag(groupHolder);
        }else{
            groupHolder = (GroupHolder) view.getTag();
        }
        Glide.with(mContext).load(ConfigUtil.BASE_URL+comments.get(i).getComUserHeader())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))//圆形剪裁
                .into(groupHolder.ivHeader);
        groupHolder.tvName.setText(comments.get(i).getComUser());
        groupHolder.tvComments.setText(comments.get(i).getComment());
        groupHolder.tvTime.setText(comments.get(i).getComTime());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        GroupHolder groupHolder;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_comments_list,viewGroup,false);
            groupHolder = new GroupHolder(view);
            view.setTag(groupHolder);
        }else{
            groupHolder = (GroupHolder) view.getTag();
        }
        Glide.with(mContext).load(ConfigUtil.BASE_URL+comments.get(i).getComUserHeader())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))//圆形剪裁
                .into(groupHolder.ivHeader);
        groupHolder.tvName.setText(comments.get(i).getComUser());
        groupHolder.tvComments.setText(comments.get(i).getComment());
        groupHolder.tvTime.setText(comments.get(i).getComTime());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
    private class GroupHolder{
        private ImageView ivHeader;
        private TextView tvName;
        private TextView tvComments;
        private TextView tvTime;
        public GroupHolder(View view){
            ivHeader = view.findViewById(R.id.iv_popupwindow_header);
            tvName = view.findViewById(R.id.tv_popupwindow_user_name);
            tvComments = view.findViewById(R.id.tv_popupwindow_user_comment);
            tvTime = view.findViewById(R.id.tv_popupwindow_time);
        }
    }
}
