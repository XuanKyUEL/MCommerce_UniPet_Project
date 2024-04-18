package com.unipet7.mcommerce.activities;

import static com.unipet7.mcommerce.activities.SignIn.EMAIL_KEY;
import static com.unipet7.mcommerce.activities.SignIn.PASSWORD_KEY;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.MessageDialogAdapter;
import com.unipet7.mcommerce.databinding.ActivitySignUpBinding;
import com.unipet7.mcommerce.models.MessageDialog;

public class SignUp extends BaseActivity {

    ActivitySignUpBinding binding;
    MessageDialogAdapter messageDialogAdapter;

    MessageDialog messageDialog;

    AppCompatEditText edtEmail, edtPassword, edtConfirmPassword;

    TextInputLayout tilEmail, tilPassword, tilConfirmPassword;

    boolean isTermsAndConditionsChecked = false;



    public static final String SHARED_PREFS = "signUpInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configTermsAndConditions();
        mapping();
        resetErrorUI();
        addEvents();
    }

    private void resetErrorUI() {
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilEmail.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // do nothing
            }
        });
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilConfirmPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void mapping() {
        edtEmail = binding.edtEmailSignUp;
        edtPassword = binding.edtPasswordSignUp;
        edtConfirmPassword = binding.rePwdEdtSignup;

        tilEmail = binding.tilEmailSignUp;
        tilPassword = binding.tilPasswordSignUp;
        tilConfirmPassword = binding.tilRePwdSignup;
    }


    private void addEvents() {
        binding.tvSignInCta.setOnClickListener(v -> {
            finish();
        });

        binding.btnSignUp.setOnClickListener(v -> {
            if (isSignUpValid()) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                signUpUser(email, password);
            }
        });
    }

    private boolean isSignUpValid() {
        if (edtEmail.getText().toString().isEmpty()) {
            tilEmail.setError("Email không được để trống");
            edtEmail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
            tilEmail.setError("Email không hợp lệ");
            edtEmail.requestFocus();
            return false;
        } else {
            tilEmail.setError(null);
        }

        if (edtPassword.getText().toString().isEmpty()) {
            tilPassword.setError("Mật khẩu không được để trống");
            edtPassword.requestFocus();
            return false;
        } else if (edtPassword.getText().toString().length() < 8) {
            tilPassword.setError("Mật khẩu phải có ít nhất 8 ký tự");
            edtPassword.requestFocus();
            return false;
        } else {
            tilPassword.setError(null);
        }

        if (edtConfirmPassword.getText().toString().isEmpty()) {
            tilConfirmPassword.setError("Vui lòng xác nhận mật khẩu");
            edtConfirmPassword.requestFocus();
            return false;
        } else if (!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
            tilConfirmPassword.setError("Mật khẩu không khớp");
            edtConfirmPassword.requestFocus();
            return false;
        } else {
            tilConfirmPassword.setError(null);
        }

        if (binding.rbTerms.isChecked()) {
            isTermsAndConditionsChecked = true;
        } else {
            Toast.makeText(this, "Vui lòng đồng ý với Điều Khoản và Dịch Vụ", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void signUpUser(String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        signUpDialog("Đăng ký thành công", "Chúc mừng bạn đã đăng ký tài khoản thành công", "Đăng nhập ngay", "Đóng");
                    } else {
                        signUpDialog("Đăng ký thất bại", "Đăng ký tài khoản không thành công", "", "Đóng");
                    }
                });
    }

    private void signUpDialog(String title, String message, String positiveText, String negativeText) {
        messageDialogAdapter = new MessageDialogAdapter(this);
        messageDialog = new MessageDialog(title, message, positiveText, negativeText);
        messageDialog.setCancelable(true);
        messageDialog.setNegativeClickListener(v1 -> {
            messageDialogAdapter.dismissDialog();
        });
        messageDialog.hasNegativeBtn = !positiveText.isEmpty();
        if (positiveText.isEmpty()) {
            messageDialog.hasPositiveBtn = false;
        } else {
            messageDialog.setPositiveClickListener(v1 -> {
                navigateToSignIn();
            });
        }
        messageDialogAdapter.showDialog(messageDialog);
    }

    private void navigateToSignIn() {
        // Lưu dữ liệu vào SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL_KEY, edtEmail.getText().toString());
        editor.putString(PASSWORD_KEY, edtPassword.getText().toString());
        editor.apply();

        // Chuyển người dùng sang hoạt động SignIn và truyền dữ liệu qua Intent
        Intent intent = new Intent(this, SignIn.class);
        intent.putExtra(EMAIL_KEY, edtEmail.getText().toString());
        intent.putExtra(PASSWORD_KEY, edtPassword.getText().toString());
        startActivity(intent);
        finish();
    }

    private void configTermsAndConditions() {
        // Tạo một SpannableString từ chuỗi nguồn
        String fullText = "Bằng cách nhấn nút Đăng Ký, bạn đã đồng ý với các Điều Khoản và Dịch Vụ của UniPet";
        SpannableString spannableString = new SpannableString(fullText);

// Tạo một UnderlineSpan để gạch chân cho chuỗi
        UnderlineSpan underlineSpan = new UnderlineSpan();

// Tạo một ForegroundColorSpan để thay đổi màu sắc của chuỗi
// Sử dụng màu brandColor của bạn, giả sử là R.color.brandColor
        Context context = this.binding.getRoot().getContext();
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.brandPrimary));

// Tìm vị trí bắt đầu và kết thúc của chuỗi "Điều Khoản và Dịch Vụ" trong chuỗi nguồn
        int start = fullText.indexOf("Điều Khoản và Dịch Vụ");
        int end = start + "Điều Khoản và Dịch Vụ".length();

// Áp dụng UnderlineSpan và ForegroundColorSpan cho SpannableString
// SPAN_EXCLUSIVE_EXCLUSIVE nghĩa là span sẽ không mở rộng đến text được thêm vào sau này
        spannableString.setSpan(underlineSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(colorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

// Đặt SpannableString cho TextView
        TextView termsAndConditions = binding.tvTerms;
        termsAndConditions.setText(spannableString);
    }
}