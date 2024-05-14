package com.unipet7.mcommerce.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.AddressAdapter;
import com.unipet7.mcommerce.utils.DetailCheckoutHelper;
import com.unipet7.mcommerce.databinding.FragmentAddressBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.Addresses;
import com.unipet7.mcommerce.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class FragmentAddress extends Fragment {
    FragmentAddressBinding binding;
    AddressAdapter addressAdapter;
    List<Addresses> addresses;

    FireStoreClass fireStoreClass;

    Bundle bundle;

    private boolean isFromCheckout = false;

    private DetailCheckoutHelper detailCheckoutHelper;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentAddressBinding.inflate(inflater, container, false);
        setActionBar(binding.toolbaraddress);
        addresses = new ArrayList<>();
        fireStoreClass = new FireStoreClass();
        fireStoreClass.GetUserAddresses(this);
        this.bundle = getArguments();
        if (bundle != null) {
            isFromCheckout = bundle.getBoolean(Constants.IS_FROM_CHECKOUT, false);
        }
        if (isFromCheckout) {
            binding.btnAddaddress.setVisibility(View.GONE);
        } else {
            binding.btnAddaddress.setVisibility(View.VISIBLE);
        }

        addEvents();
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof DetailCheckoutHelper) {
            detailCheckoutHelper = (DetailCheckoutHelper) context;
        }
    }

    public void setActionBar(@Nullable Toolbar toolbar) {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
        actionBar.setDisplayShowTitleEnabled(false);
    }
    private void addEvents() {
        binding.toolbaraddress.setNavigationOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());
        if (isFromCheckout) {
            binding.lvlAddress.setOnItemClickListener((parent, view, position, id) -> {
                Addresses address = addresses.get(position);
                if (detailCheckoutHelper != null) {
                    detailCheckoutHelper.onAddressSelected(address);
                    requireActivity().getOnBackPressedDispatcher().onBackPressed();
                }
            });
        }
        binding.btnAddaddress.setOnClickListener(v -> {
            Fragment addAddressFragment = new FragmentAdressAdd();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, addAddressFragment);
            transaction.addToBackStack(null); // Thêm Fragment hiện tại vào back stack
            transaction.commit();
        });
    }


    public void loadAddresses(List<Addresses> addresses) {
        if (addresses != null) {
            this.addresses.addAll(addresses);
            Bundle arg = getArguments();
            boolean isFromCheckout = arg != null && arg.getBoolean(Constants.IS_FROM_CHECKOUT, false);
            addressAdapter = new AddressAdapter(requireContext(), addresses,R.layout.layout_address_list, isFromCheckout);
            binding.lvlAddress.setAdapter(addressAdapter);
        }
    }
}