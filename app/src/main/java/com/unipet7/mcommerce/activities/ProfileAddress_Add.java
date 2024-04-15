package com.unipet7.mcommerce.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.MessageDialogAdapter;
import com.unipet7.mcommerce.databinding.ActivityProfileAddressAddBinding;
import com.unipet7.mcommerce.databinding.ActivityProfileAddressBinding;
import com.unipet7.mcommerce.models.MessageDialog;

public class ProfileAddress_Add extends AppCompatActivity {
    ActivityProfileAddressAddBinding binding;
    MessageDialogAdapter messageDialogAdapter;

    MessageDialog messageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_address);
        binding = ActivityProfileAddressAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActionBar(binding.toolbaradd);
        addEvents();
    }

    private void addEvents() {
        binding.btnDone.setOnClickListener(v -> {
            boolean hasError = false;

            if (binding.edtuser.getText().toString().isEmpty()) {
                binding.edtuser.setError("Tên không được để trống");
                hasError = true;
            }

            if (binding.edtphone.getText().toString().isEmpty()) {
                binding.edtphone.setError("Số điện thoại không được để trống");
                hasError = true;
            }

            if (binding.edtprovince.getText().toString().isEmpty()) {
                binding.edtprovince.setError("Huyện và Tỉnh không được để trống");
                hasError = true;
            }

            if (binding.edtstress.getText().toString().isEmpty()) {
                binding.edtstress.setError("Số nhà không được để trống");
                hasError = true;
            }
            if (hasError) {
                // Có lỗi, không thực hiện các thành phần khác
            } else {
                MessageDialogAdapter messageDialogAdapter = new MessageDialogAdapter(this);
                messageDialog = new MessageDialog("Cập nhật thành công", "Bạn đã thêm địa chỉ mới thành công", "Quay lại", "Đóng");
                messageDialog.setCancelable(true);
                messageDialog.setNegativeClickListener(v1 -> {
                    messageDialogAdapter.dismissDialog();
                });
                messageDialog.setPositiveClickListener(v1 -> {
                    finish();
                });
                messageDialogAdapter.showDialog(messageDialog);
            }
        });
    }

    public void setActionBar(@Nullable Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Set icon arrow
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowTitleEnabled(false);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Go back to the previous screen
        }
        return super.onOptionsItemSelected(item);
    }
}