package com.unipet7.mcommerce.adapters;

import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.activities.DetailProduct;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
    public void setData(List<Product> newData) {
        productList = newData;
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
        int productId = product.getProductId();
        holder.favorite.setChecked(product.isFavorite());
        String productName = product.getProductname();
        holder.productname.setText(productName);
        double roundRating = Math.round(product.getProductratenum() * 10) / 10.0;
        holder.productratenum.setText("4.5");
        holder.numOfRating.setText("  (130)");
        double price = product.getProductprice();
        double presaleprice = product.getPresaleprice();
        String formattedPrice = String.format("%,.0f đ", price);
        String formattedPreSalePrice = String.format("%,.0f đ", presaleprice);

        holder.btnAddCart.setOnClickListener(v -> {
            String productImage = product.getProductImageUrl();
            double numOfProduct = 1.0;
            String userId = fireStoreClass.getCurrentUID();
            Log.d("DetailProduct", "productName: " + productName);
            Log.d("DetailProduct", "productPrice: " + price);
            Log.d("DetailProduct", "numOfProduct: " + numOfProduct);
            Log.d("DetailProduct", "productImageUrl: " + productImage);
            Log.d("DetailProduct", "productId: " + productId);
            Toast.makeText(v.getContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
            fireStoreClass.addToCart(userId, productId, productName, price,numOfProduct ,productImage);
        });

        if (product.getSalepercent() > 0) {
            int salepercent = (int) product.getSalepercent();
            holder.salepercent.setText("-"+ salepercent + " %");
            holder.productprice.setText(formattedPrice);
            holder.presaleprice.setPaintFlags(holder.presaleprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.presaleprice.setText(formattedPreSalePrice);
        }else {
            holder.salepercent.setVisibility(View.GONE);
            holder.salespercentbg.setVisibility(View.GONE);
            holder.salebanner.setVisibility(View.INVISIBLE);
            holder.productprice.setText(formattedPrice);
            holder.presaleprice.setVisibility(View.GONE);
        }
        Glide.with(holder.itemView.getContext()).load(product.getProductImageUrl()).into(holder.imvThumb);

        holder.itemView.setOnClickListener(v -> {
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
