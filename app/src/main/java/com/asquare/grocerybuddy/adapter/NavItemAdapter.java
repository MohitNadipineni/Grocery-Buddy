package com.asquare.grocerybuddy.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asquare.grocerybuddy.R;

import java.util.List;

public class NavItemAdapter extends RecyclerView.Adapter<NavItemAdapter.ViewHolder> {
    Activity activity;
    String[] dataList;
    private OnClickMenuListener onClickMenuListener;

    public NavItemAdapter(Activity activity, String[] dataList, OnClickMenuListener onClickMenuListener) {
        this.activity = activity;
        this.dataList = dataList;
        this.onClickMenuListener = onClickMenuListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.nav_item, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvItemName.setText(dataList[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMenuListener.onItemClick(position);
            }
        });



    }

    @Override
    public int getItemCount() {

        return dataList.length;
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
