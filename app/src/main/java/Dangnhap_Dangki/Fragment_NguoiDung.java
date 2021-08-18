package Dangnhap_Dangki;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.movieandroidproject.IOnBackPressed;
import com.example.movieandroidproject.R;
import com.example.movieandroidproject.trang_chu;

import API.APIControllers;


public class Fragment_NguoiDung extends Fragment implements IOnBackPressed {
    String id;
    public Fragment_NguoiDung(String id){
        this.id = id;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thong_tin_nguoi_dung, container, false);

        Thread thread = new Thread(){
            @Override
            public void run() {
                APIControllers api = new APIControllers();
                NguoiDung nd = api.ThongTinCaNhan(id);
                TextView tendangnhap = view.findViewById(R.id.tendangnhap);
                TextView tendaydu = view.findViewById(R.id.tendaydu);
                TextView sinhnhat = view.findViewById(R.id.sinhnhat);
                TextView diachi = view.findViewById(R.id.diachi);
                TextView sodienthoai = view.findViewById(R.id.sodienthoai);
                TextView email = view.findViewById(R.id.email);
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
                Fragment selectedFragment = new trang_chu();
                FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,
                                R.anim.enter_right_to_left, R.anim.exit_right_to_left)
                        .replace(R.id.fragment_container,
                                selectedFragment).commit();
            }
        });
        return view;
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
