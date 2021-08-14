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

import com.example.movieandroidproject.R;
import com.example.movieandroidproject.trang_chu;

import API.APIControllers;


public class Fragment_NguoiDung extends Fragment {
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
                tendangnhap.setText(nd.getUsername());
                TextView tendaydu = view.findViewById(R.id.tendaydu);
                tendaydu.setText(nd.getFullName());
                TextView sinhnhat = view.findViewById(R.id.sinhnhat);
                sinhnhat.setText(nd.getBirthday());
                TextView diachi = view.findViewById(R.id.diachi);
                diachi.setText(nd.getAddress());
                TextView sodienthoai = view.findViewById(R.id.sodienthoai);
                sodienthoai.setText(nd.getPhone());
                TextView email = view.findViewById(R.id.email);
                email.setText(nd.getEmail());
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
}
