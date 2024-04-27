package com.unipet7.mcommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.ProductCart;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ProductCart> productCarts;
    private OnQuantityChangeListener onQuantityChangeListener;
    private FireStoreClass fireStoreClass;

    public CartAdapter(Context context, ArrayList<ProductCart> productCarts) {
        this.context = context;
        this.productCarts = productCarts;
        this.fireStoreClass = fireStoreClass;
    }

    public void setOnQuantityChangeListener(OnQuantityChangeListener listener) {
        this.onQuantityChangeListener = listener;
    }


    public interface OnQuantityChangeListener {
        void onQuantityChange(int position, double quantity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_recycleview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductCart productCart = productCarts.get(position);

        holder.txtProductName.setText(productCart.getProductName());
        double price = productCart.getProductPrice();
        String formattedPPrice = String.format("%,.0f đ", price);
        holder.txtProductPrice.setText(formattedPPrice);
        holder.txtNumberOrder.setText(String.valueOf(productCart.getNumOfProduct()));
        double sumnumbprice = productCart.getTotalPrice();
        String formattedsumPrice = String.format("%,.0f đ", sumnumbprice);
        holder.txtSumNumbPrice.setText(formattedsumPrice);

        // Load image from URL using Glide
        Glide.with(context).load(productCart.getProductImageUrl()).into(holder.imvProductCart);

        holder.imvMinus.setOnClickListener(v -> {
            if (onQuantityChangeListener != null) {
                double newQuantity = productCart.getNumOfProduct() - 1;
                if (newQuantity >= 0) {
                    productCart.setNumOfProduct((int) newQuantity);
                    productCart.setTotalPrice(productCart.getProductPrice() * newQuantity);
                    // Update Firebase here if needed
                    onQuantityChangeListener.onQuantityChange(position, newQuantity);
                    notifyItemChanged(position);
                }
            }
        });

        holder.imvPlus.setOnClickListener(v -> {
            if (onQuantityChangeListener != null) {
                double newQuantity = productCart.getNumOfProduct() + 1;
                productCart.setNumOfProduct((int) newQuantity);
                productCart.setTotalPrice(productCart.getProductPrice() * newQuantity);
                // Update Firebase here if needed
                onQuantityChangeListener.onQuantityChange(position, newQuantity);
                notifyItemChanged(position);
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
