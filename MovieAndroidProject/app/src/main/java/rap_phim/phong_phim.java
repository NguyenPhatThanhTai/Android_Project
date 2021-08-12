package rap_phim;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieandroidproject.R;

import java.util.ArrayList;
import java.util.List;

import API.APIControllers;
import danh_sach_tap_phim.Film_List;
import danh_sach_tap_phim.Film_list_Adapter;
import trang_chu.HighRate;

public class phong_phim extends Fragment {
    private RecyclerView ph_ep;
    private Thread thread;
    List<Film_List> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phong_phim, container, false);
        ph_ep = view.findViewById(R.id.ph_ep);

        list.add(new Film_List("test 1", "google.com", "1"));
        list.add(new Film_List("test 2", "google.com", "2"));
        list.add(new Film_List("test 3", "google.com", "3"));
        list.add(new Film_List("test 4", "google.com", "4"));
        list.add(new Film_List("test 5", "google.com", "5"));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 5);
        ph_ep.setLayoutManager(gridLayoutManager);

        thread = new Thread(this::danh_sach_tap_phim);
        thread.start();

        return view;
    }

    private void danh_sach_tap_phim(){
        APIControllers apiControllers = new APIControllers();
//        Film_list_Adapter film_list_adapter = new Film_list_Adapter(apiControllers.getListEp(highRate.getName(), highRate.getMovieId()), this.getActivity());
        Film_list_Adapter film_list_adapter = new Film_list_Adapter(list, getContext());

        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ph_ep.setAdapter(film_list_adapter);
            }
        });
    }
}
