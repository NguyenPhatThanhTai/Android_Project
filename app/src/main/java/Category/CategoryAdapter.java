package Category;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.movieandroidproject.phimtheo_theloai;
import com.squareup.picasso.Picasso;

import java.util.List;

import trang_chu.HighRate;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    List<Category> categoryData;
    Context context;

    public CategoryAdapter(List<Category> categoryData, MainActivity mainActivity){
        this.categoryData = categoryData;
        this.context = mainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Category categoryList =  categoryData.get(position);
        holder.categoryText.setText(categoryList.getCategoryName());
        holder.setImage(categoryList.getCategoryPicture());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Fragment selectedFragment = new phimtheo_theloai(categoryList.getCategoryId(), categoryList.getCategoryName());
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

    @Override
    public int getItemCount() {
        if(categoryData != null){
            return categoryData.size();
        }
        else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView categoryImage;
        TextView categoryText;
        LinearLayout cate_items_backgroud;

        public void setImage(String url){
            categoryImage = itemView.findViewById(R.id.category_img);
            //Th?? vi???n load ???nh Picasso
            Picasso.get().load(url).into(categoryImage);
        }
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.category_id);
            cate_items_backgroud = itemView.findViewById(R.id.cate_items_backgroud);

            SharedPreferences sp1 = context.getSharedPreferences("Setting", Context.MODE_PRIVATE);
            String theme = sp1.getString("Theme", null);
            if(theme != null && theme.equals("Light")){
                cate_items_backgroud.setBackgroundColor(Color.WHITE);
                categoryText.setTextColor(Color.BLACK);
            }else if (theme != null && theme.equals("Dark")){
                cate_items_backgroud.setBackgroundColor(Color.parseColor("#1C1C27"));
                categoryText.setTextColor(Color.WHITE);
            }
        }
    }

}
