package com.example.holaimedia.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.MainActivity;
import com.example.holaimedia.model.auth.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText edtEmail, edtPassword;
    private TextView tvNavigateToRegister;
    private ImageView ivShowPassword;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private User user;

    public static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initViewListener();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    private void initViews() {
        btnLogin = findViewById(R.id.btnLogin);
        edtEmail = findViewById(R.id.edtEmail);
        ivShowPassword = findViewById(R.id.ivShowPassword);
        edtPassword = findViewById(R.id.edtPassword);
        tvNavigateToRegister = findViewById(R.id.tvNavigateToRegister);
    }

    private void initViewListener() {
        ivShowPassword.setOnClickListener(view -> {
            int selection = edtPassword.getSelectionEnd();
            if (edtPassword.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                ivShowPassword.setImageResource(R.drawable.ic_hide_password);
                edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ivShowPassword.setImageResource(R.drawable.ic_show_password);
                edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            edtPassword.setSelection(selection);
        });
        tvNavigateToRegister.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));
        btnLogin.setOnClickListener(v -> loginUser());
    }

    private boolean validateLogin(String userEmail, String userPassword) {
        if (TextUtils.isEmpty(userEmail.trim())) {
            edtEmail.setError("Email không được bỏ trống");
            Toast.makeText(getApplicationContext(), "Email không được bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            edtEmail.setError("Email không hợp lệ");
            Toast.makeText(getApplicationContext(), "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(userPassword.trim())) {
            edtPassword.setError("Mật khẩu không được bỏ trống");
            Toast.makeText(getApplicationContext(), "Mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (userPassword.trim().length() < 6) {
            edtPassword.setError("Mật khẩu phải dài hơn 6 ký tự");
            Toast.makeText(getApplicationContext(), "Mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void loginUser() {
        String userEmail = edtEmail.getText().toString();
        String userPassword = edtPassword.getText().toString();

        if (!validateLogin(userEmail, userPassword)) {
            return;
        }
        auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userID = task.getResult().getUser().getUid();
                        id = userID;
                        Gson gson = new Gson();
                        database.getReference().child("Users").child(userID).get().addOnCompleteListener(task2 -> {
                            if (task.isSuccessful()) {
                                user = task2.getResult().getValue(User.class);
                                String userJson = gson.toJson(user);
                                SplashActivity.sharedPreferences.edit().putString(SplashActivity.USER_ID, userID).apply();
                                SplashActivity.sharedPreferences.edit().putString(SplashActivity.USER_DATA, userJson).apply();
                                SplashActivity.sharedPreferences.edit().putBoolean(SplashActivity.IS_LOGIN, true).apply();
                                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                finishAffinity();
                            } else {
                                Toast.makeText(this, task2.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });


                    } else {
                        Toast.makeText(getApplicationContext(), "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}