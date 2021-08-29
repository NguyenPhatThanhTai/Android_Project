package Dangnhap_Dangki;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.movieandroidproject.R;

public class ve_ung_dung extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ve_ung_dung, container, false);
        TextView verApp = view.findViewById(R.id.verapp);
        TextView XemGiDo = view.findViewById(R.id.xemgido);
        TextView checkVerApp = view.findViewById(R.id.checkVerApp);
        ConstraintLayout about_backgroud = view.findViewById(R.id.about_backgroud);

        SharedPreferences sp1 = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
        String theme = sp1.getString("Theme", null);

        if(theme != null && theme.equals("Light")){
            about_backgroud.setBackgroundColor(Color.WHITE);
            verApp.setTextColor(Color.BLACK);
            XemGiDo.setTextColor(Color.BLACK);
            checkVerApp.setTextColor(Color.BLACK);
        }else if (theme != null && theme.equals("Dark")){
            about_backgroud.setBackgroundResource(R.drawable.black_wallpaper);
            verApp.setTextColor(Color.WHITE);
            XemGiDo.setTextColor(Color.WHITE);
            checkVerApp.setTextColor(Color.WHITE);
        }

        PackageInfo pInfo = null;
        try {
            pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo.versionName;
        verApp.setText("Phiên bản: " + version);
        Button dieuKhoan = view.findViewById(R.id.dieukhoan);
        dieuKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment selectedFragment = new dieu_khoan();
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
        return view;
    }
}
