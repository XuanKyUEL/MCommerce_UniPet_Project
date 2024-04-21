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
import com.unipet7.mcommerce.models.HistoryOrders;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HistoryOrderAdapter extends BaseAdapter {
    Activity context;
    int item_layout;
    List<HistoryOrders> historyOrdersList;

    public HistoryOrderAdapter(Activity context, int item_layout, List<HistoryOrders> historyOrdersList) {
        this.context = context;
        this.item_layout = item_layout;
        this.historyOrdersList = historyOrdersList;
    }

    @Override
    public int getCount() {
        return historyOrdersList.size();
    }

    @Override
    public Object getItem(int position) {
        return historyOrdersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null) {
            //Liên kết các view trên giao diện item list
            holder = new ViewHolder();
            LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflate.inflate(item_layout, null);

            holder.imvThumb = convertView.findViewById(R.id.order_imv);
            holder.order_date = convertView.findViewById(R.id.order_date);
            holder.order_code = convertView.findViewById(R.id.order_code);
            holder.order_status = convertView.findViewById(R.id.order_status);
            holder.order_productname = convertView.findViewById(R.id.order_productname);
            holder.product_count = convertView.findViewById(R.id.product_count);
            holder.product_info = convertView.findViewById(R.id.product_info);
            holder.product_price = convertView.findViewById(R.id.product_price);
            holder.order_totalprice = convertView.findViewById(R.id.order_totalprice);
            holder.rate_button=convertView.findViewById(R.id.btn_rate);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //Liên kết dữ liệu
        HistoryOrders historyOrder = historyOrdersList.get(position);
        holder.imvThumb.setImageResource(historyOrder.getImvThumb());
        holder.order_date.setText(historyOrder.getOrder_date());
        holder.order_code.setText(historyOrder.getOrder_code());
        holder.order_status.setText(historyOrder.getOrder_status());
        holder.order_productname.setText(historyOrder.getOrder_productname());
        holder.product_info.setText(historyOrder.getProduct_info());
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY);

        String formattedCount = numberFormat.format(historyOrder.getProduct_count());
        holder.product_count.setText(formattedCount);

        String formattedPrice = numberFormat.format(historyOrder.getProduct_count()) + " đ";
        holder.product_price.setText(formattedPrice);

        String formattedTotalPrice = numberFormat.format(historyOrder.getProduct_count()) + " đ";
        holder.product_price.setText(formattedTotalPrice);
        holder.rate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, RateOrder.class); // Sử dụng context của Adapter
//                context.startActivity(intent);
            }
        });

        return convertView;
    }

    public static class ViewHolder{
        ImageView imvThumb;
        Button rate_button;
        TextView order_date, order_code,order_status, order_productname, product_info, product_count, product_price, order_totalprice ;
    }
}
