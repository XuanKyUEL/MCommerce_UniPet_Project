package com.unipet7.mcommerce.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.databinding.ActivityProfileFunctionBinding;
import com.unipet7.mcommerce.fragments.FragmentAccount;
import com.unipet7.mcommerce.fragments.FragmentAddress;
import com.unipet7.mcommerce.fragments.FragmentAdressAdd;
import com.unipet7.mcommerce.fragments.FragmentHelpCenter;
import com.unipet7.mcommerce.fragments.FragmentHistoryOrders;
import com.unipet7.mcommerce.fragments.FragmentPaymentMethod;
import com.unipet7.mcommerce.fragments.Profile;

public class ProfileFunction extends AppCompatActivity {
    ActivityProfileFunctionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileFunctionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Get the function value from the Intent
        String function = getIntent().getStringExtra("function");

        // Determine which Fragment to show based on the function value
        Fragment fragment;
        if (function != null) {
            switch (function) {
                case "about_me":
                    fragment = new FragmentAccount();
                    break;
                case "address":
                    fragment = new FragmentAddress();
                    break;
                case "help_center":
                    fragment = new FragmentHelpCenter();
                    break;
                case "payment_method":
                    fragment = new FragmentPaymentMethod();
                    break;
                case "purchased_orders":
                    fragment = new FragmentHistoryOrders();
                    break;
                default:
                    fragment = new Profile();
                    break;
            }
        } else {
            fragment = new Profile();
        }

        // Replace the current Fragment with the selected Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(binding.fragmentContainer.getId(), fragment)
                .commit();
    }

}

