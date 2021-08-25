package com.example.movieandroidproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import API.APIControllers;
import Category.Category;
import Category.CategoryAdapter;
import the_loai.*;

public class phimtheo_theloai extends Fragment {

    List<TheLoai> categoryList = new ArrayList<>();
    String id, tentheloai;
    public phimtheo_theloai(String id, String tentheloai){
        this.id = id;
        this.tentheloai = tentheloai;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cate_film, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rcv_cateFilm);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        TextView textView = view.findViewById(R.id.txt_tenCate);
        textView.setText(tentheloai);
        recyclerView.setLayoutManager(gridLayoutManager);
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                categoryList = new APIControllers().getTheLoai(id);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TheloaiAdapter theloaiAdapter = new TheloaiAdapter(categoryList, (MainActivity) getActivity());
                        recyclerView.setAdapter(theloaiAdapter);
                    }
                });
            }
        };
        thread.start();

//        System.out.println("Ở đây: " + LoadImageFromWebOperations("https://huyhoanhotel.com/wp-content/uploads/2016/05/765-default-avatar.png"));

        return view;
    }
}
