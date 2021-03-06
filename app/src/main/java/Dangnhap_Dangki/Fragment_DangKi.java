package Dangnhap_Dangki;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieandroidproject.MainActivity;
import com.example.movieandroidproject.R;
import com.example.movieandroidproject.trang_chu;

import java.util.Calendar;

import API.APIControllers;


public class Fragment_DangKi extends Fragment {
    private APIControllers api;
    private Thread thread;
    private DatePickerDialog.OnDateSetListener setListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__dang_ki, container, false);
        ((MainActivity)getActivity()).setBottomNav(5);

        EditText username = view.findViewById(R.id.username);
        EditText password = view.findViewById(R.id.password);
        EditText fullname = view.findViewById(R.id.fullname);
        EditText birthday = view.findViewById(R.id.birthday);
        EditText address = view.findViewById(R.id.address);
        EditText phone = view.findViewById(R.id.phone);
        EditText email = view.findViewById(R.id.email);
        Button dangki = view.findViewById(R.id.dangki);

        api = new APIControllers();

        //Date Picker Dialog
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = year+"/"+month+"/"+day;
                        birthday.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


        dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUserName = username.getText().toString();
                String strPassword = password.getText().toString();
                String strFullname = fullname.getText().toString();
                String strBirthday = birthday.getText().toString();
                String strAddress = address.getText().toString();
                String strPhone = phone.getText().toString();
                String strEmail = email.getText().toString();
                if(strUserName.matches("") || strPassword.matches("") || strFullname.matches("")||
                        strBirthday.matches("") || strAddress.matches("") || strPhone.matches("") ||
                        strEmail.matches("")) {
                    Toast.makeText(getContext(), "Vui l??ng ??i???n ?????y ?????", Toast.LENGTH_SHORT).show();
                }else {
                    Thread thread = new Thread(){
                        @Override
                        public void run() {
                            NguoiDung nguoiDung = api.DangKi(strUserName, strPassword, strFullname,
                                    strBirthday, strAddress, strPhone, strEmail);
                            if(nguoiDung != null) {
                                SharedPreferences sp = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                                SharedPreferences.Editor Ed = sp.edit();
                                Ed.putString("Unm", nguoiDung.getUserId());
                                Ed.commit();
                                showAlert("????ng k?? th??nh c??ng");
                                Fragment selectedFragment = new trang_chu(getActivity());
                                FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

                                manager.beginTransaction()
                                        .add(selectedFragment, "back_stack") // Add this transaction to the back stack (name is an optional name for this back stack state, or null).
                                        .addToBackStack(null)
                                        .setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,
                                                R.anim.enter_right_to_left, R.anim.exit_right_to_left)
                                        .replace(R.id.fragment_container,
                                                selectedFragment).commit();
                            }
                            else {
                                showAlert("C?? l???i x???y ra vui l??ng ki???m tra l???i");
                            }
                        }
                    };
                    thread.start();
                }
            }
        });
        return view;
    }
    private void showAlert(String content){
        getActivity().runOnUiThread(()->{
            Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
        });
    }
}