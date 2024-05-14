package com.unipet7.mcommerce.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.activities.RateOrder;
import com.unipet7.mcommerce.models.HistoryOrders;
import com.unipet7.mcommerce.models.Product;
import com.unipet7.mcommerce.models.ProductCart;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DetailOrderAdapter extends BaseAdapter {
    Activity context;
    int item_layout;
    List<ProductCart> products;

    public DetailOrderAdapter(Activity context, int item_layout, List<ProductCart> products) {
        this.context = context;
        this.item_layout = item_layout;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DetailOrderAdapter.ViewHolder holder;
        if(convertView==null) {
            //Liên kết các view trên giao diện item list
            holder = new DetailOrderAdapter.ViewHolder();
            LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflate.inflate(item_layout, null);

            holder.imvThumb = convertView.findViewById(R.id.imvproductthumb);
            holder.order_productname = convertView.findViewById(R.id.order_productname1);
            holder.product_count = convertView.findViewById(R.id.product_count1);
            holder.product_price = convertView.findViewById(R.id.product_price1);

            convertView.setTag(holder);
        }else {
            holder = (DetailOrderAdapter.ViewHolder) convertView.getTag();
        }
        //Liên kết dữ liệu
        ProductCart product = products.get(position);
        holder.order_productname.setText(product.getProductName());
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY);

        String formattedCount = numberFormat.format(product.getNumOfProduct());
        holder.product_count.setText(formattedCount);

        String formattedPrice = numberFormat.format(product.getProductPrice()) + " đ";
        holder.product_price.setText(formattedPrice);

        return convertView;
    }
    public static class ViewHolder{
        ImageView imvThumb;
        TextView order_productname, product_count, product_price;
    }
}
