package com.unipet7.mcommerce.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.AdapterVoucher;
import com.unipet7.mcommerce.databinding.ActivityVoucherBinding;
import com.unipet7.mcommerce.models.Voucher;

import java.util.ArrayList;

public class VoucherActivity extends AppCompatActivity {
    ActivityVoucherBinding binding;
    ArrayList<Voucher> vouchers;
    AdapterVoucher adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadData();
        addEvents();
    }

    private void addEvents() {
        binding.btnAppVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy vị trí của voucher được chọn từ Adapter
                int selectedPosition = adapter.getSelectedPosition();
                if (selectedPosition != -1) {
                    // Lấy voucher code tại vị trí được chọn
                    String voucherCode = vouchers.get(selectedPosition).getTxtVoucherCode();
                    // Tạo Intent để trả về dữ liệu voucher code
                    Intent intent = new Intent();
                    intent.putExtra("voucher_code", voucherCode);
                    setResult(Activity.RESULT_OK, intent);
                    finish(); // Đóng VoucherActivity
                }
            }
        });


    }

    private void loadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        binding.rvDiscountVoucher.setLayoutManager(layoutManager);
        binding.rvDiscountVoucher.setHasFixedSize(true);
        vouchers = new ArrayList<>();
        vouchers.add(new Voucher("UNIPETBESTFRIEND30", "Giảm giá 30%, tối đa 300.000 đồng cho hóa đơn từ 100.000 đồng", 30.0));
        vouchers.add(new Voucher("UNIPETBESTFRIEND50", "Giảm giá 50%, tối đa 100.000 đồng cho hóa đơn từ 200.000 đồng", 50.0));
        vouchers.add(new Voucher("UNIPETBESTFRIEND20", "Giảm giá 20%, tối đa 50.000 đồng cho hóa đơn từ 100.000 đồng", 20.0));
        vouchers.add(new Voucher("UNIPETBESTFRIEND10", "Giảm giá 10%, tối đa 10.000 đồng cho hóa đơn từ 0 đồng", 10.0));
        adapter = new AdapterVoucher(getApplicationContext(),vouchers);
        binding.rvDiscountVoucher.setAdapter(adapter);
    }
}