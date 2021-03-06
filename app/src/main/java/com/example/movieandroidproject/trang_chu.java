package com.example.movieandroidproject;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
    private Activity activity;
    private RecyclerView RCHR, RCRC;
    private Spinner TheLoai, Studio;
    private int position;
    Timer timer;
    TimerTask timerTask;

    SnapHelper snapHelper;

    LinearLayoutManager linearLayoutManagerHighRate;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    public trang_chu(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Ki???m tra quay m??n h??nh ngang
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 3);
            RCRC.setLayoutManager(gridLayoutManager);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 2);
            RCRC.setLayoutManager(gridLayoutManager);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trang_chu, container, false);
        ((MainActivity)getActivity()).setBottomNav(3);

        SharedPreferences sp1 = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
        String theme = sp1.getString("Theme", null);
        CardView theme_cardview_main = view.findViewById(R.id.theme_cardview_main);
        TextView txt_top_week, txt_watch_together, txt_top_month, txt_studio;
        txt_top_week = view.findViewById(R.id.txt_top_week);
        txt_watch_together = view.findViewById(R.id.txt_watch_together);
        txt_top_month = view.findViewById(R.id.txt_top_month);
        txt_studio = view.findViewById(R.id.txt_studio);
        TheLoai = view.findViewById(R.id.Theloai);
        Studio = view.findViewById(R.id.Studio);
        if(theme != null && theme.equals("Light")){
            theme_cardview_main.setCardBackgroundColor(Color.WHITE);
            txt_top_week.setTextColor(Color.BLACK);
            txt_watch_together.setTextColor(Color.BLACK);
            txt_top_month.setTextColor(Color.BLACK);
            txt_studio.setTextColor(Color.BLACK);
        }else if (theme != null && theme.equals("Dark")){
            theme_cardview_main.setCardBackgroundColor(Color.parseColor("#1C1C27"));
            txt_top_week.setTextColor(Color.WHITE);
            txt_watch_together.setTextColor(Color.WHITE);
            txt_top_month.setTextColor(Color.WHITE);
            txt_studio.setTextColor(Color.WHITE);
        }

        ProgressBar pg_loadHR = view.findViewById(R.id.pg_loadHR);
        pg_loadHR.setVisibility(View.VISIBLE);

        ProgressBar pg_loadRC = view.findViewById(R.id.pg_loadRC);
        pg_loadRC.setVisibility(View.VISIBLE);

        viewPager = view.findViewById(R.id.viewpager);
        RecyclerView recyclerViewHighRate = view.findViewById(R.id.rcv_HighRate);
        RCHR = recyclerViewHighRate;
        RecyclerView recyclerViewRecomment = view.findViewById(R.id.rcv_Recomment);
        RCRC = recyclerViewRecomment;

        //Add dropdown list
        thread = new Thread(this::setDanhSachCombobox);
        thread.start();

        //g???i api d??? li???u recomment film
        thread = new Thread(this::threadAllMovie);
        thread.start();

        //goi api d??? li???u highrate phim
        thread = new Thread(this::threadGetMovie);
        thread.start();

        //Ch???nh linear cho n?? cu???n ???????c sang 2 b??n
        linearLayoutManagerHighRate = new LinearLayoutManager(this.getActivity(), RecyclerView.HORIZONTAL, false);
        RCHR.setLayoutManager(linearLayoutManagerHighRate);

        if (mListHighRate != null) {
            position = Integer.MAX_VALUE / 2;
            RCHR.scrollToPosition(position);
        }

        snapHelper =  new LinearSnapHelper();
        snapHelper.attachToRecyclerView(RCHR);
        recyclerViewHighRate.smoothScrollBy(5, 0);

        RCHR.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                pg_loadHR.setVisibility(View.GONE);
                RCHR.setVisibility(View.VISIBLE);
            }
        });

        RCRC.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                pg_loadRC.setVisibility(View.GONE);
                RCRC.setVisibility(View.VISIBLE);
            }
        });

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

        //Ch???nh 1 h??ng 2 phim, cu???n xu???ng
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        RCRC.setLayoutManager(gridLayoutManager);

        //ph???n banner h??nh ???nh t??? ?????ng chuy???n
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
        list.add(new Photo(R.drawable.fate));
        list.add(new Photo(R.drawable.overlord));
        list.add(new Photo(R.drawable.silentvoice));
        list.add(new Photo(R.drawable.wwy));
        list.add(new Photo(R.drawable.haikyu));
        list.add(new Photo(R.drawable.poster1));

        return list;
    }

    private void autoSlideImages(){
        if(mListPhoto == null || mListPhoto.isEmpty() || viewPager == null){
            return;
        }

        //Init kh???i t???o timer

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

        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                RCHR.setAdapter(highRate_adapter);
            }
        });
    }

    private void threadAllMovie(){
        APIControllers apiControllers = new APIControllers();
        RecommentForYou_Adapter recommentForYou_adapter = new RecommentForYou_Adapter(apiControllers.getAllMovie(), (MainActivity) this.getActivity());

        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                RCRC.setAdapter(recommentForYou_adapter);
            }
        });
    }

    private void setDanhSachCombobox(){
        //th??? lo???i
        ArrayList<String> theloai = new ArrayList<String>();
        theloai.add("Th??? lo???i phim");
        theloai.add("Th??? lo???i 1");
        theloai.add("Th??? lo???i 2");
        theloai.add("Th??? lo???i 3");
        theloai.add("Th??? lo???i 4");
        theloai.add("Th??? lo???i 5");

        ArrayAdapter<String> adapter_theloai = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,theloai);

        //Studio
        ArrayList<String> studio = new ArrayList<String>();
        studio.add("Studio s???n xu???t");
        studio.add("Studio 1");
        studio.add("Studio 2");
        studio.add("Studio 3");

        ArrayAdapter<String> adapter_studio = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,studio);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TheLoai.setAdapter(adapter_theloai); // this will set list of values to spinner
                TheLoai.setSelection(theloai.indexOf(1));//set selected value in spinner

                Studio.setAdapter(adapter_studio); // this will set list of values to spinner
                Studio.setSelection(studio.indexOf(1));//set selected value in spinner
            }
        });
    }
}
