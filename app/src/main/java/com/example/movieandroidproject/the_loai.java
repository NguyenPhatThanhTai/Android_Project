package com.example.movieandroidproject;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import API.APIControllers;
import Category.Category;
import Category.CategoryAdapter;

public class the_loai extends Fragment implements IOnBackPressed{

    List<Category> categoryData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.items_category2, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rcv_category);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                categoryData = new APIControllers().getAllCate();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryData, (MainActivity) getActivity());
                        recyclerView.setAdapter(categoryAdapter);
                    }
                });
            }
        };
        thread.start();

//        System.out.println("Ở đây: " + LoadImageFromWebOperations("https://huyhoanhotel.com/wp-content/uploads/2016/05/765-default-avatar.png"));

        return view;
    }

    @Override
    public boolean onBackPressed() {
        Fragment selectedFragment = new trang_chu();
        FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
        manager.popBackStack();

        manager.beginTransaction()
                .setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,
                        R.anim.enter_right_to_left, R.anim.exit_right_to_left)
                .replace(R.id.fragment_container,
                        selectedFragment).commit();

        return true;
    }

//    public static Drawable LoadImageFromWebOperations(String url) {
//        try {
//            InputStream is = (InputStream) new URL(url).getContent();
//            Drawable d = Drawable.createFromStream(is, "res/drawable");
//            return d;
//        } catch (Exception e) {
//            return null;
//        }
//    }
}
