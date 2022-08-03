package com.asquare.grocerybuddy.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asquare.grocerybuddy.Model.ItemList;
import com.asquare.grocerybuddy.Model.StoreDetailsModel;
import com.asquare.grocerybuddy.R;
import com.asquare.grocerybuddy.RoomDatabase.ItemDao;
import com.asquare.grocerybuddy.RoomDatabase.MyRoomDataBase;
import com.asquare.grocerybuddy.RoomDatabase.StoreDao;
import com.asquare.grocerybuddy.adapter.ShopsListAdapter;
import com.asquare.grocerybuddy.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private int[] homeItems = {R.drawable.fruits, R.drawable.vp3, R.drawable.vp2, R.drawable.vp4,};

    private RecyclerView rvWeekly;

    ArrayList<StoreDetailsModel> storeDetailsModelArrayList = new ArrayList<>();
    ArrayList<StoreDetailsModel> storeDetailsModelArrayListFromDB = new ArrayList<>();

    ArrayList<ItemList> itemListArrayList = new ArrayList<>();
    StoreDao storeDao;


    private String[] shopsList = {"kaybee mercy african grocery store", "The kitchen Table", "Food 4 less grocery stores", "Nesters Food Market", "Walmart", "Costco Wholesale"};
    private Double[] lat = {54.116391,39.334298,54.116391,54.116391,43.651890,54.116391};
    private Double[] lng = {-122.823661,-137.966311,-122.823661,-122.823661,-79.381710,-122.823661};
    private int[] zipcode={66777,66777,66777,66777,66777,66777};
    private ShopsListAdapter shopsListAdapter;

    private MyRoomDataBase myRoomDataBase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvWeekly = view.findViewById(R.id.rvWeekly);
        rvWeekly.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        init();
    }

    private void init() {

        MyRoomDataBase myRoomDataBaseinstance = MyRoomDataBase.getMyRoomInstance(requireContext());
        storeDao = myRoomDataBaseinstance.storeDao();
        ItemDao itemDao = myRoomDataBaseinstance.itemDao();

        if (itemDao.getAllItemList().size() == 0) {
            for (int i = 1; i <= 6; i++) {
                ItemList itemList = new ItemList(i, "Bakery and Bread", 10, false);
                itemListArrayList.add(itemList);
                itemDao.insert(itemList);

                ItemList itemList1 = new ItemList(i, "Meat and Seafood", 30, false);
                itemListArrayList.add(itemList1);
                itemDao.insert(itemList1);

                ItemList itemList2 = new ItemList(i, "Pasta and Rice", 60, false);
                itemListArrayList.add(itemList2);
                itemDao.insert(itemList2);

                ItemList itemList3 = new ItemList(i, "Oils, Sauces, Salad Dressings, and Condiments", 50, false);
                itemListArrayList.add(itemList3);
                itemDao.insert(itemList3);

                ItemList itemList4 = new ItemList(i, "Cereals and Breakfast Foods", 30, false);
                itemListArrayList.add(itemList4);
                itemDao.insert(itemList4);

                ItemList itemList5 = new ItemList(i, "Soups and Canned Goods", 40, false);
                itemListArrayList.add(itemList5);
                itemDao.insert(itemList5);
            }
        }

        if (storeDao.getStore().size() == 0) {
            for (int i = 0; i < shopsList.length; i++) {
                StoreDetailsModel storeDetailsModel = new StoreDetailsModel(shopsList[i], lat[i], lng[i],zipcode[i]);
                storeDetailsModelArrayList.add(storeDetailsModel);
                storeDao.insert(storeDetailsModel);
            }
        }


      /*  StoreDetailsModel storeDetailsModel=new StoreDetailsModel(1,"Loblaws Chain Stores",29.6769,29.6769,itemListArrayList);
        storeDetailsModelArrayList.add(storeDetailsModel);
        storeDetailsModelArrayList.add(storeDetailsModel);
        storeDetailsModelArrayList.add(storeDetailsModel);
        storeDetailsModelArrayList.add(storeDetailsModel);*/
        /*StoreDetailsModel storeDetailsModel1=new StoreDetailsModel(2,"Sobeys"),);
        StoreDetailsModel storeDetailsModel2=new StoreDetailsModel(3,"Metro"),);
        StoreDetailsModel storeDetailsModel3=new StoreDetailsModel(4,"Real Canadian Superstore"),);*/
        //storeDao.getStoreList()
        shopsListAdapter = new ShopsListAdapter(requireActivity(), (ArrayList<StoreDetailsModel>) storeDao.getStore(), new ShopsListAdapter.OnClickMenuListener() {
            @Override
            public void onItemClick(int position) {
                openProductChooseFragment(position);
            }
        });

        rvWeekly.setAdapter(shopsListAdapter);

    }

    private void openProductChooseFragment(int position) {
        storeDetailsModelArrayListFromDB = (ArrayList<StoreDetailsModel>) storeDao.getStore();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.STORE_ID, storeDetailsModelArrayListFromDB.get(position).getStoreId());
        ProductChooseFragment productChooseFragment = new ProductChooseFragment();
        productChooseFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.mainContainer, productChooseFragment);
        fragmentTransaction.commit();
    }
}