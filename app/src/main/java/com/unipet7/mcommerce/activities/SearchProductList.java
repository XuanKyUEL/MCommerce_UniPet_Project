package com.unipet7.mcommerce.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.databinding.ActivitySearchProductListBinding;
import com.unipet7.mcommerce.databinding.ActivitySupportContactBinding;

public class SearchProductList extends AppCompatActivity {
    ActivitySearchProductListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchProductListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActionBar(binding.toolbarsearch);
    }
    public void setActionBar(@Nullable Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Set icon arrow
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowTitleEnabled(false);;
}
}