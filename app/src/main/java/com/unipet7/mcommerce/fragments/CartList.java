package com.unipet7.mcommerce.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.activities.DetailCheckout;
import com.unipet7.mcommerce.activities.VoucherActivity;
import com.unipet7.mcommerce.adapters.CartAdapter;
import com.unipet7.mcommerce.databinding.FragmentCartBinding;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.ProductCart;
import com.unipet7.mcommerce.models.Voucher;
import com.unipet7.mcommerce.utils.Constants;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class CartList extends Fragment {
    private static final int REQUEST_CODE_VOUCHER = 1;

    private boolean isVoucherUpdated = false;
    private String voucherCode;
    private double voucherNumb;
    private double voucherMaxDiscount;
    private double voucherMiniumValue;

    FragmentCartBinding binding;

    Voucher voucher;
    ArrayList<ProductCart> productCarts;
    private double totalCartPrice = 0.0;
    CartAdapter adapter;
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        private int lastSwipedPosition = -1;

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            if (direction == ItemTouchHelper.LEFT) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View dialogView = inflater.inflate(R.layout.dialogmessage, null);
                Window window = builder.create().getWindow();
                assert window != null;
                window.setBackgroundDrawableResource(android.R.color.transparent);
                builder.setView(dialogView);

                TextView dialogMessage = dialogView.findViewById(R.id.tv_message_details_dialog);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView dialogTitle = dialogView.findViewById(R.id.tv_title_dialog);
                Button btnCancel = dialogView.findViewById(R.id.btn_cancel_dialog);
                Button btnConfirm = dialogView.findViewById(R.id.btn_ok_dialog);
                btnCancel.setText("Hủy");
                btnConfirm.setText("Xóa");

                final AlertDialog alertDialog = builder.create();
                Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);


                btnCancel.setOnClickListener(v -> {
                    alertDialog.dismiss();
                    if (lastSwipedPosition != -1) {
                        adapter.notifyItemChanged(lastSwipedPosition);
                        lastSwipedPosition = -1;
                    }
                });
                dialogMessage.setText("Bạn có muốn xóa sản phẩm khỏi giỏ hàng?");
                dialogTitle.setText("Xóa sản phẩm");

                btnConfirm.setOnClickListener(v -> {
                    String cartItemId = String.valueOf(productCarts.get(position).getProductId());
                    deleteItem(cartItemId, position);
                    alertDialog.dismiss();
                });
                alertDialog.show();
                lastSwipedPosition = position;
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
            CalculateVoucher(voucherCode, voucherNumb, voucherMaxDiscount, voucherMiniumValue);
        }
    };


    ItemTouchHelper itemTouchHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        loadData();
        if (adapter != null) {
            adapter.setOnQuantityChangeListener((position, quantity) -> {
                ProductCart productCart = productCarts.get(position);
                productCart.setNumOfProduct((int) quantity);
                productCart.setTotalPrice(productCart.getProductPrice() * quantity);
                adapter.updateCartItem(productCart);
                adapter.notifyItemChanged(position);
                calculateTotalCartPrice();
            });
        }
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.rcCart);
        binding.btnPaymentNow.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DetailCheckout.class);
            Bundle bundle = new Bundle();
            bundle.putDouble("totalCartPrice", totalCartPrice);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        ActivityResultLauncher<Intent> voucherActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            voucher = (Voucher) data.getSerializableExtra(Constants.VOUCHER);
                            assert voucher != null;
                            this.voucherCode = voucher.getVoucher_code();
                            this.voucherNumb = voucher.getVoucher_numb();
                            this.voucherMaxDiscount = voucher.getVoucher_max_discount();
                            this.voucherMiniumValue = voucher.getVoucher_minium_value();
                            isVoucherUpdated = true;
                            Log.i("CartList", "onActivityResult: " + voucherCode);
                            CalculateVoucher(voucher.getVoucher_code(), voucher.getVoucher_numb(), voucher.getVoucher_max_discount(), voucher.getVoucher_minium_value());
                        }
                    }
                });

        binding.btnCircleVoucher.setOnClickListener(v -> voucherActivityResultLauncher.launch(new Intent(getContext(), VoucherActivity.class)));
        binding.btnVoucher.setOnClickListener(v -> {
            String voucherCode = binding.edtVoucher.getText().toString().trim();
            if (voucherCode.isEmpty()) {
                voucherActivityResultLauncher.launch(new Intent(getContext(), VoucherActivity.class));
            } else {
                checkVoucherExistence(voucherCode);
            }
        });

        FireStoreClass fireStoreClass = new FireStoreClass();
        fireStoreClass.getCartItemsRealtime(getContext(), (position, quantity) -> {
            ProductCart productCart = productCarts.get(position);
            productCart.setNumOfProduct((int) quantity);
            productCart.setTotalPrice(productCart.getProductPrice() * quantity);
            adapter.notifyItemChanged(position);
            calculateTotalCartPrice();
        });

        return binding.getRoot();
    }
    private void checkVoucherExistence(String voucherCode) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("vouchers")
                .whereEqualTo("voucher_code", voucherCode)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot voucherDoc = queryDocumentSnapshots.getDocuments().get(0);
                        if (voucherDoc != null && voucherDoc.exists()) {
                            this.voucherCode = voucherCode;
                            Map<String, Object> voucherData = voucherDoc.getData();
                            if (voucherData != null && voucherData.containsKey("voucher_numb")) {
                                this.voucherNumb = voucherDoc.getDouble("voucher_numb");
                            } else {
                                Toast.makeText(getContext(), "Dữ liệu voucher numb không hợp lệ.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (voucherData != null && voucherData.containsKey("voucher_max_discount")) {
                                this.voucherMaxDiscount = voucherDoc.getDouble("voucher_max_discount");
                            } else {
                                Toast.makeText(getContext(), "Dữ liệu voucher max không hợp lệ.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (voucherData != null && voucherData.containsKey("voucher_minium_value")) {
                                this.voucherMiniumValue = voucherDoc.getDouble("voucher_minium_value");
                            } else {
                                Toast.makeText(getContext(), "Dữ liệu voucher min không hợp lệ.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            CalculateVoucher(voucherCode, voucherNumb, voucherMaxDiscount, voucherMiniumValue);
                        } else {
                            Toast.makeText(getContext(), "Dữ liệu voucher chung không hợp lệ.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Voucher không tồn tại.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error fetching voucher documents: ", e);
                    Toast.makeText(getContext(), "Đã xảy ra lỗi khi kiểm tra voucher.", Toast.LENGTH_SHORT).show();
                });
    }


    public void updateCartItem(ProductCart productCart) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.CART)
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
    }


    private void loadData() {
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
        String formatTotalCartPrice = String.format("%,.0f đ", totalCartPrice);
        binding.txtPreNumb.setText(formatTotalCartPrice);
        updateCartPriceDisplay(totalCartPrice, 0.0);
    }

    private void CalculateVoucher(String voucherCode, double voucherNumb, double voucherMaxDiscount, double voucherMinimumValue) {
        binding.edtVoucher.setText(voucherCode);
        if (voucherCode.isEmpty()) {
            updateCartPriceDisplay(totalCartPrice, 0.0);
            return;
        }
        if (totalCartPrice >= voucherMinimumValue) {
            double discountPrice = Math.min(totalCartPrice * voucherNumb / 100, voucherMaxDiscount);
            double finalPrice = totalCartPrice - discountPrice;
            updateCartPriceDisplay(finalPrice, discountPrice);
        } else {
            double valueNeed = voucherMinimumValue - totalCartPrice;
            updateCartPriceDisplay(totalCartPrice, 0.0);
            Toast.makeText(getContext(), "Bạn cần mua thêm " + String.format("%,.0f đ", valueNeed) + " để sử dụng voucher.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCartPriceDisplay(double finalPrice, double discountPrice) {
        binding.txtDiscoutNumb.setText(String.format("%,.0f đ", discountPrice));
        binding.txtTotalNumb.setText(String.format("%,.0f đ", finalPrice));
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
