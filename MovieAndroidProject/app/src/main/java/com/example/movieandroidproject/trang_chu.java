package com.example.movieandroidproject;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import trang_chu.*;

public class trang_chu extends Fragment {
    private ViewPager viewPager;
    private Photo_Adapter photo_adapter;
    private List<Photo> mListPhoto;
    private Timer mTimer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trang_chu, container, false);
        viewPager = view.findViewById(R.id.viewpager);
        RecyclerView recyclerViewHighRate = view.findViewById(R.id.rcv_HighRate);
        RecyclerView recyclerViewRecomment = view.findViewById(R.id.rcv_Recomment);

        //Chỉnh linear cho nó cuộn được sang 2 bên
        LinearLayoutManager linearLayoutManagerHighRate = new LinearLayoutManager(this.getActivity(), RecyclerView.HORIZONTAL, false);
        recyclerViewHighRate.setLayoutManager(linearLayoutManagerHighRate);
        LinearLayoutManager linearLayoutManagerRecomment = new LinearLayoutManager(this.getActivity(), RecyclerView.HORIZONTAL, false);
        recyclerViewRecomment.setLayoutManager(linearLayoutManagerRecomment);

        //phần hình ảnh tự động chuyển
        mListPhoto = getListNewestFilm();
        photo_adapter = new Photo_Adapter(this.getActivity(), mListPhoto);
        viewPager.setAdapter(photo_adapter);

        //set dữ liệu
        HighRate_Adapter highRate_adapter = new HighRate_Adapter(getListHighRate(), (MainActivity) this.getActivity());
        recyclerViewHighRate.setAdapter(highRate_adapter);
        RecommentForYou_Adapter recommentForYou_adapter = new RecommentForYou_Adapter(getListRecommentForYou(), (MainActivity) this.getActivity());
        recyclerViewRecomment.setAdapter(recommentForYou_adapter);

        autoSlideImages();

        return view;
    }

    private List<Photo> getListNewestFilm(){
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.s5cms));
        list.add(new Photo(R.drawable.your_name));
        list.add(new Photo(R.drawable.silentvoice));
        list.add(new Photo(R.drawable.wwy));
        list.add(new Photo(R.drawable.demon_slayer));

        return list;
    }

    private List<HighRate> getListHighRate(){
        List<HighRate> list = new ArrayList<>();
        list.add(new HighRate(R.drawable.pt_your_name, "Tên cậu là gì"));
        list.add(new HighRate(R.drawable.pt_your_name, "Tên cậu là gì"));
        list.add(new HighRate(R.drawable.pt_your_name, "Tên cậu là gì"));
        list.add(new HighRate(R.drawable.pt_your_name, "Tên cậu là gì"));
        list.add(new HighRate(R.drawable.pt_your_name, "Tên cậu là gì"));

        return list;
    }

    private List<RecommentForYou> getListRecommentForYou(){
        List<RecommentForYou> list = new ArrayList<>();
        list.add(new RecommentForYou(R.drawable.pt_your_name, "Tên cậu là gì"));
        list.add(new RecommentForYou(R.drawable.pt_your_name, "Tên cậu là gì"));
        list.add(new RecommentForYou(R.drawable.pt_your_name, "Tên cậu là gì"));
        list.add(new RecommentForYou(R.drawable.pt_your_name, "Tên cậu là gì"));
        list.add(new RecommentForYou(R.drawable.pt_your_name, "Tên cậu là gì"));

        return  list;
    }

    private void autoSlideImages(){
        if(mListPhoto == null || mListPhoto.isEmpty() || viewPager == null){
            return;
        }

        //Init khởi tạo timer

        if(mTimer == null){
            mTimer = new Timer();
        }

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItems = viewPager.getCurrentItem();
                        int totalItems = mListPhoto.size() - 1;

                        if(currentItems < totalItems){
                            currentItems++;
                            viewPager.setCurrentItem(currentItems);
                        }
                        else{
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 500, 3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
    }
}
