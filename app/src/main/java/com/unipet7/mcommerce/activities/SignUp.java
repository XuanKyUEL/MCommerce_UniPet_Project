package com.unipet7.mcommerce.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.MessageDialogAdapter;
import com.unipet7.mcommerce.databinding.ActivitySignUpBinding;

public class SignUp extends BaseActivity {

    ActivitySignUpBinding binding;
    MessageDialogAdapter messageDialogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        configTermsAndConditions();
        addEvents();


        messageDialogAdapter = new MessageDialogAdapter(this,"Đăng ký thành công", "Chúc mừng bạn đã đăng ký tài khoản thành công!", "Đăng nhập ngay", "Đóng");
        messageDialogAdapter.setCancelable(true);
        messageDialogAdapter.setHasPositiveBtn(true);
        messageDialogAdapter.setHasNegativeBtn(true);
    }

    private void addEvents() {
        binding.tvSignInCta.setOnClickListener(v -> {
            finish();
        });

        binding.btnSignUp.setOnClickListener(v -> {
            messageDialogAdapter.startDialog();
            messageDialogAdapter.messageDialogNegativeBtnListener();
            messageDialogAdapter.messageDialogPositiveBtnListener(dialog -> {
                Intent intent = new Intent(this, SignIn.class);
                startActivity(intent);
                finish();
            });
        });
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