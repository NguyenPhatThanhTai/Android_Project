package com.example.movieandroidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import API.APIControllers;
import Category.Category;
import Category.CategoryAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvCategory;
    Thread thread;

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

    @Override
    protected void onResume() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (f instanceof play_movie)
        {
            // do something with f
            ((play_movie) f).test("oke ne");
        }

        super.onResume();
    }

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
                    selectedFragment = new phong_phim();
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