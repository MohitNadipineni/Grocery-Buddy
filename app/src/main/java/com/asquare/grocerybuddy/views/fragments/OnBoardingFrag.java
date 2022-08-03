package com.asquare.grocerybuddy.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.asquare.grocerybuddy.R;


public class OnBoardingFrag extends Fragment {
    private Button btLogin,btSignUp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_on_boarding2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btLogin=view.findViewById(R.id.btLogin);
        btSignUp=view.findViewById(R.id.btSignUp);

        init();
    }

    private void init() {

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginFragment();
            }
        });

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpFragment();
            }
        });

    }


    private void openLoginFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment2 loginFragment = new LoginFragment2();
        fragmentTransaction.replace(R.id.flMainContainerMain, loginFragment);
        fragmentTransaction.commit();
    }

    private void openSignUpFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SignupFragment2 signupFragment = new SignupFragment2();
        fragmentTransaction.replace(R.id.flMainContainerMain, signupFragment);
        fragmentTransaction.commit();
    }
}