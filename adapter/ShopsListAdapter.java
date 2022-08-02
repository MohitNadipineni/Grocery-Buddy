package com.asquare.grocerybuddy.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asquare.grocerybuddy.Model.StoreDetailsModel;
import com.asquare.grocerybuddy.R;

import java.util.ArrayList;

public class ShopsListAdapter extends RecyclerView.Adapter<ShopsListAdapter.ViewHolder> {
    Activity activity;
    ArrayList<StoreDetailsModel> dataList;
    private OnClickMenuListener onClickMenuListener;

    public ShopsListAdapter(Activity activity, ArrayList<StoreDetailsModel> dataList, OnClickMenuListener onClickMenuListener) {
        this.activity = activity;
        this.dataList = dataList;
        this.onClickMenuListener = onClickMenuListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.shoplist_item_layout, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        //holder.tvItemName.setText(dataList.get(position).getName().toString());
        holder.tvItemName.setText(dataList.get(position).getStoreName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMenuListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);

        }
    }

    public interface OnClickMenuListener {
        void onItemClick(int position);

    }
}
