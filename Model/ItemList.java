package com.asquare.grocerybuddy.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "item_list")
public class ItemList implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int itemId;
    @ColumnInfo(name = "store_id")
    private int storeId;
    @ColumnInfo(name = "item_name")
    private String itemName;
    @ColumnInfo(name = "item_cost")
    private int itemCost;

    private int itemQuantity;

    public Boolean isSelected;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemCost() {
        return itemCost;
    }

    public ItemList(int storeId, String itemName, int itemCost, Boolean isSelected) {
        this.storeId=storeId;
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.isSelected = isSelected;
    }

    public void setItemCost(int itemCost) {
        this.itemCost = itemCost;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
