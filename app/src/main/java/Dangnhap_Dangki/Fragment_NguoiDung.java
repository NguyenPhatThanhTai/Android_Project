package Dangnhap_Dangki;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieandroidproject.MainActivity;
import com.example.movieandroidproject.R;
import com.example.movieandroidproject.trang_chu;

import java.util.List;

import API.APIControllers;
import luu_phim.*;


public class Fragment_NguoiDung extends Fragment {
    String id;
    RecyclerView recyclerView;
    public Fragment_NguoiDung(String id){
        this.id = id;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thong_tin_nguoi_dung, container, false);
        ((MainActivity)getActivity()).setBottomNav(5);

        Switch sw_darkMode = view.findViewById(R.id.sw_darkMode);
        LinearLayout user_backgroud = view.findViewById(R.id.user_backgroud);
        TextView textView7, textView8, textView9, textView10, textView11, textView12, textView13, txt_tenLuuphim;
        textView7 = view.findViewById(R.id.checkVerApp);
        textView8 = view.findViewById(R.id.textView8);
        textView9 = view.findViewById(R.id.textView9);
        textView10 = view.findViewById(R.id.textView10);
        textView11 = view.findViewById(R.id.textView11);
        textView12 = view.findViewById(R.id.textView12);
        textView13 = view.findViewById(R.id.textView13);
        txt_tenLuuphim = view.findViewById(R.id.txt_tenLuuphim);
        TextView tendangnhap = view.findViewById(R.id.tendangnhap);
        TextView tendaydu = view.findViewById(R.id.tendaydu);
        TextView sinhnhat = view.findViewById(R.id.sinhnhat);
        TextView diachi = view.findViewById(R.id.diachi);
        TextView sodienthoai = view.findViewById(R.id.sodienthoai);
        TextView email = view.findViewById(R.id.email);

        SharedPreferences sp1 = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
        String theme = sp1.getString("Theme", null);

        if(theme != null && theme.equals("Light")){
            sw_darkMode.setChecked(false);
            user_backgroud.setBackgroundColor(Color.WHITE);
            textView7.setTextColor(Color.BLACK);
            textView8.setTextColor(Color.BLACK);
            textView9.setTextColor(Color.BLACK);
            textView10.setTextColor(Color.BLACK);
            textView11.setTextColor(Color.BLACK);
            textView12.setTextColor(Color.BLACK);
            textView13.setTextColor(Color.BLACK);
            txt_tenLuuphim.setTextColor(Color.BLACK);
            tendangnhap.setTextColor(Color.BLACK);
            tendaydu.setTextColor(Color.BLACK);
            sinhnhat.setTextColor(Color.BLACK);
            diachi.setTextColor(Color.BLACK);
            sodienthoai.setTextColor(Color.BLACK);
            email.setTextColor(Color.BLACK);
            sw_darkMode.setTextColor(Color.BLACK);
        }else if (theme != null && theme.equals("Dark")){
            sw_darkMode.setChecked(true);
            user_backgroud.setBackgroundResource(R.drawable.black_wallpaper);
            textView7.setTextColor(Color.WHITE);
            textView8.setTextColor(Color.WHITE);
            textView9.setTextColor(Color.WHITE);
            textView10.setTextColor(Color.WHITE);
            textView11.setTextColor(Color.WHITE);
            textView12.setTextColor(Color.WHITE);
            textView13.setTextColor(Color.WHITE);
            txt_tenLuuphim.setTextColor(Color.WHITE);
            tendangnhap.setTextColor(Color.WHITE);
            tendaydu.setTextColor(Color.WHITE);
            sinhnhat.setTextColor(Color.WHITE);
            diachi.setTextColor(Color.WHITE);
            sodienthoai.setTextColor(Color.WHITE);
            email.setTextColor(Color.WHITE);
            sw_darkMode.setTextColor(Color.WHITE);
        }

