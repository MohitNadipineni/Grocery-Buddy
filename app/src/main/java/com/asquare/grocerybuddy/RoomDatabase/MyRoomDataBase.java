package com.asquare.grocerybuddy.RoomDatabase;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;

import androidx.room.Database;

import com.asquare.grocerybuddy.Model.CartList;
import com.asquare.grocerybuddy.Model.DeliveredList;
import com.asquare.grocerybuddy.Model.ItemList;
import com.asquare.grocerybuddy.Model.StoreDetailsModel;
import com.asquare.grocerybuddy.Model.UserModel;

@Database(entities = {UserModel.class, StoreDetailsModel.class, ItemList.class, CartList.class, DeliveredList.class}, version = 4, exportSchema = false)
public abstract class MyRoomDataBase extends RoomDatabase {

    public abstract UserDao mainDao();
    public abstract StoreDao storeDao();
    public abstract ItemDao itemDao();
    public abstract CartDao cartDao();
    public abstract DeliveredDao deliveredDao();

    private static MyRoomDataBase myRoomInstance;
    public static String DATABASE_NAME = "Contact_info";


    public static MyRoomDataBase getMyRoomInstance(Context context) {
        if (myRoomInstance == null) {
            myRoomInstance = Room.databaseBuilder(context.getApplicationContext(), MyRoomDataBase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return myRoomInstance;
    }

}