package Dangnhap_Dangki;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movieandroidproject.R;
import com.google.android.material.tabs.TabLayout;

import API.APIControllers;

public class Dangnhap_Dangki extends Fragment {


    TabLayout tabLayout;
    ViewPager2 viewPager2;
    Dangnhap_DangkiAdapter adapter;
    private FragmentActivity myContext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dangnhap_dangki, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager2 = view.findViewById(R.id.view_pager2);
        myContext=(FragmentActivity) getActivity();
        FragmentManager fm = myContext.getSupportFragmentManager();
        adapter = new Dangnhap_DangkiAdapter(fm, getLifecycle());
        viewPager2.setAdapter(adapter);
        tabLayout.addTab(tabLayout.newTab().setText("Đăng nhập"));
        tabLayout.addTab(tabLayout.newTab().setText("Đăng kí"));
        EditText username = view.findViewById(R.id.username);
        EditText password = view.findViewById(R.id.password);
        Button dangnhap = view.findViewById(R.id.dangnhap);
        APIControllers api = new APIControllers();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                dangnhap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        api.DangNhap(username.getText().toString(), password.getText().toString());
                    }
                });
            }
        });
        thread.start();

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

        return view;
    }
}
