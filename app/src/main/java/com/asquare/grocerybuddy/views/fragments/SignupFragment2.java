package com.asquare.grocerybuddy.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asquare.grocerybuddy.Model.UserModel;
import com.asquare.grocerybuddy.R;
import com.asquare.grocerybuddy.RoomDatabase.MyRoomDataBase;


public class SignupFragment2 extends Fragment {
    private boolean passClick = false;

    private AppCompatTextView tvLogin;
    private AppCompatButton btSignup;
    private AppCompatEditText name, password, email, phone;
    private AppCompatImageView eye;

    private MyRoomDataBase myRoomDataBase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup2, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvLogin = view.findViewById(R.id.tvLogin);
        btSignup = view.findViewById(R.id.btSignup);

        name = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.edPhone);
        password = view.findViewById(R.id.password);
        email = view.findViewById(R.id.email);
        eye = view.findViewById(R.id.ivEye);

        name.setText("Mohit");
        email.setText("mohit@gmail.com");
        password.setText("123456");
        phone.setText("1234567890");


        MyRoomDataBase myRoomDataBaseinstance = MyRoomDataBase.getMyRoomInstance(requireContext());
        myRoomDataBase = myRoomDataBaseinstance;

        init();
    }

    private void init() {


        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passClick) {
                    passClick = false;
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setSelection(password.length());
                } else {
                    passClick = true;
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password.setSelection(password.length());
                }
            }
        });


        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginFragment();
            }
        });

        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.getText().toString().equals("")) {
                    if (!phone.getText().toString().equals("")) {
                        if (!email.getText().toString().equals("")) {
                            if (!password.getText().toString().equals("")) {
                                UserModel user = new UserModel(name.getText().toString(), phone.getText().toString(), email.getText().toString(), password.getText().toString(), "");
                                myRoomDataBase.mainDao().insert(user);

                                openLoginFragment();

                                Toast.makeText(requireContext(), "Registration Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(requireContext(), "please enter password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(requireContext(), "please enter phone", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(requireContext(), "please enter email", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "please enter name", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void openLoginFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment2 loginFragment2 = new LoginFragment2();
        fragmentTransaction.replace(R.id.flMainContainerMain, loginFragment2);
        fragmentTransaction.commit();
    }
}