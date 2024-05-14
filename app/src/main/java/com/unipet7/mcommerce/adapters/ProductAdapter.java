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
import com.google.firebase.firestore.FirebaseFirestore;
import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.activities.DetailProduct;
import com.unipet7.mcommerce.firebase.CheckFavoriteHelper;
import com.unipet7.mcommerce.firebase.FireStoreClass;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.utils.Constants;
import com.unipet7.mcommerce.utils.OnRemoveAllFavHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    List<Product> productList;

    FireStoreClass fireStoreClass = new FireStoreClass();

    private FirebaseFirestore firestore;

    private CheckFavoriteHelper checkFavoriteHelper;

    String userId = fireStoreClass.getCurrentUID();
    private List<Integer> favList = new ArrayList<>();

    private boolean loadFav;


    public ProductAdapter(List<Product> productList, FireStoreClass fireStoreClass, boolean loadFav) {
        this.productList = productList;
        this.userId = fireStoreClass.getCurrentUID();
        this.checkFavoriteHelper = new CheckFavoriteHelper();
        this.loadFav = loadFav;
        fireStoreClass.addFavoriteChangeListener(() -> notifyDataSetChanged());
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        fireStoreClass.removeFavoriteChangeListener(()-> notifyDataSetChanged());
    }

    public void setData(List<Product> newData) {
        productList = newData;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);
        String userId = fireStoreClass.getCurrentUID();
        String productIdString = String.valueOf(product.getProductId());
        checkFavoriteHelper.checkUserFavorite(userId, productIdString, isFavorite -> holder.favorite.setChecked(isFavorite));
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
            Log.d("DetailProduct", "productName: " + productName);
            Log.d("DetailProduct", "productPrice: " + price);
            Log.d("DetailProduct", "numOfProduct: " + numOfProduct);
            Log.d("DetailProduct", "productImageUrl: " + productImage);
            Log.d("DetailProduct", "productId: " + productId);
            Toast.makeText(v.getContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
            fireStoreClass.addToCart(productId, productName, price, numOfProduct, productImage);
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
        holder.favorite.setOnClickListener(v -> {
            // check if favorite is checked, then make it unchecked and remove from favorite list
            boolean isChecked = holder.favorite.isChecked();
            if (!isChecked) {
                holder.favorite.setChecked(false);
                fireStoreClass.removeFavorite(v.getContext(), productId);
            } else {
                holder.favorite.setChecked(true);
                fireStoreClass.addFavorite(v.getContext(), productId);
            }
        });
        // if loadFav == true, then when user click on favorite, it will remove the product from favorite list and remove from the view
        if (loadFav) {
            holder.favorite.setOnClickListener(v -> {
                // check if favorite is checked, then make it unchecked and remove from favorite list
                    fireStoreClass.removeFavorite(v.getContext(), productId);
                    productList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, productList.size());
                    if (productList.isEmpty()) {
                        if (v.getContext() instanceof OnRemoveAllFavHelper) {
                            boolean loadEmptyFav = true;
                            ((OnRemoveAllFavHelper) v.getContext()).onRemoveFav(loadEmptyFav);
                        }
                    }
            });
        }
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
}
