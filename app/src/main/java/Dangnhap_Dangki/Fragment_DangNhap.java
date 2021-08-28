package Dangnhap_Dangki;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieandroidproject.MainActivity;
import com.example.movieandroidproject.R;
import com.example.movieandroidproject.play_movie;
import com.example.movieandroidproject.trang_chu;

import API.APIControllers;
public class Fragment_DangNhap extends Fragment {
    private APIControllers api;
    private Thread thread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__dang_nhap, container, false);
        ((MainActivity)getActivity()).setBottomNav(5);

        EditText username = view.findViewById(R.id.username);
        EditText password = view.findViewById(R.id.password);
        Button dangnhap = view.findViewById(R.id.dangnhap);
        api = new APIControllers();
        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUserName = username.getText().toString();
                String strPassword = password.getText().toString();
                if(strUserName.matches("") || strPassword.matches("")) {
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ", Toast.LENGTH_SHORT).show();
                }else {
                    thread = new Thread(){
                        @Override
                        public void run() {
                            NguoiDung nguoiDung = api.DangNhap(strUserName, strPassword);
                            if(nguoiDung != null) {
                                SharedPreferences sp = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                                SharedPreferences.Editor Ed = sp.edit();
                                Ed.putString("Unm", nguoiDung.getUserId());
                                Ed.commit();
                                showAlert("Đăng nhập thành công");
                                Fragment selectedFragment = new trang_chu(getActivity());
                                FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

                                manager.beginTransaction()
                                        .add(selectedFragment, "back_stack") // Add this transaction to the back stack (name is an optional name for this back stack state, or null).
                                        .addToBackStack(null)
                                        .setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,
                                                R.anim.enter_right_to_left, R.anim.exit_right_to_left)
                                        .replace(R.id.fragment_container,
                                                selectedFragment).commit();
                            }
                            else {
                                showAlert("Sai mật khẩu hoặc tài khoản");
                            }
                        }
                    };
                    thread.start();
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void showAlert(String content){
        thread.interrupt();
        getActivity().runOnUiThread(()->{
            Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
        });
    }
}