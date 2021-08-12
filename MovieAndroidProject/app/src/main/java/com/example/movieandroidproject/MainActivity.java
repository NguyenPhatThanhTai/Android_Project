package com.example.movieandroidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import rap_phim.sanh_phim;

public class MainActivity extends AppCompatActivity {
    private int seekVideo;
    private Fragment f;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNAV = findViewById(R.id.bottom_nav);
        bottomNAV.setOnNavigationItemSelectedListener(navLis);
        bottomNAV.getMenu().findItem(R.id.nav_home).setChecked(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new trang_chu()).commit();
    }

    //Bắt sự kiện nhấn nút lui về của dt
    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }
    }

//    @Override
//    protected void onResume() {
//        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//        if (f instanceof play_movie)
//        {
//            // do something with f
//            ((play_movie) f).test("Trở lại");
//        }
//
//        super.onResume();
//    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//        getSupportFragmentManager().putFragment(outState, "myfragment", f);
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void onRestoreInstanceState(Bundle inState){
//        f = getSupportFragmentManager().getFragment(inState, "myfragment");
//    }

    private BottomNavigationView.OnNavigationItemSelectedListener navLis = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item){
            Fragment selectedFragment = null;

            switch(item.getItemId()){
                case R.id.nav_category:
                    selectedFragment = new the_loai();
                    break;
                case R.id.nav_home:
                    selectedFragment = new trang_chu();
                    break;
                case  R.id.nav_film_room:
                    selectedFragment = new sanh_phim();
                    break;
            }

            FragmentManager manager = getSupportFragmentManager();

            manager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,
                            R.anim.enter_right_to_left, R.anim.exit_right_to_left)
                    .replace(R.id.fragment_container,
                    selectedFragment).commit();

            return true;
        }
    };
}