package com.example.movieandroidproject;

import static com.example.movieandroidproject.notification_media.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import android.util.Rational;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import API.APIControllers;
import Dangnhap_Dangki.Dangnhap_Dangki;
import comment.comment;
import comment.comment_adapter;
import danh_sach_tap_phim.Film_list_Adapter;
import trang_chu.*;

import Dangnhap_Dangki.*;

public class play_movie extends Fragment {
    PlayerView movie_play;
    ProgressBar play_load;
    ImageView btFullScreen, btnPause, btnPlay;
    SimpleExoPlayer simpleExoPlayer;
    boolean flag = false;
    private HighRate highRate;
    private String url = "";
    private Thread thread;
    private Context context;
    private TextView on_playing, txt_comment_count;
    private boolean outPIP = false;
    private boolean outMoviePlay = false;
    private boolean stopView;
    private PictureInPictureParams.Builder pictureInPictureParams;
    private String Ep = "1";
    private RecyclerView rcv, rcv_comment;
    private String userId;
    private EditText txt_put_feed_back;

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

            MeowBottomNavigation bottomNAV = this.getActivity().findViewById(R.id.meow_bottom);
//            bottomNAV.getMenu().findItem(R.id.nav_home).setVisible(false);
            bottomNAV.setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            FrameLayout fr = (FrameLayout) this.getActivity().findViewById(R.id.Fram_movie);
            fr.getLayoutParams().height = 650;
            fr.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            fr.requestLayout();//It is necesary to refresh the screen

