package com.example.movieandroidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();

            return true;
        }
    };
}