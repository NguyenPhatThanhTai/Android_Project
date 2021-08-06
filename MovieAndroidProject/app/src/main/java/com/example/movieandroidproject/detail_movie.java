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

    private String img_detail;
    private String name, category, time, descrip;
    private Context context;

    public detail_movie(String img_detail, String name_detail, String category_detail, String time_detail, String descrip_detail, Context context){
        this.img_detail = img_detail;
        this.name = name_detail;
        this.category = category_detail;
        this.time = time_detail;
        this.descrip = descrip_detail;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_movie, container, false);

//        ImageView img_detail = view.findViewById(R.id.img_detail);
//        img_detail.setImageResource(this.img_detail);

        new DownloadImageTask(view.findViewById(R.id.img_detail))
                .execute(img_detail);

        TextView name_detail = view.findViewById(R.id.name_detail);
        name_detail.setText(name);
        TextView category_detail = view.findViewById(R.id.category_detail);
        category_detail.setText(category);
        TextView time_detail = view.findViewById(R.id.time_detail);
        time_detail.setText(time);
        TextView descrip_detail = view.findViewById(R.id.descrip_detail);
        descrip_detail.setText(descrip);

        Button back_detail = view.findViewById(R.id.back_detail);
        Button playnow_detail = view.findViewById(R.id.playnow_detail);

        playnow_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Gọi trang xem phim

                Fragment selectedFragment = new play_movie();
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
