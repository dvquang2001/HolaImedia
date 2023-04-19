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
import com.example.holaimedia.model.food.Cart;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    List<Cart> cartList;

    public CartAdapter(List<Cart> cartModelList) {
        this.cartList = cartModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart currentCart = cartList.get(position);
        holder.bind(currentCart, cartList, position);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCartName, tvCartPrice, tvCartQuantity, tvTotalCartPrice;
        private final ImageView ivCart, ivDeleteCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCartName = itemView.findViewById(R.id.tvCartName);
            tvCartPrice = itemView.findViewById(R.id.tvCartPrice);
            tvCartQuantity = itemView.findViewById(R.id.tvCartQuantity);
            tvTotalCartPrice = itemView.findViewById(R.id.tvTotalCartPrice);
            ivCart = itemView.findViewById(R.id.ivCart);
            ivDeleteCart = itemView.findViewById(R.id.ivDeleteCart);
        }

        void bind(Cart cart, List<Cart> cartList, int position) {
            Glide.with(itemView.getContext()).load(cart.getImageUrl()).into(ivCart);
            tvCartName.setText(cart.getProductName());
            tvCartPrice.setText(cart.getProductPrice().concat(" VND"));
            tvCartQuantity.setText(cart.getTotalQuantity());
            tvTotalCartPrice.setText(String.valueOf(cart.getTotalPrice()).concat(" VND"));
            ivDeleteCart.setOnClickListener(v ->
                    FirebaseFirestore.getInstance().collection("Cart").document(cart.getDocumentId())
                            .delete().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    cartList.remove(cart);
                                    notifyItemRemoved(position);
                                    Toast.makeText(itemView.getContext(), "Xóa 1 sản phẩm", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(itemView.getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                                }
                            }));
        }

    }
}
