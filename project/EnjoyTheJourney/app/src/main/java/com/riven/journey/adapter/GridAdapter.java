package com.riven.journey.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.riven.journey.DetailActivity;
import com.riven.journey.R;
import com.riven.journey.bean.MyWorks;

import java.util.List;

/**
 * @author 田春雨
 * 我的作品布局
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {
    private Context context;
    private List<MyWorks> list;

    public GridAdapter(Context context, List<MyWorks> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GridViewHolder(LayoutInflater.from(context).inflate(R.layout.grid_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, final int position) {
        holder.text.setText(list.get(position).getTitle());
        holder.photo.setImageBitmap(list.get(position).getPhoto());
        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("noteId", list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GridViewHolder extends RecyclerView.ViewHolder {

        private TextView text;
        private ImageView photo;
        private LinearLayout main;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            text = itemView.findViewById(R.id.text);
            main = itemView.findViewById(R.id.ll_main);
        }
    }


}
