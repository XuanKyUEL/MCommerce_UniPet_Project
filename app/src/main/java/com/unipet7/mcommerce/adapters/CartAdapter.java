package com.unipet7.mcommerce.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.models.ProductCart;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ProductCart> productCarts;
    private OnQuantityChangeListener onQuantityChangeListener;
    private FirebaseFirestore firestore;

    public CartAdapter(Context context, ArrayList<ProductCart> productCarts) {
        this.context = context;
        this.productCarts = productCarts;
        this.firestore = FirebaseFirestore.getInstance();
    }

    public void setOnQuantityChangeListener(OnQuantityChangeListener listener) {
        this.onQuantityChangeListener = listener;
    }

    public interface OnQuantityChangeListener {
        void onQuantityChange(int position, double newQuantity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_recycleview, parent, false);
        return new ViewHolder(view);
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    // Phương thức để thiết lập listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductCart productCart = productCarts.get(position);

        holder.txtProductName.setText(productCart.getProductName());
        holder.txtProductPrice.setText(String.format("%,.0f đ", productCart.getProductPrice()));
        holder.txtNumberOrder.setText(String.valueOf(productCart.getNumOfProduct()));
        holder.txtSumNumbPrice.setText(String.format("%,.0f đ", productCart.getTotalPrice()));
        // Load image from URL using Glide
        Glide.with(context).load(productCart.getProductImageUrl()).into(holder.imvProductCart);

        holder.imvMinus.setOnClickListener(v -> {
            if (onQuantityChangeListener != null) {
                double newQuantity = productCart.getNumOfProduct() - 1;
                if (newQuantity >= 1) {
                    productCart.setNumOfProduct((int) newQuantity);
                    productCart.setTotalPrice(productCart.getProductPrice() * newQuantity);

                    // Cập nhật Firestore
                    updateCartItem(productCart);

                    onQuantityChangeListener.onQuantityChange(position, newQuantity);
                }
            }
        });

        holder.imvPlus.setOnClickListener(v -> {
            if (onQuantityChangeListener != null) {
                double newQuantity = productCart.getNumOfProduct() + 1;
                productCart.setNumOfProduct((int) newQuantity);
                productCart.setTotalPrice(productCart.getProductPrice() * newQuantity);

                // Cập nhật Firestore
                updateCartItem(productCart);

                onQuantityChangeListener.onQuantityChange(position, newQuantity);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });

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
    }



    @Override
    public int getItemCount() {
        return productCarts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName, txtProductPrice, txtNumberOrder, txtSumNumbPrice;
        ImageView imvProductCart, imvPlus, imvMinus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
            txtNumberOrder = itemView.findViewById(R.id.txtNumberOrder);
            txtSumNumbPrice = itemView.findViewById(R.id.txtSumNumbPrice);
            imvProductCart = itemView.findViewById(R.id.imvProductCart);
            imvPlus = itemView.findViewById(R.id.imvPlus);
            imvMinus = itemView.findViewById(R.id.imvMinus);
        }
    }
}