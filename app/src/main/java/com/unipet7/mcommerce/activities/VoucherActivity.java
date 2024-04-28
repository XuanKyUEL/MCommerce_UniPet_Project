package com.unipet7.mcommerce.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.AdapterVoucher;
import com.unipet7.mcommerce.databinding.ActivityVoucherBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.Voucher;

import java.util.ArrayList;

public class VoucherActivity extends AppCompatActivity {
    ActivityVoucherBinding binding;
    ArrayList<Voucher> vouchers;
    AdapterVoucher adapter;
    FireStoreClass fireStoreClass; // Thêm FireStoreClass để lấy dữ liệu từ Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fireStoreClass = new FireStoreClass(); // Khởi tạo FireStoreClass
        loadData();
        addEvents();
        setActionBar(binding.toolbarall);
    }
    public void setActionBar(@Nullable Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void loadData() {
        fireStoreClass.getAllVouchers(new FireStoreClass.OnVoucherListListener() {
            @Override
            public void onSuccess(ArrayList<Voucher> voucherList) {
                vouchers = voucherList;
                adapter = new AdapterVoucher(VoucherActivity.this, vouchers);
                binding.rvDiscountVoucher.setLayoutManager(new LinearLayoutManager(VoucherActivity.this));
                binding.rvDiscountVoucher.setAdapter(adapter);
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(VoucherActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addEvents() {
        binding.btnAppVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPosition = adapter.getSelectedPosition();
                if (selectedPosition != -1) {
                    String voucherCode = vouchers.get(selectedPosition).getVoucher_code();
                    Double voucherNumb = vouchers.get(selectedPosition).getVoucher_numb();
                    Double voucherMaxDiscount = vouchers.get(selectedPosition).getVoucher_max_discount();
                    Double voucherMiniumValue = vouchers.get(selectedPosition).getVoucher_minium_value();
                    Intent intent = new Intent();
                    intent.putExtra("voucher_code", voucherCode);
                    intent.putExtra("voucher_numb", voucherNumb);
                    intent.putExtra("voucher_max", voucherMaxDiscount);
                    intent.putExtra("voucher_minium_value", voucherMiniumValue);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
