package com.unipet7.mcommerce.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.activities.VoucherActivity;
import com.unipet7.mcommerce.adapters.CartAdapter;
import com.unipet7.mcommerce.databinding.FragmentCartBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.ProductCart;

import java.util.ArrayList;

public class fragment_cart extends Fragment {
    private static final int REQUEST_CODE_VOUCHER = 1;
    private String voucherCode;
    private double voucherNumb;
    private double voucherMaxDiscount;
    private double voucherMiniumValue;

    FragmentCartBinding binding;
    ArrayList<ProductCart> productCarts;
    private double totalCartPrice = 0.0;
    CartAdapter adapter;
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            if (direction == ItemTouchHelper.LEFT) {
                String cartItemId = String.valueOf(productCarts.get(position).getProductId());
                deleteItem(cartItemId, position);
            } else if (direction == ItemTouchHelper.RIGHT) {
            }
        }


        @Override
        public void onChildDraw(
                Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                float dX, float dY, int actionState, boolean isCurrentlyActive
        ) {
            View view = viewHolder.itemView;

            Paint paint = new Paint();
            paint.setColor(getResources().getColor(R.color.brandPrimary));
            paint.setTextSize(30f);
            paint.setAntiAlias(true);

            float deleteButtonLeft = view.getRight() - (view.getRight() / 5f);
            float deleteButtonTop = view.getTop();
            float deleteButtonRight = view.getRight() - view.getPaddingRight();
            float deleteButtonBottom = view.getBottom();

            float radius = 20f;
            RectF deleteButtonDelete = new RectF(deleteButtonLeft, deleteButtonTop, deleteButtonRight, deleteButtonBottom);
            c.drawRoundRect(deleteButtonDelete, radius, radius, paint);

            paint.setColor(getResources().getColor(R.color.white));

            Drawable deleteIconDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.ic_cart_delete);

            int iconWidth = deleteIconDrawable.getIntrinsicWidth();
            int iconHeight = deleteIconDrawable.getIntrinsicHeight();
            int iconLeft = (int) (deleteButtonDelete.centerX() - iconWidth / 2f);
            int iconTop = (int) (deleteButtonDelete.centerY() - iconHeight / 2f);
            deleteIconDrawable.setBounds(iconLeft, iconTop, iconLeft + iconWidth, iconTop + iconHeight);

            deleteIconDrawable.draw(c);

            super.onChildDraw(c, recyclerView, viewHolder, dX / 5f, dY, actionState, isCurrentlyActive);
        }



        private void deleteItem(String productId, int position) {
            productCarts.remove(position);
            adapter.notifyItemRemoved(position);
            FireStoreClass fireStoreClass = new FireStoreClass();
            fireStoreClass.deleteCartItem(productId);
            calculateTotalCartPrice();
            CalculateVoucher();
        }

    };

    ItemTouchHelper itemTouchHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        setActionBar(binding.toolbarall);
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.rcCart);

        binding.btnCircleVoucher.setOnClickListener(v -> startActivityForResult(new Intent(getContext(), VoucherActivity.class), REQUEST_CODE_VOUCHER));
        binding.btnVoucher.setOnClickListener(v -> {
            String voucherCode = binding.edtVoucher.getText().toString().trim();
            if (voucherCode.isEmpty()) {
                startActivityForResult(new Intent(getContext(), VoucherActivity.class), REQUEST_CODE_VOUCHER);
            } else {
                Toast.makeText(getContext(), "Voucher code entered: " + voucherCode, Toast.LENGTH_SHORT).show();
            }
        });
        FireStoreClass fireStoreClass = new FireStoreClass();
        fireStoreClass.getCartItemsRealtime(getContext(), new CartAdapter.OnQuantityChangeListener() {
            @Override
            public void onQuantityChange(int position, double quantity) {
                ProductCart productCart = productCarts.get(position);
                productCart.setNumOfProduct((int) quantity);
                productCart.setTotalPrice(productCart.getProductPrice() * quantity);
                adapter.notifyItemChanged(position);
                calculateTotalCartPrice();
            }
        });

        return binding.getRoot();
    }

    public void updateCartItem(ProductCart productCart) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cart")
                .whereEqualTo("productId", productCart.getProductId())
                .whereEqualTo("userId", productCart.getUserId())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);
                        doc.getReference().update("numOfProduct", productCart.getNumOfProduct(), "totalPrice", productCart.getTotalPrice());
                    }
                });
        calculateTotalCartPrice();
        CalculateVoucher();
    }


    @Override
    public void onResume() {
        super.onResume();
        loadData();

        if (adapter != null) {
            adapter.setOnQuantityChangeListener(new CartAdapter.OnQuantityChangeListener() {
                @Override
                public void onQuantityChange(int position, double quantity) {
                    ProductCart productCart = productCarts.get(position);
                    productCart.setNumOfProduct((int) quantity);
                    productCart.setTotalPrice(productCart.getProductPrice() * quantity);
                    adapter.updateCartItem(productCart);
                    adapter.notifyItemChanged(position);
                    calculateTotalCartPrice();
                    CalculateVoucher();
                }
            });
        }
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
                        adapter = new CartAdapter(requireContext(), productCarts);
                        adapter.setOnQuantityChangeListener((position, newQuantity) -> {
                            ProductCart productCart = productCarts.get(position);
                            productCart.setNumOfProduct((int) newQuantity);
                            productCart.setTotalPrice(productCart.getProductPrice() * newQuantity);
                            adapter.notifyItemChanged(position);
                            updateCartItem(productCart);
                        });
                        binding.rcCart.setLayoutManager(new GridLayoutManager(getContext(), 1));
                        binding.rcCart.setAdapter(adapter);
                        calculateTotalCartPrice();
                    })
                    .addOnFailureListener(e -> Log.e("Firestore", "Error fetching documents: ", e));
        } else {
            Log.e("Firestore", "Current user ID is null or empty");
        }
    }
    private void calculateTotalCartPrice() {
        totalCartPrice = 0;
        for (ProductCart cartItem : productCarts) {
            totalCartPrice += cartItem.getTotalPrice();
        }
        binding.txtPreNumb.setText(String.valueOf(Math.round(totalCartPrice) + " đ"));

        // Check if a voucher is applied
        String voucherCode = binding.edtVoucher.getText().toString().trim();
        if (!voucherCode.isEmpty()) {
        } else {
            binding.txtTotalNumb.setText(String.valueOf(Math.round(totalCartPrice) + " đ"));}
    }


    public void setActionBar(@Nullable Toolbar toolbar) {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_profile);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_VOUCHER && resultCode == Activity.RESULT_OK && data != null) {
            voucherCode = data.getStringExtra("voucher_code");
            voucherNumb = data.getDoubleExtra("voucher_numb", 0.0);
            voucherMaxDiscount = data.getDoubleExtra("voucher_max", 0.0);
            voucherMiniumValue = data.getDoubleExtra("voucher_minium_value", 0.0);
            CalculateVoucher();
        }
    }

    private void CalculateVoucher() {
        if (totalCartPrice >= voucherMiniumValue) {
            double discountPrice = totalCartPrice * voucherNumb / 100;
            if (discountPrice <= voucherMaxDiscount) {
                binding.edtVoucher.setText(voucherCode);
                double finalPrice = totalCartPrice - discountPrice;
                binding.txtDiscoutNumb.setText(String.valueOf(Math.round(discountPrice)) + " đ");
                binding.txtTotalNumb.setText(String.valueOf(Math.round(finalPrice)) + " đ");
            } else {
                binding.edtVoucher.setText(voucherCode);
                binding.txtDiscoutNumb.setText(String.valueOf(Math.round(voucherMaxDiscount)) + " đ");
                double finalPrice = totalCartPrice - voucherMaxDiscount;
                binding.txtTotalNumb.setText(String.valueOf(Math.round(finalPrice)) + " đ");
            }
        }
        else {
            binding.edtVoucher.setText("Nhập mã giảm giá");
            double valueNeed = voucherMiniumValue - totalCartPrice;
            binding.txtDiscoutNumb.setText(String.valueOf(Math.round(0.0)) + " đ");
            Toast.makeText(getContext(), "Bạn cần mua thêm " + String.valueOf(Math.round(valueNeed)) + " đ để sử dụng voucher.", Toast.LENGTH_SHORT).show();
        }
    }

}
