package com.unipet7.mcommerce.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.models.IntroLayoutItems;
import com.unipet7.mcommerce.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }


    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // TODO: Inflate your layout and return your view holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productname.setText(product.getProductname());
        holder.productprice.setText(String.valueOf(product.getProductprice()));
        holder.productratenum.setText(String.valueOf(product.getProductratenum()));
        holder.producttotalnum.setText(String.valueOf(product.getProducttotalnum()));
        holder.presaleprice.setText(String.valueOf(product.getPresaleprice()));
        holder.salepercent.setText(String.valueOf(product.getSalepercent()));
        holder.imvThumb.setImageResource(product.getImvThumb());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView productname ,productprice, productratenum, producttotalnum, presaleprice, salepercent;
        ImageView imvThumb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productname = itemView.findViewById(R.id.textView);
            productprice = itemView.findViewById(R.id.textView2);
            producttotalnum = itemView.findViewById(R.id.txtNumberOfRating);
            productratenum = itemView.findViewById(R.id.txtRating);
            imvThumb = itemView.findViewById(R.id.imageproduct);
            salepercent = itemView.findViewById(R.id.textsalepercent);
            presaleprice = itemView.findViewById(R.id.textsale);

        }
    }
}
