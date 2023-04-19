package com.example.holaimedia.activity.digital.user_water;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.holaimedia.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddUserWaterActivity extends AppCompatActivity {
    private EditText txtsdt, txtma, txttien, txtdiachi,txthoten,txtkyhan;

    private Button btnthem;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_water);
        txtdiachi=findViewById(R.id.txtaddress);
        txtma=findViewById(R.id.txtmanuoc2);
        txttien=findViewById(R.id.txttiennuoc);
        txtsdt=findViewById(R.id.txtphone);
        btnthem=findViewById(R.id.btn_them);
        txthoten=findViewById(R.id.txtname);
        txtkyhan=findViewById(R.id.tv_kythanhtoan);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        btnthem.setOnClickListener(v -> {

            UUID uuid = UUID.randomUUID();
            String chuoi=uuid.toString();
            String sdt = txtsdt.getText().toString();
            String kyhan = txtkyhan.getText().toString();
            String ma = txtma.getText().toString();
            String diachi = txtdiachi.getText().toString();
            String tien = txttien.getText().toString();
            String ten = txthoten.getText().toString();
            auth.addAuthStateListener(firebaseAuth -> {
                database.getReference().child("Paybill").child(ma).child("Sodienthoai").setValue(sdt);
                database.getReference().child("Paybill").child(ma).child("Diachi").setValue(diachi);
                database.getReference().child("Paybill").child(ma).child("Tien").setValue(tien);
                database.getReference().child("Paybill").child(ma).child("Ten").setValue(ten);
                database.getReference().child("Paybill").child(ma).child("Ma").setValue(ma);
                database.getReference().child("Paybill").child(ma).child("Kyhan").setValue(kyhan);
                Toast.makeText(getApplicationContext(), "thêm thành công", Toast.LENGTH_SHORT).show();

            });
        });
    }
}