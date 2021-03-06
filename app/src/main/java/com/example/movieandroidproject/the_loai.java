package com.example.movieandroidproject;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import API.APIControllers;
import Category.Category;
import Category.CategoryAdapter;
import Dangnhap_Dangki.NguoiDung;

public class the_loai extends Fragment{

    TextView txtUserName, txtUserId;
    String id;
    NguoiDung nd;
    List<Category> categoryData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.items_category2, container, false);
        ((MainActivity)getActivity()).setBottomNav(1);

        LinearLayout category_background = view.findViewById(R.id.category_background);

        SharedPreferences sp1 = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
        String theme = sp1.getString("Theme", null);

        if(theme != null && theme.equals("Light")){
            category_background.setBackgroundColor(Color.WHITE);
        }else if (theme != null && theme.equals("Dark")){
            category_background.setBackgroundColor(Color.parseColor("#1C1C27"));
        }

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rcv_category);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                APIControllers api = new APIControllers();

                txtUserName = view.findViewById(R.id.txtUserName);
                txtUserId = view.findViewById(R.id.txtUserId);
                SharedPreferences sp1 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                id = sp1.getString("Unm", null);
                if(id != null){
                    nd = api.ThongTinCaNhan(id);
                }
                else {
                    nd = null;
                }

                categoryData = new APIControllers().getAllCate();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PackageInfo pInfo = null;
                        try {
                            pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        String version = pInfo.versionName;
                        if (nd != null)
                        {

                            txtUserName.setText(nd.getFullName());
                            txtUserId.setText(nd.getUserId() + " - Phi??n b???n: " + version);
                        }
                        else {
                            txtUserName.setText("Xin ch??o!");
                            txtUserId.setText("Ch??c m???t ng??y t???t l??nh!" + " - Phi??n b???n: " + version);
                        }
                        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryData, (MainActivity) getActivity());
                        recyclerView.setAdapter(categoryAdapter);
                    }
                });
            }
        };
        thread.start();

//        System.out.println("??? ????y: " + LoadImageFromWebOperations("https://huyhoanhotel.com/wp-content/uploads/2016/05/765-default-avatar.png"));

        return view;
    }
}