        sw_darkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences sp = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
                SharedPreferences.Editor Ed = sp.edit();
                if(b){
                    Ed.putString("Theme", "Dark");
                    user_backgroud.setBackgroundResource(R.drawable.black_wallpaper);
                    textView7.setTextColor(Color.WHITE);
                    textView8.setTextColor(Color.WHITE);
                    textView9.setTextColor(Color.WHITE);
                    textView10.setTextColor(Color.WHITE);
                    textView11.setTextColor(Color.WHITE);
                    textView12.setTextColor(Color.WHITE);
                    textView13.setTextColor(Color.WHITE);
                    txt_tenLuuphim.setTextColor(Color.WHITE);
                    tendangnhap.setTextColor(Color.WHITE);
                    tendaydu.setTextColor(Color.WHITE);
                    sinhnhat.setTextColor(Color.WHITE);
                    diachi.setTextColor(Color.WHITE);
                    sodienthoai.setTextColor(Color.WHITE);
                    email.setTextColor(Color.WHITE);
                    sw_darkMode.setTextColor(Color.WHITE);

                    setLuuphimAdapter();
                }else {
                    Ed.putString("Theme", "Light");
                    user_backgroud.setBackgroundColor(Color.WHITE);
                    textView7.setTextColor(Color.BLACK);
                    textView8.setTextColor(Color.BLACK);
                    textView9.setTextColor(Color.BLACK);
                    textView10.setTextColor(Color.BLACK);
                    textView11.setTextColor(Color.BLACK);
                    textView12.setTextColor(Color.BLACK);
                    textView13.setTextColor(Color.BLACK);
                    txt_tenLuuphim.setTextColor(Color.BLACK);
                    tendangnhap.setTextColor(Color.BLACK);
                    tendaydu.setTextColor(Color.BLACK);
                    sinhnhat.setTextColor(Color.BLACK);
                    diachi.setTextColor(Color.BLACK);
                    sodienthoai.setTextColor(Color.BLACK);
                    email.setTextColor(Color.BLACK);
                    sw_darkMode.setTextColor(Color.BLACK);

                    setLuuphimAdapter();
                }
                Ed.commit();
            }
        });

        Thread thread = new Thread(){
            @Override
            public void run() {
                APIControllers api = new APIControllers();
                NguoiDung nd = api.ThongTinCaNhan(id);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tendangnhap.setText(nd.getUsername());
                        tendaydu.setText(nd.getFullName());
                        sinhnhat.setText(nd.getBirthday());
                        diachi.setText(nd.getAddress());
                        sodienthoai.setText(nd.getPhone());
                        email.setText(nd.getEmail());
                    }
                });
            }
        };
        thread.start();
        Button dangxuat = view.findViewById(R.id.dangxuat);
        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                SharedPreferences.Editor Ed = sp.edit();
                Ed.remove("Unm");
                Ed.commit();
                Fragment selectedFragment = new trang_chu(getActivity());
                FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

                manager.beginTransaction()
//                        .add(selectedFragment, "back_stack") // Add this transaction to the back stack (name is an optional name for this back stack state, or null).
//                        .addToBackStack(null)
                        .setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,
                                R.anim.enter_right_to_left, R.anim.exit_right_to_left)
                        .replace(R.id.fragment_container,
                                selectedFragment).commit();
            }
        });
        Button vechungtoi = view.findViewById(R.id.veungdung);
        vechungtoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment selectedFragment = new ve_ung_dung();
                FragmentManager manager = getActivity().getSupportFragmentManager();

                manager.beginTransaction()
                        .add(selectedFragment, "back_stack") // Add this transaction to the back stack (name is an optional name for this back stack state, or null).
                        .addToBackStack(null)
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                                R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.fragment_container,
                                selectedFragment).commit();
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_luuphim);
        recyclerView.setLayoutManager(gridLayoutManager);

        setLuuphimAdapter();

        return view;
    }

    private void setLuuphimAdapter(){
        Thread thread1 = new Thread(){
            @Override
            public void run() {
                APIControllers Api = new APIControllers();
                SharedPreferences sp1 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                String userId = sp1.getString("Unm", null);
                List<luu_phim> list = Api.getListSavedFilm(userId);
                if(list != null)
                {
                    luuphimAdapter adapter = new luuphimAdapter(list, getContext());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(adapter);
                        }
                    });
                }
                else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),"Không tìm thấy phim !",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        };
        thread1.start();
    }

//    @Override
//    public boolean onBackPressed() {
//        Fragment selectedFragment = new trang_chu();
//        FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
//        manager.popBackStack();
//
//        manager.beginTransaction()
//                .setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,
//                        R.anim.enter_right_to_left, R.anim.exit_right_to_left)
//                .replace(R.id.fragment_container,
//                        selectedFragment).commit();
//
//        return true;
//    }
}
