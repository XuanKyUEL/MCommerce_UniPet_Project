package com.unipet7.mcommerce.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.NotiAdapter;
import com.unipet7.mcommerce.databinding.ActivityNotificationBinding;
import com.unipet7.mcommerce.databinding.ActivityProfileFunctionBinding;
import com.unipet7.mcommerce.models.Notice;

import java.util.ArrayList;

public class Notification extends AppCompatActivity {
    ActivityNotificationBinding binding;
    ArrayList<Notice> notices;
    NotiAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setActionBar(binding.notybar);
        addEvents();
        loadNewNotice();
        loadOldNotice();
        setContentView(binding.getRoot());
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
    private void addEvents() {
        binding.notybar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadNewNotice() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        binding.rvNewNoti.setLayoutManager(gridLayoutManager);
        binding.rvNewNoti.getRecycledViewPool();
        binding.rvNewNoti.setHasFixedSize(true);

        notices = new ArrayList<>();
        notices.add(new Notice("KHUYẾN MÃI TƯNG BỪNG !!!", "UniPet ưu đãi khách hàng nhân dịp 30/4-1/5, miễn phí vận chuyển!", "20 phút"));
        notices.add(new Notice("Đặt hàng thành công", "Đơn hàng của bạn đã được tạo thành công, thời gian dự kiến giao hàng từ 20-21/04/2024", "43 phút"));
        adapter = new NotiAdapter(this, notices);
        binding.rvNewNoti.setAdapter(adapter);

    }

    private void loadOldNotice() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        binding.rvOldNoti.setLayoutManager(gridLayoutManager);
        binding.rvOldNoti.getRecycledViewPool();
        binding.rvOldNoti.setHasFixedSize(true);

        notices = new ArrayList<>();
        notices.add(new Notice("KHUYẾN MÃI TƯNG BỪNG !!!", "UniPet ưu đãi khách hàng nhân dịp Giỗ tổ Hùng Vương, voucher ngập tràng!", "7 ngày trước"));
        notices.add(new Notice("Giao hàng thành công", "Đơn hàng đã được giao đến tay bạn thành công.", "8 ngày trước"));
        adapter = new NotiAdapter(this, notices);
        binding.rvOldNoti.setAdapter(adapter);

    }
}