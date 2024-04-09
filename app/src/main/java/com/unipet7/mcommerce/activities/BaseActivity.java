package com.unipet7.mcommerce.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.unipet7.mcommerce.adapters.MessageDialogAdapter;
import com.unipet7.mcommerce.databinding.ActivityBaseBinding;
import com.unipet7.mcommerce.models.MessageDialog;

public class BaseActivity extends AppCompatActivity {

    ActivityBaseBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}