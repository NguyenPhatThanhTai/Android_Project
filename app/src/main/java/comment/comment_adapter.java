package comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieandroidproject.MainActivity;
import com.example.movieandroidproject.R;

import org.w3c.dom.Comment;

import java.util.List;

import Category.Category;

public class comment_adapter extends RecyclerView.Adapter<comment_adapter.comment_holder> {

    List<comment> comment;
    Context context;

    public comment_adapter(List<comment> comment, Context context){
        this.comment = comment;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public comment_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.items_comment, parent, false);
        comment_holder comment_view = new comment_holder(view);

        return comment_view;
    }

    @Override
    public void onBindViewHolder(@NonNull comment_holder holder, int position) {
        comment comment_list =  comment.get(position);
        holder.txt_name_comment.setText(comment_list.getName());
        holder.txt_comment_content.setText(comment_list.getComment());
        holder.txt_comment_created.setText(comment_list.getCreated());
    }

    @Override
    public int getItemCount() {
        if(comment != null){
            return comment.size();
        }
        else{
            return 0;
        }
    }

    public class comment_holder extends RecyclerView.ViewHolder{
        private TextView txt_name_comment, txt_comment_content, txt_comment_created;

        public comment_holder(@NonNull View itemView) {
            super(itemView);
            txt_name_comment = itemView.findViewById(R.id.txt_name_comment);
            txt_comment_content = itemView.findViewById(R.id.txt_comment_content);
            txt_comment_created = itemView.findViewById(R.id.txt_comment_created);
        }
    }
}
