package com.unipet7.mcommerce.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.activities.MainActivity;
import com.unipet7.mcommerce.databinding.FragmentCartOverallBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;

public class CartOverall extends Fragment {

    public boolean loadCart;
    FragmentCartOverallBinding binding;


    public CartOverall() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartOverallBinding.inflate(inflater, container, false);
        FireStoreClass fireStoreClass = new FireStoreClass();
        fireStoreClass.getCartItemCount(this);
        if (getActivity() instanceof MainActivity mainActivity) {
            mainActivity.bottomNavigationView.getMenu().getItem(2).setChecked(true);
        }
        return binding.getRoot();
    }

    public void loadUi(boolean loadCart) {
        this.loadCart = loadCart;
        Fragment fragment;
        if (loadCart) {
            fragment = new CartList();
        } else {
            fragment = new BlankCart();
        }
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_cart_container, fragment).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        FireStoreClass fireStoreClass = new FireStoreClass();
        fireStoreClass.getCartItemCount(this);
    }
}