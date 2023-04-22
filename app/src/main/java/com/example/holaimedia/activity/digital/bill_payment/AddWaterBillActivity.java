package com.example.holaimedia.activity.digital.bill_payment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.holaimedia.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AddWaterBillActivity extends AppCompatActivity {
    private EditText etPhoneNumber, etName, etAddress, etBillCode, etMoney, etDeadline;
    private Button btnAddBill;
    private ImageView ivBack;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_water_bill);

        initViews();
        initViewListener();
    }

    private void initViews() {
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etBillCode = findViewById(R.id.etBillCode);
        etMoney = findViewById(R.id.etMoney);
        etDeadline = findViewById(R.id.etDeadline);
        btnAddBill = findViewById(R.id.btnAddBill);
        ivBack = findViewById(R.id.ivBack);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    private void initViewListener() {
        ivBack.setOnClickListener(view -> finish());
        btnAddBill.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String phone = etPhoneNumber.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String billCode = etBillCode.getText().toString().trim();
            String money = etMoney.getText().toString().trim();
            String deadLine = etDeadline.getText().toString().trim();

            if(!validateEditText()) return;
            auth.addAuthStateListener(firebaseAuth -> {
                database.getReference().child("Paybill").child(billCode).child("Ten").setValue(name);
                database.getReference().child("Paybill").child(billCode).child("Sodienthoai").setValue(phone);
                database.getReference().child("Paybill").child(billCode).child("Diachi").setValue(address);
                database.getReference().child("Paybill").child(billCode).child("Ma").setValue(billCode);
                database.getReference().child("Paybill").child(billCode).child("Tien").setValue(money);
                database.getReference().child("Paybill").child(billCode).child("Kyhan").setValue(deadLine);
                Toast.makeText(getApplicationContext(), "Thêm hóa đơn thành công", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }

    private boolean validateEditText() {
        if (etPhoneNumber.getText().toString().trim().isEmpty()) {
            etPhoneNumber.setError("Số điện thoại không được để trống");
            Toast.makeText(this, "Số điện thoại không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etName.getText().toString().trim().isEmpty()) {
            etName.setError("Tên không được để trống");
            Toast.makeText(this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etAddress.getText().toString().trim().isEmpty()) {
            etAddress.setError("Địa chỉ không được để trống");
            Toast.makeText(this, "Địa chỉ không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etBillCode.getText().toString().trim().isEmpty()) {
            etBillCode.setError("Mã hóa đơn không được để trống");
            Toast.makeText(this, "Mã hóa đơn không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etMoney.getText().toString().trim().isEmpty()) {
            etMoney.setError("Tiền thanh toán không được để trống");
            Toast.makeText(this, "Tiền thanh toán không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etDeadline.getText().toString().trim().isEmpty()) {
            etDeadline.setError("Kỳ hạn thanh toán không được để trống");
            Toast.makeText(this, "Kỳ hạn thanh toán không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etPhoneNumber.getText().toString().trim().length() != 10
                && String.valueOf(etPhoneNumber.getText().charAt(0)).equals("0")) {
            etPhoneNumber.setError("Số điện thoại không hợp lệ");
            Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}