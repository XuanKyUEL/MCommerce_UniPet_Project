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

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.CartAdapter;
import com.unipet7.mcommerce.adapters.Cart_Checkout_Adapter;
import com.unipet7.mcommerce.adapters.PaymentMethodListener;
import com.unipet7.mcommerce.databinding.ActivityNotificationBinding;
import com.unipet7.mcommerce.databinding.ActivityPaymentMethodDetailsBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.fragments.FragmentAddressEdit;
import com.unipet7.mcommerce.fragments.FragmentPaymentMethod;
import com.unipet7.mcommerce.models.ProductCart;
import com.unipet7.mcommerce.models.User;

import java.util.ArrayList;

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
        binding.txtChangePaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentPaymentMethod editAdrressFragment = new FragmentPaymentMethod();
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.containerpayment, editAdrressFragment, "editAddressFragment")
                        .addToBackStack(null)
                        .commit();

            }
        });
        binding.btnShowDiscountCode.setOnClickListener(v -> startActivityForResult(new Intent(PaymentMethodDetails.this, VoucherActivity.class), REQUEST_CODE_VOUCHER));
        binding.btnvoucheruse.setOnClickListener(v -> {
            String voucherCode = binding.edtDiscountCode.getText().toString().trim();
            if (voucherCode.isEmpty()) {
                startActivityForResult(new Intent(PaymentMethodDetails.this, VoucherActivity.class), REQUEST_CODE_VOUCHER);
            } else {
                // Add logic to handle voucher code
                // For example:
                Toast.makeText(PaymentMethodDetails.this, "Voucher code entered: " + voucherCode, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onPaymentMethodSelected(String paymentMethod) {
        // Cập nhật TextView theo phương thức thanh toán được chọn
        binding.txtPaymentMethodName.setText(paymentMethod);
    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_VOUCHER && resultCode == Activity.RESULT_OK && data != null) {
            String voucherCode = data.getStringExtra("voucher_code");
            binding.edtDiscountCode.setText(voucherCode);
        }
    }
}