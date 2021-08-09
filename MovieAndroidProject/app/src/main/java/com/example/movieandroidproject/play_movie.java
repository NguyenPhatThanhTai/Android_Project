package com.example.movieandroidproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import API.APIControllers;
import danh_sach_tap_phim.Film_List;
import danh_sach_tap_phim.Film_list_Adapter;
import trang_chu.*;

public class play_movie extends Fragment implements IOnBackPressed {
    VideoView movie_play;
    ImageView play_btn;
    ProgressBar play_load;
    private HighRate highRate;
    private String url = "";
    private Thread thread;
    private Context context;

    private RecyclerView rcv;

    public play_movie(HighRate highRate, String url) {
        this.highRate = highRate;
        this.url = url;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Kiểm tra quay màn hình ngang
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            FrameLayout fr = (FrameLayout) this.getActivity().findViewById(R.id.Fram_movie);
            fr.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            fr.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            fr.requestLayout();//It is necesary to refresh the screen
            Toast.makeText(this.getContext(), "Đang phát ở chế độ nằm ngang", Toast.LENGTH_SHORT).show();

            BottomNavigationView bottomNAV = this.getActivity().findViewById(R.id.bottom_nav);
//            bottomNAV.getMenu().findItem(R.id.nav_home).setVisible(false);
            bottomNAV.setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            FrameLayout fr = (FrameLayout) this.getActivity().findViewById(R.id.Fram_movie);
            fr.getLayoutParams().height = 650;
            fr.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            fr.requestLayout();//It is necesary to refresh the screen
            Toast.makeText(this.getContext(), "Đang phát chế độ bình thường", Toast.LENGTH_SHORT).show();

            BottomNavigationView bottomNAV = this.getActivity().findViewById(R.id.bottom_nav);
            bottomNAV.setVisibility(View.VISIBLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_player, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        movie_play = view.findViewById(R.id.movie_play);
        thread = new Thread(this::getUrlFilm);
        thread.start();

        play_btn = view.findViewById(R.id.play_button);
        play_btn.setVisibility(View.GONE);
        play_load = view.findViewById(R.id.play_loading);

        movie_play.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                play_load.setVisibility(View.GONE);
                play_btn.setVisibility(View.VISIBLE);
            }
        });

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play_btn.setVisibility(View.GONE);
                movie_play.start();
            }
        });

        RecyclerView rcv_ep = view.findViewById(R.id.rcv_ep);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        rcv_ep.setLayoutManager(gridLayoutManager);
        rcv = rcv_ep;

        thread = new Thread(this::setListEp);
        thread.start();

        return view;
    }

    private void getUrlFilm(){
        APIControllers apiControllers = new APIControllers();
        if(url.equals("")){
            url = apiControllers.getUrlById(highRate.getMovieId());
        }
        context = this.getContext();


        this.getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Uri uri = Uri.parse(url);
                movie_play.setVideoURI(uri);

                MediaController mediaController = new MediaController(context);
                movie_play.setMediaController(mediaController);
                mediaController.setAnchorView(movie_play);

//                ProgressDialog progDailog = ProgressDialog.show(context, "Xin đợi trong giây lát ...", "Chúng tôi đang chuẩn bị video cho bạn ...", true);
//
//                movie_play.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//
//                    public void onPrepared(MediaPlayer mp) {
//                        // TODO Auto-generated method stub
//                        progDailog.dismiss();
//                    }
//                });
            }
        });
    }

    private void setListEp(){
        APIControllers apiControllers = new APIControllers();
        Film_list_Adapter film_list_adapter = new Film_list_Adapter(apiControllers.getListEp(highRate.getName(), highRate.getMovieId()), this.getActivity(), highRate);

        this.getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                rcv.setAdapter(film_list_adapter);
            }
        });
    }

    public void setVideoUrl(String url){
        Uri uri = Uri.parse(url);
        movie_play.setVideoURI(uri);

        MediaController mediaController = new MediaController(context);
        movie_play.setMediaController(mediaController);
        mediaController.setAnchorView(movie_play);

        play_btn.setVisibility(View.GONE);
        play_load.setVisibility(View.VISIBLE);

        movie_play.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                play_load.setVisibility(View.GONE);
                play_btn.setVisibility(View.VISIBLE);
            }
        });

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play_btn.setVisibility(View.GONE);
                movie_play.start();
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        Fragment selectedFragment = new detail_movie(highRate, context);
        System.out.println("======== " + highRate);
        FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();

        manager.beginTransaction()
                .setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,
                        R.anim.enter_right_to_left, R.anim.exit_right_to_left)
                .replace(R.id.fragment_container,
                selectedFragment).commit();

        return true;
    }
}
