package com.unipet7.mcommerce.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.databinding.ActivityMainBinding;
import com.unipet7.mcommerce.fragments.Fragment_Empty_Notification;
import com.unipet7.mcommerce.fragments.Home;
import com.unipet7.mcommerce.fragments.Profile;
import com.unipet7.mcommerce.fragments.fragment_blank_cart;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fabCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Base_Theme_UniPet);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mapping();
        replaceFragment(new Home());
        navigateFragment();
    }

    private void mapping() {
        bottomNavigationView = binding.bottomNavigationView;
        fabCart = binding.fabCart;
    }
    

    private void navigateFragment() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            if (item.getItemId() == R.id.home_icon_bottom)
                fragment = new Home();
            else if (item.getItemId() == R.id.account_icon_bottom)
                fragment = new Profile();
            else if (item.getItemId() == R.id.fav_icon_bottom)
                fragment = new Fragment_Empty_Notification();
            else if (item.getItemId() == R.id.product_icon_bottom)
                fragment = new Fragment_Empty_Notification();
            replaceFragment(fragment);
            return true;
        });
        fabCart.setOnClickListener(v -> {
            Fragment fragment = new fragment_blank_cart();
            replaceFragment(fragment);
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}