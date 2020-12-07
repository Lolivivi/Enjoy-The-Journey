package com.example.mybaidupanorma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mybaidupanorma.R;
import com.example.mybaidupanorma.activity.DetailActivity;
import com.example.mybaidupanorma.util.CommentDetailBean;
import com.example.mybaidupanorma.util.ReplyDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 于喜梅
 */
public class CommentExpandAdapter extends BaseExpandableListAdapter {
    private List<CommentDetailBean> commentBeanList;
    private Context context;

    public CommentExpandAdapter(Context context, List<CommentDetailBean> commentBeanList){
        this.context = context;
        this.commentBeanList = commentBeanList;
    }

    @Override
    public int getGroupCount() {
        return commentBeanList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if(commentBeanList.get(i).getReplyList() == null){
            return 0;
        }else {
            return commentBeanList.get(i).getReplyList().size()>0 ? commentBeanList.get(i).getReplyList().size():0;
        }

    }

    @Override
    public Object getGroup(int i) {
        return commentBeanList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return commentBeanList.get(i).getReplyList().get(i1);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpand, View convertView, ViewGroup viewGroup) {
        final GroupHolder groupHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_item_layout, viewGroup, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        }else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        String url=commentBeanList.get(groupPosition).getUserLogo();

        Glide.with(context)
                .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)//磁盘不缓存
                .placeholder(R.drawable.loading)//正在加载时显示的图片
                .error(R.drawable.failure)//永久加载失败时显示的图片
                .fallback(R.drawable.notexist)//表示图片地址为null时显示的图片
                .circleCrop()
                .into(groupHolder.logo);
        groupHolder.tv_name.setText(commentBeanList.get(groupPosition).getNickName());
        groupHolder.tv_time.setText(commentBeanList.get(groupPosition).getCreateDate());
        groupHolder.tv_content.setText(commentBeanList.get(groupPosition).getContent());

        if(commentBeanList.get(groupPosition).getReplyTotal()==0){
            groupHolder.tv_more.setText("");
        }else{
            groupHolder.tv_more.setText("共"+commentBeanList.get(groupPosition).getReplyTotal()+"条回复");
        }
        groupHolder.tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DetailActivity mcontext =(DetailActivity)context;
                ((DetailActivity) context).OnFlagClickListener(groupPosition);
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        final ChildHolder childHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_reply_item_layout,viewGroup, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        }
        else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        String url=commentBeanList.get(groupPosition).getReplyList().get(childPosition).getUserLogo();
        Glide.with(context)
                .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)//磁盘不缓存
                .placeholder(R.drawable.loading)//正在加载时显示的图片
                .error(R.drawable.failure)//永久加载失败时显示的图片
                .fallback(R.drawable.notexist)//表示图片地址为null时显示的图片
                .circleCrop()
                .into(childHolder.logo);
        childHolder.tv_name.setText(commentBeanList.get(groupPosition).getReplyList().get(childPosition).getNickName());
        childHolder.tv_time.setText(commentBeanList.get(groupPosition).getReplyList().get(childPosition).getCreateDate());
        childHolder.tv_content.setText(commentBeanList.get(groupPosition).getReplyList().get(childPosition).getContent());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private class GroupHolder{
        private ImageView logo;
        private TextView tv_name, tv_content, tv_time,tv_more;
        private Button btn_like;
        boolean isLike;
        public GroupHolder(View view) {
            logo =  view.findViewById(R.id.comment_item_logo);
            tv_content = view.findViewById(R.id.comment_item_content);
            tv_name = view.findViewById(R.id.comment_item_userName);
            tv_time = view.findViewById(R.id.comment_item_time);
//            btn_like = view.findViewById(R.id.comment_item_like);
//            isLike=false;
            tv_more=view.findViewById(R.id.tv_more);
        }
    }

    private class ChildHolder{
        private ImageView logo;
        private TextView tv_name, tv_content, tv_time,tv_more;
        private Button btn_like;
        boolean isLike;
        public ChildHolder(View view) {
            logo =  view.findViewById(R.id.reply_item_logo);
            tv_content = view.findViewById(R.id.reply_item_content);
            tv_name = view.findViewById(R.id.reply_item_userName);
            tv_time = view.findViewById(R.id.reply_item_time);

        }
    }

    public void addTheCommentData(CommentDetailBean commentDetailBean){
        if(commentDetailBean!=null){
            commentBeanList.add(commentDetailBean);
            notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("评论数据为空!");
        }
    }

    /**
     * by moos on 2018/04/20
     * func:回复成功后插入一条数据
     * @param replyDetailBean 新的回复数据
     */
    public void addTheReplyData(ReplyDetailBean replyDetailBean, int groupPosition){
        if(replyDetailBean!=null){
            if(commentBeanList.get(groupPosition).getReplyList() != null ){
                commentBeanList.get(groupPosition).getReplyList().add(replyDetailBean);
            }else {
                List<ReplyDetailBean> replyList = new ArrayList<>();
                replyList.add(replyDetailBean);
                commentBeanList.get(groupPosition).setReplyList(replyList);
            }
            notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("回复数据为空!");
        }

    }

    /**
     * by moos on 2018/04/20
     * func:添加和展示所有回复
     * @param replyBeanList 所有回复数据
     * @param groupPosition 当前的评论
     */
    private void addReplyList(List<ReplyDetailBean> replyBeanList, int groupPosition){
        if(commentBeanList.get(groupPosition).getReplyList() != null ){
            commentBeanList.get(groupPosition).getReplyList().clear();
            commentBeanList.get(groupPosition).getReplyList().addAll(replyBeanList);
        }else {

            commentBeanList.get(groupPosition).setReplyList(replyBeanList);
        }

        notifyDataSetChanged();
    }
}
