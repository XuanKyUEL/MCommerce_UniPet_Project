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
import com.google.firebase.firestore.FirebaseFirestore;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.models.ProductCart;

import java.util.ArrayList;

public class Cart_Checkout_Adapter extends RecyclerView.Adapter<Cart_Checkout_Adapter.ViewHolder>{
    private Context context;
    private ArrayList<ProductCart> productCarts;
    private FirebaseFirestore firestore;

    public Cart_Checkout_Adapter(Context context, ArrayList<ProductCart> productCarts) {
        this.context = context;
        this.productCarts = productCarts;
        this.firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_recycleview_checkout, parent, false);
        return new Cart_Checkout_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductCart productCart = productCarts.get(position);
        holder.txtProductName.setText(productCart.getProductName());
        holder.txtTotalPrice.setText(String.valueOf(Math.round(productCart.getTotalPrice())));
        holder.txtNumberOrder.setText(String.valueOf(productCart.getNumOfProduct()));
        Glide.with(context).load(productCart.getProductImageUrl()).into(holder.imvProductCart);
    }

    @Override
    public int getItemCount() {
        return productCarts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNumberOrder, txtTotalPrice, txtProductName;
        ImageView imvProductCart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTotalPrice = itemView.findViewById(R.id.txtTotalPrice);
            txtNumberOrder = itemView.findViewById(R.id.txtProductQuantity);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            imvProductCart = itemView.findViewById(R.id.imvProductCart);
        }
    }
}
