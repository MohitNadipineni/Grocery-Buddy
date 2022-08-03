package com.asquare.grocerybuddy.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.asquare.grocerybuddy.Model.CartList;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * FROM cart_item")
    CartList getCartList();

    @Query("SELECT * FROM cart_item where user_id=:userId and item_id=:itemID")
    CartList getCartItme(int userId,int itemID);

    @Query("SELECT * FROM cart_item where user_id=:userId")
    List<CartList> getUserCartItme(int userId);

    @Insert
    void insert(CartList cart);

    @Query("SELECT COUNT(id) FROM cart_item where user_id=:userID")
    int getCount(int userID);

    @Query("DELETE FROM cart_item")
     void deleteAllCart();
}
