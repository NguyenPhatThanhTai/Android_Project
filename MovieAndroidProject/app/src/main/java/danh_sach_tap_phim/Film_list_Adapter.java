package danh_sach_tap_phim;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieandroidproject.MainActivity;
import com.example.movieandroidproject.R;
import com.example.movieandroidproject.detail_movie;
import com.example.movieandroidproject.play_movie;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import trang_chu.HighRate;
import trang_chu.HighRate_Adapter;

public class Film_list_Adapter extends RecyclerView.Adapter<Film_list_Adapter.Film_list_Holder> {
    private List<Film_List> list;
    private Context context;
    private View view;
    private HighRate highRate;
    private String url;

    public Film_list_Adapter(List<Film_List> list, Context context, HighRate highRate) {
        this.list = list;
        this.context = context;
        this.highRate = highRate;
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
        ImageView thumbnail = view.findViewById(R.id.thumbnail_video);

        if(filmList == null){
            return;
        }
        else {
            holder.ep_name.setText("Tập " + filmList.getEp());

            //anh xem trc video
            url = filmList.getUrl();
            new Film_list_Adapter.getVideoThumbnail(thumbnail)
                    .execute(filmList.getUrl());

            FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();

            holder.ep_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment f = manager.findFragmentById(R.id.fragment_container);
                    if(f instanceof play_movie)
                        // do something with f
                        ((play_movie) f).setVideoUrl(filmList.getUrl());

//                    Fragment selectedFragment = new play_movie(highRate, filmList.getUrl());
//                    ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                            selectedFragment).commit();
                }
            });

            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment f = manager.findFragmentById(R.id.fragment_container);
                    if(f instanceof play_movie)
                        // do something with f
                        ((play_movie) f).setVideoUrl(filmList.getUrl());
                }
            });
        }
    }

    private class getVideoThumbnail extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public getVideoThumbnail(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            Bitmap bitmap = null;
            MediaMetadataRetriever mediaMetadataRetriever = null;
            try
            {
                mediaMetadataRetriever = new MediaMetadataRetriever();
                if (Build.VERSION.SDK_INT >= 14)
                    mediaMetadataRetriever.setDataSource(url, new HashMap<String, String>());
                else
                    mediaMetadataRetriever.setDataSource(url);
                //   mediaMetadataRetriever.setDataSource(videoPath);
                bitmap = mediaMetadataRetriever.getFrameAtTime(50000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (mediaMetadataRetriever != null)
                {
                    mediaMetadataRetriever.release();
                }
            }

            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
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
        private TextView ep_name;

        public Film_list_Holder(@NonNull View itemView) {
            super(itemView);

            ep_name = itemView.findViewById(R.id.ep_name);
        }
    }
}
