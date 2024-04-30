package com.unipet7.mcommerce.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements FavProductInterface {

    List<Product> productList;

    FireStoreClass fireStoreClass = new FireStoreClass();
    private List<Integer> favList = new ArrayList<>();

    @Override
    public void onDataLoaded(List<Integer> favList) {
        this.favList = favList;
        notifyDataSetChanged();
    }
    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
        fireStoreClass.getFavoriteList(this);
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
            holder.salepercent.setText("-"+ salepercent + " %");
            double percent = product.getSalepercent();
            double price = product.getProductprice();
            double saleprice = price - (price * percent / 100);

            String formattedPrice = String.format("%,.0f đ", price);
            String formattedSalePrice = String.format("%,.0f đ", saleprice);

            holder.productprice.setText(formattedSalePrice);
            holder.presaleprice.setPaintFlags(holder.presaleprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.presaleprice.setText(formattedPrice);
            holder.btnAddCart.setOnClickListener(v -> {
                String productName = product.getProductname();
                double productPrice = product.getProductprice() - (product.getProductprice()*product.getSalepercent() / 100);
                String productImage = product.getProductImageUrl();
                double productId = product.getProductId();
                double numOfProduct = 1.0;
                String userId = fireStoreClass.getCurrentUID();
                Log.d("DetailProduct", "productName: " + productName);
                Log.d("DetailProduct", "productPrice: " + productPrice);
                Log.d("DetailProduct", "numOfProduct: " + numOfProduct);
                Log.d("DetailProduct", "productImageUrl: " + productImage);
                Log.d("DetailProduct", "productId: " + productId);
                Toast.makeText(v.getContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                fireStoreClass.addToCart(userId, productId, productName, productPrice,numOfProduct ,productImage);
            });

        }else {
            holder.salepercent.setVisibility(View.GONE);
            holder.salespercentbg.setVisibility(View.GONE);
            holder.salebanner.setVisibility(View.INVISIBLE);
            holder.productprice.setText(String.format("%,.0f đ", product.getProductprice()));
            holder.presaleprice.setVisibility(View.GONE);
            holder.btnAddCart.setOnClickListener(v -> {
                String productName = product.getProductname();
                double productPrice = product.getProductprice() - (product.getProductprice()*product.getSalepercent() / 100);
                String productImage = product.getProductImageUrl();
                double productId = product.getProductId();
                double numOfProduct = 1.0;
                String userId = fireStoreClass.getCurrentUID();
                Log.d("DetailProduct", "productName: " + productName);
                Log.d("DetailProduct", "productPrice: " + productPrice);
                Log.d("DetailProduct", "numOfProduct: " + numOfProduct);
                Log.d("DetailProduct", "productImageUrl: " + productImage);
                Log.d("DetailProduct", "productId: " + productId);
                Toast.makeText(v.getContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                fireStoreClass.addToCart(userId, productId, productName, productPrice,numOfProduct ,productImage);
            });

        }
        Glide.with(holder.itemView.getContext()).load(product.getProductImageUrl()).into(holder.imvThumb);

        holder.itemView.setOnClickListener(v -> {
            int productId = product.getProductId();
            Intent intent = new Intent(v.getContext(), DetailProduct.class);
            intent.putExtra(Constants.PRODUCT_ID, productId);
            v.getContext().startActivity(intent);
        });
        holder.favorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                fireStoreClass.addFavorite(buttonView.getContext(),product.getProductId());
            else {
                fireStoreClass.removeFavorite(buttonView.getContext(),product.getProductId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productname, productprice, productratenum, presaleprice, salepercent, numOfRating;
        ImageView imvThumb, salespercentbg, salebanner;
        Button btnAddCart;

        CheckBox favorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productname = itemView.findViewById(R.id.tvNameProduct_item);
            productprice = itemView.findViewById(R.id.tvProductPrice_item);
            productratenum = itemView.findViewById(R.id.tvRateValueItem);
            imvThumb = itemView.findViewById(R.id.ivProductItem);
            salepercent = itemView.findViewById(R.id.textsalepercent);
            salespercentbg = itemView.findViewById(R.id.imagesale);
            salebanner = itemView.findViewById(R.id.imagebannersale);
            presaleprice = itemView.findViewById(R.id.tvProductPriceSaleItem);
            numOfRating = itemView.findViewById(R.id.tvRateCountItem);
            btnAddCart = itemView.findViewById(R.id.btnAddCart);
            favorite = itemView.findViewById(R.id.chkFavouriteItem);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }
}
