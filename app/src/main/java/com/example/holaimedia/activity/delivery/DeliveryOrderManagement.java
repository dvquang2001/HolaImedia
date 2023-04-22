
package com.example.holaimedia.activity.delivery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holaimedia.R;
import com.example.holaimedia.adapter.delivery.OrderShipAdapter;
import com.example.holaimedia.model.delivery.OrderManagement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeliveryOrderManagement extends AppCompatActivity {
    private RecyclerView rcvUsers;
    private OrderShipAdapter mUserAdapter;
    private DatabaseReference myRef;
    private List<OrderManagement> mListUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_order_management);

        rcvUsers = findViewById(R.id.rcv_ordership);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvUsers.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvUsers.addItemDecoration(dividerItemDecoration);
        mListUsers = new ArrayList<>();
        mUserAdapter = new OrderShipAdapter(this, mListUsers);
        rcvUsers.setAdapter(mUserAdapter);
        getListUserFromRealTimeDatabase();
    }

    private void getListUserFromRealTimeDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Delivery");

        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    OrderManagement user1 = dataSnapshot.getValue(OrderManagement.class);
                    mListUsers.add(user1);

                }
                mUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}
