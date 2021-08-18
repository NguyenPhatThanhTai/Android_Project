package the_loai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieandroidproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import tim_kiem.TimKiem;
import tim_kiem.TimKiem_Adapter;

public class TheloaiAdapter extends RecyclerView.Adapter<TheloaiAdapter.TheLoai_Holder>{

    private List<TheLoai> mTheLoai;
    private View view;
    Context context;

    public TheloaiAdapter(List<TheLoai> mTheLoai, Context context) {
        this.mTheLoai = mTheLoai;
        this.context = context;
    }
    @NonNull
    @Override
    public TheLoai_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_highrate, parent, false);
        return new TheloaiAdapter.TheLoai_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheLoai_Holder holder, int position) {
        TheLoai tl = mTheLoai.get(position);
        if(tl == null){
            return;
        }
        else{
            holder.HighRate_Tittle.setText(tl.getName());
            holder.HighRate_ep_num.setText(tl.getEpisodes());
            holder.Highrate_View.setText("Lượt xem: " + tl.getViews());
            holder.setImage(tl.getThumbnails());
        }
    }

    @Override
    public int getItemCount() {
        if(mTheLoai != null){
            return mTheLoai.size();
        }
        else {
            return 0;
        }
    }

    public class TheLoai_Holder extends RecyclerView.ViewHolder{

        private TextView HighRate_Tittle, HighRate_ep_num, Highrate_View;
        private ImageView Cate_Image;

        public void setImage(String url){
            Cate_Image = itemView.findViewById(R.id.HighRate_Image);
            //Thư viện load ảnh Picasso
            Picasso.get().load(url).into(Cate_Image);
        }

        public TheLoai_Holder(@NonNull View itemView) {
            super(itemView);

            Highrate_View = itemView.findViewById(R.id.Highrate_View);
            HighRate_Tittle = itemView.findViewById(R.id.HighRate_Tittle);
            HighRate_ep_num = itemView.findViewById(R.id.ep_num);
        }
    }
}
