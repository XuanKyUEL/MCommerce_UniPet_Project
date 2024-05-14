package com.unipet7.mcommerce.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.adapters.Cart_Checkout_Adapter;
import com.unipet7.mcommerce.utils.DetailCheckoutHelper;
import com.unipet7.mcommerce.databinding.ActivityPaymentMethodDetailsBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.fragments.FragmentAddress;
import com.unipet7.mcommerce.fragments.FragmentPaymentMethod;
import com.unipet7.mcommerce.models.Addresses;
import com.unipet7.mcommerce.models.Order;
import com.unipet7.mcommerce.models.ProductCart;
import com.unipet7.mcommerce.models.User;
import com.unipet7.mcommerce.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailCheckout extends AppCompatActivity implements DetailCheckoutHelper {
    private static final int REQUEST_CODE_VOUCHER = 101;
    ActivityPaymentMethodDetailsBinding binding;

    List<Addresses> addresses;

    Addresses address;
    Cart_Checkout_Adapter adapter;
    ArrayList<ProductCart> productCarts;

    FireStoreClass fireStoreClass;

    Double totalCartPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentMethodDetailsBinding.inflate(getLayoutInflater());
        setActionBarIcon();
        addEvents();
        loadData();
        updateTotalOrder();
        setContentView(binding.getRoot());
        Log.i("PaymentMethodDetails", "onCreate: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("PaymentMethodDetails", "onPause: ");
    }



    private void loadData() {
        loadProducts();
        loadAddress();
    }

    private void loadAddress() {
        // Fetch the address[0] of the user collection
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentUserId = new FireStoreClass().getCurrentUID();
        if (currentUserId != null && !currentUserId.isEmpty()) {
            db.collection(Constants.USERS)
                    .document(currentUserId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null) {
                            addresses = user.getAddresses();
                            if (addresses != null && !addresses.isEmpty()) {
                                address = addresses.get(0);
                                binding.txtUserName.setText(address.getName());
                                binding.txtphonenumber.setText(address.getPhonenumber());
                                binding.txtAddressStreetName.setText(address.getStreet());
                                binding.txtOtherAddressInfo.setText(address.getProvince());
                            }
                        }
                    })
                    .addOnFailureListener(e -> Log.e("Firestore", "Error fetching documents: ", e));
        } else {
            Log.e("Firestore", "Current user ID is null or empty");
        }
    }

    private void loadProducts() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentUserId = new FireStoreClass().getCurrentUID();
        if (currentUserId != null && !currentUserId.isEmpty()) {
            db.collection(Constants.CART)
                    .whereEqualTo(Constants.USER_ID, currentUserId)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        productCarts = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            ProductCart productCart = document.toObject(ProductCart.class);
                            productCarts.add(productCart);
                        }
                        adapter = new Cart_Checkout_Adapter(DetailCheckout.this, productCarts);
                        binding.rclPurchasedProduct.setLayoutManager(new GridLayoutManager(DetailCheckout.this, 1));
                        binding.rclPurchasedProduct.setAdapter(adapter);
                    })
                    .addOnFailureListener(e -> Log.e("Firestore", "Error fetching documents: ", e));
        } else {
            Log.e("Firestore", "Current user ID is null or empty");
        }
    }

    private void setActionBarIcon() {
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

            Bundle arg = new Bundle();
            arg.putBoolean(Constants.IS_FROM_CHECKOUT, true);

            FragmentAddress editAdrressFragment = new FragmentAddress();
            editAdrressFragment.setArguments(arg);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerpayment, editAdrressFragment, "editAddressFragment")
                    .addToBackStack(null)
                    .commit();
        });
        binding.btnPay.setOnClickListener(v -> {
            // check if onPaymentMethodSelected is called
            if (binding.txtPaymentMethodName.getText().toString().equals("Hãy chọn phương thức thanh toán")) {
                Toast.makeText(this, "Bạn chưa chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
            }
            else {
                fireStoreClass = new FireStoreClass();
                String paymentMethod = binding.txtPaymentMethodName.getText().toString();
                Order order = new Order(paymentMethod, address, Constants.AWAIT, productCarts, totalCartPrice);
                Date currentDate = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = formatter.format(currentDate);
                order.setOrderDate(formattedDate);
                fireStoreClass.addOrder(this, order);
            }
        });
    }
    public void onPaymentMethodSelected(String paymentMethod) {
        // Cập nhật TextView theo phương thức thanh toán được chọn
        binding.txtPaymentMethodName.setText(paymentMethod);
    }

    @Override
    public void onAddressSelected(Addresses address) {
        if (address != null) {
            this.address = address;
            // Update TextViews with the new address data
            binding.txtUserName.setText(address.getName());
            binding.txtphonenumber.setText(address.getPhonenumber());
            binding.txtAddressStreetName.setText(address.getStreet());
            binding.txtOtherAddressInfo.setText(address.getProvince());
        }
    }

    private void updateTotalOrder() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("totalCartPrice")) {
            double totalCartPrice = bundle.getDouble("totalCartPrice");
            double delivery = 30000; // Fixed delivery cost as an example
            double totalprice = totalCartPrice + delivery;
            this.totalCartPrice = totalCartPrice;
            binding.txtCost.setText(String.format("%,.0f đ", totalCartPrice));
            binding.txtDeliveryCost.setText(String.format("%,.0f đ", delivery));
            binding.txtTotalPayment.setText(String.format("%,.0f đ", totalprice));
        }
    }

    public void addOrderSuccess() {
        Toast.makeText(this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.ODERSUCCESS, true);
        startActivity(intent);
        finishAffinity();
    }
}
