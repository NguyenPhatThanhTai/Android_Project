package tim_kiem;

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

import trang_chu.RecommentForYou;
import trang_chu.RecommentForYou_Adapter;

public class TimKiem_Adapter extends RecyclerView.Adapter<TimKiem_Adapter.TimKiem_Holder> {
    private List<TimKiem> mTimKiem;
    private View view;
    Context context;

    public TimKiem_Adapter(List<TimKiem> mTimKiem, Context context) {
        this.mTimKiem = mTimKiem;
        this.context = context;
    }
    @NonNull
    @Override
    public TimKiem_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_highrate, parent, false);
        return new TimKiem_Adapter.TimKiem_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimKiem_Holder holder, int position) {
        TimKiem tk = mTimKiem.get(position);
        if(tk == null){
            return;
        }
        else{
            holder.HighRate_Tittle.setText(tk.getName());
            holder.HighRate_ep_num.setText(tk.getEpisodes());
            holder.Highrate_View.setText("Lượt xem: " + tk.getViews());
            holder.setImage(tk.getThumbnails());
        }

    }

    @Override
    public int getItemCount() {
        if(mTimKiem != null){
            return mTimKiem.size();
        }
        else {
            return 0;
        }
    }

    public class TimKiem_Holder extends RecyclerView.ViewHolder{

        private TextView HighRate_Tittle, HighRate_ep_num, Highrate_View;
        private ImageView Recomment_Image;

        public void setImage(String url){
            Recomment_Image = itemView.findViewById(R.id.HighRate_Image);
            //Thư viện load ảnh Picasso
            Picasso.get().load(url).into(Recomment_Image);
        }

        public TimKiem_Holder(@NonNull View itemView) {
            super(itemView);

            Highrate_View = itemView.findViewById(R.id.Highrate_View);
            HighRate_Tittle = itemView.findViewById(R.id.HighRate_Tittle);
            HighRate_ep_num = itemView.findViewById(R.id.ep_num);
        }
    }
}
