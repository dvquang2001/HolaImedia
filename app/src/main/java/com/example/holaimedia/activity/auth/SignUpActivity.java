package com.example.holaimedia.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.MainActivity;
import com.example.holaimedia.model.auth.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtName, edtEmail, edtPassword, edtConfirmPassword;
    private Button btnSignUp;
    private TextView tvNavigateToLogin;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private User user;
    public static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        initViews();
        initViewListener();
    }

    private void initViews() {
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvNavigateToLogin = findViewById(R.id.tvNavigateToLogin);
    }

    private void initViewListener() {
        btnSignUp.setOnClickListener(view -> onSignUpClicked());
        tvNavigateToLogin.setOnClickListener(view -> onBackPressed());
    }

    private void onSignUpClicked() {
        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();
        if (!validateRegister(name, email, password, confirmPassword)) {
            return;
        }
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user = new User(name, email, password, "", "", "", "", 0L,false);
                        String userID = task.getResult().getUser().getUid();
                        id = userID;
                        Gson gson = new Gson();
                        database.getReference().child("Users").child(userID).setValue(user).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                String userJson = gson.toJson(user);
                                SplashActivity.sharedPreferences.edit().putString(SplashActivity.USER_ID,userID).apply();
                                SplashActivity.sharedPreferences.edit().putString(SplashActivity.USER_DATA, userJson).apply();
                                SplashActivity.sharedPreferences.edit().putBoolean(SplashActivity.IS_LOGIN, true).apply();
                                Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                intent.putExtra("SIGN_UP_NOTIFICATION", id);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(this, task1.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        Toast.makeText(getApplicationContext(), "Lỗi: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private boolean validateRegister(String name, String email, String password, String confirmedPassword) {
        if (TextUtils.isEmpty(name.trim())) {
            edtEmail.setError("Tên không được bỏ trống");
            Toast.makeText(getApplicationContext(), "Tên không được bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(email.trim())) {
            edtEmail.setError("Email không được bỏ trống");
            Toast.makeText(getApplicationContext(), "Email không được bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Email không hợp lệ");
            Toast.makeText(getApplicationContext(), "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password.trim())) {
            edtPassword.setError("Mật khẩu không được bỏ trống");
            Toast.makeText(getApplicationContext(), "Mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.trim().length() < 6) {
            edtPassword.setError("Mật khẩu phải dài hơn 6 ký tự");
            Toast.makeText(getApplicationContext(), "Mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!confirmedPassword.equals(password)) {
            edtConfirmPassword.setError("Mật khẩu xác thực chưa đúng");
            Toast.makeText(getApplicationContext(), "Mật khẩu xác thực chưa đúng", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}