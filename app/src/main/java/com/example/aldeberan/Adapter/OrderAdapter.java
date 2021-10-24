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
import com.example.aldeberan.Activity.OrderDetailActivity;
import com.example.aldeberan.Activity.home_product;
import com.example.aldeberan.MapFragment.MapsActivity;
import com.example.aldeberan.R;
import com.example.aldeberan.UserFragment.cartFragment;
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

        holder.orderHistoryCRowBinding.executePendingBindings();

        Glide.with(mContext).load(orderList.get(position).getProdImg()).override(450, 450).into(holder.orderHistoryCRowBinding.orderImage);
        holder.orderHistoryCRowBinding.orderHistoryStatus.setText(orderList.get(position).getOrderStatus());
        holder.orderHistoryCRowBinding.orderName.setText("Name: " + orderList.get(position).getProdName());
        String productQuantity = String.valueOf(orderList.get(position).getProdQuantity());
        holder.orderHistoryCRowBinding.orderQuantity.setText(productQuantity + "x");
        String onePrice = String.valueOf(orderList.get(position).getProdPrice());
        holder.orderHistoryCRowBinding.orderPrice.setText("RM" + onePrice);
        String orderTotal = String.valueOf(orderList.get(position).getTotal());
        holder.orderHistoryCRowBinding.addedPrice.setText(("Order total: RM"+ orderTotal));
        int itemNum = orderList.get(position).getTotalItems();

        String item;
        if (itemNum <= 1){
            item = " item";
        }
        else
            item = " items";

        String totalItem = String.valueOf(orderList.get(position).getTotalItems());
        holder.orderHistoryCRowBinding.totalItem.setText(totalItem + item);

        holder.orderHistoryCRowBinding.orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderDetailActivity = new Intent(mContext, OrderDetailActivity.class);

                String address = "";
                if (orderList.get(position).getAddLine2().isEmpty()){
                    address = orderList.get(position).getAddLine1() + ", \n"
                            + orderList.get(position).getAddCode() + ", " + orderList.get(position).getAddCity() + ", "
                            +  orderList.get(position).getAddState() + ", " + orderList.get(position).getAddCountry() + ".";
                }
                else{
                    address = orderList.get(position).getAddLine1() + ", " + orderList.get(position).getAddLine2() + ", \n"
                            + orderList.get(position).getAddCode() + ", " + orderList.get(position).getAddCity() + ", "
                            +  orderList.get(position).getAddState() + ", " + orderList.get(position).getAddCountry() + ".";
                }

                String paymentMethod = orderList.get(position).getPayType();

                Double productTotal = (orderList.get(position).getTotal());
                Double orderTotal = (orderList.get(position).getTotal() + 5);
                String orderStatus = orderList.get(position).getOrderStatus();
                int orderID = orderList.get(position).getOrderID();


                orderDetailActivity.putExtra("address", address);
                orderDetailActivity.putExtra("paymentMethod", paymentMethod);
                orderDetailActivity.putExtra("productTotal", productTotal);
                orderDetailActivity.putExtra("orderTotal", orderTotal);
                orderDetailActivity.putExtra("orderStatus", orderStatus);
                orderDetailActivity.putExtra("orderID", orderID);

                mContext.startActivity(orderDetailActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
