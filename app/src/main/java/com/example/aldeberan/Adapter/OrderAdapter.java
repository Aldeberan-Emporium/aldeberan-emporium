package com.example.aldeberan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aldeberan.Activity.OrderActivity;
import com.example.aldeberan.Activity.home_product;
import com.example.aldeberan.MapFragment.MapsActivity;
import com.example.aldeberan.R;
import com.example.aldeberan.databinding.CartDetailCRowBinding;
import com.example.aldeberan.databinding.OrderHistoryCRowBinding;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.models.OrderModel;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Cart;
import com.example.aldeberan.structures.Order;
import com.example.aldeberan.structures.Product;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{
    private Context mContext;
    private List<Order> orderList;
    private OrderModel om;

    public OrderAdapter(Context mContext, List<Order>orderList) {
        this.mContext = mContext;
        this.orderList = orderList;
    }



    public class OrderViewHolder extends RecyclerView.ViewHolder{
        OrderHistoryCRowBinding orderHistoryCRowBinding;

        public OrderViewHolder(OrderHistoryCRowBinding orderHistoryCRowBinding){
            super(orderHistoryCRowBinding.getRoot());
            this.orderHistoryCRowBinding = orderHistoryCRowBinding;
        }
    }

    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        OrderHistoryCRowBinding orderHistoryCRowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.order_history_c_row, parent, false);
        OrderAdapter.OrderViewHolder holder = new OrderAdapter.OrderViewHolder(orderHistoryCRowBinding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        //orderRef, orderDate, paymentMethod, total

        holder.orderHistoryCRowBinding.executePendingBindings();

        holder.orderHistoryCRowBinding.orderID.setText("ID: " + orderList.get(position).getOrderRef());
        holder.orderHistoryCRowBinding.orderDate.setText("Name: " + orderList.get(position).getProdName());
        holder.orderHistoryCRowBinding.orderTotalPrice.setText("Price: RM " + orderList.get(position).getProdPrice());
        holder.orderHistoryCRowBinding.orderPaymentMethod.setText(orderList.get(position).getPayType());

        holder.orderHistoryCRowBinding.orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MapsActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


}
