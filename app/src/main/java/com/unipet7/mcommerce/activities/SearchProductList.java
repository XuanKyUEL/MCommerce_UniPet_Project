package com.unipet7.mcommerce.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.ProductAdapter;
import com.unipet7.mcommerce.databinding.ActivitySearchProductListBinding;
import com.unipet7.mcommerce.databinding.ActivitySupportContactBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchProductList extends AppCompatActivity {
    ActivitySearchProductListBinding binding;
    ProductAdapter adapter;
    ArrayList<Product> products;

    List<Integer> favList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchProductListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActionBar(binding.toolbarsearch);
        FireStoreClass fireStoreClass = new FireStoreClass();
        addEvents();
        initData();
        loadData();
    }
    private void initData() {
        products = new ArrayList<>();
        products.add(new Product(R.drawable.pate1, "Thức ăn mèo gâu gâu", 20000,3,3,32,20000));
        products.add(new Product(R.drawable.pate1, "Thức ăn mèo gâu gâu", 20000,3,3, 32, 20000));
        products.add(new Product(R.drawable.pate1, "Thức ăn mèo gâu gâu", 20000,3,3,20,30000));

        adapter = new ProductAdapter(products);
    }

    private void loadData() {
        binding.lvlProductList.setLayoutManager(new GridLayoutManager(this, 2));
        binding.lvlProductList.setAdapter(adapter);
        binding.lvlProductList.setHasFixedSize(true);
    }
    private void addEvents() {
        binding.arrangefilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();
            }

        });
        binding.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet2();
            }

        });
    }
    private void showBottomSheet() {
        Dialog dialog = new Dialog(SearchProductList.this);
        dialog.setContentView(R.layout.bottom_sheet_filter);


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        ImageView closeButton = dialog.findViewById(R.id.ic_close);
        closeButton.setOnClickListener(v -> dialog.dismiss());
    }
    private void showBottomSheet2() {
        Dialog dialog = new Dialog(SearchProductList.this);
        dialog.setContentView(R.layout.bottom_sheet_filter2);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        ImageView closeButton = dialog.findViewById(R.id.ic_close);
        closeButton.setOnClickListener(v -> dialog.dismiss());
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