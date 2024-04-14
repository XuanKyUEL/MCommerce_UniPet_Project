package com.unipet7.mcommerce.activities;

import static com.unipet7.mcommerce.activities.SignIn.EMAIL_KEY;
import static com.unipet7.mcommerce.activities.SignIn.PASSWORD_KEY;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
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
        createDialog();
        addEvents();
        mapping();
    }

    private void mapping() {
        edtEmail = binding.edtEmailSignUp;
        edtPassword = binding.edtPasswordSignUp;
        edtConfirmPassword = binding.rePwdEdtSignup;

        tilEmail = binding.tilEmailSignUp;
        tilPassword = binding.tilPasswordSignUp;
        tilConfirmPassword = binding.tilRePwdSignup;
    }

    private void createDialog() {
    }

    private void addEvents() {
        binding.tvSignInCta.setOnClickListener(v -> {
            finish();
        });

        binding.btnSignUp.setOnClickListener(v -> {
            if(edtEmail.getText().toString().isEmpty()) {
                tilEmail.setError("Email không được để trống");
            } else if (edtPassword.getText().toString().isEmpty()) {
                tilPassword.setError("Mật khẩu không được để trống");
            } else if (edtConfirmPassword.getText().toString().isEmpty()) {
                tilConfirmPassword.setError("Mật khẩu xác nhận không được để trống");
            } else if (edtEmail.getText().toString().isEmpty() && edtPassword.getText().toString().isEmpty() && edtConfirmPassword.getText().toString().isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin đăng ký", Toast.LENGTH_SHORT).show();
            }else {
                messageDialogAdapter = new MessageDialogAdapter(this);
                messageDialog = new MessageDialog("Đăng Ký Thành Công", "Chúc mừng bạn đã đăng ký tài khoản thành công", "Đăng Nhập", "Đóng");
                messageDialog.setCancelable(true);
                messageDialog.setNegativeClickListener(v1 -> {
                    messageDialogAdapter.dismissDialog();
                });
                messageDialog.setNegativeClickListener(v1 -> {
                    messageDialogAdapter.dismissDialog();
                });
                messageDialog.setPositiveClickListener(v1 -> {
                    navigateToSignIn();
                });
                messageDialogAdapter.showDialog(messageDialog);
            }
        });
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