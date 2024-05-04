package com.unipet7.mcommerce.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.ProductAdapter;
import com.unipet7.mcommerce.databinding.ActivityDetailProductBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.utils.Constants;
import com.unipet7.mcommerce.utils.LoadingDialog;

import java.util.ArrayList;

public class DetailProduct extends BaseActivity {

    ActivityDetailProductBinding binding;

    TextView tvProductName, tvProductPrice, tvProductDescription, tvScore, tvNumOfRate, tvProductPresale, tvSalePercent;

    ImageView ivProductImage, imvSalePercent;

    CheckBox cbFavorite;
    Button btnAddcart;

    FireStoreClass fireStoreClass = new FireStoreClass();

    Product product;
    ProductAdapter adapter;
    public ArrayList<Product> allProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mapping();
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.PRODUCT_ID)) {
            this.product = new Product();
            int productId = intent.getIntExtra(Constants.PRODUCT_ID, -1);
            fireStoreClass.getProductDetailViaId(this, productId);
            this.product.setProductId(productId);
            Log.d("DetailProduct", "Product: " + product);
        }
        loadProductDetail();
        addEvents();
    }
    private void loadProductDetail() {
        LoadingDialog ldDialog1 = new LoadingDialog();
        ldDialog1.showLoadingDialog(this);
        FireStoreClass fireStoreClass = new FireStoreClass();
        fireStoreClass.getDetailProducts(this, allProducts);
        ldDialog1.dissmis();
    }
    public void configAdaptersProductDetail() {
        adapter = new ProductAdapter(allProducts);
        binding.rclProductDetail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rclProductDetail.setAdapter(adapter);
        binding.rclProductDetail.setHasFixedSize(true);
    }

    private void addEvents() {
        setSupportActionBar(binding.toolbarall);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
            actionBar.setTitle("Chi tiết sản phẩm");
            actionBar.setDisplayShowTitleEnabled(true);
        }

        binding.toolbarall.setNavigationOnClickListener(v -> {
            finish();
        });

        cbFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // add product to favorite
                fireStoreClass.addFavorite(this,product.getProductId());
                Log.d("DetailProduct", "Add product to favorite " + product.getProductId());
            } else {
                // remove product from favorite
                fireStoreClass.removeFavorite(this,product.getProductId());
                Log.d("DetailProduct", "Remove product from favorite " + product.getProductId());
            }
        });
        binding.imvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseProductQuantity();
            }
        });
        binding.imvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseProductQuantity();
            }
        });
    }


    private void increaseProductQuantity() {
        int currentQuantity = Integer.parseInt(binding.txtNumberOrder.getText().toString());
        if (currentQuantity < 999) {
            currentQuantity++;
            binding.txtNumberOrder.setText(String.valueOf(currentQuantity));
        } else {
            Toast.makeText(this, "Số lượng sản phẩm đã đạt giới hạn tối đa", Toast.LENGTH_SHORT).show();
        }
    }
    private void decreaseProductQuantity() {
        int currentQuantity = Integer.parseInt(binding.txtNumberOrder.getText().toString());
        if (currentQuantity > 1) {
            currentQuantity--;
            binding.txtNumberOrder.setText(String.valueOf(currentQuantity));
        } else {
            Toast.makeText(this, "Số lượng sản phẩm đã đạt giới hạn tối thiểu", Toast.LENGTH_SHORT).show();
        }
    }

    private void mapping() {
        tvProductName = binding.txtProductNameDetails;
        tvProductPrice = binding.txtProductPriceDetail;
        tvProductDescription = binding.txtDescriptionOverall;
        tvScore = binding.txtScoreProductDetail;
        tvNumOfRate = binding.txtNumberOfRating;
        tvProductPresale = binding.textsale;
        tvSalePercent = binding.textsalepercent;
        imvSalePercent = binding.imagesale;
        ivProductImage = binding.productImageDetail;
        cbFavorite = binding.chkFavouriteProductDetail;
        btnAddcart = binding.btnAddtoCart;
    }

    private void getProductDetail() {
        // get product detail base on product id

    }

    public void loadProductDetail(Product product) {
        String name = product.getProductname();
        cbFavorite.setChecked(product.isFavorite());
        tvProductName.setText(name);
        tvProductDescription.setText(product.getProductDescription());
        double price = product.getProductprice();
        String formattedPrice = String.format("%,.0f đ", price);
        // check if product is on sale
        if (product.getSalepercent() > 0) {
            tvProductPresale.setVisibility(TextView.VISIBLE);
            imvSalePercent.setVisibility(ImageView.VISIBLE);
            String salePercent = "-" + Math.round(product.getSalepercent()) + " %";
            tvSalePercent.setText(salePercent);
            double salePrice = price - price * product.getSalepercent() / 100;
            String formattedSalePrice = String.format("%,.0f đ", salePrice);
            tvProductPrice.setText(formattedSalePrice);
            tvProductPresale.setPaintFlags(tvProductPresale.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
            tvProductPresale.setText(formattedPrice);
            btnAddcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String productName = product.getProductname();
                    double productPrice = product.getProductprice() - (product.getProductprice()*product.getSalepercent() / 100);
                    String productImage = product.getProductImageUrl();
                    double productId = product.getProductId();
                    double numOfProduct = Double.parseDouble(binding.txtNumberOrder.getText().toString());
                    String userId = fireStoreClass.getCurrentUID();
                    Log.d("DetailProduct", "productName: " + productName);
                    Log.d("DetailProduct", "productPrice: " + productPrice);
                    Log.d("DetailProduct", "numOfProduct: " + numOfProduct);
                    Log.d("DetailProduct", "productImageUrl: " + productImage);
                    Log.d("DetailProduct", "productId: " + productId);
                    fireStoreClass.addToCart(userId, productId, productName, productPrice,numOfProduct ,productImage);
                    Toast.makeText(DetailProduct.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            imvSalePercent.setVisibility(ImageView.INVISIBLE);
            tvSalePercent.setVisibility(TextView.INVISIBLE);
            tvProductPresale.setVisibility(TextView.INVISIBLE);
            tvProductPrice.setText(formattedPrice);
            btnAddcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String productName = product.getProductname();
                    double productPrice = product.getProductprice() - (product.getProductprice()*product.getSalepercent() / 100);
                    String productImage = product.getProductImageUrl();
                    double productId = product.getProductId();
                    double numOfProduct = Double.parseDouble(binding.txtNumberOrder.getText().toString());
                    String userId = fireStoreClass.getCurrentUID();
                    Log.d("DetailProduct", "productName: " + productName);
                    Log.d("DetailProduct", "productPrice: " + productPrice);
                    Log.d("DetailProduct", "numOfProduct: " + numOfProduct);
                    Log.d("DetailProduct", "productImageUrl: " + productImage);
                    Log.d("DetailProduct", "productId: " + productId);
                    fireStoreClass.addToCart(userId, productId, productName, productPrice,numOfProduct ,productImage);
                    Toast.makeText(DetailProduct.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                }
            });
        }
        // load image
        Glide.with(this).load(product.getProductImageUrl()).into(ivProductImage);
    }

}