package com.example.aldeberan.Adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aldeberan.databinding.OrderDetailCRowBinding;
import com.example.aldeberan.models.OrderModel;
import com.example.aldeberan.structures.Order;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {
    private Context context;
    private List<Order> orderDetailList;
    private OrderModel om;
    private String orderStatus;

    public OrderDetailAdapter(Context context, List<Order>orderDetailList, String orderStatus) {
        this.context = context;
        this.orderDetailList = orderDetailList;
        this.om = new OrderModel();
        this.orderStatus = orderStatus;
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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.OrderDetailViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
