package com.example.movieandroidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.net.InetAddress;

import API.APIControllers;
import Dangnhap_Dangki.Dangnhap_Dangki;
import Dangnhap_Dangki.Dangnhap_DangkiAdapter;
import rap_phim.phong_phim;
import rap_phim.phong_phim_test;
import rap_phim.sanh_phim;

public class MainActivity extends AppCompatActivity {
    private int seekVideo;
    private Fragment f;
    MeowBottomNavigation bottomNavigation;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.meow_bottom);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.menu));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.loupe));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.house));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ticket));
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.profile));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

            }
        });

        bottomNavigation.setCount(5,"10");
        bottomNavigation.show(3, true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Fragment selectedFragment = null;
                String tagName = "";
                switch(item.getId()){
                    case 1:
                        tagName = "Category";
                        selectedFragment = new the_loai();
                        break;
                    case 3:
                        tagName = "Home";
                        selectedFragment = new trang_chu(MainActivity.this);
                        break;
                    case  4:
                        tagName = "Movie";
                        SharedPreferences sp1 = getSharedPreferences("Login", Context.MODE_PRIVATE);
                        String userId = sp1.getString("Unm", null);
                        if(userId != null){
                            selectedFragment = new phong_phim_test(userId);
                        }
                        else {
                            selectedFragment = new Dangnhap_Dangki();
                        }
                        break;
                    case 5:
                        tagName = "Setting";
                        selectedFragment = new Dangnhap_Dangki();
                        break;
                    case 2:
                        tagName = "Search";
                        selectedFragment = new tim_kiem();
                        break;
                }
                FragmentManager manager = getSupportFragmentManager();

                manager.beginTransaction()
                        .add(selectedFragment, tagName) // Add this transaction to the back stack (name is an optional name for this back stack state, or null).
                        .addToBackStack(null)
                        .replace(R.id.fragment_container,
                                selectedFragment).commit();
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
//                Toast.makeText(getApplicationContext(), "re", Toast.LENGTH_SHORT).show();
            }
        });


//        BottomNavigationView bottomNAV = findViewById(R.id.bottom_nav);
//        bottomNAV.setOnNavigationItemSelectedListener(navLis);
//        bottomNAV.getMenu().findItem(R.id.nav_home).setChecked(true);

        if(isNetworkConnected()){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new trang_chu(MainActivity.this)).commit();
            Thread thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    APIControllers api = new APIControllers();
                    try {
                        PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                        String version = pInfo.versionName;
                        String getVersion = api.checkVersionUpdate(version);
                        if (getVersion != null){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showDialogUpdateApp(getVersion);
                                }
                            });
                        }
                        System.out.println("Version hiện tại là: " + version);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }
        else {
            new AlertDialog.Builder(this)
                    .setTitle("Opps!")
                    .setMessage("Hình như có vấn đề gì với mạng nhà bạn, vui lòng xem xét!!!")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
    
    private void showDialogUpdateApp(String urlUpdate){
        new AlertDialog.Builder(this)
                .setTitle("Cập nhật!")
                .setMessage("Bản cập nhật mới đã ra lò, hãy tải ngay để nhận những thay đổi mới nhất!!!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlUpdate));
                        startActivity(browserIntent);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    //Bắt sự kiện nhấn nút lui về của dt
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

    public void setBottomNav(int number){
        bottomNavigation.show(number, true);
    }

//    private BottomNavigationView.OnNavigationItemSelectedListener navLis = new BottomNavigationView.OnNavigationItemSelectedListener(){
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item){
//            Fragment selectedFragment = null;
//            String tagName = "";
//
//            switch(item.getItemId()){
//                case R.id.nav_category:
//                    tagName = "Category";
//                    selectedFragment = new the_loai();
//                    break;
//                case R.id.nav_home:
//                    tagName = "Home";
//                    selectedFragment = new trang_chu(MainActivity.this);
//                    break;
//                case  R.id.nav_film_room:
//                    tagName = "Theater";
//                    SharedPreferences sp1 = getSharedPreferences("Login", Context.MODE_PRIVATE);
//                    String userId = sp1.getString("Unm", null);
//                    if(userId != null){
//                        selectedFragment = new phong_phim_test(userId);
//                    }
//                    else {
//                        selectedFragment = new Dangnhap_Dangki();
//                    }
//                    break;
//                case R.id.nav_setting:
//                    tagName = "User";
//                    selectedFragment = new Dangnhap_Dangki();
//                    break;
//                case R.id.nav_timkiem:
//                    tagName = "Search";
//                    selectedFragment = new tim_kiem();
//                    break;
//            }
//
//            FragmentManager manager = getSupportFragmentManager();
//
//            manager.beginTransaction()
//                    .add(selectedFragment, tagName) // Add this transaction to the back stack (name is an optional name for this back stack state, or null).
//                    .addToBackStack(null)
//                    .setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,
//                            R.anim.enter_right_to_left, R.anim.exit_right_to_left)
//                    .replace(R.id.fragment_container,
//                    selectedFragment).commit();
//
//            return true;
//        }
//    };
}