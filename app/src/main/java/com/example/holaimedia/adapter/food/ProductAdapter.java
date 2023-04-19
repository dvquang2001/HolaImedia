package com.example.holaimedia.adapter.food;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.holaimedia.R;
import com.example.holaimedia.activity.DetailActivity;
import com.example.holaimedia.model.food.Product;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private final List<Product> list;
    private final boolean isAdmin;
    private final OnModifyClick onModifyClick;

    public ProductAdapter(List<Product> list, boolean isAdmin, OnModifyClick onModifyClick) {
        this.list = list;
        this.isAdmin = isAdmin;
        this.onModifyClick = onModifyClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product currentProduct = list.get(position);
        holder.bind(currentProduct,isAdmin,onModifyClick);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivProduct;
        private final TextView tvProductName, tvProductPrice;
        private final LinearLayout cardEdit, cardDelete, cardContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            cardEdit = itemView.findViewById(R.id.cardEdit);
            cardDelete = itemView.findViewById(R.id.cardDelete);
            cardContent = itemView.findViewById(R.id.cardContent);
        }

        void bind(Product product, boolean isAdmin,OnModifyClick onModifyClick) {
            if(product.getImg_url().isEmpty()) {
                ivProduct.setImageResource(R.drawable.imedia);
            } else {
                Glide.with(itemView.getContext()).load(product.getImg_url()).into(ivProduct);
            }
            tvProductName.setText(product.getName());
            switch (product.getType()) {
                case "milk":
                    tvProductPrice.setText(String.valueOf(product.getPrice()).concat("VND/hộp"));
                    break;
                case "fruit":
                    tvProductPrice.setText(String.valueOf(product.getPrice()).concat("VND/kg"));
                    break;
                case "vegetable":
                    tvProductPrice.setText(String.valueOf(product.getPrice()).concat("VND/bó"));
                    break;
                case "fastfood":
                    tvProductPrice.setText(String.valueOf(product.getPrice()).concat("VND/phần"));
                    break;
            }
            if(isAdmin) {
                cardDelete.setVisibility(View.VISIBLE);
                cardEdit.setVisibility(View.VISIBLE);
            } else {
                cardDelete.setVisibility(View.GONE);
                cardEdit.setVisibility(View.GONE);
            }
            cardContent.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra("detail", product);
                itemView.getContext().startActivity(intent);
            });
            cardEdit.setOnClickListener(view -> onModifyClick.edit(product));
            cardDelete.setOnClickListener(view -> onModifyClick.delete(product));
        }
    }
}
