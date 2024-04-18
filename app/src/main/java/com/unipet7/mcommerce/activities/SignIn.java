package com.unipet7.mcommerce.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Patterns;

import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.databinding.ActivitySignInBinding;

public class SignIn extends BaseActivity {

    ActivitySignInBinding binding;

    AppCompatEditText edtEmail, edtPassword;
    TextInputLayout tilEmail, tilPassword;

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
        mapping();
        resetErrorUI();
        addEvents();
        Intent intent = getIntent();
        if (intent != null) {
            String email = intent.getStringExtra(EMAIL_KEY);
            AppCompatEditText edtEmail = binding.edtEmailSignIn;
            edtEmail.setText(email);
        }
    }

    private void resetErrorUI() {
    }

    private void mapping() {
        edtEmail = binding.edtEmailSignIn;
        edtPassword = binding.edtPasswordSignIn;
        tilEmail = binding.tilEmailSignIn;
        tilPassword = binding.tilPasswordSignIn;
    }

    private void addEvents() {
        binding.tvSignUpCta.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        });
        binding.btnSignIn.setOnClickListener(v -> {
            if (isSignInValid()) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                signInUser(email, password);
            }
        });
    }

    private void setErrorWithIcon(TextInputLayout textInputLayout, String error, int errorIconResId) {
        SpannableString spannableString = new SpannableString("  " + error);

        // Tạo một Drawable từ icon lỗi
        Drawable errorIcon = getResources().getDrawable(errorIconResId);
        errorIcon.setBounds(0, 0, errorIcon.getIntrinsicWidth(), errorIcon.getIntrinsicHeight());

        // Tạo một ImageSpan từ Drawable
        ImageSpan imageSpan = new ImageSpan(errorIcon, ImageSpan.ALIGN_BOTTOM);

        // Thêm ImageSpan vào đầu SpannableString
        spannableString.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Đặt SpannableString làm thông báo lỗi cho TextInputLayout
        textInputLayout.setError(spannableString);
    }

    private boolean isSignInValid() {
        if (edtEmail.getText().toString().trim().isEmpty()) {
            tilEmail.setErrorIconDrawable(null);
            setErrorWithIcon(tilEmail, "Email không được để trống", R.drawable.error_input_icon);
            edtEmail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString().trim()).matches()) {
            tilEmail.setErrorIconDrawable(null);
            setErrorWithIcon(tilEmail, "Email không hợp lệ", R.drawable.error_input_icon);
            edtEmail.requestFocus();
            return false;
        } else {
            tilEmail.setError(null);
        }

        if (edtPassword.getText().toString().trim().isEmpty()) {
            tilPassword.setErrorIconDrawable(null);
            setErrorWithIcon(tilPassword, "Mật khẩu không được để trống", R.drawable.error_input_icon);
            edtPassword.requestFocus();
            return false;
        } else {
            tilPassword.setError(null);
        }
        return true;
    }

    private void signInUser(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    } else {
                        tilEmail.setErrorIconDrawable(null);
                        setErrorWithIcon(tilEmail, "Email hoặc mật khẩu không đúng", R.drawable.error_input_icon);
                        edtEmail.requestFocus();
                    }
                });
    }
}