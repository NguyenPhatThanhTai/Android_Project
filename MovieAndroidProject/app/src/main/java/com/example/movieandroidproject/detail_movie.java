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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.InputStream;

public class detail_movie extends Fragment {

    private String MovieId;
    private String Name;
    private String Views;
    private String Episodes;
    private String Years;
    private String Description;
    private String Thumbnails;
    private String Fee;
    private Context context;

    public detail_movie(String movieId, String name, String views, String episodes, String years, String description, String thumbnails, String fee, Context context) {
        MovieId = movieId;
        Name = name;
        Views = views;
        Episodes = episodes;
        Years = years;
        Description = description;
        Thumbnails = thumbnails;
        Fee = fee;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_movie, container, false);

//        ImageView img_detail = view.findViewById(R.id.img_detail);
//        img_detail.setImageResource(this.img_detail);

        new DownloadImageTask(view.findViewById(R.id.img_detail))
                .execute(Thumbnails);

        TextView name_detail = view.findViewById(R.id.name_detail);
        name_detail.setText(Name);
        TextView category_detail = view.findViewById(R.id.category_detail);
        category_detail.setText("Anime");
        TextView time_detail = view.findViewById(R.id.time_detail);
        time_detail.setText(Episodes);
        TextView descrip_detail = view.findViewById(R.id.descrip_detail);
        descrip_detail.setText(Description);

        Button back_detail = view.findViewById(R.id.back_detail);
        Button playnow_detail = view.findViewById(R.id.playnow_detail);

        playnow_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Gọi trang xem phim

                Fragment selectedFragment = new play_movie(MovieId, Name);
                ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
            }
        });

        return view;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Lỗi ", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
