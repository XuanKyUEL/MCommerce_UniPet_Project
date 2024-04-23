package com.unipet7.mcommerce.adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
        double roundRating = Math.round(product.getProductratenum() * 10) / 10.0;
        holder.productratenum.setText("4.5");
        holder.numOfRating.setText("  (130)");
        if (product.getSalepercent() > 0) {
            int salepercent = (int) product.getSalepercent();
            holder.salepercent.setText(salepercent + " %");
            double percent = product.getSalepercent();
            double price = product.getProductprice();
            double saleprice = price - (price * percent / 100);

            String formattedPrice = String.format("%,.0f đ", price);
            String formattedSalePrice = String.format("%,.0f đ", saleprice);

            holder.productprice.setText(formattedSalePrice);
            holder.presaleprice.setPaintFlags(holder.presaleprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.presaleprice.setText(formattedPrice);
        }else {
            holder.salepercent.setVisibility(View.GONE);
            holder.salespercentbg.setVisibility(View.GONE);
            holder.salebanner.setVisibility(View.INVISIBLE);
            holder.presaleprice.setVisibility(View.GONE);
        }
        // glide imge from firebaseurl
        Glide.with(holder.itemView.getContext()).load(product.getProductImageUrl()).into(holder.imvThumb);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView productname ,productprice, productratenum, presaleprice, salepercent,numOfRating;
        ImageView imvThumb, salespercentbg,salebanner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productname = itemView.findViewById(R.id.textView);
            productprice = itemView.findViewById(R.id.textView2);
            productratenum = itemView.findViewById(R.id.txtRating);
            imvThumb = itemView.findViewById(R.id.imageproduct);
            salepercent = itemView.findViewById(R.id.textsalepercent);
            salespercentbg = itemView.findViewById(R.id.imagesale);
            salebanner = itemView.findViewById(R.id.imagebannersale);
            presaleprice = itemView.findViewById(R.id.textsale);
            numOfRating = itemView.findViewById(R.id.txtNumberOfRating);
        }
    }
}
