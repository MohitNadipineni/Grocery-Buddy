package com.asquare.grocerybuddy.views.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asquare.grocerybuddy.Model.CartList;
import com.asquare.grocerybuddy.R;
import com.asquare.grocerybuddy.RoomDatabase.CartDao;
import com.asquare.grocerybuddy.RoomDatabase.MyRoomDataBase;
import com.asquare.grocerybuddy.utils.AppConstants;
import com.asquare.grocerybuddy.utils.AppPreference;
import com.asquare.grocerybuddy.views.fragments.DeliveryDetailFragment;
import com.asquare.grocerybuddy.views.fragments.HomeFragment;
import com.asquare.grocerybuddy.views.fragments.MapFragment;
import com.asquare.grocerybuddy.views.fragments.OrderMyGroceryFragment;
import com.asquare.grocerybuddy.views.fragments.PaymentFragment;
import com.asquare.grocerybuddy.views.fragments.ProductDetailFragment;
import com.asquare.grocerybuddy.views.fragments.ProfileFragment;
import com.asquare.grocerybuddy.views.fragments.SignupFragment2;
import com.asquare.grocerybuddy.adapter.NavItemAdapter;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private AppCompatTextView tvTopTitle;
    private AppCompatImageView ivMenu;
    private RecyclerView rvIteams;

    private NavItemAdapter navItemAdapter;
    private DrawerLayout drawerLayout;
    private ImageView ivCart;
    private TextView tvCartBadge;
    private MyRoomDataBase myRoomDataBaseinstance;
    private CartDao cartDao;

    private BroadcastReceiver broadcastReceiver;

    private String[] navIteams = {"Home", "Profile", "LogOut"};

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvTopTitle = findViewById(R.id.tvtollbarTitle);
        ivMenu = findViewById(R.id.ivMenu);

        drawerLayout = findViewById(R.id.drawerlayout);

        rvIteams = findViewById(R.id.rvNavIteam);
        ivCart = findViewById(R.id.iv_cart);
        tvCartBadge = findViewById(R.id.tv_cart_badge);

        openHomeFragment();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int count = cartDao.getCount(AppPreference.getAppPreference(DashboardActivity.this).getValue(AppConstants.ID, 0));

                if (count >= 0) {
                    tvCartBadge.setVisibility(View.VISIBLE);
                    tvCartBadge.setText(String.valueOf(count));
                }
            }
        };

        IntentFilter filter = new IntentFilter("MyAction");
        registerReceiver(broadcastReceiver,filter);

        navItemAdapter = new NavItemAdapter(this, navIteams, new NavItemAdapter.OnClickMenuListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(int position) {
                handleNavClick(position);
            }
        });

        rvIteams.setAdapter(navItemAdapter);

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCloseDrawer();
            }
        });

        myRoomDataBaseinstance = MyRoomDataBase.getMyRoomInstance(this);
        cartDao = myRoomDataBaseinstance.cartDao();

        int count = cartDao.getCount(AppPreference.getAppPreference(this).getValue(AppConstants.ID, 0));

        if (count > 0) {
            tvCartBadge.setVisibility(View.VISIBLE);
            tvCartBadge.setText(String.valueOf(count));
        }

        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCartFragment();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void handleNavClick(int position) {

        switch (position) {
            case 0:
                openCloseDrawer();
                openHomeFragment();
                break;
            case 1:
                openCloseDrawer();
                openProfileFragment();
                break;
            case 2:
               /* openCloseDrawer();
                openPaymentFragment();*/
                break;
            case 3:
               /* openCloseDrawer();
                openOrderFragment();*/
                break;
            case 4:
               /* openCloseDrawer();
                openDeliveryDetailsFragment();*/
                break;
            case 5:
                openCloseDrawer();
                break;

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void openHomeFragment() {
        tvTopTitle.setText("Grocery buddy");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MapFragment homeFragment = new MapFragment();
        fragmentTransaction.replace(R.id.mainContainer, homeFragment);
        fragmentTransaction.commit();
    }

    private void openProfileFragment() {
        tvTopTitle.setText("Profile");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ProfileFragment profileFragment = new ProfileFragment();
        fragmentTransaction.replace(R.id.mainContainer, profileFragment);
        fragmentTransaction.commit();
    }

    private void openPaymentFragment() {
        tvTopTitle.setText("Payment");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PaymentFragment paymentFragment = new PaymentFragment();
        fragmentTransaction.replace(R.id.mainContainer, paymentFragment);
        fragmentTransaction.commit();
    }


    private void openOrderFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        OrderMyGroceryFragment orderMyGroceryFragment = new OrderMyGroceryFragment();
        fragmentTransaction.replace(R.id.mainContainer, orderMyGroceryFragment);
        fragmentTransaction.commit();
    }


    private void openDeliveryDetailsFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DeliveryDetailFragment deliveryDetailFragment = new DeliveryDetailFragment();
        fragmentTransaction.replace(R.id.mainContainer, deliveryDetailFragment);
        fragmentTransaction.commit();
    }


    private void openCloseDrawer() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private void openCartFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        ProductDetailFragment productDetailFragmentFragment = new ProductDetailFragment();
        fragmentTransaction.replace(R.id.mainContainer, productDetailFragmentFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}