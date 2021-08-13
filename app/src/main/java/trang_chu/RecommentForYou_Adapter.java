package trang_chu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieandroidproject.R;

import java.util.List;

public class RecommentForYou_Adapter extends RecyclerView.Adapter<RecommentForYou_Adapter.RecommentForYou_Holder> {

    private List<RecommentForYou> mRecomment;
    Context context;

    public RecommentForYou_Adapter(List<RecommentForYou> mRecomment, Context context) {
        this.mRecomment = mRecomment;
        this.context = context;
    }



    @NonNull
    @Override
    public RecommentForYou_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_highrate, parent, false);

        return new RecommentForYou_Adapter.RecommentForYou_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommentForYou_Holder holder, int position) {
        RecommentForYou recommentForYou = mRecomment.get(position);
        if(recommentForYou == null){
            return;
        }
        else {
            holder.RecommentForYou_Image.setImageResource(recommentForYou.getRecomment_Image());
            holder.RecommentForYou_Tittle.setText(recommentForYou.getTittle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, recommentForYou.getTittle(), Toast.LENGTH_SHORT).show();
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
        private ImageView RecommentForYou_Image;
        private TextView RecommentForYou_Tittle;

        public RecommentForYou_Holder(@NonNull View itemView) {
            super(itemView);

            RecommentForYou_Image = itemView.findViewById(R.id.HighRate_Image);
            RecommentForYou_Tittle = itemView.findViewById(R.id.HighRate_Tittle);
        }
    }
}
