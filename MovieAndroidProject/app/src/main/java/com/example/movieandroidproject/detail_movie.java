package com.example.movieandroidproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import trang_chu.*;

public class detail_movie extends Fragment implements IOnBackPressed {

    private HighRate highRate;
    private Context context;

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
