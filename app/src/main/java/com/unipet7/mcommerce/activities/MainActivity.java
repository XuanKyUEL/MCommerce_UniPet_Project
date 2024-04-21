package com.unipet7.mcommerce.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.airbnb.lottie.L;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.databinding.ActivityMainBinding;
import com.unipet7.mcommerce.fragments.FragmentAllProduct;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.fragments.Fragment_Empty_Notification;
import com.unipet7.mcommerce.fragments.Home;
import com.unipet7.mcommerce.fragments.Profile;
import com.unipet7.mcommerce.fragments.fragment_blank_cart;
import com.unipet7.mcommerce.models.User;
import com.unipet7.mcommerce.utils.LoadingDialog;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ActivityMainBinding binding;

    LoadingDialog loadingDialog;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fabCart;

    private FirebaseFirestore db;

    Fragment fragment = null;

    boolean isProfileInit = false;
    boolean isProductInit = false;

    boolean isFavoriteInit = false;

    boolean isCartInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        setTheme(R.style.Base_Theme_UniPet);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mapping();
        replaceFragment(new Home(), "initHome");
        navigateFragment();
    }

    private void mapping() {
        bottomNavigationView = binding.bottomNavigationView;
        fabCart = binding.fabCart;
        db = FirebaseFirestore.getInstance();
    }
    

    private void navigateFragment() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            handleNavigateonItemSelected(item.getItemId());
            return true;
        });
        fabCart.setOnClickListener(v -> {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("cart");
            if (fragment == null) {
                fragment = new fragment_blank_cart();
                replaceFragment(fragment, "cart");
            } else {
                getSupportFragmentManager().popBackStack("cart", 0);
            }
            // set all the other items to unchecked
            bottomNavigationView.getMenu().findItem(R.id.home_icon_bottom).setChecked(false);
            bottomNavigationView.getMenu().findItem(R.id.account_icon_bottom).setChecked(false);
            bottomNavigationView.getMenu().findItem(R.id.fav_icon_bottom).setChecked(false);
            bottomNavigationView.getMenu().findItem(R.id.product_icon_bottom).setChecked(false);
        });
    }

    private void handleNavigateonItemSelected(int itemId) {
        Fragment fragment;
        if (itemId == R.id.home_icon_bottom) {
            fragment = getSupportFragmentManager().findFragmentByTag("initHome");
            if (fragment == null) {
                fragment = new Home();
                replaceFragment(fragment, "initHome");
            } else {
                getSupportFragmentManager().popBackStack("initHome", 0);
            }
        } else if (itemId == R.id.account_icon_bottom) {
            fragment = getSupportFragmentManager().findFragmentByTag("profile");
            if (fragment == null) {
                fragment = new Profile();
                replaceFragment(fragment, "profile");
            } else {
                getSupportFragmentManager().popBackStack("profile", 0);
            }
        } else if (itemId == R.id.fav_icon_bottom) {
            fragment = getSupportFragmentManager().findFragmentByTag("favorite");
            if (fragment == null) {
                fragment = new Fragment_Empty_Notification();
                replaceFragment(fragment, "favorite");
            } else {
                getSupportFragmentManager().popBackStack("favorite", 0);
            }
        } else if (itemId == R.id.product_icon_bottom) {
            fragment = getSupportFragmentManager().findFragmentByTag("product");
            if (fragment == null) {
                fragment = new Home();
                replaceFragment(fragment, "product");
            } else {
                getSupportFragmentManager().popBackStack("product", 0);
            }
        }
    }

    private void replaceFragment(Fragment fragment, @Nullable String tag) {
        loadingDialog = new LoadingDialog();
        loadingDialog.showLoadingDialog(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.containerLayoutMain.getId(), fragment);
        transaction.commitAllowingStateLoss();
        loadingDialog.dissmis();


        // Thêm giao dịch vào back stack (nếu cần)
        if (tag != null) {
            transaction.addToBackStack(tag);
        }
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