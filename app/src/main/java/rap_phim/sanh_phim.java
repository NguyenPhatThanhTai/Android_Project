package rap_phim;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.movieandroidproject.IOnBackPressed;
import com.example.movieandroidproject.R;
import com.example.movieandroidproject.play_movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import API.APIControllers;
import Dangnhap_Dangki.Dangnhap_Dangki;
import danh_sach_tap_phim.Film_List;
import trang_chu.HighRate;
import trang_chu.RecommentForYou;
import com.example.movieandroidproject.trang_chu;

public class sanh_phim extends Fragment implements IOnBackPressed {
    String roomId, movieId, userId;
    Spinner spn_saved_movie;
    List<RecommentForYou> list;
    Thread thread;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sanh_phong_phim, container, false);
        spn_saved_movie = view.findViewById(R.id.spn_saved_movie);
        RatingBar rt_bar_detail = view.findViewById(R.id.rt_bar_movie_room);
        APIControllers api = new APIControllers();
        Button btn_join = view.findViewById(R.id.btn_join);
        Button btn_create_room = view.findViewById(R.id.btn_create_room);
        Button btn_test = view.findViewById(R.id.btn_test_room);

        thread = new Thread(this::setListMovie);
        thread.start();

        SharedPreferences sp1 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        userId = sp1.getString("Unm", null);

        //Tạo phòng mới
        btn_create_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(userId != null){
                    thread = new Thread(){
                        @Override
                        public void run() {
                            roomId = api.createRoom();
                            String check = api.addViewerToRoom(roomId, userId);
                            if(check.equals("Ok")){
                                Fragment selectedFragment = new phong_phim(roomId, movieId, userId);
                                FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

                                manager.beginTransaction()
                                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                                R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                                        .replace(R.id.fragment_container,
                                                selectedFragment).commit();
                            }
                            else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), "Có lỗi xảy ra rồi", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    };
                    thread.start();
                }
                else {
                    Fragment selectedFragment = new Dangnhap_Dangki();
                    FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

                    manager.beginTransaction()
                            .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                    R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                            .replace(R.id.fragment_container,
                                    selectedFragment).commit();
                }
            }
        });

        //Join phòng theo id
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                EditText txt_ma_phong = view.findViewById(R.id.txt_ma_phong);

                if (txt_ma_phong.getText().toString() == null || txt_ma_phong.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Vui lòng nhập mã phòng", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(userId != null){
                        Thread thread = new Thread(){
                            @Override
                            public void run() {
                                String check = api.addViewerToRoom(txt_ma_phong.getText().toString(), userId);
                                if(check.equals("Ok")){
                                    Fragment selectedFragment = new phong_phim(txt_ma_phong.getText().toString(), "", userId);
                                    FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

                                    manager.beginTransaction()
                                            .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                                    R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                                            .replace(R.id.fragment_container,
                                                    selectedFragment).commit();
                                }
                                else {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(), "Phòng không tồn tại hoặc phòng đã đầy", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        };
                        thread.start();
                    }
                    else {
                        Fragment selectedFragment = new Dangnhap_Dangki();
                        FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

                        manager.beginTransaction()
                                .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                        R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                                .replace(R.id.fragment_container,
                                        selectedFragment).commit();
                    }
                }
            }
        });

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userId != null){
                    thread = new Thread(){
                        @Override
                        public void run() {
                            Fragment selectedFragment = new phong_phim_test(userId);
                            FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

                            manager.beginTransaction()
                                    .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                            R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                                    .replace(R.id.fragment_container,
                                            selectedFragment).commit();
                        }
                    };
                    thread.start();
                }
                else {
                    Fragment selectedFragment = new Dangnhap_Dangki();
                    FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

                    manager.beginTransaction()
                            .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                    R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                            .replace(R.id.fragment_container,
                                    selectedFragment).commit();
                }
            }
        });

        spn_saved_movie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View vi, int i, long l) {
                String string = adapterView.getSelectedItem().toString();
                String[] parts = string.split(" - ");
                String part1 = parts[0];
                String part2 = parts[1];
                String part3 = parts[2];
                movieId = part2;

                if(list != null){
                    RecommentForYou recomment = list.get(Integer.parseInt(part1));

                    ImageView img_film_selected = view.findViewById(R.id.img_film_selected);
                    TextView txt_name = view.findViewById(R.id.txt_name);

                    Picasso.get().load(recomment.getThumbnails()).into(img_film_selected);
                    txt_name.setText(recomment.getName() + " - Số tập: " + recomment.getEpisodes());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rt_bar_detail.setRating(Float.parseFloat("5.0"));

        return view;
    }

    private void setListMovie(){
        APIControllers apiControllers = new APIControllers();
        list = apiControllers.getAllMovie();

        ArrayList<String> options = new ArrayList<String>();

        for (int i = 0; i < list.size(); i ++) {
            options.add(i + " - " + list.get(i).getMovieId() + " - " + list.get(i).getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,options);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spn_saved_movie.setAdapter(adapter); // this will set list of values to spinner

                spn_saved_movie.setSelection(options.indexOf(1));//set selected value in spinner
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        Fragment selectedFragment = new trang_chu();
        FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
        manager.popBackStack();

        manager.beginTransaction()
                .setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,
                        R.anim.enter_right_to_left, R.anim.exit_right_to_left)
                .replace(R.id.fragment_container,
                        selectedFragment).commit();

        return true;
    }
}
