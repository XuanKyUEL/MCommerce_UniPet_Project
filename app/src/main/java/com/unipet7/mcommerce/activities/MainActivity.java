package com.unipet7.mcommerce.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.databinding.ActivityMainBinding;
import com.unipet7.mcommerce.fragments.Fragment_Empty_Notification;
import com.unipet7.mcommerce.fragments.Home;
import com.unipet7.mcommerce.fragments.Profile;
import com.unipet7.mcommerce.fragments.fragment_blank_cart;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.models.User;
import com.unipet7.mcommerce.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fabCart;

    private FirebaseFirestore db;

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
        db = FirebaseFirestore.getInstance();
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
        transaction.replace(R.id.containerLayoutMain, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
//    private void uploadProductdb() {
//        try {
//            InputStream is = getAssets().open("product.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            String json = new String(buffer, StandardCharsets.UTF_8);
//            //
//            JSONObject jsonObject = new JSONObject(json);
//            JSONArray jsonArray = jsonObject.getJSONArray("Products");
//            for(int i = 0; i < jsonArray.length(); i++) {
//                JSONObject pdObject = jsonArray.getJSONObject(i);
//
//                int productId = Integer.parseInt(pdObject.getString("ProductId"));
//                String categoryId = pdObject.getString("CategoryId");
//                String productDescription = pdObject.getString("ProductDescription");
//                double productPrice = pdObject.getDouble("ProductPrice");
//                String productImageUrl = pdObject.getString("ProductImgUrl");
//                String productName = pdObject.getString("ProductName");
//
//                Product product = new Product();
//                product.setProductId(productId);
//                product.setCategoryId(Integer.parseInt(categoryId));
//                product.setProductDescription(productDescription);
//                product.setProductprice(productPrice);
//                product.setProductImageUrl(productImageUrl);
//                product.setProductname(productName);
//
//                db.collection(Constants.PRODUCTS)
//                        .document(String.valueOf(productId))
//                        .set(product)
//                        .addOnSuccessListener(aVoid -> Log.i(TAG, "uploadProductData: Thành công"))
//                        .addOnFailureListener(e -> Log.e(TAG, "uploadProductData: ", e));
//            }
//        } catch (IOException | JSONException e) {
//            Log.e("uploadProductData", "uploadProductData: ", e);
//        }
//    }
}