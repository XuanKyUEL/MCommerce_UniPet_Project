package com.unipet7.mcommerce.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.AdapterVoucher;
import com.unipet7.mcommerce.databinding.ActivityVoucherBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.Voucher;
import com.unipet7.mcommerce.utils.Constants;

import java.util.ArrayList;

public class VoucherActivity extends AppCompatActivity {
    ActivityVoucherBinding binding;
    ArrayList<Voucher> vouchers;
    AdapterVoucher adapter;
    FireStoreClass fireStoreClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fireStoreClass = new FireStoreClass();
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
        binding.btnAppVoucher.setOnClickListener(v -> {
            int selectedPosition = adapter.getSelectedPosition();
            Log.i("VoucherActivity", "addEvents: " + selectedPosition);
            if (selectedPosition != -1) {
                Intent intent = new Intent();
                intent.putExtra(Constants.VOUCHER, vouchers.get(selectedPosition));
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        binding.toolbarall.setNavigationOnClickListener(v -> {
            Log.i("VoucherActivity", "addEvents: ");
            finish();
        });
    }
}
