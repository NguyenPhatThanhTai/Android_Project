package com.example.movieandroidproject;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class test extends AppCompatActivity {
    VideoView v1;

    @Override
    protected void onCreate(Bundle savedInstancesState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstancesState);
        setContentView(R.layout.test);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        v1 = findViewById(R.id.v1);
        Uri uri = Uri.parse("https://gg.skid.cyou/Yoake/BokutachinoRemake/Bokutachi%20No%20Remake%201.mp4");
        v1.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        v1.setMediaController(mediaController);
        mediaController.setAnchorView(v1);

        v1.start();
    }

    public void getPosition(View v){
        int lastPosition = v1.getCurrentPosition();
        System.out.println("O day " + lastPosition);
    }

    public void setPostition(View v){
        v1.seekTo(0);
    }

    public void setFullScreen(View v){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        FrameLayout FL = findViewById(R.id.Fram);
        ViewGroup.LayoutParams params = FL.getLayoutParams();
        params.height = FrameLayout.LayoutParams.MATCH_PARENT;
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        FL.setLayoutParams(params);
    }
}
