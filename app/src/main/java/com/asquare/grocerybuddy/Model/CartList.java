package com.asquare.grocerybuddy.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_item")
public class CartList {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "item_id")
    private int itemID;
    @ColumnInfo(name = "user_id")
    public int userId;
    @ColumnInfo(name = "item_name")
    public String item_name;
    @ColumnInfo(name = "item_cost")
    public int item_cost;
    @ColumnInfo(name = "item_qty")
    public int item_qty;
    @ColumnInfo(name = "store_id")
    public int storeID;
    @ColumnInfo(name = "is_delivered")
    public boolean isDelivered;


    public CartList(int storeID,int itemID,int userId, String item_name, int item_cost, int item_qty,boolean isDelivered) {
        this.userId = userId;
        this.item_name = item_name;
        this.item_cost = item_cost;
        this.item_qty = item_qty;
        this.itemID=itemID;
        this.storeID=storeID;
        this.isDelivered=isDelivered;
    }


    public boolean selected;

    public int getCartId() {
        return userId;
    }

    public void setCartId(int cartId) {
        this.userId = cartId;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getItem_cost() {
        return item_cost;
    }

    public void setItem_cost(int item_cost) {
        this.item_cost = item_cost;
    }

    public int getQty() {
        return item_qty;
    }

    public void setQty(int qty) {
        this.item_qty = qty;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getItem_qty() {
        return item_qty;
    }

    public void setItem_qty(int item_qty) {
        this.item_qty = item_qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }
}
