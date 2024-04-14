package com.unipet7.mcommerce.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.AddressAdapter;
import com.unipet7.mcommerce.databinding.ActivityProfileAddressBinding;
import com.unipet7.mcommerce.models.Addresses;

import java.util.ArrayList;

public class ProfileAddress extends AppCompatActivity {
    ActivityProfileAddressBinding binding;
    AddressAdapter addressAdapter;
    ArrayList<Addresses> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_address);
        binding = ActivityProfileAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActionBar(binding.toolbaraddress);
        initData();
        loadData();
        addEvents();
    }

    private void initData() {
        addresses = new ArrayList<>();
        addresses.add(new Addresses("Heineken","0856433569", "Binh Dinh", "65 Nguyen Chi Thanh"));
    }
    private void loadData() {
        addressAdapter = new AddressAdapter(ProfileAddress.this, addresses,R.layout.layout_address_list);
        binding.lvlAddress.setAdapter(addressAdapter);
    }
    private void addEvents() {
        binding.btnAddaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileAddress.this, ProfileAddress_Add.class);
                startActivity(intent);
            }
        });
        binding.lvlAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProfileAddress.this, ProfileAddress_Edit.class);
                startActivity(intent);
            }
        });

    }
    public void setActionBar(@Nullable Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Set icon arrow
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowTitleEnabled(false);
    }
}