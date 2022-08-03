package com.asquare.grocerybuddy.views.fragments;

import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.asquare.grocerybuddy.Model.UserModel;
import com.asquare.grocerybuddy.R;
import com.asquare.grocerybuddy.RoomDatabase.MyRoomDataBase;
import com.asquare.grocerybuddy.RoomDatabase.UserDao;
import com.asquare.grocerybuddy.utils.AppConstants;
import com.asquare.grocerybuddy.utils.AppPreference;
import com.asquare.grocerybuddy.views.activities.DashboardActivity;

import java.util.ArrayList;
import java.util.List;


public class LoginFragment2 extends Fragment {
    private boolean passClick = false;
    private boolean isRememberme = false;


    private CheckBox checkBox;
    private AppCompatTextView forgotPAss, btSignUp;
    private AppCompatButton btLogin;
    private AppCompatEditText password, email;
    private AppCompatImageView eye;

    private MyRoomDataBase myRoomDataBase;

    UserDao userDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkBox = view.findViewById(R.id.rememberMe);
        btLogin = view.findViewById(R.id.btLogin);
        btSignUp = view.findViewById(R.id.tvSignup);
        password = view.findViewById(R.id.password);
        email = view.findViewById(R.id.email);
        eye = view.findViewById(R.id.ivEye);

        email.setText("mohit@gmail.com");
        password.setText("123456");


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

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    isRememberme = true;
                }

            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!email.getText().toString().equals("")){
                    if (!password.getText().toString().equals("")){

                        MyRoomDataBase myRoomDataBaseinstance = MyRoomDataBase.getMyRoomInstance(requireContext());
                        UserDao userDao= myRoomDataBaseinstance.mainDao();

                        UserModel userList= userDao.getUser(email.getText().toString().trim(), password.getText().toString().trim());

                        if (userList != null) {
                            Toast.makeText(requireContext(), "Login successfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(requireContext(), DashboardActivity.class);
                            startActivity(intent);
                            requireActivity().finish();

                            if(isRememberme){
                                AppPreference.getAppPreference(requireContext()).save(AppConstants.SESSION, true);
                            }
                            AppPreference.getAppPreference(requireContext()).save(AppConstants.NAME,userList.getName());
                            AppPreference.getAppPreference(requireContext()).save(AppConstants.ID,userList.getID());
                            AppPreference.getAppPreference(requireContext()).save(AppConstants.PHONE,userList.getPhone());
                            AppPreference.getAppPreference(requireContext()).save(AppConstants.EMAIL,userList.getEmail());
                            AppPreference.getAppPreference(requireContext()).save(AppConstants.SESSION,true);

                        } else {
                            Toast.makeText(requireContext(), "Unregistered user, or incorrect", Toast.LENGTH_SHORT).show();
                        }


                    }else{
                        Toast.makeText(requireContext(), "please enter password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(requireContext(), "please enter email", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpFragment();
            }
        });


    }

    private void openSignUpFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SignupFragment2 signupFragment = new SignupFragment2();
        fragmentTransaction.replace(R.id.flMainContainerMain, signupFragment);
        fragmentTransaction.commit();
    }
}