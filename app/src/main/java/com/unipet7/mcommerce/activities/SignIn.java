package com.unipet7.mcommerce.activities;

import android.content.Intent;
import android.os.Bundle;

import com.unipet7.mcommerce.databinding.ActivitySignInBinding;

public class SignIn extends BaseActivity {

    ActivitySignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
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
        });
    }
}