package com.asquare.grocerybuddy.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.asquare.grocerybuddy.Model.ItemList;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM item_list where store_id=:storeId")
    List<ItemList> getItemList(int storeId);

    @Query("SELECT * FROM item_list")
    List<ItemList> getAllItemList();

    @Insert
    void insert(ItemList item);

}
