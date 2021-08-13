package Dangnhap_Dangki;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.movieandroidproject.R;

import API.APIControllers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_DangNhap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_DangNhap extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_DangNhap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_DangNhap.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_DangNhap newInstance(String param1, String param2) {
        Fragment_DangNhap fragment = new Fragment_DangNhap();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__dang_nhap, container, false);
        EditText username = view.findViewById(R.id.username);
        EditText password = view.findViewById(R.id.password);
        Button dangnhap = view.findViewById(R.id.dangnhap);
        APIControllers api = new APIControllers();
        dangnhap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            NguoiDung nguoiDung = api.DangNhap(username.getText().toString(), password.getText().toString());
                            System.out.println(nguoiDung.getUserId());
                        }
                    };
                    thread.start();
                }
            });
        // Inflate the layout for this fragment
        return view;
    }
}