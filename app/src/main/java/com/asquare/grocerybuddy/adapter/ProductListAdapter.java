package com.asquare.grocerybuddy.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.asquare.grocerybuddy.Model.CartList;
import com.asquare.grocerybuddy.Model.ItemList;
import com.asquare.grocerybuddy.R;
import com.asquare.grocerybuddy.RoomDatabase.CartDao;
import com.asquare.grocerybuddy.RoomDatabase.MyRoomDataBase;
import com.asquare.grocerybuddy.utils.AppConstants;
import com.asquare.grocerybuddy.utils.AppPreference;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    Activity activity;
    ArrayList<ItemList> dataList;
    int minteger = 0;

    public ProductListAdapter(Activity activity, ArrayList<ItemList> dataList, OnClickMenuListener onClickMenuListener) {
        this.activity = activity;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.productlist_item_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MyRoomDataBase myRoomDataBaseinstance = MyRoomDataBase.getMyRoomInstance(activity);
        CartDao cartDao = myRoomDataBaseinstance.cartDao();

        CartList cartList = cartDao.getCartItme(AppPreference.getAppPreference(activity).getValue(AppConstants.ID, 0), dataList.get(position).getItemId());
        if (cartList != null) {
            holder.btAddToCart.setVisibility(View.GONE);
        } else {
            holder.btAddToCart.setVisibility(View.VISIBLE);
        }

        holder.tvItemName.setText(dataList.get(position).getItemName());
        dataList.get(position).setItemQuantity(1);

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList.get(position).setItemQuantity(dataList.get(position).getItemQuantity() + 1);
                holder.tvQty.setText(String.valueOf(dataList.get(position).getItemQuantity()));
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataList.get(position).getItemQuantity() != 0) {
                    dataList.get(position).setItemQuantity(dataList.get(position).getItemQuantity() - 1);
                    minteger = minteger - 1;
                    holder.tvQty.setText(String.valueOf(dataList.get(position).getItemQuantity()));
                }
            }
        });

        if (minteger == 0) {
            holder.tvItemCost.setText(String.valueOf(dataList.get(position).getItemCost()));
        } else {
            int costQty = dataList.get(position).getItemCost() * minteger;
            holder.tvItemCost.setText(String.valueOf(costQty));
        }

        holder.tvItemCost.setText(String.valueOf("$" + dataList.get(position).getItemCost()));

        if (dataList.get(position).getSelected()) {
            holder.cbKey.setChecked(true);
        }

        holder.cbKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataList.get(position).setSelected(holder.cbKey.isChecked());
                holder.cbKey.setChecked(holder.cbKey.isChecked());
            }
        });

        holder.btAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataList.get(position).getItemQuantity() != 0) {
                    CartList cartList = new CartList(dataList.get(position).getStoreId(), dataList.get(position).getItemId(), AppPreference.getAppPreference(activity).getValue(AppConstants.ID, 0),
                            dataList.get(position).getItemName(), dataList.get(position).getItemCost(), dataList.get(position).getItemQuantity(),false);
                    cartDao.insert(cartList);
                    notifyDataSetChanged();
                    activity.sendBroadcast(new Intent("MyAction"));

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvItemCost, tvQty;
        ImageView btnPlus, btnMinus;
        CheckBox cbKey;
        Button btAddToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            cbKey = itemView.findViewById(R.id.cbKey);
            tvItemCost = itemView.findViewById(R.id.tvItemCost);
            btnPlus = itemView.findViewById(R.id.ivPlus);
            btnMinus = itemView.findViewById(R.id.ivMinus);
            tvQty = itemView.findViewById(R.id.tvCount);
            btAddToCart = itemView.findViewById(R.id.bt_add_to_cart);
        }
    }

    public interface OnClickMenuListener {
        void onItemClick(int position);

    }
}
