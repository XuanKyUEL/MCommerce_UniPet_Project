package com.unipet7.mcommerce.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipet7.mcommerce.R;

import com.unipet7.mcommerce.adapters.MessageDialogAdapter;
import com.unipet7.mcommerce.databinding.FragmentAdressAddBinding;
import com.unipet7.mcommerce.models.MessageDialog;

public class FragmentAdressAdd extends Fragment {
    FragmentAdressAddBinding binding;

    MessageDialog messageDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentAdressAddBinding.inflate(inflater, container, false);
        setActionBar(binding.toolbaraddressadd);
        addEvents();
        addEvents1();
        return binding.getRoot();

    }
    private void addEvents() {
        binding.toolbaraddressadd.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }
    private void addEvents1() {
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
                MessageDialogAdapter messageDialogAdapter = new MessageDialogAdapter(requireContext());
                messageDialog = new MessageDialog("Cập nhật thành công", "Bạn đã thêm địa chỉ mới thành công", "Quay lại", "Đóng");
                messageDialog.setCancelable(true);
                messageDialog.setNegativeClickListener(v1 -> {
                    messageDialogAdapter.dismissDialog();
                });
                messageDialog.setPositiveClickListener(v1 -> {
                    getActivity().finish();
                });
                messageDialogAdapter.showDialog(messageDialog);
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
}