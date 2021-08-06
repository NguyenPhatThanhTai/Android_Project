package trang_chu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LogPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieandroidproject.R;
import com.example.movieandroidproject.detail_movie;

import java.io.InputStream;
import java.util.List;

public class HighRate_Adapter extends RecyclerView.Adapter<HighRate_Adapter.HighRateViewHolder> {

    private List<HighRate> mHighRate;
    private View view;
    Context context;

    public HighRate_Adapter(List<HighRate> mHighRate, Context context) {
        this.mHighRate = mHighRate;
        this.context = context;
    }

    @NonNull
    @Override
    public HighRateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_highrate, parent, false);
        this.view = view;
        return new HighRateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighRateViewHolder holder, int position) {
        HighRate highRate = mHighRate.get(position);
        if(highRate == null){
            return;
        }
        else {
            //set Tên cho danh sách
            holder.HighRate_Tittle.setText(highRate.getName());
            //set ảnh
            new HighRate_Adapter.DownloadImageTask(view.findViewById(R.id.HighRate_Image))
                    .execute(highRate.getThumbnails());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(context, highRate.getTen(), Toast.LENGTH_SHORT).show();

                    //Truyền dữ liệu qua để set trang detail
                    Fragment selectedFragment = new detail_movie(highRate.getMovieId(), highRate.getName(), highRate.getViews(), highRate.getEpisodes(), highRate.getYears(), highRate.getDescription(),highRate.getThumbnails(), highRate.getFee(), context);
                    ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mHighRate != null){
            return mHighRate.size();
        }
        return 0;
    }

    public class HighRateViewHolder extends RecyclerView.ViewHolder{
        private TextView HighRate_Tittle;

        public HighRateViewHolder(@NonNull View itemView) {
            super(itemView);

            HighRate_Tittle = itemView.findViewById(R.id.HighRate_Tittle);
        }
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
