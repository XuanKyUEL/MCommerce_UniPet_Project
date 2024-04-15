package com.unipet7.mcommerce.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.databinding.ActivityHistoryOrderBinding;
import com.unipet7.mcommerce.fragments.HistoryOrder_All;
import com.unipet7.mcommerce.fragments.HistoryOrder_Cancel;
import com.unipet7.mcommerce.fragments.HistoryOrder_Confirm;
import com.unipet7.mcommerce.fragments.HistoryOrder_Delivery;
import com.unipet7.mcommerce.fragments.HistoryOrder_Received;

public class HistoryOrder extends AppCompatActivity {
    ActivityHistoryOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
        setActionBar(binding.toolbar);
    }
    public void setActionBar(@Nullable Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Set icon arrow
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fragmnent = null;
            if (v.equals(binding.btnAll))
                fragmnent = new HistoryOrder_All();
            else if (v.equals(binding.btnConfirmorder))
                fragmnent = new HistoryOrder_Confirm();
            else if (v.equals(binding.btnDeliverorder))
                fragmnent = new HistoryOrder_Delivery();
            else if (v.equals(binding.btnReceivedorder))
                fragmnent = new HistoryOrder_Received();
            else if (v.equals(binding.btnCancelorder))
                fragmnent = new HistoryOrder_Cancel();
            assert fragmnent != null;
            transaction.replace(R.id.containerLayout, fragmnent);
            transaction.commit();
        }
    };

    private void addEvents() {
        binding.btnAll.setOnClickListener(onClickListener);
        binding.btnConfirmorder.setOnClickListener(onClickListener);
        binding.btnDeliverorder.setOnClickListener(onClickListener);
        binding.btnReceivedorder.setOnClickListener(onClickListener);
        binding.btnCancelorder.setOnClickListener(onClickListener);

    }
}