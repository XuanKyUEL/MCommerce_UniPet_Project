package com.unipet7.mcommerce.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.unipet7.mcommerce.adapters.MessageDialogAdapter;
import com.unipet7.mcommerce.databinding.ActivityBaseBinding;
import com.unipet7.mcommerce.models.MessageDialog;

public class BaseActivity extends AppCompatActivity {

    ActivityBaseBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void setErrorWithIcon(TextInputLayout textInputLayout, String error, int errorIconResId) {
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

}