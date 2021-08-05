package com.example.movieandroidproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class detail_movie extends Fragment {

    private int img_detail;
    private String name, category, time, descrip;

    public detail_movie(int img_detail, String name_detail, String category_detail, String time_detail, String descrip_detail){
        this.img_detail = img_detail;
        this.name = name_detail;
        this.category = category_detail;
        this.time = time_detail;
        this.descrip = descrip_detail;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_movie, container, false);

        ImageView img_detail = view.findViewById(R.id.img_detail);
        img_detail.setImageResource(this.img_detail);
        TextView name_detail = view.findViewById(R.id.name_detail);
        name_detail.setText(name);
        TextView category_detail = view.findViewById(R.id.category_detail);
        category_detail.setText(category);
        TextView time_detail = view.findViewById(R.id.time_detail);
        time_detail.setText(time);
        TextView descrip_detail = view.findViewById(R.id.descrip_detail);
        descrip_detail.setText(descrip);
        Button back_detail = view.findViewById(R.id.back_detail);
        Button playnow_detail = view.findViewById(R.id.playnow_detail);

        return view;
    }
}
