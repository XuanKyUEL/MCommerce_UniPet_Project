package com.unipet7.mcommerce.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.activities.ProfileFunction;
import com.unipet7.mcommerce.databinding.FragmentAddressBinding;
import com.unipet7.mcommerce.databinding.FragmentEmptyNotificationBinding;

public class Fragment_Empty_Notification extends Fragment {
    FragmentEmptyNotificationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentEmptyNotificationBinding.inflate(inflater, container, false);
        setActionBar(binding.notybar);
        addEvents();
        return binding.getRoot();
    }

    private void addEvents() {
        binding.notybar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
        binding.btnNotihome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment homeFragment = new Home();

                // Lấy FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Thực hiện giao diện chuyển đổi Fragment
                fragmentManager.beginTransaction()
                        .replace(((ViewGroup) requireView().getParent()).getId(), homeFragment)
                        .addToBackStack(null)
                        .commit();
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