package com.asquare.grocerybuddy.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.asquare.grocerybuddy.R;
import com.asquare.grocerybuddy.utils.AppConstants;
import com.asquare.grocerybuddy.utils.AppPreference;

public class SplashActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

        init();
    }

    private void init(){

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AppPreference.getAppPreference(SplashActivity2.this).getValue(AppConstants.SESSION, false)) {
                    Intent intent=new Intent(SplashActivity2.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent=new Intent(SplashActivity2.this, MainActivity2.class);
                    startActivity(intent);
                    finish();
                }



            }
        },2000);
    }
}