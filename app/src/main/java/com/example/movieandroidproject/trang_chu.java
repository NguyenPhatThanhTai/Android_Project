package com.example.movieandroidproject;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import API.APIControllers;
import me.relex.circleindicator.CircleIndicator;
import trang_chu.*;

public class trang_chu extends Fragment {
    private ViewPager viewPager;
    private Photo_Adapter photo_adapter;
    private List<Photo> mListPhoto;
    private List<HighRate> mListHighRate;
    private Timer mTimer;

    private Thread thread;
    private RecyclerView RCHR, RCRC;
    private Spinner TheLoai, Studio;
    private int position;
    Timer timer;
    TimerTask timerTask;

    SnapHelper snapHelper;

    LinearLayoutManager linearLayoutManagerHighRate;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trang_chu, container, false);
        viewPager = view.findViewById(R.id.viewpager);
        RecyclerView recyclerViewHighRate = view.findViewById(R.id.rcv_HighRate);
        RCHR = recyclerViewHighRate;
        RecyclerView recyclerViewRecomment = view.findViewById(R.id.rcv_Recomment);
        RCRC = recyclerViewRecomment;

        TheLoai = view.findViewById(R.id.Theloai);
        Studio = view.findViewById(R.id.Studio);

        //Add dropdown list
        thread = new Thread(this::setDanhSachCombobox);
        thread.start();

        //gọi api dữ liệu recomment film
        thread = new Thread(this::threadAllMovie);
        thread.start();

        //goi api dữ liệu highrate phim
        thread = new Thread(this::threadGetMovie);
        thread.start();

        //Chỉnh linear cho nó cuộn được sang 2 bên
        linearLayoutManagerHighRate = new LinearLayoutManager(this.getActivity(), RecyclerView.HORIZONTAL, false);
        RCHR.setLayoutManager(linearLayoutManagerHighRate);

        if (mListHighRate != null) {
            position = Integer.MAX_VALUE / 2;
            RCHR.scrollToPosition(position);
        }

        snapHelper =  new LinearSnapHelper();
        snapHelper.attachToRecyclerView(RCHR);
        recyclerViewHighRate.smoothScrollBy(5, 0);

        RCHR.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == 1) {
                    stopAutoScrollBanner();
                } else if (newState == 0) {
                    position = linearLayoutManagerHighRate.findFirstCompletelyVisibleItemPosition();
                    runAutoScrollBanner();
                }
            }
        });

        //Chỉnh 1 hàng 2 phim, cuộn xuống
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        recyclerViewRecomment.setLayoutManager(gridLayoutManager);

        //phần banner hình ảnh tự động chuyển
        mListPhoto = getListNewestFilm();
        photo_adapter = new Photo_Adapter(this.getActivity(), mListPhoto);
        viewPager.setAdapter(photo_adapter);

        CircleIndicator circleIndicator = view.findViewById(R.id.circle_indicator);
        circleIndicator.setViewPager(viewPager);
        photo_adapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        autoSlideImages();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        runAutoScrollBanner();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAutoScrollBanner();
    }

    private void stopAutoScrollBanner() {
        if (timer != null && timerTask != null) {
            timerTask.cancel();
            timer.cancel();
            timer = null;
            timerTask = null;
            position = linearLayoutManagerHighRate.findFirstCompletelyVisibleItemPosition();
        }
    }

    private void runAutoScrollBanner() {
        if (timer == null && timerTask == null) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (position == Integer.MAX_VALUE) {
                        position = Integer.MAX_VALUE / 2;
                        RCHR.scrollToPosition(position);
                        RCHR.smoothScrollBy(5, 0);
                    } else {
                        position++;
                        RCHR.smoothScrollToPosition(position);
                    }
                }
            };
            timer.schedule(timerTask, 2000, 2000);
        }
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

    private void threadGetMovie(){
        APIControllers apiControllers = new APIControllers();
        mListHighRate = apiControllers.getApiMovie();
        HighRate_Adapter highRate_adapter = new HighRate_Adapter(apiControllers.getApiMovie(), (MainActivity) this.getActivity());

        this.getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                RCHR.setAdapter(highRate_adapter);
            }
        });
    }

    private void threadAllMovie(){
        APIControllers apiControllers = new APIControllers();
        RecommentForYou_Adapter recommentForYou_adapter = new RecommentForYou_Adapter(apiControllers.getAllMovie(), (MainActivity) this.getActivity());

        this.getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                RCRC.setAdapter(recommentForYou_adapter);
            }
        });
    }

    private void setDanhSachCombobox(){
        //thể loại
        ArrayList<String> theloai = new ArrayList<String>();
        theloai.add("Thể loại phim");
        theloai.add("Thể loại 1");
        theloai.add("Thể loại 2");
        theloai.add("Thể loại 3");
        theloai.add("Thể loại 4");
        theloai.add("Thể loại 5");

        ArrayAdapter<String> adapter_theloai = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,theloai);
        TheLoai.setAdapter(adapter_theloai); // this will set list of values to spinner
        TheLoai.setSelection(theloai.indexOf(1));//set selected value in spinner

        //Studio
        ArrayList<String> studio = new ArrayList<String>();
        studio.add("Studio sản xuất");
        studio.add("Studio 1");
        studio.add("Studio 2");
        studio.add("Studio 3");

        ArrayAdapter<String> adapter_studio = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,studio);
        Studio.setAdapter(adapter_studio); // this will set list of values to spinner
        Studio.setSelection(studio.indexOf(1));//set selected value in spinner
    }
}
