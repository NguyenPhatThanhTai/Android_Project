package danh_sach_tap_phim;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieandroidproject.MainActivity;
import com.example.movieandroidproject.R;
import com.example.movieandroidproject.detail_movie;
import com.example.movieandroidproject.play_movie;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import trang_chu.HighRate;
import trang_chu.HighRate_Adapter;

public class Film_list_Adapter extends RecyclerView.Adapter<Film_list_Adapter.Film_list_Holder> {
    private List<Film_List> list;
    private Context context;
    private View view;
    int i = 1;

    public Film_list_Adapter(List<Film_List> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Film_list_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list_episode, parent, false);
        this.view = view;
        return new Film_list_Adapter.Film_list_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Film_list_Holder holder, int position) {
        Film_List filmList = list.get(position);

        if(filmList == null){
            return;
        }
        else {
            holder.ep_name.setText("Tập " + filmList.getEp());

            FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();

            holder.ep_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment f = manager.findFragmentById(R.id.fragment_container);
                    if(f instanceof play_movie)
                        // do something with f
                        ((play_movie) f).setVideoUrl(filmList.getUrl(), filmList.getEp(), filmList.getFilm_Name());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    public class Film_list_Holder extends RecyclerView.ViewHolder{
        private Button ep_name;

        public Film_list_Holder(@NonNull View itemView) {
            super(itemView);
            ep_name = itemView.findViewById(R.id.ep_name);
        }
    }
}
