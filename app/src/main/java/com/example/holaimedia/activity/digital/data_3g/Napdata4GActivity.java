package com.example.holaimedia.activity.digital.data_3g;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.auth.SplashActivity;
import com.example.holaimedia.model.auth.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

public class Napdata4GActivity extends AppCompatActivity {
    Button btn_thang,btn_ngay,btn_1g,btn_2g,btn_3g,btn_500,btn_300,btn_100,btn_thanhtoan;
    TextView txttienl,txtgoi,txtsdt,txtma,txtmk2;
    private FirebaseDatabase database;
    DatabaseReference tk10;
    private User user;
    private long currentBalance = 0L;
    private String matest;
    private String userID;
    private Gson gson;
    AlertDialog.Builder alertBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_napdata4_gactivity);
        btn_thang=findViewById(R.id.btn_thang);
        btn_ngay=findViewById(R.id.btn_ngay);

        btn_1g=findViewById(R.id.btn_1g);
        btn_2g=findViewById(R.id.btn_2g);
        btn_3g=findViewById(R.id.btn3g);
        btn_500=findViewById(R.id.btn_500);
        btn_300=findViewById(R.id.btn300);
        btn_100=findViewById(R.id.btn100);
        txttienl=findViewById(R.id.txttiensau);
        txtgoi=findViewById(R.id.txtgoi);
        txtsdt=findViewById(R.id.txtsdt);
        txtma=findViewById(R.id.txtma);
        txtmk2=findViewById(R.id.txtmk2);

        btn_thanhtoan=findViewById(R.id.btnthanhtoan);



        Date currentTime = Calendar.getInstance().getTime();
        String userPassword=txtma.getText().toString();
        tk10 = FirebaseDatabase.getInstance().getReference();


        database = FirebaseDatabase.getInstance();
        gson = new Gson();

        String userJson = SplashActivity.sharedPreferences.getString(SplashActivity.USER_DATA, null);
        userID = SplashActivity.sharedPreferences.getString(SplashActivity.USER_ID, null);
        user = gson.fromJson(userJson, User.class);

        currentBalance = user.getAccount();
        matest=user.getPinCode();

        btn_thang.setOnClickListener(v -> {
            txtgoi.setText("2GB/ngày");
            txttienl.setText("10000");

        });

        btn_ngay.setOnClickListener(v -> {
            txtgoi.setText("1GB/ngày");
            txttienl.setText("5000");

        });
        btn_1g.setOnClickListener(v -> {
            txtgoi.setText("10GB/tháng");
            txttienl.setText("100000");

        });
        btn_2g.setOnClickListener(v -> {
            txtgoi.setText("20GB/tháng");
            txttienl.setText("150000");

        });
        btn_3g.setOnClickListener(v -> {
            txtgoi.setText("30GB/tháng");
            txttienl.setText("200000");

        });
        btn_100.setOnClickListener(v -> {
            txtgoi.setText("5GB/tuần");
            txttienl.setText("50000");

        });
        btn_300.setOnClickListener(v -> {
            txtgoi.setText("6GB/tuần");
            txttienl.setText("60000");

        });
        btn_500.setOnClickListener(v -> {
            txtgoi.setText("7GB/tuần");
            txttienl.setText("70000");
        });

        txtmk2.setText(String.valueOf(matest));
        btn_thanhtoan.setOnClickListener(v -> {

            String a=txtmk2.getText().toString();
            String b=txtma.getText().toString();
            String chuoi1,chuoi2 ;

            chuoi1=txttienl.getText().toString();
            int d=Integer.parseInt(chuoi1);
            database.getReference().child("Users").child(userID).setValue(user).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if(a.equalsIgnoreCase(b)){
                        currentBalance = currentBalance - Integer.parseInt(txttienl.getText().toString());
                        int amountOfMoney = Integer.parseInt(txttienl.getText().toString());
                        user.setAccount(currentBalance);
                        //  user.setAccount(currentBalance);
                        saveUserData();

                        alertBuilder = new AlertDialog.Builder(Napdata4GActivity.this);
                        alertBuilder.setTitle("Phiếu thanh toán");
                        alertBuilder.setMessage(
                                "Giá: " + txttienl.getText().toString()+ "\n" +
                                        "Data: " + txtgoi.getText().toString()+ "\n" +
                                        "Date: " + currentTime

                        );
                        alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Toast.makeText(Napdata4GActivity.this, "Cảm ơn đã sử dụng dịch vụ", Toast.LENGTH_LONG).show();
                            }
                        });
                        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog = alertBuilder.create();
                        alertDialog.show();


                        Toast.makeText(Napdata4GActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),SplashActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Bạn nhập sai mã",Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(Napdata4GActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            });



        });

    }
    private void saveUserData() {
        String userJson = gson.toJson(user);
        SplashActivity.sharedPreferences.edit().putString(SplashActivity.USER_DATA, userJson).apply();
    }
}