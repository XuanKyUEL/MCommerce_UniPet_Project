package com.unipet7.mcommerce.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.databinding.FragmentPaymentMethodBinding;

public class FragmentPaymentMethod extends Fragment {
    FragmentPaymentMethodBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentPaymentMethodBinding.inflate(inflater, container, false);
        setActionBar(binding.toolbar);
        chooseMethod();
        addEvents();
        return binding.getRoot();

    }

    private void chooseMethod() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        // Đọc giá trị RadioButton đã lưu và thiết lập RadioButton được chọn
        int savedCheckedRadioButtonId = sharedPreferences.getInt("checkedRadioButtonId", R.id.paymentCod);
        RadioButton savedCheckedRadioButton = binding.paymentRadio.findViewById(savedCheckedRadioButtonId);
        savedCheckedRadioButton.setChecked(true);

        // Lắng nghe sự kiện thay đổi của RadioGroup
        binding.paymentRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Lưu giá trị RadioButton được chọn vào SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("checkedRadioButtonId", checkedId);
                editor.apply();
            }
        });

        binding.btnChoosePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }

    public void setActionBar(@Nullable Toolbar toolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
        actionBar.setDisplayShowTitleEnabled(false);
    }
    private void addEvents() {
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
}}