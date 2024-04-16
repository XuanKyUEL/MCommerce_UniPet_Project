package com.unipet7.mcommerce.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;

import com.unipet7.mcommerce.databinding.ActivitySignInBinding;

public class SignIn extends BaseActivity {

    ActivitySignInBinding binding;

    public static final String SHARED_PREFS = "signUpInfo";
    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();

        // Lấy dữ liệu từ Intent khi hoạt động bắt đầu
        Intent intent = getIntent();
        if (intent != null) {
            String email = intent.getStringExtra(EMAIL_KEY);
            AppCompatEditText edtEmail = binding.edtEmailSignIn;
            edtEmail.setText(email);
        }
    }

    private void addEvents() {
        binding.tvSignUpCta.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        });
        binding.btnSignIn.setOnClickListener(v -> {
//            Intent intent = new Intent(this, Home.class);
//            startActivity(intent);
//            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}