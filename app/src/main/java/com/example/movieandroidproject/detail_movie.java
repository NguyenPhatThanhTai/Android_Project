package com.example.movieandroidproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.squareup.picasso.Picasso;

import java.io.InputStream;

import API.APIControllers;
import Dangnhap_Dangki.Dangnhap_Dangki;
import rap_phim.phong_phim;
import trang_chu.*;

public class detail_movie extends Fragment implements IOnBackPressed {

    private HighRate highRate;
    private Context context;
    ImageButton save_film;
    ImageButton saved_film;
    String userId;
    Thread thread;

    public detail_movie(HighRate highRate, Context context) {
        this.highRate = highRate;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_movie, container, false);
        ImageView img_detail = view.findViewById(R.id.img_detail);
        RatingBar rt_bar_detail = view.findViewById(R.id.rt_bar_detail);
        save_film = view.findViewById(R.id.save_film);
        saved_film = view.findViewById(R.id.saved_film);
        Thread thread = new Thread(){
            @Override
            public void run() {
                check();
            }
        };
        thread.start();

        SharedPreferences sp1 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        userId = sp1.getString("Unm", null);

        save_film.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LuuPhim();
            }
        });

        saved_film.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HuyLuu();
            }
        });

        rt_bar_detail.setRating(Float.parseFloat("5.0"));

        Picasso.get().load(highRate.getThumbnails()).into(img_detail);

        TextView name_detail = view.findViewById(R.id.name_detail);
        name_detail.setText(highRate.getName());
        TextView category_detail = view.findViewById(R.id.category_detail);
        category_detail.setText("Anime");
        TextView time_detail = view.findViewById(R.id.time_detail);
        time_detail.setText(highRate.getEpisodes());
        TextView descrip_detail = view.findViewById(R.id.descrip_detail);
        descrip_detail.setText(highRate.getDescription());
        Button playnow_detail = view.findViewById(R.id.playnow_detail);

        playnow_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIControllers api = new APIControllers();
                api.Views(highRate.getMovieId());
                //Gọi trang xem phim

                Fragment selectedFragment = new play_movie(highRate, "");
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();

                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.fragment_container,
                        selectedFragment).commit();
            }
        });

        return view;
    }

    public void LuuPhim()
    {
        if(userId != null) {
            thread = new Thread() {
                @Override
                public void run() {
                    APIControllers api = new APIControllers();
                    Boolean check = api.LuuPhim(highRate.getMovieId(), userId);
                    if(check == true){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                save_film.setVisibility(View.GONE);
                                saved_film.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                    else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                saved_film.setVisibility(View.GONE);
                                save_film.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }
            };
            thread.start();
        }
        else {
            Fragment selectedFragment = new Dangnhap_Dangki();
            FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

            manager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                            R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                    .replace(R.id.fragment_container,
                            selectedFragment).commit();
        }
    }

    public void HuyLuu()
    {
        if(userId != null) {
            thread = new Thread() {
                @Override
                public void run() {
                    APIControllers api = new APIControllers();
                    Boolean check = api.HuyLuu(highRate.getMovieId(), userId);
                    if(check == true){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                saved_film.setVisibility(View.GONE);
                                save_film.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                    else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                save_film.setVisibility(View.GONE);
                                saved_film.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }
            };
            thread.start();
        }
        else {
            Fragment selectedFragment = new Dangnhap_Dangki();
            FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

            manager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                            R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                    .replace(R.id.fragment_container,
                            selectedFragment).commit();
        }
    }

    private void check(){
        APIControllers api = new APIControllers();
        Boolean check = api.CheckLuu(highRate.getMovieId(), userId);
        if(check == true) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    saved_film.setVisibility(View.VISIBLE);
                }
            });
        }
        else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    save_film.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public boolean onBackPressed() {
        Fragment selectedFragment = new trang_chu();
        FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
        manager.popBackStack();

        manager.beginTransaction()
                .setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,
                        R.anim.enter_right_to_left, R.anim.exit_right_to_left)
                .replace(R.id.fragment_container,
                selectedFragment).commit();

        return true;
    }
}
