package trang_chu;

import android.content.Context;
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

import java.util.List;

public class RecommentForYou_Adapter extends RecyclerView.Adapter<RecommentForYou_Adapter.RecommentForYou_Holder> {

    private List<RecommentForYou> mRecomment;
    private View view;
    Context context;

    public RecommentForYou_Adapter(List<RecommentForYou> mRecomment, Context context) {
        this.mRecomment = mRecomment;
        this.context = context;
    }

    @NonNull
    @Override
    public RecommentForYou_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_highrate, parent, false);

        return new RecommentForYou_Adapter.RecommentForYou_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommentForYou_Holder holder, int position) {
        RecommentForYou recomment = mRecomment.get(position);
        ImageView thumbnail_img;
        if(recomment == null){
            return;
        }
        else {
            //set Tên cho danh sách
            holder.HighRate_Tittle.setText(recomment.getName());
            //set số tập
            holder.HighRate_ep_num.setText(recomment.getEpisodes());
            //set ảnh
            thumbnail_img = view.findViewById(R.id.HighRate_Image);
            holder.setImage(recomment.getThumbnails());
            //set lượt xem
            holder.Highrate_View.setText("Lượt xem: " + recomment.getViews());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(context, highRate.getTen(), Toast.LENGTH_SHORT).show();

                    //Truyền dữ liệu qua để set trang detail
                    HighRate hi = new HighRate(recomment.getMovieId(), recomment.getName(), recomment.getViews(), recomment.getEpisodes(), recomment.getYears(), recomment.getDescription(), recomment.getThumbnails(), recomment.getFee());

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
        if(mRecomment != null){
            return mRecomment.size();
        }
        return 0;
    }

    public class RecommentForYou_Holder extends RecyclerView.ViewHolder{
        private TextView HighRate_Tittle, HighRate_ep_num, Highrate_View;
        private ImageView Recomment_Image;

        public void setImage(String url){
            Recomment_Image = itemView.findViewById(R.id.HighRate_Image);
            //Thư viện load ảnh Picasso
            Picasso.get().load(url).into(Recomment_Image);
        }

        public RecommentForYou_Holder(@NonNull View itemView) {
            super(itemView);

            Highrate_View = itemView.findViewById(R.id.Highrate_View);
            HighRate_Tittle = itemView.findViewById(R.id.HighRate_Tittle);
            HighRate_ep_num = itemView.findViewById(R.id.ep_num);
        }
    }
}
