package com.unipet7.mcommerce.adapters;

import android.content.Context;
import android.media.RouteListingPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.models.Notice;

import java.util.ArrayList;
import java.util.List;

public class NotiAdapter extends RecyclerView.Adapter<NotiAdapter.ViewHolder> {
    Context context;
    ArrayList<Notice> notice;

    public NotiAdapter(Context context, ArrayList<Notice> notice) {
        this.context = context;
        this.notice = notice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notification_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTitle.setText(notice.get(position).getNotiTtle());
        holder.txtDescript.setText(notice.get(position).getNotiDescription());
        holder.txtTime.setText(notice.get(position).getNotiTime());


    }

    @Override
    public int getItemCount() {
        return notice.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDescript, txtTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDescript = itemView.findViewById(R.id.txtNotiDescription);
            txtTime = itemView.findViewById(R.id.txtNotiTime);
            txtTitle = itemView.findViewById(R.id.txtNotiTitle);
        }
    }
}
