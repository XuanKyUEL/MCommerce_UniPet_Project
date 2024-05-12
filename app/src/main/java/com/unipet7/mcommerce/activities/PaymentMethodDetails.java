package com.unipet7.mcommerce.activities;

import static java.security.AccessController.getContext;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.AddressAdapter;
import com.unipet7.mcommerce.adapters.CartAdapter;
import com.unipet7.mcommerce.adapters.Cart_Checkout_Adapter;
import com.unipet7.mcommerce.adapters.PaymentMethodListener;
import com.unipet7.mcommerce.databinding.ActivityNotificationBinding;
import com.unipet7.mcommerce.databinding.ActivityPaymentMethodDetailsBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.fragments.FragmentAddress;
import com.unipet7.mcommerce.fragments.FragmentAddressEdit;
import com.unipet7.mcommerce.fragments.FragmentPaymentMethod;
import com.unipet7.mcommerce.models.Addresses;
import com.unipet7.mcommerce.models.ProductCart;
import com.unipet7.mcommerce.models.User;
import com.unipet7.mcommerce.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodDetails extends AppCompatActivity implements PaymentMethodListener {
    private static final int REQUEST_CODE_VOUCHER = 101;
    ActivityPaymentMethodDetailsBinding binding;
    Cart_Checkout_Adapter adapter;
    ArrayList<ProductCart> productCarts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentMethodDetailsBinding.inflate(getLayoutInflater());
        setActionBaricon();
        addEvents();
        loadData();
        updateTotalorder();
        setContentView(binding.getRoot());
    }

    private void loadData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentUserId = new FireStoreClass().getCurrentUID();
        if (currentUserId != null && !currentUserId.isEmpty()) {
            db.collection("cart")
                    .whereEqualTo("userId", currentUserId)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        productCarts = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            ProductCart productCart = document.toObject(ProductCart.class);
                            productCarts.add(productCart);
                        }
                        adapter = new Cart_Checkout_Adapter(PaymentMethodDetails.this, productCarts);
                        binding.rclPurchasedProduct.setLayoutManager(new GridLayoutManager(PaymentMethodDetails.this, 1));
                        binding.rclPurchasedProduct.setAdapter(adapter);
                    })
                    .addOnFailureListener(e -> Log.e("Firestore", "Error fetching documents: ", e));
        } else {
            Log.e("Firestore", "Current user ID is null or empty");
        }
    }

    private void setActionBaricon() {
        setSupportActionBar(binding.toolbarcheckout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        binding.toolbarcheckout.setNavigationOnClickListener(v -> finish());
    }

    private void addEvents() {
        binding.txtChangePaymentMethod.setOnClickListener(v -> {
            FragmentPaymentMethod editPaymentFragment = new FragmentPaymentMethod();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.containerpayment, editPaymentFragment, "editPaymentFragment")
                    .addToBackStack(null)
                    .commit();

        });
        binding.txtChangeAddress.setOnClickListener(v -> {
            FragmentAddress editAdrressFragment = new FragmentAddress();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.containerpayment, editAdrressFragment, "editAddressFragment")
                    .addToBackStack(null)
                    .commit();

        });
    }
    public void onPaymentMethodSelected(String paymentMethod) {
        // Cập nhật TextView theo phương thức thanh toán được chọn
        binding.txtPaymentMethodName.setText(paymentMethod);
    }
    private void updateTotalorder() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("totalCartPrice")) {
            double totalCartPrice = bundle.getDouble("totalCartPrice");
            double delivery = 30000; // Fixed delivery cost as an example
            double totalprice = totalCartPrice + delivery;
            binding.txtCost.setText(String.format("%,.0f đ", totalCartPrice));
            binding.txtDeliveryCost.setText(String.format("%,.0f đ", delivery));
            binding.txtTotalPayment.setText(String.format("%,.0f đ", totalprice));
        }
    }

}