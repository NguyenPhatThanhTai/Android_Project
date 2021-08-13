package com.example.movieandroidproject;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.URL;

import Category.Category;
import Category.CategoryAdapter;

public class the_loai extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.items_category2, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rcv_category);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        Category[] categoryData = new Category[]{
                new Category("Thể loại 1", R.drawable.hat),
                new Category("Thể loại 2", R.drawable.sword),
                new Category("Thể loại 3", R.drawable.hat),
                new Category("Thể loại 4", R.drawable.sword),
                new Category("Thể loại 5", R.drawable.hat),
                new Category("Thể loại 6", R.drawable.sword),
                new Category("Thể loại 7", R.drawable.hat),
                new Category("Thể loại 8", R.drawable.sword),
        };

//        System.out.println("Ở đây: " + LoadImageFromWebOperations("https://huyhoanhotel.com/wp-content/uploads/2016/05/765-default-avatar.png"));

        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryData, (MainActivity) this.getActivity());
        recyclerView.setAdapter(categoryAdapter);

        return view;
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
