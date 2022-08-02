package com.asquare.grocerybuddy.views.fragments;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asquare.grocerybuddy.Model.CartList;
import com.asquare.grocerybuddy.R;
import com.asquare.grocerybuddy.RoomDatabase.CartDao;
import com.asquare.grocerybuddy.RoomDatabase.ItemDao;
import com.asquare.grocerybuddy.RoomDatabase.MyRoomDataBase;
import com.asquare.grocerybuddy.adapter.CartListAdapter;
import com.asquare.grocerybuddy.utils.AppConstants;
import com.asquare.grocerybuddy.utils.AppPreference;

import java.util.ArrayList;

public class ProductDetailFragment extends Fragment {

    int minteger = 0;
    private ArrayList<CartList> selectedItemList;
    private TextView tvBuyNow,tvTotalPrice;
    private RecyclerView rvSelectedList;
    private CartListAdapter productListAdapter;
    int totalPrice=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTotalPrice=view.findViewById(R.id.tv_totalPrice);
        tvBuyNow = view.findViewById(R.id.tvBuyNow);
        rvSelectedList = view.findViewById(R.id.rvSelectedList);
        if (getArguments() != null) {
            selectedItemList= (ArrayList<CartList>) getArguments().getSerializable(AppConstants.CART_ITEMS);
        }

        MyRoomDataBase myRoomDataBaseinstance = MyRoomDataBase.getMyRoomInstance(requireContext());
        CartDao cartDao = myRoomDataBaseinstance.cartDao();

        selectedItemList= (ArrayList<CartList>) cartDao.getUserCartItme(AppPreference.getAppPreference(requireContext()).getValue(AppConstants.ID,0));

        rvSelectedList.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        productListAdapter = new CartListAdapter(requireActivity(), selectedItemList);
        rvSelectedList.setAdapter(productListAdapter);
        calculateTotal();
        tvBuyNow.setText("Buy Now ("+String.valueOf(totalPrice)+")");
        tvBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              openDeliveryChargeFragment();
            }
        });

    }

    private void calculateTotal() {
        for (CartList item:selectedItemList) {
             totalPrice+= (item.getItem_cost() * item.getQty());
        }
        tvTotalPrice.setText(String.valueOf(totalPrice));
    }

    private void openCartFragment(String selectedProduct, String itemCount,String amount) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.PRODUCT, selectedProduct);
        bundle.putString(AppConstants.ITEM_COUNT, itemCount);
        bundle.putString(AppConstants.AMOUNT, amount);
        CartFragment cartFragment = new CartFragment();
        cartFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.mainContainer, cartFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void openDeliveryChargeFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.STORE_ID, selectedItemList.get(0).getStoreID());
        bundle.putInt(AppConstants.TOTAL_PRICE,totalPrice);
        DeliveryDetailFragment deliveryDetailFragment = new DeliveryDetailFragment();
        deliveryDetailFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.mainContainer, deliveryDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}