package Dangnhap_Dangki;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movieandroidproject.IOnBackPressed;
import com.example.movieandroidproject.R;
import com.example.movieandroidproject.trang_chu;
import com.google.android.material.tabs.TabLayout;

import API.APIControllers;

public class Dangnhap_Dangki extends Fragment implements IOnBackPressed {


    TabLayout tabLayout;
    ViewPager2 viewPager2;
    Dangnhap_DangkiAdapter adapter;
    private FragmentActivity myContext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dangnhap_dangki, container, false);
        SharedPreferences sp1=getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String unm=sp1.getString("Unm", null);
        if(unm != null){
            System.out.println("================"+unm);
            Fragment selectedFragment = new Fragment_NguoiDung(unm);
            FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,
                            R.anim.enter_right_to_left, R.anim.exit_right_to_left)
                    .replace(R.id.fragment_container,
                            selectedFragment).commit();
        }
        else {
            tabLayout = view.findViewById(R.id.tab_layout);
            viewPager2 = view.findViewById(R.id.view_pager2);
            myContext=(FragmentActivity) getActivity();
            FragmentManager fm = myContext.getSupportFragmentManager();
            adapter = new Dangnhap_DangkiAdapter(fm, getLifecycle());
            viewPager2.setAdapter(adapter);
            tabLayout.addTab(tabLayout.newTab().setText("Đăng nhập"));
            tabLayout.addTab(tabLayout.newTab().setText("Đăng kí"));


            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager2.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                }
            });

        }

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
