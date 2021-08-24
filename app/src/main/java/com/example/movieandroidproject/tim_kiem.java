package com.example.movieandroidproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import API.APIControllers;
import tim_kiem.*;

public class tim_kiem extends Fragment implements IOnBackPressed{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tim_kiem, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rcv_timkiem);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        ImageButton timkiem = view.findViewById(R.id.btn_timkiem);
        TextView tim = view.findViewById(R.id.txt_timkiem);
        timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        APIControllers Api = new APIControllers();
                        List<TimKiem> list = Api.getTimKiem(tim.getText().toString());
                        if(list != null)
                        {
                            TimKiem_Adapter adapter = new TimKiem_Adapter(list, getContext());
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView.setAdapter(adapter);
                                }
                            });
                        }
                        else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(),"Không tìm thấy phim !",Toast.LENGTH_SHORT );
                                }
                            });
                        }
                    }
                };
                thread.start();
            }
        });
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