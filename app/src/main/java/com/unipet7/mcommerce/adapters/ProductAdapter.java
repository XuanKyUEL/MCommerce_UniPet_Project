package com.unipet7.mcommerce.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.activities.DetailProduct;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.utils.Constants;

import java.io.Serializable;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    static List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    public static List<Product> getProductList() {
        return productList;
    }

    public static void setProductList(List<Product> productList) {
        ProductAdapter.productList = productList;
    }

    public static void getDetailProduct(List<Product> pdList, int position, Intent intent) {
        Product product = pdList.get(position);
        intent.putExtra(Constants.PRODUCT_ID, product.getProductId());
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
            holder.productprice.setText(String.format("%,.0f đ", product.getProductprice()));
            holder.presaleprice.setVisibility(View.GONE);
            holder.btnAddCart.setOnClickListener(v -> {
                // Lấy thông tin sản phẩm tương ứng
                Product product1 = productList.get(position);
                String productName = product.getProductname();
                double productPrice = product.getProductprice();
                String productImage = product.getProductImageUrl();

                // Gọi phương thức addToCart để lưu thông tin sản phẩm vào Firestore cart collection
                FireStoreClass fireStoreClass = new FireStoreClass();
                fireStoreClass.addToCart(productName, productPrice, productImage);
            });
        }
        // glide imge from firebaseurl
        Glide.with(holder.itemView.getContext()).load(product.getProductImageUrl()).into(holder.imvThumb);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView productname, productprice, productratenum, presaleprice, salepercent, numOfRating;
        ImageView imvThumb, salespercentbg, salebanner;
        Button btnAddCart;

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
            btnAddCart = itemView.findViewById(R.id.btnAddCart);
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), DetailProduct.class);
                getDetailProduct(productList, getAdapterPosition(), intent);
                v.getContext().startActivity(intent);
            });




        }
    }
}
