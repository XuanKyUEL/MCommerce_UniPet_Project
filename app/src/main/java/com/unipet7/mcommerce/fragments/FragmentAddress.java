package com.unipet7.mcommerce.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.AddressAdapter;
import com.unipet7.mcommerce.databinding.FragmentAddressBinding;
import com.unipet7.mcommerce.models.Addresses;

import java.util.ArrayList;

public class FragmentAddress extends Fragment {
    FragmentAddressBinding binding;
    AddressAdapter addressAdapter;
    ArrayList<Addresses> addresses;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentAddressBinding.inflate(inflater, container, false);
        setActionBar(binding.toolbaraddress);
        addEvents();
        addEvents1();
        initData();
        loadData();
        return binding.getRoot();

    }
    private void initData() {
        addresses = new ArrayList<>();
        addresses.add(new Addresses("Heineken","0856433569", "Binh Dinh", "65 Nguyen Chi Thanh"));
    }
    private void loadData() {
        addressAdapter = new AddressAdapter(requireContext(), addresses,R.layout.layout_address_list);
        binding.lvlAddress.setAdapter(addressAdapter);
    }

    private void addEvents1() {
        binding.btnAddaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment addAddressFragment = new FragmentAdressAdd();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, addAddressFragment);
                transaction.addToBackStack(null); // Thêm Fragment hiện tại vào back stack
                transaction.commit();
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
        binding.toolbaraddress.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
        binding.lvlAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(requireContext(), FragmentAddressEdit.class);
                startActivity(intent);
            }
        });

    }


}