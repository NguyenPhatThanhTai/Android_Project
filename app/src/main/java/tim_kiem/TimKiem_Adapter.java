package tim_kiem;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieandroidproject.R;
import com.example.movieandroidproject.detail_movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import trang_chu.HighRate;
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
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(context, highRate.getTen(), Toast.LENGTH_SHORT).show();

                    //Truyền dữ liệu qua để set trang detail
                    HighRate hi = new HighRate(tk.getMovieId(), tk.getName(), tk.getViews(), tk.getEpisodes(), tk.getYears(), tk.getDescription(),tk.getThumbnails(), tk.getFee());

                    Fragment selectedFragment = new detail_movie(hi, context);
                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();

                    manager.beginTransaction()
                            .add(selectedFragment, "back_stack") // Add this transaction to the back stack (name is an optional name for this back stack state, or null).
                            .addToBackStack(null)
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

            SharedPreferences sp1 = context.getSharedPreferences("Setting", Context.MODE_PRIVATE);
            String theme = sp1.getString("Theme", null);
            if(theme != null && theme.equals("Light")){
                HighRate_Tittle.setTextColor(Color.BLACK);
            }else if (theme != null && theme.equals("Dark")){
                HighRate_Tittle.setTextColor(Color.WHITE);
            }
        }
    }
}
