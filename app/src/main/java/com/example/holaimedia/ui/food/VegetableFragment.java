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

public class VegetableFragment extends BaseFoodFragment {

    private View root;
    public VegetableFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root =  inflater.inflate(R.layout.fragment_vegetable, container, false);

        initViews();
        return root;
    }

    private void initViews() {
        fetchVegetableList();
        RecyclerView rcvVegetable = root.findViewById(R.id.rcvVegetable);
        rcvVegetable.setLayoutManager(new LinearLayoutManager(requireContext()));
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList, user.isAdmin(), this);
        rcvVegetable.setAdapter(productAdapter);
    }
}