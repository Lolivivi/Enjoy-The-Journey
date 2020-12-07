package com.example.mybaidupanorma.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mybaidupanorma.R;

/**
 * 我喜欢的
 * @author 田春雨
 */

public class SecondPageFragment extends Fragment {

    public static SecondPageFragment newInstance(String param1){
        SecondPageFragment fragment=new SecondPageFragment();
        Bundle args=new Bundle();
        args.putString("phone",param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_notes_layout, container, false);
        String phone=getArguments().getString("phone");
//        TextView text=view.findViewById(R.id.tv_text);
//        text.setText("这是第二个页面");
        return view;
    }
}
