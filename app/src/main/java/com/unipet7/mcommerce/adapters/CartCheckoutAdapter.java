package com.unipet7.mcommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.models.Product;

import java.util.ArrayList;

public class CartCheckoutAdapter extends RecyclerView.Adapter<CartCheckoutAdapter.ViewHolder> {
    private Context context;
    ArrayList<Product> productcheckout;
    @NonNull
    @Override
    public CartCheckoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_recycleview, parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull CartCheckoutAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
