package danh_sach_tap_phim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieandroidproject.R;

import java.util.List;

import trang_chu.HighRate;
import trang_chu.HighRate_Adapter;

public class Film_list_Adapter extends RecyclerView.Adapter<Film_list_Adapter.Film_list_Holder> {
    private List<Film_List> list;
    private Context context;
    private View view;

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
            holder.ep_name.setText("-" + filmList.getFilm_Name() + ": Tập " + filmList.getEp());
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
