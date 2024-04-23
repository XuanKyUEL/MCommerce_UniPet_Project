package com.unipet7.mcommerce.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.databinding.ActivityNotificationBinding;
import com.unipet7.mcommerce.databinding.ActivityPaymentMethodDetailsBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.User;

import java.util.ArrayList;

public class PaymentMethodDetails extends AppCompatActivity {
    ActivityPaymentMethodDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentMethodDetailsBinding.inflate(getLayoutInflater());
        FireStoreClass frs = new FireStoreClass();
        setContentView(binding.getRoot());
    }

    private void addAddress(User user) {
        binding.txtUserName.setText(user.getName());
        binding.txtAddressStreetName.setText(user.getEmail());
        binding.txtOtherAddressInfo.setText(user.getEmail());
    }
}