            MeowBottomNavigation bottomNAV = this.getActivity().findViewById(R.id.meow_bottom);
            bottomNAV.setVisibility(View.VISIBLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_player, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SharedPreferences sp1 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        userId = sp1.getString("Unm", null);

        LinearLayout ln_notLogin = view.findViewById(R.id.ln_notLogin);
        LinearLayout ln_loginEd = view.findViewById(R.id.ln_logined);
        Button btn_goLogin = view.findViewById(R.id.btn_goLogin);
        ImageButton btn_gui = view.findViewById(R.id.btn_gui);
        txt_comment_count = view.findViewById(R.id.txt_comment_count);

        if(userId != null){
            ln_loginEd.setVisibility(View.VISIBLE);
            thread = new Thread(this::setComment);
            thread.start();
            txt_put_feed_back = view.findViewById(R.id.txt_put_feed_back);
            btn_gui.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!txt_put_feed_back.getText().toString().equals("")){
                        sendComment();
                    }
                    else {
                        return;
                    }
                }
            });
        }
        else {
            ln_notLogin.setVisibility(View.VISIBLE);
            btn_goLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment selectedFragment = new Dangnhap_Dangki();
                    FragmentManager manager = getActivity().getSupportFragmentManager();

                    manager.beginTransaction()
                            .add(selectedFragment, "back_stack") // Add this transaction to the back stack (name is an optional name for this back stack state, or null).
                            .addToBackStack(null)
                            .replace(R.id.fragment_container,
                                    selectedFragment).commit();
                }
            });
        }

        this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        movie_play = view.findViewById(R.id.movie_play);
        on_playing = view.findViewById(R.id.txt_playing);

        thread = new Thread(this::getUrlFilm);
        thread.start();

        on_playing.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        on_playing.setText(highRate.getName() + " tập 1");

        play_load = view.findViewById(R.id.play_loading);
        btFullScreen = movie_play.findViewById(R.id.bt_fullscreen);
        btnPause = movie_play.findViewById(R.id.exo_pause);
        btnPlay = movie_play.findViewById(R.id.exo_play);

        pictureInPictureParams = new PictureInPictureParams.Builder();

        LoadControl loadControl = new DefaultLoadControl();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);

        rcv = view.findViewById(R.id.rcv_ep);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 4);
        rcv.setLayoutManager(gridLayoutManager);

        rcv_comment = view.findViewById(R.id.rcv_comment);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this.getActivity(), 1);
        rcv_comment.setLayoutManager(gridLayoutManager2);

        thread = new Thread(this::setListEp);
        thread.start();

        return view;
    }

    private void sendNotificationMedia(String Ep) {

        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(getContext(), "tag");

        Notification notification = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_small_movie)
                .setSubText("Đang phát")
                .setContentTitle(highRate.getName())
                .setContentText("Tập " + Ep)
                .setLargeIcon(getBitmapFromURL(highRate.getThumbnails()))
                .addAction(R.drawable.ic_small_replay, "Giảm 10 giây", null)
                .addAction(R.drawable.ic_small_pause, "Tạm dừng", null)
                .addAction(R.drawable.ic_small_forward, "Tua 10 giây", null)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1 /* #1: pause button */))
                .build();

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
        managerCompat.notify(1, notification);
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
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
                DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory("exoplayer_video");
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                MediaSource mediaSource = new ExtractorMediaSource(uri, factory, extractorsFactory, null, null);
                movie_play.setPlayer(simpleExoPlayer);
                movie_play.setKeepScreenOn(true);
                simpleExoPlayer.prepare(mediaSource);
                simpleExoPlayer.setPlayWhenReady(true);
                simpleExoPlayer.addListener(new Player.EventListener() {
                    @Override
                    public void onTimelineChanged(Timeline timeline, Object o, int i) {

                    }

                    @Override
                    public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {

                    }

                    @Override
                    public void onLoadingChanged(boolean b) {

                    }

                    @Override
                    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                        if(playbackState == Player.STATE_BUFFERING){
                            play_load.setVisibility(View.VISIBLE);
                        }else if(playbackState == Player.STATE_READY){
                            play_load.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onRepeatModeChanged(int i) {

                    }

                    @Override
                    public void onShuffleModeEnabledChanged(boolean b) {

                    }

                    @Override
                    public void onPlayerError(ExoPlaybackException e) {

                    }

                    @Override
                    public void onPositionDiscontinuity(int i) {

                    }

                    @Override
                    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                    }

                    @Override
                    public void onSeekProcessed() {

                    }
                });
                btFullScreen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(flag){
                            btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));
                            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            flag = false;
                        }else {
                            btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
                            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            flag = true;
                        }
                    }
                });
            }
        });
    }

    public void setVideoUrl(String url, String Ep, String Name){
        on_playing.setText(Name + " tập " + Ep);
        this.Ep = Ep;
        Uri uri = Uri.parse(url);
        DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory("exoplayer_video");
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(uri, factory, extractorsFactory, null, null);
        movie_play.setPlayer(simpleExoPlayer);
        movie_play.setKeepScreenOn(true);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object o, int i) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {

            }

            @Override
            public void onLoadingChanged(boolean b) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if(playbackState == Player.STATE_BUFFERING){
                    play_load.setVisibility(View.VISIBLE);
                }else if(playbackState == Player.STATE_READY){
                    play_load.setVisibility(View.GONE);
                }
            }

            @Override
            public void onRepeatModeChanged(int i) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean b) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException e) {

            }

            @Override
            public void onPositionDiscontinuity(int i) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
        btFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag){
                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    flag = false;
                }else {
                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    flag = true;
                }
            }
        });
    }

    private void setListEp(){
        APIControllers apiControllers = new APIControllers();
        Film_list_Adapter film_list_adapter = new Film_list_Adapter(apiControllers.getListEp(highRate.getName(), highRate.getMovieId()), this.getActivity());

        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rcv.setAdapter(film_list_adapter);
            }
        });
    }

    private void setComment(){
        List<comment> list = new APIControllers().getListComment(highRate.getMovieId());
        comment_adapter comment_adapter = new comment_adapter(list, this.getActivity());

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_comment_count.setText("Bình luận(" + list.size()+")");
            }
        });

        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rcv_comment.setAdapter(comment_adapter);
            }
        });
    }

    private void sendComment(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                NguoiDung nguoiDung = new APIControllers().getNguoidung(userId);
                if(new APIControllers().addComment(highRate.getMovieId(), nguoiDung.getFullName(), txt_put_feed_back.getText().toString())){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setComment();
                            txt_put_feed_back.setText("");
                        }
                    });
                }
                else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Gửi bình luận thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        };
        thread.start();
    }

    public void test(String data){
        Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        simpleExoPlayer.setPlayWhenReady(false);
        simpleExoPlayer.getPlaybackState();

        Fragment f = getFragmentManager().findFragmentById(R.id.fragment_container);

        if(outMoviePlay == false && f instanceof play_movie){
            simpleExoPlayer.setPlayWhenReady(true);
            simpleExoPlayer.getPlaybackState();
            Rational aspecration = new Rational(movie_play.getWidth(), movie_play.getHeight());
            pictureInPictureParams.setAspectRatio(aspecration).build();
            getActivity().enterPictureInPictureMode(pictureInPictureParams.build());
            outPIP = false;
            Thread thread = new Thread(){
                @Override
                public void run() {
                    super.run();
                    sendNotificationMedia(Ep);
                }
            };
            thread.start();
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.getPlaybackState();
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if(!isInPictureInPictureMode){
            outPIP = true;
            simpleExoPlayer.setPlayWhenReady(false);
            simpleExoPlayer.getPlaybackState();
        }
    }
}
