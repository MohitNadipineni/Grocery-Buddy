package com.asquare.grocerybuddy.views.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asquare.grocerybuddy.Model.CartList;
import com.asquare.grocerybuddy.Model.DeliveredList;
import com.asquare.grocerybuddy.R;
import com.asquare.grocerybuddy.RoomDatabase.CartDao;
import com.asquare.grocerybuddy.RoomDatabase.DeliveredDao;
import com.asquare.grocerybuddy.RoomDatabase.MyRoomDataBase;
import com.asquare.grocerybuddy.utils.AppConstants;
import com.asquare.grocerybuddy.utils.AppPreference;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CartFragment extends Fragment {

    private AppCompatTextView tvDescriptionLabel, tvMrpTottel, tvBottomAmount, tvTotalPrice, tvDeliceryCharges;
    private String selectedIteam = "";
    private String ItemCount = "";

    private String amount = "";
    private int totalPrice, deliveyCharges;
    private ArrayList<CartList> selectedItemList;
    private ArrayList<DeliveredList> deliveredListArrayList;


    private AppCompatButton btnPlaceOrder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        tvMrpTottel = view.findViewById(R.id.tvMrpTottel);
        tvDescriptionLabel = view.findViewById(R.id.tvDescriptionLabel);

        tvBottomAmount = view.findViewById(R.id.tvAmountt);
        tvTotalPrice = view.findViewById(R.id.tvPrice1);
        btnPlaceOrder = view.findViewById(R.id.btnPlaceOrder);
        tvDeliceryCharges = view.findViewById(R.id.tvDeliveryPrice);

        MyRoomDataBase myRoomDataBaseinstance = MyRoomDataBase.getMyRoomInstance(requireContext());
        CartDao cartDao = myRoomDataBaseinstance.cartDao();
        DeliveredDao deliveredDao = myRoomDataBaseinstance.deliveredDao();


        if (getArguments() != null) {

            totalPrice = getArguments().getInt(AppConstants.TOTAL_PRICE);
            deliveyCharges = (int) getArguments().getDouble(AppConstants.DELIVERY_AMOUNT);


            String finalAmount = String.valueOf(totalPrice + deliveyCharges);
            tvBottomAmount.setText("$" + finalAmount);

            tvTotalPrice.setText("$" + totalPrice);
            tvDeliceryCharges.setText("$" + deliveyCharges);

        }

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                selectedItemList = (ArrayList<CartList>) cartDao.getUserCartItme(AppPreference.getAppPreference(requireContext()).getValue(AppConstants.ID, 0));
                List<DeliveredList> B = selectedItemList.stream()
                        .map(developer -> new DeliveredList(developer.getStoreID(), developer.getItemID(), developer.getUserId(), developer.getItem_name(), developer.getItem_cost(), developer.getItem_qty(), developer.isDelivered()))
                        .collect(Collectors.toList());

                deliveredDao.insertAll(B);
                cartDao.deleteAllCart();
                requireContext().sendBroadcast(new Intent("MyAction"));
                Toast.makeText(requireContext(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
                openHomeFragment();

            }
        });


        init();
    }

    private void init() {
    }

    private void openHomeFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment paymentFragment = new HomeFragment();
        fragmentTransaction.replace(R.id.mainContainer, paymentFragment);
        fragmentTransaction.commit();
    }
}