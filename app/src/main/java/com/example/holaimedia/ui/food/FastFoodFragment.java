package com.example.holaimedia.ui.food;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.holaimedia.R;
import com.example.holaimedia.adapter.food.ProductAdapter;
import com.example.holaimedia.base.BaseFoodFragment;

import java.util.ArrayList;

public class FastFoodFragment extends BaseFoodFragment {

    private View root;
    public FastFoodFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root =  inflater.inflate(R.layout.fragment_fast_food, container, false);

        initViews();
        return root;
    }

    private void initViews() {
        fetchFastFoodList();
        RecyclerView rcvProduct = root.findViewById(R.id.rcvFastFood);
        rcvProduct.setLayoutManager(new LinearLayoutManager(requireContext()));
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList, user.isAdmin(), this);
        rcvProduct.setAdapter(productAdapter);
    }
}