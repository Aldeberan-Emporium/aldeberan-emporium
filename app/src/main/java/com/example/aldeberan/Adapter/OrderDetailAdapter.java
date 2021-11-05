package com.example.aldeberan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aldeberan.R;
import com.example.aldeberan.databinding.OrderDetailCRowBinding;
import com.example.aldeberan.databinding.OrderHistoryCRowBinding;
import com.example.aldeberan.models.OrderModel;
import com.example.aldeberan.structures.Order;

import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.List;

/*
Chong Wai Hou   1161104445

Chong is responsible for this feature.
*/


public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {
    private Context context;
    private List<Order> orderDetailList;
    private OrderModel om;
    private String orderStatus;

    public OrderDetailAdapter(Context context, List<Order>orderDetailList) {
        this.context = context;
        this.orderDetailList = orderDetailList;
    }

    public class OrderDetailViewHolder extends RecyclerView.ViewHolder{
        OrderDetailCRowBinding orderDetailCRowBinding;

        public OrderDetailViewHolder(OrderDetailCRowBinding orderDetailCRowBinding){
            super(orderDetailCRowBinding.getRoot());
            this.orderDetailCRowBinding = orderDetailCRowBinding;
        }
    }

    @NonNull
    @Override
    public OrderDetailAdapter.OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        OrderDetailCRowBinding orderDetailCRowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.order_detail_c_row, parent, false);
        OrderDetailAdapter.OrderDetailViewHolder holder = new OrderDetailAdapter.OrderDetailViewHolder(orderDetailCRowBinding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.OrderDetailViewHolder holder, int position) {

        holder.orderDetailCRowBinding.executePendingBindings();

        Glide.with(context).load(orderDetailList.get(position).getProdImg()).override(450, 450).into(holder.orderDetailCRowBinding.orderDetailImage);
        holder.orderDetailCRowBinding.orderDetailName.setText(orderDetailList.get(position).getProdName());
        DecimalFormat df = new DecimalFormat("#.00");
        String orderDetailPrice = df.format(orderDetailList.get(position).getProdPrice());
        holder.orderDetailCRowBinding.orderDetailQuantity.setText("x" + String.valueOf(orderDetailList.get(position).getProdQuantity()));
        holder.orderDetailCRowBinding.orderDetailPrice.setText("RM" + orderDetailPrice);
    }

    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }
}
