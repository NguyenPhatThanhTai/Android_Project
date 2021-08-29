package com.example.movieandroidproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import API.APIControllers;
import tim_kiem.*;

public class tim_kiem extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tim_kiem, container, false);
        ((MainActivity)getActivity()).setBottomNav(2);

        LinearLayout search_backgroud = view.findViewById(R.id.search_backgroud);
        ImageButton timkiem = view.findViewById(R.id.btn_timkiem);
        TextView tim = view.findViewById(R.id.txt_timkiem);

        SharedPreferences sp1 = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
        String theme = sp1.getString("Theme", null);
        if(theme != null && theme.equals("Light")){
            search_backgroud.setBackgroundColor(Color.WHITE);
            tim.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
            tim.setHintTextColor(Color.BLACK);
            tim.setTextColor(Color.BLACK);
        }else if (theme != null && theme.equals("Dark")){
            search_backgroud.setBackgroundColor(Color.parseColor("#1C1C27"));
            tim.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            tim.setHintTextColor(Color.WHITE);
            tim.setTextColor(Color.WHITE);
        }

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rcv_timkiem);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        ProgressBar pg_load_search = view.findViewById(R.id.pg_search);
        timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pg_load_search.setVisibility(View.VISIBLE);
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        APIControllers Api = new APIControllers();
                        List<TimKiem> list = Api.getTimKiem(tim.getText().toString());
                        if(list != null)
                        {
                            TimKiem_Adapter adapter = new TimKiem_Adapter(list, getContext());
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pg_load_search.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    recyclerView.setAdapter(adapter);
                                }
                            });
                        }
                        else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(),"Không tìm thấy phim !",Toast.LENGTH_SHORT ).show();
                                }
                            });
                        }
                    }
                };
                thread.start();
            }
        });
        return view;
    }
}
