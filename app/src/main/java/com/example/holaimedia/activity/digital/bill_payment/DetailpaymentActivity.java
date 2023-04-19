package com.example.holaimedia.activity.digital.bill_payment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.auth.SplashActivity;
import com.example.holaimedia.model.auth.User;
import com.example.holaimedia.model.digital.Watercustomer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

public class DetailpaymentActivity extends AppCompatActivity {
    EditText mapin;
    Button btn_thanhtoan_water;
    FirebaseAuth auth;
    private FirebaseDatabase database;
    DatabaseReference tk,tk10;
    private User user1;
    private long currentBalance = 0L;
    private String matest;
    private String userID;
    private Gson gson;
    AlertDialog.Builder alertBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailpayment);
        Bundle bundle=getIntent().getExtras();
        if(bundle==null){
            return;
        }
        Watercustomer user= (Watercustomer) bundle.get("object_user");

        TextView tvma=findViewById(R.id.tv_ma_user);
        TextView tvname=findViewById(R.id.tv_ten_user);
        TextView tvdiachi=findViewById(R.id.txt_diachi_user);
        TextView tvkyhan=findViewById(R.id.txt_kyhan_user);
        TextView tvsotien=findViewById(R.id.txt_sotien_user);
        TextView tvsdt=findViewById(R.id.txtsdt_user);
        TextView txtmk2=findViewById(R.id.txtmk3);
        mapin=findViewById(R.id.txtmapin_w);
        btn_thanhtoan_water=findViewById(R.id.btn_thanhtoan_water);

        Date currentTime = Calendar.getInstance().getTime();
        String userPassword=mapin.getText().toString();
        tk10 = FirebaseDatabase.getInstance().getReference();


        database = FirebaseDatabase.getInstance();
        gson = new Gson();

        String userJson = SplashActivity.sharedPreferences.getString(SplashActivity.USER_DATA, null);
        userID = SplashActivity.sharedPreferences.getString(SplashActivity.USER_ID, null);
        user1 = gson.fromJson(userJson, User.class);

        currentBalance = user1.getAccount();
        matest=user1.getPinCode();


        //set text intent chuyển sang
        tvma.setText(user.getMa());
        tvname.setText(user.getTen());
        tvdiachi.setText(user.getDiachi());
        tvkyhan.setText(user.getKyhan());
        tvsotien.setText(user.getTien());
        tvsdt.setText(user.getSodienthoai());

        txtmk2.setText(String.valueOf(matest));
        btn_thanhtoan_water.setOnClickListener(v -> {
            String a=txtmk2.getText().toString();
            String b=mapin.getText().toString();

            if(a.equalsIgnoreCase(b)){

                alertBuilder = new AlertDialog.Builder(DetailpaymentActivity.this);
                alertBuilder.setTitle("Phiếu thanh toán");
                alertBuilder.setMessage(
                        "Giá: " + tvsotien.getText().toString()+ "\n" +

                                "Date: " + currentTime

                );
                alertBuilder.setPositiveButton("Ok", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    Toast.makeText(DetailpaymentActivity.this, "Cảm ơn đã sử dụng dịch vụ", Toast.LENGTH_LONG).show();
                });
                alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();
                Toast.makeText(DetailpaymentActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(getApplicationContext(),"Bạn nhập sai mã",Toast.LENGTH_SHORT).show();

            }

        });


    }
    private void saveUserData() {
        String userJson = gson.toJson(user1);
        SplashActivity.sharedPreferences.edit().putString(SplashActivity.USER_DATA, userJson).apply();
    }
}
