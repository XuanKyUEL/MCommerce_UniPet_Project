package com.unipet7.mcommerce.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.databinding.ActivityDetailProductBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.utils.Constants;

public class DetailProduct extends BaseActivity {

    ActivityDetailProductBinding binding;

    TextView tvProductName, tvProductPrice, tvProductDescription, tvScore, tvNumOfRate;

    FireStoreClass fireStoreClass = new FireStoreClass();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mapping();
        getProductDetail();
    }

    private void mapping() {
        tvProductName = binding.txtProductNameDetails;
        tvProductPrice = binding.txtProductPriceDetail;
        tvProductDescription = binding.txtDescriptionOverall;
        tvScore = binding.txtScoreProductDetail;
        tvNumOfRate = binding.txtNumberOfRating;
    }

    private void getProductDetail() {
        // get product detail base on product id
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.PRODUCT_ID)) {
            int productId = intent.getIntExtra(Constants.PRODUCT_ID, -1);
            fireStoreClass.getProductDetailViaId(this, productId);
        }
    }

    public void loadProductDetail(Product product) {
        tvProductName.setText(product.getProductname());
        tvProductDescription.setText(product.getProductDescription());
        double price = product.getProductprice();
        String formattedPrice = String.format("%,.0f đ", price);
        tvProductPrice.setText(formattedPrice);
        tvScore.setText("4.5");
        tvNumOfRate.setText("  (130)");
    }
}