package com.asquare.grocerybuddy.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.asquare.grocerybuddy.Model.CartList;
import com.asquare.grocerybuddy.Model.StoreDetailsModel;
import com.asquare.grocerybuddy.Model.UserModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface StoreDao {
    @Insert
    void insert(StoreDetailsModel str
    );

    @Query("SELECT * FROM store_info ")
    List<StoreDetailsModel> getStore();

    @Query("SELECT * FROM store_info where storeId=:storeId")
    StoreDetailsModel getLatLng(int storeId);
}
