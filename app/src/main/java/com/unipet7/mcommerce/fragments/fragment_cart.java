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
    FragmentCartBinding binding;
    ArrayList<ProductCart> productCarts;
    CartAdapter adapter;
    private static final int REQUEST_CODE_VOUCHER = 101;
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            if (direction == ItemTouchHelper.LEFT) {
                String cartItemId = String.valueOf(productCarts.get(position).getProductId()); // Lấy cartItemId tương ứng với vị trí
                deleteItem(cartItemId, position);
            } else if (direction == ItemTouchHelper.RIGHT) {
                // Xử lý khi vuốt sang phải nếu cần
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

            // Fix position for button
            float deleteButtonLeft = view.getRight() - (view.getRight() / 5f);
            float deleteButtonTop = view.getTop();
            float deleteButtonRight = view.getRight() - view.getPaddingRight();
            float deleteButtonBottom = view.getBottom();

            // Draw a button
            float radius = 20f;
            RectF deleteButtonDelete = new RectF(deleteButtonLeft, deleteButtonTop, deleteButtonRight, deleteButtonBottom);
            c.drawRoundRect(deleteButtonDelete, radius, radius, paint);

            // Set color for draw text inside button
            paint.setColor(getResources().getColor(R.color.white));

            // Load the vector drawable
            Drawable deleteIconDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.ic_cart_delete); // Replace R.drawable.ic_cart_delete with your actual vector icon

            // Set the bounds for the drawable
            int iconWidth = deleteIconDrawable.getIntrinsicWidth();
            int iconHeight = deleteIconDrawable.getIntrinsicHeight();
            int iconLeft = (int) (deleteButtonDelete.centerX() - iconWidth / 2f);
            int iconTop = (int) (deleteButtonDelete.centerY() - iconHeight / 2f);
            deleteIconDrawable.setBounds(iconLeft, iconTop, iconLeft + iconWidth, iconTop + iconHeight);

            // Draw the vector icon
            deleteIconDrawable.draw(c);

            // dX of item run from 0 to `-X` width of screen
            // Item will stop in dX / 5
            super.onChildDraw(c, recyclerView, viewHolder, dX / 5f, dY, actionState, isCurrentlyActive);
        }

        private void deleteItem(String productId, int position) {
            // Xóa sản phẩm khỏi danh sách hiển thị
            productCarts.remove(position);
            adapter.notifyItemRemoved(position);

            // Xóa sản phẩm khỏi cơ sở dữ liệu Firebase
            FireStoreClass fireStoreClass = new FireStoreClass();
            fireStoreClass.deleteCartItem(productId);
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
                // Add logic to handle voucher code
                // For example:
                Toast.makeText(getContext(), "Voucher code entered: " + voucherCode, Toast.LENGTH_SHORT).show();
            }
        });
        FireStoreClass fireStoreClass = new FireStoreClass();
        fireStoreClass.getCartItemsRealtime(getContext(), new CartAdapter.OnQuantityChangeListener() {
            @Override
            public void onQuantityChange(int position, double quantity) {
                // Perform necessary actions when quantity changes in the cart
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        loadData();
        super.onResume();
    }

    private void loadData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cart")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    productCarts = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        ProductCart productCart = document.toObject(ProductCart.class);
                        productCarts.add(productCart);
                    }

                    // Create adapter and update data
                    adapter = new CartAdapter(requireContext(), productCarts);
                    binding.rcCart.setLayoutManager(new GridLayoutManager(getContext(), 1));
                    binding.rcCart.setAdapter(adapter);
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error fetching documents: ", e));
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
            String voucherCode = data.getStringExtra("voucher_code");
            binding.edtVoucher.setText(voucherCode);
        }
    }
}
