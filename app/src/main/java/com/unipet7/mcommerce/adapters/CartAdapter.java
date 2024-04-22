package com.unipet7.mcommerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.models.ProductCart;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    Context context;
    ArrayList<ProductCart> productCarts;

    public CartAdapter(Context context, ArrayList<ProductCart> productCarts) {
        this.context = context;
        this.productCarts = productCarts;
    }
    public interface OnQuantityChangeListener {void onQuantityChange(int position, int quantity);
    }

    private OnQuantityChangeListener onQuantityChangeListener;

    public void setOnQuantityChangeListener(OnQuantityChangeListener listener) {
        this.onQuantityChangeListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cart_item_recycleview, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtProductName.setText(productCarts.get(position).getTxtProductName());
        holder.txtNumberOrder.setText(String.valueOf(Math.round(productCarts.get(position).getTxtNumberOrder())));
        holder.txtProductPrice.setText(String.valueOf(Math.round(productCarts.get(position).getTxtProductPrice())) + " đ");
        double sumPrice = productCarts.get(position).getTxtNumberOrder() * productCarts.get(position).getTxtProductPrice();
        holder.txtSumNumbPrice.setText(String.valueOf(Math.round(sumPrice)) + " đ");
        holder.imvProductCart.setImageResource(productCarts.get(position).getImvProductCart());
        holder.imvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double currentOrder = productCarts.get(position).getTxtNumberOrder();
                if (currentOrder > 1) {
                    productCarts.get(position).setTxtNumberOrder(currentOrder - 1);
                    holder.txtNumberOrder.setText(String.valueOf(Math.round(productCarts.get(position).getTxtNumberOrder())));
                    double sumPrice = productCarts.get(position).getTxtNumberOrder() * productCarts.get(position).getTxtProductPrice();
                    holder.txtSumNumbPrice.setText(String.valueOf(Math.round(sumPrice)) + " đ");
                }
            }
        });

        holder.imvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productCarts.get(position).setTxtNumberOrder(productCarts.get(position).getTxtNumberOrder() + 1);
                holder.txtNumberOrder.setText(String.valueOf(Math.round(productCarts.get(position).getTxtNumberOrder())));
                double sumPrice = productCarts.get(position).getTxtNumberOrder() * productCarts.get(position).getTxtProductPrice();
                holder.txtSumNumbPrice.setText(String.valueOf(Math.round(sumPrice)) + " đ");
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
            imvProductCart = itemView.findViewById(R.id.imvProductCart);
            txtSumNumbPrice = itemView.findViewById(R.id.txtSumNumbPrice);
            imvMinus = itemView.findViewById(R.id.imvMinus);
            imvPlus = itemView.findViewById(R.id.imvPlus);
        }
    }
}
