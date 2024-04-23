package com.unipet7.mcommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.models.Product;

import java.util.ArrayList;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    Context context1;
    List<Product> productList;
    FirebaseDatabase productdb=FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public FavouriteAdapter(Context context1, List<Product> productList) {
        this.context1 = context1;
        this.productList = productList;
    }


    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productIdTextView.setText(product.getProductname());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productIdTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productIdTextView = itemView.findViewById(R.id.txtProductName);
        }
    }
}