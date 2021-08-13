package rap_phim;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.movieandroidproject.R;
import com.example.movieandroidproject.play_movie;

import java.util.ArrayList;

public class sanh_phim extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sanh_phong_phim, container, false);
        Spinner spn_saved_movie = view.findViewById(R.id.spn_saved_movie);
        RatingBar rt_bar_detail = view.findViewById(R.id.rt_bar_movie_room);
        Button btn_join = view.findViewById(R.id.btn_join);

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment selectedFragment = new phong_phim();
                FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.fragment_container,
                                selectedFragment).commit();
            }
        });

        rt_bar_detail.setRating(Float.parseFloat("4.5"));

        ArrayList<String> options=new ArrayList<String>();
        options.add("Chọn phim đã lưu ở đây");
        options.add("Tên phim 2");
        options.add("Tên phim 3");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,options);
        spn_saved_movie.setAdapter(adapter); // this will set list of values to spinner

        spn_saved_movie.setSelection(options.indexOf(1));//set selected value in spinner

        return view;
    }
}
