package com.example.holaimedia.activity.digital.bill_payment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holaimedia.R;
import com.example.holaimedia.adapter.digital.UserWaterAdapter;
import com.example.holaimedia.model.digital.Watercustomer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BillpaymentwaterMainActivity extends AppCompatActivity {
    private RecyclerView rcvUsers;
    EditText etsearch;
    FirebaseDatabase database;
    private UserWaterAdapter mUserAdapter;
    DatabaseReference myref;
    private List<Watercustomer> mListUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billpaymentwater_main);
        etsearch=findViewById(R.id.etsearch);
        database=FirebaseDatabase.getInstance();
        rcvUsers=findViewById(R.id.rcv_user);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rcvUsers.setLayoutManager(linearLayoutManager   );
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcvUsers.addItemDecoration(dividerItemDecoration);
        mListUsers=new ArrayList<>();
        mUserAdapter=new UserWaterAdapter(this,mListUsers);
        rcvUsers.setAdapter(mUserAdapter);
        getListUserFromReatimedatabase();
    }
    private void getListUserFromReatimedatabase(){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        myref=database.getReference("Paybill");

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Watercustomer user1=dataSnapshot.getValue(Watercustomer.class);
                    mListUsers.add(user1);

                }
                mUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BillpaymentwaterMainActivity.this, "looix", Toast.LENGTH_SHORT).show();
            }
        });



        //tim kiem
        etsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mUserAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
