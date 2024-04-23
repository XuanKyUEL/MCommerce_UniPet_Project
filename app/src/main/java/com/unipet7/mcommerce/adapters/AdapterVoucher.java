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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.models.Voucher;

import java.util.ArrayList;
import java.util.List;

public class AdapterVoucher extends RecyclerView.Adapter<AdapterVoucher.ViewHolder> {
    Context context;
    ArrayList<Voucher> vouchers;

    public AdapterVoucher(Context context, ArrayList<Voucher> vouchers) {
        this.context = context;
        this.vouchers = vouchers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.voucher_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVoucher.ViewHolder holder, int position) {
        holder.txtVoucherNumb.setText(String.valueOf(Math.round(vouchers.get(position).getTxtVoucherNumb())) + " %");
        holder.txtVoucherCode.setText(vouchers.get(position).getTxtVoucherCode());
        holder.txtVoucherDecription.setText(vouchers.get(position).getTxtVoucherDecription());
    }

    @Override
    public int getItemCount() {
        return vouchers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtVoucherCode, txtVoucherDecription, txtVoucherNumb;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtVoucherDecription = itemView.findViewById(R.id.txtVoucherDecription);
            txtVoucherCode = itemView.findViewById(R.id.txtVoucherCode);
            txtVoucherNumb = itemView.findViewById(R.id.txtVoucherNumb);
        }
    }
}
