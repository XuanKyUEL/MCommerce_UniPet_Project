package com.unipet7.mcommerce.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
    }

    private void loadData() {
        intData();
        adapter = new AdapterVoucher(this, vouchers, R.layout.voucher_item);
        binding.lvDiscountVoucher.setAdapter(adapter);
    }

    private void intData() {
        vouchers = new ArrayList<>();
        vouchers.add(new Voucher("UNIPETBESTFRIEND30", "Giảm giá 30%, tối đa 300.000 đồng cho hóa đơn từ 100.000 đồng", 30.0));
        vouchers.add(new Voucher("UNIPETBESTFRIEND50", "Giảm giá 50%, tối đa 100.000 đồng cho hóa đơn từ 200.000 đồng", 50.0));
        vouchers.add(new Voucher("UNIPETBESTFRIEND20", "Giảm giá 20%, tối đa 50.000 đồng cho hóa đơn từ 100.000 đồng", 20.0));
        vouchers.add(new Voucher("UNIPETBESTFRIEND10", "Giảm giá 10%, tối đa 100.000 đồng cho hóa đơn từ 0 đồng", 10.0));


    }
}