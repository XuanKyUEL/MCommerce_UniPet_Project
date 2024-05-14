package com.unipet7.mcommerce.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.fragments.FragmentAddressEdit;
import com.unipet7.mcommerce.models.Addresses;
import com.unipet7.mcommerce.utils.Constants;

import java.util.List;

public class AddressAdapter extends BaseAdapter {
    Context context;
    List<Addresses> addresses;
    int item_layout;

    private boolean isFromCheckout;

    public AddressAdapter(Context context, List<Addresses> addresses, int item_layout, boolean isFromCheckout) {
        this.context = context;
        this.addresses = addresses;
        this.item_layout = item_layout;
        this.isFromCheckout = isFromCheckout;
    }

    @Override
    public int getCount() {
        return addresses.size();
    }

    @Override
    public Object getItem(int position) {
        return addresses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(item_layout, null);
            holder.txtdetailadress = view.findViewById(R.id.txtdetailadress);
            holder.edtuser = view.findViewById(R.id.edtuser);
            holder.edtphone = view.findViewById(R.id.edtphone);
            holder.edtprovince = view.findViewById(R.id.edtprovince);
            holder.edtstreet = view.findViewById(R.id.edtstress);
            holder.name = view.findViewById(R.id.addressUsername);
            holder.editlayout = view.findViewById(R.id.editAddressImv);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        if (isFromCheckout) {
            holder.editlayout.setVisibility(View.GONE);
        } else {
            holder.editlayout.setVisibility(View.VISIBLE);
        }
        //binding
        Addresses address = addresses.get(position);
        holder.name.setText(address.getName());
        holder.txtdetailadress.setText(address.getStreet() + " , " + address.getProvince());
        holder.editlayout.setOnClickListener(v -> {
            FragmentAddressEdit editAdrressFragment = new FragmentAddressEdit();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.ADDRESS, address);
            editAdrressFragment.setArguments(bundle);

            FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, editAdrressFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
        return view;
    }
    public static class ViewHolder{
        TextView txtdetailadress, name;
        EditText edtuser, edtphone, edtprovince, edtstreet;
        ImageView editlayout;
    }
}
