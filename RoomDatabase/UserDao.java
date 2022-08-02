package com.asquare.grocerybuddy.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.asquare.grocerybuddy.Model.UserModel;

import java.util.List;


@Dao
public interface UserDao {
    @Query("SELECT * FROM user_info where email= :mail and password= :password")
    UserModel getUser(String mail, String password);

    @Insert
    void insert(UserModel user);

    //todo: update Data
    @Query("UPDATE user_info SET name = :name,lastName= :lastName, image = :image Where ID = :sID")
    int updateItem(int sID,String name,String lastName,String image);

    /*//todo:get all the data from database
    @Query("SELECT * FROM Stores")
    List<GroceryStores> getAllContact();*/

}
