package com.unipet7.mcommerce.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.models.Voucher;

import java.util.List;

public class AdapterVoucher extends BaseAdapter {
    Context context;
    List<Voucher> vouchers;
    int item;

    public AdapterVoucher(Context context, List<Voucher> vouchers, int item) {
        this.context = context;
        this.vouchers = vouchers;
        this.item = item;
    }
    public static class ViewHolder {
        TextView txtVoucherCode, txtVoucherDecription, txtVoucherNumb;
    }


    @Override
    public int getCount() {
        return vouchers.size();
    }

    @Override
    public Object getItem(int position) {
        return vouchers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(item, null);

            holder.txtVoucherCode =convertView.findViewById(R.id.txtVoucherCode);
            holder.txtVoucherDecription =convertView.findViewById(R.id.txtVoucherDecription);
            holder.txtVoucherNumb = convertView.findViewById(R.id.txtVoucherNumb);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        Voucher v = vouchers.get(position);
        holder.txtVoucherNumb.setText(String.valueOf(Math.round(v.getTxtVoucherNumb())) + "%");
        holder.txtVoucherCode.setText(v.getTxtVoucherCode());
        holder.txtVoucherDecription.setText(v.getTxtVoucherDecription());
        return convertView;
    }
}
