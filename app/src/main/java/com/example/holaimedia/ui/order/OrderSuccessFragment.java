package com.example.holaimedia.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.auth.SplashActivity;
import com.example.holaimedia.adapter.food.OrderAdapter;
import com.example.holaimedia.base.BaseOrderFragment;
import com.example.holaimedia.model.food.Order;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class OrderSuccessFragment extends BaseOrderFragment {
    private FirebaseFirestore db;
    private RecyclerView rcvOrderSuccess;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;
    private String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_order_success, container, false);

        db = FirebaseFirestore.getInstance();

        userID = SplashActivity.sharedPreferences.getString(SplashActivity.USER_ID, null);

        rcvOrderSuccess = root.findViewById(R.id.rcvOrderSuccess);
        rcvOrderSuccess.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderList);
        rcvOrderSuccess.setAdapter(orderAdapter);

        db.collection("Order").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                    String documentId = documentSnapshot.getId();
                    Order order = documentSnapshot.toObject(Order.class);
                    order.setDocumentId(documentId);
                    if(order.getUserId() == null) {
                        continue;
                    }
                    if(order.getProductName() != null && order.getProductPrice() != null) {
                        if(isSuccess(order) && order.getUserId().equals(userID)) {
                            orderList.add(order);
                        }
                    }
                    orderAdapter.notifyItemInserted(orderList.size() - 1);
                }
            } else {
                Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}
