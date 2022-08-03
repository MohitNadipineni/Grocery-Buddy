package com.asquare.grocerybuddy.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asquare.grocerybuddy.Model.CartList;
import com.asquare.grocerybuddy.R;

import java.util.ArrayList;

public class CartListAdapter  extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    Activity activity;
    ArrayList<CartList> dataList;
    //private OnClickMenuListener onClickMenuListener;
    int minteger = 0;

    public CartListAdapter(Activity activity, ArrayList<CartList> dataList) {// OnClickMenuListener onClickMenuListener) {
        this.activity = activity;
        this.dataList = dataList;
        //this.onClickMenuListener = onClickMenuListener;
    }

    @NonNull
    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_cart_items, null);

        return new CartListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvItemName.setText(dataList.get(position).getItem_name());

        int amount=dataList.get(position).getItem_cost()*dataList.get(position).getItem_qty();
        holder.tvItemCost.setText("$"+dataList.get(position).getItem_cost()+" * "+dataList.get(position).getItem_qty()+" = "+amount);
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minteger = minteger + 1;
                holder.tvQty.setText(String.valueOf(minteger));
                dataList.get(position).setQty(minteger);
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (minteger != 0) {
                    minteger = minteger - 1;
                    holder.tvQty.setText(String.valueOf(minteger));
                    dataList.get(position).setQty(minteger);
                }
            }
        });

        if (dataList.get(position).isSelected()) {
            holder.cbKey.setChecked(true);
        }

        holder.cbKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataList.get(position).setSelected(holder.cbKey.isChecked());
                holder.cbKey.setChecked(holder.cbKey.isChecked());
            }
        });

       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMenuListener.onItemClick(position);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvItemCost, tvQty;
        ImageView btnPlus, btnMinus;
        CheckBox cbKey;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            cbKey = itemView.findViewById(R.id.cbKey);
            tvItemCost = itemView.findViewById(R.id.tvItemCost);
            btnPlus = itemView.findViewById(R.id.ivPlus);
            btnMinus = itemView.findViewById(R.id.ivMinus);
            tvQty = itemView.findViewById(R.id.tvCount);
        }
    }

    /*public interface OnClickMenuListener {
        void onItemClick(int position);

    }*/
}
