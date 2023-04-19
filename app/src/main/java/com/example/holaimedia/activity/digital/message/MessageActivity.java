package com.example.holaimedia.activity.digital.message;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holaimedia.R;
import com.example.holaimedia.adapter.digital.MessageAdapter;
import com.example.holaimedia.model.digital.Message;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MessageActivity extends AppCompatActivity {
    private  TextInputLayout tfMessage;
    private FloatingActionButton fabSend;

    private RecyclerView rcvMessage;
    private ArrayList<Message> messages;
    private MessageAdapter adapter;
    private DatabaseReference db;
    private  FirebaseAuth auth;
    private  FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        db = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        messages = new ArrayList<>();

        receiveMessages();
        initViews();
        initViewListener();
    }

    private void initViews() {
        tfMessage = findViewById(R.id.tfMessage);
        fabSend = findViewById(R.id.fabSend);

        rcvMessage = findViewById(R.id.rcvMessage);
        adapter = new MessageAdapter(this, messages,this,user.getEmail());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvMessage.setLayoutManager(layoutManager);
        rcvMessage.setAdapter(adapter);
        rcvMessage.addOnLayoutChangeListener((view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (adapter.getItemCount() > 0) {
                rcvMessage.smoothScrollToPosition(adapter.getItemCount() - 1);
            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    private void initViewListener() {
        String uEmail = user.getEmail();
        String timeStamp = new SimpleDateFormat("dd-MM-yy_HH:mm a").format(Calendar.getInstance().getTime());
        fabSend.setOnClickListener(view -> {
            String msg = tfMessage.getEditText().getText().toString();
            db.child("Messages").push().setValue(new Message(uEmail, msg, timeStamp)).addOnCompleteListener(task -> tfMessage.getEditText().setText(""));
        });
    }

    private void receiveMessages() {
        db.child("Messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Message message = snap.getValue(Message.class);
                    adapter.addMessage(message);
                    adapter.notifyItemChanged(messages.size()-1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}