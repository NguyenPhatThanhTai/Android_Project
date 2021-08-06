package com.example.movieandroidproject;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class play_movie extends Fragment {
    VideoView movie_play;

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
        Uri uri = Uri.parse("https://gg.skid.cyou/Yoake/BokutachinoRemake/Bokutachi%20No%20Remake%201.mp4");
        movie_play.setVideoURI(uri);

        MediaController mediaController = new MediaController(this.getContext());
        movie_play.setMediaController(mediaController);
        mediaController.setAnchorView(movie_play);

        movie_play.start();

        return view;
    }
}
