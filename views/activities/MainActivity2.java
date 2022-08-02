package com.asquare.grocerybuddy.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.asquare.grocerybuddy.R;
import com.asquare.grocerybuddy.views.fragments.LoginFragment2;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        openOnBoardingFragment();
    }


    private void openOnBoardingFragment() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment2 loginFragment = new LoginFragment2();
        fragmentTransaction.replace(R.id.flMainContainerMain, loginFragment);
        fragmentTransaction.commit();
    }


}