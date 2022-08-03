package com.asquare.grocerybuddy.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.asquare.grocerybuddy.Model.CartList;
import com.asquare.grocerybuddy.Model.ItemList;
import com.asquare.grocerybuddy.R;
import com.asquare.grocerybuddy.RoomDatabase.ItemDao;
import com.asquare.grocerybuddy.RoomDatabase.MyRoomDataBase;
import com.asquare.grocerybuddy.adapter.ProductListAdapter;
import com.asquare.grocerybuddy.utils.AppConstants;

import java.util.ArrayList;


public class ProductChooseFragment extends Fragment {

    //private String[] groceryList = {"Bakery and Bread", "Meat and Seafood", "Pasta and Rice", "Oils, Sauces, Salad Dressings, and Condiments", "Cereals and Breakfast Foods", "Soups and Canned Goods"};
    //private String[] amountList = {"10", "30", "60", "50", "30", "40"};
    private ProductListAdapter productListListAdapter;
    private RecyclerView rvProduct;
    private AppCompatButton tvSaveCart;
    private ArrayList<ItemList> itemList;
    private int storeId;
    private ArrayList<CartList> selectedItemList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_choose, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            storeId = getArguments().getInt(AppConstants.STORE_ID, 0);
        }
        rvProduct = view.findViewById(R.id.rvProductList);
        tvSaveCart = view.findViewById(R.id.btnSaveCart);
        rvProduct.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        MyRoomDataBase myRoomDataBaseinstance = MyRoomDataBase.getMyRoomInstance(requireContext());
        ItemDao itemDao = myRoomDataBaseinstance.itemDao();
        itemList = (ArrayList<ItemList>) itemDao.getItemList(storeId);

        productListListAdapter = new ProductListAdapter(requireActivity(), itemList, new ProductListAdapter.OnClickMenuListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        rvProduct.setAdapter(productListListAdapter);
        tvSaveCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSelectedItemList();
                openCartFragment(selectedItemList);
            }
        });

    }

    private void getSelectedItemList() {
        /*selectedItemList = new ArrayList<CartList>();
        for (int i = 0; i <= itemList.size() - 1; i++) {
            if (itemList.get(i).getSelected()) {
                selectedItemList.add(new CartList(itemList.get(i).getItemId(), itemList.get(i).getItemName(), itemList.get(i).getItemCost(), 1));
            }
        }*/
    }

    private void openCartFragment(ArrayList<CartList> selectedItemList) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.CART_ITEMS, selectedItemList);
        ProductDetailFragment productDetailFragmentFragment = new ProductDetailFragment();
        productDetailFragmentFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.mainContainer, productDetailFragmentFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}