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
import com.unipet7.mcommerce.databinding.FragmentAddressEditBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.Addresses;
import com.unipet7.mcommerce.models.MessageDialog;
import com.unipet7.mcommerce.utils.Constants;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddressEdit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddressEdit extends Fragment {
    FragmentAddressEditBinding binding;

    FireStoreClass fireStoreClass = new FireStoreClass();

    Addresses address;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentAddressEdit() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAddressEdit.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAddressEdit newInstance(String param1, String param2) {
        FragmentAddressEdit fragment = new FragmentAddressEdit();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddressEditBinding.inflate(inflater, container, false);
        assert getArguments() != null;
        this.address = getArguments().getParcelable(Constants.ADDRESS);
        assert address != null;
        loadAddressData(address);
        setActionBar(binding.toolbaraddessedit);
        addEvents();
        return binding.getRoot();
    }

    private void loadAddressData(Addresses address) {
        binding.edtaddressname.setText(address.getName());
        binding.edtaddressphone.setText(address.getPhonenumber());
        binding.edtaddressprovince.setText(address.getProvince());
        binding.edtaddressstress.setText(address.getStreet());
    }

    private void addEvents() {
        binding.toolbaraddessedit.setNavigationOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());
        binding.btnDel.setOnClickListener(v -> {
            MessageDialog messageDialog = new MessageDialog("Xác nhận xóa", "Bạn có chắc chắn muốn xóa địa chỉ này không?", "Xóa", "Hủy");
            MessageDialogAdapter messageDialogAdapter = new MessageDialogAdapter(requireContext());
            messageDialog.setCancelable(true);
            messageDialog.setPositiveClickListener(v1 -> {
                messageDialogAdapter.dismissDialog();
                fireStoreClass.deleteAddress(this, address.getAddressId());
            });
            messageDialog.setNegativeClickListener(v1 -> messageDialogAdapter.dismissDialog()); // Dismiss dialog
            messageDialogAdapter.showDialog(messageDialog);
        });
        binding.btnDoneedit.setOnClickListener(v -> {
            boolean hasError = isHasError();
            if (!hasError) {
                try {
                    Addresses addresses = new Addresses(
                            binding.edtaddressname.getText().toString(),
                            binding.edtaddressphone.getText().toString(),
                            binding.edtaddressprovince.getText().toString(),
                            binding.edtaddressstress.getText().toString()
                    );
                    fireStoreClass.updateAddress(this, addresses, addresses.getAddressId());
                } catch (Exception e) {
                    Log.e("Error", Objects.requireNonNull(e.getMessage()));
                }
            }
        });
    }

    private boolean isHasError() {
        boolean hasError = false;

        if (binding.edtaddressname.getText().toString().isEmpty()) {
            binding.edtaddressname.setError("Tên không được để trống");
            return true;
        }

        if (binding.edtaddressphone.getText().toString().isEmpty()) {
            binding.edtaddressphone.setError("Số điện thoại không được để trống");
            return true;
        }

        if (binding.edtaddressprovince.getText().toString().isEmpty()) {
            binding.edtaddressprovince.setError("Huyện và Tỉnh không được để trống");
            return true;
        }

        if (binding.edtaddressstress.getText().toString().isEmpty()) {
            binding.edtaddressstress.setError("Số nhà không được để trống");
            return true;
        }
        return hasError;
    }

    public void setActionBar(@Nullable Toolbar toolbar) {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    public void deleteAddressSuccess() {
        MessageDialog messageDialog = new MessageDialog("Xóa địa chỉ thành công", "Địa chỉ đã được xóa khỏi danh sách", null, "Đóng");
        MessageDialogAdapter messageDialogAdapter = new MessageDialogAdapter(requireContext());
        messageDialog.hasPositiveBtn = false;
        messageDialog.setCancelable(true);
        messageDialog.setNegativeClickListener(v1 -> {
            messageDialogAdapter.dismissDialog();
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });
        messageDialogAdapter.showDialog(messageDialog);
    }

    public void updateAddressSuccess() {
        MessageDialog messageDialog = new MessageDialog("Cập nhật thành công", "Địa chỉ đã được cập nhật thành công", null, "Đóng");
        MessageDialogAdapter messageDialogAdapter = new MessageDialogAdapter(requireContext());
        messageDialog.hasPositiveBtn = false;
        messageDialog.setCancelable(true);
        messageDialog.setNegativeClickListener(v1 -> {
            messageDialogAdapter.dismissDialog();
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });
        messageDialogAdapter.showDialog(messageDialog);
    }
}