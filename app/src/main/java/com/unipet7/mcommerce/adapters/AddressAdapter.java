package com.unipet7.mcommerce.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.models.Addresses;

import java.util.ArrayList;

public class AddressAdapter extends BaseAdapter {
    Context context;
    ArrayList<Addresses> addresses;
    int item_layout;

    public AddressAdapter(Context context, ArrayList<Addresses> addresses, int item_layout) {
        this.context = context;
        this.addresses = addresses;
        this.item_layout = item_layout;
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
        return 0;
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

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        //binding
        Addresses address = addresses.get(position);
        holder.txtdetailadress.setText(address.getStreet() + " , " + address.getProvince());

        return view;
    }
    public static class ViewHolder{
        TextView txtdetailadress;
        EditText edtuser, edtphone, edtprovince, edtstreet;
    }
}
