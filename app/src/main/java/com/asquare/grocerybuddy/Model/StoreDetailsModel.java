package com.asquare.grocerybuddy.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "store_info")
public class StoreDetailsModel {

    @PrimaryKey(autoGenerate = true)
    public int storeId;

    @ColumnInfo(name = "store_name")
    public String storeName;

    @ColumnInfo(name = "latitude")
    public double latitude;

    @ColumnInfo(name = "longitude")
    public double lng;

    @ColumnInfo(name = "zipcode")
    public int zipcode;


    //foriegn for Item


    public StoreDetailsModel(String storeName, double latitude, double lng,int zipcode) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.latitude = latitude;
        this.lng = lng;
        this.zipcode=zipcode;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }


    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
