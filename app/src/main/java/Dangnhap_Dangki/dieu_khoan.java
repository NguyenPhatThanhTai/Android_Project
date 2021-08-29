package Dangnhap_Dangki;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movieandroidproject.R;

public class dieu_khoan extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dieu_khoan, container, false);
        TextView temp = view.findViewById(R.id.temp);
        TextView temp_content = view.findViewById(R.id.temp_content);
        LinearLayout temp_backgroud = view.findViewById(R.id.temp_backgroud);

        SharedPreferences sp1 = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
        String theme = sp1.getString("Theme", null);

        if(theme != null && theme.equals("Light")){
            temp_backgroud.setBackgroundColor(Color.WHITE);
            temp.setTextColor(Color.BLACK);
            temp_content.setTextColor(Color.BLACK);
        }else if (theme != null && theme.equals("Dark")){
            temp_backgroud.setBackgroundResource(R.drawable.black_wallpaper);
            temp.setTextColor(Color.WHITE);
            temp_content.setTextColor(Color.WHITE);
        }

        return view;
    }
}
