package trang_chu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieandroidproject.R;
import com.example.movieandroidproject.detail_movie;
import com.squareup.picasso.Picasso;

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
        ImageView thumbnail_img;
        if(highRate == null){
            return;
        }
        else {
            //set Tên cho danh sách
            holder.HighRate_Tittle.setText(highRate.getName());
            //set số tập
            holder.HighRate_ep_num.setText(highRate.getEpisodes());
            //set ảnh
            thumbnail_img = view.findViewById(R.id.HighRate_Image);
            //Thư viện load ảnh Picasso
            Picasso.get().load(highRate.getThumbnails()).into(thumbnail_img);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(context, highRate.getTen(), Toast.LENGTH_SHORT).show();

                    //Truyền dữ liệu qua để set trang detail
                    HighRate hi = new HighRate(highRate.getMovieId(), highRate.getName(), highRate.getViews(), highRate.getEpisodes(), highRate.getYears(), highRate.getDescription(),highRate.getThumbnails(), highRate.getFee());

                    Fragment selectedFragment = new detail_movie(hi, context);
                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();

                    manager.beginTransaction()
                            .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                    R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                            .replace(R.id.fragment_container,
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
        private TextView HighRate_Tittle, HighRate_ep_num;
        private ImageView HighRate_Image;

        public HighRateViewHolder(@NonNull View itemView) {
            super(itemView);

            HighRate_Tittle = itemView.findViewById(R.id.HighRate_Tittle);
            HighRate_ep_num = itemView.findViewById(R.id.ep_num);
            HighRate_Image = itemView.findViewById(R.id.HighRate_Image);
        }
    }
}
