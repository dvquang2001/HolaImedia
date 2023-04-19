package com.example.holaimedia.adapter.food;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.holaimedia.R;
import com.example.holaimedia.model.food.Order;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    List<Order> orderList;

    public OrderAdapter(List<Order> cartModelList) {
        this.orderList = cartModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order currentOrder = orderList.get(position);
        holder.bind(currentOrder, orderList, position);
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvOrderName, tvOrderPrice, tvOrderQuantity, tvTotalOrderPrice, tvCurrentDate, tvCurrentTime;
        private final ImageView ivOrder, ivDeleteOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderName = itemView.findViewById(R.id.tvOrderName);
            tvOrderPrice = itemView.findViewById(R.id.tvOrderPrice);
            tvOrderQuantity = itemView.findViewById(R.id.tvOrderQuantity);
            tvTotalOrderPrice = itemView.findViewById(R.id.tvTotalOrderPrice);
            tvCurrentDate = itemView.findViewById(R.id.tvCurrentDate);
            tvCurrentTime = itemView.findViewById(R.id.tvCurrentTime);
            ivOrder = itemView.findViewById(R.id.ivOrder);
            ivDeleteOrder = itemView.findViewById(R.id.ivDeleteOrder);
        }

        void bind(Order order, List<Order> orderList, int position) {
            Glide.with(itemView.getContext()).load(order.getImageUrl()).into(ivOrder);
            tvOrderName.setText(order.getProductName());
            tvOrderPrice.setText(order.getProductPrice().concat("VND"));
            tvOrderQuantity.setText(order.getTotalQuantity());
            tvTotalOrderPrice.setText(String.valueOf(order.getTotalPrice()).concat("VND"));
            tvCurrentDate.setText(order.getCurrentDate());
            tvCurrentTime.setText(order.getCurrentTime());
            ivDeleteOrder.setOnClickListener(v ->
                    FirebaseFirestore.getInstance().collection("Order").document(order.getDocumentId())
                            .delete().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    orderList.remove(order);
                                    notifyItemRemoved(position);
                                    Toast.makeText(itemView.getContext(), "Xóa 1 sản phẩm", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(itemView.getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                                }
                            }));
        }

    }
}
