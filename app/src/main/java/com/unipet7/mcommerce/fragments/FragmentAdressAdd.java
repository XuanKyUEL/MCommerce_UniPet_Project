package com.unipet7.mcommerce.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipet7.mcommerce.R;

import com.unipet7.mcommerce.adapters.MessageDialogAdapter;
import com.unipet7.mcommerce.databinding.FragmentAdressAddBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.Addresses;
import com.unipet7.mcommerce.models.MessageDialog;

import java.util.Objects;

public class FragmentAdressAdd extends Fragment {
    FragmentAdressAddBinding binding;

    MessageDialog messageDialog;

    FireStoreClass fireStoreClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentAdressAddBinding.inflate(inflater, container, false);
        setActionBar(binding.toolbaraddressadd);
        fireStoreClass = new FireStoreClass();
        addEvents();
        return binding.getRoot();

    }
    private void addEvents() {
        binding.toolbaraddressadd.setNavigationOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());
        binding.btnDone.setOnClickListener(v -> {
            boolean hasError = isHasError();
            if (!hasError) {
                try {
                    Addresses addresses = new Addresses(
                            binding.edtuser.getText().toString(),
                            binding.edtphone.getText().toString(),
                            binding.edtprovince.getText().toString(),
                            binding.edtstress.getText().toString()
                    );
                    fireStoreClass.addAddress(this,addresses);
                } catch (Exception e) {
                    Log.e("Error", Objects.requireNonNull(e.getMessage()));
                }
            }
        });
    }

    private boolean isHasError() {
        boolean hasError = false;

        if (binding.edtuser.getText().toString().isEmpty()) {
            binding.edtuser.setError("Tên không được để trống");
            return true;
        }

        if (binding.edtphone.getText().toString().isEmpty()) {
            binding.edtphone.setError("Số điện thoại không được để trống");
            return true;
        }

        if (binding.edtprovince.getText().toString().isEmpty()) {
            binding.edtprovince.setError("Huyện và Tỉnh không được để trống");
            return true;
        }

        if (binding.edtstress.getText().toString().isEmpty()) {
            binding.edtstress.setError("Số nhà không được để trống");
            return true;
        }
        return hasError;
    }

    public void setActionBar(@Nullable Toolbar toolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    public void addAddressSuccess() {
        MessageDialogAdapter messageDialogAdapter = new MessageDialogAdapter(requireContext());
        messageDialog = new MessageDialog("Cập nhật thành công", "Bạn đã thêm địa chỉ mới thành công", null, "Đóng");
        messageDialog.setCancelable(true);
        messageDialog.setNegativeClickListener(v1 -> {
            messageDialogAdapter.dismissDialog();
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });
        messageDialog.hasPositiveBtn = false;
        messageDialogAdapter.showDialog(messageDialog);
    }
}