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

public class HighRate_Adapter extends RecyclerView.Adapter<HighRate_Adapter.HighRateViewHolder> {

    private List<HighRate> mHighRate;
    Context context;

    public HighRate_Adapter(List<HighRate> mHighRate, Context context) {
        this.mHighRate = mHighRate;
        this.context = context;
    }

    @NonNull
    @Override
    public HighRateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_highrate, parent, false);

        return new HighRateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighRateViewHolder holder, int position) {
        HighRate highRate = mHighRate.get(position);
        if(highRate == null){
            return;
        }
        else {
            holder.HighRate_Image.setImageResource(highRate.getImageId());
            holder.HighRate_Tittle.setText(highRate.getTittle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, highRate.getTittle(), Toast.LENGTH_SHORT).show();
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
        private ImageView HighRate_Image;
        private TextView HighRate_Tittle;

        public HighRateViewHolder(@NonNull View itemView) {
            super(itemView);

            HighRate_Image = itemView.findViewById(R.id.HighRate_Image);
            HighRate_Tittle = itemView.findViewById(R.id.HighRate_Tittle);
        }
    }
}
